package com.leeson.core.utils;

import com.leeson.common.utils.ThreadLocalDateUtil;
import com.leeson.common.utils.stringUtils;
import com.leeson.core.bean.Portalaccount;
import com.leeson.core.bean.Portalbas;
import com.leeson.core.bean.Portallinkrecord;
import com.leeson.core.bean.Portallinkrecordall;
import com.leeson.core.bean.Portallogrecord;
import com.leeson.core.controller.AjaxInterFaceController;
import com.leeson.core.service.PortalaccountService;
import com.leeson.core.service.PortallinkrecordService;
import com.leeson.core.service.PortallinkrecordallService;
import com.leeson.core.service.PortallogrecordService;
import com.leeson.portal.core.model.Config;
import com.leeson.portal.core.model.LateAuthMap;
import com.leeson.portal.core.model.OnlineMap;
import com.leeson.portal.core.model.UniFiMacSiteMap;
import com.leeson.portal.core.model.ipMap;
import com.leeson.portal.core.service.InterfaceControl;
import com.leeson.portal.core.service.action.unifi.UniFiMethod;
import com.leeson.portal.core.utils.SpringContextHelper;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

public class Kick {
	private static Logger log = Logger.getLogger(Kick.class);
	private static Config configDefault = Config.getInstance();
	private static OnlineMap onlineMap = OnlineMap.getInstance();

	private static ApplicationContext ac = SpringContextHelper.getApplicationContext();
	private static PortallinkrecordallService linkAllService = (PortallinkrecordallService) ac
			.getBean("portallinkrecordallServiceImpl");

	private static PortallinkrecordService portallinkrecordService = (PortallinkrecordService) ac
			.getBean("portallinkrecordServiceImpl");

	private static PortalaccountService portalaccountService = (PortalaccountService) ac
			.getBean("portalaccountServiceImpl");

	private static PortallogrecordService portallogrecordService = (PortallogrecordService) ac
			.getBean("portallogrecordServiceImpl");

	public static void deleteOnline(String ip) {
		int i = ip.lastIndexOf(":");
		String ubip = ip.substring(i + 1);
		String uip = ip.substring(0, i);

		String[] loginInfo = null;
		loginInfo = (String[]) onlineMap.getOnlineUserMap().get(ip);
		if (loginInfo != null) {
			doLinkAll(loginInfo, "管理员清除");
			String username = loginInfo[0];
			String mac = loginInfo[4];
			String type = loginInfo[6];
			Portalbas config = (Portalbas) configDefault.getConfigMap().get(ubip);
			if ((config != null) && ("1".equals(type)) && ("1".equals(config.getIsPortalCheck()))) {
				doLinkRecord(loginInfo, "管理员清除");
			}
			try {
				String time = loginInfo[3];
				Date loginTime = ThreadLocalDateUtil.parse(time);
				String nowString = ThreadLocalDateUtil.format(new Date());
				Date nowTime = ThreadLocalDateUtil.parse(nowString);
				Long costTime = Long.valueOf(nowTime.getTime() - loginTime.getTime());
				costTime = Long.valueOf(costTime.longValue() / 1000L / 60L);
				doLogRecord("IP: " + ip + " mac: " + mac + " 用户: " + username + " 上线时间: " + time + " 在线时长: " + costTime
						+ "分钟,管理员清除！");

				if (((Portalbas) configDefault.getConfigMap().get("")).getIsdebug().equals("1"))
					log.info("IP: " + ip + " mac: " + mac + " user: " + username + " , Del Online By Admin!");
			} catch (Exception localException) {
			}
			AjaxInterFaceController.SangforLogout(uip, username);
			CheckTimeUtils.recordTime(loginInfo);
			CheckAutoLoginUtils.recordTime(loginInfo);
		}

		onlineMap.getOnlineUserMap().remove(ip);
		ipMap.getInstance().getIpmap().remove(uip);
	}

