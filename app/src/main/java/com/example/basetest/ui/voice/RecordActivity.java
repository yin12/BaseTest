package com.example.basetest.ui.voice;

import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.basetest.R;
import com.example.basetest.ui.base.BaseActivity;

import butterknife.Bind;

/**
 * 录音
 * Created by YinZZ on 2016/8/29.
 */
public class RecordActivity extends BaseActivity {
    private static final String TAG = "RecordActivity";

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected int getContentView() {
        return R.layout.activity_record;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

        /* 设置标题*/
        toolbar.setTitle(getResources().getString(R.string.menu_record));
        setSupportActionBar(toolbar);
        /* 返回键可用*/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        /* 返回事件*/
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RecordActivity.this.finish();
            }
        });
    }
}
