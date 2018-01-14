package com.leeson.common.web.springmvc;

import javax.servlet.ServletContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

@Component
public class ServletContextRealPathResolver
  implements RealPathResolver, ServletContextAware
{
  private ServletContext context;

  public String get(String path)
  {
    String realpath = this.context.getRealPath(path);

    if (realpath == null) {
      realpath = this.context.getRealPath("/") + path;
    }
    return realpath;
  }

  public void setServletContext(ServletContext servletContext) {
    this.context = servletContext;
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.common.web.springmvc.ServletContextRealPathResolver
 * JD-Core Version:    0.6.2
 */