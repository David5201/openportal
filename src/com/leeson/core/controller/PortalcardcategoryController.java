package com.leeson.core.controller;

import com.leeson.common.page.Pagination;
import com.leeson.common.utils.stringUtils;
import com.leeson.core.bean.Portalcardcategory;
import com.leeson.core.query.PortalcardcategoryQuery;
import com.leeson.core.query.PortalspeedQuery;
import com.leeson.core.service.PortalcardcategoryService;
import com.leeson.core.service.PortalspeedService;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/portalcardcategoryController")
public class PortalcardcategoryController
{

  @Autowired
  private PortalcardcategoryService portalcardcategoryService;

  @Autowired
  private PortalspeedService portalspeedService;

  @RequestMapping({"ist.action"})
  public String page(PortalcardcategoryQuery query, ModelMap model)
  {
    query.setNameLike(true);
    query.setDescriptionLike(true);
    if (stringUtils.isBlank(query.getName())) {
      query.setName(null);
    }
    if (stringUtils.isBlank(query.getDescription())) {
      query.setDescription(null);
    }
    if (stringUtils.isBlank(query.getState())) {
      query.setState(null);
    }
    List speeds = this.portalspeedService
      .getPortalspeedList(new PortalspeedQuery());
    model.addAttribute("speeds", speeds);
    Pagination pagination = this.portalcardcategoryService.getPortalcardcategoryListWithPage(query);
    model.addAttribute("pagination", pagination);
    model.addAttribute("query", query);
    return "portalcardcategory/list";
  }

  @RequestMapping({"ddV.action"})
  public String addV(ModelMap model)
  {
    List speeds = this.portalspeedService
      .getPortalspeedList(new PortalspeedQuery());
    model.addAttribute("speeds", speeds);
    return "portalcardcategory/save";
  }

  @RequestMapping(value={"dd.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String add(Portalcardcategory e, ModelMap model)
  {
    if ((stringUtils.isBlank(e.getName())) || (stringUtils.isBlank(e.getState())) || (e.getTime() == null) || (e.getTime().longValue() == 0L)) {
      model.addAttribute("msg", "名称 类型和计数不能为空！");
      model.addAttribute("entity", e);
      List speeds = this.portalspeedService
        .getPortalspeedList(new PortalspeedQuery());
      model.addAttribute("speeds", speeds);
      return "portalcardcategory/save";
    }
    if (e.getMoney() == null) {
      model.addAttribute("msg", "售价不能为空！");
      model.addAttribute("entity", e);
      List speeds = this.portalspeedService
        .getPortalspeedList(new PortalspeedQuery());
      model.addAttribute("speeds", speeds);
      return "portalcardcategory/save";
    }
    if (e.getMaclimitcount() == null) {
      e.setMaclimitcount(Integer.valueOf(1));
    }
    if (e.getMoney() == null) {
      e.setMoney(Double.valueOf(0.0D));
    }
    this.portalcardcategoryService.addPortalcardcategory(e);

    return "redirect:ist.action";
  }

  @RequestMapping({"ditV.action"})
  public String editV(@RequestParam Long id, ModelMap model)
  {
    Portalcardcategory e = this.portalcardcategoryService.getPortalcardcategoryByKey(id);
    model.addAttribute("entity", e);
    List speeds = this.portalspeedService
      .getPortalspeedList(new PortalspeedQuery());
    model.addAttribute("speeds", speeds);
    return "portalcardcategory/save";
  }

  @RequestMapping(value={"dit.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String edit(Portalcardcategory e, ModelMap model)
  {
    if ((stringUtils.isBlank(e.getName())) || (stringUtils.isBlank(e.getState())) || (e.getTime() == null) || (e.getTime().longValue() == 0L)) {
      model.addAttribute("msg", "名称 类型和计数不能为空！");
      model.addAttribute("entity", e);
      List speeds = this.portalspeedService
        .getPortalspeedList(new PortalspeedQuery());
      model.addAttribute("speeds", speeds);
      return "portalcardcategory/save";
    }
    if (e.getMoney() == null) {
      model.addAttribute("msg", "售价不能为空！");
      model.addAttribute("entity", e);
      List speeds = this.portalspeedService
        .getPortalspeedList(new PortalspeedQuery());
      model.addAttribute("speeds", speeds);
      return "portalcardcategory/save";
    }
    if (e.getMaclimitcount() == null) {
      e.setMaclimitcount(Integer.valueOf(1));
    }
    if (e.getMoney() == null) {
      e.setMoney(Double.valueOf(0.0D));
    }
    this.portalcardcategoryService.updatePortalcardcategoryByKeyAll(e);
    return "redirect:ist.action";
  }

  @RequestMapping({"elete.action"})
  public String delete(@RequestParam Long id)
  {
    this.portalcardcategoryService.deleteByKey(id);
    return "redirect:ist.action";
  }

  @RequestMapping(value={"eletes.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String deletes(@RequestParam Long[] ids)
  {
    List list = Arrays.asList(ids);
    this.portalcardcategoryService.deleteByKeys(list);

    return "redirect:ist.action";
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.PortalcardcategoryController
 * JD-Core Version:    0.6.2
 */