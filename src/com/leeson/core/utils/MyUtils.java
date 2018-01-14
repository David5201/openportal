package com.leeson.core.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang.StringEscapeUtils;

public class MyUtils
{
  public static boolean checkUserName(String userName)
  {
    String regex = "([a-z]|[A-Z]|[0-9]|[\\u4e00-\\u9fa5])+";
    Pattern p = Pattern.compile(regex);
    Matcher m = p.matcher(userName);
    return m.matches();
  }

  public static boolean checkInput(String input)
  {
    input = URLDecoder.decode(input);
    input = StringEscapeUtils.unescapeHtml(input);
    String regex = "([a-z]|[A-Z]|[0-9]|[\\u4e00-\\u9fa5]|[\\s\t\n])+";
    Pattern p = Pattern.compile(regex);
    Matcher m = p.matcher(input);
    return m.matches();
  }

  public static String creatMac()
  {
    byte[] SerialNo = new byte[2];
    short SerialNo_int = (short)(int)(1.0D + Math.random() * 32767.0D);
    for (int i = 0; i < 2; i++) {
      int offset = (SerialNo.length - 1 - i) * 8;
      SerialNo[i] = ((byte)(SerialNo_int >>> offset & 0xFF));
    }

    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < SerialNo.length; i++) {
      String hex = Integer.toHexString(SerialNo[i] & 0xFF);
      if (hex.length() == 1) {
        hex = '0' + hex;
      }
      sb.append(":" + hex);
    }
    return sb.toString();
  }

  public static String creatToken() {
    byte[] SerialNo = new byte[2];
    short SerialNo_int = (short)(int)(1.0D + Math.random() * 32767.0D);
    for (int i = 0; i < 2; i++) {
      int offset = (SerialNo.length - 1 - i) * 8;
      SerialNo[i] = ((byte)(SerialNo_int >>> offset & 0xFF));
    }

    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < SerialNo.length; i++) {
      String hex = Integer.toHexString(SerialNo[i] & 0xFF);
      if (hex.length() == 1) {
        hex = '0' + hex;
      }
      sb.append(hex);
    }
    return sb.toString();
  }

  private static String callCmd(String[] cmd)
  {
    String result = "";
    String line = "";
    try {
      Process proc = Runtime.getRuntime().exec(cmd);
      InputStreamReader is = new InputStreamReader(proc.getInputStream());
      BufferedReader br = new BufferedReader(is);
      while ((line = br.readLine()) != null)
        result = result + line;
    }
    catch (Exception e) {
      System.out.println("这不是Linux系统！！");
      return "";
    }
    return result;
  }

  private static String callCmd(String[] cmd, String[] another)
  {
    String result = "";
    String line = "";
    try {
      Runtime rt = Runtime.getRuntime();
      Process proc = rt.exec(cmd);
      proc.waitFor();
      proc = rt.exec(another);
      InputStreamReader is = new InputStreamReader(proc.getInputStream());
      BufferedReader br = new BufferedReader(is);
      while ((line = br.readLine()) != null)
        result = result + line;
    }
    catch (Exception e) {
      System.out.println("这不是windows系统！！");
      return "";
    }
    return result;
  }

  private static String filterMacAddress(String ip, String sourceString, String macSeparator)
  {
    String result = "";
    String regExp = "((([0-9,A-F,a-f]{1,2}" + macSeparator + "){1,5})[0-9,A-F,a-f]{1,2})";
    Pattern pattern = Pattern.compile(regExp);
    Matcher matcher = pattern.matcher(sourceString);
    while (matcher.find()) {
      result = matcher.group(1);
      if (sourceString.lastIndexOf(ip) <= sourceString.indexOf(matcher.group(1))) {
        break;
      }
    }
    return result;
  }

  private static String getMacInWindows(String ip)
  {
    String result = "";
    String[] cmd = { "cmd", "/c", "ping " + ip };
    String[] another = { "cmd", "/c", "arp -a " + ip };
    String cmdResult = callCmd(cmd, another);
    result = filterMacAddress(ip, cmdResult, "-");
    return result;
  }

  private static String getMacInLinux(String ip)
  {
    String result = "";
    String[] cmd = { "/bin/sh", "-c", "ping " + ip + " -c 2 && arp -a" };
    String cmdResult = callCmd(cmd);
    result = filterMacAddress(ip, cmdResult, ":");
    return result;
  }

  private static String getMacAddress(String ip)
  {
    String macAddress = getMacInWindows(ip);
    if ((macAddress != null) && (!"".equals(macAddress))) {
      macAddress = macAddress.trim();
    }
    if ((macAddress == null) || ("".equals(macAddress))) {
      macAddress = getMacInLinux(ip);
      if ((macAddress != null) && (!"".equals(macAddress))) {
        macAddress = macAddress.trim();
      }
    }
    return macAddress;
  }

  public static String getMac(String ip)
  {
    String mac = getMacAddress(ip);
    if ((mac == null) || (mac.equals(""))) {
      System.out.println("mac 地址获取失败！！！");

      mac = "aa:aa:aa:aa:aa:aa";
      System.out.println("随机创建mac: " + mac);
    }
    mac = mac.replace("-", ":");
    System.out.println("ip: " + ip + " Mac: " + mac);
    return mac;
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.utils.MyUtils
 * JD-Core Version:    0.6.2
 */