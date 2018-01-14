/*    */ package com.leeson.core.utils;
/*    */ 
/*    */ import com.leeson.common.utils.ThreadLocalDateUtil;
/*    */ import com.leeson.common.utils.stringUtils;
/*    */ import com.leeson.portal.core.model.AutoLoginMacMap;
/*    */ import java.util.Date;
/*    */ import java.util.Map;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class CheckAutoLoginUtils
/*    */ {
/* 14 */   private static Logger logger = Logger.getLogger(CheckAutoLoginUtils.class);
/*    */ 
/*    */   public static void recordTime(String[] onlineInfo) {
/*    */     try {
/* 18 */       String ikmac = onlineInfo[4];
/* 19 */       String time = onlineInfo[3];
/*    */ 
/* 21 */       Date loginTime = ThreadLocalDateUtil.parse(time);
/* 22 */       String nowString = ThreadLocalDateUtil.format(new Date());
/* 23 */       Date nowTime = ThreadLocalDateUtil.parse(nowString);
/* 24 */       Long costTime = Long.valueOf(nowTime.getTime() - loginTime.getTime());
/*    */ 
/* 26 */       if ((stringUtils.isNotBlank(ikmac)) && (!"error".equals(ikmac))) {
/* 27 */         String[] macTimeInfo = (String[])AutoLoginMacMap.getInstance().getAutoLoginMacMap().get(ikmac);
/* 28 */         if (macTimeInfo != null) {
/* 29 */           Long oldcostTime = Long.valueOf(macTimeInfo[1]);
/* 30 */           macTimeInfo[0] = "";
/* 31 */           macTimeInfo[1] = String.valueOf(costTime.longValue() + oldcostTime.longValue());
/*    */ 
/* 33 */           AutoLoginMacMap.getInstance().getAutoLoginMacMap().put(ikmac, macTimeInfo);
/*    */         }
/*    */       }
/*    */     }
/*    */     catch (Exception e) {
/* 38 */       logger.error("==============异常开始=============");
/* 39 */       logger.error(e);
/* 40 */       logger.error("异常信息", e);
/* 41 */       logger.error("==============异常结束=============");
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.utils.CheckAutoLoginUtils
 * JD-Core Version:    0.6.2
 */