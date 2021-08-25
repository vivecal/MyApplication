package com.vivecal.calculator.entity;


import com.vivecal.calculator.basic.VCBaseVo;

/**
 * 描述：计算结果
 * 创建日期:2021/5/25
 * 创建人：ChenKun
 */
public class CalculationVo extends VCBaseVo {
    private String result;
    private String total;
    private String detail;
    private String nickname;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
