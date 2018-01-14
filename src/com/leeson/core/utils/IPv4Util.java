package com.leeson.core.utils;

import java.io.PrintStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IPv4Util
{
  public static long ipToLong(String strIp)
  {
    long[] ip = new long[4];

    int position1 = strIp.indexOf(".");
    int position2 = strIp.indexOf(".", position1 + 1);
    int position3 = strIp.indexOf(".", position2 + 1);

    ip[0] = Long.parseLong(strIp.substring(0, position1));
    ip[1] = Long.parseLong(strIp.substring(position1 + 1, position2));
    ip[2] = Long.parseLong(strIp.substring(position2 + 1, position3));
    ip[3] = Long.parseLong(strIp.substring(position3 + 1));
    return (ip[0] << 24) + (ip[1] << 16) + (ip[2] << 8) + ip[3];
  }

  public static String longToIP(long longIp)
  {
    StringBuffer sb = new StringBuffer("");

    sb.append(String.valueOf(longIp >>> 24));
    sb.append(".");

    sb.append(String.valueOf((longIp & 0xFFFFFF) >>> 16));
    sb.append(".");

    sb.append(String.valueOf((longIp & 0xFFFF) >>> 8));
    sb.append(".");

    sb.append(String.valueOf(longIp & 0xFF));
    return sb.toString();
  }

  public static boolean isIP(String addr)
  {
    if ((addr.length() < 7) || (addr.length() > 15) || ("".equals(addr)))
    {
      return false;
    }

    String rexp = "\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b";

    Pattern pat = Pattern.compile(rexp);

    Matcher mat = pat.matcher(addr);

    boolean ipAddress = mat.find();

    return ipAddress;
  }

  public static void main(String[] args)
    throws Exception
  {
    String ipAddr = "1.168.8.1";
    String ipAddr1 = "192.168.7.254";

    long ipInt = ipToLong(ipAddr);
    long ipInt1 = ipToLong(ipAddr1);

    System.out.println("IP: " + ipAddr + "  --> int: " + ipInt);
    System.out.println("IP: " + ipAddr1 + "  --> int: " + ipInt1);
    System.out.println(ipInt > ipInt1);

    String ipAddrTest = "1.255.255.0";
    System.out.println("is IP: " + isIP(ipAddrTest));
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.utils.IPv4Util
 * JD-Core Version:    0.6.2
 */