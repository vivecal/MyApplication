package com.vivecal.calculator.sharedpreference;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;


import com.vivecal.calculator.utils.MD5Util;

import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteOpenHelper;

/**
 * date：2020/11/18
 * author：ChenKun
 * version：1.0
 * 描述：基于SQLCipher的数据库工具
 * 测试：
 * 风险点：
 */
public class VCDBHelper extends SQLiteOpenHelper {

    private static final String TAG = "VCDBHelper";
    private String dbPassword;
    private String dbSQL;

    public VCDBHelper(Context context, String name, int version, String dbSQL) {
        super(context, name, null, version);
        this.dbPassword = MD5Util.encode(name);
        this.dbSQL = dbSQL;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d(TAG, "onCreate");
        if (!TextUtils.isEmpty(dbSQL)) {
            executeSQL(sqLiteDatabase, dbSQL);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade");
        if (!TextUtils.isEmpty(dbSQL)) {
            executeSQL(sqLiteDatabase, dbSQL);
        }
    }

    public SQLiteDatabase getWritableDatabase() {
        return getWritableDatabase(dbPassword);
    }

    /**
     * 执行多条SQL语句查询
     * @param database
     * @param batchSQL
     */
    private void executeSQL(SQLiteDatabase database, String batchSQL){
        try {
            String[] sqlArr = batchSQL.split(";");
            for (int i=0; i<sqlArr.length; i++) {
                database.execSQL(sqlArr[i]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
