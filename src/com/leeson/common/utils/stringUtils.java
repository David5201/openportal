package com.leeson.common.utils;

public class stringUtils
{
  public static boolean isEmpty(String str)
  {
    if ((str == null) || (str.length() == 0)) {
      return true;
    }
    return false;
  }

  public static boolean isNotEmpty(String str) {
    if ((str == null) || (str.length() == 0)) {
      return false;
    }
    return true;
  }

  public static boolean isBlank(String str)
  {
    if ((str == null) || (str.length() == 0) || ("".equals(str.trim())) || ("null".equals(str)) || ("null".equals(str.trim()))) {
      return true;
    }
    return false;
  }

  public static boolean isNotBlank(String str) {
    if ((str == null) || (str.length() == 0) || ("".equals(str.trim())) || ("null".equals(str)) || ("null".equals(str.trim()))) {
      return false;
    }
    return true;
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.common.utils.stringUtils
 * JD-Core Version:    0.6.2
 */