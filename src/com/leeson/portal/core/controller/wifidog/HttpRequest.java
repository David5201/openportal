package com.leeson.portal.core.controller.wifidog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

public class HttpRequest
{
  public static void sendGet(String url, String param)
  {
    String result = "";
    BufferedReader in = null;
    try {
      String urlNameString = url + "?" + param;
      URL realUrl = new URL(urlNameString);

      URLConnection connection = realUrl.openConnection();

      connection.setRequestProperty("accept", "*/*");
      connection.setRequestProperty("connection", "Keep-Alive");
      connection.setRequestProperty("user-agent", 
        "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");

      connection.connect();

      in = new BufferedReader(new InputStreamReader(
        connection.getInputStream()));
      String line;
      while ((line = in.readLine()) != null)
      {
        result = result + line;
      }
      System.out.println(result);
    } catch (Exception e) {
      System.out.println("发送GET请求出现异常！" + e);
      try
      {
        if (in != null)
          in.close();
      }
      catch (Exception localException1)
      {
      }
    }
    finally
    {
      try
      {
        if (in != null)
          in.close();
      }
      catch (Exception localException2)
      {
      }
    }
  }

  public static String sendPost(String url, String param)
  {
    PrintWriter out = null;
    BufferedReader in = null;
    String result = "";
    try {
      URL realUrl = new URL(url);

      URLConnection conn = realUrl.openConnection();

      conn.setRequestProperty("accept", "*/*");
      conn.setRequestProperty("connection", "Keep-Alive");
      conn.setRequestProperty("user-agent", 
        "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");

      conn.setDoOutput(true);
      conn.setDoInput(true);

      out = new PrintWriter(conn.getOutputStream());

      out.print(param);

      out.flush();

      in = new BufferedReader(
        new InputStreamReader(conn.getInputStream()));
      String line;
      while ((line = in.readLine()) != null)
      {
        result = result + line;
      }
      System.out.println(result);
    }
    catch (Exception localException)
    {
      try
      {
        if (out != null) {
          out.close();
        }
        if (in != null)
          in.close();
      }
      catch (IOException localIOException)
      {
      }
    }
    finally
    {
      try
      {
        if (out != null) {
          out.close();
        }
        if (in != null) {
          in.close();
        }
      }
      catch (IOException localIOException1)
      {
      }
    }
    return result;
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.controller.wifidog.HttpRequest
 * JD-Core Version:    0.6.2
 */