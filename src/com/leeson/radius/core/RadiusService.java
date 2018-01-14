package com.leeson.radius.core;

import com.leeson.core.bean.Portalbas;
import com.leeson.core.bean.Radiusnas;
import com.leeson.core.query.RadiusnasQuery;
import com.leeson.core.service.RadiusnasService;
import com.leeson.portal.core.model.Config;
import com.leeson.portal.core.utils.SpringContextHelper;
import com.leeson.radius.core.model.RadiusNasMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.log4j.Logger;

public class RadiusService
  implements ServletContextListener
{
  private static Config config = Config.getInstance();
  private static Logger log = Logger.getLogger(RadiusService.class);
  private RadiusnasService radiusnasService;

  public void contextDestroyed(ServletContextEvent arg0)
  {
    Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
    if (basConfig.getIsdebug().equals("1"))
      log.info("Radius Service Stop");
  }

  public void contextInitialized(ServletContextEvent servletContextEvent)
  {
    this.radiusnasService = ((RadiusnasService)SpringContextHelper.getBean("radiusnasServiceImpl"));

    List<Radiusnas> es = this.radiusnasService.getRadiusnasList(new RadiusnasQuery());
    for (Radiusnas e : es) {
      String[] client = new String[9];
      client[0] = Tool.ByteToHex(e.getSharedSecret().getBytes());
      client[1] = e.getType();
      client[2] = e.getEx1();
      client[3] = e.getEx2();
      client[4] = e.getEx3();
      client[5] = e.getEx4();
      client[6] = e.getEx5();
      client[7] = e.getName();
      client[8] = e.getEx6();
      RadiusNasMap.getInstance().getNasMap().put(e.getIp(), client);
    }

    new Thread()
    {
      public void run()
      {
        try {
          RadiusMain.radiusServer();
        }
        catch (Exception e) {
          RadiusService.log.error("Radius Service Start Error !!");
          Tool.writeErrorLog("Error", e);
        }
      }
    }
    .start();
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.radius.core.RadiusService
 * JD-Core Version:    0.6.2
 */