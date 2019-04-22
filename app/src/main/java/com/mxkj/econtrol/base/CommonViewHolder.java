package com.mxkj.econtrol.base;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.zhy.autolayout.utils.AutoUtils;

import org.w3c.dom.Text;

/**
 * Created by liangshan on 2017/3/30.
 *
 * @Description:通用viewHolder
 */

public class CommonViewHolder extends RecyclerView.ViewHolder {
    protected View convertView;
    protected SparseArray<View> views;

    public CommonViewHolder(View itemView) {
        super(itemView);
        convertView = itemView;
        //自动适配
        AutoUtils.auto(itemView);
        views = new SparseArray<>();
    }

    public <T extends View> T getView(int id) {
        View view = views.get(id);
        if (view == null) {
            view = convertView.findViewById(id);
            views.put(id, view);
        }
        return (T) view;

    }

    public void setText(int id, String text) {
        View view = getView(id);
        if (view instanceof TextView) {
            ((TextView) view).setText(text);
        }
    }

    public void setSrc(int id, String url) {
        View view = getView(id);

        Glide.with(view.getContext()).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).into((ImageView) view);
    }
    public void setSrc(int id, int  sourceid) {
        View view = getView(id);

        Glide.with(view.getContext()).load(sourceid).diskCacheStrategy(DiskCacheStrategy.ALL).into((ImageView) view);
    }


}
