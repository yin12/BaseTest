package com.example.basetest.ui;

import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.basetest.R;
import com.example.basetest.adapter.MainRecyclerAdapter;
import com.example.basetest.bean.MainBase;
import com.example.basetest.listener.OnItemClickListener;
import com.example.basetest.ui.base.BaseActivity;
import com.example.basetest.ui.green_dao.GreenDaoActivity;
import com.example.basetest.ui.map.LineActivity;
import com.example.basetest.ui.map.LocationActivity;
import com.example.basetest.ui.map.NavigationActivity;
import com.example.basetest.ui.immerse.ImmerseActivity;
import com.example.basetest.ui.other.OtherActivity;
import com.example.basetest.ui.photo.PhotoActivity;
import com.example.basetest.ui.push.PushActivity;
import com.example.basetest.ui.voice.RecordActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 主活动
 * Created by YinZZ on 2016/8/26.
 */
public class MainActivity extends BaseActivity implements Toolbar.OnMenuItemClickListener,
        NavigationView.OnNavigationItemSelectedListener, OnItemClickListener {
    private static final String TAG = "MainActivity";

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @Bind(R.id.navigation_view)
    NavigationView navigationView;

    @Bind(R.id.rv_list)
    RecyclerView recyclerView;

    private Menu menu;

    private MainRecyclerAdapter adapter;
    private List<MainBase> list;

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        list = new ArrayList<>();

        list.add(new MainBase(getResources().getString(R.string.menu_photo)));
        list.add(new MainBase(getResources().getString(R.string.menu_location)));
        list.add(new MainBase(getResources().getString(R.string.menu_navigation)));
        list.add(new MainBase(getResources().getString(R.string.menu_line)));
        list.add(new MainBase(getResources().getString(R.string.menu_record)));
        list.add(new MainBase(getResources().getString(R.string.menu_push)));
        list.add(new MainBase(getResources().getString(R.string.menu_immerse)));
        list.add(new MainBase(getResources().getString(R.string.menu_green_dao)));
        list.add(new MainBase(getResources().getString(R.string.menu_other)));

    }

    @Override
    protected void initView() {
        setTitle(getResources().getString(R.string.app_name));
        setSupportActionBar(toolbar);

        /* 设置抽柜导航事件*/
        toolbar.setOnMenuItemClickListener(this);

          /*设置抽屉导航*/
        final ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar,
                R.string.drawer_open, R.string.drawer_close);
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        initRecyclerView();
    }

    private void initRecyclerView() {
        /* 类似 ListView*/
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        /* 类似 GridView*/
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        adapter = new MainRecyclerAdapter(list);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(this);

//        recyclerView.setLoadingMoreEnabled(true);
//        recyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallPulse);
//        recyclerView.setRefreshProgressStyle(ProgressStyle.BallPulse);
//        recyclerView.setArrowImageView(R.mipmap.pull_down_arrow);
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        return false;
    }

    @Override
    public void onClick(View view, int position) {
        switch (position) {
            case 0://拍照
                goToActivity(PhotoActivity.class, null);
                break;
            case 1://定位
                goToActivity(LocationActivity.class, null);
                break;
            case 2://导航
                goToActivity(NavigationActivity.class, null);
                break;
            case 3://线路规划
                goToActivity(LineActivity.class, null);
                break;
            case 4://录音
                goToActivity(RecordActivity.class, null);
                break;
            case 5://推送
                goToActivity(PushActivity.class, null);
                break;
            case 6://沉浸式下拉
                goToActivity(ImmerseActivity.class, null);
                break;
            case 7://GreenDao for ORM框架
                goToActivity(GreenDaoActivity.class, null);
                break;
            case 8://其他
                goToActivity(OtherActivity.class, null);
                break;
            default:
                break;
        }
    }
}
