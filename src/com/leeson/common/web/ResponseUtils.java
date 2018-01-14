package com.leeson.common.web;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ResponseUtils
{
  public static final Logger log = LoggerFactory.getLogger(ResponseUtils.class);

  public static void renderText(HttpServletResponse response, String text)
  {
    render(response, "text/plain;charset=UTF-8", text);
  }

  public static void renderJson(HttpServletResponse response, String text)
  {
    render(response, "application/json;charset=UTF-8", text);
  }

  public static void renderXml(HttpServletResponse response, String text)
  {
    render(response, "text/xml;charset=UTF-8", text);
  }

  public static void render(HttpServletResponse response, String contentType, String text)
  {
    response.setContentType(contentType);
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0L);
    try {
      response.getWriter().write(text);
    } catch (IOException e) {
      log.error(e.getMessage(), e);
    }
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.common.web.ResponseUtils
 * JD-Core Version:    0.6.2
 */