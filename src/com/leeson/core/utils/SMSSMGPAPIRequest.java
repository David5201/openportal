package com.leeson.core.utils;

import com.leeson.common.utils.stringUtils;
import com.leeson.core.bean.Portalbas;
import com.leeson.core.bean.Portalsmslogs;
import com.leeson.core.service.PortalsmslogsService;
import com.leeson.portal.core.model.Config;
import com.leeson.portal.core.model.PhoneCodeMap;
import com.leeson.portal.core.utils.SpringContextHelper;
import com.wondertek.ctmp.protocol.smgp.SMGPApi;
import com.wondertek.ctmp.protocol.smgp.SMGPLoginRespMessage;
import com.wondertek.ctmp.protocol.smgp.SMGPSubmitMessage;
import com.wondertek.ctmp.protocol.util.SequenceGenerator;
import java.util.Date;
import java.util.Map;
import org.apache.log4j.Logger;

public class SMSSMGPAPIRequest
{
  private static Config config = Config.getInstance();
  private static Logger logger = Logger.getLogger(SMSSMGPAPIRequest.class);

  private static PortalsmslogsService smslogsService = (PortalsmslogsService)
    SpringContextHelper.getBean("portalsmslogsServiceImpl");

  public static boolean send(String url, String appId, String appKey, String sign, String template, String text, String phone, Long id)
  {
    int port = 8890;
    String host = "218.77.121.59";
    try {
      String[] ts = url.split(":");
      host = ts[0];
      port = Integer.valueOf(ts[1]).intValue();
    }
    catch (Exception localException1)
    {
    }
    try {
      String yzm = 
        String.valueOf(Math.round(Math.random() * 8999.0D + 1000.0D));

      if (stringUtils.isNotBlank(text)) {
        if ("[yzm]".equals(text)) {
          text = "您的验证码是 " + yzm;
        }
        else if (text.contains("[yzm]"))
          text = text.replace("[yzm]", yzm);
        else {
          text = "您的验证码是 " + yzm;
        }
      }
      else {
        text = "您的验证码是 " + yzm;
      }

      Object[] objs = new Object[2];
      objs[0] = yzm;
      objs[1] = new Date();

      if (sendAPI(host, port, appId, appKey, template, sign, phone, text)) {
        PhoneCodeMap.getInstance().getPhoneCodeMap().put(phone, objs);

        Portalsmslogs smslogs = new Portalsmslogs();
        smslogs.setInfo(text);
        smslogs.setPhone(phone);
        smslogs.setSendDate(new Date());
        smslogs.setSid(id);
        smslogs.setType("9");
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

  private static boolean sendAPI(String host, int port, String clientId, String password, String serviceId, String srcTermId, String phone, String text)
  {
    String[] mobiles = { phone };
    String msgContent = text;
    byte msgFmt = 15;
    byte needReport = 0;

    SMGPApi mtApi = new SMGPApi();
    mtApi.setHost(host);
    mtApi.setPort(port);
    mtApi.setClientId(clientId);
    mtApi.setPassword(password);
    mtApi.setVersion((byte)0);
    try
    {
      SMGPLoginRespMessage resp = mtApi.connect();
      if ((resp != null) && (resp.getStatus() == 0)) {
        if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1"))
          logger.info("SMGP Connect Success !!");
      }
      else {
        if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
          logger.info("SMGP Connect Error , Status=" + resp.getStatus());
        }
        return false;
      }
    } catch (Exception e) {
      if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
        logger.error("==============SMGP Connect ERROR Start=============");
        logger.error(e);
        logger.error("ERROR INFO ", e);
        logger.error("==============SMGP Connect ERROR End=============");
      }
      return false;
    }
    try
    {
      SMGPSubmitMessage submit = new SMGPSubmitMessage();
      submit.setSequenceNumber(SequenceGenerator.nextSequence());

      submit.setPriority((byte)3);
      submit.setSrcTermId(srcTermId);
      submit.setChargeTermId(srcTermId);
      submit.setServiceId(serviceId);
      submit.setDestTermIdArray(mobiles);
      submit.setMsgFmt(msgFmt);
      submit.setMsgContent(msgContent);
      submit.setNeedReport(needReport);
      mtApi.sendMsg(submit);
      if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
        logger.info("SMGP Send Success , Status=" + submit);
      }
      mtApi.close();
      return true;
    } catch (Exception e) {
      if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
        logger.error("==============SMGP Send ERROR Start=============");
        logger.error(e);
        logger.error("ERROR INFO ", e);
        logger.error("==============SMGP Send ERROR End=============");
      }
      mtApi.close();
    }return false;
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.utils.SMSSMGPAPIRequest
 * JD-Core Version:    0.6.2
 */