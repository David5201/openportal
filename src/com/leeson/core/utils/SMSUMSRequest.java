package com.leeson.core.utils;

import com.leeson.common.utils.stringUtils;
import com.leeson.core.bean.Portalbas;
import com.leeson.core.bean.Portalsmslogs;
import com.leeson.core.service.PortalsmslogsService;
import com.leeson.portal.core.model.Config;
import com.leeson.portal.core.model.PhoneCodeMap;
import com.leeson.portal.core.utils.SpringContextHelper;
import java.util.Date;
import java.util.Map;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.Logger;

public class SMSUMSRequest
{
  private static Config config = Config.getInstance();
  private static Logger logger = Logger.getLogger(SMSUMSRequest.class);

  private static PortalsmslogsService smslogsService = (PortalsmslogsService)
    SpringContextHelper.getBean("portalsmslogsServiceImpl");

  public static boolean send(String url, String key, String secret, String template, String text, String phone, Long id)
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

      if (SMSRequest(url, key, secret, template, text, phone)) {
        PhoneCodeMap.getInstance().getPhoneCodeMap().put(phone, objs);

        Portalsmslogs smslogs = new Portalsmslogs();
        smslogs.setInfo(text);
        smslogs.setPhone(phone);
        smslogs.setSendDate(new Date());
        smslogs.setSid(id);
        smslogs.setType("5");
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

  private static boolean SMSRequest(String url, String LoginName, String Password, String SpCode, String text, String phone)
  {
    String info = null;
    try {
      HttpClient httpclient = new HttpClient();
      PostMethod post = new PostMethod(url);
      post.getParams().setParameter(
        "http.protocol.content-charset", "gbk");
      post.addParameter("SpCode", SpCode);
      post.addParameter("LoginName", LoginName);
      post.addParameter("Password", Password);
      post.addParameter("MessageContent", text);
      post.addParameter("UserNumber", phone);
      post.addParameter("SerialNumber", "");
      post.addParameter("ScheduleTime", "");
      post.addParameter("ExtendAccessNum", "");
      post.addParameter("f", "1");
      httpclient.executeMethod(post);
      info = new String(post.getResponseBody(), "gbk");
      if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
        logger.info("SMS Send Result : " + info);
      }
      return true;
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
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.utils.SMSUMSRequest
 * JD-Core Version:    0.6.2
 */