package com.leeson.radius.core;

import com.leeson.common.utils.ThreadLocalDateUtil;
import com.leeson.common.utils.stringUtils;
import com.leeson.core.bean.Portalbas;
import com.leeson.core.service.ConfigService;
import com.leeson.portal.core.utils.SpringContextHelper;
import com.leeson.radius.core.model.RadiusNasMap;
import com.leeson.radius.core.model.RadiusOnlineMap;
import com.leeson.radius.core.utils.COAThread;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.log4j.Logger;

public class RadiusCheckService
  implements ServletContextListener
{
  private static com.leeson.portal.core.model.Config config = com.leeson.portal.core.model.Config.getInstance();
  private static Logger logger = Logger.getLogger(RadiusCheckService.class);
  private static ConfigService configService;
  private String path;
  private static Timer timerstartCheck = new Timer();

  public void contextDestroyed(ServletContextEvent servletContextEvent)
  {
    timerstartCheck.cancel();
    configService = (ConfigService)
      SpringContextHelper.getBean("configServiceImpl");
    if (1 == configService.getConfigByKey(Long.valueOf(1L)).getShutdownKick().intValue())
    {
      RadiusOnlineMap.getInstance().getRadiusOnlineMap().clear();
    }
    RadiusOnlineMapToDisk(servletContextEvent);
    COAThread.offThread();
  }

  public void contextInitialized(ServletContextEvent servletContextEvent)
  {
    configService = (ConfigService)
      SpringContextHelper.getBean("configServiceImpl");
    if (1 != configService.getConfigByKey(Long.valueOf(1L)).getShutdownKick().intValue()) {
      RadiusOnlineMapFromDisk(servletContextEvent);
    }

    new Thread()
    {
      public void run()
      {
        try {
          RadiusCheckService.this.startCheck();
          RadiusCheckService.logger.info("Radius Online Check Servcie Start Success!! 1min Later Start , Every " + 
            RadiusCheckService.configService.getConfigByKey(Long.valueOf(1L)).getCheckTime() + 
            " S Check Once !!");
        }
        catch (Exception e) {
          RadiusCheckService.logger.error("Radius Online Check Servcie Start ERROR !!");
        }
      }
    }
    .start();

    this.path = servletContextEvent.getServletContext().getRealPath("/");
    new Thread()
    {
      public void run()
      {
        try {
          RadiusCheckService.this.deleteFiles(RadiusCheckService.this.path);
        }
        catch (Exception e) {
          RadiusCheckService.logger.error("==============ERROR Start=============");
          RadiusCheckService.logger.error(e);
          RadiusCheckService.logger.error("ERROR INFO ", e);
          RadiusCheckService.logger.error("==============ERROR End=============");
        }
      }
    }
    .start();
  }

  private void startCheck()
  {
    TimerTask task = new TimerTask()
    {
      public void run() {
        Portalbas basConfig = (Portalbas)RadiusCheckService.config.getConfigMap().get("");
        try {
          if (1 == RadiusCheckService.configService.getConfigByKey(Long.valueOf(1L)).getRadiusOn().intValue()) {
            if (basConfig.getIsdebug().equals("1")) {
              RadiusCheckService.logger.info("Start Check Radius Online User List !!");
            }
            Iterator iterator = RadiusOnlineMap.getInstance()
              .getRadiusOnlineMap().keySet().iterator();
            while (iterator.hasNext()) {
              Object o = iterator.next();
              String t = o.toString();
              String[] radiusOnlineInfo = 
                (String[])RadiusOnlineMap.getInstance()
                .getRadiusOnlineMap().get(t);
              if ((radiusOnlineInfo != null) && (radiusOnlineInfo.length > 0))
                try {
                  Date updateTime = ThreadLocalDateUtil.parse(radiusOnlineInfo[14]);
                  String nowString = ThreadLocalDateUtil.format(new Date());
                  Date nowTime = ThreadLocalDateUtil.parse(nowString);
                  long costTime = nowTime.getTime() - updateTime.getTime();
                  String[] clients = null;
                  String nasip = radiusOnlineInfo[0];
                  String NasIdentifier = radiusOnlineInfo[16];
                  if (stringUtils.isNotBlank(nasip)) {
                    clients = (String[])RadiusNasMap.getInstance().getNasMap().get(nasip);
                  }

                  if ((clients == null) || (clients.length == 0)) {
                    String ip = radiusOnlineInfo[1];
                    if ((stringUtils.isNotBlank(ip)) && 
                      (!ip.equals(nasip))) {
                      Iterator iteratorRM = RadiusNasMap.getInstance().getNasMap().keySet().iterator();
                      while (iteratorRM.hasNext()) {
                        Object orm = iteratorRM.next();
                        String ttm = orm.toString();
                        if (ip.equals(RadiusCheckService.DomainToIP(ttm))) {
                          clients = (String[])RadiusNasMap.getInstance().getNasMap().get(ttm);
                          break;
                        }
                      }
                    }
                  }

                  if (((clients == null) || (clients.length == 0)) && 
                    (NasIdentifier != null) && (NasIdentifier != "")) {
                    Iterator iteratorNas = RadiusNasMap.getInstance().getNasMap().keySet()
                      .iterator();
                    while (iteratorNas.hasNext()) {
                      Object oNas = iteratorNas.next();
                      String tNas = oNas.toString();
                      String[] temp = (String[])RadiusNasMap.getInstance().getNasMap().get(tNas);
                      if ((temp.length > 0) && (NasIdentifier.equals(temp[7]))) {
                        clients = temp;
                        break;
                      }
                    }

                  }

                  if ((clients != null) && (clients.length > 0)) {
                    String timeS = clients[4];
                    if (stringUtils.isNotBlank(timeS)) {
                      long time = Long.valueOf(timeS).longValue();
                      if ((time > 0L) && 
                        (costTime >= time * 1000L))
                        COAThread.COA_Account_Cost(radiusOnlineInfo, "Radius Update Check COA");
                    }
                  }
                }
                catch (Exception e)
                {
                  RadiusCheckService.logger.error("Radius Online Check Servcie ERROR!!");
                  RadiusCheckService.logger.error("==============ERROR Start=============");
                  RadiusCheckService.logger.error(e);
                  RadiusCheckService.logger.error("ERROR INFO ", e);
                  RadiusCheckService.logger.error("==============ERROR End=============");
                }
            }
          }
        }
        catch (Exception e) {
          RadiusCheckService.logger.error("Radius Online Check Servcie ERROR!!");
          RadiusCheckService.logger.error("==============ERROR Start=============");
          RadiusCheckService.logger.error(e);
          RadiusCheckService.logger.error("ERROR INFO ", e);
          RadiusCheckService.logger.error("==============ERROR End=============");
        }
      }
    };
    long delay = 60000L;
    long intevalPeriod = configService.getConfigByKey(Long.valueOf(1L)).getCheckTime().longValue() * 1000L;

    timerstartCheck.scheduleAtFixedRate(task, delay, intevalPeriod);
  }

  private void RadiusOnlineMapToDisk(ServletContextEvent servletContextEvent)
  {
    Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
    ObjectOutputStream os = null;
    try {
      os = new ObjectOutputStream(new FileOutputStream(
        servletContextEvent.getServletContext().getRealPath("/") + 
        "/RadiusOnlineMap.dat"));
      os.writeObject(RadiusOnlineMap.getInstance().getRadiusOnlineMap());
      if (basConfig.getIsdebug().equals("1"))
        logger.info("RadiusOnlineMapToDisk !!");
    }
    catch (Exception e) {
      logger.error("==============ERROR Start=============");
      logger.error(e);
      logger.error("ERROR INFO ", e);
      logger.error("==============ERROR End=============");
      try
      {
        if (os != null)
          os.close();
      }
      catch (IOException e1) {
        logger.error("==============ERROR Start=============");
        logger.error(e1);
        logger.error("ERROR INFO ", e1);
        logger.error("==============ERROR End=============");
      }
    }
    finally
    {
      try
      {
        if (os != null)
          os.close();
      }
      catch (IOException e) {
        logger.error("==============ERROR Start=============");
        logger.error(e);
        logger.error("ERROR INFO ", e);
        logger.error("==============ERROR End=============");
      }
    }
  }

  private void RadiusOnlineMapFromDisk(ServletContextEvent servletContextEvent)
  {
    Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
    ObjectInputStream is = null;
    File parent = new File(servletContextEvent.getServletContext()
    		.getRealPath("/") + "/RadiusOnlineMap.dat");

    label318: 
    try { if (parent.exists()) {
        is = new ObjectInputStream(new FileInputStream(
          servletContextEvent.getServletContext()
          .getRealPath("/") + 
          "/RadiusOnlineMap.dat"));
        Map radiusOnlineMap = (ConcurrentHashMap)is
          .readObject();
        RadiusOnlineMap.getInstance().setRadiusOnlineMap(radiusOnlineMap);
        if (basConfig.getIsdebug().equals("1")) {
          logger.info("RadiusOnlineMapFromDisk !!");

          break label318;
        } } else if (basConfig.getIsdebug().equals("1")) {
        logger.info("RadiusOnlineMap File Not Exists !!");
      }
    } catch (Exception e)
    {
      logger.error("==============ERROR Start=============");
      logger.error(e);
      logger.error("ERROR INFO ", e);
      logger.error("==============ERROR End=============");
      try
      {
        if (is != null) {
          is.close();
        }
        parent = null;
      } catch (IOException e1) {
        logger.error("==============ERROR Start=============");
        logger.error(e1);
        logger.error("ERROR INFO ", e1);
        logger.error("==============ERROR End=============");
      }
    }
    finally
    {
      try
      {
        if (is != null) {
          is.close();
        }
        parent = null;
      } catch (IOException e) {
        logger.error("==============ERROR Start=============");
        logger.error(e);
        logger.error("ERROR INFO ", e);
        logger.error("==============ERROR End=============");
      }
    }
  }

  private void deleteFiles(String path) {
    Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
    File file = new File(path + "/RadiusOnlineMap.dat");
    if (file.exists()) {
      file.delete();
      if (basConfig.getIsdebug().equals("1")) {
        logger.info("Server Start Format RadiusOnlineMap File!!");
      }
    }
    file = null;
  }

  private static String DomainToIP(String domain) {
    if (configService.getConfigByKey(Long.valueOf(1L)).getUseDomain().intValue() == 0) {
      return domain;
    }
    String ip = "";
    try {
      ip = java.net.InetAddress.getByName(domain).toString().split("/")[1];
    } catch (UnknownHostException e) {
      Tool.writeErrorLog("Radius DomainToIP ERROR INFO ", e);
    }
    System.out.println("Domain:" + domain + " IP:" + ip);
    return ip;
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.radius.core.RadiusCheckService
 * JD-Core Version:    0.6.2
 */