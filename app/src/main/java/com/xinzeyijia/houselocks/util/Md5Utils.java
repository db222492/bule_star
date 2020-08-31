package com.xinzeyijia.houselocks.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @ClassName Md5Utils
 * @Description: TODO
 * @Author wangzhongpeng
 * @Date 2019/7/5
 * @Version V1.0
 **/
public class Md5Utils {

    public static String encrypt_md5(String intStr)
    {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");  //获取MD5加密实例
            char[] charArray = intStr.toCharArray();
            byte[] byteArray = new byte[charArray.length];
            for(int i =0;i<charArray.length;i++){
                byteArray[i]= (byte)charArray[i];
            }
            byte[] md5Bytes = md.digest(byteArray);
            StringBuffer hexValue = new StringBuffer();
            for(int i=0;i<md5Bytes.length;i++){
                int val = ((int)md5Bytes[i])& 0xff;
                if(val < 16){
                    hexValue.append("0");
                }
                hexValue.append(Integer.toHexString(val));
            }
            return hexValue.toString();
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Invalid algorithm.");
            return null;
        }
    }

    public static String bytes2Hex(byte[] bts)     //加密字节数组转十六进制字符串
    {
        String des = "";
        String tmp = null;
        for (int i = 0; i < bts.length; i++) {
            tmp = (Integer.toHexString(bts[i] & 0xFF));  //转十六进制字符
            if (tmp.length() == 1) {
                des += "0";
            }
            des += tmp;
        }
        return des;
    }

    public static String convertMD5(String inStr){
        char[] a = inStr.toCharArray();
        for(int  i=0;i<a.length;i++){
            a[i] = (char)(a[i]^'t');
        }
        String s = new String(a);
        return s;
    }

}
