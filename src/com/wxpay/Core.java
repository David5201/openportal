package com.wxpay;

import com.wxpay.bean.WxPayDto;
import com.wxpay.utils.GetWxOrderno;
import com.wxpay.utils.RequestHandler;
import com.wxpay.utils.Sha1Util;
import com.wxpay.utils.TenpayUtil;
import java.io.PrintStream;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;

public class Core
{
  public static void main(String[] args)
  {
    WxPayDto tpWxPay1 = new WxPayDto();
    tpWxPay1.setBody("1小时时长卡在线充值");
    tpWxPay1.setOrderId(UUID.randomUUID().toString().replace("-", ""));
    tpWxPay1.setSpbillCreateIp("192.168.0.1");
    tpWxPay1.setTotalFee("0.01");
    getCodeurl(tpWxPay1, "wxa2f8476221a55836", "86977ec049338c99bc30a2d2400f2d75", "1332600801", "86977ec049338c99bc30a2d2400f2d75", "http:eturnWXpayNotify.action");
  }

  public static String getCodeurl(WxPayDto tpWxPayDto, String appid, String appsecret, String partner, String partnerkey, String notifyurl)
  {
    String orderId = tpWxPayDto.getOrderId();

    String attach = "";

    String totalFee = getMoney(tpWxPayDto.getTotalFee());

    String spbill_create_ip = tpWxPayDto.getSpbillCreateIp();

    String notify_url = notifyurl;
    String trade_type = "NATIVE";

    String mch_id = partner;

    String nonce_str = getNonceStr();

    String body = tpWxPayDto.getBody();

    String out_trade_no = orderId;

    SortedMap packageParams = new TreeMap();
    packageParams.put("appid", appid);
    packageParams.put("mch_id", mch_id);
    packageParams.put("nonce_str", nonce_str);
    packageParams.put("body", body);
    packageParams.put("attach", attach);
    packageParams.put("out_trade_no", out_trade_no);

    packageParams.put("total_fee", totalFee);
    packageParams.put("spbill_create_ip", spbill_create_ip);
    packageParams.put("notify_url", notify_url);

    packageParams.put("trade_type", trade_type);

    RequestHandler reqHandler = new RequestHandler(null, null);
    reqHandler.init(appid, appsecret, partnerkey);

    String sign = reqHandler.createSign(packageParams);
    String xml = "<xml><appid>" + appid + "</appid>" + "<mch_id>" + 
      mch_id + "</mch_id>" + "<nonce_str>" + nonce_str + 
      "<ign>" + 
      "<body><![CDATA[" + body + "]]></body>" + 
      "<out_trade_no>" + out_trade_no + 
      "<ttach>" + 
      "<total_fee>" + totalFee + "</total_fee>" + 
      "<spbill_create_ip>" + spbill_create_ip + 
      "</spbill_create_ip>" + "<notify_url>" + notify_url + 
      "</notify_url>" + "<trade_type>" + trade_type + 
      "<ml>";
    String code_url = "";
    String createOrderURL = "https:ay/unifiedorder";

    new GetWxOrderno(); code_url = GetWxOrderno.getCodeUrl(createOrderURL, xml);
    System.out.println("weixin pay code_url----------------" + code_url);

    return code_url;
  }

  public static String getPackage(WxPayDto tpWxPayDto, String appid, String appsecret, String partner, String partnerkey, String notifyurl)
  {
    String openId = tpWxPayDto.getOpenId();

    String orderId = tpWxPayDto.getOrderId();

    String attach = "";

    String totalFee = getMoney(tpWxPayDto.getTotalFee());

    String spbill_create_ip = tpWxPayDto.getSpbillCreateIp();

    String notify_url = notifyurl;
    String trade_type = "JSAPI";

    String mch_id = partner;

    String nonce_str = getNonceStr();

    String body = tpWxPayDto.getBody();

    String out_trade_no = orderId;

    SortedMap packageParams = new TreeMap();
    packageParams.put("appid", appid);
    packageParams.put("mch_id", mch_id);
    packageParams.put("nonce_str", nonce_str);
    packageParams.put("body", body);
    packageParams.put("attach", attach);
    packageParams.put("out_trade_no", out_trade_no);

    packageParams.put("total_fee", totalFee);
    packageParams.put("spbill_create_ip", spbill_create_ip);
    packageParams.put("notify_url", notify_url);

    packageParams.put("trade_type", trade_type);
    packageParams.put("openid", openId);

    RequestHandler reqHandler = new RequestHandler(null, null);
    reqHandler.init(appid, appsecret, partnerkey);

    String sign = reqHandler.createSign(packageParams);
    String xml = "<xml><appid>" + appid + "</appid>" + "<mch_id>" + 
      mch_id + "</mch_id>" + "<nonce_str>" + nonce_str + 
      "<ign>" + 
      "<body><![CDATA[" + body + "]]></body>" + 
      "<out_trade_no>" + out_trade_no + 
      "<ttach>" + 
      "<total_fee>" + totalFee + "</total_fee>" + 
      "<spbill_create_ip>" + spbill_create_ip + 
      "</spbill_create_ip>" + "<notify_url>" + notify_url + 
      "</notify_url>" + "<trade_type>" + trade_type + 
      "<penid>" + 
      "</xml>";
    String prepay_id = "";
    String createOrderURL = "https:ay/unifiedorder";

    new GetWxOrderno(); prepay_id = GetWxOrderno.getPayNo(createOrderURL, xml);

    System.out.println("prepay_id=" + prepay_id);

    SortedMap finalpackage = new TreeMap();
    String timestamp = Sha1Util.getTimeStamp();
    String packages = "prepay_id=" + prepay_id;
    finalpackage.put("appId", appid);
    finalpackage.put("timeStamp", timestamp);
    finalpackage.put("nonceStr", nonce_str);
    finalpackage.put("package", packages);
    finalpackage.put("signType", "MD5");

    String finalsign = reqHandler.createSign(finalpackage);

    String finaPackage = "{'appId': \"" + appid + "\",'timeStamp': \"" + timestamp + 
      "\",'nonceStr': \"" + nonce_str + "\",'package': \"" + 
      packages + "\",'signType' : \"MD5\",'paySign':\"" + 
      finalsign + "\"}";

    System.out.println("V3 jsApi package:    " + finaPackage);
    return finaPackage;
  }

  public static String getNonceStr()
  {
    String currTime = TenpayUtil.getCurrTime();

    String strTime = currTime.substring(8, currTime.length());

    String strRandom = TenpayUtil.buildRandom(4)+"";

    return strTime + strRandom;
  }

  public static String getMoney(String amount)
  {
    if (amount == null) {
      return "";
    }

    String currency = amount.replaceAll("\\$|\\￥|\\,", "");
    int index = currency.indexOf(".");
    int length = currency.length();
    Long amLong = Long.valueOf(0L);
    if (index == -1)
      amLong = Long.valueOf(currency + "00");
    else if (length - index >= 3)
      amLong = Long.valueOf(currency.substring(0, index + 3).replace(".", ""));
    else if (length - index == 2)
      amLong = Long.valueOf(currency.substring(0, index + 2).replace(".", "") + 0);
    else {
      amLong = Long.valueOf(currency.substring(0, index + 1).replace(".", "") + "00");
    }
    return amLong.toString();
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.wxpay.Core
 * JD-Core Version:    0.6.2
 */