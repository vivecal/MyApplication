package com.vivecal.calculator.weight;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.widget.RemoteViews;

import com.vivecal.calculator.R;
import com.vivecal.calculator.activity.CustomWidgetActivity;
import com.vivecal.calculator.utils.Logs;

/**
 * 描述：定义了App Widget的基本生命周期
 * 创建日期:2021/7/2
 * 创建人：ChenKun
 */
public class DeskWidgetProvider extends AppWidgetProvider {

    private boolean serviceBind = false;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            serviceBind = true;
            Logs.d("MyWidget","创建小组件： 服务绑定成功");
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            serviceBind = false;
            Logs.d("MyWidget","删除小组件： 服务解绑成功");
        }
    };

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        Logs.d("MyWidget","第一次被创建时调用这个方法");
        //开启日期更新服务
        Intent serviceIntent = new Intent(context, DateUpdateService.class);
        context.startService(serviceIntent);
    }

    @Override
    public void onDisabled(Context context) {
        Logs.d("MyWidget","当最后一个App Widget被删除时调用该方法");
        //停止日期更新服务
        if(serviceBind){
            context.unbindService(serviceConnection);
        }
        context.stopService(new Intent(context,DateUpdateService.class));
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        //调用父类的onReceive方法不能少，否则就无法监听到onUpdate事件了
        super.onReceive(context, intent);
        Logs.d("MyWidget","接收广播事件 : %s",intent.getAction());
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {
        Logs.d("MyWidget","在到达指定的更新时间之后或者当用户向桌面添加App Widget时调用这个方法");
    }


    @Override
    public void onDeleted(Context context, int[] appWidgetIds){
        Logs.d("MyWidget","App Widget被删除时调用这个方法");

    }


}
