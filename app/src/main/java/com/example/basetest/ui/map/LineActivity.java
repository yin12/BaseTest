package com.example.basetest.ui.map;

import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.basetest.R;
import com.example.basetest.ui.base.BaseActivity;

import butterknife.Bind;

/**
 * 线路规划
 * Created by YinZZ on 2016/8/29.
 */
public class LineActivity extends BaseActivity {
    private static final String TAG = "LineActivity";
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected int getContentView() {
        return R.layout.activity_line;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        /*  设置标题*/
        toolbar.setTitle(getResources().getString(R.string.menu_line));
        setSupportActionBar(toolbar);
        /*  返回键可用*/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        /*  返回事件*/
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LineActivity.this.finish();
            }
        });
    }
}
