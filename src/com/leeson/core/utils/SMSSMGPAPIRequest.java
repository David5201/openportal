/*     */ package com.leeson.core.utils;
/*     */ 
/*     */ import com.leeson.common.utils.stringUtils;
/*     */ import com.leeson.core.bean.Portalbas;
/*     */ import com.leeson.core.bean.Portalsmslogs;
/*     */ import com.leeson.core.service.PortalsmslogsService;
/*     */ import com.leeson.portal.core.model.Config;
/*     */ import com.leeson.portal.core.model.PhoneCodeMap;
/*     */ import com.leeson.portal.core.utils.SpringContextHelper;
/*     */ import com.wondertek.ctmp.protocol.smgp.SMGPApi;
/*     */ import com.wondertek.ctmp.protocol.smgp.SMGPLoginRespMessage;
/*     */ import com.wondertek.ctmp.protocol.smgp.SMGPSubmitMessage;
/*     */ import com.wondertek.ctmp.protocol.util.SequenceGenerator;
/*     */ import java.util.Date;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class SMSSMGPAPIRequest
/*     */ {
/*  19 */   private static Config config = Config.getInstance();
/*  20 */   private static Logger logger = Logger.getLogger(SMSSMGPAPIRequest.class);
/*     */ 
/*  22 */   private static PortalsmslogsService smslogsService = (PortalsmslogsService)
/*  23 */     SpringContextHelper.getBean("portalsmslogsServiceImpl");
/*     */ 
/*     */   public static boolean send(String url, String appId, String appKey, String sign, String template, String text, String phone, Long id)
/*     */   {
/*  27 */     int port = 8890;
/*  28 */     String host = "218.77.121.59";
/*     */     try {
/*  30 */       String[] ts = url.split(":");
/*  31 */       host = ts[0];
/*  32 */       port = Integer.valueOf(ts[1]).intValue();
/*     */     }
/*     */     catch (Exception localException1)
/*     */     {
/*     */     }
/*     */     try {
/*  38 */       String yzm = 
/*  39 */         String.valueOf(Math.round(Math.random() * 8999.0D + 1000.0D));
/*     */ 
/*  41 */       if (stringUtils.isNotBlank(text)) {
/*  42 */         if ("[yzm]".equals(text)) {
/*  43 */           text = "您的验证码是 " + yzm;
/*     */         }
/*  45 */         else if (text.contains("[yzm]"))
/*  46 */           text = text.replace("[yzm]", yzm);
/*     */         else {
/*  48 */           text = "您的验证码是 " + yzm;
/*     */         }
/*     */       }
/*     */       else {
/*  52 */         text = "您的验证码是 " + yzm;
/*     */       }
/*     */ 
/*  55 */       Object[] objs = new Object[2];
/*  56 */       objs[0] = yzm;
/*  57 */       objs[1] = new Date();
/*     */ 
/*  59 */       if (sendAPI(host, port, appId, appKey, template, sign, phone, text)) {
/*  60 */         PhoneCodeMap.getInstance().getPhoneCodeMap().put(phone, objs);
/*     */ 
/*  62 */         Portalsmslogs smslogs = new Portalsmslogs();
/*  63 */         smslogs.setInfo(text);
/*  64 */         smslogs.setPhone(phone);
/*  65 */         smslogs.setSendDate(new Date());
/*  66 */         smslogs.setSid(id);
/*  67 */         smslogs.setType("9");
/*  68 */         smslogsService.addPortalsmslogs(smslogs);
/*  69 */         return true;
/*     */       }
/*  71 */       return false;
/*     */     } catch (Exception e) {
/*  73 */       if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
/*  74 */         logger.error("==============ERROR Start=============");
/*  75 */         logger.error(e);
/*  76 */         logger.error("ERROR INFO ", e);
/*  77 */         logger.error("==============ERROR End=============");
/*     */       }
/*     */     }
/*  79 */     return false;
/*     */   }
/*     */ 
/*     */   private static boolean sendAPI(String host, int port, String clientId, String password, String serviceId, String srcTermId, String phone, String text)
/*     */   {
/*  91 */     String[] mobiles = { phone };
/*  92 */     String msgContent = text;
/*  93 */     byte msgFmt = 15;
/*  94 */     byte needReport = 0;
/*     */ 
/*  96 */     SMGPApi mtApi = new SMGPApi();
/*  97 */     mtApi.setHost(host);
/*  98 */     mtApi.setPort(port);
/*  99 */     mtApi.setClientId(clientId);
/* 100 */     mtApi.setPassword(password);
/* 101 */     mtApi.setVersion((byte)0);
/*     */     try
/*     */     {
/* 104 */       SMGPLoginRespMessage resp = mtApi.connect();
/* 105 */       if ((resp != null) && (resp.getStatus() == 0)) {
/* 106 */         if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1"))
/* 107 */           logger.info("SMGP Connect Success !!");
/*     */       }
/*     */       else {
/* 110 */         if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
/* 111 */           logger.info("SMGP Connect Error , Status=" + resp.getStatus());
/*     */         }
/* 113 */         return false;
/*     */       }
/*     */     } catch (Exception e) {
/* 116 */       if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
/* 117 */         logger.error("==============SMGP Connect ERROR Start=============");
/* 118 */         logger.error(e);
/* 119 */         logger.error("ERROR INFO ", e);
/* 120 */         logger.error("==============SMGP Connect ERROR End=============");
/*     */       }
/* 122 */       return false;
/*     */     }
/*     */     try
/*     */     {
/* 126 */       SMGPSubmitMessage submit = new SMGPSubmitMessage();
/* 127 */       submit.setSequenceNumber(SequenceGenerator.nextSequence());
/*     */ 
/* 129 */       submit.setPriority((byte)3);
/* 130 */       submit.setSrcTermId(srcTermId);
/* 131 */       submit.setChargeTermId(srcTermId);
/* 132 */       submit.setServiceId(serviceId);
/* 133 */       submit.setDestTermIdArray(mobiles);
/* 134 */       submit.setMsgFmt(msgFmt);
/* 135 */       submit.setMsgContent(msgContent);
/* 136 */       submit.setNeedReport(needReport);
/* 137 */       mtApi.sendMsg(submit);
/* 138 */       if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
/* 139 */         logger.info("SMGP Send Success , Status=" + submit);
/*     */       }
/* 141 */       mtApi.close();
/* 142 */       return true;
/*     */     } catch (Exception e) {
/* 144 */       if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
/* 145 */         logger.error("==============SMGP Send ERROR Start=============");
/* 146 */         logger.error(e);
/* 147 */         logger.error("ERROR INFO ", e);
/* 148 */         logger.error("==============SMGP Send ERROR End=============");
/*     */       }
/* 150 */       mtApi.close();
/* 151 */     }return false;
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.utils.SMSSMGPAPIRequest
 * JD-Core Version:    0.6.2
 */