package com.alipay.sign;

import java.io.UnsupportedEncodingException;
import org.apache.commons.codec.digest.DigestUtils;

public class MD5
{
  public static String sign(String text, String key, String input_charset)
  {
    text = text + key;
    return DigestUtils.md5Hex(getContentBytes(text, input_charset));
  }

  public static boolean verify(String text, String sign, String key, String input_charset)
  {
    text = text + key;
    String mysign = DigestUtils.md5Hex(getContentBytes(text, input_charset));
    if (mysign.equals(sign)) {
      return true;
    }

    return false;
  }

  private static byte[] getContentBytes(String content, String charset)
  {
    if ((charset == null) || ("".equals(charset)))
      return content.getBytes();
    try
    {
      return content.getBytes(charset); } catch (UnsupportedEncodingException e) {
    }
    throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.alipay.sign.MD5
 * JD-Core Version:    0.6.2
 */