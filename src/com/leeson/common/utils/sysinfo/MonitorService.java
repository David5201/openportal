package com.leeson.common.utils.sysinfo;

import com.sun.management.OperatingSystemMXBean;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.management.ManagementFactory;
import java.util.StringTokenizer;
import org.apache.log4j.Logger;

public class MonitorService {
	private static Logger log = Logger.getLogger(MonitorService.class);
	private static final int CPUTIME = 30;
	private static final int PERCENT = 100;
	private static final int FAULTLENGTH = 10;
	private static String linuxVersion = "";

	public static MonitorInfoBean getMonitorInfoBean() {
		MonitorInfoBean infoBean = new MonitorInfoBean();
		try {
			int kb = 1024;

			long totalMemory = Runtime.getRuntime().totalMemory() / kb;

			long freeMemory = Runtime.getRuntime().freeMemory() / kb;

			long maxMemory = Runtime.getRuntime().maxMemory() / kb;
			OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

			String osName = System.getProperty("os.name");

			long totalMemorySize = osmxb.getTotalPhysicalMemorySize() / kb;

			long freePhysicalMemorySize = osmxb.getFreePhysicalMemorySize() / kb;

			long usedMemory = (osmxb.getTotalPhysicalMemorySize() - osmxb.getFreePhysicalMemorySize()) / kb;
			ThreadGroup parentThread = Thread.currentThread().getThreadGroup();
			/*
			 * for (parentThread; parentThread.getParent() != null;) {
			 * parentThread = parentThread.getParent();
			 */
			// 这段逻辑要核实
			while (parentThread.getParent() != null) {
				parentThread = parentThread.getParent();
			}

			int totalThread = parentThread.activeCount();
			double cpuRatio = 0.0D;
			if (osName.toLowerCase().startsWith("windows"))
				cpuRatio = getCpuRatioForWindows();
			else {
				cpuRatio = getCpuRateForLinux();
			}

			infoBean.setFreeMemory(freeMemory);
			infoBean.setFreePhysicalMemorySize(freePhysicalMemorySize);
			infoBean.setMaxMemory(maxMemory);
			infoBean.setOsName(osName);
			infoBean.setTotalMemory(totalMemory);
			infoBean.setTotalMemorySize(totalMemorySize);
			infoBean.setTotalThread(totalThread);
			infoBean.setUsedMemory(usedMemory);
			infoBean.setCpuRatio(cpuRatio);
			infoBean.setDiskTotalSpace(getDiskTotalSpace());
			infoBean.setDiskFreeSpace(getDiskFreeSpace());
		} catch (Exception e) {
			log.error("==============ERROR Start=============");
			log.error(e);
			log.error("ERROR INFO ", e);
			log.error("==============ERROR End=============");
		}
		return infoBean;
	}

	private static double getCpuRateForLinux() {
		InputStream is = null;
		InputStreamReader isr = null;
		BufferedReader brStat = null;
		StringTokenizer tokenStat = null;
		double cpu = 0.0D;
		try {
			Process process = Runtime.getRuntime().exec("top -b -n 1");
			is = process.getInputStream();
			isr = new InputStreamReader(is);
			brStat = new BufferedReader(isr);

			String line = brStat.readLine();
			while (line != null) {
				if (line.contains("java")) {
					tokenStat = new StringTokenizer(line);
					tokenStat.nextToken();
					tokenStat.nextToken();
					tokenStat.nextToken();
					tokenStat.nextToken();
					tokenStat.nextToken();
					tokenStat.nextToken();
					tokenStat.nextToken();
					tokenStat.nextToken();
					String cpuUsage = tokenStat.nextToken();
					double usage = new Double(cpuUsage).doubleValue();
					cpu += usage;
				}
				if (line.contains("mysqld")) {
					tokenStat = new StringTokenizer(line);
					tokenStat.nextToken();
					tokenStat.nextToken();
					tokenStat.nextToken();
					tokenStat.nextToken();
					tokenStat.nextToken();
					tokenStat.nextToken();
					tokenStat.nextToken();
					tokenStat.nextToken();
					String cpuUsage = tokenStat.nextToken();
					double usage = new Double(cpuUsage).doubleValue();
					cpu += usage;
				}
				if (line.contains("nginx")) {
					tokenStat = new StringTokenizer(line);
					tokenStat.nextToken();
					tokenStat.nextToken();
					tokenStat.nextToken();
					tokenStat.nextToken();
					tokenStat.nextToken();
					tokenStat.nextToken();
					tokenStat.nextToken();
					tokenStat.nextToken();
					String cpuUsage = tokenStat.nextToken();
					double usage = new Double(cpuUsage).doubleValue();
					cpu += usage;
				}
				line = brStat.readLine();
			}
			return Math.round(cpu);
		} catch (Exception e) {
			freeResource(is, isr, brStat);
			return 1.0D;
		} finally {
			freeResource(is, isr, brStat);
		}
	}

