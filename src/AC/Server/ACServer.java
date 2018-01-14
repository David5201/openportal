package AC.Server;

import AC.Actions.Ack_Auth;
import AC.Actions.Ack_Challenge;
import AC.Actions.Ack_InfoH3C;
import AC.Actions.Ack_Quit;
import AC.Actions.Ack_Unknow;
import AC.Utils.WR;
import com.leeson.core.bean.Portalbas;
import com.leeson.portal.core.model.Config;
import java.io.PrintStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Map;
import org.apache.log4j.Logger;

public class ACServer extends Thread
{
  private static Config config = Config.getInstance();
  private static Logger log = Logger.getLogger(ACServer.class);

  private static Boolean isRun = Boolean.valueOf(true);
  private static DatagramSocket socket = null;

  DatagramPacket data = null;

  public ACServer(DatagramPacket data)
  {
    this.data = data;
  }

  public void run()
  {
    System.out.println("Rececie Packet Size: " + this.data.getLength());
    byte[] Req_Data_Base = new byte[this.data.getLength()];
    for (int l = 0; l < Req_Data_Base.length; l++) {
      Req_Data_Base[l] = this.data.getData()[l];
    }

    String ip = this.data.getAddress().getHostAddress();
    int port = this.data.getPort();

    byte[] Ver = new byte[1];
    byte[] Type = new byte[1];
    byte[] Mod = new byte[1];
    byte[] Rsvd = new byte[1];
    byte[] SerialNo = new byte[2];
    byte[] ReqID = new byte[2];
    byte[] UserIP = new byte[4];
    byte[] UserPort = new byte[2];
    byte[] ErrCode = new byte[1];
    byte[] AttrNum = new byte[1];

    Ver[0] = Req_Data_Base[0];
    Type[0] = Req_Data_Base[1];
    Mod[0] = Req_Data_Base[2];
    Rsvd[0] = Req_Data_Base[3];
    SerialNo[0] = Req_Data_Base[4];
    SerialNo[1] = Req_Data_Base[5];
    ReqID[0] = Req_Data_Base[6];
    ReqID[1] = Req_Data_Base[7];
    UserIP[0] = Req_Data_Base[8];
    UserIP[1] = Req_Data_Base[9];
    UserIP[2] = Req_Data_Base[10];
    UserIP[3] = Req_Data_Base[11];
    UserPort[0] = Req_Data_Base[12];
    UserPort[1] = Req_Data_Base[13];
    ErrCode[0] = Req_Data_Base[14];
    AttrNum[0] = Req_Data_Base[15];

    if ((Ver[0] & 0xFF) == 0) {
      Ack_Unknow.Ack(ip, port, Req_Data_Base);
      WR.space();
    }
    else
    {
      String username = null;
      String userpass = null;
      String chappass = null;
      int pos = 0;
      if ((Ver[0] & 0xFF) == 1) {
        pos = 16;
      }
      if ((Ver[0] & 0xFF) == 2) {
        pos = 32;
      }

      int AN = AttrNum[0] & 0xFF;
      if (AN > 0) {
        int num = 1;
        while (num <= AN) {
          int type = Req_Data_Base[pos] & 0xFF;
          pos++;
          int len = (Req_Data_Base[pos] & 0xFF) - 2;
          pos++;
          byte[] buf = new byte[len];
          for (int l = 0; l < buf.length; l++) {
            buf[l] = Req_Data_Base[(pos + l)];
          }
          pos += len;
          if (type == 1)
            username = new String(buf);
          else if (type == 2)
            userpass = new String(buf);
          else if (type == 4) {
            chappass = WR.Getbyte2HexString(buf);
          }
          num++;
        }

      }

      if ((Type[0] & 0xFF) == 9) {
        Ack_InfoH3C.Info(ip, port, Req_Data_Base);
      }
      else if ((Type[0] & 0xFF) == 1) {
        Ack_Challenge.Challenge(ip, port, Req_Data_Base);
      }
      else if ((Type[0] & 0xFF) == 3) {
        Show_User(Mod, username, userpass, chappass);
        Ack_Auth.Auth(ip, port, Req_Data_Base);
      }
      else if (((Type[0] & 0xFF) == 5) && ((ErrCode[0] & 0xFF) == 0)) {
        Ack_Quit.Quit(ip, port, Req_Data_Base);
      }
      else if (((Type[0] & 0xFF) == 5) && ((ErrCode[0] & 0xFF) == 1))
      {
        if (((ReqID[0] & 0xFF) == 0) && 
          ((ReqID[1] & 0xFF) == 0))
          System.out.println("Challenge request TimeOut Request OR Logout Requst TimeOut Request !!");
        else if (((ReqID[0] & 0xFF) != 0) || ((ReqID[1] & 0xFF) != 0))
          System.out.println("Auth request TimeOut Request !!");
        else {
          System.out.println("Other request TimeOut Request !!");
        }
      }
      else if ((Type[0] & 0xFF) == 7) {
        System.out.println("Rececie " + ip + ":" + port + ":" + "Auth Success Packet " + 
          WR.Getbyte2HexString(Req_Data_Base));
        if ((Req_Data_Base[15] & 0xFF) == 0) {
          System.out.println("Host: " + ip + " Login Success !!");
        }
        WR.space();
      }
      else
      {
        Ack_Unknow.Ack(ip, port, Req_Data_Base);
        WR.space();
      }
    }
  }

  private static void Show_User(byte[] Mod, String username, String userpass, String chappass)
  {
    System.out.println("Username: " + username);
    if ((Mod[0] & 0xFF) == 0)
      System.out.println("CHAP-Password: " + chappass);
    else if ((Mod[0] & 0xFF) == 1)
      System.out.println("PAP Password: " + userpass);
  }

  public static void openServer()
  {
    try {
      socket = new DatagramSocket(2000);
      System.out.println("AC Service Start OK , Listen Portal UDP 2000 !!");
      WR.space();
      while (isRun.booleanValue()) {
        byte[] b = new byte[100];
        DatagramPacket data = new DatagramPacket(b, b.length);
        socket.receive(data);
        new ACServer(data).start();
      }
    } catch (Exception e) {
      if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
        log.error("==============ERROR Start=============");
        log.error(e);
        log.error("ERROR INFO ", e);
        log.error("==============ERROR End=============");
      }
    }
  }

  public static void closeServer()
  {
    try
    {
      isRun = Boolean.valueOf(false);
      if (socket != null) {
        socket.close();
        socket = null;
      }
    } catch (Exception e) {
      if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
        log.error("==============ERROR Start=============");
        log.error(e);
        log.error("ERROR INFO ", e);
        log.error("==============ERROR End=============");
      }
    }
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     AC.Server.ACServer
 * JD-Core Version:    0.6.2
 */