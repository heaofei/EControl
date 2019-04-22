package com.mxkj.econtrol.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mxkj.econtrol.R;
import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.base.CommonAdapter;
import com.mxkj.econtrol.base.CommonViewHolder;
import com.mxkj.econtrol.bean.response.ResGetAppPushMessageList;
import com.mxkj.econtrol.bean.response.ResLockPowerList;
import com.mxkj.econtrol.bean.response.TcpResSearchLockListRespones;

import java.util.List;

/**
 * Created by chanjun on 2018/1/15.
 *  搜索 门锁 列表adapter
 */

public class SearchLockListAdapter extends CommonAdapter<TcpResSearchLockListRespones> {

    private Context context;
    private View.OnClickListener mListener;

    public SearchLockListAdapter(Context context, List<TcpResSearchLockListRespones> mDatas, int mLayoutId, View.OnClickListener listener) {
        super(mDatas, mLayoutId);
        this.context = context;
        this.mListener =listener;
    }


    public void upDateAdapter(List<TcpResSearchLockListRespones> mDatas){
        this.mDatas =mDatas;
        notifyDataSetChanged();
    }

    @Override
    public void convert(CommonViewHolder viewHolder, TcpResSearchLockListRespones contentBean) {

        TextView tv_name = viewHolder.getView(R.id.tv_name);
        TextView tv_add = viewHolder.getView(R.id.tv_add);

        tv_name.setText(contentBean.getCommand().getIeeeAddress());

        tv_add.setTag(contentBean); // 在activity里面做点击事件
        tv_add.setOnClickListener(mListener);


    }
}
