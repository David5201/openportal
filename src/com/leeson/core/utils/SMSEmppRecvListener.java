/*     */ package com.leeson.core.utils;
/*     */ 
/*     */ import com.leeson.core.bean.Portalbas;
/*     */ import com.leeson.portal.core.model.Config;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.wyrsoft.smsapi.MessageClient;
/*     */ import org.wyrsoft.smsapi.MessageClientFactory;
/*     */ import org.wyrsoft.smsapi.MessageListener;
/*     */ import org.wyrsoft.smsapi.SmsDR;
/*     */ import org.wyrsoft.smsapi.SmsMO;
/*     */ import org.wyrsoft.smsapi.SmsMT;
/*     */ import org.wyrsoft.smsapi.SmsUtil;
/*     */ 
/*     */ public class SMSEmppRecvListener
/*     */   implements MessageListener
/*     */ {
/*  33 */   private static Config config = Config.getInstance();
/*  34 */   private static Logger log = Logger.getLogger(SMSEmppRecvListener.class);
/*     */ 
/*  36 */   private static MessageClient client = null;
/*     */ 
/*  38 */   private int drCount = 0;
/*     */ 
/*  40 */   private int mtCount = 0;
/*     */ 
/*  42 */   private static SMSEmppRecvListener listener = new SMSEmppRecvListener();
/*     */ 
/*     */   private SMSEmppRecvListener() {
/*  45 */     client = MessageClientFactory.createClient("empp2.0", this);
/*     */   }
/*     */ 
/*     */   public static SMSEmppRecvListener getListener() {
/*  49 */     return listener;
/*     */   }
/*     */ 
/*     */   public MessageClient getClient() {
/*  53 */     return client;
/*     */   }
/*     */ 
/*     */   public void initialize(String serverAddress, int port, String username, String password)
/*     */     throws Exception
/*     */   {
/*  61 */     client.setUsername(username);
/*  62 */     client.setPassword(password);
/*  63 */     client.setRemoteAddr(serverAddress);
/*  64 */     client.setRemotePort(port);
/*  65 */     client.setVersion(32);
/*     */ 
/*  69 */     client.connect();
/*     */   }
/*     */ 
/*     */   public void onBindAck(int arg0)
/*     */   {
/*  76 */     if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1"))
/*  77 */       log.info("onBound: " + arg0);
/*     */   }
/*     */ 
/*     */   public void onClosed(ArrayList arg0)
/*     */   {
/*  89 */     if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1"))
/*  90 */       log.info("onClosed:" + arg0);
/*     */   }
/*     */ 
/*     */   public void onSmsDR(SmsDR dr)
/*     */   {
/*  99 */     this.drCount += 1;
/* 100 */     if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
/* 101 */       log.info("onSmsDR " + this.drCount);
/* 102 */       log.info("Source-Addr: " + dr.getSourceAddr());
/* 103 */       log.info("Message-ID : " + dr.getMessageID());
/* 104 */       log.info("ErrorCode  : " + dr.getErrorCode());
/* 105 */       log.info("Receipt    : " + dr.getReceipt());
/* 106 */       log.info("Status     : " + dr.getStatus());
/*     */     }
/*     */   }
/*     */ 
/*     */   public void onSmsMO(SmsMO mo)
/*     */   {
/* 115 */     if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
/* 116 */       log.info("onSmsMO");
/* 117 */       log.info("Data-Coding  : " + mo.getDataCoding());
/* 118 */       log.info("Dest-Addr    : " + mo.getDestAddr());
/* 119 */       log.info("Content      : " + SmsUtil.getLetter(mo));
/* 120 */       log.info("Service-Code : " + mo.getServiceCode());
/* 121 */       log.info("Source-Addr  : " + mo.getSourceAddr());
/*     */     }
/*     */   }
/*     */ 
/*     */   public void onSmsMTAck(SmsMT ack)
/*     */   {
/* 130 */     this.mtCount += 1;
/* 131 */     if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
/* 132 */       log.info("onSmsMTAck " + this.mtCount);
/* 133 */       log.info("Message-ID: " + ack.getMessageID());
/* 134 */       log.info("Result    : " + ack.getResult());
/*     */     }
/*     */   }
/*     */ 
/*     */   public void onMmsDR(SmsDR mmsdr)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void onMmsMO(SmsMO mmsdr)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void onMmsRR(SmsDR mmsdr)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void onMmsMTAck(SmsMT mmsmt)
/*     */   {
/*     */   }
/*     */ 
/*     */   public int send(String number, String content)
/*     */   {
/* 181 */     SmsMT mt = new SmsMT();
/*     */ 
/* 184 */     mt.setRegisteredDelivery(0);
/*     */ 
/* 188 */     mt.setUnicodeMessage(content);
/*     */ 
/* 197 */     String[] addresses = number.split(";");
/* 198 */     for (int i = 0; i < addresses.length; i++) {
/* 199 */       mt.addDestAddr(addresses[i]);
/*     */     }
/*     */ 
/* 204 */     int timeout = 0;
/*     */     try {
/* 206 */       client.send(mt, timeout);
/*     */     } catch (Exception e) {
/* 208 */       if ((((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) && 
/* 209 */         (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1"))) {
/* 210 */         log.error("==============ERROR Start=============");
/* 211 */         log.error(e);
/* 212 */         log.error("ERROR INFO ", e);
/* 213 */         log.error("==============ERROR End=============");
/*     */       }
/*     */ 
/* 216 */       return -1;
/*     */     }
/*     */ 
/* 220 */     if (timeout == 0) {
/* 221 */       return 0;
/*     */     }
/*     */ 
/* 225 */     return mt.getResult();
/*     */   }
/*     */ 
/*     */   public void close()
/*     */   {
/* 232 */     client.close();
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.utils.SMSEmppRecvListener
 * JD-Core Version:    0.6.2
 */