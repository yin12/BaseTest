package com.example.basetest.adapter;

import android.view.View;
import android.view.ViewGroup;

import com.example.basetest.R;
import com.example.basetest.adapter.base.BaseListViewAdapter;

/**
 * exampleListViewAdapter
 * Created by YinZZ on 2016/8/26.
 */
public class SimpleListAdapter extends BaseListViewAdapter {
    @Override
    protected int inflateLayoutRes() {
        return R.layout.adapter_list_simple;
    }

    @Override
    protected View bindView(int position, View convertView, ViewGroup parent, SparseArrayViewHolder holder) {
        return null;
    }
}
