package com.leeson.portal.core.service.utils;

import com.leeson.common.utils.stringUtils;

public class PortalUtil
{
  public static String Getbyte2HexString(byte[] b)
  {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < b.length; i++) {
      String hex = Integer.toHexString(b[i] & 0xFF);
      if (hex.length() == 1) {
        hex = '0' + hex;
      }
      sb.append(hex);
    }
    return "[" + sb.toString() + "]";
  }

  public static String Getbyte2MacString(byte[] b) {
    StringBuilder sb = new StringBuilder();
    sb.append("");
    for (int i = 0; i < b.length; i++) {
      String hex = Integer.toHexString(b[i] & 0xFF);
      if (hex.length() == 1) {
        hex = '0' + hex;
      }
      sb.append(hex);
      if (i < b.length - 1) {
        sb.append(":");
      }
    }
    return sb.toString();
  }

  public static String MacFormat(String ikmac) {
    if (stringUtils.isNotBlank(ikmac)) {
      ikmac = ikmac.replace("-", "");
      ikmac = ikmac.replace(":", "");
      ikmac = ikmac.trim().toLowerCase();
      StringBuilder sb = new StringBuilder();
      sb.append("");
      int count = ikmac.length();
      for (int i = 1; i <= count; i++) {
        sb.append(ikmac.substring(i - 1, i));
        if ((i < count) && (i % 2 == 0)) {
          sb.append(":");
        }
      }
      return sb.toString();
    }
    return "";
  }

  public static String MacFormat1(String ikmac)
  {
    if (stringUtils.isNotBlank(ikmac)) {
      ikmac = ikmac.replace("-", "");
      ikmac = ikmac.replace(":", "");
      ikmac = ikmac.trim().toLowerCase();
      return ikmac;
    }
    return "";
  }

  public static byte[] SerialNo()
  {
    byte[] SerialNo = new byte[2];
    short SerialNo_int = (short)(int)(1.0D + Math.random() * 32767.0D);
    for (int i = 0; i < 2; i++) {
      int offset = (SerialNo.length - 1 - i) * 8;
      SerialNo[i] = ((byte)(SerialNo_int >>> offset & 0xFF));
    }
    return SerialNo;
  }

  public static byte[] SerialNo(short SerialNo_int) {
    byte[] SerialNo = new byte[2];
    for (int i = 0; i < 2; i++) {
      int offset = (SerialNo.length - 1 - i) * 8;
      SerialNo[i] = ((byte)(SerialNo_int >>> offset & 0xFF));
    }
    return SerialNo;
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.service.utils.PortalUtil
 * JD-Core Version:    0.6.2
 */