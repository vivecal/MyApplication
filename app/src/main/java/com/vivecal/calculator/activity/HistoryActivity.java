package com.vivecal.calculator.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.vivecal.calculator.R;
import com.vivecal.calculator.adapter.CalculationAdapter;
import com.vivecal.calculator.basic.BaseActivity;
import com.vivecal.calculator.entity.CalculationVo;
import com.vivecal.calculator.sharedpreference.SharedPreferenceUtil;
import com.vivecal.calculator.utils.ToastUtil;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述：历史数据
 * 创建时间：2021/6/23 8:41
 * 创建人：ChenKun
 */
public class HistoryActivity extends BaseActivity implements View.OnClickListener {

    private CalculationAdapter historyAdapter;
    private RecyclerView historyRv;
    private ImageView ivRightIconClear;//清空按钮

    @Override
    protected int getLayout() {
        return R.layout.activity_history;
    }

    @Override
    protected void initViews() {
        setPageTitle(getString(R.string.activity_title_history));
        setLefAction();

        historyRv = findViewById(R.id.historyRv);
        //从右往左，第二个按钮
        ivRightIconClear = findViewById(R.id.iv_title_right_icon);
        ivRightIconClear.setImageResource(R.drawable.ic_baseline_clear);

        ivRightIconClear.setVisibility(View.VISIBLE);

        ivRightIconClear.setOnClickListener(this);

        historyAdapter = new CalculationAdapter(activity);
        historyRv.setAdapter(historyAdapter);
        historyRv.setLayoutManager(new LinearLayoutManager(activity));

        showHistoryData();
    }

    /**
     * 描述：显示历史记录
     * 创建时间：2021/5/26 14:31
     * 创建人：ChenKun
     */
    private void showHistoryData(){
        if(!TextUtils.isEmpty(SharedPreferenceUtil.getInfoFormShared(MainActivity.SP_SAVE_KEY))){
            try {
                JSONArray jsonArray = new JSONArray(SharedPreferenceUtil.getInfoFormShared(MainActivity.SP_SAVE_KEY));
                List<CalculationVo> savedData = new ArrayList<>();
                for(int i=0; i<jsonArray.length(); i++){
                    //最新的添加到首位展示
                    savedData.add(0,new Gson().fromJson(jsonArray.get(i).toString(),CalculationVo.class));
                }
                if(savedData.size()>0){
                    historyAdapter.getDatas().clear();
                    historyAdapter.getDatas().addAll(savedData);
                    historyAdapter.notifyDataSetChanged();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            historyAdapter.getDatas().clear();
            historyAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_title_right_icon) {//清空
            SharedPreferenceUtil.removeData(MainActivity.SP_SAVE_KEY);
            showHistoryData();
            ToastUtil.showToastShort(activity, "清除成功！");
        }
    }
}