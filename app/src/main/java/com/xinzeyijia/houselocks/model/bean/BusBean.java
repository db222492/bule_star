package com.xinzeyijia.houselocks.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by EDZ on 2018/12/27.
 * 此类用来传递不同类型的对象
 */

public class BusBean implements Parcelable {
    private String type;
    private Object t;

    public BusBean(String type, Object t) {
        this.type = type;
        this.t = t;
    }

    protected BusBean(Parcel in) {
        type = in.readString();
    }

    public static final Creator<BusBean> CREATOR = new Creator<BusBean>() {
        @Override
        public BusBean createFromParcel(Parcel in) {
            return new BusBean(in);
        }

        @Override
        public BusBean[] newArray(int size) {
            return new BusBean[size];
        }
    };

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getT() {
        return   t;
    }

    public void setT(Object t) {
        this.t = t;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(type);
    }
}
