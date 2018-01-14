package com.leeson.core.controller;

import com.leeson.common.page.Pagination;
import com.leeson.core.bean.Historyonline;
import com.leeson.core.query.HistoryonlineQuery;
import com.leeson.core.service.HistoryonlineService;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HistoryonlineController
{

  @Autowired
  private HistoryonlineService historyonlineService;

  @RequestMapping({"ist.action"})
  public String page(HistoryonlineQuery query, HttpServletRequest request, HttpServletResponse response, ModelMap model)
  {
    Pagination pagination = this.historyonlineService
      .getHistoryonlineListWithPage(query);
    model.addAttribute("pagination", pagination);
    model.addAttribute("query", query);
    return "historyonline/list";
  }

  @RequestMapping({"elete.action"})
  public String delete(@RequestParam Long id)
  {
    this.historyonlineService.deleteByKey(id);
    return "redirect:ist.action";
  }

  @RequestMapping(value={"eletes.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String deletes(@RequestParam Long[] ids)
  {
    List list = Arrays.asList(ids);
    this.historyonlineService.deleteByKeys(list);

    return "redirect:ist.action";
  }

  @RequestMapping(value={"eletes.action"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  public String deletes()
  {
    this.historyonlineService.deleteAll();
    return "redirect:ist.action";
  }

  @RequestMapping({"ear.action"})
  public String year(HttpServletRequest request, HttpServletResponse response, ModelMap model)
  {
    List list = new ArrayList();
    Date now = new Date();
    int year = now.getYear() + 1900;
    int yearO = year - 9;
    while (yearO <= year) {
      HistoryonlineQuery q = new HistoryonlineQuery();
      q.setRecYear(Integer.valueOf(yearO));
      List<Historyonline> hs = this.historyonlineService
        .getHistoryonlineList(q);
      long counts = 0L;
      if (hs.size() != 0) {
        for (Historyonline h : hs) {
          counts += h.getCounts().longValue();
        }
        counts /= hs.size();
      }
      OnlineCounts e = new OnlineCounts();
      e.setId(Integer.valueOf(yearO));
      e.setCount(Long.valueOf(counts));
      list.add(e);
      yearO++;
    }
    model.addAttribute("e", list);
    model.addAttribute("tag", "year");
    return "homeAction/onlineLogs";
  }

  @RequestMapping({"onth.action"})
  public String month(HttpServletRequest request, HttpServletResponse response, ModelMap model)
  {
    List list = new ArrayList();
    int monthO = 1;
    int month = 12;
    while (monthO <= month) {
      HistoryonlineQuery q = new HistoryonlineQuery();
      q.setRecMonth(Integer.valueOf(monthO));
      List<Historyonline> hs = this.historyonlineService
        .getHistoryonlineList(q);
      long counts = 0L;
      if (hs.size() != 0) {
        for (Historyonline h : hs) {
          counts += h.getCounts().longValue();
        }
        counts /= hs.size();
      }
      OnlineCounts e = new OnlineCounts();
      e.setId(Integer.valueOf(monthO));
      e.setCount(Long.valueOf(counts));
      list.add(e);
      monthO++;
    }
    model.addAttribute("e", list);
    model.addAttribute("tag", "month");
    return "homeAction/onlineLogs";
  }

  @RequestMapping({"onthO.action"})
  public String monthO(HttpServletRequest request, HttpServletResponse response, ModelMap model)
  {
    List list = new ArrayList();
    int dayO = 0;
    int day = 12;
    while (dayO <= day) {
      Calendar cal = Calendar.getInstance();
      cal.add(2, -dayO);
      String old = new SimpleDateFormat("yyyy-MM-dd ").format(cal.getTime());
      System.out.println(old);
      Date thisDate = cal.getTime();

      HistoryonlineQuery q = new HistoryonlineQuery();
      q.setRecMonth(Integer.valueOf(thisDate.getMonth() + 1));
      q.setRecYear(Integer.valueOf(thisDate.getYear() + 1900));
      List<Historyonline> hs = this.historyonlineService
        .getHistoryonlineList(q);
      long counts = 0L;
      if (hs.size() != 0) {
        for (Historyonline h : hs) {
          counts += h.getCounts().longValue();
        }
        counts /= hs.size();
      }
      OnlineCounts e = new OnlineCounts();
      e.setId(Integer.valueOf(thisDate.getMonth() + 1));
      e.setCount(Long.valueOf(counts));
      list.add(e);
      dayO++;
    }

    int a = list.size();
    List listOK = new ArrayList();
    for (int i = a - 1; i >= 0; i--) {
      listOK.add((OnlineCounts)list.get(i));
    }
    model.addAttribute("e", listOK);
    model.addAttribute("tag", "monthO");
    return "homeAction/onlineLogs";
  }

  @RequestMapping({"eek.action"})
  public String week(HttpServletRequest request, HttpServletResponse response, ModelMap model)
  {
    List list = new ArrayList();
    int weekO = 1;
    int week = 7;
    while (weekO <= week) {
      HistoryonlineQuery q = new HistoryonlineQuery();
      q.setRecWeek(Integer.valueOf(weekO));
      List<Historyonline> hs = this.historyonlineService
        .getHistoryonlineList(q);
      long counts = 0L;
      if (hs.size() != 0) {
        for (Historyonline h : hs) {
          counts += h.getCounts().longValue();
        }
        counts /= hs.size();
      }
      OnlineCounts e = new OnlineCounts();
      e.setId(Integer.valueOf(weekO));
      e.setCount(Long.valueOf(counts));
      list.add(e);
      weekO++;
    }
    model.addAttribute("e", list);
    model.addAttribute("tag", "week");
    return "homeAction/onlineLogs";
  }

  @RequestMapping({"eekO.action"})
  public String weekO(HttpServletRequest request, HttpServletResponse response, ModelMap model)
  {
    List list = new ArrayList();
    int dayO = 0;
    int day = 7;
    while (dayO <= day) {
      Calendar cal = Calendar.getInstance();
      cal.add(5, -dayO);
      String old = new SimpleDateFormat("yyyy-MM-dd ").format(cal.getTime());
      System.out.println(old);
      Date thisDate = cal.getTime();

      HistoryonlineQuery q = new HistoryonlineQuery();
      q.setRecDay(Integer.valueOf(thisDate.getDate()));
      q.setRecMonth(Integer.valueOf(thisDate.getMonth() + 1));
      q.setRecYear(Integer.valueOf(thisDate.getYear() + 1900));
      List<Historyonline> hs = this.historyonlineService
        .getHistoryonlineList(q);
      long counts = 0L;
      if (hs.size() != 0) {
        for (Historyonline h : hs) {
          counts += h.getCounts().longValue();
        }
        counts /= hs.size();
      }
      OnlineCounts e = new OnlineCounts();
      int week = thisDate.getDay();
      if (week == 0) {
        week = 7;
      }
      e.setId(Integer.valueOf(week));
      e.setCount(Long.valueOf(counts));
      list.add(e);
      dayO++;
    }

    int a = list.size();
    List listOK = new ArrayList();
    for (int i = a - 1; i >= 0; i--) {
      listOK.add((OnlineCounts)list.get(i));
    }
    model.addAttribute("e", listOK);
    model.addAttribute("tag", "weekO");
    return "homeAction/onlineLogs";
  }

  @RequestMapping({"ay.action"})
  public String day(HttpServletRequest request, HttpServletResponse response, ModelMap model)
  {
    List list = new ArrayList();
    int dayO = 1;
    int day = 31;
    while (dayO <= day) {
      HistoryonlineQuery q = new HistoryonlineQuery();
      q.setRecDay(Integer.valueOf(dayO));
      List<Historyonline> hs = this.historyonlineService
        .getHistoryonlineList(q);
      long counts = 0L;
      if (hs.size() != 0) {
        for (Historyonline h : hs) {
          counts += h.getCounts().longValue();
        }
        counts /= hs.size();
      }
      OnlineCounts e = new OnlineCounts();
      e.setId(Integer.valueOf(dayO));
      e.setCount(Long.valueOf(counts));
      list.add(e);
      dayO++;
    }
    model.addAttribute("e", list);
    model.addAttribute("tag", "day");
    return "homeAction/onlineLogs";
  }

  @RequestMapping({"ayO.action"})
  public String dayO(HttpServletRequest request, HttpServletResponse response, ModelMap model)
  {
    List list = new ArrayList();
    int dayO = 0;
    int day = 31;
    while (dayO <= day) {
      Calendar cal = Calendar.getInstance();
      cal.add(5, -dayO);
      String old = new SimpleDateFormat("yyyy-MM-dd ").format(cal.getTime());
      System.out.println(old);
      Date thisDate = cal.getTime();

      HistoryonlineQuery q = new HistoryonlineQuery();
      q.setRecDay(Integer.valueOf(thisDate.getDate()));
      q.setRecMonth(Integer.valueOf(thisDate.getMonth() + 1));
      q.setRecYear(Integer.valueOf(thisDate.getYear() + 1900));
      List<Historyonline> hs = this.historyonlineService
        .getHistoryonlineList(q);
      long counts = 0L;
      if (hs.size() != 0) {
        for (Historyonline h : hs) {
          counts += h.getCounts().longValue();
        }
        counts /= hs.size();
      }
      OnlineCounts e = new OnlineCounts();
      e.setId(Integer.valueOf(thisDate.getDate()));
      e.setCount(Long.valueOf(counts));
      list.add(e);
      dayO++;
    }

    int a = list.size();
    List listOK = new ArrayList();
    for (int i = a - 1; i >= 0; i--) {
      listOK.add((OnlineCounts)list.get(i));
    }
    model.addAttribute("e", listOK);
    model.addAttribute("tag", "dayO");
    return "homeAction/onlineLogs";
  }

  @RequestMapping({"our.action"})
  public String hour(HttpServletRequest request, HttpServletResponse response, ModelMap model)
  {
    List list = new ArrayList();
    int hourO = 0;
    int hour = 23;
    while (hourO <= hour) {
      HistoryonlineQuery q = new HistoryonlineQuery();
      q.setRecTime(Integer.valueOf(hourO));
      List<Historyonline> hs = this.historyonlineService
        .getHistoryonlineList(q);
      long counts = 0L;
      if (hs.size() != 0) {
        for (Historyonline h : hs) {
          counts += h.getCounts().longValue();
        }
        counts /= hs.size();
      }
      OnlineCounts e = new OnlineCounts();
      e.setId(Integer.valueOf(hourO));
      e.setCount(Long.valueOf(counts));
      list.add(e);
      hourO++;
    }
    model.addAttribute("e", list);
    model.addAttribute("tag", "hour");
    return "homeAction/onlineLogs";
  }

  @RequestMapping({"ourO.action"})
  public String hourO(HttpServletRequest request, HttpServletResponse response, ModelMap model)
  {
    List list = new ArrayList();
    HistoryonlineQuery q = new HistoryonlineQuery();
    q.orderbyId(false);
    q.setPageSize(24);
    q.setPage(1);
    Pagination pagination = this.historyonlineService.getHistoryonlineListWithPage(q);
    List<Historyonline> hs = (List<Historyonline>) pagination.getList();
    if (hs.size() != 0) {
      for (Historyonline h : hs) {
        OnlineCounts e = new OnlineCounts();
        e.setId(h.getRecTime());
        e.setCount(h.getCounts());
        list.add(e);
      }
    } else {
      OnlineCounts e = new OnlineCounts();
      e.setId(Integer.valueOf(new Date().getHours()));
      e.setCount(Long.valueOf(0L));
      list.add(e);
    }

    int a = list.size();
    Object listOK = new ArrayList();
    for (int i = a - 1; i >= 0; i--) {
      ((List)listOK).add((OnlineCounts)list.get(i));
    }
    model.addAttribute("e", listOK);
    model.addAttribute("tag", "hourO");
    return "homeAction/onlineLogs";
  }

  public class OnlineCounts
  {
    Integer id;
    Long count;

    public OnlineCounts()
    {
    }

    public Integer getId()
    {
      return this.id;
    }
    public void setId(Integer id) {
      this.id = id;
    }
    public Long getCount() {
      return this.count;
    }
    public void setCount(Long count) {
      this.count = count;
    }
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.HistoryonlineController
 * JD-Core Version:    0.6.2
 */