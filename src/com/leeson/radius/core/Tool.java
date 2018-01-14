package com.leeson.radius.core;

import com.leeson.core.bean.Portalbas;
import com.leeson.portal.core.model.Config;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.MessageDigest;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;
import org.apache.log4j.Logger;

public class Tool
{
  private static Logger log = Logger.getLogger(Tool.class);

  private static Config config = Config.getInstance();

  public static void writeLog(String name, String content) {
    Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
    try
    {
      if (basConfig.getIsdebug().equals("1"))
        log.info("[" + name + "]" + content);
    }
    catch (Exception localException)
    {
    }
  }

  public static void writeErrorLog(String name, Exception content)
  {
    Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
    try
    {
      if (basConfig.getIsdebug().equals("1"))
        log.error("[" + name + "]" + content);
    }
    catch (Exception localException)
    {
    }
  }

  public static String getAttributeIP(String ip)
  {
    byte[] UserIP = new byte[4];
    String[] ips = ip.split("[.]");

    for (int i = 0; i < 4; i++) {
      int m = Integer.valueOf(ips[i]).intValue();
      byte b = (byte)m;
      UserIP[i] = b;
    }
    String info = ByteToHex(UserIP);

    String ret = null;
    try
    {
      String len = Integer.toHexString(6);
      while (len.length() < 2)
        len = "0" + len;
      String types = Integer.toHexString(8);
      while (types.length() < 2)
        types = "0" + types;
      ret = types + len + info;
    } catch (Exception localException) {
    }
    return ret;
  }

  public static String getAttributeNasIP(String ip) {
    byte[] UserIP = new byte[4];
    String[] ips = ip.split("[.]");

    for (int i = 0; i < 4; i++) {
      int m = Integer.valueOf(ips[i]).intValue();
      byte b = (byte)m;
      UserIP[i] = b;
    }
    String info = ByteToHex(UserIP);

    String ret = null;
    try
    {
      String len = Integer.toHexString(6);
      while (len.length() < 2)
        len = "0" + len;
      String types = Integer.toHexString(4);
      while (types.length() < 2)
        types = "0" + types;
      ret = types + len + info;
    } catch (Exception localException) {
    }
    return ret;
  }

  public static void openProxy(String proxyhost, String proxyport)
  {
    try {
      System.setProperty("sun.net.client.defaultConnectTimeout", "3000");
      System.setProperty("sun.net.client.defaultReadTimeout", "10000");
      if ((proxyhost != null) && (proxyport != null)) {
        Properties prop = System.getProperties();
        prop.put("http.proxyHost", proxyhost);
        prop.put("http.proxyPort", proxyport);
      }
    }
    catch (Exception localException) {
    }
  }

  public static String sendURL(String url) {
    StringBuffer ret = new StringBuffer();
    InputStream is = null;
    InputStreamReader isr = null;
    BufferedReader br = null;
    try {
      URL su = new URL(url);
      is = su.openStream();
      isr = new InputStreamReader(is);
      br = new BufferedReader(isr);
      for (String line = br.readLine(); line != null; line = br
        .readLine())
      {
        ret.append(line + "\r\n");
      }
    } catch (Exception localException) {
    }
    try { if (br != null) {
        br.close();
        br = null;
      }
    } catch (Exception localException1) {
    }
    try {
      if (isr != null) {
        isr.close();
        isr = null;
      }
    } catch (Exception localException2) {
    }
    try {
      if (is != null) {
        is.close();
        is = null;
      }
    } catch (Exception localException3) {
    }
    return ret.toString();
  }

  public static String encodeMD5(String str)
  {
    String ret = "";
    try {
      MessageDigest md = MessageDigest.getInstance("MD5");
      md.update(HexToByte(str));

      ret = ByteToHex(md.digest());
    } catch (Exception localException) {
    }
    return ret;
  }

