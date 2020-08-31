package com.xinzeyijia.houselocks.util.date_util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.support.constraint.Constraints.TAG;
import static com.xinzeyijia.houselocks.util.LogUtils.loge;
import static com.xinzeyijia.houselocks.util.date_util.ConstUtils.*;


public class TimeUtils {
    private TimeUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * <p>在工具类中经常使用到工具类的格式化描述，这个主要是一个日期的操作类，所以日志格式主要使用 SimpleDateFormat的定义格式.</p>
     * 格式的意义如下： 日期和时间模式 <br>
     * <p>日期和时间格式由日期和时间模式字符串指定。在日期和时间模式字符串中，未加引号的字母 'A' 到 'Z' 和 'a' 到 'z'
     * 被解释为模式字母，用来表示日期或时间字符串元素。文本可以使用单引号 (') 引起来，以免进行解释。"''"
     * 表示单引号。所有其他字符均不解释；只是在格式化时将它们简单复制到输出字符串，或者在分析时与输入字符串进行匹配。
     * </p>
     * 定义了以下模式字母（所有其他字符 'A' 到 'Z' 和 'a' 到 'z' 都被保留）： <br>
     * <table border="1" cellspacing="1" cellpadding="1" summary="Chart shows pattern letters, date/time component,
     * presentation, and examples.">
     * <tr>
     * <th align="left">字母</th>
     * <th align="left">日期或时间元素</th>
     * <th align="left">表示</th>
     * <th align="left">示例</th>
     * </tr>
     * <tr>
     * <td><code>G</code></td>
     * <td>Era 标志符</td>
     * <td>Text</td>
     * <td><code>AD</code></td>
     * </tr>
     * <tr>
     * <td><code>y</code> </td>
     * <td>年 </td>
     * <td>Year </td>
     * <td><code>1996</code>; <code>96</code> </td>
     * </tr>
     * <tr>
     * <td><code>M</code> </td>
     * <td>年中的月份 </td>
     * <td>Month </td>
     * <td><code>July</code>; <code>Jul</code>; <code>07</code> </td>
     * </tr>
     * <tr>
     * <td><code>w</code> </td>
     * <td>年中的周数 </td>
     * <td>Number </td>
     * <td><code>27</code> </td>
     * </tr>
     * <tr>
     * <td><code>W</code> </td>
     * <td>月份中的周数 </td>
     * <td>Number </td>
     * <td><code>2</code> </td>
     * </tr>
     * <tr>
     * <td><code>D</code> </td>
     * <td>年中的天数 </td>
     * <td>Number </td>
     * <td><code>189</code> </td>
     * </tr>
     * <tr>
     * <td><code>d</code> </td>
     * <td>月份中的天数 </td>
     * <td>Number </td>
     * <td><code>10</code> </td>
     * </tr>
     * <tr>
     * <td><code>F</code> </td>
     * <td>月份中的星期 </td>
     * <td>Number </td>
     * <td><code>2</code> </td>
     * </tr>
     * <tr>
     * <td><code>E</code> </td>
     * <td>星期中的天数 </td>
     * <td>Text </td>
     * <td><code>Tuesday</code>; <code>Tue</code> </td>
     * </tr>
     * <tr>
     * <td><code>a</code> </td>
     * <td>Am/pm 标记 </td>
     * <td>Text </td>
     * <td><code>PM</code> </td>
     * </tr>
     * <tr>
     * <td><code>H</code> </td>
     * <td>一天中的小时数（0-23） </td>
     * <td>Number </td>
     * <td><code>0</code> </td>
     * </tr>
     * <tr>
     * <td><code>k</code> </td>
     * <td>一天中的小时数（1-24） </td>
     * <td>Number </td>
     * <td><code>24</code> </td>
     * </tr>
     * <tr>
     * <td><code>K</code> </td>
     * <td>am/pm 中的小时数（0-11） </td>
     * <td>Number </td>
     * <td><code>0</code> </td>
     * </tr>
     * <tr>
     * <td><code>h</code> </td>
     * <td>am/pm 中的小时数（1-12） </td>
     * <td>Number </td>
     * <td><code>12</code> </td>
     * </tr>
     * <tr>
     * <td><code>m</code> </td>
     * <td>小时中的分钟数 </td>
     * <td>Number </td>
     * <td><code>30</code> </td>
     * </tr>
     * <tr>
     * <td><code>s</code> </td>
     * <td>分钟中的秒数 </td>
     * <td>Number </td>
     * <td><code>55</code> </td>
     * </tr>
     * <tr>
     * <td><code>S</code> </td>
     * <td>毫秒数 </td>
     * <td>Number </td>
     * <td><code>978</code> </td>
     * </tr>
     * <tr>
     * <td><code>z</code> </td>
     * <td>时区 </td>
     * <td>General time zone </td>
     * <td><code>Pacific Standard Time</code>; <code>PST</code>; <code>GMT-08:00</code> </td>
     * </tr>
     * <tr>
     * <td><code>Z</code> </td>
     * <td>时区 </td>
     * <td>RFC 822 time zone </td>
     * <td><code>-0800</code> </td>
     * </tr>
     * </table>
     * <pre>
     *                          HH:mm    15:44
     *                         h:mm a    3:44 下午
     *                        HH:mm z    15:44 CST
     *                        HH:mm Z    15:44 +0800
     *                     HH:mm zzzz    15:44 中国标准时间
     *                       HH:mm:ss    15:44:40
     *                     yyyy-MM-dd    2016-08-12
     *               yyyy-MM-dd HH:mm    2016-08-12 15:44
     *            yyyy-MM-dd HH:mm:ss    2016-08-12 15:44:40
     *       yyyy-MM-dd HH:mm:ss zzzz    2016-08-12 15:44:40 中国标准时间
     *  EEEE yyyy-MM-dd HH:mm:ss zzzz    星期五 2016-08-12 15:44:40 中国标准时间
     *       yyyy-MM-dd HH:mm:ss.SSSZ    2016-08-12 15:44:40.461+0800
     *     yyyy-MM-dd'T'HH:mm:ss.SSSZ    2016-08-12T15:44:40.461+0800
     *   yyyy.MM.dd G 'at' HH:mm:ss z    2016.08.12 公元 at 15:44:40 CST
     *                         K:mm a    3:44 下午
     *               EEE, MMM d, ''yy    星期五, 八月 12, '16
     *          hh 'o''clock' a, zzzz    03 o'clock 下午, 中国标准时间
     *   yyyyy.MMMMM.dd GGG hh:mm aaa    02016.八月.12 公元 03:44 下午
     *     EEE, d MMM yyyy HH:mm:ss Z    星期五, 12 八月 2016 15:44:40 +0800
     *                  yyMMddHHmmssZ    160812154440+0800
     *     yyyy-MM-dd'T'HH:mm:ss.SSSZ    2016-08-12T15:44:40.461+0800
     * EEEE 'DATE('yyyy-MM-dd')' 'TIME('HH:mm:ss')' zzzz    星期五 DATE(2016-08-12) TIME(15:44:40) 中国标准时间
     * </pre>
     * 注意SimpleDateFormat不是线程安全的
     */
    public static final SimpleDateFormat DEFAULT_SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

    public static final SimpleDateFormat UTC_SDF = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault());

