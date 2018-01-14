/*     */ package com.leeson.core.utils;
/*     */ 
/*     */ import com.leeson.common.utils.stringUtils;
/*     */ import com.leeson.core.bean.Portalaccount;
/*     */ import com.leeson.core.bean.Portalaccountmacs;
/*     */ import com.leeson.core.bean.Portalsmslogs;
/*     */ import com.leeson.core.query.PortalaccountQuery;
/*     */ import com.leeson.core.query.PortalaccountmacsQuery;
/*     */ import com.leeson.core.service.PortalaccountService;
/*     */ import com.leeson.core.service.PortalaccountmacsService;
/*     */ import com.leeson.core.service.PortalsmslogsService;
/*     */ import com.leeson.portal.core.model.PhoneCodeMap;
/*     */ import com.leeson.portal.core.model.PhoneUserMap;
/*     */ import com.leeson.portal.core.utils.SpringContextHelper;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import javax.servlet.ServletContext;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import net.sf.json.JSONObject;
/*     */ import org.apache.cxf.endpoint.Client;
/*     */ import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
/*     */ 
/*     */ public class SMSProRequest
/*     */ {
/*  30 */   private static PortalaccountmacsService macsService = (PortalaccountmacsService)
/*  31 */     SpringContextHelper.getBean("portalaccountmacsServiceImpl");
/*     */ 
/*  32 */   private static PortalaccountService accService = (PortalaccountService)
/*  33 */     SpringContextHelper.getBean("portalaccountServiceImpl");
/*     */ 
/*  34 */   private static PortalsmslogsService smslogsService = (PortalsmslogsService)
/*  35 */     SpringContextHelper.getBean("portalsmslogsServiceImpl");
/*     */ 
/*  39 */   private static String userName = "";
/*     */ 
/*  44 */   private static String pwd = "";
/*     */ 
/*  55 */   private static String appServiceId = "";
/*     */   private static final String messageServiceId = "133df0de-b11e-445a-b551-c5416f61b80e";
/*     */   private static final String userServiceId = "50549c60-f2d5-4cfb-8bdc-249f26664c5e";
/*     */ 
/*     */   public static boolean send(String url, String phone, String mac, String text, HttpServletRequest request, Long id)
/*     */   {
/*     */     try
/*     */     {
/*  73 */       init(request);
/*     */ 
/*  75 */       String yzm = String.valueOf(Math.round(Math.random() * 8999.0D + 1000.0D));
/*     */ 
/*  77 */       if (stringUtils.isNotBlank(text)) {
/*  78 */         if (text.contains("[yzm]"))
/*  79 */           text = text.replace("[yzm]", yzm);
/*     */         else
/*  81 */           text = yzm;
/*     */       }
/*     */       else {
/*  84 */         text = yzm;
/*     */       }
/*  86 */       String messageXml = "<Message><content>" + text + "</content><types>sms,qywx,im</types><userMobiles>" + phone + "</userMobiles></Message>";
/*     */ 
/*  88 */       JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
/*  89 */       Client client = dcf.createClient(url);
/*     */ 
/*  91 */       String result = (String)client.invoke("invokeWithUserName", new Object[] { userName, pwd, appServiceId, "133df0de-b11e-445a-b551-c5416f61b80e", messageXml })[0];
/*     */ 
/*  93 */       result = result.replace("[", "");
/*  94 */       result = result.replace("]", "");
/*     */ 
/*  96 */       JSONObject jsonObj = JSONObject.fromObject(result);
/*  97 */       JSONObject data = jsonObj.getJSONObject("statusMap");
/*  98 */       String state = data.getString("sms");
/*     */ 
/* 102 */       if ("success".equals(state)) {
/* 103 */         Object[] objs = new Object[2];
/* 104 */         objs[0] = yzm;
/* 105 */         objs[1] = new Date();
/* 106 */         PhoneCodeMap.getInstance().getPhoneCodeMap().put(phone, objs);
/*     */ 
/* 108 */         Portalsmslogs smslogs = new Portalsmslogs();
/* 109 */         smslogs.setInfo(text);
/* 110 */         smslogs.setPhone(phone);
/* 111 */         smslogs.setSendDate(new Date());
/* 112 */         smslogs.setSid(id);
/* 113 */         smslogs.setType("1");
/* 114 */         smslogsService.addPortalsmslogs(smslogs);
/*     */ 
/* 117 */         String mobile = phone;
/* 118 */         JaxWsDynamicClientFactory dcf1 = JaxWsDynamicClientFactory.newInstance();
/* 119 */         Client client1 = dcf1.createClient(url);
/* 120 */         result = (String)client1.invoke("invokeWithUserName", new Object[] { userName, pwd, appServiceId, "50549c60-f2d5-4cfb-8bdc-249f26664c5e", mobile })[0];
/*     */ 
/* 122 */         System.out.println(result);
/* 123 */         if (result.contains("true")) {
/* 124 */           PhoneUserMap.getInstance().getPhoneUserMap().put(phone, phone);
/* 125 */           if (stringUtils.isNotBlank(mac)) {
/* 126 */             addMac(mac);
/*     */           }
/*     */         }
/* 129 */         return true;
/*     */       }
/* 131 */       return false;
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 136 */       System.out.println("error" + e);
/* 137 */     }return false;
/*     */   }
/*     */ 
/*     */   private static void init(HttpServletRequest request)
/*     */   {
/*     */     try
/*     */     {
/* 145 */       String descDir = request.getServletContext().getRealPath("/");
/* 146 */       Properties props = new Properties();
/* 147 */       String propPath = descDir + 
/* 148 */         "sms.properties";
/* 149 */       FileInputStream in = new FileInputStream(propPath);
/* 150 */       props.load(in);
/* 151 */       userName = props.getProperty("username");
/* 152 */       pwd = props.getProperty("password");
/* 153 */       appServiceId = props.getProperty("appid");
/* 154 */       in.close();
/*     */     }
/*     */     catch (Exception localException)
/*     */     {
/*     */     }
/*     */   }
/*     */ 
/*     */   private static void addMac(String userMac) {
/* 162 */     boolean macHave = false;
/* 163 */     List<Portalaccountmacs> macs = macsService
/* 164 */       .getPortalaccountmacsList(new PortalaccountmacsQuery());
/* 165 */     for (Portalaccountmacs mac : macs) {
/* 166 */       if (mac.getMac().equals(userMac)) {
/* 167 */         macHave = true;
/* 168 */         break;
/*     */       }
/*     */     }
/* 171 */     if (!macHave) {
/* 172 */       List accs = accService.getPortalaccountList(new PortalaccountQuery());
/* 173 */       if ((accs.size() > 0) && 
/* 174 */         (((Portalaccount)accs.get(0)).getAutologin().intValue() == 1)) {
/* 175 */         Portalaccountmacs accMac = new Portalaccountmacs();
/* 176 */         accMac.setAccountId(((Portalaccount)accs.get(0)).getId());
/* 177 */         accMac.setMac(userMac);
/* 178 */         macsService.addPortalaccountmacs(accMac);
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.utils.SMSProRequest
 * JD-Core Version:    0.6.2
 */