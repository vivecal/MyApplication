package com.vivecal.calculator.entity;

import com.vivecal.calculator.basic.VCBaseVo;

import java.util.List;

/**
 * 描述：运费数据
 * 创建日期:2021/6/23
 * 创建人：ChenKun
 */
public class FreightVo extends VCBaseVo {
    private List<CalResultVo> freights;

    public FreightVo(List<CalResultVo> freights) {
        this.freights = freights;
    }

    public List<CalResultVo> getFreights() {
        return freights;
    }

    public void setFreights(List<CalResultVo> freights) {
        this.freights = freights;
    }
}
