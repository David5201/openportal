package AC.Actions;

import AC.Utils.WR;
import java.io.PrintStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Ack_InfoH3C
{
  public static void Info(String ip, int port, byte[] Req_Data)
  {
    System.out.println("收到" + ip + ":" + port + ":" + "发来的Info会话请求报文：" + 
      WR.Getbyte2HexString(Req_Data));

    byte[] Ack_Data_Info = null;
    if ((Req_Data[0] & 0xFF) == 1) {
      Ack_Data_Info = new byte[16];
    }
    if ((Req_Data[0] & 0xFF) == 2) {
      Ack_Data_Info = new byte[32];
    }

    for (int i = 0; i < Ack_Data_Info.length; i++) {
      Ack_Data_Info[i] = Req_Data[i];
    }

    short typet = 10;
    Ack_Data_Info[1] = ((byte)typet);

    short ErrCodet = 0;
    Ack_Data_Info[14] = ((byte)ErrCodet);

    short atrnumt = 0;
    Ack_Data_Info[15] = ((byte)atrnumt);

    System.out.println("准备发送Info回复报文给：" + ip + ":" + port + ":" + 
      WR.Getbyte2HexString(Ack_Data_Info));

    DatagramSocket scoket_Auth_ACK = null;
    try {
      scoket_Auth_ACK = new DatagramSocket();
      DatagramPacket data = new DatagramPacket(Ack_Data_Info, 
        Ack_Data_Info.length, InetAddress.getByName(ip), port);
      scoket_Auth_ACK.send(data);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    } finally {
      scoket_Auth_ACK.close();
    }
    WR.space();
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     AC.Actions.Ack_InfoH3C
 * JD-Core Version:    0.6.2
 */