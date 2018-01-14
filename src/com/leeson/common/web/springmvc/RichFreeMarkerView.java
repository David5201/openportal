package com.leeson.common.web.springmvc;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;

public class RichFreeMarkerView extends FreeMarkerView
{
  public static final String CONTEXT_PATH = "base";

  protected void exposeHelpers(Map model, HttpServletRequest request)
    throws Exception
  {
    super.exposeHelpers(model, request);
    model.put("base", request.getContextPath());
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.common.web.springmvc.RichFreeMarkerView
 * JD-Core Version:    0.6.2
 */