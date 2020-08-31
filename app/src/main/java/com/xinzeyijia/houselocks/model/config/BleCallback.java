package com.xinzeyijia.houselocks.model.config;

/**
 *
 * Created by LiuLei on 2017/10/23.
 */

public interface BleCallback<T> {
    void onWriteSuccess(T value);
}
