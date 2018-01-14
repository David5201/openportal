package com.leeson.common.web.springmvc;

import org.springframework.web.servlet.view.AbstractTemplateViewResolver;
import org.springframework.web.servlet.view.AbstractUrlBasedView;

public class SimpleFreeMarkerViewResolver extends AbstractTemplateViewResolver
{
  public SimpleFreeMarkerViewResolver()
  {
    setViewClass(SimpleFreeMarkerView.class);
  }

  protected AbstractUrlBasedView buildView(String viewName)
    throws Exception
  {
    AbstractUrlBasedView view = super.buildView(viewName);

    if (viewName.startsWith("/")) {
      view.setUrl(viewName + getSuffix());
    }
    return view;
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.common.web.springmvc.SimpleFreeMarkerViewResolver
 * JD-Core Version:    0.6.2
 */