	public static void kickUserWISPrSyn(String ip, String mac, String site) {
		int i = ip.lastIndexOf(":");
		String ubip = ip.substring(i + 1);
		String uip = ip.substring(0, i);

		String[] loginInfo = null;
		loginInfo = (String[]) onlineMap.getOnlineUserMap().get(ip);
		if (loginInfo != null) {
			doLinkAll(loginInfo, "WISPr同步");
			String username = loginInfo[0];
			mac = loginInfo[4];
			String type = loginInfo[6];
			Portalbas config = (Portalbas) configDefault.getConfigMap().get(ubip);
			if ((config != null) && ("1".equals(type)) && ("1".equals(config.getIsPortalCheck()))) {
				doLinkRecord(loginInfo, "WISPr同步");
			}
			try {
				String time = loginInfo[3];
				Date loginTime = ThreadLocalDateUtil.parse(time);
				String nowString = ThreadLocalDateUtil.format(new Date());
				Date nowTime = ThreadLocalDateUtil.parse(nowString);
				Long costTime = Long.valueOf(nowTime.getTime() - loginTime.getTime());
				costTime = Long.valueOf(costTime.longValue() / 1000L / 60L);
				doLogRecord("IP: " + ip + " mac: " + mac + " 用户: " + username + " 上线时间: " + time + " 在线时长: " + costTime
						+ "分钟,WISPr同步!");

				if (((Portalbas) configDefault.getConfigMap().get("")).getIsdebug().equals("1"))
					log.info("IP: " + ip + " mac: " + mac + " user: " + username + " , OffLine By WISPrSyn!");
			} catch (Exception localException) {
			}
			AjaxInterFaceController.SangforLogout(uip, username);
			CheckTimeUtils.recordTime(loginInfo);
			CheckAutoLoginUtils.recordTime(loginInfo);
		}

		onlineMap.getOnlineUserMap().remove(ip);
		ipMap.getInstance().getIpmap().remove(uip);
	}

	public static void kickUserSyn(String ip) {
		int i = ip.lastIndexOf(":");
		String ubip = ip.substring(i + 1);
		String uip = ip.substring(0, i);
		String mac = "";
		String[] loginInfo = null;
		loginInfo = (String[]) onlineMap.getOnlineUserMap().get(ip);
		if (loginInfo != null) {
			doLinkAll(loginInfo, "在线同步");
			String username = loginInfo[0];
			mac = loginInfo[4];
			String type = loginInfo[6];
			Portalbas config = (Portalbas) configDefault.getConfigMap().get(ubip);
			if ((config != null) && ("1".equals(type)) && ("1".equals(config.getIsPortalCheck()))) {
				doLinkRecord(loginInfo, "在线同步");
			}
			try {
				String time = loginInfo[3];
				Date loginTime = ThreadLocalDateUtil.parse(time);
				String nowString = ThreadLocalDateUtil.format(new Date());
				Date nowTime = ThreadLocalDateUtil.parse(nowString);
				Long costTime = Long.valueOf(nowTime.getTime() - loginTime.getTime());
				costTime = Long.valueOf(costTime.longValue() / 1000L / 60L);
				doLogRecord("IP: " + ip + " mac: " + mac + " 用户: " + username + " 上线时间: " + time + " 在线时长: " + costTime
						+ "分钟,在线同步!");

				if (((Portalbas) configDefault.getConfigMap().get("")).getIsdebug().equals("1"))
					log.info("IP: " + ip + " mac: " + mac + " user: " + username + " , OffLine By IkuaiSyn!");
			} catch (Exception localException) {
			}
			AjaxInterFaceController.SangforLogout(uip, username);
			CheckTimeUtils.recordTime(loginInfo);
			CheckAutoLoginUtils.recordTime(loginInfo);
		}

		onlineMap.getOnlineUserMap().remove(ip);
		ipMap.getInstance().getIpmap().remove(uip);
	}

	public static void kickUserDeleteUser(String ip) {
		coreMethod(ip, "删除用户", "Portal Kick By Delete User");
	}

	public static void kickUserUpdate(String ip) {
		coreMethod(ip, "服务器更新", "Portal Kick By Server Update");
	}

	public static void kickUserUnDingYue(String ip) {
		coreMethod(ip, "取消关注", "Portal Kick By UnDingYue WeiXin");
	}

	public static void kickUserOneDayLimit(String ip) {
		coreMethod(ip, "每日时长", "Portal Kick By One Day Online Limit");
	}

