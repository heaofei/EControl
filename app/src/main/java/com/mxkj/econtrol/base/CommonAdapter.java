package com.mxkj.econtrol.base;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.List;

/**
 * Created by liangshan on 2017/3/30.
 *
 * @Description:通用RecyclerView适配器
 */

public abstract class CommonAdapter<T> extends RecyclerView.Adapter<CommonViewHolder> {
    //数据集
    protected List<T> mDatas;
    //布局Id
    protected int mLayoutId;
    //item点击事件监听
    protected OnItemClickListener onItemClickListener;
    //itemd长按事件监听
    protected OnItemLongClickListener onItemLongClickListener;
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;

    public CommonAdapter(List<T> mDatas, int mLayoutId) {
        this.mDatas = mDatas;
        this.mLayoutId = mLayoutId;
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(mLayoutId, parent, false);
        return new CommonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CommonViewHolder holder, final int position) {
        //当设置了点击事件且没用设置长按时
        if (onItemClickListener != null ) {
            holder.convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(holder.convertView, position);
                }
            });
        }
        if (onItemLongClickListener != null) {
            holder.convertView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onItemLongClickListener.onItemLongClick(holder.convertView, position);
                    return true;
                }
            });
        }
        convert(holder, mDatas.get(position));

    }


    @Override
    public int getItemCount() {
        return mDatas != null ? mDatas.size() : 0;
    }

    public abstract void convert(CommonViewHolder viewHolder, T t);

    //点击监听器
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    //长按监听器
    public interface OnItemLongClickListener {
        void onItemLongClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener longClickListener) {
        onItemLongClickListener = longClickListener;
    }
}
