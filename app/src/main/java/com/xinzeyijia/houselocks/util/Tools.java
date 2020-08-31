package com.xinzeyijia.houselocks.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;


import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 常用工具类
 * Created by ye on 2016/11/15.
 */

public class Tools {

    private static final String TAG = "TUBO_Tools";

    private static final Pattern EMOTION_URL = Pattern.compile("\\[(\\S+?)\\]");
    /**
     * 不可见字符正则
     */
    private static final Pattern ALL_WHITESPACE_PATTERN = Pattern.compile("\\s*|\r|\n|\t");

    /**
     * 邮箱正则
     */
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");

    /**
     * 正则：身份证号码15位
     */
    public static final String REGEX_IDCARD15 = "^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$";
    /**
     * 正则：身份证号码18位
     */
    public static final String REGEX_IDCARD18 = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9Xx])$";

    // 手机，11位 ，1开头，第二位3、4、5、8，
    public static final String phone_mobile = "^[1][3,4,5,7,8][0-9]{9}$";

    // 带区号座机，区号和号码之间用‘-’连接
    public static final String phone_with_zip = "^[0][1-9]{2,3}-[0-9]{5,10}$";

    // 无区号座机
    public static final String phone_without_zip = "^[1-9]{1}[0-9]{5,8}$";

    // 无区号座机
    public static final String phone_400_or_800 = "^(400|800)[0-9]{7}$";

    // 所有电话
    public static final String phone_all_kind = "(" + phone_mobile + ")|("
            + phone_with_zip + ")|(" + phone_without_zip + ")|("
            + phone_400_or_800 + ")";

    /**
     * 电话号码判断
     */
    public static boolean isTelephone(String str) {
//        Pattern pattern = Pattern.compile(phone_mobile);
//        return pattern.matcher(str).matches();
        return str.length() == 11;
    }

    /**
     * 邮箱格式验证
     */
    public static boolean isEmail(String email) {
        Matcher m = EMAIL_PATTERN.matcher(email);
        return m.matches();
    }

    /**
     * 验证身份证号码15位
     *
     * @param string 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isIDCard15(String string) {
        return isMatch(REGEX_IDCARD15, string);
    }

    /**
     * 验证身份证号码18位
     *
     * @param string 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isIDCard18(String string) {
        return isMatch(REGEX_IDCARD18, string);
    }

    /**
     * string是否匹配regex
     *
     * @param regex  正则表达式字符串
     * @param string 要匹配的字符串
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isMatch(String regex, String string) {
        return !isEmpty(string) && Pattern.matches(regex, string);
    }

    /**
     * 判断是否为空
     *
     * @param string
     * @return
     */
    @SuppressLint("NewApi")
    public static boolean NotNull(String string) {
        return string != null && !string.isEmpty() && string.length() != 0;
    }

    public static boolean NotNull(Object obj) {
        if (obj instanceof ArrayList) {
            return NotNull((ArrayList) obj);
        } else if (obj instanceof String) {
            return NotNull((String) obj);
        } else if (obj instanceof List) {
            return NotNull((List) obj);
        } else if (obj instanceof Object[]) {
            return NotNull((Object[]) obj);
        } else {
            return obj != null;
        }
    }

    /*
     * 判断List是否为空
     */
    public static boolean NotNull(ArrayList<?> list) {
        return list != null && !list.isEmpty();
    }

    /**
     * 验证密码是否合法
     */
    public static boolean judgePwd(String pwd) {
        int length = pwd.length();
        return length >= 6 && length <= 14;
    }

    /*
     * List是否为空
     */
    public static boolean NotNull(List<?> list) {
        return list != null && !list.isEmpty();
    }

    public static <T> boolean NotNull(T[] arry) {
        return arry != null && arry.length != 0;
    }

    /**
     * 判断空字符串
     */
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0 || str.trim().length() == 0
                || delAllWhitespace(str).length() == 0;
    }

    /**
     * 判断输入的是否是  汉字、字母、数字、下划线
     */
    public static boolean isInputType(String str) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa50-9a-zA-Z_]*");
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 判断字符串是否是数字
     */
    public static boolean isNumber(String txt) {
        Pattern p = Pattern.compile("[0-9]*");
        Matcher m = p.matcher(txt);
        return m.matches();
    }

    /**
     * 判断money
     */
    public static boolean isMoneyNumber(String txt) {
        Pattern p = Pattern.compile("[0-9\\.]*");
        Matcher m = p.matcher(txt);
        return m.matches();
    }

    /**
     * 删除字符串中所有的不可见字符
     *
     * @param str 源字符串
     * @return 删除所有不可见字符后的字符串
     */
    public static String delAllWhitespace(String str) {
        String result = str;
        if (str != null) {
            Matcher matcher = ALL_WHITESPACE_PATTERN.matcher(str);
            result = matcher.replaceAll("");
        }
        return result;
    }

    /**
     * 判断是否有网络连接
     *
     * @param context
     * @return
     */
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 判断是否是 WI-FI 连接
     *
     * @param context
     * @return
     */
    public static boolean isWifiNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mWiFiNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mWiFiNetworkInfo != null && mWiFiNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                return mWiFiNetworkInfo.isAvailable();
            } else {
                return false;
            }
        }
        return false;
    }

    /**
     * 获取字符长度
     */
    public static int getWordCount(String s) {
        int length = 0;
        for (int i = 0; i < s.length(); i++) {
            int ascii = Character.codePointAt(s, i);
            if (ascii >= 0 && ascii <= 255)
                length++;
            else
                length += 2;

        }
        return length;

    }

    /**
     * 替换表情
     */
    public static String getWordEmojiStr(String s) {
        String result = "";
        if (s.contains("[emoji_")) {

        }
        return result;
    }

    /**
     * 获取屏幕宽度
     */
    public static int getWidthPixels(Context context) {
        final int scale = context.getResources().getDisplayMetrics().widthPixels;
        return scale;
    }

    /**
     * 获取屏幕高度
     */
    public static int getHightPixels(Context context) {
        final int scale = context.getResources().getDisplayMetrics().heightPixels;
        return scale;
    }

    /**
     * 动态计算Listview高度
     */
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight;
        listView.setLayoutParams(params);
    }

    /**
     * 动态计算GridView高度
     */
    public static void setListViewHeightBasedOnChildren(GridView listView, int col, int padding) {
        // 获取listview的adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        // i每次加4，相当于listAdapter.getCount()小于等于4时 循环一次，计算一次item的高度，
        // listAdapter.getCount()小于等于8时计算两次高度相加
        for (int i = 0; i < listAdapter.getCount(); i += col) {
            // 获取listview的每一个item
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            // 获取item的高度和
            totalHeight += listItem.getMeasuredHeight() + padding;
        }

        // 获取listview的布局参数
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        // 设置高度
        params.height = totalHeight;
        // 设置margin
//        ((ViewGroup.MarginLayoutParams) params).setMargins(10, 10, 10, 10);
        // 设置参数
        listView.setLayoutParams(params);
    }

    /**
     * 获取版本Code
     */
    public static int getVersionCode(Context context) {
        return getPackageInfo(context).versionCode;
    }

    /**
     * 获取版本号
     */
    public static String getVersionName(Context context) {
        return getPackageInfo(context).versionName;
    }

    /**
     * 获取版本信息
     */
    private static PackageInfo getPackageInfo(Context context) {
        PackageInfo pi = null;

        try {
            PackageManager pm = context.getPackageManager();
            pi = pm.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);

            return pi;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pi;
    }

    /**
     * 隐藏键盘
     */
    public static void setKeyGone(Context context, EditText editText) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    /**
     * 显示键盘
     */
    public static void setKeyShow(Context context, EditText editText) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, 0);
    }

    /**
     * 获取设备MAC地址
     *
  
     */
    public static String getMac() {
        String macSerial = null;
        String str = "";

        try {
            Process pp = Runtime.getRuntime().exec("cat /sys/class/net/wlan0/address ");
            InputStreamReader ir = new InputStreamReader(pp.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);

            for (; null != str; ) {
                str = input.readLine();
                if (str != null) {
                    macSerial = str.trim();// 去空格
                    break;
                }
            }
        } catch (IOException ex) {
            // 赋予默认值
            ex.printStackTrace();
        }
        return macSerial;
    }
    /**
     * 检测是否有emoji表情
     *
     * @param source
     * @return
     */
    public static boolean containsEmoji(String source) {
        int len = source.length();
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (!isEmojiCharacter(codePoint)) { //如果不能匹配,则该字符是Emoji表情
                return false;
            }
        }
        return true;
    }

    /**
     * 判断是否是Emoji
     *
     * @param codePoint 比较的单个字符
     * @return
     */
    private static boolean isEmojiCharacter(char codePoint) {
        return codePoint == 0x0 || codePoint == 0x9 || codePoint == 0xA || codePoint == 0xD || codePoint >= 0x20 && codePoint <= 0xD7FF || codePoint >= 0xE000 && codePoint <= 0xFFFD;
    }
}
