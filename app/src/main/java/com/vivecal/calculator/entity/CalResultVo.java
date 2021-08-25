package com.vivecal.calculator.entity;

import com.vivecal.calculator.basic.VCBaseVo;

/**
 * 描述：
 * 创建日期:2021/6/8
 * 创建人：ChenKun
 */
public class CalResultVo extends VCBaseVo {
    private String nickName;
    private String paperCount;
    private String freight;
    private String detail;

    public CalResultVo(String nickName, String paperCount, String freight, String detail) {
        this.nickName = nickName;
        this.paperCount = paperCount;
        this.freight = freight;
        this.detail = detail;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPaperCount() {
        return paperCount;
    }

    public void setPaperCount(String paperCount) {
        this.paperCount = paperCount;
    }

    public String getFreight() {
        return freight;
    }

    public void setFreight(String freight) {
        this.freight = freight;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
