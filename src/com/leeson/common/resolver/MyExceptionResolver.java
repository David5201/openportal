package com.leeson.common.resolver;

import com.leeson.core.bean.Portalbas;
import com.leeson.portal.core.model.Config;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

public class MyExceptionResolver
  implements HandlerExceptionResolver
{
  private static Logger logger = Logger.getLogger(MyExceptionResolver.class);
  private static Config config = Config.getInstance();

  public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
  {
    Portalbas basConfig = (Portalbas)config.getConfigMap().get("");

    if (basConfig.getIsdebug().equals("1")) {
      logger.error("==============Error Start=============");
      logger.error(ex);
      logger.error("Error Info", ex);
      logger.error("==============Error Stop=============");
    }

    ModelAndView mv = new ModelAndView("errors");

    return mv;
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.common.resolver.MyExceptionResolver
 * JD-Core Version:    0.6.2
 */