package com.leeson.core.utils;

import com.leeson.core.bean.Portalsmslogs;
import com.leeson.core.service.PortalsmslogsService;
import com.leeson.portal.core.model.PhoneCodeMap;
import com.leeson.portal.core.utils.SpringContextHelper;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.Map;
import net.sf.json.JSONObject;

public class SMSRequest
{
  private static PortalsmslogsService smslogsService = (PortalsmslogsService)
    SpringContextHelper.getBean("portalsmslogsServiceImpl");

  public static boolean send(String url, String phone, String key, Long id)
  {
    String result = "";
    BufferedReader in = null;
    try {
      String urlNameString = url + "?gwid=" + key + "&mobile=" + phone;
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
      JSONObject jsonObj = JSONObject.fromObject(result);
      String status = jsonObj.getString("status");
      if ("1".equals(status))
      {
        JSONObject data = jsonObj.getJSONObject("data");
        String yzm = data.getString("sms_code");
        System.out.println("phone=" + phone + " code=" + yzm);
        Object[] objs = new Object[2];
        objs[0] = yzm;
        objs[1] = new Date();
        PhoneCodeMap.getInstance().getPhoneCodeMap().put(phone, objs);

        Portalsmslogs smslogs = new Portalsmslogs();
        smslogs.setInfo(yzm);
        smslogs.setPhone(phone);
        smslogs.setSendDate(new Date());
        smslogs.setSid(id);
        smslogs.setType("0");
        smslogsService.addPortalsmslogs(smslogs);
        return true;
      }
      return false;
    }
    catch (Exception e) {
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
 * Qualified Name:     com.leeson.core.utils.SMSRequest
 * JD-Core Version:    0.6.2
 */