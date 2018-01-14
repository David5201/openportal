/*     */ package com.wxpay;
/*     */ 
/*     */ import com.wxpay.bean.WxPayDto;
/*     */ import com.wxpay.utils.GetWxOrderno;
/*     */ import com.wxpay.utils.RequestHandler;
/*     */ import com.wxpay.utils.Sha1Util;
/*     */ import com.wxpay.utils.TenpayUtil;
/*     */ import java.io.PrintStream;
/*     */ import java.util.SortedMap;
/*     */ import java.util.TreeMap;
/*     */ import java.util.UUID;
/*     */ 
/*     */ public class Core
/*     */ {
/*     */   public static void main(String[] args)
/*     */   {
/*  45 */     WxPayDto tpWxPay1 = new WxPayDto();
/*  46 */     tpWxPay1.setBody("1小时时长卡在线充值");
/*  47 */     tpWxPay1.setOrderId(UUID.randomUUID().toString().replace("-", ""));
/*  48 */     tpWxPay1.setSpbillCreateIp("192.168.0.1");
/*  49 */     tpWxPay1.setTotalFee("0.01");
/*  50 */     getCodeurl(tpWxPay1, "wxa2f8476221a55836", "86977ec049338c99bc30a2d2400f2d75", "1332600801", "86977ec049338c99bc30a2d2400f2d75", "http://portal.openportal.com.cn/returnWXpayNotify.action");
/*     */   }
/*     */ 
/*     */   public static String getCodeurl(WxPayDto tpWxPayDto, String appid, String appsecret, String partner, String partnerkey, String notifyurl)
/*     */   {
/*  61 */     String orderId = tpWxPayDto.getOrderId();
/*     */ 
/*  63 */     String attach = "";
/*     */ 
/*  65 */     String totalFee = getMoney(tpWxPayDto.getTotalFee());
/*     */ 
/*  68 */     String spbill_create_ip = tpWxPayDto.getSpbillCreateIp();
/*     */ 
/*  70 */     String notify_url = notifyurl;
/*  71 */     String trade_type = "NATIVE";
/*     */ 
/*  74 */     String mch_id = partner;
/*     */ 
/*  76 */     String nonce_str = getNonceStr();
/*     */ 
/*  79 */     String body = tpWxPayDto.getBody();
/*     */ 
/*  82 */     String out_trade_no = orderId;
/*     */ 
/*  84 */     SortedMap packageParams = new TreeMap();
/*  85 */     packageParams.put("appid", appid);
/*  86 */     packageParams.put("mch_id", mch_id);
/*  87 */     packageParams.put("nonce_str", nonce_str);
/*  88 */     packageParams.put("body", body);
/*  89 */     packageParams.put("attach", attach);
/*  90 */     packageParams.put("out_trade_no", out_trade_no);
/*     */ 
/*  93 */     packageParams.put("total_fee", totalFee);
/*  94 */     packageParams.put("spbill_create_ip", spbill_create_ip);
/*  95 */     packageParams.put("notify_url", notify_url);
/*     */ 
/*  97 */     packageParams.put("trade_type", trade_type);
/*     */ 
/*  99 */     RequestHandler reqHandler = new RequestHandler(null, null);
/* 100 */     reqHandler.init(appid, appsecret, partnerkey);
/*     */ 
/* 102 */     String sign = reqHandler.createSign(packageParams);
/* 103 */     String xml = "<xml><appid>" + appid + "</appid>" + "<mch_id>" + 
/* 104 */       mch_id + "</mch_id>" + "<nonce_str>" + nonce_str + 
/* 105 */       "</nonce_str>" + "<sign>" + sign + "</sign>" + 
/* 106 */       "<body><![CDATA[" + body + "]]></body>" + 
/* 107 */       "<out_trade_no>" + out_trade_no + 
/* 108 */       "</out_trade_no>" + "<attach>" + attach + "</attach>" + 
/* 109 */       "<total_fee>" + totalFee + "</total_fee>" + 
/* 110 */       "<spbill_create_ip>" + spbill_create_ip + 
/* 111 */       "</spbill_create_ip>" + "<notify_url>" + notify_url + 
/* 112 */       "</notify_url>" + "<trade_type>" + trade_type + 
/* 113 */       "</trade_type>" + "</xml>";
/* 114 */     String code_url = "";
/* 115 */     String createOrderURL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
/*     */ 
/* 118 */     new GetWxOrderno(); code_url = GetWxOrderno.getCodeUrl(createOrderURL, xml);
/* 119 */     System.out.println("weixin pay code_url----------------" + code_url);
/*     */ 
/* 121 */     return code_url;
/*     */   }
/*     */ 
/*     */   public static String getPackage(WxPayDto tpWxPayDto, String appid, String appsecret, String partner, String partnerkey, String notifyurl)
/*     */   {
/* 132 */     String openId = tpWxPayDto.getOpenId();
/*     */ 
/* 135 */     String orderId = tpWxPayDto.getOrderId();
/*     */ 
/* 137 */     String attach = "";
/*     */ 
/* 139 */     String totalFee = getMoney(tpWxPayDto.getTotalFee());
/*     */ 
/* 142 */     String spbill_create_ip = tpWxPayDto.getSpbillCreateIp();
/*     */ 
/* 144 */     String notify_url = notifyurl;
/* 145 */     String trade_type = "JSAPI";
/*     */ 
/* 149 */     String mch_id = partner;
/*     */ 
/* 151 */     String nonce_str = getNonceStr();
/*     */ 
/* 154 */     String body = tpWxPayDto.getBody();
/*     */ 
/* 157 */     String out_trade_no = orderId;
/*     */ 
/* 159 */     SortedMap packageParams = new TreeMap();
/* 160 */     packageParams.put("appid", appid);
/* 161 */     packageParams.put("mch_id", mch_id);
/* 162 */     packageParams.put("nonce_str", nonce_str);
/* 163 */     packageParams.put("body", body);
/* 164 */     packageParams.put("attach", attach);
/* 165 */     packageParams.put("out_trade_no", out_trade_no);
/*     */ 
/* 168 */     packageParams.put("total_fee", totalFee);
/* 169 */     packageParams.put("spbill_create_ip", spbill_create_ip);
/* 170 */     packageParams.put("notify_url", notify_url);
/*     */ 
/* 172 */     packageParams.put("trade_type", trade_type);
/* 173 */     packageParams.put("openid", openId);
/*     */ 
/* 175 */     RequestHandler reqHandler = new RequestHandler(null, null);
/* 176 */     reqHandler.init(appid, appsecret, partnerkey);
/*     */ 
/* 178 */     String sign = reqHandler.createSign(packageParams);
/* 179 */     String xml = "<xml><appid>" + appid + "</appid>" + "<mch_id>" + 
/* 180 */       mch_id + "</mch_id>" + "<nonce_str>" + nonce_str + 
/* 181 */       "</nonce_str>" + "<sign>" + sign + "</sign>" + 
/* 182 */       "<body><![CDATA[" + body + "]]></body>" + 
/* 183 */       "<out_trade_no>" + out_trade_no + 
/* 184 */       "</out_trade_no>" + "<attach>" + attach + "</attach>" + 
/* 185 */       "<total_fee>" + totalFee + "</total_fee>" + 
/* 186 */       "<spbill_create_ip>" + spbill_create_ip + 
/* 187 */       "</spbill_create_ip>" + "<notify_url>" + notify_url + 
/* 188 */       "</notify_url>" + "<trade_type>" + trade_type + 
/* 189 */       "</trade_type>" + "<openid>" + openId + "</openid>" + 
/* 190 */       "</xml>";
/* 191 */     String prepay_id = "";
/* 192 */     String createOrderURL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
/*     */ 
/* 195 */     new GetWxOrderno(); prepay_id = GetWxOrderno.getPayNo(createOrderURL, xml);
/*     */ 
/* 197 */     System.out.println("prepay_id=" + prepay_id);
/*     */ 
/* 202 */     SortedMap finalpackage = new TreeMap();
/* 203 */     String timestamp = Sha1Util.getTimeStamp();
/* 204 */     String packages = "prepay_id=" + prepay_id;
/* 205 */     finalpackage.put("appId", appid);
/* 206 */     finalpackage.put("timeStamp", timestamp);
/* 207 */     finalpackage.put("nonceStr", nonce_str);
/* 208 */     finalpackage.put("package", packages);
/* 209 */     finalpackage.put("signType", "MD5");
/*     */ 
/* 211 */     String finalsign = reqHandler.createSign(finalpackage);
/*     */ 
/* 213 */     String finaPackage = "{'appId': \"" + appid + "\",'timeStamp': \"" + timestamp + 
/* 214 */       "\",'nonceStr': \"" + nonce_str + "\",'package': \"" + 
/* 215 */       packages + "\",'signType' : \"MD5\",'paySign':\"" + 
/* 216 */       finalsign + "\"}";
/*     */ 
/* 218 */     System.out.println("V3 jsApi package:    " + finaPackage);
/* 219 */     return finaPackage;
/*     */   }
/*     */ 
/*     */   public static String getNonceStr()
/*     */   {
/* 228 */     String currTime = TenpayUtil.getCurrTime();
/*     */ 
/* 230 */     String strTime = currTime.substring(8, currTime.length());
/*     */ 
/* 232 */     String strRandom = TenpayUtil.buildRandom(4)+"";
/*     */ 
/* 234 */     return strTime + strRandom;
/*     */   }
/*     */ 
/*     */   public static String getMoney(String amount)
/*     */   {
/* 243 */     if (amount == null) {
/* 244 */       return "";
/*     */     }
/*     */ 
/* 247 */     String currency = amount.replaceAll("\\$|\\￥|\\,", "");
/* 248 */     int index = currency.indexOf(".");
/* 249 */     int length = currency.length();
/* 250 */     Long amLong = Long.valueOf(0L);
/* 251 */     if (index == -1)
/* 252 */       amLong = Long.valueOf(currency + "00");
/* 253 */     else if (length - index >= 3)
/* 254 */       amLong = Long.valueOf(currency.substring(0, index + 3).replace(".", ""));
/* 255 */     else if (length - index == 2)
/* 256 */       amLong = Long.valueOf(currency.substring(0, index + 2).replace(".", "") + 0);
/*     */     else {
/* 258 */       amLong = Long.valueOf(currency.substring(0, index + 1).replace(".", "") + "00");
/*     */     }
/* 260 */     return amLong.toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.wxpay.Core
 * JD-Core Version:    0.6.2
 */