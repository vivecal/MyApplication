package com.vivecal.calculator.activity;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.vivecal.calculator.R;
import com.vivecal.calculator.basic.BaseActivity;
import com.vivecal.calculator.entity.WidgetSettingVo;
import com.vivecal.calculator.sharedpreference.SharedPreferenceUtil;
import com.vivecal.calculator.utils.BitmapUtil;
import com.vivecal.calculator.utils.Logs;
import com.vivecal.calculator.utils.ToastUtil;
import com.vivecal.calculator.weight.DateUpdateService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 描述：小组件设置
 * 创建时间：2021/7/16 15:42
 * 创建人：ChenKun
 */
public class CustomWidgetActivity extends BaseActivity implements View.OnClickListener {

    public static final String DEFAULT_DATE = "2020年7月4日 14:00:00";
    public static final String DEFAULT_CUSTOM_TEXT = "和最爱的宝贝在一起";

    private final float SMALL_TEXT_SIZE = 15;
    private final float MID_TEXT_SIZE = 18;
    private final float LARGE_TEXT_SIZE = 23;

    private final String COLOR_YELLOW = "#FFCC7F";
    private final String COLOR_PINK = "#DBAADF";
    private final String COLOR_BLUE = "#00b2ee";
    private final String COLOR_ORANGE = "#FFA500";

    private DateUpdateService.UpdateBinder updateBinder;
    private WidgetSettingVo widgetSettingVo;
    //获取服务中计时器的时间
    private ServiceConnection serviceConnection;
    private boolean isServiceBind = false;

    @BindView(R.id.widgetTv)
    TextView widgetTv;
    @BindView(R.id.widgetRl)
    RelativeLayout widgetRl;
    @BindView(R.id.ivTextColorYellow)
    ImageView ivTextColorYellow;
    @BindView(R.id.ivTextColorPink)
    ImageView ivTextColorPink;
    @BindView(R.id.ivTextColorBlue)
    ImageView ivTextColorBlue;
    @BindView(R.id.ivTextColorOrange)
    ImageView ivTextColorOrange;
    @BindView(R.id.etTextColor)
    EditText etTextColor;
    @BindView(R.id.btnPreviewTvColor)
    Button btnPreviewTvColor;
    @BindView(R.id.ivTextSizeSmall)
    TextView ivTextSizeSmall;
    @BindView(R.id.ivTextSizeMiddle)
    TextView ivTextSizeMiddle;
    @BindView(R.id.ivTextSizeLarge)
    TextView ivTextSizeLarge;
    @BindView(R.id.etTextSize)
    EditText etTextSize;
    @BindView(R.id.btnPreviewTvSize)
    Button btnPreviewTvSize;
    @BindView(R.id.etShowingText)
    EditText etShowingText;
    @BindView(R.id.btnPreviewText)
    Button btnPreviewText;
    @BindView(R.id.ivWidgetBgDefault)
    ImageView ivWidgetBgDefault;
    @BindView(R.id.ivWidgetBgYellow)
    ImageView ivWidgetBgYellow;
    @BindView(R.id.ivWidgetBgBlue)
    ImageView ivWidgetBgBlue;
    @BindView(R.id.tvWidgetBgSelect)
    TextView tvWidgetBgSelect;
    @BindView(R.id.tv_title_right_text)
    TextView tvTitleRightText;
    @BindView(R.id.widgetTvTitle)
    TextView widgetTvTitle;
    @BindView(R.id.tvDefaultDate)
    TextView tvDefaultDate;

    @Override
    protected int getLayout() {
        return R.layout.activity_custom_widget;
    }

    @Override
    protected void initViews() {
        ButterKnife.bind(activity);
        setPageTitle("编辑小组件");
        setLefAction();//返回按钮事件设置

        tvTitleRightText.setVisibility(View.VISIBLE);
        tvTitleRightText.setText("恢复默认");

        //加载当前样式
        initDefaultStyle();
        //初始化选中状态
        setSelectedStyle();
        //绑定服务
        bindDateService();
    }

    /**
     * 描述：加载本地配置
     * 创建时间：2021/7/9 16:22
     * 创建人：ChenKun
     */
    private void initDefaultStyle(){
        //初始化小组件
        widgetSettingVo = new Gson().fromJson(SharedPreferenceUtil.getInfoFormShared("Widget_Info"),WidgetSettingVo.class);
        if(widgetSettingVo == null){
            reSetWidgetInfo();
        }
    }

    private void reSetWidgetInfo(){
        widgetSettingVo = new WidgetSettingVo();
        widgetSettingVo.setSettingDate(DEFAULT_DATE);
        widgetSettingVo.setTextColor(COLOR_YELLOW);
        widgetSettingVo.setTextSize(SMALL_TEXT_SIZE);
        widgetSettingVo.setBgBase64("0");
        widgetSettingVo.setCustomText(DEFAULT_CUSTOM_TEXT);
    }

