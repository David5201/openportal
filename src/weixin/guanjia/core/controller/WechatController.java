package weixin.guanjia.core.controller;

import com.leeson.core.bean.Portalbas;
import com.leeson.portal.core.model.Config;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.jeewx.api.core.exception.WexinReqException;
import org.jeewx.api.wxbase.wxserviceip.JwServiceIpAPI;
import org.jeewx.api.wxuser.user.JwUserAPI;
import org.jeewx.api.wxuser.user.model.Wxuser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import weixin.entity.WeixinAccount;
import weixin.guanjia.core.entity.common.AccessToken;
import weixin.guanjia.core.entity.common.Button;
import weixin.guanjia.core.entity.common.CommonButton;
import weixin.guanjia.core.entity.common.Menu;
import weixin.guanjia.core.util.SignUtil;
import weixin.guanjia.core.util.WeixinUtil;
import weixin.service.WeixinAccessTokenService;
import weixin.service.WeixinAccountService;
import weixin.service.WeixinChatService;

@Controller
public class WechatController
{

  @Autowired
  private WeixinChatService wechatService;

  @Autowired
  private WeixinAccountService weixinAccountService;

  @Autowired
  private WeixinAccessTokenService weixinAccessTokenService;
  private static Config config = Config.getInstance();
  private static Logger log = Logger.getLogger(WechatController.class);

