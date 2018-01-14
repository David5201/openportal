package api;

import com.leeson.common.utils.stringUtils;
import com.leeson.core.bean.Portalaccount;
import com.leeson.core.bean.Portalaccountgroup;
import com.leeson.core.query.PortalaccountQuery;
import com.leeson.core.service.ConfigService;
import com.leeson.core.service.PortalaccountService;
import com.leeson.core.service.PortalaccountgroupService;
import com.leeson.core.service.PortalaccountmacsService;
import com.leeson.core.service.PortalconfigService;
import com.leeson.core.utils.Kick;
import com.leeson.portal.core.model.OnlineMap;
import com.leeson.portal.core.utils.SpringContextHelper;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

public class ServiceSocket extends Thread
{
  PortalaccountService portalaccountService = (PortalaccountService)SpringContextHelper.getBean("portalaccountServiceImpl");

  PortalaccountmacsService macsService = (PortalaccountmacsService)SpringContextHelper.getBean("portalaccountmacsServiceImpl");

  PortalconfigService portalconfigService = (PortalconfigService)SpringContextHelper.getBean("portalconfigServiceImpl");

  ConfigService configService = (ConfigService)SpringContextHelper.getBean("configServiceImpl");

  PortalaccountgroupService portalaccountgroupService = (PortalaccountgroupService)SpringContextHelper.getBean("portalaccountgroupServiceImpl");

  private static OnlineMap onlineMap = OnlineMap.getInstance();
  private static DecimalFormat df = new DecimalFormat(".##");
  private static ServerSocket ss = null;
  public Socket sock;

  private String checkIn(Portalaccount e, Integer macLimit, Integer count)
  {
    try
    {
      if ((stringUtils.isBlank(e.getLoginName())) || 
        (stringUtils.isBlank(e.getPassword()))) {
        return "error";
      }

      int accountState = Integer.valueOf(e.getState()).intValue();
      if ((accountState < 0) || (accountState > 3)) {
        return "error";
      }

      Long userTime = this.portalaccountgroupService.getPortalaccountgroupByKey(Long.valueOf(1L)).getTime();
      if ((accountState == 2) && 
        (e.getTime() == null)) {
        e.setTime(userTime);
      }

      if ((accountState == 3) && 
        (e.getDate() == null)) {
        return "error";
      }

      if (e.getDate() == null) {
        e.setDate(new Date());
      }

      if (count == null) {
        count = Integer.valueOf(1);
      }
      if (macLimit == null) {
        macLimit = Integer.valueOf(0);
      }
      if (stringUtils.isBlank(e.getGender())) {
        e.setGender(null);
      }

      PortalaccountQuery aq = new PortalaccountQuery();
      aq.setLoginName(e.getLoginName());
      aq.setLoginNameLike(false);
      List accountList = this.portalaccountService
        .getPortalaccountList(aq);
      if (accountList.size() > 0)
      {
        e.setId(((Portalaccount)accountList.get(0)).getId());
        e.setMaclimit(macLimit);
        e.setMaclimitcount(count);
        e.setSpeed(Long.valueOf(1L));
        e.setAutologin(Integer.valueOf(0));
        this.portalaccountService.updatePortalaccountByKey(e);
        return "ok";
      }

      e.setMaclimit(macLimit);
      e.setMaclimitcount(count);
      e.setSpeed(Long.valueOf(1L));
      e.setAutologin(Integer.valueOf(0));
      this.portalaccountService.addPortalaccount(e);

      return "ok";
    }
    catch (Exception ex)
    {
      System.err.println("CheckIN ERROR " + ex);
      try {
        return "error";
      }
      catch (Exception exc) {
        return "error";
      }
    }
  }

  private String checkOut(String loginName)
  {
    try
    {
      if (stringUtils.isBlank(loginName)) {
        return "error";
      }

      PortalaccountQuery aq = new PortalaccountQuery();
      aq.setLoginName(loginName);
      aq.setLoginNameLike(false);
      List accountList = this.portalaccountService
        .getPortalaccountList(aq);
      if (accountList.size() > 0) {
        Portalaccount acc = (Portalaccount)accountList.get(0);
        Long id = acc.getId();
        Set<Map.Entry<String, String[]>> entries = onlineMap.getOnlineUserMap().entrySet();
        for (Map.Entry entry : entries) {
          String[] info = (String[])entry.getValue();
          String ip = (String)entry.getKey();
          if ((stringUtils.isNotBlank(info[1])) && 
            (Long.valueOf(info[1]) == id)) {
            Kick.kickUserDeleteUser(ip);
          }
        }

        int accountState = Integer.valueOf(acc.getState()).intValue();
        acc = this.portalaccountService.getPortalaccountByKey(id);
        acc.setState("0");
        this.portalaccountService.updatePortalaccountByKey(acc);

        Long costTime = Long.valueOf(0L);
        if (accountState == 2) {
          Long userTime = this.portalaccountgroupService.getPortalaccountgroupByKey(Long.valueOf(1L)).getTime();
          costTime = Long.valueOf(userTime.longValue() - acc.getTime().longValue());
        }
        return String.valueOf(costTime);
      }

      return "error";
    }
    catch (Exception e)
    {
      System.err.println("CheckOUT ERROR " + e);
      try {
        return "error";
      }
      catch (Exception exc) {
        return "error";
      }
    }
  }

