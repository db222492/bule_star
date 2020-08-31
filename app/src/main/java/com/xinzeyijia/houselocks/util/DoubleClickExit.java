package com.xinzeyijia.houselocks.util;

/**
 * Created by liyu on 2017/1/5.
 */

public class DoubleClickExit {
    public static long mLastClick = 0L;
    private static final int THRESHOLD = 5000;

    public static boolean check() {
        long now = System.currentTimeMillis();
        boolean b = now - mLastClick < THRESHOLD;
        if (!b) {
            mLastClick = now;
        }
        return b;
    }
}
