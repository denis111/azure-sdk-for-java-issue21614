package com.example.azuresdkforjavaissue21614.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

public class IoUtil {

  private static final int BUFFER_SIZE = 16384;

  /**
   * Fast copy.
   *
   * @param src src
   * @param dest dest
   * @param bufferSize bufferSize
   * @throws IOException exception
   */
  @SuppressWarnings("PMD.CloseResource")
  public static void fastCopy(final InputStream src, final OutputStream dest, int bufferSize)
      throws IOException {
    ReadableByteChannel inputChannel = Channels.newChannel(src);
    WritableByteChannel outputChannel = Channels.newChannel(dest);
    fastCopy(inputChannel, outputChannel, bufferSize);
  }

  public static void fastCopy(final InputStream src, final OutputStream dest) throws IOException {
    fastCopy(src, dest, BUFFER_SIZE);
  }

  /**
   * Fast copy.
   *
   * @param src src
   * @param dest dest
   * @param bufferSize bufferSize
   * @throws IOException exception
   */
  public static void fastCopy(
      final ReadableByteChannel src, final WritableByteChannel dest, int bufferSize)
      throws IOException {
    final ByteBuffer buffer = ByteBuffer.allocateDirect(bufferSize);

    while (src.read(buffer) != -1) {
      buffer.flip();
      dest.write(buffer);
      buffer.compact();
    }

    buffer.flip();

    while (buffer.hasRemaining()) {
      dest.write(buffer);
    }
  }
}
