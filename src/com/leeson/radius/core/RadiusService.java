/*    */ package com.leeson.radius.core;
/*    */ 
/*    */ import com.leeson.core.bean.Portalbas;
/*    */ import com.leeson.core.bean.Radiusnas;
/*    */ import com.leeson.core.query.RadiusnasQuery;
/*    */ import com.leeson.core.service.RadiusnasService;
/*    */ import com.leeson.portal.core.model.Config;
/*    */ import com.leeson.portal.core.utils.SpringContextHelper;
/*    */ import com.leeson.radius.core.model.RadiusNasMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import javax.servlet.ServletContextEvent;
/*    */ import javax.servlet.ServletContextListener;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class RadiusService
/*    */   implements ServletContextListener
/*    */ {
/* 24 */   private static Config config = Config.getInstance();
/* 25 */   private static Logger log = Logger.getLogger(RadiusService.class);
/*    */   private RadiusnasService radiusnasService;
/*    */ 
/*    */   public void contextDestroyed(ServletContextEvent arg0)
/*    */   {
/* 31 */     Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
/* 32 */     if (basConfig.getIsdebug().equals("1"))
/* 33 */       log.info("Radius Service Stop");
/*    */   }
/*    */ 
/*    */   public void contextInitialized(ServletContextEvent servletContextEvent)
/*    */   {
/* 41 */     this.radiusnasService = ((RadiusnasService)SpringContextHelper.getBean("radiusnasServiceImpl"));
/*    */ 
/* 43 */     List<Radiusnas> es = this.radiusnasService.getRadiusnasList(new RadiusnasQuery());
/* 44 */     for (Radiusnas e : es) {
/* 45 */       String[] client = new String[9];
/* 46 */       client[0] = Tool.ByteToHex(e.getSharedSecret().getBytes());
/* 47 */       client[1] = e.getType();
/* 48 */       client[2] = e.getEx1();
/* 49 */       client[3] = e.getEx2();
/* 50 */       client[4] = e.getEx3();
/* 51 */       client[5] = e.getEx4();
/* 52 */       client[6] = e.getEx5();
/* 53 */       client[7] = e.getName();
/* 54 */       client[8] = e.getEx6();
/* 55 */       RadiusNasMap.getInstance().getNasMap().put(e.getIp(), client);
/*    */     }
/*    */ 
/* 60 */     new Thread()
/*    */     {
/*    */       public void run()
/*    */       {
/*    */         try {
/* 65 */           RadiusMain.radiusServer();
/*    */         }
/*    */         catch (Exception e) {
/* 68 */           RadiusService.log.error("Radius Service Start Error !!");
/* 69 */           Tool.writeErrorLog("Error", e);
/*    */         }
/*    */       }
/*    */     }
/* 73 */     .start();
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.radius.core.RadiusService
 * JD-Core Version:    0.6.2
 */