  public static String decodeMD5(String sharedSecret, String authenticator, String password)
  {
    String ret = "";
    try {
      byte[] pwd = HexToByte(password);
      if ((pwd != null) && (pwd.length >= 16))
      {
        byte[] temp = HexToByte(encodeMD5(sharedSecret + authenticator));
        for (int i = 0; i < 16; i++) {
          pwd[i] = ((byte)(temp[i] ^ pwd[i]));
        }
        if (pwd.length > 16) {
          for (int i = 16; i < pwd.length; i += 16) {
            temp = HexToByte(encodeMD5(sharedSecret + 
              ByteToHex(pwd, i - 16, 16)));
            for (int j = 0; j < 16; j++) {
              pwd[(i + j)] = ((byte)(temp[j] ^ pwd[(i + j)]));
            }
          }
        }
        int len = pwd.length;
        while ((len > 0) && (pwd[(len - 1)] == 0))
          len--;
        ret = new String(pwd, 0, len);
      }
    } catch (Exception localException) {
    }
    return ret;
  }

  public static String ByteToHex(byte[] buf) {
    StringBuffer ret = new StringBuffer();
    String result = null;
    try {
      for (int i = 0; i < buf.length; i++) {
        String temp = Integer.toHexString(buf[i] & 0xFF);
        while (temp.length() < 2)
          temp = "0" + temp;
        ret.append(temp);
      }
      result = ret.toString().toUpperCase();
    } catch (Exception localException) {
    }
    return result;
  }

  public static String ByteToHex(byte[] buf, int begin, int end) {
    StringBuffer ret = new StringBuffer();
    String result = null;
    try {
      for (int i = begin; i < end; i++) {
        String temp = Integer.toHexString(buf[i] & 0xFF);
        while (temp.length() < 2)
          temp = "0" + temp;
        ret.append(temp);
      }
      result = ret.toString().toUpperCase();
    } catch (Exception localException) {
    }
    return result;
  }

  public static byte[] HexToByte(String value) {
    byte[] ret = null;
    try {
      int len = value.length() / 2;
      ret = new byte[len];
      for (int i = 0; i < len; i++)
        ret[i] = ((byte)Integer.parseInt(
          value.substring(i * 2, i * 2 + 2), 16));
    }
    catch (Exception localException) {
    }
    return ret;
  }

  public static long ByteToLong(byte[] value) {
    long ret = 0L;
    try {
      for (int i = 0; i < value.length; i++)
        ret = ret << 8 | value[i] & 0xFF;
    }
    catch (Exception localException) {
    }
    return ret;
  }

  public static int ByteToInt(byte[] value) {
    int ret = value[0] & 0xFF;
    return ret;
  }

  public static String ByteToIP(byte[] value) {
    String ret = null;
    try {
      if ((value != null) && (value.length >= 4))
      {
        ret = (value[0] & 0xFF) + '.' + (
          value[1] & 0xFF) + '.' + (
          value[2] & 0xFF) + '.' + (
          value[3] & 0xFF)+"";
      }
    } catch (Exception localException) {
    }
    return ret;
  }

  public static String[][] getAttributes(String attributes)
  {
    String[][] ret = null;
    Vector list = null;
    try {
      list = new Vector();
      while (attributes.length() >= 4) {
        int len = 4;
        try {
          len = Integer.parseInt(attributes.substring(2, 4), 16) * 2;
          if (len >= 4)
            list.add(attributes.substring(0, len));
          else
            len = 4;
        }
        catch (Exception localException) {
        }
        attributes = attributes.substring(len);
      }
    } catch (Exception localException1) {
    }
    try {
      if ((list != null) && (list.size() > 0)) {
        int len = list.size();
        ret = new String[len][2];
        for (int i = 0; i < len; i++)
          try {
            String temp = (String)list.get(i);
            ret[i][0] = temp.substring(0, 2);
            ret[i][1] = temp.substring(4);
          }
          catch (Exception localException2) {
          }
        list.clear();
      }
    } catch (Exception localException3) {
    }
    return ret;
  }

