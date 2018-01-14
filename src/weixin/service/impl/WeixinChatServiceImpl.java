package weixin.service.impl;

import com.leeson.common.utils.ThreadLocalDateUtil;
import com.leeson.common.utils.stringUtils;
import com.leeson.core.bean.Portalautologin;
import com.leeson.core.bean.Portalbas;
import com.leeson.core.bean.Portalbasauth;
import com.leeson.core.controller.AjaxInterFaceController;
import com.leeson.core.query.PortalbasauthQuery;
import com.leeson.core.service.PortalautologinService;
import com.leeson.core.service.PortalbasauthService;
import com.leeson.core.utils.Kick;
import com.leeson.portal.core.model.AutoLoginMacMap;
import com.leeson.portal.core.model.AutoLoginMap;
import com.leeson.portal.core.model.Config;
import com.leeson.portal.core.model.OnlineMap;
import com.leeson.portal.core.model.OpenIdMap;
import com.leeson.portal.core.model.WISPrWXRadiusTempMap;
import com.leeson.portal.core.model.WeixinMap;
import java.io.PrintStream;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.jeewx.api.wxuser.user.JwUserAPI;
import org.jeewx.api.wxuser.user.model.Wxuser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import weixin.entity.WeixinAccount;
import weixin.guanjia.core.entity.common.AccessToken;
import weixin.guanjia.core.entity.message.resp.TextMessageResp;
import weixin.guanjia.core.util.MessageUtil;
import weixin.guanjia.core.util.WeixinUtil;
import weixin.service.WeixinAccessTokenService;
import weixin.service.WeixinAccountService;
import weixin.service.WeixinChatService;

