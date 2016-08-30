package com.example.basetest.adapter;

import com.example.basetest.R;
import com.example.basetest.adapter.base.BaseRecyclerViewAdapter;
import com.example.basetest.bean.MainBase;
import com.example.basetest.util.Utils;

import java.util.List;

/**
 * 主菜单Adapter
 * Created by YinZZ on 2016/8/29.
 */
public class MainRecyclerAdapter extends BaseRecyclerViewAdapter {
    private static final String TAG = "MainRecyclerAdapter";

    /**
     * @param list the datas to attach the adapter
     */
    public MainRecyclerAdapter(List<MainBase> list) {
        super(list);
    }

    @Override
    public int inflateLayoutRes() {
        return R.layout.adapter_recycler_main;
    }

    @Override
    protected void bindDataToItemView(SparseArrayViewHolder sparseArrayViewHolder, int position) {
        MainBase item = (MainBase) getItem(position);

        if (item != null) {
            sparseArrayViewHolder.setText(R.id.txt_title, Utils.checkValue(item.getTitle()));
            sparseArrayViewHolder.setImageResource(R.id.img_icon,R.mipmap.ic_launcher);
        }

    }
}
