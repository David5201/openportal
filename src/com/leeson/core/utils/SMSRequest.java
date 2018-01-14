/*    */ package com.leeson.core.utils;
/*    */ 
/*    */ import com.leeson.core.bean.Portalsmslogs;
/*    */ import com.leeson.core.service.PortalsmslogsService;
/*    */ import com.leeson.portal.core.model.PhoneCodeMap;
/*    */ import com.leeson.portal.core.utils.SpringContextHelper;
/*    */ import java.io.BufferedReader;
/*    */ import java.io.InputStreamReader;
/*    */ import java.io.PrintStream;
/*    */ import java.net.URL;
/*    */ import java.net.URLConnection;
/*    */ import java.util.Date;
/*    */ import java.util.Map;
/*    */ import net.sf.json.JSONObject;
/*    */ 
/*    */ public class SMSRequest
/*    */ {
/* 17 */   private static PortalsmslogsService smslogsService = (PortalsmslogsService)
/* 18 */     SpringContextHelper.getBean("portalsmslogsServiceImpl");
/*    */ 
/*    */   public static boolean send(String url, String phone, String key, Long id)
/*    */   {
/* 30 */     String result = "";
/* 31 */     BufferedReader in = null;
/*    */     try {
/* 33 */       String urlNameString = url + "?gwid=" + key + "&mobile=" + phone;
/* 34 */       URL realUrl = new URL(urlNameString);
/*    */ 
/* 36 */       URLConnection connection = realUrl.openConnection();
/*    */ 
/* 38 */       connection.setRequestProperty("accept", "*/*");
/* 39 */       connection.setRequestProperty("connection", "Keep-Alive");
/* 40 */       connection.setRequestProperty("user-agent", 
/* 41 */         "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
/*    */ 
/* 43 */       connection.connect();
/*    */ 
/* 45 */       in = new BufferedReader(new InputStreamReader(
/* 46 */         connection.getInputStream()));
/*    */       String line;
/* 48 */       while ((line = in.readLine()) != null)
/*    */       {
/* 49 */         result = result + line;
/*    */       }
/* 51 */       System.out.println("SMS_API Result= " + result);
/* 52 */       JSONObject jsonObj = JSONObject.fromObject(result);
/* 53 */       String status = jsonObj.getString("status");
/* 54 */       if ("1".equals(status))
/*    */       {
/* 56 */         JSONObject data = jsonObj.getJSONObject("data");
/* 57 */         String yzm = data.getString("sms_code");
/* 58 */         System.out.println("phone=" + phone + " code=" + yzm);
/* 59 */         Object[] objs = new Object[2];
/* 60 */         objs[0] = yzm;
/* 61 */         objs[1] = new Date();
/* 62 */         PhoneCodeMap.getInstance().getPhoneCodeMap().put(phone, objs);
/*    */ 
/* 64 */         Portalsmslogs smslogs = new Portalsmslogs();
/* 65 */         smslogs.setInfo(yzm);
/* 66 */         smslogs.setPhone(phone);
/* 67 */         smslogs.setSendDate(new Date());
/* 68 */         smslogs.setSid(id);
/* 69 */         smslogs.setType("0");
/* 70 */         smslogsService.addPortalsmslogs(smslogs);
/* 71 */         return true;
/*    */       }
/* 73 */       return false;
/*    */     }
/*    */     catch (Exception e) {
/* 76 */       System.out.println("发送GET请求出现异常！" + e);
/* 77 */       return false;
/*    */     }
/*    */     finally
/*    */     {
/*    */       try {
/* 82 */         if (in != null)
/* 83 */           in.close();
/*    */       }
/*    */       catch (Exception e2) {
/* 86 */         System.out.println("关闭资源出现异常！" + e2);
/*    */       }
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.utils.SMSRequest
 * JD-Core Version:    0.6.2
 */