	private static double getCpuRateForLinuxOld() {
		InputStream is = null;
		InputStreamReader isr = null;
		BufferedReader brStat = null;
		StringTokenizer tokenStat = null;
		try {
			System.out.println("Get usage rate of CUP , linux version: " + linuxVersion);
			Process process = Runtime.getRuntime().exec("top -b -n 1");
			is = process.getInputStream();
			isr = new InputStreamReader(is);
			brStat = new BufferedReader(isr);
			double d;
			if (linuxVersion.equals("2.4")) {
				brStat.readLine();
				brStat.readLine();
				brStat.readLine();
				brStat.readLine();
				tokenStat = new StringTokenizer(brStat.readLine());
				tokenStat.nextToken();
				tokenStat.nextToken();
				String user = tokenStat.nextToken();
				tokenStat.nextToken();
				String system = tokenStat.nextToken();
				tokenStat.nextToken();
				String nice = tokenStat.nextToken();
				System.out.println(user + " , " + system + " , " + nice);
				user = user.substring(0, user.indexOf("%"));
				system = system.substring(0, system.indexOf("%"));
				nice = nice.substring(0, nice.indexOf("%"));
				float userUsage = new Float(user).floatValue();
				float systemUsage = new Float(system).floatValue();
				float niceUsage = new Float(nice).floatValue();
				return (userUsage + systemUsage + niceUsage) / 100.0F;
			}
			brStat.readLine();
			brStat.readLine();
			tokenStat = new StringTokenizer(brStat.readLine());
			tokenStat.nextToken();
			tokenStat.nextToken();
			tokenStat.nextToken();
			tokenStat.nextToken();
			tokenStat.nextToken();
			tokenStat.nextToken();
			tokenStat.nextToken();
			String cpuUsage = tokenStat.nextToken();
			System.out.println("CPU idle : " + cpuUsage);
			Float usage = new Float(cpuUsage.substring(0, cpuUsage.indexOf("%")));
			return 1.0F - usage.floatValue() / 100.0F;
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
			freeResource(is, isr, brStat);
			return 1.0D;
		} finally {
			freeResource(is, isr, brStat);
		}
	}

	private static void freeResource(InputStream is, InputStreamReader isr, BufferedReader br) {
		try {
			if (is != null)
				is.close();
			if (isr != null)
				isr.close();
			if (br != null)
				br.close();
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
		}
	}

