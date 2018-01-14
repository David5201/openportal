package com.leeson.portal.core.controller;

import com.leeson.common.utils.ThreadLocalDateUtil;
import com.leeson.common.utils.stringUtils;
import com.leeson.core.api.AccountAPIRequest;
import com.leeson.core.bean.Advadv;
import com.leeson.core.bean.Portalaccount;
import com.leeson.core.bean.Portalaccountmacs;
import com.leeson.core.bean.Portalap;
import com.leeson.core.bean.Portalautologin;
import com.leeson.core.bean.Portalbas;
import com.leeson.core.bean.Portalbasauth;
import com.leeson.core.bean.Portalip;
import com.leeson.core.bean.Portallinkrecord;
import com.leeson.core.bean.Portallogrecord;
import com.leeson.core.bean.Portalonlinelimit;
import com.leeson.core.bean.Portalssid;
import com.leeson.core.bean.Portaltimeweb;
import com.leeson.core.bean.Portalurlparameter;
import com.leeson.core.bean.Portalweb;
import com.leeson.core.controller.AjaxInterFaceController;
import com.leeson.core.query.PortalaccountQuery;
import com.leeson.core.query.PortalaccountmacsQuery;
import com.leeson.core.query.PortalapQuery;
import com.leeson.core.query.PortalbasQuery;
import com.leeson.core.query.PortalbasauthQuery;
import com.leeson.core.query.PortalipQuery;
import com.leeson.core.query.PortalssidQuery;
import com.leeson.core.query.PortaltimewebQuery;
import com.leeson.core.service.AdvadvService;
import com.leeson.core.service.ConfigService;
import com.leeson.core.service.PortalaccountService;
import com.leeson.core.service.PortalaccountmacsService;
import com.leeson.core.service.PortalapService;
import com.leeson.core.service.PortalautologinService;
import com.leeson.core.service.PortalbasService;
import com.leeson.core.service.PortalbasauthService;
import com.leeson.core.service.PortalipService;
import com.leeson.core.service.PortallinkrecordService;
import com.leeson.core.service.PortallogrecordService;
import com.leeson.core.service.PortalonlinelimitService;
import com.leeson.core.service.PortalssidService;
import com.leeson.core.service.PortaltimewebService;
import com.leeson.core.service.PortalurlparameterService;
import com.leeson.core.service.PortalwebService;
import com.leeson.core.utils.IPv4Util;
import com.leeson.core.utils.Kick;
import com.leeson.portal.core.controller.utils.BASE64;
import com.leeson.portal.core.model.AccountAPIMap;
import com.leeson.portal.core.model.AutoLoginMacMap;
import com.leeson.portal.core.model.AutoLoginMap;
import com.leeson.portal.core.model.LateAuthMap;
import com.leeson.portal.core.model.MacLimitMap;
import com.leeson.portal.core.model.OnlineMap;
import com.leeson.portal.core.model.UserLimitMap;
import com.leeson.portal.core.model.iKuaiMacIpMap;
import com.leeson.portal.core.model.ipMacMap;
import com.leeson.portal.core.model.ipMap;
import com.leeson.portal.core.model.isDo;
import com.leeson.portal.core.service.InterfaceControl;
import com.leeson.portal.core.service.action.unifi.UniFiMethod;
import com.leeson.portal.core.service.utils.PortalUtil;
import com.leeson.portal.core.utils.GetNgnixRemotIP;
import com.leeson.portal.core.utils.SpringContextHelper;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

public class IndexAction extends HttpServlet {
	private static final long serialVersionUID = -1966047929923869408L;
	private static OnlineMap onlineMap = OnlineMap.getInstance();

	private static com.leeson.portal.core.model.Config config = com.leeson.portal.core.model.Config.getInstance();

	private static Logger logger = Logger.getLogger(IndexAction.class);

	private static PortalaccountService accountService = (PortalaccountService) SpringContextHelper
			.getBean("portalaccountServiceImpl");

	private static PortalaccountmacsService macsService = (PortalaccountmacsService) SpringContextHelper
			.getBean("portalaccountmacsServiceImpl");

	private static PortallinkrecordService linkRecordService = (PortallinkrecordService) SpringContextHelper
			.getBean("portallinkrecordServiceImpl");

	private static PortallogrecordService logrecordService = (PortallogrecordService) SpringContextHelper
			.getBean("portallogrecordServiceImpl");

	private static PortalbasService basService = (PortalbasService) SpringContextHelper.getBean("portalbasServiceImpl");

	private static PortalurlparameterService urlparametersService = (PortalurlparameterService) SpringContextHelper
			.getBean("portalurlparameterServiceImpl");

	private static PortalbasauthService basauthService = (PortalbasauthService) SpringContextHelper
			.getBean("portalbasauthServiceImpl");

	private static PortalonlinelimitService onlinelimitService = (PortalonlinelimitService) SpringContextHelper
			.getBean("portalonlinelimitServiceImpl");

	private static PortalwebService webService = (PortalwebService) SpringContextHelper.getBean("portalwebServiceImpl");

	private static PortalautologinService autologinService = (PortalautologinService) SpringContextHelper
			.getBean("portalautologinServiceImpl");

	private static PortalapService apService = (PortalapService) SpringContextHelper.getBean("portalapServiceImpl");

	private static PortalssidService ssidService = (PortalssidService) SpringContextHelper
			.getBean("portalssidServiceImpl");

	private static PortalipService ipService = (PortalipService) SpringContextHelper.getBean("portalipServiceImpl");

	private static ConfigService configService = (ConfigService) SpringContextHelper.getBean("configServiceImpl");

	private static PortaltimewebService portaltimewebService = (PortaltimewebService) SpringContextHelper
			.getBean("portaltimewebServiceImpl");

	private static AdvadvService advadvService = (AdvadvService) SpringContextHelper.getBean("advadvServiceImpl");

