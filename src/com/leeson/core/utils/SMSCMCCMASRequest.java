package com.leeson.core.utils;

import com.leeson.common.utils.stringUtils;
import com.leeson.core.bean.Portalbas;
import com.leeson.core.bean.Portalsmslogs;
import com.leeson.core.service.PortalsmslogsService;
import com.leeson.portal.core.model.Config;
import com.leeson.portal.core.model.PhoneCodeMap;
import com.leeson.portal.core.utils.SpringContextHelper;
import com.mascloud.sdkclient.Client;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import org.apache.log4j.Logger;

public class SMSCMCCMASRequest
{
  private static Config config = Config.getInstance();
  private static Logger logger = Logger.getLogger(SMSCMCCMASRequest.class);
  private static boolean isLogin = false;
  private static final Client client = Client.getInstance();
  private static PortalsmslogsService smslogsService = (PortalsmslogsService)
    SpringContextHelper.getBean("portalsmslogsServiceImpl");

  public static boolean send(int type, String url, String key, String secret, String company, String template, String text, String sign, String phone, int time, Long id)
  {
    try
    {
      String yzm = 
        String.valueOf(Math.round(Math.random() * 8999.0D + 1000.0D));

      if (stringUtils.isNotBlank(text)) {
        if ("[yzm]".equals(text)) {
          text = yzm;
        }
        else if (text.contains("[yzm]"))
          text = text.replace("[yzm]", yzm);
        else {
          text = yzm;
        }
      }
      else {
        text = yzm;
      }

      Object[] objs = new Object[2];
      objs[0] = yzm;
      objs[1] = new Date();

      boolean sendResult = false;
      if (stringUtils.isEmpty(template))
        sendResult = doSend(url, key, secret, company, sign, phone, text);
      else {
        sendResult = doSend(url, key, secret, company, sign, phone, yzm, template);
      }
      if (sendResult) {
        PhoneCodeMap.getInstance().getPhoneCodeMap().put(phone, objs);

        Portalsmslogs smslogs = new Portalsmslogs();
        smslogs.setInfo(text);
        smslogs.setPhone(phone);
        smslogs.setSendDate(new Date());
        smslogs.setSid(id);
        smslogs.setType("12");
        smslogsService.addPortalsmslogs(smslogs);
        if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
          logger.info("SMS Send Success");
        }
        return true;
      }
      if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
        logger.info("SMS Send Fail");
      }
      return false;
    } catch (Exception e) {
      if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
        logger.error("==============ERROR Start=============");
        logger.error(e);
        logger.error("ERROR INFO ", e);
        logger.error("==============ERROR End=============");
        logger.info("SMS Send Fail");
      }
    }
    return false;
  }

  private static boolean doSend(String url, String key, String secret, String company, String sign, String phone, String text)
  {
    try
    {
      if (!isLogin) {
        logger.info("SMS API Login ...");
        if (client.login(url, key, secret, company)) {
          logger.info("SMS API Login Success !");
          isLogin = true;
        }
      }
      int sendResult = client.sendDSMS(new String[] { phone }, text, "", 
        1, sign, UUID.randomUUID().toString().replace("-", ""), 
        false);
      logger.info("SMS API Send Result= " + sendResult);
      if (sendResult == 1)
        return true;
    }
    catch (Exception localException) {
    }
    return false;
  }

  private static boolean doSend(String url, String key, String secret, String company, String sign, String phone, String text, String template)
  {
    try
    {
      if (!isLogin) {
        logger.info("SMS API Login ...");
        if (client.login(url, key, secret, company)) {
          logger.info("SMS API Login Success !");
          isLogin = true;
        }
      }
      int sendResult = client.sendTSMS(new String[] { phone }, template, new String[] { text }, "", 1, sign, UUID.randomUUID().toString().replace("-", ""));
      logger.info("SMS API Send Result= " + sendResult);
      if (sendResult == 1)
        return true;
    }
    catch (Exception localException) {
    }
    return false;
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.utils.SMSCMCCMASRequest
 * JD-Core Version:    0.6.2
 */