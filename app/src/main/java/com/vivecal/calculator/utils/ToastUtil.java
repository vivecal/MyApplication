package com.vivecal.calculator.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * 描述：Toast
 * 创建日期:2021/6/23
 * 创建人：ChenKun
 */
public class ToastUtil {

    public static void showToastShort(Context context,String message){
        Toast mToast = Toast.makeText(context,message,Toast.LENGTH_SHORT);
        mToast.setGravity(Gravity.CENTER,0,0);
        mToast.show();
    }

    public static void showToastLong(Context context,String message){
        Toast mToast = Toast.makeText(context,message,Toast.LENGTH_LONG);
        mToast.setGravity(Gravity.CENTER,0,0);
        mToast.show();
    }
}
