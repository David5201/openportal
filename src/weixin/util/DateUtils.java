/*     */ package weixin.util;
/*     */ 
/*     */ import java.beans.PropertyEditorSupport;
/*     */ import java.sql.Timestamp;
/*     */ import java.text.DateFormat;
/*     */ import java.text.ParseException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.GregorianCalendar;
/*     */ import org.springframework.util.StringUtils;
/*     */ 
/*     */ public class DateUtils extends PropertyEditorSupport
/*     */ {
/*  24 */   public static final SimpleDateFormat date_sdf = new SimpleDateFormat(
/*  25 */     "yyyy-MM-dd");
/*     */ 
/*  27 */   public static final SimpleDateFormat yyyyMMdd = new SimpleDateFormat(
/*  28 */     "yyyyMMdd");
/*     */ 
/*  30 */   public static final SimpleDateFormat date_sdf_wz = new SimpleDateFormat(
/*  31 */     "yyyy年MM月dd日");
/*     */ 
/*  32 */   public static final SimpleDateFormat time_sdf = new SimpleDateFormat(
/*  33 */     "yyyy-MM-dd HH:mm");
/*     */ 
/*  34 */   public static final SimpleDateFormat yyyymmddhhmmss = new SimpleDateFormat(
/*  35 */     "yyyyMMddHHmmss");
/*     */ 
/*  36 */   public static final SimpleDateFormat short_time_sdf = new SimpleDateFormat(
/*  37 */     "HH:mm");
/*     */ 
/*  38 */   public static final SimpleDateFormat datetimeFormat = new SimpleDateFormat(
/*  39 */     "yyyy-MM-dd HH:mm:ss");
/*     */   private static final long DAY_IN_MILLIS = 86400000L;
/*     */   private static final long HOUR_IN_MILLIS = 3600000L;
/*     */   private static final long MINUTE_IN_MILLIS = 60000L;
/*     */   private static final long SECOND_IN_MILLIS = 1000L;
/*     */ 
/*     */   private static SimpleDateFormat getSDFormat(String pattern)
/*     */   {
/*  47 */     return new SimpleDateFormat(pattern);
/*     */   }
/*     */ 
/*     */   public static Calendar getCalendar()
/*     */   {
/*  56 */     return Calendar.getInstance();
/*     */   }
/*     */ 
/*     */   public static Calendar getCalendar(long millis)
/*     */   {
/*  67 */     Calendar cal = Calendar.getInstance();
/*  68 */     cal.setTime(new Date(millis));
/*  69 */     return cal;
/*     */   }
/*     */ 
/*     */   public static String date2SStr()
/*     */   {
/*  77 */     Date date = getDate();
/*  78 */     if (date == null) {
/*  79 */       return null;
/*     */     }
/*  81 */     return yyyyMMdd.format(date);
/*     */   }
/*     */ 
/*     */   public static Date getDate()
/*     */   {
/*  95 */     return new Date();
/*     */   }
/*     */ 
/*     */   public static Date getDate(long millis)
/*     */   {
/* 106 */     return new Date(millis);
/*     */   }
/*     */ 
/*     */   public static String timestamptoStr(Timestamp time)
/*     */   {
/* 116 */     Date date = null;
/* 117 */     if (time != null) {
/* 118 */       date = new Date(time.getTime());
/*     */     }
/* 120 */     return date2Str(date_sdf);
/*     */   }
/*     */ 
/*     */   public static Timestamp str2Timestamp(String str)
/*     */   {
/* 130 */     Date date = str2Date(str, date_sdf);
/* 131 */     return new Timestamp(date.getTime());
/*     */   }
/*     */ 
/*     */   public static Date str2Date(String str, SimpleDateFormat sdf)
/*     */   {
/* 140 */     if ((str == null) || ("".equals(str))) {
/* 141 */       return null;
/*     */     }
/* 143 */     Date date = null;
/*     */     try {
/* 145 */       return sdf.parse(str);
/*     */     }
/*     */     catch (ParseException e) {
/* 148 */       e.printStackTrace();
/*     */     }
/* 150 */     return null;
/*     */   }
/*     */ 
/*     */   public static String date2Str(SimpleDateFormat date_sdf)
/*     */   {
/* 163 */     Date date = getDate();
/* 164 */     if (date == null) {
/* 165 */       return null;
/*     */     }
/* 167 */     return date_sdf.format(date);
/*     */   }
/*     */ 
/*     */   public static String dataformat(String data, String format)
/*     */   {
/* 177 */     SimpleDateFormat sformat = new SimpleDateFormat(format);
/* 178 */     Date date = null;
/*     */     try {
/* 180 */       date = sformat.parse(data);
/*     */     }
/*     */     catch (ParseException e) {
/* 183 */       e.printStackTrace();
/*     */     }
/* 185 */     return sformat.format(date);
/*     */   }
/*     */ 
/*     */   public static String date2Str(Date date, SimpleDateFormat date_sdf)
/*     */   {
/* 197 */     if (date == null) {
/* 198 */       return null;
/*     */     }
/* 200 */     return date_sdf.format(date);
/*     */   }
/*     */ 
/*     */   public static String getDate(String format)
/*     */   {
/* 212 */     Date date = new Date();
/* 213 */     if (date == null) {
/* 214 */       return null;
/*     */     }
/* 216 */     SimpleDateFormat sdf = new SimpleDateFormat(format);
/* 217 */     return sdf.format(date);
/*     */   }
/*     */ 
/*     */   public static Timestamp getTimestamp(long millis)
/*     */   {
/* 228 */     return new Timestamp(millis);
/*     */   }
/*     */ 
/*     */   public static Timestamp getTimestamp(String time)
/*     */   {
/* 239 */     return new Timestamp(Long.parseLong(time));
/*     */   }
/*     */ 
/*     */   public static Timestamp getTimestamp()
/*     */   {
/* 248 */     return new Timestamp(new Date().getTime());
/*     */   }
/*     */ 
/*     */   public static Timestamp getTimestamp(Date date)
/*     */   {
/* 259 */     return new Timestamp(date.getTime());
/*     */   }
/*     */ 
/*     */   public static Timestamp getCalendarTimestamp(Calendar cal)
/*     */   {
/* 270 */     return new Timestamp(cal.getTime().getTime());
/*     */   }
/*     */ 
/*     */   public static Timestamp gettimestamp() {
/* 274 */     Date dt = new Date();
/* 275 */     DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
/* 276 */     String nowTime = df.format(dt);
/* 277 */     Timestamp buydate = Timestamp.valueOf(nowTime);
/* 278 */     return buydate;
/*     */   }
/*     */ 
/*     */   public static long getMillis()
/*     */   {
/* 292 */     return new Date().getTime();
/*     */   }
/*     */ 
/*     */   public static long getMillis(Calendar cal)
/*     */   {
/* 303 */     return cal.getTime().getTime();
/*     */   }
/*     */ 
/*     */   public static long getMillis(Date date)
/*     */   {
/* 314 */     return date.getTime();
/*     */   }
/*     */ 
/*     */   public static long getMillis(Timestamp ts)
/*     */   {
/* 325 */     return ts.getTime();
/*     */   }
/*     */ 
/*     */   public static String formatDate()
/*     */   {
/* 339 */     return date_sdf.format(getCalendar().getTime());
/*     */   }
/*     */ 
/*     */   public static String getDataString(SimpleDateFormat formatstr)
/*     */   {
/* 345 */     return formatstr.format(getCalendar().getTime());
/*     */   }
/*     */ 
/*     */   public static String formatDate(Calendar cal)
/*     */   {
/* 355 */     return date_sdf.format(cal.getTime());
/*     */   }
/*     */ 
/*     */   public static String formatDate(Date date)
/*     */   {
/* 366 */     return date_sdf.format(date);
/*     */   }
/*     */ 
/*     */   public static String formatDate(long millis)
/*     */   {
/* 377 */     return date_sdf.format(new Date(millis));
/*     */   }
/*     */ 
/*     */   public static String formatDate(String pattern)
/*     */   {
/* 388 */     return getSDFormat(pattern).format(getCalendar().getTime());
/*     */   }
/*     */ 
/*     */   public static String formatDate(Calendar cal, String pattern)
/*     */   {
/* 401 */     return getSDFormat(pattern).format(cal.getTime());
/*     */   }
/*     */ 
/*     */   public static String formatDate(Date date, String pattern)
/*     */   {
/* 414 */     return getSDFormat(pattern).format(date);
/*     */   }
/*     */ 
/*     */   public static String formatTime()
/*     */   {
/* 428 */     return time_sdf.format(getCalendar().getTime());
/*     */   }
/*     */ 
/*     */   public static String formatTime(long millis)
/*     */   {
/* 439 */     return time_sdf.format(new Date(millis));
/*     */   }
/*     */ 
/*     */   public static String formatTime(Calendar cal)
/*     */   {
/* 450 */     return time_sdf.format(cal.getTime());
/*     */   }
/*     */ 
/*     */   public static String formatTime(Date date)
/*     */   {
/* 461 */     return time_sdf.format(date);
/*     */   }
/*     */ 
/*     */   public static String formatShortTime()
/*     */   {
/* 475 */     return short_time_sdf.format(getCalendar().getTime());
/*     */   }
/*     */ 
/*     */   public static String formatShortTime(long millis)
/*     */   {
/* 486 */     return short_time_sdf.format(new Date(millis));
/*     */   }
/*     */ 
/*     */   public static String formatShortTime(Calendar cal)
/*     */   {
/* 497 */     return short_time_sdf.format(cal.getTime());
/*     */   }
/*     */ 
/*     */   public static String formatShortTime(Date date)
/*     */   {
/* 508 */     return short_time_sdf.format(date);
/*     */   }
/*     */ 
/*     */   public static Date parseDate(String src, String pattern)
/*     */     throws ParseException
/*     */   {
/* 531 */     return getSDFormat(pattern).parse(src);
/*     */   }
/*     */ 
/*     */   public static Calendar parseCalendar(String src, String pattern)
/*     */     throws ParseException
/*     */   {
/* 549 */     Date date = parseDate(src, pattern);
/* 550 */     Calendar cal = Calendar.getInstance();
/* 551 */     cal.setTime(date);
/* 552 */     return cal;
/*     */   }
/*     */ 
/*     */   public static String formatAddDate(String src, String pattern, int amount)
/*     */     throws ParseException
/*     */   {
/* 558 */     Calendar cal = parseCalendar(src, pattern);
/* 559 */     cal.add(5, amount);
/* 560 */     return formatDate(cal);
/*     */   }
/*     */ 
/*     */   public static Timestamp parseTimestamp(String src, String pattern)
/*     */     throws ParseException
/*     */   {
/* 576 */     Date date = parseDate(src, pattern);
/* 577 */     return new Timestamp(date.getTime());
/*     */   }
/*     */ 
/*     */   public static int dateDiff(char flag, Calendar calSrc, Calendar calDes)
/*     */   {
/* 598 */     long millisDiff = getMillis(calSrc) - getMillis(calDes);
/*     */ 
/* 600 */     if (flag == 'y') {
/* 601 */       return calSrc.get(1) - calDes.get(1);
/*     */     }
/*     */ 
/* 604 */     if (flag == 'd') {
/* 605 */       return (int)(millisDiff / 86400000L);
/*     */     }
/*     */ 
/* 608 */     if (flag == 'h') {
/* 609 */       return (int)(millisDiff / 3600000L);
/*     */     }
/*     */ 
/* 612 */     if (flag == 'm') {
/* 613 */       return (int)(millisDiff / 60000L);
/*     */     }
/*     */ 
/* 616 */     if (flag == 's') {
/* 617 */       return (int)(millisDiff / 1000L);
/*     */     }
/*     */ 
/* 620 */     return 0;
/*     */   }
/*     */ 
/*     */   public void setAsText(String text)
/*     */     throws IllegalArgumentException
/*     */   {
/* 630 */     if (StringUtils.hasText(text))
/*     */       try {
/* 632 */         if ((text.indexOf(":") == -1) && (text.length() == 10)) {
/* 633 */           setValue(date_sdf.parse(text));
/* 634 */           return; } if ((text.indexOf(":") > 0) && (text.length() == 19)) {
/* 635 */           setValue(datetimeFormat.parse(text));
/* 636 */           return;
/* 637 */         }throw new IllegalArgumentException(
/* 638 */           "Could not parse date, date format is error ");
/*     */       }
/*     */       catch (ParseException ex) {
/* 641 */         IllegalArgumentException iae = new IllegalArgumentException(
/* 642 */           "Could not parse date: " + ex.getMessage());
/* 643 */         iae.initCause(ex);
/* 644 */         throw iae;
/*     */       }
/*     */     else
/* 647 */       setValue(null);
/*     */   }
/*     */ 
/*     */   public static int getYear() {
/* 651 */     GregorianCalendar calendar = new GregorianCalendar();
/* 652 */     calendar.setTime(getDate());
/* 653 */     return calendar.get(1);
/*     */   }
/*     */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     weixin.util.DateUtils
 * JD-Core Version:    0.6.2
 */