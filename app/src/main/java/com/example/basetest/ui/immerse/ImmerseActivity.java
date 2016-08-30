package com.example.basetest.ui.immerse;

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
 * 沉浸式下拉
 * Created by YinZZ on 2016/8/29.
 */
public class ImmerseActivity extends BaseActivity implements OnItemClickListener {
    private static final String TAG = "ImmerseActivity";

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.rv_list)
    RecyclerView recyclerView;

    private List<MainBase> list;
    private MainRecyclerAdapter adapter;

    @Override
    protected int getContentView() {
        return R.layout.activity_immerse;
    }

    @Override
    protected void initData() {
        list = new ArrayList<>();

        list.add(new MainBase("沉浸式无内容，不带viewpager"));
        list.add(new MainBase("沉浸式有内容，不带viewpager"));
        list.add(new MainBase("沉浸式无内容，带viewpager"));
        list.add(new MainBase("沉浸式有内容，不带viewpager"));

    }

    @Override
    protected void initView() {
        /*  设置标题*/
        toolbar.setTitle(getResources().getString(R.string.menu_immerse));
        setSupportActionBar(toolbar);
        /*  返回键可用*/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        /*  返回事件*/
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImmerseActivity.this.finish();
            }
        });

        initRecyclerView();
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
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
                goToActivity(NoTitleNoTableActivity.class,null);
                break;
            case 1://
                goToActivity(TitleNoTableActivity.class,null);
                break;
            case 2://
                goToActivity(NoTitleTableActivity.class,null);
                break;
            case 3://
                goToActivity(TitleTableActivity.class,null);
                break;
            default:
                break;
        }
    }
}
