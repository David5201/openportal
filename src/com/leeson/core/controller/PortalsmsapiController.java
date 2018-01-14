/*     */ package com.leeson.core.controller;
/*     */ 
/*     */ import com.leeson.common.page.Pagination;
/*     */ import com.leeson.common.utils.stringUtils;
/*     */ import com.leeson.core.bean.Portalsmsapi;
/*     */ import com.leeson.core.query.PortalsmsapiQuery;
/*     */ import com.leeson.core.service.PortalsmsapiService;
/*     */ import com.leeson.core.utils.SMSAliRequest;
/*     */ import com.leeson.core.utils.SMSEmppRequest;
/*     */ import com.leeson.core.utils.SMSKaiLiRequest;
/*     */ import com.leeson.core.utils.SMSOpenMasRequest;
/*     */ import com.leeson.core.utils.SMSProRequest;
/*     */ import com.leeson.core.utils.SMSRequest;
/*     */ import com.leeson.core.utils.SMSSMGPAPIRequest;
/*     */ import com.leeson.core.utils.SMSSUBMAILRequest;
/*     */ import com.leeson.core.utils.SMSUMSRequest;
/*     */ import com.leeson.core.utils.SMSVirtualRequest;
/*     */ import com.leeson.portal.core.model.PhoneCodeMap;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.ui.ModelMap;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ import org.springframework.web.bind.annotation.RequestParam;
/*     */ import org.springframework.web.bind.annotation.ResponseBody;
/*     */ 
/*     */ @Controller
/*     */ public class PortalsmsapiController
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private PortalsmsapiService portalsmsapiService;
/*     */ 
/*     */   @RequestMapping({"/portalsmsapi/list.action"})
/*     */   public String page(PortalsmsapiQuery query, HttpServletRequest request, HttpServletResponse response, ModelMap model)
/*     */   {
/*  53 */     query.setNameLike(true);
/*  54 */     query.setStateLike(false);
/*  55 */     query.setUrlLike(true);
/*  56 */     if (stringUtils.isBlank(query.getName())) {
/*  57 */       query.setName(null);
/*     */     }
/*  59 */     if (stringUtils.isBlank(query.getUrl())) {
/*  60 */       query.setUrl(null);
/*     */     }
/*  62 */     if (stringUtils.isBlank(query.getState())) {
/*  63 */       query.setState(null);
/*     */     }
/*  65 */     Pagination pagination = this.portalsmsapiService
/*  66 */       .getPortalsmsapiListWithPage(query);
/*  67 */     model.addAttribute("pagination", pagination);
/*  68 */     model.addAttribute("query", query);
/*  69 */     return "portalsmsapi/list";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/portalsmsapi/add.action"})
/*     */   public String add(ModelMap model)
/*     */   {
/*  76 */     return "portalsmsapi/save";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/portalsmsapi/add.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String add(Portalsmsapi e)
/*     */   {
/*  82 */     int state = Integer.valueOf(e.getState()).intValue();
/*  83 */     if (state == 1) {
/*  84 */       List<Portalsmsapi> es = this.portalsmsapiService
/*  85 */         .getPortalsmsapiList(new PortalsmsapiQuery());
/*  86 */       for (Portalsmsapi ec : es) {
/*  87 */         ec.setState("0");
/*  88 */         this.portalsmsapiService.updatePortalsmsapiByKey(ec);
/*     */       }
/*     */     }
/*     */ 
/*  92 */     if ("0".equals(e.getType())) {
/*  93 */       e.setUrl("http://auth.ikuai8.com/api2/sms/send.php");
/*     */     }
/*  95 */     if ("2".equals(e.getType())) {
/*  96 */       e.setUrl("http://127.0.0.1");
/*     */     }
/*  98 */     if ("3".equals(e.getType())) {
/*  99 */       e.setUrl("http://gw.api.taobao.com/router/rest");
/*     */     }
/* 101 */     if (("4".equals(e.getType())) && 
/* 102 */       (stringUtils.isBlank(e.getUrl()))) {
/* 103 */       e.setUrl("127.0.0.1:9981");
/*     */     }
/*     */ 
/* 106 */     if ("5".equals(e.getType())) {
/* 107 */       e.setUrl("http://ums.zj165.com:8888/sms/Api/Send.do");
/*     */     }
/* 109 */     if (("6".equals(e.getType())) && 
/* 110 */       (stringUtils.isBlank(e.getUrl()))) {
/* 111 */       e.setUrl("http://www.openmas.net:9080/OpenMasService");
/*     */     }
/*     */ 
/* 114 */     if ("7".equals(e.getType())) {
/* 115 */       e.setUrl("http://127.0.0.1");
/*     */     }
/* 117 */     if (("8".equals(e.getType())) && 
/* 118 */       (stringUtils.isBlank(e.getUrl()))) {
/* 119 */       e.setUrl("http://www.jc-chn.cn/smsSend.do");
/*     */     }
/*     */ 
/* 122 */     if (("9".equals(e.getType())) && 
/* 123 */       (stringUtils.isBlank(e.getUrl()))) {
/* 124 */       e.setUrl("127.0.0.1:8890");
/*     */     }
/*     */ 
/* 127 */     if (("10".equals(e.getType())) && 
/* 128 */       (stringUtils.isBlank(e.getUrl()))) {
/* 129 */       e.setUrl("https://dx.ipyy.net/smsJson.aspx");
/*     */     }
/*     */ 
/* 132 */     if ("11".equals(e.getType())) {
/* 133 */       e.setUrl("http://139.224.36.226:1082/wgws/OrderServlet");
/*     */     }
/* 135 */     if (("12".equals(e.getType())) && 
/* 136 */       (stringUtils.isBlank(e.getUrl()))) {
/* 137 */       e.setUrl("http://mas.ecloud.10086.cn/app/sdk/login");
/*     */     }
/*     */ 
/* 141 */     this.portalsmsapiService.addPortalsmsapi(e);
/*     */ 
/* 143 */     return "redirect:/portalsmsapi/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/portalsmsapi/edit.action"})
/*     */   public String editV(@RequestParam Long id, ModelMap model)
/*     */   {
/* 149 */     Portalsmsapi e = this.portalsmsapiService.getPortalsmsapiByKey(id);
/* 150 */     model.addAttribute("entity", e);
/* 151 */     return "portalsmsapi/save";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/portalsmsapi/edit.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String edit(Portalsmsapi e)
/*     */   {
/* 157 */     int state = Integer.valueOf(e.getState()).intValue();
/* 158 */     if (state == 1) {
/* 159 */       List<Portalsmsapi> es = this.portalsmsapiService
/* 160 */         .getPortalsmsapiList(new PortalsmsapiQuery());
/* 161 */       for (Portalsmsapi ec : es) {
/* 162 */         ec.setState("0");
/* 163 */         this.portalsmsapiService.updatePortalsmsapiByKey(ec);
/*     */       }
/*     */     }
/*     */ 
/* 167 */     if ("0".equals(e.getType())) {
/* 168 */       e.setUrl("http://auth.ikuai8.com/api2/sms/send.php");
/*     */     }
/* 170 */     if ("2".equals(e.getType())) {
/* 171 */       e.setUrl("http://127.0.0.1");
/*     */     }
/* 173 */     if ("3".equals(e.getType())) {
/* 174 */       e.setUrl("http://gw.api.taobao.com/router/rest");
/*     */     }
/* 176 */     if (("4".equals(e.getType())) && 
/* 177 */       (stringUtils.isBlank(e.getUrl()))) {
/* 178 */       e.setUrl("127.0.0.1:9981");
/*     */     }
/*     */ 
/* 181 */     if ("5".equals(e.getType())) {
/* 182 */       e.setUrl("http://ums.zj165.com:8888/sms/Api/Send.do");
/*     */     }
/* 184 */     if (("6".equals(e.getType())) && 
/* 185 */       (stringUtils.isBlank(e.getUrl()))) {
/* 186 */       e.setUrl("http://www.openmas.net:9080/OpenMasService");
/*     */     }
/*     */ 
/* 189 */     if ("7".equals(e.getType())) {
/* 190 */       e.setUrl("http://127.0.0.1");
/*     */     }
/* 192 */     if (("8".equals(e.getType())) && 
/* 193 */       (stringUtils.isBlank(e.getUrl()))) {
/* 194 */       e.setUrl("http://www.jc-chn.cn/smsSend.do");
/*     */     }
/*     */ 
/* 197 */     if (("9".equals(e.getType())) && 
/* 198 */       (stringUtils.isBlank(e.getUrl()))) {
/* 199 */       e.setUrl("127.0.0.1:8890");
/*     */     }
/*     */ 
/* 202 */     if (("10".equals(e.getType())) && 
/* 203 */       (stringUtils.isBlank(e.getUrl()))) {
/* 204 */       e.setUrl("https://dx.ipyy.net/smsJson.aspx");
/*     */     }
/*     */ 
/* 207 */     if ("11".equals(e.getType())) {
/* 208 */       e.setUrl("http://139.224.36.226:1082/wgws/OrderServlet");
/*     */     }
/* 210 */     if (("12".equals(e.getType())) && 
/* 211 */       (stringUtils.isBlank(e.getUrl()))) {
/* 212 */       e.setUrl("http://mas.ecloud.10086.cn/app/sdk/login");
/*     */     }
/*     */ 
/* 216 */     this.portalsmsapiService.updatePortalsmsapiByKey(e);
/* 217 */     return "redirect:/portalsmsapi/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/portalsmsapi/editV.action"})
/*     */   public String editState(@RequestParam Long id, @RequestParam String to)
/*     */   {
/* 223 */     if (to.equals("state")) {
/* 224 */       Portalsmsapi e = this.portalsmsapiService.getPortalsmsapiByKey(id);
/* 225 */       int state = Integer.valueOf(e.getState()).intValue();
/* 226 */       if (state == 1) {
/* 227 */         Portalsmsapi ec = this.portalsmsapiService.getPortalsmsapiByKey(Long.valueOf(1L));
/* 228 */         ec.setState("1");
/* 229 */         this.portalsmsapiService.updatePortalsmsapiByKey(ec);
/* 230 */         if (e.getId().longValue() != 1L)
/* 231 */           e.setState("0");
/*     */       }
/*     */       else
/*     */       {
/* 235 */         List<Portalsmsapi> es = this.portalsmsapiService
/* 236 */           .getPortalsmsapiList(new PortalsmsapiQuery());
/* 237 */         for (Portalsmsapi ec : es) {
/* 238 */           ec.setState("0");
/* 239 */           this.portalsmsapiService.updatePortalsmsapiByKey(ec);
/*     */         }
/* 241 */         e.setState("1");
/*     */       }
/* 243 */       this.portalsmsapiService.updatePortalsmsapiByKey(e);
/*     */     }
/*     */ 
/* 246 */     if (to.equals("more")) {
/* 247 */       Portalsmsapi e = this.portalsmsapiService.getPortalsmsapiByKey(id);
/* 248 */       int more = Integer.valueOf(e.getMore()).intValue();
/* 249 */       if (more == 1)
/* 250 */         e.setMore("0");
/*     */       else {
/* 252 */         e.setMore("1");
/*     */       }
/* 254 */       this.portalsmsapiService.updatePortalsmsapiByKey(e);
/*     */     }
/*     */ 
/* 257 */     return "redirect:/portalsmsapi/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping({"/portalsmsapi/delete.action"})
/*     */   public String delete(@RequestParam Long id)
/*     */   {
/* 263 */     if (id.longValue() != 1L) {
/* 264 */       this.portalsmsapiService.deleteByKey(id);
/*     */     }
/* 266 */     return "redirect:/portalsmsapi/list.action";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/portalsmsapi/deletes.action"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String deletes(@RequestParam Long[] ids)
/*     */   {
/* 273 */     List list = Arrays.asList(ids);
/* 274 */     if (list.contains(Long.valueOf(1L))) {
/* 275 */       list.remove(Long.valueOf(1L));
/*     */     }
/* 277 */     this.portalsmsapiService.deleteByKeys(list);
/*     */ 
/* 279 */     return "redirect:/portalsmsapi/list.action";
/*     */   }
/*     */ 
/*     */   @ResponseBody
/*     */   @RequestMapping({"/sms/test.action"})
/*     */   public Map<String, String> test(String phone, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 287 */     Map map = new HashMap();
/* 288 */     if ((stringUtils.isBlank(phone)) || (phone.length() != 11)) {
/* 289 */       map.put("ret", "1");
/* 290 */       map.put("msg", "手机号码不正确！");
/* 291 */       return map;
/*     */     }
/*     */     try {
/* 294 */       Long.parseLong(phone);
/*     */     } catch (Exception e) {
/* 296 */       map.put("ret", "1");
/* 297 */       map.put("msg", "手机号码不正确！");
/* 298 */       return map;
/*     */     }
/*     */ 
/* 301 */     PortalsmsapiQuery query = new PortalsmsapiQuery();
/* 302 */     query.setState("1");
/* 303 */     List smsList = this.portalsmsapiService
/* 304 */       .getPortalsmsapiList(query);
/* 305 */     Long count = Long.valueOf(0L);
/* 306 */     String url = "";
/* 307 */     Long id = Long.valueOf(1L);
/* 308 */     String type = "0";
/* 309 */     String text = "";
/* 310 */     int time = 1;
/*     */ 
/* 312 */     String key = "";
/* 313 */     String secret = "";
/* 314 */     String sign = "";
/* 315 */     String template = "";
/* 316 */     String company = "";
/*     */ 
/* 318 */     if (smsList.size() > 0) {
/* 319 */       Portalsmsapi smsapi = (Portalsmsapi)smsList.get(0);
/* 320 */       count = smsapi.getCount();
/* 321 */       url = smsapi.getUrl();
/* 322 */       id = smsapi.getId();
/* 323 */       type = smsapi.getType();
/* 324 */       text = smsapi.getText();
/* 325 */       time = smsapi.getTime().intValue();
/*     */ 
/* 327 */       key = smsapi.getAppkey();
/* 328 */       secret = smsapi.getAppsecret();
/* 329 */       sign = smsapi.getSmssign();
/* 330 */       template = smsapi.getSmstemplate();
/* 331 */       company = smsapi.getCompany();
/*     */     } else {
/* 333 */       Portalsmsapi smsapi = this.portalsmsapiService.getPortalsmsapiByKey(Long.valueOf(1L));
/* 334 */       count = smsapi.getCount();
/* 335 */       url = smsapi.getUrl();
/* 336 */       type = smsapi.getType();
/* 337 */       text = smsapi.getText();
/* 338 */       time = smsapi.getTime().intValue();
/*     */ 
/* 340 */       key = smsapi.getAppkey();
/* 341 */       secret = smsapi.getAppsecret();
/* 342 */       sign = smsapi.getSmssign();
/* 343 */       template = smsapi.getSmstemplate();
/* 344 */       company = smsapi.getCompany();
/*     */     }
/*     */ 
/* 347 */     if (stringUtils.isNotBlank(url)) {
/* 348 */       Boolean state = Boolean.valueOf(false);
/* 349 */       if ("0".equals(type)) {
/* 350 */         state = Boolean.valueOf(SMSRequest.send(url, phone, key, id));
/* 351 */       } else if ("2".equals(type)) {
/* 352 */         state = Boolean.valueOf(SMSVirtualRequest.send(phone));
/*     */         try {
/* 354 */           Object[] objs = 
/* 355 */             (Object[])PhoneCodeMap.getInstance()
/* 355 */             .getPhoneCodeMap().get(phone);
/* 356 */           String yzm = (String)objs[0];
/* 357 */           if (stringUtils.isNotBlank(text)) {
/* 358 */             if ("[yzm]".equals(text)) {
/* 359 */               text = "【" + sign + "】您的验证码是" + yzm + " 有效期" + time + 
/* 360 */                 "分钟! 【" + company + "】提供!";
/*     */             }
/* 362 */             else if (text.contains("[yzm]"))
/* 363 */               text = text.replace("[yzm]", yzm);
/*     */             else {
/* 365 */               text = "【" + sign + "】您的验证码是" + yzm + " 有效期" + 
/* 366 */                 time + "分钟! 【" + company + "】提供!";
/*     */             }
/*     */           }
/*     */           else {
/* 370 */             text = "【" + sign + "】您的验证码是" + yzm + " 有效期" + time + 
/* 371 */               "分钟! 【" + company + "】提供!";
/*     */           }
/* 373 */           map.put("msg", text);
/*     */         } catch (Exception e) {
/* 375 */           map.put("ret", "1");
/* 376 */           map.put("msg", "获取验证码失败，请使用其他方式或重试！");
/*     */         }
/* 378 */       } else if ("3".equals(type)) {
/* 379 */         state = Boolean.valueOf(SMSAliRequest.send(url, phone, key, secret, sign, 
/* 380 */           template, company, id));
/* 381 */       } else if ("4".equals(type)) {
/* 382 */         state = Boolean.valueOf(SMSEmppRequest.send(1, url, key, secret, template, 
/* 383 */           text, sign, company, phone, time, id));
/* 384 */       } else if ("5".equals(type)) {
/* 385 */         state = Boolean.valueOf(SMSUMSRequest.send(url, key, secret, template, text, 
/* 386 */           phone, id));
/* 387 */       } else if ("6".equals(type)) {
/* 388 */         state = Boolean.valueOf(SMSOpenMasRequest.send(1, url, key, secret, template, 
/* 389 */           text, sign, company, phone, time, id));
/* 390 */       } else if ("7".equals(type)) {
/* 391 */         state = Boolean.valueOf(SMSSUBMAILRequest.send(key, secret, sign, template, 
/* 392 */           phone, id));
/* 393 */       } else if ("8".equals(type)) {
/* 394 */         state = Boolean.valueOf(SMSKaiLiRequest.send(1, url, key, secret, text, sign, 
/* 395 */           company, phone, time, id));
/* 396 */       } else if ("9".equals(type)) {
/* 397 */         state = Boolean.valueOf(SMSSMGPAPIRequest.send(url, key, secret, sign, 
/* 398 */           template, text, phone, id));
/*     */       } else {
/* 400 */         HttpSession session = request.getSession();
/* 401 */         String mac = (String)session.getAttribute("ikmac");
/* 402 */         state = Boolean.valueOf(SMSProRequest.send(url, phone, mac, text, request, id));
/*     */       }
/*     */ 
/* 405 */       if (state.booleanValue()) {
/* 406 */         count = Long.valueOf(count.longValue() + 1L);
/* 407 */         Portalsmsapi smsapi = new Portalsmsapi();
/* 408 */         smsapi.setId(id);
/* 409 */         smsapi.setCount(count);
/* 410 */         this.portalsmsapiService.updatePortalsmsapiByKey(smsapi);
/* 411 */         map.put("ret", "0");
/*     */       } else {
/* 413 */         map.put("ret", "1");
/* 414 */         map.put("msg", "获取验证码失败，请使用其他认证方式或重试！");
/*     */       }
/*     */     } else {
/* 417 */       map.put("ret", "1");
/* 418 */       map.put("msg", "短信网关配置出错，请联系管理员！");
/*     */     }
/* 420 */     return map;
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.PortalsmsapiController
 * JD-Core Version:    0.6.2
 */