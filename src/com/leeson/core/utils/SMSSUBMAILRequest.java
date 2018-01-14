/*    */ package com.leeson.core.utils;
/*    */ 
/*    */ import com.leeson.common.utils.stringUtils;
/*    */ import com.leeson.core.bean.Portalbas;
/*    */ import com.leeson.core.bean.Portalsmslogs;
/*    */ import com.leeson.core.service.PortalsmslogsService;
/*    */ import com.leeson.portal.core.model.Config;
/*    */ import com.leeson.portal.core.model.PhoneCodeMap;
/*    */ import com.leeson.portal.core.utils.SpringContextHelper;
/*    */ import config.AppConfig;
/*    */ import java.util.Date;
/*    */ import java.util.Map;
/*    */ import lib.MESSAGEXsend;
/*    */ import org.apache.log4j.Logger;
/*    */ import utils.ConfigLoader;
/*    */ 
/*    */ public class SMSSUBMAILRequest
/*    */ {
/* 21 */   private static Config config = Config.getInstance();
/* 22 */   private static Logger logger = Logger.getLogger(SMSSUBMAILRequest.class);
/*    */ 
/* 24 */   private static PortalsmslogsService smslogsService = (PortalsmslogsService)
/* 25 */     SpringContextHelper.getBean("portalsmslogsServiceImpl");
/*    */ 
/*    */   public static boolean send(String appId, String appKey, String sign, String template, String phone, Long id)
/*    */   {
/*    */     try
/*    */     {
/* 30 */       String text = String.valueOf(Math.round(Math.random() * 8999.0D + 1000.0D));
/* 31 */       Object[] objs = new Object[2];
/* 32 */       objs[0] = text;
/* 33 */       objs[1] = new Date();
/*    */ 
/* 35 */       if (SubMail_SendSMS(appId, appKey, sign, template, phone, text)) {
/* 36 */         PhoneCodeMap.getInstance().getPhoneCodeMap().put(phone, objs);
/*    */ 
/* 38 */         Portalsmslogs smslogs = new Portalsmslogs();
/* 39 */         smslogs.setInfo(text);
/* 40 */         smslogs.setPhone(phone);
/* 41 */         smslogs.setSendDate(new Date());
/* 42 */         smslogs.setSid(id);
/* 43 */         smslogs.setType("7");
/* 44 */         smslogsService.addPortalsmslogs(smslogs);
/* 45 */         return true;
/*    */       }
/* 47 */       return false;
/*    */     }
/*    */     catch (Exception e) {
/* 50 */       if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
/* 51 */         logger.error("==============ERROR Start=============");
/* 52 */         logger.error(e);
/* 53 */         logger.error("ERROR INFO ", e);
/* 54 */         logger.error("==============ERROR End=============");
/*    */       }
/*    */     }
/* 56 */     return false;
/*    */   }
/*    */ 
/*    */   private static boolean SubMail_SendSMS(String appId, String appKey, String sign, String template, String phone, String text)
/*    */   {
/*    */     try
/*    */     {
/* 63 */       if (stringUtils.isBlank(sign)) {
/* 64 */         sign = "code";
/*    */       }
/* 66 */       AppConfig config = ConfigLoader.load(appId, appKey);
/* 67 */       MESSAGEXsend submail = new MESSAGEXsend(config);
/* 68 */       submail.addTo(phone);
/* 69 */       submail.setProject(template);
/* 70 */       submail.addVar(sign, text);
/* 71 */       return submail.xsend();
/*    */     } catch (Exception e) {
/* 73 */       if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
/* 74 */         logger.error("==============ERROR Start=============");
/* 75 */         logger.error(e);
/* 76 */         logger.error("ERROR INFO ", e);
/* 77 */         logger.error("==============ERROR End=============");
/*    */       }
/*    */     }
/* 79 */     return false;
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.utils.SMSSUBMAILRequest
 * JD-Core Version:    0.6.2
 */