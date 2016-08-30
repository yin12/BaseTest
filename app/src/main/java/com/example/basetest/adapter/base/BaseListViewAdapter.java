package com.example.basetest.adapter.base;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * listView 基类适配器
 * Created by YinZZ on 2016/8/26.
 */
public abstract class BaseListViewAdapter<E> extends BaseAdapter {
    private static final String TAG = "BaseListViewAdapter";

    protected View itemView;

    protected List<E> list;
    protected Context context;
    protected LayoutInflater mInflater;

    protected abstract int inflateLayoutRes();

    // adapter中的内部点击事件
    protected Map<Integer, onInternalClickListener> canClickItem;

    protected abstract View bindView(int position, View convertView, ViewGroup parent, SparseArrayViewHolder holder);


    public List<E> getList() {
        return list;
    }

    public void setList(List<E> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list == null ? null : list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SparseArrayViewHolder holder;
        if (null == convertView) {
            holder = new SparseArrayViewHolder();
            convertView = LayoutInflater.from(context)
                    .inflate(inflateLayoutRes(), null);
            convertView.setTag(holder);
        } else {
            holder = (SparseArrayViewHolder) convertView.getTag();
        }

        this.itemView = convertView;
        // 绑定内部点击监听
        addInternalClickListener(convertView, position, list.get(position));
        return bindView(position, convertView, parent, holder);
    }

    private void addInternalClickListener(final View itemV,
                                          final Integer position, final Object valuesMap) {
        if (canClickItem != null) {
            for (Integer key : canClickItem.keySet()) {
                View inView = itemV.findViewById(key);
                final onInternalClickListener inviewListener = canClickItem
                        .get(key);
                if (inView != null && inviewListener != null) {
                    inView.setOnClickListener(new View.OnClickListener() {

                        public void onClick(View v) {
                            inviewListener.OnClickListener(itemV, v, position, valuesMap);
                        }
                    });
                }
            }
        }
    }


    public class SparseArrayViewHolder {
        public SparseArray<View> views = new SparseArray<View>();

        /**
         * 指定resId和类型即可获取到相应的view
         *
         * @param resId
         * @param <T>
         * @return
         */
        public <T extends View> T getView(int resId) {
            View v = views.get(resId);
            if (null == v
                    && itemView != null) {
                v = itemView.findViewById(resId);
                views.put(resId, v);
            }
            return (T) v;
        }

        public SparseArrayViewHolder setText(int viewId, String value) {
            TextView view = getView(viewId);
            view.setText(value);
            return SparseArrayViewHolder.this;
        }

        public SparseArrayViewHolder setText(int viewId, CharSequence text) {
            TextView view = getView(viewId);
            view.setText(text);
            return SparseArrayViewHolder.this;
        }

        public SparseArrayViewHolder setTextColor(int viewId, int textColor) {
            TextView view = getView(viewId);
            view.setTextColor(textColor);
            return SparseArrayViewHolder.this;
        }

        public SparseArrayViewHolder setImageResource(int viewId, int imageResId) {
            ImageView view = getView(viewId);
            view.setImageResource(imageResId);
            return SparseArrayViewHolder.this;
        }

        public SparseArrayViewHolder setBackgroundColor(int viewId, int color) {
            View view = getView(viewId);
            view.setBackgroundColor(color);
            return SparseArrayViewHolder.this;
        }

        public SparseArrayViewHolder setBackgroundResource(int viewId, int backgroundRes) {
            View view = getView(viewId);
            view.setBackgroundResource(backgroundRes);
            return SparseArrayViewHolder.this;
        }

        public SparseArrayViewHolder setVisible(int viewId, boolean visible) {
            View view = getView(viewId);
            view.setVisibility(visible ? View.VISIBLE : View.GONE);
            return SparseArrayViewHolder.this;
        }

        public SparseArrayViewHolder setOnClickListener(int viewId, View.OnClickListener listener) {
            View view = getView(viewId);
            view.setOnClickListener(listener);
            return SparseArrayViewHolder.this;
        }

        public SparseArrayViewHolder setOnTouchListener(int viewId, View.OnTouchListener listener) {
            View view = getView(viewId);
            view.setOnTouchListener(listener);
            return SparseArrayViewHolder.this;
        }

        public SparseArrayViewHolder setOnLongClickListener(int viewId, View.OnLongClickListener
                listener) {
            View view = getView(viewId);
            view.setOnLongClickListener(listener);
            return SparseArrayViewHolder.this;
        }

        public SparseArrayViewHolder setTag(int viewId, Object tag) {
            View view = getView(viewId);
            view.setTag(tag);
            return SparseArrayViewHolder.this;
        }
    }

    public void setOnInViewClickListener(Integer key, onInternalClickListener onClickListener) {
        if (canClickItem == null)
            canClickItem = new HashMap<Integer, onInternalClickListener>();
        canClickItem.put(key, onClickListener);
    }

    public interface onInternalClickListener {
        public void OnClickListener(View parentV, View v, Integer position, Object values);
    }

}
