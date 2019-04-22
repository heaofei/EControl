package com.mxkj.econtrol.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mxkj.econtrol.R;
import com.mxkj.econtrol.bean.response.ResGetTimeRanking;

import java.util.List;

/**
 * Created by chanjun on 2017/9/6.
 * @Description:
 * ResGetTimeRanking.TimeRankBean
 */

public class UseNumberAdapter extends BaseAdapter {
    private boolean isEdit = false;

    private Context context;
    private  List<ResGetTimeRanking.TimeRankBean> list;
    private LayoutInflater mInflater;//布局装载器对象
    private int mView;

    public UseNumberAdapter(Context context , List<ResGetTimeRanking.TimeRankBean> list,int viewType) {
        this.context = context;
        this.list = list;
        this.mView = viewType;
        mInflater = LayoutInflater.from(context);
    }

    public void upDateAdapter(List<ResGetTimeRanking.TimeRankBean> mDatas){
        this.list =mDatas;
        notifyDataSetChanged();

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //将布局文件转化为View对象
        View view = mInflater.inflate(mView,null);

        TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
        TextView tv_number = (TextView) view.findViewById(R.id.tv_number);
        ImageView iv_number = (ImageView) view.findViewById(R.id.iv_number);

        tv_name.setText(list.get(position).getPartName());
        tv_number.setText(list.get(position).getCount()+"次");

        if (position==0){
            iv_number.setImageResource(R.drawable.ic_number_1st);
            tv_name.setTextColor(Color.parseColor("#666666"));
            tv_number.setTextColor(Color.parseColor("#666666"));
        }else if (position ==1){
            iv_number.setImageResource(R.drawable.ic_number_2nd);
            tv_name.setTextColor(Color.parseColor("#666666"));
            tv_number.setTextColor(Color.parseColor("#666666"));
        }else if (position ==2){
            iv_number.setImageResource(R.drawable.ic_number_3rd);
            tv_name.setTextColor(Color.parseColor("#666666"));
            tv_number.setTextColor(Color.parseColor("#666666"));
        }else if (position ==3){
            iv_number.setImageResource(R.drawable.ic_number_4th);
            tv_name.setTextColor(Color.parseColor("#999999"));
            tv_number.setTextColor(Color.parseColor("#999999"));
        }else if (position ==4){
            iv_number.setImageResource(R.drawable.ic_number_5th);
            tv_name.setTextColor(Color.parseColor("#999999"));
            tv_number.setTextColor(Color.parseColor("#999999"));
        }else if (position ==5){
            iv_number.setImageResource(R.drawable.ic_number_6th);
            tv_name.setTextColor(Color.parseColor("#999999"));
            tv_number.setTextColor(Color.parseColor("#999999"));
        }else if (position ==6){
            iv_number.setImageResource(R.drawable.ic_number_7th);
            tv_name.setTextColor(Color.parseColor("#999999"));
            tv_number.setTextColor(Color.parseColor("#999999"));
        }else if (position ==7){
            iv_number.setImageResource(R.drawable.ic_number_8th);
            tv_name.setTextColor(Color.parseColor("#999999"));
            tv_number.setTextColor(Color.parseColor("#999999"));
        }else if (position ==8){
            iv_number.setImageResource(R.drawable.ic_number_9th);
            tv_name.setTextColor(Color.parseColor("#999999"));
            tv_number.setTextColor(Color.parseColor("#999999"));
        }else if (position ==9){
            iv_number.setImageResource(R.drawable.ic_number_10th);
            tv_name.setTextColor(Color.parseColor("#999999"));
            tv_number.setTextColor(Color.parseColor("#999999"));
        }

        return view;
    }

}
