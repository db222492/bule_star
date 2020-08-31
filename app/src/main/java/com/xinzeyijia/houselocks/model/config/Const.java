
package com.xinzeyijia.houselocks.model.config;

/**
 * TCP服务器配置
 */
public interface Const {
     String TCP_HOST = "192.168.1.64";// AndroidConfig.getIpAdress(SUtils.getApp());
     String UDP_HOST = "172.16.9.12";
     String UM_APPKEY = "5d47beb83fc1953682000a83";
     String UM_SECRET = "5d47beb83fc1953682000a83";
     String AIOT_APPKEY = "555a58ea7343496886638dbda9d8a73c";
     String AIOT_SECRET = "cb25c9e6adfb4c12a9768c035e0e319d";
     String WEIXIN_APPKEY = "wxc2d59ee692a839ab";
     String WEIXIN_SECRET = "f213db3c04a5137cba030562e2c3d01f";
     String CLIENT = "client";
     String SEVER = "sever";
     int TCP_PORT = 6667;
     int UDP_PORT = 5004;
     int START_SUCCESS = 200;
     int LINK_SUCCESS = 300;
     int FAILED = -1;
}
