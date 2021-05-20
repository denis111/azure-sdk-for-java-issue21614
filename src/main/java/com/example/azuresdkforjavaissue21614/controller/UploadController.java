package com.example.azuresdkforjavaissue21614.controller;

import com.example.azuresdkforjavaissue21614.exception.ResumeException;
import com.example.azuresdkforjavaissue21614.service.UploadService;
import com.example.azuresdkforjavaissue21614.util.HttpServletRequestBoundingRunnable;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ForkJoinPool;
import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

@RestController
@PermitAll
@Slf4j
public class UploadController {

  private final UploadService uploadService;

  @Autowired
  public UploadController(UploadService uploadService) {
    this.uploadService = uploadService;
  }

  @RequestMapping(
      path = "/upload/{filename}",
      method = RequestMethod.PUT,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public DeferredResult<String> uploadTransferFile(
      @PathVariable("filename") String filename,
      HttpServletRequest request,
      @RequestHeader(value = "Content-MD5", required = false) String md5,
      @RequestHeader(value = HttpHeaders.CONTENT_LENGTH, required = false, defaultValue = "-1")
          final long size,
      @RequestHeader(value = HttpHeaders.CONTENT_RANGE, required = false) final String range)
      throws IOException {
    ShallowEtagHeaderFilter.disableContentCaching(request);
    DeferredResult<String> result = new DeferredResult<>(90000L * 60);
    final var is = (InputStream) request.getInputStream();
    ForkJoinPool.commonPool()
        .submit(
            new HttpServletRequestBoundingRunnable(request) {
              @Override
              public void doRun() {
                try {
                  var innerRange = range;
                  if (StringUtils.hasLength(innerRange)) {
                    innerRange = innerRange.replaceFirst("bytes ", "");
                    String parts = innerRange.split("/")[0];
                    String[] fromTo = parts.split("-");
                    Long start = fromTo.length > 1 ? Long.valueOf(fromTo[0]) : null;
                    Long end = fromTo.length > 1 ? Long.valueOf(fromTo[1]) : null;
                    result.setResult(
                        uploadService.uploadFile(filename, start, end, md5, size, is)
                    );
                  } else {
                    result.setResult(
                        uploadService.uploadFile(filename, is));
                  }
                } catch (ResumeException e) {
                  result.setErrorResult(
                      ResponseExceptionHandler.handleResumeException(e));
                } catch (Exception e) {
                  result.setErrorResult(e);
                  log.error("Upload error", e);
                }
              }
            });
    return result;
  }
}
