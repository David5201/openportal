package com.leeson.portal.core.service;

import com.leeson.common.utils.ThreadLocalDateUtil;
import com.leeson.common.utils.stringUtils;
import com.leeson.core.bean.Portalaccount;
import com.leeson.core.bean.Portalbas;
import com.leeson.core.bean.Portallinkrecord;
import com.leeson.core.bean.Portallogrecord;
import com.leeson.core.controller.AjaxInterFaceController;
import com.leeson.core.query.PortalbasQuery;
import com.leeson.core.service.ConfigService;
import com.leeson.core.service.PortalaccountService;
import com.leeson.core.service.PortalbasService;
import com.leeson.core.service.PortallinkrecordService;
import com.leeson.core.service.PortallogrecordService;
import com.leeson.core.utils.CheckAutoLoginUtils;
import com.leeson.core.utils.CheckTimeUtils;
import com.leeson.core.utils.Kick;
import com.leeson.portal.core.model.OnlineMap;
import com.leeson.portal.core.model.iKuaiMacIpMap;
import com.leeson.portal.core.service.utils.ACKAuthenticator;
import com.leeson.portal.core.service.utils.PortalUtil;
import com.leeson.portal.core.utils.SpringContextHelper;
import java.io.PrintStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

public class ReportServer extends Thread
{
  private static com.leeson.portal.core.model.Config config = com.leeson.portal.core.model.Config.getInstance();
  private static OnlineMap onlineMap = OnlineMap.getInstance();
  private static Logger log = Logger.getLogger(ReportServer.class);
  private static ApplicationContext ac = SpringContextHelper.getApplicationContext();
  private static PortalbasService basService = (PortalbasService)ac.getBean("portalbasServiceImpl");
  private static ConfigService configService = (ConfigService)
    SpringContextHelper.getBean("configServiceImpl");

  private static Boolean isRun = Boolean.valueOf(true);
  private static DatagramSocket socket = null;

  DatagramPacket data = null;

  public ReportServer(DatagramPacket data)
  {
    this.data = data;
  }

  public void run() {
    Portalbas basConfig = (Portalbas)config.getConfigMap().get("");

    byte[] Req_Data_Base = new byte[this.data.getLength()];
    for (int l = 0; l < Req_Data_Base.length; l++) {
      Req_Data_Base[l] = this.data.getData()[l];
    }

    String ip = this.data.getAddress().getHostAddress();
    int port = this.data.getPort();

    String domain = ip;
    try {
      Iterator iteratorConfig = config.getConfigMap().keySet().iterator();
      while (iteratorConfig.hasNext()) {
        Object o = iteratorConfig.next();
        String t = o.toString();
        String basip = ((Portalbas)config.getConfigMap().get(t)).getBasIp();
        String basipT = DomainToIP(basip);
        if (ip.equals(basipT)) {
          domain = basip;
          break;
        }
      }
    }
    catch (Exception localException)
    {
    }
    if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
      log.info("Receive BAS Domain:" + domain + " IP:" + ip + ":" + port + ":" + "Packet Size:" + this.data.getLength() + " Packet Text: " + 
        PortalUtil.Getbyte2HexString(Req_Data_Base));
    }

    byte[] Ver = new byte[1];
    byte[] Type = new byte[1];
    byte[] Mod = new byte[1];
    byte[] Rsvd = new byte[1];
    byte[] SerialNo = new byte[2];
    byte[] ReqID = new byte[2];
    byte[] UserIP = new byte[4];
    byte[] UserPort = new byte[2];
    byte[] ErrCode = new byte[1];
    byte[] AttrNum = new byte[1];

    Ver[0] = Req_Data_Base[0];
    Type[0] = Req_Data_Base[1];
    Mod[0] = Req_Data_Base[2];
    Rsvd[0] = Req_Data_Base[3];
    SerialNo[0] = Req_Data_Base[4];
    SerialNo[1] = Req_Data_Base[5];
    ReqID[0] = Req_Data_Base[6];
    ReqID[1] = Req_Data_Base[7];
    UserIP[0] = Req_Data_Base[8];
    UserIP[1] = Req_Data_Base[9];
    UserIP[2] = Req_Data_Base[10];
    UserIP[3] = Req_Data_Base[11];
    UserPort[0] = Req_Data_Base[12];
    UserPort[1] = Req_Data_Base[13];
    ErrCode[0] = Req_Data_Base[14];
    AttrNum[0] = Req_Data_Base[15];

