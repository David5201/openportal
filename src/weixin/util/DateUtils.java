package weixin.util;

import java.beans.PropertyEditorSupport;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import org.springframework.util.StringUtils;

public class DateUtils extends PropertyEditorSupport
{
  public static final SimpleDateFormat date_sdf = new SimpleDateFormat(
    "yyyy-MM-dd");

  public static final SimpleDateFormat yyyyMMdd = new SimpleDateFormat(
    "yyyyMMdd");

  public static final SimpleDateFormat date_sdf_wz = new SimpleDateFormat(
    "yyyy年MM月dd日");

  public static final SimpleDateFormat time_sdf = new SimpleDateFormat(
    "yyyy-MM-dd HH:mm");

  public static final SimpleDateFormat yyyymmddhhmmss = new SimpleDateFormat(
    "yyyyMMddHHmmss");

  public static final SimpleDateFormat short_time_sdf = new SimpleDateFormat(
    "HH:mm");

  public static final SimpleDateFormat datetimeFormat = new SimpleDateFormat(
    "yyyy-MM-dd HH:mm:ss");
  private static final long DAY_IN_MILLIS = 86400000L;
  private static final long HOUR_IN_MILLIS = 3600000L;
  private static final long MINUTE_IN_MILLIS = 60000L;
  private static final long SECOND_IN_MILLIS = 1000L;

  private static SimpleDateFormat getSDFormat(String pattern)
  {
    return new SimpleDateFormat(pattern);
  }

  public static Calendar getCalendar()
  {
    return Calendar.getInstance();
  }

  public static Calendar getCalendar(long millis)
  {
    Calendar cal = Calendar.getInstance();
    cal.setTime(new Date(millis));
    return cal;
  }

  public static String date2SStr()
  {
    Date date = getDate();
    if (date == null) {
      return null;
    }
    return yyyyMMdd.format(date);
  }

  public static Date getDate()
  {
    return new Date();
  }

  public static Date getDate(long millis)
  {
    return new Date(millis);
  }

  public static String timestamptoStr(Timestamp time)
  {
    Date date = null;
    if (time != null) {
      date = new Date(time.getTime());
    }
    return date2Str(date_sdf);
  }

  public static Timestamp str2Timestamp(String str)
  {
    Date date = str2Date(str, date_sdf);
    return new Timestamp(date.getTime());
  }