  public static String getAttributeValue(String ip, int type, String value)
  {
    String ret = null;
    String name = null;
    try {
      switch (type)
      {
      case 1:
        name = "User-Name";
        ret = new String(HexToByte(value));

        break;
      case 2:
        name = "User-Password";
        ret = value;

        break;
      case 3:
        name = "Chap-Password";
        ret = value;

        break;
      case 4:
        name = "Nas-IP-Address";
        ret = ByteToIP(HexToByte(value));

        break;
      case 5:
        name = "Nas-Port";
        ret = Long.toString(ByteToLong(HexToByte(value)));

        break;
      case 6:
        name = "Service-Type";
        ret = getServiceType((int)ByteToLong(HexToByte(value)));

        break;
      case 7:
        name = "Framed-Protocol";
        ret = getFramedProtocol((int)ByteToLong(HexToByte(value)));

        break;
      case 8:
        name = "Framed-IP-Address";
        ret = ByteToIP(HexToByte(value));

        break;
      case 9:
        name = "Framed-IP-Netmask";
        ret = ByteToIP(HexToByte(value));

        break;
      case 10:
        name = "Framed-Routing";
        ret = getFramedRouting((int)ByteToLong(HexToByte(value)));

        break;
      case 11:
        name = "Filter-Id";
        ret = new String(HexToByte(value));

        break;
      case 12:
        name = "Framed-MTU";
        ret = Long.toString(ByteToLong(HexToByte(value)));

        break;
      case 13:
        name = "Framed-Compression";
        ret = getFramedCompression((int)ByteToLong(HexToByte(value)));

        break;
      case 14:
        name = "Login-IP-Host";
        ret = ByteToIP(HexToByte(value));

        break;
      case 15:
        name = "Login-Service";
        ret = getLoginService((int)ByteToLong(HexToByte(value)));

        break;
      case 16:
        name = "Login-TCP-Port";
        ret = Long.toString(ByteToLong(HexToByte(value)));

        break;
      case 18:
        name = "Reply-Message";
        ret = new String(HexToByte(value));

        break;
      case 19:
        name = "Callback-Number";
        ret = new String(HexToByte(value));

        break;
      case 20:
        name = "Callback-Id";
        ret = new String(HexToByte(value));

        break;
      case 22:
        name = "Framed-Route";
        ret = new String(HexToByte(value));

        break;
      case 23:
        name = "Framed-IPX-Network";
        ret = ByteToIP(HexToByte(value));

        break;
      case 24:
        name = "State";
        ret = value;

        break;
      case 25:
        name = "Class";
        ret = value;

        break;
      case 26:
        name = "Vendor-Specific";
        ret = value;

        break;
      case 27:
        name = "Session-Timeout";
        ret = Long.toString(ByteToLong(HexToByte(value)));

        break;
      case 28:
        name = "Idle-Timeout";
        ret = Long.toString(ByteToLong(HexToByte(value)));

        break;
      case 29:
        name = "Termination-Action";
        ret = getTerminationAction((int)ByteToLong(HexToByte(value)));

        break;
      case 30:
        name = "Called-Station-Id";
        ret = new String(HexToByte(value));

        break;
      case 31:
        name = "Calling-Station-Id";
        ret = new String(HexToByte(value));

        break;
      case 32:
        name = "Nas-Identifier";
        ret = new String(HexToByte(value));

        break;
      case 33:
        name = "Proxy-State";
        ret = value;

        break;
      case 34:
        name = "Login-LAT-Service";
        ret = new String(HexToByte(value));

        break;
      case 35:
        name = "Login-LAT-Node";
        ret = new String(HexToByte(value));

        break;
      case 36:
        name = "Login-LAT-Group";
        ret = value;

        break;
      case 37:
        name = "Framed-AppleTalk-Link";
        ret = Long.toString(ByteToLong(HexToByte(value)));

        break;
      case 38:
        name = "Framed-AppleTalk-Network";
        ret = Long.toString(ByteToLong(HexToByte(value)));

        break;
      case 39:
        name = "Framed-AppleTalk-Zone";
        ret = new String(HexToByte(value));

        break;
      case 40:
        name = "Acct-Status-Type";
        ret = getAcctStatusType((int)ByteToLong(HexToByte(value)));

        break;
      case 41:
        name = "Acct-Delay-Time";
        ret = Long.toString(ByteToLong(HexToByte(value)));

        break;
      case 42:
        name = "Acct-Input-Octets";
        ret = Long.toString(ByteToLong(HexToByte(value)));

        break;
      case 43:
        name = "Acct-Output-Octets";
        ret = Long.toString(ByteToLong(HexToByte(value)));

        break;
      case 44:
        name = "Acct-Session-Id";
        ret = new String(HexToByte(value));

        break;
      case 45:
        name = "Acct-Authentic";
        ret = getAcctAuthentic((int)ByteToLong(HexToByte(value)));

        break;
      case 46:
        name = "Acct-Session-Time";
        ret = Long.toString(ByteToLong(HexToByte(value)));

        break;
      case 47:
        name = "Acct-Input-Packets";
        ret = Long.toString(ByteToLong(HexToByte(value)));

        break;
      case 48:
        name = "Acct-Output-Packets";
        ret = Long.toString(ByteToLong(HexToByte(value)));

        break;
      case 49:
        name = "Acct-Terminate-Cause";
        ret = getAcctTerminateCause((int)ByteToLong(HexToByte(value)));

        break;
      case 50:
        name = "Acct-Multi-Session-Id";
        ret = new String(HexToByte(value));

        break;
      case 51:
        name = "Acct-Link-Count";
        ret = Long.toString(ByteToLong(HexToByte(value)));

        break;
      case 52:
        name = "Acct-Input-Gigawords";
        ret = Long.toString(ByteToLong(HexToByte(value)));

        break;
      case 53:
        name = "Acct-Output-Gigawords";
        ret = Long.toString(ByteToLong(HexToByte(value)));

        break;
      case 55:
        name = "Event-Timestamp";
        ret = Long.toString(ByteToLong(HexToByte(value)));

        break;
      case 60:
        name = "CHAP-Challenge";
        ret = value;

        break;
      case 61:
        name = "NAS-Port-Type";
        ret = getNASPortType((int)ByteToLong(HexToByte(value)));

        break;
      case 62:
        name = "Port-Limit";
        ret = Long.toString(ByteToLong(HexToByte(value)));

        break;
      case 63:
        name = "Login-LAT-Port";
        ret = Long.toString(ByteToLong(HexToByte(value)));

        break;
      case 68:
        name = "Acct-Tunnel-Connection";
        ret = new String(HexToByte(value));

        break;
      case 70:
        name = "ARAP-Password";
        ret = new String(HexToByte(value));

        break;
      case 71:
        name = "ARAP-Features";
        ret = new String(HexToByte(value));

        break;
      case 72:
        name = "ARAP-Zone-Access";
        ret = Long.toString(ByteToLong(HexToByte(value)));

        break;
      case 73:
        name = "ARAP-Security";
        ret = Long.toString(ByteToLong(HexToByte(value)));

        break;
      case 74:
        name = "ARAP-Security-Data";
        ret = new String(HexToByte(value));

        break;
      case 75:
        name = "Password-Retry";
        ret = Long.toString(ByteToLong(HexToByte(value)));

        break;
      case 76:
        name = "Prompt";
        ret = getPrompt((int)ByteToLong(HexToByte(value)));

        break;
      case 77:
        name = "Connect-Info";
        ret = new String(HexToByte(value));

        break;
      case 78:
        name = "Configuration-Token";
        ret = new String(HexToByte(value));

        break;
      case 79:
        name = "EAP-Message";
        ret = value;

        break;
      case 80:
        name = "Message-Authenticator";
        ret = value;

        break;
      case 84:
        name = "ARAP-Challenge-Response";
        ret = new String(HexToByte(value));

        break;
      case 85:
        name = "Acct-Interim-Interval";
        ret = Long.toString(ByteToLong(HexToByte(value)));

        break;
      case 87:
        name = "NAS-Port-Id";
        ret = new String(HexToByte(value));

        break;
      case 88:
        name = "Framed-Pool";
        ret = new String(HexToByte(value));

        break;
      case 95:
        name = "NAS-IPv6-Address";
        ret = value;

        break;
      case 96:
        name = "Framed-Interface-Id";
        ret = value;

        break;
      case 97:
        name = "Framed-IPv6-Prefix";
        ret = value;

        break;
      case 98:
        name = "Login-IPv6-Host";
        ret = value;

        break;
      case 99:
        name = "Framed-IPv6-Route";
        ret = new String(HexToByte(value));

        break;
      case 100:
        name = "Framed-IPv6-Pool";
        ret = new String(HexToByte(value));

        break;
      case 206:
        name = "Digest-Response";
        ret = new String(HexToByte(value));

        break;
      case 207:
        name = "Digest-Attributes";
        ret = value;

        break;
      default:
        name = "";
        ret = value;
      }
    }
    catch (Exception localException)
    {
    }
    if (ret == null)
      ret = value;
    writeLog(ip, ">> " + name + "(" + type + ")=" + ret);
    return ret;
  }

