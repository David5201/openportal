package com.leeson.core.controller;

import com.leeson.common.utils.stringUtils;
import com.leeson.portal.core.listener.ReportService;
import com.leeson.portal.core.model.CoreConfigMap;
import com.leeson.portal.core.model.WySlot15gasa;
import com.leeson.portal.core.model.Wz3ofg0r225avuerr;
import com.leeson.portal.core.model.isDo;
import com.leeson.portal.core.utils.AaHpl8Ha9bNPen9OLddV;
import com.leeson.portal.core.utils.BoemXwfltxQ41gbgpEPru9p7Tnp;
import com.leeson.portal.core.utils.fI8m9X5dVXZEBo6Js;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class PortallicController
{
  private static SimpleDateFormat fs = new SimpleDateFormat("yyyy-MM-dd");

  @RequestMapping({"icense.action"})
  public String license(ModelMap model, HttpServletRequest request) {
    int license = 0;
    String RTU4Qzk3RTc5NEI1RTU5NTg2MjYy = (String)Wz3ofg0r225avuerr.getInstance().getXr9hk0cvnsx().get("mec");
    if (stringUtils.isBlank(RTU4Qzk3RTc5NEI1RTU5NTg2MjYy)) {
      RTU4Qzk3RTc5NEI1RTU5NTg2MjYy = "";
    }
    String[] MxMzIyRDMzMzAy = (String[])WySlot15gasa.getInstance().getAmkbYQX3eQjuwtnxpbjYgQGZOr().get(RTU4Qzk3RTc5NEI1RTU5NTg2MjYy);
    if ((MxMzIyRDMzMzAy != null) && (MxMzIyRDMzMzAy.length > 5)) { String p = AaHpl8Ha9bNPen9OLddV.decode(MxMzIyRDMzMzAy[0]);
      String apCount = MxMzIyRDMzMzAy[1];
      String toDateString = MxMzIyRDMzMzAy[2];
      String basCount = MxMzIyRDMzMzAy[4];
      String macCount = MxMzIyRDMzMzAy[5];
      Date saveDate;
      try { saveDate = fs.parse(toDateString); }
      catch (ParseException e)
      {
        saveDate = new Date();
      }Date now = new Date();
      String nowString = fs.format(now);
      Date nowDate;
      try { nowDate = fs.parse(nowString); }
      catch (ParseException e)
      {
        nowDate = saveDate;
      }
      if (nowDate.getTime() < saveDate.getTime()) {
        model.addAttribute("to", p);
        model.addAttribute("ap", apCount);
        model.addAttribute("bas", basCount);
        model.addAttribute("mac", macCount);
        model.addAttribute("todate", toDateString);
        license = 1;
      } else {
        model.addAttribute("to", p);
        model.addAttribute("ap", apCount);
        model.addAttribute("bas", basCount);
        model.addAttribute("mac", macCount);
        model.addAttribute("todate", toDateString + " 已到期,请重新购买授权!!");
        license = 2;
      }
    }
    model.addAttribute("license", Integer.valueOf(license));
    return "portallic/license";
  }

  @RequestMapping({"ownload.action"})
  public String download(ModelMap model, HttpServletRequest request, HttpServletResponse response)
  {
    try
    {
      String cfgPath = request.getServletContext().getRealPath("/");
      Date now = new Date();
      SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
      String nowString = format.format(now);
      File dir = new File(cfgPath + "ExcelOut/");
      if (!dir.exists()) {
        dir.mkdirs();
      }
      String filePath = cfgPath + "ExcelOut/" + nowString + ".me";
      write(filePath, fI8m9X5dVXZEBo6Js.pV3Y5xivmveI277H6QS87V(), "utf-8");

      File file = new File(filePath);

      response.reset();
      response.setHeader("Content-Disposition", 
        "attachment; filename=\"" + file.getName() + "\"");
      response.addHeader("Content-Length", file.length()+"");
      response.setContentType("application/octet-stream;charset=UTF-8");

      InputStream fis = new BufferedInputStream(new FileInputStream(
        filePath));
      byte[] buffer = new byte[fis.available()];
      fis.read(buffer);
      fis.close();
      OutputStream outputStream = new BufferedOutputStream(
        response.getOutputStream());
      outputStream.write(buffer);
      outputStream.flush();
      outputStream.close();
    }
    catch (IOException ioe) {
      model.addAttribute("msg", "发生错误!");
      model.addAttribute("err", Integer.valueOf(1));

      int license = 0;
      String RTU4Qzk3RTc5NEI1RTU5NTg2MjYy = (String)Wz3ofg0r225avuerr.getInstance().getXr9hk0cvnsx().get("mec");
      if (stringUtils.isBlank(RTU4Qzk3RTc5NEI1RTU5NTg2MjYy)) {
        RTU4Qzk3RTc5NEI1RTU5NTg2MjYy = "";
      }
      String[] MxMzIyRDMzMzAy = (String[])WySlot15gasa.getInstance().getAmkbYQX3eQjuwtnxpbjYgQGZOr().get(RTU4Qzk3RTc5NEI1RTU5NTg2MjYy);
      if ((MxMzIyRDMzMzAy != null) && (MxMzIyRDMzMzAy.length > 5)) { String p = AaHpl8Ha9bNPen9OLddV.decode(MxMzIyRDMzMzAy[0]);
        String apCount = MxMzIyRDMzMzAy[1];
        String toDateString = MxMzIyRDMzMzAy[2];
        String basCount = MxMzIyRDMzMzAy[4];
        String macCount = MxMzIyRDMzMzAy[5];
        Date saveDate;
        try { saveDate = fs.parse(toDateString); }
        catch (ParseException e)
        {
          saveDate = new Date();
        }Date now = new Date();
        String nowString = fs.format(now);
        Date nowDate;
        try { nowDate = fs.parse(nowString); }
        catch (ParseException e)
        {
          nowDate = saveDate;
        }
        if (nowDate.getTime() < saveDate.getTime()) {
          model.addAttribute("to", p);
          model.addAttribute("ap", apCount);
          model.addAttribute("bas", basCount);
          model.addAttribute("mac", macCount);
          model.addAttribute("todate", toDateString);
          license = 1;
        } else {
          model.addAttribute("to", p);
          model.addAttribute("ap", apCount);
          model.addAttribute("bas", basCount);
          model.addAttribute("mac", macCount);
          model.addAttribute("todate", toDateString + " 已到期,请重新购买授权!!");
          license = 2;
        }
      }
      model.addAttribute("license", Integer.valueOf(license));
      return "portallic/license";
    }
    model.addAttribute("err", Integer.valueOf(0));

    int license = 0;
    String RTU4Qzk3RTc5NEI1RTU5NTg2MjYy = (String)Wz3ofg0r225avuerr.getInstance().getXr9hk0cvnsx().get("mec");
    if (stringUtils.isBlank(RTU4Qzk3RTc5NEI1RTU5NTg2MjYy)) {
      RTU4Qzk3RTc5NEI1RTU5NTg2MjYy = "";
    }
    String[] MxMzIyRDMzMzAy = (String[])WySlot15gasa.getInstance().getAmkbYQX3eQjuwtnxpbjYgQGZOr().get(RTU4Qzk3RTc5NEI1RTU5NTg2MjYy);
    if ((MxMzIyRDMzMzAy != null) && (MxMzIyRDMzMzAy.length > 5)) { String p = AaHpl8Ha9bNPen9OLddV.decode(MxMzIyRDMzMzAy[0]);
      String apCount = MxMzIyRDMzMzAy[1];
      String toDateString = MxMzIyRDMzMzAy[2];
      String basCount = MxMzIyRDMzMzAy[4];
      String macCount = MxMzIyRDMzMzAy[5];
      Date saveDate;
      try { saveDate = fs.parse(toDateString); }
      catch (ParseException e)
      {
        saveDate = new Date();
      }Date now = new Date();
      String nowString = fs.format(now);
      Date nowDate;
      try { nowDate = fs.parse(nowString); }
      catch (ParseException e)
      {
        nowDate = saveDate;
      }
      if (nowDate.getTime() < saveDate.getTime()) {
        model.addAttribute("to", p);
        model.addAttribute("ap", apCount);
        model.addAttribute("bas", basCount);
        model.addAttribute("mac", macCount);
        model.addAttribute("todate", toDateString);
        license = 1;
      } else {
        model.addAttribute("to", p);
        model.addAttribute("ap", apCount);
        model.addAttribute("bas", basCount);
        model.addAttribute("mac", macCount);
        model.addAttribute("todate", toDateString + " 已到期,请重新购买授权!!");
        license = 2;
      }
    }
    model.addAttribute("license", Integer.valueOf(license));
    return "portallic/license";
  }

  @RequestMapping(value={"pload.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String upload(@RequestParam(required=false) MultipartFile file, ModelMap model, HttpServletRequest request)
  {
    String ext = FilenameUtils.getExtension(file.getOriginalFilename());
    if (!ext.equals("lic")) {
      model.addAttribute("msg", "文件错误!");
      model.addAttribute("err", Integer.valueOf(1));

      int license = 0;
      String RTU4Qzk3RTc5NEI1RTU5NTg2MjYy = (String)Wz3ofg0r225avuerr.getInstance().getXr9hk0cvnsx().get("mec");
      if (stringUtils.isBlank(RTU4Qzk3RTc5NEI1RTU5NTg2MjYy)) {
        RTU4Qzk3RTc5NEI1RTU5NTg2MjYy = "";
      }
      String[] MxMzIyRDMzMzAy = (String[])WySlot15gasa.getInstance().getAmkbYQX3eQjuwtnxpbjYgQGZOr().get(RTU4Qzk3RTc5NEI1RTU5NTg2MjYy);
      if ((MxMzIyRDMzMzAy != null) && (MxMzIyRDMzMzAy.length > 5)) { String p = AaHpl8Ha9bNPen9OLddV.decode(MxMzIyRDMzMzAy[0]);
        String apCount = MxMzIyRDMzMzAy[1];
        String toDateString = MxMzIyRDMzMzAy[2];
        String basCount = MxMzIyRDMzMzAy[4];
        String macCount = MxMzIyRDMzMzAy[5];
        Date saveDate;
        try { saveDate = fs.parse(toDateString); }
        catch (ParseException e)
        {
          saveDate = new Date();
        }Date now = new Date();
        String nowString = fs.format(now);
        Date nowDate;
        try { nowDate = fs.parse(nowString); }
        catch (ParseException e)
        {
          nowDate = saveDate;
        }
        if (nowDate.getTime() < saveDate.getTime()) {
          model.addAttribute("to", p);
          model.addAttribute("ap", apCount);
          model.addAttribute("bas", basCount);
          model.addAttribute("mac", macCount);
          model.addAttribute("todate", toDateString);
          license = 1;
        } else {
          model.addAttribute("to", p);
          model.addAttribute("ap", apCount);
          model.addAttribute("bas", basCount);
          model.addAttribute("mac", macCount);
          model.addAttribute("todate", toDateString + " 已到期,请重新购买授权!!");
          license = 2;
        }
      }
      model.addAttribute("license", Integer.valueOf(license));
      return "portallic/license";
    }

    String sourceFile = "license.lic";
    String dir = request.getServletContext().getRealPath("/");
    String filePath = dir + sourceFile;
    try
    {
      InputStream in = file.getInputStream();
      File uploadFile = new File(dir, sourceFile);
      FileUtils.copyInputStreamToFile(in, uploadFile);
      in.close();
    } catch (Exception ex) {
      model.addAttribute("msg", "上传出错!");
      model.addAttribute("err", Integer.valueOf(1));
      File df = new File(filePath);
      if (df.exists()) {
        ReportService.deleteAll(df);
      }

      int license = 0;
      String RTU4Qzk3RTc5NEI1RTU5NTg2MjYy = (String)Wz3ofg0r225avuerr.getInstance().getXr9hk0cvnsx().get("mec");
      if (stringUtils.isBlank(RTU4Qzk3RTc5NEI1RTU5NTg2MjYy)) {
        RTU4Qzk3RTc5NEI1RTU5NTg2MjYy = "";
      }
      String[] MxMzIyRDMzMzAy = (String[])WySlot15gasa.getInstance().getAmkbYQX3eQjuwtnxpbjYgQGZOr().get(RTU4Qzk3RTc5NEI1RTU5NTg2MjYy);
      if ((MxMzIyRDMzMzAy != null) && (MxMzIyRDMzMzAy.length > 5)) { String p = AaHpl8Ha9bNPen9OLddV.decode(MxMzIyRDMzMzAy[0]);
        String apCount = MxMzIyRDMzMzAy[1];
        String toDateString = MxMzIyRDMzMzAy[2];
        String basCount = MxMzIyRDMzMzAy[4];
        String macCount = MxMzIyRDMzMzAy[5];
        Date saveDate;
        try { saveDate = fs.parse(toDateString); }
        catch (ParseException e)
        {
          saveDate = new Date();
        }Date now = new Date();
        String nowString = fs.format(now);
        Date nowDate;
        try { nowDate = fs.parse(nowString); }
        catch (ParseException e)
        {
          nowDate = saveDate;
        }
        if (nowDate.getTime() < saveDate.getTime()) {
          model.addAttribute("to", p);
          model.addAttribute("ap", apCount);
          model.addAttribute("bas", basCount);
          model.addAttribute("mac", macCount);
          model.addAttribute("todate", toDateString);
          license = 1;
        } else {
          model.addAttribute("to", p);
          model.addAttribute("ap", apCount);
          model.addAttribute("bas", basCount);
          model.addAttribute("mac", macCount);
          model.addAttribute("todate", toDateString + " 已到期,请重新购买授权!!");
          license = 2;
        }
      }
      model.addAttribute("license", Integer.valueOf(license));
      return "portallic/license";
    }

    model.addAttribute("msg", "上传成功!");
    model.addAttribute("err", Integer.valueOf(0));

    String licensePath = request.getServletContext().getRealPath("/") + "/license.lic";
    Read(licensePath);

    godo();

    int license = 0;
    String RTU4Qzk3RTc5NEI1RTU5NTg2MjYy = (String)Wz3ofg0r225avuerr.getInstance().getXr9hk0cvnsx().get("mec");
    if (stringUtils.isBlank(RTU4Qzk3RTc5NEI1RTU5NTg2MjYy)) {
      RTU4Qzk3RTc5NEI1RTU5NTg2MjYy = "";
    }
    String[] MxMzIyRDMzMzAy = (String[])WySlot15gasa.getInstance().getAmkbYQX3eQjuwtnxpbjYgQGZOr().get(RTU4Qzk3RTc5NEI1RTU5NTg2MjYy);
    if ((MxMzIyRDMzMzAy != null) && (MxMzIyRDMzMzAy.length > 5)) { String p = AaHpl8Ha9bNPen9OLddV.decode(MxMzIyRDMzMzAy[0]);
      String apCount = MxMzIyRDMzMzAy[1];
      String toDateString = MxMzIyRDMzMzAy[2];
      String basCount = MxMzIyRDMzMzAy[4];
      String macCount = MxMzIyRDMzMzAy[5];
      Date saveDate;
      try { saveDate = fs.parse(toDateString); }
      catch (ParseException e)
      {
        saveDate = new Date();
      }Date now = new Date();
      String nowString = fs.format(now);
      Date nowDate;
      try { nowDate = fs.parse(nowString); }
      catch (ParseException e)
      {
        nowDate = saveDate;
      }
      if (nowDate.getTime() < saveDate.getTime()) {
        model.addAttribute("to", p);
        model.addAttribute("ap", apCount);
        model.addAttribute("bas", basCount);
        model.addAttribute("mac", macCount);
        model.addAttribute("todate", toDateString);
        license = 1;
      } else {
        model.addAttribute("to", p);
        model.addAttribute("ap", apCount);
        model.addAttribute("bas", basCount);
        model.addAttribute("mac", macCount);
        model.addAttribute("todate", toDateString + " 已到期,请重新购买授权!!");
        license = 2;
      }
    }
    model.addAttribute("license", Integer.valueOf(license));
    return "portallic/license";
  }

  private static void write(String path, String content, String charset)
    throws IOException
  {
    FileOutputStream fos = new FileOutputStream(path);
    OutputStreamWriter writer = null;
    try {
      if (charset != null)
        writer = new OutputStreamWriter(fos, charset);
      else {
        writer = new OutputStreamWriter(fos);
      }
      writer.append(content);
    } finally {
      if (writer != null) {
        writer.flush();
        writer.close();
      }
    }
  }

  private static void Read(String filePath) {
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
    catch (Exception localException) {
    }
  }

  private static void godo() {
    Date nowDate = new Date();
    String RTU4Qzk3RTc5NEI1RTU5NTg2MjYy = (String)Wz3ofg0r225avuerr.getInstance().getXr9hk0cvnsx().get("mec");
    if (stringUtils.isBlank(RTU4Qzk3RTc5NEI1RTU5NTg2MjYy)) {
      RTU4Qzk3RTc5NEI1RTU5NTg2MjYy = "";
    }
    String[] MxMzIyRDMzMzAy = (String[])WySlot15gasa.getInstance().getAmkbYQX3eQjuwtnxpbjYgQGZOr().get(RTU4Qzk3RTc5NEI1RTU5NTg2MjYy);
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
        String[] core = (String[])CoreConfigMap.getInstance().getCoreConfigMap().get("core");
        core[1] = MxMzIyRDMzMzAy[5];
        CoreConfigMap.getInstance().getCoreConfigMap().put("core", core);
      }
    }
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.PortallicController
 * JD-Core Version:    0.6.2
 */