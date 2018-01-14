/*    */ package AC.Utils;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ public class Make_Challenge
/*    */ {
/*    */   public static String getChallenge()
/*    */   {
/*  8 */     String base = "abcdefghijklmnopqrstuvwxyz0123456789";
/*  9 */     int length = 16;
/* 10 */     Random random = new Random();
/* 11 */     StringBuffer sb = new StringBuffer();
/* 12 */     for (int i = 0; i < length; i++) {
/* 13 */       int number = random.nextInt(base.length());
/* 14 */       sb.append(base.charAt(number));
/*    */     }
/* 16 */     return sb.toString();
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     AC.Utils.Make_Challenge
 * JD-Core Version:    0.6.2
 */