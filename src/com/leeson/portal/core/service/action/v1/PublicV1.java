package com.leeson.portal.core.service.action.v1;

import com.leeson.core.bean.Portalbas;
import com.leeson.portal.core.model.Config;
import com.leeson.portal.core.service.utils.PortalUtil;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Map;
import org.apache.log4j.Logger;

public class PublicV1
{
  private static Config config = Config.getInstance();
  private static Logger log = Logger.getLogger(PublicV1.class);

  public static boolean choose(int type, byte[] Req_Quit, int timeout_Sec, String Bas_IP, int bas_PORT)
  {
    if (type == 0) {
      return offline(Req_Quit, timeout_Sec, Bas_IP, bas_PORT);
    }
    return timeoutAffirm(type, Req_Quit, Bas_IP, bas_PORT);
  }

  public static boolean offline(byte[] Req_Quit, int timeout_Sec, String Bas_IP, int bas_PORT)
  {
    Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
    if (basConfig.getIsdebug().equals("1")) {
      log.info("REQ Quit" + PortalUtil.Getbyte2HexString(Req_Quit));
    }

    DatagramSocket dataSocket = null;
    byte[] ACK_Data = new byte[16];
    try {
      dataSocket = new DatagramSocket();
      DatagramPacket requestPacket = new DatagramPacket(Req_Quit, 16, 
        InetAddress.getByName(Bas_IP), bas_PORT);
      dataSocket.send(requestPacket);
      DatagramPacket receivePacket = new DatagramPacket(ACK_Data, 16);
      dataSocket.setSoTimeout(timeout_Sec * 1000);
      dataSocket.receive(receivePacket);
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
      log.info("请求下线成功！！！");
    }

    return true;
  }

  public static boolean timeoutAffirm(int type, byte[] Req_Quit, String Bas_IP, int bas_PORT)
  {
    Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
    DatagramSocket dataSocket = null;
    Req_Quit[14] = 1;
    try {
      dataSocket = new DatagramSocket();
      DatagramPacket requestPacket = new DatagramPacket(Req_Quit, 16, 
        InetAddress.getByName(Bas_IP), bas_PORT);
      dataSocket.send(requestPacket);
      if (basConfig.getIsdebug().equals("1")) {
        log.info("发送超时回复报文成功: " + PortalUtil.Getbyte2HexString(Req_Quit));
      }

      return true;
    } catch (IOException e) {
      if (basConfig.getIsdebug().equals("1")) {
        log.info("发送超时回复报文出现未知错误！！！");
      }

      return false;
    } finally {
      dataSocket.close();
    }
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.service.action.v1.PublicV1
 * JD-Core Version:    0.6.2
 */