    /**
     * 描述：绑定服务，刷新View
     * 创建时间：2021/7/9 16:23
     * 创建人：ChenKun
     */
    private void bindDateService(){
        Intent intentBind = new Intent(this, DateUpdateService.class);
        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                isServiceBind = true;
                updateBinder = (DateUpdateService.UpdateBinder) iBinder;
                String showingText = updateBinder.getService().getShowingText();
                String titleText = updateBinder.getService().getTitleText();
                Logs.d("MyWidget"," onServiceConnected 绑定服务成功 当前内容：%s",showingText);
                widgetTvTitle.setText(titleText);
                widgetTv.setText(showingText);
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                Logs.d("MyWidget"," onServiceDisconnected 解绑服务成功");
                isServiceBind = false;
            }
        };
        activity.bindService(intentBind, serviceConnection, Service.BIND_AUTO_CREATE);
    }

    /**
     * 描述：设置选中状态
     * 创建时间：2021/7/9 16:23
     * 创建人：ChenKun
     */
    private void setSelectedStyle() {
        //文本颜色
        switch (widgetSettingVo.getTextColor()){
            case COLOR_YELLOW:
                ivTextColorYellow.setBackground(getResources().getDrawable(R.drawable.bg_rect_pop_item));
                break;
            case COLOR_PINK:
                ivTextColorPink.setBackground(getResources().getDrawable(R.drawable.bg_rect_pop_item));
                break;
            case COLOR_BLUE:
                ivTextColorBlue.setBackground(getResources().getDrawable(R.drawable.bg_rect_pop_item));
                break;
            case COLOR_ORANGE:
                ivTextColorOrange.setBackground(getResources().getDrawable(R.drawable.bg_rect_pop_item));
                break;
        }
        //文字大小
        switch ((int)widgetSettingVo.getTextSize()){
            case (int)SMALL_TEXT_SIZE:
                ivTextSizeSmall.setBackground(getResources().getDrawable(R.drawable.bg_rect_pop_item));
                break;
            case (int)MID_TEXT_SIZE:
                ivTextSizeMiddle.setBackground(getResources().getDrawable(R.drawable.bg_rect_pop_item));
                break;
            case (int)LARGE_TEXT_SIZE:
                ivTextSizeLarge.setBackground(getResources().getDrawable(R.drawable.bg_rect_pop_item));
                break;
        }
        //小组件背景
        switch (widgetSettingVo.getBgBase64()){
            case "0":
                ivWidgetBgDefault.setBackground(getResources().getDrawable(R.drawable.bg_rect_pop_item));
                break;
            case "1":
                ivWidgetBgYellow.setBackground(getResources().getDrawable(R.drawable.bg_rect_pop_item));
                break;
            case "2":
                ivWidgetBgBlue.setBackground(getResources().getDrawable(R.drawable.bg_rect_pop_item));
                break;
            default:
                reSetWidgetBg();
                break;
        }
        //文本预览
        widgetTv.setText("xxx天xx小时xx分钟xx秒");
        //小标题
        widgetTvTitle.setText(widgetSettingVo.getCustomText());
        //用户定义文本
        etShowingText.setText(widgetSettingVo.getCustomText());
        //设定日期
        tvDefaultDate.setText(DEFAULT_DATE);

        widgetTv.setTextSize(widgetSettingVo.getTextSize());
        widgetTv.setTextColor(Color.parseColor(widgetSettingVo.getTextColor()));
        widgetTvTitle.setTextColor(Color.parseColor(widgetSettingVo.getTextColor()));
        widgetRl.setBackground(getBackgroundDrawable());
    }

    private void reSetTvColor(){
        ivTextColorYellow.setBackground(null);
        ivTextColorPink.setBackground(null);
        ivTextColorBlue.setBackground(null);
        ivTextColorOrange.setBackground(null);
    }

    private void reSetTvSize(){
        ivTextSizeSmall.setBackground(null);
        ivTextSizeMiddle.setBackground(null);
        ivTextSizeLarge.setBackground(null);
    }

    private void reSetWidgetBg(){
        ivWidgetBgDefault.setBackground(null);
        ivWidgetBgBlue.setBackground(null);
        ivWidgetBgYellow.setBackground(null);
    }


    @OnClick({R.id.ivTextColorYellow, R.id.ivTextColorPink, R.id.ivTextColorBlue, R.id.ivTextColorOrange, R.id.btnPreviewTvColor, R.id.ivTextSizeSmall, R.id.ivTextSizeMiddle, R.id.ivTextSizeLarge, R.id.btnPreviewTvSize, R.id.btnPreviewText, R.id.ivWidgetBgDefault, R.id.ivWidgetBgYellow, R.id.ivWidgetBgBlue, R.id.tvWidgetBgSelect,R.id.tv_title_right_text})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivTextColorYellow:
                reSetTvColor();
                widgetTv.setTextColor(Color.parseColor(COLOR_YELLOW));
                widgetTvTitle.setTextColor(Color.parseColor(COLOR_YELLOW));
                ivTextColorYellow.setBackground(getResources().getDrawable(R.drawable.bg_rect_pop_item));
                break;
            case R.id.ivTextColorPink:
                reSetTvColor();
                widgetTv.setTextColor(Color.parseColor(COLOR_PINK));
                widgetTvTitle.setTextColor(Color.parseColor(COLOR_PINK));
                ivTextColorPink.setBackground(getResources().getDrawable(R.drawable.bg_rect_pop_item));
                break;
            case R.id.ivTextColorBlue:
                reSetTvColor();
                widgetTv.setTextColor(Color.parseColor(COLOR_BLUE));
                widgetTvTitle.setTextColor(Color.parseColor(COLOR_BLUE));
                ivTextColorBlue.setBackground(getResources().getDrawable(R.drawable.bg_rect_pop_item));
                break;
            case R.id.ivTextColorOrange:
                reSetTvColor();
                widgetTv.setTextColor(Color.parseColor(COLOR_ORANGE));
                widgetTvTitle.setTextColor(Color.parseColor(COLOR_ORANGE));
                ivTextColorOrange.setBackground(getResources().getDrawable(R.drawable.bg_rect_pop_item));
                break;
            case R.id.btnPreviewTvColor://自定义颜色
                if (etTextColor.getText().length() == 0) {
                    return;
                }
                try {
                    int textColor = Color.parseColor(etTextColor.getText().toString());
                    reSetTvColor();
                    widgetTv.setTextColor(textColor);
                } catch (RuntimeException e) {
                    ToastUtil.showToastShort(activity, "请输入正确的颜色格式");
                }
                break;
            case R.id.ivTextSizeSmall:
                reSetTvSize();
                widgetTv.setTextSize(SMALL_TEXT_SIZE);
                etTextSize.setText(String.valueOf(SMALL_TEXT_SIZE));
                ivTextSizeSmall.setBackground(getResources().getDrawable(R.drawable.bg_rect_pop_item));
                break;
            case R.id.ivTextSizeMiddle:
                reSetTvSize();
                widgetTv.setTextSize(MID_TEXT_SIZE);
                etTextSize.setText(String.valueOf(MID_TEXT_SIZE));
                ivTextSizeMiddle.setBackground(getResources().getDrawable(R.drawable.bg_rect_pop_item));
                break;
            case R.id.ivTextSizeLarge:
                reSetTvSize();
                widgetTv.setTextSize(LARGE_TEXT_SIZE);
                etTextSize.setText(String.valueOf(LARGE_TEXT_SIZE));
                ivTextSizeLarge.setBackground(getResources().getDrawable(R.drawable.bg_rect_pop_item));
                break;
            case R.id.btnPreviewTvSize://自定义文字大小
                if (etTextSize.getText().length() == 0) {
                    return;
                }
                widgetTv.setTextSize(TypedValue.COMPLEX_UNIT_SP,Integer.parseInt(etTextSize.getText().toString()));
                break;
            case R.id.btnPreviewText:
                if (etShowingText.getText().length() == 0) {
                    return;
                }
                widgetTv.setText(etShowingText.getText().toString());
                break;
            case R.id.ivWidgetBgDefault:
                reSetWidgetBg();
                widgetRl.setBackground(getResources().getDrawable(R.drawable.bg_app_widget));
                ivWidgetBgDefault.setBackground(getResources().getDrawable(R.drawable.bg_rect_pop_item));
                break;
            case R.id.ivWidgetBgYellow:
                reSetWidgetBg();
                widgetRl.setBackground(getResources().getDrawable(R.drawable.bg_app_widget_yellow));
                ivWidgetBgYellow.setBackground(getResources().getDrawable(R.drawable.bg_rect_pop_item));
                break;
            case R.id.ivWidgetBgBlue:
                reSetWidgetBg();
                widgetRl.setBackground(getResources().getDrawable(R.drawable.bg_app_widget_blue));
                ivWidgetBgBlue.setBackground(getResources().getDrawable(R.drawable.bg_rect_pop_item));
                break;
            case R.id.tvWidgetBgSelect://相册选择
                ToastUtil.showToastShort(activity, "暂不支持自定义背景");
                break;
            case R.id.tv_title_right_text://恢复默认
                reSetWidgetInfo();
                //初始化选中状态
                setSelectedStyle();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        if(isServiceBind){
            unbindService(serviceConnection);
        }
        super.onDestroy();
    }

    /**
     * 描述：获取背景图
     * 创建时间：2021/7/16 15:04
     * 创建人：ChenKun
     */
    private Drawable getBackgroundDrawable(){
        Drawable drawable;
        //小组件背景
        switch (widgetSettingVo.getBgBase64()){
            case "0":
                drawable = getResources().getDrawable(R.drawable.bg_app_widget);
                break;
            case "1":
                drawable = getResources().getDrawable(R.drawable.bg_app_widget_yellow);
                break;
            case "2":
                drawable = getResources().getDrawable(R.drawable.bg_app_widget_blue);
                break;
            default:
                drawable = new BitmapDrawable(getResources(),BitmapUtil.base64ToBitmap(widgetSettingVo.getBgBase64()));
                break;
        }
        return drawable;
    }
}