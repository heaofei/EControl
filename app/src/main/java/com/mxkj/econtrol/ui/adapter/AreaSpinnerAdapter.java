package com.mxkj.econtrol.ui.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mxkj.econtrol.R;
import com.mxkj.econtrol.bean.response.Area;

import java.util.List;

/**
 * Created by liangshan on 2017/4/12.
 *
 * @Description:
 */

public class AreaSpinnerAdapter extends BaseAdapter {
    protected Context mContext;
    protected List<Area> mData;

    public AreaSpinnerAdapter(Context context, List<Area> data) {
        mContext = context;
        mData = data;

    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = new TextView(mContext);
        }
        TextView textView = (TextView) view;
        textView.setGravity(Gravity.CENTER);
        textView.setText(mData.get(position).getAreaName());
        return view;
    }
}
