package com.leeson.core.controller;

import com.leeson.common.utils.DiyUtils;
import com.leeson.portal.core.listener.ReportService;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
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
public class PortaldiyController
{
  @RequestMapping({"iy.action"})
  public String in(ModelMap model, HttpServletRequest request)
  {
    model.addAttribute("err", Integer.valueOf(2));
    return "portaldiy/diy";
  }

  @RequestMapping({"ownload.action"})
  public String backupSQL(ModelMap model, HttpServletRequest request, HttpServletResponse response)
  {
    try
    {
      String descDir = request.getServletContext().getRealPath("/");
      String dir = descDir + "/version";
      File fileLocation = new File(dir);

      if (!fileLocation.exists()) {
        boolean isCreated = fileLocation.mkdir();
        if (!isCreated) {
          model.addAttribute("msg", "发生错误!");
          model.addAttribute("err", Integer.valueOf(1));
          return "portaldiy/diy";
        }
      }

      String path = DiyUtils.Zip(descDir);
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
    }
    catch (IOException e) {
      model.addAttribute("msg", "发生错误!");
      model.addAttribute("err", Integer.valueOf(1));
      return "portaldiy/diy";
    }
    model.addAttribute("err", Integer.valueOf(0));
    return "portaldiy/diy";
  }

  @RequestMapping(value={"pdate.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String restoreSQL(@RequestParam(required=false) MultipartFile file, ModelMap model, HttpServletRequest request)
  {
    String ext = FilenameUtils.getExtension(file.getOriginalFilename());
    if (!ext.equals("zip")) {
      model.addAttribute("msg", "文件错误!");
      model.addAttribute("err", Integer.valueOf(1));
      return "portaldiy/diy";
    }

    Date now = new Date();
    SimpleDateFormat format = new SimpleDateFormat(
      "yyyy-MM-dd-HH-mm-ss");
    String nowString = format.format(now);
    String sourceFile = "web-" + nowString + ".zip";
    String dir = null;
    try
    {
      InputStream in = file.getInputStream();
      dir = request.getServletContext().getRealPath("/version");
      File fileLocation = new File(dir);

      if (!fileLocation.exists()) {
        boolean isCreated = fileLocation.mkdir();
        if (!isCreated)
        {
          model.addAttribute("msg", "权限不足!");
          model.addAttribute("err", Integer.valueOf(1));
          return "portaldiy/diy";
        }
      }

      File uploadFile = new File(dir, sourceFile);

      FileUtils.copyInputStreamToFile(in, uploadFile);
      in.close();
    } catch (Exception ex) {
      model.addAttribute("msg", "上传出错!");
      model.addAttribute("err", Integer.valueOf(1));
      File df = new File(dir);
      if (df.exists()) {
        ReportService.deleteAll(df);
      }
      return "portaldiy/diy";
    }
    try
    {
      String descDir = request.getServletContext().getRealPath("/");
      sourceFile = dir + "/" + sourceFile;
      DiyUtils.unZip(descDir, sourceFile);
    }
    catch (Exception e) {
      model.addAttribute("msg", "更新出错!");
      model.addAttribute("err", Integer.valueOf(1));
      File df = new File(dir);
      if (df.exists()) {
        ReportService.deleteAll(df);
      }
      return "portaldiy/diy";
    }

    File df = new File(dir);
    if (df.exists()) {
      ReportService.deleteAll(df);
    }
    model.addAttribute("msg", "更新成功!");
    model.addAttribute("err", Integer.valueOf(0));
    return "portaldiy/diy";
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.PortaldiyController
 * JD-Core Version:    0.6.2
 */