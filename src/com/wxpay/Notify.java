package com.wxpay;

import com.wxpay.bean.WxPayResult;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.PrintStream;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;

public class Notify
{
  protected void notify(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    System.out.print("微信支付回调数据开始");

    String notityXml = "";
    String resXml = "";
    try
    {
      String inputLine;
      while ((inputLine = request.getReader().readLine()) != null)
      {
        notityXml = notityXml + inputLine;
      }
      request.getReader().close();
    } catch (Exception e) {
      e.printStackTrace();
    }

    System.out.println("接收到的报文：" + notityXml);

    Map m = parseXmlToList2(notityXml);
    WxPayResult wpr = new WxPayResult();
    wpr.setAppid(m.get("appid").toString());
    wpr.setBankType(m.get("bank_type").toString());
    wpr.setCashFee(m.get("cash_fee").toString());
    wpr.setFeeType(m.get("fee_type").toString());
    wpr.setIsSubscribe(m.get("is_subscribe").toString());
    wpr.setMchId(m.get("mch_id").toString());
    wpr.setNonceStr(m.get("nonce_str").toString());
    wpr.setOpenid(m.get("openid").toString());
    wpr.setOutTradeNo(m.get("out_trade_no").toString());
    wpr.setResultCode(m.get("result_code").toString());
    wpr.setReturnCode(m.get("return_code").toString());
    wpr.setSign(m.get("sign").toString());
    wpr.setTimeEnd(m.get("time_end").toString());
    wpr.setTotalFee(m.get("total_fee").toString());
    wpr.setTradeType(m.get("trade_type").toString());
    wpr.setTransactionId(m.get("transaction_id").toString());

    if ("SUCCESS".equals(wpr.getResultCode()))
    {
      resXml = "<xml><return_code><![CDATA[SUCCESS]]><eturn_msg></xml> ";
    }
    else {
      resXml = "<xml><return_code><![CDATA[FAIL]]><eturn_msg></xml> ";
    }

    System.out.println("微信支付回调数据结束");

    BufferedOutputStream out = new BufferedOutputStream(
      response.getOutputStream());
    out.write(resXml.getBytes());
    out.flush();
    out.close();
  }

  public static Map parseXmlToList2(String xml)
  {
    Map retMap = new HashMap();
    try {
      StringReader read = new StringReader(xml);

      InputSource source = new InputSource(read);

      SAXBuilder sb = new SAXBuilder();

      Document doc = sb.build(source);
      Element root = doc.getRootElement();
      List<Element> es = root.getChildren();
      if ((es != null) && (es.size() != 0))
        for (Element element : es)
          retMap.put(element.getName(), element.getValue());
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return retMap;
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.wxpay.Notify
 * JD-Core Version:    0.6.2
 */