	private static double getCpuRatioForWindows() {
		try {
			String procCmd = System.getenv("windir")
					+ "wbem//wmic.exe process get Caption,CommandLine,KernelModeTime,ReadOperationCount,ThreadCount,UserModeTime,WriteOperationCount";

			long[] c0 = readCpu(Runtime.getRuntime().exec(procCmd));
			Thread.sleep(30L);
			long[] c1 = readCpu(Runtime.getRuntime().exec(procCmd));
			if ((c0 != null) && (c1 != null)) {
				long idletime = c1[0] - c0[0];
				long busytime = c1[1] - c0[1];
				return Math.round(Double.valueOf(100L * busytime / (busytime + idletime)).doubleValue());
			}
			return 0.0D;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return 0.0D;
	}

	private static long[] readCpu(Process proc) {
		long[] retn = new long[2];
		try {
			proc.getOutputStream().close();
			InputStreamReader ir = new InputStreamReader(proc.getInputStream());
			LineNumberReader input = new LineNumberReader(ir);
			String line = input.readLine();
			if ((line == null) || (line.length() < 10)) {
				return null;
			}
			int capidx = line.indexOf("Caption");
			int cmdidx = line.indexOf("CommandLine");
			int rocidx = line.indexOf("ReadOperationCount");
			int umtidx = line.indexOf("UserModeTime");
			int kmtidx = line.indexOf("KernelModeTime");
			int wocidx = line.indexOf("WriteOperationCount");
			long idletime = 0L;
			long kneltime = 0L;
			long usertime = 0L;
			while ((line = input.readLine()) != null)
				if (line.length() >= wocidx) {
					String caption = Bytes.substring(line, capidx, cmdidx - 1).trim();
					String cmd = Bytes.substring(line, cmdidx, kmtidx - 1).trim();
					if (cmd.indexOf("wmic.exe") < 0) {
						String s1 = Bytes.substring(line, kmtidx, rocidx - 1).trim();
						String s2 = Bytes.substring(line, umtidx, wocidx - 1).trim();
						if ((caption.equals("System Idle Process")) || (caption.equals("System"))) {
							if (s1.length() > 0)
								idletime += Long.valueOf(s1).longValue();
							if (s2.length() > 0)
								idletime += Long.valueOf(s2).longValue();
						} else {
							if (s1.length() > 0)
								kneltime += Long.valueOf(s1).longValue();
							if (s2.length() > 0)
								usertime += Long.valueOf(s2).longValue();
						}
					}
				}
			retn[0] = idletime;
			retn[1] = (kneltime + usertime);
			return retn;
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				proc.getInputStream().close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	private static String getDiskInfo() {
		StringBuffer sb = new StringBuffer();
		File[] roots = File.listRoots();
		double PhysicalTotalSpace = 0.0D;
		double PhysicalFreeSpace = 0.0D;

		for (File file : roots) {
			long totalSpace = file.getTotalSpace();

			long usableSpace = file.getUsableSpace();
			if (totalSpace > 0L) {
				PhysicalTotalSpace = PhysicalTotalSpace + Math.round(totalSpace / 1073741824.0D * 100.0D / 100.0D);

				PhysicalFreeSpace = PhysicalFreeSpace + Math.round(usableSpace / 1073741824.0D * 100.0D / 100.0D);

				sb.append(file.getPath() + "(总计：");
				sb.append(Math.round(totalSpace / 1073741824.0D * 100.0D / 100.0D) + "GB  ");
				if (Math.round(usableSpace / 1073741824.0D * 100.0D / 100.0D) <= 1L)
					sb.append("剩余：" + Math.round(usableSpace / 1048576.0D * 100.0D / 100.0D) + "MB ");
				else {
					sb.append("剩余：" + Math.round(usableSpace / 1073741824.0D * 100.0D / 100.0D) + "GB ");
				}
				sb.append("已使用" + Math.round((totalSpace - usableSpace) / 1073741824.0D * 100.0D / 100.0D) + "GB) ");
			}
		}
		System.out.println(PhysicalFreeSpace + "GB /" + PhysicalTotalSpace + "GB");
		return sb.toString();
	}

	private static double getDiskTotalSpace() {
		File[] roots = File.listRoots();
		double PhysicalTotalSpace = 0.0D;
		File[] arrayOfFile1 = roots;
		int j = roots.length;
		for (int i = 0; i < j; i++) {
			File file = arrayOfFile1[i];
			long totalSpace = file.getTotalSpace();
			if (totalSpace > 0L) {
				PhysicalTotalSpace = PhysicalTotalSpace + Math.round(totalSpace / 1073741824.0D * 100.0D / 100.0D);
			}
		}
		return PhysicalTotalSpace;
	}

	private static double getDiskFreeSpace() {
		File[] roots = File.listRoots();
		double PhysicalFreeSpace = 0.0D;
		File[] arrayOfFile1 = roots;
		int j = roots.length;
		for (int i = 0; i < j; i++) {
			File file = arrayOfFile1[i];
			long usableSpace = file.getUsableSpace();
			if (usableSpace > 0L) {
				PhysicalFreeSpace = PhysicalFreeSpace + Math.round(usableSpace / 1073741824.0D * 100.0D / 100.0D);
			}
		}
		return PhysicalFreeSpace;
	}

	public static void main(String[] args) throws Exception {
	}
}

/*
 * Location:
 * C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name: com.leeson.common.utils.sysinfo.MonitorService JD-Core
 * Version: 0.6.2
 */