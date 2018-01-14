package com.leeson.portal.core.controller.utils;

import java.io.PrintStream;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class BASE64
{
  public static String decryptBASE64(String key)
    throws Exception
  {
    return new String(new BASE64Decoder().decodeBuffer(key));
  }

  public static String encryptBASE64(String key)
    throws Exception
  {
    return new BASE64Encoder().encodeBuffer(key.getBytes());
  }

  public static void main(String[] args) throws Exception
  {
    String data = encryptBASE64("nasname=iKuai&gwid=d17c3a0c684b421966e0ac4ba01b4015&user_ip=192.168.1.1&mac=00:11:22:33:44:55&route_ver=2.5.0&bssid=00:02:06:0a:0d:f2&ssid=iKuai8&apmac=00:02:06:0a:0d:f1&timestamp=1437467086");
    System.out.println("加密后：" + data);

    System.out.println("解密后：" + decryptBASE64(data));

    System.out.println("解密后：" + decryptBASE64("bmFzbmFtZT1pS3VhaSZnd2lkPWQxN2MzYTBjNjg0YjQyMTk2NmUwYWM0YmEwMWI0MDE1JnVzZXJfaXA9MTkyLjE2OC4xLjEmbWFjPTAwOjExOjIyOjMzOjQ0OjU1JnJvdXRlX3Zlcj0yLjUuMCZic3NpZD0wMDowMjowNjowYTowZDpmMiZzc2lkPWlLdWFpOCZhcG1hYz0wMDowMjowNjowYTowZDpmMSZ0aW1lc3RhbXA9MTQzNzQ2NzA4Ngo="));
    System.out.println("解密后：" + decryptBASE64("aHR0cDovL3d3dy5xcS5jb20vCg=="));
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.controller.utils.BASE64
 * JD-Core Version:    0.6.2
 */