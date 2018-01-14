package com.leeson.radius.core;

import com.leeson.common.utils.stringUtils;
import com.leeson.core.bean.Portalbas;
import com.leeson.portal.core.model.Config;
import com.leeson.portal.core.service.utils.PortalUtil;
import com.leeson.radius.core.utils.DoRecord;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Map;
import org.apache.log4j.Logger;

public class RadiusCOA
{
  private static Logger log = Logger.getLogger(RadiusCOA.class);
  private static Config config = Config.getInstance();

  public static boolean req_COA(String[] radiusOnlineInfo, String info) {
    DatagramSocket dataSocket = null;
    Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
    try {
      int timeoutSec = Integer.parseInt(basConfig.getTimeoutSec());

      if ((radiusOnlineInfo == null) || (radiusOnlineInfo.length <= 0)) {
        if (basConfig.getIsdebug().equals("1"))
          log.info("RadiusOnlineInfo Data is not Haveing !!");
        String acctSessionId;
        return false;
      }

      int port = 3799;
      String portString = radiusOnlineInfo[17];
      try {
        Integer portT = Integer.valueOf(portString);
        if (portT != null) {
          port = portT.intValue();
        }
      }
      catch (Exception localException1)
      {
      }
      String name = "Disconnect-Request";

      String ip = radiusOnlineInfo[1];
      String userIP = radiusOnlineInfo[2];
      String userName = radiusOnlineInfo[4];
      String sharedSecret = radiusOnlineInfo[5];
      String clientType = radiusOnlineInfo[8];
      String acctSessionId = radiusOnlineInfo[13];
      String nasip = radiusOnlineInfo[0];
      byte[] data = null;

      if (("5".equals(clientType)) || ("7".equals(clientType))) {
        data = new byte[364];
        data[0] = 0;
        data[1] = 1;
        data[2] = 0;
        data[3] = 1;

        byte[] sharedSecretByte = Tool.HexToByte(sharedSecret);
        byte[] sharedSecretByteInit = new byte[36];
        sharedSecretByteInit[0] = 0;
        sharedSecretByteInit[1] = 17;
        sharedSecretByteInit[2] = 0;
        sharedSecretByteInit[3] = ((byte)sharedSecretByte.length);
        for (int i = 0; i < sharedSecretByte.length; i++) {
          sharedSecretByteInit[(4 + i)] = sharedSecretByte[i];
        }
        for (int i = 0; i < sharedSecretByteInit.length; i++) {
          data[(4 + i)] = sharedSecretByteInit[i];
        }

        byte[] usernameByte = userName.getBytes();
        byte[] usernameByteInit = new byte[36];
        usernameByteInit[0] = 0;
        usernameByteInit[1] = 20;
        usernameByteInit[2] = 0;
        usernameByteInit[3] = ((byte)usernameByte.length);
        for (int i = 0; i < usernameByte.length; i++) {
          usernameByteInit[(4 + i)] = usernameByte[i];
        }
        for (int i = 0; i < usernameByteInit.length; i++)
          data[(40 + i)] = usernameByteInit[i];
      }
      else
      {
        int identifier = (int)(255.0D * Math.random());
        if (identifier < 1) {
          identifier = 1;
        }
        if (identifier > 255) {
          identifier = 255;
        }

        String authenticator = "00000000000000000000000000000000";

        String attributes = Tool.getAttributeString(44, acctSessionId);

        if (stringUtils.isNotBlank(userName)) {
          attributes = attributes + Tool.getAttributeString(1, userName);
        }

        if (stringUtils.isNotBlank(userIP)) {
          attributes = attributes + Tool.getAttributeIP(userIP);
        }

        if (stringUtils.isNotBlank(nasip)) {
          attributes = attributes + Tool.getAttributeNasIP(nasip);
        }
        data = Tool.getOutData(name, sharedSecret, ip, port, 
          40, identifier, authenticator, attributes);
      }

      if (basConfig.getIsdebug().equals("1")) {
        log.info("REQ CoA port " + port + " : " + PortalUtil.Getbyte2HexString(data));
      }

      dataSocket = new DatagramSocket();
      DatagramPacket requestPacket = new DatagramPacket(data, 
        data.length, InetAddress.getByName(ip), port);
      dataSocket.send(requestPacket);
      if (!"5".equals(clientType)) {
        byte[] ACK_Data = new byte[100];
        DatagramPacket receivePacket = new DatagramPacket(ACK_Data, 100);
        dataSocket.setSoTimeout(timeoutSec * 1000);
        dataSocket.receive(receivePacket);
        ACK_Data = new byte[receivePacket.getLength()];
        for (int i = 0; i < ACK_Data.length; i++) {
          ACK_Data[i] = receivePacket.getData()[i];
        }

        if (basConfig.getIsdebug().equals("1")) {
          log.info("ACK CoA: " + PortalUtil.Getbyte2HexString(ACK_Data));
        }

        if ((ACK_Data[0] & 0xFF) == 41) {
          if (basConfig.getIsdebug().equals("1"))
            log.info("CoA Offline Success !!");
          return true;
        }
        if (basConfig.getIsdebug().equals("1"))
          log.info("CoA Offline Fail !!");
        return false;
      }
      return true;
    }
    catch (IOException e)
    {
      if (basConfig.getIsdebug().equals("1"))
        Tool.writeErrorLog("CoA Error", e);
      String acctSessionId;
      return false;
    } finally {
      if (dataSocket != null) {
        dataSocket.close();
      }
      try
      {
        Thread.sleep(3000L);
      }
      catch (InterruptedException localInterruptedException5) {
      }
      try {
        if ((radiusOnlineInfo != null) && (radiusOnlineInfo.length > 0)) {
          String acctSessionId = radiusOnlineInfo[13];
          DoRecord.coreMethod(acctSessionId, info);
        }
      }
      catch (Exception localException6)
      {
      }
    }
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.radius.core.RadiusCOA
 * JD-Core Version:    0.6.2
 */