package AC.Utils;

import java.io.PrintStream;

public class WR
{
  public static String Getbyte2HexString(byte[] b)
  {
    StringBuilder sb = new StringBuilder();

    for (int i = 0; i < b.length; i++) {
      String hex = Integer.toHexString(b[i] & 0xFF);
      if (hex.length() == 1) {
        hex = '0' + hex;
      }
      sb.append(hex);
    }
    return "[" + sb.toString() + "]";
  }

  public static void space() {
    System.out
      .println("########################################################");
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     AC.Utils.WR
 * JD-Core Version:    0.6.2
 */