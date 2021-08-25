package com.vivecal.calculator.viewmodle;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.vivecal.calculator.entity.CalculationVo;

import java.util.List;

/**
 * 描述：参与运费的数据列表 vm
 * 创建日期:2021/6/23
 * 创建人：ChenKun
 */
public class FreightViewModel extends ViewModel {
    private MutableLiveData<List<CalculationVo>> selectedList = new MutableLiveData<>();

    public MutableLiveData<List<CalculationVo>> getSelectedList() {
        return selectedList;
    }
}
