/*    */ package com.leeson.portal.core.utils;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ import java.net.InetAddress;
/*    */ import java.net.NetworkInterface;
/*    */ import java.util.Enumeration;
/*    */ 
/*    */ public final class NetworkUtils
/*    */ {
/*    */   private static String getMacAddress()
/*    */   {
/* 10 */     StringBuilder macAddress = new StringBuilder();
/*    */     try {
/* 12 */       Enumeration el = 
/* 13 */         NetworkInterface.getNetworkInterfaces();
/* 14 */       while (el.hasMoreElements()) {
/* 15 */         byte[] mac = ((NetworkInterface)el.nextElement()).getHardwareAddress();
/* 16 */         if (mac != null)
/*    */         {
/* 18 */           StringBuilder builder = new StringBuilder();
/* 19 */           for (int i = 0; i < mac.length; i++) {
/* 20 */             builder.append(hexByte(mac[i]));
/* 21 */             if (i < mac.length - 1) {
/* 22 */               builder.append("-");
/*    */             }
/*    */           }
/*    */ 
/* 26 */           macAddress.append("[");
/* 27 */           macAddress.append(builder);
/* 28 */           macAddress.append("]");
/*    */         }
/*    */       }
/*    */     } catch (Exception localException) {
/*    */     }
/* 33 */     String result = macAddress.toString();
/* 34 */     result = result.replace("[]", "");
/* 35 */     result = result.replace("[00-00-00-00-00-00-00-e0]", "");
/* 36 */     result = result.toUpperCase();
/* 37 */     return result;
/*    */   }
/*    */ 
/*    */   private static String hexByte(byte b) {
/* 41 */     String s = "000000" + Integer.toHexString(b);
/* 42 */     return s.substring(s.length() - 2);
/*    */   }
/*    */ 
/*    */   public static String getInfo() {
/* 46 */     StringBuilder sb = new StringBuilder();
/* 47 */     sb.append("Version : OpenPortalServer V3.10.0.0 2017-07-01");
/* 48 */     sb.append("    ");
/* 49 */     sb.append(" OS : " + System.getProperty("os.name"));
/* 50 */     sb.append("    ");
/* 51 */     sb.append(" MAC : " + getMacAddress());
/* 52 */     sb.append("    ");
/* 53 */     sb.append(" IP : ");
/* 54 */     String configString = "";
/*    */     try {
/* 56 */       configString = InetAddress.getLocalHost().getHostAddress();
/*    */     } catch (Exception e) {
/* 58 */       configString = "ERROR";
/*    */     }
/* 60 */     sb.append(configString);
/* 61 */     sb.append("    ");
/* 62 */     sb.append(" HOST NAME : ");
/*    */     try {
/* 64 */       configString = InetAddress.getLocalHost().getHostName();
/*    */     } catch (Exception e) {
/* 66 */       configString = "ERROR";
/*    */     }
/* 68 */     sb.append(configString);
/* 69 */     return sb.toString();
/*    */   }
/*    */ 
/*    */   public static void main(String[] args) {
/* 73 */     System.out.println(getInfo());
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.utils.NetworkUtils
 * JD-Core Version:    0.6.2
 */