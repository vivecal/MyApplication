package com.vivecal.calculator.weight;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

/**
 * 描述：
 * 创建日期:2021/6/30
 * 创建人：ChenKun
 */
public class HappyDisappearView extends View {

    private Context context;

    public HappyDisappearView(Context context) {
        super(context,null,0,0);
    }

    public HappyDisappearView(Context context, @Nullable AttributeSet attrs) {
        super(context,attrs,0,0);
    }

    public HappyDisappearView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context,attrs,defStyleAttr,0);
    }

    public HappyDisappearView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
        init();
    }

    private void init(){
        LayoutInflater inflater = LayoutInflater.from(context);
    }
}
