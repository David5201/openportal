package com.leeson.portal.core.listener;

import com.leeson.common.utils.ThreadLocalDateUtil;
import com.leeson.common.utils.stringUtils;
import com.leeson.core.bean.Portalautologin;
import com.leeson.core.bean.Portalbas;
import com.leeson.core.service.PortalautologinService;
import com.leeson.core.utils.Kick;
import com.leeson.portal.core.model.AutoLoginCheckTimeMap;
import com.leeson.portal.core.model.AutoLoginMacMap;
import com.leeson.portal.core.model.Config;
import com.leeson.portal.core.model.LateAuthMap;
import com.leeson.portal.core.model.OnlineMap;
import com.leeson.portal.core.utils.SpringContextHelper;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
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

public class AutoLoginService
  implements ServletContextListener
{
  private static Config config = Config.getInstance();
  private static Logger logger = Logger.getLogger(AutoLoginService.class);
  private static PortalautologinService autoLoginService;
  private static Timer timerstartCheck = new Timer();
  private static Timer timerLateAuthCheck = new Timer();

  public void contextDestroyed(ServletContextEvent servletContextEvent)
  {
    timerstartCheck.cancel();
    timerLateAuthCheck.cancel();
    limitMacMapToDisk(servletContextEvent);
    AutoLoginCheckTimeMapToDisk(servletContextEvent);
  }

  public void contextInitialized(ServletContextEvent servletContextEvent)
  {
    AutoLoginCheckTimeMapFromDisk(servletContextEvent);
    if ((AutoLoginCheckTimeMap.getInstance()
      .getAutoLoginCheckTimeMap().get("time") == null) || 
      (10L > 
      ((Long)AutoLoginCheckTimeMap.getInstance()
      .getAutoLoginCheckTimeMap().get("time")).longValue())) {
      AutoLoginCheckTimeMap.getInstance().getAutoLoginCheckTimeMap()
        .put("time", Long.valueOf(10L));
    }
    limitMacMapFromDisk(servletContextEvent);

    autoLoginService = (PortalautologinService)
      SpringContextHelper.getBean("portalautologinServiceImpl");

    new Thread()
    {
      public void run()
      {
        try {
          AutoLoginService.this.startCheck();
          AutoLoginService.logger.info("AutoLogin Check Servcie Start Success!! 1min Later Start , Every " + 
            AutoLoginCheckTimeMap.getInstance()
            .getAutoLoginCheckTimeMap().get("time") + 
            "min Check Once !!");
        }
        catch (Exception e) {
          AutoLoginService.logger.error("AutoLogin Check Servcie Start ERROR !!");
        }
      }
    }
    .start();

    new Thread()
    {
      public void run()
      {
        try {
          AutoLoginService.this.LateAuthCheck();
          AutoLoginService.logger.info("LateAuth Check Servcie Start Success!! 1min Later Start , Every 10 S Check Once !!");
        }
        catch (Exception e)
        {
          AutoLoginService.logger.error("LateAuth Check Servcie Start ERROR !!");
        }
      }
    }
    .start();
  }

  private void LateAuthCheck()
  {
    TimerTask task = new TimerTask()
    {
      public void run() {
        if (CheckOnlineService.Do())
          try {
            Iterator iteratorMac = LateAuthMap.getInstance()
              .getLateAuthMap().keySet()
              .iterator();
            while (iteratorMac.hasNext()) {
              Object o = iteratorMac.next();
              String t = o.toString();
              Date loginDate = 
                (Date)LateAuthMap.getInstance()
                .getLateAuthMap().get(t);
              Date nowDate = new Date();
              long willTime = 14712289280L;
              Date willDate = new Date(nowDate.getTime() + 
                willTime);
              if ((loginDate != null) && 
                (loginDate.getTime() < nowDate.getTime())) {
                Iterator it = OnlineMap.getInstance()
                  .getOnlineUserMap().keySet()
                  .iterator();
                while (it.hasNext()) {
                  Object oo = it.next();
                  String tt = oo.toString();
                  String[] loginInfo = 
                    (String[])OnlineMap.getInstance()
                    .getOnlineUserMap().get(tt);
                  String mac = loginInfo[4];
                  if (t.equals(mac)) {
                    String basip = loginInfo[10];
                    Portalbas bas = 
                      (Portalbas)AutoLoginService.config.getConfigMap()
                      .get(basip);
                    if (bas != null) {
                      Integer state = bas.getLateAuth();
                      Long perTime = bas
                        .getLateAuthTime();
                      if ((1 == state.intValue()) && (perTime != null) && 
                        (perTime.longValue() > 0L)) {
                        long costTime = nowDate
                          .getTime() - 
                          loginDate.getTime();
                        costTime /= 1000L;
                        System.out
                          .println("LaterAuth Check mac: " + 
                          t + 
                          " timepermit: " + 
                          perTime + 
                          " costTime:" + 
                          costTime);
                        if (costTime <= perTime.longValue()) break;
                        LateAuthMap.getInstance()
                          .getLateAuthMap()
                          .put(t, willDate);

                        Kick.kickUserLaterAuth(tt);

                        break;
                      }LateAuthMap.getInstance()
                        .getLateAuthMap()
                        .put(t, willDate);

                      Kick.kickUserLaterAuth(tt);

                      break;
                    }LateAuthMap.getInstance()
                      .getLateAuthMap()
                      .put(t, willDate);

                    Kick.kickUserLaterAuth(tt);

                    break;
                  }
                }
              }
            }
          } catch (Exception e) {
            AutoLoginService.logger.error("LateAuth Check Start ERROR!!");
            AutoLoginService.logger.error("==============ERROR Start=============");
            AutoLoginService.logger.error(e);
            AutoLoginService.logger.error("ERROR INFO ", e);
            AutoLoginService.logger.error("==============ERROR End=============");
          }
      }
    };
    long delay = 20000L;
    long intevalPeriod = 10000L;
    timerLateAuthCheck.scheduleAtFixedRate(task, delay, intevalPeriod);
  }

  private void startCheck()
  {
    TimerTask task = new TimerTask()
    {
      public void run() {
        if (CheckOnlineService.Do())
          try {
            Iterator iteratorMac = AutoLoginMacMap.getInstance()
              .getAutoLoginMacMap().keySet()
              .iterator();
            while (iteratorMac.hasNext()) {
              Object o = iteratorMac.next();
              String t = o.toString();
              String[] TimeInfo = 
                (String[])AutoLoginMacMap.getInstance()
                .getAutoLoginMacMap().get(t);
              Long id = Long.valueOf(TimeInfo[2]);
              Portalautologin autologin = AutoLoginService.autoLoginService
                .getPortalautologinByKey(id);
              if ((autologin != null) && 
                (autologin.getState().intValue() == 1)) {
                Long timepermit = autologin.getTime();
                if ((timepermit != null) && (timepermit.longValue() > 0L)) {
                  String loginTimeString = TimeInfo[0];
                  Long oldcostTime = 
                    Long.valueOf(TimeInfo[1]);

                  if (stringUtils.isNotBlank(loginTimeString)) {
                    Date loginTime = 
                      ThreadLocalDateUtil.parse(loginTimeString);
                    String nowString = 
                      ThreadLocalDateUtil.format(new Date());
                    Date nowTime = 
                      ThreadLocalDateUtil.parse(nowString);
                    Long costTime = Long.valueOf(nowTime.getTime() - 
                      loginTime.getTime() + 
                      oldcostTime.longValue());
                    System.out
                      .println("AutoLogin Check mac: " + 
                      t + 
                      " id: " + 
                      id + 
                      " timepermit: " + 
                      timepermit + 
                      " costTime:" + 
                      costTime.longValue() / 1000L);
                    if (costTime.longValue() >= timepermit.longValue()) {
                      Iterator it = 
                        OnlineMap.getInstance()
                        .getOnlineUserMap()
                        .keySet().iterator();
                      while (it.hasNext()) {
                        Object oo = it.next();
                        String tt = oo.toString();
                        String[] loginInfo = 
                          (String[])OnlineMap.getInstance()
                          .getOnlineUserMap()
                          .get(tt);
                        String mac = loginInfo[4];
                        if (t.equals(mac)) {
                          Kick.kickUserAutoLogin(tt);
                          break;
                        }
                      }
                      TimeInfo[0] = "";
                      TimeInfo[1] = 
                        String.valueOf(costTime);

                      AutoLoginMacMap.getInstance()
                        .getAutoLoginMacMap()
                        .remove(t);
                    }
                  }
                }
              }
            }

          }
          catch (Exception e)
          {
            AutoLoginService.logger.error("AutoLogin Check Start ERROR!!");
            AutoLoginService.logger.error("==============ERROR Start=============");
            AutoLoginService.logger.error(e);
            AutoLoginService.logger.error("ERROR INFO ", e);
            AutoLoginService.logger.error("==============ERROR End=============");
          }
      }
    };
    long delay = 60000L;
    long intevalPeriod = 
      ((Long)AutoLoginCheckTimeMap.getInstance()
      .getAutoLoginCheckTimeMap().get("time")).longValue() * 60000L;

    timerstartCheck.scheduleAtFixedRate(task, delay, intevalPeriod);
  }

  private void limitMacMapToDisk(ServletContextEvent servletContextEvent) {
    Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
    ObjectOutputStream os = null;
    try {
      os = new ObjectOutputStream(new FileOutputStream(
        servletContextEvent.getServletContext().getRealPath("/") + 
        "/autologinMap.dat"));
      os.writeObject(AutoLoginMacMap.getInstance().getAutoLoginMacMap());
      if (basConfig.getIsdebug().equals("1"))
        logger.info("autologinMapToDisk !!");
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

  private void limitMacMapFromDisk(ServletContextEvent servletContextEvent) {
    Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
    ObjectInputStream is = null;
    File parent = new File(servletContextEvent.getServletContext()
    		.getRealPath("/") + "/autologinMap.dat");

    label318: 
    try { if (parent.exists()) {
        is = new ObjectInputStream(new FileInputStream(
          servletContextEvent.getServletContext()
          .getRealPath("/") + "/autologinMap.dat"));
        Map autologinMap = (ConcurrentHashMap)is
          .readObject();
        AutoLoginMacMap.getInstance().setAutoLoginMacMap(autologinMap);
        if (basConfig.getIsdebug().equals("1")) {
          logger.info("autologinMapFromDisk !!");

          break label318;
        } } else if (basConfig.getIsdebug().equals("1")) {
        logger.info("autologinMap File Not Exists !!");
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

  private void AutoLoginCheckTimeMapToDisk(ServletContextEvent servletContextEvent)
  {
    Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
    ObjectOutputStream os = null;
    try {
      os = new ObjectOutputStream(new FileOutputStream(
        servletContextEvent.getServletContext().getRealPath("/") + 
        "/AutoLoginCheckTimeMap.dat"));
      os.writeObject(AutoLoginCheckTimeMap.getInstance()
        .getAutoLoginCheckTimeMap());
      if (basConfig.getIsdebug().equals("1"))
        logger.info("AutoLoginCheckTimeMapToDisk !!");
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

  private void AutoLoginCheckTimeMapFromDisk(ServletContextEvent servletContextEvent)
  {
    Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
    ObjectInputStream is = null;
    File parent = new File(servletContextEvent.getServletContext()
    		.getRealPath("/") + "/AutoLoginCheckTimeMap.dat");

    label340: 
    try { if (parent.exists()) {
        is = new ObjectInputStream(new FileInputStream(
          servletContextEvent.getServletContext()
          .getRealPath("/") + 
          "/AutoLoginCheckTimeMap.dat"));
        Map autoLoginCheckTimeMap = (ConcurrentHashMap)is
          .readObject();
        AutoLoginCheckTimeMap.getInstance().setAutoLoginCheckTimeMap(
          autoLoginCheckTimeMap);
        if (basConfig.getIsdebug().equals("1")) {
          logger.info("AutoLoginCheckTimeMapFromDisk !!");

          break label340;
        } } else { if (basConfig.getIsdebug().equals("1")) {
          logger.info("AutoLoginCheckTimeMap File Not Exists , Use default 10M !!");
        }
        AutoLoginCheckTimeMap.getInstance().getAutoLoginCheckTimeMap()
          .put("time", Long.valueOf(10L)); }
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
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.listener.AutoLoginService
 * JD-Core Version:    0.6.2
 */