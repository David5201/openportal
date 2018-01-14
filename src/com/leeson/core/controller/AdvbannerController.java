package com.leeson.core.controller;

import com.leeson.common.page.Pagination;
import com.leeson.common.utils.stringUtils;
import com.leeson.core.bean.Advadv;
import com.leeson.core.bean.Advbanner;
import com.leeson.core.bean.Portaluser;
import com.leeson.core.query.AdvadvQuery;
import com.leeson.core.query.AdvbannerQuery;
import com.leeson.core.query.AdvstoresQuery;
import com.leeson.core.query.PortaluserQuery;
import com.leeson.core.service.AdvadvService;
import com.leeson.core.service.AdvbannerService;
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
public class AdvbannerController
{
  private static Logger logger = Logger.getLogger(AdvbannerController.class);

  @Autowired
  private AdvbannerService advbannerService;

  @Autowired
  private AdvadvService advadvService;

  @Autowired
  private PortaluserService portaluserService;

  @Autowired
  private AdvstoresService advstoresService;

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
      int result = ImageUtil.checkImgHW(filePath, 600, 300);
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
  public String page(AdvbannerQuery query, HttpServletRequest request, HttpServletResponse response, ModelMap model)
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
    if (stringUtils.isBlank(query.getName())) {
      query.setName(null);
    }

    Pagination pagination = this.advbannerService.getAdvbannerListWithPage(query);
    model.addAttribute("pagination", pagination);
    model.addAttribute("query", query);

    AdvstoresQuery squery = new AdvstoresQuery();
    AdvadvQuery aQuery = new AdvadvQuery();
    if (!"admin".equals(user.getLoginName())) {
      squery.setUid(Long.valueOf(uid));
      aQuery.setUid(Long.valueOf(uid));
    }
    List stores = this.advstoresService.getAdvstoresList(squery);
    model.addAttribute("stores", stores);
    List advs = this.advadvService.getAdvadvList(aQuery);
    model.addAttribute("advs", advs);
    return "advbanner/list";
  }

  @RequestMapping({"dd.action"})
  public String addv(Advbanner e, ModelMap model, HttpServletRequest request)
  {
    HttpSession session = request.getSession();
    Portaluser user = (Portaluser)session.getAttribute("user");
    if ((user == null) || (user.getId() == null)) {
      return "homeAction/index";
    }
    long uid = user.getId().longValue();
    AdvadvQuery aQuery = new AdvadvQuery();
    if (!"admin".equals(user.getLoginName())) {
      aQuery.setUid(Long.valueOf(uid));
    }
    List advs = this.advadvService.getAdvadvList(aQuery);
    model.addAttribute("advs", advs);
    model.addAttribute("entity", e);
    return "advbanner/save";
  }

  @RequestMapping(value={"dd.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String add(Advbanner e, ModelMap model, HttpServletRequest request)
  {
    HttpSession session = request.getSession();
    Portaluser user = (Portaluser)session.getAttribute("user");
    if ((user == null) || (user.getId() == null)) {
      return "homeAction/index";
    }
    long uid = user.getId().longValue();
    Advadv adv = this.advadvService.getAdvadvByKey(e.getAid());
    long auid = adv.getUid().longValue();
    if (uid != auid) {
      return "redirect:ist.action";
    }
    e.setUid(Long.valueOf(uid));
    e.setSid(adv.getSid());

    String domain = e.getUrl().trim();
    if (stringUtils.isNotBlank(domain)) {
      if ((!domain.startsWith("http:"))) {
        domain = "http://" + domain;
      }
      while (domain.endsWith("/"))
        domain = domain.substring(0, domain.length() - 1);
    }
    else {
      domain = 
        ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap()
        .get("core"))[0];
    }
    e.setUrl(domain);

    this.advbannerService.addAdvbanner(e);

    return "redirect:ist.action";
  }

  @RequestMapping({"dit.action"})
  public String edit(@RequestParam Long id, ModelMap model, HttpServletRequest request)
  {
    Advbanner e = this.advbannerService.getAdvbannerByKey(id);
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

    AdvadvQuery aQuery = new AdvadvQuery();
    if (!"admin".equals(user.getLoginName())) {
      aQuery.setUid(Long.valueOf(uid));
    }
    List advs = this.advadvService.getAdvadvList(aQuery);
    model.addAttribute("advs", advs);

    model.addAttribute("entity", e);
    return "advbanner/save";
  }

  @RequestMapping(value={"dit.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String edit(Advbanner e, ModelMap model, HttpServletRequest request)
  {
    Advbanner b = this.advbannerService.getAdvbannerByKey(e.getId());
    HttpSession session = request.getSession();
    Portaluser user = (Portaluser)session.getAttribute("user");
    if ((user == null) || (user.getId() == null)) {
      return "homeAction/index";
    }
    long uid = user.getId().longValue();
    if (!"admin".equals(user.getLoginName())) {
      long suid = b.getUid().longValue();
      if (suid != uid) {
        return "redirect:ist.action";
      }
    }

    String domain = e.getUrl().trim();
    if (stringUtils.isNotBlank(domain)) {
      if ((!domain.startsWith("http:"))) {
        domain = "http://" + domain;
      }
      while (domain.endsWith("/"))
        domain = domain.substring(0, domain.length() - 1);
    }
    else {
      domain = 
        ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap()
        .get("core"))[0];
    }
    e.setUrl(domain);

    this.advbannerService.updateAdvbannerByKey(e);
    return "redirect:ist.action";
  }

  @RequestMapping({"elete.action"})
  public String delete(@RequestParam Long id, HttpServletRequest request)
  {
    Advbanner s = this.advbannerService.getAdvbannerByKey(id);
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
    this.advbannerService.deleteByKey(id);
    return "redirect:ist.action";
  }

  @RequestMapping(value={"eletes.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String deletes(@RequestParam Long[] ids, HttpServletRequest request)
  {
    List<Long> list = Arrays.asList(ids);
    for (Long id : list) {
      Advbanner s = this.advbannerService.getAdvbannerByKey(id);
      HttpSession session = request.getSession();
      Portaluser user = (Portaluser)session.getAttribute("user");
      if ((user == null) || (user.getId() == null)) {
        return "homeAction/index";
      }
      long uid = user.getId().longValue();
      if (!"admin".equals(user.getLoginName())) {
        long suid = s.getUid().longValue();
        if (suid == uid)
          this.advbannerService.deleteByKey(id);
      }
      else {
        this.advbannerService.deleteByKey(id);
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
    AdvbannerQuery query = new AdvbannerQuery();
    if (!"admin".equals(user.getLoginName())) {
      query.setUid(Long.valueOf(uid));
    }
    List<Advbanner> banners = this.advbannerService.getAdvbannerList(query);
    for (Advbanner banner : banners) {
      this.advbannerService.deleteByKey(banner.getId());
    }
    return "redirect:ist.action";
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.AdvbannerController
 * JD-Core Version:    0.6.2
 */