/*     */ package ikuaiAPI;
/*     */ 
/*     */ import AC.Utils.WR;
/*     */ import com.leeson.common.utils.ThreadLocalDateUtil;
/*     */ import com.leeson.common.utils.stringUtils;
/*     */ import com.leeson.core.bean.Portalaccount;
/*     */ import com.leeson.core.bean.Portalautologin;
/*     */ import com.leeson.core.bean.Portalbas;
/*     */ import com.leeson.core.bean.Portalonlinelimit;
/*     */ import com.leeson.core.query.PortalaccountQuery;
/*     */ import com.leeson.core.service.PortalaccountService;
/*     */ import com.leeson.core.service.PortalautologinService;
/*     */ import com.leeson.core.service.PortalonlinelimitService;
/*     */ import com.leeson.portal.core.model.AutoLoginMacMap;
/*     */ import com.leeson.portal.core.model.AutoLoginMap;
/*     */ import com.leeson.portal.core.model.Config;
/*     */ import com.leeson.portal.core.model.MacLimitMap;
/*     */ import com.leeson.portal.core.model.UserLimitMap;
/*     */ import com.leeson.portal.core.model.isDo;
/*     */ import com.leeson.portal.core.utils.SpringContextHelper;
/*     */ import ikuaiAPI.utils.DES_CBC;
/*     */ import java.net.DatagramPacket;
/*     */ import java.net.DatagramSocket;
/*     */ import java.net.InetAddress;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class RoamAutoAuth extends Thread
/*     */ {
/*  34 */   private static Config config = Config.getInstance();
/*  35 */   private static Logger log = Logger.getLogger(RoamAutoAuth.class);
/*     */ 
/*  39 */   PortalaccountService accountService = (PortalaccountService)SpringContextHelper.getBean("portalaccountServiceImpl");
/*     */ 
/*  41 */   PortalonlinelimitService onlinelimitService = (PortalonlinelimitService)SpringContextHelper.getBean("portalonlinelimitServiceImpl");
/*     */ 
/*  43 */   PortalautologinService autologinService = (PortalautologinService)SpringContextHelper.getBean("portalautologinServiceImpl");
/*     */ 
/*  45 */   private static Boolean isRun = Boolean.valueOf(true);
/*  46 */   private static DatagramSocket socket = null;
/*     */ 
/*  48 */   DatagramPacket data = null;
/*     */ 
/*     */   public RoamAutoAuth(DatagramPacket data)
/*     */   {
/*  52 */     this.data = data;
/*     */   }
/*     */ 
/*     */   public void run() {
/*     */     try {
/*  57 */       byte[] Req_Data_Base = new byte[this.data.getLength()];
/*  58 */       for (int l = 0; l < Req_Data_Base.length; l++) {
/*  59 */         Req_Data_Base[l] = this.data.getData()[l];
/*     */       }
/*     */ 
/*  63 */       String ip = this.data.getAddress().getHostAddress();
/*  64 */       int port = this.data.getPort();
/*     */ 
/*  66 */       if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
/*  67 */         log.info("Rececie ikuai RoamAutoAuth Packet : SourceIP=" + ip + 
/*  68 */           " SourcePort=" + port + " Size=" + this.data.getLength() + 
/*  69 */           " Data=" + WR.Getbyte2HexString(Req_Data_Base));
/*     */       }
/*     */ 
/*  73 */       byte[] Ver = new byte[1];
/*  74 */       byte[] Type = new byte[1];
/*  75 */       byte[] Mod = new byte[1];
/*  76 */       byte[] AttrNum = new byte[1];
/*     */ 
/*  84 */       Ver[0] = Req_Data_Base[0];
/*  85 */       Type[0] = Req_Data_Base[1];
/*  86 */       Mod[0] = Req_Data_Base[2];
/*  87 */       AttrNum[0] = Req_Data_Base[3];
/*     */ 
/*  89 */       String usermac = null;
/*  90 */       String userip = null;
/*  91 */       int pos = 4;
/*     */ 
/*  93 */       int AN = AttrNum[0] & 0xFF;
/*  94 */       if (AN > 0) {
/*  95 */         int num = 1;
/*  96 */         while (num <= AN) {
/*  97 */           int type = Req_Data_Base[pos] & 0xFF;
/*  98 */           pos++;
/*  99 */           int len = (Req_Data_Base[pos] & 0xFF) - 2;
/* 100 */           pos++;
/* 101 */           byte[] buf = new byte[len];
/* 102 */           for (int l = 0; l < buf.length; l++) {
/* 103 */             buf[l] = Req_Data_Base[(pos + l)];
/*     */           }
/* 105 */           pos += len;
/* 106 */           if (type == 3)
/* 107 */             usermac = new String(buf);
/* 108 */           else if (type == 4) {
/* 109 */             userip = new String(buf);
/*     */           }
/* 111 */           num++;
/*     */         }
/*     */       }
/*     */ 
/* 115 */       String isDes = "no";
/* 116 */       if ((Mod[0] & 0xFF) == 1) {
/* 117 */         isDes = "yes";
/*     */       }
/*     */ 
/* 120 */       if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
/* 121 */         log.info("ikuai RoamAutoAuth From Packet Info : userMac=" + usermac + 
/* 122 */           " userIP=" + userip + " isDes=" + isDes);
/*     */       }
/*     */ 
/* 125 */       String iv = "roamauth";
/* 126 */       String key = "OpenPortal";
/* 127 */       String usrStr = "";
/* 128 */       String pwdStr = "";
/*     */ 
/* 130 */       boolean isRoamAutoAuth = false;
/* 131 */       if (stringUtils.isNotBlank(usermac)) {
/* 132 */         String[] infos = checkMac(usermac);
/* 133 */         if ((infos != null) && (infos.length == 2)) {
/* 134 */           usrStr = infos[0];
/* 135 */           pwdStr = infos[1];
/* 136 */           isRoamAutoAuth = true;
/*     */         }
/*     */       }
/*     */ 
/* 140 */       if (isRoamAutoAuth) {
/* 141 */         byte[] usr = usrStr.getBytes();
/* 142 */         byte[] pwd = pwdStr.getBytes();
/* 143 */         Req_Data_Base[1] = 2;
/* 144 */         Req_Data_Base[3] = 4;
/*     */ 
/* 146 */         if ((Mod[0] & 0xFF) == 1) {
/*     */           try {
/* 148 */             pwd = DES_CBC.encrypt(pwdStr, key, iv);
/* 149 */             pwdStr = WR.Getbyte2HexString(pwd);
/*     */           } catch (Exception e) {
/* 151 */             if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
/* 152 */               log.error("==============ERROR Start=============");
/* 153 */               log.error(e);
/* 154 */               log.error("ERROR INFO ", e);
/* 155 */               log.error("==============ERROR End=============");
/*     */             }
/* 157 */             isDes = "no";
/* 158 */             Req_Data_Base[2] = 0;
/*     */           }
/*     */         }
/*     */ 
/* 162 */         if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
/* 163 */           log.info("ikuai RoamAutoAuth To Packet Info : userMac=" + usermac + 
/* 164 */             " userIP=" + userip + " usr=" + usrStr + " pwd=" + pwdStr + " isDes=" + isDes);
/*     */         }
/*     */ 
/* 167 */         byte[] Ack_Data = new byte[Req_Data_Base.length + usr.length + 
/* 168 */           pwd.length + 4];
/* 169 */         for (int l = 0; l < Req_Data_Base.length; l++) {
/* 170 */           Ack_Data[l] = Req_Data_Base[l];
/*     */         }
/*     */ 
/* 173 */         pos = Req_Data_Base.length;
/* 174 */         Ack_Data[pos] = 1;
/* 175 */         Ack_Data[(pos + 1)] = ((byte)(usr.length + 2));
/* 176 */         for (int i = 0; i < usr.length; i++) {
/* 177 */           Ack_Data[(pos + 2 + i)] = usr[i];
/*     */         }
/*     */ 
/* 180 */         pos = Req_Data_Base.length + 2 + usr.length;
/* 181 */         Ack_Data[pos] = 2;
/* 182 */         Ack_Data[(pos + 1)] = ((byte)(pwd.length + 2));
/* 183 */         for (int i = 0; i < pwd.length; i++) {
/* 184 */           Ack_Data[(pos + 2 + i)] = pwd[i];
/*     */         }
/*     */ 
/*     */         try
/*     */         {
/* 190 */           DatagramPacket data = new DatagramPacket(Ack_Data, Ack_Data.length, 
/* 191 */             InetAddress.getByName(ip), port);
/* 192 */           socket.send(data);
/*     */         } catch (Exception e) {
/* 194 */           if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
/* 195 */             log.error("==============ERROR Start=============");
/* 196 */             log.error(e);
/* 197 */             log.error("ERROR INFO ", e);
/* 198 */             log.error("==============ERROR End=============");
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 204 */         if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
/* 205 */           log.info("ACK ikuai RoamAutoAuth Packet : ToIP=" + ip + 
/* 206 */             " ToPort=" + port + " Size=" + Ack_Data.length + 
/* 207 */             " Data=" + WR.Getbyte2HexString(Ack_Data));
/*     */         }
/*     */       }
/* 210 */       else if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
/* 211 */         log.info("ikuai RoamAutoAuth : userMac=" + usermac + 
/* 212 */           " userIP=" + userip + " Can not RoamAutoAuth !!!!!!!!!!!!!!");
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 216 */       log.error("==============ERROR Start=============");
/* 217 */       log.error(e);
/* 218 */       log.error("ERROR INFO ", e);
/* 219 */       log.error("==============ERROR End=============");
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void openServer() {
/*     */     try {
/* 225 */       socket = new DatagramSocket(50200);
/* 226 */       if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
/* 227 */         log.info("ikuai RoamAutoAuth Service Start OK , Listen Portal UDP 50200 !!");
/*     */       }
/*     */ 
/* 230 */       while (isRun.booleanValue()) {
/* 231 */         byte[] b = new byte[100];
/* 232 */         DatagramPacket data = new DatagramPacket(b, b.length);
/* 233 */         socket.receive(data);
/* 234 */         new RoamAutoAuth(data).start();
/*     */       }
/*     */     } catch (Exception e) {
/* 237 */       if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
/* 238 */         log.error("==============ERROR Start=============");
/* 239 */         log.error(e);
/* 240 */         log.error("ERROR INFO ", e);
/* 241 */         log.error("==============ERROR End=============");
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void closeServer()
/*     */   {
/*     */     try {
/* 249 */       isRun = Boolean.valueOf(false);
/* 250 */       if (socket != null) {
/* 251 */         socket.close();
/* 252 */         socket = null;
/*     */       }
/*     */     } catch (Exception e) {
/* 255 */       if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
/* 256 */         log.error("==============ERROR Start=============");
/* 257 */         log.error(e);
/* 258 */         log.error("ERROR INFO ", e);
/* 259 */         log.error("==============ERROR End=============");
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private boolean checkTimeOut(String userState, Long userId, Date userDate, Long userTime)
/*     */   {
/* 274 */     Portalaccount account = this.accountService.getPortalaccountByKey(userId);
/*     */ 
/* 276 */     if (userState.equals(String.valueOf(0))) {
/* 277 */       return false;
/*     */     }
/*     */ 
/* 280 */     if (userState.equals(String.valueOf(1))) {
/* 281 */       return true;
/*     */     }
/*     */ 
/* 284 */     if (userState.equals(String.valueOf(3))) {
/* 285 */       Date now = new Date();
/* 286 */       if (userDate.getTime() > now.getTime()) {
/* 287 */         return true;
/*     */       }
/* 289 */       account.setState(String.valueOf(2));
/* 290 */       this.accountService.updatePortalaccountByKey(account);
/* 291 */       userState = "2";
/*     */     }
/*     */ 
/* 295 */     if (userState.equals("2")) {
/* 296 */       if (userTime.longValue() > 0L) {
/* 297 */         return true;
/*     */       }
/* 299 */       account.setState(String.valueOf(0));
/* 300 */       this.accountService.updatePortalaccountByKey(account);
/* 301 */       return false;
/*     */     }
/*     */ 
/* 304 */     return false;
/*     */   }
/*     */ 
/*     */   private int CheckMacTimeLimitMethod(String param, Long id)
/*     */   {
/* 315 */     Portalonlinelimit onlinelimit = this.onlinelimitService
/* 316 */       .getPortalonlinelimitByKey(id);
/* 317 */     if (onlinelimit.getState().intValue() == 1) {
/* 318 */       if (onlinelimit.getType().intValue() == 0) {
/* 319 */         if ((stringUtils.isNotBlank(param)) && (!"error".equals(param))) {
/* 320 */           String[] TimeInfo = 
/* 321 */             (String[])MacLimitMap.getInstance()
/* 321 */             .getMacLimitMap().get(param);
/* 322 */           if (TimeInfo != null) {
/* 323 */             Long timepermit = onlinelimit.getTime();
/* 324 */             if (Long.valueOf(TimeInfo[1]).longValue() >= timepermit.longValue())
/* 325 */               return 1;
/*     */           }
/*     */         }
/*     */         else {
/* 329 */           return 2;
/*     */         }
/*     */       }
/* 332 */       else if (stringUtils.isNotBlank(param)) {
/* 333 */         String[] TimeInfo = 
/* 334 */           (String[])UserLimitMap.getInstance()
/* 334 */           .getUserLimitMap().get(param);
/* 335 */         if (TimeInfo != null) {
/* 336 */           Long timepermit = onlinelimit.getTime();
/* 337 */           if (Long.valueOf(TimeInfo[1]).longValue() >= timepermit.longValue())
/* 338 */             return 1;
/*     */         }
/*     */       }
/*     */       else {
/* 342 */         return 2;
/*     */       }
/*     */     }
/*     */ 
/* 346 */     return 0;
/*     */   }
/*     */ 
/*     */   public static boolean Do() {
/* 350 */     Long isThis = Long.valueOf(new Date().getTime());
/* 351 */     boolean Do = false;
/* 352 */     if (isThis.longValue() < isDo.getInstance().getId().longValue()) {
/* 353 */       Do = true;
/*     */     }
/* 355 */     return Do;
/*     */   }
/*     */ 
/*     */   public String[] checkMac(String ikmac) {
/* 359 */     if ((Do()) && 
/* 360 */       (stringUtils.isNotBlank(ikmac))) {
/* 361 */       String[] macTimeInfo = 
/* 362 */         (String[])AutoLoginMacMap.getInstance()
/* 362 */         .getAutoLoginMacMap().get(ikmac);
/* 363 */       if (macTimeInfo != null) {
/* 364 */         String[] userinfo = 
/* 365 */           (String[])AutoLoginMap.getInstance()
/* 365 */           .getAutoLoginMap().get(ikmac);
/*     */         try {
/* 367 */           Long id = Long.valueOf(macTimeInfo[2]);
/* 368 */           if (1 != CheckMacTimeLimitMethod(ikmac, id)) {
/* 369 */             String authUser = userinfo[0];
/* 370 */             String authPwd = userinfo[1];
/* 371 */             String username = userinfo[2];
/* 372 */             Portalautologin autologin = this.autologinService
/* 373 */               .getPortalautologinByKey(id);
/* 374 */             if ((autologin != null) && 
/* 375 */               (autologin.getState().intValue() == 1)) {
/* 376 */               Long timepermit = autologin.getTime();
/* 377 */               String loginTimeString = macTimeInfo[0];
/* 378 */               Long oldcostTime = 
/* 379 */                 Long.valueOf(macTimeInfo[1]);
/* 380 */               Long costTime = oldcostTime;
/* 381 */               if (stringUtils.isNotBlank(loginTimeString)) {
/* 382 */                 Date loginTime = 
/* 383 */                   ThreadLocalDateUtil.parse(loginTimeString);
/* 384 */                 String nowString = 
/* 385 */                   ThreadLocalDateUtil.format(new Date());
/* 386 */                 Date nowTime = ThreadLocalDateUtil.parse(nowString);
/* 387 */                 costTime = Long.valueOf(nowTime.getTime() - 
/* 388 */                   loginTime.getTime() + 
/* 389 */                   oldcostTime.longValue());
/*     */               }
/* 391 */               if ((costTime.longValue() < timepermit.longValue()) || 
/* 392 */                 (timepermit.longValue() <= 0L)) {
/* 393 */                 Long userId = null;
/* 394 */                 String userState = null;
/* 395 */                 boolean CheckAccount = false;
/* 396 */                 if (3L == id.longValue())
/*     */                 {
/* 398 */                   PortalaccountQuery accq = new PortalaccountQuery();
/* 399 */                   accq.setLoginName(username);
/* 400 */                   List accs = this.accountService
/* 401 */                     .getPortalaccountList(accq);
/* 402 */                   if ((accs != null) && 
/* 403 */                     (accs.size() == 1)) {
/* 404 */                     Portalaccount acc = (Portalaccount)accs.get(0);
/* 405 */                     if (acc != null) {
/* 406 */                       userId = acc.getId();
/* 407 */                       Date userDate = acc
/* 408 */                         .getDate();
/* 409 */                       Long userTime = acc
/* 410 */                         .getTime();
/* 411 */                       userState = acc.getState();
/*     */ 
/* 415 */                       if (checkTimeOut(userState, 
/* 414 */                         userId, userDate, 
/* 415 */                         userTime))
/* 416 */                         CheckAccount = true;
/*     */                     }
/*     */                   }
/*     */                 }
/*     */                 else {
/* 421 */                   CheckAccount = true;
/*     */                 }
/*     */ 
/* 424 */                 if (CheckAccount) {
/* 425 */                   String[] infos = new String[2];
/* 426 */                   infos[0] = authUser;
/* 427 */                   infos[1] = authPwd;
/* 428 */                   return infos;
/*     */                 }
/* 430 */                 AutoLoginMacMap.getInstance()
/* 431 */                   .getAutoLoginMacMap()
/* 432 */                   .remove(ikmac);
/* 433 */                 AutoLoginMap.getInstance()
/* 434 */                   .getAutoLoginMap()
/* 435 */                   .remove(ikmac);
/*     */               }
/*     */               else
/*     */               {
/* 439 */                 AutoLoginMacMap.getInstance()
/* 440 */                   .getAutoLoginMacMap()
/* 441 */                   .remove(ikmac);
/* 442 */                 AutoLoginMap.getInstance()
/* 443 */                   .getAutoLoginMap()
/* 444 */                   .remove(ikmac);
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */         catch (Exception localException)
/*     */         {
/*     */         }
/*     */       }
/*     */     }
/* 454 */     return null;
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     ikuaiAPI.RoamAutoAuth
 * JD-Core Version:    0.6.2
 */