package com.leeson.core.controller;

import AC.Server.ACServer;
import com.leeson.common.utils.MySQLUtils;
import com.leeson.common.utils.stringUtils;
import com.leeson.core.bean.Portalbas;
import com.leeson.core.utils.Kick;
import com.leeson.core.utils.UpdateOnlineRequest;
import com.leeson.portal.core.listener.ReportService;
import com.leeson.portal.core.model.Config;
import com.leeson.portal.core.model.OnlineMap;
import com.leeson.portal.core.service.ReportServer;
import com.leeson.radius.core.RadiusMain;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class PortalupdateController
{
  private static Config config = Config.getInstance();

  private static Logger logger = Logger.getLogger(PortalupdateController.class);
  private static OnlineMap onlineMap = OnlineMap.getInstance();

  @RequestMapping({"ersion.action"})
  public String in(ModelMap model, HttpServletRequest request)
  {
    String version = ReadVersionInfo(request);
    model.addAttribute("version", version);

    String versionLocal = ReadVersionLocalInfo(request);
    if (stringUtils.isNotBlank(versionLocal)) {
      String[] getV = UpdateOnlineRequest.send(versionLocal);
      if ((getV != null) && (getV.length == 2)) {
        String v = getV[0];
        String vInfo = getV[1];
        model.addAttribute("versionOnlne", vInfo);
        model.addAttribute("v", v);
      } else {
        model.addAttribute("versionOnlne", null);
      }
    } else {
      model.addAttribute("versionOnlne", null);
    }
    return "portalupdate/version";
  }

  @ResponseBody
  @RequestMapping({"pdate"})
  public Map<String, String> updateOnline(String v, ModelMap model, HttpServletRequest request)
  {
    Map map = new HashMap();
    String dir = request.getServletContext().getRealPath("/version");
    Date now = new Date();
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
    String nowString = format.format(now);
    File fileLocation = new File(dir);

    if (!fileLocation.exists()) {
      boolean isCreated = fileLocation.mkdir();
      if (!isCreated)
      {
        map.put("ret", "1");
        map.put("msg", "权限不足！！");
        return map;
      }
    }
    dir = request.getServletContext().getRealPath("/version");
    String localFile = dir + "/" + nowString + ".patch";

    kicks();
    ReportServer.closeServer();
    ACServer.closeServer();
    RadiusMain.stop(1);

    Boolean state = 
      Boolean.valueOf(UpdateOnlineRequest.updateService(v, localFile, request));
    if (!state.booleanValue()) {
      map.put("ret", "1");
      map.put("msg", "升级失败,请等待服务重启！！");
      return map;
    }
    map.put("ret", "0");
    map.put("msg", "升级成功,请等待服务重启！！");
    return map;
  }

  @RequestMapping({"ackupSQL.action"})
  public String backupSQL(ModelMap model, HttpServletRequest request, HttpServletResponse response)
  {
    try
    {
      String descDir = request.getServletContext().getRealPath("/");
      Properties props = new Properties();
      String propPath = descDir + 
        "WEB-INFroperties/jdbc.properties";

      FileInputStream in = new FileInputStream(propPath);
      props.load(in);
      String username = props.getProperty("jdbc1.username");
      String password = props.getProperty("jdbc1.password");

      in.close();
      String versionPath = descDir + "version/";

      File isH = new File(versionPath);
      if (!isH.exists()) {
        isH.mkdir();
      }

      SimpleDateFormat format = new SimpleDateFormat(
        "yyyy-MM-dd-HH-mm-ss");
      String nowString = format.format(new Date());
      String path = versionPath + nowString + ".dat";

      if (MySQLUtils.backup(username, password, "openportalserver", path)) {
        File file = new File(path);

        response.reset();
        response.setHeader("Content-Disposition", 
          "attachment; filename=\"" + file.getName() + "\"");
        response.addHeader("Content-Length", file.length()+"");
        response.setContentType("application/octet-stream;charset=UTF-8");

        InputStream fis = new BufferedInputStream(new FileInputStream(
          path));
        byte[] buffer = new byte[fis.available()];
        fis.read(buffer);
        fis.close();
        OutputStream outputStream = new BufferedOutputStream(
          response.getOutputStream());
        outputStream.write(buffer);
        outputStream.flush();
        outputStream.close();
      } else {
        model.addAttribute("msg", "备份数据库出错!");
        model.addAttribute("err", Integer.valueOf(0));
        return "portalupdate/version";
      }
    }
    catch (Exception e) {
      logger.error("backupSQL", e);
      model.addAttribute("msg", "备份数据库出错!");
      model.addAttribute("err", Integer.valueOf(0));
      return "portalupdate/version";
    }

    String version = ReadVersionInfo(request);
    model.addAttribute("version", version);
    return "portalupdate/version";
  }

  @RequestMapping(value={"estoreSQL.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String restoreSQL(@RequestParam(required=false) MultipartFile file, ModelMap model, HttpServletRequest request)
  {
    String ext = FilenameUtils.getExtension(file.getOriginalFilename());
    if (!ext.equals("dat")) {
      model.addAttribute("msg", "文件格式错误!");
      model.addAttribute("err", Integer.valueOf(0));
      return "portalupdate/version";
    }

    String dir = null;
    String nowString = null;
    String tempPath = "";
    try {
      InputStream in = file.getInputStream();
      dir = request.getServletContext().getRealPath("/version");
      File fileLocation = new File(dir);

      if (!fileLocation.exists()) {
        boolean isCreated = fileLocation.mkdir();
        if (!isCreated)
        {
          model.addAttribute("msg", "权限不足!");
          model.addAttribute("err", Integer.valueOf(0));
          return "portalupdate/version";
        }
      }
      dir = request.getServletContext().getRealPath("/version");

      Date now = new Date();
      SimpleDateFormat format = new SimpleDateFormat(
        "yyyy-MM-dd-HH-mm-ss");
      nowString = format.format(now);

      File uploadFile = new File(dir, nowString + ".dat");

      FileUtils.copyInputStreamToFile(in, uploadFile);
      in.close();
    } catch (Exception ex) {
      model.addAttribute("msg", "传送文件异常!");
      model.addAttribute("err", Integer.valueOf(0));
      File df = new File(dir);
      if (df.exists()) {
        ReportService.deleteAll(df);
      }
      return "portalupdate/version";
    }
    try
    {
      String descDir = request.getServletContext().getRealPath("/");
      String sys = System.getProperty("os.name");
      if (sys.startsWith("W")) {
        String unZipPath = descDir.replace("ROOT\\", "");
        tempPath = unZipPath.replace("webapps\\", "");
      } else if (sys.startsWith("L")) {
        String unZipPath = descDir.replace("ROOT/", "");
        tempPath = unZipPath.replace("webapps/", "");
      } else {
        model.addAttribute("msg", "获取OS信息失败!");
        model.addAttribute("err", Integer.valueOf(0));
        File df = new File(dir);
        if (df.exists()) {
          ReportService.deleteAll(df);
        }
        return "portalupdate/version";
      }

      tempPath = tempPath + "temp/";

      Properties props = new Properties();
      String propPath = descDir + 
        "WEB-INFroperties/jdbc.properties";
      FileInputStream in = new FileInputStream(propPath);
      props.load(in);
      String username = props.getProperty("jdbc1.username");
      String password = props.getProperty("jdbc1.password");
      in.close();

      String path = dir + "/" + nowString + ".dat";

      kicks();
      ReportServer.closeServer();
      ACServer.closeServer();
      RadiusMain.stop(1);

      if (!MySQLUtils.restore(username, password, "openportalserver", 
        path)) {
        model.addAttribute("msg", "数据库还原错误!");
        model.addAttribute("err", Integer.valueOf(0));
        File df = new File(dir);
        if (df.exists()) {
          ReportService.deleteAll(df);
        }
        return "portalupdate/version";
      }
    }
    catch (Exception e) {
      model.addAttribute("msg", "数据库还原错误!");
      model.addAttribute("err", Integer.valueOf(0));
      File df = new File(dir);
      if (df.exists()) {
        ReportService.deleteAll(df);
      }
      return "portalupdate/version";
    }

    File df = new File(dir);
    if (df.exists()) {
      ReportService.deleteAll(df);
    }
    File tempFile = new File(tempPath);
    if (tempFile.exists()) {
      ReportService.deleteAll(tempFile);
    }

    dir = request.getServletContext().getRealPath("/version");
    File fileLocation = new File(dir);

    if (!fileLocation.exists()) {
      boolean isCreated = fileLocation.mkdir();
      if (!isCreated)
      {
        model.addAttribute("msg", "权限不足!");
        model.addAttribute("err", Integer.valueOf(0));
        return "portalupdate/version";
      }
    }
    dir = request.getServletContext().getRealPath("/version");
    UpdateOnlineRequest.restartService(dir, request);

    String version = ReadVersionInfo(request);
    model.addAttribute("msg", "数据库还原成功,请等待服务重启！！");
    model.addAttribute("version", version);
    model.addAttribute("err", Integer.valueOf(1));
    return "portalupdate/version";
  }

  @RequestMapping({"lear.action"})
  public String clear(ModelMap model, HttpServletRequest request)
  {
    String rootPath = request.getServletContext().getRealPath("/");
    String sys = System.getProperty("os.name");
    if (sys.startsWith("W")) {
      rootPath = rootPath.replace("ROOT\\", "");
      rootPath = rootPath.replace("webapps\\", "");
    } else if (sys.startsWith("L")) {
      rootPath = rootPath.replace("ROOT/", "");
      rootPath = rootPath.replace("webapps/", "");
    } else {
      model.addAttribute("msg", "获取OS信息失败!");
      model.addAttribute("err", Integer.valueOf(0));
      return "portalupdate/version";
    }

    String fastworkPath = rootPath + "fastwork/";
    String logsPath = rootPath + "logs/";

    String versionPath = request.getServletContext()
      .getRealPath("/version");

    File file = new File(fastworkPath);
    if (file.exists()) {
      ReportService.deleteAll(file);
    }
    if (!file.exists()) {
      file.mkdir();
    }
    file = new File(logsPath);
    if (file.exists()) {
      ReportService.deleteAll(file);
    }
    if (!file.exists()) {
      file.mkdir();
    }

    file = new File(versionPath);
    if (file.exists()) {
      ReportService.deleteAll(file);
    }
    if (!file.exists()) {
      file.mkdir();
    }

    String version = ReadVersionInfo(request);
    model.addAttribute("msg", "缓存清除成功!  ");
    model.addAttribute("version", version);
    model.addAttribute("err", Integer.valueOf(1));
    return "portalupdate/version";
  }

  @RequestMapping(value={"pdate.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String update(@RequestParam(required=false) MultipartFile file, ModelMap model, HttpServletRequest request)
  {
    String ext = FilenameUtils.getExtension(file.getOriginalFilename());
    if (!ext.equals("patch")) {
      model.addAttribute("msg", "文件格式错误!");
      model.addAttribute("err", Integer.valueOf(0));
      return "portalupdate/version";
    }

    String dir = null;
    String nowString = null;
    try {
      InputStream in = file.getInputStream();
      dir = request.getServletContext().getRealPath("/version");
      File fileLocation = new File(dir);

      if (!fileLocation.exists()) {
        boolean isCreated = fileLocation.mkdir();
        if (!isCreated)
        {
          model.addAttribute("msg", "权限不足!");
          model.addAttribute("err", Integer.valueOf(0));
          return "portalupdate/version";
        }
      }
      dir = request.getServletContext().getRealPath("/version");

      Date now = new Date();
      SimpleDateFormat format = new SimpleDateFormat(
        "yyyy-MM-dd-HH-mm-ss");
      nowString = format.format(now);

      File uploadFile = new File(dir, nowString + ".patch");

      FileUtils.copyInputStreamToFile(in, uploadFile);
      in.close();
    } catch (Exception ex) {
      model.addAttribute("msg", "传送文件异常!");
      model.addAttribute("err", Integer.valueOf(0));
      File df = new File(dir);
      if (df.exists()) {
        ReportService.deleteAll(df);
      }
      return "portalupdate/version";
    }
    String localFile = dir + "/" + nowString + ".patch";

    kicks();
    ReportServer.closeServer();
    ACServer.closeServer();
    RadiusMain.stop(1);

    Boolean state = Boolean.valueOf(UpdateOnlineRequest.updateService(localFile, request));
    if (!state.booleanValue()) {
      model.addAttribute("msg", "升级失败,请等待服务重启！！");
      model.addAttribute("err", Integer.valueOf(0));
      return "portalupdate/version";
    }
    String version = ReadVersionInfo(request);
    model.addAttribute("msg", "升级成功,请等待服务重启！！");
    model.addAttribute("version", version);
    model.addAttribute("err", Integer.valueOf(1));
    return "portalupdate/version";
  }

  @RequestMapping({"estart.action"})
  public String restart(ModelMap model, HttpServletRequest request)
  {
    String dir = request.getServletContext().getRealPath("/version");
    File fileLocation = new File(dir);

    if (!fileLocation.exists()) {
      boolean isCreated = fileLocation.mkdir();
      if (!isCreated)
      {
        model.addAttribute("msg", "权限不足!");
        model.addAttribute("err", Integer.valueOf(0));
        return "portalupdate/version";
      }
    }
    dir = request.getServletContext().getRealPath("/version");

    kicks();
    ReportServer.closeServer();
    ACServer.closeServer();
    RadiusMain.stop(1);

    Boolean state = Boolean.valueOf(UpdateOnlineRequest.restartService(dir, request));

    String version = ReadVersionInfo(request);
    model.addAttribute("version", version);
    String versionLocal = ReadVersionLocalInfo(request);
    if (stringUtils.isNotBlank(versionLocal)) {
      String[] getV = UpdateOnlineRequest.send(versionLocal);
      if ((getV != null) && (getV.length == 2)) {
        String v = getV[0];
        String vInfo = getV[1];
        model.addAttribute("versionOnlne", vInfo);
        model.addAttribute("v", v);
      } else {
        model.addAttribute("versionOnlne", null);
      }
    } else {
      model.addAttribute("versionOnlne", null);
    }
    if (state.booleanValue())
      model.addAttribute("msg", "请等待服务重启！！");
    else {
      model.addAttribute("msg", "请求服务重启失败！！");
    }
    model.addAttribute("err", Integer.valueOf(1));
    return "portalupdate/version";
  }

  private void kicks() {
    HashMap OnlineUserMap = new HashMap();
    OnlineUserMap.putAll(onlineMap.getOnlineUserMap());
    Iterator iterator = OnlineUserMap.keySet().iterator();
    while (iterator.hasNext()) {
      Object o = iterator.next();
      String host = o.toString();
      Kick.kickUserUpdate(host);
    }
  }

  private String ReadVersionInfo(HttpServletRequest request)
  {
    Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
    String filePath = request.getServletContext().getRealPath("/") + 
      "/info.txt";
    String msg = "";
    try {
      String encoding = "utf-8";
      File file = new File(filePath);
      if ((file.isFile()) && (file.exists())) {
        InputStreamReader read = new InputStreamReader(
          new FileInputStream(file), encoding);
        BufferedReader bufferedReader = new BufferedReader(read);
        String lineTxt = null;

        while ((lineTxt = bufferedReader.readLine()) != null) {
          msg = msg + lineTxt;
        }
        read.close();
      }
      else if (basConfig.getIsdebug().equals("1")) {
        logger.info("版本信息文件info.txt不存在!");
      }
    }
    catch (Exception e) {
      if (basConfig.getIsdebug().equals("1")) {
        logger.info("读取版本信息文件info.txt出错!");
      }
    }
    return msg;
  }

  private String ReadVersionLocalInfo(HttpServletRequest request) {
    Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
    String filePath = request.getServletContext().getRealPath("/WEB-INF") + 
      "/v.version";
    String msg = "";
    try {
      String encoding = "utf-8";
      File file = new File(filePath);
      if ((file.isFile()) && (file.exists())) {
        InputStreamReader read = new InputStreamReader(
          new FileInputStream(file), encoding);
        BufferedReader bufferedReader = new BufferedReader(read);
        String lineTxt = null;

        while ((lineTxt = bufferedReader.readLine()) != null) {
          msg = msg + lineTxt;
        }
        read.close();
      }
      else if (basConfig.getIsdebug().equals("1")) {
        logger.info("版本信息文件v.version不存在!");
      }
    }
    catch (Exception e) {
      if (basConfig.getIsdebug().equals("1")) {
        logger.info("读取版本信息文件v.version出错!");
      }
    }
    return msg.trim();
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.PortalupdateController
 * JD-Core Version:    0.6.2
 */