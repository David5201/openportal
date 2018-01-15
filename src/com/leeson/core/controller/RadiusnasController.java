package com.leeson.core.controller;

import com.leeson.common.page.Pagination;
import com.leeson.common.utils.stringUtils;
import com.leeson.core.bean.Radiusnas;
import com.leeson.core.query.RadiusnasQuery;
import com.leeson.core.service.RadiusnasService;
import com.leeson.radius.core.Tool;
import com.leeson.radius.core.model.RadiusNasMap;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/radiusnasController")
public class RadiusnasController
{

  @Autowired
  private RadiusnasService radiusnasService;

  @RequestMapping({"ist.action"})
  public String page(RadiusnasQuery query, HttpServletRequest request, HttpServletResponse response, ModelMap model)
  {
    if (stringUtils.isBlank(query.getType())) {
      query.setType(null);
    }
    query.setNameLike(true);
    query.setDescriptionLike(true);
    Pagination pagination = this.radiusnasService.getRadiusnasListWithPage(query);
    model.addAttribute("pagination", pagination);
    model.addAttribute("query", query);
    return "radiusnas/list";
  }

  @RequestMapping({"dd.action"})
  public String addV(Radiusnas e, ModelMap model)
  {
    e.setEx1("0");
    e.setEx2("300");
    e.setEx3("600");
    e.setEx4("600");
    e.setEx5("1");
    e.setEx6("3799");
    model.addAttribute("entity", e);
    return "radiusnas/save";
  }

  @RequestMapping(value={"dd.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String add(Radiusnas e, ModelMap model)
  {
    if ((stringUtils.isBlank(e.getIp())) || (stringUtils.isBlank(e.getName())) || (stringUtils.isBlank(e.getSharedSecret()))) {
      model.addAttribute("msg", "地址、名称、密钥必须填写！");
      model.addAttribute("entity", e);
      return "radiusnas/save";
    }
    RadiusnasQuery q = new RadiusnasQuery();
    q.setIp(e.getIp());
    q.setIpLike(false);
    if (this.radiusnasService.getRadiusnasList(q).size() > 0) {
      model.addAttribute("msg", "该IP已经存在！");
      model.addAttribute("entity", e);
      return "radiusnas/save";
    }
    q = new RadiusnasQuery();
    q.setName(e.getName());
    q.setNameLike(false);
    if (this.radiusnasService.getRadiusnasList(q).size() > 0) {
      model.addAttribute("msg", "该名称已经存在！");
      model.addAttribute("entity", e);
      return "radiusnas/save";
    }
    this.radiusnasService.addRadiusnas(e);
    RadiusNasMap.getInstance().getNasMap().remove(e.getIp());
    String[] client = new String[9];
    client[0] = Tool.ByteToHex(e.getSharedSecret().getBytes());
    client[1] = e.getType();
    client[2] = e.getEx1();
    client[3] = e.getEx2();
    client[4] = e.getEx3();
    client[5] = e.getEx4();
    client[6] = e.getEx5();
    client[7] = e.getName();
    client[8] = e.getEx6();
    RadiusNasMap.getInstance().getNasMap().put(e.getIp(), client);
    return "redirect:ist.action";
  }

  @RequestMapping({"dit.action"})
  public String editV(@RequestParam Long id, ModelMap model)
  {
    Radiusnas e = this.radiusnasService.getRadiusnasByKey(id);
    model.addAttribute("entity", e);
    return "radiusnas/save";
  }

  @RequestMapping(value={"dit.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String edit(Radiusnas e, ModelMap model)
  {
    if ((stringUtils.isBlank(e.getIp())) || (stringUtils.isBlank(e.getName())) || (stringUtils.isBlank(e.getSharedSecret()))) {
      model.addAttribute("msg", "地址、名称、密钥必须填写！");
      model.addAttribute("entity", e);
      return "radiusnas/save";
    }
    RadiusnasQuery q = new RadiusnasQuery();
    q.setIp(e.getIp());
    q.setIpLike(false);
    List nasList = this.radiusnasService.getRadiusnasList(q);
    if ((nasList.size() > 0) && 
      (((Radiusnas)nasList.get(0)).getId() != e.getId())) {
      model.addAttribute("msg", "该IP已经存在！");
      model.addAttribute("entity", e);
      return "radiusnas/save";
    }

    q = new RadiusnasQuery();
    q.setName(e.getName());
    q.setNameLike(false);
    nasList = this.radiusnasService.getRadiusnasList(q);
    if ((nasList.size() > 0) && 
      (((Radiusnas)nasList.get(0)).getId() != e.getId())) {
      model.addAttribute("msg", "该名称已经存在！");
      model.addAttribute("entity", e);
      return "radiusnas/save";
    }

    RadiusNasMap.getInstance().getNasMap().remove(this.radiusnasService.getRadiusnasByKey(e.getId()).getIp());
    String[] client = new String[9];
    client[0] = Tool.ByteToHex(e.getSharedSecret().getBytes());
    client[1] = e.getType();
    client[2] = e.getEx1();
    client[3] = e.getEx2();
    client[4] = e.getEx3();
    client[5] = e.getEx4();
    client[6] = e.getEx5();
    client[7] = e.getName();
    client[8] = e.getEx6();
    RadiusNasMap.getInstance().getNasMap().put(e.getIp(), client);
    this.radiusnasService.updateRadiusnasByKey(e);
    return "redirect:ist.action";
  }

  @RequestMapping({"elete.action"})
  public String delete(@RequestParam Long id)
  {
    RadiusNasMap.getInstance().getNasMap().remove(this.radiusnasService.getRadiusnasByKey(id).getIp());
    this.radiusnasService.deleteByKey(id);
    return "redirect:ist.action";
  }

  @RequestMapping(value={"eletes.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String deletes(@RequestParam Long[] ids)
  {
    List<Long> list = Arrays.asList(ids);
    for (Long id : list) {
      RadiusNasMap.getInstance().getNasMap().remove(this.radiusnasService.getRadiusnasByKey(id).getIp());
    }
    this.radiusnasService.deleteByKeys(list);

    return "redirect:ist.action";
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.RadiusnasController
 * JD-Core Version:    0.6.2
 */