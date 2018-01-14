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
/*     */ import org.apache.log4j.Logger;
/*     */ import org.wyrsoft.smsapi.MessageClient;
/*     */ 
/*     */ public class SMSEmppRequest
/*     */ {
/*  16 */   private static Config config = Config.getInstance();
/*  17 */   private static Logger logger = Logger.getLogger(SMSEmppRequest.class);
/*     */ 
/*  19 */   private static PortalsmslogsService smslogsService = (PortalsmslogsService)
/*  20 */     SpringContextHelper.getBean("portalsmslogsServiceImpl");
/*     */   private static final int port = 9981;
/*     */ 
/*     */   public static boolean send(int type, String url, String key, String secret, String template, String text, String sign, String company, String phone, int time, Long id)
/*     */   {
/*     */     try
/*     */     {
/*  29 */       String yzm = 
/*  30 */         String.valueOf(Math.round(Math.random() * 8999.0D + 1000.0D));
/*  31 */       if (type == 1) {
/*  32 */         if (stringUtils.isNotBlank(text)) {
/*  33 */           if ("[yzm]".equals(text)) {
/*  34 */             text = "【" + sign + "】帐号注册验证码是" + yzm + " 有效期" + time + 
/*  35 */               "分钟! 【" + company + "】提供!";
/*     */           }
/*  37 */           else if (text.contains("[yzm]"))
/*  38 */             text = text.replace("[yzm]", yzm);
/*     */           else {
/*  40 */             text = "【" + sign + "】帐号注册验证码是" + yzm + " 有效期" + time + 
/*  41 */               "分钟! 【" + company + "】提供!";
/*     */           }
/*     */         }
/*     */         else {
/*  45 */           text = "【" + sign + "】帐号注册验证码是" + yzm + " 有效期" + time + 
/*  46 */             "分钟! 【" + company + "】提供!";
/*     */         }
/*     */       }
/*  49 */       else if (stringUtils.isNotBlank(text)) {
/*  50 */         if ("[yzm]".equals(text)) {
/*  51 */           text = "【" + sign + "】上网验证码是" + yzm + " 有效期" + time + 
/*  52 */             "分钟! 【" + company + "】提供!";
/*     */         }
/*  54 */         else if (text.contains("[yzm]"))
/*  55 */           text = text.replace("[yzm]", yzm);
/*     */         else {
/*  57 */           text = "【" + sign + "】上网验证码是" + yzm + " 有效期" + time + 
/*  58 */             "分钟! 【" + company + "】提供!";
/*     */         }
/*     */       }
/*     */       else {
/*  62 */         text = "【" + sign + "】上网验证码是" + yzm + " 有效期" + time + 
/*  63 */           "分钟! 【" + company + "】提供!";
/*     */       }
/*     */ 
/*  67 */       Object[] objs = new Object[2];
/*  68 */       objs[0] = yzm;
/*  69 */       objs[1] = new Date();
/*     */ 
/*  71 */       String accountId = key;
/*  72 */       String password = secret;
/*     */ 
/*  75 */       String host = url;
/*  76 */       int hostPort = 9981;
/*     */       try {
/*  78 */         String[] ts = url.split(":");
/*  79 */         host = ts[0];
/*  80 */         hostPort = Integer.valueOf(ts[1]).intValue();
/*     */       }
/*     */       catch (Exception localException1) {
/*     */       }
/*  84 */       String mobile = phone;
/*  85 */       String content = text;
/*     */ 
/*  87 */       SMSEmppRecvListener listener = SMSEmppRecvListener.getListener();
/*  88 */       MessageClient client = listener.getClient();
/*  89 */       if (!client.isBound()) {
/*  90 */         listener.initialize(host, hostPort, accountId, password);
/*     */       }
/*     */ 
/*  93 */       int b = listener.send(mobile, content);
/*     */ 
/*  95 */       if (b == 0) {
/*  96 */         if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1"))
/*  97 */           logger.info("SMS Send Result : Success");
/*     */       }
/*     */       else {
/* 100 */         if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
/* 101 */           logger.info("SMS Send Result : Fail");
/*     */         }
/*     */ 
/* 110 */         return false;
/*     */       }
/*     */ 
/* 121 */       Portalsmslogs smslogs = new Portalsmslogs();
/* 122 */       smslogs.setInfo(text);
/* 123 */       smslogs.setPhone(phone);
/* 124 */       smslogs.setSendDate(new Date());
/* 125 */       smslogs.setSid(id);
/* 126 */       smslogs.setType("4");
/* 127 */       smslogsService.addPortalsmslogs(smslogs);
/* 128 */       PhoneCodeMap.getInstance().getPhoneCodeMap().put(phone, objs);
/* 129 */       return true;
/*     */     } catch (Exception e) {
/* 131 */       if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
/* 132 */         logger.error("==============ERROR Start=============");
/* 133 */         logger.error(e);
/* 134 */         logger.error("ERROR INFO ", e);
/* 135 */         logger.error("==============ERROR End=============");
/*     */       }
/*     */     }
/* 137 */     return false;
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.utils.SMSEmppRequest
 * JD-Core Version:    0.6.2
 */