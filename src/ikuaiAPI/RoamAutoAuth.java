package ikuaiAPI;

import AC.Utils.WR;
import com.leeson.common.utils.ThreadLocalDateUtil;
import com.leeson.common.utils.stringUtils;
import com.leeson.core.bean.Portalaccount;
import com.leeson.core.bean.Portalautologin;
import com.leeson.core.bean.Portalbas;
import com.leeson.core.bean.Portalonlinelimit;
import com.leeson.core.query.PortalaccountQuery;
import com.leeson.core.service.PortalaccountService;
import com.leeson.core.service.PortalautologinService;
import com.leeson.core.service.PortalonlinelimitService;
import com.leeson.portal.core.model.AutoLoginMacMap;
import com.leeson.portal.core.model.AutoLoginMap;
import com.leeson.portal.core.model.Config;
import com.leeson.portal.core.model.MacLimitMap;
import com.leeson.portal.core.model.UserLimitMap;
import com.leeson.portal.core.model.isDo;
import com.leeson.portal.core.utils.SpringContextHelper;
import ikuaiAPI.utils.DES_CBC;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

public class RoamAutoAuth extends Thread
{
  private static Config config = Config.getInstance();
  private static Logger log = Logger.getLogger(RoamAutoAuth.class);

  PortalaccountService accountService = (PortalaccountService)SpringContextHelper.getBean("portalaccountServiceImpl");

  PortalonlinelimitService onlinelimitService = (PortalonlinelimitService)SpringContextHelper.getBean("portalonlinelimitServiceImpl");

  PortalautologinService autologinService = (PortalautologinService)SpringContextHelper.getBean("portalautologinServiceImpl");

  private static Boolean isRun = Boolean.valueOf(true);
  private static DatagramSocket socket = null;

  DatagramPacket data = null;

  public RoamAutoAuth(DatagramPacket data)
  {
    this.data = data;
  }

