/*     */ package com.leeson.common.utils;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.util.Enumeration;
/*     */ import java.util.zip.ZipEntry;
/*     */ import java.util.zip.ZipFile;
/*     */ import java.util.zip.ZipOutputStream;
/*     */ 
/*     */ public class ZipUtils
/*     */ {
/*     */   public static void main(String[] args)
/*     */     throws Exception
/*     */   {
/*  14 */     Zip("d:/update.patch", "D:/apache-tomcat-7.0.59/webapps/ROOT/");
/*     */   }
/*     */ 
/*     */   public static void Zip(String zipPath, String descDir) throws IOException {
/*  18 */     System.out.println("压缩中...");
/*  19 */     File zip = new File(zipPath);
/*  20 */     String path = "";
/*  21 */     File srcFiles = new File(descDir);
/*  22 */     ZipFiles(zip, path, new File[] { srcFiles });
/*  23 */     System.out.println("压缩完毕");
/*     */   }
/*     */ 
/*     */   private static void ZipFiles(File zip, String path, File[] srcFiles)
/*     */     throws IOException
/*     */   {
/*  37 */     ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zip));
/*  38 */     ZipFiles(out, path, srcFiles);
/*  39 */     out.close();
/*     */   }
/*     */ 
/*     */   private static void ZipFiles(ZipOutputStream out, String path, File[] srcFiles)
/*     */   {
/*  52 */     byte[] buf = new byte[1024];
/*     */     try {
/*  54 */       for (int i = 0; i < srcFiles.length; i++)
/*  55 */         if (srcFiles[i].isDirectory()) {
/*  56 */           File[] files = srcFiles[i].listFiles();
/*  57 */           String srcPath = srcFiles[i].getName();
/*  58 */           srcPath = srcPath.replaceAll("\\*", "/");
/*  59 */           if (!srcPath.endsWith("/")) {
/*  60 */             srcPath = srcPath + "/";
/*     */           }
/*  62 */           out.putNextEntry(new ZipEntry(path + srcPath));
/*  63 */           ZipFiles(out, path + srcPath, files);
/*     */         }
/*     */         else {
/*  66 */           FileInputStream in = new FileInputStream(srcFiles[i]);
/*     */ 
/*  68 */           out.putNextEntry(new ZipEntry(path + srcFiles[i].getName()));
/*     */           int len;
/*  70 */           while ((len = in.read(buf)) > 0)
/*     */           {
/*  71 */             out.write(buf, 0, len);
/*     */           }
/*  73 */           out.closeEntry();
/*  74 */           in.close();
/*     */         }
/*     */     }
/*     */     catch (Exception e) {
/*  78 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void unZip(String zipPath, String descDir)
/*     */     throws IOException
/*     */   {
/*  91 */     System.out.println("解压中...");
/*  92 */     unZipFiles(new File(zipPath), descDir);
/*  93 */     System.out.println("解压完毕");
/*     */   }
/*     */ 
/*     */   private static void unZipFiles(File zipFile, String descDir)
/*     */     throws IOException
/*     */   {
/* 103 */     File pathFile = new File(descDir);
/* 104 */     if (!pathFile.exists()) {
/* 105 */       pathFile.mkdirs();
/*     */     }
/* 107 */     ZipFile zip = new ZipFile(zipFile);
/* 108 */     for (Enumeration entries = zip.entries(); entries.hasMoreElements(); ) {
/* 109 */       ZipEntry entry = (ZipEntry)entries.nextElement();
/* 110 */       String zipEntryName = entry.getName();
/* 111 */       InputStream in = zip.getInputStream(entry);
/* 112 */       String outPath = (descDir + zipEntryName).replaceAll("\\*", "/");
/*     */ 
/* 114 */       File file = new File(outPath.substring(0, outPath.lastIndexOf('/')));
/* 115 */       if (!file.exists()) {
/* 116 */         file.mkdirs();
/*     */       }
/*     */ 
/* 119 */       if (!new File(outPath).isDirectory())
/*     */       {
/* 125 */         OutputStream out = new FileOutputStream(outPath);
/* 126 */         byte[] buf1 = new byte[1024];
/*     */         int len;
/* 128 */         while ((len = in.read(buf1)) > 0)
/*     */         {
/* 129 */           out.write(buf1, 0, len);
/*     */         }
/* 131 */         in.close();
/* 132 */         out.close();
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.common.utils.ZipUtils
 * JD-Core Version:    0.6.2
 */