package com.wxpay.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RequestHandler
{
  private String tokenUrl;
  private String gateUrl;
  private String notifyUrl;
  private String appid;
  private String appkey;
  private String partnerkey;
  private String appsecret;
  private String key;
  private SortedMap parameters;
  private String Token;
  private String charset;
  private String debugInfo;
  private String last_errcode;
  private HttpServletRequest request;
  private HttpServletResponse response;

  public RequestHandler(HttpServletRequest request, HttpServletResponse response)
  {
    this.last_errcode = "0";
    this.request = request;
    this.response = response;

    this.charset = "UTF-8";
    this.parameters = new TreeMap();

    this.notifyUrl = "https:ateway/simpleverifynotifyid.xml";
  }

  public void init(String app_id, String app_secret, String partner_key)
  {
    this.last_errcode = "0";
    this.Token = "token_";
    this.debugInfo = "";
    this.appid = app_id;
    this.partnerkey = partner_key;
    this.appsecret = app_secret;
    this.key = partner_key;
  }

  public void init()
  {
  }

  public String getLasterrCode()
  {
    return this.last_errcode;
  }

  public String getGateUrl()
  {
    return this.gateUrl;
  }

  public String getParameter(String parameter)
  {
    String s = (String)this.parameters.get(parameter);
    return s == null ? "" : s;
  }

  public void setKey(String key)
  {
    this.partnerkey = key;
  }

  public void setAppKey(String key) {
    this.appkey = key;
  }

  public String UrlEncode(String src) throws UnsupportedEncodingException
  {
    return URLEncoder.encode(src, this.charset).replace("+", "%20");
  }

  public String genPackage(SortedMap<String, String> packageParams)
    throws UnsupportedEncodingException
  {
    String sign = createSign(packageParams);

    StringBuffer sb = new StringBuffer();
    Set es = packageParams.entrySet();
    Iterator it = es.iterator();
    while (it.hasNext()) {
      Map.Entry entry = (Map.Entry)it.next();
      String k = (String)entry.getKey();
      String v = (String)entry.getValue();
      sb.append(k + "=" + UrlEncode(v) + "&");
    }

    String packageValue = new StringBuilder("sign=").append(sign).toString();

    return packageValue;
  }

  public String createSign(SortedMap<String, String> packageParams)
  {
    StringBuffer sb = new StringBuffer();
    Set es = packageParams.entrySet();
    Iterator it = es.iterator();
    while (it.hasNext()) {
      Map.Entry entry = (Map.Entry)it.next();
      String k = (String)entry.getKey();
      String v = (String)entry.getValue();
      if ((v != null) && (!"".equals(v)) && (!"sign".equals(k)) && 
        (!"key".equals(k))) {
        sb.append(k + "=" + v + "&");
      }
    }
    sb.append("key=" + getKey());
    String sign = MD5Util.MD5Encode(sb.toString(), this.charset)
      .toUpperCase();
    return sign;
  }

  public boolean createMd5Sign(String signParams)
  {
    StringBuffer sb = new StringBuffer();
    Set es = this.parameters.entrySet();
    Iterator it = es.iterator();
    while (it.hasNext()) {
      Map.Entry entry = (Map.Entry)it.next();
      String k = (String)entry.getKey();
      String v = (String)entry.getValue();
      if ((!"sign".equals(k)) && (v != null) && (!"".equals(v))) {
        sb.append(k + "=" + v + "&");
      }

    }

    String enc = TenpayUtil.getCharacterEncoding(this.request, 
      this.response);
    String sign = MD5Util.MD5Encode(sb.toString(), enc).toLowerCase();

    String tenpaySign = getParameter("sign").toLowerCase();

    setDebugInfo(sb.toString() + " => sign:" + sign + " tenpaySign:" + 
      tenpaySign);

    return tenpaySign.equals(sign);
  }

  public String parseXML()
  {
    StringBuffer sb = new StringBuffer();
    sb.append("<xml>");
    Set es = this.parameters.entrySet();
    Iterator it = es.iterator();
    while (it.hasNext()) {
      Map.Entry entry = (Map.Entry)it.next();
      String k = (String)entry.getKey();
      String v = (String)entry.getValue();
      if ((v != null) && (!"".equals(v)) && (!"appkey".equals(k)))
      {
        sb.append("<" + k + ">" + getParameter(k) + "</" + k + ">\n");
      }
    }
    sb.append("</xml>");
    return sb.toString();
  }

  protected void setDebugInfo(String debugInfo)
  {
    this.debugInfo = debugInfo;
  }
  public void setPartnerkey(String partnerkey) {
    this.partnerkey = partnerkey;
  }
  public String getDebugInfo() {
    return this.debugInfo;
  }
  public String getKey() {
    return this.key;
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.wxpay.utils.RequestHandler
 * JD-Core Version:    0.6.2
 */