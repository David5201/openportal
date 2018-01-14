/*    */ package com.leeson.radius.core.utils;
/*    */ 
/*    */ import com.leeson.common.utils.stringUtils;
/*    */ import com.leeson.core.bean.Config;
/*    */ import com.leeson.core.bean.Portalaccountmacs;
/*    */ import com.leeson.core.query.PortalaccountmacsQuery;
/*    */ import com.leeson.core.service.ConfigService;
/*    */ import com.leeson.core.service.PortalaccountmacsService;
/*    */ import com.leeson.portal.core.utils.SpringContextHelper;
/*    */ import java.util.List;
/*    */ 
/*    */ public class MacLimit
/*    */ {
/* 13 */   private static PortalaccountmacsService macsService = (PortalaccountmacsService)
/* 14 */     SpringContextHelper.getBean("portalaccountmacsServiceImpl");
/*    */ 
/* 15 */   private static ConfigService configService = (ConfigService)
/* 16 */     SpringContextHelper.getBean("configServiceImpl");
/*    */ 
/*    */   public static boolean macLimit(Long id, String username, String userMac, int limitMac, int limitCount)
/*    */   {
/* 20 */     if ((stringUtils.isNotBlank(userMac)) && 
/* 21 */       (1 == configService.getConfigByKey(Long.valueOf(1L)).getRadiusOn().intValue()) && 
/* 22 */       (limitMac == 1)) {
/* 23 */       PortalaccountmacsQuery mq = new PortalaccountmacsQuery();
/* 24 */       mq.setAccountId(id);
/* 25 */       List<Portalaccountmacs> accountmacs = macsService
/* 26 */         .getPortalaccountmacsList(mq);
/* 27 */       if ((accountmacs.size() < 1) || (limitCount == 0) || 
/* 28 */         (accountmacs.size() < limitCount)) {
/* 29 */         Boolean isHave = Boolean.valueOf(false);
/* 30 */         for (Portalaccountmacs amacs : accountmacs) {
/* 31 */           if (amacs.getMac().toLowerCase().equals(userMac)) {
/* 32 */             isHave = Boolean.valueOf(true);
/* 33 */             break;
/*    */           }
/*    */         }
/* 36 */         if (!isHave.booleanValue()) {
/* 37 */           Portalaccountmacs mac = new Portalaccountmacs();
/* 38 */           mac.setAccountId(id);
/* 39 */           mac.setMac(userMac);
/* 40 */           macsService.addPortalaccountmacs(mac);
/*    */         }
/*    */       } else {
/* 43 */         Boolean isHave = Boolean.valueOf(false);
/* 44 */         for (Portalaccountmacs amacs : accountmacs) {
/* 45 */           if (amacs.getMac().toLowerCase().equals(userMac)) {
/* 46 */             isHave = Boolean.valueOf(true);
/* 47 */             break;
/*    */           }
/*    */         }
/* 50 */         if (!isHave.booleanValue()) {
/* 51 */           return false;
/*    */         }
/*    */       }
/*    */ 
/*    */     }
/*    */ 
/* 57 */     return true;
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.radius.core.utils.MacLimit
 * JD-Core Version:    0.6.2
 */