  private static String getServiceType(int value) {
    String ret = null;
    try {
      switch (value)
      {
      case 1:
        ret = "Login-User";

        break;
      case 2:
        ret = "Framed-User";

        break;
      case 3:
        ret = "Callback-Login-User";

        break;
      case 4:
        ret = "Callback-Framed-User";

        break;
      case 5:
        ret = "Outbound-User";

        break;
      case 6:
        ret = "Administrative-User";

        break;
      case 7:
        ret = "NAS-Prompt-User";

        break;
      case 8:
        ret = "Authenticate-Only";

        break;
      case 9:
        ret = "Callback-NAS-Prompt";

        break;
      case 10:
        ret = "Call-Check";

        break;
      case 11:
        ret = "Callback-Administrative";

        break;
      case 12:
        ret = "Voice";

        break;
      case 13:
        ret = "Fax";

        break;
      case 14:
        ret = "Modem-Relay";

        break;
      case 15:
        ret = "IAPP-Register";

        break;
      case 16:
        ret = "IAPP-AP-Check";

        break;
      default:
        ret = "";
      }

      ret = ret + "(" + value + ")";
    } catch (Exception localException) {
    }
    return ret;
  }

  private static String getFramedProtocol(int value) {
    String ret = null;
    try {
      switch (value)
      {
      case 1:
        ret = "PPP";

        break;
      case 2:
        ret = "SLIP";

        break;
      case 3:
        ret = "ARAP";

        break;
      case 4:
        ret = "Gandalf-SLML";

        break;
      case 5:
        ret = "Xylogics-IPX-SLIP";

        break;
      case 6:
        ret = "X.75-Synchronous";

        break;
      case 7:
        ret = "GPRS-PDP-Context";

        break;
      default:
        ret = "";
      }

      ret = ret + "(" + value + ")";
    } catch (Exception localException) {
    }
    return ret;
  }

