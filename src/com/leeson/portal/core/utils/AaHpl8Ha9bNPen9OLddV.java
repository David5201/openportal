package com.leeson.portal.core.utils;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class AaHpl8Ha9bNPen9OLddV
{
  private static String hexString = "0123456789ABCDEF";

  public static String toHexString(String s)
  {
    String str = "";
    for (int i = 0; i < s.length(); i++) {
      int ch = s.charAt(i);
      String s4 = Integer.toHexString(ch);
      str = str + s4;
    }
    return str;
  }

  public static String toStringHex1(String s)
  {
    byte[] baKeyword = new byte[s.length() / 2];
    for (int i = 0; i < baKeyword.length; i++)
      try {
        baKeyword[i] = ((byte)(0xFF & Integer.parseInt(
          s.substring(i * 2, i * 2 + 2), 16)));
      } catch (Exception e) {
        e.printStackTrace();
      }
    try
    {
      s = new String(baKeyword, "utf-8");
    } catch (Exception e1) {
      e1.printStackTrace();
    }
    return s;
  }

  public static String toStringHex2(String s)
  {
    byte[] baKeyword = new byte[s.length() / 2];
    for (int i = 0; i < baKeyword.length; i++)
      try {
        baKeyword[i] = ((byte)(0xFF & Integer.parseInt(
          s.substring(i * 2, i * 2 + 2), 16)));
      } catch (Exception e) {
        e.printStackTrace();
      }
    try
    {
      s = new String(baKeyword, "utf-8");
    } catch (Exception e1) {
      e1.printStackTrace();
    }
    return s;
  }

  public static String encode(String str)
  {
    byte[] bytes = null;
    try {
      bytes = str.getBytes("utf-8");
    } catch (UnsupportedEncodingException localUnsupportedEncodingException) {
    }
    StringBuilder sb = new StringBuilder(bytes.length * 2);

    for (int i = 0; i < bytes.length; i++) {
      sb.append(hexString.charAt((bytes[i] & 0xF0) >> 4));
      sb.append(hexString.charAt((bytes[i] & 0xF) >> 0));
    }
    return sb.toString();
  }

  public static String decode(String bytes)
  {
    ByteArrayOutputStream baos = new ByteArrayOutputStream(
      bytes.length() / 2);

    for (int i = 0; i < bytes.length(); i += 2)
      baos.write(hexString.indexOf(bytes.charAt(i)) << 4 | hexString
        .indexOf(bytes.charAt(i + 1)));
    String result = "";
    try {
      result = new String(baos.toByteArray(), "utf-8");
    } catch (UnsupportedEncodingException localUnsupportedEncodingException) {
    }
    return result;
  }

  public static void printHexString(String hint, byte[] b)
  {
    System.out.print(hint);
    for (int i = 0; i < b.length; i++) {
      String hex = Integer.toHexString(b[i] & 0xFF);
      if (hex.length() == 1) {
        hex = '0' + hex;
      }
      System.out.print(hex.toUpperCase() + " ");
    }
    System.out.println("");
  }

  public static String Bytes2HexString(byte[] b)
  {
    String ret = "";
    for (int i = 0; i < b.length; i++) {
      String hex = Integer.toHexString(b[i] & 0xFF);
      if (hex.length() == 1) {
        hex = '0' + hex;
      }
      ret = ret + hex.toUpperCase();
    }
    return ret;
  }

  public static byte uniteBytes(byte src0, byte src1)
  {
    byte _b0 = Byte.decode("0x" + new String(new byte[] { src0 }))
      .byteValue();
    _b0 = (byte)(_b0 << 4);
    byte _b1 = Byte.decode("0x" + new String(new byte[] { src1 }))
      .byteValue();
    byte ret = (byte)(_b0 ^ _b1);
    return ret;
  }

  public static byte[] HexString2Bytes(String src)
  {
    byte[] ret = new byte[8];
    byte[] tmp = src.getBytes();
    for (int i = 0; i < 8; i++) {
      ret[i] = uniteBytes(tmp[(i * 2)], tmp[(i * 2 + 1)]);
    }
    return ret;
  }

  public static String decryptBASE64(String key)
  {
    String result = "";
    try {
      result = new String(new BASE64Decoder().decodeBuffer(key));
    } catch (Exception localException) {
    }
    return result;
  }

  public static String encryptBASE64(String key)
  {
    String result = "";
    try {
      result = new BASE64Encoder().encodeBuffer(key.getBytes());
    } catch (Exception localException) {
    }
    return result;
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.utils.AaHpl8Ha9bNPen9OLddV
 * JD-Core Version:    0.6.2
 */