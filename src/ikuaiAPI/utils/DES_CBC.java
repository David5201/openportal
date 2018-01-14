package ikuaiAPI.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class DES_CBC
{
  public static byte[] encrypt(String message, String key, String ivStr)
    throws Exception
  {
    Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
    DESKeySpec desKeySpec = new DESKeySpec(key.getBytes());
    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
    SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
    IvParameterSpec iv = new IvParameterSpec(ivStr.getBytes());
    cipher.init(1, secretKey, iv);
    return cipher.doFinal(message.getBytes());
  }

  public static String decrypt(byte[] message, String key, String ivStr) throws Exception
  {
    Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
    DESKeySpec desKeySpec = new DESKeySpec(key.getBytes());
    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
    SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
    IvParameterSpec iv = new IvParameterSpec(ivStr.getBytes());
    cipher.init(2, secretKey, iv);
    return new String(cipher.doFinal(message));
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     ikuaiAPI.utils.DES_CBC
 * JD-Core Version:    0.6.2
 */