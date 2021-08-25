package com.vivecal.calculator;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.vivecal.calculator.basic.VCApplication;
import com.vivecal.calculator.utils.Logs;


/**
 * date：2020/11/13
 * author：ChenKun
 * version：1.0
 * 描述：
 * 测试：
 * 风险点：
 */
public class MyApplication extends VCApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle bundle) {
                Logs.d("ActivityLifecycle","onActivityCreated ==>" + activity.getClass().getSimpleName());
            }

            @Override
            public void onActivityStarted(@NonNull Activity activity) {
                Logs.d("ActivityLifecycle","onActivityStarted ==>" + activity.getClass().getSimpleName());
            }

            @Override
            public void onActivityResumed(@NonNull Activity activity) {
                Logs.d("ActivityLifecycle","onActivityResumed ==>" + activity.getClass().getSimpleName());
            }

            @Override
            public void onActivityPaused(@NonNull Activity activity) {
                Logs.d("ActivityLifecycle","onActivityPaused ==>" + activity.getClass().getSimpleName());
            }

            @Override
            public void onActivityStopped(@NonNull Activity activity) {
                Logs.d("ActivityLifecycle","onActivityStopped ==>" + activity.getClass().getSimpleName());
            }

            @Override
            public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle bundle) {
                Logs.d("ActivityLifecycle","onActivitySaveInstanceState ==>" + activity.getClass().getSimpleName());
            }

            @Override
            public void onActivityDestroyed(@NonNull Activity activity) {
                Logs.d("ActivityLifecycle","onActivityDestroyed ==>" + activity.getClass().getSimpleName());
            }
        });
    }
}
