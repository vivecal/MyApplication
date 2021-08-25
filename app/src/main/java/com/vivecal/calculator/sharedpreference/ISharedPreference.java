package com.vivecal.calculator.sharedpreference;

/**
 * date：2020/11/17
 * author：ChenKun
 * version：1.0
 */
public interface ISharedPreference {
    /**
     * 删除本地key对应的value
     * @param key 待删除key
     * @return
     */
    boolean removeData(String key);

    /**
     * 删除所有数据
     * @return
     */
    boolean removeAllData();

    String getInfoFormShared(String key);

    String getInfoFormShared(String key, String defaultValue);

    boolean setInfoToShared(String key, String value);

    boolean getInfoFromShared(String key, boolean defaultValue);

    boolean setInfoToShared(String key, boolean value);

    int getInfoFromShared(String key, int defaultValue);

    boolean setInfoToShared(String key, int value);

    long getInfoFromShared(String key, long defaultValue);

    boolean setInfoToShared(String key, long value);
}
