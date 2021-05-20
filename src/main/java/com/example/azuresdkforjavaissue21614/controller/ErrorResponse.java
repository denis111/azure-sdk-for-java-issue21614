package com.example.azuresdkforjavaissue21614.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Error response. */
@JsonInclude(value = Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
public class ErrorResponse {

  private String message;

  public ErrorResponse(String message) {
    this.message = message;
  }

  public ErrorResponse(RuntimeException e) {
    this.message = e.getMessage();
  }
}
