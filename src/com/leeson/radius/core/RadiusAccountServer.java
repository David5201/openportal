package com.leeson.radius.core;

import com.leeson.common.utils.ThreadLocalDateUtil;
import com.leeson.common.utils.stringUtils;
import com.leeson.core.bean.Config;
import com.leeson.core.bean.Portalaccount;
import com.leeson.core.query.PortalaccountQuery;
import com.leeson.core.service.ConfigService;
import com.leeson.core.service.PortalaccountService;
import com.leeson.portal.core.model.OnlineMap;
import com.leeson.portal.core.service.utils.PortalUtil;
import com.leeson.portal.core.utils.SpringContextHelper;
import com.leeson.radius.core.model.RadiusNasMap;
import com.leeson.radius.core.model.RadiusOnlineMap;
import com.leeson.radius.core.utils.COAThread;
import com.leeson.radius.core.utils.DoRecord;
import java.io.PrintStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RadiusAccountServer
  implements Runnable
{
  private int listenPort = 1813;

  private boolean isRun = false;

  private DatagramSocket server = null;
  private PortalaccountService portalaccountService;
  private ConfigService configService;

  public RadiusAccountServer()
  {
    this.portalaccountService = ((PortalaccountService)
      SpringContextHelper.getBean("portalaccountServiceImpl"));
    this.configService = ((ConfigService)
      SpringContextHelper.getBean("configServiceImpl"));
  }

  public void finalize() {
    stop();
  }

  public void run()
  {
    try {
      if (readConfig())
      {
        if (startRadius()) {
          this.isRun = true;
          while (this.isRun)
            try {
              byte[] buf = new byte[4096];
              DatagramPacket in = new DatagramPacket(buf, 4096);
              this.server.receive(in);
              new ReceiveAccountThread(this, in);
            } catch (Exception e1) {
              Tool.writeErrorLog("Accounting Error", e1);
            }
        }
      }
    }
    catch (Exception e) {
      Tool.writeErrorLog("Accounting Error", e);
    }
  }

  private boolean readConfig()
  {
    boolean ret = true;
    return ret;
  }

  public void stop()
  {
    this.isRun = false;
    try {
      if (this.server != null) {
        this.server.close();
        this.server = null;
      }
    } catch (Exception e) {
      Tool.writeErrorLog("Accounting Error", e);
    }
  }

  private boolean startRadius()
  {
    boolean ret = false;
    try {
      Integer port = this.configService.getConfigByKey(Long.valueOf(1L)).getAcctPort();
      if (port != null)
        this.listenPort = port.intValue();
      else {
        this.listenPort = 1813;
      }
      if (this.listenPort > 0) {
        this.server = new DatagramSocket(this.listenPort);
        ret = true;
      }
    } catch (Exception e) {
      Tool.writeErrorLog("Accounting Error", e);
    }
    Tool.writeLog("Radius Accounting Service Start", ret ? " Success" : 
      " fail");
    return ret;
  }

  public void receive(DatagramPacket in)
  {
    try {
      String ip = in.getAddress().getHostAddress();
      int port = in.getPort();

      byte[] inData = in.getData();

      int code = inData[0];

      byte[] id = new byte[1];
      id[0] = inData[1];
      int identifier = Tool.ByteToInt(id);

      int length = in.getLength();

      String authenticator = Tool.ByteToHex(inData, 4, 20);

      String attributes = Tool.ByteToHex(inData, 20, length);
      Tool.writeLog("Accounting Receive ", "ip=" + ip + ",port=" + port + 
        ",code=" + code + ",identifier=" + identifier + 
        ",length=" + length + ",authenticator=" + authenticator + 
        ",attributes=" + attributes);
      String[][] attributesList = null;
      if ((attributes != null) && (attributes.length() > 0)) {
        attributesList = Tool.getAttributes(attributes);
      }
      byte[] outData = optionData(code, ip, port, identifier, 
        authenticator, attributesList);
      if (outData != null)
      {
        int lengthTo = outData.length;

        String attributesTo = Tool.ByteToHex(outData, 20, lengthTo);

        String[][] attributesListTo = null;
        if ((attributesTo != null) && (attributesTo.length() > 0)) {
          attributesListTo = Tool.getAttributes(attributesTo);
        }
        if ((attributesListTo != null) && (attributesListTo.length > 0)) {
          for (int i = 0; i < attributesListTo.length; i++) {
            try {
              int type = Integer.parseInt(attributesListTo[i][0], 
                16);
              Tool.getAttributeValue(ip, type, 
                attributesListTo[i][1]).trim();
            } catch (Exception e) {
              Tool.writeErrorLog("Accounting Error", e);
            }
          }

        }

        DatagramPacket out = new DatagramPacket(outData, 
          outData.length, in.getSocketAddress());
        this.server.send(out);
        Tool.writeLog(ip, "Accounting Send OK !!");
      }
    } catch (Exception e) {
      Tool.writeErrorLog("Accounting Error", e);
    }
  }

  private byte[] optionData(int code, String ip, int port, int identifier, String authenticator, String[][] attributesList)
  {
    byte[] ret = null;
    try {
      switch (code)
      {
      case 4:
        Tool.writeLog(ip, "Accounting>>Accounting-Request");
        ret = accountingRequest(ip, port, identifier, authenticator, 
          attributesList);

        break;
      default:
        Tool.writeLog(ip, "Accounting code ERROR (" + code + ")");
      }
    }
    catch (Exception e)
    {
      Tool.writeErrorLog("Accounting Error", e);
    }
    return ret;
  }

  private byte[] accountingRequest(String ip, int port, int identifier, String authenticator, String[][] attributesList)
  {
    byte[] ret = null;
    try {
      String inS = "0";
      String outS = "0";
      String name = null;
      String userIP = null;
      String nasIP = null;
      String AcctSessionId = null;
      String CallingStationId = null;
      String AcctSessionTime = null;
      String AcctType = null;
      String NasIdentifier = "";
      for (int i = 0; i < attributesList.length; i++) {
        try {
          int type = Integer.parseInt(attributesList[i][0], 16);
          String value = Tool.getAttributeValue(ip, type, 
            attributesList[i][1]).trim();
          switch (type)
          {
          case 1:
            name = value;

            break;
          case 4:
            nasIP = value;

            break;
          case 5:
            break;
          case 8:
            userIP = value;

            break;
          case 31:
            CallingStationId = value;

            break;
          case 32:
            NasIdentifier = value;

            break;
          case 40:
            AcctType = value;

            break;
          case 42:
            inS = value;

            break;
          case 43:
            outS = value;

            break;
          case 44:
            AcctSessionId = value;

            break;
          case 46:
            AcctSessionTime = value;
          }

        }
        catch (Exception e1)
        {
          Tool.writeErrorLog("Accounting Error", e1);
        }
      }

      if (stringUtils.isNotBlank(CallingStationId)) {
        CallingStationId = PortalUtil.MacFormat(CallingStationId);
      }
      try
      {
        if (userIP != null) {
          String[] accountInfo = null;
          Iterator iterator = OnlineMap.getInstance()
            .getOnlineUserMap().keySet()
            .iterator();
          while (iterator.hasNext()) {
            Object o = iterator.next();
            String t = o.toString();
            if (t.contains(userIP + ":")) {
              accountInfo = 
                (String[])OnlineMap.getInstance()
                .getOnlineUserMap().get(t);
              accountInfo[7] = inS;
              accountInfo[8] = outS;
              OnlineMap.getInstance().getOnlineUserMap()
                .put(t, accountInfo);
            }
          }
        }
      }
      catch (Exception e) {
        Tool.writeErrorLog("Accounting Error", e);
      }

      String attributes = "";
      int code = 5;

      String client = "";
      String clientType = "";
      String coa = "3799";
      String nasname = NasIdentifier;
      String[] clients = null;
      if (stringUtils.isNotBlank(nasIP)) {
        clients = (String[])RadiusNasMap.getInstance().getNasMap().get(nasIP);
      }
      if ((clients != null) && (clients.length > 0)) {
        client = clients[0];
        clientType = clients[1];
        coa = clients[8];
        nasname = clients[7];
      }
      else if ((stringUtils.isNotBlank(ip)) && 
        (!ip.equals(nasIP))) {
        Iterator iterator = RadiusNasMap.getInstance().getNasMap().keySet().iterator();
        while (iterator.hasNext()) {
          Object o = iterator.next();
          String t = o.toString();
          if (ip.equals(DomainToIP(t))) {
            clients = (String[])RadiusNasMap.getInstance().getNasMap().get(t);
            break;
          }
        }
        if ((clients != null) && (clients.length > 0)) {
          client = clients[0];
          clientType = clients[1];
          coa = clients[8];
          nasname = clients[7];
        }

      }

      if ((stringUtils.isBlank(client)) && 
        (NasIdentifier != null) && (NasIdentifier != "")) {
        Iterator iterator = RadiusNasMap.getInstance().getNasMap().keySet()
          .iterator();
        while (iterator.hasNext()) {
          Object o = iterator.next();
          String t = o.toString();
          String[] temp = (String[])RadiusNasMap.getInstance().getNasMap().get(t);
          if ((temp.length > 0) && (NasIdentifier.equals(temp[7]))) {
            clients = temp;
            break;
          }
        }
        if ((clients != null) && (clients.length > 0)) {
          client = clients[0];
          clientType = clients[1];
          coa = clients[8];
          nasname = clients[7];
        }
      }

      if (stringUtils.isBlank(client)) {
        client = Tool.ByteToHex("OpenPortal".getBytes());
      }
      if (stringUtils.isBlank(clientType)) {
        clientType = "0";
      }

      if (client != null) {
        String sharedSecret = client;
        if (sharedSecret != null) {
          Tool.writeLog(ip, "Accounting-Request Print Finish !!");
          ret = Tool.getOutData("accountingResponse", sharedSecret, 
            ip, port, code, identifier, authenticator, 
            attributes);

          Date nowDate = new Date();
          String nowS = ThreadLocalDateUtil.format(nowDate);

          if ((stringUtils.isNotBlank(AcctSessionId)) && 
            (stringUtils.isNotBlank(AcctType))) {
            PortalaccountQuery aq = new PortalaccountQuery();
            aq.setLoginName(name);
            aq.setLoginNameLike(false);
            List users = this.portalaccountService
              .getPortalaccountList(aq);
            String userState = "0";
            Date userDate = new Date();
            long userTime = 0L;
            long now = userDate.getTime();
            int sessionTime = 0;
            long octets = 0L;
            if ((users != null) && (users.size() > 0)) {
              Portalaccount user = (Portalaccount)users.get(0);
              userState = user.getState();
              userDate = user.getDate();
              userTime = user.getTime().longValue();
              if ("0".equals(userState)) {
                sessionTime = 0;
              }

              if ("1".equals(userState)) {
                sessionTime = (int)(userTime / 1000L);
                if (sessionTime <= 0) {
                  sessionTime = 86400;
                }
              }
              if ("3".equals(userState)) {
                sessionTime = (int)((userDate.getTime() - now) / 1000L);
                if (sessionTime <= 0) {
                  user.setState("2");
                  this.portalaccountService
                    .updatePortalaccountByKey(user);
                  userState = "2";
                }
              }
              if ("2".equals(userState)) {
                sessionTime = (int)(userTime / 1000L);
                if (sessionTime <= 0) {
                  user.setState("4");
                  this.portalaccountService
                    .updatePortalaccountByKey(user);
                  userState = "4";
                }
              }
              if ("4".equals(userState)) {
                sessionTime = 86400;
                octets = user.getOctets().longValue();
                if (octets <= 0L) {
                  user.setState("0");
                  this.portalaccountService
                    .updatePortalaccountByKey(user);
                  sessionTime = 0;
                  octets = 0L;
                }

              }

              if ("Start(1)".equals(AcctType)) {
                if (stringUtils.isBlank(nasIP)) {
                  nasIP = ip;
                }
                String[] radiusOnlineInfo = new String[18];
                radiusOnlineInfo[0] = nasIP;
                radiusOnlineInfo[1] = ip;
                radiusOnlineInfo[2] = userIP;
                radiusOnlineInfo[3] = CallingStationId;
                radiusOnlineInfo[4] = name;
                radiusOnlineInfo[5] = sharedSecret;
                radiusOnlineInfo[6] = 
                  String.valueOf(sessionTime);
                radiusOnlineInfo[7] = String.valueOf(octets);
                radiusOnlineInfo[8] = clientType;
                radiusOnlineInfo[9] = nowS;
                radiusOnlineInfo[10] = "0";
                radiusOnlineInfo[11] = "0";
                radiusOnlineInfo[12] = "0";
                radiusOnlineInfo[13] = AcctSessionId;
                radiusOnlineInfo[14] = nowS;
                radiusOnlineInfo[15] = userState;
                radiusOnlineInfo[16] = nasname;
                radiusOnlineInfo[17] = coa;
                RadiusOnlineMap.getInstance()
                  .getRadiusOnlineMap()
                  .put(AcctSessionId, radiusOnlineInfo);
              }

              if (("Interim-Update(3)".equals(AcctType)) || ("Stop(2)".equals(AcctType))) {
                String[] radiusOnlineInfo = 
                  (String[])RadiusOnlineMap.getInstance().getRadiusOnlineMap()
                  .get(AcctSessionId);
                if ((radiusOnlineInfo != null) && 
                  (radiusOnlineInfo.length > 0)) {
                  if (stringUtils.isNotBlank(AcctSessionTime)) {
                    radiusOnlineInfo[10] = AcctSessionTime;
                  }
                  if (stringUtils.isNotBlank(inS)) {
                    radiusOnlineInfo[11] = inS;
                  }
                  if (stringUtils.isNotBlank(outS)) {
                    radiusOnlineInfo[12] = outS;
                  }
                  radiusOnlineInfo[14] = nowS;
                  radiusOnlineInfo[1] = ip;
                  radiusOnlineInfo[16] = nasname;
                  radiusOnlineInfo[17] = clients[8];

                  RadiusOnlineMap.getInstance()
                    .getRadiusOnlineMap()
                    .put(AcctSessionId, 
                    radiusOnlineInfo);

                  if ("Interim-Update(3)".equals(AcctType))
                  {
                    if ("4".equals(userState)) {
                      try {
                        long in = Long.valueOf(inS).longValue();
                        long out = Long.valueOf(outS).longValue();
                        long all = in + out;
                        if (all > octets)
                          COAThread.COA_Account_Cost(radiusOnlineInfo, "Radius Octets Over COA");
                      }
                      catch (Exception localException1)
                      {
                      }
                    }
                    if ("2".equals(userState)) {
                      try {
                        long costTime = Long.valueOf(AcctSessionTime).longValue() * 1000L;
                        if (userTime <= costTime)
                          COAThread.COA_Account_Cost(radiusOnlineInfo, "Radius Time Over COA");
                      }
                      catch (Exception localException2)
                      {
                      }
                    }
                    if ("3".equals(userState)) {
                      try {
                        if (userDate.getTime() - now <= 0L) {
                          COAThread.COA_Account_Cost(radiusOnlineInfo, "Radius Date Over COA");
                        }
                      }
                      catch (Exception localException3)
                      {
                      }
                    }
                  }
                }

              }

              if ("Stop(2)".equals(AcctType))
              {
                DoRecord.coreMethod(AcctSessionId, 
                  "Radius Account Stop");
              }
            }
          }
        }
      }
    }
    catch (Exception e) {
      Tool.writeErrorLog("Accounting Error", e);
    }
    return ret;
  }

  private String DomainToIP(String domain) {
    if (this.configService.getConfigByKey(Long.valueOf(1L)).getUseDomain().intValue() == 0) {
      return domain;
    }
    String ip = "";
    try {
      ip = InetAddress.getByName(domain).toString().split("/")[1];
    } catch (UnknownHostException e) {
      Tool.writeErrorLog("Radius DomainToIP ERROR INFO ", e);
    }
    System.out.println("Domain:" + domain + " IP:" + ip);
    return ip;
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.radius.core.RadiusAccountServer
 * JD-Core Version:    0.6.2
 */