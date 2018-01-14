/*    */ package com.leeson.core.utils;
/*    */ 
/*    */ import com.chinamobile.openmas.client.Sms;
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
/*    */ public class SMSOpenMasRequest
/*    */ {
/* 16 */   private static Config config = Config.getInstance();
/* 17 */   private static Logger logger = Logger.getLogger(SMSOpenMasRequest.class);
/*    */ 
/* 19 */   private static PortalsmslogsService smslogsService = (PortalsmslogsService)
/* 20 */     SpringContextHelper.getBean("portalsmslogsServiceImpl");
/*    */ 
/*    */   public static boolean send(int type, String url, String key, String secret, String template, String text, String sign, String company, String phone, int time, Long id)
/*    */   {
/*    */     try
/*    */     {
/* 27 */       String yzm = 
/* 28 */         String.valueOf(Math.round(Math.random() * 8999.0D + 1000.0D));
/* 29 */       if (type == 1) {
/* 30 */         if (stringUtils.isNotBlank(text)) {
/* 31 */           if ("[yzm]".equals(text)) {
/* 32 */             text = "【" + sign + "】帐号注册验证码是" + yzm + " 有效期" + time + 
/* 33 */               "分钟! 【" + company + "】提供!";
/*    */           }
/* 35 */           else if (text.contains("[yzm]"))
/* 36 */             text = text.replace("[yzm]", yzm);
/*    */           else {
/* 38 */             text = "【" + sign + "】帐号注册验证码是" + yzm + " 有效期" + time + 
/* 39 */               "分钟! 【" + company + "】提供!";
/*    */           }
/*    */         }
/*    */         else {
/* 43 */           text = "【" + sign + "】帐号注册验证码是" + yzm + " 有效期" + time + 
/* 44 */             "分钟! 【" + company + "】提供!";
/*    */         }
/*    */       }
/* 47 */       else if (stringUtils.isNotBlank(text)) {
/* 48 */         if ("[yzm]".equals(text)) {
/* 49 */           text = "【" + sign + "】上网验证码是" + yzm + " 有效期" + time + 
/* 50 */             "分钟! 【" + company + "】提供!";
/*    */         }
/* 52 */         else if (text.contains("[yzm]"))
/* 53 */           text = text.replace("[yzm]", yzm);
/*    */         else {
/* 55 */           text = "【" + sign + "】上网验证码是" + yzm + " 有效期" + time + 
/* 56 */             "分钟! 【" + company + "】提供!";
/*    */         }
/*    */       }
/*    */       else {
/* 60 */         text = "【" + sign + "】上网验证码是" + yzm + " 有效期" + time + 
/* 61 */           "分钟! 【" + company + "】提供!";
/*    */       }
/*    */ 
/* 65 */       Object[] objs = new Object[2];
/* 66 */       objs[0] = yzm;
/* 67 */       objs[1] = new Date();
/*    */ 
/* 69 */       Sms sms = new Sms(url);
/* 70 */       String[] destinationAddresses = { phone };
/* 71 */       String result = sms.SendMessage(destinationAddresses, text, 
/* 72 */         template, key, secret);
/* 73 */       if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
/* 74 */         logger.info("SMS Send Result : " + result);
/*    */       }
/*    */ 
/* 77 */       PhoneCodeMap.getInstance().getPhoneCodeMap().put(phone, objs);
/* 78 */       Portalsmslogs smslogs = new Portalsmslogs();
/* 79 */       smslogs.setInfo(text);
/* 80 */       smslogs.setPhone(phone);
/* 81 */       smslogs.setSendDate(new Date());
/* 82 */       smslogs.setSid(id);
/* 83 */       smslogs.setType("6");
/* 84 */       smslogsService.addPortalsmslogs(smslogs);
/* 85 */       return true;
/*    */     } catch (Exception e) {
/* 87 */       if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
/* 88 */         logger.error("==============ERROR Start=============");
/* 89 */         logger.error(e);
/* 90 */         logger.error("ERROR INFO ", e);
/* 91 */         logger.error("==============ERROR End=============");
/*    */       }
/*    */     }
/* 93 */     return false;
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.utils.SMSOpenMasRequest
 * JD-Core Version:    0.6.2
 */