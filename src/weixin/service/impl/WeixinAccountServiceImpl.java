package weixin.service.impl;

import java.io.FileInputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import weixin.entity.WeixinAccount;
import weixin.service.WeixinAccountService;

@Service
public class WeixinAccountServiceImpl
  implements WeixinAccountService
{
  private static List<WeixinAccount> list = new ArrayList();

  public List<WeixinAccount> getList(HttpServletRequest request) {
    if (list.size() == 0) {
      String appid = "null";
      String appsecret = "null";
      String token = "null";
      String AESKey = "null";
      try {
        System.out.println("Read wx.properties File ....");
        String path = request.getServletContext().getRealPath("/");
        Properties props = new Properties();
        String propPath = path + "wx.properties";

        FileInputStream in = new FileInputStream(propPath);
        props.load(in);
        appid = props.getProperty("appid");
        appsecret = props.getProperty("appsecret");
        token = props.getProperty("token");
        AESKey = props.getProperty("AESKey");
        in.close();
      } catch (Exception localException) {
      }
      WeixinAccount account = new WeixinAccount();
      account.setAppid(appid);
      account.setAppsecret(appsecret);
      account.setAccounttoken(token);
      account.setEncodingAESKey(AESKey);
      list.add(account);
    }

    return list;
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     weixin.service.impl.WeixinAccountServiceImpl
 * JD-Core Version:    0.6.2
 */