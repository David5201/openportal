package com.leeson.core.controller;

import com.leeson.common.page.Pagination;
import com.leeson.common.utils.stringUtils;
import com.leeson.core.bean.Portalcard;
import com.leeson.core.bean.Portalorder;
import com.leeson.core.query.PortalcardQuery;
import com.leeson.core.query.PortalorderQuery;
import com.leeson.core.service.PortalcardService;
import com.leeson.core.service.PortalcardcategoryService;
import com.leeson.core.service.PortalorderService;
import com.leeson.core.utils.ExcelUtils;
import java.io.File;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PortalmoneyController
{

  @Autowired
  private PortalcardService portalcardService;

  @Autowired
  private PortalcardcategoryService portalcardcategoryService;

  @Autowired
  private PortalorderService portalorderService;
  private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  private static DecimalFormat df = new DecimalFormat(".##");

  @RequestMapping({"rder.action"})
  public String order(PortalorderQuery query, String begintime, String endtime, HttpServletRequest request, HttpServletResponse response, ModelMap model)
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
    if (stringUtils.isBlank(query.getBuyer())) {
      query.setBuyer(null);
    }
    if (stringUtils.isBlank(query.getSeller())) {
      query.setSeller(null);
    }

    query.setState("1");
    query.setStateLike(false);
    query.orderbyBuyDate(true);
    query.orderbyPayDate(true);

    Double money = Double.valueOf(0.0D);
    String begintimeQS = null;
    String endtimeQS = null;
    Date begin_time = null;
    Date end_time = null;

    if (stringUtils.isNotBlank(begintime)) {
      begintimeQS = begintime + " 00:00:00";
      try {
        begin_time = format.parse(begintimeQS);
        query.setBegin_time1(begin_time);
      } catch (ParseException e) {
        model.addAttribute("msg", "开始日期格式错误！");
        begintime = null;
      }
    }
    if (stringUtils.isNotBlank(endtime)) {
      endtimeQS = endtime + " 23:59:59";
      try {
        end_time = format.parse(endtimeQS);
        query.setEnd_time1(end_time);
      } catch (ParseException e) {
        model.addAttribute("msg", "结束日期格式错误！");
        endtime = null;
      }
    }
    Pagination pagination = this.portalorderService
      .getPortalorderListWithPage(query);
    List<Portalorder> orders = this.portalorderService.getPortalorderList(query);
    for (Portalorder order : orders) {
      money = Double.valueOf(money.doubleValue() + order.getMoney().doubleValue());
    }
    String moneyS = df.format(money);
    if (moneyS.startsWith(".")) {
      moneyS = "0" + moneyS;
    }
    model.addAttribute("pagination", pagination);
    model.addAttribute("query", query);
    model.addAttribute("begintime", begintime);
    model.addAttribute("endtime", endtime);
    model.addAttribute("money", moneyS);
    return "portalmoney/order";
  }

  @RequestMapping({"ard.action"})
  public String card(PortalcardQuery query, String begintime, String endtime, HttpServletRequest request, HttpServletResponse response, ModelMap model)
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
    query.setState("0");
    query.orderbyBuyDate(true);
    query.orderbyPayDate(true);

    Double money = Double.valueOf(0.0D);
    String begintimeQS = null;
    String endtimeQS = null;
    Date begin_time = null;
    Date end_time = null;

    if (stringUtils.isNotBlank(begintime)) {
      begintimeQS = begintime + " 00:00:00";
      try {
        begin_time = format.parse(begintimeQS);
        query.setBegin_time1(begin_time);
      } catch (ParseException e) {
        model.addAttribute("msg", "开始日期格式错误！");
        begintime = null;
      }
    }
    if (stringUtils.isNotBlank(endtime)) {
      endtimeQS = endtime + " 23:59:59";
      try {
        end_time = format.parse(endtimeQS);
        query.setEnd_time1(end_time);
      } catch (ParseException e) {
        model.addAttribute("msg", "结束日期格式错误！");
        endtime = null;
      }
    }
    Pagination pagination = this.portalcardService
      .getPortalcardSaleListWithPage(query);
    List<Portalcard> cards = this.portalcardService.getPortalcardSaleList(query);
    for (Portalcard card : cards) {
      money = Double.valueOf(money.doubleValue() + card.getMoney().doubleValue());
    }
    String moneyS = df.format(money);
    if (moneyS.startsWith(".")) {
      moneyS = "0" + moneyS;
    }
    model.addAttribute("pagination", pagination);
    model.addAttribute("query", query);
    model.addAttribute("begintime", begintime);
    model.addAttribute("endtime", endtime);
    model.addAttribute("money", moneyS);
    return "portalmoney/card";
  }

  @RequestMapping({"utCard.action"})
  public String outCard(PortalcardQuery query, String begintime, String endtime, HttpServletRequest request, HttpServletResponse response, ModelMap model)
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
    query.setState("0");
    query.orderbyBuyDate(true);
    query.orderbyPayDate(true);

    Double money = Double.valueOf(0.0D);
    String begintimeQS = null;
    String endtimeQS = null;
    Date begin_time = null;
    Date end_time = null;

    if (stringUtils.isNotBlank(begintime)) {
      begintimeQS = begintime + " 00:00:00";
      try {
        begin_time = format.parse(begintimeQS);
        query.setBegin_time1(begin_time);
      } catch (ParseException e) {
        model.addAttribute("msg", "开始日期格式错误！");
        begintime = null;
      }
    }
    if (stringUtils.isNotBlank(endtime)) {
      endtimeQS = endtime + " 23:59:59";
      try {
        end_time = format.parse(endtimeQS);
        query.setEnd_time1(end_time);
      } catch (ParseException e) {
        model.addAttribute("msg", "结束日期格式错误！");
        endtime = null;
      }
    }
    List<Portalcard> cards = this.portalcardService.getPortalcardSaleList(query);
    for (Portalcard card : cards) {
      money = Double.valueOf(money.doubleValue() + card.getMoney().doubleValue());
    }
    String moneyS = df.format(money);
    if (moneyS.startsWith(".")) {
      moneyS = "0" + moneyS;
    }

    String cfgPath = request.getServletContext().getRealPath("/");
    Date now = new Date();
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
    String nowString = format.format(now);
    File dir = new File(cfgPath + "ExcelOut/");
    if (!dir.exists()) {
      dir.mkdirs();
    }
    String fileName = cfgPath + "ExcelOut/Cards_" + nowString + ".xls";
    try {
      ExcelUtils.writeCardsToExcel(fileName, cards, moneyS);
    }
    catch (Exception e) {
      model.addAttribute("msg", "文件创建失败！");
      model.addAttribute("downUrl", null);
      model.addAttribute("err", Integer.valueOf(1));
      return "portalmoney/outResult";
    }
    model.addAttribute("msg", "文件创建成功！");
    model.addAttribute("downUrl", "Cards_" + nowString + ".xls");
    model.addAttribute("creatDate", nowString);
    model.addAttribute("err", Integer.valueOf(0));
    return "portalmoney/outResult";
  }

  @RequestMapping({"utOrder.action"})
  public String outOrder(PortalorderQuery query, String begintime, String endtime, HttpServletRequest request, HttpServletResponse response, ModelMap model)
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
    if (stringUtils.isBlank(query.getBuyer())) {
      query.setBuyer(null);
    }
    if (stringUtils.isBlank(query.getSeller())) {
      query.setSeller(null);
    }

    query.setState("1");
    query.setStateLike(false);
    query.orderbyBuyDate(true);
    query.orderbyPayDate(true);

    Double money = Double.valueOf(0.0D);
    String begintimeQS = null;
    String endtimeQS = null;
    Date begin_time = null;
    Date end_time = null;

    if (stringUtils.isNotBlank(begintime)) {
      begintimeQS = begintime + " 00:00:00";
      try {
        begin_time = format.parse(begintimeQS);
        query.setBegin_time1(begin_time);
      } catch (ParseException e) {
        model.addAttribute("msg", "开始日期格式错误！");
        begintime = null;
      }
    }
    if (stringUtils.isNotBlank(endtime)) {
      endtimeQS = endtime + " 23:59:59";
      try {
        end_time = format.parse(endtimeQS);
        query.setEnd_time1(end_time);
      } catch (ParseException e) {
        model.addAttribute("msg", "结束日期格式错误！");
        endtime = null;
      }
    }
    List<Portalorder> orders = this.portalorderService.getPortalorderList(query);
    for (Portalorder order : orders) {
      money = Double.valueOf(money.doubleValue() + order.getMoney().doubleValue());
    }
    String moneyS = df.format(money);
    if (moneyS.startsWith(".")) {
      moneyS = "0" + moneyS;
    }

    String cfgPath = request.getServletContext().getRealPath("/");
    Date now = new Date();
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
    String nowString = format.format(now);
    File dir = new File(cfgPath + "ExcelOut/");
    if (!dir.exists()) {
      dir.mkdirs();
    }
    String fileName = cfgPath + "ExcelOut/Orders_" + nowString + ".xls";
    try {
      ExcelUtils.writeOrdersToExcel(fileName, orders, moneyS);
    }
    catch (Exception e) {
      model.addAttribute("msg", "文件创建失败！");
      model.addAttribute("downUrl", null);
      model.addAttribute("err", Integer.valueOf(1));
      return "portalmoney/outResult";
    }
    model.addAttribute("msg", "文件创建成功！");
    model.addAttribute("downUrl", "Orders_" + nowString + ".xls");
    model.addAttribute("creatDate", nowString);
    model.addAttribute("err", Integer.valueOf(0));
    return "portalmoney/outResult";
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.PortalmoneyController
 * JD-Core Version:    0.6.2
 */