package com.example.basetest.ui.photo;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.basetest.R;
import com.example.basetest.adapter.MainRecyclerAdapter;
import com.example.basetest.bean.MainBase;
import com.example.basetest.listener.OnItemClickListener;
import com.example.basetest.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 拍照
 * Created by YinZZ on 2016/8/29.
 */
public class PhotoActivity extends BaseActivity implements OnItemClickListener {
    private static final String TAG = "PhotoActivity";

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.rv_list)
    RecyclerView recyclerView;

    private List<MainBase> list;
    private MainRecyclerAdapter adapter;

    @Override
    protected int getContentView() {
        return R.layout.activity_photo;
    }

    @Override
    protected void initData() {

        list = new ArrayList<>();

        list.add(new MainBase("本地相册无，系统拍照有"));
        list.add(new MainBase("本地相册有，系统拍照无"));
        list.add(new MainBase("本地相册，系统拍照"));
    }

    @Override
    protected void initView() {
        /* 设置标题*/
        toolbar.setTitle(getResources().getString(R.string.menu_photo));
        setSupportActionBar(toolbar);
        /* 返回键可用 */
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        /* 设置返回事件*/
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PhotoActivity.this.finish();
            }
        });

        initRecyclerView();

    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        adapter = new MainRecyclerAdapter(list);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(this);

    }

    @Override
    public void onClick(View view, int position) {
        switch (position) {
            case 0://
                goToActivity(LocalPhotoActivity.class, null);
                break;
            case 1://

                break;
            case 2://

                break;
            default:
                break;
        }
    }
}
