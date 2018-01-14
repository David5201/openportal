package com.leeson.core.utils;

import com.leeson.common.utils.stringUtils;
import com.leeson.core.bean.Portalbas;
import com.leeson.core.bean.Portalsmslogs;
import com.leeson.core.service.PortalsmslogsService;
import com.leeson.portal.core.model.Config;
import com.leeson.portal.core.model.PhoneCodeMap;
import com.leeson.portal.core.utils.SpringContextHelper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Map;
import org.apache.log4j.Logger;

public class SMSWXHYRequest
{
  private static Config config = Config.getInstance();
  private static Logger logger = Logger.getLogger(SMSWXHYRequest.class);

  private static PortalsmslogsService smslogsService = (PortalsmslogsService)
    SpringContextHelper.getBean("portalsmslogsServiceImpl");

  public static boolean send(String url, String account, String password, String text, String phone, Long id)
  {
    try
    {
      String yzm = 
        String.valueOf(Math.round(Math.random() * 8999.0D + 1000.0D));

      if (stringUtils.isNotBlank(text)) {
        if (text.contains("[yzm]"))
          text = text.replace("[yzm]", yzm);
        else
          text = yzm;
      }
      else {
        text = yzm;
      }

      Object[] objs = new Object[2];
      objs[0] = yzm;
      objs[1] = new Date();

      String decodeText = new String(text);
      text = URLEncoder.encode(text, "UTF-8");
      String params = "apName=" + account + "&apPassword=" + password + "&calledNumber=" + phone + "&content=" + text + "&sendTime=&srcId=&ServiceId=";
      String result = sendPost(url, params);

      if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
        logger.info("SMS Send Result = " + result);
      }

      if (result.contains("<error>0</error>")) {
        PhoneCodeMap.getInstance().getPhoneCodeMap().put(phone, objs);

        Portalsmslogs smslogs = new Portalsmslogs();
        smslogs.setInfo(decodeText);
        smslogs.setPhone(phone);
        smslogs.setSendDate(new Date());
        smslogs.setSid(id);
        smslogs.setType("11");
        smslogsService.addPortalsmslogs(smslogs);
        return true;
      }
      return false;
    }
    catch (Exception e) {
      if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
        logger.error("==============ERROR Start=============");
        logger.error(e);
        logger.error("ERROR INFO ", e);
        logger.error("==============ERROR End=============");
      }
    }
    return false;
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
 * Qualified Name:     com.leeson.core.utils.SMSWXHYRequest
 * JD-Core Version:    0.6.2
 */