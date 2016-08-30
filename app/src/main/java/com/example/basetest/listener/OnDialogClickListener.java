package com.example.basetest.listener;

import com.example.basetest.dialog.base.BaseDialog;

/**
 * 基类弹出框监听器
 * Created by YinZZ on 2016/8/26.
 */
public interface OnDialogClickListener {
    void onSave(BaseDialog dialog, Object... data);

    void onCancel(BaseDialog dialog);
}
