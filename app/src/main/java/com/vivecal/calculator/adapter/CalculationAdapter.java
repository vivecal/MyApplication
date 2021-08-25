package com.vivecal.calculator.adapter;

import android.content.Context;
import android.widget.TextView;

import com.vivecal.calculator.R;
import com.vivecal.calculator.entity.CalculationVo;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述：计算结果Adapter
 * 创建日期:2021/5/25
 * 创建人：ChenKun
 */
public class CalculationAdapter extends CommonAdapter<CalculationVo> {

    private Context context;
    private boolean calculateMode = false;//计算模式
    private List<CalculationVo> selectedData = new ArrayList<>();//已选数据

    public CalculationAdapter(Context context) {
        super(context, R.layout.item_history_rv, new ArrayList<CalculationVo>());
        this.context = context;
    }

    @Override
    protected void convert(ViewHolder holder, final CalculationVo calculationVo, int position) {
        TextView finalResultTv = holder.getView(R.id.tvDataAfter);
        TextView itemSumTv = holder.getView(R.id.tvDataBefore);
        TextView detailTv = holder.getView(R.id.tvDataDetail);
        TextView nicknameTv = holder.getView(R.id.tvNickName);

        finalResultTv.setText(calculationVo.getResult());
        itemSumTv.setText(calculationVo.getTotal());
        detailTv.setText(calculationVo.getDetail());
        nicknameTv.setText(calculationVo.getNickname());
    }
}