  private static String getFramedRouting(int value) {
    String ret = null;
    try {
      switch (value)
      {
      case 0:
        ret = "None";

        break;
      case 1:
        ret = "Broadcast";

        break;
      case 2:
        ret = "Listen";

        break;
      case 3:
        ret = "Broadcast-Listen";

        break;
      default:
        ret = "";
      }

      ret = ret + "(" + value + ")";
    } catch (Exception localException) {
    }
    return ret;
  }

  private static String getFramedCompression(int value) {
    String ret = null;
    try {
      switch (value)
      {
      case 0:
        ret = "None";

        break;
      case 1:
        ret = "Van-Jacobson-TCP-IP";

        break;
      case 2:
        ret = "IPX-Header-Compression";

        break;
      case 3:
        ret = "Stac-LZS";

        break;
      default:
        ret = "";
      }

      ret = ret + "(" + value + ")";
    } catch (Exception localException) {
    }
    return ret;
  }

  private static String getLoginService(int value) {
    String ret = null;
    try {
      switch (value)
      {
      case 0:
        ret = "Telnet";

        break;
      case 1:
        ret = "Rlogin";

        break;
      case 2:
        ret = "TCP-Clear";

        break;
      case 3:
        ret = "PortMaster";

        break;
      case 4:
        ret = "LAT";

        break;
      case 5:
        ret = "X25-PAD";

        break;
      case 6:
        ret = "X25-T3POS";

        break;
      case 7:
        ret = "TCP-Clear-Quiet";

        break;
      default:
        ret = "";
      }

      ret = ret + "(" + value + ")";
    } catch (Exception localException) {
    }
    return ret;
  }

