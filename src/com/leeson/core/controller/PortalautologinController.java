package com.leeson.core.controller;

import com.leeson.core.bean.Portalautologin;
import com.leeson.core.bean.Portalbas;
import com.leeson.core.query.PortalautologinQuery;
import com.leeson.core.service.PortalautologinService;
import com.leeson.portal.core.model.AutoLoginCheckTimeMap;
import com.leeson.portal.core.model.Config;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PortalautologinController
{
  private static Config config = Config.getInstance();

  private static Logger logger = Logger.getLogger(PortalautologinController.class);

  @Autowired
  private PortalautologinService portalautologinService;

  @RequestMapping({"how.action"})
  public String show(ModelMap model) {
    List eAll = this.portalautologinService
      .getPortalautologinList(new PortalautologinQuery());

    model.addAttribute("pagination", eAll);
    Long time = 
      (Long)AutoLoginCheckTimeMap.getInstance()
      .getAutoLoginCheckTimeMap().get("time");
    model.addAttribute("time", time);
    return "portalautologin/list";
  }

  @RequestMapping({"dit.action"})
  public String edit(@RequestParam Long id, ModelMap model)
  {
    Portalautologin e = this.portalautologinService.getPortalautologinByKey(id);
    if (e.getState().intValue() == 0)
      e.setState(Integer.valueOf(1));
    else {
      e.setState(Integer.valueOf(0));
    }
    this.portalautologinService.updatePortalautologinByKey(e);
    return "redirect:how.action";
  }

  @RequestMapping({"ave.action"})
  public String save(@RequestParam Long id, ModelMap model)
  {
    Portalautologin e = this.portalautologinService.getPortalautologinByKey(id);
    int time = (int)(e.getTime().longValue() / 60000L);
    model.addAttribute("entity", e);
    model.addAttribute("time", Integer.valueOf(time));
    return "portalautologin/config";
  }

  @RequestMapping(value={"ave.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String save(Portalautologin e, ModelMap model)
  {
    e.setTime(Long.valueOf(e.getTime().longValue() * 60000L));
    this.portalautologinService.updatePortalautologinByKey(e);
    List es = this.portalautologinService
      .getPortalautologinList(new PortalautologinQuery());
    model.addAttribute("pagination", es);
    Long time = 
      (Long)AutoLoginCheckTimeMap.getInstance()
      .getAutoLoginCheckTimeMap().get("time");
    model.addAttribute("time", time);
    model.addAttribute("msg", "保存成功！！");
    return "portalautologin/list";
  }

  @RequestMapping(value={"imeset.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String timeset(Long time, ModelMap model, HttpServletRequest request)
  {
    if ((time == null) || (10L > time.longValue())) {
      time = Long.valueOf(10L);
    }
    AutoLoginCheckTimeMap.getInstance().getAutoLoginCheckTimeMap()
      .put("time", time);

    AutoLoginCheckTimeMapToDisk(request);

    model.addAttribute("time", time);
    model.addAttribute("msg", "设置成功！！");

    List es = this.portalautologinService
      .getPortalautologinList(new PortalautologinQuery());
    model.addAttribute("pagination", es);
    return "portalautologin/list";
  }

  private void AutoLoginCheckTimeMapToDisk(HttpServletRequest request)
  {
    Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
    ObjectOutputStream os = null;
    try {
      os = new ObjectOutputStream(new FileOutputStream(
        request.getServletContext().getRealPath("/") + 
        "/AutoLoginCheckTimeMap.dat"));
      os.writeObject(AutoLoginCheckTimeMap.getInstance().getAutoLoginCheckTimeMap());
      if (basConfig.getIsdebug().equals("1"))
        logger.info("AutoLoginCheckTimeMapToDisk !!");
    }
    catch (Exception e) {
      logger.error("==============ERROR Start=============");
      logger.error(e);
      logger.error("ERROR INFO ", e);
      logger.error("==============ERROR End=============");
      try
      {
        if (os != null)
          os.close();
      }
      catch (IOException e1) {
        logger.error("==============ERROR Start=============");
        logger.error(e1);
        logger.error("ERROR INFO ", e1);
        logger.error("==============ERROR End=============");
      }
    }
    finally
    {
      try
      {
        if (os != null)
          os.close();
      }
      catch (IOException e) {
        logger.error("==============ERROR Start=============");
        logger.error(e);
        logger.error("ERROR INFO ", e);
        logger.error("==============ERROR End=============");
      }
    }
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.PortalautologinController
 * JD-Core Version:    0.6.2
 */