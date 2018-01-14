/*    */ package com.leeson.radius.core.utils;
/*    */ 
/*    */ import com.leeson.common.utils.stringUtils;
/*    */ import com.leeson.radius.core.model.RadiusOnlineMap;
/*    */ import java.util.Iterator;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ 
/*    */ public class OnlineLimit
/*    */ {
/*    */   public static boolean macLimit(String username, String userMac, int limitCount)
/*    */   {
/* 11 */     if ((stringUtils.isNotBlank(userMac)) && 
/* 12 */       (limitCount > 0)) {
/* 13 */       int count = 0;
/* 14 */       Iterator iterator = RadiusOnlineMap.getInstance()
/* 15 */         .getRadiusOnlineMap().keySet().iterator();
/* 16 */       while (iterator.hasNext()) {
/* 17 */         Object o = iterator.next();
/* 18 */         String t = o.toString();
/* 19 */         String[] loginInfo = 
/* 20 */           (String[])RadiusOnlineMap.getInstance()
/* 20 */           .getRadiusOnlineMap().get(t);
/* 21 */         String haveUsername = loginInfo[4];
/* 22 */         if (username.equals(haveUsername)) {
/* 23 */           count++;
/*    */         }
/*    */       }
/* 26 */       if (count >= limitCount) {
/* 27 */         return false;
/*    */       }
/*    */     }
/*    */ 
/* 31 */     return true;
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.radius.core.utils.OnlineLimit
 * JD-Core Version:    0.6.2
 */