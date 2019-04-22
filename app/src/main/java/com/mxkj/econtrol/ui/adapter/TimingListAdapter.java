package com.mxkj.econtrol.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mxkj.econtrol.R;
import com.mxkj.econtrol.bean.response.ResGetHouseAllPartList;
import com.mxkj.econtrol.bean.response.ResGetScenePartOperatorTimerList;

import java.util.ArrayList;
import java.util.List;

public class TimingListAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<ResGetScenePartOperatorTimerList.ListBean> mList;
    //布局Id
    protected int mLayoutId;
    private View.OnClickListener mListener;


    public TimingListAdapter(Context context, ArrayList<ResGetScenePartOperatorTimerList.ListBean> list,int mLayoutId,View.OnClickListener listener) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mList = list;
        this.mListener =listener;
        this.mLayoutId = mLayoutId;
    }

    public void upDateAdapter(ArrayList<ResGetScenePartOperatorTimerList.ListBean> list){
        this.mList = list;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(mInflater.inflate(mLayoutId, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final MyViewHolder viewHolder = (MyViewHolder) holder;

        viewHolder.tv_time.setText(mList.get(position).getTime());
        viewHolder.iv_switch.setTag(mList.get(position));
        viewHolder.iv_switch.setOnClickListener(mListener); // 在activity里面做点击事件


        if (mList.get(position).getStatus().equals("1")) {
            viewHolder.iv_switch.setImageResource(R.drawable.icon_btn_on);
        }else {
            viewHolder.iv_switch.setImageResource(R.drawable.icon_btn_off);
        }


    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    public void removeItem(int position) {
        mList.remove(position);
        notifyDataSetChanged();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView iv_switch;
        public TextView tv_time;
        public TextView delete;
        public LinearLayout layout;

        public MyViewHolder(View itemView) {
            super(itemView);
            iv_switch = (ImageView) itemView.findViewById(R.id.iv_switch);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            delete = (TextView) itemView.findViewById(R.id.item_delete);
            layout = (LinearLayout) itemView.findViewById(R.id.item_layout);
        }
    }
}
