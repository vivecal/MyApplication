package com.vivecal.calculator.utils;

import android.util.Log;

/**
 * 描述：日志打印类
 * 创建日期:2021/6/29
 * 创建人：ChenKun
 */
public class Logs {

    public static boolean SHOW_LOG = true;

    public static void d(String tag , String msg){
        if(!SHOW_LOG){
            return;
        }
        Log.d(tag,msg);
    }

    public static void d(String tag , String msg , Object... values){
        if(!SHOW_LOG){
            return;
        }
        Log.d(tag,String.format(msg , values));
    }

    public static void e(String tag , String msg){
        if(!SHOW_LOG){
            return;
        }
        Log.e(tag,msg);
    }

    public static void e(String tag , String msg , Object... values){
        if(!SHOW_LOG){
            return;
        }
        Log.e(tag,String.format(msg , values));
    }
}
