package com.leeson.core.utils;

import com.leeson.common.utils.stringUtils;
import com.leeson.core.bean.Portalaccount;
import com.leeson.core.bean.Portalaccountmacs;
import com.leeson.core.bean.Portalsmslogs;
import com.leeson.core.query.PortalaccountQuery;
import com.leeson.core.query.PortalaccountmacsQuery;
import com.leeson.core.service.PortalaccountService;
import com.leeson.core.service.PortalaccountmacsService;
import com.leeson.core.service.PortalsmslogsService;
import com.leeson.portal.core.model.PhoneCodeMap;
import com.leeson.portal.core.model.PhoneUserMap;
import com.leeson.portal.core.utils.SpringContextHelper;
import java.io.FileInputStream;
import java.io.PrintStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;

public class SMSProRequest
{
  private static PortalaccountmacsService macsService = (PortalaccountmacsService)
    SpringContextHelper.getBean("portalaccountmacsServiceImpl");

  private static PortalaccountService accService = (PortalaccountService)
    SpringContextHelper.getBean("portalaccountServiceImpl");

  private static PortalsmslogsService smslogsService = (PortalsmslogsService)
    SpringContextHelper.getBean("portalsmslogsServiceImpl");

  private static String userName = "";

  private static String pwd = "";

  private static String appServiceId = "";
  private static final String messageServiceId = "133df0de-b11e-445a-b551-c5416f61b80e";
  private static final String userServiceId = "50549c60-f2d5-4cfb-8bdc-249f26664c5e";

  public static boolean send(String url, String phone, String mac, String text, HttpServletRequest request, Long id)
  {
    try
    {
      init(request);

      String yzm = String.valueOf(Math.round(Math.random() * 8999.0D + 1000.0D));

      if (stringUtils.isNotBlank(text)) {
        if (text.contains("[yzm]"))
          text = text.replace("[yzm]", yzm);
        else
          text = yzm;
      }
      else {
        text = yzm;
      }
      String messageXml = "<Message><content>" + text + "<ypes><userMobiles>" + phone + "<essage>";

      JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
      Client client = dcf.createClient(url);

      String result = (String)client.invoke("invokeWithUserName", new Object[] { userName, pwd, appServiceId, "133df0de-b11e-445a-b551-c5416f61b80e", messageXml })[0];

      result = result.replace("[", "");
      result = result.replace("]", "");

      JSONObject jsonObj = JSONObject.fromObject(result);
      JSONObject data = jsonObj.getJSONObject("statusMap");
      String state = data.getString("sms");

      if ("success".equals(state)) {
        Object[] objs = new Object[2];
        objs[0] = yzm;
        objs[1] = new Date();
        PhoneCodeMap.getInstance().getPhoneCodeMap().put(phone, objs);

        Portalsmslogs smslogs = new Portalsmslogs();
        smslogs.setInfo(text);
        smslogs.setPhone(phone);
        smslogs.setSendDate(new Date());
        smslogs.setSid(id);
        smslogs.setType("1");
        smslogsService.addPortalsmslogs(smslogs);

        String mobile = phone;
        JaxWsDynamicClientFactory dcf1 = JaxWsDynamicClientFactory.newInstance();
        Client client1 = dcf1.createClient(url);
        result = (String)client1.invoke("invokeWithUserName", new Object[] { userName, pwd, appServiceId, "50549c60-f2d5-4cfb-8bdc-249f26664c5e", mobile })[0];

        System.out.println(result);
        if (result.contains("true")) {
          PhoneUserMap.getInstance().getPhoneUserMap().put(phone, phone);
          if (stringUtils.isNotBlank(mac)) {
            addMac(mac);
          }
        }
        return true;
      }
      return false;
    }
    catch (Exception e)
    {
      System.out.println("error" + e);
    }return false;
  }

  private static void init(HttpServletRequest request)
  {
    try
    {
      String descDir = request.getServletContext().getRealPath("/");
      Properties props = new Properties();
      String propPath = descDir + 
        "sms.properties";
      FileInputStream in = new FileInputStream(propPath);
      props.load(in);
      userName = props.getProperty("username");
      pwd = props.getProperty("password");
      appServiceId = props.getProperty("appid");
      in.close();
    }
    catch (Exception localException)
    {
    }
  }

  private static void addMac(String userMac) {
    boolean macHave = false;
    List<Portalaccountmacs> macs = macsService
      .getPortalaccountmacsList(new PortalaccountmacsQuery());
    for (Portalaccountmacs mac : macs) {
      if (mac.getMac().equals(userMac)) {
        macHave = true;
        break;
      }
    }
    if (!macHave) {
      List accs = accService.getPortalaccountList(new PortalaccountQuery());
      if ((accs.size() > 0) && 
        (((Portalaccount)accs.get(0)).getAutologin().intValue() == 1)) {
        Portalaccountmacs accMac = new Portalaccountmacs();
        accMac.setAccountId(((Portalaccount)accs.get(0)).getId());
        accMac.setMac(userMac);
        macsService.addPortalaccountmacs(accMac);
      }
    }
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.utils.SMSProRequest
 * JD-Core Version:    0.6.2
 */