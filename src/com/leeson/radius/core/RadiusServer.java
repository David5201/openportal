package com.leeson.radius.core;

import com.leeson.common.utils.stringUtils;
import com.leeson.core.bean.Config;
import com.leeson.core.bean.Portalaccount;
import com.leeson.core.bean.Portalspeed;
import com.leeson.core.query.PortalaccountQuery;
import com.leeson.core.service.ConfigService;
import com.leeson.core.service.PortalaccountService;
import com.leeson.core.service.PortalspeedService;
import com.leeson.portal.core.model.isDo;
import com.leeson.portal.core.service.utils.ChapPassword;
import com.leeson.portal.core.service.utils.PortalUtil;
import com.leeson.portal.core.utils.SpringContextHelper;
import com.leeson.radius.core.model.RadiusNasMap;
import com.leeson.radius.core.model.RadiusOnlineMap;
import com.leeson.radius.core.utils.COAThread;
import com.leeson.radius.core.utils.MacLimit;
import com.leeson.radius.core.utils.OnlineLimit;
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

public class RadiusServer
  implements Runnable
{
  private int listenPort = 1812;

  private boolean isRun = false;

  private DatagramSocket server = null;
  private PortalaccountService portalaccountService;
  private PortalspeedService speedService;
  private ConfigService configService;

  public RadiusServer()
  {
    this.portalaccountService = ((PortalaccountService)
      SpringContextHelper.getBean("portalaccountServiceImpl"));
    this.speedService = ((PortalspeedService)
      SpringContextHelper.getBean("portalspeedServiceImpl"));
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
              new ReceiveThread(this, in);
            } catch (Exception e1) {
              Tool.writeErrorLog("Error", e1);
            }
        }
      }
    }
    catch (Exception e) {
      Tool.writeErrorLog("Error", e);
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
      Tool.writeErrorLog("Error", e);
    }
  }

  private boolean startRadius()
  {
    boolean ret = false;
    try {
      Integer port = this.configService.getConfigByKey(Long.valueOf(1L)).getAuthPort();
      if (port != null)
        this.listenPort = port.intValue();
      else {
        this.listenPort = 1812;
      }
      if (this.listenPort > 0) {
        this.server = new DatagramSocket(this.listenPort);
        ret = true;
      }
    } catch (Exception e) {
      Tool.writeErrorLog("error", e);
    }
    Tool.writeLog("Radius Service Start", ret ? " Success" : " fail");
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
      Tool.writeLog("Receive ", "ip=" + ip + ",port=" + port + ",code=" + 
        code + ",identifier=" + identifier + ",length=" + length + 
        ",authenticator=" + authenticator + ",attributes=" + 
        attributes);
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
              Tool.writeErrorLog("Error", e);
            }
          }

        }

        DatagramPacket out = new DatagramPacket(outData, 
          outData.length, in.getSocketAddress());
        this.server.send(out);
        Tool.writeLog(ip, "Send OK !!");
      }
    } catch (Exception e) {
      Tool.writeErrorLog("error", e);
    }
  }

  private byte[] optionData(int code, String ip, int port, int identifier, String authenticator, String[][] attributesList)
  {
    byte[] ret = null;
    try {
      switch (code)
      {
      case 1:
        Tool.writeLog(ip, ">>Access-Request");
        ret = accessRequest(ip, port, identifier, authenticator, 
          attributesList);

        break;
      case 11:
        Tool.writeLog(ip, ">>Access-Challenge");
        ret = accessChallenge(ip, port, identifier, authenticator, 
          attributesList);

        break;
      case 12:
        Tool.writeLog(ip, ">>Status-Server");
        ret = statusServer(ip, port, identifier, authenticator, 
          attributesList);

        break;
      case 13:
        Tool.writeLog(ip, ">>Status-client");
        ret = statusClient(ip, port, identifier, authenticator, 
          attributesList);

        break;
      case 255:
        Tool.writeLog(ip, ">>Reserved");
        ret = reserved(ip, port, identifier, authenticator, 
          attributesList);

        break;
      default:
        Tool.writeLog(ip, "code ERROR (" + code + ")");
      }
    }
    catch (Exception e)
    {
      Tool.writeErrorLog("Error", e);
    }
    return ret;
  }

  private byte[] accessRequest(String ip, int port, int identifier, String authenticator, String[][] attributesList)
  {
    byte[] ret = null;
    try {
      String name = null;
      String password = null;
      String userIP = null;
      String Challenge = null;
      String nasIP = null;
      String AcctSessionId = null;
      String CallingStationId = null;
      String NasIdentifier = "";
      int chap = 0;
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
          case 2:
            password = value;

            break;
          case 3:
            password = value;
            chap = 1;

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
          case 44:
            AcctSessionId = value;

            break;
          case 60:
            Challenge = value;
          }

        }
        catch (Exception e1)
        {
          Tool.writeErrorLog("Error", e1);
        }
      }
      if (stringUtils.isNotBlank(CallingStationId)) {
        CallingStationId = PortalUtil.MacFormat(CallingStationId);
      }
      if ((name != null) && (password != null) && (ip != null)) {
        String username = name;
        String client = "";
        String clientType = "";
        String acctInterimInterval = "";
        String idleTimeout = "";
        String autoKick = "";
        String[] clients = null;
        if (stringUtils.isNotBlank(nasIP)) {
          clients = (String[])RadiusNasMap.getInstance().getNasMap().get(nasIP);
        }
        if ((clients != null) && (clients.length > 0)) {
          client = clients[0];
          clientType = clients[1];
          acctInterimInterval = clients[3];
          idleTimeout = clients[5];
          autoKick = clients[6];
        }
        else if ((stringUtils.isNotBlank(ip)) && 
          (!ip.equals(nasIP))) {
          Iterator iterator = RadiusNasMap.getInstance().getNasMap().keySet()
            .iterator();
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
            acctInterimInterval = clients[3];
            idleTimeout = clients[5];
            autoKick = clients[6];
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
            acctInterimInterval = clients[3];
            idleTimeout = clients[5];
            autoKick = clients[6];
          }
        }

        if (stringUtils.isBlank(client)) {
          client = Tool.ByteToHex("OpenPortal".getBytes());
        }
        if (stringUtils.isBlank(clientType)) {
          clientType = "0";
        }
        if (stringUtils.isBlank(acctInterimInterval)) {
          acctInterimInterval = "300";
        }
        if (stringUtils.isBlank(idleTimeout)) {
          idleTimeout = "0";
        }
        if (stringUtils.isBlank(autoKick)) {
          autoKick = "1";
        }

        if (stringUtils.isNotBlank(client)) {
          String sharedSecret = client;
          if (stringUtils.isNotBlank(sharedSecret)) {
            if (chap == 0) {
              password = Tool.decodeMD5(sharedSecret, 
                authenticator, password);
            }
            Tool.writeLog("Access-Request", "ip=" + ip + ",port=" + 
              port + ",name=" + name + ",password=" + 
              password);
            String result = null;

            PortalaccountQuery aq = new PortalaccountQuery();
            aq.setLoginName(name);
            aq.setLoginNameLike(false);
            List users = this.portalaccountService
              .getPortalaccountList(aq);
            Long id = Long.valueOf(0L);
            String pwd = "";
            Long sid = Long.valueOf(0L);

            String userState = "0";
            Date userDate = new Date();
            long userTime = 0L;
            long now = userDate.getTime();
            int sessionTime = 0;
            if ((users != null) && (users.size() > 0)) {
              Portalaccount user = (Portalaccount)users.get(0);
              pwd = user.getPassword();
              id = user.getId();
              sid = user.getSpeed();
              userState = user.getState();
              userDate = user.getDate();
              userTime = user.getTime().longValue();
              if ("0".equals(userState)) {
                sessionTime = 0;
                result = "-1";
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
                long octets = user.getOctets().longValue();
                if (octets <= 0L) {
                  user.setState("0");
                  this.portalaccountService
                    .updatePortalaccountByKey(user);
                  sessionTime = 0;
                  octets = 0L;
                  result = "-1";
                }

              }

              if (!OnlineLimit.macLimit(username, 
                CallingStationId, user.getMaclimitcount().intValue())) {
                if ("1".equals(autoKick)) {
                  Iterator iterator = RadiusOnlineMap.getInstance()
                    .getRadiusOnlineMap().keySet().iterator();
                  while (iterator.hasNext()) {
                    Object o = iterator.next();
                    String t = o.toString();
                    String[] loginInfo = 
                      (String[])RadiusOnlineMap.getInstance()
                      .getRadiusOnlineMap().get(t);
                    String haveUsername = loginInfo[4];
                    if (username.equals(haveUsername)) {
                      COAThread.COA_Account_Cost(loginInfo, "Radius AutoKick COA");
                      break;
                    }
                  }
                } else {
                  sessionTime = 0;
                  result = "-1";
                }

              }

              if (!MacLimit.macLimit(id, username, 
                CallingStationId, user.getMaclimit().intValue(), 
                user.getMaclimitcount().intValue())) {
                sessionTime = 0;
                result = "-1";
              }
            }
            else
            {
              sessionTime = 0;
              result = "-1";
            }

            if (chap == 0) {
              Tool.writeLog(ip, "Database PWD=" + pwd + 
                ":::: Req PWD=" + password);
              if ((password.equals(pwd)) && 
                (result == null))
                result = "0";
            }
            else
            {
              byte[] pwdByte = pwd.getBytes();
              byte[] ChallengeByte = Tool.HexToByte(Challenge);
              byte[] chapidByte = new byte[2];
              chapidByte[1] = ((byte)Integer.parseInt(
                password.substring(0, 2), 16));

              String chap_password = Tool.ByteToHex(
                ChapPassword.MK_ChapPwd(chapidByte, ChallengeByte, 
                pwdByte));
              password = password.substring(2);

              Tool.writeLog(ip, "Database ChapPWD=" + 
                chap_password + ":::: Req ChapPWD=" + 
                password);

              if ((password.equals(chap_password)) && 
                (result == null)) {
                result = "0";
              }

            }

            String attributes = null;
            int code = 0;
            if (result != null) {
              if (result.equals("0"))
              {
                if (RadiusOnlineMap.getInstance()
                  .getRadiusOnlineMap().size() < 
                  Integer.valueOf(
                  ((String[])com.leeson.portal.core.model.CoreConfigMap.getInstance()
                  .getCoreConfigMap().get("core"))[1]).intValue())
                {
                  name = "Access-Accept";
                  attributes = "";
                  if ((userIP != null) && (!"".equals(userIP))) {
                    attributes = attributes + 
                      Tool.getAttributeIP(userIP);
                  }
                  if (stringUtils.isNotBlank(AcctSessionId)) {
                    attributes = attributes + 
                      Tool.getAttributeString(44, 
                      AcctSessionId);
                  }
                  attributes = attributes + 
                    Tool.getAttributeString(18, 
                    "success!");
                  attributes = attributes + 
                    Tool.getAttributeInt(27, 
                    sessionTime);

                  Integer idletime = null;
                  if (stringUtils.isNotBlank(idleTimeout))
                    try {
                      idletime = 
                        Integer.valueOf(idleTimeout);
                    }
                    catch (Exception localException1) {
                    }
                  Integer acctInterval = null;

                  if (stringUtils.isNotBlank(acctInterimInterval))
                    try {
                      acctInterval = 
                        Integer.valueOf(acctInterimInterval);
                    }
                    catch (Exception localException2)
                    {
                    }
                  if ((idletime != null) && (idletime.intValue() != 0)) {
                    attributes = attributes + 
                      Tool.getAttributeInt(28, 
                      idletime.intValue());
                  }
                  if ((acctInterval != null) && 
                    (acctInterval.intValue() != 0)) {
                    attributes = attributes + 
                      Tool.getAttributeInt(85, 
                      acctInterval.intValue());
                  }

                  if (Do()) {
                    try
                    {
                      if (stringUtils.isNotBlank(clientType)) {
                        Portalspeed speed = this.speedService
                          .getPortalspeedByKey(sid);
                        if (speed != null) {
                          if ("6".equals(clientType))
                          {
                            attributes = attributes + 
                              Tool.getAttributeVendor(
                              26, 
                              3902);
                            attributes = attributes + 
                              Tool.getAttributeSpeed(
                              83, 
                              (int)Math.floor(speed
                              .getDown().longValue()));

                            attributes = attributes + 
                              Tool.getAttributeVendor(
                              26, 
                              3902);
                            attributes = attributes + 
                              Tool.getAttributeSpeed(
                              89, 
                              (int)Math.floor(speed
                              .getUp().longValue()));
                          }

                          if ("4".equals(clientType))
                          {
                            int down = 
                              (int)Math.floor(speed
                              .getDown().longValue());
                            int up = 
                              (int)Math.floor(speed
                              .getUp().longValue());
                            String s = 
                              String.valueOf(up) + 
                              "k/" + 
                              String.valueOf(down) + 
                              "k";
                            int valueLen = 
                              Tool.getAttributeStringLen(s);
                            attributes = attributes + 
                              Tool.getAttributeVendor(
                              26, 
                              14988, 
                              valueLen);
                            attributes = attributes + 
                              Tool.getAttributeString(
                              8, s);
                          }

                          if ("5".equals(clientType))
                          {
                            attributes = attributes + 
                              Tool.getAttributeVendor(
                              26, 
                              10055);
                            attributes = attributes + 
                              Tool.getAttributeSpeed(
                              2, 
                              (int)Math.floor(speed
                              .getDown().longValue() * 0.125D));

                            attributes = attributes + 
                              Tool.getAttributeVendor(
                              26, 
                              10055);
                            attributes = attributes + 
                              Tool.getAttributeSpeed(
                              1, 
                              (int)Math.floor(speed
                              .getUp().longValue() * 0.125D));
                          }

                          if ("7".equals(clientType))
                          {
                            attributes = attributes + 
                              Tool.getAttributeVendor(
                              26, 
                              10055);
                            attributes = attributes + 
                              Tool.getAttributeSpeed(
                              2, 
                              (int)Math.floor(speed
                              .getDown().longValue() * 0.125D));

                            attributes = attributes + 
                              Tool.getAttributeVendor(
                              26, 
                              10055);
                            attributes = attributes + 
                              Tool.getAttributeSpeed(
                              1, 
                              (int)Math.floor(speed
                              .getUp().longValue() * 0.125D));
                          }

                          if ("1".equals(clientType))
                          {
                            attributes = attributes + 
                              Tool.getAttributeVendor(
                              26, 
                              2011);
                            attributes = attributes + 
                              Tool.getAttributeSpeed(
                              5, 
                              (int)Math.floor(speed
                              .getDown().longValue() * 1024L));

                            attributes = attributes + 
                              Tool.getAttributeVendor(
                              26, 
                              2011);
                            attributes = attributes + 
                              Tool.getAttributeSpeed(
                              6, 
                              (int)Math.floor(speed
                              .getMdown().longValue() * 1024L));

                            attributes = attributes + 
                              Tool.getAttributeVendor(
                              26, 
                              2011);
                            attributes = attributes + 
                              Tool.getAttributeSpeed(
                              2, 
                              (int)Math.floor(speed
                              .getUp().longValue() * 1024L));

                            attributes = attributes + 
                              Tool.getAttributeVendor(
                              26, 
                              2011);
                            attributes = attributes + 
                              Tool.getAttributeSpeed(
                              3, 
                              (int)Math.floor(speed
                              .getMup().longValue() * 1024L));
                          }

                          if ("2".equals(clientType))
                          {
                            attributes = attributes + 
                              Tool.getAttributeVendor(
                              26, 
                              25506);
                            attributes = attributes + 
                              Tool.getAttributeSpeed(
                              5, 
                              (int)Math.floor(speed
                              .getDown().longValue() * 1024L));

                            attributes = attributes + 
                              Tool.getAttributeVendor(
                              26, 
                              25506);
                            attributes = attributes + 
                              Tool.getAttributeSpeed(
                              4, 
                              (int)Math.floor(speed
                              .getMdown().longValue() * 1024L));

                            attributes = attributes + 
                              Tool.getAttributeVendor(
                              26, 
                              25506);
                            attributes = attributes + 
                              Tool.getAttributeSpeed(
                              2, 
                              (int)Math.floor(speed
                              .getUp().longValue() * 1024L));

                            attributes = attributes + 
                              Tool.getAttributeVendor(
                              26, 
                              25506);
                            attributes = attributes + 
                              Tool.getAttributeSpeed(
                              1, 
                              (int)Math.floor(speed
                              .getMup().longValue() * 1024L));
                          }
                        }
                      }
                    }
                    catch (Exception e) {
                      Tool.writeErrorLog("Error", e);
                    }

                  }

                  code = 2;
                  Tool.writeLog(ip, "Account Check OK !!");
                }
                else {
                  name = "Access-Reject";
                  attributes = Tool.getAttributeString(18, 
                    "System Max Online Limit!");
                  code = 3;
                  Tool.writeLog(ip, "Account is Out of Time !!");
                }
              } else {
                name = "Access-Reject";
                attributes = Tool.getAttributeString(18, 
                  "Account is Out of Time or Mac Limit!");
                code = 3;
                Tool.writeLog(ip, "Account is Out of Time !!");
              }
            } else {
              name = "Access-Reject";
              attributes = Tool.getAttributeString(18, 
                "Account Check Fail!");
              code = 3;
              Tool.writeLog(ip, "Account Check Fail !!");
            }
            Tool.writeLog(ip, "Access-Request Print Finish !!");
            ret = Tool.getOutData(name, sharedSecret, ip, port, 
              code, identifier, authenticator, attributes);
          } else {
            Tool.writeLog(ip, "ShareKey is NULL !!");
          }
        } else {
          Tool.writeLog(ip, "Get ShareKey Fail !!");
        }
      }
    } catch (Exception e) {
      Tool.writeErrorLog("error", e);
    }
    return ret;
  }

  private byte[] accessChallenge(String ip, int port, int identifier, String authenticator, String[][] attributesList)
  {
    byte[] ret = null;
    try {
      for (int i = 0; i < attributesList.length; i++)
        try {
          int type = Integer.parseInt(attributesList[i][0], 16);
          Tool.getAttributeValue(ip, type, attributesList[i][1])
            .trim();
        } catch (Exception e1) {
          Tool.writeErrorLog("Error", e1);
        }
    }
    catch (Exception e) {
      Tool.writeErrorLog("Error", e);
    }
    return ret;
  }

  private byte[] statusServer(String ip, int port, int identifier, String authenticator, String[][] attributesList)
  {
    byte[] ret = null;
    try {
      for (int i = 0; i < attributesList.length; i++)
        try {
          int type = Integer.parseInt(attributesList[i][0], 16);
          Tool.getAttributeValue(ip, type, attributesList[i][1])
            .trim();
        } catch (Exception e1) {
          Tool.writeErrorLog("Error", e1);
        }
    }
    catch (Exception e) {
      Tool.writeErrorLog("Error", e);
    }
    return ret;
  }

  private byte[] statusClient(String ip, int port, int identifier, String authenticator, String[][] attributesList)
  {
    byte[] ret = null;
    try {
      for (int i = 0; i < attributesList.length; i++)
        try {
          int type = Integer.parseInt(attributesList[i][0], 16);
          Tool.getAttributeValue(ip, type, attributesList[i][1])
            .trim();
        } catch (Exception e1) {
          Tool.writeErrorLog("Error", e1);
        }
    }
    catch (Exception e) {
      Tool.writeErrorLog("Error", e);
    }
    return ret;
  }

  private byte[] reserved(String ip, int port, int identifier, String authenticator, String[][] attributesList)
  {
    byte[] ret = null;
    try {
      for (int i = 0; i < attributesList.length; i++)
        try {
          int type = Integer.parseInt(attributesList[i][0], 16);
          Tool.getAttributeValue(ip, type, attributesList[i][1])
            .trim();
        } catch (Exception e1) {
          Tool.writeErrorLog("Error", e1);
        }
    }
    catch (Exception e) {
      Tool.writeErrorLog("Error", e);
    }
    return ret;
  }

  public static boolean Do() {
    Long isThis = Long.valueOf(new Date().getTime());
    boolean Do = false;
    if (isThis.longValue() < isDo.getInstance().getId().longValue()) {
      Do = true;
    }
    return Do;
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
 * Qualified Name:     com.leeson.radius.core.RadiusServer
 * JD-Core Version:    0.6.2
 */