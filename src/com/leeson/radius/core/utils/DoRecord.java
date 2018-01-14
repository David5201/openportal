package com.leeson.radius.core.utils;

import com.leeson.common.utils.ThreadLocalDateUtil;
import com.leeson.common.utils.stringUtils;
import com.leeson.core.bean.Config;
import com.leeson.core.bean.Portalaccount;
import com.leeson.core.bean.Portallinkrecord;
import com.leeson.core.bean.Radiuslinkrecordall;
import com.leeson.core.query.PortalaccountQuery;
import com.leeson.core.service.ConfigService;
import com.leeson.core.service.PortalaccountService;
import com.leeson.core.service.PortallinkrecordService;
import com.leeson.core.service.RadiuslinkrecordallService;
import com.leeson.portal.core.model.RadiusAPIMap;
import com.leeson.portal.core.utils.HttpRequest;
import com.leeson.portal.core.utils.SpringContextHelper;
import com.leeson.radius.core.Tool;
import com.leeson.radius.core.model.RadiusOnlineMap;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

public class DoRecord
{
  private static Logger log = Logger.getLogger(DoRecord.class);

  private static ApplicationContext ac = SpringContextHelper.getApplicationContext();
  private static PortallinkrecordService portallinkrecordService = (PortallinkrecordService)ac
    .getBean("portallinkrecordServiceImpl");

  private static PortalaccountService portalaccountService = (PortalaccountService)ac
    .getBean("portalaccountServiceImpl");

  private static RadiuslinkrecordallService radiuslinkallService = (RadiuslinkrecordallService)ac
    .getBean("radiuslinkrecordallServiceImpl");

  private static ConfigService configService = (ConfigService)
    SpringContextHelper.getBean("configServiceImpl");

  public static void coreMethod(String AcctSessionId, String logInfo)
  {
    if (stringUtils.isNotBlank(AcctSessionId)) {
      try {
        String[] radiusOnlineInfo = 
          (String[])RadiusOnlineMap.getInstance()
          .getRadiusOnlineMap().get(AcctSessionId);
        if ((radiusOnlineInfo != null) && (radiusOnlineInfo.length > 0)) {
          doLinkAll(radiusOnlineInfo, logInfo);
          Tool.writeLog(logInfo, " NasIP:" + radiusOnlineInfo[0] + 
            " UserIP: " + radiusOnlineInfo[2] + " Mac: " + 
            radiusOnlineInfo[3] + " UserName: " + 
            radiusOnlineInfo[4] + " !");
          if (1 == configService.getConfigByKey(Long.valueOf(1L)).getRadiusOn().intValue()) {
            PortalaccountQuery aq = new PortalaccountQuery();
            aq.setLoginName(radiusOnlineInfo[4]);
            aq.setLoginNameLike(false);
            List users = portalaccountService
              .getPortalaccountList(aq);
            if ((users != null) && (users.size() > 0)) {
              Portalaccount account = (Portalaccount)users.get(0);
              String state = account.getState();
              Date now = new Date();
              Date loginTime = ThreadLocalDateUtil.parse(radiusOnlineInfo[9]);
              Long costTime = Long.valueOf(now.getTime() - loginTime.getTime());
              long in = 0L;
              long out = 0L;
              try {
                in = Long.valueOf(radiusOnlineInfo[11]).longValue();
                out = Long.valueOf(radiusOnlineInfo[12]).longValue();
              } catch (Exception localException1) {
              }
              long octets = in + out;
              Portallinkrecord linkRecord = new Portallinkrecord();
              linkRecord.setStartDate(loginTime);
              linkRecord.setIp(radiusOnlineInfo[2]);
              linkRecord.setBasip(radiusOnlineInfo[0]);
              linkRecord.setMac(radiusOnlineInfo[3]);
              linkRecord.setIns(Long.valueOf(in));
              linkRecord.setOuts(Long.valueOf(out));
              linkRecord.setOctets(Long.valueOf(octets));
              linkRecord.setLoginName(radiusOnlineInfo[4]);
              linkRecord.setState(state);
              linkRecord.setUid(account.getId());
              linkRecord.setEndDate(now);
              linkRecord.setTime(costTime);
              linkRecord.setEx1("Radius");
              linkRecord.setEx2(logInfo);
              portallinkrecordService
                .addPortallinkrecord(linkRecord);
              if (!state.equals("1")) {
                Long haveTime = account.getTime();
                Long newHaveTime = Long.valueOf(haveTime.longValue() - costTime.longValue());
                if ((state.equals("3")) && 
                  (account.getDate().getTime() <= now.getTime())) {
                  account.setState("2");
                  portalaccountService.updatePortalaccountByKey(account);
                }

                if (state.equals("2")) {
                  if (newHaveTime.longValue() <= 0L) {
                    account.setState("4");
                  }
                  account.setTime(newHaveTime);
                  portalaccountService.updatePortalaccountByKey(account);
                }
                if ((state.equals("4")) || (state.equals("0"))) {
                  long haveOctets = account.getOctets().longValue() - octets;
                  if (haveOctets <= 0L) {
                    account.setState("0");
                  }
                  account.setOctets(Long.valueOf(haveOctets));
                  portalaccountService.updatePortalaccountByKey(account);
                }
              }

              RadiusOnlineMap.getInstance().getRadiusOnlineMap().remove(AcctSessionId);
              try
              {
                String radiusUrl = (String)RadiusAPIMap.getInstance().getRadiusAPIMap().get("url");
                String radiusState = (String)RadiusAPIMap.getInstance().getRadiusAPIMap().get("state");
                if ((stringUtils.isNotBlank(radiusState)) && (stringUtils.isNotBlank(radiusUrl)) && ("1".equals(radiusState))) {
                  String params = "u=" + radiusOnlineInfo[4] + "&ip=" + radiusOnlineInfo[2] + "&mac=" + radiusOnlineInfo[3] + "&t=" + costTime + "&out=" + out + "&in=" + in + "&oct=" + octets;
                  HttpRequest.sendPost(radiusUrl, params);
                }
              }
              catch (Exception localException2) {
              }
            }
          }
        }
      }
      catch (Exception e) {
        log.error("==============ERROR Start=============");
        log.error(e);
        log.error("ERROR INFO ", e);
        log.error("==============ERROR End=============");
      }
      RadiusOnlineMap.getInstance().getRadiusOnlineMap()
        .remove(AcctSessionId);
    }
  }