  public String doMethod(String inS)
  {
    try {
      if (inS.startsWith("%ci")) {
        return CheckIN(inS);
      }
      if (inS.startsWith("%co"))
        return CheckOUT(inS);
    }
    catch (Exception e) {
      return "error";
    }
    return "error";
  }

  private String CheckIN(String inS) {
    String result = "error";
    try
    {
      String[] ts = inS.split("-");
      String room_name = ts[1];
      String user_name = ts[2];

      String loginName = room_name + user_name;
      String name = loginName;
      String password = ts[3];
      String description = ts[4] + " Check IN";
      String state = "2";

      Portalaccount acc = new Portalaccount();
      acc.setLoginName(loginName);
      acc.setName(name);
      acc.setPassword(password);
      acc.setDescription(description);
      acc.setState(state);

      if ("error".equals(checkIn(acc, null, null))) {
        return result;
      }
      result = "#is-" + room_name + "-" + loginName + "-$";
    } catch (Exception e) {
      System.err.println("CheckIN Error " + e);
      return result;
    }
    return result;
  }

  private String CheckOUT(String inS) {
    String result = "error";
    try
    {
      String[] ts = inS.split("-");
      String room_name = ts[1];
      String loginName = ts[2];

      String timeS = checkOut(loginName);
      if ("error".equals(timeS)) {
        return result;
      }
      Long timeM = Long.valueOf(Long.valueOf(timeS).longValue() / 60000L);
      double money = Double.valueOf((String)APIMap.getInstance().getApiMap().get("money")).doubleValue() / 60.0D;
      double costMoney = timeM.longValue() * money;
      String cost = df.format(costMoney);
      if (cost.startsWith(".")) {
        cost = "0" + cost;
      }
      result = "#cc-" + room_name + "-" + loginName + "-" + cost + "-$";
    } catch (Exception e) {
      System.err.println("CheckOUT Error " + e);
      return result;
    }
    return result;
  }

  public ServiceSocket(Socket sock)
  {
    this.sock = sock;
  }

  public void run() {
    try {
      String ip = this.sock.getRemoteSocketAddress().toString();

      OutputStream os = this.sock.getOutputStream();
      InputStream is = this.sock.getInputStream();
      byte[] buf = new byte[1024];
      int len = is.read(buf);
      String inS = new String(buf, 0, len).trim();
      inS = inS.toLowerCase();
      System.out.println("API Service Receive From IP " + ip + " Data [" + inS + "]");
      String outS = doMethod(inS);

      System.out.println("API Service Response To IP " + ip + " Data [" + outS + "]");
      os.write(outS.getBytes());
      os.close();
      is.close();
      this.sock.close();
    } catch (Exception e) {
      System.err.println("Do API Service Method Error: " + e);
    }
  }

  public static void openServer(String path)
  {
    int port = 6000;
    String money = "2.0";
    try {
      System.out.println("Read api.properties File ....");
      Properties props = new Properties();
      String propPath = path + "api.properties";

      FileInputStream in = new FileInputStream(propPath);
      props.load(in);
      port = Integer.valueOf(props.getProperty("port")).intValue();
      money = props.getProperty("money");
      in.close();
    } catch (Exception e) {
      port = 6000;
      money = "2.0";
    }
    APIMap.getInstance().getApiMap().put("money", money);
    System.out.println("API ServerSocket TCP PORT : " + port);
    System.out.println("Every Hour : " + money);
    try {
      ss = new ServerSocket(port);
      while (true) {
        Socket s = ss.accept();
        new ServiceSocket(s).start();
      }
    } catch (Exception e) {
      System.err.println("Open API Service Error: " + e);
    }
  }

  public static void closeServer()
  {
    try
    {
      if (ss != null) {
        ss.close();
        ss = null;
      }
    }
    catch (Exception localException)
    {
    }
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     api.ServiceSocket
 * JD-Core Version:    0.6.2
 */