  public static Date str2Date(String str, SimpleDateFormat sdf)
  {
    if ((str == null) || ("".equals(str))) {
      return null;
    }
    Date date = null;
    try {
      return sdf.parse(str);
    }
    catch (ParseException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static String date2Str(SimpleDateFormat date_sdf)
  {
    Date date = getDate();
    if (date == null) {
      return null;
    }
    return date_sdf.format(date);
  }

  public static String dataformat(String data, String format)
  {
    SimpleDateFormat sformat = new SimpleDateFormat(format);
    Date date = null;
    try {
      date = sformat.parse(data);
    }
    catch (ParseException e) {
      e.printStackTrace();
    }
    return sformat.format(date);
  }

  public static String date2Str(Date date, SimpleDateFormat date_sdf)
  {
    if (date == null) {
      return null;
    }
    return date_sdf.format(date);
  }

  public static String getDate(String format)
  {
    Date date = new Date();
    if (date == null) {
      return null;
    }
    SimpleDateFormat sdf = new SimpleDateFormat(format);
    return sdf.format(date);
  }

  public static Timestamp getTimestamp(long millis)
  {
    return new Timestamp(millis);
  }

  public static Timestamp getTimestamp(String time)
  {
    return new Timestamp(Long.parseLong(time));
  }

  public static Timestamp getTimestamp()
  {
    return new Timestamp(new Date().getTime());
  }

  public static Timestamp getTimestamp(Date date)
  {
    return new Timestamp(date.getTime());
  }

  public static Timestamp getCalendarTimestamp(Calendar cal)
  {
    return new Timestamp(cal.getTime().getTime());
  }

  public static Timestamp gettimestamp() {
    Date dt = new Date();
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String nowTime = df.format(dt);
    Timestamp buydate = Timestamp.valueOf(nowTime);
    return buydate;
  }

  public static long getMillis()
  {
    return new Date().getTime();
  }

  public static long getMillis(Calendar cal)
  {
    return cal.getTime().getTime();
  }

  public static long getMillis(Date date)
  {
    return date.getTime();
  }

  public static long getMillis(Timestamp ts)
  {
    return ts.getTime();
  }

  public static String formatDate()
  {
    return date_sdf.format(getCalendar().getTime());
  }

  public static String getDataString(SimpleDateFormat formatstr)
  {
    return formatstr.format(getCalendar().getTime());
  }

  public static String formatDate(Calendar cal)
  {
    return date_sdf.format(cal.getTime());
  }

  public static String formatDate(Date date)
  {
    return date_sdf.format(date);
  }

  public static String formatDate(long millis)
  {
    return date_sdf.format(new Date(millis));
  }

  public static String formatDate(String pattern)
  {
    return getSDFormat(pattern).format(getCalendar().getTime());
  }

  public static String formatDate(Calendar cal, String pattern)
  {
    return getSDFormat(pattern).format(cal.getTime());
  }

  public static String formatDate(Date date, String pattern)
  {
    return getSDFormat(pattern).format(date);
  }

  public static String formatTime()
  {
    return time_sdf.format(getCalendar().getTime());
  }

  public static String formatTime(long millis)
  {
    return time_sdf.format(new Date(millis));
  }

  public static String formatTime(Calendar cal)
  {
    return time_sdf.format(cal.getTime());
  }

  public static String formatTime(Date date)
  {
    return time_sdf.format(date);
  }

  public static String formatShortTime()
  {
    return short_time_sdf.format(getCalendar().getTime());
  }

  public static String formatShortTime(long millis)
  {
    return short_time_sdf.format(new Date(millis));
  }

  public static String formatShortTime(Calendar cal)
  {
    return short_time_sdf.format(cal.getTime());
  }

  public static String formatShortTime(Date date)
  {
    return short_time_sdf.format(date);
  }

  public static Date parseDate(String src, String pattern)
    throws ParseException
  {
    return getSDFormat(pattern).parse(src);
  }

  public static Calendar parseCalendar(String src, String pattern)
    throws ParseException
  {
    Date date = parseDate(src, pattern);
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    return cal;
  }

  public static String formatAddDate(String src, String pattern, int amount)
    throws ParseException
  {
    Calendar cal = parseCalendar(src, pattern);
    cal.add(5, amount);
    return formatDate(cal);
  }

  public static Timestamp parseTimestamp(String src, String pattern)
    throws ParseException
  {
    Date date = parseDate(src, pattern);
    return new Timestamp(date.getTime());
  }

  public static int dateDiff(char flag, Calendar calSrc, Calendar calDes)
  {
    long millisDiff = getMillis(calSrc) - getMillis(calDes);

    if (flag == 'y') {
      return calSrc.get(1) - calDes.get(1);
    }

    if (flag == 'd') {
      return (int)(millisDiff / 86400000L);
    }

    if (flag == 'h') {
      return (int)(millisDiff / 3600000L);
    }

    if (flag == 'm') {
      return (int)(millisDiff / 60000L);
    }

    if (flag == 's') {
      return (int)(millisDiff / 1000L);
    }

    return 0;
  }

  public void setAsText(String text)
    throws IllegalArgumentException
  {
    if (StringUtils.hasText(text))
      try {
        if ((text.indexOf(":") == -1) && (text.length() == 10)) {
          setValue(date_sdf.parse(text));
          return; } if ((text.indexOf(":") > 0) && (text.length() == 19)) {
          setValue(datetimeFormat.parse(text));
          return;
        }throw new IllegalArgumentException(
          "Could not parse date, date format is error ");
      }
      catch (ParseException ex) {
        IllegalArgumentException iae = new IllegalArgumentException(
          "Could not parse date: " + ex.getMessage());
        iae.initCause(ex);
        throw iae;
      }
    else
      setValue(null);
  }

  public static int getYear() {
    GregorianCalendar calendar = new GregorianCalendar();
    calendar.setTime(getDate());
    return calendar.get(1);
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     weixin.util.DateUtils
 * JD-Core Version:    0.6.2
 */