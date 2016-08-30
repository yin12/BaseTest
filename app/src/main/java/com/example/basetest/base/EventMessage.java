package com.example.basetest.base;

import android.view.View;

/**
 * Created by Administrator on 2016/8/26.
 */
public class EventMessage {

    private int type;
    private Object object;
    private View view;

    protected EventMessage(int type, Object object) {
        this.type = type;
        this.object = object;
    }

    protected EventMessage(View view, int type, Object object) {
        this.view = view;
        this.type = type;
        this.object = object;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }
}
