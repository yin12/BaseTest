package com.example.basetest.ui.photo;

import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.basetest.R;
import com.example.basetest.ui.base.BaseActivity;

import butterknife.Bind;

/**
 * 系统拍照
 * Created by YinZZ on 2016/8/29.
 */
public class LocalPhotoActivity extends BaseActivity {
    private static final String TAG = "LocalPhotoActivity";

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected int getContentView() {
        return R.layout.activity_photo_local;

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        initToolbar();

    }

    private void initToolbar() {
        toolbar.setTitle(getResources().getString(R.string.menu_photo));
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LocalPhotoActivity.this.finish();
            }
        });
    }
}