	@SuppressWarnings("unused")
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
response.setContentType("text/html;charset=utf-8");
request.setCharacterEncoding("utf-8");

HttpSession session = request.getSession();

String ikenc = request.getParameter("ikenc");
String refer = request.getParameter("refer");

String ikcs = "";
String ikweb = (String) session.getAttribute("ikweb");

if ((stringUtils.isNotBlank(ikenc)) && (stringUtils.isNotBlank(refer)))
	try {
		ikcs = BASE64.decryptBASE64(ikenc);
		ikweb = BASE64.decryptBASE64(refer);
	} catch (Exception localException1) {
	}
String ikbasip = "";
String ikip = "";
String ssid = "";
String nasname = "";
String apmac = "";
String ikmac = (String) session.getAttribute("ikmac");

if (stringUtils.isNotBlank(ikcs)) {
	String[] cs = ikcs.split("&");
	for (String c : cs) {
		if (c.startsWith("nasname=")) {
			nasname = c.replace("nasname=", "");
		}
		if (c.startsWith("basip=")) {
			ikbasip = c.replace("basip=", "").trim();
		}
		if (c.startsWith("user_ip=")) {
			ikip = c.replace("user_ip=", "");
		}
		if (c.startsWith("mac=")) {
			ikmac = c.replace("mac=", "");
		}
		if (c.startsWith("apmac=")) {
			apmac = c.replace("apmac=", "");
		}
		if (c.startsWith("ssid=")) {
			ssid = c.replace("ssid=", "");
			ssid = URLDecoder.decode(ssid, "utf-8");
		}

	}

}

Cookie[] cookies = request.getCookies();
String cip = "";
String cbasip = "";
String cpassword = "";
String cmac = "";
String cSite = "";
if (cookies != null) {
	for (int i = 0; i < cookies.length; i++) {
		if (cookies[i].getName().equals("ip"))
			cip = cookies[i].getValue();
		if (cookies[i].getName().equals("basip"))
			cbasip = cookies[i].getValue();
		if (cookies[i].getName().equals("password"))
			cpassword = cookies[i].getValue();
		if (cookies[i].getName().equals("mac"))
			cmac = cookies[i].getValue();
		if (cookies[i].getName().equals("site")) {
			cSite = cookies[i].getValue();
		}
	}
}

String ip = "";
String basip = "";

String pip = request
		.getParameter(urlparametersService.getPortalurlparameterByKey(Long.valueOf(1L)).getUserip());
String pmac = request
		.getParameter(urlparametersService.getPortalurlparameterByKey(Long.valueOf(1L)).getUsermac());
String pnasname = request
		.getParameter(urlparametersService.getPortalurlparameterByKey(Long.valueOf(1L)).getBasname());
String purl = request.getParameter(urlparametersService.getPortalurlparameterByKey(Long.valueOf(1L)).getUrl());
String pbasip = request
		.getParameter(urlparametersService.getPortalurlparameterByKey(Long.valueOf(1L)).getBasip());
String pssid = request
		.getParameter(urlparametersService.getPortalurlparameterByKey(Long.valueOf(1L)).getSsid());
String papmac = request
		.getParameter(urlparametersService.getPortalurlparameterByKey(Long.valueOf(1L)).getApmac());

Portalbas basConfig = (Portalbas) config.getConfigMap().get("");
if (stringUtils.isNotBlank(pnasname)) {
	PortalbasQuery bq = new PortalbasQuery();
	bq.setBasname(pnasname);
	bq.setBasnameLike(false);
	List bs = basService.getPortalbasList(bq);
	if (bs.size() > 0) {
		basConfig = (Portalbas) bs.get(0);
	}
}
if (stringUtils.isNotBlank(nasname)) {
	PortalbasQuery bq = new PortalbasQuery();
	bq.setBasname(nasname);
	bq.setBasnameLike(false);
	List bs = basService.getPortalbasList(bq);
	if (bs.size() > 0) {
		basConfig = (Portalbas) bs.get(0);
	}
}

basip = ikbasip;
if (stringUtils.isBlank(basip)) {
	basip = pbasip;
}
if ((stringUtils.isBlank(basip)) && (stringUtils.isNotBlank(pnasname))) {
	PortalbasQuery bq = new PortalbasQuery();
	bq.setBasname(pnasname);
	bq.setBasnameLike(false);
	List bs = basService.getPortalbasList(bq);
	if (bs.size() > 0) {
		basip = ((Portalbas) bs.get(0)).getBasIp();
	}
}

if ((stringUtils.isBlank(basip)) && (stringUtils.isNotBlank(nasname))) {
	PortalbasQuery bq = new PortalbasQuery();
	bq.setBasname(nasname);
	bq.setBasnameLike(false);
	List bs = basService.getPortalbasList(bq);
	if (bs.size() > 0) {
		basip = ((Portalbas) bs.get(0)).getBasIp();
	}
}

if (stringUtils.isBlank(basip)) {
	basip = (String) session.getAttribute("basip");
}
if (stringUtils.isBlank(basip)) {
	basip = cbasip;
}
if (stringUtils.isBlank(basip)) {
	if ("0".equals(basConfig.getIsOut())) {
		basip = basConfig.getBasIp();
	} else {
		basip = GetNgnixRemotIP.getRemoteAddrIp(request);
	}
}

Portalbas basConfigFind = (Portalbas) config.getConfigMap().get(basip);

if (basConfigFind != null) {
	if (stringUtils.isNotBlank(basConfigFind.getBasIp()))
		basConfig = basConfigFind;
} else {
	basip = basConfig.getBasIp();
}

if ((basConfig.getBas().equals("1")) || (basConfig.getBas().equals("0"))) {
	ip = pip;
	if (stringUtils.isBlank(ip)) {
		ip = (String) session.getAttribute("ip");
	}
	if (stringUtils.isBlank(ip)) {
		ip = cip;
	}
	if (stringUtils.isBlank(ip)) {
		if ("0".equals(basConfig.getIsOut())) {
			ip = GetNgnixRemotIP.getRemoteAddrIp(request);
		} else
			ip = "0.0.0.0";

	}

	if (stringUtils.isNotBlank(pmac)) {
		ikmac = pmac;
	}
	if (stringUtils.isBlank(ikmac)) {
		ikmac = cmac;
	}
	if (stringUtils.isNotBlank(purl)) {
		purl = new String(purl.getBytes("iso-8859-1"), "utf-8");
		ikweb = purl;
	}

	if (stringUtils.isNotBlank(pssid)) {
		pssid = new String(pssid.getBytes("iso-8859-1"), "utf-8");
		ssid = pssid;
	}
	if (stringUtils.isBlank(ssid)) {
		ssid = (String) session.getAttribute("ssid");
	}
	if (stringUtils.isBlank(ssid)) {
		ssid = pnasname;
	}

	if (stringUtils.isNotBlank(papmac)) {
		apmac = papmac;
	}

}

if ((basConfig.getBas().equals("2")) || (basConfig.getBas().equals("4"))) {
	basip = ikbasip;
	if ((stringUtils.isBlank(basip)) && (stringUtils.isNotBlank(nasname))) {
		PortalbasQuery bq = new PortalbasQuery();
		bq.setBasname(nasname);
		bq.setBasnameLike(false);
		List bs = basService.getPortalbasList(bq);
		if (bs.size() > 0) {
			basip = ((Portalbas) bs.get(0)).getBasIp();
		}
	}

	if (stringUtils.isBlank(basip)) {
		basip = (String) session.getAttribute("basip");
	}
	if (stringUtils.isBlank(basip)) {
		basip = cbasip;
	}
	if (stringUtils.isBlank(basip)) {
		if ("0".equals(basConfig.getIsOut())) {
			basip = basConfig.getBasIp();
		} else {
			basip = GetNgnixRemotIP.getRemoteAddrIp(request);
		}
	}

	if (stringUtils.isBlank(ikmac)) {
		ikmac = cmac;
	}
	ikmac = PortalUtil.MacFormat(ikmac);

	ip = ikip;
	if (stringUtils.isBlank(ip)) {
		ip = (String) iKuaiMacIpMap.getInstance().getMacIpMap().get(ikmac);
	}
	if (stringUtils.isBlank(ip)) {
		if ("0".equals(basConfig.getIsOut())) {
			ip = GetNgnixRemotIP.getRemoteAddrIp(request);
		} else
			ip = "0.0.0.0";

	}

	if (stringUtils.isBlank(ssid)) {
		ssid = (String) session.getAttribute("ssid");
	}
	if (stringUtils.isBlank(ssid)) {
		ssid = nasname;
	}

	if (stringUtils.isNotBlank(ikmac)) {
		ikmac = PortalUtil.MacFormat(ikmac);

		iKuaiMacIpMap.getInstance().getMacIpMap().put(ikmac, ip);
		Cookie cookieMac = new Cookie("mac", ikmac);
		cookieMac.setMaxAge(86400);
		response.addCookie(cookieMac);
		session.setAttribute("ikmac", ikmac);
	}

}

if (basConfig.getBas().equals("3")) {
	basip = basConfig.getBasIp();

	if (stringUtils.isBlank(ikmac)) {
		ikmac = cmac;
	}

	ip = GetNgnixRemotIP.getRemoteAddrIp(request);
	ip = ikmac;

	if (stringUtils.isBlank(ssid)) {
		ssid = (String) session.getAttribute("ssid");
	}

	String site = (String) session.getAttribute("site");
	if (stringUtils.isBlank(site)) {
		site = cSite;
	}
	if (stringUtils.isBlank(site)) {
		site = "default";
	}

}

basConfigFind = (Portalbas) config.getConfigMap().get(basip);
if (basConfigFind != null) {
	if (stringUtils.isNotBlank(basConfigFind.getBasIp()))
		basConfig = basConfigFind;
} else {
	basip = basConfig.getBasIp();
}

if (stringUtils.isBlank(ssid)) {
	ssid = (String) session.getAttribute("ssid");
}
if (stringUtils.isBlank(ssid)) {
	ssid = nasname;
}
if (stringUtils.isNotBlank(ikmac)) {
	ikmac = PortalUtil.MacFormat(ikmac);

	Cookie cookieMac = new Cookie("mac", ikmac);
	cookieMac.setMaxAge(86400);
	response.addCookie(cookieMac);
	session.setAttribute("ikmac", ikmac);
}

if (stringUtils.isBlank(apmac)) {
	apmac = (String) session.getAttribute("apmac");
}
if (stringUtils.isNotBlank(apmac)) {
	session.setAttribute("apmac", apmac);
}

if (stringUtils.isNotBlank(ssid)) {
	ssid = URLDecoder.decode(ssid);
	ssid = StringEscapeUtils.unescapeHtml(ssid);
	session.setAttribute("ssid", ssid);
}
if (stringUtils.isNotBlank(ikweb)) {
	ikweb = URLDecoder.decode(ikweb);
	ikweb = StringEscapeUtils.unescapeHtml(ikweb);
	if (!ikweb.startsWith("http")) {
		ikweb = "http://" + ikweb;
	}
}

String webMod = getWebMod(ssid, apmac, ip, basConfig.getWeb());
session.setAttribute("web", webMod);

String userAgent = request.getHeader("user-agent");
boolean isComputer = false;
String agent = "";
if (stringUtils.isNotBlank(userAgent)) {
	if (userAgent.contains("Windows")) {
		isComputer = true;
		agent = "Windows";
	} else if (userAgent.contains("Macintosh")) {
		isComputer = true;
		agent = "MacOS";
	} else if (userAgent.contains("Linux")) {
		isComputer = false;
		agent = "Android";
	} else if (userAgent.contains("Android")) {
		isComputer = false;
		agent = "Android";
	} else if (userAgent.contains("iPhone")) {
		isComputer = false;
		agent = "IOS";
	} else if (userAgent.contains("iPad")) {
		isComputer = false;
		agent = "IOS";
	} else if (userAgent.contains("iPod")) {
		isComputer = false;
		agent = "IOS";
	}
}

session.setAttribute("agent", agent);
if (!"1".equals(basConfig.getIsComputer())) {
	if (isComputer) {
		session.setAttribute("web", "");
		request.setAttribute("msg", "当前系统设置不允许电脑认证！！");
		request.getRequestDispatcher("L.jsp").forward(request, response);
		return;
	}
}

boolean isLogin = onlineMap.getOnlineUserMap().containsKey(ip + ":" + basip);
if (isLogin) {
	if (basConfig.getIsNtf().intValue() == 1) {
		String[] loginInfo = (String[]) onlineMap.getOnlineUserMap().get(ip + ":" + basip);
		GetMacTimeLimitMethod(loginInfo, ikmac, session);
		String username = loginInfo[0];
		session.setAttribute("username", username);
		session.setAttribute("password", cpassword);
		session.setAttribute("ip", ip);
		session.setAttribute("basip", basip);
		session.setAttribute("ssid", ssid);

		session.setAttribute("ikweb", ikweb);

		if (stringUtils.isNotBlank(ikweb))
			request.getRequestDispatcher("/" + webMod + "ok.jsp?l=" + ikweb).forward(request, response);
		else {
			request.getRequestDispatcher("/" + webMod + "ok.jsp").forward(request, response);
		}
		return;
	}
	Kick.kickUserSyn(ip + ":" + basip);
}

if (onlineMap.getOnlineUserMap().size() >= Integer.valueOf(
		((String[]) com.leeson.portal.core.model.CoreConfigMap.getInstance().getCoreConfigMap().get("core"))[1])
		.intValue()) {
	session.setAttribute("ip", ip);
	session.setAttribute("basip", basip);
	session.setAttribute("ssid", ssid);
	request.setAttribute("msg", "已超过允许最大用户数限制！！");
	request.getRequestDispatcher("/" + webMod + "OL.jsp").forward(request, response);
	return;
}

if ((Do()) && (!ipMap.getInstance().getIpmap().containsKey(ip))) {
	ipMap.getInstance().getIpmap().put(ip, Integer.valueOf(1));

	if (stringUtils.isNotBlank(ikmac)) {
		Integer lateAuthState = basConfig.getLateAuth();
		Long lateAuthTime = basConfig.getLateAuthTime();
		if ((lateAuthState != null) && (lateAuthTime != null) && (1 == lateAuthState.intValue())
				&& (0L < lateAuthTime.longValue())) {
			if (!LateAuthMap.getInstance().getLateAuthMap().containsKey(ikmac)) {
				String authUser = basConfig.getBasUser();
				String authPwd = basConfig.getBasPwd();
				String authUrl = ikweb;
				if (stringUtils.isBlank(authUrl)) {
					authUrl = "/" + webMod + "ok.jsp";
				}

				boolean info = false;
				if ((basConfig.getBas().equals("1")) || (basConfig.getBas().equals("0"))) {
					info = InterfaceControl.Method("PORTAL_LOGIN", authUser, authPwd, ip, basip, ikmac)
							.booleanValue();
				}

				if ((basConfig.getBas().equals("2")) || (basConfig.getBas().equals("4"))) {
					info = InterfaceControl.Method("PORTAL_LOGIN", authUser, authPwd, ip, basip, ikmac)
							.booleanValue();
				}

				if (basConfig.getBas().equals("3")) {
					String site = (String) session.getAttribute("site");
					PortalaccountQuery aq = new PortalaccountQuery();
					aq.setLoginName(authUser);
					aq.setLoginNameLike(false);
					List accs = accountService.getPortalaccountList(aq);
					int accTime = 1440;
					if (accs.size() == 1) {
						long accTimeLong = ((Portalaccount) accs.get(0)).getTime().longValue() / 60000L;
						if (accTimeLong > 0L) {
							accTime = (int) accTimeLong;
						}
					}
					info = UniFiMethod.sendAuthorization(ikmac, accTime, site, basip);
				}
				if (info) {
					session.setAttribute("username", "延迟认证");
					session.setAttribute("ip", ip);
					session.setAttribute("basip", basip);

					Cookie cookieIp = new Cookie("ip", ip);
					cookieIp.setMaxAge(86400);
					response.addCookie(cookieIp);

					Cookie cookieBasIp = new Cookie("basip", basip);
					cookieBasIp.setMaxAge(86400);
					response.addCookie(cookieBasIp);

					String[] loginInfo = new String[16];
					loginInfo[0] = "延迟认证";
					Date now = new Date();
					loginInfo[3] = ThreadLocalDateUtil.format(now);
					loginInfo[4] = ikmac;
					loginInfo[6] = "8";
					loginInfo[7] = "0";
					loginInfo[8] = "0";

					loginInfo[9] = ip;
					loginInfo[10] = basip;
					loginInfo[11] = ((Portalbas) config.getConfigMap().get(basip)).getBasname();
					loginInfo[12] = ssid;
					loginInfo[13] = apmac;
					loginInfo[14] = "no";
					loginInfo[15] = agent;

					onlineMap.getOnlineUserMap().put(ip + ":" + basip, loginInfo);

					Portallogrecord logRecord = new Portallogrecord();
					logRecord.setInfo("IP: " + ip + ":" + basip + " mac: " + ikmac + " 延迟认证登录成功!");
					logRecord.setRecDate(now);
					logrecordService.addPortallogrecord(logRecord);
					if (stringUtils.isNotBlank(ssid)) {
						try {
							PortalssidQuery apq = new PortalssidQuery();
							apq.setSsid(ssid);
							apq.setSsidLike(false);
							List aps = ssidService.getPortalssidList(apq);
							if ((aps != null) && (aps.size() > 0)) {
								Portalssid ap = (Portalssid) aps.get(0);
								ap.setBasip(basip);
								long count = ap.getCount().longValue() + 1L;
								ap.setCount(Long.valueOf(count));
								ssidService.updatePortalssidByKey(ap);
							} else {
								Portalssid ap = new Portalssid();
								ap.setSsid(ssid);
								ap.setBasip(basip);
								ap.setCount(Long.valueOf(1L));
								ssidService.addPortalssid(ap);
							}
						} catch (Exception e) {
							logger.error("==============ERROR Start=============");
							logger.error(e);
							logger.error("ERROR INFO ", e);
							logger.error("==============ERROR End=============");
						}
					}
					if (stringUtils.isNotBlank(apmac)) {
						try {
							PortalapQuery apq = new PortalapQuery();
							apq.setMac(apmac);
							apq.setMacLike(false);
							List aps = apService.getPortalapList(apq);
							if ((aps != null) && (aps.size() > 0)) {
								Portalap ap = (Portalap) aps.get(0);
								ap.setBasip(basip);
								long count = ap.getCount().longValue() + 1L;
								ap.setCount(Long.valueOf(count));
								apService.updatePortalapByKey(ap);
							} else {
								Portalap ap = new Portalap();
								ap.setMac(apmac);
								ap.setBasip(basip);
								ap.setCount(Long.valueOf(1L));
								apService.addPortalap(ap);
							}
						} catch (Exception e) {
							logger.error("==============ERROR Start=============");
							logger.error(e);
							logger.error("ERROR INFO ", e);
							logger.error("==============ERROR End=============");
						}
					}

					LateAuthMap.getInstance().getLateAuthMap().put(ikmac, new Date());

					AjaxInterFaceController.SangforLogin(ip, "延迟认证", ikmac, apmac, basip);

					ipMap.getInstance().getIpmap().remove(ip);

					ikweb = ((String[]) com.leeson.portal.core.model.CoreConfigMap.getInstance()
							.getCoreConfigMap().get("core"))[0];

					if (stringUtils.isNotBlank(ikweb)) {
						response.sendRedirect(ikweb);
						return;
					}
					response.sendRedirect(authUrl);
					return;
				}
			}
		}
	}
	String authPwd;
	String password = "";
	if (stringUtils.isNotBlank(ikmac)) {
		String[] macTimeInfo = (String[]) AutoLoginMacMap.getInstance().getAutoLoginMacMap().get(ikmac);
		String[] userinfo = (String[]) AutoLoginMap.getInstance().getAutoLoginMap().get(ikmac);
		if ((macTimeInfo != null) && (userinfo != null)) {
			try {
				Long id = Long.valueOf(macTimeInfo[2]);
				if (1 != CheckMacTimeLimitMethod(ikmac, id)) {
					String authUser = userinfo[0];
					authPwd = userinfo[1];
					String username = userinfo[2];
					System.out.println(authUser + " " + authPwd + " " + username);
					Portalautologin autologin = autologinService.getPortalautologinByKey(id);
					if ((autologin != null) && (autologin.getState().intValue() == 1)) {
						Long timepermit = autologin.getTime();
						String loginTimeString = macTimeInfo[0];
						Long oldcostTime = Long.valueOf(macTimeInfo[1]);
						Long costTime = oldcostTime;

						if (stringUtils.isNotBlank(loginTimeString)) {
							Date loginTime = ThreadLocalDateUtil.parse(loginTimeString);
							String nowString = ThreadLocalDateUtil.format(new Date());
							Date nowTime = ThreadLocalDateUtil.parse(nowString);
							costTime = Long
									.valueOf(nowTime.getTime() - loginTime.getTime() + oldcostTime.longValue());
						}
						if ((costTime.longValue() < timepermit.longValue()) || (timepermit.longValue() <= 0L)) {
							Long userId = Long.valueOf(0L);
							String userState = null;
							password = authPwd;
							boolean CheckAccount = false;
							Object o;
							if (3L == id.longValue()) {
								PortalaccountQuery accq = new PortalaccountQuery();
								accq.setLoginNameLike(false);
								accq.setLoginName(username);

								if (!"1".equals(basConfig.getIsPortalCheck())) {
									accq.setPasswordLike(false);
									accq.setPassword(password);
								}
								List accs = accountService.getPortalaccountList(accq);
								if ((accs != null) && (accs.size() == 1)) {
									Portalaccount acc = (Portalaccount) accs.get(0);
									if (acc != null) {
										userId = acc.getId();
										Date userDate = acc.getDate();
										Long userTime = acc.getTime();
										Long octets = acc.getOctets();
										if (octets == null) {
											octets = Long.valueOf(0L);
										}
										userState = acc.getState();
										password = acc.getPassword();

										if (checkTimeOut(userState, userId, userDate, userTime, octets)) {
											CheckAccount = true;
										}

										if (!"1".equals(basConfig.getIsPortalCheck())) {
											if (!password.equals(authPwd)) {
												CheckAccount = false;
											}

										}

										if (CheckAccount) {
											if ("1".equals(basConfig.getIsPortalCheck())) {
												Integer limitCount = acc.getMaclimitcount();
												if ((limitCount != null) && (limitCount.intValue() > 0)) {
													int count = 0;
													Iterator iterator = OnlineMap.getInstance()
															.getOnlineUserMap().keySet().iterator();

													while (iterator.hasNext()) {
														o = iterator.next();
														String t = o.toString();
														String[] loginInfo = (String[]) OnlineMap.getInstance()
																.getOnlineUserMap().get(t);
														String haveUsername = loginInfo[0];

														if (username.equals(haveUsername)) {
															count++;
														}
													}
													if (count >= limitCount.intValue()) {
														CheckAccount = false;
													}
												}
											}
										}

									}

								}

							} else if (4L == id.longValue()) {
								String accountAPI_Url = (String) AccountAPIMap.getInstance().getAccountAPIMap()
										.get("autourl");
								String accountAPI_State = (String) AccountAPIMap.getInstance()
										.getAccountAPIMap().get("autostate");

								if (stringUtils.isNotBlank(accountAPI_Url)) {
									if ((stringUtils.isNotBlank(accountAPI_State))
											&& ("1".equals(accountAPI_State))) {
										boolean apiResult = AccountAPIRequest.send(accountAPI_Url, authUser,
												authPwd, ip, ikmac, agent);
										if (apiResult) {
											CheckAccount = true;
											//break label4786;
											//break;
										}
										CheckAccount = false;

										//break label4786;
										//break;
									}
								}
								CheckAccount = true;
							} else {
								CheckAccount = true;
							}

							//label4786: if (CheckAccount) {
							if (CheckAccount) {
								Boolean info = Boolean.valueOf(false);

								if (((Portalbas) config.getConfigMap().get(basip)).getBas().equals("3")) {
									String site = (String) session.getAttribute("site");
									PortalaccountQuery aq = new PortalaccountQuery();
									aq.setLoginName(authUser);
									aq.setLoginNameLike(false);
									List accs = accountService.getPortalaccountList(aq);
									int accTime = 1440;
									if (accs.size() == 1) {
										long accTimeLong = ((Portalaccount) accs.get(0)).getTime().longValue()
												/ 60000L;
										if (accTimeLong > 0L) {
											accTime = (int) accTimeLong;
										}
									}
									info = Boolean.valueOf(
											UniFiMethod.sendAuthorization(ikmac, accTime, site, basip));
								} else {
									info = InterfaceControl.Method("PORTAL_LOGIN", authUser, authPwd, ip, basip,
											ikmac);
								}

								if (info.booleanValue()) {
									if (stringUtils.isNotBlank(ssid)) {
										try {
											PortalssidQuery apq = new PortalssidQuery();
											apq.setSsid(ssid);
											apq.setSsidLike(false);
											List aps = ssidService.getPortalssidList(apq);
											if ((aps != null) && (aps.size() > 0)) {
												Portalssid ap = (Portalssid) aps.get(0);
												ap.setBasip(basip);
												long count = ap.getCount().longValue() + 1L;
												ap.setCount(Long.valueOf(count));
												ssidService.updatePortalssidByKey(ap);
											} else {
												Portalssid ap = new Portalssid();
												ap.setSsid(ssid);
												ap.setBasip(basip);
												ap.setCount(Long.valueOf(1L));
												ssidService.addPortalssid(ap);
											}
										} catch (Exception e) {
											logger.error("==============ERROR Start=============");
											logger.error(e);
											logger.error("ERROR INFO ", e);
											logger.error("==============ERROR End=============");
										}

									}

									if (stringUtils.isNotBlank(apmac)) {
										try {
											PortalapQuery apq = new PortalapQuery();
											apq.setMac(apmac);
											apq.setMacLike(false);
											List aps = apService.getPortalapList(apq);
											if ((aps != null) && (aps.size() > 0)) {
												Portalap ap = (Portalap) aps.get(0);
												ap.setBasip(basip);
												long count = ap.getCount().longValue() + 1L;
												ap.setCount(Long.valueOf(count));
												apService.updatePortalapByKey(ap);
											} else {
												Portalap ap = new Portalap();
												ap.setMac(apmac);
												ap.setBasip(basip);
												ap.setCount(Long.valueOf(1L));
												apService.addPortalap(ap);
											}
										} catch (Exception e) {
											logger.error("==============ERROR Start=============");
											logger.error(e);
											logger.error("ERROR INFO ", e);
											logger.error("==============ERROR End=============");
										}

									}

									if ((stringUtils.isBlank(ikmac)) || ("error".equals(ikmac))) {
										ikmac = (String) ipMacMap.getInstance().getIpMacMap()
												.get(ip + ":" + basip);
									}

									UpdateMacTimeLimitMethod(ikmac, id, session, username);
									AutoLoginPutMethod(ikmac, id);

									session.setAttribute("username", username);
									session.setAttribute("password", password);
									session.setAttribute("ip", ip);
									session.setAttribute("basip", basip);

									String[] loginInfo = new String[16];
									loginInfo[0] = username;
									Date now = new Date();
									loginInfo[3] = ThreadLocalDateUtil.format(now);
									loginInfo[4] = ikmac;
									if ((3L == id.longValue()) || (4L == id.longValue())) {
										loginInfo[5] = "ok";
									}
									if (3L == id.longValue()) {
										if ((stringUtils.isNotBlank(userState)) && (userId != null)) {
											Long recordId = doLinkRecord(username, ip, basip, userState, userId,
													ikmac);
											loginInfo[1] = String.valueOf(userId);
											loginInfo[2] = String.valueOf(recordId);
											session.setAttribute("password", password);
										}
									}
									String tid = "0";
									if (1L == id.longValue())
										tid = "4";
									else if (2L == id.longValue())
										tid = "0";
									else if (3L == id.longValue())
										tid = "1";
									else if (4L == id.longValue())
										tid = "2";
									else if (5L == id.longValue())
										tid = "3";
									else if (6L == id.longValue())
										tid = "5";
									else if (7L == id.longValue())
										tid = "6";
									else if (8L == id.longValue()) {
										tid = "7";
									}
									loginInfo[6] = tid;
									loginInfo[7] = "0";
									loginInfo[8] = "0";

									loginInfo[9] = ip;
									loginInfo[10] = basip;
									loginInfo[11] = ((Portalbas) config.getConfigMap().get(basip)).getBasname();
									loginInfo[12] = ssid;
									loginInfo[13] = apmac;
									loginInfo[14] = "yes";
									loginInfo[15] = agent;

									onlineMap.getOnlineUserMap().put(ip + ":" + basip, loginInfo);
									Portallogrecord logRecord = new Portallogrecord();

									logRecord.setInfo("IP: " + ip + ":" + basip + " mac: " + ikmac + " 用户: "
											+ username + " ,无感知登录成功!");
									logRecord.setRecDate(now);
									logrecordService.addPortallogrecord(logRecord);

									String authUrl = ikweb;
									PortalbasauthQuery bsq = new PortalbasauthQuery();
									bsq.setBasip(basip);
									List<Portalbasauth> basauths = basauthService.getPortalbasauthList(bsq);
									if (basauths.size() > 0) {
										for (Portalbasauth ba : basauths) {
											if (ba.getType() == Integer.valueOf(tid)) {
												authUrl = ba.getUrl();

												if (!stringUtils.isBlank(authUrl))
													break;
												authUrl = ikweb;

												break;
											}
										}

									}

									AjaxInterFaceController.SangforLogin(ip, username, ikmac, apmac, basip);

									ipMap.getInstance().getIpmap().remove(ip);

									if (stringUtils.isNotBlank(authUrl)) {
										response.sendRedirect(authUrl);
										return;
									}
									authUrl = "/" + webMod + "ok.jsp";
									response.sendRedirect(authUrl);
									return;
								}

								AutoLoginMacMap.getInstance().getAutoLoginMacMap().remove(ikmac);
								AutoLoginMap.getInstance().getAutoLoginMap().remove(ikmac);
							} else {
								AutoLoginMacMap.getInstance().getAutoLoginMacMap().remove(ikmac);
								AutoLoginMap.getInstance().getAutoLoginMap().remove(ikmac);
							}
						} else {
							AutoLoginMacMap.getInstance().getAutoLoginMacMap().remove(ikmac);
							AutoLoginMap.getInstance().getAutoLoginMap().remove(ikmac);
						}
					}
				}
			} catch (Exception e) {
				logger.error("==============ERROR Start=============");
				logger.error(e);
				logger.error("ERROR INFO ", e);
				logger.error("==============ERROR End=============");
				AutoLoginMacMap.getInstance().getAutoLoginMacMap().remove(ikmac);
				AutoLoginMap.getInstance().getAutoLoginMap().remove(ikmac);
			}
		}

	}

	if ((1 != CheckMacTimeLimitMethod(ikmac, Long.valueOf(3L)))
			&& (1 != CheckMacTimeLimitMethod(ikmac, Long.valueOf(4L)))) {
		Boolean info = Boolean.valueOf(false);

		String userMac = ikmac;

		if (stringUtils.isNotBlank(userMac)) {
			List<Portalaccountmacs> macs = macsService.getPortalaccountmacsList(new PortalaccountmacsQuery());
			label8524: label8675: for (Portalaccountmacs mac : macs) {
				if (mac.getMac().equals(userMac)) {
					Portalaccount acc = accountService.getPortalaccountByKey(mac.getAccountId());
					if (acc == null)
						break;
					int state = acc.getAutologin().intValue();
					if (state != 1)
						break;
					if (basConfig.getAuthInterface().contains("1")) {
						PortalbasauthQuery bsq = new PortalbasauthQuery();
						bsq.setBasip(basip);
						String authUser = basConfig.getBasUser();
						authPwd = basConfig.getBasPwd();
						String authUrl = ikweb;
						if (stringUtils.isBlank(authUrl)) {
							authUrl = "/" + webMod + "ok.jsp";
						}
						List<Portalbasauth> basauths = basauthService.getPortalbasauthList(bsq);
						if (basauths.size() > 0) {
							for (Portalbasauth ba : basauths) {
								if (ba.getType().intValue() == 1) {
									authUser = ba.getUsername();
									authPwd = ba.getPassword();

									if (stringUtils.isBlank(ikweb)) {
										authUrl = ba.getUrl();
									}

									if ((stringUtils.isBlank(authUser)) || (stringUtils.isBlank(authPwd))) {
										authUser = basConfig.getBasUser();
										authPwd = basConfig.getBasPwd();
									}

									if (!stringUtils.isBlank(authUrl))
										break;
									authUrl = "/" + webMod + "ok.jsp";

									break;
								}
							}
						}

						if (acc == null)
							break;
						Long userId = acc.getId();
						Date userDate = acc.getDate();
						Long userTime = acc.getTime();
						Long octets = acc.getOctets();
						if (octets == null) {
							octets = Long.valueOf(0L);
						}
						String username = acc.getLoginName();
						String userState = acc.getState();
						password = acc.getPassword();

						if (!"1".equals(basConfig.getIsPortalCheck())) {
							authUser = username;
							authPwd = password;
						}

						if (!checkTimeOut(userState, userId, userDate, userTime, octets)) {
							break;
						}
						boolean checkOnlineLimit = true;

						if ("1".equals(basConfig.getIsPortalCheck())) {
							Integer limitCount = acc.getMaclimitcount();
							if ((limitCount != null) && (limitCount.intValue() > 0)) {
								int countOnline = 0;
								Iterator iterator = OnlineMap.getInstance().getOnlineUserMap().keySet()
										.iterator();

								while (iterator.hasNext()) {
									Object o = iterator.next();
									String t = o.toString();
									String[] loginInfo = (String[]) OnlineMap.getInstance().getOnlineUserMap()
											.get(t);
									String haveUsername = loginInfo[0];

									if (username.equals(haveUsername)) {
										countOnline++;
									}
								}
								if (countOnline >= limitCount.intValue()) {
									checkOnlineLimit = false;
								}
							}
						}

						if (!checkOnlineLimit) {
							break;
						}
						if ((basConfig.getBas().equals("1")) || (basConfig.getBas().equals("0"))) {
							info = InterfaceControl.Method("PORTAL_LOGIN", authUser, authPwd, ip, basip, ikmac);
						}

						if ((basConfig.getBas().equals("2")) || (basConfig.getBas().equals("4"))) {
							info = InterfaceControl.Method("PORTAL_LOGIN", authUser, authPwd, ip, basip, ikmac);
						}

						if (basConfig.getBas().equals("3")) {
							String site = (String) session.getAttribute("site");
							PortalaccountQuery aq = new PortalaccountQuery();
							aq.setLoginName(authUser);
							aq.setLoginNameLike(false);
							List accs = accountService.getPortalaccountList(aq);
							int accTime = 1440;
							if (accs.size() == 1) {
								long accTimeLong = ((Portalaccount) accs.get(0)).getTime().longValue() / 60000L;
								if (accTimeLong > 0L) {
									accTime = (int) accTimeLong;
								}
							}
							info = Boolean.valueOf(UniFiMethod.sendAuthorization(ikmac, accTime, site, basip));
						}
						if (!info.booleanValue())
							break;
						Long recordId = doLinkRecord(username, ip, basip, userState, userId, ikmac);

						session.setAttribute("password", password);
						Cookie cookiePwd = new Cookie("password", password);
						cookiePwd.setMaxAge(86400);
						response.addCookie(cookiePwd);
						session.setAttribute("username", username);
						session.setAttribute("ip", ip);
						session.setAttribute("basip", basip);

						Cookie cookieIp = new Cookie("ip", ip);
						cookieIp.setMaxAge(86400);
						response.addCookie(cookieIp);

						Cookie cookieBasIp = new Cookie("basip", basip);
						cookieBasIp.setMaxAge(86400);
						response.addCookie(cookieBasIp);

						String[] loginInfo = new String[16];
						loginInfo[0] = username;
						loginInfo[1] = String.valueOf(userId);
						loginInfo[2] = String.valueOf(recordId);

						Date now = new Date();
						loginInfo[3] = ThreadLocalDateUtil.format(now);
						loginInfo[4] = ikmac;
						loginInfo[5] = "ok";
						loginInfo[6] = "1";
						loginInfo[7] = "0";
						loginInfo[8] = "0";

						loginInfo[9] = ip;
						loginInfo[10] = basip;
						loginInfo[11] = ((Portalbas) config.getConfigMap().get(basip)).getBasname();
						loginInfo[12] = ssid;
						loginInfo[13] = apmac;
						loginInfo[14] = "yes";
						loginInfo[15] = agent;

						onlineMap.getOnlineUserMap().put(ip + ":" + basip, loginInfo);

						Portallogrecord logRecord = new Portallogrecord();
						logRecord.setInfo("IP: " + ip + ":" + basip + " mac: " + ikmac + " 系统用户: " + username
								+ ",无感知登录成功!");
						logRecord.setRecDate(now);
						logrecordService.addPortallogrecord(logRecord);

						UpdateMacTimeLimitMethod(ikmac, Long.valueOf(3L), request.getSession(), username);

						if (stringUtils.isNotBlank(ssid)) {
							try {
								PortalssidQuery apq = new PortalssidQuery();
								apq.setSsid(ssid);
								apq.setSsidLike(false);
								List aps = ssidService.getPortalssidList(apq);
								if ((aps != null) && (aps.size() > 0)) {
									Portalssid ap = (Portalssid) aps.get(0);
									ap.setBasip(basip);
									long count = ap.getCount().longValue() + 1L;
									ap.setCount(Long.valueOf(count));
									ssidService.updatePortalssidByKey(ap);
								} else {
									Portalssid ap = new Portalssid();
									ap.setSsid(ssid);
									ap.setBasip(basip);
									ap.setCount(Long.valueOf(1L));
									ssidService.addPortalssid(ap);
								}
							} catch (Exception e) {
								logger.error("==============ERROR Start=============");
								logger.error(e);
								logger.error("ERROR INFO ", e);
								logger.error("==============ERROR End=============");
							}

						}

						if (stringUtils.isNotBlank(apmac)) {
							try {
								PortalapQuery apq = new PortalapQuery();
								apq.setMac(apmac);
								apq.setMacLike(false);
								List aps = apService.getPortalapList(apq);
								if ((aps != null) && (aps.size() > 0)) {
									Portalap ap = (Portalap) aps.get(0);
									ap.setBasip(basip);
									long count = ap.getCount().longValue() + 1L;
									ap.setCount(Long.valueOf(count));
									apService.updatePortalapByKey(ap);
								} else {
									Portalap ap = new Portalap();
									ap.setMac(apmac);
									ap.setBasip(basip);
									ap.setCount(Long.valueOf(1L));
									apService.addPortalap(ap);
								}
							} catch (Exception e) {
								logger.error("==============ERROR Start=============");
								logger.error(e);
								logger.error("ERROR INFO ", e);
								logger.error("==============ERROR End=============");
							}

						}

						AjaxInterFaceController.SangforLogin(ip, username, ikmac, apmac, basip);

						ipMap.getInstance().getIpmap().remove(ip);

						if (stringUtils.isNotBlank(ikweb)) {
							response.sendRedirect(ikweb);
							return;
						}

						response.sendRedirect(authUrl);
						return;
					}

					PortalbasauthQuery bsq = new PortalbasauthQuery();
					bsq.setBasip(basip);
					String authUrl = ikweb;
					if (stringUtils.isBlank(authUrl)) {
						authUrl = "/" + webMod + "ok.jsp";
					}
					List<Portalbasauth> basauths = basauthService.getPortalbasauthList(bsq);
					if (basauths.size() > 0) {
						for (Portalbasauth ba : basauths) {
							if (ba.getType().intValue() == 2) {
								if (stringUtils.isBlank(ikweb)) {
									authUrl = ba.getUrl();
								}

								if (!stringUtils.isBlank(authUrl))
									break;
								authUrl = "/" + webMod + "ok.jsp";

								break;
							}
						}
					}

					String[] userinfo = null;
					if ((!stringUtils.isBlank(userMac)) && (!"error".equals(userMac))) {
						userinfo = (String[]) AutoLoginMap.getInstance().getAutoLoginMap().get(userMac);
					}
					String username = "";
					String phone = "";
					if ((userinfo != null) && (userinfo.length == 2)) {
						username = userinfo[0];
						password = userinfo[1];
					}
					if ((userinfo != null) && (userinfo.length == 3)) {
						username = userinfo[0];
						password = userinfo[1];
						phone = userinfo[2];
					}

					if (stringUtils.isNotBlank(username)) {
						if (stringUtils.isNotBlank(password)) {
							boolean CheckAccount = false;
							String accountAPI_Url = (String) AccountAPIMap.getInstance().getAccountAPIMap()
									.get("autourl");
							String accountAPI_State = (String) AccountAPIMap.getInstance().getAccountAPIMap()
									.get("autostate");

							if (stringUtils.isNotBlank(accountAPI_Url)) {
								if ((stringUtils.isNotBlank(accountAPI_State))
										&& ("1".equals(accountAPI_State))) {
									boolean apiResult = AccountAPIRequest.send(accountAPI_Url, username,
											password, ip, ikmac, agent);
									if (apiResult) {
										CheckAccount = true;
										break label8524;
									}
									CheckAccount = false;

									break label8524;
								}
							}
							CheckAccount = true;

							if (!CheckAccount) {
								break label8675;
							}
							if ((basConfig.getBas().equals("1")) || (basConfig.getBas().equals("0"))) {
								info = InterfaceControl.Method("PORTAL_LOGIN", username, password, ip, basip,
										ikmac);
							}

							if ((basConfig.getBas().equals("2")) || (basConfig.getBas().equals("4"))) {
								info = InterfaceControl.Method("PORTAL_LOGIN", username, password, ip, basip,
										ikmac);
							}

							if (!basConfig.getBas().equals("3"))
								break label8675;
							String site = (String) session.getAttribute("site");
							info = Boolean.valueOf(UniFiMethod.sendAuthorization(ikmac, 1440, site, basip));

							break label8675;
						}
					}
					info = Boolean.valueOf(false);

					if (!info.booleanValue()) {
						break;
					}
					if (stringUtils.isNotBlank(phone)) {
						username = phone;
					}

					session.setAttribute("username", username);
					session.setAttribute("password", password);
					session.setAttribute("ip", ip);
					session.setAttribute("basip", basip);

					Cookie cookieIp = new Cookie("ip", ip);
					cookieIp.setMaxAge(86400);
					response.addCookie(cookieIp);

					Cookie cookieBasIp = new Cookie("basip", basip);
					cookieBasIp.setMaxAge(86400);
					response.addCookie(cookieBasIp);

					String[] loginInfo = new String[16];
					loginInfo[0] = username;

					Date now = new Date();
					loginInfo[3] = ThreadLocalDateUtil.format(now);
					loginInfo[4] = ikmac;
					loginInfo[5] = "ok";
					loginInfo[6] = "2";
					loginInfo[7] = "0";
					loginInfo[8] = "0";

					loginInfo[9] = ip;
					loginInfo[10] = basip;
					loginInfo[11] = ((Portalbas) config.getConfigMap().get(basip)).getBasname();
					loginInfo[12] = ssid;
					loginInfo[13] = apmac;
					loginInfo[14] = "yes";
					loginInfo[15] = agent;

					onlineMap.getOnlineUserMap().put(ip + ":" + basip, loginInfo);

					Portallogrecord logRecord = new Portallogrecord();
					logRecord.setInfo("IP: " + ip + ":" + basip + " mac: " + ikmac + " Radius用户: " + username
							+ ",无感知登录成功!");
					logRecord.setRecDate(now);
					logrecordService.addPortallogrecord(logRecord);

					UpdateMacTimeLimitMethod(ikmac, Long.valueOf(4L), request.getSession(), username);

					if (stringUtils.isNotBlank(ssid)) {
						try {
							PortalssidQuery apq = new PortalssidQuery();
							apq.setSsid(ssid);
							apq.setSsidLike(false);
							List aps = ssidService.getPortalssidList(apq);
							if ((aps != null) && (aps.size() > 0)) {
								Portalssid ap = (Portalssid) aps.get(0);
								ap.setBasip(basip);
								long count = ap.getCount().longValue() + 1L;
								ap.setCount(Long.valueOf(count));
								ssidService.updatePortalssidByKey(ap);
							} else {
								Portalssid ap = new Portalssid();
								ap.setSsid(ssid);
								ap.setBasip(basip);
								ap.setCount(Long.valueOf(1L));
								ssidService.addPortalssid(ap);
							}
						} catch (Exception e) {
							logger.error("==============ERROR Start=============");
							logger.error(e);
							logger.error("ERROR INFO ", e);
							logger.error("==============ERROR End=============");
						}

					}

					if (stringUtils.isNotBlank(apmac)) {
						try {
							PortalapQuery apq = new PortalapQuery();
							apq.setMac(apmac);
							apq.setMacLike(false);
							List aps = apService.getPortalapList(apq);
							if ((aps != null) && (aps.size() > 0)) {
								Portalap ap = (Portalap) aps.get(0);
								ap.setBasip(basip);
								long count = ap.getCount().longValue() + 1L;
								ap.setCount(Long.valueOf(count));
								apService.updatePortalapByKey(ap);
							} else {
								Portalap ap = new Portalap();
								ap.setMac(apmac);
								ap.setBasip(basip);
								ap.setCount(Long.valueOf(1L));
								apService.addPortalap(ap);
							}
						} catch (Exception e) {
							logger.error("==============ERROR Start=============");
							logger.error(e);
							logger.error("ERROR INFO ", e);
							logger.error("==============ERROR End=============");
						}

					}

					AjaxInterFaceController.SangforLogin(ip, username, ikmac, apmac, basip);

					ipMap.getInstance().getIpmap().remove(ip);

					if (stringUtils.isNotBlank(ikweb)) {
						response.sendRedirect(ikweb);
						return;
					}

					response.sendRedirect(authUrl);
					return;
				}

			}

		}

	}

	ipMap.getInstance().getIpmap().remove(ip);
}

ipMap.getInstance().getIpmap().remove(ip);

Cookie cookieIP = new Cookie("ip", null);
cookieIP.setMaxAge(-1);
response.addCookie(cookieIP);
Cookie cookieBasIp = new Cookie("basip", null);
cookieBasIp.setMaxAge(-1);
response.addCookie(cookieBasIp);
Cookie cookiePwd = new Cookie("password", null);
cookiePwd.setMaxAge(-1);
response.addCookie(cookiePwd);

if ((basConfig.getBas().equals("1")) || (basConfig.getBas().equals("0"))) {
	ip = pip;
	if (stringUtils.isBlank(ip)) {
		ip = (String) session.getAttribute("ip");
	}
	if (stringUtils.isBlank(ip)) {
		if ("0".equals(basConfig.getIsOut())) {
			ip = GetNgnixRemotIP.getRemoteAddrIp(request);
		} else
			ip = "0.0.0.0";

	}

	basip = pbasip;
	if ((stringUtils.isBlank(basip)) && (stringUtils.isNotBlank(pnasname))) {
		PortalbasQuery bq = new PortalbasQuery();
		bq.setBasname(pnasname);
		bq.setBasnameLike(false);
		List bs = basService.getPortalbasList(bq);
		if (bs.size() > 0) {
			basip = ((Portalbas) bs.get(0)).getBasIp();
		}
	}

	if (stringUtils.isBlank(basip)) {
		basip = (String) session.getAttribute("basip");
	}
	if (stringUtils.isBlank(basip)) {
		if ("0".equals(basConfig.getIsOut())) {
			basip = basConfig.getBasIp();
		} else {
			basip = GetNgnixRemotIP.getRemoteAddrIp(request);
		}

	}

}

if ((basConfig.getBas().equals("2")) || (basConfig.getBas().equals("4"))) {
	basip = ikbasip;
	if ((stringUtils.isBlank(basip)) && (stringUtils.isNotBlank(nasname))) {
		PortalbasQuery bq = new PortalbasQuery();
		bq.setBasname(nasname);
		bq.setBasnameLike(false);
		List bs = basService.getPortalbasList(bq);
		if (bs.size() > 0) {
			basip = ((Portalbas) bs.get(0)).getBasIp();
		}
	}

	if (stringUtils.isBlank(basip)) {
		basip = (String) session.getAttribute("basip");
	}
	if (stringUtils.isBlank(basip)) {
		if ("0".equals(basConfig.getIsOut())) {
			basip = basConfig.getBasIp();
		} else {
			basip = GetNgnixRemotIP.getRemoteAddrIp(request);
		}
	}

	ip = ikip;
	if (stringUtils.isBlank(ip)) {
		ip = (String) iKuaiMacIpMap.getInstance().getMacIpMap().get(ikmac);
	}
	if (stringUtils.isBlank(ip)) {
		if ("0".equals(basConfig.getIsOut())) {
			ip = GetNgnixRemotIP.getRemoteAddrIp(request);
		} else
			ip = "0.0.0.0";

	}

}

if (basConfig.getBas().equals("3")) {
	basip = basConfig.getBasIp();

	ip = GetNgnixRemotIP.getRemoteAddrIp(request);
}

basConfigFind = (Portalbas) config.getConfigMap().get(basip);
if (basConfigFind != null) {
	if (stringUtils.isNotBlank(basConfigFind.getBasIp()))
		basConfig = basConfigFind;
} else {
	basip = basConfig.getBasIp();
}

session.setAttribute("ip", ip);
session.setAttribute("basip", basip);
session.setAttribute("ikweb", ikweb);
session.setAttribute("ssid", ssid);

String auth = basConfig.getAuthInterface();
session.setAttribute("auth", auth);

session.setAttribute("api", "default");

if (Do()) {
	try {
	if (stringUtils.isNotBlank(webMod)) {
		String ids = webMod.replace("/", "");
		Long id = Long.valueOf(ids);
		Portalweb web = webService.getPortalwebByKey(id);
		Long advID = Long.valueOf(0L);
		if (web != null) {
			advID = web.getAdv();
			Advadv adv = advadvService.getAdvadvByKey(advID);
			if (adv != null) {
				int state = adv.getState().intValue();
				if (state == 1) {
					Date startDate = adv.getShowDate();
					Date endDate = adv.getEndDate();
					Date nowDate = new Date();
					if (((startDate == null) || (nowDate.getTime() >= startDate.getTime()))
							&& ((endDate == null) || (endDate.getTime() >= nowDate.getTime()))) {
						request.getRequestDispatcher("/portal.action?id=" + advID + "&auth=0")
								.forward(request, response);
					}
				}
			}
		}
	}
	} catch (Exception localException2) {
	}
}
request.getRequestDispatcher("/" + webMod + "auth.jsp").forward(request, response);
}

	public static String getWebMod(String ssid, String apmac, String ip, Long basConfigWeb) {
		String webMod = "";
		Long webID = Long.valueOf(0L);
		if (Do()) {
			try {
				PortaltimewebQuery timewebq;
				try {
					Date date = new Date();
					int weekDay = date.getDay();
					if (weekDay == 0) {
						weekDay = 7;
					}
					int dateDay = date.getDate();
					int monthDay = date.getMonth() + 1;

					timewebq = new PortaltimewebQuery();
					timewebq.orderbyPos(true);
					timewebq.orderbyId(true);
					List<Portaltimeweb> timewebs = portaltimewebService.getPortaltimewebList(timewebq);
					if ((timewebs != null) && (timewebs.size() > 0))
						for (Portaltimeweb timeweb : timewebs) {
							Date setday = timeweb.getViewdate();
							int weekday = timeweb.getViewweekday().intValue();
							int dateday = timeweb.getViewdateday().intValue();
							int monthday = timeweb.getViewmonth().intValue();
							if ((setday != null) || (weekday != 0) || (dateday != 0) || (monthday != 0)) {
								if (setday != null) {
									long time = date.getTime();
									long endTime = setday.getTime() + 86400000L;
									if ((setday.getTime() <= time) && (time <= endTime)) {
										Portalweb web = webService.getPortalwebByKey(timeweb.getWeb());
										if (web != null) {
											webMod = String.valueOf(web.getId()) + "/";
											webID = web.getId();
											break;
										}
									}
								} else {
									boolean weekState = false;
									boolean dayState = false;
									boolean monthState = false;
									if (weekday == 0) {
										weekState = true;
									} else if (weekDay == weekday) {
										weekState = true;
									}

									if (dateday == 0) {
										dayState = true;
									} else if (dateDay == dateday) {
										dayState = true;
									}

									if (monthday == 0) {
										monthState = true;
									} else if (monthDay == monthday) {
										monthState = true;
									}

									if ((weekState) && (dayState) && (monthState)) {
										Portalweb web = webService.getPortalwebByKey(timeweb.getWeb());
										if (web != null) {
											webMod = String.valueOf(web.getId()) + "/";
											webID = web.getId();
											break;
										}
									}
								}
							}
						}
				} catch (Exception localException1) {
				}
				if ((stringUtils.isBlank(webMod)) && (stringUtils.isNotBlank(ssid))) {
					PortalssidQuery ssidq = new PortalssidQuery();
					ssidq.setSsid(ssid);
					ssidq.setSsidLike(false);
					List ssids = ssidService.getPortalssidList(ssidq);
					if ((ssids != null) && (ssids.size() > 0)) {
						Portalssid sside = (Portalssid) ssids.get(0);
						Portalweb web = webService.getPortalwebByKey(sside.getWeb());
						if (web != null) {
							webMod = String.valueOf(web.getId()) + "/";
							webID = web.getId();
						}
					}
				}

				if ((stringUtils.isBlank(webMod)) && (stringUtils.isNotBlank(apmac))) {
					PortalapQuery apq = new PortalapQuery();
					apq.setMac(apmac);
					apq.setMacLike(false);
					List aps = apService.getPortalapList(apq);
					if ((aps != null) && (aps.size() > 0)) {
						Portalap ap = (Portalap) aps.get(0);
						Portalweb web = webService.getPortalwebByKey(ap.getWeb());
						if (web != null) {
							webMod = String.valueOf(web.getId()) + "/";
							webID = web.getId();
						}
					}
				}

				if (stringUtils.isBlank(webMod)) {
					Portalweb web = webService.getPortalwebByKey(basConfigWeb);
					if (web != null) {
						webMod = String.valueOf(web.getId()) + "/";
						webID = web.getId();
					}
				}
				if (stringUtils.isBlank(webMod)) {
					long ipL = IPv4Util.ipToLong(ip);
					List<Portalip> iplList = ipService.getPortalipList(new PortalipQuery());
					for (Portalip portalip : iplList) {
						long startH = IPv4Util.ipToLong(portalip.getStart());
						long endH = IPv4Util.ipToLong(portalip.getEnd());
						if ((ipL >= startH) && (ipL <= endH)) {
							Portalweb web = webService.getPortalwebByKey(portalip.getWeb());
							if (web == null)
								break;
							webMod = String.valueOf(web.getId()) + "/";
							webID = web.getId();

							break;
						}
					}
				}
			} catch (Exception e) {
				webMod = "";
				webID = Long.valueOf(0L);
			}
		}
		try {
			if (webID.longValue() != 0L) {
				Portalweb portalweb = webService.getPortalwebByKey(webID);
				if (portalweb != null) {
					portalweb.setCountShow(Long.valueOf(portalweb.getCountShow().longValue() + 1L));
					webService.updatePortalwebByKey(portalweb);
				}
			} else {
				com.leeson.core.bean.Config config = configService.getConfigByKey(Long.valueOf(1L));
				if (config != null) {
					config.setCountShow(Long.valueOf(config.getCountShow().longValue() + 1L));
					configService.updateConfigByKey(config);
				}
			}
		} catch (Exception localException2) {
		}
		return webMod;
	}

	private static boolean checkTimeOut(String userState, Long userId, Date userDate, Long userTime, Long octets) {
		Portalaccount account = accountService.getPortalaccountByKey(userId);

		if (userState.equals("0")) {
			return false;
		}

		if (userState.equals("1")) {
			return true;
		}

		if (userState.equals("3")) {
			Date now = new Date();
			if (userDate.getTime() > now.getTime()) {
				return true;
			}
			account.setState("2");
			accountService.updatePortalaccountByKey(account);
			userState = "2";
		}

		if (userState.equals("2")) {
			if (userTime.longValue() > 0L) {
				return true;
			}
			account.setState("4");
			accountService.updatePortalaccountByKey(account);
			userState = "4";
		}

		if (userState.equals("4")) {
			if (octets.longValue() > 0L) {
				return true;
			}
			account.setState("0");
			accountService.updatePortalaccountByKey(account);
			return false;
		}

		return false;
	}

	private static Long doLinkRecord(String username, String ip, String basip, String userState, Long userId,
			String mac) {
		Portalbas basconfig = (Portalbas) config.getConfigMap().get(basip);
		if ((basconfig != null) && ("1".equals(basconfig.getIsPortalCheck()))) {
			Portallinkrecord linkRecord = new Portallinkrecord();
			linkRecord.setStartDate(new Date());
			linkRecord.setIp(ip);
			linkRecord.setBasip(basip);
			linkRecord.setMac(mac);
			linkRecord.setIns(Long.valueOf(0L));
			linkRecord.setOuts(Long.valueOf(0L));
			linkRecord.setOctets(Long.valueOf(0L));
			linkRecord.setLoginName(username);
			linkRecord.setState(userState);
			linkRecord.setUid(userId);
			linkRecordService.addPortallinkrecord(linkRecord);
			return linkRecord.getId();
		}

		return null;
	}

	private static int CheckMacTimeLimitMethod(String param, Long id) {
		Portalonlinelimit onlinelimit = onlinelimitService.getPortalonlinelimitByKey(id);
		if (onlinelimit.getState().intValue() == 1) {
			if (onlinelimit.getType().intValue() == 0) {
				if ((stringUtils.isNotBlank(param)) && (!"error".equals(param))) {
					String[] TimeInfo = (String[]) MacLimitMap.getInstance().getMacLimitMap().get(param);
					if (TimeInfo != null) {
						Long timepermit = onlinelimit.getTime();
						if (Long.valueOf(TimeInfo[1]).longValue() >= timepermit.longValue())
							return 1;
					}
				} else {
					return 2;
				}
			} else if (stringUtils.isNotBlank(param)) {
				String[] TimeInfo = (String[]) UserLimitMap.getInstance().getUserLimitMap().get(param);
				if (TimeInfo != null) {
					Long timepermit = onlinelimit.getTime();
					if (Long.valueOf(TimeInfo[1]).longValue() >= timepermit.longValue())
						return 1;
				}
			} else {
				return 2;
			}
		}

		return 0;
	}

	private static void GetMacTimeLimitMethod(String[] loginInfo, String mac, HttpSession session) {
		String username = loginInfo[0];

		String loginTimeString = loginInfo[3];
		if ((stringUtils.isBlank(mac)) || ("error".equals(mac))) {
			mac = loginInfo[4];
		}
		Date nowTime = new Date();
		Date loginTime = nowTime;
		try {
			loginTime = ThreadLocalDateUtil.parse(loginTimeString);
		} catch (Exception localException) {
		}

		int costTime = (int) ((nowTime.getTime() - loginTime.getTime()) / 60000L);

		int haveTime = (int) (getTime(username).longValue() / 60000L);

		int toHaveTime = haveTime;

		Long oldcostTime = Long.valueOf(0L);
		Boolean notLimit = Boolean.valueOf(true);
		if ((stringUtils.isNotBlank(mac)) && (!"error".equals(mac))) {
			String[] macTimeInfo = (String[]) MacLimitMap.getInstance().getMacLimitMap().get(mac);
			if (macTimeInfo != null) {
				Long id = Long.valueOf(macTimeInfo[2]);
				oldcostTime = Long.valueOf(macTimeInfo[1]);
				Portalonlinelimit onlinelimit = onlinelimitService.getPortalonlinelimitByKey(id);
				if (onlinelimit != null) {
					Long timepermit = onlinelimit.getTime();
					loginTimeString = macTimeInfo[0];
					if (onlinelimit.getState().intValue() == 1) {
						if (stringUtils.isNotBlank(loginTimeString))
							try {
								loginTime = ThreadLocalDateUtil.parse(loginTimeString);
							} catch (Exception localException1) {
							}
						costTime = (int) ((nowTime.getTime() - loginTime.getTime() + oldcostTime.longValue()) / 60000L);
						haveTime = (int) (timepermit.longValue() / 60000L);
						notLimit = Boolean.valueOf(false);
					}
				}
			}
		}

		int overTime = costTime;
		if (!notLimit.booleanValue()) {
			haveTime -= overTime;
		}
		if (haveTime > toHaveTime) {
			haveTime = toHaveTime;
		}
		if (haveTime < 0) {
			haveTime = 0;
		}
		if (notLimit.booleanValue()) {
			overTime = costTime + (int) (oldcostTime.longValue() / 60000L);
		}

		String haveTimeString = String.valueOf(haveTime);
		String overTimeString = String.valueOf(overTime);
		session.setAttribute("haveTime", haveTimeString);
		session.setAttribute("overTime", overTimeString);
	}

	public static void UpdateMacTimeLimitMethod(String mac, Long id, HttpSession session, String username) {
		Portalonlinelimit onlinelimit = onlinelimitService.getPortalonlinelimitByKey(id);
		Long timepermit = onlinelimit.getTime();
		Long costTime = Long.valueOf(0L);
		int haveTime = (int) (getTime(username).longValue() / 60000L);
		int toHaveTime = haveTime;
		Long oldcostTime = Long.valueOf(0L);
		Boolean notLimit = Boolean.valueOf(true);
		if ((stringUtils.isNotBlank(mac)) && (!"error".equals(mac))) {
			String[] macTimeInfo = (String[]) MacLimitMap.getInstance().getMacLimitMap().get(mac);
			if (macTimeInfo != null) {
				oldcostTime = Long.valueOf(macTimeInfo[1]);
			}
			if ((onlinelimit.getState().intValue() == 1) && (onlinelimit.getType().intValue() == 0)) {
				Date now = new Date();
				String nowString = ThreadLocalDateUtil.format(now);
				if (macTimeInfo == null) {
					macTimeInfo = new String[3];
					macTimeInfo[1] = "0";
				}
				macTimeInfo[0] = nowString;
				macTimeInfo[2] = String.valueOf(id);
				MacLimitMap.getInstance().getMacLimitMap().put(mac, macTimeInfo);
				costTime = oldcostTime;
				haveTime = (int) (timepermit.longValue() / 60000L);
				notLimit = Boolean.valueOf(false);
			}
		}

		int overTime = (int) (costTime.longValue() / 60000L);
		haveTime -= overTime;
		if (haveTime > toHaveTime) {
			haveTime = toHaveTime;
		}
		if (haveTime < 0) {
			haveTime = 0;
		}
		if (notLimit.booleanValue()) {
			overTime += (int) (oldcostTime.longValue() / 60000L);
		}
		String haveTimeString = String.valueOf(haveTime);
		String overTimeString = String.valueOf(overTime);
		session.setAttribute("haveTime", haveTimeString);
		session.setAttribute("overTime", overTimeString);
	}

	private static Long getTime(String username) {
		PortalaccountQuery query = new PortalaccountQuery();
		query.setLoginName(username);
		query.setLoginNameLike(false);
		List as = accountService.getPortalaccountList(query);
		if ((as != null) && (as.size() > 0)) {
			Portalaccount a = (Portalaccount) as.get(0);
			String userState = a.getState();
			Date userDate = a.getDate();
			Long userTime = a.getTime();

			if (userState.equals("0")) {
				return Long.valueOf(0L);
			}

			if (userState.equals("1")) {
				if (userTime.longValue() > 0L) {
					return userTime;
				}
				return Long.valueOf(0L);
			}

			if (userState.equals("3")) {
				Date now = new Date();
				if (userDate.getTime() > now.getTime()) {
					return Long.valueOf(userDate.getTime() - now.getTime());
				}
				return Long.valueOf(0L);
			}

			if (userState.equals("2")) {
				if (userTime.longValue() > 0L) {
					return userTime;
				}
				return Long.valueOf(0L);
			}

			return Long.valueOf(0L);
		}
		return Long.valueOf(getHaveTime() * 60000L);
	}

	private static int getHaveTime() {
		try {
			Date nowdate = new Date();
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(nowdate);
			calendar.add(5, 1);
			Date tdate = calendar.getTime();
			SimpleDateFormat fs = new SimpleDateFormat("yyyy-MM-dd");
			String tsString = fs.format(tdate);
			Date t = fs.parse(tsString);
			return (int) ((t.getTime() - nowdate.getTime()) / 60000L);
		} catch (Exception e) {
		}
		return 1440;
	}

	public static boolean Do() {
		Long isThis = Long.valueOf(new Date().getTime());
		boolean Do = false;
		if (isThis.longValue() < isDo.getInstance().getId().longValue()) {
			Do = true;
		}
		return Do;
	}

	public static void AutoLoginPutMethod(String mac, Long id) {
		Portalautologin autologin = autologinService.getPortalautologinByKey(id);
		if ((autologin != null) && (autologin.getState().intValue() == 1) && (stringUtils.isNotBlank(mac))
				&& (!"error".equals(mac))) {
			String[] macTimeInfo = (String[]) AutoLoginMacMap.getInstance().getAutoLoginMacMap().get(mac);
			Date now = new Date();
			String nowString = ThreadLocalDateUtil.format(now);
			if (macTimeInfo == null) {
				macTimeInfo = new String[3];
				macTimeInfo[1] = "0";
			}
			macTimeInfo[0] = nowString;
			macTimeInfo[2] = String.valueOf(id);
			AutoLoginMacMap.getInstance().getAutoLoginMacMap().put(mac, macTimeInfo);
		}
	}
}

/*
 * Location:
 * C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name: com.leeson.portal.core.controller.IndexAction JD-Core
 * Version: 0.6.2
 */