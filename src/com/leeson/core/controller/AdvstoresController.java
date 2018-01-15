package com.leeson.core.controller;

import com.leeson.common.page.Pagination;
import com.leeson.common.utils.stringUtils;
import com.leeson.core.bean.Advstores;
import com.leeson.core.bean.Portaluser;
import com.leeson.core.query.AdvstoresQuery;
import com.leeson.core.query.PortaluserQuery;
import com.leeson.core.service.AdvstoresService;
import com.leeson.core.service.PortaluserService;
import com.leeson.core.utils.ImageUtil;
import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Controller
@RequestMapping("/advstoresController")
public class AdvstoresController
{
  private static Logger logger = Logger.getLogger(AdvstoresController.class);

  @Autowired
  private AdvstoresService advstoresService;

  @Autowired
  private PortaluserService portaluserService;

  @ResponseBody
  @RequestMapping({"pload"})
  public Map<String, String> uploadPreviewImage(HttpServletRequest request, HttpServletResponse response) { Map map = new HashMap();
    try {
      MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
      MultipartFile image = multipartRequest.getFile("advimg");

      String imageName = image.getOriginalFilename();
      String file_ext = imageName.substring(imageName.lastIndexOf(".") + 1);
      String tempImageName = UUID.randomUUID().toString() + "." + file_ext;

      SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
      String filename = "/" + df.format(new Date()) + "/";

      String filePath = request.getServletContext().getRealPath("/advPic") + filename;
      File fileLocation = new File(filePath);
      if (!fileLocation.exists()) {
        fileLocation.mkdir();
      }
      filePath = filePath + tempImageName;
      File file = new File(filePath);
      byte[] advImageBytes = null;
      InputStream advImageStream = null;
      file.createNewFile();
      advImageStream = image.getInputStream();
      advImageBytes = FileCopyUtils.copyToByteArray(advImageStream);
      FileCopyUtils.copy(advImageBytes, file);
      advImageStream.close();
      int result = ImageUtil.checkImgHW(filePath, 96, 96);
      if (result == 0) {
        String tempPath = "/advPic/" + filename + tempImageName;
        map.put("tempPath", tempPath);
        map.put("ret", "0");
        map.put("msg", "上传成功！！");
      } else if (result == 1) {
        map.put("ret", "1");
        map.put("msg", "图片尺寸错误！！");
      } else {
        map.put("ret", "1");
        map.put("msg", "图片格式错误！！");
      }
    } catch (Exception e) {
      logger.error("==============ERROR Start=============");
      logger.error(e);
      logger.error("ERROR INFO ", e);
      logger.error("==============ERROR End=============");
      map.put("ret", "1");
      map.put("msg", "发生错误！！");
    }
    return map;
  }

  @RequestMapping({"ist.action"})
  public String page(AdvstoresQuery query, HttpServletRequest request, HttpServletResponse response, ModelMap model)
  {
    List users = this.portaluserService
      .getPortaluserList(new PortaluserQuery());
    model.addAttribute("users", users);

    HttpSession session = request.getSession();
    Portaluser user = (Portaluser)session.getAttribute("user");
    if ((user == null) || (user.getId() == null)) {
      return "homeAction/index";
    }
    long uid = user.getId().longValue();
    if (!"admin".equals(user.getLoginName())) {
      query.setUid(Long.valueOf(uid));
    }

    query.setNameLike(true);
    query.setDescriptionLike(true);
    if (stringUtils.isBlank(query.getName())) {
      query.setName(null);
    }
    if (stringUtils.isBlank(query.getDescription())) {
      query.setDescription(null);
    }
    Pagination pagination = this.advstoresService
      .getAdvstoresListWithPage(query);
    model.addAttribute("pagination", pagination);
    model.addAttribute("query", query);
    return "advstores/list";
  }

  @RequestMapping({"dd.action"})
  public String add(ModelMap model, HttpServletRequest request)
  {
    HttpSession session = request.getSession();
    Portaluser user = (Portaluser)session.getAttribute("user");
    if ((user == null) || (user.getId() == null)) {
      return "homeAction/index";
    }
    return "advstores/save";
  }

