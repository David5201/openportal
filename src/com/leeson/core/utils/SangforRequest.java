package com.leeson.core.utils;

import com.leeson.common.utils.stringUtils;
import com.leeson.core.bean.Portalbas;
import com.leeson.portal.core.model.Config;
import com.leeson.portal.core.model.SangforAPIMap;
import com.leeson.portal.core.utils.AaHpl8Ha9bNPen9OLddV;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;

public class SangforRequest {
	private static Config config = Config.getInstance();
	private static Logger logger = Logger.getLogger(SangforRequest.class);
	private static SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");

	private static void sendGet(String url, String param) {
		String result = "";
		BufferedReader in = null;
		try {
			String urlNameString = url + "?" + param;
			URL realUrl = new URL(urlNameString);

			URLConnection connection = realUrl.openConnection();

			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");

			connection.connect();

			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result = result + line;
			}
			if (((Portalbas) config.getConfigMap().get("")).getIsdebug().equals("1"))
				System.out.println(result);
		} catch (Exception e) {
			if (((Portalbas) config.getConfigMap().get("")).getIsdebug().equals("1")) {
				logger.error("==============ERROR Start=============");
				logger.error(e);
				logger.error("ERROR INFO ", e);
				logger.error("==============ERROR End=============");
			}

			try {
				if (in != null)
					in.close();
			} catch (Exception localException1) {
			}
		} finally {
			try {
				if (in != null)
					in.close();
			} catch (Exception localException2) {
			}
		}
	}

	public static void login(String userIP, String userName, String mac, String apmac, String basip) {
		try {
			String type = (String) SangforAPIMap.getInstance().getSangforAPIMap().get("type");
			String serverIP = (String) SangforAPIMap.getInstance().getSangforAPIMap().get("url");
			String port = (String) SangforAPIMap.getInstance().getSangforAPIMap().get("port");
			if (stringUtils.isNotBlank(type))
				if (type.equals("0")) {
					if (stringUtils.isNotBlank(serverIP)) {
						if (((Portalbas) config.getConfigMap().get("")).getIsdebug().equals("1")) {
							logger.error("Sangfor logon : serverIP=" + serverIP + " userIP=" + userIP + " userName="
									+ userName);
						}
						String url = "http:gi-bin/caauth.cgi";
						String params = "ui=web&opr=logon&chk_cookie=0&info=";
						String paramSub = userIP + "/" + userName + "/" + "/CloudPortal";
						paramSub = new String(paramSub.getBytes("UTF-8"), "UTF-8");
						paramSub = AaHpl8Ha9bNPen9OLddV.encryptBASE64(paramSub);
						params = params + paramSub;
						sendGet(url, params);
					}
				} else if ((type.equals("1")) && (stringUtils.isNotBlank(serverIP)) && (stringUtils.isNotBlank(port))) {
					String url = "http:ronline/Msg";
					Date now = new Date();
					String timeS = format.format(now);
					long timeLong = now.getTime() + 86400000L;
					Date expiretime = new Date(timeLong);
					String expiretimeS = format.format(expiretime);
					if (stringUtils.isBlank(apmac)) {
						apmac = basip;
					}
					String params = "FunName@ncHttpLogin&account=" + userName + "&expiretime=" + expiretimeS + "&ip="
							+ userIP + "&mac=" + mac + "&time=" + timeS + "&location=" + apmac + "&netid=" + basip;
					sendGet(url, params);
				}
		} catch (Exception e) {
			if (((Portalbas) config.getConfigMap().get("")).getIsdebug().equals("1")) {
				logger.error("==============ERROR Start=============");
				logger.error(e);
				logger.error("ERROR INFO ", e);
				logger.error("==============ERROR End=============");
			}
		}
	}

	public static void logout(String userIP, String userName) {
		try {
			String type = (String) SangforAPIMap.getInstance().getSangforAPIMap().get("type");
			String serverIP = (String) SangforAPIMap.getInstance().getSangforAPIMap().get("url");
			String port = (String) SangforAPIMap.getInstance().getSangforAPIMap().get("port");
			if (stringUtils.isNotBlank(type)) {
				if (type.equals("0")) {
					if (stringUtils.isNotBlank(serverIP)) {
						if (((Portalbas) config.getConfigMap().get("")).getIsdebug().equals("1")) {
							logger.error("Sangfor logout : serverIP=" + serverIP + " userIP=" + userIP + " userName="
									+ userName);
						}
						String url = "http:gi-bin/caauth.cgi";
						String params = "ui=web&opr=logout&chk_cookie=0&info=";
						String paramSub = userIP + "/" + userName + "/" + "/CloudPortal";
						paramSub = new String(paramSub.getBytes("UTF-8"), "UTF-8");
						paramSub = AaHpl8Ha9bNPen9OLddV.encryptBASE64(paramSub);
						params = params + paramSub;
						sendGet(url, params);
					}
				} else if ((type.equals("1")) && (stringUtils.isNotBlank(serverIP)) && (stringUtils.isNotBlank(port))) {
					String url = "http:ronline/Msg";
					Date now = new Date();
					String timeS = format.format(now);
					String params = "FunName@ncHttpLogout&ip=" + userIP + "&time=" + timeS;
					sendGet(url, params);
				}
			}
		} catch (Exception e) {
			if (((Portalbas) config.getConfigMap().get("")).getIsdebug().equals("1")) {
				logger.error("==============ERROR Start=============");
				logger.error(e);
				logger.error("ERROR INFO ", e);
				logger.error("==============ERROR End=============");
			}
		}
	}
}

/*
 * Location:
 * C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name: com.leeson.core.utils.SangforRequest JD-Core Version: 0.6.2
 */