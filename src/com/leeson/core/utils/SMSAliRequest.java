/*    */ package com.leeson.core.utils;
/*    */ 
/*    */ import com.leeson.core.bean.Portalsmslogs;
/*    */ import com.leeson.core.service.PortalsmslogsService;
/*    */ import com.leeson.portal.core.model.PhoneCodeMap;
/*    */ import com.leeson.portal.core.utils.SpringContextHelper;
/*    */ import com.taobao.api.DefaultTaobaoClient;
/*    */ import com.taobao.api.TaobaoClient;
/*    */ import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
/*    */ import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
/*    */ import java.io.PrintStream;
/*    */ import java.util.Date;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class SMSAliRequest
/*    */ {
/* 15 */   private static PortalsmslogsService smslogsService = (PortalsmslogsService)
/* 16 */     SpringContextHelper.getBean("portalsmslogsServiceImpl");
/*    */ 
/*    */   public static boolean send(String url, String phone, String appkey, String secret, String smsFreeSignName, String smsTemplateCode, String company, Long id)
/*    */   {
/*    */     try
/*    */     {
/* 30 */       String text = 
/* 31 */         String.valueOf(Math.round(Math.random() * 8999.0D + 1000.0D));
/*    */ 
/* 33 */       TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
/* 34 */       AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
/* 35 */       req.setExtend("");
/* 36 */       req.setSmsType("normal");
/* 37 */       req.setSmsFreeSignName(smsFreeSignName);
/* 38 */       req.setSmsParamString("{code:'" + text + "'}");
/* 39 */       req.setSmsParamString("{code:'" + text + "',product:'" + company + "'}");
/* 40 */       req.setRecNum(phone);
/* 41 */       req.setSmsTemplateCode(smsTemplateCode);
/* 42 */       AlibabaAliqinFcSmsNumSendResponse rsp = (AlibabaAliqinFcSmsNumSendResponse)client.execute(req);
/* 43 */       String result = rsp.getBody();
/*    */ 
/* 45 */       System.out.println(result);
/*    */ 
/* 47 */       if ((!result.contains("error_response")) && (result.contains("\"success\":true"))) {
/* 48 */         Object[] objs = new Object[2];
/* 49 */         objs[0] = text;
/* 50 */         objs[1] = new Date();
/* 51 */         PhoneCodeMap.getInstance().getPhoneCodeMap().put(phone, objs);
/*    */ 
/* 53 */         Portalsmslogs smslogs = new Portalsmslogs();
/* 54 */         smslogs.setInfo(text);
/* 55 */         smslogs.setPhone(phone);
/* 56 */         smslogs.setSendDate(new Date());
/* 57 */         smslogs.setSid(id);
/* 58 */         smslogs.setType("3");
/* 59 */         smslogsService.addPortalsmslogs(smslogs);
/* 60 */         return true;
/*    */       }
/* 62 */       return false;
/*    */     }
/*    */     catch (Exception e)
/*    */     {
/* 66 */       System.out.println("error" + e);
/* 67 */     }return false;
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.utils.SMSAliRequest
 * JD-Core Version:    0.6.2
 */