  @RequestMapping(value={"dd.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String add(Advstores e, HttpServletRequest request)
  {
    HttpSession session = request.getSession();
    Portaluser user = (Portaluser)session.getAttribute("user");
    if ((user == null) || (user.getId() == null)) {
      return "homeAction/index";
    }
    long uid = user.getId().longValue();
    e.setUid(Long.valueOf(uid));
    e.setCreatDate(new Date());
    this.advstoresService.addAdvstores(e);

    return "redirect:ist.action";
  }

  @RequestMapping({"dit.action"})
  public String edit(@RequestParam Long id, ModelMap model, HttpServletRequest request)
  {
    Advstores e = this.advstoresService.getAdvstoresByKey(id);
    HttpSession session = request.getSession();
    Portaluser user = (Portaluser)session.getAttribute("user");
    if ((user == null) || (user.getId() == null)) {
      return "homeAction/index";
    }
    long uid = user.getId().longValue();
    if (!"admin".equals(user.getLoginName())) {
      long suid = e.getUid().longValue();
      if (suid != uid) {
        return "redirect:ist.action";
      }
    }
    model.addAttribute("entity", e);
    return "advstores/save";
  }

  @RequestMapping(value={"dit.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String edit(Advstores e, HttpServletRequest request)
  {
    Advstores s = this.advstoresService.getAdvstoresByKey(e.getId());
    HttpSession session = request.getSession();
    Portaluser user = (Portaluser)session.getAttribute("user");
    if ((user == null) || (user.getId() == null)) {
      return "homeAction/index";
    }
    long uid = user.getId().longValue();
    if (!"admin".equals(user.getLoginName())) {
      long suid = s.getUid().longValue();
      if (suid != uid) {
        return "redirect:ist.action";
      }
    }
    this.advstoresService.updateAdvstoresByKey(e);
    return "redirect:ist.action";
  }

  @RequestMapping({"elete.action"})
  public String delete(@RequestParam Long id, HttpServletRequest request)
  {
    Advstores s = this.advstoresService.getAdvstoresByKey(id);
    HttpSession session = request.getSession();
    Portaluser user = (Portaluser)session.getAttribute("user");
    if ((user == null) || (user.getId() == null)) {
      return "homeAction/index";
    }
    long uid = user.getId().longValue();
    if (!"admin".equals(user.getLoginName())) {
      long suid = s.getUid().longValue();
      if (suid != uid) {
        return "redirect:ist.action";
      }
    }
    this.advstoresService.deleteByKey(id);
    return "redirect:ist.action";
  }

  @RequestMapping(value={"eletes.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String deletes(@RequestParam Long[] ids, HttpServletRequest request)
  {
    List<Long> list = Arrays.asList(ids);
    for (Long id : list) {
      Advstores s = this.advstoresService.getAdvstoresByKey(id);
      HttpSession session = request.getSession();
      Portaluser user = (Portaluser)session.getAttribute("user");
      if ((user == null) || (user.getId() == null)) {
        return "homeAction/index";
      }
      long uid = user.getId().longValue();
      if (!"admin".equals(user.getLoginName())) {
        long suid = s.getUid().longValue();
        if (suid == uid)
          this.advstoresService.deleteByKey(id);
      }
      else {
        this.advstoresService.deleteByKey(id);
      }
    }

    return "redirect:ist.action";
  }

  @RequestMapping(value={"eletes.action"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  public String deletes(HttpServletRequest request)
  {
    HttpSession session = request.getSession();
    Portaluser user = (Portaluser)session.getAttribute("user");
    if ((user == null) || (user.getId() == null)) {
      return "homeAction/index";
    }
    long uid = user.getId().longValue();
    AdvstoresQuery query = new AdvstoresQuery();
    if (!"admin".equals(user.getLoginName())) {
      query.setUid(Long.valueOf(uid));
    }
    List<Advstores> stores = this.advstoresService.getAdvstoresList(query);
    for (Advstores s : stores) {
      this.advstoresService.deleteByKey(s.getId());
    }
    return "redirect:ist.action";
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.AdvstoresController
 * JD-Core Version:    0.6.2
 */