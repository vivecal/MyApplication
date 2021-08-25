package com.vivecal.calculator.entity;

import com.vivecal.calculator.basic.VCBaseVo;

/**
 * 描述：
 * 创建日期:2021/7/9
 * 创建人：ChenKun
 */
public class WidgetSettingVo extends VCBaseVo {
    //文字颜色
    private String textColor;
    //文字大小
    private float textSize;
    //背景的base64
    private String bgBase64;
    //用户设定的日期
    private String settingDate;
    //用户自定义的文本
    private String customText;

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    public float getTextSize() {
        return textSize;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    public String getBgBase64() {
        return bgBase64;
    }

    public void setBgBase64(String bgBase64) {
        this.bgBase64 = bgBase64;
    }

    public String getSettingDate() {
        return settingDate;
    }

    public void setSettingDate(String settingDate) {
        this.settingDate = settingDate;
    }

    public String getCustomText() {
        return customText;
    }

    public void setCustomText(String customText) {
        this.customText = customText;
    }
}
