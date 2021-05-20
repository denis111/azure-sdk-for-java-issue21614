package com.example.azuresdkforjavaissue21614.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * When the request is over it will be cleaned up and everything reset to null. This wrapper keeps
 * serverName even if request is over.
 */
public class ServerNameHttpServletRequestWrapper extends HttpServletRequestWrapper {

  private final String serverName;

  /**
   * Constructs a request object wrapping the given request.
   *
   * @param request the {@link HttpServletRequest} to be wrapped.
   * @throws IllegalArgumentException if the request is null
   */
  public ServerNameHttpServletRequestWrapper(HttpServletRequest request) {
    super(request);
    serverName = request.getServerName();
  }

  @Override
  public String getServerName() {
    return serverName;
  }
}
