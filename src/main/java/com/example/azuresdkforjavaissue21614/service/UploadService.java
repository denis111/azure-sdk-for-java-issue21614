package com.example.azuresdkforjavaissue21614.service;

import com.azure.storage.blob.BlobContainerAsyncClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.models.Block;
import com.azure.storage.blob.models.BlockList;
import com.azure.storage.blob.models.BlockListType;
import com.azure.storage.blob.specialized.BlockBlobAsyncClient;
import com.azure.storage.common.Utility;
import com.example.azuresdkforjavaissue21614.exception.ResumeException;
import com.example.azuresdkforjavaissue21614.util.IoUtil;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.WritableResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@Slf4j
public class UploadService {

  private final transient ResourceLoader resourceLoader;
  private final transient BlobContainerAsyncClient blobContainerClient;

  public static final long SIZE = 64 * 1024 * 1024;
  private static final int BUFFER_SIZE = 64 * 1024;
  private static final String BUCKET = "issue21614";

  @Autowired
  public UploadService(ResourceLoader resourceLoader,
      BlobServiceClientBuilder blobServiceClientBuilder) {
    this.resourceLoader = resourceLoader;
    this.blobContainerClient = blobServiceClientBuilder.buildAsyncClient()
        .getBlobContainerAsyncClient(BUCKET);
  }

  public String uploadFile(String filename, Long from, Long to, String md5, long length, InputStream is) {

    boolean wholeFile = from != null && to != null && from == 0 && to + 1 >= SIZE;
    if (from != null && to != null) {
      if (length > (to - from) + 1) {
        throw new RuntimeException("Invalid file part size!");
      }
      if (!wholeFile
          && (length - 1 > to
          || (SIZE - 1 > to && to - from < BUFFER_SIZE - 2))) {
        throw new RuntimeException("Too small part size!");
      }
    }
    if (wholeFile) {
      return uploadFile(filename, is);
    } else {
      var blockBlobClient = blobContainerClient.getBlobAsyncClient(filename)
          .getBlockBlobAsyncClient();
      if (exists(filename)) {
        throw new ResumeException(0, sizeRemote(filename) - 1);
      }
      if (from == null || to == null) {
        if (exists(filename)) {
          throw new ResumeException(0, sizeRemote(filename) - 1);
        } else {
          throw new ResumeException(
              0, getBlocks(blockBlobClient).stream().mapToLong(Block::getSizeLong).sum() - 1);
        }
      }
      if (from == 0) {
        log.info("Uploading to Azure: " + filename);
      }
      List<Block> blocks = from == 0 ? null : getBlocks(blockBlobClient);
      if (blocks != null) {
        long sum = blocks.stream().mapToLong(Block::getSizeLong).sum();
        if (from != sum) {
          throw new ResumeException(0, sum - 1);
        }
      }
      long partSize = to + 1 - from;
      String blockId = Base64.getEncoder().encodeToString(
          concat(filename.getBytes(StandardCharsets.UTF_8), longToBytes(from)));
      blockBlobClient.stageBlockWithResponse(blockId,
          Utility.convertStreamToByteBuffer(is, partSize, BUFFER_SIZE, false), partSize,
          StringUtils.hasLength(md5) ? Base64.getDecoder().decode(md5) : null, null)
          .block();
      // last block
      if (SIZE - 1 == to) {
        blockBlobClient.listBlocks(BlockListType.UNCOMMITTED)
            .blockOptional()
            .ifPresent(list -> blockBlobClient.commitBlockList(
                list.getUncommittedBlocks().stream().map(Block::getName)
                    .collect(Collectors.toList())).block());
        log.info("Uploaded to Azure: " + filename);
      } else {
        throw new ResumeException(0, to);
      }
      return blockBlobClient.getBlobUrl();
    }
  }

  public String uploadFile(String filename, InputStream is) {
    log.info("Uploading to Azure: " + filename);
    Resource resource = getResource(filename);

    try {

      try (OutputStream os = ((WritableResource) resource).getOutputStream()) {
        IoUtil.fastCopy(is, os, BUFFER_SIZE);
      }

      log.info("Uploaded to Azure: " + filename);

    } catch (IOException gsEx) {
      throw new RuntimeException("Error uploading to Azure Strorage", gsEx);
    }
    return blobContainerClient.getBlobAsyncClient(filename)
        .getBlockBlobAsyncClient()
        .getBlobUrl();
  }

  @PostConstruct
  protected void initContainer() {
    this.blobContainerClient.exists().filter(e -> !e)
        .flatMap(e -> this.blobContainerClient.create())
        .block();
  }

  private Resource getResource(String filename) {
    return resourceLoader.getResource("azure-blob://" + BUCKET + "/" + filename);
  }

  private boolean exists(String filename) {
    return getResource(filename).exists();
  }

  private long sizeRemote(String filename) {
    try {
      return getResource(filename).contentLength();
    } catch (IOException e) {
      throw new RuntimeException("Error reading Azure storage", e);
    }
  }

  private List<Block> getBlocks(BlockBlobAsyncClient blockBlobClient) {
    BlockList blockList = blockBlobClient.listBlocks(BlockListType.ALL).block();
    if (blockList == null) {
      throw new RuntimeException("Remote file not found");
    }
    List<Block> blocks = new ArrayList<>(blockList.getCommittedBlocks());
    blocks.addAll(blockList.getUncommittedBlocks());
    return blocks;
  }

  private byte[] longToBytes(long x) {
    ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
    buffer.putLong(x);
    return buffer.array();
  }

  private static byte[] concat(byte[] first, byte[] second) {
    byte[] result = Arrays.copyOf(first, first.length + second.length);
    System.arraycopy(second, 0, result, first.length, second.length);
    return result;
  }
}
