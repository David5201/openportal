package com.leeson.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class ZipUtils
{
  public static void main(String[] args)
    throws Exception
  {
	  Zip("d:/update.patch", "D:/apache-tomcat-7.0.59/webapps/ROOT/");
  }

  public static void Zip(String zipPath, String descDir) throws IOException {
    System.out.println("压缩中...");
    File zip = new File(zipPath);
    String path = "";
    File srcFiles = new File(descDir);
    ZipFiles(zip, path, new File[] { srcFiles });
    System.out.println("压缩完毕");
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

  public static void unZip(String zipPath, String descDir)
    throws IOException
  {
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
 * Qualified Name:     com.leeson.common.utils.ZipUtils
 * JD-Core Version:    0.6.2
 */