    public static final SimpleDateFormat DATE_NO_YEAR_SDF = new SimpleDateFormat("MM-dd", Locale.getDefault());

    public static final SimpleDateFormat HOUR_SDF = new SimpleDateFormat("HH:mm", Locale.getDefault());
    public static final SimpleDateFormat HOUR_MM = new SimpleDateFormat("HHmm", Locale.getDefault());

    public static final SimpleDateFormat HOURLY_FORECAST_SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
    public static final SimpleDateFormat HOUR_SDF_T = new SimpleDateFormat("K:mm a", Locale.getDefault());
    public static final SimpleDateFormat HOUR_SDF_C = new SimpleDateFormat("MM月dd日", Locale.getDefault());
    public static final SimpleDateFormat HOUR_SDF_CT = new SimpleDateFormat("MM月dd日HH:mm", Locale.getDefault());
    public static final SimpleDateFormat HOUR_SDF_D = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    public static final SimpleDateFormat HOUR_SDF_CD = new SimpleDateFormat("MM月dd日 HH:mm", Locale.getDefault());
    public static final SimpleDateFormat HOUR_OPEN_KEY = new SimpleDateFormat("yyyyMMddHHmm", Locale.getDefault());
    public static final SimpleDateFormat MM = new SimpleDateFormat("MM", Locale.getDefault());
    public static final SimpleDateFormat HH = new SimpleDateFormat("HH", Locale.getDefault());
    public static final SimpleDateFormat DD = new SimpleDateFormat("dd", Locale.getDefault());
    public static final SimpleDateFormat HOUR_OPEN_YMD = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
    public static final SimpleDateFormat HOUR_OPEN_YMDZ = new SimpleDateFormat("yyyy年MM月dd日", Locale.getDefault());
    public static final SimpleDateFormat HOUR_OPEN_YMDZHM = new SimpleDateFormat("yyyy年MM月dd日 HH:mm", Locale.getDefault());
    public static final SimpleDateFormat HOUR_OPEN_KEY_CUSTOM = new SimpleDateFormat("yyMMddHHmm", Locale.getDefault());

