/*    */ package com.leeson.portal.core.service.utils;
/*    */ 
/*    */ import com.leeson.core.bean.Portalbas;
/*    */ import com.leeson.portal.core.model.Config;
/*    */ import java.security.MessageDigest;
/*    */ import java.security.NoSuchAlgorithmException;
/*    */ import java.util.Map;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class ACKAuthenticator
/*    */ {
/* 20 */   private static Logger log = Logger.getLogger(ACKAuthenticator.class);
/* 21 */   private static Config config = Config.getInstance();
/*    */ 
/*    */   public static byte[] MK_Authen(byte[] Buff, byte[] Attrs, String sharedSecret, byte[] reqAuthen)
/*    */   {
/* 38 */     Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
/* 39 */     byte[] Secret = sharedSecret.getBytes();
/* 40 */     byte[] Authen = new byte[16];
/*    */ 
/* 42 */     byte[] buf = new byte[Buff.length + 16 + Attrs.length + Secret.length];
/*    */ 
/* 44 */     for (int i = 0; i < Buff.length; i++) {
/* 45 */       buf[i] = Buff[i];
/*    */     }
/* 47 */     for (int i = 0; i < 16; i++) {
/* 48 */       buf[(Buff.length + i)] = reqAuthen[i];
/*    */     }
/* 50 */     if (Attrs.length > 0) {
/* 51 */       for (int i = 0; i < Attrs.length; i++) {
/* 52 */         buf[(Buff.length + 16 + i)] = Attrs[i];
/*    */       }
/* 54 */       for (int i = 0; i < Secret.length; i++)
/* 55 */         buf[(Buff.length + 16 + Attrs.length + i)] = Secret[i];
/*    */     }
/*    */     else {
/* 58 */       for (int i = 0; i < Secret.length; i++) {
/* 59 */         buf[(Buff.length + 16 + i)] = Secret[i];
/*    */       }
/*    */ 
/*    */     }
/*    */ 
/*    */     try
/*    */     {
/* 70 */       MessageDigest md = MessageDigest.getInstance("MD5");
/* 71 */       md.update(buf);
/* 72 */       Authen = md.digest();
/* 73 */       if (basConfig.getIsdebug().equals("1"))
/* 74 */         log.info("生成Request Authenticator :" + 
/* 75 */           PortalUtil.Getbyte2HexString(Authen));
/*    */     }
/*    */     catch (NoSuchAlgorithmException e)
/*    */     {
/* 79 */       if (basConfig.getIsdebug().equals("1")) {
/* 80 */         log.info("生成Request Authenticator出错！");
/*    */       }
/*    */     }
/*    */ 
/* 84 */     return Authen;
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.service.utils.ACKAuthenticator
 * JD-Core Version:    0.6.2
 */