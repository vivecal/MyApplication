package com.vivecal.calculator.provider;

import android.content.Context;


import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteOpenHelper;

/**
 * 描述：
 * 创建日期:2021/7/1
 * 创建人：ChenKun
 */
public class HDBHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 10;
    private static final String DB_NAME = "YZL_AND_CK";


    public HDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, factory, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE IF NOT EXISTS %s " +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT,";
        sqLiteDatabase.execSQL("");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