@Service
public class WeixinChatServiceImpl
  implements WeixinChatService
{
  private static Logger log = Logger.getLogger(WeixinChatServiceImpl.class);
  private static Config config = Config.getInstance();

  @Autowired
  private WeixinAccountService weixinAccountService;

  @Autowired
  private WeixinAccessTokenService weixinAccessTokenService;

  @Autowired
  private PortalautologinService autologinService;

  @Autowired
  private PortalbasauthService basauthService;

  public String coreService(HttpServletRequest request) { Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
    String respMessage = null;
    try
    {
      String respContent = "请求处理异常，请稍候尝试！";

      Map requestMap = MessageUtil.parseXml(request);

      String fromUserName = (String)requestMap.get("FromUserName");

      String toUserName = (String)requestMap.get("ToUserName");

      String msgType = (String)requestMap.get("MsgType");
      String msgId = (String)requestMap.get("MsgId");

      String content = (String)requestMap.get("Content");
      if (basConfig.getIsdebug().equals("1")) {
        log.info("------------WeiXin Client Request---------------------   |   fromUserName:" + 
          fromUserName + 
          "   |   ToUserName:" + 
          toUserName + 
          "   |   msgType:" + 
          msgType + 
          "   |   msgId:" + 
          msgId + 
          "   |   content:" + content);

        log.info("-toUserName--------" + toUserName);
      }

      TextMessageResp textMessage = new TextMessageResp();
      textMessage.setToUserName(fromUserName);
      textMessage.setFromUserName(toUserName);
      textMessage.setCreateTime(new Date().getTime());
      textMessage.setMsgType("text");

      respMessage = MessageUtil.textMessageToXml(textMessage);

      if (msgType.equals("text")) {
        if (basConfig.getIsdebug().equals("1")) {
          log.info("------------WeiXin Client Request------------------Text Message---");
        }
        content = findKey(content, toUserName, fromUserName, request);
        if (content == null) {
          content = "感谢您的关注 ！！ 回复\"上网\"或者点击【上网验证】菜单，获取联网权限！！";
        }
        textMessage.setContent(content);
        respMessage = doTextResponse(content, toUserName, textMessage, 
          respMessage, fromUserName, request, msgId, msgType);
      }
      else if (msgType.equals("image")) {
        respContent = "您发送的是图片消息！";
      }
      else if (msgType.equals("location")) {
        respContent = "您发送的是地理位置消息！";
      }
      else if (msgType.equals("link")) {
        respContent = "您发送的是链接消息！";
      }
      else if (msgType.equals("voice")) {
        respContent = "您发送的是音频消息！";
      }
      else if (msgType.equals("event"))
      {
        String eventType = (String)requestMap.get("Event");
        if (basConfig.getIsdebug().equals("1")) {
          log.info("------------WeiXin Client Request------------------Event Message--- eventTpye=" + 
            eventType);
        }

        if (eventType.equals("WifiConnected")) {
          doWifiEventResponse(requestMap, textMessage, respMessage, 
            toUserName, fromUserName, respContent, request);
        }

        if (eventType.equals("subscribe")) {
          respMessage = doDingYueEventResponse(requestMap, 
            textMessage, respMessage, toUserName, fromUserName, 
            respContent);
        }
        else if (eventType.equals("unsubscribe"))
        {
          respMessage = doUnDingYueEventResponse(requestMap, 
            textMessage, respMessage, toUserName, fromUserName, 
            respContent);
        }
        else if (eventType.equals("CLICK"))
          respMessage = doMyMenuEvent(requestMap, textMessage, 
            respMessage, toUserName, fromUserName, respContent, 
            request);
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return respMessage;
  }

  private String findKey(String content, String toUsername, String openID, HttpServletRequest request)
  {
    if (content.equals("上网")) {
      String url = "请先打开浏览器访问任意网站,完成WEB认证流程！";
      String[] magicInfo = 
        (String[])OpenIdMap.getInstance().getOpenIdMap()
        .get(openID);

      if ((magicInfo != null) && (magicInfo.length >= 3)) {
        String host = magicInfo[0];
        String mac = magicInfo[2];
        String[] userInfo = 
          (String[])OnlineMap.getInstance().getOnlineUserMap()
          .get(host);
        if ((userInfo != null) && (userInfo.length > 0)) {
          String username = userInfo[0].replace("-临时放行", "");
          userInfo[0] = username;

          OnlineMap.getInstance().getOnlineUserMap().put(host, userInfo);

          WeixinMap.getInstance().getWeixinipmap().remove(host);
          WISPrWXRadiusTempMap.getInstance().getWisprWXRadiusTempMap().remove(host);
          log.info("host: " + host + " mac: " + mac + 
            " GZH Auth Success , Remove WeixinTempMap !!");
          url = "恭喜，认证成功!!";

          PortalbasauthQuery bsq = new PortalbasauthQuery();

          int i = host.lastIndexOf(":");
          String basip = host.substring(i + 1);
          String ip = host.substring(0, i);

          bsq.setBasip(basip);
          String authUser = ((Portalbas)config.getConfigMap().get(basip)).getBasUser();
          String authPwd = ((Portalbas)config.getConfigMap().get(basip)).getBasPwd();
          List<Portalbasauth> basauths = this.basauthService
            .getPortalbasauthList(bsq);
          if (basauths.size() > 0) {
            for (Portalbasauth ba : basauths) {
              if (ba.getType().intValue() == 5) {
                authUser = ba.getUsername();
                authPwd = ba.getPassword();
                if ((!stringUtils.isBlank(authUser)) && 
                  (!stringUtils.isBlank(authPwd))) break;
                authUser = ((Portalbas)config.getConfigMap().get(basip))
                  .getBasUser();
                authPwd = ((Portalbas)config.getConfigMap().get(basip))
                  .getBasPwd();

                break;
              }
            }
          }

          if ((stringUtils.isBlank(authUser)) || (stringUtils.isBlank(authPwd))) {
            PortalbasauthQuery baq = new PortalbasauthQuery();
            baq.setBasid(Long.valueOf(0L));
            basauths = this.basauthService
              .getPortalbasauthList(baq);
            if (basauths.size() > 0) {
              for (Portalbasauth ba : basauths) {
                if (ba.getType().intValue() == 5) {
                  authUser = ba.getUsername();
                  authPwd = ba.getPassword();
                  break;
                }
              }
            }
          }
          if ((stringUtils.isBlank(authUser)) || (stringUtils.isBlank(authPwd))) {
            Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
            authUser = basConfig.getBasUser();
            authPwd = basConfig.getBasPwd();
          }

          AutoLoginPutMethod(mac, Long.valueOf(7L), authUser, authPwd, userInfo[0]);

          AjaxInterFaceController.SangforLogin(ip, username, mac, "", basip);
        }
      }

      return url;
    }
    return null;
  }

  String doTextResponse(String content, String toUserName, TextMessageResp textMessage, String respMessage, String fromUserName, HttpServletRequest request, String msgId, String msgType)
    throws Exception
  {
    respMessage = MessageUtil.textMessageToXml(textMessage);

    return respMessage;
  }

  String doDingYueEventResponse(Map<String, String> requestMap, TextMessageResp textMessage, String respMessage, String toUserName, String fromUserName, String respContent)
  {
    textMessage.setContent("感谢您的关注 ！！ 回复\"上网\"或者点击【上网验证】菜单，获取联网权限！！");
    respMessage = MessageUtil.textMessageToXml(textMessage);

    return respMessage;
  }

  String doMyMenuEvent(Map<String, String> requestMap, TextMessageResp textMessage, String respMessage, String toUserName, String fromUserName, String respContent, HttpServletRequest request)
    throws Exception
  {
    Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
    String key = (String)requestMap.get("EventKey");

    String url = "请先打开浏览器访问任意网站,完成WEB认证流程！";
    String[] magicInfo = 
      (String[])OpenIdMap.getInstance().getOpenIdMap()
      .get(fromUserName);

    if ((magicInfo != null) && (magicInfo.length >= 3)) {
      String host = magicInfo[0];
      String mac = magicInfo[2];
      String[] userInfo = 
        (String[])OnlineMap.getInstance().getOnlineUserMap()
        .get(host);
      if ((userInfo != null) && (userInfo.length > 0)) {
        String username = userInfo[0].replace("-临时放行", "");
        userInfo[0] = username;

        OnlineMap.getInstance().getOnlineUserMap().put(host, userInfo);

        WeixinMap.getInstance().getWeixinipmap().remove(host);
        WISPrWXRadiusTempMap.getInstance().getWisprWXRadiusTempMap().remove(host);
        log.info("host: " + host + " mac: " + mac + 
          " GZH Auth Success , Remove WeixinTempMap !!");
        url = "恭喜，认证成功!!";

        PortalbasauthQuery bsq = new PortalbasauthQuery();

        int i = host.lastIndexOf(":");
        String basip = host.substring(i + 1);
        String ip = host.substring(0, i);

        bsq.setBasip(basip);
        String authUser = ((Portalbas)config.getConfigMap().get(basip)).getBasUser();
        String authPwd = ((Portalbas)config.getConfigMap().get(basip)).getBasPwd();
        List<Portalbasauth> basauths = this.basauthService
          .getPortalbasauthList(bsq);
        if (basauths.size() > 0) {
          for (Portalbasauth ba : basauths) {
            if (ba.getType().intValue() == 5) {
              authUser = ba.getUsername();
              authPwd = ba.getPassword();
              if ((!stringUtils.isBlank(authUser)) && 
                (!stringUtils.isBlank(authPwd))) break;
              authUser = ((Portalbas)config.getConfigMap().get(basip))
                .getBasUser();
              authPwd = ((Portalbas)config.getConfigMap().get(basip))
                .getBasPwd();

              break;
            }
          }
        }

        if ((stringUtils.isBlank(authUser)) || (stringUtils.isBlank(authPwd))) {
          PortalbasauthQuery baq = new PortalbasauthQuery();
          baq.setBasid(Long.valueOf(0L));
          basauths = this.basauthService
            .getPortalbasauthList(baq);
          if (basauths.size() > 0) {
            for (Portalbasauth ba : basauths) {
              if (ba.getType().intValue() == 5) {
                authUser = ba.getUsername();
                authPwd = ba.getPassword();
                break;
              }
            }
          }
        }
        if ((stringUtils.isBlank(authUser)) || (stringUtils.isBlank(authPwd))) {
          authUser = basConfig.getBasUser();
          authPwd = basConfig.getBasPwd();
        }

        AutoLoginPutMethod(mac, Long.valueOf(7L), authUser, authPwd, userInfo[0]);

        AjaxInterFaceController.SangforLogin(ip, username, mac, "", basip);
      }
    }

    textMessage.setContent(url);
    if (basConfig.getIsdebug().equals("1")) {
      log.info("Menu CLICK " + key);
    }
    respMessage = MessageUtil.textMessageToXml(textMessage);

    return respMessage;
  }

  String doUnDingYueEventResponse(Map<String, String> requestMap, TextMessageResp textMessage, String respMessage, String toUserName, String fromUserName, String respContent)
  {
    Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
    String[] magicInfo = 
      (String[])OpenIdMap.getInstance().getOpenIdMap()
      .get(fromUserName);
    OpenIdMap.getInstance().getOpenIdMap().remove(fromUserName);
    if ((magicInfo != null) && (magicInfo.length >= 3)) {
      String host = magicInfo[0];
      String mac = magicInfo[2];
      String[] userInfo = 
        (String[])OnlineMap.getInstance().getOnlineUserMap()
        .get(host);
      if ((userInfo != null) && (userInfo.length > 0))
      {
        if (basConfig.getIsdebug().equals("1")) {
          log.info("Event:UnDingYueEvent OpenID=" + fromUserName + 
            " host=" + host + " mac=" + mac + " Kick Out !!!");
          Kick.kickUserUnDingYue(host);
          AutoLoginMacMap.getInstance().getAutoLoginMacMap().remove(mac);
          AutoLoginMap.getInstance().getAutoLoginMap().remove(mac);
        }
      }
    }

    return "";
  }

  String doWifiEventResponse(Map<String, String> requestMap, TextMessageResp textMessage, String respMessage, String toUserName, String fromUserName, String respContent, HttpServletRequest request)
  {
    String text = "感谢您使用我们的无线网络，公众号认证方式继续 回复【上网】或者 点击【上网验证】菜单，获取联网权限！！";
    String jsonT = "{\"touser\":\"" + fromUserName + 
      "\",\"msgtype\":\"text\",\"text\":{\"content\":\"" + text + 
      "\"}}";
    String appid = ((WeixinAccount)this.weixinAccountService.getList(request).get(0)).getAppid();
    String appsecret = ((WeixinAccount)this.weixinAccountService.getList(request).get(0))
      .getAppsecret();
    String accessToken = WeixinUtil.getAccessToken(
      this.weixinAccessTokenService, appid, appsecret).getToken();
    System.out.println("token=" + accessToken);
    System.out.println("Post KF API !!");
    System.out.println(jsonT);
    WeixinUtil.httpRequest(
      "https:gi-binustom/send?access_token=" + 
      accessToken, "POST", jsonT);
    return null;
  }

  public String getUserNick(String fromUserName, HttpServletRequest request) {
    String userNick = "";
    try {
      String appid = ((WeixinAccount)this.weixinAccountService.getList(request).get(0))
        .getAppid();
      String appsecret = ((WeixinAccount)this.weixinAccountService.getList(request).get(0))
        .getAppsecret();
      String accessToken = WeixinUtil.getAccessToken(
        this.weixinAccessTokenService, appid, appsecret).getToken();
      Wxuser user = JwUserAPI.getWxuser(accessToken, fromUserName);
      if (user != null) {
        System.out.println("WeiXin User Info ::::: " + 
          user.getSubscribe() + ":" + user.getNickname() + ":" + 
          user.getCountry() + ":" + user.getCity() + ":" + 
          user.getProvince() + ":" + user.getRemark() + ":" + 
          user.getSubscribe_time());
        userNick = user.getNickname();
      }
    } catch (Exception localException) {
    }
    return userNick;
  }

  private void AutoLoginPutMethod(String mac, Long id, String authUser, String authPwd, String username)
  {
    Portalautologin autologin = this.autologinService
      .getPortalautologinByKey(id);
    if ((autologin != null) && (autologin.getState().intValue() == 1) && 
      (stringUtils.isNotBlank(mac)) && (!"error".equals(mac))) {
      String[] macTimeInfo = (String[])AutoLoginMacMap.getInstance().getAutoLoginMacMap().get(mac);
      Date now = new Date();
      String nowString = ThreadLocalDateUtil.format(now);
      if (macTimeInfo == null) {
        macTimeInfo = new String[3];
        macTimeInfo[1] = "0";
      }
      macTimeInfo[0] = nowString;
      macTimeInfo[2] = String.valueOf(id);
      AutoLoginMacMap.getInstance().getAutoLoginMacMap().put(mac, macTimeInfo);

      String[] userinfo = new String[3];
      userinfo[0] = authUser;
      userinfo[1] = authPwd;
      userinfo[2] = username;
      AutoLoginMap.getInstance().getAutoLoginMap().put(mac, userinfo);
    }
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     weixin.service.impl.WeixinChatServiceImpl
 * JD-Core Version:    0.6.2
 */