  public void run() {
    try {
      byte[] Req_Data_Base = new byte[this.data.getLength()];
      for (int l = 0; l < Req_Data_Base.length; l++) {
        Req_Data_Base[l] = this.data.getData()[l];
      }

      String ip = this.data.getAddress().getHostAddress();
      int port = this.data.getPort();

      if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
        log.info("Rececie ikuai RoamAutoAuth Packet : SourceIP=" + ip + 
          " SourcePort=" + port + " Size=" + this.data.getLength() + 
          " Data=" + WR.Getbyte2HexString(Req_Data_Base));
      }

      byte[] Ver = new byte[1];
      byte[] Type = new byte[1];
      byte[] Mod = new byte[1];
      byte[] AttrNum = new byte[1];

      Ver[0] = Req_Data_Base[0];
      Type[0] = Req_Data_Base[1];
      Mod[0] = Req_Data_Base[2];
      AttrNum[0] = Req_Data_Base[3];

      String usermac = null;
      String userip = null;
      int pos = 4;

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
          if (type == 3)
            usermac = new String(buf);
          else if (type == 4) {
            userip = new String(buf);
          }
          num++;
        }
      }

      String isDes = "no";
      if ((Mod[0] & 0xFF) == 1) {
        isDes = "yes";
      }

      if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
        log.info("ikuai RoamAutoAuth From Packet Info : userMac=" + usermac + 
          " userIP=" + userip + " isDes=" + isDes);
      }

      String iv = "roamauth";
      String key = "OpenPortal";
      String usrStr = "";
      String pwdStr = "";

      boolean isRoamAutoAuth = false;
      if (stringUtils.isNotBlank(usermac)) {
        String[] infos = checkMac(usermac);
        if ((infos != null) && (infos.length == 2)) {
          usrStr = infos[0];
          pwdStr = infos[1];
          isRoamAutoAuth = true;
        }
      }

      if (isRoamAutoAuth) {
        byte[] usr = usrStr.getBytes();
        byte[] pwd = pwdStr.getBytes();
        Req_Data_Base[1] = 2;
        Req_Data_Base[3] = 4;

        if ((Mod[0] & 0xFF) == 1) {
          try {
            pwd = DES_CBC.encrypt(pwdStr, key, iv);
            pwdStr = WR.Getbyte2HexString(pwd);
          } catch (Exception e) {
            if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
              log.error("==============ERROR Start=============");
              log.error(e);
              log.error("ERROR INFO ", e);
              log.error("==============ERROR End=============");
            }
            isDes = "no";
            Req_Data_Base[2] = 0;
          }
        }

        if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
          log.info("ikuai RoamAutoAuth To Packet Info : userMac=" + usermac + 
            " userIP=" + userip + " usr=" + usrStr + " pwd=" + pwdStr + " isDes=" + isDes);
        }

        byte[] Ack_Data = new byte[Req_Data_Base.length + usr.length + 
          pwd.length + 4];
        for (int l = 0; l < Req_Data_Base.length; l++) {
          Ack_Data[l] = Req_Data_Base[l];
        }

        pos = Req_Data_Base.length;
        Ack_Data[pos] = 1;
        Ack_Data[(pos + 1)] = ((byte)(usr.length + 2));
        for (int i = 0; i < usr.length; i++) {
          Ack_Data[(pos + 2 + i)] = usr[i];
        }

        pos = Req_Data_Base.length + 2 + usr.length;
        Ack_Data[pos] = 2;
        Ack_Data[(pos + 1)] = ((byte)(pwd.length + 2));
        for (int i = 0; i < pwd.length; i++) {
          Ack_Data[(pos + 2 + i)] = pwd[i];
        }

        try
        {
          DatagramPacket data = new DatagramPacket(Ack_Data, Ack_Data.length, 
            InetAddress.getByName(ip), port);
          socket.send(data);
        } catch (Exception e) {
          if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
            log.error("==============ERROR Start=============");
            log.error(e);
            log.error("ERROR INFO ", e);
            log.error("==============ERROR End=============");
          }

        }

        if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
          log.info("ACK ikuai RoamAutoAuth Packet : ToIP=" + ip + 
            " ToPort=" + port + " Size=" + Ack_Data.length + 
            " Data=" + WR.Getbyte2HexString(Ack_Data));
        }
      }
      else if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
        log.info("ikuai RoamAutoAuth : userMac=" + usermac + 
          " userIP=" + userip + " Can not RoamAutoAuth !!!!!!!!!!!!!!");
      }
    }
    catch (Exception e) {
      log.error("==============ERROR Start=============");
      log.error(e);
      log.error("ERROR INFO ", e);
      log.error("==============ERROR End=============");
    }
  }

  public static void openServer() {
    try {
      socket = new DatagramSocket(50200);
      if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
        log.info("ikuai RoamAutoAuth Service Start OK , Listen Portal UDP 50200 !!");
      }

      while (isRun.booleanValue()) {
        byte[] b = new byte[100];
        DatagramPacket data = new DatagramPacket(b, b.length);
        socket.receive(data);
        new RoamAutoAuth(data).start();
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
    try {
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

  private boolean checkTimeOut(String userState, Long userId, Date userDate, Long userTime)
  {
    Portalaccount account = this.accountService.getPortalaccountByKey(userId);

    if (userState.equals(String.valueOf(0))) {
      return false;
    }

    if (userState.equals(String.valueOf(1))) {
      return true;
    }

    if (userState.equals(String.valueOf(3))) {
      Date now = new Date();
      if (userDate.getTime() > now.getTime()) {
        return true;
      }
      account.setState(String.valueOf(2));
      this.accountService.updatePortalaccountByKey(account);
      userState = "2";
    }

    if (userState.equals("2")) {
      if (userTime.longValue() > 0L) {
        return true;
      }
      account.setState(String.valueOf(0));
      this.accountService.updatePortalaccountByKey(account);
      return false;
    }

    return false;
  }

  private int CheckMacTimeLimitMethod(String param, Long id)
  {
    Portalonlinelimit onlinelimit = this.onlinelimitService
      .getPortalonlinelimitByKey(id);
    if (onlinelimit.getState().intValue() == 1) {
      if (onlinelimit.getType().intValue() == 0) {
        if ((stringUtils.isNotBlank(param)) && (!"error".equals(param))) {
          String[] TimeInfo = 
            (String[])MacLimitMap.getInstance()
            .getMacLimitMap().get(param);
          if (TimeInfo != null) {
            Long timepermit = onlinelimit.getTime();
            if (Long.valueOf(TimeInfo[1]).longValue() >= timepermit.longValue())
              return 1;
          }
        }
        else {
          return 2;
        }
      }
      else if (stringUtils.isNotBlank(param)) {
        String[] TimeInfo = 
          (String[])UserLimitMap.getInstance()
          .getUserLimitMap().get(param);
        if (TimeInfo != null) {
          Long timepermit = onlinelimit.getTime();
          if (Long.valueOf(TimeInfo[1]).longValue() >= timepermit.longValue())
            return 1;
        }
      }
      else {
        return 2;
      }
    }

    return 0;
  }

  public static boolean Do() {
    Long isThis = Long.valueOf(new Date().getTime());
    boolean Do = false;
    if (isThis.longValue() < isDo.getInstance().getId().longValue()) {
      Do = true;
    }
    return Do;
  }

  public String[] checkMac(String ikmac) {
    if ((Do()) && 
      (stringUtils.isNotBlank(ikmac))) {
      String[] macTimeInfo = 
        (String[])AutoLoginMacMap.getInstance()
        .getAutoLoginMacMap().get(ikmac);
      if (macTimeInfo != null) {
        String[] userinfo = 
          (String[])AutoLoginMap.getInstance()
          .getAutoLoginMap().get(ikmac);
        try {
          Long id = Long.valueOf(macTimeInfo[2]);
          if (1 != CheckMacTimeLimitMethod(ikmac, id)) {
            String authUser = userinfo[0];
            String authPwd = userinfo[1];
            String username = userinfo[2];
            Portalautologin autologin = this.autologinService
              .getPortalautologinByKey(id);
            if ((autologin != null) && 
              (autologin.getState().intValue() == 1)) {
              Long timepermit = autologin.getTime();
              String loginTimeString = macTimeInfo[0];
              Long oldcostTime = 
                Long.valueOf(macTimeInfo[1]);
              Long costTime = oldcostTime;
              if (stringUtils.isNotBlank(loginTimeString)) {
                Date loginTime = 
                  ThreadLocalDateUtil.parse(loginTimeString);
                String nowString = 
                  ThreadLocalDateUtil.format(new Date());
                Date nowTime = ThreadLocalDateUtil.parse(nowString);
                costTime = Long.valueOf(nowTime.getTime() - 
                  loginTime.getTime() + 
                  oldcostTime.longValue());
              }
              if ((costTime.longValue() < timepermit.longValue()) || 
                (timepermit.longValue() <= 0L)) {
                Long userId = null;
                String userState = null;
                boolean CheckAccount = false;
                if (3L == id.longValue())
                {
                  PortalaccountQuery accq = new PortalaccountQuery();
                  accq.setLoginName(username);
                  List accs = this.accountService
                    .getPortalaccountList(accq);
                  if ((accs != null) && 
                    (accs.size() == 1)) {
                    Portalaccount acc = (Portalaccount)accs.get(0);
                    if (acc != null) {
                      userId = acc.getId();
                      Date userDate = acc
                        .getDate();
                      Long userTime = acc
                        .getTime();
                      userState = acc.getState();

                      if (checkTimeOut(userState, 
                        userId, userDate, 
                        userTime))
                        CheckAccount = true;
                    }
                  }
                }
                else {
                  CheckAccount = true;
                }

                if (CheckAccount) {
                  String[] infos = new String[2];
                  infos[0] = authUser;
                  infos[1] = authPwd;
                  return infos;
                }
                AutoLoginMacMap.getInstance()
                  .getAutoLoginMacMap()
                  .remove(ikmac);
                AutoLoginMap.getInstance()
                  .getAutoLoginMap()
                  .remove(ikmac);
              }
              else
              {
                AutoLoginMacMap.getInstance()
                  .getAutoLoginMacMap()
                  .remove(ikmac);
                AutoLoginMap.getInstance()
                  .getAutoLoginMap()
                  .remove(ikmac);
              }
            }
          }
        }
        catch (Exception localException)
        {
        }
      }
    }
    return null;
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     ikuaiAPI.RoamAutoAuth
 * JD-Core Version:    0.6.2
 */