package com.leeson.core.utils;

import java.io.IOException;
import java.io.PrintStream;
import java.net.URLEncoder;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class DesUtil
{
  private static final String DES = "DES";
  private static final String CDES = "DES";
  public static final String DES_KEY = "wp63vxd9";

  public static String encrypt(String data, String key)
    throws Exception
  {
    byte[] bt = encrypt(data.getBytes("utf-8"), key.getBytes());
    String strs = new BASE64Encoder().encode(bt);
    return strs;
  }

  public static String decrypt(String data, String key)
    throws IOException, Exception
  {
    if (data == null)
      return null;
    BASE64Decoder decoder = new BASE64Decoder();
    byte[] buf = decoder.decodeBuffer(data);
    byte[] bt = decrypt(buf, key.getBytes());
    return new String(bt, "utf-8");
  }

  private static byte[] encrypt(byte[] data, byte[] key)
    throws Exception
  {
    SecureRandom sr = new SecureRandom();

    DESKeySpec dks = new DESKeySpec(key);

    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
    SecretKey securekey = keyFactory.generateSecret(dks);

    Cipher cipher = Cipher.getInstance("DES");

    cipher.init(1, securekey, sr);
    return cipher.doFinal(data);
  }

  private static byte[] decrypt(byte[] data, byte[] key)
    throws Exception
  {
    SecureRandom sr = new SecureRandom();

    DESKeySpec dks = new DESKeySpec(key);

    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
    SecretKey securekey = keyFactory.generateSecret(dks);

    Cipher cipher = Cipher.getInstance("DES");

    cipher.init(2, securekey, sr);
    return cipher.doFinal(data);
  }

  public static void main(String[] args) throws Exception {
    String str = encrypt("15850503315", "wifi_njqxq");
    System.out.println(str);
    System.out.println(URLEncoder.encode(str, "utf-8"));
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.utils.DesUtil
 * JD-Core Version:    0.6.2
 */