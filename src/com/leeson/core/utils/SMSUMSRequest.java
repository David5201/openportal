/*     */ package com.leeson.core.utils;
/*     */ 
/*     */ import com.leeson.common.utils.stringUtils;
/*     */ import com.leeson.core.bean.Portalbas;
/*     */ import com.leeson.core.bean.Portalsmslogs;
/*     */ import com.leeson.core.service.PortalsmslogsService;
/*     */ import com.leeson.portal.core.model.Config;
/*     */ import com.leeson.portal.core.model.PhoneCodeMap;
/*     */ import com.leeson.portal.core.utils.SpringContextHelper;
/*     */ import java.util.Date;
/*     */ import java.util.Map;
/*     */ import org.apache.commons.httpclient.HttpClient;
/*     */ import org.apache.commons.httpclient.methods.PostMethod;
/*     */ import org.apache.commons.httpclient.params.HttpMethodParams;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class SMSUMSRequest
/*     */ {
/*  18 */   private static Config config = Config.getInstance();
/*  19 */   private static Logger logger = Logger.getLogger(SMSUMSRequest.class);
/*     */ 
/*  21 */   private static PortalsmslogsService smslogsService = (PortalsmslogsService)
/*  22 */     SpringContextHelper.getBean("portalsmslogsServiceImpl");
/*     */ 
/*     */   public static boolean send(String url, String key, String secret, String template, String text, String phone, Long id)
/*     */   {
/*     */     try
/*     */     {
/*  28 */       String yzm = 
/*  29 */         String.valueOf(Math.round(Math.random() * 8999.0D + 1000.0D));
/*     */ 
/*  31 */       if (stringUtils.isNotBlank(text)) {
/*  32 */         if (text.contains("[yzm]"))
/*  33 */           text = text.replace("[yzm]", yzm);
/*     */         else
/*  35 */           text = yzm;
/*     */       }
/*     */       else {
/*  38 */         text = yzm;
/*     */       }
/*     */ 
/*  41 */       Object[] objs = new Object[2];
/*  42 */       objs[0] = yzm;
/*  43 */       objs[1] = new Date();
/*     */ 
/*  45 */       if (SMSRequest(url, key, secret, template, text, phone)) {
/*  46 */         PhoneCodeMap.getInstance().getPhoneCodeMap().put(phone, objs);
/*     */ 
/*  48 */         Portalsmslogs smslogs = new Portalsmslogs();
/*  49 */         smslogs.setInfo(text);
/*  50 */         smslogs.setPhone(phone);
/*  51 */         smslogs.setSendDate(new Date());
/*  52 */         smslogs.setSid(id);
/*  53 */         smslogs.setType("5");
/*  54 */         smslogsService.addPortalsmslogs(smslogs);
/*  55 */         return true;
/*     */       }
/*  57 */       return false;
/*     */     }
/*     */     catch (Exception e) {
/*  60 */       if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
/*  61 */         logger.error("==============ERROR Start=============");
/*  62 */         logger.error(e);
/*  63 */         logger.error("ERROR INFO ", e);
/*  64 */         logger.error("==============ERROR End=============");
/*     */       }
/*     */     }
/*  66 */     return false;
/*     */   }
/*     */ 
/*     */   private static boolean SMSRequest(String url, String LoginName, String Password, String SpCode, String text, String phone)
/*     */   {
/*  72 */     String info = null;
/*     */     try {
/*  74 */       HttpClient httpclient = new HttpClient();
/*  75 */       PostMethod post = new PostMethod(url);
/*  76 */       post.getParams().setParameter(
/*  77 */         "http.protocol.content-charset", "gbk");
/*  78 */       post.addParameter("SpCode", SpCode);
/*  79 */       post.addParameter("LoginName", LoginName);
/*  80 */       post.addParameter("Password", Password);
/*  81 */       post.addParameter("MessageContent", text);
/*  82 */       post.addParameter("UserNumber", phone);
/*  83 */       post.addParameter("SerialNumber", "");
/*  84 */       post.addParameter("ScheduleTime", "");
/*  85 */       post.addParameter("ExtendAccessNum", "");
/*  86 */       post.addParameter("f", "1");
/*  87 */       httpclient.executeMethod(post);
/*  88 */       info = new String(post.getResponseBody(), "gbk");
/*  89 */       if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
/*  90 */         logger.info("SMS Send Result : " + info);
/*     */       }
/*  92 */       return true;
/*     */     } catch (Exception e) {
/*  94 */       if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
/*  95 */         logger.error("==============ERROR Start=============");
/*  96 */         logger.error(e);
/*  97 */         logger.error("ERROR INFO ", e);
/*  98 */         logger.error("==============ERROR End=============");
/*     */       }
/*     */     }
/* 100 */     return false;
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.utils.SMSUMSRequest
 * JD-Core Version:    0.6.2
 */