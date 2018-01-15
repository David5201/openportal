package com.leeson.core.controller;

import com.leeson.common.page.Pagination;
import com.leeson.common.utils.stringUtils;
import com.leeson.core.bean.Portallinkrecordall;
import com.leeson.core.query.PortallinkrecordallQuery;
import com.leeson.core.service.PortallinkrecordallService;
import com.leeson.core.utils.ExcelUtils;
import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/portallinkrecordallController")
public class PortallinkrecordallController
{

  @Autowired
  private PortallinkrecordallService portallinkrecordallService;
  private static SimpleDateFormat format = new SimpleDateFormat(
    "yyyy-MM-dd HH:mm:ss");

  private static DecimalFormat df = new DecimalFormat(".##");

  @RequestMapping({"ist.action"})
  public String page(PortallinkrecordallQuery query, String query_begin_time, String query_end_time, HttpServletRequest request, HttpServletResponse response, ModelMap model)
  {
    query.setLoginNameLike(true);
    query.setIpLike(true);
    query.setBasipLike(true);
    query.setApmacLike(true);
    query.setBasnameLike(true);
    query.setMacLike(true);
    query.setSsidLike(true);
    query.orderbyId(false);
    if (stringUtils.isBlank(query.getLoginName())) {
      query.setLoginName(null);
    }
    if (stringUtils.isBlank(query.getIp())) {
      query.setIp(null);
    }
    if (stringUtils.isBlank(query.getBasip())) {
      query.setBasip(null);
    }
    if (stringUtils.isBlank(query.getApmac())) {
      query.setApmac(null);
    }
    if (stringUtils.isBlank(query.getBasname())) {
      query.setBasname(null);
    }
    if (stringUtils.isBlank(query.getMac())) {
      query.setMac(null);
    }
    if (stringUtils.isBlank(query.getSsid())) {
      query.setSsid(null);
    }
    if (stringUtils.isBlank(query.getAgent())) {
      query.setAgent(null);
    }
    if (stringUtils.isBlank(query.getAuto())) {
      query.setAuto(null);
    }
    if (stringUtils.isBlank(query.getMethodtype())) {
      query.setMethodtype(null);
    }
    if (stringUtils.isBlank(query.getState())) {
      query.setState(null);
    }

    if (stringUtils.isBlank(query_begin_time)) {
      query_begin_time = null;
    }
    if (stringUtils.isBlank(query_end_time)) {
      query_end_time = null;
    }
    Date begin_time = null;
    Date end_time = null;
    try {
      if (stringUtils.isNotBlank(query_begin_time)) {
        begin_time = format.parse(query_begin_time + " 00:00:00");
        query.setBegin_time(begin_time);
      }
    } catch (Exception e) {
      model.addAttribute("msg", "开始日期格式错误！");
      query_begin_time = null;
    }
    try {
      if (stringUtils.isNotBlank(query_end_time)) {
        end_time = format.parse(query_end_time + " 23:59:59");
        query.setEnd_time(end_time);
      }
    } catch (Exception e) {
      model.addAttribute("msg", "结束日期格式错误！");
      query_end_time = null;
    }

    Pagination pagination = this.portallinkrecordallService
      .getPortallinkrecordallListWithPage(query);
    model.addAttribute("pagination", pagination);
    model.addAttribute("query", query);
    model.addAttribute("query_begin_time", query_begin_time);
    model.addAttribute("query_end_time", query_end_time);
    return "portallinkrecordall/list";
  }

  @RequestMapping({"elete.action"})
  public String delete(@RequestParam Long id)
  {
    this.portallinkrecordallService.deleteByKey(id);
    return "redirect:ist.action";
  }

