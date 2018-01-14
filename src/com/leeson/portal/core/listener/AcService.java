package com.leeson.portal.core.listener;

import AC.Server.ACServer;
import com.leeson.core.bean.Portalbas;
import com.leeson.portal.core.model.Config;
import java.util.Map;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.log4j.Logger;

public class AcService
  implements ServletContextListener
{
  private static Config config = Config.getInstance();
  private static Logger log = Logger.getLogger(AcService.class);

  public void contextDestroyed(ServletContextEvent arg0)
  {
    if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1"))
      log.info("AC Service Stop !!");
  }

  public void contextInitialized(ServletContextEvent servletContextEvent)
  {
    new Thread()
    {
      public void run()
      {
        try {
          ACServer.openServer();
        }
        catch (Exception e) {
          AcService.log.error("AC Service Start ERROR !!");
          e.printStackTrace();
        }
      }
    }
    .start();
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.listener.AcService
 * JD-Core Version:    0.6.2
 */