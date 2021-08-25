package com.vivecal.calculator.adapter;

import android.content.Context;
import android.widget.TextView;

import com.vivecal.calculator.R;
import com.vivecal.calculator.entity.CalResultVo;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;

/**
 * 描述：计算结果Adapter
 * 创建日期:2021/5/25
 * 创建人：ChenKun
 */
public class FreightAdapter extends CommonAdapter<CalResultVo> {

    public FreightAdapter(Context context) {
        super(context, R.layout.item_freight_rv, new ArrayList<CalResultVo>());
    }

    @Override
    protected void convert(ViewHolder holder, final CalResultVo calResultVo, int position) {
        TextView nicknameTv = holder.getView(R.id.tvNickName);
        TextView freightTv = holder.getView(R.id.tvFreight);
        TextView paperCountTv = holder.getView(R.id.tvPaperCount);
        TextView detailTv = holder.getView(R.id.tvDataDetail);

        nicknameTv.setText(calResultVo.getNickName());
        freightTv.setText(calResultVo.getFreight());
        paperCountTv.setText(calResultVo.getPaperCount());
        detailTv.setText(calResultVo.getDetail());
    }

}
