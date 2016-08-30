package com.example.basetest.dialog.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.basetest.R;
import com.example.basetest.listener.OnDialogClickListener;

/**
 * 弹出框基类
 * Created by YinZZ on 2016/8/26.
 */
public abstract class BaseDialog extends Dialog {
    private static final String TAG = "BaseDialog";

    public Context mContext;
    public LayoutInflater mInflater;
    public View view;

    private LinearLayout content, rly_load;
    private TextView title, txt_load, txt_left, txt_right;
    private LinearLayout btn_left, btn_right;
    private View v_btn;

    protected OnDialogClickListener listener = null;

    public BaseDialog(Context context, int layout) {
        super(context, R.style.MyDialog);
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.view = mInflater.inflate(layout, null);
    }

    public BaseDialog(Context context) {
        super(context, R.style.MyDialog);
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.view = null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_base);


        title = (TextView) findViewById(R.id.txt_title);
        btn_left = (LinearLayout) findViewById(R.id.leftBtnLayout);
        btn_right = (LinearLayout) findViewById(R.id.rightBtnLayout);
        content = (LinearLayout) findViewById(R.id.content);
        rly_load = (LinearLayout) findViewById(R.id.load);
        txt_load = (TextView) findViewById(R.id.txt_load);
        txt_left = (TextView) findViewById(R.id.txt_left);
        txt_right = (TextView) findViewById(R.id.txt_right);
        v_btn = (View) findViewById(R.id.v_btn);

        btn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSave();
            }
        });

        btn_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCancel();
            }
        });

        // 更新自定义视图
        content.removeAllViews();

        view = (view == null) ? view = new View(getContext()) : view;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(params);
        content.addView(view);

        onLoad();
    }

    /*TODO 设置宽度*/
    public void setDialogAttributes(Double width) {
        WindowManager m = ((Activity) mContext).getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.width = (int) (width * d.getWidth());
        lp.dimAmount = 0.4f;
        lp.alpha = 0.95f;
        this.getWindow().setAttributes(lp);
    }

    /*TODO 设置宽高*/
    public void setDialogAttributes(Double width, Double height) {
        WindowManager m = ((Activity) mContext).getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.width = (int) (width * d.getWidth());
        lp.height = (int) (height * d.getHeight());
        lp.dimAmount = 0.4f;
        lp.alpha = 0.95f;
        this.getWindow().setAttributes(lp);
    }

    /*TODO 设置宽高、比例*/
    public void setDialogAttributes(Double width, Double height, float alpha) {
        WindowManager m = ((Activity) mContext).getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.width = (int) (width * d.getWidth());
        lp.height = (int) (height * d.getHeight());
        lp.dimAmount = 0.4f;
        lp.alpha = alpha;
        this.getWindow().setAttributes(lp);
    }

    /*TODO 加载*/
    public abstract void onLoad();

    /*TODO 保存*/
    public abstract void onSave();

    /*TODO 取消*/
    public abstract void onCancel();

    /*TODO 获取自定义视图*/
    public View getView() {
        return view;
    }

    /*TODO 确定按钮*/
    public LinearLayout getBtnSave() {
        return btn_left;
    }

    /*TODO 返回(取消)按钮*/
    public LinearLayout getBtnCancel() {
        return btn_right;
    }

    /*TODO 确定按钮文字*/
    public TextView getLeftTextView() {
        return txt_left;
    }


    /*TODO 取消按钮文字*/
    public TextView getRightTextView() {
        return txt_right;
    }


    /*TODO 设置标题*/
    public void setTitleText(String name) {
        this.title.setText(name);
    }

    /*TODO 分割线*/
    public View getBtnView() {
        return v_btn;
    }

    /*TODO 内容*/
    public LinearLayout getContent() {
        return content;
    }

    /*TODO 执行/结束加载动画(无提示)*/
    public void onProgressLoad(boolean isload) {
        if (isload) {
            content.setVisibility(View.INVISIBLE);
            rly_load.setVisibility(View.VISIBLE);
        } else {
            content.setVisibility(View.VISIBLE);
            rly_load.setVisibility(View.GONE);
            txt_load.setVisibility(View.GONE);
        }
    }

    /*TODO 执行/结束加载动画(有提示)*/
    public void onProgressLoad(boolean isload, String loadString) {
        if (isload) {
            content.setVisibility(View.INVISIBLE);
            rly_load.setVisibility(View.VISIBLE);
            txt_load.setVisibility(View.VISIBLE);
            txt_load.setText(loadString);
        } else {
            content.setVisibility(View.VISIBLE);
            rly_load.setVisibility(View.GONE);
            txt_load.setVisibility(View.GONE);
        }
    }

    public TextView getTitle() {
        return title;
    }

    /*TODO 消息提醒*/
    protected void notice(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    /*TODO 消息提醒*/
    protected void notice(int msg) {
        Toast.makeText(mContext, mContext.getString(msg), Toast.LENGTH_SHORT).show();
    }

    /*TODO 设置监听*/
    public void setOnDialogClickListener(OnDialogClickListener listener) {
        this.listener = listener;
    }
}
