package com.leeson.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Tools
{
  public static boolean notEmpty(String s)
  {
    return (s != null) && (!"".equals(s)) && (!"null".equals(s));
  }

  public static boolean isEmpty(String s)
  {
    return (s == null) || ("".equals(s)) || ("null".equals(s));
  }

  public static String[] str2StrArray(String str, String splitRegex)
  {
    if (isEmpty(str)) {
      return null;
    }
    return str.split(splitRegex);
  }

  public static String[] str2StrArray(String str)
  {
    return str2StrArray(str, ",\\s*");
  }

  public static String date2Str(Date date)
  {
    return date2Str(date, "yyyy-MM-dd HH:mm:ss");
  }

  public static Date str2Date(String date)
  {
    if (notEmpty(date)) {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      try {
        return sdf.parse(date);
      }
      catch (ParseException e) {
        e.printStackTrace();

        return new Date();
      }
    }
    return null;
  }

  public static String date2Str(Date date, String format)
  {
    if (date != null) {
      SimpleDateFormat sdf = new SimpleDateFormat(format);
      return sdf.format(date);
    }
    return "";
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.common.util.Tools
 * JD-Core Version:    0.6.2
 */