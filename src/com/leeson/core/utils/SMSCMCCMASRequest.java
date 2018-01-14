/*     */ package com.leeson.core.utils;
/*     */ 
/*     */ import com.leeson.common.utils.stringUtils;
/*     */ import com.leeson.core.bean.Portalbas;
/*     */ import com.leeson.core.bean.Portalsmslogs;
/*     */ import com.leeson.core.service.PortalsmslogsService;
/*     */ import com.leeson.portal.core.model.Config;
/*     */ import com.leeson.portal.core.model.PhoneCodeMap;
/*     */ import com.leeson.portal.core.utils.SpringContextHelper;
/*     */ import com.mascloud.sdkclient.Client;
/*     */ import java.util.Date;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class SMSCMCCMASRequest
/*     */ {
/*  17 */   private static Config config = Config.getInstance();
/*  18 */   private static Logger logger = Logger.getLogger(SMSCMCCMASRequest.class);
/*  19 */   private static boolean isLogin = false;
/*  20 */   private static final Client client = Client.getInstance();
/*  21 */   private static PortalsmslogsService smslogsService = (PortalsmslogsService)
/*  22 */     SpringContextHelper.getBean("portalsmslogsServiceImpl");
/*     */ 
/*     */   public static boolean send(int type, String url, String key, String secret, String company, String template, String text, String sign, String phone, int time, Long id)
/*     */   {
/*     */     try
/*     */     {
/*  29 */       String yzm = 
/*  30 */         String.valueOf(Math.round(Math.random() * 8999.0D + 1000.0D));
/*     */ 
/*  32 */       if (stringUtils.isNotBlank(text)) {
/*  33 */         if ("[yzm]".equals(text)) {
/*  34 */           text = yzm;
/*     */         }
/*  36 */         else if (text.contains("[yzm]"))
/*  37 */           text = text.replace("[yzm]", yzm);
/*     */         else {
/*  39 */           text = yzm;
/*     */         }
/*     */       }
/*     */       else {
/*  43 */         text = yzm;
/*     */       }
/*     */ 
/*  46 */       Object[] objs = new Object[2];
/*  47 */       objs[0] = yzm;
/*  48 */       objs[1] = new Date();
/*     */ 
/*  50 */       boolean sendResult = false;
/*  51 */       if (stringUtils.isEmpty(template))
/*  52 */         sendResult = doSend(url, key, secret, company, sign, phone, text);
/*     */       else {
/*  54 */         sendResult = doSend(url, key, secret, company, sign, phone, yzm, template);
/*     */       }
/*  56 */       if (sendResult) {
/*  57 */         PhoneCodeMap.getInstance().getPhoneCodeMap().put(phone, objs);
/*     */ 
/*  59 */         Portalsmslogs smslogs = new Portalsmslogs();
/*  60 */         smslogs.setInfo(text);
/*  61 */         smslogs.setPhone(phone);
/*  62 */         smslogs.setSendDate(new Date());
/*  63 */         smslogs.setSid(id);
/*  64 */         smslogs.setType("12");
/*  65 */         smslogsService.addPortalsmslogs(smslogs);
/*  66 */         if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
/*  67 */           logger.info("SMS Send Success");
/*     */         }
/*  69 */         return true;
/*     */       }
/*  71 */       if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
/*  72 */         logger.info("SMS Send Fail");
/*     */       }
/*  74 */       return false;
/*     */     } catch (Exception e) {
/*  76 */       if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
/*  77 */         logger.error("==============ERROR Start=============");
/*  78 */         logger.error(e);
/*  79 */         logger.error("ERROR INFO ", e);
/*  80 */         logger.error("==============ERROR End=============");
/*  81 */         logger.info("SMS Send Fail");
/*     */       }
/*     */     }
/*  83 */     return false;
/*     */   }
/*     */ 
/*     */   private static boolean doSend(String url, String key, String secret, String company, String sign, String phone, String text)
/*     */   {
/*     */     try
/*     */     {
/*  93 */       if (!isLogin) {
/*  94 */         logger.info("SMS API Login ...");
/*  95 */         if (client.login(url, key, secret, company)) {
/*  96 */           logger.info("SMS API Login Success !");
/*  97 */           isLogin = true;
/*     */         }
/*     */       }
/* 100 */       int sendResult = client.sendDSMS(new String[] { phone }, text, "", 
/* 101 */         1, sign, UUID.randomUUID().toString().replace("-", ""), 
/* 102 */         false);
/* 103 */       logger.info("SMS API Send Result= " + sendResult);
/* 104 */       if (sendResult == 1)
/* 105 */         return true;
/*     */     }
/*     */     catch (Exception localException) {
/*     */     }
/* 109 */     return false;
/*     */   }
/*     */ 
/*     */   private static boolean doSend(String url, String key, String secret, String company, String sign, String phone, String text, String template)
/*     */   {
/*     */     try
/*     */     {
/* 117 */       if (!isLogin) {
/* 118 */         logger.info("SMS API Login ...");
/* 119 */         if (client.login(url, key, secret, company)) {
/* 120 */           logger.info("SMS API Login Success !");
/* 121 */           isLogin = true;
/*     */         }
/*     */       }
/* 124 */       int sendResult = client.sendTSMS(new String[] { phone }, template, new String[] { text }, "", 1, sign, UUID.randomUUID().toString().replace("-", ""));
/* 125 */       logger.info("SMS API Send Result= " + sendResult);
/* 126 */       if (sendResult == 1)
/* 127 */         return true;
/*     */     }
/*     */     catch (Exception localException) {
/*     */     }
/* 131 */     return false;
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.utils.SMSCMCCMASRequest
 * JD-Core Version:    0.6.2
 */