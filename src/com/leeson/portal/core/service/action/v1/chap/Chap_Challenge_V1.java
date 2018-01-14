package com.leeson.portal.core.service.action.v1.chap;

import com.leeson.core.bean.Portalbas;
import com.leeson.portal.core.model.Config;
import com.leeson.portal.core.service.utils.PortalUtil;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Map;
import org.apache.log4j.Logger;

public class Chap_Challenge_V1
{
  private static Config config = Config.getInstance();
  private static Logger log = Logger.getLogger(Chap_Challenge_V1.class);

  public static byte[] Action(String Bas_IP, int bas_PORT, int timeout_Sec, byte[] SerialNo, byte[] UserIP)
  {
    Portalbas basConfig = (Portalbas)config.getConfigMap().get("");

    DatagramSocket dataSocket = null;

    byte[] ErrorInfo = new byte[2];

    byte[] Req_Challenge = new byte[16];

    Req_Challenge[0] = 1;
    Req_Challenge[1] = 1;
    Req_Challenge[2] = 0;
    Req_Challenge[3] = 0;
    Req_Challenge[4] = SerialNo[0];
    Req_Challenge[5] = SerialNo[1];
    Req_Challenge[6] = 0;
    Req_Challenge[7] = 0;
    Req_Challenge[8] = UserIP[0];
    Req_Challenge[9] = UserIP[1];
    Req_Challenge[10] = UserIP[2];
    Req_Challenge[11] = UserIP[3];
    Req_Challenge[12] = 0;
    Req_Challenge[13] = 0;
    Req_Challenge[14] = 0;
    Req_Challenge[15] = 0;
    if (basConfig.getIsdebug().equals("1")) {
      log.info("REQ Challenge" + PortalUtil.Getbyte2HexString(Req_Challenge));
    }
    try
    {
      dataSocket = new DatagramSocket();
      DatagramPacket requestPacket = new DatagramPacket(Req_Challenge, 
        16, InetAddress.getByName(Bas_IP), bas_PORT);
      dataSocket.send(requestPacket);
      byte[] ACK_Challenge = new byte[34];
      DatagramPacket receivePacket = new DatagramPacket(ACK_Challenge, 34);
      dataSocket.setSoTimeout(timeout_Sec * 1000);
      dataSocket.receive(receivePacket);
      if (basConfig.getIsdebug().equals("1")) {
        log.info("ACK Challenge" + 
          PortalUtil.Getbyte2HexString(ACK_Challenge));
      }

      ErrorInfo[0] = ACK_Challenge[6];
      ErrorInfo[1] = ACK_Challenge[7];

      if ((ACK_Challenge[14] & 0xFF) == 0) {
        if (basConfig.getIsdebug().equals("1")) {
          log.info("请求Challenge成功,准备发送REQ Auth认证请求!!!");
        }

        return ACK_Challenge;
      }if ((ACK_Challenge[14] & 0xFF) == 1) {
        if (basConfig.getIsdebug().equals("1")) {
          log.info("发情Challenge请求被拒绝!!!");
        }

        return ErrorInfo;
      }if ((ACK_Challenge[14] & 0xFF) == 2) {
        if (basConfig.getIsdebug().equals("1")) {
          log.info("发送Challenge请求已建立!!!");
        }

        return ErrorInfo;
      }if ((ACK_Challenge[14] & 0xFF) == 3) {
        if (basConfig.getIsdebug().equals("1")) {
          log.info("系统繁忙，请稍后再试!!!");
        }

        return ErrorInfo;
      }if ((ACK_Challenge[14] & 0xFF) == 4) {
        if (basConfig.getIsdebug().equals("1")) {
          log.info("发送Challenge请求失败!!!");
        }

        return ErrorInfo;
      }
      if (basConfig.getIsdebug().equals("1")) {
        log.info("发送Challenge请求发生未知错误!!!");
      }

      return ErrorInfo;
    }
    catch (IOException e)
    {
      byte[] arrayOfByte1;
      if (basConfig.getIsdebug().equals("1")) {
        log.info("发送Challenge请求无响应!!!");
      }

      return ErrorInfo;
    } finally {
      dataSocket.close();
    }
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.service.action.v1.chap.Chap_Challenge_V1
 * JD-Core Version:    0.6.2
 */