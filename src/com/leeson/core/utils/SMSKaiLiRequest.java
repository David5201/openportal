/*     */ package com.leeson.core.utils;
/*     */ 
/*     */ import com.leeson.common.utils.stringUtils;
/*     */ import com.leeson.core.bean.Portalbas;
/*     */ import com.leeson.core.bean.Portalsmslogs;
/*     */ import com.leeson.core.service.PortalsmslogsService;
/*     */ import com.leeson.portal.core.model.Config;
/*     */ import com.leeson.portal.core.model.PhoneCodeMap;
/*     */ import com.leeson.portal.core.utils.SpringContextHelper;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.PrintStream;
/*     */ import java.net.URL;
/*     */ import java.net.URLConnection;
/*     */ import java.net.URLEncoder;
/*     */ import java.util.Date;
/*     */ import java.util.Map;
/*     */ import org.apache.commons.codec.digest.DigestUtils;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class SMSKaiLiRequest
/*     */ {
/*  21 */   private static Config config = Config.getInstance();
/*  22 */   private static Logger logger = Logger.getLogger(SMSKaiLiRequest.class);
/*     */ 
/*  24 */   private static PortalsmslogsService smslogsService = (PortalsmslogsService)
/*  25 */     SpringContextHelper.getBean("portalsmslogsServiceImpl");
/*     */ 
/*     */   public static boolean send(int type, String url, String key, String secret, String text, String sign, String company, String phone, int time, Long id)
/*     */   {
/*     */     try
/*     */     {
/*  31 */       String yzm = 
/*  32 */         String.valueOf(Math.round(Math.random() * 8999.0D + 1000.0D));
/*     */ 
/*  34 */       if (type == 1) {
/*  35 */         if (stringUtils.isNotBlank(text)) {
/*  36 */           if ("[yzm]".equals(text)) {
/*  37 */             text = "【" + sign + "】帐号注册验证码是" + yzm + " 有效期" + time + 
/*  38 */               "分钟! 【" + company + "】提供!";
/*     */           }
/*  40 */           else if (text.contains("[yzm]"))
/*  41 */             text = text.replace("[yzm]", yzm);
/*     */           else {
/*  43 */             text = "【" + sign + "】帐号注册验证码是" + yzm + " 有效期" + time + 
/*  44 */               "分钟! 【" + company + "】提供!";
/*     */           }
/*     */         }
/*     */         else {
/*  48 */           text = "【" + sign + "】帐号注册验证码是" + yzm + " 有效期" + time + 
/*  49 */             "分钟! 【" + company + "】提供!";
/*     */         }
/*     */       }
/*  52 */       else if (stringUtils.isNotBlank(text)) {
/*  53 */         if ("[yzm]".equals(text)) {
/*  54 */           text = "【" + sign + "】上网验证码是" + yzm + " 有效期" + time + 
/*  55 */             "分钟! 【" + company + "】提供!";
/*     */         }
/*  57 */         else if (text.contains("[yzm]"))
/*  58 */           text = text.replace("[yzm]", yzm);
/*     */         else {
/*  60 */           text = "【" + sign + "】上网验证码是" + yzm + " 有效期" + time + 
/*  61 */             "分钟! 【" + company + "】提供!";
/*     */         }
/*     */       }
/*     */       else {
/*  65 */         text = "【" + sign + "】上网验证码是" + yzm + " 有效期" + time + 
/*  66 */           "分钟! 【" + company + "】提供!";
/*     */       }
/*     */ 
/*  70 */       Object[] objs = new Object[2];
/*  71 */       objs[0] = yzm;
/*  72 */       objs[1] = new Date();
/*     */ 
/*  75 */       String content = text;
/*  76 */       content = URLEncoder.encode(content, "UTF-8");
/*     */ 
/*  78 */       String password = DigestUtils.md5Hex(secret);
/*  79 */       password = DigestUtils.md5Hex(key + password);
/*     */ 
/*  81 */       String prams = "username=" + key + "&password=" + password + 
/*  82 */         "&mobile=" + phone + "&content=" + content;
/*  83 */       if (send(url, prams)) {
/*  84 */         PhoneCodeMap.getInstance().getPhoneCodeMap().put(phone, objs);
/*     */ 
/*  86 */         Portalsmslogs smslogs = new Portalsmslogs();
/*  87 */         smslogs.setInfo(text);
/*  88 */         smslogs.setPhone(phone);
/*  89 */         smslogs.setSendDate(new Date());
/*  90 */         smslogs.setSid(id);
/*  91 */         smslogs.setType("8");
/*  92 */         smslogsService.addPortalsmslogs(smslogs);
/*  93 */         return true;
/*     */       }
/*  95 */       return false;
/*     */     } catch (Exception e) {
/*  97 */       if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
/*  98 */         logger.error("==============ERROR Start=============");
/*  99 */         logger.error(e);
/* 100 */         logger.error("ERROR INFO ", e);
/* 101 */         logger.error("==============ERROR End=============");
/*     */       }
/*     */     }
/* 103 */     return false;
/*     */   }
/*     */ 
/*     */   private static boolean send(String url, String prams)
/*     */   {
/* 118 */     String result = "";
/* 119 */     BufferedReader in = null;
/*     */     try {
/* 121 */       String urlNameString = url + "?" + prams;
/* 122 */       URL realUrl = new URL(urlNameString);
/*     */ 
/* 124 */       URLConnection connection = realUrl.openConnection();
/*     */ 
/* 126 */       connection.setRequestProperty("accept", "*/*");
/* 127 */       connection.setRequestProperty("connection", "Keep-Alive");
/* 128 */       connection.setRequestProperty("user-agent", 
/* 129 */         "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
/*     */ 
/* 131 */       connection.connect();
/*     */ 
/* 133 */       in = new BufferedReader(new InputStreamReader(
/* 134 */         connection.getInputStream()));
/*     */       String line;
/* 136 */       while ((line = in.readLine()) != null)
/*     */       {
/* 137 */         result = result + line;
/*     */       }
/* 139 */       System.out.println("SMS_API Result= " + result);
/*     */ 
/* 141 */       if (stringUtils.isNotBlank(result)) {
/*     */         try {
/* 143 */           long state = Long.valueOf(result.trim()).longValue();
/* 144 */           if (state > 0L)
/* 145 */             return true;
/*     */         }
/*     */         catch (Exception e) {
/* 148 */           return false;
/*     */         }
/*     */       }
/* 151 */       return false;
/*     */     } catch (Exception e) {
/* 153 */       System.out.println("发送GET请求出现异常！" + e);
/* 154 */       return false;
/*     */     }
/*     */     finally
/*     */     {
/*     */       try {
/* 159 */         if (in != null)
/* 160 */           in.close();
/*     */       }
/*     */       catch (Exception e2) {
/* 163 */         System.out.println("关闭资源出现异常！" + e2);
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.utils.SMSKaiLiRequest
 * JD-Core Version:    0.6.2
 */