    /**
     * 将时间戳转为时间字符串
     * <p>格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param milliseconds 毫秒时间戳
     * @return 时间字符串
     */
    public static String milliseconds2String(long milliseconds) {
        return milliseconds2String(milliseconds, DEFAULT_SDF);
    }

    /**
     * 将时间戳转为时间字符串
     * <p>格式为用户自定义</p>
     *
     * @param milliseconds 毫秒时间戳
     * @param format       时间格式
     * @return 时间字符串
     */
    public static String milliseconds2String(long milliseconds, SimpleDateFormat format) {
        return format.format(new Date(milliseconds));
    }

    /**
     * 将时间字符串转为时间戳
     * <p>格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time 时间字符串
     * @return 毫秒时间戳
     */
    public static long string2Milliseconds(String time) {
        return string2Milliseconds(time, DEFAULT_SDF);
    }

    /**
     * 将时间字符串转为时间戳
     * <p>格式为用户自定义</p>
     *
     * @param time   时间字符串
     * @param format 时间格式
     * @return 毫秒时间戳
     */
    public static long string2Milliseconds(String time, SimpleDateFormat format) {
        try {
            return format.parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 将时间字符串转为时间戳
     * <p>格式为用户自定义</p>
     *
     * @param time   时间字符串
     * @param format 时间格式
     * @return 毫秒时间戳
     */
    public static String string2string(String time, SimpleDateFormat format, SimpleDateFormat format2) {

        try {
            String temp = time.replace("T", " ");
            long time1 = format.parse(temp).getTime();
            return milliseconds2String(time1, format2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * 将时间字符串转为Date类型
     * <p>格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time 时间字符串
     * @return Date类型
     */
    public static Date string2Date(String time) {
        return string2Date(time, DEFAULT_SDF);
    }

    /**
     * 将时间字符串转为Date类型
     * <p>格式为用户自定义</p>
     *
     * @param time   时间字符串
     * @param format 时间格式
     * @return Date类型
     */
    public static Date string2Date(String time, SimpleDateFormat format) {
        return new Date(string2Milliseconds(time, format));
    }

    /**
     * 将Date类型转为时间字符串
     * <p>格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time Date类型时间
     * @return 时间字符串
     */
    public static String date2String(Date time) {
        return date2String(time, DEFAULT_SDF);
    }

    /**
     * 将Date类型转为时间字符串
     * <p>格式为用户自定义</p>
     *
     * @param time   Date类型时间
     * @param format 时间格式
     * @return 时间字符串
     */
    public static String date2String(Date time, SimpleDateFormat format) {
        return format.format(time);
    }

    /**
     * 将Date类型转为时间戳
     *
     * @param time Date类型时间
     * @return 毫秒时间戳
     */
    public static long date2Milliseconds(Date time) {
        return time.getTime();
    }

    /**
     * 将时间戳转为Date类型
     *
     * @param milliseconds 毫秒时间戳
     * @return Date类型时间
     */
    public static Date milliseconds2Date(long milliseconds) {
        return new Date(milliseconds);
    }

    /**
     * 毫秒时间戳单位转换（单位：unit）
     *
     * @param milliseconds 毫秒时间戳
     * @param unit
     * @return unit时间戳
     */
    private static long milliseconds2Unit(long milliseconds, ConstUtils.TimeUnit unit) {
        switch (unit) {
            case MSEC:
                return milliseconds / MSEC;
            case SEC:
                return milliseconds / SEC;
            case MIN:
                return milliseconds / MIN;
            case HOUR:
                return milliseconds / HOUR;
            case DAY:
                return milliseconds / DAY;
        }
        return -1;
    }

    /**
     * 获取两个时间差（单位：unit）
     * <p>time1和time2格式都为yyyy-MM-dd HH:mm:ss</p>
     *
     * @return unit时间戳
     */
    public static long getDayNum(String endTime, String beginTime) {

        return ((Long.parseLong(endTime) - Long.parseLong(beginTime)) / (1000 * 60 * 60 * 24));
    }

    /**
     * 获取两个时间差（单位：unit）
     * <p>time1和time2格式都为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time0 时间字符串1
     * @param time1 时间字符串2
     * @param unit
     * @return unit时间戳
     */
    public static long getIntervalTime(String time0, String time1, ConstUtils.TimeUnit unit) {
        return getIntervalTime(time0, time1, unit, DEFAULT_SDF);
    }

    /**
     * 获取两个时间差（单位：unit）
     * <p>time1和time2格式都为format</p>
     *
     * @param time0  时间字符串1
     * @param time1  时间字符串2
     * @param unit
     * @param format 时间格式
     * @return unit时间戳
     */
    public static long getIntervalTime(String time0, String time1, ConstUtils.TimeUnit unit, SimpleDateFormat format) {
        return milliseconds2Unit(Math.abs(string2Milliseconds(time0, format)
                - string2Milliseconds(time1, format)), unit);
    }

    /**
     * 获取两个时间差（单位：unit）
     * <p>time1和time2都为Date类型</p>
     *
     * @param time0 Date类型时间1
     * @param time1 Date类型时间2
     * @param unit
     * @return unit时间戳
     */
    public static long getIntervalTime(Date time0, Date time1, ConstUtils.TimeUnit unit) {
        return milliseconds2Unit(Math.abs(date2Milliseconds(time1)
                - date2Milliseconds(time0)), unit);
    }

    /**
     * 获取当前时间
     *
     * @return 毫秒时间戳
     */
    public static long getCurTimeMills() {
        return System.currentTimeMillis();
    }

    /**
     * 获取当前时间
     * <p>格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @return 时间字符串
     */
    public static String getCurTimeString() {
        return date2String(new Date());
    }

    /**
     * 获取当前时间
     * <p>格式为用户自定义</p>
     *
     * @param format 时间格式
     * @return 时间字符串
     */
    public static String getCurTimeString(SimpleDateFormat format) {
        return date2String(new Date(), format);
    }

    /**
     * 获取当前时间
     * <p>Date类型</p>
     *
     * @return Date类型时间
     */
    public static Date getCurTimeDate() {
        return new Date();
    }

    /**
     * 获取与当前时间的差（单位：unit）
     * <p>time格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time 时间字符串
     * @param unit
     * @return unit时间戳
     */
    public static long getIntervalByNow(String time, ConstUtils.TimeUnit unit) {
        return getIntervalByNow(time, unit, DEFAULT_SDF);
    }

    /**
     * 获取与当前时间的差（单位：unit）
     * <p>time格式为format</p>
     *
     * @param time   时间字符串
     * @param unit
     * @param format 时间格式
     * @return unit时间戳
     */
    public static long getIntervalByNow(String time, ConstUtils.TimeUnit unit, SimpleDateFormat format) {
        return getIntervalTime(getCurTimeString(), time, unit, format);
    }

    /**
     * 获取与当前时间的差（单位：unit）
     * <p>time为Date类型</p>
     *
     * @param time Date类型时间
     * @param unit
     * @return unit时间戳
     */
    public static long getIntervalByNow(Date time, ConstUtils.TimeUnit unit) {
        return getIntervalTime(getCurTimeDate(), time, unit);
    }

    /**
     * 判断闰年
     *
     * @param year 年份
     * @return {@code true}: 闰年<br>{@code false}: 平年
     */
    public static boolean isLeapYear(int year) {
        return year % 4 == 0 && year % 100 != 0 || year % 400 == 0;
    }

    /**
     * 获取星期
     * <p>time格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time 时间字符串
     * @return 星期
     */
    public static String getWeek(String time) {
        return new SimpleDateFormat("EEEE", Locale.getDefault()).format(string2Date(time));
    }

    /**
     * 获取星期
     *
     * @param time   时间字符串
     * @param format 时间格式
     * @return 星期
     */
    public static String getWeek(String time, SimpleDateFormat format) {
        return new SimpleDateFormat("EEEE", Locale.getDefault()).format(string2Date(time, format));
    }

    /**
     * 获取星期
     *
     * @param time Date类型时间
     * @return 星期
     */
    public static String getWeek(Date time) {
        return new SimpleDateFormat("EEEE", Locale.getDefault()).format(time);
    }

    /**
     * 获取星期
     * <p>注意：周日的Index才是1，周六为7</p>
     * <p>time格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time 时间字符串
     * @return 1...5
     */
    public static int getWeekIndex(String time) {
        Date date = string2Date(time);
        return getWeekIndex(date);
    }

    /**
     * 获取星期
     * <p>注意：周日的Index才是1，周六为7</p>
     *
     * @param time   时间字符串
     * @param format 时间格式
     * @return 1...7
     */
    public static int getWeekIndex(String time, SimpleDateFormat format) {
        Date date = string2Date(time, format);
        return getWeekIndex(date);
    }

    /**
     * 获取星期
     * <p>注意：周日的Index才是1，周六为7</p>
     *
     * @param time Date类型时间
     * @return 1...7
     */
    public static int getWeekIndex(Date time) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 获取月份中的第几周
     * <p>注意：国外周日才是新的一周的开始</p>
     * <p>time格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time 时间字符串
     * @return 1...5
     */
    public static int getWeekOfMonth(String time) {
        Date date = string2Date(time);
        return getWeekOfMonth(date);
    }

    /**
     * 获取月份中的第几周
     * <p>注意：国外周日才是新的一周的开始</p>
     *
     * @param time   时间字符串
     * @param format 时间格式
     * @return 1...5
     */
    public static int getWeekOfMonth(String time, SimpleDateFormat format) {
        Date date = string2Date(time, format);
        return getWeekOfMonth(date);
    }

    /**
     * 获取月份中的第几周
     * <p>注意：国外周日才是新的一周的开始</p>
     *
     * @param time Date类型时间
     * @return 1...5
     */
    public static int getWeekOfMonth(Date time) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        return cal.get(Calendar.WEEK_OF_MONTH);
    }

    /**
     * 获取年份中的第几周
     * <p>注意：国外周日才是新的一周的开始</p>
     * <p>time格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time 时间字符串
     * @return 1...54
     */
    public static int getWeekOfYear(String time) {
        Date date = string2Date(time);
        return getWeekOfYear(date);
    }

    /**
     * 获取年份中的第几周
     * <p>注意：国外周日才是新的一周的开始</p>
     *
     * @param time   时间字符串
     * @param format 时间格式
     * @return 1...54
     */
    public static int getWeekOfYear(String time, SimpleDateFormat format) {
        Date date = string2Date(time, format);
        return getWeekOfYear(date);
    }

    /**
     * 获取年份中的第几周
     * <p>注意：国外周日才是新的一周的开始</p>
     *
     * @param time Date类型时间
     * @return 1...54
     */
    public static int getWeekOfYear(Date time) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        return cal.get(Calendar.WEEK_OF_YEAR);
    }

    public static String string2String(String time, SimpleDateFormat fromFormat, SimpleDateFormat toFormat) {
        return TimeUtils.date2String(TimeUtils.string2Date(time, fromFormat), toFormat);
    }

    public static String stringtoString(String time, SimpleDateFormat fromFormat) {
        return TimeUtils.milliseconds2String(Long.parseLong(time), fromFormat);
    }

    public static boolean isBefore(Date d1, Date d2) {
        return d1.before(d2);
    }

    public static boolean isBefore(String d1, String d2, SimpleDateFormat sdf) {
        try {
            return sdf.parse(d1).before(sdf.parse(d2));
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 获取某个日期的前几天的日期
     *
     * @param dateString 某日期
     * @param dayNumber  前面第几天
     * @return
     */
    public static String getPreviousDay(String dateString, int dayNumber) {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day - dayNumber);

        String previousDay = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
        return previousDay;
    }

    /**
     * 获取某个日期的前几天的日期
     *
     * @param date      某日期
     * @param dayNumber 后面第几天
     * @return
     */
    public static String getFutureDay(Date date, int dayNumber, SimpleDateFormat dateformat) {
        Calendar c = Calendar.getInstance();

        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day + dayNumber);

        String previousDay = dateformat.format(c.getTime());
        return previousDay;
    }

    public static String getSystemTime() {
        return String.valueOf(System.currentTimeMillis());
    }

    /**
     * 判断当前时间是否在两个时间段之内
     *
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @return
     */
    public static boolean isNowBetween(String beginTime, String endTime) {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        Date now = null;
        Date begin = null;
        Date end = null;
        try {
            now = df.parse(df.format(new Date()));
            begin = df.parse(beginTime);
            end = df.parse(endTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Calendar nowCal = Calendar.getInstance();
        nowCal.setTime(now);

        Calendar beginCal = Calendar.getInstance();
        beginCal.setTime(begin);

        Calendar endCal = Calendar.getInstance();
        endCal.setTime(end);

        if (nowCal.after(beginCal) && nowCal.before(endCal)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取当前时间在某段时间内的百分比位置
     *
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @return
     */
    public static float getTimeDiffPercent(String beginTime, String endTime) {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        Date now = null;
        Date begin = null;
        Date end = null;
        try {
            now = df.parse(df.format(new Date()));
            begin = df.parse(beginTime);
            end = df.parse(endTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (float) (now.getTime() - begin.getTime()) / (float) (end.getTime() - begin.getTime());
    }

    static Calendar today = Calendar.getInstance();
    static LunarCalendar cl = new LunarCalendar();


    /*获取日期*/
    public static String getDay(String date) {
        String h;
        String[] day = date.split("-");
        h = day[2];
        return h;
    }

    /*获取月份*/
    public static String getMonth(String date) {
        String m;
        String[] day = date.split("-");
        m = day[1];
        return m;
    }

    /*获取年份*/
    public static String getYear(String date) {
        String y;
        String[] day = date.split("-");
        y = day[0];
        return y;
    }

    /*获取当前系统时间*/
    public static String getSysDate() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        return sdf.format(date);
    }

    /*格式化日期时间*/
    public static String formatDatetime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        return sdf.format(date);
    }

    public static String formatDatetime(String date) throws ParseException {
        DateFormat fmt = new SimpleDateFormat("yyyy年MM月dd日");
        Date d = fmt.parse(date);
        return d.toString();
    }

    public static String formatDatetime(String date, int forid) {
        if (date == null || "".equals(date.trim())) {
            return "";
        } else {
            String str = "";
            str = date.substring(0, date.indexOf("."));
            String[] array = str.split(" ");
            String[] dates = array[0].split("-");
            switch (forid) {
                case 0:  //yyyy-MM-dd HH:mm:ss
                    str = date.substring(0, date.indexOf("."));
                    break;
                case 1:  //yyyy-MM-dd
                    str = date.substring(0, date.indexOf("."));
                    str = str.substring(0, str.indexOf(" "));
                    break;
                case 2:  //yyyy年MM月dd日 HH:mm:ss
                    str = dates[0] + "年" + dates[1] + "月" + dates[2] + "日 " + array[1];
                    break;
                case 3:  //yyyy年MM月dd日 HH:mm
                    str = dates[0] + "年" + dates[1] + "月" + dates[2] + "日 " + array[1].substring(0, array[1].lastIndexOf(":"));
                    break;
                case 4:  //yyyy年MM月dd日 HH:mm:ss
                    str = dates[0] + "年" + dates[1] + "月" + dates[2] + "日 ";
                    break;
                default:
                    break;
            }
            return str;
        }
    }

    /*获取当前时间的毫秒*/
    public String getSysTimeMillise() {
        long i = System.currentTimeMillis();
        return String.valueOf(i);
    }

    /*获取星期几*/
    public static String getWeek() {
        Calendar cal = Calendar.getInstance();
        int i = cal.get(Calendar.DAY_OF_WEEK);
        switch (i) {
            case 1:
                return "星期日";
            case 2:
                return "星期一";
            case 3:
                return "星期二";
            case 4:
                return "星期三";
            case 5:
                return "星期四";
            case 6:
                return "星期五";
            case 7:
                return "星期六";
            default:
                return "";
        }
    }

    public static Date parse(String str, String pattern, Locale locale) {
        if (str == null || pattern == null) {
            return null;
        }
        try {
            return new SimpleDateFormat(pattern, locale).parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将时间戳转为时间字符串
     *
     * @param millis 时间戳
     * @return 时间字符串
     */
    public static String millis2String(long millis) {
        return millis2String(millis, DEFAULT_SDF);
    }

    /**
     * 将时间戳转为时间字符串
     *
     * @param millis 时间戳
     * @param format 时间格式
     * @return 时间字符串
     */
    public static String millis2String(long millis, @Nullable SimpleDateFormat format) {
        return format.format(new Date(millis));
    }

    /**
     * 将时间字符串转换为时间戳
     *
     * @param timeStr 时间字符串
     * @return 毫秒时间戳
     */
    public static long string2Millis(String timeStr) {
        return TextUtils.isEmpty(timeStr) ? 0 : string2Millis(timeStr, DEFAULT_SDF);
    }

    /**
     * 将时间字符串转换为时间戳
     *
     * @param timeStr 时间字符串
     * @param format  时间格式
     * @return
     */
    public static long string2Millis(String timeStr, @Nullable SimpleDateFormat format) {
        if (TextUtils.isEmpty(timeStr)) return 0;
        long millis = 0;
        try {
            Date date = format.parse(timeStr);
            millis = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            loge(TAG, "String2Millis Error ========> timeStr: " + timeStr);
        }
        return millis;
    }


    /**
     * 将时间字符串转为 Date 类型
     *
     * @param timeStr 时间字符串
     * @param format  时间格式
     * @return Date 类型
     */
    public static Date string2Date(String timeStr, @Nullable String format) {
        if (TextUtils.isEmpty(timeStr)) return null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.parse(timeStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将 Date 类型转为时间字符串
     * <p>格式为 format</p>
     *
     * @param date   Date 类型时间
     * @param format 时间格式
     * @return 时间字符串
     */
    public static String date2String(Date date, @Nullable String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * 将 Date 类型转为时间戳
     *
     * @param date Date 类型时间
     * @return 毫秒时间戳
     */
    public static long date2Millis(Date date) {
        return date == null ? null : date.getTime();
    }

    /**
     * 将时间戳转为 Date 类型
     *
     * @param millis 毫秒时间戳
     * @return Date 类型时间
     */
    public static Date millis2Date(long millis) {
        return new Date(millis);
    }

    /**
     * 将时间转换天数、小时数、分钟数、秒数
     *
     * @param timeStr 时间字符串
     * @return int[0]: 天数 <br> int[1]: 小时数 <br> int[2]: 分钟数 <br> int[3]: 秒数
     */
    public static int[] millis2Array(String timeStr) {
        return millis2Array(timeStr, DEFAULT_SDF);
    }

    /**
     * 将时间转换天数、小时数、分钟数、秒数
     *
     * @param timeStr 时间字符串
     * @param format  时间格式
     * @return int[0]: 天数 <br> int[1]: 小时数 <br> int[2]: 分钟数 <br> int[3]: 秒数
     */
    public static int[] millis2Array(String timeStr, @Nullable SimpleDateFormat format) {
        return TextUtils.isEmpty(timeStr) ? null : millis2Array(string2Millis(timeStr));
    }

    /**
     * 将时间转换天数、小时数、分钟数、秒数
     *
     * @param date date_icon
     * @return int[0]: 天数 <br> int[1]: 小时数 <br> int[2]: 分钟数 <br> int[3]: 秒数
     */
    public static int[] millis2Array(Date date) {
        return date == null ? null : millis2Array(date.getTime());
    }

    /**
     * 将时间转换天数、小时数、分钟数、秒数
     *
     * @param millis 毫秒数
     * @return int[0]: 天数 <br> int[1]: 小时数 <br> int[2]: 分钟数 <br> int[3]: 秒数
     */
    public static int[] millis2Array(long millis) {
        long secondDiff = millis / 1000;
        int days = (int) (secondDiff / (60 * 60 * 24));
        int hours = (int) ((secondDiff - days * (60 * 60 * 24)) / (60 * 60));
        int minutes = (int) ((secondDiff - days * (60 * 60 * 24) - hours * (60 * 60)) / 60);
        int seconds = (int) ((secondDiff - days * (60 * 60 * 24) - hours * (60 * 60) - minutes * 60));
        return new int[]{days, hours, minutes, seconds};
    }

    /**
     * 计算时间差
     *
     * @param startTime 起始时间
     * @param endTime   结束时间
     * @return 毫秒级时间差
     */
    public static long caculateTimeDiff(@NonNull Object startTime, @NonNull Object endTime) {
        return caculateTimeDiff(startTime, endTime, DEFAULT_SDF);
    }

    /**
     * 计算时间差
     *
     * @param startTime 起始时间
     * @param endTime   结束时间
     * @param foramt    时间格式
     * @return 毫秒级时间差
     */
    public static long caculateTimeDiff(@NonNull Object startTime, @NonNull Object endTime, @Nullable SimpleDateFormat foramt) {
        long milliStart, milliEnd;
        if (startTime instanceof String) {
            milliStart = string2Millis((String) startTime, foramt);
        } else if (startTime instanceof Long) {
            milliStart = (long) startTime;
        } else if (startTime instanceof Date) {
            milliStart = ((Date) startTime).getTime();
        } else {
            loge(TAG, "Error startTime in the caculateTimeDiff () method ========> startTime: " + startTime);
            throw new UnsupportedOperationException("startTime foramt error");
        }
        if (endTime instanceof String) {
            milliEnd = string2Millis((String) endTime, foramt);
        } else if (endTime instanceof Long) {
            milliEnd = (long) endTime;
        } else if (endTime instanceof Date) {
            milliEnd = ((Date) endTime).getTime();
        } else {
            loge(TAG, "Error endTime in the caculateTimeDiff () method ========> endTime: " + endTime);
            throw new UnsupportedOperationException("endTime foramt error");
        }
        return (milliEnd - milliStart);
    }

    /**
     * 计算时间差
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return int[0]: 天数 <br> int[1]: 小时数 <br> int[2]: 分钟数 <br> int[3]: 秒数
     */
    public static int[] caculateTimeDiffArray(@NonNull Object startTime, @NonNull Object endTime) {
        return caculateTimeDiffArray(startTime, endTime);
    }

    /**
     * 计算时间差
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param format    时间格式
     * @return int[0]: 天数 <br> int[1]: 小时数 <br> int[2]: 分钟数 <br> int[3]: 秒数
     */
    public static int[] caculateTimeDiffArray(@NonNull Object startTime, @NonNull Object endTime, @Nullable SimpleDateFormat format) {
        return millis2Array(caculateTimeDiff(startTime, endTime, format));
    }

    /**
     * 比较两个时间的大小
     *
     * @param t1 date_icon 1
     * @param t2 date_icon 2
     * @return {@code true}: t1 >= t2<br>{@code false}: t1 < t2
     */
    public static boolean judgeTime(@NonNull Object t1, @NonNull Object t2) {
        return caculateTimeDiff(t2, t1) >= 0;
    }
    /**
     * 判断给定字符串时间是否为今日
     * @param sdate
     * @return boolean
     */
    public static boolean isToday(String sdate){
        boolean b = false;
        Date time = toDate(sdate);
        Date today = new Date();
        if(time != null){
            String nowDate = HOUR_SDF_D.format(today);
            String timeDate = HOUR_SDF_D.format(time);
            if(nowDate.equals(timeDate)){
                b = true;
            }
        }
        return b;
    }

    /**
     * 将字符串转位日期类型
     * @param sdate
     * @return
     */
    public static Date toDate(String sdate) {
        try {
            return HOUR_SDF_D.parse(sdate);
        } catch (ParseException e) {
            return null;
        }
    }

}
