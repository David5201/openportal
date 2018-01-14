/*    */ package com.leeson.portal.core.service.action.v2.pap;
/*    */ 
/*    */ import com.leeson.portal.core.service.action.v2.PublicV2;
/*    */ 
/*    */ public class PAP_Quit_V2
/*    */ {
/*    */   public static boolean quit(int type, String Bas_IP, int bas_PORT, int timeout_Sec, byte[] SerialNo, byte[] UserIP, String sharedSecret)
/*    */   {
/* 15 */     byte[] Req_Quit = new byte[32];
/* 16 */     Req_Quit[0] = 2;
/* 17 */     Req_Quit[1] = 5;
/* 18 */     Req_Quit[2] = 1;
/* 19 */     Req_Quit[3] = 0;
/* 20 */     Req_Quit[4] = SerialNo[0];
/* 21 */     Req_Quit[5] = SerialNo[1];
/* 22 */     Req_Quit[6] = 0;
/* 23 */     Req_Quit[7] = 0;
/* 24 */     Req_Quit[8] = UserIP[0];
/* 25 */     Req_Quit[9] = UserIP[1];
/* 26 */     Req_Quit[10] = UserIP[2];
/* 27 */     Req_Quit[11] = UserIP[3];
/* 28 */     Req_Quit[12] = 0;
/* 29 */     Req_Quit[13] = 0;
/* 30 */     Req_Quit[14] = 0;
/* 31 */     Req_Quit[15] = 0;
/* 32 */     return PublicV2.choose(type, Req_Quit, timeout_Sec, Bas_IP, bas_PORT, 
/* 33 */       sharedSecret);
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.service.action.v2.pap.PAP_Quit_V2
 * JD-Core Version:    0.6.2
 */