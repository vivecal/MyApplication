package com.vivecal.calculator.viewmodle;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

/**
 * 描述：倒数日的 VM
 * 创建日期:2021/7/9
 * 创建人：ChenKun
 */
public class DateCountViewModel extends ViewModel {
    private MutableLiveData<String> dateLiveData = new MutableLiveData<>();

    public MutableLiveData<String> getDateLiveData(){
        return dateLiveData;
    }

    public static class DateFactory implements ViewModelProvider.Factory{

        public DateFactory() {
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> aClass) {
            return (T) new DateCountViewModel();
        }
    }
}
