package com.leeson.portal.core.service.action;

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

public class ReqInfo
{
  private static Logger log = Logger.getLogger(ReqInfo.class);

  private static Config config = Config.getInstance();

  public static byte[] reqInfo(String Bas_IP, int bas_PORT, int timeout_Sec, byte[] SerialNo, byte[] UserIP, String sharedSecret, int portalVer)
  {
    Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
    DatagramSocket dataSocket = null;
    byte[] ErrorInfo = new byte[1];
    byte[] BBuff = new byte[16];
    byte[] Attrs = new byte[4];

    if (portalVer == 1) {
      byte[] Req_Info = new byte[20];
      Req_Info[0] = 1;
    } else if (portalVer == 2) {
      byte[] Req_Info = new byte[36];
      Req_Info[0] = 2;
    } else {
      if (basConfig.getIsdebug().equals("1")) {
        log.info("不支持该版本!!!");
      }

      ErrorInfo[0] = 12;
      return ErrorInfo;
    }
    byte[] Req_Info = new byte[15];
    Req_Info[1] = 9;
    Req_Info[2] = 0;
    Req_Info[3] = 0;
    Req_Info[4] = SerialNo[0];
    Req_Info[5] = SerialNo[1];
    Req_Info[6] = 0;
    Req_Info[7] = 0;
    Req_Info[8] = UserIP[0];
    Req_Info[9] = UserIP[1];
    Req_Info[10] = UserIP[2];
    Req_Info[11] = UserIP[3];
    Req_Info[12] = 0;
    Req_Info[13] = 0;
    Req_Info[14] = 0;
    Req_Info[15] = 1;
    for (int i = 0; i < 16; i++) {
      BBuff[i] = Req_Info[i];
    }
    Attrs[0] = 8;
    Attrs[1] = 4;
    Attrs[2] = 0;
    Attrs[3] = 0;
    Req_Info[16] = 8;
    Req_Info[17] = 4;
    Req_Info[18] = 0;
    Req_Info[19] = 0;
    if (portalVer == 2) {
      byte[] Authen = Authenticator.MK_Authen(BBuff, Attrs, sharedSecret);
      for (int i = 0; i < 16; i++) {
        Req_Info[(16 + i)] = Authen[i];
      }
      Req_Info[32] = 8;
      Req_Info[33] = 4;
      Req_Info[34] = 0;
      Req_Info[35] = 0;
    }

    if (basConfig.getIsdebug().equals("1")) {
      log.info("REQ Info" + PortalUtil.Getbyte2HexString(Req_Info));
    }
    try
    {
      dataSocket = new DatagramSocket();
      DatagramPacket requestPacket = new DatagramPacket(Req_Info, 
        Req_Info.length, InetAddress.getByName(Bas_IP), bas_PORT);
      dataSocket.send(requestPacket);
      byte[] ACK_Info_Data = new byte[50];
      DatagramPacket receivePacket = new DatagramPacket(
        ACK_Info_Data, 50);
      dataSocket.setSoTimeout(timeout_Sec * 1000);
      dataSocket.receive(receivePacket);
      if (basConfig.getIsdebug().equals("1")) {
        log.info("ACK Info" + 
          PortalUtil.Getbyte2HexString(ACK_Info_Data));
      }

      if ((ACK_Info_Data[14] & 0xFF) == 0) {
        if (basConfig.getIsdebug().equals("1")) {
          log.info("建立INFO会话成功,准备发送REQ Challenge!!!");
        }

        return ACK_Info_Data;
      }if ((ACK_Info_Data[14] & 0xFF) == 1) {
        if (basConfig.getIsdebug().equals("1")) {
          log.info("建立INFO会话失败,不支持信息查询功能或者处理失败!!!");
        }

        ErrorInfo[0] = 11;
        return ErrorInfo;
      }if ((ACK_Info_Data[14] & 0xFF) == 2) {
        if (basConfig.getIsdebug().equals("1")) {
          log.info("建立INFO会话失败,消息处理失败，由于某种不可知原因，使处理失败，例如询问消息格式错误等!!!");
        }

        ErrorInfo[0] = 12;
        return ErrorInfo;
      }
      if (basConfig.getIsdebug().equals("1")) {
        log.info("建立INFO会话失败,出现未知错误!!!");
      }

      ErrorInfo[0] = 13;
      return ErrorInfo;
    }
    catch (IOException e)
    {
      byte[] arrayOfByte1;
      if (basConfig.getIsdebug().equals("1")) {
        log.info("建立INFO会话，请求无响应!!!");
      }

      ErrorInfo[0] = 1;
      return ErrorInfo;
    } finally {
      dataSocket.close();
    }
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.service.action.ReqInfo
 * JD-Core Version:    0.6.2
 */