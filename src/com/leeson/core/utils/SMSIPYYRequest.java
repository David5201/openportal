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
import org.apache.log4j.Logger;

public class SMSIPYYRequest
{
  private static Config config = Config.getInstance();
  private static Logger logger = Logger.getLogger(SMSIPYYRequest.class);

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

      String params = "action=send&userid=&account=" + account + "&password=" + password + "&mobile=" + phone + "&content=" + text + "&sendTime=&extno=";
      String result = HttpsUtils.httpsRequest(url, "POST", params);
      if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
        logger.info("SMS Send Result = " + result);
      }

      if (result.contains("Success")) {
        PhoneCodeMap.getInstance().getPhoneCodeMap().put(phone, objs);

        Portalsmslogs smslogs = new Portalsmslogs();
        smslogs.setInfo(text);
        smslogs.setPhone(phone);
        smslogs.setSendDate(new Date());
        smslogs.setSid(id);
        smslogs.setType("10");
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
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.utils.SMSIPYYRequest
 * JD-Core Version:    0.6.2
 */