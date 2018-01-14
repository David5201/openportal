/*     */ package com.leeson.common.utils;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.PrintStream;
/*     */ 
/*     */ public class MySQLUtils
/*     */ {
/*     */   public static boolean backup(String username, String password, String dbName, String backupPath)
/*     */   {
/*  19 */     boolean status = false;
/*  20 */     String sys = System.getProperty("os.name");
/*  21 */     System.out.println(sys);
/*     */ 
/*  23 */     String command = "";
/*  24 */     if (sys.startsWith("W")) {
/*  25 */       command = "cmd /c mysqldump -u " + username + " -p" + password + 
/*  26 */         " --default-character-set=utf8 " + dbName + ">" + 
/*  27 */         backupPath;
/*  28 */     } else if (sys.startsWith("L")) {
/*  29 */       command = "mysqldump -u " + username + " -p" + password + " " + 
/*  30 */         dbName + " -r " + backupPath;
/*     */     } else {
/*  32 */       System.out.println("操作系统属性获取失败！！");
/*  33 */       return false;
/*     */     }
/*     */     try {
/*  36 */       Process runtimeProcess = Runtime.getRuntime().exec(command);
/*  37 */       int processComplete = runtimeProcess.waitFor();
/*  38 */       if (processComplete == 0) {
/*  39 */         System.out.println("MySQLManager: Backup database Successfull");
/*  40 */         status = true;
/*     */       } else {
/*  42 */         System.out.println("MySQLManager: Backup database Failure!");
/*     */       }
/*     */     } catch (IOException ioe) {
/*  45 */       System.out.println("Exception IO");
/*  46 */       ioe.printStackTrace();
/*     */     } catch (Exception e) {
/*  48 */       System.out.println("Exception");
/*  49 */       e.printStackTrace();
/*     */     }
/*  51 */     return status;
/*     */   }
/*     */ 
/*     */   public static boolean restore(String username, String password, String dbName, String filePath)
/*     */   {
/*  56 */     boolean status = false;
/*  57 */     String sys = System.getProperty("os.name");
/*  58 */     System.out.println(sys);
/*     */ 
/*  60 */     String command = "";
/*  61 */     if (sys.startsWith("W")) {
/*  62 */       command = "cmd /c mysql -u " + username + " -p" + password + 
/*  63 */         " --default-character-set=utf8 " + dbName + "<" + 
/*  64 */         filePath;
/*     */       try {
/*  66 */         Process runtimeProcess = Runtime.getRuntime().exec(command);
/*  67 */         int processComplete = runtimeProcess.waitFor();
/*  68 */         if (processComplete == 0) {
/*  69 */           System.out
/*  70 */             .println("MySQLManager:Restore database Successfull");
/*  71 */           status = true;
/*     */         } else {
/*  73 */           System.out.println("MySQLManager:Restore database Failure");
/*     */         }
/*     */       } catch (IOException ioe) {
/*  76 */         System.out.println("Exception IO");
/*  77 */         ioe.printStackTrace();
/*     */       } catch (Exception e) {
/*  79 */         System.out.println("Exception");
/*  80 */         e.printStackTrace();
/*     */       }
/*  82 */       return status;
/*  83 */     }if (sys.startsWith("L")) {
/*  84 */       command = "mysql -u " + username + " -p" + password + " " + 
/*  85 */         dbName + " < " + filePath;
/*     */       try
/*     */       {
/*  89 */         Runtime runtime = Runtime.getRuntime();
/*  90 */         String[] commandL = { "/bin/bash", "-c", command };
/*  91 */         Process process = runtime.exec(commandL);
/*     */ 
/*  94 */         InputStreamReader in = new InputStreamReader(
/*  95 */           process.getInputStream());
/*  96 */         BufferedReader br = new BufferedReader(in);
/*  97 */         String line = null;
/*  98 */         while ((line = br.readLine()) != null) {
/*  99 */           System.out.println(line);
/*     */         }
/* 101 */         br.close();
/* 102 */         in.close();
/*     */ 
/* 105 */         InputStreamReader in2 = new InputStreamReader(
/* 106 */           process.getErrorStream());
/* 107 */         BufferedReader br2 = new BufferedReader(in2);
/* 108 */         String line2 = null;
/* 109 */         while ((line2 = br2.readLine()) != null) {
/* 110 */           System.out.println("=" + line2);
/*     */         }
/* 112 */         br2.close();
/* 113 */         in2.close();
/* 114 */         System.out.println("MySQLManager:Restore database Successfull");
/* 115 */         return true;
/*     */       } catch (Exception e) {
/* 117 */         System.out.println("MySQLManager:Restore database Failure");
/* 118 */         System.out.println(e);
/* 119 */         return false;
/*     */       }
/*     */     }
/*     */ 
/* 123 */     System.out.println("操作系统属性获取失败！！");
/* 124 */     return false;
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/* 130 */     backup("root", "iwsiqh", "openportalserver", "d:/update.dat");
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.common.utils.MySQLUtils
 * JD-Core Version:    0.6.2
 */