package com.wxpay.utils;

import java.security.MessageDigest;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.SortedMap;

public class Sha1Util
{
  public static String getNonceStr()
  {
    Random random = new Random();
    return MD5Util.MD5Encode(String.valueOf(random.nextInt(10000)), "UTF-8");
  }
  public static String getTimeStamp() {
    return String.valueOf(System.currentTimeMillis() / 1000L);
  }

  public static String createSHA1Sign(SortedMap<String, String> signParams) throws Exception
  {
    StringBuffer sb = new StringBuffer();
    Set es = signParams.entrySet();
    Iterator it = es.iterator();
    while (it.hasNext()) {
      Map.Entry entry = (Map.Entry)it.next();
      String k = (String)entry.getKey();
      String v = (String)entry.getValue();
      sb.append(k + "=" + v + "&");
    }

    String params = sb.substring(0, sb.lastIndexOf("&"));

    return getSha1(params);
  }

  public static String getSha1(String str) {
    if ((str == null) || (str.length() == 0)) {
      return null;
    }
    char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
      'a', 'b', 'c', 'd', 'e', 'f' };
    try
    {
      MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
      mdTemp.update(str.getBytes("GBK"));

      byte[] md = mdTemp.digest();
      int j = md.length;
      char[] buf = new char[j * 2];
      int k = 0;
      for (int i = 0; i < j; i++) {
        byte byte0 = md[i];
        buf[(k++)] = hexDigits[(byte0 >>> 4 & 0xF)];
        buf[(k++)] = hexDigits[(byte0 & 0xF)];
      }
      return new String(buf); } catch (Exception e) {
    }
    return null;
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.wxpay.utils.Sha1Util
 * JD-Core Version:    0.6.2
 */