package com.vivecal.calculator.sharedpreference;

import android.content.Context;


import com.vivecal.calculator.utils.MD5Util;

import net.sqlcipher.Cursor;
import net.sqlcipher.database.SQLiteDatabase;

/**
 * date：2020/11/17
 * author：ChenKun
 * version：1.0
 * 描述：数据库存储工具
 * 测试：
 * 风险点：
 */
public class VCSharedPreferenceDB implements ISharedPreference{

    private static int VC_DB_VERSION = 1;//数据库版本
    private static String VC_DB_NAME = "Vivecal.db";//数据库文件名
    private static final String VC_TABLE_NAME = "Vivecal";//数据库表名
    //建表SQL语句(数据格式为：KEY、VALUE)
    private static final String VC_TABLE_SQL = "CREATE TABLE " + VC_TABLE_NAME +
            "(KEY TEXT PRIMARY KEY,VALUE TEXT)";

    private VCDBHelper vcdbHelper;

    public VCSharedPreferenceDB(Context context) {
        SQLiteDatabase.loadLibs(context);
        vcdbHelper = new VCDBHelper(context,VC_TABLE_NAME,VC_DB_VERSION,VC_TABLE_SQL);
    }

    @Override
    public boolean removeData(String key) {
        SQLiteDatabase sqLiteDatabase = vcdbHelper.getWritableDatabase();
        try {
            //手动设置开始事务
            sqLiteDatabase.beginTransaction();
            sqLiteDatabase.delete(VC_TABLE_NAME,"KEY=?",new String[]{MD5Util.encode(key)});
            //设置事务处理成功，不设置会自动回滚不提交
            sqLiteDatabase.setTransactionSuccessful();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            //处理完成
            sqLiteDatabase.endTransaction();
        }
        return true;
    }

    @Override
    public boolean removeAllData() {
        SQLiteDatabase sqLiteDatabase = vcdbHelper.getWritableDatabase();
        try {
            //手动设置开始事务
            sqLiteDatabase.beginTransaction();
            sqLiteDatabase.delete(VC_TABLE_NAME,null,null);
            //设置事务处理成功，不设置会自动回滚不提交
            sqLiteDatabase.setTransactionSuccessful();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            //处理完成
            sqLiteDatabase.endTransaction();
        }
        return true;
    }

    @Override
    public String getInfoFormShared(String key) {
        return getInfoFromDatabase(key,"");
    }

    @Override
    public String getInfoFormShared(String key, String defaultValue) {
        return getInfoFromDatabase(key,defaultValue);
    }

    @Override
    public boolean setInfoToShared(String key, String value) {
        return setInfoToDatabase(key,value);
    }

    @Override
    public boolean getInfoFromShared(String key, boolean defaultValue) {
        String defaultStr = String.valueOf(defaultValue);
        return Boolean.parseBoolean(getInfoFromDatabase(key,defaultStr));
    }

    @Override
    public boolean setInfoToShared(String key, boolean value) {
        String valueStr = String.valueOf(value);
        return setInfoToDatabase(key,valueStr);
    }

    @Override
    public int getInfoFromShared(String key, int defaultValue) {
        String defaultStr = String.valueOf(defaultValue);
        return Integer.parseInt(getInfoFromDatabase(key,defaultStr));
    }

    @Override
    public boolean setInfoToShared(String key, int value) {
        String valueStr = String.valueOf(value);
        return setInfoToDatabase(key,valueStr);
    }

    @Override
    public long getInfoFromShared(String key, long defaultValue) {
        String defaultStr = String.valueOf(defaultValue);
        return Long.parseLong(getInfoFromDatabase(key,defaultStr));
    }

    @Override
    public boolean setInfoToShared(String key, long value) {
        String valueStr = String.valueOf(value);
        return setInfoToDatabase(key,valueStr);
    }

    /**
     * description:  将数据存储到本地加密数据库
     * date: 2020/11/18 13:31
     * author: ChenKun
     */
    private boolean setInfoToDatabase(String key, String value){
        Cursor cursor = null;
        try {
            String sql = "select * from " + VC_TABLE_NAME + " where KEY = ?";
            SQLiteDatabase sqLiteDatabase = vcdbHelper.getWritableDatabase();
            cursor = sqLiteDatabase.rawQuery(sql,new String[]{MD5Util.encode(key)});
            if(cursor.getCount() > 0){
                sql = "update " + VC_TABLE_NAME + " set VALUE = ? where KEY = ?";
                sqLiteDatabase.execSQL(sql,new String[]{value,MD5Util.encode(key)});
            } else {
                sql = "insert into " + VC_TABLE_NAME + "(KEY, VALUE) values(?, ?)";
                sqLiteDatabase.execSQL(sql,new String[]{MD5Util.encode(key),value});
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
           if(cursor != null){
               cursor.close();
           }
        }
        return true;
    }

    /**
     * description: 从本地加密数据库查询数据
     * date: 2020/11/18 11:06
     * author: ChenKun
     */
    private String getInfoFromDatabase(String key, String defaultValue) {
        String strValue = defaultValue;
        SQLiteDatabase sqLiteDatabase = vcdbHelper.getWritableDatabase();
        try {
            //手动设置开始事务
            sqLiteDatabase.beginTransaction();
            String sql = "select * from " + VC_TABLE_NAME + " where KEY = ?";
            Cursor cursor = sqLiteDatabase.rawQuery(sql,new String[]{MD5Util.encode(key)});
            //如果查到数据
            if(cursor.moveToFirst()){
                strValue =cursor.getString(cursor.getColumnIndex("VALUE"));
            }
            //关闭游标
            cursor.close();
            //设置事务处理成功，不设置会自动回滚不提交
            sqLiteDatabase.setTransactionSuccessful();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            //处理完成
            sqLiteDatabase.endTransaction();
        }
        return strValue;
    }
}
