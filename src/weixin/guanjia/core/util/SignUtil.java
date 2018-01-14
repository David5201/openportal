package weixin.guanjia.core.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class SignUtil
{
  public static boolean checkSignature(String token, String signature, String timestamp, String nonce)
  {
    String[] arr = { token, timestamp, nonce };

    Arrays.sort(arr);
    StringBuilder content = new StringBuilder();
    for (int i = 0; i < arr.length; i++) {
      content.append(arr[i]);
    }
    MessageDigest md = null;
    String tmpStr = null;
    try
    {
      md = MessageDigest.getInstance("SHA-1");

      byte[] digest = md.digest(content.toString().getBytes());
      tmpStr = byteToStr(digest);
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }

    content = null;

    return tmpStr != null ? tmpStr.equals(signature.toUpperCase()) : false;
  }

  private static String byteToStr(byte[] byteArray)
  {
    String strDigest = "";
    for (int i = 0; i < byteArray.length; i++) {
      strDigest = strDigest + byteToHexStr(byteArray[i]);
    }
    return strDigest;
  }

  private static String byteToHexStr(byte mByte)
  {
    char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
    char[] tempArr = new char[2];
    tempArr[0] = Digit[(mByte >>> 4 & 0xF)];
    tempArr[1] = Digit[(mByte & 0xF)];

    String s = new String(tempArr);
    return s;
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     weixin.guanjia.core.util.SignUtil
 * JD-Core Version:    0.6.2
 */