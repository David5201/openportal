package com.leeson.core.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;

public class UpdateOnlineRequest
{
  public static String[] send(String versionNO)
  {
    String result = "";
    BufferedReader in = null;
    try {
      String urlNameString = "http:et_ajax_version.action?v=" + versionNO;
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
      System.out.println("Update_API Result= " + result);
      JSONObject jsonObj = JSONObject.fromObject(result);
      String version = jsonObj.getString("v");
      String versionInfo = jsonObj.getString("info");
      String[] v = new String[2];
      v[0] = version;
      v[1] = versionInfo;
      return v;
    } catch (Exception e) {
      System.out.println("发送GET请求出现异常！" + e);
      return null;
    }
    finally
    {
      try {
        if (in != null)
          in.close();
      }
      catch (Exception e2) {
        System.out.println("关闭资源出现异常！" + e2);
      }
    }
  }

  public static boolean updateService(String v, String localFile, HttpServletRequest request)
  {
    String url = request.getScheme() + ":pdate/UpdateOnline";
    String param = "v=" + v + "&path=" + localFile;
    PrintWriter out = null;
    BufferedReader in = null;
    String result = "";
    boolean back = false;
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
      if (result.contains("update=ok")) {
        back = true;
      }
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
        if (in != null)
          in.close();
      }
      catch (IOException localIOException1)
      {
      }
    }
    return back;
  }

  public static boolean updateService(String localFile, HttpServletRequest request)
  {
    String url = request.getScheme() + ":pdate/Update";
    String param = "path=" + localFile;
    PrintWriter out = null;
    BufferedReader in = null;
    String result = "";
    boolean back = false;
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
      if (result.contains("update=ok")) {
        back = true;
      }
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
        if (in != null)
          in.close();
      }
      catch (IOException localIOException1)
      {
      }
    }
    return back;
  }

  public static boolean restartService(String localFile, HttpServletRequest request)
  {
    String url = request.getScheme() + ":pdate/Restart";
    String param = "path=" + localFile;
    PrintWriter out = null;
    BufferedReader in = null;
    String result = "";
    boolean back = false;
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
      if (result.contains("restart=ok")) {
        back = true;
      }
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
        if (in != null)
          in.close();
      }
      catch (IOException localIOException1)
      {
      }
    }
    return back;
  }

  public static void main(String[] args) {
    send("0");
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.utils.UpdateOnlineRequest
 * JD-Core Version:    0.6.2
 */