/*    */ package weixin.service.impl;
/*    */ 
/*    */ import java.io.FileInputStream;
/*    */ import java.io.PrintStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.Properties;
/*    */ import javax.servlet.ServletContext;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import org.springframework.stereotype.Service;
/*    */ import weixin.entity.WeixinAccount;
/*    */ import weixin.service.WeixinAccountService;
/*    */ 
/*    */ @Service
/*    */ public class WeixinAccountServiceImpl
/*    */   implements WeixinAccountService
/*    */ {
/* 18 */   private static List<WeixinAccount> list = new ArrayList();
/*    */ 
/*    */   public List<WeixinAccount> getList(HttpServletRequest request) {
/* 21 */     if (list.size() == 0) {
/* 22 */       String appid = "null";
/* 23 */       String appsecret = "null";
/* 24 */       String token = "null";
/* 25 */       String AESKey = "null";
/*    */       try {
/* 27 */         System.out.println("Read wx.properties File ....");
/* 28 */         String path = request.getServletContext().getRealPath("/");
/* 29 */         Properties props = new Properties();
/* 30 */         String propPath = path + "wx.properties";
/*    */ 
/* 32 */         FileInputStream in = new FileInputStream(propPath);
/* 33 */         props.load(in);
/* 34 */         appid = props.getProperty("appid");
/* 35 */         appsecret = props.getProperty("appsecret");
/* 36 */         token = props.getProperty("token");
/* 37 */         AESKey = props.getProperty("AESKey");
/* 38 */         in.close();
/*    */       } catch (Exception localException) {
/*    */       }
/* 41 */       WeixinAccount account = new WeixinAccount();
/* 42 */       account.setAppid(appid);
/* 43 */       account.setAppsecret(appsecret);
/* 44 */       account.setAccounttoken(token);
/* 45 */       account.setEncodingAESKey(AESKey);
/* 46 */       list.add(account);
/*    */     }
/*    */ 
/* 49 */     return list;
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     weixin.service.impl.WeixinAccountServiceImpl
 * JD-Core Version:    0.6.2
 */