  @RequestMapping(value={"/weixin"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  public void wechatGet(HttpServletRequest request, HttpServletResponse response, @RequestParam("signature") String signature, @RequestParam("timestamp") String timestamp, @RequestParam("nonce") String nonce, @RequestParam("echostr") String echostr)
  {
    Portalbas basConfig = (Portalbas)config.getConfigMap().get("");

    if (basConfig.getIsdebug().equals("1")) {
      log.info("signature: " + signature);
      log.info("timestamp: " + timestamp);
      log.info("nonce: " + nonce);
      log.info("echostr: " + echostr);
    }
    List<WeixinAccount> weixinAccounts = this.weixinAccountService.getList(request);
    for (WeixinAccount account : weixinAccounts)
    {
      if (SignUtil.checkSignature(account.getAccounttoken(), signature, 
        timestamp, nonce))
        try {
          if (basConfig.getIsdebug().equals("1")) {
            log.info("WeiXin GZH Check Success !!");
          }
          response.getWriter().print(echostr);
        }
        catch (IOException e)
        {
          e.printStackTrace();
        }
    }
  }

  @RequestMapping(value={"/weixin"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public void wechatPost(HttpServletResponse response, HttpServletRequest request)
    throws IOException
  {
    Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
    String respMessage = this.wechatService.coreService(request);
    if (basConfig.getIsdebug().equals("1")) {
      log.info("Post request: " + request);
      log.info("response: " + respMessage);
    }
    PrintWriter out = response.getWriter();
    out.print(respMessage);
    out.close();
  }

  @RequestMapping(value={"/menu"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  public void Menu(HttpServletRequest req, HttpServletResponse response)
  {
    Portalbas basConfig = (Portalbas)config.getConfigMap().get("");

    CommonButton commonButton = new CommonButton();
    commonButton.setName("上网验证");
    commonButton.setType("click");
    commonButton.setKey("GET_AUTH");
    Button[] b = new Button[1];
    b[0] = commonButton;

    Menu menu = new Menu();
    menu.setButton(b);

    JSONObject jsonMenu = JSONObject.fromObject(menu);
    String appid = ((WeixinAccount)this.weixinAccountService.getList(req).get(0)).getAppid();
    String appsecret = ((WeixinAccount)this.weixinAccountService.getList(req).get(0)).getAppsecret();

    String accessToken = WeixinUtil.getAccessToken(this.weixinAccessTokenService, appid, appsecret).getToken();
    String url = WeixinUtil.menu_create_url.replace("ACCESS_TOKEN", 
      accessToken);
    JSONObject jsonObject = new JSONObject();

    String message = null;
    String info = null;
    try {
      jsonObject = WeixinUtil.httpRequest(url, "POST", jsonMenu.toString());
      if (basConfig.getIsdebug().equals("1")) {
        log.info("Post Menu: " + jsonObject);
      }
      info = jsonObject.toString();
      if (jsonObject != null) {
        if (jsonObject.getInt("errcode") == 0) {
          message = "Syn Menu Success !!";
        }
        else
          message = "Syn Menu Error !! Error Code: " + jsonObject.getInt("errcode") + " Error Info: " + jsonObject.getString("errmsg");
      }
      else
        message = "Syn Menu Error !! Url is Error !!";
    }
    catch (Exception e) {
      message = "Syn Menu Error !!";
    }

    try
    {
      PrintWriter out = response.getWriter();
      out.print(info + "==========" + message);
      if (basConfig.getIsdebug().equals("1")) {
        log.info(info + "==========" + message);
      }
      out.close();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  @RequestMapping(value={"/user"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  public void user(HttpServletRequest req, HttpServletResponse response)
  {
    Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
    String token = null;

    String appid = ((WeixinAccount)this.weixinAccountService.getList(req).get(0)).getAppid();
    String appsecret = ((WeixinAccount)this.weixinAccountService.getList(req).get(0)).getAppsecret();
    token = WeixinUtil.getAccessToken(this.weixinAccessTokenService, appid, appsecret).getToken();

    List<Wxuser> users = null;
    try
    {
      users = JwUserAPI.getAllWxuser(token, null);
    } catch (WexinReqException localWexinReqException) {
    }
    StringBuffer result = new StringBuffer();

    if (users != null) {
      for (Wxuser wxuser : users) {
        result.append("County: " + wxuser.getCountry() + " City: " + wxuser.getCity() + " UserName: " + wxuser.getNickname());
      }
    }

    try
    {
      PrintWriter out = response.getWriter();
      out.print("GZH User Info::" + result);
      if (basConfig.getIsdebug().equals("1")) {
        log.info("GZH User Info::" + result);
      }
      out.close();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  @RequestMapping(value={"/ip"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  public void ip(HttpServletRequest req, HttpServletResponse response)
  {
    String token = null;

    String appid = ((WeixinAccount)this.weixinAccountService.getList(req).get(0)).getAppid();
    String appsecret = ((WeixinAccount)this.weixinAccountService.getList(req).get(0)).getAppsecret();
    token = WeixinUtil.getAccessToken(this.weixinAccessTokenService, appid, appsecret).getToken();
    try
    {
      List<String> ips = JwServiceIpAPI.getServiceIpList(token);
      StringBuffer result = new StringBuffer();

      if (ips != null) {
        for (String ip : ips) {
          result.append("ip:" + ip + "****");
        }
      }

      PrintWriter out = response.getWriter();
      out.print("WeiXin Server IP: " + result);
      out.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @RequestMapping(value={"/kf"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  public void kf(HttpServletRequest req, HttpServletResponse response)
  {
    String fromUserName = "oSzCquAYiRNbfQrVuLVKpaTWTKJM";
    String text = "感谢您使用我们的无线网络，公众号认证方式继续 回复【上网】或者 点击【上网验证】菜单，获取联网权限！！";
    String jsonT = "{\"touser\":\"" + fromUserName + "\",\"msgtype\":\"text\",\"text\":{\"content\":\"" + text + "\"}}";
    String appid = ((WeixinAccount)this.weixinAccountService.getList(req).get(0)).getAppid();
    String appsecret = ((WeixinAccount)this.weixinAccountService.getList(req).get(0)).getAppsecret();
    String accessToken = WeixinUtil.getAccessToken(this.weixinAccessTokenService, appid, appsecret).getToken();
    System.out.println("token=" + accessToken);
    System.out.println("Post KF API !!");
    System.out.println(jsonT);
    WeixinUtil.httpRequest("https:gi-binustom/send?access_token=" + accessToken, "POST", jsonT);
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     weixin.guanjia.core.controller.WechatController
 * JD-Core Version:    0.6.2
 */