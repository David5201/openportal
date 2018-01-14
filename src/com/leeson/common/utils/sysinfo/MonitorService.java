/*     */ package com.leeson.common.utils.sysinfo;
/*     */ 
/*     */ import com.sun.management.OperatingSystemMXBean;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.LineNumberReader;
/*     */ import java.io.OutputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.lang.management.ManagementFactory;
/*     */ import java.util.StringTokenizer;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class MonitorService
/*     */ {
/*  23 */   private static Logger log = Logger.getLogger(MonitorService.class);
/*     */   private static final int CPUTIME = 30;
/*     */   private static final int PERCENT = 100;
/*     */   private static final int FAULTLENGTH = 10;
/*  27 */   private static String linuxVersion = "";
/*     */ 
/*     */   public static MonitorInfoBean getMonitorInfoBean()
/*     */   {
/*  37 */     MonitorInfoBean infoBean = new MonitorInfoBean();
/*     */     try {
/*  39 */       int kb = 1024;
/*     */ 
/*  41 */       long totalMemory = Runtime.getRuntime().totalMemory() / kb;
/*     */ 
/*  43 */       long freeMemory = Runtime.getRuntime().freeMemory() / kb;
/*     */ 
/*  45 */       long maxMemory = Runtime.getRuntime().maxMemory() / kb;
/*  46 */       OperatingSystemMXBean osmxb = (OperatingSystemMXBean)
/*  47 */         ManagementFactory.getOperatingSystemMXBean();
/*     */ 
/*  49 */       String osName = System.getProperty("os.name");
/*     */ 
/*  51 */       long totalMemorySize = osmxb.getTotalPhysicalMemorySize() / kb;
/*     */ 
/*  53 */       long freePhysicalMemorySize = osmxb.getFreePhysicalMemorySize() / 
/*  54 */         kb;
/*     */ 
/*  56 */       long usedMemory = (osmxb.getTotalPhysicalMemorySize() - osmxb
/*  57 */         .getFreePhysicalMemorySize()) / kb;
/*     */ 		ThreadGroup parentThread = Thread.currentThread().getThreadGroup();
///*  60 */       for (parentThread; parentThread.getParent() != null;) {
///*  61 */         parentThread = parentThread.getParent();
///*     */       }//这段逻辑要核实
				while(parentThread.getParent() != null){
				  parentThread = parentThread.getParent();
				}

/*  64 */       int totalThread = parentThread.activeCount();
/*  65 */       double cpuRatio = 0.0D;
/*  66 */       if (osName.toLowerCase().startsWith("windows"))
/*  67 */         cpuRatio = getCpuRatioForWindows();
/*     */       else {
/*  69 */         cpuRatio = getCpuRateForLinux();
/*     */       }
/*     */ 
/*  72 */       infoBean.setFreeMemory(freeMemory);
/*  73 */       infoBean.setFreePhysicalMemorySize(freePhysicalMemorySize);
/*  74 */       infoBean.setMaxMemory(maxMemory);
/*  75 */       infoBean.setOsName(osName);
/*  76 */       infoBean.setTotalMemory(totalMemory);
/*  77 */       infoBean.setTotalMemorySize(totalMemorySize);
/*  78 */       infoBean.setTotalThread(totalThread);
/*  79 */       infoBean.setUsedMemory(usedMemory);
/*  80 */       infoBean.setCpuRatio(cpuRatio);
/*  81 */       infoBean.setDiskTotalSpace(getDiskTotalSpace());
/*  82 */       infoBean.setDiskFreeSpace(getDiskFreeSpace());
/*     */     } catch (Exception e) {
/*  84 */       log.error("==============ERROR Start=============");
/*  85 */       log.error(e);
/*  86 */       log.error("ERROR INFO ", e);
/*  87 */       log.error("==============ERROR End=============");
/*     */     }
/*  89 */     return infoBean;
/*     */   }
/*     */ 
/*     */   private static double getCpuRateForLinux() {
/*  93 */     InputStream is = null;
/*  94 */     InputStreamReader isr = null;
/*  95 */     BufferedReader brStat = null;
/*  96 */     StringTokenizer tokenStat = null;
/*  97 */     double cpu = 0.0D;
/*     */     try {
/*  99 */       Process process = Runtime.getRuntime().exec("top -b -n 1");
/* 100 */       is = process.getInputStream();
/* 101 */       isr = new InputStreamReader(is);
/* 102 */       brStat = new BufferedReader(isr);
/*     */ 
/* 104 */       String line = brStat.readLine();
/* 105 */       while (line != null) {
/* 106 */         if (line.contains("java")) {
/* 107 */           tokenStat = new StringTokenizer(line);
/* 108 */           tokenStat.nextToken();
/* 109 */           tokenStat.nextToken();
/* 110 */           tokenStat.nextToken();
/* 111 */           tokenStat.nextToken();
/* 112 */           tokenStat.nextToken();
/* 113 */           tokenStat.nextToken();
/* 114 */           tokenStat.nextToken();
/* 115 */           tokenStat.nextToken();
/* 116 */           String cpuUsage = tokenStat.nextToken();
/* 117 */           double usage = new Double(cpuUsage).doubleValue();
/* 118 */           cpu += usage;
/*     */         }
/* 120 */         if (line.contains("mysqld")) {
/* 121 */           tokenStat = new StringTokenizer(line);
/* 122 */           tokenStat.nextToken();
/* 123 */           tokenStat.nextToken();
/* 124 */           tokenStat.nextToken();
/* 125 */           tokenStat.nextToken();
/* 126 */           tokenStat.nextToken();
/* 127 */           tokenStat.nextToken();
/* 128 */           tokenStat.nextToken();
/* 129 */           tokenStat.nextToken();
/* 130 */           String cpuUsage = tokenStat.nextToken();
/* 131 */           double usage = new Double(cpuUsage).doubleValue();
/* 132 */           cpu += usage;
/*     */         }
/* 134 */         if (line.contains("nginx")) {
/* 135 */           tokenStat = new StringTokenizer(line);
/* 136 */           tokenStat.nextToken();
/* 137 */           tokenStat.nextToken();
/* 138 */           tokenStat.nextToken();
/* 139 */           tokenStat.nextToken();
/* 140 */           tokenStat.nextToken();
/* 141 */           tokenStat.nextToken();
/* 142 */           tokenStat.nextToken();
/* 143 */           tokenStat.nextToken();
/* 144 */           String cpuUsage = tokenStat.nextToken();
/* 145 */           double usage = new Double(cpuUsage).doubleValue();
/* 146 */           cpu += usage;
/*     */         }
/* 148 */         line = brStat.readLine();
/*     */       }
/* 150 */       return Math.round(cpu);
/*     */     } catch (Exception e) {
/* 152 */       freeResource(is, isr, brStat);
/* 153 */       return 1.0D;
/*     */     } finally {
/* 155 */       freeResource(is, isr, brStat);
/*     */     }
/*     */   }
/*     */ 
/*     */   private static double getCpuRateForLinuxOld() {
/* 160 */     InputStream is = null;
/* 161 */     InputStreamReader isr = null;
/* 162 */     BufferedReader brStat = null;
/* 163 */     StringTokenizer tokenStat = null;
/*     */     try {
/* 165 */       System.out.println("Get usage rate of CUP , linux version: " + 
/* 166 */         linuxVersion);
/* 167 */       Process process = Runtime.getRuntime().exec("top -b -n 1");
/* 168 */       is = process.getInputStream();
/* 169 */       isr = new InputStreamReader(is);
/* 170 */       brStat = new BufferedReader(isr);
/*     */       double d;
/* 171 */       if (linuxVersion.equals("2.4")) {
/* 172 */         brStat.readLine();
/* 173 */         brStat.readLine();
/* 174 */         brStat.readLine();
/* 175 */         brStat.readLine();
/* 176 */         tokenStat = new StringTokenizer(brStat.readLine());
/* 177 */         tokenStat.nextToken();
/* 178 */         tokenStat.nextToken();
/* 179 */         String user = tokenStat.nextToken();
/* 180 */         tokenStat.nextToken();
/* 181 */         String system = tokenStat.nextToken();
/* 182 */         tokenStat.nextToken();
/* 183 */         String nice = tokenStat.nextToken();
/* 184 */         System.out.println(user + " , " + system + " , " + nice);
/* 185 */         user = user.substring(0, user.indexOf("%"));
/* 186 */         system = system.substring(0, system.indexOf("%"));
/* 187 */         nice = nice.substring(0, nice.indexOf("%"));
/* 188 */         float userUsage = new Float(user).floatValue();
/* 189 */         float systemUsage = new Float(system).floatValue();
/* 190 */         float niceUsage = new Float(nice).floatValue();
/* 191 */         return (userUsage + systemUsage + niceUsage) / 100.0F;
/*     */       }
/* 193 */       brStat.readLine();
/* 194 */       brStat.readLine();
/* 195 */       tokenStat = new StringTokenizer(brStat.readLine());
/* 196 */       tokenStat.nextToken();
/* 197 */       tokenStat.nextToken();
/* 198 */       tokenStat.nextToken();
/* 199 */       tokenStat.nextToken();
/* 200 */       tokenStat.nextToken();
/* 201 */       tokenStat.nextToken();
/* 202 */       tokenStat.nextToken();
/* 203 */       String cpuUsage = tokenStat.nextToken();
/* 204 */       System.out.println("CPU idle : " + cpuUsage);
/* 205 */       Float usage = new Float(cpuUsage.substring(0, 
/* 206 */         cpuUsage.indexOf("%")));
/* 207 */       return 1.0F - usage.floatValue() / 100.0F;
/*     */     }
/*     */     catch (IOException ioe) {
/* 210 */       System.out.println(ioe.getMessage());
/* 211 */       freeResource(is, isr, brStat);
/* 212 */       return 1.0D;
/*     */     } finally {
/* 214 */       freeResource(is, isr, brStat);
/*     */     }
/*     */   }
/*     */ 
/*     */   private static void freeResource(InputStream is, InputStreamReader isr, BufferedReader br)
/*     */   {
/*     */     try {
/* 221 */       if (is != null)
/* 222 */         is.close();
/* 223 */       if (isr != null)
/* 224 */         isr.close();
/* 225 */       if (br != null)
/* 226 */         br.close();
/*     */     } catch (IOException ioe) {
/* 228 */       System.out.println(ioe.getMessage());
/*     */     }
/*     */   }
/*     */ 
/*     */   private static double getCpuRatioForWindows()
/*     */   {
/*     */     try
/*     */     {
/* 240 */       String procCmd = System.getenv("windir") + 
/* 241 */         "//system32//wbem//wmic.exe process get Caption,CommandLine,KernelModeTime,ReadOperationCount,ThreadCount,UserModeTime,WriteOperationCount";
/*     */ 
/* 243 */       long[] c0 = readCpu(Runtime.getRuntime().exec(procCmd));
/* 244 */       Thread.sleep(30L);
/* 245 */       long[] c1 = readCpu(Runtime.getRuntime().exec(procCmd));
/* 246 */       if ((c0 != null) && (c1 != null)) {
/* 247 */         long idletime = c1[0] - c0[0];
/* 248 */         long busytime = c1[1] - c0[1];
/* 249 */         return Math.round(Double.valueOf(
/* 250 */           100L * busytime / (busytime + idletime))
/* 251 */           .doubleValue());
/*     */       }
/* 253 */       return 0.0D;
/*     */     }
/*     */     catch (Exception ex) {
/* 256 */       ex.printStackTrace();
/* 257 */     }return 0.0D;
/*     */   }
/*     */ 
/*     */   private static long[] readCpu(Process proc)
/*     */   {
/* 269 */     long[] retn = new long[2];
/*     */     try {
/* 271 */       proc.getOutputStream().close();
/* 272 */       InputStreamReader ir = new InputStreamReader(proc.getInputStream());
/* 273 */       LineNumberReader input = new LineNumberReader(ir);
/* 274 */       String line = input.readLine();
/* 275 */       if ((line == null) || (line.length() < 10)) {
/* 276 */         return null;
/*     */       }
/* 278 */       int capidx = line.indexOf("Caption");
/* 279 */       int cmdidx = line.indexOf("CommandLine");
/* 280 */       int rocidx = line.indexOf("ReadOperationCount");
/* 281 */       int umtidx = line.indexOf("UserModeTime");
/* 282 */       int kmtidx = line.indexOf("KernelModeTime");
/* 283 */       int wocidx = line.indexOf("WriteOperationCount");
/* 284 */       long idletime = 0L;
/* 285 */       long kneltime = 0L;
/* 286 */       long usertime = 0L;
/* 287 */       while ((line = input.readLine()) != null)
/* 288 */         if (line.length() >= wocidx)
/*     */         {
/* 293 */           String caption = Bytes.substring(line, capidx, cmdidx - 1)
/* 294 */             .trim();
/* 295 */           String cmd = Bytes.substring(line, cmdidx, kmtidx - 1).trim();
/* 296 */           if (cmd.indexOf("wmic.exe") < 0)
/*     */           {
/* 299 */             String s1 = Bytes.substring(line, kmtidx, rocidx - 1).trim();
/* 300 */             String s2 = Bytes.substring(line, umtidx, wocidx - 1).trim();
/* 301 */             if ((caption.equals("System Idle Process")) || 
/* 302 */               (caption.equals("System"))) {
/* 303 */               if (s1.length() > 0)
/* 304 */                 idletime += Long.valueOf(s1).longValue();
/* 305 */               if (s2.length() > 0)
/* 306 */                 idletime += Long.valueOf(s2).longValue();
/*     */             }
/*     */             else {
/* 309 */               if (s1.length() > 0)
/* 310 */                 kneltime += Long.valueOf(s1).longValue();
/* 311 */               if (s2.length() > 0)
/* 312 */                 usertime += Long.valueOf(s2).longValue(); 
/*     */             }
/*     */           }
/*     */         }
/* 314 */       retn[0] = idletime;
/* 315 */       retn[1] = (kneltime + usertime);
/* 316 */       return retn;
/*     */     } catch (Exception ex) {
/* 318 */       ex.printStackTrace();
/*     */     } finally {
/*     */       try {
/* 321 */         proc.getInputStream().close();
/*     */       } catch (Exception e) {
/* 323 */         e.printStackTrace();
/*     */       }
/*     */     }
/* 326 */     return null;
/*     */   }
/*     */ 
/*     */   private static String getDiskInfo() {
/* 330 */     StringBuffer sb = new StringBuffer();
/* 331 */     File[] roots = File.listRoots();
/* 332 */     double PhysicalTotalSpace = 0.0D;
/* 333 */     double PhysicalFreeSpace = 0.0D;
/*     */ 
/* 335 */     for (File file : roots) {
/* 336 */       long totalSpace = file.getTotalSpace();
/*     */ 
/* 338 */       long usableSpace = file.getUsableSpace();
/* 339 */       if (totalSpace > 0L)
/*     */       {
/* 341 */         PhysicalTotalSpace = PhysicalTotalSpace + 
/* 341 */           Math.round(totalSpace / 1073741824.0D * 100.0D / 100.0D);
/*     */ 
/* 343 */         PhysicalFreeSpace = PhysicalFreeSpace + 
/* 343 */           Math.round(usableSpace / 1073741824.0D * 100.0D / 100.0D);
/*     */ 
/* 345 */         sb.append(file.getPath() + "(总计：");
/* 346 */         sb.append(
/* 347 */           Math.round(totalSpace / 1073741824.0D * 100.0D / 100.0D) + 
/* 348 */           "GB  ");
/* 349 */         if (Math.round(usableSpace / 1073741824.0D * 100.0D / 100.0D) <= 1L)
/* 350 */           sb.append("剩余：" + 
/* 351 */             Math.round(usableSpace / 1048576.0D * 100.0D / 100.0D) + 
/* 352 */             "MB ");
/*     */         else {
/* 354 */           sb.append("剩余：" + 
/* 355 */             Math.round(usableSpace / 1073741824.0D * 100.0D / 100.0D) + 
/* 356 */             "GB ");
/*     */         }
/* 358 */         sb.append("已使用" + 
/* 359 */           Math.round((totalSpace - usableSpace) / 1073741824.0D * 100.0D / 100.0D) + 
/* 360 */           "GB) ");
/*     */       }
/*     */     }
/* 363 */     System.out.println(PhysicalFreeSpace + "GB /" + PhysicalTotalSpace + 
/* 364 */       "GB");
/* 365 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   private static double getDiskTotalSpace() {
/* 369 */     File[] roots = File.listRoots();
/* 370 */     double PhysicalTotalSpace = 0.0D;
/* 371 */     File[] arrayOfFile1 = roots; int j = roots.length; for (int i = 0; i < j; i++) { File file = arrayOfFile1[i];
/* 372 */       long totalSpace = file.getTotalSpace();
/* 373 */       if (totalSpace > 0L)
/*     */       {
/* 375 */         PhysicalTotalSpace = PhysicalTotalSpace + 
/* 375 */           Math.round(totalSpace / 1073741824.0D * 100.0D / 100.0D);
/*     */       }
/*     */     }
/* 378 */     return PhysicalTotalSpace;
/*     */   }
/*     */ 
/*     */   private static double getDiskFreeSpace() {
/* 382 */     File[] roots = File.listRoots();
/* 383 */     double PhysicalFreeSpace = 0.0D;
/* 384 */     File[] arrayOfFile1 = roots; int j = roots.length; for (int i = 0; i < j; i++) { File file = arrayOfFile1[i];
/* 385 */       long usableSpace = file.getUsableSpace();
/* 386 */       if (usableSpace > 0L)
/*     */       {
/* 388 */         PhysicalFreeSpace = PhysicalFreeSpace + 
/* 388 */           Math.round(usableSpace / 1073741824.0D * 100.0D / 100.0D);
/*     */       }
/*     */     }
/* 391 */     return PhysicalFreeSpace;
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */     throws Exception
/*     */   {
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.common.utils.sysinfo.MonitorService
 * JD-Core Version:    0.6.2
 */