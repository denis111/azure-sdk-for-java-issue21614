package com.example.azuresdkforjavaissue21614.controller;

import com.example.azuresdkforjavaissue21614.exception.ResumeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
@Slf4j
public class ResponseExceptionHandler {

  private static final String INTERNAL_SERVER_ERROR = "Internal server error";

  /**
   * Handle resume exception.
   *
   * @param e exception
   * @return Response Entity
   */
  public static ResponseEntity<ErrorResponse> handleResumeException(ResumeException e) {
    return ResponseEntity.status(HttpStatus.PERMANENT_REDIRECT)
        .header(HttpHeaders.CONTENT_LENGTH, "0")
        .header(HttpHeaders.RANGE, "bytes=" + e.getStart() + "-" + e.getEnd())
        .body(new ErrorResponse(e));
  }

  @ExceptionHandler({Exception.class})
  protected ResponseEntity<ErrorResponse> handle(Exception e, WebRequest request) {
      log.error("API Error:", e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(new ErrorResponse(INTERNAL_SERVER_ERROR));
    }
}