  private static String getAcctStatusType(int value) {
    String ret = null;
    try {
      switch (value)
      {
      case 1:
        ret = "Start";

        break;
      case 2:
        ret = "Stop";

        break;
      case 3:
        ret = "Interim-Update";

        break;
      case 4:
        ret = "Alive";

        break;
      case 7:
        ret = "Accounting-On";

        break;
      case 8:
        ret = "Accounting-Off";

        break;
      case 9:
        ret = "Tunnel-Start";

        break;
      case 10:
        ret = "Tunnel-Stop";

        break;
      case 11:
        ret = "Tunnel-Reject";

        break;
      case 12:
        ret = "Tunnel-Link-Start";

        break;
      case 13:
        ret = "Tunnel-Link-Stop";

        break;
      case 14:
        ret = "Tunnel-Link-Reject";

        break;
      case 15:
        ret = "Failed";

        break;
      case 5:
      case 6:
      default:
        ret = "";
      }

      ret = ret + "(" + value + ")";
    } catch (Exception localException) {
    }
    return ret;
  }

  private static String getAcctAuthentic(int value) {
    String ret = null;
    try {
      switch (value)
      {
      case 1:
        ret = "RADIUS";

        break;
      case 2:
        ret = "Local";

        break;
      case 3:
        ret = "Remote";

        break;
      case 4:
        ret = "Diameter";

        break;
      default:
        ret = "";
      }

      ret = ret + "(" + value + ")";
    } catch (Exception localException) {
    }
    return ret;
  }

  private static String getTerminationAction(int value) {
    String ret = null;
    try {
      switch (value)
      {
      case 0:
        ret = "Default";

        break;
      case 1:
        ret = "RADIUS-Request";

        break;
      default:
        ret = "";
      }

      ret = ret + "(" + value + ")";
    } catch (Exception localException) {
    }
    return ret;
  }

