package com.leeson.core.utils;

import com.leeson.core.bean.Portalprivilege;
import com.leeson.core.query.PortalprivilegeQuery;
import com.leeson.core.service.PortalprivilegeService;
import com.leeson.portal.core.utils.SpringContextHelper;
import java.io.PrintStream;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class InitListener
  implements ServletContextListener
{
  public void contextInitialized(ServletContextEvent sce)
  {
    PortalprivilegeService privilegeService = (PortalprivilegeService)SpringContextHelper.getBean("portalprivilegeServiceImpl");
    initData(sce.getServletContext(), privilegeService);
  }

  public static void initData(ServletContext ce, PortalprivilegeService privilegeService)
  {
    Map topPrivilegeList = new LinkedHashMap();
    PortalprivilegeQuery allpq = new PortalprivilegeQuery();
    allpq.orderbyPosition(true);
    List<Portalprivilege> all = privilegeService.getPortalprivilegeList(allpq);
    for (Portalprivilege p : all) {
      if (p.getParentId() == null) {
        PortalprivilegeQuery pq = new PortalprivilegeQuery();
        pq.setParentId(p.getId());
        pq.orderbyPosition(true);
        List<Portalprivilege> children = privilegeService.getPortalprivilegeList(pq);
        Map childrenMap = new LinkedHashMap();
        for (Portalprivilege portalprivilege : children) {
          childrenMap.put(portalprivilege.getUrl(), portalprivilege.getName());
        }
        topPrivilegeList.put(p.getName(), childrenMap);
      }

    }

    ce.setAttribute("topPrivilegeList", topPrivilegeList);
    System.out.println("------------> Ready Menu topPrivilegeList Data <------------");

    Collection allPrivilegeUrls = privilegeService.getAllPrivilegeUrls();
    ce.setAttribute("allPrivilegeUrls", allPrivilegeUrls);
    System.out.println("------------> Ready PrivilegeUrls Data <------------");

    List allprivileges = privilegeService.getPortalprivilegeList(allpq);
    List topPrivileges = privilegeService.findTopList();
    List choosePrivileges = PrivilegeUtils.getAllDepartments(allprivileges);
    ce.setAttribute("allprivileges", allprivileges);
    System.out.println("------------> Ready allprivileges Data <------------");
    ce.setAttribute("topPrivileges", topPrivileges);
    System.out.println("------------> Ready topPrivileges Data <------------");
    ce.setAttribute("choosePrivileges", choosePrivileges);
    System.out.println("------------> Ready choosePrivileges Data <------------");
  }

  public void contextDestroyed(ServletContextEvent arg0)
  {
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.utils.InitListener
 * JD-Core Version:    0.6.2
 */