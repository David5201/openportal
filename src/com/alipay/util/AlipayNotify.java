package com.alipay.util;

import com.alipay.config.AlipayConfig;
import com.alipay.sign.MD5;
import com.leeson.core.bean.Payapi;
import com.leeson.core.service.PayapiService;
import com.leeson.portal.core.utils.SpringContextHelper;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class AlipayNotify
{
  private static PayapiService payapiService = (PayapiService)
    SpringContextHelper.getBean("payapiServiceImpl");
  private static final String HTTPS_VERIFY_URL = "https:ateway.do?service=notify_verify&";

  public static boolean verify(Map<String, String> params)
  {
    String responseTxt = "false";
    if (params.get("notify_id") != null) {
      String notify_id = (String)params.get("notify_id");
      responseTxt = verifyResponse(notify_id);
    }
    String sign = "";
    if (params.get("sign") != null) sign = (String)params.get("sign");
    boolean isSign = getSignVeryfy(params, sign);

    if ((isSign) && (responseTxt.equals("true"))) {
      return true;
    }
    return false;
  }

  private static boolean getSignVeryfy(Map<String, String> Params, String sign)
  {
    Map sParaNew = AlipayCore.paraFilter(Params);

    String preSignStr = AlipayCore.createLinkString(sParaNew);

    boolean isSign = false;
    if (AlipayConfig.sign_type.equals("MD5")) {
      isSign = MD5.verify(preSignStr, sign, payapiService.getPayapiByKey(Long.valueOf(1L)).getAlipayKey(), AlipayConfig.input_charset);
    }
    return isSign;
  }

  private static String verifyResponse(String notify_id)
  {
    String partner = payapiService.getPayapiByKey(Long.valueOf(1L)).getAlipayPartner();
    String veryfy_url = "https:ateway.do?service=notify_verify&partner=" + partner + "&notify_id=" + notify_id;

    return checkUrl(veryfy_url);
  }

  private static String checkUrl(String urlvalue)
  {
    String inputLine = "";
    try
    {
      URL url = new URL(urlvalue);
      HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
      BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection
        .getInputStream()));
      inputLine = in.readLine().toString();
    } catch (Exception e) {
      e.printStackTrace();
      inputLine = "";
    }

    return inputLine;
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.alipay.util.AlipayNotify
 * JD-Core Version:    0.6.2
 */