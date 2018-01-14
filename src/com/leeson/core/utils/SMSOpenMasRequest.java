package com.leeson.core.utils;

import com.chinamobile.openmas.client.Sms;
import com.leeson.common.utils.stringUtils;
import com.leeson.core.bean.Portalbas;
import com.leeson.core.bean.Portalsmslogs;
import com.leeson.core.service.PortalsmslogsService;
import com.leeson.portal.core.model.Config;
import com.leeson.portal.core.model.PhoneCodeMap;
import com.leeson.portal.core.utils.SpringContextHelper;
import java.util.Date;
import java.util.Map;
import org.apache.log4j.Logger;

public class SMSOpenMasRequest
{
  private static Config config = Config.getInstance();
  private static Logger logger = Logger.getLogger(SMSOpenMasRequest.class);

  private static PortalsmslogsService smslogsService = (PortalsmslogsService)
    SpringContextHelper.getBean("portalsmslogsServiceImpl");

  public static boolean send(int type, String url, String key, String secret, String template, String text, String sign, String company, String phone, int time, Long id)
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

      Sms sms = new Sms(url);
      String[] destinationAddresses = { phone };
      String result = sms.SendMessage(destinationAddresses, text, 
        template, key, secret);
      if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
        logger.info("SMS Send Result : " + result);
      }

      PhoneCodeMap.getInstance().getPhoneCodeMap().put(phone, objs);
      Portalsmslogs smslogs = new Portalsmslogs();
      smslogs.setInfo(text);
      smslogs.setPhone(phone);
      smslogs.setSendDate(new Date());
      smslogs.setSid(id);
      smslogs.setType("6");
      smslogsService.addPortalsmslogs(smslogs);
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
 * Qualified Name:     com.leeson.core.utils.SMSOpenMasRequest
 * JD-Core Version:    0.6.2
 */