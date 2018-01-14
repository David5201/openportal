/*    */ package com.leeson.portal.core.service.utils;
/*    */ 
/*    */ import com.leeson.core.bean.Portalbas;
/*    */ import com.leeson.portal.core.model.Config;
/*    */ import java.io.UnsupportedEncodingException;
/*    */ import java.security.MessageDigest;
/*    */ import java.security.NoSuchAlgorithmException;
/*    */ import java.util.Map;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class ChapPassword
/*    */ {
/* 21 */   private static Logger log = Logger.getLogger(ChapPassword.class);
/* 22 */   private static Config config = Config.getInstance();
/*    */ 
/*    */   public static byte[] MK_ChapPwd(byte[] ReqID, byte[] Challenge, byte[] usp) throws UnsupportedEncodingException
/*    */   {
/* 26 */     Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
/* 27 */     byte[] ChapPwd = new byte[16];
/*    */ 
/* 29 */     byte[] buf = new byte[1 + usp.length + Challenge.length];
/*    */ 
/* 36 */     buf[0] = ReqID[1];
/* 37 */     for (int i = 0; i < usp.length; i++) {
/* 38 */       buf[(1 + i)] = usp[i];
/*    */     }
/* 40 */     for (int i = 0; i < Challenge.length; i++) {
/* 41 */       buf[(1 + usp.length + i)] = Challenge[i];
/*    */     }
/*    */ 
/*    */     try
/*    */     {
/* 51 */       MessageDigest md = MessageDigest.getInstance("MD5");
/* 52 */       md.update(buf);
/* 53 */       ChapPwd = md.digest();
/* 54 */       if (basConfig.getIsdebug().equals("1"))
/* 55 */         log.info("生成Chap-Password" + PortalUtil.Getbyte2HexString(ChapPwd));
/*    */     }
/*    */     catch (NoSuchAlgorithmException e)
/*    */     {
/* 59 */       if (basConfig.getIsdebug().equals("1")) {
/* 60 */         log.info("生成Chap-Password出错！");
/*    */       }
/*    */     }
/*    */ 
/* 64 */     return ChapPwd;
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.service.utils.ChapPassword
 * JD-Core Version:    0.6.2
 */