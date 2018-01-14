package com.leeson.portal.core.listener;

import com.leeson.common.utils.stringUtils;
import com.leeson.core.bean.Portalbas;
import com.leeson.core.bean.Portalconfig;
import com.leeson.core.bean.Zsqhdapi;
import com.leeson.core.query.PortalbasQuery;
import com.leeson.core.service.ConfigService;
import com.leeson.core.service.PortalbasService;
import com.leeson.core.service.PortalconfigService;
import com.leeson.core.service.ZsqhdapiService;
import com.leeson.portal.core.model.AccountAPIMap;
import com.leeson.portal.core.model.CoreConfigMap;
import com.leeson.portal.core.model.RadiusAPIMap;
import com.leeson.portal.core.model.SangforAPIMap;
import com.leeson.portal.core.model.WySlot15gasa;
import com.leeson.portal.core.model.Wz3ofg0r225avuerr;
import com.leeson.portal.core.model.ipMap;
import com.leeson.portal.core.model.isDo;
import com.leeson.portal.core.service.ReportServer;
import com.leeson.portal.core.utils.BoemXwfltxQ41gbgpEPru9p7Tnp;
import com.leeson.portal.core.utils.SpringContextHelper;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.log4j.Logger;

public class ReportService
  implements ServletContextListener
{
  private static com.leeson.portal.core.model.Config config = com.leeson.portal.core.model.Config.getInstance();

  private static Logger log = Logger.getLogger(ReportService.class);

  private static Timer timerUnLockIPService = new Timer();

  public void contextDestroyed(ServletContextEvent servletContextEvent)
  {
    timerUnLockIPService.cancel();
    if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1"))
      log.info("PortalServer Stop!!");
  }

  public void contextInitialized(ServletContextEvent servletContextEvent)
  {
    GetConfigFromDisk(servletContextEvent);
    SangforAPIMapFromDisk(servletContextEvent);
    AccountAPIMapFromDisk(servletContextEvent);
    RadiusAPIMapFromDisk(servletContextEvent);

    Read(servletContextEvent);

    godo();

    InitConfig4DB(servletContextEvent);

    new Thread()
    {
      public void run()
      {
        try {
          ReportServer.openServer();
        }
        catch (Exception e) {
          ReportService.log.error("PortalServer Start Error ！");
          ReportService.log.error("==============ERROR Start=============");
          ReportService.log.error(e);
          ReportService.log.error("ERROR INFO ", e);
          ReportService.log.error("==============ERROR End=============");
        }
      }
    }
    .start();

    new Thread()
    {
      public void run()
      {
        try {
          ReportService.this.UnLockIPService();
        }
        catch (Exception e) {
          ReportService.log.error("UnLock IP-Lock Service Start Error ！");
          ReportService.log.error("==============ERROR Start=============");
          ReportService.log.error(e);
          ReportService.log.error("ERROR INFO ", e);
          ReportService.log.error("==============ERROR End=============");
        }
      }
    }
    .start();
    try
    {
      String path_out = servletContextEvent.getServletContext()
    		 .getRealPath("/") + "ExcelOut/";
      final File file_out = new File(path_out);
      System.out.println("PortalServer Start Format Temp Files!!");
      if (file_out.exists())
      {
        new Thread()
        {
          public void run()
          {
            try {
              ReportService.deleteAll(file_out);
              file_out.mkdir();
            }
            catch (Exception e) {
              ReportService.log.error("==============ERROR Start=============");
              ReportService.log.error(e);
              ReportService.log.error("ERROR INFO ", e);
              ReportService.log.error("==============ERROR End=============");
            }
          }
        }
        .start();
      }
      else
        file_out.mkdir();
    }
    catch (Exception e) {
      log.error("==============ERROR Start=============");
      log.error(e);
      log.error("ERROR INFO ", e);
      log.error("==============ERROR End=============");
    }
    try
    {
      String path_v = servletContextEvent.getServletContext()
    		  .getRealPath("/") + "version/";
      final File file_v = new File(path_v);
      System.out.println("PortalServer Start Format Version Files!!");
      if (file_v.exists())
      {
        new Thread()
        {
          public void run()
          {
            try {
              ReportService.deleteAll(file_v);
              file_v.mkdir();
            }
            catch (Exception e) {
              ReportService.log.error("==============ERROR Start=============");
              ReportService.log.error(e);
              ReportService.log.error("ERROR INFO ", e);
              ReportService.log.error("==============ERROR End=============");
            }
          }
        }
        .start();
      }
      else
        file_v.mkdir();
    }
    catch (Exception e) {
      log.error("==============ERROR Start=============");
      log.error(e);
      log.error("ERROR INFO ", e);
      log.error("==============ERROR End=============");
    }
  }

  private void UnLockIPService()
  {
    TimerTask taskUnLockIP = new TimerTask()
    {
      public void run() {
        Portalbas basConfig = (Portalbas)ReportService.config.getConfigMap().get("");
        if (basConfig.getIsdebug().equals("1")) {
          ReportService.log.info("Start UnLock IP-Lock List Service !!");
        }
        ipMap.getInstance().getIpmap().clear();
      }
    };
    long delay = 20000L;
    long intevalPeriod = 30000L;

    timerUnLockIPService.scheduleAtFixedRate(taskUnLockIP, delay, intevalPeriod);
  }

  private void Read(ServletContextEvent servletContextEvent) {
    String filePath = servletContextEvent.getServletContext()
      .getRealPath("/") + 
      "/license.lic";
    try {
      String encoding = "utf-8";
      File file = new File(filePath);
      if ((file.isFile()) && (file.exists())) {
        InputStreamReader read = new InputStreamReader(
          new FileInputStream(file), encoding);
        BufferedReader bufferedReader = new BufferedReader(read);
        String lineTxt = null;
        String msg = "";
        while ((lineTxt = bufferedReader.readLine()) != null) {
          msg = msg + lineTxt.trim();
        }
        read.close();
        if (stringUtils.isNotBlank(msg))
          BoemXwfltxQ41gbgpEPru9p7Tnp.XZluueWcHZVOWoHedyv(msg);
      }
    }
    catch (Exception e) {
      log.error("==============ERROR Start=============");
      log.error(e);
      log.error("ERROR INFO ", e);
      log.error("==============ERROR End=============");
    }
  }

  private void InitConfig4DB(ServletContextEvent servletContextEvent)
  {
    SimpleDateFormat fs = new SimpleDateFormat("yyyy-MM-dd");
    int basCount = 0;
    Date nowDate = new Date();
    String nowString = fs.format(nowDate);
    try {
      nowDate = fs.parse(nowString);
    } catch (ParseException e) {
      nowDate = new Date();
    }
    String RTU4Qzk3RTc5NEI1RTU5NTg2MjYy = 
      (String)Wz3ofg0r225avuerr.getInstance()
      .getXr9hk0cvnsx().get("mec");
    if (stringUtils.isBlank(RTU4Qzk3RTc5NEI1RTU5NTg2MjYy)) {
      RTU4Qzk3RTc5NEI1RTU5NTg2MjYy = "";
    }
    String[] MxMzIyRDMzMzAy = 
      (String[])WySlot15gasa.getInstance()
      .getAmkbYQX3eQjuwtnxpbjYgQGZOr()
      .get(RTU4Qzk3RTc5NEI1RTU5NTg2MjYy);
    Boolean notCan = Boolean.valueOf(false);
    if ((MxMzIyRDMzMzAy != null) && (MxMzIyRDMzMzAy.length > 5)) { String toDateString = MxMzIyRDMzMzAy[2];
      Date saveDate;
      try {
        saveDate = fs.parse(toDateString);
      }
      catch (ParseException e)
      {
        saveDate = nowDate;
      }
      if (nowDate.getTime() < saveDate.getTime()) {
        basCount = Integer.valueOf(MxMzIyRDMzMzAy[4]).intValue();
      }
      else
        notCan = Boolean.valueOf(true);
    }
    else
    {
      notCan = Boolean.valueOf(true);
    }

    Map configMap = config.getConfigMap();

    PortalconfigService configService = (PortalconfigService)
      SpringContextHelper.getBean("portalconfigServiceImpl");
    ConfigService configAllService = (ConfigService)
      SpringContextHelper.getBean("configServiceImpl");
    String isdebug = String.valueOf(configAllService.getConfigByKey(Long.valueOf(1L))
      .getIsDebug());

    Portalconfig config = configService.getPortalconfigByKey(Long.valueOf(1L));
    Portalbas cfg = new Portalbas();
    cfg.setBasIp(config.getBasIp());
    cfg.setBasPort(config.getBasPort());
    cfg.setSharedSecret(config.getSharedSecret());
    cfg.setAuthType(config.getAuthType());
    cfg.setTimeoutSec(config.getTimeoutSec());
    cfg.setPortalVer(config.getPortalVer());
    cfg.setBas(config.getBas());
    cfg.setIsOut(config.getIsOut());
    cfg.setIsPortalCheck(config.getIsPortalCheck());
    cfg.setAuthInterface(config.getAuthInterface());
    cfg.setBasUser(config.getBasUser());
    cfg.setBasPwd(config.getBasPwd());
    cfg.setWeb(config.getWeb());
    cfg.setBasname(config.getBasname());
    cfg.setIsComputer(config.getIsComputer());
    cfg.setIsdebug(isdebug);
    cfg.setLateAuth(config.getLateAuth());
    cfg.setLateAuthTime(config.getLateAuthTime());
    cfg.setIsNtf(config.getIsNtf());
    configMap.put("", cfg);

    configMap.put(config.getBasIp(), cfg);

    if (cfg.getIsdebug().equals("1")) {
      log.info("Read Config Setting : " + config);
    }

    int count = 0;
    PortalbasService basService = (PortalbasService)
      SpringContextHelper.getBean("portalbasServiceImpl");
    List<Portalbas> bs = basService.getPortalbasList(new PortalbasQuery());
    for (Portalbas bas : bs) {
      count++;
      if (notCan.booleanValue()) {
        if (count <= 2) {
          bas.setIsdebug(isdebug);
          configMap.put(bas.getBasIp(), bas);
        }
      }
      else if (count <= basCount) {
        bas.setIsdebug(isdebug);
        configMap.put(bas.getBasIp(), bas);
      }
    }
  }

  public static void deleteAll(File file)
  {
    if ((file.isFile()) || (file.list().length == 0)) {
      file.delete();
    } else {
      File[] files = file.listFiles();
      for (int i = 0; i < files.length; i++) {
        deleteAll(files[i]);
        files[i].delete();
      }

      if (file.exists())
        file.delete();
    }
  }

  private void GetConfigFromDisk(ServletContextEvent servletContextEvent)
  {
    String limit = "100";
    String url = "http://www.openportal.com.cn";
    FileInputStream in = null;
    try {
      String path = servletContextEvent.getServletContext()
        .getRealPath("/") + 
        "ib/core.properties";
      Properties props = new Properties();
      in = new FileInputStream(path);
      props.load(in);
      limit = props.getProperty("limit");
      url = props.getProperty("url");
      in.close();
    } catch (Exception e) {
      limit = "100";
      url = "http://www.openportal.com.cn";
      try
      {
        if (in != null)
          in.close();
      }
      catch (IOException localIOException)
      {
      }
    }
    finally
    {
      try
      {
        if (in != null)
          in.close();
      }
      catch (IOException localIOException1) {
      }
    }
    int limitInt = Integer.valueOf(limit).intValue();
    if (limitInt > 100) {
      limit = "100";
    }
    String[] core = new String[2];
    core[0] = url;
    core[1] = limit;
    CoreConfigMap.getInstance().getCoreConfigMap().put("core", core);
  }

  private void SangforAPIMapFromDisk(ServletContextEvent servletContextEvent) {
    String type = "";
    String url = "";
    String port = "";
    FileInputStream in = null;
    try {
      String path = servletContextEvent.getServletContext()
        .getRealPath("/") + 
        "/sangfor.properties";
      Properties props = new Properties();
      in = new FileInputStream(path);
      props.load(in);
      type = props.getProperty("type");
      url = props.getProperty("url");
      port = props.getProperty("port");
      in.close();
    }
    catch (Exception localException) {
      try {
        if (in != null)
          in.close();
      }
      catch (IOException localIOException)
      {
      }
    }
    finally
    {
      try
      {
        if (in != null)
          in.close();
      }
      catch (IOException localIOException1) {
      }
    }
    log.info("Sangfor API Read for Files , Url=" + url);
    SangforAPIMap.getInstance().getSangforAPIMap().put("type", type);
    SangforAPIMap.getInstance().getSangforAPIMap().put("url", url);
    SangforAPIMap.getInstance().getSangforAPIMap().put("port", port);
  }

  private void RadiusAPIMapFromDisk(ServletContextEvent servletContextEvent) {
    String state = "";
    String url = "";
    FileInputStream in = null;
    try {
      String path = servletContextEvent.getServletContext()
        .getRealPath("/") + 
        "/radiusAPI.properties";
      Properties props = new Properties();
      in = new FileInputStream(path);
      props.load(in);
      state = props.getProperty("state");
      url = props.getProperty("url");
      in.close();
    }
    catch (Exception localException) {
      try {
        if (in != null)
          in.close();
      }
      catch (IOException localIOException)
      {
      }
    }
    finally
    {
      try
      {
        if (in != null)
          in.close();
      }
      catch (IOException localIOException1) {
      }
    }
    log.info("Radius API Read for Files , Url=" + url + " , State=" + state);
    RadiusAPIMap.getInstance().getRadiusAPIMap().put("state", state);
    RadiusAPIMap.getInstance().getRadiusAPIMap().put("url", url);
  }

  private void AccountAPIMapFromDisk(ServletContextEvent servletContextEvent) {
    String url = "";
    String state = "";
    String publicurl = "";
    String publicstate = "";
    String autourl = "";
    String autostate = "";

    ZsqhdapiService zsqhdapiService = (ZsqhdapiService)
      SpringContextHelper.getBean("zsqhdapiServiceImpl");

    Zsqhdapi api = zsqhdapiService.getZsqhdapiByKey(Long.valueOf(1L));
    if (api != null) {
      url = api.getUrl();
      state = String.valueOf(api.getState());
      publicurl = api.getPublicurl();
      publicstate = String.valueOf(api.getPublicstate());
      autourl = api.getAutourl();
      autostate = String.valueOf(api.getAutostate());
    }

    log.info("account API Read for DB , Url=" + url + " State=" + state + 
      " publicurl=" + publicurl + " publicstate=" + publicstate + " autostate=" + autostate + " autourl=" + autourl);
    AccountAPIMap.getInstance().getAccountAPIMap().put("url", url);
    AccountAPIMap.getInstance().getAccountAPIMap().put("state", state);
    AccountAPIMap.getInstance().getAccountAPIMap().put("publicurl", publicurl);
    AccountAPIMap.getInstance().getAccountAPIMap().put("publicstate", publicstate);
    AccountAPIMap.getInstance().getAccountAPIMap().put("autourl", autourl);
    AccountAPIMap.getInstance().getAccountAPIMap().put("autostate", autostate);
  }

  private void AccountAPIMapFromDisk1(ServletContextEvent servletContextEvent) {
    String url = "";
    String state = "";
    String publicurl = "";
    String publicstate = "";
    String autourl = "";
    String autostate = "";
    FileInputStream in = null;
    try {
      String path = servletContextEvent.getServletContext()
        .getRealPath("/") + 
        "/accountAPI.properties";
      Properties props = new Properties();
      in = new FileInputStream(path);
      props.load(in);
      url = props.getProperty("url");
      state = props.getProperty("state");
      publicurl = props.getProperty("publicurl");
      publicstate = props.getProperty("publicstate");
      autourl = props.getProperty("autourl");
      autostate = props.getProperty("autostate");
      in.close();
    }
    catch (Exception localException) {
      try {
        if (in != null)
          in.close();
      }
      catch (IOException localIOException)
      {
      }
    }
    finally
    {
      try
      {
        if (in != null)
          in.close();
      }
      catch (IOException localIOException1) {
      }
    }
    log.info("account API Read for Files , Url=" + url + " State=" + state + 
      " publicurl=" + publicurl + " publicstate=" + publicstate);
    AccountAPIMap.getInstance().getAccountAPIMap().put("url", url);
    AccountAPIMap.getInstance().getAccountAPIMap().put("state", state);
    AccountAPIMap.getInstance().getAccountAPIMap().put("publicurl", publicurl);
    AccountAPIMap.getInstance().getAccountAPIMap().put("publicstate", publicstate);
    AccountAPIMap.getInstance().getAccountAPIMap().put("autourl", autourl);
    AccountAPIMap.getInstance().getAccountAPIMap().put("autostate", autostate);
  }

  private static void godo() {
    SimpleDateFormat fs = new SimpleDateFormat("yyyy-MM-dd");
    Date nowDate = new Date();
    String RTU4Qzk3RTc5NEI1RTU5NTg2MjYy = 
      (String)Wz3ofg0r225avuerr.getInstance()
      .getXr9hk0cvnsx().get("mec");
    if (stringUtils.isBlank(RTU4Qzk3RTc5NEI1RTU5NTg2MjYy)) {
      RTU4Qzk3RTc5NEI1RTU5NTg2MjYy = "";
    }
    String[] MxMzIyRDMzMzAy = 
      (String[])WySlot15gasa.getInstance()
      .getAmkbYQX3eQjuwtnxpbjYgQGZOr()
      .get(RTU4Qzk3RTc5NEI1RTU5NTg2MjYy);
    if ((MxMzIyRDMzMzAy != null) && (MxMzIyRDMzMzAy.length > 5)) { String toDateString = MxMzIyRDMzMzAy[2];
      Date saveDate;
      try {
        saveDate = fs.parse(toDateString);
      }
      catch (ParseException err)
      {
        saveDate = nowDate;
      }
      if (nowDate.getTime() < saveDate.getTime()) {
        isDo.getInstance().setId(Long.valueOf(saveDate.getTime()));
        String[] core = 
          (String[])CoreConfigMap.getInstance().getCoreConfigMap()
          .get("core");
        core[1] = MxMzIyRDMzMzAy[5];
        CoreConfigMap.getInstance().getCoreConfigMap()
          .put("core", core);
      }
    }
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.listener.ReportService
 * JD-Core Version:    0.6.2
 */