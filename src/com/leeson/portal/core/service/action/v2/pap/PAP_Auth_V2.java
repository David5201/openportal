package com.leeson.portal.core.service.action.v2.pap;

import com.leeson.core.bean.Portalbas;
import com.leeson.portal.core.model.Config;
import com.leeson.portal.core.service.action.GetMac;
import com.leeson.portal.core.service.utils.Authenticator;
import com.leeson.portal.core.service.utils.PortalUtil;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Map;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;

public class PAP_Auth_V2
{
  private static Config config = Config.getInstance();
  private static Logger log = Logger.getLogger(PAP_Auth_V2.class);

  public static boolean auth(String Bas_IP, int bas_PORT, int timeout_Sec, String in_username, String in_password, byte[] SerialNo, byte[] UserIP, String sharedSecret)
  {
    byte[] Username = in_username.getBytes();
    byte[] password = in_password.getBytes();
    byte[] authbuff = new byte[4 + Username.length + password.length + 6];
    authbuff[0] = 1;
    authbuff[1] = ((byte)(Username.length + 2));
    for (int i = 0; i < Username.length; i++) {
      authbuff[(2 + i)] = Username[i];
    }
    authbuff[(2 + Username.length)] = 2;
    authbuff[(3 + Username.length)] = ((byte)(password.length + 2));
    for (int i = 0; i < password.length; i++) {
      authbuff[(4 + Username.length + i)] = password[i];
    }

    byte[] BasIP = new byte[4];
    String[] basips = Bas_IP.split("[.]");

    for (int i = 0; i < 4; i++) {
      int m = NumberUtils.toInt(basips[i]);
      byte b = (byte)m;
      BasIP[i] = b;
    }
    authbuff[(4 + Username.length + password.length)] = 10;
    authbuff[(4 + Username.length + password.length + 1)] = 6;
    authbuff[(4 + Username.length + password.length + 2)] = BasIP[0];
    authbuff[(4 + Username.length + password.length + 3)] = BasIP[1];
    authbuff[(4 + Username.length + password.length + 4)] = BasIP[2];
    authbuff[(4 + Username.length + password.length + 5)] = BasIP[3];

    return Req_Auth(Username, password, SerialNo, UserIP, 
      authbuff, timeout_Sec, Bas_IP, bas_PORT, sharedSecret);
  }

  public static boolean Req_Auth(byte[] Username, byte[] password, byte[] SerialNo, byte[] UserIP, byte[] authbuff, int timeout_Sec, String Bas_IP, int bas_PORT, String sharedSecret)
  {
    Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
    DatagramSocket dataSocket = null;

    byte[] Req_Auth = new byte[36 + Username.length + password.length + 6];
    Req_Auth[0] = 2;
    Req_Auth[1] = 3;
    Req_Auth[2] = 1;
    Req_Auth[3] = 0;
    Req_Auth[4] = SerialNo[0];
    Req_Auth[5] = SerialNo[1];
    Req_Auth[6] = 0;
    Req_Auth[7] = 0;
    Req_Auth[8] = UserIP[0];
    Req_Auth[9] = UserIP[1];
    Req_Auth[10] = UserIP[2];
    Req_Auth[11] = UserIP[3];
    Req_Auth[12] = 0;
    Req_Auth[13] = 0;
    Req_Auth[14] = 0;
    Req_Auth[15] = 3;
    byte[] BBuff = new byte[16];
    for (int i = 0; i < 16; i++) {
      BBuff[i] = Req_Auth[i];
    }
    byte[] Authen = Authenticator.MK_Authen(BBuff, authbuff, sharedSecret);
    for (int i = 0; i < 16; i++) {
      Req_Auth[(16 + i)] = Authen[i];
    }
    for (int i = 0; i < authbuff.length; i++) {
      Req_Auth[(32 + i)] = authbuff[i];
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

      byte[] ACK_Auth_Data = new byte[100];
      DatagramPacket receivePacket = new DatagramPacket(ACK_Auth_Data, 100);

      dataSocket.setSoTimeout(timeout_Sec * 1000);
      dataSocket.receive(receivePacket);
      ACK_Auth_Data = new byte[receivePacket.getLength()];
      for (int i = 0; i < ACK_Auth_Data.length; i++) {
        ACK_Auth_Data[i] = receivePacket.getData()[i];
      }
      if (basConfig.getIsdebug().equals("1")) {
        log.info("ACK Auth" + PortalUtil.Getbyte2HexString(ACK_Auth_Data));
      }

      if (((ACK_Auth_Data[14] & 0xFF) == 0) || 
        ((ACK_Auth_Data[14] & 0xFF) == 2))
      {
        GetMac.getMac(ACK_Auth_Data, Bas_IP, UserIP);

        if (basConfig.getIsdebug().equals("1")) {
          log.info("认证成功,准备发送AFF_ACK_AUTH!!!");
        }

        return AFF_Ack_Auth(SerialNo, UserIP, Bas_IP, 
          bas_PORT, sharedSecret);
      }
      if ((ACK_Auth_Data[14] & 0xFF) == 1) {
        if (basConfig.getIsdebug().equals("1")) {
          log.info("发送认证请求被拒绝!!!");
        }

      }
      else if ((ACK_Auth_Data[14] & 0xFF) == 3) {
        if (basConfig.getIsdebug().equals("1")) {
          log.info("系统繁忙，请稍后再试!!!");
        }
      }
      else if ((ACK_Auth_Data[14] & 0xFF) == 4) {
        if (basConfig.getIsdebug().equals("1")) {
          log.info("发送认证请求失败!!!");
        }

      }
      else if (basConfig.getIsdebug().equals("1")) {
        log.info("发送认证请求出现未知错误!!!");
      }

      return false;
    }
    catch (IOException e) {
      if (basConfig.getIsdebug().equals("1")) {
        log.info("发送认证请求无响应!!!");
      }

      PAP_Quit_V2.quit(2, Bas_IP, bas_PORT, timeout_Sec, SerialNo, 
        UserIP, sharedSecret);
      return false;
    } finally {
      dataSocket.close();
    }
  }

