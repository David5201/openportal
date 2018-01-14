/*     */ package com.leeson.core.controller;
/*     */ 
/*     */ import com.leeson.common.page.Pagination;
/*     */ import com.leeson.common.utils.stringUtils;
/*     */ import com.leeson.core.bean.Config;
/*     */ import com.leeson.core.bean.Portallogrecord;
/*     */ import com.leeson.core.bean.Portalsmsapi;
/*     */ import com.leeson.core.bean.Portaluser;
/*     */ import com.leeson.core.query.PortalaccountQuery;
/*     */ import com.leeson.core.query.PortallogrecordQuery;
/*     */ import com.leeson.core.query.PortalmessageQuery;
/*     */ import com.leeson.core.query.PortalphonesQuery;
/*     */ import com.leeson.core.query.PortalsmsapiQuery;
/*     */ import com.leeson.core.service.ConfigService;
/*     */ import com.leeson.core.service.PortalaccountService;
/*     */ import com.leeson.core.service.PortallogrecordService;
/*     */ import com.leeson.core.service.PortalmessageService;
/*     */ import com.leeson.core.service.PortalphonesService;
/*     */ import com.leeson.core.service.PortalsmsapiService;
/*     */ import com.leeson.core.utils.SMSAliRequest;
/*     */ import com.leeson.core.utils.SMSCMCCMASRequest;
/*     */ import com.leeson.core.utils.SMSEmppRequest;
/*     */ import com.leeson.core.utils.SMSIPYYRequest;
/*     */ import com.leeson.core.utils.SMSKaiLiRequest;
/*     */ import com.leeson.core.utils.SMSOpenMasRequest;
/*     */ import com.leeson.core.utils.SMSProRequest;
/*     */ import com.leeson.core.utils.SMSRequest;
/*     */ import com.leeson.core.utils.SMSSMGPAPIRequest;
/*     */ import com.leeson.core.utils.SMSSUBMAILRequest;
/*     */ import com.leeson.core.utils.SMSUMSRequest;
/*     */ import com.leeson.core.utils.SMSVirtualRequest;
/*     */ import com.leeson.core.utils.SMSWXHYRequest;
/*     */ import com.leeson.portal.core.model.OnlineMap;
/*     */ import com.leeson.portal.core.model.PhoneCodeMap;
/*     */ import com.leeson.portal.core.model.WifiDogOnlineMap;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ import org.springframework.web.bind.annotation.ResponseBody;
/*     */ 
/*     */ @Controller
/*     */ public class AjaxController
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private PortalmessageService portalmessageService;
/*     */ 
/*     */   @Autowired
/*     */   private PortallogrecordService portallogrecordService;
/*     */ 
/*     */   @Autowired
/*     */   private PortalaccountService portalaccountService;
/*     */ 
/*     */   @Autowired
/*     */   private PortalsmsapiService portalsmsapiService;
/*     */ 
/*     */   @Autowired
/*     */   private ConfigService configService;
/*     */ 
/*     */   @Autowired
/*     */   private PortalphonesService portalphonesService;
/*     */ 
/*     */   @ResponseBody
/*     */   @RequestMapping({"/ajax_smsAPI_find"})
/*     */   public Map<String, String> find(String phone, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  75 */     Map map = new HashMap();
/*  76 */     if ((stringUtils.isBlank(phone)) || (phone.length() != 11)) {
/*  77 */       map.put("ret", "1");
/*  78 */       map.put("msg", "手机号码不正确！");
/*  79 */       return map;
/*     */     }
/*     */     try {
/*  82 */       Long.parseLong(phone);
/*     */     } catch (Exception e) {
/*  84 */       map.put("ret", "1");
/*  85 */       map.put("msg", "手机号码不正确！");
/*  86 */       return map;
/*     */     }
/*     */ 
/*  89 */     PortalsmsapiQuery query = new PortalsmsapiQuery();
/*  90 */     query.setState("1");
/*  91 */     List smsList = this.portalsmsapiService
/*  92 */       .getPortalsmsapiList(query);
/*  93 */     Long count = Long.valueOf(0L);
/*  94 */     String url = "";
/*  95 */     Long id = Long.valueOf(1L);
/*  96 */     String type = "0";
/*  97 */     String text = "";
/*  98 */     int time = 1;
/*     */ 
/* 100 */     String key = "";
/* 101 */     String secret = "";
/* 102 */     String sign = "";
/* 103 */     String template = "";
/* 104 */     String company = "";
/*     */ 
/* 106 */     if (smsList.size() > 0) {
/* 107 */       Portalsmsapi smsapi = (Portalsmsapi)smsList.get(0);
/* 108 */       count = smsapi.getCount();
/* 109 */       url = smsapi.getUrl();
/* 110 */       id = smsapi.getId();
/* 111 */       type = smsapi.getType();
/* 112 */       text = smsapi.getText();
/* 113 */       time = smsapi.getTime().intValue();
/*     */ 
/* 115 */       key = smsapi.getAppkey();
/* 116 */       secret = smsapi.getAppsecret();
/* 117 */       sign = smsapi.getSmssign();
/* 118 */       template = smsapi.getSmstemplate();
/* 119 */       company = smsapi.getCompany();
/*     */     } else {
/* 121 */       Portalsmsapi smsapi = this.portalsmsapiService.getPortalsmsapiByKey(Long.valueOf(1L));
/* 122 */       count = smsapi.getCount();
/* 123 */       url = smsapi.getUrl();
/* 124 */       type = smsapi.getType();
/* 125 */       text = smsapi.getText();
/* 126 */       time = smsapi.getTime().intValue();
/*     */ 
/* 128 */       key = smsapi.getAppkey();
/* 129 */       secret = smsapi.getAppsecret();
/* 130 */       sign = smsapi.getSmssign();
/* 131 */       template = smsapi.getSmstemplate();
/* 132 */       company = smsapi.getCompany();
/*     */     }
/*     */ 
/* 135 */     if (stringUtils.isNotBlank(url)) {
/* 136 */       Boolean state = Boolean.valueOf(false);
/* 137 */       if ("0".equals(type)) {
/* 138 */         state = Boolean.valueOf(SMSRequest.send(url, phone, key, id));
/* 139 */       } else if ("2".equals(type)) {
/* 140 */         state = Boolean.valueOf(SMSVirtualRequest.send(phone));
/*     */         try {
/* 142 */           Object[] objs = 
/* 143 */             (Object[])PhoneCodeMap.getInstance()
/* 143 */             .getPhoneCodeMap().get(phone);
/* 144 */           String yzm = (String)objs[0];
/* 145 */           if (stringUtils.isNotBlank(text)) {
/* 146 */             if ("[yzm]".equals(text)) {
/* 147 */               text = "【" + sign + "】您的验证码是" + yzm + " 有效期" + time + 
/* 148 */                 "分钟! 【" + company + "】提供!";
/*     */             }
/* 150 */             else if (text.contains("[yzm]"))
/* 151 */               text = text.replace("[yzm]", yzm);
/*     */             else {
/* 153 */               text = "【" + sign + "】您的验证码是" + yzm + " 有效期" + 
/* 154 */                 time + "分钟! 【" + company + "】提供!";
/*     */             }
/*     */           }
/*     */           else {
/* 158 */             text = "【" + sign + "】您的验证码是" + yzm + " 有效期" + time + 
/* 159 */               "分钟! 【" + company + "】提供!";
/*     */           }
/* 161 */           map.put("msg", text);
/*     */         } catch (Exception e) {
/* 163 */           map.put("ret", "1");
/* 164 */           map.put("msg", "获取验证码失败，请使用其他方式或重试！");
/*     */         }
/* 166 */       } else if ("3".equals(type)) {
/* 167 */         state = Boolean.valueOf(SMSAliRequest.send(url, phone, key, secret, sign, 
/* 168 */           template, company, id));
/* 169 */       } else if ("4".equals(type)) {
/* 170 */         state = Boolean.valueOf(SMSEmppRequest.send(1, url, key, secret, template, 
/* 171 */           text, sign, company, phone, time, id));
/* 172 */       } else if ("5".equals(type)) {
/* 173 */         state = Boolean.valueOf(SMSUMSRequest.send(url, key, secret, template, text, 
/* 174 */           phone, id));
/* 175 */       } else if ("6".equals(type)) {
/* 176 */         state = Boolean.valueOf(SMSOpenMasRequest.send(1, url, key, secret, template, 
/* 177 */           text, sign, company, phone, time, id));
/* 178 */       } else if ("7".equals(type)) {
/* 179 */         state = Boolean.valueOf(SMSSUBMAILRequest.send(key, secret, sign, template, 
/* 180 */           phone, id));
/* 181 */       } else if ("8".equals(type)) {
/* 182 */         state = Boolean.valueOf(SMSKaiLiRequest.send(1, url, key, secret, text, sign, 
/* 183 */           company, phone, time, id));
/* 184 */       } else if ("9".equals(type)) {
/* 185 */         state = Boolean.valueOf(SMSSMGPAPIRequest.send(url, key, secret, sign, 
/* 186 */           template, text, phone, id));
/* 187 */       } else if ("10".equals(type)) {
/* 188 */         state = Boolean.valueOf(SMSIPYYRequest.send(url, key, secret, text, phone, id));
/* 189 */       } else if ("11".equals(type)) {
/* 190 */         state = Boolean.valueOf(SMSWXHYRequest.send(url, key, secret, text, phone, id));
/* 191 */       } else if ("12".equals(type)) {
/* 192 */         state = Boolean.valueOf(SMSCMCCMASRequest.send(1, url, key, secret, company, template, text, sign, phone, time, id));
/*     */       } else {
/* 194 */         HttpSession session = request.getSession();
/* 195 */         String mac = (String)session.getAttribute("ikmac");
/* 196 */         state = Boolean.valueOf(SMSProRequest.send(url, phone, mac, text, request, id));
/*     */       }
/*     */ 
/* 199 */       if (state.booleanValue()) {
/* 200 */         count = Long.valueOf(count.longValue() + 1L);
/* 201 */         Portalsmsapi smsapi = new Portalsmsapi();
/* 202 */         smsapi.setId(id);
/* 203 */         smsapi.setCount(count);
/* 204 */         this.portalsmsapiService.updatePortalsmsapiByKey(smsapi);
/* 205 */         map.put("ret", "0");
/*     */       } else {
/* 207 */         map.put("ret", "1");
/* 208 */         map.put("msg", "获取验证码失败，请使用其他认证方式或重试！");
/*     */       }
/*     */     } else {
/* 211 */       map.put("ret", "1");
/* 212 */       map.put("msg", "短信网关配置出错，请联系管理员！");
/*     */     }
/* 214 */     return map;
/*     */   }
/*     */ 
/*     */   @ResponseBody
/*     */   @RequestMapping({"/ajax_smsAPI_regist"})
/*     */   public Map<String, String> regist(String phone, HttpServletRequest request, HttpServletResponse response) {
/* 221 */     Map map = new HashMap();
/* 222 */     if ((stringUtils.isBlank(phone)) || (phone.length() != 11)) {
/* 223 */       map.put("ret", "1");
/* 224 */       map.put("msg", "手机号码不正确！");
/* 225 */       return map;
/*     */     }
/*     */     try {
/* 228 */       Long.parseLong(phone);
/*     */     } catch (Exception e) {
/* 230 */       map.put("ret", "1");
/* 231 */       map.put("msg", "手机号码不正确！");
/* 232 */       return map;
/*     */     }
/*     */ 
/* 235 */     PortalsmsapiQuery query = new PortalsmsapiQuery();
/* 236 */     query.setState("1");
/* 237 */     List smsList = this.portalsmsapiService
/* 238 */       .getPortalsmsapiList(query);
/* 239 */     Long count = Long.valueOf(0L);
/* 240 */     String url = "";
/* 241 */     Long id = Long.valueOf(1L);
/* 242 */     String type = "0";
/* 243 */     String text = "";
/* 244 */     int time = 1;
/*     */ 
/* 246 */     String key = "";
/* 247 */     String secret = "";
/* 248 */     String sign = "";
/* 249 */     String template = "";
/* 250 */     String company = "";
/*     */ 
/* 252 */     if (smsList.size() > 0) {
/* 253 */       Portalsmsapi smsapi = (Portalsmsapi)smsList.get(0);
/* 254 */       count = smsapi.getCount();
/* 255 */       url = smsapi.getUrl();
/* 256 */       id = smsapi.getId();
/* 257 */       type = smsapi.getType();
/* 258 */       text = smsapi.getText();
/* 259 */       time = smsapi.getTime().intValue();
/*     */ 
/* 261 */       key = smsapi.getAppkey();
/* 262 */       secret = smsapi.getAppsecret();
/* 263 */       sign = smsapi.getSmssign();
/* 264 */       template = smsapi.getSmstemplate();
/* 265 */       company = smsapi.getCompany();
/*     */     } else {
/* 267 */       Portalsmsapi smsapi = this.portalsmsapiService.getPortalsmsapiByKey(Long.valueOf(1L));
/* 268 */       count = smsapi.getCount();
/* 269 */       url = smsapi.getUrl();
/* 270 */       type = smsapi.getType();
/* 271 */       text = smsapi.getText();
/* 272 */       time = smsapi.getTime().intValue();
/*     */ 
/* 274 */       key = smsapi.getAppkey();
/* 275 */       secret = smsapi.getAppsecret();
/* 276 */       sign = smsapi.getSmssign();
/* 277 */       template = smsapi.getSmstemplate();
/* 278 */       company = smsapi.getCompany();
/*     */     }
/*     */ 
/* 281 */     if (2 == this.configService.getConfigByKey(Long.valueOf(1L)).getAccountAdd().intValue()) {
/* 282 */       PortalaccountQuery aq = new PortalaccountQuery();
/* 283 */       aq.setLoginName(phone);
/* 284 */       aq.setLoginNameLike(false);
/* 285 */       if (this.portalaccountService.getPortalaccountList(aq).size() > 0) {
/* 286 */         map.put("ret", "1");
/* 287 */         map.put("msg", "当前手机号已注册！");
/* 288 */         return map;
/*     */       }
/*     */     }
/*     */ 
/* 292 */     if (stringUtils.isNotBlank(url)) {
/* 293 */       Boolean state = Boolean.valueOf(false);
/* 294 */       if ("0".equals(type)) {
/* 295 */         state = Boolean.valueOf(SMSRequest.send(url, phone, key, id));
/* 296 */       } else if ("2".equals(type)) {
/* 297 */         state = Boolean.valueOf(SMSVirtualRequest.send(phone));
/*     */         try {
/* 299 */           Object[] objs = 
/* 300 */             (Object[])PhoneCodeMap.getInstance()
/* 300 */             .getPhoneCodeMap().get(phone);
/* 301 */           String yzm = (String)objs[0];
/* 302 */           if (stringUtils.isNotBlank(text)) {
/* 303 */             if ("[yzm]".equals(text)) {
/* 304 */               text = "【" + sign + "】上网验证码是" + yzm + " 有效期" + time + 
/* 305 */                 "分钟! 【" + company + "】提供!";
/*     */             }
/* 307 */             else if (text.contains("[yzm]"))
/* 308 */               text = text.replace("[yzm]", yzm);
/*     */             else {
/* 310 */               text = "【" + sign + "】上网验证码是" + yzm + " 有效期" + 
/* 311 */                 time + "分钟! 【" + company + "】提供!";
/*     */             }
/*     */           }
/*     */           else {
/* 315 */             text = "【" + sign + "】上网验证码是" + yzm + " 有效期" + time + 
/* 316 */               "分钟! 【" + company + "】提供!";
/*     */           }
/* 318 */           map.put("msg", text);
/*     */         } catch (Exception e) {
/* 320 */           map.put("ret", "1");
/* 321 */           map.put("msg", "获取验证码失败，请使用其他方式或重试！");
/*     */         }
/* 323 */       } else if ("3".equals(type)) {
/* 324 */         state = Boolean.valueOf(SMSAliRequest.send(url, phone, key, secret, sign, 
/* 325 */           template, company, id));
/* 326 */       } else if ("4".equals(type)) {
/* 327 */         state = Boolean.valueOf(SMSEmppRequest.send(1, url, key, secret, template, 
/* 328 */           text, sign, company, phone, time, id));
/* 329 */       } else if ("5".equals(type)) {
/* 330 */         state = Boolean.valueOf(SMSUMSRequest.send(url, key, secret, template, text, 
/* 331 */           phone, id));
/* 332 */       } else if ("6".equals(type)) {
/* 333 */         state = Boolean.valueOf(SMSOpenMasRequest.send(1, url, key, secret, template, 
/* 334 */           text, sign, company, phone, time, id));
/* 335 */       } else if ("7".equals(type)) {
/* 336 */         state = Boolean.valueOf(SMSSUBMAILRequest.send(key, secret, sign, template, 
/* 337 */           phone, id));
/* 338 */       } else if ("8".equals(type)) {
/* 339 */         state = Boolean.valueOf(SMSKaiLiRequest.send(1, url, key, secret, text, sign, 
/* 340 */           company, phone, time, id));
/* 341 */       } else if ("9".equals(type)) {
/* 342 */         state = Boolean.valueOf(SMSSMGPAPIRequest.send(url, key, secret, sign, 
/* 343 */           template, text, phone, id));
/* 344 */       } else if ("10".equals(type)) {
/* 345 */         state = Boolean.valueOf(SMSIPYYRequest.send(url, key, secret, text, phone, id));
/* 346 */       } else if ("11".equals(type)) {
/* 347 */         state = Boolean.valueOf(SMSWXHYRequest.send(url, key, secret, text, phone, id));
/* 348 */       } else if ("12".equals(type)) {
/* 349 */         state = Boolean.valueOf(SMSCMCCMASRequest.send(1, url, key, secret, company, template, text, sign, phone, time, id));
/*     */       } else {
/* 351 */         HttpSession session = request.getSession();
/* 352 */         String mac = (String)session.getAttribute("ikmac");
/* 353 */         state = Boolean.valueOf(SMSProRequest.send(url, phone, mac, text, request, id));
/*     */       }
/*     */ 
/* 356 */       if (state.booleanValue()) {
/* 357 */         count = Long.valueOf(count.longValue() + 1L);
/* 358 */         Portalsmsapi smsapi = new Portalsmsapi();
/* 359 */         smsapi.setId(id);
/* 360 */         smsapi.setCount(count);
/* 361 */         this.portalsmsapiService.updatePortalsmsapiByKey(smsapi);
/* 362 */         map.put("ret", "0");
/*     */       } else {
/* 364 */         map.put("ret", "1");
/* 365 */         map.put("msg", "获取验证码失败，请使用其他认证方式或重试！");
/*     */       }
/*     */     } else {
/* 368 */       map.put("ret", "1");
/* 369 */       map.put("msg", "短信网关配置出错，请联系管理员！");
/*     */     }
/* 371 */     return map;
/*     */   }
/*     */ 
/*     */   @ResponseBody
/*     */   @RequestMapping({"/ajax_smsAPI"})
/*     */   public Map<String, String> weixinsms(String phone, HttpServletRequest request, HttpServletResponse response) {
/* 378 */     Map map = new HashMap();
/* 379 */     if ((stringUtils.isBlank(phone)) || (phone.length() != 11)) {
/* 380 */       map.put("ret", "1");
/* 381 */       map.put("msg", "手机号码不正确！");
/* 382 */       return map;
/*     */     }
/*     */     try {
/* 385 */       Long.parseLong(phone);
/*     */     } catch (Exception e) {
/* 387 */       map.put("ret", "1");
/* 388 */       map.put("msg", "手机号码不正确！");
/* 389 */       return map;
/*     */     }
/*     */ 
/* 392 */     if (1 == this.configService.getConfigByKey(Long.valueOf(1L)).getSmsAuthList().intValue()) {
/* 393 */       PortalphonesQuery phonesQuery = new PortalphonesQuery();
/* 394 */       phonesQuery.setPhone(phone);
/* 395 */       phonesQuery.setPhoneLike(false);
/* 396 */       int isCanAuth = this.portalphonesService.getPortalphonesCount(phonesQuery).intValue();
/* 397 */       if (isCanAuth <= 0) {
/* 398 */         map.put("ret", "1");
/* 399 */         map.put("msg", "当前手机号未授权，请联系管理员！");
/* 400 */         return map;
/*     */       }
/*     */     }
/*     */ 
/* 404 */     PortalsmsapiQuery query = new PortalsmsapiQuery();
/* 405 */     query.setState("1");
/* 406 */     List smsList = this.portalsmsapiService
/* 407 */       .getPortalsmsapiList(query);
/* 408 */     Long count = Long.valueOf(0L);
/* 409 */     String url = "";
/* 410 */     Long id = Long.valueOf(1L);
/* 411 */     String type = "0";
/* 412 */     String more = "0";
/* 413 */     String text = "";
/* 414 */     int time = 1;
/*     */ 
/* 416 */     String key = "";
/* 417 */     String secret = "";
/* 418 */     String sign = "";
/* 419 */     String template = "";
/* 420 */     String company = "";
/*     */ 
/* 422 */     if (smsList.size() > 0) {
/* 423 */       Portalsmsapi smsapi = (Portalsmsapi)smsList.get(0);
/* 424 */       count = smsapi.getCount();
/* 425 */       url = smsapi.getUrl();
/* 426 */       id = smsapi.getId();
/* 427 */       type = smsapi.getType();
/* 428 */       more = smsapi.getMore();
/* 429 */       text = smsapi.getText();
/* 430 */       time = smsapi.getTime().intValue();
/*     */ 
/* 432 */       key = smsapi.getAppkey();
/* 433 */       secret = smsapi.getAppsecret();
/* 434 */       sign = smsapi.getSmssign();
/* 435 */       template = smsapi.getSmstemplate();
/* 436 */       company = smsapi.getCompany();
/*     */     } else {
/* 438 */       Portalsmsapi smsapi = this.portalsmsapiService.getPortalsmsapiByKey(Long.valueOf(1L));
/* 439 */       count = smsapi.getCount();
/* 440 */       url = smsapi.getUrl();
/* 441 */       type = smsapi.getType();
/* 442 */       more = smsapi.getMore();
/* 443 */       text = smsapi.getText();
/* 444 */       time = smsapi.getTime().intValue();
/*     */ 
/* 446 */       key = smsapi.getAppkey();
/* 447 */       secret = smsapi.getAppsecret();
/* 448 */       sign = smsapi.getSmssign();
/* 449 */       template = smsapi.getSmstemplate();
/* 450 */       company = smsapi.getCompany();
/*     */     }
/*     */ 
/* 453 */     if ("0".equals(more)) {
/* 454 */       Boolean isOnline = Boolean.valueOf(false);
/* 455 */       Iterator it = OnlineMap.getInstance().getOnlineUserMap().keySet().iterator();
/* 456 */       while (it.hasNext()) {
/* 457 */         Object o = it.next();
/* 458 */         String host = o.toString();
/* 459 */         String[] loginInfo = (String[])OnlineMap.getInstance().getOnlineUserMap().get(host);
/* 460 */         String username = loginInfo[0];
/* 461 */         if (phone.equals(username)) {
/* 462 */           isOnline = Boolean.valueOf(true);
/* 463 */           break;
/*     */         }
/*     */       }
/* 466 */       if (isOnline.booleanValue()) {
/* 467 */         map.put("ret", "1");
/* 468 */         map.put("msg", "当前手机号已在线！");
/* 469 */         return map;
/*     */       }
/*     */     }
/*     */ 
/* 473 */     if (stringUtils.isNotBlank(url)) {
/* 474 */       Boolean state = Boolean.valueOf(false);
/* 475 */       if ("0".equals(type)) {
/* 476 */         state = Boolean.valueOf(SMSRequest.send(url, phone, key, id));
/* 477 */       } else if ("2".equals(type)) {
/* 478 */         state = Boolean.valueOf(SMSVirtualRequest.send(phone));
/*     */         try {
/* 480 */           Object[] objs = 
/* 481 */             (Object[])PhoneCodeMap.getInstance()
/* 481 */             .getPhoneCodeMap().get(phone);
/* 482 */           String yzm = (String)objs[0];
/* 483 */           if (stringUtils.isNotBlank(text)) {
/* 484 */             if ("[yzm]".equals(text)) {
/* 485 */               text = "【" + sign + "】上网验证码是" + yzm + " 有效期" + time + 
/* 486 */                 "分钟! 【" + company + "】提供!";
/*     */             }
/* 488 */             else if (text.contains("[yzm]"))
/* 489 */               text = text.replace("[yzm]", yzm);
/*     */             else {
/* 491 */               text = "【" + sign + "】上网验证码是" + yzm + " 有效期" + 
/* 492 */                 time + "分钟! 【" + company + "】提供!";
/*     */             }
/*     */           }
/*     */           else {
/* 496 */             text = "【" + sign + "】上网验证码是" + yzm + " 有效期" + time + 
/* 497 */               "分钟! 【" + company + "】提供!";
/*     */           }
/* 499 */           map.put("msg", text);
/*     */         } catch (Exception e) {
/* 501 */           map.put("ret", "1");
/* 502 */           map.put("msg", "获取验证码失败，请使用其他认证方式或重试！");
/*     */         }
/* 504 */       } else if ("3".equals(type)) {
/* 505 */         state = Boolean.valueOf(SMSAliRequest.send(url, phone, key, secret, sign, 
/* 506 */           template, company, id));
/* 507 */       } else if ("4".equals(type)) {
/* 508 */         state = Boolean.valueOf(SMSEmppRequest.send(0, url, key, secret, template, 
/* 509 */           text, sign, company, phone, time, id));
/* 510 */       } else if ("5".equals(type)) {
/* 511 */         state = Boolean.valueOf(SMSUMSRequest.send(url, key, secret, template, text, 
/* 512 */           phone, id));
/* 513 */       } else if ("6".equals(type)) {
/* 514 */         state = Boolean.valueOf(SMSOpenMasRequest.send(0, url, key, secret, template, 
/* 515 */           text, sign, company, phone, time, id));
/* 516 */       } else if ("7".equals(type)) {
/* 517 */         state = Boolean.valueOf(SMSSUBMAILRequest.send(key, secret, sign, template, 
/* 518 */           phone, id));
/* 519 */       } else if ("8".equals(type)) {
/* 520 */         state = Boolean.valueOf(SMSKaiLiRequest.send(0, url, key, secret, text, sign, 
/* 521 */           company, phone, time, id));
/* 522 */       } else if ("9".equals(type)) {
/* 523 */         state = Boolean.valueOf(SMSSMGPAPIRequest.send(url, key, secret, sign, 
/* 524 */           template, text, phone, id));
/* 525 */       } else if ("10".equals(type)) {
/* 526 */         state = Boolean.valueOf(SMSIPYYRequest.send(url, key, secret, text, phone, id));
/* 527 */       } else if ("11".equals(type)) {
/* 528 */         state = Boolean.valueOf(SMSWXHYRequest.send(url, key, secret, text, phone, id));
/* 529 */       } else if ("12".equals(type)) {
/* 530 */         state = Boolean.valueOf(SMSCMCCMASRequest.send(0, url, key, secret, company, template, text, sign, phone, time, id));
/*     */       } else {
/* 532 */         HttpSession session = request.getSession();
/* 533 */         String mac = (String)session.getAttribute("ikmac");
/* 534 */         state = Boolean.valueOf(SMSProRequest.send(url, phone, mac, text, request, id));
/*     */       }
/*     */ 
/* 537 */       if (state.booleanValue()) {
/* 538 */         count = Long.valueOf(count.longValue() + 1L);
/* 539 */         Portalsmsapi smsapi = new Portalsmsapi();
/* 540 */         smsapi.setId(id);
/* 541 */         smsapi.setCount(count);
/* 542 */         this.portalsmsapiService.updatePortalsmsapiByKey(smsapi);
/* 543 */         map.put("ret", "0");
/*     */       } else {
/* 545 */         map.put("ret", "1");
/* 546 */         map.put("msg", "获取验证码失败，请使用其他认证方式或重试！");
/*     */       }
/*     */     } else {
/* 549 */       map.put("ret", "1");
/* 550 */       map.put("msg", "短信网关配置出错，请联系管理员！");
/*     */     }
/* 552 */     return map;
/*     */   }
/*     */ 
/*     */   @ResponseBody
/*     */   @RequestMapping({"/get_ajax_onlineCount"})
/*     */   public Map<String, Object> onlineCount(HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 560 */     Map map = new HashMap();
/*     */ 
/* 562 */     int online_count = OnlineMap.getInstance().getOnlineUserMap().size() + 
/* 563 */       WifiDogOnlineMap.getInstance().getWifiDogOnlineMap().size();
/* 564 */     map.put("online_count", Integer.valueOf(online_count));
/*     */ 
/* 566 */     return map;
/*     */   }
/*     */ 
/*     */   @ResponseBody
/*     */   @RequestMapping({"/get_ajax_fenleiCount"})
/*     */   public Map<String, Object> fenleiCount()
/*     */   {
/* 574 */     Map map = new HashMap();
/*     */ 
/* 576 */     PortalaccountQuery pq = new PortalaccountQuery();
/* 577 */     pq.setState("0");
/*     */ 
/* 579 */     int lock_count = this.portalaccountService.getPortalaccountCount(pq).intValue();
/* 580 */     int acc_count = this.portalaccountService
/* 581 */       .getPortalaccountCount(new PortalaccountQuery()).intValue();
/*     */ 
/* 583 */     int online_count = OnlineMap.getInstance().getOnlineUserMap().size() + 
/* 584 */       WifiDogOnlineMap.getInstance().getWifiDogOnlineMap().size();
/*     */ 
/* 586 */     map.put("online_count", Integer.valueOf(online_count));
/* 587 */     map.put("acc_count", Integer.valueOf(acc_count));
/* 588 */     map.put("lock_count", Integer.valueOf(lock_count));
/* 589 */     map.put("true_count", Integer.valueOf(acc_count - lock_count));
/*     */ 
/* 591 */     int outline_count = acc_count - online_count;
/* 592 */     if (outline_count < 0) {
/* 593 */       outline_count = 0;
/*     */     }
/* 595 */     map.put("outline_count", Integer.valueOf(outline_count));
/*     */ 
/* 597 */     return map;
/*     */   }
/*     */ 
/*     */   @ResponseBody
/*     */   @RequestMapping({"/get_ajax_msgCount"})
/*     */   public Map<String, Object> msgCount(HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 605 */     Map map = new HashMap();
/* 606 */     Portaluser user = (Portaluser)request.getSession()
/* 607 */       .getAttribute("user");
/* 608 */     if ((user == null) || (user.getId() == null)) {
/* 609 */       map.put("msgCount", Integer.valueOf(0));
/* 610 */       return map;
/*     */     }
/* 612 */     String toid = user.getId().toString();
/* 613 */     PortalmessageQuery message = new PortalmessageQuery();
/* 614 */     message.setToid(toid);
/* 615 */     message.setState("0");
/* 616 */     message.setDelin("0");
/* 617 */     message.setToPos("0");
/* 618 */     map.put("msgCount", this.portalmessageService.getPortalmessageCount(message));
/* 619 */     return map;
/*     */   }
/*     */ 
/*     */   @ResponseBody
/*     */   @RequestMapping({"/get_ajax_log"})
/*     */   public Map<String, String> log(HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 628 */     SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
/* 629 */     Map map = new LinkedHashMap();
/* 630 */     PortallogrecordQuery logrecordQuery = new PortallogrecordQuery();
/* 631 */     logrecordQuery.setPageSize(7);
/* 632 */     logrecordQuery.setPage(1);
/* 633 */     logrecordQuery.orderbyId(false);
/*     */ 
/* 635 */     List<Portallogrecord> operationRecords = (List<Portallogrecord>) this.portallogrecordService.getPortallogrecordListWithPage(logrecordQuery).getList();
/* 637 */     int i = 7;
/* 638 */     for (Portallogrecord log : operationRecords) {
/* 639 */       String t = format.format(log.getRecDate());
/* 640 */       t = t + "." + i--;
/* 641 */       map.put(t, log.getInfo());
/*     */     }
/* 643 */     return map;
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.AjaxController
 * JD-Core Version:    0.6.2
 */