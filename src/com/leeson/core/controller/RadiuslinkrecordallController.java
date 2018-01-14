package com.leeson.core.controller;

import com.leeson.common.page.Pagination;
import com.leeson.common.utils.stringUtils;
import com.leeson.core.bean.Radiuslinkrecordall;
import com.leeson.core.query.RadiuslinkrecordallQuery;
import com.leeson.core.service.RadiuslinkrecordallService;
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
public class RadiuslinkrecordallController
{

  @Autowired
  private RadiuslinkrecordallService radiuslinkrecordallService;
  private static SimpleDateFormat format = new SimpleDateFormat(
    "yyyy-MM-dd HH:mm:ss");

  private static DecimalFormat df = new DecimalFormat(".##");

  @RequestMapping({"ist.action"})
  public String page(RadiuslinkrecordallQuery query, String query_begin_time, String query_end_time, HttpServletRequest request, HttpServletResponse response, ModelMap model)
  {
    query.setEx3Like(true);
    query.setAcctsessionidLike(true);
    query.setCallingstationidLike(true);
    query.setNameLike(true);
    query.setNasipLike(true);
    query.setSourceipLike(true);
    query.setStateLike(true);
    query.setUseripLike(true);
    query.orderbyId(false);
    if (stringUtils.isBlank(query.getEx3())) {
      query.setEx3(null);
    }
    if (stringUtils.isBlank(query.getAcctsessionid())) {
      query.setAcctsessionid(null);
    }
    if (stringUtils.isBlank(query.getCallingstationid())) {
      query.setCallingstationid(null);
    }
    if (stringUtils.isBlank(query.getName())) {
      query.setName(null);
    }
    if (stringUtils.isBlank(query.getNasip())) {
      query.setNasip(null);
    }
    if (stringUtils.isBlank(query.getSourceip())) {
      query.setSourceip(null);
    }
    if (stringUtils.isBlank(query.getState())) {
      query.setState(null);
    }
    if (stringUtils.isBlank(query.getUserip())) {
      query.setUserip(null);
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

    Pagination pagination = this.radiuslinkrecordallService
      .getRadiuslinkrecordallListWithPage(query);
    model.addAttribute("pagination", pagination);
    model.addAttribute("query", query);
    model.addAttribute("query_begin_time", query_begin_time);
    model.addAttribute("query_end_time", query_end_time);
    return "radiuslinkrecordall/list";
  }

  @RequestMapping({"elete.action"})
  public String delete(@RequestParam Long id)
  {
    this.radiuslinkrecordallService.deleteByKey(id);
    return "redirect:ist.action";
  }

  @RequestMapping(value={"eletes.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String deletes(@RequestParam Long[] ids)
  {
    List list = Arrays.asList(ids);
    this.radiuslinkrecordallService.deleteByKeys(list);

    return "redirect:ist.action";
  }

  @RequestMapping(value={"eletes.action"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  public String deletes()
  {
    this.radiuslinkrecordallService.deleteAll();
    return "redirect:ist.action";
  }

  @RequestMapping({"utlist.action"})
  public String outlist(RadiuslinkrecordallQuery query, String query_begin_time, String query_end_time, HttpServletRequest request, HttpServletResponse response, ModelMap model)
  {
    query.setEx3Like(true);
    query.setAcctsessionidLike(true);
    query.setCallingstationidLike(true);
    query.setNameLike(true);
    query.setNasipLike(true);
    query.setSourceipLike(true);
    query.setStateLike(true);
    query.setUseripLike(true);
    query.orderbyId(false);
    if (stringUtils.isBlank(query.getEx3())) {
      query.setEx3(null);
    }
    if (stringUtils.isBlank(query.getAcctsessionid())) {
      query.setAcctsessionid(null);
    }
    if (stringUtils.isBlank(query.getCallingstationid())) {
      query.setCallingstationid(null);
    }
    if (stringUtils.isBlank(query.getName())) {
      query.setName(null);
    }
    if (stringUtils.isBlank(query.getNasip())) {
      query.setNasip(null);
    }
    if (stringUtils.isBlank(query.getSourceip())) {
      query.setSourceip(null);
    }
    if (stringUtils.isBlank(query.getState())) {
      query.setState(null);
    }
    if (stringUtils.isBlank(query.getUserip())) {
      query.setUserip(null);
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
    long octets = 0L;
    List<Radiuslinkrecordall> linkAll = this.radiuslinkrecordallService
      .getRadiuslinkrecordallList(query);
    for (Radiuslinkrecordall link : linkAll) {
      onlineTime += link.getTime().longValue();
      octets = octets + link.getIns().longValue() + link.getOuts().longValue();
    }
    onlineTime /= 60000L;
    octets = octets / 1024L / 1024L;
    String onlineTimeS = df.format(onlineTime);
    String octetsS = df.format(octets);
    if (onlineTimeS.startsWith(".")) {
      onlineTimeS = "0" + onlineTimeS;
    }
    if (octetsS.startsWith(".")) {
      octetsS = "0" + octetsS;
    }

    String cfgPath = request.getServletContext().getRealPath("/");
    Date now = new Date();
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
    String nowString = format.format(now);
    File dir = new File(cfgPath + "ExcelOut/");
    if (!dir.exists()) {
      dir.mkdirs();
    }
    String fileName = cfgPath + "ExcelOut/RadiusLinks_" + nowString + ".xls";
    try {
      ExcelUtils.writeRadiusLinksToExcel(fileName, linkAll, onlineTimeS, octetsS);
    } catch (Exception e) {
      model.addAttribute("msg", "文件创建失败！");
      model.addAttribute("downUrl", null);
      model.addAttribute("err", Integer.valueOf(1));
      return "radiuslinkrecordall/outResult";
    }
    model.addAttribute("msg", "文件创建成功！");
    model.addAttribute("downUrl", "RadiusLinks_" + nowString + ".xls");
    model.addAttribute("creatDate", nowString);
    model.addAttribute("err", Integer.valueOf(0));
    return "radiuslinkrecordall/outResult";
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.RadiuslinkrecordallController
 * JD-Core Version:    0.6.2
 */