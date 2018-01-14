/*     */ package com.wxpay;
/*     */ 
/*     */ import com.wxpay.bean.WxPayResult;
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.PrintStream;
/*     */ import java.io.StringReader;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.jdom.Document;
/*     */ import org.jdom.Element;
/*     */ import org.jdom.input.SAXBuilder;
/*     */ import org.xml.sax.InputSource;
/*     */ 
/*     */ public class Notify
/*     */ {
/*     */   protected void notify(HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/*  26 */     System.out.print("微信支付回调数据开始");
/*     */ 
/*  30 */     String notityXml = "";
/*  31 */     String resXml = "";
/*     */     try
/*     */     {
/*     */       String inputLine;
/*  34 */       while ((inputLine = request.getReader().readLine()) != null)
/*     */       {
/*  35 */         notityXml = notityXml + inputLine;
/*     */       }
/*  37 */       request.getReader().close();
/*     */     } catch (Exception e) {
/*  39 */       e.printStackTrace();
/*     */     }
/*     */ 
/*  42 */     System.out.println("接收到的报文：" + notityXml);
/*     */ 
/*  44 */     Map m = parseXmlToList2(notityXml);
/*  45 */     WxPayResult wpr = new WxPayResult();
/*  46 */     wpr.setAppid(m.get("appid").toString());
/*  47 */     wpr.setBankType(m.get("bank_type").toString());
/*  48 */     wpr.setCashFee(m.get("cash_fee").toString());
/*  49 */     wpr.setFeeType(m.get("fee_type").toString());
/*  50 */     wpr.setIsSubscribe(m.get("is_subscribe").toString());
/*  51 */     wpr.setMchId(m.get("mch_id").toString());
/*  52 */     wpr.setNonceStr(m.get("nonce_str").toString());
/*  53 */     wpr.setOpenid(m.get("openid").toString());
/*  54 */     wpr.setOutTradeNo(m.get("out_trade_no").toString());
/*  55 */     wpr.setResultCode(m.get("result_code").toString());
/*  56 */     wpr.setReturnCode(m.get("return_code").toString());
/*  57 */     wpr.setSign(m.get("sign").toString());
/*  58 */     wpr.setTimeEnd(m.get("time_end").toString());
/*  59 */     wpr.setTotalFee(m.get("total_fee").toString());
/*  60 */     wpr.setTradeType(m.get("trade_type").toString());
/*  61 */     wpr.setTransactionId(m.get("transaction_id").toString());
/*     */ 
/*  63 */     if ("SUCCESS".equals(wpr.getResultCode()))
/*     */     {
/*  65 */       resXml = "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml> ";
/*     */     }
/*     */     else {
/*  68 */       resXml = "<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[报文为空]]></return_msg></xml> ";
/*     */     }
/*     */ 
/*  72 */     System.out.println("微信支付回调数据结束");
/*     */ 
/*  74 */     BufferedOutputStream out = new BufferedOutputStream(
/*  75 */       response.getOutputStream());
/*  76 */     out.write(resXml.getBytes());
/*  77 */     out.flush();
/*  78 */     out.close();
/*     */   }
/*     */ 
/*     */   public static Map parseXmlToList2(String xml)
/*     */   {
/*  92 */     Map retMap = new HashMap();
/*     */     try {
/*  94 */       StringReader read = new StringReader(xml);
/*     */ 
/*  96 */       InputSource source = new InputSource(read);
/*     */ 
/*  98 */       SAXBuilder sb = new SAXBuilder();
/*     */ 
/* 100 */       Document doc = sb.build(source);
/* 101 */       Element root = doc.getRootElement();
/* 102 */       List<Element> es = root.getChildren();
/* 103 */       if ((es != null) && (es.size() != 0))
/* 104 */         for (Element element : es)
/* 105 */           retMap.put(element.getName(), element.getValue());
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 109 */       e.printStackTrace();
/*     */     }
/* 111 */     return retMap;
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.wxpay.Notify
 * JD-Core Version:    0.6.2
 */