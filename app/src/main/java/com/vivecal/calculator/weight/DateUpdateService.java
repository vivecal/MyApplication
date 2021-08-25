package com.vivecal.calculator.weight;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.RemoteViews;

import com.google.gson.Gson;
import com.vivecal.calculator.R;
import com.vivecal.calculator.activity.CustomWidgetActivity;
import com.vivecal.calculator.entity.WidgetSettingVo;
import com.vivecal.calculator.sharedpreference.SharedPreferenceUtil;
import com.vivecal.calculator.utils.DateUtil;
import com.vivecal.calculator.utils.Logs;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 描述：定时更新日期的Service
 * 创建日期:2021/7/2
 * 创建人：ChenKun
 */
public class DateUpdateService extends Service {

    private TimerTask timerTask;
    private Timer timer = new Timer();
    private String showingText;
    private String titleFormat;//小标题文本
    private UpdateBinder updateBinder = new UpdateBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return updateBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Logs.d("MyWidget","DateUpdateService => onCreate");
        timerTask = new TimerTask() {
            @Override
            public void run() {
                updateTime();
            }
        };
        timer.schedule(timerTask,0,1000);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Logs.d("MyWidget","DateUpdateService => onStartCommand");
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Logs.d("MyWidget","DateUpdateService => onDestroy");
        super.onDestroy();
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        Logs.d("MyWidget","DateUpdateService => onTaskRemoved");
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        Logs.d("MyWidget","DateUpdateService => onTrimMemory");
    }

    public void updateTime(){
        //计算时间差
        String awayFromTheDate = formatDateShowing();
        RemoteViews remoteViews = new RemoteViews(this.getPackageName(), R.layout.widget_layout);
        remoteViews.setTextViewText(R.id.widgetTv,awayFromTheDate);//时间差
        remoteViews.setTextViewText(R.id.widgetTvTitle, titleFormat);//小标题
        Logs.d("WIDGET_CONTENT", titleFormat +awayFromTheDate);
        showingText = awayFromTheDate;

        ComponentName componentName = new ComponentName(this,DeskWidgetProvider.class);
        AppWidgetManager.getInstance(this).updateAppWidget(componentName,remoteViews);
    }

    /**
     * 描述：处理日期
     * 创建时间：2021/7/5 9:00
     * 创建人：ChenKun
     */
    private String formatDateShowing(){
        long theTimeMills = DateUtil.parseTimeMills(CustomWidgetActivity.DEFAULT_DATE, "yyyy年MM月dd日 HH:mm:ss");
        long currentTime = System.currentTimeMillis();
        WidgetSettingVo widgetSettingVo = null;
        try {
            widgetSettingVo = new Gson().fromJson(SharedPreferenceUtil.getInfoFormShared("Widget_Info"), WidgetSettingVo.class);
        } catch (Exception e){
            Logs.d("WIDGET_CONTENT","WidgetSettingVo类型转换失败");
        }
        String awayFromTheDate;
        if(currentTime > theTimeMills){
            awayFromTheDate = DateUtil.timeFormat(currentTime - theTimeMills);
            titleFormat = String.format("%s已经:",widgetSettingVo == null?CustomWidgetActivity.DEFAULT_CUSTOM_TEXT:widgetSettingVo.getCustomText());
        } else {
            awayFromTheDate =DateUtil.timeFormat(theTimeMills - currentTime);
            titleFormat = String.format("%s还有:",widgetSettingVo == null?CustomWidgetActivity.DEFAULT_CUSTOM_TEXT:widgetSettingVo.getCustomText());
        }
        return awayFromTheDate;
    }

    public class UpdateBinder extends Binder{
        public DateUpdateService getService(){
            return DateUpdateService.this;
        }
    }

    public String getShowingText(){
        return showingText;
    }

    public String getTitleText(){
        return titleFormat;
    }
}