  private static String getNASPortType(int value) {
    String ret = null;
    try {
      switch (value)
      {
      case 0:
        ret = "Async";

        break;
      case 1:
        ret = "Sync";

        break;
      case 2:
        ret = "ISDN";

        break;
      case 3:
        ret = "ISDN-V120";

        break;
      case 4:
        ret = "ISDN-V110";

        break;
      case 5:
        ret = "Virtual";

        break;
      case 6:
        ret = "PIAFS";

        break;
      case 7:
        ret = "HDLC-Clear-Channel";

        break;
      case 8:
        ret = "X.25";

        break;
      case 9:
        ret = "X.75";

        break;
      case 10:
        ret = "G.3-Fax";

        break;
      case 11:
        ret = "SDSL";

        break;
      case 12:
        ret = "ADSL-CAP";

        break;
      case 13:
        ret = "ADSL-DMT";

        break;
      case 14:
        ret = "IDSL";

        break;
      case 15:
        ret = "Ethernet";

        break;
      case 16:
        ret = "xDSL";

        break;
      case 17:
        ret = "Cable";

        break;
      case 18:
        ret = "Wireless-Other";

        break;
      case 19:
        ret = "Wireless-802.11";

        break;
      case 20:
        ret = "Token-Ring";

        break;
      case 21:
        ret = "FDDI";

        break;
      case 22:
        ret = "Wireless-CDMA2000";

        break;
      case 23:
        ret = "Wireless-UMTS";

        break;
      case 24:
        ret = "Wireless-1X-EV";

        break;
      case 25:
        ret = "IAPP";

        break;
      default:
        ret = "";
      }

      ret = ret + "(" + value + ")";
    } catch (Exception localException) {
    }
    return ret;
  }

  private static String getAcctTerminateCause(int value) {
    String ret = null;
    try {
      switch (value)
      {
      case 1:
        ret = "User-Request";

        break;
      case 2:
        ret = "Lost-Carrier";

        break;
      case 3:
        ret = "Lost-Service";

        break;
      case 4:
        ret = "Idle-Timeout";

        break;
      case 5:
        ret = "Session-Timeout";

        break;
      case 6:
        ret = "Admin-Reset";

        break;
      case 7:
        ret = "Admin-Reboot";

        break;
      case 8:
        ret = "Port-Error";

        break;
      case 9:
        ret = "NAS-Error";

        break;
      case 10:
        ret = "NAS-Request";

        break;
      case 11:
        ret = "NAS-Reboot";

        break;
      case 12:
        ret = "Port-Unneeded";

        break;
      case 13:
        ret = "Port-Preempted";

        break;
      case 14:
        ret = "Port-Suspended";

        break;
      case 15:
        ret = "Service-Unavailable";

        break;
      case 16:
        ret = "Callback";

        break;
      case 17:
        ret = "User-Error";

        break;
      case 18:
        ret = "Host-Request";

        break;
      case 19:
        ret = "Supplicant-Restart";

        break;
      case 20:
        ret = "Reauthentication-Failure";

        break;
      case 21:
        ret = "Port-Reinit";

        break;
      case 22:
        ret = "Port-Disabled";

        break;
      default:
        ret = "";
      }

      ret = ret + "(" + value + ")";
    } catch (Exception localException) {
    }
    return ret;
  }

  private static String getPrompt(int value) {
    String ret = null;
    try {
      switch (value)
      {
      case 0:
        ret = "No-Echo";

        break;
      case 1:
        ret = "Echo";

        break;
      default:
        ret = "";
      }

      ret = ret + "(" + value + ")";
    } catch (Exception localException) {
    }
    return ret;
  }

  public static String getAttributeString(int type, String value) {
    String ret = null;
    try {
      String info = ByteToHex(value.getBytes());
      String len = Integer.toHexString(2 + info.length() / 2);
      while (len.length() < 2)
        len = "0" + len;
      String types = Integer.toHexString(type);
      while (types.length() < 2)
        types = "0" + types;
      ret = types + len + info;
    } catch (Exception localException) {
    }
    return ret;
  }

  public static int getAttributeStringLen(String value) {
    int len = 0;
    try {
      String info = ByteToHex(value.getBytes());
      len = 2 + info.length() / 2;
    } catch (Exception localException) {
    }
    return len;
  }

