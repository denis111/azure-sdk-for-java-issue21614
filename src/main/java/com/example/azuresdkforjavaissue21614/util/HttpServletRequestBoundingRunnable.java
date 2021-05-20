package com.example.azuresdkforjavaissue21614.util;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequestEvent;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.RequestContextListener;

/**
 * Bounds request to another thread for async {@link RequestContextHolder}.
 */
public abstract class HttpServletRequestBoundingRunnable implements Runnable {

  private final transient HttpServletRequest request;

  /**
   * HttpServletRequestBoundingRunnable.
   *
   * @param request request
   */
  public HttpServletRequestBoundingRunnable(HttpServletRequest request) {
    this.request =
        request instanceof ServerNameHttpServletRequestWrapper
            ? request
            : new ServerNameHttpServletRequestWrapper(request);
  }

  @Override
  public void run() {
    final RequestContextListener rcl = new RequestContextListener();
    final ServletContext sc = request.getServletContext();
    if (sc != null) {
      rcl.requestInitialized(new ServletRequestEvent(sc, request));
    }
    doRun();
  }

  public abstract void doRun();
}
