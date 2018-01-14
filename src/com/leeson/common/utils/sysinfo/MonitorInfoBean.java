/*    */ package com.leeson.common.utils.sysinfo;
/*    */ 
/*    */ public class MonitorInfoBean
/*    */ {
/*    */   private long totalMemory;
/*    */   private long freeMemory;
/*    */   private long maxMemory;
/*    */   private String osName;
/*    */   private long totalMemorySize;
/*    */   private long freePhysicalMemorySize;
/*    */   private long usedMemory;
/*    */   private int totalThread;
/*    */   private double cpuRatio;
/*    */   private double diskTotalSpace;
/*    */   private double diskFreeSpace;
/*    */ 
/*    */   public long getTotalMemory()
/*    */   {
/* 26 */     return this.totalMemory;
/*    */   }
/*    */   public void setTotalMemory(long totalMemory) {
/* 29 */     this.totalMemory = totalMemory;
/*    */   }
/*    */   public long getFreeMemory() {
/* 32 */     return this.freeMemory;
/*    */   }
/*    */   public void setFreeMemory(long freeMemory) {
/* 35 */     this.freeMemory = freeMemory;
/*    */   }
/*    */   public long getMaxMemory() {
/* 38 */     return this.maxMemory;
/*    */   }
/*    */   public void setMaxMemory(long maxMemory) {
/* 41 */     this.maxMemory = maxMemory;
/*    */   }
/*    */   public String getOsName() {
/* 44 */     return this.osName;
/*    */   }
/*    */   public void setOsName(String osName) {
/* 47 */     this.osName = osName;
/*    */   }
/*    */   public long getTotalMemorySize() {
/* 50 */     return this.totalMemorySize;
/*    */   }
/*    */   public void setTotalMemorySize(long totalMemorySize) {
/* 53 */     this.totalMemorySize = totalMemorySize;
/*    */   }
/*    */   public long getFreePhysicalMemorySize() {
/* 56 */     return this.freePhysicalMemorySize;
/*    */   }
/*    */   public void setFreePhysicalMemorySize(long freePhysicalMemorySize) {
/* 59 */     this.freePhysicalMemorySize = freePhysicalMemorySize;
/*    */   }
/*    */   public long getUsedMemory() {
/* 62 */     return this.usedMemory;
/*    */   }
/*    */   public void setUsedMemory(long usedMemory) {
/* 65 */     this.usedMemory = usedMemory;
/*    */   }
/*    */   public int getTotalThread() {
/* 68 */     return this.totalThread;
/*    */   }
/*    */   public void setTotalThread(int totalThread) {
/* 71 */     this.totalThread = totalThread;
/*    */   }
/*    */   public double getCpuRatio() {
/* 74 */     return this.cpuRatio;
/*    */   }
/*    */   public void setCpuRatio(double cpuRatio) {
/* 77 */     this.cpuRatio = cpuRatio;
/*    */   }
/*    */   public double getDiskTotalSpace() {
/* 80 */     return this.diskTotalSpace;
/*    */   }
/*    */   public void setDiskTotalSpace(double diskTotalSpace) {
/* 83 */     this.diskTotalSpace = diskTotalSpace;
/*    */   }
/*    */   public double getDiskFreeSpace() {
/* 86 */     return this.diskFreeSpace;
/*    */   }
/*    */   public void setDiskFreeSpace(double diskFreeSpace) {
/* 89 */     this.diskFreeSpace = diskFreeSpace;
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.common.utils.sysinfo.MonitorInfoBean
 * JD-Core Version:    0.6.2
 */