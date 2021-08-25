package com.vivecal.calculator.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.vivecal.calculator.R;
import com.vivecal.calculator.basic.BaseActivity;

import java.text.DecimalFormat;
import java.text.Format;

/**
 * 描述：基金计算器
 * 创建时间：2021/6/30 15:10
 * 创建人：ChenKun
 */
public class FundActivity extends BaseActivity implements View.OnClickListener {

    private EditText etRise;
    private EditText etHoldRise;
    private EditText etFundPriceRise;
    private EditText etDown;
    private EditText etHoldDown;
    private TextView tvRiseResult;
    private TextView tvDownResult;

    @Override
    protected int getLayout() {
        return R.layout.activity_fund;
    }

    @Override
    protected void initViews() {
        setPageTitle(getString(R.string.activity_title_fund_cal));
        setLefAction();

        tvRiseResult = findViewById(R.id.tvRiseResult);
        tvDownResult = findViewById(R.id.tvDownResult);
        TextView tvTitleRight = findViewById(R.id.tv_title_right_text);

        etRise = findViewById(R.id.etRise);
        etHoldRise = findViewById(R.id.etHoldRise);
        etFundPriceRise = findViewById(R.id.etFundPriceRise);
        etDown = findViewById(R.id.etDown);
        etHoldDown = findViewById(R.id.etHoldDown);

        Button btnCalRise = findViewById(R.id.btnCalRise);
        Button btnCalDown = findViewById(R.id.btnCalDown);

        tvTitleRight.setText("清空");
        tvTitleRight.setVisibility(View.VISIBLE);

        btnCalRise.setOnClickListener(this);
        btnCalDown.setOnClickListener(this);
        tvTitleRight.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        switch (view.getId()){
            case R.id.btnCalRise:
                double valueRise = Double.parseDouble(etRise.getText().toString());
                double valueHold = Double.parseDouble(etHoldRise.getText().toString());
                double funkPrice = Double.parseDouble(etFundPriceRise.getText().toString());
                String sold = decimalFormat.format((valueRise * 5 * valueHold / 100)/funkPrice);
                tvRiseResult.setText(String.format("需要卖出%s份",sold));
                break;
            case R.id.btnCalDown:
                double valueDown = Double.parseDouble(etDown.getText().toString());
                double valueHoldDown = Double.parseDouble(etHoldDown.getText().toString());
                String buy = decimalFormat.format(valueDown * 5 * valueHoldDown /100);
                tvDownResult.setText(String.format("需要买入%s元",buy));
                break;
            case R.id.tv_title_right_text:
                etRise.setText("");
                etHoldRise.setText("");
                etFundPriceRise.setText("");
                etDown.setText("");
                etHoldDown.setText("");
                break;
        }
    }
}