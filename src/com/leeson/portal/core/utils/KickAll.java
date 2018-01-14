/*    */ package com.leeson.portal.core.utils;
/*    */ 
/*    */ import com.leeson.common.utils.stringUtils;
/*    */ import com.leeson.core.bean.Portalbas;
/*    */ import com.leeson.core.controller.AjaxInterFaceController;
/*    */ import com.leeson.portal.core.model.Config;
/*    */ import com.leeson.portal.core.model.OnlineMap;
/*    */ import com.leeson.portal.core.model.UniFiMacSiteMap;
/*    */ import com.leeson.portal.core.service.InterfaceControl;
/*    */ import com.leeson.portal.core.service.action.unifi.UniFiMethod;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class KickAll
/*    */ {
/* 15 */   private static Config configDefault = Config.getInstance();
/* 16 */   private static OnlineMap onlineMap = OnlineMap.getInstance();
/*    */ 
/*    */   public static void kickUser(String ip)
/*    */   {
/* 23 */     int i = ip.lastIndexOf(":");
/* 24 */     String ubip = ip.substring(i + 1);
/* 25 */     String uip = ip.substring(0, i);
/*    */ 
/* 27 */     Portalbas config = (Portalbas)configDefault.getConfigMap().get(ubip);
/* 28 */     if (config == null) {
/* 29 */       String[] loginInfo = null;
/* 30 */       loginInfo = (String[])onlineMap.getOnlineUserMap().get(ip);
/* 31 */       if (loginInfo != null) {
/* 32 */         String username = loginInfo[0];
/* 33 */         onlineMap.getOnlineUserMap().remove(ip);
/* 34 */         AjaxInterFaceController.SangforLogout(uip, username);
/*    */       }
/* 36 */       return;
/*    */     }
/* 38 */     String[] loginInfo = null;
/* 39 */     loginInfo = (String[])onlineMap.getOnlineUserMap().get(ip);
/* 40 */     if (loginInfo != null) {
/* 41 */       String username = loginInfo[0];
/* 42 */       String mac = loginInfo[4];
/* 43 */       if (config.getBas().equals("3")) {
/* 44 */         if (stringUtils.isNotBlank(mac)) {
/* 45 */           String site = 
/* 46 */             (String)UniFiMacSiteMap.getInstance().getMacSiteMap()
/* 46 */             .get(mac);
/* 47 */           UniFiMethod.sendUnauthorization(mac, site, ubip);
/*    */         }
/* 49 */       } else if ((!config.getBas().equals("5")) && 
/* 50 */         (!config.getBas().equals("6")) && 
/* 51 */         (!config.getBas().equals("7")) && 
/* 52 */         (!config
/* 52 */         .getBas().equals("8"))) {
/* 53 */         InterfaceControl.Method("PORTAL_LOGINOUT", username, 
/* 54 */           "password", uip, ubip, mac);
/*    */       }
/* 56 */       onlineMap.getOnlineUserMap().remove(ip);
/* 57 */       AjaxInterFaceController.SangforLogout(uip, username);
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.utils.KickAll
 * JD-Core Version:    0.6.2
 */