/*     */ package weixin.service.impl;
/*     */ 
/*     */ import com.leeson.common.utils.ThreadLocalDateUtil;
/*     */ import com.leeson.common.utils.stringUtils;
/*     */ import com.leeson.core.bean.Portalautologin;
/*     */ import com.leeson.core.bean.Portalbas;
/*     */ import com.leeson.core.bean.Portalbasauth;
/*     */ import com.leeson.core.controller.AjaxInterFaceController;
/*     */ import com.leeson.core.query.PortalbasauthQuery;
/*     */ import com.leeson.core.service.PortalautologinService;
/*     */ import com.leeson.core.service.PortalbasauthService;
/*     */ import com.leeson.core.utils.Kick;
/*     */ import com.leeson.portal.core.model.AutoLoginMacMap;
/*     */ import com.leeson.portal.core.model.AutoLoginMap;
/*     */ import com.leeson.portal.core.model.Config;
/*     */ import com.leeson.portal.core.model.OnlineMap;
/*     */ import com.leeson.portal.core.model.OpenIdMap;
/*     */ import com.leeson.portal.core.model.WISPrWXRadiusTempMap;
/*     */ import com.leeson.portal.core.model.WeixinMap;
/*     */ import java.io.PrintStream;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.jeewx.api.wxuser.user.JwUserAPI;
/*     */ import org.jeewx.api.wxuser.user.model.Wxuser;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Service;
/*     */ import weixin.entity.WeixinAccount;
/*     */ import weixin.guanjia.core.entity.common.AccessToken;
/*     */ import weixin.guanjia.core.entity.message.resp.TextMessageResp;
/*     */ import weixin.guanjia.core.util.MessageUtil;
/*     */ import weixin.guanjia.core.util.WeixinUtil;
/*     */ import weixin.service.WeixinAccessTokenService;
/*     */ import weixin.service.WeixinAccountService;
/*     */ import weixin.service.WeixinChatService;
/*     */ 
/*     */ @Service
/*     */ public class WeixinChatServiceImpl
/*     */   implements WeixinChatService
/*     */ {
/*  43 */   private static Logger log = Logger.getLogger(WeixinChatServiceImpl.class);
/*  44 */   private static Config config = Config.getInstance();
/*     */ 
/*     */   @Autowired
/*     */   private WeixinAccountService weixinAccountService;
/*     */ 
/*     */   @Autowired
/*     */   private WeixinAccessTokenService weixinAccessTokenService;
/*     */ 
/*     */   @Autowired
/*     */   private PortalautologinService autologinService;
/*     */ 
/*     */   @Autowired
/*     */   private PortalbasauthService basauthService;
/*     */ 
/*  57 */   public String coreService(HttpServletRequest request) { Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
/*  58 */     String respMessage = null;
/*     */     try
/*     */     {
/*  61 */       String respContent = "请求处理异常，请稍候尝试！";
/*     */ 
/*  63 */       Map requestMap = MessageUtil.parseXml(request);
/*     */ 
/*  65 */       String fromUserName = (String)requestMap.get("FromUserName");
/*     */ 
/*  67 */       String toUserName = (String)requestMap.get("ToUserName");
/*     */ 
/*  69 */       String msgType = (String)requestMap.get("MsgType");
/*  70 */       String msgId = (String)requestMap.get("MsgId");
/*     */ 
/*  72 */       String content = (String)requestMap.get("Content");
/*  73 */       if (basConfig.getIsdebug().equals("1")) {
/*  74 */         log.info("------------WeiXin Client Request---------------------   |   fromUserName:" + 
/*  75 */           fromUserName + 
/*  76 */           "   |   ToUserName:" + 
/*  77 */           toUserName + 
/*  78 */           "   |   msgType:" + 
/*  79 */           msgType + 
/*  80 */           "   |   msgId:" + 
/*  81 */           msgId + 
/*  82 */           "   |   content:" + content);
/*     */ 
/*  84 */         log.info("-toUserName--------" + toUserName);
/*     */       }
/*     */ 
/*  88 */       TextMessageResp textMessage = new TextMessageResp();
/*  89 */       textMessage.setToUserName(fromUserName);
/*  90 */       textMessage.setFromUserName(toUserName);
/*  91 */       textMessage.setCreateTime(new Date().getTime());
/*  92 */       textMessage.setMsgType("text");
/*     */ 
/*  95 */       respMessage = MessageUtil.textMessageToXml(textMessage);
/*     */ 
/*  97 */       if (msgType.equals("text")) {
/*  98 */         if (basConfig.getIsdebug().equals("1")) {
/*  99 */           log.info("------------WeiXin Client Request------------------Text Message---");
/*     */         }
/* 101 */         content = findKey(content, toUserName, fromUserName, request);
/* 102 */         if (content == null) {
/* 103 */           content = "感谢您的关注 ！！ 回复\"上网\"或者点击【上网验证】菜单，获取联网权限！！";
/*     */         }
/* 105 */         textMessage.setContent(content);
/* 106 */         respMessage = doTextResponse(content, toUserName, textMessage, 
/* 107 */           respMessage, fromUserName, request, msgId, msgType);
/*     */       }
/* 110 */       else if (msgType.equals("image")) {
/* 111 */         respContent = "您发送的是图片消息！";
/*     */       }
/* 114 */       else if (msgType.equals("location")) {
/* 115 */         respContent = "您发送的是地理位置消息！";
/*     */       }
/* 118 */       else if (msgType.equals("link")) {
/* 119 */         respContent = "您发送的是链接消息！";
/*     */       }
/* 122 */       else if (msgType.equals("voice")) {
/* 123 */         respContent = "您发送的是音频消息！";
/*     */       }
/* 126 */       else if (msgType.equals("event"))
/*     */       {
/* 128 */         String eventType = (String)requestMap.get("Event");
/* 129 */         if (basConfig.getIsdebug().equals("1")) {
/* 130 */           log.info("------------WeiXin Client Request------------------Event Message--- eventTpye=" + 
/* 131 */             eventType);
/*     */         }
/*     */ 
/* 135 */         if (eventType.equals("WifiConnected")) {
/* 136 */           doWifiEventResponse(requestMap, textMessage, respMessage, 
/* 137 */             toUserName, fromUserName, respContent, request);
/*     */         }
/*     */ 
/* 140 */         if (eventType.equals("subscribe")) {
/* 141 */           respMessage = doDingYueEventResponse(requestMap, 
/* 142 */             textMessage, respMessage, toUserName, fromUserName, 
/* 143 */             respContent);
/*     */         }
/* 146 */         else if (eventType.equals("unsubscribe"))
/*     */         {
/* 148 */           respMessage = doUnDingYueEventResponse(requestMap, 
/* 149 */             textMessage, respMessage, toUserName, fromUserName, 
/* 150 */             respContent);
/*     */         }
/* 153 */         else if (eventType.equals("CLICK"))
/* 154 */           respMessage = doMyMenuEvent(requestMap, textMessage, 
/* 155 */             respMessage, toUserName, fromUserName, respContent, 
/* 156 */             request);
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 160 */       e.printStackTrace();
/*     */     }
/* 162 */     return respMessage;
/*     */   }
/*     */ 
/*     */   private String findKey(String content, String toUsername, String openID, HttpServletRequest request)
/*     */   {
/* 172 */     if (content.equals("上网")) {
/* 173 */       String url = "请先打开浏览器访问任意网站,完成WEB认证流程！";
/* 174 */       String[] magicInfo = 
/* 175 */         (String[])OpenIdMap.getInstance().getOpenIdMap()
/* 175 */         .get(openID);
/*     */ 
/* 177 */       if ((magicInfo != null) && (magicInfo.length >= 3)) {
/* 178 */         String host = magicInfo[0];
/* 179 */         String mac = magicInfo[2];
/* 180 */         String[] userInfo = 
/* 181 */           (String[])OnlineMap.getInstance().getOnlineUserMap()
/* 181 */           .get(host);
/* 182 */         if ((userInfo != null) && (userInfo.length > 0)) {
/* 183 */           String username = userInfo[0].replace("-临时放行", "");
/* 184 */           userInfo[0] = username;
/*     */ 
/* 194 */           OnlineMap.getInstance().getOnlineUserMap().put(host, userInfo);
/*     */ 
/* 196 */           WeixinMap.getInstance().getWeixinipmap().remove(host);
/* 197 */           WISPrWXRadiusTempMap.getInstance().getWisprWXRadiusTempMap().remove(host);
/* 198 */           log.info("host: " + host + " mac: " + mac + 
/* 199 */             " GZH Auth Success , Remove WeixinTempMap !!");
/* 200 */           url = "恭喜，认证成功!!";
/*     */ 
/* 203 */           PortalbasauthQuery bsq = new PortalbasauthQuery();
/*     */ 
/* 208 */           int i = host.lastIndexOf(":");
/* 209 */           String basip = host.substring(i + 1);
/* 210 */           String ip = host.substring(0, i);
/*     */ 
/* 212 */           bsq.setBasip(basip);
/* 213 */           String authUser = ((Portalbas)config.getConfigMap().get(basip)).getBasUser();
/* 214 */           String authPwd = ((Portalbas)config.getConfigMap().get(basip)).getBasPwd();
/* 215 */           List<Portalbasauth> basauths = this.basauthService
/* 216 */             .getPortalbasauthList(bsq);
/* 217 */           if (basauths.size() > 0) {
/* 218 */             for (Portalbasauth ba : basauths) {
/* 219 */               if (ba.getType().intValue() == 5) {
/* 220 */                 authUser = ba.getUsername();
/* 221 */                 authPwd = ba.getPassword();
/* 222 */                 if ((!stringUtils.isBlank(authUser)) && 
/* 223 */                   (!stringUtils.isBlank(authPwd))) break;
/* 224 */                 authUser = ((Portalbas)config.getConfigMap().get(basip))
/* 225 */                   .getBasUser();
/* 226 */                 authPwd = ((Portalbas)config.getConfigMap().get(basip))
/* 227 */                   .getBasPwd();
/*     */ 
/* 229 */                 break;
/*     */               }
/*     */             }
/*     */           }
/*     */ 
/* 234 */           if ((stringUtils.isBlank(authUser)) || (stringUtils.isBlank(authPwd))) {
/* 235 */             PortalbasauthQuery baq = new PortalbasauthQuery();
/* 236 */             baq.setBasid(Long.valueOf(0L));
/* 237 */             basauths = this.basauthService
/* 238 */               .getPortalbasauthList(baq);
/* 239 */             if (basauths.size() > 0) {
/* 240 */               for (Portalbasauth ba : basauths) {
/* 241 */                 if (ba.getType().intValue() == 5) {
/* 242 */                   authUser = ba.getUsername();
/* 243 */                   authPwd = ba.getPassword();
/* 244 */                   break;
/*     */                 }
/*     */               }
/*     */             }
/*     */           }
/* 249 */           if ((stringUtils.isBlank(authUser)) || (stringUtils.isBlank(authPwd))) {
/* 250 */             Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
/* 251 */             authUser = basConfig.getBasUser();
/* 252 */             authPwd = basConfig.getBasPwd();
/*     */           }
/*     */ 
/* 255 */           AutoLoginPutMethod(mac, Long.valueOf(7L), authUser, authPwd, userInfo[0]);
/*     */ 
/* 257 */           AjaxInterFaceController.SangforLogin(ip, username, mac, "", basip);
/*     */         }
/*     */       }
/*     */ 
/* 261 */       return url;
/*     */     }
/* 263 */     return null;
/*     */   }
/*     */ 
/*     */   String doTextResponse(String content, String toUserName, TextMessageResp textMessage, String respMessage, String fromUserName, HttpServletRequest request, String msgId, String msgType)
/*     */     throws Exception
/*     */   {
/* 339 */     respMessage = MessageUtil.textMessageToXml(textMessage);
/*     */ 
/* 400 */     return respMessage;
/*     */   }
/*     */ 
/*     */   String doDingYueEventResponse(Map<String, String> requestMap, TextMessageResp textMessage, String respMessage, String toUserName, String fromUserName, String respContent)
/*     */   {
/* 428 */     textMessage.setContent("感谢您的关注 ！！ 回复\"上网\"或者点击【上网验证】菜单，获取联网权限！！");
/* 429 */     respMessage = MessageUtil.textMessageToXml(textMessage);
/*     */ 
/* 464 */     return respMessage;
/*     */   }
/*     */ 
/*     */   String doMyMenuEvent(Map<String, String> requestMap, TextMessageResp textMessage, String respMessage, String toUserName, String fromUserName, String respContent, HttpServletRequest request)
/*     */     throws Exception
/*     */   {
/* 487 */     Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
/* 488 */     String key = (String)requestMap.get("EventKey");
/*     */ 
/* 501 */     String url = "请先打开浏览器访问任意网站,完成WEB认证流程！";
/* 502 */     String[] magicInfo = 
/* 503 */       (String[])OpenIdMap.getInstance().getOpenIdMap()
/* 503 */       .get(fromUserName);
/*     */ 
/* 505 */     if ((magicInfo != null) && (magicInfo.length >= 3)) {
/* 506 */       String host = magicInfo[0];
/* 507 */       String mac = magicInfo[2];
/* 508 */       String[] userInfo = 
/* 509 */         (String[])OnlineMap.getInstance().getOnlineUserMap()
/* 509 */         .get(host);
/* 510 */       if ((userInfo != null) && (userInfo.length > 0)) {
/* 511 */         String username = userInfo[0].replace("-临时放行", "");
/* 512 */         userInfo[0] = username;
/*     */ 
/* 522 */         OnlineMap.getInstance().getOnlineUserMap().put(host, userInfo);
/*     */ 
/* 524 */         WeixinMap.getInstance().getWeixinipmap().remove(host);
/* 525 */         WISPrWXRadiusTempMap.getInstance().getWisprWXRadiusTempMap().remove(host);
/* 526 */         log.info("host: " + host + " mac: " + mac + 
/* 527 */           " GZH Auth Success , Remove WeixinTempMap !!");
/* 528 */         url = "恭喜，认证成功!!";
/*     */ 
/* 531 */         PortalbasauthQuery bsq = new PortalbasauthQuery();
/*     */ 
/* 536 */         int i = host.lastIndexOf(":");
/* 537 */         String basip = host.substring(i + 1);
/* 538 */         String ip = host.substring(0, i);
/*     */ 
/* 540 */         bsq.setBasip(basip);
/* 541 */         String authUser = ((Portalbas)config.getConfigMap().get(basip)).getBasUser();
/* 542 */         String authPwd = ((Portalbas)config.getConfigMap().get(basip)).getBasPwd();
/* 543 */         List<Portalbasauth> basauths = this.basauthService
/* 544 */           .getPortalbasauthList(bsq);
/* 545 */         if (basauths.size() > 0) {
/* 546 */           for (Portalbasauth ba : basauths) {
/* 547 */             if (ba.getType().intValue() == 5) {
/* 548 */               authUser = ba.getUsername();
/* 549 */               authPwd = ba.getPassword();
/* 550 */               if ((!stringUtils.isBlank(authUser)) && 
/* 551 */                 (!stringUtils.isBlank(authPwd))) break;
/* 552 */               authUser = ((Portalbas)config.getConfigMap().get(basip))
/* 553 */                 .getBasUser();
/* 554 */               authPwd = ((Portalbas)config.getConfigMap().get(basip))
/* 555 */                 .getBasPwd();
/*     */ 
/* 557 */               break;
/*     */             }
/*     */           }
/*     */         }
/*     */ 
/* 562 */         if ((stringUtils.isBlank(authUser)) || (stringUtils.isBlank(authPwd))) {
/* 563 */           PortalbasauthQuery baq = new PortalbasauthQuery();
/* 564 */           baq.setBasid(Long.valueOf(0L));
/* 565 */           basauths = this.basauthService
/* 566 */             .getPortalbasauthList(baq);
/* 567 */           if (basauths.size() > 0) {
/* 568 */             for (Portalbasauth ba : basauths) {
/* 569 */               if (ba.getType().intValue() == 5) {
/* 570 */                 authUser = ba.getUsername();
/* 571 */                 authPwd = ba.getPassword();
/* 572 */                 break;
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/* 577 */         if ((stringUtils.isBlank(authUser)) || (stringUtils.isBlank(authPwd))) {
/* 578 */           authUser = basConfig.getBasUser();
/* 579 */           authPwd = basConfig.getBasPwd();
/*     */         }
/*     */ 
/* 582 */         AutoLoginPutMethod(mac, Long.valueOf(7L), authUser, authPwd, userInfo[0]);
/*     */ 
/* 584 */         AjaxInterFaceController.SangforLogin(ip, username, mac, "", basip);
/*     */       }
/*     */     }
/*     */ 
/* 588 */     textMessage.setContent(url);
/* 589 */     if (basConfig.getIsdebug().equals("1")) {
/* 590 */       log.info("Menu CLICK " + key);
/*     */     }
/* 592 */     respMessage = MessageUtil.textMessageToXml(textMessage);
/*     */ 
/* 636 */     return respMessage;
/*     */   }
/*     */ 
/*     */   String doUnDingYueEventResponse(Map<String, String> requestMap, TextMessageResp textMessage, String respMessage, String toUserName, String fromUserName, String respContent)
/*     */   {
/* 642 */     Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
/* 643 */     String[] magicInfo = 
/* 644 */       (String[])OpenIdMap.getInstance().getOpenIdMap()
/* 644 */       .get(fromUserName);
/* 645 */     OpenIdMap.getInstance().getOpenIdMap().remove(fromUserName);
/* 646 */     if ((magicInfo != null) && (magicInfo.length >= 3)) {
/* 647 */       String host = magicInfo[0];
/* 648 */       String mac = magicInfo[2];
/* 649 */       String[] userInfo = 
/* 650 */         (String[])OnlineMap.getInstance().getOnlineUserMap()
/* 650 */         .get(host);
/* 651 */       if ((userInfo != null) && (userInfo.length > 0))
/*     */       {
/* 653 */         if (basConfig.getIsdebug().equals("1")) {
/* 654 */           log.info("Event:UnDingYueEvent OpenID=" + fromUserName + 
/* 655 */             " host=" + host + " mac=" + mac + " Kick Out !!!");
/* 656 */           Kick.kickUserUnDingYue(host);
/* 657 */           AutoLoginMacMap.getInstance().getAutoLoginMacMap().remove(mac);
/* 658 */           AutoLoginMap.getInstance().getAutoLoginMap().remove(mac);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 663 */     return "";
/*     */   }
/*     */ 
/*     */   String doWifiEventResponse(Map<String, String> requestMap, TextMessageResp textMessage, String respMessage, String toUserName, String fromUserName, String respContent, HttpServletRequest request)
/*     */   {
/* 669 */     String text = "感谢您使用我们的无线网络，公众号认证方式继续 回复【上网】或者 点击【上网验证】菜单，获取联网权限！！";
/* 670 */     String jsonT = "{\"touser\":\"" + fromUserName + 
/* 671 */       "\",\"msgtype\":\"text\",\"text\":{\"content\":\"" + text + 
/* 672 */       "\"}}";
/* 673 */     String appid = ((WeixinAccount)this.weixinAccountService.getList(request).get(0)).getAppid();
/* 674 */     String appsecret = ((WeixinAccount)this.weixinAccountService.getList(request).get(0))
/* 675 */       .getAppsecret();
/* 676 */     String accessToken = WeixinUtil.getAccessToken(
/* 677 */       this.weixinAccessTokenService, appid, appsecret).getToken();
/* 678 */     System.out.println("token=" + accessToken);
/* 679 */     System.out.println("Post KF API !!");
/* 680 */     System.out.println(jsonT);
/* 681 */     WeixinUtil.httpRequest(
/* 682 */       "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + 
/* 683 */       accessToken, "POST", jsonT);
/* 684 */     return null;
/*     */   }
/*     */ 
/*     */   public String getUserNick(String fromUserName, HttpServletRequest request) {
/* 688 */     String userNick = "";
/*     */     try {
/* 690 */       String appid = ((WeixinAccount)this.weixinAccountService.getList(request).get(0))
/* 691 */         .getAppid();
/* 692 */       String appsecret = ((WeixinAccount)this.weixinAccountService.getList(request).get(0))
/* 693 */         .getAppsecret();
/* 694 */       String accessToken = WeixinUtil.getAccessToken(
/* 695 */         this.weixinAccessTokenService, appid, appsecret).getToken();
/* 696 */       Wxuser user = JwUserAPI.getWxuser(accessToken, fromUserName);
/* 697 */       if (user != null) {
/* 698 */         System.out.println("WeiXin User Info ::::: " + 
/* 699 */           user.getSubscribe() + ":" + user.getNickname() + ":" + 
/* 700 */           user.getCountry() + ":" + user.getCity() + ":" + 
/* 701 */           user.getProvince() + ":" + user.getRemark() + ":" + 
/* 702 */           user.getSubscribe_time());
/* 703 */         userNick = user.getNickname();
/*     */       }
/*     */     } catch (Exception localException) {
/*     */     }
/* 707 */     return userNick;
/*     */   }
/*     */ 
/*     */   private void AutoLoginPutMethod(String mac, Long id, String authUser, String authPwd, String username)
/*     */   {
/* 718 */     Portalautologin autologin = this.autologinService
/* 719 */       .getPortalautologinByKey(id);
/* 720 */     if ((autologin != null) && (autologin.getState().intValue() == 1) && 
/* 721 */       (stringUtils.isNotBlank(mac)) && (!"error".equals(mac))) {
/* 722 */       String[] macTimeInfo = (String[])AutoLoginMacMap.getInstance().getAutoLoginMacMap().get(mac);
/* 723 */       Date now = new Date();
/* 724 */       String nowString = ThreadLocalDateUtil.format(now);
/* 725 */       if (macTimeInfo == null) {
/* 726 */         macTimeInfo = new String[3];
/* 727 */         macTimeInfo[1] = "0";
/*     */       }
/* 729 */       macTimeInfo[0] = nowString;
/* 730 */       macTimeInfo[2] = String.valueOf(id);
/* 731 */       AutoLoginMacMap.getInstance().getAutoLoginMacMap().put(mac, macTimeInfo);
/*     */ 
/* 733 */       String[] userinfo = new String[3];
/* 734 */       userinfo[0] = authUser;
/* 735 */       userinfo[1] = authPwd;
/* 736 */       userinfo[2] = username;
/* 737 */       AutoLoginMap.getInstance().getAutoLoginMap().put(mac, userinfo);
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     weixin.service.impl.WeixinChatServiceImpl
 * JD-Core Version:    0.6.2
 */