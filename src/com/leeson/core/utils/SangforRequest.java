/*     */ package com.leeson.core.utils;
/*     */ 
/*     */ import com.leeson.common.utils.stringUtils;
/*     */ import com.leeson.core.bean.Portalbas;
/*     */ import com.leeson.portal.core.model.Config;
/*     */ import com.leeson.portal.core.model.SangforAPIMap;
/*     */ import com.leeson.portal.core.utils.AaHpl8Ha9bNPen9OLddV;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.PrintStream;
/*     */ import java.net.URL;
/*     */ import java.net.URLConnection;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class SangforRequest
/*     */ {
/*  19 */   private static Config config = Config.getInstance();
/*  20 */   private static Logger logger = Logger.getLogger(SangforRequest.class);
/*  21 */   private static SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
/*     */ 
/*     */   private static void sendGet(String url, String param)
/*     */   {
/*  32 */     String result = "";
/*  33 */     BufferedReader in = null;
/*     */     try {
/*  35 */       String urlNameString = url + "?" + param;
/*  36 */       URL realUrl = new URL(urlNameString);
/*     */ 
/*  38 */       URLConnection connection = realUrl.openConnection();
/*     */ 
/*  40 */       connection.setRequestProperty("accept", "*/*");
/*  41 */       connection.setRequestProperty("connection", "Keep-Alive");
/*  42 */       connection.setRequestProperty("user-agent", 
/*  43 */         "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
/*     */ 
/*  45 */       connection.connect();
/*     */ 
/*  53 */       in = new BufferedReader(new InputStreamReader(
/*  54 */         connection.getInputStream()));
/*     */       String line;
/*  56 */       while ((line = in.readLine()) != null)
/*     */       {
/*  57 */         result = result + line;
/*     */       }
/*  59 */       if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1"))
/*  60 */         System.out.println(result);
/*     */     }
/*     */     catch (Exception e) {
/*  63 */       if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
/*  64 */         logger.error("==============ERROR Start=============");
/*  65 */         logger.error(e);
/*  66 */         logger.error("ERROR INFO ", e);
/*  67 */         logger.error("==============ERROR End=============");
/*     */       }
/*     */ 
/*     */       try
/*     */       {
/*  73 */         if (in != null)
/*  74 */           in.close();
/*     */       }
/*     */       catch (Exception localException1)
/*     */       {
/*     */       }
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/*  73 */         if (in != null)
/*  74 */           in.close();
/*     */       }
/*     */       catch (Exception localException2)
/*     */       {
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void login(String userIP, String userName, String mac, String apmac, String basip)
/*     */   {
/*     */     try
/*     */     {
/*  87 */       String type = (String)SangforAPIMap.getInstance().getSangforAPIMap().get("type");
/*  88 */       String serverIP = (String)SangforAPIMap.getInstance().getSangforAPIMap().get("url");
/*  89 */       String port = (String)SangforAPIMap.getInstance().getSangforAPIMap().get("port");
/*  90 */       if (stringUtils.isNotBlank(type))
/*  91 */         if (type.equals("0")) {
/*  92 */           if (stringUtils.isNotBlank(serverIP)) {
/*  93 */             if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
/*  94 */               logger.error("Sangfor logon : serverIP=" + serverIP + " userIP=" + userIP + " userName=" + userName);
/*     */             }
/*  96 */             String url = "http://" + serverIP + ":85/cgi-bin/caauth.cgi";
/*  97 */             String params = "ui=web&opr=logon&chk_cookie=0&info=";
/*  98 */             String paramSub = userIP + "/" + userName + "/" + "/CloudPortal";
/*  99 */             paramSub = new String(paramSub.getBytes("UTF-8"), "UTF-8");
/* 100 */             paramSub = AaHpl8Ha9bNPen9OLddV.encryptBASE64(paramSub);
/* 101 */             params = params + paramSub;
/* 102 */             sendGet(url, params);
/*     */           }
/* 104 */         } else if ((type.equals("1")) && 
/* 105 */           (stringUtils.isNotBlank(serverIP)) && (stringUtils.isNotBlank(port))) {
/* 106 */           String url = "http://" + serverIP + ":" + port + "/pronline/Msg";
/* 107 */           Date now = new Date();
/* 108 */           String timeS = format.format(now);
/* 109 */           long timeLong = now.getTime() + 86400000L;
/* 110 */           Date expiretime = new Date(timeLong);
/* 111 */           String expiretimeS = format.format(expiretime);
/* 112 */           if (stringUtils.isBlank(apmac)) {
/* 113 */             apmac = basip;
/*     */           }
/* 115 */           String params = "FunName@ncHttpLogin&account=" + userName + "&expiretime=" + expiretimeS + "&ip=" + userIP + "&mac=" + mac + "&time=" + timeS + "&location=" + apmac + "&netid=" + basip;
/* 116 */           sendGet(url, params);
/*     */         }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 121 */       if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
/* 122 */         logger.error("==============ERROR Start=============");
/* 123 */         logger.error(e);
/* 124 */         logger.error("ERROR INFO ", e);
/* 125 */         logger.error("==============ERROR End=============");
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void logout(String userIP, String userName)
/*     */   {
/*     */     try
/*     */     {
/* 134 */       String type = (String)SangforAPIMap.getInstance().getSangforAPIMap().get("type");
/* 135 */       String serverIP = (String)SangforAPIMap.getInstance().getSangforAPIMap().get("url");
/* 136 */       String port = (String)SangforAPIMap.getInstance().getSangforAPIMap().get("port");
/* 137 */       if (stringUtils.isNotBlank(type)) {
/* 138 */         if (type.equals("0")) {
/* 139 */           if (stringUtils.isNotBlank(serverIP)) {
/* 140 */             if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
/* 141 */               logger.error("Sangfor logout : serverIP=" + serverIP + " userIP=" + userIP + " userName=" + userName);
/*     */             }
/* 143 */             String url = "http://" + serverIP + ":85/cgi-bin/caauth.cgi";
/* 144 */             String params = "ui=web&opr=logout&chk_cookie=0&info=";
/* 145 */             String paramSub = userIP + "/" + userName + "/" + "/CloudPortal";
/* 146 */             paramSub = new String(paramSub.getBytes("UTF-8"), "UTF-8");
/* 147 */             paramSub = AaHpl8Ha9bNPen9OLddV.encryptBASE64(paramSub);
/* 148 */             params = params + paramSub;
/* 149 */             sendGet(url, params);
/*     */           }
/* 151 */         } else if ((type.equals("1")) && 
/* 152 */           (stringUtils.isNotBlank(serverIP)) && (stringUtils.isNotBlank(port))) {
/* 153 */           String url = "http://" + serverIP + ":" + port + "/pronline/Msg";
/* 154 */           Date now = new Date();
/* 155 */           String timeS = format.format(now);
/* 156 */           String params = "FunName@ncHttpLogout&ip=" + userIP + "&time=" + timeS;
/* 157 */           sendGet(url, params);
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 163 */       if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
/* 164 */         logger.error("==============ERROR Start=============");
/* 165 */         logger.error(e);
/* 166 */         logger.error("ERROR INFO ", e);
/* 167 */         logger.error("==============ERROR End=============");
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.utils.SangforRequest
 * JD-Core Version:    0.6.2
 */