/*     */ package weixin.guanjia.core.controller;
/*     */ 
/*     */ import com.leeson.core.bean.Portalbas;
/*     */ import com.leeson.portal.core.model.Config;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.io.PrintWriter;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import net.sf.json.JSONObject;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.jeewx.api.core.exception.WexinReqException;
/*     */ import org.jeewx.api.wxbase.wxserviceip.JwServiceIpAPI;
/*     */ import org.jeewx.api.wxuser.user.JwUserAPI;
/*     */ import org.jeewx.api.wxuser.user.model.Wxuser;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ import org.springframework.web.bind.annotation.RequestParam;
/*     */ import weixin.entity.WeixinAccount;
/*     */ import weixin.guanjia.core.entity.common.AccessToken;
/*     */ import weixin.guanjia.core.entity.common.Button;
/*     */ import weixin.guanjia.core.entity.common.CommonButton;
/*     */ import weixin.guanjia.core.entity.common.Menu;
/*     */ import weixin.guanjia.core.util.SignUtil;
/*     */ import weixin.guanjia.core.util.WeixinUtil;
/*     */ import weixin.service.WeixinAccessTokenService;
/*     */ import weixin.service.WeixinAccountService;
/*     */ import weixin.service.WeixinChatService;
/*     */ 
/*     */ @Controller
/*     */ public class WechatController
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private WeixinChatService wechatService;
/*     */ 
/*     */   @Autowired
/*     */   private WeixinAccountService weixinAccountService;
/*     */ 
/*     */   @Autowired
/*     */   private WeixinAccessTokenService weixinAccessTokenService;
/*  47 */   private static Config config = Config.getInstance();
/*  48 */   private static Logger log = Logger.getLogger(WechatController.class);
/*     */ 
/*     */   @RequestMapping(value={"/weixin"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
/*     */   public void wechatGet(HttpServletRequest request, HttpServletResponse response, @RequestParam("signature") String signature, @RequestParam("timestamp") String timestamp, @RequestParam("nonce") String nonce, @RequestParam("echostr") String echostr)
/*     */   {
/*  57 */     Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
/*     */ 
/*  59 */     if (basConfig.getIsdebug().equals("1")) {
/*  60 */       log.info("signature: " + signature);
/*  61 */       log.info("timestamp: " + timestamp);
/*  62 */       log.info("nonce: " + nonce);
/*  63 */       log.info("echostr: " + echostr);
/*     */     }
/*  65 */     List<WeixinAccount> weixinAccounts = this.weixinAccountService.getList(request);
/*  66 */     for (WeixinAccount account : weixinAccounts)
/*     */     {
/*  68 */       if (SignUtil.checkSignature(account.getAccounttoken(), signature, 
/*  68 */         timestamp, nonce))
/*     */         try {
/*  70 */           if (basConfig.getIsdebug().equals("1")) {
/*  71 */             log.info("WeiXin GZH Check Success !!");
/*     */           }
/*  73 */           response.getWriter().print(echostr);
/*     */         }
/*     */         catch (IOException e)
/*     */         {
/*  77 */           e.printStackTrace();
/*     */         }
/*     */     }
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/weixin"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public void wechatPost(HttpServletResponse response, HttpServletRequest request)
/*     */     throws IOException
/*     */   {
/*  86 */     Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
/*  87 */     String respMessage = this.wechatService.coreService(request);
/*  88 */     if (basConfig.getIsdebug().equals("1")) {
/*  89 */       log.info("Post request: " + request);
/*  90 */       log.info("response: " + respMessage);
/*     */     }
/*  92 */     PrintWriter out = response.getWriter();
/*  93 */     out.print(respMessage);
/*  94 */     out.close();
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/menu"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
/*     */   public void Menu(HttpServletRequest req, HttpServletResponse response)
/*     */   {
/* 121 */     Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
/*     */ 
/* 220 */     CommonButton commonButton = new CommonButton();
/* 221 */     commonButton.setName("上网验证");
/* 222 */     commonButton.setType("click");
/* 223 */     commonButton.setKey("GET_AUTH");
/* 224 */     Button[] b = new Button[1];
/* 225 */     b[0] = commonButton;
/*     */ 
/* 230 */     Menu menu = new Menu();
/* 231 */     menu.setButton(b);
/*     */ 
/* 233 */     JSONObject jsonMenu = JSONObject.fromObject(menu);
/* 234 */     String appid = ((WeixinAccount)this.weixinAccountService.getList(req).get(0)).getAppid();
/* 235 */     String appsecret = ((WeixinAccount)this.weixinAccountService.getList(req).get(0)).getAppsecret();
/*     */ 
/* 238 */     String accessToken = WeixinUtil.getAccessToken(this.weixinAccessTokenService, appid, appsecret).getToken();
/* 239 */     String url = WeixinUtil.menu_create_url.replace("ACCESS_TOKEN", 
/* 240 */       accessToken);
/* 241 */     JSONObject jsonObject = new JSONObject();
/*     */ 
/* 243 */     String message = null;
/* 244 */     String info = null;
/*     */     try {
/* 246 */       jsonObject = WeixinUtil.httpRequest(url, "POST", jsonMenu.toString());
/* 247 */       if (basConfig.getIsdebug().equals("1")) {
/* 248 */         log.info("Post Menu: " + jsonObject);
/*     */       }
/* 250 */       info = jsonObject.toString();
/* 251 */       if (jsonObject != null) {
/* 252 */         if (jsonObject.getInt("errcode") == 0) {
/* 253 */           message = "Syn Menu Success !!";
/*     */         }
/*     */         else
/* 256 */           message = "Syn Menu Error !! Error Code: " + jsonObject.getInt("errcode") + " Error Info: " + jsonObject.getString("errmsg");
/*     */       }
/*     */       else
/* 259 */         message = "Syn Menu Error !! Url is Error !!";
/*     */     }
/*     */     catch (Exception e) {
/* 262 */       message = "Syn Menu Error !!";
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 267 */       PrintWriter out = response.getWriter();
/* 268 */       out.print(info + "==========" + message);
/* 269 */       if (basConfig.getIsdebug().equals("1")) {
/* 270 */         log.info(info + "==========" + message);
/*     */       }
/* 272 */       out.close();
/*     */     }
/*     */     catch (IOException e) {
/* 275 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/user"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
/*     */   public void user(HttpServletRequest req, HttpServletResponse response)
/*     */   {
/* 291 */     Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
/* 292 */     String token = null;
/*     */ 
/* 296 */     String appid = ((WeixinAccount)this.weixinAccountService.getList(req).get(0)).getAppid();
/* 297 */     String appsecret = ((WeixinAccount)this.weixinAccountService.getList(req).get(0)).getAppsecret();
/* 298 */     token = WeixinUtil.getAccessToken(this.weixinAccessTokenService, appid, appsecret).getToken();
/*     */ 
/* 300 */     List<Wxuser> users = null;
/*     */     try
/*     */     {
/* 303 */       users = JwUserAPI.getAllWxuser(token, null);
/*     */     } catch (WexinReqException localWexinReqException) {
/*     */     }
/* 306 */     StringBuffer result = new StringBuffer();
/*     */ 
/* 308 */     if (users != null) {
/* 309 */       for (Wxuser wxuser : users) {
/* 310 */         result.append("County: " + wxuser.getCountry() + " City: " + wxuser.getCity() + " UserName: " + wxuser.getNickname());
/*     */       }
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 316 */       PrintWriter out = response.getWriter();
/* 317 */       out.print("GZH User Info::" + result);
/* 318 */       if (basConfig.getIsdebug().equals("1")) {
/* 319 */         log.info("GZH User Info::" + result);
/*     */       }
/* 321 */       out.close();
/*     */     }
/*     */     catch (IOException e) {
/* 324 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/ip"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
/*     */   public void ip(HttpServletRequest req, HttpServletResponse response)
/*     */   {
/* 336 */     String token = null;
/*     */ 
/* 340 */     String appid = ((WeixinAccount)this.weixinAccountService.getList(req).get(0)).getAppid();
/* 341 */     String appsecret = ((WeixinAccount)this.weixinAccountService.getList(req).get(0)).getAppsecret();
/* 342 */     token = WeixinUtil.getAccessToken(this.weixinAccessTokenService, appid, appsecret).getToken();
/*     */     try
/*     */     {
/* 347 */       List<String> ips = JwServiceIpAPI.getServiceIpList(token);
/* 348 */       StringBuffer result = new StringBuffer();
/*     */ 
/* 350 */       if (ips != null) {
/* 351 */         for (String ip : ips) {
/* 352 */           result.append("ip:" + ip + "****");
/*     */         }
/*     */       }
/*     */ 
/* 356 */       PrintWriter out = response.getWriter();
/* 357 */       out.print("WeiXin Server IP: " + result);
/* 358 */       out.close();
/*     */     } catch (Exception e) {
/* 360 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/kf"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
/*     */   public void kf(HttpServletRequest req, HttpServletResponse response)
/*     */   {
/* 371 */     String fromUserName = "oSzCquAYiRNbfQrVuLVKpaTWTKJM";
/* 372 */     String text = "感谢您使用我们的无线网络，公众号认证方式继续 回复【上网】或者 点击【上网验证】菜单，获取联网权限！！";
/* 373 */     String jsonT = "{\"touser\":\"" + fromUserName + "\",\"msgtype\":\"text\",\"text\":{\"content\":\"" + text + "\"}}";
/* 374 */     String appid = ((WeixinAccount)this.weixinAccountService.getList(req).get(0)).getAppid();
/* 375 */     String appsecret = ((WeixinAccount)this.weixinAccountService.getList(req).get(0)).getAppsecret();
/* 376 */     String accessToken = WeixinUtil.getAccessToken(this.weixinAccessTokenService, appid, appsecret).getToken();
/* 377 */     System.out.println("token=" + accessToken);
/* 378 */     System.out.println("Post KF API !!");
/* 379 */     System.out.println(jsonT);
/* 380 */     WeixinUtil.httpRequest("https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + accessToken, "POST", jsonT);
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     weixin.guanjia.core.controller.WechatController
 * JD-Core Version:    0.6.2
 */