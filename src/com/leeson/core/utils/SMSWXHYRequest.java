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
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.PrintWriter;
/*     */ import java.net.URL;
/*     */ import java.net.URLConnection;
/*     */ import java.net.URLEncoder;
/*     */ import java.util.Date;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class SMSWXHYRequest
/*     */ {
/*  22 */   private static Config config = Config.getInstance();
/*  23 */   private static Logger logger = Logger.getLogger(SMSWXHYRequest.class);
/*     */ 
/*  25 */   private static PortalsmslogsService smslogsService = (PortalsmslogsService)
/*  26 */     SpringContextHelper.getBean("portalsmslogsServiceImpl");
/*     */ 
/*     */   public static boolean send(String url, String account, String password, String text, String phone, Long id)
/*     */   {
/*     */     try
/*     */     {
/*  31 */       String yzm = 
/*  32 */         String.valueOf(Math.round(Math.random() * 8999.0D + 1000.0D));
/*     */ 
/*  34 */       if (stringUtils.isNotBlank(text)) {
/*  35 */         if (text.contains("[yzm]"))
/*  36 */           text = text.replace("[yzm]", yzm);
/*     */         else
/*  38 */           text = yzm;
/*     */       }
/*     */       else {
/*  41 */         text = yzm;
/*     */       }
/*     */ 
/*  44 */       Object[] objs = new Object[2];
/*  45 */       objs[0] = yzm;
/*  46 */       objs[1] = new Date();
/*     */ 
/*  48 */       String decodeText = new String(text);
/*  49 */       text = URLEncoder.encode(text, "UTF-8");
/*  50 */       String params = "apName=" + account + "&apPassword=" + password + "&calledNumber=" + phone + "&content=" + text + "&sendTime=&srcId=&ServiceId=";
/*  51 */       String result = sendPost(url, params);
/*     */ 
/*  53 */       if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
/*  54 */         logger.info("SMS Send Result = " + result);
/*     */       }
/*     */ 
/*  57 */       if (result.contains("<error>0</error>")) {
/*  58 */         PhoneCodeMap.getInstance().getPhoneCodeMap().put(phone, objs);
/*     */ 
/*  60 */         Portalsmslogs smslogs = new Portalsmslogs();
/*  61 */         smslogs.setInfo(decodeText);
/*  62 */         smslogs.setPhone(phone);
/*  63 */         smslogs.setSendDate(new Date());
/*  64 */         smslogs.setSid(id);
/*  65 */         smslogs.setType("11");
/*  66 */         smslogsService.addPortalsmslogs(smslogs);
/*  67 */         return true;
/*     */       }
/*  69 */       return false;
/*     */     }
/*     */     catch (Exception e) {
/*  72 */       if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
/*  73 */         logger.error("==============ERROR Start=============");
/*  74 */         logger.error(e);
/*  75 */         logger.error("ERROR INFO ", e);
/*  76 */         logger.error("==============ERROR End=============");
/*     */       }
/*     */     }
/*  78 */     return false;
/*     */   }
/*     */ 
/*     */   public static String sendPost(String url, String param)
/*     */   {
/*  91 */     PrintWriter out = null;
/*  92 */     BufferedReader in = null;
/*  93 */     String result = "";
/*     */     try {
/*  95 */       URL realUrl = new URL(url);
/*     */ 
/*  97 */       URLConnection conn = realUrl.openConnection();
/*     */ 
/*  99 */       conn.setRequestProperty("accept", "*/*");
/* 100 */       conn.setRequestProperty("connection", "Keep-Alive");
/* 101 */       conn.setRequestProperty("user-agent", 
/* 102 */         "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
/*     */ 
/* 104 */       conn.setDoOutput(true);
/* 105 */       conn.setDoInput(true);
/*     */ 
/* 107 */       out = new PrintWriter(conn.getOutputStream());
/*     */ 
/* 109 */       out.print(param);
/*     */ 
/* 111 */       out.flush();
/*     */ 
/* 113 */       in = new BufferedReader(
/* 114 */         new InputStreamReader(conn.getInputStream()));
/*     */       String line;
/* 116 */       while ((line = in.readLine()) != null)
/*     */       {
/* 117 */         result = result + line;
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (Exception localException)
/*     */     {
/*     */       try
/*     */       {
/* 127 */         if (out != null) {
/* 128 */           out.close();
/*     */         }
/* 130 */         if (in != null)
/* 131 */           in.close();
/*     */       }
/*     */       catch (IOException localIOException)
/*     */       {
/*     */       }
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/* 127 */         if (out != null) {
/* 128 */           out.close();
/*     */         }
/* 130 */         if (in != null) {
/* 131 */           in.close();
/*     */         }
/*     */       }
/*     */       catch (IOException localIOException1)
/*     */       {
/*     */       }
/*     */     }
/* 138 */     return result;
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.utils.SMSWXHYRequest
 * JD-Core Version:    0.6.2
 */