package com.vivecal.calculator.sharedpreference;


import com.vivecal.calculator.MyApplication;

/**
 * date：2020/11/17
 * author：ChenKun
 * version：1.0
 * 描述：数据库存储工具类
 * 测试：
 * 风险点：
 */
public class SharedPreferenceUtil {
    private static VCSharedPreferenceDB sharedPreferenceDB = new VCSharedPreferenceDB(MyApplication.getInstance());

    public static boolean removeData(String key) {
        return sharedPreferenceDB.removeData(key);
    }

    public static boolean removeAllData() {
        return sharedPreferenceDB.removeAllData();
    }

    public static String getInfoFormShared(String key) {
        return sharedPreferenceDB.getInfoFormShared(key);
    }

    public static String getInfoFormShared(String key, String defaultValue) {
        return sharedPreferenceDB.getInfoFormShared(key,defaultValue);
    }

    public static boolean setInfoToShared(String key, String value) {
        return sharedPreferenceDB.setInfoToShared(key,value);
    }

    public static boolean getInfoFromShared(String key, boolean defaultValue) {
        return sharedPreferenceDB.getInfoFromShared(key,defaultValue);
    }

    public static boolean setInfoToShared(String key, boolean value) {
        return sharedPreferenceDB.setInfoToShared(key,value);
    }

    public static int getInfoFromShared(String key, int defaultValue) {
        return sharedPreferenceDB.getInfoFromShared(key,defaultValue);
    }

    public static boolean setInfoToShared(String key, int value) {
        return sharedPreferenceDB.setInfoToShared(key,value);
    }

    public static long getInfoFromShared(String key, long defaultValue) {
        return sharedPreferenceDB.getInfoFromShared(key,defaultValue);
    }

    public static boolean setInfoToShared(String key, long value) {
        return sharedPreferenceDB.setInfoToShared(key,value);
    }
}