    if ((Type[0] & 0xFF) == 8)
    {
      String sharedSecret = basConfig.getSharedSecret();
      int basPort = Integer.parseInt(basConfig.getBasPort());

      if (stringUtils.isNotBlank(ip)) {
        PortalbasQuery bq = new PortalbasQuery();
        bq.setBasIp(domain);
        bq.setBasIpLike(false);
        List basList = basService.getPortalbasList(bq);
        if (basList.size() > 0) {
          basConfig = (Portalbas)basList.get(0);
          basPort = Integer.parseInt(basConfig.getBasPort());
          sharedSecret = basConfig.getSharedSecret();
        }
      }

      byte[] Ack_Data_Quit = null;
      if ((Ver[0] & 0xFF) == 1) {
        Ack_Data_Quit = new byte[16];
      }
      if ((Ver[0] & 0xFF) == 2) {
        Ack_Data_Quit = new byte[32];
      }
      for (int i = 0; i < 16; i++) {
        Ack_Data_Quit[i] = Req_Data_Base[i];
      }
      short typet = 14;
      Ack_Data_Quit[1] = ((byte)typet);
      Ack_Data_Quit[15] = 0;

      if ((Ver[0] & 0xFF) == 2) {
        byte[] Attrs = new byte[0];
        byte[] BBuff = new byte[16];
        byte[] reqAuthen = new byte[16];
        for (int i = 0; i < 16; i++) {
          BBuff[i] = Ack_Data_Quit[i];
        }
        if (Req_Data_Base.length >= 32) {
          for (int i = 0; i < 16; i++) {
            reqAuthen[i] = Req_Data_Base[(16 + i)];
          }
          if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
            log.info("Get reqAuthen " + PortalUtil.Getbyte2HexString(reqAuthen));
          }
        }
        byte[] Authen = ACKAuthenticator.MK_Authen(BBuff, Attrs, sharedSecret, reqAuthen);
        for (int i = 0; i < 16; i++) {
          Ack_Data_Quit[(16 + i)] = Authen[i];
        }
      }