	public static void kickUserAutoLogin(String ip) {
		coreMethod(ip, "无感知时长", "Portal Kick By AutoLogin Limit");
	}

	public static void kickUserLaterAuth(String ip) {
		coreMethod(ip, "延迟认证", "Portal Kick By LaterAuth Limit");
	}

	public static void kickUserSessionTimeOut(String ip) {
		coreMethod(ip, "SessionTimeOut", "Portal Kick By SessionTimeOut");
	}

	public static void kickUserByCustomer(String ip) {
		coreMethod(ip, "用户中心操作", "Portal Kick By Customer");
	}

	public static void kickUserByAdmin(String ip) {
		coreMethod(ip, "管理员操作", "Portal Kick By Admin");
	}

	public static void kickUserAllAPI(String ip) {
		coreMethod(ip, "API操作", "Portal Kick By API");
	}

	public static void kickUserIPAPI(String ip) {
		coreMethod(ip, "API操作", "Portal Kick By API");
	}

	public static void offLine(String ip, String mac, String site) {
		coreMethod(ip, "主动下线", "OffLine By Self");
	}

	public static void WeixinOffLine(String ip) {
		coreMethod(ip, "微信临时放行", "Portal Kick By UnFinish WinXin Auth");
	}

	private static void doLinkRecord(String[] loginInfo, String info) {
		try {
			String state = null;
			String uid = loginInfo[1];
			String rid = loginInfo[2];
			if ((stringUtils.isBlank(uid)) || (stringUtils.isBlank(rid))) {
				return;
			}
			Long userId = Long.valueOf(Long.parseLong(loginInfo[1]));
			Long recordId = Long.valueOf(Long.parseLong(loginInfo[2]));
			if ((userId == null) || (recordId == null) || (userId.longValue() == 0L) || (recordId.longValue() == 0L)) {
				return;
			}
			Portallinkrecord linkRecord = portallinkrecordService.getPortallinkrecordByKey(recordId);
			Portalaccount account = portalaccountService.getPortalaccountByKey(userId);
			if (account != null) {
				state = account.getState();
			}
			long in = Long.valueOf(loginInfo[7]).longValue();
			long out = Long.valueOf(loginInfo[8]).longValue();
			long octets = in + out;
			linkRecord.setIns(Long.valueOf(in));
			linkRecord.setOuts(Long.valueOf(out));
			linkRecord.setOctets(Long.valueOf(octets));
			Date now = new Date();
			linkRecord.setEndDate(now);
			Long costTime = Long.valueOf(now.getTime() - linkRecord.getStartDate().getTime());
			linkRecord.setTime(costTime);
			linkRecord.setState(state);
			linkRecord.setEx1("Portal");
			linkRecord.setEx2(info);
			portallinkrecordService.updatePortallinkrecordByKey(linkRecord);
			if (!state.equals("1")) {
				Long haveTime = account.getTime();
				Long newHaveTime = Long.valueOf(haveTime.longValue() - costTime.longValue());
				if ((state.equals("3")) && (account.getDate().getTime() <= now.getTime())) {
					account.setState("2");
					portalaccountService.updatePortalaccountByKey(account);
				}

				if (state.equals("2")) {
					if (newHaveTime.longValue() <= 0L) {
						account.setState("4");
					}
					account.setTime(newHaveTime);
					portalaccountService.updatePortalaccountByKey(account);
				}
				if ((state.equals("4")) || (state.equals("0"))) {
					long haveOctets = account.getOctets().longValue() - octets;
					if (haveOctets <= 0L) {
						account.setState("0");
					}
					account.setOctets(Long.valueOf(haveOctets));
					portalaccountService.updatePortalaccountByKey(account);
				}
			}
		} catch (Exception localException) {
		}
	}

	private static void doLogRecord(String info) {
		try {
			Portallogrecord logRecord = new Portallogrecord();
			Date nowDate = new Date();
			logRecord.setInfo(info);
			logRecord.setRecDate(nowDate);
			portallogrecordService.addPortallogrecord(logRecord);
		} catch (Exception localException) {
		}
	}

