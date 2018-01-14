package com.leeson.portal.core.utils;

import java.io.PrintStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

public final class NetworkUtils
{
  private static String getMacAddress()
  {
    StringBuilder macAddress = new StringBuilder();
    try {
      Enumeration el = 
        NetworkInterface.getNetworkInterfaces();
      while (el.hasMoreElements()) {
        byte[] mac = ((NetworkInterface)el.nextElement()).getHardwareAddress();
        if (mac != null)
        {
          StringBuilder builder = new StringBuilder();
          for (int i = 0; i < mac.length; i++) {
            builder.append(hexByte(mac[i]));
            if (i < mac.length - 1) {
              builder.append("-");
            }
          }

          macAddress.append("[");
          macAddress.append(builder);
          macAddress.append("]");
        }
      }
    } catch (Exception localException) {
    }
    String result = macAddress.toString();
    result = result.replace("[]", "");
    result = result.replace("[00-00-00-00-00-00-00-e0]", "");
    result = result.toUpperCase();
    return result;
  }

  private static String hexByte(byte b) {
    String s = "000000" + Integer.toHexString(b);
    return s.substring(s.length() - 2);
  }

  public static String getInfo() {
    StringBuilder sb = new StringBuilder();
    sb.append("Version : OpenPortalServer V3.10.0.0 2017-07-01");
    sb.append("    ");
    sb.append(" OS : " + System.getProperty("os.name"));
    sb.append("    ");
    sb.append(" MAC : " + getMacAddress());
    sb.append("    ");
    sb.append(" IP : ");
    String configString = "";
    try {
      configString = InetAddress.getLocalHost().getHostAddress();
    } catch (Exception e) {
      configString = "ERROR";
    }
    sb.append(configString);
    sb.append("    ");
    sb.append(" HOST NAME : ");
    try {
      configString = InetAddress.getLocalHost().getHostName();
    } catch (Exception e) {
      configString = "ERROR";
    }
    sb.append(configString);
    return sb.toString();
  }

  public static void main(String[] args) {
    System.out.println(getInfo());
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.utils.NetworkUtils
 * JD-Core Version:    0.6.2
 */