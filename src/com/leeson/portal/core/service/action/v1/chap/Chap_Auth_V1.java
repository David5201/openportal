package com.leeson.portal.core.service.action.v1.chap;

import com.leeson.core.bean.Portalbas;
import com.leeson.portal.core.model.Config;
import com.leeson.portal.core.service.utils.ChapPassword;
import com.leeson.portal.core.service.utils.PortalUtil;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Map;
import org.apache.log4j.Logger;

public class Chap_Auth_V1
{
  private static Config config = Config.getInstance();
  private static Logger log = Logger.getLogger(Chap_Auth_V1.class);

  public static byte[] auth(String Bas_IP, int bas_PORT, int timeout_Sec, String in_username, String in_password, byte[] SerialNo, byte[] UserIP, byte[] ReqID, byte[] Challenge)
  {
    byte[] ChapPass = new byte[16];
    byte[] Username = in_username.getBytes();
    byte[] password = in_password.getBytes();
    try {
      ChapPass = ChapPassword.MK_ChapPwd(ReqID, Challenge, password);
    }
    catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return Req_Auth(Username, ChapPass, SerialNo, UserIP, 
      ReqID, timeout_Sec, Bas_IP, bas_PORT);
  }

  public static byte[] Req_Auth(byte[] Username, byte[] ChapPass, byte[] SerialNo, byte[] UserIP, byte[] ReqID, int timeout_Sec, String Bas_IP, int bas_PORT)
  {
    Portalbas basConfig = (Portalbas)config.getConfigMap().get("");

    DatagramSocket dataSocket = null;

    byte[] ErrorInfo = new byte[1];
    byte[] Req_Auth = new byte[20 + Username.length + ChapPass.length];
    Req_Auth[0] = 1;
    Req_Auth[1] = 3;
    Req_Auth[2] = 0;
    Req_Auth[3] = 0;
    Req_Auth[4] = SerialNo[0];
    Req_Auth[5] = SerialNo[1];
    Req_Auth[6] = ReqID[0];
    Req_Auth[7] = ReqID[1];
    Req_Auth[8] = UserIP[0];
    Req_Auth[9] = UserIP[1];
    Req_Auth[10] = UserIP[2];
    Req_Auth[11] = UserIP[3];
    Req_Auth[12] = 0;
    Req_Auth[13] = 0;
    Req_Auth[14] = 0;
    Req_Auth[15] = 2;
    Req_Auth[16] = 1;
    Req_Auth[17] = ((byte)(Username.length + 2));
    for (int i = 0; i < Username.length; i++) {
      Req_Auth[(18 + i)] = Username[i];
    }
    Req_Auth[(18 + Username.length)] = 4;
    Req_Auth[(19 + Username.length)] = ((byte)(ChapPass.length + 2));
    for (int i = 0; i < ChapPass.length; i++) {
      Req_Auth[(20 + Username.length + i)] = ChapPass[i];
    }
    if (basConfig.getIsdebug().equals("1")) {
      log.info("REQ Auth" + PortalUtil.Getbyte2HexString(Req_Auth));
    }
    try
    {
      dataSocket = new DatagramSocket();
      DatagramPacket requestPacket = new DatagramPacket(Req_Auth, 
        Req_Auth.length, InetAddress.getByName(Bas_IP), bas_PORT);
      dataSocket.send(requestPacket);
      byte[] ACK_Auth_Data = new byte[16];
      DatagramPacket receivePacket = new DatagramPacket(ACK_Auth_Data, 
        ACK_Auth_Data.length);
      dataSocket.setSoTimeout(timeout_Sec * 1000);
      dataSocket.receive(receivePacket);
      if (basConfig.getIsdebug().equals("1")) {
        log.info("ACK Auth" + PortalUtil.Getbyte2HexString(ACK_Auth_Data));
      }

      if (((ACK_Auth_Data[14] & 0xFF) == 0) || ((ACK_Auth_Data[14] & 0xFF) == 2)) {
        if (basConfig.getIsdebug().equals("1")) {
          log.info("认证成功,准备发送AFF_ACK_AUTH!!!");
        }

        return AFF_Ack_Auth(SerialNo, UserIP, ReqID, 
          Bas_IP, bas_PORT);
      }if ((ACK_Auth_Data[14] & 0xFF) == 1) {
        if (basConfig.getIsdebug().equals("1")) {
          log.info("发送认证请求被拒绝!!!");
        }

        ErrorInfo[0] = 21;
        return ErrorInfo;
      }if ((ACK_Auth_Data[14] & 0xFF) == 2) {
        if (basConfig.getIsdebug().equals("1")) {
          log.info("发送认证请求连接已建立!!!");
        }

        ErrorInfo[0] = 22;
        return ErrorInfo;
      }if ((ACK_Auth_Data[14] & 0xFF) == 3) {
        if (basConfig.getIsdebug().equals("1")) {
          log.info("系统繁忙,请稍后再试!!!");
        }

        ErrorInfo[0] = 23;
        return ErrorInfo;
      }if ((ACK_Auth_Data[14] & 0xFF) == 4) {
        if (basConfig.getIsdebug().equals("1")) {
          log.info("发送认证请求失败!!!");
        }

        ErrorInfo[0] = 24;
        return ErrorInfo;
      }
      if (basConfig.getIsdebug().equals("1")) {
        log.info("发送认证请求出现未知错误!!!");
      }

      ErrorInfo[0] = 2;
      return ErrorInfo;
    }
    catch (IOException e)
    {
      byte[] arrayOfByte1;
      if (basConfig.getIsdebug().equals("1")) {
        log.info("发送认证请求无响应!!!");
      }

      ErrorInfo[0] = 2;
      return ErrorInfo;
    } finally {
      dataSocket.close();
    }
  }

