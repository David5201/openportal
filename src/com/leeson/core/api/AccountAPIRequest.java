package com.leeson.core.api;

import com.leeson.core.bean.Portalaccount;
import com.leeson.core.bean.Portalbas;
import com.leeson.core.query.PortalaccountQuery;
import com.leeson.core.service.PortalaccountService;
import com.leeson.portal.core.controller.utils.BASE64;
import com.leeson.portal.core.model.Config;
import com.leeson.portal.core.utils.SpringContextHelper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

public class AccountAPIRequest
{
  private static Config config = Config.getInstance();
  private static Logger logger = Logger.getLogger(AccountAPIRequest.class);

  private static PortalaccountService accountService = (PortalaccountService)
    SpringContextHelper.getBean("portalaccountServiceImpl");

  public static boolean sendPost(String url, String usr, String pwd, String ip, String mac, String type)
  {
    String pwdEn = pwd;
    try {
      pwdEn = BASE64.encryptBASE64(pwd);
    } catch (Exception e1) {
      if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
        logger.error("Account API Error : " + e1);
      }
    }

    String param = "usr=" + usr + "&pwd=" + pwdEn + "&ip=" + ip + "&mac=" + mac + "&type=" + type;

    if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
      logger.info("Account API Request : URL=" + url);
      logger.info("Account API Request : Params=" + param);
    }

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
      if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
        logger.info("Account API Result : " + result);
      }

      JSONObject jsonObj = JSONObject.fromObject(result);
      int state = jsonObj.getInt("status");
      String info = jsonObj.getString("info");
      info = decodeUnicode(info);
      if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
        logger.info("Account API Info : " + info);
      }

      if (state == 1) {
        JSONObject data = jsonObj.getJSONObject("data");
        double hour = data.getDouble("alivetime");
        long time = (long)(hour * 3600000.0D);
        if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
          logger.info("Account API Time : " + time);
        }

        PortalaccountQuery aq = new PortalaccountQuery();
        aq.setLoginName(usr);
        aq.setLoginNameLike(false);
        List accounts = accountService.getPortalaccountList(aq);
        if (accounts.size() > 0) {
          Portalaccount account = (Portalaccount)accounts.get(0);
          account.setPassword(pwd);
          account.setTime(Long.valueOf(time));
          accountService.updatePortalaccountByKey(account);
        } else {
          try {
            Portalaccount account = new Portalaccount();
            account.setLoginName(usr);
            account.setPassword(pwd);
            account.setName(usr);
            account.setDate(new Date());
            account.setState("1");
            account.setTime(Long.valueOf(time));
            account.setMaclimit(Integer.valueOf(0));
            account.setMaclimitcount(Integer.valueOf(0));
            account.setAutologin(Integer.valueOf(0));
            account.setSpeed(Long.valueOf(1L));
            accountService.addPortalaccount(account);
          }
          catch (Exception e) {
            if (((Portalbas)config.getConfigMap().get("")).getIsdebug()
              .equals("1")) {
              logger.error("==============ERROR Start=============");
              logger.error(e);
              logger.error("ERROR INFO ", e);
              logger.error("==============ERROR End=============");
            }
            return false;
          }
        }
        return true;
      }
    }
    catch (Exception e) {
      if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
        logger.error("==============ERROR Start=============");
        logger.error(e);
        logger.error("ERROR INFO ", e);
        logger.error("==============ERROR End=============");
      }
      return false;
    }
    finally
    {
      try {
        if (out != null) {
          out.close();
        }
        if (in != null)
          in.close();
      }
      catch (IOException localIOException3)
      {
      }
    }
    try
    {
      if (out != null) {
        out.close();
      }
      if (in != null) {
        in.close();
      }
    }
    catch (IOException localIOException4)
    {
    }
    return false;
  }

  public static boolean guestSendPost(String url, String usr, String ip, String mac, String type)
  {
    String param = "usr=" + usr + "&ip=" + ip + "&mac=" + mac + "&type=" + type;

    if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
      logger.info("Guest Account API Request : URL=" + url);
      logger.info("Guest Account API Request : Params=" + param);
    }

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
      if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
        logger.info("Guest Account API Result : " + result);
      }

      JSONObject jsonObj = JSONObject.fromObject(result);
      int state = jsonObj.getInt("status");
      String info = jsonObj.getString("info");
      info = decodeUnicode(info);
      if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
        logger.info("Account API Info : " + info);
      }

      if (state == 1) {
        JSONObject data = jsonObj.getJSONObject("data");
        double hour = data.getDouble("alivetime");
        long time = (long)(hour * 3600000.0D);
        if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
          logger.info("Account API Time : " + time);
        }
        return true;
      }
    } catch (Exception e) {
      if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
        logger.error("==============ERROR Start=============");
        logger.error(e);
        logger.error("ERROR INFO ", e);
        logger.error("==============ERROR End=============");
      }
      return false;
    }
    finally
    {
      try {
        if (out != null) {
          out.close();
        }
        if (in != null)
          in.close();
      }
      catch (IOException localIOException2)
      {
      }
    }
    try
    {
      if (out != null) {
        out.close();
      }
      if (in != null) {
        in.close();
      }
    }
    catch (IOException localIOException3)
    {
    }
    return false;
  }

  public static String decodeUnicode(String theString)
  {
    int len = theString.length();

    StringBuffer outBuffer = new StringBuffer(len);

    for (int x = 0; x < len; )
    {
      char aChar = theString.charAt(x++);

      if (aChar == '\\')
      {
        aChar = theString.charAt(x++);

        if (aChar == 'u')
        {
          int value = 0;

          for (int i = 0; i < 4; i++)
          {
            aChar = theString.charAt(x++);

            switch (aChar)
            {
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
              value = (value << 4) + aChar - 48;
              break;
            case 'a':
            case 'b':
            case 'c':
            case 'd':
            case 'e':
            case 'f':
              value = (value << 4) + 10 + aChar - 97;
              break;
            case 'A':
            case 'B':
            case 'C':
            case 'D':
            case 'E':
            case 'F':
              value = (value << 4) + 10 + aChar - 65;
              break;
            case ':':
            case ';':
            case '<':
            case '=':
            case '>':
            case '?':
            case '@':
            case 'G':
            case 'H':
            case 'I':
            case 'J':
            case 'K':
            case 'L':
            case 'M':
            case 'N':
            case 'O':
            case 'P':
            case 'Q':
            case 'R':
            case 'S':
            case 'T':
            case 'U':
            case 'V':
            case 'W':
            case 'X':
            case 'Y':
            case 'Z':
            case '[':
            case '\\':
            case ']':
            case '^':
            case '_':
            case '`':
            default:
              throw new IllegalArgumentException(
                "Malformed   \\uxxxx   encoding.");
            }
          }

          outBuffer.append((char)value);
        } else {
          if (aChar == 't')
            aChar = '\t';
          else if (aChar == 'r') {
            aChar = '\r';
          }
          else if (aChar == 'n')
          {
            aChar = '\n';
          }
          else if (aChar == 'f')
          {
            aChar = '\f';
          }
          outBuffer.append(aChar);
        }

      }
      else
      {
        outBuffer.append(aChar);
      }
    }

    return outBuffer.toString();
  }

  public static boolean send(String url, String usr, String pwd, String ip, String mac, String type)
  {
    return sendPost(url, usr, pwd, ip, mac, type);
  }

  public static boolean guestSend(String url, String usr, String ip, String mac, String type) {
    return guestSendPost(url, usr, ip, mac, type);
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.api.AccountAPIRequest
 * JD-Core Version:    0.6.2
 */