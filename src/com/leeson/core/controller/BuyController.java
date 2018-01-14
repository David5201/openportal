/*      */ package com.leeson.core.controller;
/*      */ 
/*      */ import com.alipay.config.AlipayConfig;
/*      */ import com.alipay.util.AlipayNotify;
/*      */ import com.alipay.util.AlipaySubmit;
/*      */ import com.leeson.common.utils.stringUtils;
/*      */ import com.leeson.core.bean.Config;
/*      */ import com.leeson.core.bean.Payapi;
/*      */ import com.leeson.core.bean.Portalaccount;
/*      */ import com.leeson.core.bean.Portalcard;
/*      */ import com.leeson.core.bean.Portalcardcategory;
/*      */ import com.leeson.core.bean.Portalorder;
/*      */ import com.leeson.core.query.PortalaccountQuery;
/*      */ import com.leeson.core.query.PortalcardQuery;
/*      */ import com.leeson.core.query.PortalcardcategoryQuery;
/*      */ import com.leeson.core.query.PortalspeedQuery;
/*      */ import com.leeson.core.service.ConfigService;
/*      */ import com.leeson.core.service.PayapiService;
/*      */ import com.leeson.core.service.PortalaccountService;
/*      */ import com.leeson.core.service.PortalcardService;
/*      */ import com.leeson.core.service.PortalcardcategoryService;
/*      */ import com.leeson.core.service.PortalorderService;
/*      */ import com.leeson.core.service.PortalspeedService;
/*      */ import com.leeson.core.utils.HttpsUtils;
/*      */ import com.leeson.portal.core.model.isDo;
/*      */ import com.leeson.portal.core.utils.GetNgnixRemotIP;
/*      */ import com.wxpay.Core;
/*      */ import com.wxpay.Notify;
/*      */ import com.wxpay.bean.WxPayDto;
/*      */ import com.wxpay.bean.WxPayResult;
/*      */ import com.wxpay.utils.RequestHandler;
/*      */ import java.io.BufferedOutputStream;
/*      */ import java.io.BufferedReader;
/*      */ import java.io.PrintStream;
/*      */ import java.io.PrintWriter;
/*      */ import java.io.UnsupportedEncodingException;
/*      */ import java.util.Date;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.SortedMap;
/*      */ import java.util.TreeMap;
/*      */ import java.util.UUID;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpServletResponse;
/*      */ import javax.servlet.http.HttpSession;
/*      */ import net.sf.json.JSONObject;
/*      */ import org.apache.log4j.Logger;
/*      */ import org.springframework.beans.factory.annotation.Autowired;
/*      */ import org.springframework.stereotype.Controller;
/*      */ import org.springframework.ui.ModelMap;
/*      */ import org.springframework.web.bind.annotation.RequestMapping;
/*      */ 
/*      */ @Controller
/*      */ public class BuyController
/*      */ {
/*      */ 
/*      */   @Autowired
/*      */   private PortalcardService portalcardService;
/*      */ 
/*      */   @Autowired
/*      */   private PortalcardcategoryService portalcardcategoryService;
/*      */ 
/*      */   @Autowired
/*      */   private PortalaccountService portalaccountService;
/*      */ 
/*      */   @Autowired
/*      */   private PortalorderService portalorderService;
/*      */ 
/*      */   @Autowired
/*      */   private ConfigService configService;
/*      */ 
/*      */   @Autowired
/*      */   private PortalspeedService portalspeedService;
/*      */ 
/*      */   @Autowired
/*      */   private PayapiService payapiService;
/*   81 */   private static Logger logger = Logger.getLogger(BuyController.class);
/*      */ 
/*      */   @RequestMapping({"/buy.action"})
/*      */   public String buy(ModelMap model)
/*      */   {
/*   86 */     List list = this.portalcardcategoryService
/*   87 */       .getPortalcardcategoryList(new PortalcardcategoryQuery());
/*   88 */     model.addAttribute("list", list);
/*   89 */     List speeds = this.portalspeedService
/*   90 */       .getPortalspeedList(new PortalspeedQuery());
/*   91 */     model.addAttribute("speeds", speeds);
/*   92 */     return "buy/buy";
/*      */   }
/*      */ 
/*      */   @RequestMapping({"/goBuy.action"})
/*      */   public String goBuy(Long id, ModelMap model, HttpServletRequest request)
/*      */   {
/*   98 */     if (Do()) {
/*   99 */       if (id == null) {
/*  100 */         List list = this.portalcardcategoryService
/*  101 */           .getPortalcardcategoryList(new PortalcardcategoryQuery());
/*  102 */         model.addAttribute("list", list);
/*  103 */         model.addAttribute("msg", "该充值卡不存在！！");
/*  104 */         return "buy/buy";
/*      */       }
/*      */ 
/*  107 */       Portalcardcategory card = this.portalcardcategoryService
/*  108 */         .getPortalcardcategoryByKey(id);
/*  109 */       if (card == null) {
/*  110 */         List list = this.portalcardcategoryService
/*  111 */           .getPortalcardcategoryList(new PortalcardcategoryQuery());
/*  112 */         model.addAttribute("list", list);
/*  113 */         model.addAttribute("msg", "该充值卡不存在！！");
/*  114 */         return "buy/buy";
/*      */       }
/*      */ 
/*  117 */       HttpSession session = request.getSession();
/*  118 */       String un = (String)session.getAttribute("username");
/*  119 */       String pwd = (String)session.getAttribute("password");
/*  120 */       if ((stringUtils.isBlank(un)) || (stringUtils.isBlank(pwd))) {
/*  121 */         model.addAttribute("msg", "用户验证失败，请先登录！！");
/*  122 */         return "portalcustomer/login";
/*      */       }
/*  124 */       PortalaccountQuery aq = new PortalaccountQuery();
/*  125 */       aq.setLoginName(un);
/*  126 */       aq.setPassword(pwd);
/*  127 */       List accounts = this.portalaccountService
/*  128 */         .getPortalaccountList(aq);
/*  129 */       if (accounts.size() != 1) {
/*  130 */         model.addAttribute("msg", "用户验证失败，请先登录！！");
/*  131 */         return "portalcustomer/login";
/*      */       }
/*  133 */       Portalaccount acc = (Portalaccount)accounts.get(0);
/*  134 */       Long userId = acc.getId();
/*  135 */       Long cardId = card.getId();
/*      */ 
/*  137 */       session.setAttribute("userId", userId);
/*  138 */       session.setAttribute("cardId", cardId);
/*      */ 
/*  140 */       model.addAttribute("user", acc);
/*  141 */       model.addAttribute("card", card);
/*      */ 
/*  143 */       List speeds = this.portalspeedService
/*  144 */         .getPortalspeedList(new PortalspeedQuery());
/*  145 */       model.addAttribute("speeds", speeds);
/*      */ 
/*  147 */       Payapi payapi = this.payapiService.getPayapiByKey(Long.valueOf(1L));
/*  148 */       int alipay = payapi.getAlipay().intValue();
/*  149 */       int wxpay = payapi.getWeixin().intValue();
/*  150 */       model.addAttribute("alipay", Integer.valueOf(alipay));
/*  151 */       model.addAttribute("wxpay", Integer.valueOf(wxpay));
/*      */ 
/*  153 */       return "buy/goBuy";
/*      */     }
/*  155 */     List list = this.portalcardcategoryService
/*  156 */       .getPortalcardcategoryList(new PortalcardcategoryQuery());
/*  157 */     model.addAttribute("list", list);
/*  158 */     model.addAttribute("msg", "系统未授权或已过期！！");
/*  159 */     return "buy/buy";
/*      */   }
/*      */ 
/*      */   @RequestMapping({"/goAlipay.action"})
/*      */   public String goAlipay(ModelMap model, HttpServletRequest request)
/*      */   {
/*  167 */     if (Do()) {
/*  168 */       HttpSession session = request.getSession();
/*      */       try
/*      */       {
/*  171 */         Long userID = Long.valueOf(((Long)session.getAttribute("userId")).longValue());
/*  172 */         Long cardID = Long.valueOf(((Long)session.getAttribute("cardId")).longValue());
/*      */ 
/*  174 */         if (cardID == null) {
/*  175 */           List list = this.portalcardcategoryService
/*  176 */             .getPortalcardcategoryList(new PortalcardcategoryQuery());
/*  177 */           model.addAttribute("list", list);
/*  178 */           model.addAttribute("msg", "该充值卡不存在！！");
/*  179 */           return "buy/buy";
/*      */         }
/*      */ 
/*  182 */         Portalcardcategory card = this.portalcardcategoryService
/*  183 */           .getPortalcardcategoryByKey(cardID);
/*  184 */         if (card == null) {
/*  185 */           List list = this.portalcardcategoryService
/*  186 */             .getPortalcardcategoryList(new PortalcardcategoryQuery());
/*  187 */           model.addAttribute("list", list);
/*  188 */           model.addAttribute("msg", "该充值卡不存在！！");
/*  189 */           return "buy/buy";
/*      */         }
/*      */ 
/*  192 */         Portalaccount account = this.portalaccountService
/*  193 */           .getPortalaccountByKey(userID);
/*  194 */         if (account == null) {
/*  195 */           model.addAttribute("msg", "用户验证失败，请先登录！！");
/*  196 */           return "portalcustomer/login";
/*      */         }
/*      */ 
/*  200 */         String categoryType = card.getState();
/*      */         String payType;
/*  202 */         if (categoryType.equals("0")) {
/*  203 */           payType = "2";
/*      */         }
/*      */         else
/*      */         {
/*  204 */           if (categoryType.equals("4"))
/*  205 */             payType = "4";
/*      */           else {
/*  207 */             payType = "3";
/*      */           }
/*      */         }
/*  210 */         if (card.getMoney() == null) {
/*  211 */           card.setMoney(Double.valueOf(0.0D));
/*      */         }
/*      */ 
/*  214 */         Long time = card.getTime();
/*  215 */         Long payTime = Long.valueOf(0L);
/*  216 */         if (categoryType.equals("0"))
/*  217 */           payTime = Long.valueOf(time.longValue() * 1000L * 60L * 60L);
/*  218 */         else if (categoryType.equals("1"))
/*  219 */           payTime = Long.valueOf(time.longValue() * 1000L * 60L * 60L * 24L);
/*  220 */         else if (categoryType.equals("2"))
/*  221 */           payTime = Long.valueOf(time.longValue() * 1000L * 60L * 60L * 24L * 31L);
/*  222 */         else if (categoryType.equals("3"))
/*  223 */           payTime = Long.valueOf(time.longValue() * 1000L * 60L * 60L * 24L * 31L * 12L);
/*  224 */         else if (categoryType.equals("4")) {
/*  225 */           payTime = Long.valueOf(time.longValue() * 1024L * 1024L);
/*      */         }
/*      */ 
/*  228 */         String cdkey = UUID.randomUUID().toString();
/*  229 */         Portalcard cardOrder = new Portalcard();
/*  230 */         cardOrder.setName(card.getName());
/*  231 */         cardOrder.setDescription(card.getDescription());
/*  232 */         cardOrder.setCategoryType(categoryType);
/*  233 */         cardOrder.setPayType(payType);
/*  234 */         cardOrder.setPayTime(payTime);
/*  235 */         cardOrder.setAccountDel(Integer.valueOf(0));
/*  236 */         cardOrder.setUserDel(Integer.valueOf(0));
/*  237 */         cardOrder.setCdKey(cdkey);
/*  238 */         cardOrder.setMoney(card.getMoney());
/*      */ 
/*  240 */         cardOrder.setState("-1");
/*  241 */         cardOrder.setBuyDate(new Date());
/*  242 */         cardOrder.setAccountName(account.getLoginName());
/*  243 */         cardOrder.setAccountId(userID);
/*      */ 
/*  245 */         cardOrder.setMaclimit(card.getMaclimit());
/*  246 */         cardOrder.setMaclimitcount(card.getMaclimitcount());
/*  247 */         cardOrder.setAutologin(card.getAutologin());
/*  248 */         cardOrder.setSpeed(card.getSpeed());
/*      */ 
/*  250 */         this.portalcardService.addPortalcard(cardOrder);
/*      */ 
/*  255 */         String out_trade_no = cdkey;
/*      */ 
/*  258 */         String subject = card.getName();
/*      */ 
/*  261 */         String total_fee = String.valueOf(card.getMoney());
/*      */ 
/*  264 */         String body = card.getDescription();
/*      */ 
/*  267 */         String return_url = request.getScheme() + "://" + 
/*  268 */           request.getServerName() + ":" + 
/*  269 */           request.getServerPort() + request.getContextPath() + 
/*  270 */           "/returnAlipay.action";
/*      */ 
/*  273 */         String notify_url = this.configService.getConfigByKey(Long.valueOf(1L)).getDomain() + 
/*  274 */           "/returnAlipayNotify.action";
/*      */ 
/*  279 */         Payapi payapi = this.payapiService.getPayapiByKey(Long.valueOf(1L));
/*      */ 
/*  281 */         Map sParaTemp = new HashMap();
/*  282 */         sParaTemp.put("service", AlipayConfig.service);
/*  283 */         sParaTemp.put("partner", payapi.getAlipayPartner());
/*  284 */         sParaTemp.put("seller_id", payapi.getAlipayPartner());
/*  285 */         sParaTemp.put("_input_charset", AlipayConfig.input_charset);
/*  286 */         sParaTemp.put("payment_type", AlipayConfig.payment_type);
/*  287 */         sParaTemp.put("notify_url", notify_url);
/*  288 */         sParaTemp.put("return_url", return_url);
/*  289 */         sParaTemp.put("anti_phishing_key", 
/*  290 */           AlipayConfig.anti_phishing_key);
/*      */ 
/*  293 */         sParaTemp.put("exter_invoke_ip", 
/*  294 */           GetNgnixRemotIP.getRemoteAddrIp(request));
/*  295 */         sParaTemp.put("out_trade_no", out_trade_no);
/*  296 */         sParaTemp.put("subject", subject);
/*  297 */         sParaTemp.put("total_fee", total_fee);
/*  298 */         sParaTemp.put("body", body);
/*      */ 
/*  303 */         String sHtmlText = AlipaySubmit.buildRequest(sParaTemp, "get", 
/*  304 */           "确认");
/*  305 */         model.addAttribute("web", sHtmlText);
/*  306 */         model.addAttribute("cid", cardOrder.getId());
/*      */ 
/*  308 */         if (isPortalWXAuthInner(request)) {
/*  309 */           model.addAttribute("weixin", Integer.valueOf(1));
/*  310 */           model.addAttribute("ones", Integer.valueOf(1));
/*      */         } else {
/*  312 */           model.addAttribute("weixin", Integer.valueOf(0));
/*      */         }
/*  314 */         return "buy/goAlipay";
/*      */       } catch (Exception e) {
/*  316 */         List list = this.portalcardcategoryService
/*  317 */           .getPortalcardcategoryList(new PortalcardcategoryQuery());
/*  318 */         model.addAttribute("list", list);
/*  319 */         model.addAttribute("msg", "发生错误！！");
/*  320 */         return "buy/buy";
/*      */       }
/*      */     }
/*  323 */     List list = this.portalcardcategoryService
/*  324 */       .getPortalcardcategoryList(new PortalcardcategoryQuery());
/*  325 */     model.addAttribute("list", list);
/*  326 */     model.addAttribute("msg", "系统未授权或已过期！！");
/*  327 */     return "buy/buy";
/*      */   }
/*      */ 
/*      */   @RequestMapping({"/goAlipayUrl.action"})
/*      */   public String goAlipay(Long id, ModelMap model, HttpServletRequest request)
/*      */   {
/*  335 */     if (Do()) {
/*      */       try {
/*  337 */         if (id == null) {
/*  338 */           List list = this.portalcardcategoryService
/*  339 */             .getPortalcardcategoryList(new PortalcardcategoryQuery());
/*  340 */           model.addAttribute("list", list);
/*  341 */           model.addAttribute("msg", "该充值卡不存在！！");
/*  342 */           return "buy/buy";
/*      */         }
/*      */ 
/*  345 */         Portalcard card = this.portalcardService.getPortalcardByKey(id);
/*      */ 
/*  347 */         if (card == null) {
/*  348 */           List list = this.portalcardcategoryService
/*  349 */             .getPortalcardcategoryList(new PortalcardcategoryQuery());
/*  350 */           model.addAttribute("list", list);
/*  351 */           model.addAttribute("msg", "该充值卡不存在！！");
/*  352 */           return "buy/buy";
/*      */         }
/*      */ 
/*  355 */         if (!"-1".equals(card.getState())) {
/*  356 */           List list = this.portalcardcategoryService
/*  357 */             .getPortalcardcategoryList(new PortalcardcategoryQuery());
/*  358 */           model.addAttribute("list", list);
/*  359 */           model.addAttribute("msg", "该订单已支付！！");
/*  360 */           return "buy/buy";
/*      */         }
/*      */ 
/*  365 */         String out_trade_no = card.getCdKey();
/*      */ 
/*  368 */         String subject = card.getName();
/*      */ 
/*  371 */         String total_fee = String.valueOf(card.getMoney());
/*      */ 
/*  374 */         String body = card.getDescription();
/*      */ 
/*  377 */         String return_url = request.getScheme() + "://" + 
/*  378 */           request.getServerName() + ":" + 
/*  379 */           request.getServerPort() + request.getContextPath() + 
/*  380 */           "/returnAlipay.action";
/*      */ 
/*  383 */         String notify_url = this.configService.getConfigByKey(Long.valueOf(1L)).getDomain() + 
/*  384 */           "/returnAlipayNotify.action";
/*      */ 
/*  389 */         Payapi payapi = this.payapiService.getPayapiByKey(Long.valueOf(1L));
/*      */ 
/*  391 */         Map sParaTemp = new HashMap();
/*  392 */         sParaTemp.put("service", AlipayConfig.service);
/*  393 */         sParaTemp.put("partner", payapi.getAlipayPartner());
/*  394 */         sParaTemp.put("seller_id", payapi.getAlipayPartner());
/*  395 */         sParaTemp.put("_input_charset", AlipayConfig.input_charset);
/*  396 */         sParaTemp.put("payment_type", AlipayConfig.payment_type);
/*  397 */         sParaTemp.put("notify_url", notify_url);
/*  398 */         sParaTemp.put("return_url", return_url);
/*  399 */         sParaTemp.put("anti_phishing_key", 
/*  400 */           AlipayConfig.anti_phishing_key);
/*      */ 
/*  403 */         sParaTemp.put("exter_invoke_ip", 
/*  404 */           GetNgnixRemotIP.getRemoteAddrIp(request));
/*  405 */         sParaTemp.put("out_trade_no", out_trade_no);
/*  406 */         sParaTemp.put("subject", subject);
/*  407 */         sParaTemp.put("total_fee", total_fee);
/*  408 */         sParaTemp.put("body", body);
/*      */ 
/*  413 */         String sHtmlText = AlipaySubmit.buildRequest(sParaTemp, "get", 
/*  414 */           "确认");
/*  415 */         model.addAttribute("web", sHtmlText);
/*      */ 
/*  417 */         model.addAttribute("cid", id);
/*  418 */         if (isPortalWXAuthInner(request)) {
/*  419 */           model.addAttribute("weixin", Integer.valueOf(1));
/*  420 */           model.addAttribute("ones", Integer.valueOf(0));
/*      */         } else {
/*  422 */           model.addAttribute("weixin", Integer.valueOf(0));
/*      */         }
/*  424 */         return "buy/goAlipay";
/*      */       } catch (Exception e) {
/*  426 */         List list = this.portalcardcategoryService
/*  427 */           .getPortalcardcategoryList(new PortalcardcategoryQuery());
/*  428 */         model.addAttribute("list", list);
/*  429 */         model.addAttribute("msg", "发生错误！！");
/*  430 */         return "buy/buy";
/*      */       }
/*      */     }
/*  433 */     List list = this.portalcardcategoryService
/*  434 */       .getPortalcardcategoryList(new PortalcardcategoryQuery());
/*  435 */     model.addAttribute("list", list);
/*  436 */     model.addAttribute("msg", "系统未授权或已过期！！");
/*  437 */     return "buy/buy";
/*      */   }
/*      */ 
/*      */   @RequestMapping({"/returnAlipayNotify.action"})
/*      */   public void returnAlipayNotify(ModelMap model, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/*  446 */     if (Do())
/*      */       try
/*      */       {
/*  449 */         Map params = new HashMap();
/*  450 */         Map requestParams = request.getParameterMap();
/*  451 */         Iterator iter = requestParams.keySet().iterator();
/*  452 */         while (iter
/*  452 */           .hasNext()) {
/*  453 */           String name = (String)iter.next();
/*  454 */           String[] values = (String[])requestParams.get(name);
/*  455 */           String valueStr = "";
/*  456 */           for (int i = 0; i < values.length; i++) {
/*  457 */             valueStr = 
/*  458 */               valueStr + values[i] + ",";
/*      */           }
/*      */ 
/*  463 */           params.put(name, valueStr);
/*  464 */           logger.info("returnAlipayNotify Info : " + name + "=" + 
/*  465 */             valueStr);
/*      */         }
/*      */ 
/*  471 */         String out_trade_no = request.getParameter("out_trade_no");
/*  472 */         String cardKey = out_trade_no;
/*      */ 
/*  476 */         String trade_no = request.getParameter("trade_no");
/*  477 */         String buyer = request.getParameter("buyer_email");
/*  478 */         String seller = request.getParameter("seller_email");
/*      */ 
/*  481 */         String trade_status = request.getParameter("trade_status");
/*      */ 
/*  485 */         if (AlipayNotify.verify(params))
/*      */         {
/*  491 */           if (trade_status.equals("TRADE_FINISHED"))
/*      */           {
/*  496 */             if (stringUtils.isNotBlank(cardKey)) {
/*  497 */               Boolean boo = Boolean.valueOf(false);
/*  498 */               PortalcardQuery cq = new PortalcardQuery();
/*  499 */               cq.setCdKey(cardKey);
/*  500 */               cq.setCdKeyLike(false);
/*  501 */               cq.setStateLike(false);
/*  502 */               cq.setState("-1");
/*  503 */               List cards = this.portalcardService
/*  504 */                 .getPortalcardList(cq);
/*  505 */               if ((cards != null) && (cards.size() == 1)) {
/*  506 */                 boo = Boolean.valueOf(true);
/*      */               }
/*  508 */               if (boo.booleanValue()) {
/*  509 */                 Portalcard card = (Portalcard)cards.get(0);
/*  510 */                 Long aid = card.getAccountId();
/*  511 */                 if (aid != null) {
/*  512 */                   Portalaccount e = this.portalaccountService
/*  513 */                     .getPortalaccountByKey(aid);
/*  514 */                   if (e != null) {
/*  515 */                     String payType = card.getPayType();
/*  516 */                     Long payTime = card.getPayTime();
/*  517 */                     String state = e.getState();
/*  518 */                     Long oldDate = Long.valueOf(e.getDate().getTime());
/*  519 */                     Long oldTime = e.getTime();
/*  520 */                     Long oldOctets = e.getOctets();
/*      */ 
/*  522 */                     if (oldOctets == null) {
/*  523 */                       oldOctets = Long.valueOf(0L);
/*      */                     }
/*  525 */                     if (oldTime == null) {
/*  526 */                       oldTime = Long.valueOf(0L);
/*      */                     }
/*      */ 
/*  529 */                     Long now = Long.valueOf(new Date().getTime());
/*      */ 
/*  533 */                     if (payType.equals("3"))
/*      */                     {
/*      */                       Long newDate;
/*  535 */                       if (oldDate.longValue() < new Date().getTime())
/*  536 */                         newDate = Long.valueOf(now.longValue() + payTime.longValue());
/*      */                       else {
/*  538 */                         newDate = Long.valueOf(oldDate.longValue() + payTime.longValue());
/*      */                       }
/*  540 */                       e.setDate(new Date(newDate.longValue()));
/*  541 */                       e.setState(payType);
/*      */                     }
/*      */ 
/*  544 */                     if (payType.equals("2")) {
/*  545 */                       if (oldTime.longValue() < 0L)
/*  546 */                         e.setTime(payTime);
/*      */                       else {
/*  548 */                         e.setTime(Long.valueOf(oldTime.longValue() + payTime.longValue()));
/*      */                       }
/*  550 */                       e.setState(payType);
/*      */                     }
/*      */ 
/*  553 */                     if (payType.equals("4")) {
/*  554 */                       if (oldOctets.longValue() < 0L)
/*  555 */                         e.setOctets(payTime);
/*      */                       else {
/*  557 */                         e.setOctets(Long.valueOf(oldOctets.longValue() + payTime.longValue()));
/*      */                       }
/*  559 */                       e.setState(payType);
/*      */                     }
/*      */ 
/*  562 */                     if (state.equals("1")) {
/*  563 */                       e.setState(state);
/*      */                     }
/*      */ 
/*  566 */                     e.setMaclimit(card.getMaclimit());
/*  567 */                     e.setMaclimitcount(card
/*  568 */                       .getMaclimitcount());
/*  569 */                     e.setAutologin(card.getAutologin());
/*  570 */                     e.setSpeed(card.getSpeed());
/*      */ 
/*  572 */                     this.portalaccountService
/*  573 */                       .updatePortalaccountByKey(e);
/*  574 */                     card.setAccountId(aid);
/*  575 */                     card.setAccountName(e.getLoginName());
/*  576 */                     card.setState("2");
/*  577 */                     card.setPayDate(new Date());
/*  578 */                     this.portalcardService
/*  579 */                       .updatePortalcardByKey(card);
/*      */ 
/*  582 */                     Portalorder order = new Portalorder();
/*  583 */                     order.setAccountDel(card
/*  584 */                       .getAccountDel());
/*  585 */                     order.setAccountId(card.getAccountId());
/*  586 */                     order.setAccountName(card
/*  587 */                       .getAccountName());
/*  588 */                     order.setBuyDate(card.getBuyDate());
/*  589 */                     order.setBuyer(buyer);
/*  590 */                     order.setCategoryType(card
/*  591 */                       .getCategoryType());
/*  592 */                     order.setCdKey(card.getCdKey());
/*  593 */                     order.setDescription(card
/*  594 */                       .getDescription());
/*  595 */                     order.setMoney(card.getMoney());
/*  596 */                     order.setName(card.getName());
/*  597 */                     order.setPayby(Integer.valueOf(1));
/*  598 */                     order.setPayDate(card.getPayDate());
/*  599 */                     order.setPayTime(card.getPayTime());
/*  600 */                     order.setPayType(card.getPayType());
/*  601 */                     order.setSeller(seller);
/*  602 */                     order.setState("1");
/*  603 */                     order.setTradeno(trade_no);
/*  604 */                     order.setUserDel(card.getUserDel());
/*  605 */                     this.portalorderService
/*  606 */                       .addPortalorder(order);
/*      */                   }
/*      */                 }
/*      */               }
/*      */ 
/*      */             }
/*      */ 
/*      */           }
/*  614 */           else if (trade_status.equals("TRADE_SUCCESS"))
/*      */           {
/*  619 */             if (stringUtils.isNotBlank(cardKey)) {
/*  620 */               Boolean boo = Boolean.valueOf(false);
/*  621 */               PortalcardQuery cq = new PortalcardQuery();
/*  622 */               cq.setCdKey(cardKey);
/*  623 */               cq.setCdKeyLike(false);
/*  624 */               cq.setStateLike(false);
/*  625 */               cq.setState("-1");
/*  626 */               List cards = this.portalcardService
/*  627 */                 .getPortalcardList(cq);
/*  628 */               if ((cards != null) && (cards.size() == 1)) {
/*  629 */                 boo = Boolean.valueOf(true);
/*      */               }
/*  631 */               if (boo.booleanValue()) {
/*  632 */                 Portalcard card = (Portalcard)cards.get(0);
/*  633 */                 Long aid = card.getAccountId();
/*  634 */                 if (aid != null) {
/*  635 */                   Portalaccount e = this.portalaccountService
/*  636 */                     .getPortalaccountByKey(aid);
/*  637 */                   if (e != null) {
/*  638 */                     String payType = card.getPayType();
/*  639 */                     Long payTime = card.getPayTime();
/*  640 */                     String state = e.getState();
/*  641 */                     Long oldDate = Long.valueOf(e.getDate().getTime());
/*  642 */                     Long oldTime = e.getTime();
/*  643 */                     Long oldOctets = e.getOctets();
/*      */ 
/*  645 */                     if (oldOctets == null) {
/*  646 */                       oldOctets = Long.valueOf(0L);
/*      */                     }
/*  648 */                     if (oldTime == null) {
/*  649 */                       oldTime = Long.valueOf(0L);
/*      */                     }
/*      */ 
/*  652 */                     Long now = Long.valueOf(new Date().getTime());
/*      */ 
/*  656 */                     if (payType.equals("3"))
/*      */                     {
/*      */                       Long newDate;
/*  658 */                       if (oldDate.longValue() < new Date().getTime())
/*  659 */                         newDate = Long.valueOf(now.longValue() + payTime.longValue());
/*      */                       else {
/*  661 */                         newDate = Long.valueOf(oldDate.longValue() + payTime.longValue());
/*      */                       }
/*  663 */                       e.setDate(new Date(newDate.longValue()));
/*  664 */                       e.setState(payType);
/*      */                     }
/*      */ 
/*  667 */                     if (payType.equals("2")) {
/*  668 */                       if (oldTime.longValue() < 0L)
/*  669 */                         e.setTime(payTime);
/*      */                       else {
/*  671 */                         e.setTime(Long.valueOf(oldTime.longValue() + payTime.longValue()));
/*      */                       }
/*  673 */                       e.setState(payType);
/*      */                     }
/*      */ 
/*  676 */                     if (payType.equals("4")) {
/*  677 */                       if (oldOctets.longValue() < 0L)
/*  678 */                         e.setOctets(payTime);
/*      */                       else {
/*  680 */                         e.setOctets(Long.valueOf(oldOctets.longValue() + payTime.longValue()));
/*      */                       }
/*  682 */                       e.setState(payType);
/*      */                     }
/*      */ 
/*  685 */                     if (state.equals("1")) {
/*  686 */                       e.setState(state);
/*      */                     }
/*      */ 
/*  689 */                     e.setMaclimit(card.getMaclimit());
/*  690 */                     e.setMaclimitcount(card
/*  691 */                       .getMaclimitcount());
/*  692 */                     e.setAutologin(card.getAutologin());
/*  693 */                     e.setSpeed(card.getSpeed());
/*      */ 
/*  695 */                     this.portalaccountService
/*  696 */                       .updatePortalaccountByKey(e);
/*  697 */                     card.setAccountId(aid);
/*  698 */                     card.setAccountName(e.getLoginName());
/*  699 */                     card.setState("2");
/*  700 */                     card.setPayDate(new Date());
/*  701 */                     this.portalcardService
/*  702 */                       .updatePortalcardByKey(card);
/*      */ 
/*  705 */                     Portalorder order = new Portalorder();
/*  706 */                     order.setAccountDel(card
/*  707 */                       .getAccountDel());
/*  708 */                     order.setAccountId(card.getAccountId());
/*  709 */                     order.setAccountName(card
/*  710 */                       .getAccountName());
/*  711 */                     order.setBuyDate(card.getBuyDate());
/*  712 */                     order.setBuyer(buyer);
/*  713 */                     order.setCategoryType(card
/*  714 */                       .getCategoryType());
/*  715 */                     order.setCdKey(card.getCdKey());
/*  716 */                     order.setDescription(card
/*  717 */                       .getDescription());
/*  718 */                     order.setMoney(card.getMoney());
/*  719 */                     order.setName(card.getName());
/*  720 */                     order.setPayby(Integer.valueOf(1));
/*  721 */                     order.setPayDate(card.getPayDate());
/*  722 */                     order.setPayTime(card.getPayTime());
/*  723 */                     order.setPayType(card.getPayType());
/*  724 */                     order.setSeller(seller);
/*  725 */                     order.setState("1");
/*  726 */                     order.setTradeno(trade_no);
/*  727 */                     order.setUserDel(card.getUserDel());
/*  728 */                     this.portalorderService
/*  729 */                       .addPortalorder(order);
/*      */                   }
/*      */ 
/*      */                 }
/*      */ 
/*      */               }
/*      */ 
/*      */             }
/*      */ 
/*      */           }
/*      */ 
/*  741 */           String respMessage = "success";
/*  742 */           PrintWriter out = response.getWriter();
/*  743 */           out.print(respMessage);
/*  744 */           out.close();
/*      */         }
/*      */         else
/*      */         {
/*  748 */           String respMessage = "fail";
/*  749 */           PrintWriter out = response.getWriter();
/*  750 */           out.print(respMessage);
/*  751 */           out.close();
/*      */         }
/*      */       } catch (Exception e) {
/*      */         try {
/*  755 */           String respMessage = "fail";
/*  756 */           PrintWriter out = response.getWriter();
/*  757 */           out.print(respMessage);
/*  758 */           out.close();
/*      */         }
/*      */         catch (Exception localException1) {
/*      */         }
/*      */       }
/*      */     else try {
/*  764 */         String respMessage = "fail";
/*  765 */         PrintWriter out = response.getWriter();
/*  766 */         out.print(respMessage);
/*  767 */         out.close();
/*      */       }
/*      */       catch (Exception localException2)
/*      */       {
/*      */       }
/*      */   }
/*      */ 
/*      */   @RequestMapping({"/returnAlipay.action"})
/*      */   public String returnAlipay(ModelMap model, HttpServletRequest request)
/*      */   {
/*  778 */     if (Do())
/*      */     {
/*  780 */       Map params = new HashMap();
/*  781 */       Map requestParams = request.getParameterMap();
/*  782 */       Iterator iter = requestParams.keySet().iterator();
/*  783 */       while (iter
/*  783 */         .hasNext()) {
/*  784 */         String name = (String)iter.next();
/*  785 */         String[] values = (String[])requestParams.get(name);
/*  786 */         String valueStr = "";
/*  787 */         for (int i = 0; i < values.length; i++) {
/*  788 */           valueStr = 
/*  789 */             valueStr + values[i] + ",";
/*      */         }
/*      */         try
/*      */         {
/*  793 */           valueStr = new String(valueStr.getBytes("ISO-8859-1"), 
/*  794 */             "utf-8");
/*      */         }
/*      */         catch (UnsupportedEncodingException e) {
/*  797 */           logger.info("returnAlipay Code ISO-8859-1 to UTF-8 Error : " + 
/*  798 */             e);
/*      */         }
/*  800 */         params.put(name, valueStr);
/*  801 */         logger.info("returnAlipay Info : " + name + "=" + valueStr);
/*      */       }
/*      */ 
/*  815 */       String trade_status = request.getParameter("trade_status");
/*      */ 
/*  819 */       if (AlipayNotify.verify(params))
/*      */       {
/*  825 */         if (!trade_status.equals("TRADE_FINISHED"))
/*      */         {
/*  896 */           trade_status.equals("TRADE_SUCCESS");
/*      */         }
/*      */ 
/*  970 */         HttpSession session = request.getSession();
/*  971 */         Portalaccount e = new Portalaccount();
/*  972 */         String un = (String)session.getAttribute("username");
/*  973 */         String pwd = (String)session.getAttribute("password");
/*      */ 
/*  975 */         if ((stringUtils.isNotBlank(un)) && (stringUtils.isNotBlank(pwd))) {
/*  976 */           PortalaccountQuery aq = new PortalaccountQuery();
/*  977 */           aq.setLoginNameLike(false);
/*  978 */           aq.setPasswordLike(false);
/*      */ 
/*  980 */           aq.setLoginName(un);
/*  981 */           aq.setPassword(pwd);
/*  982 */           List accounts = this.portalaccountService.getPortalaccountList(aq);
/*  983 */           if (accounts.size() == 1) {
/*  984 */             e = (Portalaccount)accounts.get(0);
/*      */           }
/*      */         }
/*  987 */         String isOnline = "在线";
/*  988 */         model.addAttribute("entity", e);
/*  989 */         model.addAttribute("isOnline", isOnline);
/*  990 */         model.addAttribute("msg", "恭喜您，充值成功！");
/*  991 */         return "portalcustomer/index";
/*      */       }
/*      */ 
/*  994 */       HttpSession session = request.getSession();
/*  995 */       Portalaccount e = new Portalaccount();
/*  996 */       String un = (String)session.getAttribute("username");
/*  997 */       String pwd = (String)session.getAttribute("password");
/*      */ 
/*  999 */       if ((stringUtils.isNotBlank(un)) && (stringUtils.isNotBlank(pwd))) {
/* 1000 */         PortalaccountQuery aq = new PortalaccountQuery();
/* 1001 */         aq.setLoginNameLike(false);
/* 1002 */         aq.setPasswordLike(false);
/*      */ 
/* 1004 */         aq.setLoginName(un);
/* 1005 */         aq.setPassword(pwd);
/* 1006 */         List accounts = this.portalaccountService.getPortalaccountList(aq);
/* 1007 */         if (accounts.size() == 1) {
/* 1008 */           e = (Portalaccount)accounts.get(0);
/*      */         }
/*      */       }
/* 1011 */       String isOnline = "在线";
/* 1012 */       model.addAttribute("entity", e);
/* 1013 */       model.addAttribute("isOnline", isOnline);
/* 1014 */       model.addAttribute("msg", "充值失败！");
/* 1015 */       return "portalcustomer/index";
/*      */     }
/*      */ 
/* 1018 */     HttpSession session = request.getSession();
/* 1019 */     Portalaccount e = new Portalaccount();
/* 1020 */     String un = (String)session.getAttribute("username");
/* 1021 */     String pwd = (String)session.getAttribute("password");
/*      */ 
/* 1023 */     if ((stringUtils.isNotBlank(un)) && (stringUtils.isNotBlank(pwd))) {
/* 1024 */       PortalaccountQuery aq = new PortalaccountQuery();
/* 1025 */       aq.setLoginNameLike(false);
/* 1026 */       aq.setPasswordLike(false);
/*      */ 
/* 1028 */       aq.setLoginName(un);
/* 1029 */       aq.setPassword(pwd);
/* 1030 */       List accounts = this.portalaccountService.getPortalaccountList(aq);
/* 1031 */       if (accounts.size() == 1) {
/* 1032 */         e = (Portalaccount)accounts.get(0);
/*      */       }
/*      */     }
/* 1035 */     String isOnline = "在线";
/* 1036 */     model.addAttribute("entity", e);
/* 1037 */     model.addAttribute("isOnline", isOnline);
/* 1038 */     model.addAttribute("msg", "系统未授权或已过期！！");
/* 1039 */     return "portalcustomer/index";
/*      */   }
/*      */ 
/*      */   @RequestMapping({"/goWXpay.action"})
/*      */   public String goWXpay(ModelMap model, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/* 1048 */     if (Do()) {
/* 1049 */       HttpSession session = request.getSession();
/*      */       try
/*      */       {
/* 1052 */         Long userID = Long.valueOf(((Long)session.getAttribute("userId")).longValue());
/* 1053 */         Long cardID = Long.valueOf(((Long)session.getAttribute("cardId")).longValue());
/*      */ 
/* 1055 */         if (cardID == null) {
/* 1056 */           List list = this.portalcardcategoryService
/* 1057 */             .getPortalcardcategoryList(new PortalcardcategoryQuery());
/* 1058 */           model.addAttribute("list", list);
/* 1059 */           model.addAttribute("msg", "该充值卡不存在！！");
/* 1060 */           return "buy/buy";
/*      */         }
/*      */ 
/* 1063 */         Portalcardcategory card = this.portalcardcategoryService
/* 1064 */           .getPortalcardcategoryByKey(cardID);
/* 1065 */         if (card == null) {
/* 1066 */           List list = this.portalcardcategoryService
/* 1067 */             .getPortalcardcategoryList(new PortalcardcategoryQuery());
/* 1068 */           model.addAttribute("list", list);
/* 1069 */           model.addAttribute("msg", "该充值卡不存在！！");
/* 1070 */           return "buy/buy";
/*      */         }
/*      */ 
/* 1073 */         Portalaccount account = this.portalaccountService
/* 1074 */           .getPortalaccountByKey(userID);
/* 1075 */         if (account == null) {
/* 1076 */           model.addAttribute("msg", "用户验证失败，请先登录！！");
/* 1077 */           return "portalcustomer/login";
/*      */         }
/*      */ 
/* 1080 */         if (isPortalWXAuthInner(request)) {
/* 1081 */           Payapi payAPI = this.payapiService.getPayapiByKey(Long.valueOf(1L));
/* 1082 */           String weixinAppId = payAPI.getWeixinAppId();
/* 1083 */           String url = this.configService.getConfigByKey(Long.valueOf(1L)).getDomain() + "/doWXpay.action";
/* 1084 */           String authUrl = "https://open.weixin.qq.com/connect/oauth2/authorize";
/* 1085 */           model.addAttribute("url", url);
/* 1086 */           model.addAttribute("authUrl", authUrl);
/* 1087 */           model.addAttribute("appid", weixinAppId);
/* 1088 */           return "buy/oauth";
/*      */         }
/* 1090 */         return "redirect:/doWXpay.action";
/*      */       }
/*      */       catch (Exception e) {
/* 1093 */         List list = this.portalcardcategoryService
/* 1094 */           .getPortalcardcategoryList(new PortalcardcategoryQuery());
/* 1095 */         model.addAttribute("list", list);
/* 1096 */         model.addAttribute("msg", "发生错误！！");
/* 1097 */         return "buy/buy";
/*      */       }
/*      */     }
/* 1100 */     List list = this.portalcardcategoryService
/* 1101 */       .getPortalcardcategoryList(new PortalcardcategoryQuery());
/* 1102 */     model.addAttribute("list", list);
/* 1103 */     model.addAttribute("msg", "系统未授权或已过期！！");
/* 1104 */     return "buy/buy";
/*      */   }
/*      */ 
/*      */   @RequestMapping({"/doWXpay.action"})
/*      */   public String doWXpay(ModelMap model, HttpServletRequest request)
/*      */   {
/* 1111 */     if (Do()) {
/* 1112 */       HttpSession session = request.getSession();
/*      */       try
/*      */       {
/* 1115 */         Long userID = Long.valueOf(((Long)session.getAttribute("userId")).longValue());
/* 1116 */         Long cardID = Long.valueOf(((Long)session.getAttribute("cardId")).longValue());
/*      */ 
/* 1118 */         if (cardID == null) {
/* 1119 */           List list = this.portalcardcategoryService
/* 1120 */             .getPortalcardcategoryList(new PortalcardcategoryQuery());
/* 1121 */           model.addAttribute("list", list);
/* 1122 */           model.addAttribute("msg", "该充值卡不存在！！");
/* 1123 */           return "buy/buy";
/*      */         }
/*      */ 
/* 1126 */         Portalcardcategory card = this.portalcardcategoryService
/* 1127 */           .getPortalcardcategoryByKey(cardID);
/* 1128 */         if (card == null) {
/* 1129 */           List list = this.portalcardcategoryService
/* 1130 */             .getPortalcardcategoryList(new PortalcardcategoryQuery());
/* 1131 */           model.addAttribute("list", list);
/* 1132 */           model.addAttribute("msg", "该充值卡不存在！！");
/* 1133 */           return "buy/buy";
/*      */         }
/*      */ 
/* 1136 */         Portalaccount account = this.portalaccountService
/* 1137 */           .getPortalaccountByKey(userID);
/* 1138 */         if (account == null) {
/* 1139 */           model.addAttribute("msg", "用户验证失败，请先登录！！");
/* 1140 */           return "portalcustomer/login";
/*      */         }
/*      */ 
/* 1144 */         String categoryType = card.getState();
/*      */         String payType;
/* 1146 */         if (categoryType.equals("0")) {
/* 1147 */           payType = "2";
/*      */         }
/*      */         else
/*      */         {
/* 1148 */           if (categoryType.equals("4"))
/* 1149 */             payType = "4";
/*      */           else {
/* 1151 */             payType = "3";
/*      */           }
/*      */         }
/* 1154 */         if (card.getMoney() == null) {
/* 1155 */           card.setMoney(Double.valueOf(0.0D));
/*      */         }
/*      */ 
/* 1158 */         Long time = card.getTime();
/* 1159 */         Long payTime = Long.valueOf(0L);
/* 1160 */         if (categoryType.equals("0"))
/* 1161 */           payTime = Long.valueOf(time.longValue() * 1000L * 60L * 60L);
/* 1162 */         else if (categoryType.equals("1"))
/* 1163 */           payTime = Long.valueOf(time.longValue() * 1000L * 60L * 60L * 24L);
/* 1164 */         else if (categoryType.equals("2"))
/* 1165 */           payTime = Long.valueOf(time.longValue() * 1000L * 60L * 60L * 24L * 31L);
/* 1166 */         else if (categoryType.equals("3"))
/* 1167 */           payTime = Long.valueOf(time.longValue() * 1000L * 60L * 60L * 24L * 31L * 12L);
/* 1168 */         else if (categoryType.equals("4")) {
/* 1169 */           payTime = Long.valueOf(time.longValue() * 1024L * 1024L);
/*      */         }
/*      */ 
/* 1172 */         String cdkey = UUID.randomUUID().toString().replace("-", "");
/* 1173 */         Portalcard cardOrder = new Portalcard();
/* 1174 */         cardOrder.setName(card.getName());
/* 1175 */         cardOrder.setDescription(card.getDescription());
/* 1176 */         cardOrder.setCategoryType(categoryType);
/* 1177 */         cardOrder.setPayType(payType);
/* 1178 */         cardOrder.setPayTime(payTime);
/* 1179 */         cardOrder.setAccountDel(Integer.valueOf(0));
/* 1180 */         cardOrder.setUserDel(Integer.valueOf(0));
/* 1181 */         cardOrder.setCdKey(cdkey);
/* 1182 */         cardOrder.setMoney(card.getMoney());
/*      */ 
/* 1184 */         cardOrder.setState("-1");
/* 1185 */         cardOrder.setBuyDate(new Date());
/* 1186 */         cardOrder.setAccountName(account.getLoginName());
/* 1187 */         cardOrder.setAccountId(userID);
/*      */ 
/* 1189 */         cardOrder.setMaclimit(card.getMaclimit());
/* 1190 */         cardOrder.setMaclimitcount(card.getMaclimitcount());
/* 1191 */         cardOrder.setAutologin(card.getAutologin());
/* 1192 */         cardOrder.setSpeed(card.getSpeed());
/*      */ 
/* 1194 */         this.portalcardService.addPortalcard(cardOrder);
/*      */ 
/* 1198 */         String notify_url = this.configService.getConfigByKey(Long.valueOf(1L)).getDomain() + 
/* 1199 */           "/returnWXpayNotify.action";
/*      */ 
/* 1202 */         Payapi payAPI = this.payapiService.getPayapiByKey(Long.valueOf(1L));
/* 1203 */         String weixinAppId = payAPI.getWeixinAppId();
/* 1204 */         String weixinAppSecret = payAPI.getWeixinAppSecret();
/* 1205 */         String weixinPartner = payAPI.getWeixinPartner();
/* 1206 */         String weixinKey = payAPI.getWeixinKey();
/*      */ 
/* 1208 */         String code = request.getParameter("code");
/* 1209 */         String openId = "";
/* 1210 */         if (stringUtils.isNotBlank(code)) {
/* 1211 */           String oauth2_url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
/* 1212 */           String oauth_url = oauth2_url.replace("APPID", weixinAppId)
/* 1213 */             .replace("SECRET", weixinAppSecret)
/* 1214 */             .replace("CODE", code);
/* 1215 */           JSONObject jsonObject = httpsRequestToJsonObject(oauth_url, 
/* 1216 */             "POST", null);
/* 1217 */           Object errorCode = jsonObject.get("errcode");
/* 1218 */           if (errorCode != null) {
/* 1219 */             System.out.println("code error");
/*      */           } else {
/* 1221 */             openId = jsonObject.getString("openid");
/* 1222 */             System.out.println("openId:" + openId);
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/* 1227 */         WxPayDto tpWxPay = new WxPayDto();
/* 1228 */         tpWxPay.setOpenId(openId);
/* 1229 */         tpWxPay.setBody(card.getName());
/* 1230 */         tpWxPay.setOrderId(cdkey);
/* 1231 */         tpWxPay.setSpbillCreateIp(
/* 1232 */           GetNgnixRemotIP.getRemoteAddrIp(request));
/* 1233 */         tpWxPay.setTotalFee(String.valueOf(card.getMoney()));
/*      */ 
/* 1235 */         model.addAttribute("user", account);
/* 1236 */         model.addAttribute("card", card);
/*      */ 
/* 1238 */         if (isPortalWXAuthInner(request)) {
/* 1239 */           model.addAttribute("weixin", Integer.valueOf(1));
/* 1240 */           String codeData = Core.getPackage(tpWxPay, weixinAppId, 
/* 1241 */             weixinAppSecret, weixinPartner, weixinKey, notify_url);
/* 1242 */           if (stringUtils.isNotBlank(codeData)) {
/* 1243 */             model.addAttribute("codeData", codeData);
/* 1244 */             return "buy/goWXpay";
/*      */           }
/* 1246 */           List list = this.portalcardcategoryService
/* 1247 */             .getPortalcardcategoryList(new PortalcardcategoryQuery());
/* 1248 */           model.addAttribute("list", list);
/* 1249 */           model.addAttribute("msg", "发生错误！！");
/* 1250 */           return "buy/buy";
/*      */         }
/*      */ 
/* 1253 */         model.addAttribute("weixin", Integer.valueOf(0));
/* 1254 */         String codeUrl = Core.getCodeurl(tpWxPay, weixinAppId, 
/* 1255 */           weixinAppSecret, weixinPartner, weixinKey, notify_url);
/* 1256 */         if (stringUtils.isNotBlank(codeUrl)) {
/* 1257 */           model.addAttribute("codeUrl", codeUrl);
/* 1258 */           return "buy/goWXpay";
/*      */         }
/* 1260 */         List list = this.portalcardcategoryService
/* 1261 */           .getPortalcardcategoryList(new PortalcardcategoryQuery());
/* 1262 */         model.addAttribute("list", list);
/* 1263 */         model.addAttribute("msg", "发生错误！！");
/* 1264 */         return "buy/buy";
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/* 1269 */         List list = this.portalcardcategoryService
/* 1270 */           .getPortalcardcategoryList(new PortalcardcategoryQuery());
/* 1271 */         model.addAttribute("list", list);
/* 1272 */         model.addAttribute("msg", "发生错误！！");
/* 1273 */         return "buy/buy";
/*      */       }
/*      */     }
/* 1276 */     List list = this.portalcardcategoryService
/* 1277 */       .getPortalcardcategoryList(new PortalcardcategoryQuery());
/* 1278 */     model.addAttribute("list", list);
/* 1279 */     model.addAttribute("msg", "系统未授权或已过期！！");
/* 1280 */     return "buy/buy";
/*      */   }
/*      */ 
/*      */   @RequestMapping({"/returnWXpayNotify.action"})
/*      */   public void returnWXpayNotify(ModelMap model, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/* 1289 */     if (Do())
/*      */     {
/*      */       try
/*      */       {
/* 1293 */         String notityXml = "";
/* 1294 */         String resXml = "";
/*      */         String inputLine;
/* 1296 */         while ((inputLine = request.getReader().readLine()) != null)
/*      */         {
/* 1297 */           notityXml = notityXml + inputLine;
/*      */         }
/* 1299 */         request.getReader().close();
/*      */ 
/* 1301 */         logger.info("Recive Weixin Server Pay Notify Result : " + 
/* 1302 */           notityXml);
/*      */ 
/* 1304 */         Map m = Notify.parseXmlToList2(notityXml);
/* 1305 */         String TrueSign = "";
/*      */         try {
/* 1307 */           SortedMap packageParams = new TreeMap();
/* 1308 */           Map params = new HashMap();
/* 1309 */           Iterator iter = m.keySet().iterator();
/* 1310 */           while (iter
/* 1310 */             .hasNext()) {
/* 1311 */             String name = (String)iter.next();
/* 1312 */             String value = (String)m.get(name);
/*      */ 
/* 1314 */             params.put(name, value);
/* 1315 */             packageParams.put(name, value);
/* 1316 */             logger.info("returnWXpayNotify Info : " + name + "=" + 
/* 1317 */               value);
/*      */           }
/* 1319 */           packageParams.remove("sign");
/*      */ 
/* 1321 */           Payapi payAPI = this.payapiService.getPayapiByKey(Long.valueOf(1L));
/* 1322 */           String weixinAppId = payAPI.getWeixinAppId();
/* 1323 */           String weixinAppSecret = payAPI.getWeixinAppSecret();
/* 1324 */           String weixinKey = payAPI.getWeixinKey();
/*      */ 
/* 1326 */           RequestHandler reqHandler = new RequestHandler(null, null);
/* 1327 */           reqHandler.init(weixinAppId, weixinAppSecret, weixinKey);
/* 1328 */           TrueSign = reqHandler.createSign(packageParams);
/* 1329 */           logger.info("returnWXpayNotify doSign : " + TrueSign);
/*      */         }
/*      */         catch (Exception localException1) {
/*      */         }
/* 1333 */         String cardKey = ((String)m.get("out_trade_no")).toString();
/* 1334 */         String buyer = ((String)m.get("openid")).toString();
/* 1335 */         String seller = ((String)m.get("appid")).toString();
/* 1336 */         String trade_no = ((String)m.get("transaction_id")).toString();
/* 1337 */         String sign = ((String)m.get("sign")).toString();
/*      */ 
/* 1339 */         WxPayResult wpr = new WxPayResult();
/* 1340 */         wpr.setAppid(seller);
/* 1341 */         wpr.setBankType(((String)m.get("bank_type")).toString());
/* 1342 */         wpr.setCashFee(((String)m.get("cash_fee")).toString());
/* 1343 */         wpr.setFeeType(((String)m.get("fee_type")).toString());
/* 1344 */         wpr.setIsSubscribe(((String)m.get("is_subscribe")).toString());
/* 1345 */         wpr.setMchId(((String)m.get("mch_id")).toString());
/* 1346 */         wpr.setNonceStr(((String)m.get("nonce_str")).toString());
/* 1347 */         wpr.setOpenid(buyer);
/* 1348 */         wpr.setOutTradeNo(cardKey);
/* 1349 */         wpr.setResultCode(((String)m.get("result_code")).toString());
/* 1350 */         wpr.setReturnCode(((String)m.get("return_code")).toString());
/* 1351 */         wpr.setSign(sign);
/* 1352 */         wpr.setTimeEnd(((String)m.get("time_end")).toString());
/* 1353 */         wpr.setTotalFee(((String)m.get("total_fee")).toString());
/* 1354 */         wpr.setTradeType(((String)m.get("trade_type")).toString());
/* 1355 */         wpr.setTransactionId(trade_no);
/*      */ 
/* 1359 */         if (("SUCCESS".equals(wpr.getResultCode())) && 
/* 1360 */           (TrueSign.equals(sign)))
/*      */         {
/* 1362 */           if (stringUtils.isNotBlank(cardKey)) {
/* 1363 */             Boolean boo = Boolean.valueOf(false);
/* 1364 */             PortalcardQuery cq = new PortalcardQuery();
/* 1365 */             cq.setCdKey(cardKey);
/* 1366 */             cq.setCdKeyLike(false);
/* 1367 */             cq.setStateLike(false);
/* 1368 */             cq.setState("-1");
/* 1369 */             List cards = this.portalcardService
/* 1370 */               .getPortalcardList(cq);
/* 1371 */             if ((cards != null) && (cards.size() == 1)) {
/* 1372 */               boo = Boolean.valueOf(true);
/*      */             }
/* 1374 */             if (boo.booleanValue()) {
/* 1375 */               Portalcard card = (Portalcard)cards.get(0);
/* 1376 */               Long aid = card.getAccountId();
/* 1377 */               if (aid != null) {
/* 1378 */                 Portalaccount e = this.portalaccountService
/* 1379 */                   .getPortalaccountByKey(aid);
/* 1380 */                 if (e != null) {
/* 1381 */                   String payType = card.getPayType();
/* 1382 */                   Long payTime = card.getPayTime();
/* 1383 */                   String state = e.getState();
/* 1384 */                   Long oldDate = Long.valueOf(e.getDate().getTime());
/* 1385 */                   Long oldTime = e.getTime();
/* 1386 */                   Long oldOctets = e.getOctets();
/*      */ 
/* 1388 */                   if (oldOctets == null) {
/* 1389 */                     oldOctets = Long.valueOf(0L);
/*      */                   }
/* 1391 */                   if (oldTime == null) {
/* 1392 */                     oldTime = Long.valueOf(0L);
/*      */                   }
/*      */ 
/* 1395 */                   Long now = Long.valueOf(new Date().getTime());
/*      */ 
/* 1399 */                   if (payType.equals("3"))
/*      */                   {
/*      */                     Long newDate;
/* 1401 */                     if (oldDate.longValue() < new Date().getTime())
/* 1402 */                       newDate = Long.valueOf(now.longValue() + payTime.longValue());
/*      */                     else {
/* 1404 */                       newDate = Long.valueOf(oldDate.longValue() + payTime.longValue());
/*      */                     }
/* 1406 */                     e.setDate(new Date(newDate.longValue()));
/* 1407 */                     e.setState(payType);
/*      */                   }
/*      */ 
/* 1410 */                   if (payType.equals("2")) {
/* 1411 */                     if (oldTime.longValue() < 0L)
/* 1412 */                       e.setTime(payTime);
/*      */                     else {
/* 1414 */                       e.setTime(Long.valueOf(oldTime.longValue() + payTime.longValue()));
/*      */                     }
/* 1416 */                     e.setState(payType);
/*      */                   }
/*      */ 
/* 1419 */                   if (payType.equals("4")) {
/* 1420 */                     if (oldOctets.longValue() < 0L)
/* 1421 */                       e.setOctets(payTime);
/*      */                     else {
/* 1423 */                       e.setOctets(Long.valueOf(oldOctets.longValue() + payTime.longValue()));
/*      */                     }
/* 1425 */                     e.setState(payType);
/*      */                   }
/*      */ 
/* 1428 */                   if (state.equals("1")) {
/* 1429 */                     e.setState(state);
/*      */                   }
/*      */ 
/* 1432 */                   e.setMaclimit(card.getMaclimit());
/* 1433 */                   e.setMaclimitcount(card.getMaclimitcount());
/* 1434 */                   e.setAutologin(card.getAutologin());
/* 1435 */                   e.setSpeed(card.getSpeed());
/*      */ 
/* 1437 */                   this.portalaccountService
/* 1438 */                     .updatePortalaccountByKey(e);
/* 1439 */                   card.setAccountId(aid);
/* 1440 */                   card.setAccountName(e.getLoginName());
/* 1441 */                   card.setState("2");
/* 1442 */                   card.setPayDate(new Date());
/* 1443 */                   this.portalcardService
/* 1444 */                     .updatePortalcardByKey(card);
/*      */ 
/* 1447 */                   Portalorder order = new Portalorder();
/* 1448 */                   order.setAccountDel(card.getAccountDel());
/* 1449 */                   order.setAccountId(card.getAccountId());
/* 1450 */                   order.setAccountName(card.getAccountName());
/* 1451 */                   order.setBuyDate(card.getBuyDate());
/* 1452 */                   order.setBuyer(buyer);
/* 1453 */                   order.setCategoryType(card
/* 1454 */                     .getCategoryType());
/* 1455 */                   order.setCdKey(card.getCdKey());
/* 1456 */                   order.setDescription(card.getDescription());
/* 1457 */                   order.setMoney(card.getMoney());
/* 1458 */                   order.setName(card.getName());
/* 1459 */                   order.setPayby(Integer.valueOf(2));
/* 1460 */                   order.setPayDate(card.getPayDate());
/* 1461 */                   order.setPayTime(card.getPayTime());
/* 1462 */                   order.setPayType(card.getPayType());
/* 1463 */                   order.setSeller(seller);
/* 1464 */                   order.setState("1");
/* 1465 */                   order.setTradeno(trade_no);
/* 1466 */                   order.setUserDel(card.getUserDel());
/* 1467 */                   this.portalorderService.addPortalorder(order);
/*      */                 }
/*      */               }
/*      */             }
/*      */           }
/* 1472 */           resXml = "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml> ";
/*      */         }
/*      */         else
/*      */         {
/* 1477 */           resXml = "<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[报文为空]]></return_msg></xml> ";
/*      */         }
/*      */ 
/* 1482 */         BufferedOutputStream out = new BufferedOutputStream(
/* 1483 */           response.getOutputStream());
/* 1484 */         out.write(resXml.getBytes());
/* 1485 */         out.flush();
/* 1486 */         out.close();
/*      */       } catch (Exception e) {
/* 1488 */         logger.error("==============ERROR Start=============");
/* 1489 */         logger.error(e);
/* 1490 */         logger.error("ERROR INFO ", e);
/* 1491 */         logger.error("==============ERROR End=============");
/*      */         try {
/* 1493 */           String resXml = "<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[报文为空]]></return_msg></xml> ";
/*      */ 
/* 1497 */           BufferedOutputStream out = new BufferedOutputStream(
/* 1498 */             response.getOutputStream());
/* 1499 */           out.write(resXml.getBytes());
/* 1500 */           out.flush();
/* 1501 */           out.close();
/*      */         } catch (Exception localException2) {
/*      */         }
/*      */       }
/*      */     }
/*      */     else try {
/* 1507 */         String resXml = "<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[报文为空]]></return_msg></xml> ";
/*      */ 
/* 1511 */         BufferedOutputStream out = new BufferedOutputStream(
/* 1512 */           response.getOutputStream());
/* 1513 */         out.write(resXml.getBytes());
/* 1514 */         out.flush();
/* 1515 */         out.close();
/*      */       }
/*      */       catch (Exception localException3)
/*      */       {
/*      */       } 
/*      */   }
/*      */ 
/*      */   public static boolean Do()
/*      */   {
/* 1523 */     Long isThis = Long.valueOf(new Date().getTime());
/* 1524 */     boolean Do = false;
/* 1525 */     if (isThis.longValue() < isDo.getInstance().getId().longValue()) {
/* 1526 */       Do = true;
/*      */     }
/* 1528 */     return Do;
/*      */   }
/*      */ 
/*      */   public static boolean isPortalWXAuthInner(HttpServletRequest request)
/*      */   {
/* 1539 */     String WEI_XIN_BROWSER = "MicroMessenger/";
/*      */ 
/* 1542 */     String userAgent = request.getHeader("user-agent");
/* 1543 */     boolean isPortalWXAuthInner = false;
/* 1544 */     if (stringUtils.isNotBlank(userAgent))
/*      */     {
/* 1546 */       if (userAgent.contains(WEI_XIN_BROWSER)) {
/* 1547 */         isPortalWXAuthInner = true;
/*      */       }
/*      */     }
/* 1550 */     return isPortalWXAuthInner;
/*      */   }
/*      */ 
/*      */   public static JSONObject httpsRequestToJsonObject(String requestUrl, String requestMethod, String outputStr)
/*      */   {
/* 1555 */     JSONObject jsonObject = null;
/*      */     try {
/* 1557 */       String buffer = HttpsUtils.httpsRequest(requestUrl, requestMethod, outputStr);
/* 1558 */       jsonObject = JSONObject.fromObject(buffer);
/*      */     } catch (Exception e) {
/* 1560 */       System.out.println(e.getMessage());
/*      */     }
/* 1562 */     return jsonObject;
/*      */   }
/*      */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.BuyController
 * JD-Core Version:    0.6.2
 */