  public static byte[] AFF_Ack_Auth(byte[] SerialNo, byte[] UserIP, byte[] ReqID, String Bas_IP, int bas_PORT)
  {
    Portalbas basConfig = (Portalbas)config.getConfigMap().get("");

    DatagramSocket dataSocket = null;

    byte[] ErrorInfo = new byte[1];
    byte[] AFF_Ack_Auth = new byte[16];

    AFF_Ack_Auth[0] = 1;
    AFF_Ack_Auth[1] = 7;
    AFF_Ack_Auth[2] = 0;
    AFF_Ack_Auth[3] = 0;
    AFF_Ack_Auth[4] = SerialNo[0];
    AFF_Ack_Auth[5] = SerialNo[1];
    AFF_Ack_Auth[6] = ReqID[0];
    AFF_Ack_Auth[7] = ReqID[1];
    AFF_Ack_Auth[8] = UserIP[0];
    AFF_Ack_Auth[9] = UserIP[1];
    AFF_Ack_Auth[10] = UserIP[2];
    AFF_Ack_Auth[11] = UserIP[3];
    AFF_Ack_Auth[12] = 0;
    AFF_Ack_Auth[13] = 0;
    AFF_Ack_Auth[14] = 0;
    AFF_Ack_Auth[15] = 0;
    if (basConfig.getIsdebug().equals("1")) {
      log.info("AFF_Ack_Auth" + PortalUtil.Getbyte2HexString(AFF_Ack_Auth));
    }
    try
    {
      dataSocket = new DatagramSocket();
      DatagramPacket requestPacket = new DatagramPacket(AFF_Ack_Auth, 16, 
        InetAddress.getByName(Bas_IP), bas_PORT);
      dataSocket.send(requestPacket);
      if (basConfig.getIsdebug().equals("1"))
        log.info("发送AFF_Ack_Auth认证成功响应报文回复成功!!!");
    }
    catch (IOException e)
    {
      if (basConfig.getIsdebug().equals("1"))
        log.info("发送AFF_Ack_Auth认证成功响应报文回复失败!!!");
    }
    finally
    {
      dataSocket.close();
    }
    ErrorInfo[0] = 20;
    return ErrorInfo;
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.service.action.v1.chap.Chap_Auth_V1
 * JD-Core Version:    0.6.2
 */