package com.vivecal.calculator.fragment;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.vivecal.calculator.R;
import com.vivecal.calculator.activity.MainActivity;
import com.vivecal.calculator.basic.BaseFragment;
import com.vivecal.calculator.sharedpreference.SharedPreferenceUtil;

/**
 * 描述：修改计算公式
 * 创建日期:2021/7/16
 * 创建人：ChenKun
 */
public class MethodModifyFragment extends BaseFragment implements View.OnClickListener{

    private EditText etNo1;
    private EditText etNo2;
    private EditText etNo3;

    public static MethodModifyFragment newInstance(){
        return new MethodModifyFragment();
    }

    @Override
    protected int getContentLayout() {
        return R.layout.fragment_modify_method;
    }

    @Override
    protected void initView() {
        etNo1 = (EditText) findViewById(R.id.etNo1);
        etNo2 = (EditText) findViewById(R.id.etNo2);
        etNo3 = (EditText) findViewById(R.id.etNo3);
        Button btnCancel = (Button) findViewById(R.id.btnCancel);
        Button btnCommit = (Button) findViewById(R.id.btnCommit);

        etNo1.setText(SharedPreferenceUtil.getInfoFormShared("CAL_NO1","165"));
        etNo2.setText(SharedPreferenceUtil.getInfoFormShared("CAL_NO2","1.1"));
        etNo3.setText(SharedPreferenceUtil.getInfoFormShared("CAL_NO3","1"));

        btnCancel.setOnClickListener(this);
        btnCommit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        InputMethodManager m = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        m.hideSoftInputFromWindow(view.getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
        switch (view.getId()){
            case R.id.btnCancel://退出
                finishFragment();
                break;
            case R.id.btnCommit://保存并退出
                saveData();
                finishFragment();
                toast("保存成功！");
                break;
        }
    }

    private void finishFragment(){
        if(getActivity() == null || getActivity().isFinishing()){
            return;
        }
        if(getActivity() instanceof MainActivity){
            ((MainActivity)getActivity()).removeSelf(this);
        }
    }

    private void saveData(){
        if(etNo1.length() == 0 || etNo2.length() == 0 || etNo3.length() == 0){
            return;
        }

        SharedPreferenceUtil.setInfoToShared("CAL_NO1",etNo1.getText().toString());
        SharedPreferenceUtil.setInfoToShared("CAL_NO2",etNo2.getText().toString());
        SharedPreferenceUtil.setInfoToShared("CAL_NO3",etNo3.getText().toString());
    }
}