  public static boolean AFF_Ack_Auth(byte[] SerialNo, byte[] UserIP, String Bas_IP, int bas_PORT, String sharedSecret)
  {
    Portalbas basConfig = (Portalbas)config.getConfigMap().get("");

    DatagramSocket dataSocket = null;

    byte[] AFF_Ack_Auth_Data = new byte[32];

    AFF_Ack_Auth_Data[0] = 2;
    AFF_Ack_Auth_Data[1] = 7;
    AFF_Ack_Auth_Data[2] = 1;
    AFF_Ack_Auth_Data[3] = 0;
    AFF_Ack_Auth_Data[4] = SerialNo[0];
    AFF_Ack_Auth_Data[5] = SerialNo[1];
    AFF_Ack_Auth_Data[6] = 0;
    AFF_Ack_Auth_Data[7] = 0;
    AFF_Ack_Auth_Data[8] = UserIP[0];
    AFF_Ack_Auth_Data[9] = UserIP[1];
    AFF_Ack_Auth_Data[10] = UserIP[2];
    AFF_Ack_Auth_Data[11] = UserIP[3];
    AFF_Ack_Auth_Data[12] = 0;
    AFF_Ack_Auth_Data[13] = 0;
    AFF_Ack_Auth_Data[14] = 0;
    AFF_Ack_Auth_Data[15] = 0;
    byte[] BBBuff = new byte[16];
    for (int i = 0; i < BBBuff.length; i++) {
      BBBuff[i] = AFF_Ack_Auth_Data[i];
    }
    byte[] Attrs = new byte[0];
    byte[] BAuthen = Authenticator.MK_Authen(BBBuff, Attrs, sharedSecret);
    for (int i = 0; i < 16; i++) {
      AFF_Ack_Auth_Data[(16 + i)] = BAuthen[i];
    }
    if (basConfig.getIsdebug().equals("1")) {
      log.info("AFF_Ack_Auth" + 
        PortalUtil.Getbyte2HexString(AFF_Ack_Auth_Data));
    }
    try
    {
      dataSocket = new DatagramSocket();

      DatagramPacket requestPacket = new DatagramPacket(
        AFF_Ack_Auth_Data, 32, InetAddress.getByName(Bas_IP), 
        bas_PORT);
      dataSocket.send(requestPacket);
      if (basConfig.getIsdebug().equals("1")) {
        log.info("发送AFF_Ack_Auth认证成功回复报文成功!!!");
      }

      return true;
    } catch (IOException e) {
      if (basConfig.getIsdebug().equals("1")) {
        log.info("发送AFF_Ack_Auth认证成功回复报文出错!!!");
      }

      return false;
    } finally {
      dataSocket.close();
    }
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.service.action.v2.pap.PAP_Auth_V2
 * JD-Core Version:    0.6.2
 */