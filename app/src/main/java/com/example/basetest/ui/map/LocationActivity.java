package com.example.basetest.ui.map;

import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.basetest.R;
import com.example.basetest.ui.base.BaseActivity;

import butterknife.Bind;

/**
 * this is a LocationActivity for Location
 * Created by YinZZ on 2016/8/29.
 */
public class LocationActivity extends BaseActivity {
    private static final String TAG = "LocationActivity";

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected int getContentView() {
        return R.layout.activity_location;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        /*  设置标题*/
        toolbar.setTitle(getResources().getString(R.string.menu_location));
        setSupportActionBar(toolbar);
        /*  返回键可用*/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        /*  返回事件*/
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LocationActivity.this.finish();
            }
        });
    }
}
