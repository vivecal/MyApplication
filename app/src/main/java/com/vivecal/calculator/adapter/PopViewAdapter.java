package com.vivecal.calculator.adapter;

import android.content.Context;
import android.widget.TextView;

import com.vivecal.calculator.R;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;

/**
 * 描述：
 * 创建日期:2021/6/28
 * 创建人：ChenKun
 */
public class PopViewAdapter extends CommonAdapter<String> {

    public PopViewAdapter(Context context) {
        super(context, R.layout.item_pop_menu, new ArrayList<String>());
    }

    @Override
    protected void convert(ViewHolder holder, String str, int position) {
        TextView tvItem = holder.getView(R.id.tv_item_menu_text);
        tvItem.setText(str);
    }
}
