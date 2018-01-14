/*    */ package com.leeson.core.utils;
/*    */ 
/*    */ import com.leeson.common.utils.ThreadLocalDateUtil;
/*    */ import com.leeson.common.utils.stringUtils;
/*    */ import com.leeson.portal.core.model.MacLimitMap;
/*    */ import com.leeson.portal.core.model.UserLimitMap;
/*    */ import java.util.Date;
/*    */ import java.util.Map;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class CheckTimeUtils
/*    */ {
/* 15 */   private static Logger logger = Logger.getLogger(CheckTimeUtils.class);
/*    */ 
/*    */   public static void recordTime(String[] onlineInfo) {
/*    */     try {
/* 19 */       String phone = onlineInfo[0];
/* 20 */       String ikmac = onlineInfo[4];
/* 21 */       String time = onlineInfo[3];
/*    */ 
/* 23 */       Date loginTime = ThreadLocalDateUtil.parse(time);
/* 24 */       String nowString = ThreadLocalDateUtil.format(new Date());
/* 25 */       Date nowTime = ThreadLocalDateUtil.parse(nowString);
/* 26 */       Long costTime = Long.valueOf(nowTime.getTime() - loginTime.getTime());
/*    */ 
/* 29 */       String[] userTimeInfo = (String[])UserLimitMap.getInstance().getUserLimitMap().get(phone);
/* 30 */       if (userTimeInfo != null) {
/* 31 */         Long oldcostTime = Long.valueOf(userTimeInfo[1]);
/* 32 */         userTimeInfo[0] = "";
/* 33 */         userTimeInfo[1] = String.valueOf(costTime.longValue() + oldcostTime.longValue());
/* 34 */         userTimeInfo[2] = "0";
/* 35 */         UserLimitMap.getInstance().getUserLimitMap().put(phone, userTimeInfo);
/*    */       }
/*    */ 
/* 40 */       if ((stringUtils.isNotBlank(ikmac)) && (!"error".equals(ikmac))) {
/* 41 */         String[] macTimeInfo = (String[])MacLimitMap.getInstance().getMacLimitMap().get(ikmac);
/* 42 */         if (macTimeInfo != null) {
/* 43 */           Long oldcostTime = Long.valueOf(macTimeInfo[1]);
/* 44 */           macTimeInfo[0] = "";
/* 45 */           macTimeInfo[1] = String.valueOf(costTime.longValue() + oldcostTime.longValue());
/* 46 */           macTimeInfo[2] = "0";
/* 47 */           MacLimitMap.getInstance().getMacLimitMap().put(ikmac, macTimeInfo);
/*    */         }
/*    */       }
/*    */     }
/*    */     catch (Exception e) {
/* 52 */       logger.error("==============异常开始=============");
/* 53 */       logger.error(e);
/* 54 */       logger.error("异常信息", e);
/* 55 */       logger.error("==============异常结束=============");
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.utils.CheckTimeUtils
 * JD-Core Version:    0.6.2
 */