  public static String getAttributeVendor(int type, int value, int valueLen)
  {
    String ret = null;
    try
    {
      byte[] b = new byte[4];
      for (int i = 0; i < 4; i++) {
        int offset = (b.length - 1 - i) * 8;
        b[i] = ((byte)(value >>> offset & 0xFF));
      }
      String info = ByteToHex(b);
      String len = Integer.toHexString(6 + valueLen);
      while (len.length() < 2)
        len = "0" + len;
      String types = Integer.toHexString(type);
      while (types.length() < 2)
        types = "0" + types;
      ret = types + len + info;
    }
    catch (Exception localException)
    {
    }
    return ret;
  }

  public static String getAttributeVendor(int type, int value) {
    String ret = null;
    try
    {
      byte[] b = new byte[4];
      for (int i = 0; i < 4; i++) {
        int offset = (b.length - 1 - i) * 8;
        b[i] = ((byte)(value >>> offset & 0xFF));
      }
      String info = ByteToHex(b);
      String len = Integer.toHexString(12);
      while (len.length() < 2)
        len = "0" + len;
      String types = Integer.toHexString(type);
      while (types.length() < 2)
        types = "0" + types;
      ret = types + len + info;
    }
    catch (Exception localException)
    {
    }
    return ret;
  }

  public static String getAttributeSpeed(int type, int value) {
    String ret = null;
    try
    {
      byte[] b = new byte[4];
      for (int i = 0; i < 4; i++) {
        int offset = (b.length - 1 - i) * 8;
        b[i] = ((byte)(value >>> offset & 0xFF));
      }
      String info = ByteToHex(b);
      String len = Integer.toHexString(6);
      while (len.length() < 2)
        len = "0" + len;
      String types = Integer.toHexString(type);
      while (types.length() < 2)
        types = "0" + types;
      ret = types + len + info;
    }
    catch (Exception localException)
    {
    }
    return ret;
  }

  public static String getAttributeInt(int type, int value)
  {
    String ret = null;
    try
    {
      byte[] b = new byte[4];
      for (int i = 0; i < 4; i++) {
        int offset = (b.length - 1 - i) * 8;
        b[i] = ((byte)(value >>> offset & 0xFF));
      }
      String info = ByteToHex(b);
      String len = Integer.toHexString(6);
      while (len.length() < 2)
        len = "0" + len;
      String types = Integer.toHexString(type);
      while (types.length() < 2)
        types = "0" + types;
      ret = types + len + info;
    }
    catch (Exception localException)
    {
    }
    return ret;
  }

  public static byte[] getOutData(String name, String sharedSecret, String ip, int port, int code, int identifier, String authenticator, String attributes)
  {
    byte[] ret = null;
    try {
      int length = 20 + attributes.length() / 2;
      String str = Integer.toHexString(code);
      while (str.length() < 2)
        str = "0" + str;
      String temp = Integer.toHexString(identifier);
      while (temp.length() < 2)
        temp = "0" + temp;
      str = str + temp;
      temp = Integer.toHexString(length);
      while (temp.length() < 4)
        temp = "0" + temp;
      str = str + temp;
      authenticator = encodeMD5(str + authenticator + attributes + 
        sharedSecret);
      str = str + authenticator;
      str = str + attributes;
      ret = HexToByte(str);
      writeLog(name, "ip=" + ip + ",port=" + port + ",code=" + code + 
        ",identifier=" + identifier + ",length=" + length + 
        ",authenticator=" + authenticator + ",attributes=" + 
        attributes);
    } catch (Exception localException) {
    }
    return ret;
  }

  public static String sendServer(String ip, String url, String str) {
    String ret = null;
    try {
      str = url + "?" + str;
      String result = sendURL(str);
      writeLog(ip, str);
      int i = result.indexOf("<result>");
      if (i >= 0) {
        i += 8;
        int j = result.indexOf("</result>", i);
        if (j > i) {
          ret = result.substring(i, j).trim();
        }

      }

      writeLog(ip, "result=" + ret);
    } catch (Exception localException) {
    }
    return ret;
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.radius.core.Tool
 * JD-Core Version:    0.6.2
 */