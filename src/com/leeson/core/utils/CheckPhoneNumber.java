package com.leeson.core.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckPhoneNumber
{
  public static String yd = "^1(3[4-9]|5[01789]|8[78])\\d{8}$";

  public static String lt = "^1(3[0-2]|5[256]|8[56])\\d{8}$";

  public static String dx = "^(18[09]|1[35]3)\\d{8}$";

  public static boolean isPhoneNumberDX(String number)
  {
    Pattern p = Pattern.compile(dx);
    Matcher m = p.matcher(number);
    return m.matches();
  }

  public static boolean isPhoneNumberAllDX(String[] list)
  {
    Pattern p = Pattern.compile(dx);
    String[] arrayOfString = list; int j = list.length; for (int i = 0; i < j; i++) { String string = arrayOfString[i];
      if (string.length() != 11)
        return false;
      Matcher m = p.matcher(string);
      if (!m.matches()) {
        return false;
      }
    }
    return true;
  }

  public static boolean isPhoneNumberYD(String number)
  {
    Pattern p = Pattern.compile(yd);
    Matcher m = p.matcher(number);
    return m.matches();
  }

  public static boolean isPhoneNumberAllYD(String[] list)
  {
    Pattern p = Pattern.compile(yd);
    String[] arrayOfString = list; int j = list.length; for (int i = 0; i < j; i++) { String string = arrayOfString[i];
      if (string.length() != 11)
        return false;
      Matcher m = p.matcher(string);
      if (!m.matches()) {
        return false;
      }
    }
    return true;
  }

  public static boolean isPhoneNumberLT(String number)
  {
    Pattern p = Pattern.compile(lt);
    Matcher m = p.matcher(number);
    return m.matches();
  }

  public static boolean isPhoneNumberAllLT(String[] list)
  {
    Pattern p = Pattern.compile(lt);
    String[] arrayOfString = list; int j = list.length; for (int i = 0; i < j; i++) { String string = arrayOfString[i];
      if (string.length() != 11)
        return false;
      Matcher m = p.matcher(string);
      if (!m.matches()) {
        return false;
      }
    }
    return true;
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.utils.CheckPhoneNumber
 * JD-Core Version:    0.6.2
 */