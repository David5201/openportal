package com.leeson.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class DiyUtils
{
  private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

  public static void main(String[] args) throws Exception
  {
  }

  public static String Zip(String root, String id) throws IOException
  {
    Date now = new Date();
    String nowString = format.format(now);
    String zipPath = root + "eb-" + nowString + ".zip";

    System.out.println("压缩中...");
    File zip = new File(zipPath);
    String path = "";
    File[] srcFiles = new File[1];
    File f0 = new File(root + "/" + id);
    srcFiles[0] = f0;
    ZipFiles(zip, path, srcFiles);
    System.out.println("压缩完毕");
    return zipPath;
  }

  public static String Zip(String root) throws IOException
  {
    Date now = new Date();
    String nowString = format.format(now);
    String zipPath = root + "eb-" + nowString + ".zip";

    System.out.println("压缩中...");
    File zip = new File(zipPath);
    String path = "";
    File[] srcFiles = new File[19];
    File f0 = new File(root + "/auth.jsp");
    File f1 = new File(root + "/ok.jsp");
    File f2 = new File(root + "/out.jsp");
    File f3 = new File(root + "/dist");
    File f4 = new File(root + "/weixin");
    File f5 = new File(root + "/wx.jsp");
    File f6 = new File(root + "/wxpc.jsp");
    File f7 = new File(root + "/APIauth.jsp");
    File f8 = new File(root + "/APIok.jsp");
    File f9 = new File(root + "/APIout.jsp");
    File f10 = new File(root + "/APIwx.jsp");
    File f11 = new File(root + "/APIwxpc.jsp");
    File f12 = new File(root + "/error.html");
    File f13 = new File(root + "/info.jsp");
    File f14 = new File(root + "/OL.jsp");
    File f15 = new File(root + "/wifidogAuth.jsp");
    File f16 = new File(root + "/wifidogOk.jsp");
    File f17 = new File(root + "/wifidogOut.jsp");
    File f18 = new File(root + "/wifidogWx.jsp");
    srcFiles[0] = f0;
    srcFiles[1] = f1;
    srcFiles[2] = f2;
    srcFiles[3] = f3;
    srcFiles[4] = f4;
    srcFiles[5] = f5;
    srcFiles[6] = f6;
    srcFiles[7] = f7;
    srcFiles[8] = f8;
    srcFiles[9] = f9;
    srcFiles[10] = f10;
    srcFiles[11] = f11;
    srcFiles[12] = f12;
    srcFiles[13] = f13;
    srcFiles[14] = f14;
    srcFiles[15] = f15;
    srcFiles[16] = f16;
    srcFiles[17] = f17;
    srcFiles[18] = f18;
    ZipFiles(zip, path, srcFiles);
    System.out.println("压缩完毕");
    return zipPath;
  }

  private static void ZipFiles(File zip, String path, File[] srcFiles)
    throws IOException
  {
    ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zip));
    ZipFiles(out, path, srcFiles);
    out.close();
  }

  private static void ZipFiles(ZipOutputStream out, String path, File[] srcFiles)
  {
    byte[] buf = new byte[1024];
    try {
      for (int i = 0; i < srcFiles.length; i++)
        if (srcFiles[i].isDirectory()) {
          File[] files = srcFiles[i].listFiles();
          String srcPath = srcFiles[i].getName();
          srcPath = srcPath.replaceAll("\\*", "/");
          if (!srcPath.endsWith("/")) {
            srcPath = srcPath + "/";
          }
          out.putNextEntry(new ZipEntry(path + srcPath));
          ZipFiles(out, path + srcPath, files);
        }
        else {
          FileInputStream in = new FileInputStream(srcFiles[i]);

          out.putNextEntry(new ZipEntry(path + srcFiles[i].getName()));
          int len;
          while ((len = in.read(buf)) > 0)
          {
            out.write(buf, 0, len);
          }
          out.closeEntry();
          in.close();
        }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void unZip(String descDir, String zipPath)
    throws IOException
  {
    descDir = descDir.replace("\\", "/");
    zipPath = zipPath.replace("\\", "/");

    System.out.println("解压中...");
    unZipFiles(new File(zipPath), descDir);
    System.out.println("解压完毕");
  }

  private static void unZipFiles(File zipFile, String descDir)
    throws IOException
  {
    File pathFile = new File(descDir);
    if (!pathFile.exists()) {
      pathFile.mkdirs();
    }
    ZipFile zip = new ZipFile(zipFile);
    for (Enumeration entries = zip.entries(); entries.hasMoreElements(); ) {
      ZipEntry entry = (ZipEntry)entries.nextElement();
      String zipEntryName = entry.getName();
      InputStream in = zip.getInputStream(entry);
      String outPath = (descDir + zipEntryName).replaceAll("\\*", "/");

      File file = new File(outPath.substring(0, outPath.lastIndexOf('/')));
      if (!file.exists()) {
        file.mkdirs();
      }

      if (!new File(outPath).isDirectory())
      {
        OutputStream out = new FileOutputStream(outPath);
        byte[] buf1 = new byte[1024];
        int len;
        while ((len = in.read(buf1)) > 0)
        {
          out.write(buf1, 0, len);
        }
        in.close();
        out.close();
      }
    }
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.common.utils.DiyUtils
 * JD-Core Version:    0.6.2
 */