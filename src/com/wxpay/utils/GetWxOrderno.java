/*     */ package com.wxpay.utils;
/*     */ 
/*     */ import com.wxpay.utils.http.HttpClientConnectionManager;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.InputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.http.HttpResponse;
/*     */ import org.apache.http.client.methods.HttpPost;
/*     */ import org.apache.http.entity.StringEntity;
/*     */ import org.apache.http.impl.client.DefaultHttpClient;
/*     */ import org.apache.http.params.HttpParams;
/*     */ import org.apache.http.util.EntityUtils;
/*     */ import org.jdom.Document;
/*     */ import org.jdom.Element;
/*     */ import org.jdom.input.SAXBuilder;
/*     */ 
/*     */ public class GetWxOrderno
/*     */ {
/*  32 */   public static DefaultHttpClient httpclient = (DefaultHttpClient)HttpClientConnectionManager.getSSLInstance(null);
/*     */ 
/*     */   public static String getPayNo(String url, String xmlParam)
/*     */   {
/*  45 */     DefaultHttpClient client = new DefaultHttpClient();
/*  46 */     client.getParams().setParameter("http.protocol.allow-circular-redirects", Boolean.valueOf(true));
/*  47 */     HttpPost httpost = HttpClientConnectionManager.getPostMethod(url);
/*  48 */     String prepay_id = "";
/*     */     try {
/*  50 */       httpost.setEntity(new StringEntity(xmlParam, "UTF-8"));
/*  51 */       HttpResponse response = httpclient.execute(httpost);
/*  52 */       String jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");
/*  53 */       System.out.println("payID=   " + jsonStr);
/*  54 */       if (jsonStr.indexOf("FAIL") != -1) {
/*  55 */         return prepay_id;
/*     */       }
/*  57 */       Map map = doXMLParse(jsonStr);
/*  58 */       prepay_id = (String)map.get("prepay_id");
/*     */     }
/*     */     catch (Exception e) {
/*  61 */       e.printStackTrace();
/*     */     }
/*  63 */     return prepay_id;
/*     */   }
/*     */ 
/*     */   public static String getCodeUrl(String url, String xmlParam)
/*     */   {
/*  75 */     DefaultHttpClient client = new DefaultHttpClient();
/*  76 */     client.getParams().setParameter("http.protocol.allow-circular-redirects", Boolean.valueOf(true));
/*  77 */     HttpPost httpost = HttpClientConnectionManager.getPostMethod(url);
/*  78 */     String code_url = "";
/*     */     try {
/*  80 */       httpost.setEntity(new StringEntity(xmlParam, "UTF-8"));
/*  81 */       HttpResponse response = httpclient.execute(httpost);
/*  82 */       String jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");
/*  83 */       if (jsonStr.indexOf("FAIL") != -1) {
/*  84 */         return code_url;
/*     */       }
/*  86 */       Map map = doXMLParse(jsonStr);
/*  87 */       code_url = (String)map.get("code_url");
/*     */     }
/*     */     catch (Exception e) {
/*  90 */       e.printStackTrace();
/*     */     }
/*  92 */     return code_url;
/*     */   }
/*     */ 
/*     */   public static Map doXMLParse(String strxml)
/*     */     throws Exception
/*     */   {
/* 104 */     if ((strxml == null) || ("".equals(strxml))) {
/* 105 */       return null;
/*     */     }
/*     */ 
/* 108 */     Map m = new HashMap();
/* 109 */     InputStream in = String2Inputstream(strxml);
/* 110 */     SAXBuilder builder = new SAXBuilder();
/* 111 */     Document doc = builder.build(in);
/* 112 */     Element root = doc.getRootElement();
/* 113 */     List list = root.getChildren();
/* 114 */     Iterator it = list.iterator();
/* 115 */     while (it.hasNext()) {
/* 116 */       Element e = (Element)it.next();
/* 117 */       String k = e.getName();
/* 118 */       String v = "";
/* 119 */       List children = e.getChildren();
/* 120 */       if (children.isEmpty())
/* 121 */         v = e.getTextNormalize();
/*     */       else {
/* 123 */         v = getChildrenText(children);
/*     */       }
/*     */ 
/* 126 */       m.put(k, v);
/*     */     }
/*     */ 
/* 130 */     in.close();
/*     */ 
/* 132 */     return m;
/*     */   }
/*     */ 
/*     */   public static String getChildrenText(List children)
/*     */   {
/* 140 */     StringBuffer sb = new StringBuffer();
/* 141 */     if (!children.isEmpty()) {
/* 142 */       Iterator it = children.iterator();
/* 143 */       while (it.hasNext()) {
/* 144 */         Element e = (Element)it.next();
/* 145 */         String name = e.getName();
/* 146 */         String value = e.getTextNormalize();
/* 147 */         List list = e.getChildren();
/* 148 */         sb.append("<" + name + ">");
/* 149 */         if (!list.isEmpty()) {
/* 150 */           sb.append(getChildrenText(list));
/*     */         }
/* 152 */         sb.append(value);
/* 153 */         sb.append("</" + name + ">");
/*     */       }
/*     */     }
/*     */ 
/* 157 */     return sb.toString();
/*     */   }
/*     */   public static InputStream String2Inputstream(String str) {
/* 160 */     return new ByteArrayInputStream(str.getBytes());
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.wxpay.utils.GetWxOrderno
 * JD-Core Version:    0.6.2
 */