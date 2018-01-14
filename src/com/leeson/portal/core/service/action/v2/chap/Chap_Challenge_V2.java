package com.leeson.portal.core.service.action.v2.chap;

import com.leeson.core.bean.Portalbas;
import com.leeson.portal.core.model.Config;
import com.leeson.portal.core.service.utils.Authenticator;
import com.leeson.portal.core.service.utils.PortalUtil;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Map;
import org.apache.log4j.Logger;

public class Chap_Challenge_V2
{
  private static Logger log = Logger.getLogger(Chap_Challenge_V2.class);
  private static Config config = Config.getInstance();

  public static byte[] challenge(String Bas_IP, int bas_PORT, int timeout_Sec, byte[] SerialNo, byte[] UserIP, String sharedSecret)
  {
    Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
    DatagramSocket dataSocket = null;
    byte[] ErrorInfo = new byte[2];
    byte[] Req_Challenge = new byte[32];
    byte[] BBuff = new byte[16];
    byte[] Attrs = new byte[0];
    Req_Challenge[0] = 2;
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
    for (int i = 0; i < 16; i++) {
      BBuff[i] = Req_Challenge[i];
    }
    byte[] Authen = Authenticator.MK_Authen(BBuff, Attrs, sharedSecret);
    for (int i = 0; i < 16; i++) {
      Req_Challenge[(16 + i)] = Authen[i];
    }
    if (basConfig.getIsdebug().equals("1")) {
      log.info("REQ Challenge" + PortalUtil.Getbyte2HexString(Req_Challenge));
    }
    try
    {
      dataSocket = new DatagramSocket();
      DatagramPacket requestPacket = new DatagramPacket(Req_Challenge, 
        32, InetAddress.getByName(Bas_IP), bas_PORT);
      dataSocket.send(requestPacket);
      byte[] ACK_Challenge_Data = new byte[50];
      DatagramPacket receivePacket = new DatagramPacket(
        ACK_Challenge_Data, 50);
      dataSocket.setSoTimeout(timeout_Sec * 1000);
      dataSocket.receive(receivePacket);
      if (basConfig.getIsdebug().equals("1")) {
        log.info("ACK Challenge" + 
          PortalUtil.Getbyte2HexString(ACK_Challenge_Data));
      }

      ErrorInfo[0] = ACK_Challenge_Data[6];
      ErrorInfo[1] = ACK_Challenge_Data[7];

      if ((ACK_Challenge_Data[14] & 0xFF) == 0) {
        if (basConfig.getIsdebug().equals("1")) {
          log.info("发送Challenge请求成功,准备发送REQ Auth!!!");
        }

        return ACK_Challenge_Data;
      }if ((ACK_Challenge_Data[14] & 0xFF) == 1) {
        if (basConfig.getIsdebug().equals("1")) {
          log.info("发送Challenge请求被拒绝!!!");
        }

        return ErrorInfo;
      }if ((ACK_Challenge_Data[14] & 0xFF) == 2) {
        if (basConfig.getIsdebug().equals("1")) {
          log.info("发送Challenge连接已建立!!!");
        }

        return ErrorInfo;
      }if ((ACK_Challenge_Data[14] & 0xFF) == 3) {
        if (basConfig.getIsdebug().equals("1")) {
          log.info("系统繁忙，请稍后再试!!!");
        }

        return ErrorInfo;
      }if ((ACK_Challenge_Data[14] & 0xFF) == 4) {
        if (basConfig.getIsdebug().equals("1")) {
          log.info("发送Challenge请求出现未知错误!!!");
        }

        return ErrorInfo;
      }
      if (basConfig.getIsdebug().equals("1")) {
        log.info("发送Challenge请求出现未知错误!!!");
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
 * Qualified Name:     com.leeson.portal.core.service.action.v2.chap.Chap_Challenge_V2
 * JD-Core Version:    0.6.2
 */