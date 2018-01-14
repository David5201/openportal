/*    */ package AC.Utils;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ 
/*    */ public class WR
/*    */ {
/*    */   public static String Getbyte2HexString(byte[] b)
/*    */   {
/*  7 */     StringBuilder sb = new StringBuilder();
/*    */ 
/*  9 */     for (int i = 0; i < b.length; i++) {
/* 10 */       String hex = Integer.toHexString(b[i] & 0xFF);
/* 11 */       if (hex.length() == 1) {
/* 12 */         hex = '0' + hex;
/*    */       }
/* 14 */       sb.append(hex);
/*    */     }
/* 16 */     return "[" + sb.toString() + "]";
/*    */   }
/*    */ 
/*    */   public static void space() {
/* 20 */     System.out
/* 21 */       .println("########################################################");
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     AC.Utils.WR
 * JD-Core Version:    0.6.2
 */