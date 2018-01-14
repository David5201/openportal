/*     */ package api;
/*     */ 
/*     */ import com.leeson.common.utils.stringUtils;
/*     */ import com.leeson.core.bean.Portalaccount;
/*     */ import com.leeson.core.bean.Portalaccountgroup;
/*     */ import com.leeson.core.query.PortalaccountQuery;
/*     */ import com.leeson.core.service.ConfigService;
/*     */ import com.leeson.core.service.PortalaccountService;
/*     */ import com.leeson.core.service.PortalaccountgroupService;
/*     */ import com.leeson.core.service.PortalaccountmacsService;
/*     */ import com.leeson.core.service.PortalconfigService;
/*     */ import com.leeson.core.utils.Kick;
/*     */ import com.leeson.portal.core.model.OnlineMap;
/*     */ import com.leeson.portal.core.utils.SpringContextHelper;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.net.ServerSocket;
/*     */ import java.net.Socket;
/*     */ import java.text.DecimalFormat;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Properties;
/*     */ import java.util.Set;
/*     */ 
/*     */ public class ServiceSocket extends Thread
/*     */ {
/*  30 */   PortalaccountService portalaccountService = (PortalaccountService)SpringContextHelper.getBean("portalaccountServiceImpl");
/*     */ 
/*  32 */   PortalaccountmacsService macsService = (PortalaccountmacsService)SpringContextHelper.getBean("portalaccountmacsServiceImpl");
/*     */ 
/*  34 */   PortalconfigService portalconfigService = (PortalconfigService)SpringContextHelper.getBean("portalconfigServiceImpl");
/*     */ 
/*  36 */   ConfigService configService = (ConfigService)SpringContextHelper.getBean("configServiceImpl");
/*     */ 
/*  38 */   PortalaccountgroupService portalaccountgroupService = (PortalaccountgroupService)SpringContextHelper.getBean("portalaccountgroupServiceImpl");
/*     */ 
/*  41 */   private static OnlineMap onlineMap = OnlineMap.getInstance();
/*  42 */   private static DecimalFormat df = new DecimalFormat(".##");
/*  43 */   private static ServerSocket ss = null;
/*     */   public Socket sock;
/*     */ 
/*     */   private String checkIn(Portalaccount e, Integer macLimit, Integer count)
/*     */   {
/*     */     try
/*     */     {
/*  49 */       if ((stringUtils.isBlank(e.getLoginName())) || 
/*  50 */         (stringUtils.isBlank(e.getPassword()))) {
/*  51 */         return "error";
/*     */       }
/*     */ 
/*  55 */       int accountState = Integer.valueOf(e.getState()).intValue();
/*  56 */       if ((accountState < 0) || (accountState > 3)) {
/*  57 */         return "error";
/*     */       }
/*     */ 
/*  61 */       Long userTime = this.portalaccountgroupService.getPortalaccountgroupByKey(Long.valueOf(1L)).getTime();
/*  62 */       if ((accountState == 2) && 
/*  63 */         (e.getTime() == null)) {
/*  64 */         e.setTime(userTime);
/*     */       }
/*     */ 
/*  72 */       if ((accountState == 3) && 
/*  73 */         (e.getDate() == null)) {
/*  74 */         return "error";
/*     */       }
/*     */ 
/*  78 */       if (e.getDate() == null) {
/*  79 */         e.setDate(new Date());
/*     */       }
/*     */ 
/*  83 */       if (count == null) {
/*  84 */         count = Integer.valueOf(1);
/*     */       }
/*  86 */       if (macLimit == null) {
/*  87 */         macLimit = Integer.valueOf(0);
/*     */       }
/*  89 */       if (stringUtils.isBlank(e.getGender())) {
/*  90 */         e.setGender(null);
/*     */       }
/*     */ 
/*  93 */       PortalaccountQuery aq = new PortalaccountQuery();
/*  94 */       aq.setLoginName(e.getLoginName());
/*  95 */       aq.setLoginNameLike(false);
/*  96 */       List accountList = this.portalaccountService
/*  97 */         .getPortalaccountList(aq);
/*  98 */       if (accountList.size() > 0)
/*     */       {
/* 100 */         e.setId(((Portalaccount)accountList.get(0)).getId());
/* 101 */         e.setMaclimit(macLimit);
/* 102 */         e.setMaclimitcount(count);
/* 103 */         e.setSpeed(Long.valueOf(1L));
/* 104 */         e.setAutologin(Integer.valueOf(0));
/* 105 */         this.portalaccountService.updatePortalaccountByKey(e);
/* 106 */         return "ok";
/*     */       }
/*     */ 
/* 110 */       e.setMaclimit(macLimit);
/* 111 */       e.setMaclimitcount(count);
/* 112 */       e.setSpeed(Long.valueOf(1L));
/* 113 */       e.setAutologin(Integer.valueOf(0));
/* 114 */       this.portalaccountService.addPortalaccount(e);
/*     */ 
/* 116 */       return "ok";
/*     */     }
/*     */     catch (Exception ex)
/*     */     {
/* 121 */       System.err.println("CheckIN ERROR " + ex);
/*     */       try {
/* 123 */         return "error";
/*     */       }
/*     */       catch (Exception exc) {
/* 126 */         return "error";
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private String checkOut(String loginName)
/*     */   {
/*     */     try
/*     */     {
/* 135 */       if (stringUtils.isBlank(loginName)) {
/* 136 */         return "error";
/*     */       }
/*     */ 
/* 140 */       PortalaccountQuery aq = new PortalaccountQuery();
/* 141 */       aq.setLoginName(loginName);
/* 142 */       aq.setLoginNameLike(false);
/* 143 */       List accountList = this.portalaccountService
/* 144 */         .getPortalaccountList(aq);
/* 145 */       if (accountList.size() > 0) {
/* 146 */         Portalaccount acc = (Portalaccount)accountList.get(0);
/* 147 */         Long id = acc.getId();
/* 148 */         Set<Map.Entry<String, String[]>> entries = onlineMap.getOnlineUserMap().entrySet();
/* 150 */         for (Map.Entry entry : entries) {
/* 151 */           String[] info = (String[])entry.getValue();
/* 152 */           String ip = (String)entry.getKey();
/* 153 */           if ((stringUtils.isNotBlank(info[1])) && 
/* 154 */             (Long.valueOf(info[1]) == id)) {
/* 155 */             Kick.kickUserDeleteUser(ip);
/*     */           }
/*     */         }
/*     */ 
/* 159 */         int accountState = Integer.valueOf(acc.getState()).intValue();
/* 160 */         acc = this.portalaccountService.getPortalaccountByKey(id);
/* 161 */         acc.setState("0");
/* 162 */         this.portalaccountService.updatePortalaccountByKey(acc);
/*     */ 
/* 164 */         Long costTime = Long.valueOf(0L);
/* 165 */         if (accountState == 2) {
/* 166 */           Long userTime = this.portalaccountgroupService.getPortalaccountgroupByKey(Long.valueOf(1L)).getTime();
/* 167 */           costTime = Long.valueOf(userTime.longValue() - acc.getTime().longValue());
/*     */         }
/* 169 */         return String.valueOf(costTime);
/*     */       }
/*     */ 
/* 172 */       return "error";
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 176 */       System.err.println("CheckOUT ERROR " + e);
/*     */       try {
/* 178 */         return "error";
/*     */       }
/*     */       catch (Exception exc) {
/* 181 */         return "error";
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public String doMethod(String inS)
/*     */   {
/*     */     try {
/* 189 */       if (inS.startsWith("%ci")) {
/* 190 */         return CheckIN(inS);
/*     */       }
/* 192 */       if (inS.startsWith("%co"))
/* 193 */         return CheckOUT(inS);
/*     */     }
/*     */     catch (Exception e) {
/* 196 */       return "error";
/*     */     }
/* 198 */     return "error";
/*     */   }
/*     */ 
/*     */   private String CheckIN(String inS) {
/* 202 */     String result = "error";
/*     */     try
/*     */     {
/* 205 */       String[] ts = inS.split("-");
/* 206 */       String room_name = ts[1];
/* 207 */       String user_name = ts[2];
/*     */ 
/* 209 */       String loginName = room_name + user_name;
/* 210 */       String name = loginName;
/* 211 */       String password = ts[3];
/* 212 */       String description = ts[4] + " Check IN";
/* 213 */       String state = "2";
/*     */ 
/* 215 */       Portalaccount acc = new Portalaccount();
/* 216 */       acc.setLoginName(loginName);
/* 217 */       acc.setName(name);
/* 218 */       acc.setPassword(password);
/* 219 */       acc.setDescription(description);
/* 220 */       acc.setState(state);
/*     */ 
/* 222 */       if ("error".equals(checkIn(acc, null, null))) {
/* 223 */         return result;
/*     */       }
/* 225 */       result = "#is-" + room_name + "-" + loginName + "-$";
/*     */     } catch (Exception e) {
/* 227 */       System.err.println("CheckIN Error " + e);
/* 228 */       return result;
/*     */     }
/* 230 */     return result;
/*     */   }
/*     */ 
/*     */   private String CheckOUT(String inS) {
/* 234 */     String result = "error";
/*     */     try
/*     */     {
/* 237 */       String[] ts = inS.split("-");
/* 238 */       String room_name = ts[1];
/* 239 */       String loginName = ts[2];
/*     */ 
/* 241 */       String timeS = checkOut(loginName);
/* 242 */       if ("error".equals(timeS)) {
/* 243 */         return result;
/*     */       }
/* 245 */       Long timeM = Long.valueOf(Long.valueOf(timeS).longValue() / 60000L);
/* 246 */       double money = Double.valueOf((String)APIMap.getInstance().getApiMap().get("money")).doubleValue() / 60.0D;
/* 247 */       double costMoney = timeM.longValue() * money;
/* 248 */       String cost = df.format(costMoney);
/* 249 */       if (cost.startsWith(".")) {
/* 250 */         cost = "0" + cost;
/*     */       }
/* 252 */       result = "#cc-" + room_name + "-" + loginName + "-" + cost + "-$";
/*     */     } catch (Exception e) {
/* 254 */       System.err.println("CheckOUT Error " + e);
/* 255 */       return result;
/*     */     }
/* 257 */     return result;
/*     */   }
/*     */ 
/*     */   public ServiceSocket(Socket sock)
/*     */   {
/* 264 */     this.sock = sock;
/*     */   }
/*     */ 
/*     */   public void run() {
/*     */     try {
/* 269 */       String ip = this.sock.getRemoteSocketAddress().toString();
/*     */ 
/* 271 */       OutputStream os = this.sock.getOutputStream();
/* 272 */       InputStream is = this.sock.getInputStream();
/* 273 */       byte[] buf = new byte[1024];
/* 274 */       int len = is.read(buf);
/* 275 */       String inS = new String(buf, 0, len).trim();
/* 276 */       inS = inS.toLowerCase();
/* 277 */       System.out.println("API Service Receive From IP " + ip + " Data [" + inS + "]");
/* 278 */       String outS = doMethod(inS);
/*     */ 
/* 280 */       System.out.println("API Service Response To IP " + ip + " Data [" + outS + "]");
/* 281 */       os.write(outS.getBytes());
/* 282 */       os.close();
/* 283 */       is.close();
/* 284 */       this.sock.close();
/*     */     } catch (Exception e) {
/* 286 */       System.err.println("Do API Service Method Error: " + e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void openServer(String path)
/*     */   {
/* 292 */     int port = 6000;
/* 293 */     String money = "2.0";
/*     */     try {
/* 295 */       System.out.println("Read api.properties File ....");
/* 296 */       Properties props = new Properties();
/* 297 */       String propPath = path + "api.properties";
/*     */ 
/* 299 */       FileInputStream in = new FileInputStream(propPath);
/* 300 */       props.load(in);
/* 301 */       port = Integer.valueOf(props.getProperty("port")).intValue();
/* 302 */       money = props.getProperty("money");
/* 303 */       in.close();
/*     */     } catch (Exception e) {
/* 305 */       port = 6000;
/* 306 */       money = "2.0";
/*     */     }
/* 308 */     APIMap.getInstance().getApiMap().put("money", money);
/* 309 */     System.out.println("API ServerSocket TCP PORT : " + port);
/* 310 */     System.out.println("Every Hour : " + money);
/*     */     try {
/* 312 */       ss = new ServerSocket(port);
/*     */       while (true) {
/* 314 */         Socket s = ss.accept();
/* 315 */         new ServiceSocket(s).start();
/*     */       }
/*     */     } catch (Exception e) {
/* 318 */       System.err.println("Open API Service Error: " + e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void closeServer()
/*     */   {
/*     */     try
/*     */     {
/* 326 */       if (ss != null) {
/* 327 */         ss.close();
/* 328 */         ss = null;
/*     */       }
/*     */     }
/*     */     catch (Exception localException)
/*     */     {
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     api.ServiceSocket
 * JD-Core Version:    0.6.2
 */