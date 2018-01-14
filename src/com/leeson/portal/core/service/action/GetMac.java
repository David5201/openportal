/*    */ package com.leeson.portal.core.service.action;
/*    */ 
/*    */ import com.leeson.core.bean.Portalbas;
/*    */ import com.leeson.portal.core.model.Config;
/*    */ import com.leeson.portal.core.model.ipMacMap;
/*    */ import com.leeson.portal.core.service.utils.PortalUtil;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class GetMac
/*    */ {
/* 18 */   private static Logger log = Logger.getLogger(GetMac.class);
/* 19 */   private static Config config = Config.getInstance();
/*    */ 
/*    */   public static void getMac(byte[] data, String basIp, byte[] userIpBuf) {
/* 22 */     Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
/* 23 */     String userIp = "";
/* 24 */     for (int i = 0; i < 4; i++) {
/* 25 */       if (i < 3)
/* 26 */         userIp = userIp + (userIpBuf[i] & 0xFF) + ".";
/*    */       else {
/* 28 */         userIp = userIp + (userIpBuf[i] & 0xFF);
/*    */       }
/*    */     }
/*    */ 
/* 32 */     String mac = "";
/* 33 */     Boolean state = Boolean.valueOf(false);
/* 34 */     if ((data[15] & 0xFF) != 0) {
/* 35 */       int pos = 0;
/* 36 */       if ((data[0] & 0xFF) == 1) {
/* 37 */         pos = 16;
/*    */       }
/* 39 */       if ((data[0] & 0xFF) == 2) {
/* 40 */         pos = 32;
/*    */       }
/* 42 */       int AN = data[15] & 0xFF;
/*    */ 
/* 44 */       int num = 1;
/* 45 */       while (num <= AN) {
/* 46 */         if (pos >= data.length)
/*    */         {
/*    */           break;
/*    */         }
/* 50 */         int type = data[pos] & 0xFF;
/* 51 */         pos++;
/*    */ 
/* 53 */         if (pos >= data.length)
/*    */         {
/*    */           break;
/*    */         }
/* 57 */         int len = (data[pos] & 0xFF) - 2;
/* 58 */         pos++;
/*    */ 
/* 60 */         byte[] buf = new byte[len];
/* 61 */         for (int l = 0; l < buf.length; l++) {
/* 62 */           if (pos + l >= data.length) {
/*    */             break;
/*    */           }
/* 65 */           buf[l] = data[(pos + l)];
/*    */         }
/* 67 */         pos += len;
/* 68 */         if (type == 11) {
/* 69 */           mac = PortalUtil.Getbyte2MacString(buf);
/* 70 */           state = Boolean.valueOf(true);
/* 71 */           break;
/*    */         }
/* 73 */         num++;
/*    */       }
/*    */     }
/*    */ 
/* 77 */     if ((state.booleanValue()) && (!mac.equals("")))
/* 78 */       ipMacMap.getInstance().getIpMacMap().put(userIp + ":" + basIp, mac.trim().toLowerCase());
/*    */     else {
/* 80 */       ipMacMap.getInstance().getIpMacMap().put(userIp + ":" + basIp, "error");
/*    */     }
/* 82 */     if (basConfig.getIsdebug().equals("1"))
/* 83 */       log.info("用户IP: " + userIp + " BasIp: " + basIp + "Mac地址是：" + mac.trim());
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.service.action.GetMac
 * JD-Core Version:    0.6.2
 */