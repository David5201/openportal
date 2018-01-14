/*    */ package com.leeson.core.utils;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ import java.util.regex.Matcher;
/*    */ import java.util.regex.Pattern;
/*    */ 
/*    */ public class IPv4Util
/*    */ {
/*    */   public static long ipToLong(String strIp)
/*    */   {
/* 10 */     long[] ip = new long[4];
/*    */ 
/* 12 */     int position1 = strIp.indexOf(".");
/* 13 */     int position2 = strIp.indexOf(".", position1 + 1);
/* 14 */     int position3 = strIp.indexOf(".", position2 + 1);
/*    */ 
/* 16 */     ip[0] = Long.parseLong(strIp.substring(0, position1));
/* 17 */     ip[1] = Long.parseLong(strIp.substring(position1 + 1, position2));
/* 18 */     ip[2] = Long.parseLong(strIp.substring(position2 + 1, position3));
/* 19 */     ip[3] = Long.parseLong(strIp.substring(position3 + 1));
/* 20 */     return (ip[0] << 24) + (ip[1] << 16) + (ip[2] << 8) + ip[3];
/*    */   }
/*    */ 
/*    */   public static String longToIP(long longIp)
/*    */   {
/* 25 */     StringBuffer sb = new StringBuffer("");
/*    */ 
/* 27 */     sb.append(String.valueOf(longIp >>> 24));
/* 28 */     sb.append(".");
/*    */ 
/* 30 */     sb.append(String.valueOf((longIp & 0xFFFFFF) >>> 16));
/* 31 */     sb.append(".");
/*    */ 
/* 33 */     sb.append(String.valueOf((longIp & 0xFFFF) >>> 8));
/* 34 */     sb.append(".");
/*    */ 
/* 36 */     sb.append(String.valueOf(longIp & 0xFF));
/* 37 */     return sb.toString();
/*    */   }
/*    */ 
/*    */   public static boolean isIP(String addr)
/*    */   {
/* 43 */     if ((addr.length() < 7) || (addr.length() > 15) || ("".equals(addr)))
/*    */     {
/* 45 */       return false;
/*    */     }
/*    */ 
/* 51 */     String rexp = "\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b";
/*    */ 
/* 53 */     Pattern pat = Pattern.compile(rexp);
/*    */ 
/* 55 */     Matcher mat = pat.matcher(addr);
/*    */ 
/* 57 */     boolean ipAddress = mat.find();
/*    */ 
/* 59 */     return ipAddress;
/*    */   }
/*    */ 
/*    */   public static void main(String[] args)
/*    */     throws Exception
/*    */   {
/* 67 */     String ipAddr = "1.168.8.1";
/* 68 */     String ipAddr1 = "192.168.7.254";
/*    */ 
/* 70 */     long ipInt = ipToLong(ipAddr);
/* 71 */     long ipInt1 = ipToLong(ipAddr1);
/*    */ 
/* 74 */     System.out.println("IP: " + ipAddr + "  --> int: " + ipInt);
/* 75 */     System.out.println("IP: " + ipAddr1 + "  --> int: " + ipInt1);
/* 76 */     System.out.println(ipInt > ipInt1);
/*    */ 
/* 78 */     String ipAddrTest = "1.255.255.0";
/* 79 */     System.out.println("is IP: " + isIP(ipAddrTest));
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.utils.IPv4Util
 * JD-Core Version:    0.6.2
 */