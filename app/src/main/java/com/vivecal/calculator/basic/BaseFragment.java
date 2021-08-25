package com.vivecal.calculator.basic;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vivecal.calculator.MyApplication;
import com.vivecal.calculator.utils.ToastUtil;

/**
 * 描述：基础Fragment
 * 创建日期:2021/7/16
 * 创建人：ChenKun
 */
public abstract class BaseFragment extends Fragment {

    private View contentView;
    private boolean isFirstLoad = false;

    public BaseFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if(contentView == null){
            isFirstLoad = true;
            contentView = inflater.inflate(getContentLayout(),container,false);
        } else {
            isFirstLoad = false;
            ViewGroup var4 = (ViewGroup)this.contentView.getParent();
            if (null != var4) {
                var4.removeView(this.contentView);
            }
        }
        return contentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(isFirstLoad){
            initView();
        }
    }

    protected View findViewById(int id){
        View view = null;
        if(contentView !=null){
            view = contentView.findViewById(id);
        }
        return view;
    }

    protected void toast(String msg){
        ToastUtil.showToastShort(MyApplication.getInstance(),msg);
    }

    protected abstract int  getContentLayout();
    protected abstract void initView();
}
