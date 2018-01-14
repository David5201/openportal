/*     */ package weixin.guanjia.core.util;
/*     */ 
/*     */ import com.leeson.core.bean.Portalbas;
/*     */ import com.leeson.portal.core.model.Config;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.OutputStream;
/*     */ import java.net.ConnectException;
/*     */ import java.net.URL;
/*     */ import java.security.SecureRandom;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.net.ssl.HttpsURLConnection;
/*     */ import javax.net.ssl.SSLContext;
/*     */ import javax.net.ssl.SSLSocketFactory;
/*     */ import javax.net.ssl.TrustManager;
/*     */ import net.sf.json.JSONObject;
/*     */ import org.apache.log4j.Logger;
/*     */ import sun.misc.BASE64Decoder;
/*     */ import sun.misc.BASE64Encoder;
/*     */ import weixin.entity.WeixinAccessToken;
/*     */ import weixin.guanjia.core.entity.common.AccessToken;
/*     */ import weixin.service.WeixinAccessTokenService;
/*     */ 
/*     */ public class WeixinUtil
/*     */ {
/*     */   public static final String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
/*  40 */   public static String menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
/*     */ 
/*  42 */   public static String send_message_url = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN";
/*     */ 
/*  44 */   private static Logger log = Logger.getLogger(WeixinUtil.class);
/*  45 */   private static Config config = Config.getInstance();
/*     */ 
/*     */   public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr)
/*     */   {
/*  57 */     Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
/*  58 */     JSONObject jsonObject = null;
/*  59 */     StringBuffer buffer = new StringBuffer();
/*     */     try
/*     */     {
/*  62 */       TrustManager[] tm = { new MyX509TrustManager() };
/*  63 */       SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
/*  64 */       sslContext.init(null, tm, new SecureRandom());
/*     */ 
/*  66 */       SSLSocketFactory ssf = sslContext.getSocketFactory();
/*     */ 
/*  68 */       URL url = new URL(requestUrl);
/*  69 */       HttpsURLConnection httpUrlConn = (HttpsURLConnection)url.openConnection();
/*  70 */       httpUrlConn.setSSLSocketFactory(ssf);
/*     */ 
/*  72 */       httpUrlConn.setDoOutput(true);
/*  73 */       httpUrlConn.setDoInput(true);
/*  74 */       httpUrlConn.setUseCaches(false);
/*     */ 
/*  76 */       httpUrlConn.setRequestMethod(requestMethod);
/*     */ 
/*  78 */       if ("GET".equalsIgnoreCase(requestMethod)) {
/*  79 */         httpUrlConn.connect();
/*     */       }
/*     */ 
/*  82 */       if (outputStr != null) {
/*  83 */         OutputStream outputStream = httpUrlConn.getOutputStream();
/*     */ 
/*  85 */         outputStream.write(outputStr.getBytes("UTF-8"));
/*  86 */         outputStream.close();
/*     */       }
/*     */ 
/*  90 */       InputStream inputStream = httpUrlConn.getInputStream();
/*  91 */       InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
/*  92 */       BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
/*     */ 
/*  94 */       String str = null;
/*  95 */       while ((str = bufferedReader.readLine()) != null) {
/*  96 */         buffer.append(str);
/*     */       }
/*  98 */       bufferedReader.close();
/*  99 */       inputStreamReader.close();
/*     */ 
/* 101 */       inputStream.close();
/* 102 */       inputStream = null;
/* 103 */       httpUrlConn.disconnect();
/* 104 */       jsonObject = JSONObject.fromObject(buffer.toString());
/*     */     }
/*     */     catch (ConnectException ce) {
/* 107 */       if (basConfig.getIsdebug().equals("1"))
/* 108 */         log.info("Weixin server connection timed out.");
/*     */     }
/*     */     catch (Exception e) {
/* 111 */       if (basConfig.getIsdebug().equals("1")) {
/* 112 */         log.info("https request error:{}" + e.getMessage());
/*     */       }
/*     */     }
/* 115 */     return jsonObject;
/*     */   }
/*     */ 
/*     */   public static AccessToken getAccessToken(WeixinAccessTokenService weixinAccessTokenService, String appid, String appsecret)
/*     */   {
/* 129 */     Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
/* 130 */     WeixinAccessToken accessTocken = getRealAccessToken(weixinAccessTokenService);
/*     */ 
/* 132 */     if (accessTocken.getAccess_token() != null) {
/* 133 */       if (basConfig.getIsdebug().equals("1")) {
/* 134 */         log.info("==========is have tocken============" + accessTocken);
/*     */       }
/* 136 */       SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
/* 137 */       Date end = new Date();
/* 138 */       Date oldATTime = new Date(accessTocken.getAddTime().getTime() + accessTocken.getExpires_in() * 1000);
/* 139 */       String oldTime = format.format(oldATTime);
/* 140 */       if (basConfig.getIsdebug().equals("1")) {
/* 141 */         log.info("have tocken to Date: " + oldTime);
/*     */       }
/* 143 */       if (end.getTime() - accessTocken.getAddTime().getTime() > accessTocken.getExpires_in() * 1000) {
/* 144 */         if (basConfig.getIsdebug().equals("1")) {
/* 145 */           log.info("have tocken is out of Date !");
/*     */         }
/* 147 */         AccessToken accessToken = null;
/* 148 */         String requestUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET".replace("APPID", appid).replace("APPSECRET", appsecret);
/* 149 */         JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
/*     */ 
/* 151 */         if (jsonObject != null) {
/*     */           try {
/* 153 */             accessToken = new AccessToken();
/* 154 */             accessToken.setToken(jsonObject.getString("access_token"));
/* 155 */             accessToken.setExpiresIn(jsonObject.getInt("expires_in"));
/*     */ 
/* 157 */             WeixinAccessToken at = new WeixinAccessToken();
/* 158 */             at.setId(Long.valueOf(1L));
/* 159 */             at.setAddTime(new Date());
/* 160 */             at.setExpires_in(jsonObject.getInt("expires_in"));
/* 161 */             at.setAccess_token(jsonObject.getString("access_token"));
/* 162 */             updateAccessToken(at, weixinAccessTokenService);
/* 163 */             if (basConfig.getIsdebug().equals("1"))
/* 164 */               log.info("Try get AccessToken : " + at);
/*     */           }
/*     */           catch (Exception e)
/*     */           {
/* 168 */             accessToken = null;
/*     */ 
/* 170 */             String wrongMessage = "Get token error errcode:{} errmsg:{}" + jsonObject.getInt("errcode") + jsonObject.getString("errmsg");
/* 171 */             if (basConfig.getIsdebug().equals("1")) {
/* 172 */               log.info(wrongMessage);
/*     */             }
/*     */           }
/*     */         }
/* 176 */         return accessToken;
/*     */       }
/*     */ 
/* 179 */       AccessToken accessToken = new AccessToken();
/* 180 */       accessToken.setToken(accessTocken.getAccess_token());
/* 181 */       accessToken.setExpiresIn(accessTocken.getExpires_in());
/* 182 */       return accessToken;
/*     */     }
/*     */ 
/* 186 */     AccessToken accessToken = null;
/* 187 */     String requestUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET".replace("APPID", appid).replace("APPSECRET", appsecret);
/* 188 */     JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
/*     */ 
/* 190 */     if (jsonObject != null) {
/*     */       try {
/* 192 */         accessToken = new AccessToken();
/* 193 */         accessToken.setToken(jsonObject.getString("access_token"));
/* 194 */         accessToken.setExpiresIn(jsonObject.getInt("expires_in"));
/*     */ 
/* 196 */         WeixinAccessToken at = new WeixinAccessToken();
/* 197 */         at.setId(Long.valueOf(1L));
/* 198 */         at.setAddTime(new Date());
/* 199 */         at.setExpires_in(jsonObject.getInt("expires_in"));
/* 200 */         at.setAccess_token(jsonObject.getString("access_token"));
/* 201 */         updateAccessToken(at, weixinAccessTokenService);
/* 202 */         if (basConfig.getIsdebug().equals("1"))
/* 203 */           log.info("Get token First , AccessToken : " + at);
/*     */       }
/*     */       catch (Exception e) {
/* 206 */         accessToken = null;
/*     */ 
/* 208 */         String wrongMessage = "Get token error errcode:{} errmsg:{}" + jsonObject.getInt("errcode") + jsonObject.getString("errmsg");
/* 209 */         if (basConfig.getIsdebug().equals("1")) {
/* 210 */           log.info(wrongMessage);
/*     */         }
/*     */       }
/*     */     }
/* 214 */     return accessToken;
/*     */   }
/*     */ 
/*     */   private static WeixinAccessToken getRealAccessToken(WeixinAccessTokenService weixinAccessTokenService)
/*     */   {
/* 224 */     List accessTockenList = weixinAccessTokenService.getAccessTockens();
/* 225 */     return (WeixinAccessToken)accessTockenList.get(0);
/*     */   }
/*     */ 
/*     */   private static void saveAccessToken(WeixinAccessToken accessTocken, WeixinAccessTokenService weixinAccessTokenService)
/*     */   {
/* 233 */     weixinAccessTokenService.save(accessTocken);
/*     */   }
/*     */ 
/*     */   private static void updateAccessToken(WeixinAccessToken accessTocken, WeixinAccessTokenService weixinAccessTokenService)
/*     */   {
/* 241 */     weixinAccessTokenService.updateAccessToken(accessTocken);
/*     */   }
/*     */ 
/*     */   public static String encode(byte[] bstr)
/*     */   {
/* 251 */     return new BASE64Encoder().encode(bstr);
/*     */   }
/*     */ 
/*     */   public static byte[] decode(String str)
/*     */   {
/* 261 */     byte[] bt = null;
/*     */     try {
/* 263 */       BASE64Decoder decoder = new BASE64Decoder();
/* 264 */       bt = decoder.decodeBuffer(str);
/*     */     } catch (IOException e) {
/* 266 */       e.printStackTrace();
/*     */     }
/* 268 */     return bt;
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     weixin.guanjia.core.util.WeixinUtil
 * JD-Core Version:    0.6.2
 */