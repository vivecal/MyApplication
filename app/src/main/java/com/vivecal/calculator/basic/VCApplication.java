package com.vivecal.calculator.basic;

import android.app.Application;

public abstract class VCApplication extends Application {

    private static VCApplication vcApplication;

    public VCApplication() {
    }

    public static VCApplication getInstance(){
        return vcApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        vcApplication = this;
    }
}
