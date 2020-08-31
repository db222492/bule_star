package com.xinzeyijia.houselocks.util.socketutil;

/**
 * Created by zhangzc on 18-6-6.
 */

public class SocketGetconfig {




    /**
     * type : response_getconfig
     * errno : 0
     * SSID1 :
     * KEY1 :
     */

    private String type;
    private int errno=-1;
    private String SSID1;
    private String KEY1;
    private int batterypercent;
    private String uploadstatus;

    public String getUploadstatus() {
        return uploadstatus;
    }

    public void setUploadstatus(String uploadstatus) {
        this.uploadstatus = uploadstatus;
    }

    public void setBatterypercent(int batterypercent) {
        this.batterypercent = batterypercent;
    }

    public int getBatterypercent() {
        return batterypercent;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "SocketGetconfig{" +
                "type='" + type + '\'' +
                ", errno=" + errno +
                ", SSID1='" + SSID1 + '\'' +
                ", KEY1='" + KEY1 + '\'' +
                ", batterypercent=" + batterypercent +
                ", uploadstatus='" + uploadstatus + '\'' +
                '}';
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getErrno() {
        return errno;
    }

    public void setErrno(int errno) {
        this.errno = errno;
    }

    public String getSSID1() {
        return SSID1;
    }

    public void setSSID1(String SSID1) {
        this.SSID1 = SSID1;
    }

    public String getKEY1() {
        return KEY1;
    }

    public void setKEY1(String KEY1) {
        this.KEY1 = KEY1;
    }
}
