package com.xinzeyijia.houselocks.base;

/**
 * Created by EDZ on 2018/11/14.
 */

/**
 * switch (messageBean.getCode()) {
 * case 1001:
 * <p>
 * break;
 * case 1004:
 * break;
 * case 3004:
 * break;
 * <p>
 * }
 */
public interface BaseView {
    //加载错误
    void showMessage(String s);

    void startRegist();

    void startLogin();

    void startBind();
}
