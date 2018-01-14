/*     */ package com.leeson.common.utils;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.Enumeration;
/*     */ import java.util.zip.ZipEntry;
/*     */ import java.util.zip.ZipFile;
/*     */ import java.util.zip.ZipOutputStream;
/*     */ 
/*     */ public class DiyUtils
/*     */ {
/*  14 */   private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
/*     */ 
/*     */   public static void main(String[] args) throws Exception
/*     */   {
/*     */   }
/*     */ 
/*     */   public static String Zip(String root, String id) throws IOException
/*     */   {
/*  22 */     Date now = new Date();
/*  23 */     String nowString = format.format(now);
/*  24 */     String zipPath = root + "/version/web-" + nowString + ".zip";
/*     */ 
/*  26 */     System.out.println("压缩中...");
/*  27 */     File zip = new File(zipPath);
/*  28 */     String path = "";
/*  29 */     File[] srcFiles = new File[1];
/*  30 */     File f0 = new File(root + "/" + id);
/*  31 */     srcFiles[0] = f0;
/*  32 */     ZipFiles(zip, path, srcFiles);
/*  33 */     System.out.println("压缩完毕");
/*  34 */     return zipPath;
/*     */   }
/*     */ 
/*     */   public static String Zip(String root) throws IOException
/*     */   {
/*  39 */     Date now = new Date();
/*  40 */     String nowString = format.format(now);
/*  41 */     String zipPath = root + "/version/web-" + nowString + ".zip";
/*     */ 
/*  43 */     System.out.println("压缩中...");
/*  44 */     File zip = new File(zipPath);
/*  45 */     String path = "";
/*  46 */     File[] srcFiles = new File[19];
/*  47 */     File f0 = new File(root + "/auth.jsp");
/*  48 */     File f1 = new File(root + "/ok.jsp");
/*  49 */     File f2 = new File(root + "/out.jsp");
/*  50 */     File f3 = new File(root + "/dist");
/*  51 */     File f4 = new File(root + "/weixin");
/*  52 */     File f5 = new File(root + "/wx.jsp");
/*  53 */     File f6 = new File(root + "/wxpc.jsp");
/*  54 */     File f7 = new File(root + "/APIauth.jsp");
/*  55 */     File f8 = new File(root + "/APIok.jsp");
/*  56 */     File f9 = new File(root + "/APIout.jsp");
/*  57 */     File f10 = new File(root + "/APIwx.jsp");
/*  58 */     File f11 = new File(root + "/APIwxpc.jsp");
/*  59 */     File f12 = new File(root + "/error.html");
/*  60 */     File f13 = new File(root + "/info.jsp");
/*  61 */     File f14 = new File(root + "/OL.jsp");
/*  62 */     File f15 = new File(root + "/wifidogAuth.jsp");
/*  63 */     File f16 = new File(root + "/wifidogOk.jsp");
/*  64 */     File f17 = new File(root + "/wifidogOut.jsp");
/*  65 */     File f18 = new File(root + "/wifidogWx.jsp");
/*  66 */     srcFiles[0] = f0;
/*  67 */     srcFiles[1] = f1;
/*  68 */     srcFiles[2] = f2;
/*  69 */     srcFiles[3] = f3;
/*  70 */     srcFiles[4] = f4;
/*  71 */     srcFiles[5] = f5;
/*  72 */     srcFiles[6] = f6;
/*  73 */     srcFiles[7] = f7;
/*  74 */     srcFiles[8] = f8;
/*  75 */     srcFiles[9] = f9;
/*  76 */     srcFiles[10] = f10;
/*  77 */     srcFiles[11] = f11;
/*  78 */     srcFiles[12] = f12;
/*  79 */     srcFiles[13] = f13;
/*  80 */     srcFiles[14] = f14;
/*  81 */     srcFiles[15] = f15;
/*  82 */     srcFiles[16] = f16;
/*  83 */     srcFiles[17] = f17;
/*  84 */     srcFiles[18] = f18;
/*  85 */     ZipFiles(zip, path, srcFiles);
/*  86 */     System.out.println("压缩完毕");
/*  87 */     return zipPath;
/*     */   }
/*     */ 
/*     */   private static void ZipFiles(File zip, String path, File[] srcFiles)
/*     */     throws IOException
/*     */   {
/*  99 */     ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zip));
/* 100 */     ZipFiles(out, path, srcFiles);
/* 101 */     out.close();
/*     */   }
/*     */ 
/*     */   private static void ZipFiles(ZipOutputStream out, String path, File[] srcFiles)
/*     */   {
/* 114 */     byte[] buf = new byte[1024];
/*     */     try {
/* 116 */       for (int i = 0; i < srcFiles.length; i++)
/* 117 */         if (srcFiles[i].isDirectory()) {
/* 118 */           File[] files = srcFiles[i].listFiles();
/* 119 */           String srcPath = srcFiles[i].getName();
/* 120 */           srcPath = srcPath.replaceAll("\\*", "/");
/* 121 */           if (!srcPath.endsWith("/")) {
/* 122 */             srcPath = srcPath + "/";
/*     */           }
/* 124 */           out.putNextEntry(new ZipEntry(path + srcPath));
/* 125 */           ZipFiles(out, path + srcPath, files);
/*     */         }
/*     */         else {
/* 128 */           FileInputStream in = new FileInputStream(srcFiles[i]);
/*     */ 
/* 130 */           out.putNextEntry(new ZipEntry(path + srcFiles[i].getName()));
/*     */           int len;
/* 132 */           while ((len = in.read(buf)) > 0)
/*     */           {
/* 133 */             out.write(buf, 0, len);
/*     */           }
/* 135 */           out.closeEntry();
/* 136 */           in.close();
/*     */         }
/*     */     }
/*     */     catch (Exception e) {
/* 140 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void unZip(String descDir, String zipPath)
/*     */     throws IOException
/*     */   {
/* 153 */     descDir = descDir.replace("\\", "/");
/* 154 */     zipPath = zipPath.replace("\\", "/");
/*     */ 
/* 156 */     System.out.println("解压中...");
/* 157 */     unZipFiles(new File(zipPath), descDir);
/* 158 */     System.out.println("解压完毕");
/*     */   }
/*     */ 
/*     */   private static void unZipFiles(File zipFile, String descDir)
/*     */     throws IOException
/*     */   {
/* 168 */     File pathFile = new File(descDir);
/* 169 */     if (!pathFile.exists()) {
/* 170 */       pathFile.mkdirs();
/*     */     }
/* 172 */     ZipFile zip = new ZipFile(zipFile);
/* 173 */     for (Enumeration entries = zip.entries(); entries.hasMoreElements(); ) {
/* 174 */       ZipEntry entry = (ZipEntry)entries.nextElement();
/* 175 */       String zipEntryName = entry.getName();
/* 176 */       InputStream in = zip.getInputStream(entry);
/* 177 */       String outPath = (descDir + zipEntryName).replaceAll("\\*", "/");
/*     */ 
/* 179 */       File file = new File(outPath.substring(0, outPath.lastIndexOf('/')));
/* 180 */       if (!file.exists()) {
/* 181 */         file.mkdirs();
/*     */       }
/*     */ 
/* 184 */       if (!new File(outPath).isDirectory())
/*     */       {
/* 190 */         OutputStream out = new FileOutputStream(outPath);
/* 191 */         byte[] buf1 = new byte[1024];
/*     */         int len;
/* 193 */         while ((len = in.read(buf1)) > 0)
/*     */         {
/* 194 */           out.write(buf1, 0, len);
/*     */         }
/* 196 */         in.close();
/* 197 */         out.close();
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.common.utils.DiyUtils
 * JD-Core Version:    0.6.2
 */