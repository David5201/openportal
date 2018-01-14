package AC.Utils;

import java.util.Random;

public class Make_Challenge
{
  public static String getChallenge()
  {
    String base = "abcdefghijklmnopqrstuvwxyz0123456789";
    int length = 16;
    Random random = new Random();
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < length; i++) {
      int number = random.nextInt(base.length());
      sb.append(base.charAt(number));
    }
    return sb.toString();
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     AC.Utils.Make_Challenge
 * JD-Core Version:    0.6.2
 */