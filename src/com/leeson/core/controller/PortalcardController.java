package com.leeson.core.controller;

import com.leeson.common.page.Pagination;
import com.leeson.common.utils.stringUtils;
import com.leeson.core.bean.Portalcard;
import com.leeson.core.bean.Portalcardcategory;
import com.leeson.core.query.PortalcardQuery;
import com.leeson.core.query.PortalcardcategoryQuery;
import com.leeson.core.query.PortalspeedQuery;
import com.leeson.core.service.PortalcardService;
import com.leeson.core.service.PortalcardcategoryService;
import com.leeson.core.service.PortalspeedService;
import com.leeson.core.utils.ExcelUtils;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PortalcardController
{

  @Autowired
  private PortalcardService portalcardService;

  @Autowired
  private PortalcardcategoryService portalcardcategoryService;

  @Autowired
  private PortalspeedService portalspeedService;

  @RequestMapping({"ist.action"})
  public String page(PortalcardQuery query, HttpServletRequest request, HttpServletResponse response, ModelMap model)
  {
    query.setUserDel(Integer.valueOf(0));
    query.setNameLike(true);
    query.setAccountNameLike(true);
    if (stringUtils.isBlank(query.getName())) {
      query.setName(null);
    }
    if (stringUtils.isBlank(query.getAccountName())) {
      query.setAccountName(null);
    }
    if (stringUtils.isBlank(query.getCategoryType())) {
      query.setCategoryType(null);
    }
    if (stringUtils.isBlank(query.getPayType())) {
      query.setPayType(null);
    }
    if (stringUtils.isBlank(query.getState())) {
      query.setState(null);
    }
    List speeds = this.portalspeedService
      .getPortalspeedList(new PortalspeedQuery());
    model.addAttribute("speeds", speeds);
    Pagination pagination = this.portalcardService
      .getPortalcardListWithPage(query);
    model.addAttribute("pagination", pagination);
    model.addAttribute("query", query);
    return "portalcard/list";
  }

  @RequestMapping({"dd.action"})
  public String add(Long cardCategoryId, ModelMap model)
  {
    List cardCategoryList = this.portalcardcategoryService
      .getPortalcardcategoryList(new PortalcardcategoryQuery());
    if ((cardCategoryId != null) && (cardCategoryId.longValue() != 0L)) {
      model.addAttribute("cardCategoryId", cardCategoryId);
      Double money = this.portalcardcategoryService
        .getPortalcardcategoryByKey(cardCategoryId).getMoney();
      model.addAttribute("money", money);
    }
    model.addAttribute("cardCategoryList", cardCategoryList);
    return "portalcard/save";
  }

  @RequestMapping(value={"dd.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String add(Portalcard e, String cardCategoryId, String cardCount, ModelMap model)
  {
    if ((stringUtils.isBlank(e.getName())) || 
      (stringUtils.isBlank(cardCategoryId)) || 
      (stringUtils.isBlank(cardCount))) {
      model.addAttribute("msg", "充值卡分类必须选择，名称和创建数量必须填写！！！");
      model.addAttribute("entity", e);
      model.addAttribute("money", e.getMoney());
      if (stringUtils.isNotBlank(cardCategoryId)) {
        model.addAttribute("cardCategoryId", 
          Long.valueOf(cardCategoryId));
      }
      if (stringUtils.isNotBlank(cardCount)) {
        model.addAttribute("cardCount", Integer.valueOf(cardCount));
      }
      List cardCategoryList = this.portalcardcategoryService
        .getPortalcardcategoryList(new PortalcardcategoryQuery());
      model.addAttribute("cardCategoryList", cardCategoryList);
      return "portalcard/save";
    }
    Long ccId = Long.valueOf(cardCategoryId);
    int count = Integer.valueOf(cardCount).intValue();
    Portalcardcategory cc = this.portalcardcategoryService
      .getPortalcardcategoryByKey(ccId);
    String categoryType = cc.getState();
    String payType;
    if (categoryType.equals("0")) {
      payType = "2";
    }
    else
    {
      if (categoryType.equals("4"))
        payType = "4";
      else {
        payType = "3";
      }
    }
    if (e.getMoney() == null) {
      e.setMoney(cc.getMoney());
    }
    if (e.getMoney() == null) {
      e.setMoney(Double.valueOf(0.0D));
    }

    Long time = cc.getTime();
    Long payTime = Long.valueOf(0L);
    if (categoryType.equals("0"))
      payTime = Long.valueOf(time.longValue() * 1000L * 60L * 60L);
    else if (categoryType.equals("1"))
      payTime = Long.valueOf(time.longValue() * 1000L * 60L * 60L * 24L);
    else if (categoryType.equals("2"))
      payTime = Long.valueOf(time.longValue() * 1000L * 60L * 60L * 24L * 31L);
    else if (categoryType.equals("3"))
      payTime = Long.valueOf(time.longValue() * 1000L * 60L * 60L * 24L * 31L * 12L);
    else if (categoryType.equals("4")) {
      payTime = Long.valueOf(time.longValue() * 1024L * 1024L);
    }

    for (int i = 1; i <= count; i++) {
      Portalcard card = new Portalcard();
      card.setName(e.getName());
      card.setDescription(e.getDescription());
      card.setCategoryType(categoryType);
      card.setPayType(payType);
      card.setState("0");
      card.setPayTime(payTime);
      card.setAccountDel(Integer.valueOf(0));
      card.setUserDel(Integer.valueOf(0));
      card.setCdKey(UUID.randomUUID().toString());
      card.setMoney(e.getMoney());

      card.setMaclimit(cc.getMaclimit());
      card.setMaclimitcount(cc.getMaclimitcount());
      card.setAutologin(cc.getAutologin());
      card.setSpeed(cc.getSpeed());

      this.portalcardService.addPortalcard(card);
    }

    return "redirect:ist.action";
  }

  @RequestMapping({"dit.action"})
  public String edit(@RequestParam Long id)
  {
    Portalcard card = this.portalcardService.getPortalcardByKey(id);
    int state = Integer.valueOf(card.getState()).intValue();
    state++;
    if (state > 2) {
      state = 0;
    }
    card.setState(String.valueOf(state));
    this.portalcardService.updatePortalcardByKey(card);
    return "redirect:ist.action";
  }

  @RequestMapping({"elete.action"})
  public String delete(@RequestParam Long id)
  {
    Portalcard card = new Portalcard();
    card.setId(id);
    card.setUserDel(Integer.valueOf(1));
    this.portalcardService.updatePortalcardByKey(card);
    return "redirect:ist.action";
  }

  @RequestMapping(value={"eletes.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String deletes(@RequestParam Long[] ids)
  {
    List<Long> list = Arrays.asList(ids);
    for (Long id : list) {
      Portalcard card = new Portalcard();
      card.setId(id);
      card.setUserDel(Integer.valueOf(1));
      this.portalcardService.updatePortalcardByKey(card);
    }

    return "redirect:ist.action";
  }

  @RequestMapping({"ut.action"})
  public String out(PortalcardQuery query, ModelMap model)
  {
    query.setNameLike(true);
    query.setAccountNameLike(true);
    if (stringUtils.isBlank(query.getName())) {
      query.setName(null);
    }
    if (stringUtils.isBlank(query.getAccountName())) {
      query.setAccountName(null);
    }
    if (stringUtils.isBlank(query.getCategoryType())) {
      query.setCategoryType(null);
    }
    if (stringUtils.isBlank(query.getPayType())) {
      query.setPayType(null);
    }
    if (stringUtils.isBlank(query.getState())) {
      query.setState(null);
    }
    List speeds = this.portalspeedService
      .getPortalspeedList(new PortalspeedQuery());
    model.addAttribute("speeds", speeds);
    Pagination pagination = this.portalcardService
      .getPortalcardListWithPage(query);
    model.addAttribute("pagination", pagination);
    model.addAttribute("query", query);
    return "portalcard/out";
  }

  @RequestMapping({"utV.action"})
  public String outV(PortalcardQuery query, ModelMap model, HttpServletRequest request)
  {
    query.setNameLike(true);
    query.setAccountNameLike(true);
    if (stringUtils.isBlank(query.getName())) {
      query.setName(null);
    }
    if (stringUtils.isBlank(query.getAccountName())) {
      query.setAccountName(null);
    }
    if (stringUtils.isBlank(query.getCategoryType())) {
      query.setCategoryType(null);
    }
    if (stringUtils.isBlank(query.getPayType())) {
      query.setPayType(null);
    }
    if (stringUtils.isBlank(query.getState())) {
      query.setState(null);
    }
    List cards = this.portalcardService.getPortalcardList(query);
    String cfgPath = request.getServletContext().getRealPath("/");
    Date now = new Date();
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
    String nowString = format.format(now);
    File dir = new File(cfgPath + "ExcelOut/");
    if (!dir.exists()) {
      dir.mkdirs();
    }
    String fileName = cfgPath + "ExcelOut/card_" + nowString + ".xls";
    try {
      ExcelUtils.writeCardsToExcel(fileName, cards);
    } catch (Exception e) {
      e.printStackTrace();
      model.addAttribute("msg", "文件创建失败！");
      model.addAttribute("downUrl", null);
      model.addAttribute("err", Integer.valueOf(1));
      return "portalaccount/outResult";
    }
    model.addAttribute("msg", "文件创建成功！");
    model.addAttribute("downUrl", "card_" + nowString + ".xls");
    model.addAttribute("creatDate", nowString);
    model.addAttribute("err", Integer.valueOf(0));
    return "portalcard/outResult";
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.PortalcardController
 * JD-Core Version:    0.6.2
 */