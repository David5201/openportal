package com.leeson.portal.core.utils;

import com.leeson.common.utils.stringUtils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.NetworkInterface;
import java.util.Enumeration;
import org.apache.commons.codec.digest.DigestUtils;

public class fI8m9X5dVXZEBo6Js
{
  private static String cHZVOWoHedyv()
  {
    StringBuilder M2MzQ0NTM3NDEzNjM4MzQzN = new StringBuilder();
    try {
      Enumeration el = 
        NetworkInterface.getNetworkInterfaces();
      while (el.hasMoreElements()) {
        byte[] mac = ((NetworkInterface)el.nextElement()).getHardwareAddress();
        if (mac != null)
        {
          StringBuilder builder = new StringBuilder();
          for (int i = 0; i < mac.length; i++) {
            builder.append(hexByte(mac[i]));
          }
          M2MzQ0NTM3NDEzNjM4MzQzN.append(builder);
        }
      }
    } catch (Exception localException) {
    }
    String result = M2MzQ0NTM3NDEzNjM4MzQzN.toString();
    result = result.toUpperCase();
    result = result.replace("00000000000000E0", "");
    return "G" + result;
  }

  private static String hexByte(byte b) {
    String s = "000000" + Integer.toHexString(b);
    return s.substring(s.length() - 2);
  }

  private static String XTRuxvYEzmYNhYQMW() {
    String result = "";
    try {
      File file = File.createTempFile("tmp", ".vbs");
      file.deleteOnExit();
      FileWriter fw = new FileWriter(file);
      String vbs = "Set objWMIService = GetObject(\"winmgmts:\\\\.\\root\\cimv2\")\nSet colItems = objWMIService.ExecQuery _ \n   (\"Select * from Win32_Processor\") \nFor Each objItem in colItems \n    Wscript.Echo objItem.ProcessorId \n    exit for  ' do the first cpu only! \nNext \n";

      fw.write(vbs);
      fw.close();
      Process p = Runtime.getRuntime().exec(
        "cscript //NoLogo " + file.getPath());
      BufferedReader input = new BufferedReader(new InputStreamReader(
        p.getInputStream()));
      String line;
      while ((line = input.readLine()) != null)
      {
        result = result + line;
      }
      input.close();
      file.delete();
    }
    catch (Exception localException) {
    }
    if ((result.trim().length() < 1) || (result == null)) {
      result = "E";
    }
    return result.trim();
  }

  private static String zITsztQAqoO4Ikggk() {
    String result = "";
    try {
      Process p = Runtime.getRuntime().exec("dmidecode -t processor | grep ID");
      BufferedReader input = new BufferedReader(new InputStreamReader(
        p.getInputStream()));
      String line;
      while ((line = input.readLine()) != null)
      {
        result = result + line;
      }
      input.close();

      if ((result.trim().length() < 1) || (result == null) || (!result.contains("ID:"))) {
        return "E";
      }
      String[] infos = result.split("ID:");
      result = infos[1];
      result = result.trim();
      result = result.replace(" ", "");
      if (result.length() >= 16) {
        result = result.substring(0, 16);
      }

      return result.trim();
    } catch (Exception e) {
    }
    return "E";
  }

  private static String S3VX4Nrex1t5VqAQ2()
  {
    String RTc5NEI1RTU5NTg2MjYyNj = System.getProperty("os.name");
    if (RTc5NEI1RTU5NTg2MjYyNj.startsWith("W"))
      return "WG" + XTRuxvYEzmYNhYQMW();
    if (RTc5NEI1RTU5NTg2MjYyNj.startsWith("L")) {
      return "LG" + zITsztQAqoO4Ikggk();
    }
    return "EG";
  }

  public static String pV3Y5xivmveI277H6QS87V()
  {
    try {
      StringBuilder RTRCOEFERTU5QkJERTZCMkIzRTU4Qz = new StringBuilder();
      RTRCOEFERTU5QkJERTZCMkIzRTU4Qz.append(S3VX4Nrex1t5VqAQ2());
      RTRCOEFERTU5QkJERTZCMkIzRTU4Qz.append(cHZVOWoHedyv());
      String k8KVEI3aa5bvOQ6W = RTRCOEFERTU5QkJERTZCMkIzRTU4Qz.toString();
      String KMyPAGX5Qnk8R7 = AaHpl8Ha9bNPen9OLddV.encryptBASE64(k8KVEI3aa5bvOQ6W);

      StringBuilder c5NEI1RTU5NTg = new StringBuilder();
      c5NEI1RTU5NTg.append(DigestUtils.md5Hex(k8KVEI3aa5bvOQ6W).toUpperCase());
      c5NEI1RTU5NTg.append(AaHpl8Ha9bNPen9OLddV.encode(KMyPAGX5Qnk8R7));
      c5NEI1RTU5NTg.append(DigestUtils.md5Hex(KMyPAGX5Qnk8R7).toUpperCase());

      String zUzNDM2NDMzNDM2MzQ0NT = "";
      if (stringUtils.isBlank(KMyPAGX5Qnk8R7)) {
        zUzNDM2NDMzNDM2MzQ0NT = "E";
      }
      return c5NEI1RTU5NTg.toString();
    }
    catch (Exception e) {
    }
    return "E";
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.utils.fI8m9X5dVXZEBo6Js
 * JD-Core Version:    0.6.2
 */