package com.leeson.portal.core.service.action.v2;

import com.leeson.core.bean.Portalbas;
import com.leeson.portal.core.model.Config;
import com.leeson.portal.core.service.utils.Authenticator;
import com.leeson.portal.core.service.utils.PortalUtil;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Map;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;

public class PublicV2
{
  private static Config config = Config.getInstance();
  private static Logger log = Logger.getLogger(PublicV2.class);

  public static boolean choose(int type, byte[] Req_Quit, int timeout_Sec, String Bas_IP, int bas_PORT, String sharedSecret)
  {
    if (type == 0) {
      return offline(Req_Quit, timeout_Sec, Bas_IP, bas_PORT, 
        sharedSecret);
    }
    return timeoutAffirm(Req_Quit, Bas_IP, bas_PORT, 
      sharedSecret);
  }

  public static boolean offline(byte[] Req_Quit, int timeout_Sec, String Bas_IP, int bas_PORT, String sharedSecret)
  {
    Portalbas basConfig = (Portalbas)config.getConfigMap().get("");

    DatagramSocket dataSocket = null;
    byte[] ACK_Data = new byte[100];
    byte[] BBuff = new byte[16];
    Req_Quit[15] = 1;
    for (int i = 0; i < 16; i++) {
      BBuff[i] = Req_Quit[i];
    }

    byte[] Attrs = new byte[6];
    byte[] BasIP = new byte[4];
    String[] basips = Bas_IP.split("[.]");

    for (int i = 0; i < 4; i++) {
      int m = NumberUtils.toInt(basips[i]);
      byte b = (byte)m;
      BasIP[i] = b;
    }
    Attrs[0] = 10;
    Attrs[1] = 6;
    Attrs[2] = BasIP[0];
    Attrs[3] = BasIP[1];
    Attrs[4] = BasIP[2];
    Attrs[5] = BasIP[3];

    byte[] Authen = Authenticator.MK_Authen(BBuff, Attrs, sharedSecret);

    for (int i = 0; i < 16; i++) {
      Req_Quit[(16 + i)] = Authen[i];
    }

    byte[] Req_Quit_F = new byte[Req_Quit.length + 6];
    for (int i = 0; i < Req_Quit.length; i++) {
      Req_Quit_F[i] = Req_Quit[i];
    }
    for (int i = 0; i < 6; i++) {
      Req_Quit_F[(32 + i)] = Attrs[i];
    }

    if (basConfig.getIsdebug().equals("1")) {
      log.info("REQ Quit" + PortalUtil.Getbyte2HexString(Req_Quit_F));
    }
    try
    {
      dataSocket = new DatagramSocket();
      DatagramPacket requestPacket = new DatagramPacket(Req_Quit_F, 38, 
        InetAddress.getByName(Bas_IP), bas_PORT);
      dataSocket.send(requestPacket);
      DatagramPacket receivePacket = new DatagramPacket(ACK_Data, 100);
      dataSocket.setSoTimeout(timeout_Sec * 1000);
      dataSocket.receive(receivePacket);
      ACK_Data = new byte[receivePacket.getLength()];
      for (int i = 0; i < ACK_Data.length; i++) {
        ACK_Data[i] = receivePacket.getData()[i];
      }
      if (basConfig.getIsdebug().equals("1"))
        log.info("ACK Quit" + PortalUtil.Getbyte2HexString(ACK_Data));
    }
    catch (IOException e)
    {
      if (basConfig.getIsdebug().equals("1")) {
        log.info("发送下线请求无响应!!!");
      }

      return false;
    } finally {
      dataSocket.close();
    }
    if ((ACK_Data[14] & 0xFF) == 1) {
      if (basConfig.getIsdebug().equals("1")) {
        log.info("发送下线请求被拒绝!!!");
      }

      return false;
    }if ((ACK_Data[14] & 0xFF) == 2) {
      if (basConfig.getIsdebug().equals("1")) {
        log.info("发送下线请求出现错误!!!");
      }

      return false;
    }
    if (basConfig.getIsdebug().equals("1")) {
      log.info("请求下线成功！！");
    }

    return true;
  }

  public static boolean timeoutAffirm(byte[] Req_Quit, String Bas_IP, int bas_PORT, String sharedSecret)
  {
    Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
    DatagramSocket dataSocket = null;
    byte[] BBuff = new byte[16];
    Req_Quit[14] = 1;
    Req_Quit[15] = 1;
    for (int i = 0; i < 16; i++) {
      BBuff[i] = Req_Quit[i];
    }

    byte[] Attrs = new byte[6];
    byte[] BasIP = new byte[4];
    String[] basips = Bas_IP.split("[.]");

    for (int i = 0; i < 4; i++) {
      int m = NumberUtils.toInt(basips[i]);
      byte b = (byte)m;
      BasIP[i] = b;
    }
    Attrs[0] = 10;
    Attrs[1] = 6;
    Attrs[2] = BasIP[0];
    Attrs[3] = BasIP[1];
    Attrs[4] = BasIP[2];
    Attrs[5] = BasIP[3];

    byte[] Authen = Authenticator.MK_Authen(BBuff, Attrs, 
      sharedSecret);
    for (int i = 0; i < 16; i++) {
      Req_Quit[(16 + i)] = Authen[i];
    }

    byte[] Req_Quit_F = new byte[Req_Quit.length + 6];
    for (int i = 0; i < Req_Quit.length; i++) {
      Req_Quit_F[i] = Req_Quit[i];
    }
    for (int i = 0; i < 6; i++) {
      Req_Quit_F[(32 + i)] = Attrs[i];
    }
    try
    {
      dataSocket = new DatagramSocket();
      DatagramPacket requestPacket = new DatagramPacket(Req_Quit_F, 38, 
        InetAddress.getByName(Bas_IP), bas_PORT);
      dataSocket.send(requestPacket);
      if (basConfig.getIsdebug().equals("1")) {
        log.info("发送超时回复报文成功:" + PortalUtil.Getbyte2HexString(Req_Quit));
      }

      return true;
    } catch (IOException e) {
      if (basConfig.getIsdebug().equals("1")) {
        log.info("请求超时回复报文发生未知错误!");
      }

      return false;
    } finally {
      dataSocket.close();
    }
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.service.action.v2.PublicV2
 * JD-Core Version:    0.6.2
 */