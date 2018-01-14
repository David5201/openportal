/*    */ package com.leeson.core.utils;
/*    */ 
/*    */ import com.leeson.core.bean.Portalprivilege;
/*    */ import com.leeson.core.query.PortalprivilegeQuery;
/*    */ import com.leeson.core.service.PortalprivilegeService;
/*    */ import com.leeson.portal.core.utils.SpringContextHelper;
/*    */ import java.io.PrintStream;
/*    */ import java.util.Collection;
/*    */ import java.util.LinkedHashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import javax.servlet.ServletContext;
/*    */ import javax.servlet.ServletContextEvent;
/*    */ import javax.servlet.ServletContextListener;
/*    */ 
/*    */ public class InitListener
/*    */   implements ServletContextListener
/*    */ {
/*    */   public void contextInitialized(ServletContextEvent sce)
/*    */   {
/* 25 */     PortalprivilegeService privilegeService = (PortalprivilegeService)SpringContextHelper.getBean("portalprivilegeServiceImpl");
/* 26 */     initData(sce.getServletContext(), privilegeService);
/*    */   }
/*    */ 
/*    */   public static void initData(ServletContext ce, PortalprivilegeService privilegeService)
/*    */   {
/* 33 */     Map topPrivilegeList = new LinkedHashMap();
/* 34 */     PortalprivilegeQuery allpq = new PortalprivilegeQuery();
/* 35 */     allpq.orderbyPosition(true);
/* 36 */     List<Portalprivilege> all = privilegeService.getPortalprivilegeList(allpq);
/* 37 */     for (Portalprivilege p : all) {
/* 38 */       if (p.getParentId() == null) {
/* 39 */         PortalprivilegeQuery pq = new PortalprivilegeQuery();
/* 40 */         pq.setParentId(p.getId());
/* 41 */         pq.orderbyPosition(true);
/* 42 */         List<Portalprivilege> children = privilegeService.getPortalprivilegeList(pq);
/* 43 */         Map childrenMap = new LinkedHashMap();
/* 44 */         for (Portalprivilege portalprivilege : children) {
/* 45 */           childrenMap.put(portalprivilege.getUrl(), portalprivilege.getName());
/*    */         }
/* 47 */         topPrivilegeList.put(p.getName(), childrenMap);
/*    */       }
/*    */ 
/*    */     }
/*    */ 
/* 52 */     ce.setAttribute("topPrivilegeList", topPrivilegeList);
/* 53 */     System.out.println("------------> Ready Menu topPrivilegeList Data <------------");
/*    */ 
/* 56 */     Collection allPrivilegeUrls = privilegeService.getAllPrivilegeUrls();
/* 57 */     ce.setAttribute("allPrivilegeUrls", allPrivilegeUrls);
/* 58 */     System.out.println("------------> Ready PrivilegeUrls Data <------------");
/*    */ 
/* 63 */     List allprivileges = privilegeService.getPortalprivilegeList(allpq);
/* 64 */     List topPrivileges = privilegeService.findTopList();
/* 65 */     List choosePrivileges = PrivilegeUtils.getAllDepartments(allprivileges);
/* 66 */     ce.setAttribute("allprivileges", allprivileges);
/* 67 */     System.out.println("------------> Ready allprivileges Data <------------");
/* 68 */     ce.setAttribute("topPrivileges", topPrivileges);
/* 69 */     System.out.println("------------> Ready topPrivileges Data <------------");
/* 70 */     ce.setAttribute("choosePrivileges", choosePrivileges);
/* 71 */     System.out.println("------------> Ready choosePrivileges Data <------------");
/*    */   }
/*    */ 
/*    */   public void contextDestroyed(ServletContextEvent arg0)
/*    */   {
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.utils.InitListener
 * JD-Core Version:    0.6.2
 */