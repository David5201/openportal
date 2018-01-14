/*     */ package com.alipay.util;
/*     */ 
/*     */ import com.alipay.config.AlipayConfig;
/*     */ import com.alipay.sign.MD5;
/*     */ import com.leeson.core.bean.Payapi;
/*     */ import com.leeson.core.service.PayapiService;
/*     */ import com.leeson.portal.core.utils.SpringContextHelper;
/*     */ import java.io.IOException;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.dom4j.Document;
/*     */ import org.dom4j.DocumentException;
/*     */ import org.dom4j.Node;
/*     */ import org.dom4j.io.SAXReader;
/*     */ 
/*     */ public class AlipaySubmit
/*     */ {
/*  33 */   private static PayapiService payapiService = (PayapiService)
/*  34 */     SpringContextHelper.getBean("payapiServiceImpl");
/*     */   private static final String ALIPAY_GATEWAY_NEW = "https://mapi.alipay.com/gateway.do?";
/*     */ 
/*     */   public static String buildRequestMysign(Map<String, String> sPara)
/*     */   {
/*  46 */     String prestr = AlipayCore.createLinkString(sPara);
/*  47 */     String mysign = "";
/*  48 */     if (AlipayConfig.sign_type.equals("MD5")) {
/*  49 */       mysign = MD5.sign(prestr, payapiService.getPayapiByKey(Long.valueOf(1L)).getAlipayKey(), AlipayConfig.input_charset);
/*     */     }
/*  51 */     return mysign;
/*     */   }
/*     */ 
/*     */   private static Map<String, String> buildRequestPara(Map<String, String> sParaTemp)
/*     */   {
/*  61 */     Map sPara = AlipayCore.paraFilter(sParaTemp);
/*     */ 
/*  63 */     String mysign = buildRequestMysign(sPara);
/*     */ 
/*  66 */     sPara.put("sign", mysign);
/*  67 */     sPara.put("sign_type", AlipayConfig.sign_type);
/*     */ 
/*  69 */     return sPara;
/*     */   }
/*     */ 
/*     */   public static String buildRequest(Map<String, String> sParaTemp, String strMethod, String strButtonName)
/*     */   {
/*  81 */     Map sPara = buildRequestPara(sParaTemp);
/*  82 */     List keys = new ArrayList(sPara.keySet());
/*     */ 
/*  84 */     StringBuffer sbHtml = new StringBuffer();
/*     */ 
/*  86 */     sbHtml.append("<form id=\"alipaysubmit\" name=\"alipaysubmit\" action=\"https://mapi.alipay.com/gateway.do?_input_charset=" + 
/*  87 */       AlipayConfig.input_charset + "\" method=\"" + strMethod + 
/*  88 */       "\">");
/*     */ 
/*  90 */     for (int i = 0; i < keys.size(); i++) {
/*  91 */       String name = (String)keys.get(i);
/*  92 */       String value = (String)sPara.get(name);
/*     */ 
/*  94 */       sbHtml.append("<input type=\"hidden\" name=\"" + name + "\" value=\"" + value + "\"/>");
/*     */     }
/*     */ 
/*  98 */     sbHtml.append("<input type=\"submit\" value=\"" + strButtonName + "\" style=\"display:none;\"></form>");
/*  99 */     sbHtml.append("<script>document.forms['alipaysubmit'].submit();</script>");
/*     */ 
/* 101 */     return sbHtml.toString();
/*     */   }
/*     */ 
/*     */   public static String query_timestamp()
/*     */     throws MalformedURLException, DocumentException, IOException
/*     */   {
/* 118 */     String strUrl = "https://mapi.alipay.com/gateway.do?service=query_timestamp&partner=" + payapiService.getPayapiByKey(Long.valueOf(1L)).getAlipayPartner() + "&_input_charset" + AlipayConfig.input_charset;
/* 119 */     StringBuffer result = new StringBuffer();
/*     */ 
/* 121 */     SAXReader reader = new SAXReader();
/* 122 */     Document doc = reader.read(new URL(strUrl).openStream());
/*     */ 
/* 124 */     List nodeList = doc.selectNodes("//alipay/*");
/*     */     Iterator localIterator2;
/* 126 */     label194: for (Iterator localIterator1 = nodeList.iterator(); localIterator1.hasNext(); 
/* 131 */       localIterator2.hasNext())
/*     */     {
/* 126 */       Node node = (Node)localIterator1.next();
/*     */ 
/* 128 */       if ((!node.getName().equals("is_success")) || (!node.getText().equals("T")))
/*     */         break label194;
/* 130 */       List nodeList1 = doc.selectNodes("//response/timestamp/*");
/* 131 */       localIterator2 = nodeList1.iterator();
				Node node1 = (Node)localIterator2.next();
/* 132 */       result.append(node1.getText());
				continue;
/*     */     }
/*     */ 
/* 137 */     return result.toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.alipay.util.AlipaySubmit
 * JD-Core Version:    0.6.2
 */