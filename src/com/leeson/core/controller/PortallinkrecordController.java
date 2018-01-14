package com.leeson.core.controller;

import com.leeson.common.page.Pagination;
import com.leeson.common.utils.stringUtils;
import com.leeson.core.bean.Portallinkrecord;
import com.leeson.core.query.PortallinkrecordQuery;
import com.leeson.core.service.PortallinkrecordService;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PortallinkrecordController
{

  @Autowired
  private PortallinkrecordService portallinkrecordService;

  @RequestMapping({"ist.action"})
  public String page(PortallinkrecordQuery query, ModelMap model)
  {
    query.setLoginNameLike(true);
    query.setIpLike(true);
    query.orderbyId(false);
    query.setUserDel(Integer.valueOf(0));
    if (stringUtils.isBlank(query.getIp())) {
      query.setIp(null);
    }
    if (stringUtils.isBlank(query.getLoginName())) {
      query.setLoginName(null);
    }
    if (stringUtils.isBlank(query.getState())) {
      query.setState(null);
    }
    if (stringUtils.isBlank(query.getEx1())) {
      query.setEx1(null);
    }

    Pagination pagination = this.portallinkrecordService.getPortallinkrecordListWithPage(query);
    model.addAttribute("pagination", pagination);
    model.addAttribute("query", query);
    return "portallinkrecord/list";
  }

  @RequestMapping({"elete.action"})
  public String delete(@RequestParam Long id)
  {
    Portallinkrecord lr = new Portallinkrecord();
    lr.setId(id);
    lr.setUserDel(Integer.valueOf(1));
    this.portallinkrecordService.updatePortallinkrecordByKey(lr);
    return "redirect:ist.action";
  }

  @RequestMapping(value={"eletes.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String deletes(@RequestParam Long[] ids)
  {
    List<Long> list = Arrays.asList(ids);
    for (Long id : list) {
      Portallinkrecord lr = new Portallinkrecord();
      lr.setId(id);
      lr.setUserDel(Integer.valueOf(1));
      this.portallinkrecordService.updatePortallinkrecordByKey(lr);
    }
    return "redirect:ist.action";
  }

  @RequestMapping(value={"eletes.action"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  public String deletes()
  {
    this.portallinkrecordService.deleteAll();

    return "redirect:ist.action";
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.PortallinkrecordController
 * JD-Core Version:    0.6.2
 */