package com.example.azuresdkforjavaissue21614.exception;

import lombok.Getter;

@Getter
public class ResumeException extends RuntimeException {

  private static final long serialVersionUID = 1L;
  private final long start;
  private final long end;

  /**
   * Resume exception.
   *
   * @param start start
   * @param end end
   */
  public ResumeException(long start, long end) {
    super("Resume Incomplete");
    this.start = start;
    this.end = end;
  }

  /**
   * Resume exception.
   *
   * @param start start
   * @param end end
   * @param message message
   */
  public ResumeException(long start, long end, String message) {
    super(message);
    this.start = start;
    this.end = end;
  }
}
