package com.leeson.core.utils;

import com.leeson.common.utils.stringUtils;
import com.leeson.core.bean.Portalbas;
import com.leeson.core.bean.Portalsmslogs;
import com.leeson.core.service.PortalsmslogsService;
import com.leeson.portal.core.model.Config;
import com.leeson.portal.core.model.PhoneCodeMap;
import com.leeson.portal.core.utils.SpringContextHelper;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Map;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;

public class SMSKaiLiRequest
{
  private static Config config = Config.getInstance();
  private static Logger logger = Logger.getLogger(SMSKaiLiRequest.class);

  private static PortalsmslogsService smslogsService = (PortalsmslogsService)
    SpringContextHelper.getBean("portalsmslogsServiceImpl");

  public static boolean send(int type, String url, String key, String secret, String text, String sign, String company, String phone, int time, Long id)
  {
    try
    {
      String yzm = 
        String.valueOf(Math.round(Math.random() * 8999.0D + 1000.0D));

      if (type == 1) {
        if (stringUtils.isNotBlank(text)) {
          if ("[yzm]".equals(text)) {
            text = "【" + sign + "】帐号注册验证码是" + yzm + " 有效期" + time + 
              "分钟! 【" + company + "】提供!";
          }
          else if (text.contains("[yzm]"))
            text = text.replace("[yzm]", yzm);
          else {
            text = "【" + sign + "】帐号注册验证码是" + yzm + " 有效期" + time + 
              "分钟! 【" + company + "】提供!";
          }
        }
        else {
          text = "【" + sign + "】帐号注册验证码是" + yzm + " 有效期" + time + 
            "分钟! 【" + company + "】提供!";
        }
      }
      else if (stringUtils.isNotBlank(text)) {
        if ("[yzm]".equals(text)) {
          text = "【" + sign + "】上网验证码是" + yzm + " 有效期" + time + 
            "分钟! 【" + company + "】提供!";
        }
        else if (text.contains("[yzm]"))
          text = text.replace("[yzm]", yzm);
        else {
          text = "【" + sign + "】上网验证码是" + yzm + " 有效期" + time + 
            "分钟! 【" + company + "】提供!";
        }
      }
      else {
        text = "【" + sign + "】上网验证码是" + yzm + " 有效期" + time + 
          "分钟! 【" + company + "】提供!";
      }

      Object[] objs = new Object[2];
      objs[0] = yzm;
      objs[1] = new Date();

      String content = text;
      content = URLEncoder.encode(content, "UTF-8");

      String password = DigestUtils.md5Hex(secret);
      password = DigestUtils.md5Hex(key + password);

      String prams = "username=" + key + "&password=" + password + 
        "&mobile=" + phone + "&content=" + content;
      if (send(url, prams)) {
        PhoneCodeMap.getInstance().getPhoneCodeMap().put(phone, objs);

        Portalsmslogs smslogs = new Portalsmslogs();
        smslogs.setInfo(text);
        smslogs.setPhone(phone);
        smslogs.setSendDate(new Date());
        smslogs.setSid(id);
        smslogs.setType("8");
        smslogsService.addPortalsmslogs(smslogs);
        return true;
      }
      return false;
    } catch (Exception e) {
      if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
        logger.error("==============ERROR Start=============");
        logger.error(e);
        logger.error("ERROR INFO ", e);
        logger.error("==============ERROR End=============");
      }
    }
    return false;
  }

  private static boolean send(String url, String prams)
  {
    String result = "";
    BufferedReader in = null;
    try {
      String urlNameString = url + "?" + prams;
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
      System.out.println("SMS_API Result= " + result);

      if (stringUtils.isNotBlank(result)) {
        try {
          long state = Long.valueOf(result.trim()).longValue();
          if (state > 0L)
            return true;
        }
        catch (Exception e) {
          return false;
        }
      }
      return false;
    } catch (Exception e) {
      System.out.println("发送GET请求出现异常！" + e);
      return false;
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
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.utils.SMSKaiLiRequest
 * JD-Core Version:    0.6.2
 */