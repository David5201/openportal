package AC.Actions;

import AC.Utils.Make_Challenge;
import AC.Utils.WR;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Ack_Challenge
{
  public static void Challenge(String ip, int port, byte[] Req_Data)
  {
    System.out.println("收到" + ip + ":" + port + ":" + "发来的Challenge请求报文：" + 
      WR.Getbyte2HexString(Req_Data));

    byte[] ReqID = new byte[2];
    int ReqID_int = (int)(1.0D + Math.random() * 32767.0D);
    for (int i = 0; i < 2; i++) {
      int offset = (ReqID.length - 1 - i) * 8;
      ReqID[i] = ((byte)(ReqID_int >>> offset & 0xFF));
    }
    System.out.println("生成的ReqID内容：" + ReqID_int);
    System.out
      .println("验证ReqID内容：" + ((ReqID[0] << 8) + (ReqID[1] & 0xFF)));

    int pos = 16;
    byte[] Ack_Data_challenge = null;
    if ((Req_Data[0] & 0xFF) == 1) {
      pos = 16;
      Ack_Data_challenge = new byte[34];
    }
    if ((Req_Data[0] & 0xFF) == 2) {
      pos = 32;
      Ack_Data_challenge = new byte[50];
    }

    for (int i = 0; i < pos; i++) {
      Ack_Data_challenge[i] = Req_Data[i];
    }

    short typet = 2;
    Ack_Data_challenge[1] = ((byte)typet);

    short ErrCodet = 0;
    Ack_Data_challenge[14] = ((byte)ErrCodet);

    Ack_Data_challenge[6] = ReqID[0];
    Ack_Data_challenge[7] = ReqID[1];

    short atrnumt = 1;
    Ack_Data_challenge[15] = ((byte)atrnumt);
    short atrtypt = 3;

    Ack_Data_challenge[pos] = ((byte)atrtypt);
    int atrleg = 18;
    Ack_Data_challenge[(pos + 1)] = ((byte)(atrleg & 0xFF));

    byte[] Challenge = new byte[16];

    String challengeStr = Make_Challenge.getChallenge();
    try {
      Challenge = challengeStr.getBytes("US-ASCII");
    }
    catch (UnsupportedEncodingException e1) {
      e1.printStackTrace();
    }
    System.out.println("生成的challenge内容：" + challengeStr);
    System.out.println("验证challenge内容：" + new String(Challenge));

    for (int i = 0; i < Challenge.length; i++) {
      Ack_Data_challenge[(pos + 2 + i)] = Challenge[i];
    }

    System.out.println("准备发送Challenge回复报文给：" + ip + ":" + port + ":" + 
      WR.Getbyte2HexString(Ack_Data_challenge));

    DatagramSocket scoket_Challenge_Ack = null;
    try {
      scoket_Challenge_Ack = new DatagramSocket();
      DatagramPacket data = new DatagramPacket(Ack_Data_challenge, 
        Ack_Data_challenge.length, InetAddress.getByName(ip), port);
      scoket_Challenge_Ack.send(data);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    } finally {
      scoket_Challenge_Ack.close();
    }
    WR.space();
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     AC.Actions.Ack_Challenge
 * JD-Core Version:    0.6.2
 */