/*     */ package com.leeson.core.api;
/*     */ 
/*     */ import com.leeson.core.bean.Portalaccount;
/*     */ import com.leeson.core.bean.Portalbas;
/*     */ import com.leeson.core.query.PortalaccountQuery;
/*     */ import com.leeson.core.service.PortalaccountService;
/*     */ import com.leeson.portal.core.controller.utils.BASE64;
/*     */ import com.leeson.portal.core.model.Config;
/*     */ import com.leeson.portal.core.utils.SpringContextHelper;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.PrintWriter;
/*     */ import java.net.URL;
/*     */ import java.net.URLConnection;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.sf.json.JSONObject;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class AccountAPIRequest
/*     */ {
/*  24 */   private static Config config = Config.getInstance();
/*  25 */   private static Logger logger = Logger.getLogger(AccountAPIRequest.class);
/*     */ 
/*  27 */   private static PortalaccountService accountService = (PortalaccountService)
/*  28 */     SpringContextHelper.getBean("portalaccountServiceImpl");
/*     */ 
/*     */   public static boolean sendPost(String url, String usr, String pwd, String ip, String mac, String type)
/*     */   {
/*  40 */     String pwdEn = pwd;
/*     */     try {
/*  42 */       pwdEn = BASE64.encryptBASE64(pwd);
/*     */     } catch (Exception e1) {
/*  44 */       if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
/*  45 */         logger.error("Account API Error : " + e1);
/*     */       }
/*     */     }
/*     */ 
/*  49 */     String param = "usr=" + usr + "&pwd=" + pwdEn + "&ip=" + ip + "&mac=" + mac + "&type=" + type;
/*     */ 
/*  51 */     if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
/*  52 */       logger.info("Account API Request : URL=" + url);
/*  53 */       logger.info("Account API Request : Params=" + param);
/*     */     }
/*     */ 
/*  56 */     PrintWriter out = null;
/*  57 */     BufferedReader in = null;
/*  58 */     String result = "";
/*     */     try {
/*  60 */       URL realUrl = new URL(url);
/*     */ 
/*  62 */       URLConnection conn = realUrl.openConnection();
/*     */ 
/*  64 */       conn.setRequestProperty("accept", "*/*");
/*  65 */       conn.setRequestProperty("connection", "Keep-Alive");
/*  66 */       conn.setRequestProperty("user-agent", 
/*  67 */         "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
/*     */ 
/*  69 */       conn.setDoOutput(true);
/*  70 */       conn.setDoInput(true);
/*     */ 
/*  72 */       out = new PrintWriter(conn.getOutputStream());
/*     */ 
/*  74 */       out.print(param);
/*     */ 
/*  76 */       out.flush();
/*     */ 
/*  78 */       in = new BufferedReader(
/*  79 */         new InputStreamReader(conn.getInputStream()));
/*     */       String line;
/*  81 */       while ((line = in.readLine()) != null)
/*     */       {
/*  82 */         result = result + line;
/*     */       }
/*  84 */       if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
/*  85 */         logger.info("Account API Result : " + result);
/*     */       }
/*     */ 
/*  92 */       JSONObject jsonObj = JSONObject.fromObject(result);
/*  93 */       int state = jsonObj.getInt("status");
/*  94 */       String info = jsonObj.getString("info");
/*  95 */       info = decodeUnicode(info);
/*  96 */       if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
/*  97 */         logger.info("Account API Info : " + info);
/*     */       }
/*     */ 
/* 100 */       if (state == 1) {
/* 101 */         JSONObject data = jsonObj.getJSONObject("data");
/* 102 */         double hour = data.getDouble("alivetime");
/* 103 */         long time = (long)(hour * 3600000.0D);
/* 104 */         if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
/* 105 */           logger.info("Account API Time : " + time);
/*     */         }
/*     */ 
/* 108 */         PortalaccountQuery aq = new PortalaccountQuery();
/* 109 */         aq.setLoginName(usr);
/* 110 */         aq.setLoginNameLike(false);
/* 111 */         List accounts = accountService.getPortalaccountList(aq);
/* 112 */         if (accounts.size() > 0) {
/* 113 */           Portalaccount account = (Portalaccount)accounts.get(0);
/* 114 */           account.setPassword(pwd);
/* 115 */           account.setTime(Long.valueOf(time));
/* 116 */           accountService.updatePortalaccountByKey(account);
/*     */         } else {
/*     */           try {
/* 119 */             Portalaccount account = new Portalaccount();
/* 120 */             account.setLoginName(usr);
/* 121 */             account.setPassword(pwd);
/* 122 */             account.setName(usr);
/* 123 */             account.setDate(new Date());
/* 124 */             account.setState("1");
/* 125 */             account.setTime(Long.valueOf(time));
/* 126 */             account.setMaclimit(Integer.valueOf(0));
/* 127 */             account.setMaclimitcount(Integer.valueOf(0));
/* 128 */             account.setAutologin(Integer.valueOf(0));
/* 129 */             account.setSpeed(Long.valueOf(1L));
/* 130 */             accountService.addPortalaccount(account);
/*     */           }
/*     */           catch (Exception e) {
/* 133 */             if (((Portalbas)config.getConfigMap().get("")).getIsdebug()
/* 133 */               .equals("1")) {
/* 134 */               logger.error("==============ERROR Start=============");
/* 135 */               logger.error(e);
/* 136 */               logger.error("ERROR INFO ", e);
/* 137 */               logger.error("==============ERROR End=============");
/*     */             }
/* 139 */             return false;
/*     */           }
/*     */         }
/* 142 */         return true;
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 146 */       if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
/* 147 */         logger.error("==============ERROR Start=============");
/* 148 */         logger.error(e);
/* 149 */         logger.error("ERROR INFO ", e);
/* 150 */         logger.error("==============ERROR End=============");
/*     */       }
/* 152 */       return false;
/*     */     }
/*     */     finally
/*     */     {
/*     */       try {
/* 157 */         if (out != null) {
/* 158 */           out.close();
/*     */         }
/* 160 */         if (in != null)
/* 161 */           in.close();
/*     */       }
/*     */       catch (IOException localIOException3)
/*     */       {
/*     */       }
/*     */     }
/*     */     try
/*     */     {
/* 157 */       if (out != null) {
/* 158 */         out.close();
/*     */       }
/* 160 */       if (in != null) {
/* 161 */         in.close();
/*     */       }
/*     */     }
/*     */     catch (IOException localIOException4)
/*     */     {
/*     */     }
/* 167 */     return false;
/*     */   }
/*     */ 
/*     */   public static boolean guestSendPost(String url, String usr, String ip, String mac, String type)
/*     */   {
/* 180 */     String param = "usr=" + usr + "&ip=" + ip + "&mac=" + mac + "&type=" + type;
/*     */ 
/* 182 */     if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
/* 183 */       logger.info("Guest Account API Request : URL=" + url);
/* 184 */       logger.info("Guest Account API Request : Params=" + param);
/*     */     }
/*     */ 
/* 187 */     PrintWriter out = null;
/* 188 */     BufferedReader in = null;
/* 189 */     String result = "";
/*     */     try {
/* 191 */       URL realUrl = new URL(url);
/*     */ 
/* 193 */       URLConnection conn = realUrl.openConnection();
/*     */ 
/* 195 */       conn.setRequestProperty("accept", "*/*");
/* 196 */       conn.setRequestProperty("connection", "Keep-Alive");
/* 197 */       conn.setRequestProperty("user-agent", 
/* 198 */         "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
/*     */ 
/* 200 */       conn.setDoOutput(true);
/* 201 */       conn.setDoInput(true);
/*     */ 
/* 203 */       out = new PrintWriter(conn.getOutputStream());
/*     */ 
/* 205 */       out.print(param);
/*     */ 
/* 207 */       out.flush();
/*     */ 
/* 209 */       in = new BufferedReader(
/* 210 */         new InputStreamReader(conn.getInputStream()));
/*     */       String line;
/* 212 */       while ((line = in.readLine()) != null)
/*     */       {
/* 213 */         result = result + line;
/*     */       }
/* 215 */       if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
/* 216 */         logger.info("Guest Account API Result : " + result);
/*     */       }
/*     */ 
/* 223 */       JSONObject jsonObj = JSONObject.fromObject(result);
/* 224 */       int state = jsonObj.getInt("status");
/* 225 */       String info = jsonObj.getString("info");
/* 226 */       info = decodeUnicode(info);
/* 227 */       if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
/* 228 */         logger.info("Account API Info : " + info);
/*     */       }
/*     */ 
/* 231 */       if (state == 1) {
/* 232 */         JSONObject data = jsonObj.getJSONObject("data");
/* 233 */         double hour = data.getDouble("alivetime");
/* 234 */         long time = (long)(hour * 3600000.0D);
/* 235 */         if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
/* 236 */           logger.info("Account API Time : " + time);
/*     */         }
/* 238 */         return true;
/*     */       }
/*     */     } catch (Exception e) {
/* 241 */       if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
/* 242 */         logger.error("==============ERROR Start=============");
/* 243 */         logger.error(e);
/* 244 */         logger.error("ERROR INFO ", e);
/* 245 */         logger.error("==============ERROR End=============");
/*     */       }
/* 247 */       return false;
/*     */     }
/*     */     finally
/*     */     {
/*     */       try {
/* 252 */         if (out != null) {
/* 253 */           out.close();
/*     */         }
/* 255 */         if (in != null)
/* 256 */           in.close();
/*     */       }
/*     */       catch (IOException localIOException2)
/*     */       {
/*     */       }
/*     */     }
/*     */     try
/*     */     {
/* 252 */       if (out != null) {
/* 253 */         out.close();
/*     */       }
/* 255 */       if (in != null) {
/* 256 */         in.close();
/*     */       }
/*     */     }
/*     */     catch (IOException localIOException3)
/*     */     {
/*     */     }
/* 262 */     return false;
/*     */   }
/*     */ 
/*     */   public static String decodeUnicode(String theString)
/*     */   {
/* 272 */     int len = theString.length();
/*     */ 
/* 274 */     StringBuffer outBuffer = new StringBuffer(len);
/*     */ 
/* 276 */     for (int x = 0; x < len; )
/*     */     {
/* 278 */       char aChar = theString.charAt(x++);
/*     */ 
/* 280 */       if (aChar == '\\')
/*     */       {
/* 282 */         aChar = theString.charAt(x++);
/*     */ 
/* 284 */         if (aChar == 'u')
/*     */         {
/* 288 */           int value = 0;
/*     */ 
/* 290 */           for (int i = 0; i < 4; i++)
/*     */           {
/* 292 */             aChar = theString.charAt(x++);
/*     */ 
/* 294 */             switch (aChar)
/*     */             {
/*     */             case '0':
/*     */             case '1':
/*     */             case '2':
/*     */             case '3':
/*     */             case '4':
/*     */             case '5':
/*     */             case '6':
/*     */             case '7':
/*     */             case '8':
/*     */             case '9':
/* 312 */               value = (value << 4) + aChar - 48;
/* 313 */               break;
/*     */             case 'a':
/*     */             case 'b':
/*     */             case 'c':
/*     */             case 'd':
/*     */             case 'e':
/*     */             case 'f':
/* 320 */               value = (value << 4) + 10 + aChar - 97;
/* 321 */               break;
/*     */             case 'A':
/*     */             case 'B':
/*     */             case 'C':
/*     */             case 'D':
/*     */             case 'E':
/*     */             case 'F':
/* 328 */               value = (value << 4) + 10 + aChar - 65;
/* 329 */               break;
/*     */             case ':':
/*     */             case ';':
/*     */             case '<':
/*     */             case '=':
/*     */             case '>':
/*     */             case '?':
/*     */             case '@':
/*     */             case 'G':
/*     */             case 'H':
/*     */             case 'I':
/*     */             case 'J':
/*     */             case 'K':
/*     */             case 'L':
/*     */             case 'M':
/*     */             case 'N':
/*     */             case 'O':
/*     */             case 'P':
/*     */             case 'Q':
/*     */             case 'R':
/*     */             case 'S':
/*     */             case 'T':
/*     */             case 'U':
/*     */             case 'V':
/*     */             case 'W':
/*     */             case 'X':
/*     */             case 'Y':
/*     */             case 'Z':
/*     */             case '[':
/*     */             case '\\':
/*     */             case ']':
/*     */             case '^':
/*     */             case '_':
/*     */             case '`':
/*     */             default:
/* 331 */               throw new IllegalArgumentException(
/* 332 */                 "Malformed   \\uxxxx   encoding.");
/*     */             }
/*     */           }
/*     */ 
/* 336 */           outBuffer.append((char)value);
/*     */         } else {
/* 338 */           if (aChar == 't')
/* 339 */             aChar = '\t';
/* 340 */           else if (aChar == 'r') {
/* 341 */             aChar = '\r';
/*     */           }
/* 343 */           else if (aChar == 'n')
/*     */           {
/* 345 */             aChar = '\n';
/*     */           }
/* 347 */           else if (aChar == 'f')
/*     */           {
/* 349 */             aChar = '\f';
/*     */           }
/* 351 */           outBuffer.append(aChar);
/*     */         }
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/* 357 */         outBuffer.append(aChar);
/*     */       }
/*     */     }
/*     */ 
/* 361 */     return outBuffer.toString();
/*     */   }
/*     */ 
/*     */   public static boolean send(String url, String usr, String pwd, String ip, String mac, String type)
/*     */   {
/* 367 */     return sendPost(url, usr, pwd, ip, mac, type);
/*     */   }
/*     */ 
/*     */   public static boolean guestSend(String url, String usr, String ip, String mac, String type) {
/* 371 */     return guestSendPost(url, usr, ip, mac, type);
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.api.AccountAPIRequest
 * JD-Core Version:    0.6.2
 */