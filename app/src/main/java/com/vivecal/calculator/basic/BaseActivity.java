package com.vivecal.calculator.basic;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.vivecal.calculator.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * 描述：基础 Activity
 * 创建日期:2021/6/22
 * 创建人：ChenKun
 */
public abstract class BaseActivity extends FragmentActivity {

    protected Activity activity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //取消标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        activity = this;
        if(getLayout() != 0){
            setContentView(getLayout());
        }
        //沉浸式标题栏
        ImmersionBar.with(this)
                .statusBarDarkFont(true)//状态栏字体设置深色
                .init();
        EventBus.getDefault().register(this);

        initViews();
    }

    @Override
    protected void onDestroy() {
        ImmersionBar.with(this).destroy(); //必须调用该方法，防止内存泄漏
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe
    public void onEvent(Object object) {

    }

    /**
     * 描述：设置标题
     * 创建时间：2021/6/23 8:50
     * 创建人：ChenKun
     */
    protected void setPageTitle(String title){
        TextView tvTitle = findViewById(R.id.tv_title_content);
        if(tvTitle != null){
            tvTitle.setText(title);
        }
    }

    /**
     * 描述：设置返回事件
     * 创建时间：2021/6/25 16:48
     * 创建人：ChenKun
     */
    protected void setLefAction(){
        ImageView ivTitleBack = findViewById(R.id.iv_title_bar_back);
        if(ivTitleBack!= null){
            ivTitleBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }

    protected void startFragment(int id, Fragment fragment){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(id,fragment);
        transaction.commitAllowingStateLoss();
    }

    protected void removeFragment(Fragment fragment){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.remove(fragment);
        transaction.commitAllowingStateLoss();
    }

    protected abstract void initViews();
    protected abstract int getLayout();
}