  public static void doLinkAll(String[] loginInfo, String info) {
    try {
      if (loginInfo != null) {
        String time = loginInfo[9];
        Date loginTime = new Date();
        String nowString = ThreadLocalDateUtil.format(new Date());
        Date nowTime = new Date();
        try {
          loginTime = ThreadLocalDateUtil.parse(time);
          nowTime = ThreadLocalDateUtil.parse(nowString);
        } catch (Exception localException1) {
        }
        Long costTime = Long.valueOf(nowTime.getTime() - loginTime.getTime());
        long in = 0L;
        long out = 0L;
        try {
          in = Long.valueOf(loginInfo[11]).longValue();
          out = Long.valueOf(loginInfo[12]).longValue();
        } catch (Exception localException2) {
        }
        long octets = in + out;
        Radiuslinkrecordall linkAll = new Radiuslinkrecordall();
        linkAll.setTime(costTime);
        linkAll.setStartDate(loginTime);
        linkAll.setEndDate(nowTime);
        linkAll.setName(loginInfo[4]);
        linkAll.setState(loginInfo[15]);
        linkAll.setAcctsessionid(loginInfo[13]);
        linkAll.setCallingstationid(loginInfo[3]);
        linkAll.setIns(Long.valueOf(in));
        linkAll.setOuts(Long.valueOf(out));
        linkAll.setOctets(Long.valueOf(octets));
        linkAll.setNasip(loginInfo[0]);
        linkAll.setSourceip(loginInfo[1]);
        linkAll.setUserip(loginInfo[2]);
        linkAll.setEx1(loginInfo[8]);
        linkAll.setEx2(info);
        linkAll.setEx3(loginInfo[16]);
        radiuslinkallService.addRadiuslinkrecordall(linkAll);
      }
    } catch (Exception e) {
      log.error("==============ERROR Start=============");
      log.error(e);
      log.error("ERROR INFO ", e);
      log.error("==============ERROR End=============");
    }
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.radius.core.utils.DoRecord
 * JD-Core Version:    0.6.2
 */