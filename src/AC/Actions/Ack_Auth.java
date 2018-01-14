package AC.Actions;

import AC.Utils.WR;
import java.io.PrintStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import org.apache.commons.lang.math.NumberUtils;

public class Ack_Auth
{
  public static void Auth(String ip, int port, byte[] Req_Data)
  {
    System.out.println("收到" + ip + ":" + port + ":" + "发来的认证请求报文：" + 
      WR.Getbyte2HexString(Req_Data));

    byte[] Ack_Data_Auth = null;
    int pos = 0;
    if ((Req_Data[0] & 0xFF) == 1) {
      Ack_Data_Auth = new byte[30];
      pos = 16;
    }
    if ((Req_Data[0] & 0xFF) == 2) {
      Ack_Data_Auth = new byte[46];
      pos = 32;
    }

    int Len = 0;
    if (Req_Data.length >= Ack_Data_Auth.length)
      Len = Ack_Data_Auth.length;
    else {
      Len = Req_Data.length;
    }
    for (int i = 0; i < Len; i++) {
      Ack_Data_Auth[i] = Req_Data[i];
    }

    short typet = 4;
    Ack_Data_Auth[1] = ((byte)typet);

    short ErrCodet = 0;
    Ack_Data_Auth[14] = ((byte)ErrCodet);

    short atrnumt = 2;
    Ack_Data_Auth[15] = ((byte)atrnumt);

    String Bas_IP = "127.0.0.1";
    byte[] BasIP = new byte[4];
    String[] basips = Bas_IP.split("[.]");

    for (int i = 0; i < 4; i++) {
      int m = NumberUtils.toInt(basips[i]);
      byte b = (byte)m;
      BasIP[i] = b;
    }
    byte[] mac = new byte[6];

    int a = (int)(255.0D * Math.random());
    int b = (int)(255.0D * Math.random());
    int c = (int)(255.0D * Math.random());
    int d = (int)(255.0D * Math.random());
    int e = (int)(255.0D * Math.random());
    int f = (int)(255.0D * Math.random());
    mac[0] = ((byte)a);
    mac[1] = ((byte)b);
    mac[2] = ((byte)c);
    mac[3] = ((byte)d);
    mac[4] = ((byte)e);
    mac[5] = ((byte)f);
    Ack_Data_Auth[pos] = 10;
    Ack_Data_Auth[(pos + 1)] = 6;
    Ack_Data_Auth[(pos + 2)] = BasIP[0];
    Ack_Data_Auth[(pos + 3)] = BasIP[1];
    Ack_Data_Auth[(pos + 4)] = BasIP[2];
    Ack_Data_Auth[(pos + 5)] = BasIP[3];
    Ack_Data_Auth[(pos + 6)] = 11;
    Ack_Data_Auth[(pos + 7)] = 8;
    Ack_Data_Auth[(pos + 8)] = mac[0];
    Ack_Data_Auth[(pos + 9)] = mac[1];
    Ack_Data_Auth[(pos + 10)] = mac[2];
    Ack_Data_Auth[(pos + 11)] = mac[3];
    Ack_Data_Auth[(pos + 12)] = mac[4];
    Ack_Data_Auth[(pos + 13)] = mac[5];

    System.out.println("准备发送认证回复报文给：" + ip + ":" + port + ":" + 
      WR.Getbyte2HexString(Ack_Data_Auth));

    DatagramSocket scoket_Auth_ACK = null;
    try {
      scoket_Auth_ACK = new DatagramSocket();
      DatagramPacket data = new DatagramPacket(Ack_Data_Auth, 
        Ack_Data_Auth.length, InetAddress.getByName(ip), port);
      scoket_Auth_ACK.send(data);
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    } finally {
      scoket_Auth_ACK.close();
    }
    WR.space();
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     AC.Actions.Ack_Auth
 * JD-Core Version:    0.6.2
 */