  @RequestMapping(value={"eletes.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String deletes(@RequestParam Long[] ids)
  {
    List list = Arrays.asList(ids);
    this.portallinkrecordallService.deleteByKeys(list);
    return "redirect:ist.action";
  }

  @RequestMapping(value={"eletes.action"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  public String deletes()
  {
    this.portallinkrecordallService.deleteAll();
    return "redirect:ist.action";
  }

  @RequestMapping({"utlist.action"})
  public String outlist(PortallinkrecordallQuery query, String query_begin_time, String query_end_time, HttpServletRequest request, HttpServletResponse response, ModelMap model)
  {
    query.setLoginNameLike(true);
    query.setIpLike(true);
    query.setBasipLike(true);
    query.setApmacLike(true);
    query.setBasnameLike(true);
    query.setMacLike(true);
    query.setSsidLike(true);
    query.orderbyId(false);
    if (stringUtils.isBlank(query.getLoginName())) {
      query.setLoginName(null);
    }
    if (stringUtils.isBlank(query.getIp())) {
      query.setIp(null);
    }
    if (stringUtils.isBlank(query.getBasip())) {
      query.setBasip(null);
    }
    if (stringUtils.isBlank(query.getApmac())) {
      query.setApmac(null);
    }
    if (stringUtils.isBlank(query.getBasname())) {
      query.setBasname(null);
    }
    if (stringUtils.isBlank(query.getMac())) {
      query.setMac(null);
    }
    if (stringUtils.isBlank(query.getSsid())) {
      query.setSsid(null);
    }
    if (stringUtils.isBlank(query.getAgent())) {
      query.setAgent(null);
    }
    if (stringUtils.isBlank(query.getAuto())) {
      query.setAuto(null);
    }
    if (stringUtils.isBlank(query.getMethodtype())) {
      query.setMethodtype(null);
    }
    if (stringUtils.isBlank(query.getState())) {
      query.setState(null);
    }

    if (stringUtils.isBlank(query_begin_time)) {
      query_begin_time = null;
    }
    if (stringUtils.isBlank(query_end_time)) {
      query_end_time = null;
    }
    Date begin_time = null;
    Date end_time = null;
    try {
      if (stringUtils.isNotBlank(query_begin_time)) {
        begin_time = format.parse(query_begin_time + " 00:00:00");
        query.setBegin_time(begin_time);
      }
    } catch (Exception e) {
      model.addAttribute("msg", "开始日期格式错误！");
      query_begin_time = null;
    }
    try {
      if (stringUtils.isNotBlank(query_end_time)) {
        end_time = format.parse(query_end_time + " 23:59:59");
        query.setEnd_time(end_time);
      }
    } catch (Exception e) {
      model.addAttribute("msg", "结束日期格式错误！");
      query_end_time = null;
    }

    long onlineTime = 0L;
    List<Portallinkrecordall> linkAll = this.portallinkrecordallService
      .getPortallinkrecordallList(query);
    for (Portallinkrecordall link : linkAll) {
      onlineTime += link.getTime().longValue();
    }
    onlineTime /= 60000L;
    String onlineTimeS = df.format(onlineTime);
    if (onlineTimeS.startsWith(".")) {
      onlineTimeS = "0" + onlineTimeS;
    }

    String cfgPath = request.getServletContext().getRealPath("/");
    Date now = new Date();
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
    String nowString = format.format(now);
    File dir = new File(cfgPath + "ExcelOut/");
    if (!dir.exists()) {
      dir.mkdirs();
    }
    String fileName = cfgPath + "ExcelOut/Links_" + nowString + ".xls";
    try {
      ExcelUtils.writeLinksToExcel(fileName, linkAll, onlineTimeS);
    } catch (Exception e) {
      model.addAttribute("msg", "文件创建失败！");
      model.addAttribute("downUrl", null);
      model.addAttribute("err", Integer.valueOf(1));
      return "portallinkrecordall/outResult";
    }
    model.addAttribute("msg", "文件创建成功！");
    model.addAttribute("downUrl", "Links_" + nowString + ".xls");
    model.addAttribute("creatDate", nowString);
    model.addAttribute("err", Integer.valueOf(0));
    return "portallinkrecordall/outResult";
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.PortallinkrecordallController
 * JD-Core Version:    0.6.2
 */