	public static void coreMethod(String ip, String msg, String logInfo) {
		int i = ip.lastIndexOf(":");
		String ubip = ip.substring(i + 1);
		String uip = ip.substring(0, i);

		Portalbas config = (Portalbas) configDefault.getConfigMap().get(ubip);
		if (config == null) {
			String[] loginInfo = null;
			loginInfo = (String[]) onlineMap.getOnlineUserMap().get(ip);
			if (loginInfo != null) {
				doLinkAll(loginInfo, msg);
				String username = loginInfo[0];
				String mac = loginInfo[4];
				try {
					String time = loginInfo[3];
					Date loginTime = ThreadLocalDateUtil.parse(time);
					String nowString = ThreadLocalDateUtil.format(new Date());
					Date nowTime = ThreadLocalDateUtil.parse(nowString);
					Long costTime = Long.valueOf(nowTime.getTime() - loginTime.getTime());
					costTime = Long.valueOf(costTime.longValue() / 1000L / 60L);

					doLogRecord("IP: " + ip + " mac: " + mac + " 用户: " + username + " 上线时间: " + time + " 在线时长: "
							+ costTime + "分钟," + msg + "！");

					if (((Portalbas) configDefault.getConfigMap().get("")).getIsdebug().equals("1"))
						log.info("IP: " + ip + " mac: " + mac + " user: " + username + " , " + logInfo + "!");
				} catch (Exception localException) {
				}
				CheckTimeUtils.recordTime(loginInfo);
				CheckAutoLoginUtils.recordTime(loginInfo);
				AjaxInterFaceController.SangforLogout(uip, username);
			}

			onlineMap.getOnlineUserMap().remove(ip);
			return;
		}

		String[] loginInfo = null;
		loginInfo = (String[]) onlineMap.getOnlineUserMap().get(ip);
		if (loginInfo != null) {
			doLinkAll(loginInfo, msg);
			String username = loginInfo[0];
			String mac = loginInfo[4];
			String type = loginInfo[6];
			if (("1".equals(type)) && ("1".equals(config.getIsPortalCheck()))) {
				doLinkRecord(loginInfo, msg);
			}
			Boolean info = Boolean.valueOf(false);
			if (config.getBas().equals("3")) {
				if (stringUtils.isNotBlank(mac)) {
					String site = (String) UniFiMacSiteMap.getInstance().getMacSiteMap().get(mac);
					info = Boolean.valueOf(UniFiMethod.sendUnauthorization(mac, site, ubip));
				}
			} else if ((!config.getBas().equals("5")) && (!config.getBas().equals("6"))
					&& (!config.getBas().equals("7")) && (!config.getBas().equals("8")))
				info = InterfaceControl.Method("PORTAL_LOGINOUT", username, "password", uip, ubip, mac);
			else
				info = Boolean.valueOf(true);
			try {
				String time = loginInfo[3];
				Date loginTime = ThreadLocalDateUtil.parse(time);
				String nowString = ThreadLocalDateUtil.format(new Date());
				Date nowTime = ThreadLocalDateUtil.parse(nowString);
				Long costTime = Long.valueOf(nowTime.getTime() - loginTime.getTime());
				costTime = Long.valueOf(costTime.longValue() / 1000L / 60L);

				if (info.booleanValue()) {
					doLogRecord("IP: " + ip + " mac: " + mac + " 用户: " + username + " 上线时间: " + time + " 在线时长: "
							+ costTime + "分钟," + msg + "！");

					if (((Portalbas) configDefault.getConfigMap().get("")).getIsdebug().equals("1"))
						log.info("IP: " + ip + " mac: " + mac + " user: " + username + " , " + logInfo + "!");
				} else {
					doLogRecord("IP: " + ip + " mac: " + mac + " 用户: " + username + " 上线时间: " + time + " 在线时长: "
							+ costTime + "分钟," + msg + "???");

					if (((Portalbas) configDefault.getConfigMap().get("")).getIsdebug().equals("1"))
						log.info("IP: " + ip + " mac: " + mac + " user: " + username + " , " + logInfo + "???");
				}
			} catch (Exception localException1) {
			}
			CheckTimeUtils.recordTime(loginInfo);
			CheckAutoLoginUtils.recordTime(loginInfo);
			AjaxInterFaceController.SangforLogout(uip, username);
			if ((stringUtils.isNotBlank(mac)) && (!"8".equals(type))) {
				LateAuthMap.getInstance().getLateAuthMap().remove(mac);
			}
		}
		onlineMap.getOnlineUserMap().remove(ip);
		ipMap.getInstance().getIpmap().remove(uip);
	}