      if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
        log.info("Read Send NTF_LogOut Resp Packet to: " + ip + ":" + basPort + ":" + 
          PortalUtil.Getbyte2HexString(Ack_Data_Quit));
      }

      try
      {
        DatagramPacket data = new DatagramPacket(Ack_Data_Quit, 
          Ack_Data_Quit.length, InetAddress.getByName(ip), basPort);
        socket.send(data);
      }
      catch (Exception localException1)
      {
      }

      if ((basConfig.getBas()
        .equals("2")) || 
        (basConfig.getBas()
        .equals("4")))
      {
        ikuaiOffline(Req_Data_Base, domain);
      }
      else
      {
        String userIP = bytesToIp(UserIP);

        if (onlineMap.getOnlineUserMap().containsKey(userIP + ":" + domain)) {
          String[] loginInfo = (String[])onlineMap.getOnlineUserMap().get(
            userIP + ":" + domain);
          Kick.doLinkAll(loginInfo, "NTF报文");
          String username = loginInfo[0];
          String mac = loginInfo[4];

          if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
            log.info("NTF_LogOut Request Want Logout User: " + userIP + ":" + domain + " mac: " + mac);
          }

          CheckTimeUtils.recordTime(loginInfo);
          CheckAutoLoginUtils.recordTime(loginInfo);
          try
          {
            doLinkRecord(loginInfo, "NTF报文");
            String time = loginInfo[3];
            Date loginTime = ThreadLocalDateUtil.parse(time);
            String nowString = ThreadLocalDateUtil.format(new Date());
            Date nowTime = ThreadLocalDateUtil.parse(nowString);
            Long costTime = Long.valueOf(nowTime.getTime() - loginTime.getTime());
            costTime = Long.valueOf(costTime.longValue() / 1000L / 60L);
            doLogRecord("IP: " + userIP + ":" + domain + " mac: " + mac + " 用户: " + username + " 上线时间: " + time + " 在线时长: " + costTime + 
              "分钟,NTF报文！");
          }
          catch (Exception localException2)
          {
          }
          AjaxInterFaceController.SangforLogout(userIP, username);

          onlineMap.getOnlineUserMap().remove(userIP + ":" + domain);

          if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1"))
            log.info("IP: " + userIP + ":" + domain + " mac: " + mac + " username: " + username + ", Logout By Ntf_Logout !!");
        }
      }
    }
  }

  private void ikuaiOffline(byte[] data, String ip)
  {
    String mac = getMac(data);
    String userIP = "";

    if (stringUtils.isNotBlank(mac)) {
      userIP = (String)iKuaiMacIpMap.getInstance().getMacIpMap().get(mac);
    }
    if (stringUtils.isNotBlank(userIP))
    {
      if (onlineMap.getOnlineUserMap().containsKey(userIP + ":" + ip)) {
        String[] loginInfo = (String[])onlineMap.getOnlineUserMap().get(
          userIP + ":" + ip);
        Kick.doLinkAll(loginInfo, "NTF报文");
        String username = loginInfo[0];

        if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
          log.info("IKuai NTF_LogOut Request Want Logout User: " + userIP + ":" + ip + " mac: " + mac);
        }

        CheckTimeUtils.recordTime(loginInfo);
        CheckAutoLoginUtils.recordTime(loginInfo);
        try
        {
          doLinkRecord(loginInfo, "NTF报文");
          String time = loginInfo[3];
          Date loginTime = ThreadLocalDateUtil.parse(time);
          String nowString = ThreadLocalDateUtil.format(new Date());
          Date nowTime = ThreadLocalDateUtil.parse(nowString);
          Long costTime = Long.valueOf(nowTime.getTime() - loginTime.getTime());
          costTime = Long.valueOf(costTime.longValue() / 1000L / 60L);
          doLogRecord("IP: " + userIP + ":" + ip + " mac: " + mac + " 用户: " + username + " 上线时间: " + time + " 在线时长: " + costTime + 
            "分钟,NTF报文！");
        }
        catch (Exception localException) {
        }
        AjaxInterFaceController.SangforLogout(userIP, username);

        onlineMap.getOnlineUserMap().remove(userIP + ":" + ip);

        if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1"))
          log.info("IP: " + userIP + ":" + ip + " mac: " + mac + " username: " + username + ", Logout By Ntf_Logout !!");
      }
    }
  }

  private static String getMac(byte[] data)
  {
    String mac = "";
    if ((data[15] & 0xFF) != 0) {
      int pos = 0;
      if ((data[0] & 0xFF) == 1) {
        pos = 16;
      }
      if ((data[0] & 0xFF) == 2) {
        pos = 32;
      }
      int AN = data[15] & 0xFF;

      int num = 1;
      while (num <= AN) {
        if (pos >= data.length)
        {
          break;
        }
        int type = data[pos] & 0xFF;
        pos++;

        if (pos >= data.length)
        {
          break;
        }
        int len = (data[pos] & 0xFF) - 2;
        pos++;

        byte[] buf = new byte[len];
        for (int l = 0; l < buf.length; l++) {
          if (pos + l >= data.length) {
            break;
          }
          buf[l] = data[(pos + l)];
        }
        pos += len;
        if (type == 11) {
          mac = new String(buf);
          break;
        }
        num++;
      }
    }

    return mac;
  }

  private static String bytesToIp(byte[] bytes)
  {
			return (bytes[0] & 0xFF) + '.' 
					+ (bytes[1] & 0xFF) + '.' 
						+ (bytes[2] & 0xFF) + '.'
							+ (bytes[3] & 0xFF)+"";
  }

  private void doLinkRecord(String[] loginInfo, String info)
  {
    try
    {
      String type = loginInfo[6];
      String ubip = loginInfo[10];
      Portalbas basconfig = (Portalbas)config.getConfigMap().get(ubip);
      if ((basconfig != null) && 
        ("1".equals(type)) && ("1".equals(basconfig.getIsPortalCheck()))) {
        String uid = loginInfo[1];
        String rid = loginInfo[2];
        if ((stringUtils.isBlank(uid)) || (stringUtils.isBlank(rid))) {
          return;
        }
        Long userId = Long.valueOf(Long.parseLong(loginInfo[1]));
        Long recordId = Long.valueOf(Long.parseLong(loginInfo[2]));
        if ((userId == null) || (recordId == null) || (userId.longValue() == 0L) || (recordId.longValue() == 0L)) {
          return;
        }
        PortallinkrecordService linkRecordService = (PortallinkrecordService)ac
          .getBean("portallinkrecordServiceImpl");
        PortalaccountService accountService = (PortalaccountService)ac
          .getBean("portalaccountServiceImpl");
        Portallinkrecord linkRecord = linkRecordService
          .getPortallinkrecordByKey(recordId);
        Portalaccount account = accountService.getPortalaccountByKey(userId);
        String state = account.getState();

        long in = Long.valueOf(loginInfo[7]).longValue();
        long out = Long.valueOf(loginInfo[8]).longValue();
        long octets = in + out;
        linkRecord.setIns(Long.valueOf(in));
        linkRecord.setOuts(Long.valueOf(out));
        linkRecord.setOctets(Long.valueOf(octets));
        Date now = new Date();
        linkRecord.setEndDate(now);
        Long costTime = Long.valueOf(now.getTime() - linkRecord.getStartDate().getTime());
        linkRecord.setTime(costTime);
        linkRecord.setState(state);
        linkRecord.setEx1("Portal");
        linkRecord.setEx2(info);
        linkRecordService.updatePortallinkrecordByKey(linkRecord);

        if (!state.equals("1")) {
          Long haveTime = account.getTime();
          Long newHaveTime = Long.valueOf(haveTime.longValue() - costTime.longValue());
          if ((state.equals("3")) && 
            (account.getDate().getTime() <= now.getTime())) {
            account.setState("2");
            accountService.updatePortalaccountByKey(account);
          }

          if (state.equals("2")) {
            if (newHaveTime.longValue() <= 0L) {
              account.setState("4");
            }
            account.setTime(newHaveTime);
            accountService.updatePortalaccountByKey(account);
          }
          if ((state.equals("4")) || (state.equals("0"))) {
            long haveOctets = account.getOctets().longValue() - octets;
            if (haveOctets <= 0L) {
              account.setState("0");
            }
            account.setOctets(Long.valueOf(haveOctets));
            accountService.updatePortalaccountByKey(account);
          }
        }
      }
    }
    catch (Exception localException)
    {
    }
  }

  private void doLogRecord(String info)
  {
    try
    {
      Portallogrecord logRecord = new Portallogrecord();
      Date nowDate = new Date();
      logRecord.setInfo(info);
      logRecord.setRecDate(nowDate);
      PortallogrecordService logRecordService = (PortallogrecordService)ac
        .getBean("portallogrecordServiceImpl");
      logRecordService.addPortallogrecord(logRecord);
    }
    catch (Exception localException)
    {
    }
  }

  private static String DomainToIP(String domain) {
    if (configService.getConfigByKey(Long.valueOf(1L)).getUseDomain().intValue() == 0) {
      return domain;
    }
    String ip = "";
    try {
      ip = InetAddress.getByName(domain).toString().split("/")[1];
    } catch (UnknownHostException e) {
      log.error("DomainToIP ERROR!!");
      log.error("==============ERROR Start=============");
      log.error(e);
      log.error("ERROR INFO ", e);
      log.error("==============ERROR End=============");
    }
    System.out.println("Domain:" + domain + " IP:" + ip);
    return ip;
  }

  public static void openServer() {
    try {
      socket = new DatagramSocket(configService.getConfigByKey(Long.valueOf(1L)).getPortalPort().intValue());
      if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
        log.info("PortalServer Service Start Success , Listen Portal UDP Default 50100 !!");
      }

      while (isRun.booleanValue()) {
        byte[] b = new byte[100];
        DatagramPacket data = new DatagramPacket(b, b.length);
        socket.receive(data);
        new ReportServer(data).start();
      }
    } catch (Exception e) {
      if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
        log.error("==============ERROR Start=============");
        log.error(e);
        log.error("ERROR INFO ", e);
        log.error("==============ERROR End=============");
      }
    }
  }

  public static void closeServer()
  {
    try
    {
      isRun = Boolean.valueOf(false);
      if (socket != null) {
        socket.close();
        socket = null;
      }
    } catch (Exception e) {
      if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
        log.error("==============ERROR Start=============");
        log.error(e);
        log.error("ERROR INFO ", e);
        log.error("==============ERROR End=============");
      }
    }
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.service.ReportServer
 * JD-Core Version:    0.6.2
 */