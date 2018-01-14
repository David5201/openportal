package AC.Actions;

import AC.Utils.WR;
import java.io.PrintStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Ack_Quit
{
  public static void Quit(String ip, int port, byte[] Req_Data)
  {
    System.out.println("收到" + ip + ":" + port + ":" + "发来的下线请求报文：" + 
      WR.Getbyte2HexString(Req_Data));

    byte[] Ack_Data_Quit = null;
    if ((Req_Data[0] & 0xFF) == 1) {
      Ack_Data_Quit = new byte[16];
    }
    if ((Req_Data[0] & 0xFF) == 2) {
      Ack_Data_Quit = new byte[32];
    }

    for (int i = 0; i < Ack_Data_Quit.length; i++) {
      Ack_Data_Quit[i] = Req_Data[i];
    }
    short typet = 6;
    Ack_Data_Quit[1] = ((byte)typet);

    short ErrCodet = 0;
    Ack_Data_Quit[14] = ((byte)ErrCodet);

    System.out.println("准备发送下线回复报文给：" + ip + ":" + port + ":" + 
      WR.Getbyte2HexString(Ack_Data_Quit));

    DatagramSocket scoket_Quit_ACK = null;
    try {
      scoket_Quit_ACK = new DatagramSocket();
      DatagramPacket data = new DatagramPacket(Ack_Data_Quit, 
        Ack_Data_Quit.length, InetAddress.getByName(ip), port);
      scoket_Quit_ACK.send(data);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    } finally {
      scoket_Quit_ACK.close();
    }
    WR.space();
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     AC.Actions.Ack_Quit
 * JD-Core Version:    0.6.2
 */