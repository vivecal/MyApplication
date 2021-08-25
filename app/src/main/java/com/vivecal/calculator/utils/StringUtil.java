package com.vivecal.calculator.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;

import java.util.List;

import static android.content.Context.ACTIVITY_SERVICE;

/**
 * 描述：
 * 创建日期:2021/6/22
 * 创建人：ChenKun
 */
public class StringUtil {
    /**
     * 描述：随机单词
     * 创建时间：2021/6/22 16:56
     * 创建人：ChenKun
     */
    public static String randomWord(int length) {
        String word = "";
        for (int i = 0; i < length; i++) {
            if(i==0){
                word += (char) randomChar(0);
            } else {
                word += (char) randomChar(1);
            }
        }
        return word;
    }

    /**
     * 描述：随机字母
     * 创建时间：2021/6/22 16:55
     * 创建人：ChenKun
     * @param flag 0：大写  1：小写
     */
    private static byte randomChar(int flag) {
        byte resultBt;
        byte bt = (byte) (Math.random() * 26);// 0 <= bt < 26
        if (flag == 0) {
            resultBt = (byte) (65 + bt);
        } else {
            resultBt = (byte) (97 + bt);
        }
        return resultBt;
    }

    public static boolean isActivityExisted(Context context, String pkgName){
        /**获取ActivityManager*/
        ActivityManager activityManager = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            List<ActivityManager.AppTask> list = activityManager.getAppTasks();
            ActivityManager.RecentTaskInfo taskInfo;
            for (ActivityManager.AppTask appTask : list) {
                taskInfo = appTask.getTaskInfo();
                Logs.d("bring2Front", "1111 bring2Front: " + taskInfo.affiliatedTaskId);
                return true;
            }
        } else {
            /**获得当前运行的task(任务)*/
            List<ActivityManager.RunningTaskInfo> taskInfoList = activityManager.getRunningTasks(100);
            for (ActivityManager.RunningTaskInfo taskInfo : taskInfoList) {
                /**找到本应用的 task，并将它切换到前台*/
                if (taskInfo.topActivity.getPackageName().equals(context.getPackageName())) {
                    Logs.d("bring2Front", "222 bring2Front: " + taskInfo.topActivity.getPackageName());
                    return true;
                }
            }
        }
        return false;
    }
}
