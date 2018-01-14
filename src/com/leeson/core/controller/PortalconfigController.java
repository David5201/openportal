/*     */ package com.leeson.core.controller;
/*     */ 
/*     */ import com.leeson.core.bean.Portalbas;
/*     */ import com.leeson.core.bean.Portalbasauth;
/*     */ import com.leeson.core.bean.Portalconfig;
/*     */ import com.leeson.core.query.PortalbasQuery;
/*     */ import com.leeson.core.query.PortalbasauthQuery;
/*     */ import com.leeson.core.query.PortalbasauthView;
/*     */ import com.leeson.core.query.PortalwebQuery;
/*     */ import com.leeson.core.service.ConfigService;
/*     */ import com.leeson.core.service.PortalbasService;
/*     */ import com.leeson.core.service.PortalbasauthService;
/*     */ import com.leeson.core.service.PortalconfigService;
/*     */ import com.leeson.core.service.PortalwebService;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.ui.ModelMap;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ 
/*     */ @Controller
/*     */ public class PortalconfigController
/*     */ {
/*  35 */   private static com.leeson.portal.core.model.Config base_cfg = com.leeson.portal.core.model.Config.getInstance();
/*     */ 
/*     */   @Autowired
/*     */   private PortalconfigService portalconfigService;
/*     */ 
/*     */   @Autowired
/*     */   private PortalbasauthService portalbasauthService;
/*     */ 
/*     */   @Autowired
/*     */   private PortalbasService portalbasService;
/*     */ 
/*     */   @Autowired
/*     */   private PortalwebService portalwebService;
/*     */ 
/*     */   @Autowired
/*     */   private ConfigService configService;
/*     */ 
/*  50 */   @RequestMapping({"/portalconfig/show"})
/*     */   public String show(ModelMap model) { Portalconfig e = this.portalconfigService.getPortalconfigByKey(Long.valueOf(1L));
/*  51 */     e.setIsdebug(String.valueOf(this.configService.getConfigByKey(Long.valueOf(1L)).getIsDebug()));
/*  52 */     model.addAttribute("entity", e);
/*  53 */     PortalbasauthQuery baq = new PortalbasauthQuery();
/*  54 */     baq.setBasid(Long.valueOf(0L));
/*  55 */     List<Portalbasauth> basauths = this.portalbasauthService.getPortalbasauthList(baq);
/*  56 */     for (Portalbasauth ba : basauths) {
/*  57 */       model.addAttribute("username" + ba.getType(), ba.getUsername());
/*  58 */       model.addAttribute("password" + ba.getType(), ba.getPassword());
/*  59 */       model.addAttribute("url" + ba.getType(), ba.getUrl());
/*  60 */       model.addAttribute("time" + ba.getType(), Long.valueOf(ba.getSessiontime().longValue() / 60000L));
/*     */     }
/*     */ 
/*  63 */     List webs = this.portalwebService.getPortalwebList(new PortalwebQuery());
/*  64 */     model.addAttribute("webs", webs);
/*  65 */     return "portalconfig/config";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/portalconfig/save"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String edit(Portalconfig e, ModelMap model, PortalbasauthView bav)
/*     */   {
/*  71 */     e.setId(Long.valueOf(1L));
/*  72 */     if ((e.getBas().equals("2")) || (e.getBas().equals("4"))) {
/*  73 */       e.setAuthType("0");
/*  74 */       e.setPortalVer("1");
/*     */     }
/*     */ 
/*  77 */     PortalbasQuery bq = new PortalbasQuery();
/*  78 */     bq.setBasIp(e.getBasIp());
/*  79 */     bq.setBasIpLike(false);
/*  80 */     if (this.portalbasService.getPortalbasList(bq).size() > 0) {
/*  81 */       model.addAttribute("entity", e);
/*  82 */       model.addAttribute("msg", "Bas IP 已经存在！!");
/*  83 */       PortalbasauthQuery baq = new PortalbasauthQuery();
/*  84 */       baq.setBasid(Long.valueOf(0L));
/*  85 */       List<Portalbasauth> basauths = this.portalbasauthService.getPortalbasauthList(baq);
/*  86 */       for (Portalbasauth ba : basauths) {
/*  87 */         model.addAttribute("username" + ba.getType(), ba.getUsername());
/*  88 */         model.addAttribute("password" + ba.getType(), ba.getPassword());
/*  89 */         model.addAttribute("url" + ba.getType(), ba.getUrl());
/*  90 */         model.addAttribute("time" + ba.getType(), Long.valueOf(ba.getSessiontime().longValue() / 60000L));
/*     */       }
/*  92 */       List webs = this.portalwebService.getPortalwebList(new PortalwebQuery());
/*  93 */       model.addAttribute("webs", webs);
/*  94 */       return "portalconfig/config";
/*     */     }
/*     */ 
/*  97 */     if (e.getAuthInterface() == null) {
/*  98 */       e.setAuthInterface("");
/*     */     }
/* 100 */     Portalconfig oldConfig = this.portalconfigService.getPortalconfigByKey(Long.valueOf(1L));
/* 101 */     base_cfg.getConfigMap().remove(oldConfig.getBasIp());
/* 102 */     e.setIsdebug(String.valueOf(this.configService.getConfigByKey(Long.valueOf(1L)).getIsDebug()));
/* 103 */     if (e.getLateAuthTime() == null) {
/* 104 */       e.setLateAuthTime(Long.valueOf(10L));
/*     */     }
/* 106 */     this.portalconfigService.updatePortalconfigByKeyAll(e);
/*     */ 
/* 108 */     SetAuth(e, bav);
/*     */ 
/* 110 */     e = this.portalconfigService.getPortalconfigByKey(Long.valueOf(1L));
/* 111 */     InitConfig(e);
/* 112 */     model.addAttribute("entity", e);
/* 113 */     model.addAttribute("msg", "设备对接设置信息更新成功！！");
/* 114 */     PortalbasauthQuery baq = new PortalbasauthQuery();
/* 115 */     baq.setBasid(Long.valueOf(0L));
/* 116 */     List<Portalbasauth> basauths = this.portalbasauthService.getPortalbasauthList(baq);
/* 117 */     for (Portalbasauth ba : basauths) {
/* 118 */       model.addAttribute("username" + ba.getType(), ba.getUsername());
/* 119 */       model.addAttribute("password" + ba.getType(), ba.getPassword());
/* 120 */       model.addAttribute("url" + ba.getType(), ba.getUrl());
/* 121 */       model.addAttribute("time" + ba.getType(), Long.valueOf(ba.getSessiontime().longValue() / 60000L));
/*     */     }
/* 123 */     List webs = this.portalwebService.getPortalwebList(new PortalwebQuery());
/* 124 */     model.addAttribute("webs", webs);
/* 125 */     return "portalconfig/config";
/*     */   }
/*     */ 
/*     */   private void SetAuth(Portalconfig e, PortalbasauthView bav) {
/* 129 */     if (bav.getTime0() == null) {
/* 130 */       bav.setTime0(Long.valueOf(0L));
/*     */     }
/* 132 */     if (bav.getTime1() == null) {
/* 133 */       bav.setTime1(Long.valueOf(0L));
/*     */     }
/* 135 */     if (bav.getTime2() == null) {
/* 136 */       bav.setTime2(Long.valueOf(0L));
/*     */     }
/* 138 */     if (bav.getTime3() == null) {
/* 139 */       bav.setTime3(Long.valueOf(0L));
/*     */     }
/* 141 */     if (bav.getTime4() == null) {
/* 142 */       bav.setTime4(Long.valueOf(0L));
/*     */     }
/* 144 */     if (bav.getTime5() == null) {
/* 145 */       bav.setTime5(Long.valueOf(0L));
/*     */     }
/* 147 */     if (bav.getTime6() == null) {
/* 148 */       bav.setTime6(Long.valueOf(0L));
/*     */     }
/* 150 */     if (bav.getTime7() == null) {
/* 151 */       bav.setTime7(Long.valueOf(0L));
/*     */     }
/*     */ 
/* 154 */     PortalbasauthQuery baq = new PortalbasauthQuery();
/* 155 */     baq.setBasid(Long.valueOf(0L));
/*     */ 
/* 157 */     baq.setType(Integer.valueOf(0));
/* 158 */     List basauths = this.portalbasauthService.getPortalbasauthList(baq);
/* 159 */     if (basauths.size() > 0) {
/* 160 */       Portalbasauth ba = (Portalbasauth)basauths.get(0);
/* 161 */       ba.setBasip(e.getBasIp());
/* 162 */       ba.setUsername(bav.getUsername0());
/* 163 */       ba.setPassword(bav.getPassword0());
/* 164 */       ba.setUrl(bav.getUrl0());
/* 165 */       ba.setType(Integer.valueOf(0));
/* 166 */       ba.setSessiontime(Long.valueOf(bav.getTime0().longValue() * 60000L));
/* 167 */       this.portalbasauthService.updatePortalbasauthByKeyAll(ba);
/*     */     } else {
/* 169 */       Portalbasauth ba = new Portalbasauth();
/* 170 */       ba.setBasid(Long.valueOf(0L));
/* 171 */       ba.setBasip(e.getBasIp());
/* 172 */       ba.setUsername(bav.getUsername0());
/* 173 */       ba.setPassword(bav.getPassword0());
/* 174 */       ba.setUrl(bav.getUrl0());
/* 175 */       ba.setType(Integer.valueOf(0));
/* 176 */       ba.setSessiontime(Long.valueOf(bav.getTime0().longValue() * 60000L));
/* 177 */       this.portalbasauthService.addPortalbasauth(ba);
/*     */     }
/*     */ 
/* 180 */     baq.setType(Integer.valueOf(1));
/* 181 */     basauths = this.portalbasauthService.getPortalbasauthList(baq);
/* 182 */     if (basauths.size() > 0) {
/* 183 */       Portalbasauth ba = (Portalbasauth)basauths.get(0);
/* 184 */       ba.setBasip(e.getBasIp());
/* 185 */       ba.setUsername(bav.getUsername1());
/* 186 */       ba.setPassword(bav.getPassword1());
/* 187 */       ba.setUrl(bav.getUrl1());
/* 188 */       ba.setSessiontime(Long.valueOf(bav.getTime1().longValue() * 60000L));
/* 189 */       this.portalbasauthService.updatePortalbasauthByKeyAll(ba);
/*     */     } else {
/* 191 */       Portalbasauth ba = new Portalbasauth();
/* 192 */       ba.setBasid(Long.valueOf(0L));
/* 193 */       ba.setBasip(e.getBasIp());
/* 194 */       ba.setUsername(bav.getUsername1());
/* 195 */       ba.setPassword(bav.getPassword1());
/* 196 */       ba.setUrl(bav.getUrl1());
/* 197 */       ba.setType(Integer.valueOf(1));
/* 198 */       ba.setSessiontime(Long.valueOf(bav.getTime1().longValue() * 60000L));
/* 199 */       this.portalbasauthService.addPortalbasauth(ba);
/*     */     }
/*     */ 
/* 202 */     baq.setType(Integer.valueOf(2));
/* 203 */     basauths = this.portalbasauthService.getPortalbasauthList(baq);
/* 204 */     if (basauths.size() > 0) {
/* 205 */       Portalbasauth ba = (Portalbasauth)basauths.get(0);
/* 206 */       ba.setBasip(e.getBasIp());
/* 207 */       ba.setUsername(bav.getUsername2());
/* 208 */       ba.setPassword(bav.getPassword2());
/* 209 */       ba.setUrl(bav.getUrl2());
/* 210 */       ba.setSessiontime(Long.valueOf(bav.getTime2().longValue() * 60000L));
/* 211 */       this.portalbasauthService.updatePortalbasauthByKeyAll(ba);
/*     */     } else {
/* 213 */       Portalbasauth ba = new Portalbasauth();
/* 214 */       ba.setBasid(Long.valueOf(0L));
/* 215 */       ba.setBasip(e.getBasIp());
/* 216 */       ba.setUsername(bav.getUsername2());
/* 217 */       ba.setPassword(bav.getPassword2());
/* 218 */       ba.setUrl(bav.getUrl2());
/* 219 */       ba.setType(Integer.valueOf(2));
/* 220 */       ba.setSessiontime(Long.valueOf(bav.getTime2().longValue() * 60000L));
/* 221 */       this.portalbasauthService.addPortalbasauth(ba);
/*     */     }
/*     */ 
/* 224 */     baq.setType(Integer.valueOf(3));
/* 225 */     basauths = this.portalbasauthService.getPortalbasauthList(baq);
/* 226 */     if (basauths.size() > 0) {
/* 227 */       Portalbasauth ba = (Portalbasauth)basauths.get(0);
/* 228 */       ba.setBasip(e.getBasIp());
/* 229 */       ba.setUsername(bav.getUsername3());
/* 230 */       ba.setPassword(bav.getPassword3());
/* 231 */       ba.setUrl(bav.getUrl3());
/* 232 */       ba.setSessiontime(Long.valueOf(bav.getTime3().longValue() * 60000L));
/* 233 */       this.portalbasauthService.updatePortalbasauthByKeyAll(ba);
/*     */     } else {
/* 235 */       Portalbasauth ba = new Portalbasauth();
/* 236 */       ba.setBasid(Long.valueOf(0L));
/* 237 */       ba.setBasip(e.getBasIp());
/* 238 */       ba.setUsername(bav.getUsername3());
/* 239 */       ba.setPassword(bav.getPassword3());
/* 240 */       ba.setUrl(bav.getUrl3());
/* 241 */       ba.setType(Integer.valueOf(3));
/* 242 */       ba.setSessiontime(Long.valueOf(bav.getTime3().longValue() * 60000L));
/* 243 */       this.portalbasauthService.addPortalbasauth(ba);
/*     */     }
/*     */ 
/* 246 */     baq.setType(Integer.valueOf(4));
/* 247 */     basauths = this.portalbasauthService.getPortalbasauthList(baq);
/* 248 */     if (basauths.size() > 0) {
/* 249 */       Portalbasauth ba = (Portalbasauth)basauths.get(0);
/* 250 */       ba.setBasip(e.getBasIp());
/* 251 */       ba.setUsername(bav.getUsername4());
/* 252 */       ba.setPassword(bav.getPassword4());
/* 253 */       ba.setUrl(bav.getUrl4());
/* 254 */       ba.setSessiontime(Long.valueOf(bav.getTime4().longValue() * 60000L));
/* 255 */       this.portalbasauthService.updatePortalbasauthByKeyAll(ba);
/*     */     } else {
/* 257 */       Portalbasauth ba = new Portalbasauth();
/* 258 */       ba.setBasid(Long.valueOf(0L));
/* 259 */       ba.setBasip(e.getBasIp());
/* 260 */       ba.setUsername(bav.getUsername4());
/* 261 */       ba.setPassword(bav.getPassword4());
/* 262 */       ba.setUrl(bav.getUrl4());
/* 263 */       ba.setType(Integer.valueOf(4));
/* 264 */       ba.setSessiontime(Long.valueOf(bav.getTime4().longValue() * 60000L));
/* 265 */       this.portalbasauthService.addPortalbasauth(ba);
/*     */     }
/*     */ 
/* 268 */     baq.setType(Integer.valueOf(5));
/* 269 */     basauths = this.portalbasauthService.getPortalbasauthList(baq);
/* 270 */     if (basauths.size() > 0) {
/* 271 */       Portalbasauth ba = (Portalbasauth)basauths.get(0);
/* 272 */       ba.setBasip(e.getBasIp());
/* 273 */       ba.setUsername(bav.getUsername5());
/* 274 */       ba.setPassword(bav.getPassword5());
/* 275 */       ba.setUrl(bav.getUrl5());
/* 276 */       ba.setSessiontime(Long.valueOf(bav.getTime5().longValue() * 60000L));
/* 277 */       this.portalbasauthService.updatePortalbasauthByKeyAll(ba);
/*     */     } else {
/* 279 */       Portalbasauth ba = new Portalbasauth();
/* 280 */       ba.setBasid(Long.valueOf(0L));
/* 281 */       ba.setBasip(e.getBasIp());
/* 282 */       ba.setUsername(bav.getUsername5());
/* 283 */       ba.setPassword(bav.getPassword5());
/* 284 */       ba.setUrl(bav.getUrl5());
/* 285 */       ba.setType(Integer.valueOf(5));
/* 286 */       ba.setSessiontime(Long.valueOf(bav.getTime5().longValue() * 60000L));
/* 287 */       this.portalbasauthService.addPortalbasauth(ba);
/*     */     }
/*     */ 
/* 290 */     baq.setType(Integer.valueOf(6));
/* 291 */     basauths = this.portalbasauthService.getPortalbasauthList(baq);
/* 292 */     if (basauths.size() > 0) {
/* 293 */       Portalbasauth ba = (Portalbasauth)basauths.get(0);
/* 294 */       ba.setBasip(e.getBasIp());
/* 295 */       ba.setUsername(bav.getUsername6());
/* 296 */       ba.setPassword(bav.getPassword6());
/* 297 */       ba.setUrl(bav.getUrl6());
/* 298 */       ba.setSessiontime(Long.valueOf(bav.getTime6().longValue() * 60000L));
/* 299 */       this.portalbasauthService.updatePortalbasauthByKeyAll(ba);
/*     */     } else {
/* 301 */       Portalbasauth ba = new Portalbasauth();
/* 302 */       ba.setBasid(Long.valueOf(0L));
/* 303 */       ba.setBasip(e.getBasIp());
/* 304 */       ba.setUsername(bav.getUsername6());
/* 305 */       ba.setPassword(bav.getPassword6());
/* 306 */       ba.setUrl(bav.getUrl6());
/* 307 */       ba.setType(Integer.valueOf(6));
/* 308 */       ba.setSessiontime(Long.valueOf(bav.getTime6().longValue() * 60000L));
/* 309 */       this.portalbasauthService.addPortalbasauth(ba);
/*     */     }
/*     */ 
/* 312 */     baq.setType(Integer.valueOf(7));
/* 313 */     basauths = this.portalbasauthService.getPortalbasauthList(baq);
/* 314 */     if (basauths.size() > 0) {
/* 315 */       Portalbasauth ba = (Portalbasauth)basauths.get(0);
/* 316 */       ba.setBasip(e.getBasIp());
/* 317 */       ba.setUsername(bav.getUsername7());
/* 318 */       ba.setPassword(bav.getPassword7());
/* 319 */       ba.setUrl(bav.getUrl7());
/* 320 */       ba.setSessiontime(Long.valueOf(bav.getTime7().longValue() * 60000L));
/* 321 */       this.portalbasauthService.updatePortalbasauthByKeyAll(ba);
/*     */     } else {
/* 323 */       Portalbasauth ba = new Portalbasauth();
/* 324 */       ba.setBasid(Long.valueOf(0L));
/* 325 */       ba.setBasip(e.getBasIp());
/* 326 */       ba.setUsername(bav.getUsername7());
/* 327 */       ba.setPassword(bav.getPassword7());
/* 328 */       ba.setUrl(bav.getUrl7());
/* 329 */       ba.setType(Integer.valueOf(7));
/* 330 */       ba.setSessiontime(Long.valueOf(bav.getTime7().longValue() * 60000L));
/* 331 */       this.portalbasauthService.addPortalbasauth(ba);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void InitConfig(Portalconfig config)
/*     */   {
/* 340 */     Portalbas cfg = new Portalbas();
/*     */ 
/* 342 */     cfg.setBasIp(config.getBasIp());
/* 343 */     cfg.setBasPort(config.getBasPort());
/* 344 */     cfg.setSharedSecret(config.getSharedSecret());
/* 345 */     cfg.setAuthType(config.getAuthType());
/* 346 */     cfg.setTimeoutSec(config.getTimeoutSec());
/* 347 */     cfg.setPortalVer(config.getPortalVer());
/* 348 */     cfg.setBas(config.getBas());
/* 349 */     cfg.setIsdebug(String.valueOf(this.configService.getConfigByKey(Long.valueOf(1L)).getIsDebug()));
/* 350 */     cfg.setIsOut(config.getIsOut());
/* 351 */     cfg.setIsPortalCheck(config.getIsPortalCheck());
/* 352 */     cfg.setBasUser(config.getBasUser());
/* 353 */     cfg.setBasPwd(config.getBasPwd());
/* 354 */     cfg.setAuthInterface(config.getAuthInterface());
/* 355 */     cfg.setWeb(config.getWeb());
/* 356 */     cfg.setBasname(config.getBasname());
/* 357 */     cfg.setIsComputer(config.getIsComputer());
/* 358 */     cfg.setLateAuth(config.getLateAuth());
/* 359 */     cfg.setLateAuthTime(config.getLateAuthTime());
/* 360 */     cfg.setIsNtf(config.getIsNtf());
/*     */ 
/* 362 */     base_cfg.getConfigMap().put(config.getBasIp(), cfg);
/* 363 */     base_cfg.getConfigMap().put("", cfg);
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.controller.PortalconfigController
 * JD-Core Version:    0.6.2
 */