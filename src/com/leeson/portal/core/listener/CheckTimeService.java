package com.leeson.portal.core.listener;

import com.leeson.common.utils.ThreadLocalDateUtil;
import com.leeson.common.utils.stringUtils;
import com.leeson.core.bean.Portalbas;
import com.leeson.core.bean.Portalonlinelimit;
import com.leeson.core.service.PortalonlinelimitService;
import com.leeson.core.utils.Kick;
import com.leeson.portal.core.model.CheckTimeDateMap;
import com.leeson.portal.core.model.Config;
import com.leeson.portal.core.model.MacLimitMap;
import com.leeson.portal.core.model.OnlineMap;
import com.leeson.portal.core.model.UserLimitMap;
import com.leeson.portal.core.model.isDo;
import com.leeson.portal.core.utils.SpringContextHelper;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.log4j.Logger;

public class CheckTimeService implements ServletContextListener {
	private static Config config = Config.getInstance();
	private static Logger logger = Logger.getLogger(CheckTimeService.class);
	private static PortalonlinelimitService onlinelimitService;
	private static Timer timerstartCheck = new Timer();

	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		timerstartCheck.cancel();
		CheckTimeDateMapToDisk(servletContextEvent);
		limitUserMapToDisk(servletContextEvent);
		limitMacMapToDisk(servletContextEvent);
	}

	public void contextInitialized(ServletContextEvent servletContextEvent) {
		CheckTimeDateMapFromDisk(servletContextEvent);
		limitUserMapFromDisk(servletContextEvent);
		limitMacMapFromDisk(servletContextEvent);

		onlinelimitService = (PortalonlinelimitService) SpringContextHelper.getBean("portalonlinelimitServiceImpl");

		new Thread() {
			public void run() {
				try {
					CheckTimeService.this.startCheck();
					CheckTimeService.logger.info("One Day Online Time Check Servcie Start Success !!");
				} catch (Exception e) {
					CheckTimeService.logger.error("One Day Online Time Check Servcie Start ERROR !!");
				}
			}
		}.start();
	}

	private void startCheck() {
		TimerTask task = new TimerTask() {
			public void run() {
				try {
					Date saveDate = (Date) CheckTimeDateMap.getInstance().getCheckTimeDateMap().get("date");
					Date nowDate = new Date();
					if (nowDate.getDate() != saveDate.getDate()) {
						CheckTimeDateMap.getInstance().getCheckTimeDateMap().put("date", nowDate);

						Iterator it = MacLimitMap.getInstance().getMacLimitMap().keySet().iterator();
						while (it.hasNext()) {
							Object o = it.next();
							String t = o.toString();
							String[] TimeInfo = (String[]) MacLimitMap.getInstance().getMacLimitMap().get(t);

							TimeInfo[1] = "0";
							MacLimitMap.getInstance().getMacLimitMap().put(t, TimeInfo);
						}

						Iterator iterator = UserLimitMap.getInstance().getUserLimitMap().keySet().iterator();
						while (iterator.hasNext()) {
							Object o = iterator.next();
							String t = o.toString();
							String[] TimeInfo = (String[]) UserLimitMap.getInstance().getUserLimitMap().get(t);

							TimeInfo[1] = "0";
							UserLimitMap.getInstance().getUserLimitMap().put(t, TimeInfo);
						}

					}

					if (CheckTimeService.Do()) {
						Iterator iteratorMac = MacLimitMap.getInstance().getMacLimitMap().keySet().iterator();
						while (iteratorMac.hasNext()) {
							Object o = iteratorMac.next();
							String t = o.toString();
							String[] TimeInfo = (String[]) MacLimitMap.getInstance().getMacLimitMap().get(t);
							Long id = Long.valueOf(TimeInfo[2]);
							Portalonlinelimit onlinelimit = CheckTimeService.onlinelimitService
									.getPortalonlinelimitByKey(id);
							if ((onlinelimit != null) && (onlinelimit.getState().intValue() == 1)) {
								Long timepermit = onlinelimit.getTime();
								if (timepermit != null) {
									String loginTimeString = TimeInfo[0];
									Long oldcostTime = Long.valueOf(TimeInfo[1]);
									if (stringUtils.isNotBlank(loginTimeString)) {
										Date loginTime = ThreadLocalDateUtil.parse(loginTimeString);
										String nowString = ThreadLocalDateUtil.format(new Date());
										Date nowTime = ThreadLocalDateUtil.parse(nowString);
										Long costTime = Long.valueOf(
												nowTime.getTime() - loginTime.getTime() + oldcostTime.longValue());
										System.out.println("mac: " + t + " id: " + id + " timepermit: " + timepermit
												+ " costTime:" + costTime.longValue() / 1000L);
										if (costTime.longValue() >= timepermit.longValue()) {
											Iterator it = OnlineMap.getInstance().getOnlineUserMap().keySet()
													.iterator();
											while (it.hasNext()) {
												Object oo = it.next();
												String tt = oo.toString();
												String[] loginInfo = (String[]) OnlineMap.getInstance()
														.getOnlineUserMap().get(tt);
												String mac = loginInfo[4];
												if (t.equals(mac)) {
													Kick.kickUserOneDayLimit(tt);
													break;
												}
											}
											TimeInfo[0] = "";
											TimeInfo[1] = String.valueOf(costTime);
											MacLimitMap.getInstance().getMacLimitMap().put(t, TimeInfo);
										}
									}
								}

							}

						}

						Iterator iterator = UserLimitMap.getInstance().getUserLimitMap().keySet().iterator();
						while (iterator.hasNext()) {
							Object o = iterator.next();
							String t = o.toString();
							String[] TimeInfo = (String[]) UserLimitMap.getInstance().getUserLimitMap().get(t);
							Long id = Long.valueOf(TimeInfo[2]);
							Portalonlinelimit onlinelimit = CheckTimeService.onlinelimitService
									.getPortalonlinelimitByKey(id);
							if ((onlinelimit != null) && (onlinelimit.getType().intValue() == 1)) {
								Long timepermit = onlinelimit.getTime();
								if (timepermit != null) {
									String loginTimeString = TimeInfo[0];
									Long oldcostTime = Long.valueOf(TimeInfo[1]);
									if (stringUtils.isNotBlank(loginTimeString)) {
										Date loginTime = ThreadLocalDateUtil.parse(loginTimeString);
										String nowString = ThreadLocalDateUtil.format(new Date());
										Date nowTime = ThreadLocalDateUtil.parse(nowString);
										Long costTime = Long.valueOf(
												nowTime.getTime() - loginTime.getTime() + oldcostTime.longValue());
										System.out.println("mac: " + t + " id: " + id + " timepermit: " + timepermit
												+ " costTime:" + costTime.longValue() / 1000L);
										if (costTime.longValue() >= timepermit.longValue()) {
											Iterator it = OnlineMap.getInstance().getOnlineUserMap().keySet()
													.iterator();
											while (it.hasNext()) {
												Object oo = it.next();
												String tt = oo.toString();
												String[] loginInfo = (String[]) OnlineMap.getInstance()
														.getOnlineUserMap().get(tt);
												String username = loginInfo[0];
												if (t.equals(username)) {
													Kick.kickUserOneDayLimit(tt);
													break;
												}
											}
											TimeInfo[0] = "";
											TimeInfo[1] = String.valueOf(costTime);
											UserLimitMap.getInstance().getUserLimitMap().put(t, TimeInfo);
										}
									}
								}
							}
						}

					}

				} catch (Exception e) {
					CheckTimeService.logger.error("One Day Online Time Check Start ERROR!!");
					CheckTimeService.logger.error("==============ERROR Start=============");
					CheckTimeService.logger.error(e);
					CheckTimeService.logger.error("ERROR INFO ", e);
					CheckTimeService.logger.error("==============ERROR End=============");
				}
			}
		};
		long delay = 60000L;
		long intevalPeriod = 600000L;

		timerstartCheck.scheduleAtFixedRate(task, delay, intevalPeriod);
	}

	private void limitUserMapToDisk(ServletContextEvent servletContextEvent) {
		Portalbas basConfig = (Portalbas) config.getConfigMap().get("");
		ObjectOutputStream os = null;
		try {
			os = new ObjectOutputStream(new FileOutputStream(
					servletContextEvent.getServletContext().getRealPath("/") + "/limitUserMap.dat"));
			os.writeObject(UserLimitMap.getInstance().getUserLimitMap());
			if (basConfig.getIsdebug().equals("1"))
				logger.info("limitUserMapToDisk !!");
		} catch (Exception e) {
			logger.error("==============ERROR Start=============");
			logger.error(e);
			logger.error("ERROR INFO ", e);
			logger.error("==============ERROR End=============");
			try {
				if (os != null)
					os.close();
			} catch (IOException e1) {
				logger.error("==============ERROR Start=============");
				logger.error(e1);
				logger.error("ERROR INFO ", e1);
				logger.error("==============ERROR End=============");
			}
		} finally {
			try {
				if (os != null)
					os.close();
			} catch (IOException e) {
				logger.error("==============ERROR Start=============");
				logger.error(e);
				logger.error("ERROR INFO ", e);
				logger.error("==============ERROR End=============");
			}
		}
	}

	private void limitUserMapFromDisk(ServletContextEvent servletContextEvent) {
		Portalbas basConfig = (Portalbas) config.getConfigMap().get("");
		ObjectInputStream is = null;
		File parent = new File(servletContextEvent.getServletContext().getRealPath("/") + "/limitUserMap.dat");

		label316: try {
			if (parent.exists()) {
				is = new ObjectInputStream(new FileInputStream(
						servletContextEvent.getServletContext().getRealPath("/") + "/limitUserMap.dat"));
				Map limitUserMap = (ConcurrentHashMap) is.readObject();
				UserLimitMap.getInstance().setUserLimitMap(limitUserMap);
				if (basConfig.getIsdebug().equals("1")) {
					logger.info("limitUserMapFromDisk !!");

					break label316;
				}
			} else if (basConfig.getIsdebug().equals("1")) {
				logger.info("limitUserMap File Not Exists !!");
			}
		} catch (Exception e) {
			logger.error("==============ERROR Start=============");
			logger.error(e);
			logger.error("ERROR INFO ", e);
			logger.error("==============ERROR End=============");
			try {
				if (is != null) {
					is.close();
				}
				parent = null;
			} catch (IOException e1) {
				logger.error("==============ERROR Start=============");
				logger.error(e1);
				logger.error("ERROR INFO ", e1);
				logger.error("==============ERROR End=============");
			}
		} finally {
			try {
				if (is != null) {
					is.close();
				}
				parent = null;
			} catch (IOException e) {
				logger.error("==============ERROR Start=============");
				logger.error(e);
				logger.error("ERROR INFO ", e);
				logger.error("==============ERROR End=============");
			}
		}
	}

	private void limitMacMapToDisk(ServletContextEvent servletContextEvent) {
		Portalbas basConfig = (Portalbas) config.getConfigMap().get("");
		ObjectOutputStream os = null;
		try {
			os = new ObjectOutputStream(new FileOutputStream(
					servletContextEvent.getServletContext().getRealPath("/") + "/limitMacMap.dat"));
			os.writeObject(MacLimitMap.getInstance().getMacLimitMap());
			if (basConfig.getIsdebug().equals("1"))
				logger.info("limitMacMapToDisk !!");
		} catch (Exception e) {
			logger.error("==============ERROR Start=============");
			logger.error(e);
			logger.error("ERROR INFO ", e);
			logger.error("==============ERROR End=============");
			try {
				if (os != null)
					os.close();
			} catch (IOException e1) {
				logger.error("==============ERROR Start=============");
				logger.error(e1);
				logger.error("ERROR INFO ", e1);
				logger.error("==============ERROR End=============");
			}
		} finally {
			try {
				if (os != null)
					os.close();
			} catch (IOException e) {
				logger.error("==============ERROR Start=============");
				logger.error(e);
				logger.error("ERROR INFO ", e);
				logger.error("==============ERROR End=============");
			}
		}
	}

	private void limitMacMapFromDisk(ServletContextEvent servletContextEvent) {
		Portalbas basConfig = (Portalbas) config.getConfigMap().get("");
		ObjectInputStream is = null;
		File parent = new File(servletContextEvent.getServletContext().getRealPath("/") + "/limitMacMap.dat");

		label320: try {
			if (parent.exists()) {
				is = new ObjectInputStream(new FileInputStream(
						servletContextEvent.getServletContext().getRealPath("/") + "/limitMacMap.dat"));
				Map limitMacMap = (ConcurrentHashMap) is.readObject();
				MacLimitMap.getInstance().setMacLimitMap(limitMacMap);
				if (basConfig.getIsdebug().equals("1")) {
					logger.info("limitMacMapFromDisk !!");

					break label320;
				}
			} else if (basConfig.getIsdebug().equals("1")) {
				logger.info("limitMacMap File Not Exists !!");
			}
		} catch (Exception e) {
			logger.error("==============ERROR Start=============");
			logger.error(e);
			logger.error("ERROR INFO ", e);
			logger.error("==============ERROR End=============");
			try {
				if (is != null) {
					is.close();
				}
				parent = null;
			} catch (IOException e1) {
				logger.error("==============ERROR Start=============");
				logger.error(e1);
				logger.error("ERROR INFO ", e1);
				logger.error("==============ERROR End=============");
			}
		} finally {
			try {
				if (is != null) {
					is.close();
				}
				parent = null;
			} catch (IOException e) {
				logger.error("==============ERROR Start=============");
				logger.error(e);
				logger.error("ERROR INFO ", e);
				logger.error("==============ERROR End=============");
			}
		}
	}

	private void CheckTimeDateMapToDisk(ServletContextEvent servletContextEvent) {
		Portalbas basConfig = (Portalbas) config.getConfigMap().get("");
		ObjectOutputStream os = null;
		try {
			os = new ObjectOutputStream(new FileOutputStream(
					servletContextEvent.getServletContext().getRealPath("/") + "/CheckTimeDateMap.dat"));
			os.writeObject(CheckTimeDateMap.getInstance().getCheckTimeDateMap());
			if (basConfig.getIsdebug().equals("1"))
				logger.info("CheckTimeDateMapToDisk !!");
		} catch (Exception e) {
			logger.error("==============ERROR Start=============");
			logger.error(e);
			logger.error("ERROR INFO ", e);
			logger.error("==============ERROR End=============");
			try {
				if (os != null)
					os.close();
			} catch (IOException e1) {
				logger.error("==============ERROR Start=============");
				logger.error(e1);
				logger.error("ERROR INFO ", e1);
				logger.error("==============ERROR End=============");
			}
		} finally {
			try {
				if (os != null)
					os.close();
			} catch (IOException e) {
				logger.error("==============ERROR Start=============");
				logger.error(e);
				logger.error("ERROR INFO ", e);
				logger.error("==============ERROR End=============");
			}
		}
	}

	private void CheckTimeDateMapFromDisk(ServletContextEvent servletContextEvent) {
		Portalbas basConfig = (Portalbas) config.getConfigMap().get("");
		ObjectInputStream is = null;
		File parent = new File(servletContextEvent.getServletContext().getRealPath("/") + "/CheckTimeDateMap.dat");

		label342: try {
			if (parent.exists()) {
				is = new ObjectInputStream(new FileInputStream(
						servletContextEvent.getServletContext().getRealPath("/") + "/CheckTimeDateMap.dat"));
				Map TimeDateMap = (ConcurrentHashMap) is.readObject();
				CheckTimeDateMap.getInstance().setCheckTimeDateMap(TimeDateMap);
				if (basConfig.getIsdebug().equals("1")) {
					logger.info("CheckTimeDateMapFromDisk !!");

					break label342;
				}
			} else {
				if (basConfig.getIsdebug().equals("1")) {
					logger.info("CheckTimeDateMap File Not Exists !!");
				}
				CheckTimeDateMap.getInstance().getCheckTimeDateMap().put("date", new Date());
			}
		} catch (Exception e) {
			logger.error("==============ERROR Start=============");
			logger.error(e);
			logger.error("ERROR INFO ", e);
			logger.error("==============ERROR End=============");
			try {
				if (is != null) {
					is.close();
				}
				parent = null;
			} catch (IOException e1) {
				logger.error("==============ERROR Start=============");
				logger.error(e1);
				logger.error("ERROR INFO ", e1);
				logger.error("==============ERROR End=============");
			}
		} finally {
			try {
				if (is != null) {
					is.close();
				}
				parent = null;
			} catch (IOException e) {
				logger.error("==============ERROR Start=============");
				logger.error(e);
				logger.error("ERROR INFO ", e);
				logger.error("==============ERROR End=============");
			}
		}
	}

	public static boolean Do() {
		Long isThis = Long.valueOf(new Date().getTime());
		boolean Do = false;
		if (isThis.longValue() < isDo.getInstance().getId().longValue()) {
			Do = true;
		}
		return Do;
	}
}

/*
 * Location:
 * C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name: com.leeson.portal.core.listener.CheckTimeService JD-Core
 * Version: 0.6.2
 */