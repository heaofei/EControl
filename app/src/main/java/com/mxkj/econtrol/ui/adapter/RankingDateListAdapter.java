package com.mxkj.econtrol.ui.adapter;

import android.graphics.Color;
import android.widget.ImageView;
import android.widget.TextView;

import com.mxkj.econtrol.R;
import com.mxkj.econtrol.base.CommonAdapter;
import com.mxkj.econtrol.base.CommonViewHolder;
import com.mxkj.econtrol.bean.response.ResGetRankingDateList;
import com.mxkj.econtrol.bean.response.ResGetTimeRanking;

import java.util.List;

/**
 * Created by chanjun on 2017/9/6.
 * @Description:
 */

public class RankingDateListAdapter extends CommonAdapter<ResGetRankingDateList.RankingDateBean> {
    private boolean isEdit = false;

    public RankingDateListAdapter(List<ResGetRankingDateList.RankingDateBean> mDatas, int mLayoutId) {
        super(mDatas, mLayoutId);
    }
    public void upDateAdapter(List<ResGetRankingDateList.RankingDateBean> mDatas){
        this.mDatas =mDatas;
        notifyDataSetChanged();

    }

    @Override
    public void convert(CommonViewHolder viewHolder, ResGetRankingDateList.RankingDateBean timeRankBean) {
        TextView tv_name = viewHolder.getView(R.id.tv_name);
        tv_name.setText(timeRankBean.getDateText());
        int indexOf = mDatas.indexOf(timeRankBean);

        if (timeRankBean.isClick()){
            tv_name.setTextColor(Color.parseColor("#ff695A"));
        }else {
            tv_name.setTextColor(Color.parseColor("#666666"));
        }



    }
}
