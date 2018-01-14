package com.leeson.core.utils;

import com.leeson.common.utils.stringUtils;
import com.leeson.core.bean.Portalbas;
import com.leeson.core.bean.Portalsmslogs;
import com.leeson.core.service.PortalsmslogsService;
import com.leeson.portal.core.model.Config;
import com.leeson.portal.core.model.PhoneCodeMap;
import com.leeson.portal.core.utils.SpringContextHelper;
import config.AppConfig;
import java.util.Date;
import java.util.Map;
import lib.MESSAGEXsend;
import org.apache.log4j.Logger;
import utils.ConfigLoader;

public class SMSSUBMAILRequest
{
  private static Config config = Config.getInstance();
  private static Logger logger = Logger.getLogger(SMSSUBMAILRequest.class);

  private static PortalsmslogsService smslogsService = (PortalsmslogsService)
    SpringContextHelper.getBean("portalsmslogsServiceImpl");

  public static boolean send(String appId, String appKey, String sign, String template, String phone, Long id)
  {
    try
    {
      String text = String.valueOf(Math.round(Math.random() * 8999.0D + 1000.0D));
      Object[] objs = new Object[2];
      objs[0] = text;
      objs[1] = new Date();

      if (SubMail_SendSMS(appId, appKey, sign, template, phone, text)) {
        PhoneCodeMap.getInstance().getPhoneCodeMap().put(phone, objs);

        Portalsmslogs smslogs = new Portalsmslogs();
        smslogs.setInfo(text);
        smslogs.setPhone(phone);
        smslogs.setSendDate(new Date());
        smslogs.setSid(id);
        smslogs.setType("7");
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

  private static boolean SubMail_SendSMS(String appId, String appKey, String sign, String template, String phone, String text)
  {
    try
    {
      if (stringUtils.isBlank(sign)) {
        sign = "code";
      }
      AppConfig config = ConfigLoader.load(appId, appKey);
      MESSAGEXsend submail = new MESSAGEXsend(config);
      submail.addTo(phone);
      submail.setProject(template);
      submail.addVar(sign, text);
      return submail.xsend();
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
 * Qualified Name:     com.leeson.core.utils.SMSSUBMAILRequest
 * JD-Core Version:    0.6.2
 */