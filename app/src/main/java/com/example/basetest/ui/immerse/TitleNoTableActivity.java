package com.example.basetest.ui.immerse;

import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.basetest.R;
import com.example.basetest.ui.base.BaseActivity;

import butterknife.Bind;

/**
 * 有内容，无列表
 * Created by YinZZ on 2016/8/29.
 */
public class TitleNoTableActivity extends BaseActivity {
    private static final String TAG = "TitleNoTableActivity";

    @Bind(R.id.app_bar)
    AppBarLayout appBarLayout;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected int getContentView() {
        return R.layout.activity_no_table_title;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

        toolbar.setTitle("有内容，无列表");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TitleNoTableActivity.this.finish();
            }
        });
    }
}
