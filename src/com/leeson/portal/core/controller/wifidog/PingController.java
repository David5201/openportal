/*     */ package com.leeson.portal.core.controller.wifidog;
/*     */ 
/*     */ import com.leeson.common.utils.ThreadLocalDateUtil;
/*     */ import com.leeson.common.utils.stringUtils;
/*     */ import com.leeson.core.bean.Portalbas;
/*     */ import com.leeson.portal.core.model.Config;
/*     */ import com.leeson.portal.core.model.WifiDogAPMap;
/*     */ import com.leeson.portal.core.utils.GetNgnixRemotIP;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.util.Date;
/*     */ import java.util.Map;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.ServletOutputStream;
/*     */ import javax.servlet.http.HttpServlet;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class PingController extends HttpServlet
/*     */ {
/*     */   private static final long serialVersionUID = -1966047929923869408L;
/*  31 */   private static Config config = Config.getInstance();
/*     */ 
/*  33 */   private static Logger logger = Logger.getLogger(PingController.class);
/*     */ 
/*     */   protected void service(HttpServletRequest request, HttpServletResponse response)
/*     */     throws ServletException, IOException
/*     */   {
/*  57 */     String now = ThreadLocalDateUtil.format(new Date());
/*     */ 
/*  59 */     String gw_id = request.getParameter("gw_id");
/*  60 */     String sys_uptime = request.getParameter("sys_uptime");
/*  61 */     String sys_memfree = request.getParameter("sys_memfree");
/*  62 */     String sys_load = request.getParameter("sys_load");
/*  63 */     String wifidog_uptime = request.getParameter("wifidog_uptime");
/*  64 */     String[] apInfo = new String[6];
/*  65 */     apInfo[0] = sys_uptime;
/*  66 */     apInfo[1] = sys_memfree;
/*  67 */     apInfo[2] = sys_load;
/*  68 */     apInfo[3] = wifidog_uptime;
/*     */ 
/*  81 */     apInfo[4] = GetNgnixRemotIP.getRemoteAddrIp(request);
/*  82 */     apInfo[5] = now;
/*     */ 
/*  84 */     String query = request.getQueryString();
/*     */ 
/*  90 */     if (((Portalbas)config.getConfigMap().get("")).getIsdebug().equals("1")) {
/*  91 */       logger.info("WifiDog Ping : " + request.getRequestURL().toString() + 
/*  92 */         "?" + query);
/*     */     }
/*  94 */     if (stringUtils.isBlank(gw_id)) {
/*  95 */       System.out.println("WifiDog Ping Error !");
/*  96 */       response.getOutputStream().write("{'error':'2'}".getBytes());
/*  97 */       return;
/*     */     }
/*  99 */     WifiDogAPMap.getInstance().getWifiDogAPMap().put(gw_id, apInfo);
/* 100 */     System.out.println("WifiDog Ping Success !");
/* 101 */     response.getOutputStream().write("Pong".getBytes());
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.controller.wifidog.PingController
 * JD-Core Version:    0.6.2
 */