/*    */ package com.leeson.core.utils;
/*    */ 
/*    */ import com.leeson.common.utils.stringUtils;
/*    */ import com.leeson.core.bean.Portalbas;
/*    */ import com.leeson.core.bean.Portalsmslogs;
/*    */ import com.leeson.core.service.PortalsmslogsService;
/*    */ import com.leeson.portal.core.model.Config;
/*    */ import com.leeson.portal.core.model.PhoneCodeMap;
/*    */ import com.leeson.portal.core.utils.SpringContextHelper;
/*    */ import java.util.Date;
/*    */ import java.util.Map;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class SMSIPYYRequest
/*    */ {
/* 15 */   private static Config config = Config.getInstance();
/* 16 */   private static Logger logger = Logger.getLogger(SMSIPYYRequest.class);
/*    */ 
/* 18 */   private static PortalsmslogsService smslogsService = (PortalsmslogsService)
/* 19 */     SpringContextHelper.getBean("portalsmslogsServiceImpl");
/*    */ 
/*    */   public static boolean send(String url, String account, String password, String text, String phone, Long id)
/*    */   {
/*    */     try
/*    */     {
/* 24 */       String yzm = 
/* 25 */         String.valueOf(Math.round(Math.random() * 8999.0D + 1000.0D));
/*    */ 
/* 27 */       if (stringUtils.isNotBlank(text)) {
/* 28 */         if (text.contains("[yzm]"))
/* 29 */           text = text.replace("[yzm]", yzm);
/*    */         else
/* 31 */           text = yzm;
/*    */       }
/*    */       else {
/* 34 */         text = yzm;
/*    */       }
/*    */ 
/* 37 */       Object[] objs = new Object[2];
/* 38 */       objs[0] = yzm;
/* 39 */       objs[1] = new Date();
/*    */ 
/* 41 */       String params = "action=send&userid=&account=" + account + "&password=" + password + "&mobile=" + phone + "&content=" + text + "&sendTime=&extno=";
/* 42 */       String result = HttpsUtils.httpsRequest(url, "POST", params);
/* 43 */       if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
/* 44 */         logger.info("SMS Send Result = " + result);
/*    */       }
/*    */ 
/* 47 */       if (result.contains("Success")) {
/* 48 */         PhoneCodeMap.getInstance().getPhoneCodeMap().put(phone, objs);
/*    */ 
/* 50 */         Portalsmslogs smslogs = new Portalsmslogs();
/* 51 */         smslogs.setInfo(text);
/* 52 */         smslogs.setPhone(phone);
/* 53 */         smslogs.setSendDate(new Date());
/* 54 */         smslogs.setSid(id);
/* 55 */         smslogs.setType("10");
/* 56 */         smslogsService.addPortalsmslogs(smslogs);
/* 57 */         return true;
/*    */       }
/* 59 */       return false;
/*    */     }
/*    */     catch (Exception e) {
/* 62 */       if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
/* 63 */         logger.error("==============ERROR Start=============");
/* 64 */         logger.error(e);
/* 65 */         logger.error("ERROR INFO ", e);
/* 66 */         logger.error("==============ERROR End=============");
/*    */       }
/*    */     }
/* 68 */     return false;
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.utils.SMSIPYYRequest
 * JD-Core Version:    0.6.2
 */