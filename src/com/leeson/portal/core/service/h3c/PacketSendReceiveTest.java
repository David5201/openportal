package com.leeson.portal.core.service.h3c;

import com.leeson.core.bean.Portalbas;
import com.leeson.portal.core.model.Config;
import com.leeson.portal.core.service.utils.PortalUtil;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;
import org.apache.log4j.Logger;

public class PacketSendReceiveTest
{
  private static Logger log = Logger.getLogger(PacketSendReceiveTest.class);

  private static Config config = Config.getInstance();

  public static byte[] reqInfo(short SerialNo, String UserIP)
  {
    Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
    String basIp = basConfig.getBasIp();
    String bas = basConfig.getBas();
    int basPort = Integer.parseInt(basConfig.getBasPort());
    String sharedSecret = basConfig.getSharedSecret();
    String authType = basConfig.getAuthType();
    int timeoutSec = Integer.parseInt(basConfig.getTimeoutSec());
    int portalVer = Integer.parseInt(basConfig.getPortalVer());

    PacketBuilder localPacketBuilder = new PacketBuilder();
    localPacketBuilder.setHead((byte)80, (byte)portalVer);
    localPacketBuilder.setHead((byte)81, (byte)9);
    localPacketBuilder.setHead((byte)87, CommonFunctions.ipv4ToBytes(UserIP));
    localPacketBuilder.setHead((byte)84, SerialNo);
    localPacketBuilder.setAttribute((byte)8, new byte[2]);
    localPacketBuilder.setShareSecret(sharedSecret.getBytes());
    try
    {
      localPacketBuilder.setDestineAddress(InetAddress.getByName(basIp));
      localPacketBuilder.setDestinePort(basPort);
    }
    catch (UnknownHostException localUnknownHostException)
    {
      localUnknownHostException.printStackTrace();
    }
    DatagramSocket dataSocket = null;
    try {
      dataSocket = new DatagramSocket();
      dataSocket.send(localPacketBuilder.composeWebPacket());
      byte[] ACK_Data_Temp = new byte[100];
      DatagramPacket receivePacket = new DatagramPacket(
        ACK_Data_Temp, 100);
      dataSocket.setSoTimeout(timeoutSec * 1000);
      dataSocket.receive(receivePacket);
      byte[] ACK_Data = new byte[receivePacket.getLength()];
      for (int i = 0; i < ACK_Data.length; i++) {
        ACK_Data[i] = receivePacket.getData()[i];
      }
      if (basConfig.getIsdebug().equals("1")) {
        log.info("ACK" + 
          PortalUtil.Getbyte2HexString(ACK_Data));
      }

      return ACK_Data;
    }
    catch (IOException e)
    {
      byte[] arrayOfByte1;
      if (basConfig.getIsdebug().equals("1")) {
        log.info("建立INFO会话，请求无响应!!!");
      }
      byte[] ErrorInfo = new byte[1];
      ErrorInfo[0] = 1;
      return ErrorInfo;
    } finally {
      dataSocket.close();
    }
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.service.h3c.PacketSendReceiveTest
 * JD-Core Version:    0.6.2
 */