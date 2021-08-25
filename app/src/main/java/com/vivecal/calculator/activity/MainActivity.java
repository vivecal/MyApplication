package com.vivecal.calculator.activity;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.vivecal.calculator.R;
import com.vivecal.calculator.basic.BaseActivity;
import com.vivecal.calculator.entity.CalculationVo;
import com.vivecal.calculator.fragment.MethodModifyFragment;
import com.vivecal.calculator.sharedpreference.SharedPreferenceUtil;
import com.vivecal.calculator.utils.Logs;
import com.vivecal.calculator.utils.StringUtil;
import com.vivecal.calculator.utils.ToastUtil;
import com.vivecal.calculator.weight.PopUnderView;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.DecimalFormat;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    //输入框内容
    private StringBuilder inputStr = new StringBuilder();
    //输入框View
    private TextView tvCalculateResult;
    //本地数据存储key
    public static final String SP_SAVE_KEY = "YZL_CK_CAL_DATA";
    //退格
    private ImageView btnBackspace;
    //昵称
    private TextView tvNickName;
    //计算后数据
    private TextView tvDataAfter;
    //计算前数据
    private TextView tvDataBefore;
    //数据明细
    private TextView tvDataDetail;

    private String calNo1,calNo2,calNo3;
    private ImageView ivTitleRight;

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews() {
        setLefAction();
        getViews();
    }

    /**
     * 描述：初始化 View
     * 创建时间：2021/5/25 13:56
     * 创建人：ChenKun
     */
    private void getViews(){
        Button number0 = findViewById(R.id.number0);
        Button number1 = findViewById(R.id.number1);
        Button number2 = findViewById(R.id.number2);
        Button number3 = findViewById(R.id.number3);
        Button number4 = findViewById(R.id.number4);
        Button number5 = findViewById(R.id.number5);
        Button number6 = findViewById(R.id.number6);
        Button number7 = findViewById(R.id.number7);
        Button number8 = findViewById(R.id.number8);
        Button number9 = findViewById(R.id.number9);
        Button dot = findViewById(R.id.dot);
        Button btnPlus = findViewById(R.id.btnPlus);
        Button btnEnter = findViewById(R.id.btnEnter);
        Button btnMultiplication = findViewById(R.id.btnMultiplication);
        ivTitleRight = findViewById(R.id.iv_title_right_icon);
        ImageView ivTitleRight2 = findViewById(R.id.iv_title_right_icon2);

        tvNickName = findViewById(R.id.tvNickName);
        tvDataAfter = findViewById(R.id.tvDataAfter);
        tvDataBefore = findViewById(R.id.tvDataBefore);
        tvDataDetail = findViewById(R.id.tvDataDetail);
        btnBackspace = findViewById(R.id.btnBackspace);
        tvCalculateResult = findViewById(R.id.tvCalculateResult);

        number0.setOnClickListener(this);
        number1.setOnClickListener(this);
        number2.setOnClickListener(this);
        number3.setOnClickListener(this);
        number4.setOnClickListener(this);
        number5.setOnClickListener(this);
        number6.setOnClickListener(this);
        number7.setOnClickListener(this);
        number8.setOnClickListener(this);
        number9.setOnClickListener(this);
        btnPlus.setOnClickListener(this);
        btnEnter.setOnClickListener(this);
        dot.setOnClickListener(this);
        btnMultiplication.setOnClickListener(this);
        ivTitleRight.setOnClickListener(this);
        ivTitleRight2.setOnClickListener(this);
        btnBackspace.setOnClickListener(this);
        btnBackspace.setOnLongClickListener(view -> {
            inputStr = new StringBuilder();
            clearInput();
            return true;
        });

        ivTitleRight.setImageResource(R.drawable.ic_more);
        ivTitleRight2.setImageResource(R.drawable.ic_history);
        ivTitleRight.setVisibility(View.VISIBLE);
        ivTitleRight2.setVisibility(View.VISIBLE);

        initMethodNo();
    }

    private void initMethodNo(){
        calNo1 = SharedPreferenceUtil.getInfoFormShared("CAL_NO1","165");
        calNo2 = SharedPreferenceUtil.getInfoFormShared("CAL_NO2","1.1");
        calNo3 = SharedPreferenceUtil.getInfoFormShared("CAL_NO3","1");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.dot:
                showInput(".");
                break;
            case R.id.number0:
                showInput("0");
                break;
            case R.id.number1:
                showInput("1");
                break;
            case R.id.number2:
                showInput("2");
                break;
            case R.id.number3:
                showInput("3");
                break;
            case R.id.number4:
                showInput("4");
                break;
            case R.id.number5:
                showInput("5");
                break;
            case R.id.number6:
                showInput("6");
                break;
            case R.id.number7:
                showInput("7");
                break;
            case R.id.number8:
                showInput("8");
                break;
            case R.id.number9:
                showInput("9");
                break;
            case R.id.btnPlus://加
                showInput("+");
                break;
            case R.id.btnMultiplication://乘法
                showInput("x");
                break;
            case R.id.btnBackspace://退格
                if(inputStr.length()>0){
                    inputStr.delete(inputStr.length()-1,inputStr.length());
                    tvCalculateResult.setText(inputStr.toString());
                }
                break;
            case R.id.btnEnter://单次输入结束
                if(TextUtils.isEmpty(inputStr.toString())){
                    return;
                }
                try{
                    initMethodNo();
                    refreshDataList();
                }catch (Exception e){
                    //防止故意乱输，导致闪退
                    ToastUtil.showToastShort(activity,"请检查您的输入是否正确");
                }
                break;
            case R.id.iv_title_right_icon2://历史记录
                startActivity(new Intent(activity,HistoryActivity.class));
                break;
            case R.id.iv_title_right_icon:
                PopUnderView underView = new PopUnderView(activity,
                        new String[]{
                                "公式修改",
                                "算涨跌",
                                "桌面倒数小组件"});
                underView.setOnItemClickListener((view1, holder, position) -> {
                    if(position == 0){
                        //小算盘公式设置
                        startFragment(R.id.fragment_container,MethodModifyFragment.newInstance());
                    } else if(position == 1){
                        //基金涨跌计算
                        startActivity(new Intent(activity,FundActivity.class));
                    } else if(position == 2){
                        //小组件样式设定
                        startActivity(new Intent(activity,CustomWidgetActivity.class));
                    }
                });
                underView.showView(ivTitleRight);
                break;
        }
    }

    private void showInput(String number){
        inputStr.append(number);
        tvCalculateResult.setText(inputStr.toString());
    }

    private void clearInput(){
        inputStr = new StringBuilder();
        tvCalculateResult.setText("");
        tvNickName.setText("");
        tvDataAfter.setText("");
        tvDataBefore.setText("");
        tvDataDetail.setText("");
    }


    /**
     * 描述：实时计算结果
     * 创建时间：2021/5/25 14:04
     * 创建人：ChenKun
     */
    private void refreshDataList(){
        //*****数据检查*****
        if(inputStr.toString().endsWith("+")){
            inputStr.substring(inputStr.length()-1,inputStr.length());
        }
        /*
         * dataVo:正在操作的一条数据
         * calculationStr：正在操作的数据源
         * inputStr：回显在输入框的内容
         */
        //*********开始设置数据*******
        CalculationVo dataVo = new CalculationVo();
        dataVo.setNickname(StringUtil.randomWord(4));
        dataVo.setDetail(inputStr.toString());//详情
        int total = 0;//相加结果
        //1000+600+2000*8+3000
        if(!TextUtils.isEmpty(inputStr.toString())){
            for(String itemStr : inputStr.toString().split("\\+")){
                if(TextUtils.isEmpty(itemStr)){
                    continue;
                }
                //判断是否有乘法
                if(itemStr.contains("x")){
                    double mItemTotal = 1;
                    for(String mItem : itemStr.split("x")){
                        if(TextUtils.isEmpty(mItem)){
                            continue;
                        }
                        mItemTotal = mItemTotal * Double.parseDouble(mItem);
                    }
                    if(mItemTotal != 1){
                        itemStr = String.valueOf(mItemTotal);
                        Logs.d("TAG","乘法结果为： " + itemStr);
                    }
                }
                total += Double.parseDouble(itemStr);
            }
        }
        dataVo.setTotal(String.valueOf(total));//总和
        double result = (Double.parseDouble(dataVo.getTotal()))/Double.parseDouble(calNo1) * Double.parseDouble(calNo2) + Double.parseDouble(calNo3);
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        dataVo.setResult(decimalFormat.format(result));//结果

        //保存数据
        saveDataToDisk(dataVo);
    }

    /**
     * 描述：保存数据到本地
     * 创建时间：2021/5/25 19:50
     * 创建人：ChenKun
     */
    private void saveDataToDisk(CalculationVo dataVo) {
        JSONArray jsonArray = new JSONArray();
        Gson gson = new Gson();
        try {
            //如果本地已经有数据
            String localDataStr = SharedPreferenceUtil.getInfoFormShared(SP_SAVE_KEY,"");
            if(!TextUtils.isEmpty(localDataStr)){
                jsonArray = new JSONArray(localDataStr);
            }
            jsonArray.put(gson.toJson(dataVo));
            SharedPreferenceUtil.setInfoToShared(SP_SAVE_KEY,jsonArray.toString());
            tvNickName.setText(dataVo.getNickname());
            tvDataAfter.setText(dataVo.getResult());
            tvDataBefore.setText(dataVo.getTotal());
            tvDataDetail.setText(dataVo.getDetail());
            tvCalculateResult.setText("");
            inputStr = new StringBuilder();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void removeSelf(Fragment fragment){
        removeFragment(fragment);
    }
}