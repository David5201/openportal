/*    */ package com.leeson.common.interceptor;
/*    */ 
/*    */ import com.leeson.common.utils.stringUtils;
/*    */ import com.leeson.core.bean.Portalbas;
/*    */ import com.leeson.core.bean.Portalurlparameter;
/*    */ import com.leeson.core.service.PortalurlparameterService;
/*    */ import com.leeson.portal.core.model.Config;
/*    */ import com.leeson.portal.core.model.ipMap;
/*    */ import com.leeson.portal.core.utils.GetNgnixRemotIP;
/*    */ import com.leeson.portal.core.utils.SpringContextHelper;
/*    */ import java.io.PrintWriter;
/*    */ import java.util.Map;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import javax.servlet.http.HttpSession;
/*    */ import net.sf.json.JSONObject;
/*    */ import org.apache.log4j.Logger;
/*    */ import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
/*    */ 
/*    */ public class CheckIpLockInterceptor extends HandlerInterceptorAdapter
/*    */ {
/* 23 */   private static Logger logger = Logger.getLogger(CheckIpLockInterceptor.class);
/* 24 */   private static Config config = Config.getInstance();
/* 25 */   private static ipMap ipMapContext = ipMap.getInstance();
/*    */ 
/* 27 */   PortalurlparameterService urlparametersService = (PortalurlparameterService)SpringContextHelper.getBean("portalurlparameterServiceImpl");
/*    */ 
/*    */   public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
/*    */     throws Exception
/*    */   {
/* 32 */     response.setContentType("text/html;charset=utf-8");
/* 33 */     Portalbas basConfig = (Portalbas)config.getConfigMap().get("");
/*    */ 
/* 35 */     HttpSession session = request.getSession();
/*    */ 
/* 37 */     String ip = request.getParameter("ip");
/* 38 */     String pip = request.getParameter(this.urlparametersService
/* 39 */       .getPortalurlparameterByKey(Long.valueOf(1L)).getUserip());
/* 40 */     String sip = (String)session.getAttribute("ip");
/*    */ 
/* 42 */     if (stringUtils.isBlank(ip)) {
/* 43 */       ip = pip;
/*    */     }
/* 45 */     if (stringUtils.isBlank(ip)) {
/* 46 */       ip = sip;
/*    */     }
/* 48 */     if (stringUtils.isBlank(ip)) {
/* 49 */       ip = GetNgnixRemotIP.getRemoteAddrIp(request);
/*    */     }
/* 51 */     if (!ipMapContext.getIpmap().containsKey(ip)) {
/* 52 */       ipMapContext.getIpmap().put(ip, Integer.valueOf(1));
/* 53 */       return true;
/*    */     }
/* 55 */     if (basConfig.getIsdebug().equals("1")) {
/* 56 */       logger.error(" IP: " + ip + " to Post is too fast !!!");
/*    */     }
/*    */ 
/* 61 */     String msgTemp = "你操作太快,请稍后再试！！";
/* 62 */     msgTemp = new String(msgTemp.getBytes("UTF-8"), "UTF-8");
/*    */ 
/* 64 */     JSONObject jo = new JSONObject();
/* 65 */     jo.put("ret", Integer.valueOf(1));
/* 66 */     jo.put("msg", msgTemp);
/* 67 */     String respMessage = jo.toString();
/* 68 */     PrintWriter out = response.getWriter();
/* 69 */     out.print(respMessage);
/* 70 */     out.close();
/* 71 */     return false;
/*    */   }
/*    */ 
/*    */   public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object o, Exception excptn)
/*    */     throws Exception
/*    */   {
/* 83 */     HttpSession session = request.getSession();
/* 84 */     String ip = request.getParameter("ip");
/* 85 */     String pip = request.getParameter(this.urlparametersService
/* 86 */       .getPortalurlparameterByKey(Long.valueOf(1L)).getUserip());
/* 87 */     String sip = (String)session.getAttribute("ip");
/*    */ 
/* 89 */     if (stringUtils.isBlank(ip)) {
/* 90 */       ip = pip;
/*    */     }
/* 92 */     if (stringUtils.isBlank(ip)) {
/* 93 */       ip = sip;
/*    */     }
/* 95 */     if (stringUtils.isBlank(ip)) {
/* 96 */       ip = GetNgnixRemotIP.getRemoteAddrIp(request);
/*    */     }
/* 98 */     ipMapContext.getIpmap().remove(ip);
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.common.interceptor.CheckIpLockInterceptor
 * JD-Core Version:    0.6.2
 */