	public static void doLinkAll(String[] loginInfo, String info) {
		try {
			if (loginInfo != null) {
				String time = loginInfo[3];
				Date loginTime = new Date();
				String nowString = ThreadLocalDateUtil.format(new Date());
				Date nowTime = new Date();
				try {
					loginTime = ThreadLocalDateUtil.parse(time);
					nowTime = ThreadLocalDateUtil.parse(nowString);
				} catch (Exception localException1) {
				}
				Long costTime = Long.valueOf(nowTime.getTime() - loginTime.getTime());
				String state = null;
				try {
					String uid = loginInfo[1];
					if (stringUtils.isNotBlank(uid)) {
						Long userId = Long.valueOf(Long.parseLong(uid));
						if ((userId != null) && (userId.longValue() != 0L)) {
							Portalaccount account = portalaccountService.getPortalaccountByKey(userId);
							if (account != null)
								state = account.getState();
						}
					}
				} catch (Exception localException2) {
				}
				String type = loginInfo[6];
				if ("0".equals(type))
					type = "一键认证";
				else if ("1".equals(type))
					type = "本地用户";
				else if ("2".equals(type))
					type = "Radius";
				else if ("3".equals(type))
					type = "APP认证";
				else if ("4".equals(type))
					type = "短信认证";
				else if ("5".equals(type))
					type = "微信认证";
				else if ("6".equals(type))
					type = "公众号认证";
				else if ("7".equals(type))
					type = "访客认证";
				else if ("8".equals(type)) {
					type = "延迟认证";
				}
				String basname = loginInfo[11];
				String ssid = loginInfo[12];
				String apmac = loginInfo[13];
				String agent = loginInfo[15];
				if (stringUtils.isBlank(basname)) {
					basname = "base";
				}
				if (stringUtils.isBlank(ssid)) {
					ssid = "unknow";
				}
				if (stringUtils.isBlank(apmac)) {
					apmac = "unknow";
				}
				if (stringUtils.isBlank(agent)) {
					agent = "unknow";
				}
				if (stringUtils.isBlank(state)) {
					state = "-1";
				}
				Portallinkrecordall linkAll = new Portallinkrecordall();

				long in = 0L;
				long out = 0L;
				try {
					in = Long.valueOf(Long.valueOf(loginInfo[7]).longValue()).longValue();
					out = Long.valueOf(Long.valueOf(loginInfo[8]).longValue()).longValue();
				} catch (Exception localException3) {
				}
				long octets = in + out;

				linkAll.setAgent(agent);
				linkAll.setApmac(apmac);
				linkAll.setAuto(loginInfo[14]);
				linkAll.setBasip(loginInfo[10]);
				linkAll.setBasname(basname);
				linkAll.setEndDate(new Date());
				linkAll.setIns(Long.valueOf(in));
				linkAll.setIp(loginInfo[9]);
				linkAll.setLoginName(loginInfo[0]);
				linkAll.setMac(loginInfo[4]);
				linkAll.setOuts(Long.valueOf(out));
				linkAll.setSsid(ssid);
				linkAll.setStartDate(loginTime);
				linkAll.setState(state);
				linkAll.setTime(costTime);
				linkAll.setMethodtype(type);
				linkAll.setOctets(Long.valueOf(octets));
				linkAll.setEx1(info);
				linkAllService.addPortallinkrecordall(linkAll);
			}
		} catch (Exception e) {
			log.error("==============ERROR Start=============");
			log.error(e);
			log.error("ERROR INFO ", e);
			log.error("==============ERROR End=============");
		}
	}
}

/*
 * Location:
 * C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name: com.leeson.core.utils.Kick JD-Core Version: 0.6.2
 */