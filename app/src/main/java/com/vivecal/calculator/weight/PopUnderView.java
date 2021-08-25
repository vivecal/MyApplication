package com.vivecal.calculator.weight;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.vivecal.calculator.R;
import com.vivecal.calculator.adapter.PopViewAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.Arrays;

/**
 * 描述：在指定View下弹出气泡
 * 创建日期:2021/6/25
 * 创建人：ChenKun
 */
public class PopUnderView {

    private final PopupWindow popupWindow;
    private final PopViewAdapter popViewAdapter;

    public PopUnderView(Context context,String[] data) {
        View popView = LayoutInflater.from(context).inflate(R.layout.view_under_layout,null);

        RecyclerView rvPopView = popView.findViewById(R.id.rvPopView);
        popViewAdapter = new PopViewAdapter(context);
        rvPopView.setAdapter(popViewAdapter);
        rvPopView.setLayoutManager(new LinearLayoutManager(context));

        popupWindow = new PopupWindow(popView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,true);
        setData(data);
    }

    /**
     * 描述：设置数据
     * 创建时间：2021/6/28 9:12
     * 创建人：ChenKun
     */
    public void setData(String[] data){
        if(data == null || data.length==0){
            return ;
        }
        popViewAdapter.getDatas().clear();
        popViewAdapter.getDatas().addAll(Arrays.asList(data));
        popViewAdapter.notifyDataSetChanged();
    }

    /**
     * 描述：在View anchor下方展示
     * 创建时间：2021/6/25 16:42
     * 创建人：ChenKun
     */
    public void showView(View anchor){
        if(popViewAdapter.getItemCount() == 0){
            return;
        }
        popupWindow.showAsDropDown(anchor);
    }

    /**
     * 描述：关闭气泡
     * 创建时间：2021/6/28 9:54
     * 创建人：ChenKun
     */
    public void dismiss(){
        if(popupWindow != null){
            popupWindow.dismiss();
        }
    }

    public void setOnItemClickListener(final OnPopItemClickListener onPopItemClickListener){
        popViewAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                popupWindow.dismiss();
                onPopItemClickListener.onItemClick(view,holder,position);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }

    public interface OnPopItemClickListener {
        void onItemClick(View view, RecyclerView.ViewHolder holder, int position);
    }
}
