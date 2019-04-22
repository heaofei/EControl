package com.mxkj.econtrol.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mxkj.econtrol.R;
import com.mxkj.econtrol.app.Config;
import com.mxkj.econtrol.bean.response.AdapterHouseControlLogList;
import com.mxkj.econtrol.bean.response.ResGetHouseControlLogList;
import com.mxkj.econtrol.utils.DateTimeUtil;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * Created by chan on 2018/4/10.
 *
 * @Description: 历史操作适配器
 */

public class HouseControlLogListAdapter extends BaseExpandableListAdapter {

    private List<AdapterHouseControlLogList> mDatas;
    private Context mContext;

    public HouseControlLogListAdapter(Context context, List<AdapterHouseControlLogList> mDatas) {
        this.mContext = context;
        this.mDatas = mDatas;
    }

    public  void  updateAdapter(List<AdapterHouseControlLogList> mDatas){
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mDatas.get(groupPosition) == null ? 0 : mDatas.get(groupPosition).getControlLogLists().size();
    }
    @Override
    public Object getGroup(int groupPosition) {
        return mDatas.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mDatas.get(groupPosition).getControlLogLists().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.notify_group_item, parent, false);
        }
        TextView title = (TextView) view.findViewById(R.id.tv_notify_group);
        View viewline = (View) view.findViewById(R.id.viewline);

        String time = DateTimeUtil.getTime(mDatas.get(groupPosition).getControlLogLists().get(0).getCreateTime());
        if (time.length() > 10) {
            time = time.substring(5, 10);
            time = time.replace("-", "月");
        }
        title.setText(time + "日");

        if (groupPosition == 0) {
            viewline.setVisibility(View.GONE);
        } else {
            viewline.setVisibility(View.VISIBLE);
        }

        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.notify_content_item, parent, false);
            AutoUtils.auto(view);
        }
        TextView title = (TextView) view.findViewById(R.id.tv_notify_content);
        TextView time = (TextView) view.findViewById(R.id.tv_time);
        TextView tv_nick_name = (TextView) view.findViewById(R.id.tv_nick_name);
        ImageView header = (ImageView) view.findViewById(R.id.imv_header);

        AdapterHouseControlLogList.HouseControlLogList logList = mDatas.get(groupPosition).getControlLogLists().get(childPosition);

//        String time = DateTimeUtil.getTime(System.currentTimeMillis(), logList.getCreateTime());

        String times = "";

        if (DateTimeUtil.isToday(logList.getCreateTime())) { // 是今天
            times = DateTimeUtil.getTimeHM(System.currentTimeMillis(), logList.getCreateTime());
        } else { // 不是今天
            times = DateTimeUtil.getTimeHM(logList.getCreateTime());
        }

        time.setText(times);

        String showStr = "";
        if (!TextUtils.isEmpty(logList.getShow())) {
            showStr = "把" + logList.getShow();
            if (!TextUtils.isEmpty(logList.getCommand())) {
                /*if (logList.getCommand().indexOf("d") != -1) {
                    showStr = showStr.replace("25%", "柔光");
                    showStr = showStr.replace("50%", "中光");
                    showStr = showStr.replace("75%", "亮光");
                    showStr = showStr.replace("100%", "强光");
                }*/
                if (logList.getCommand().indexOf("rgb") != -1) {

//                    设置为 自定义颜色
                    if (showStr.indexOf("255,255,255") == -1
                            && showStr.indexOf("255,107,107") == -1
                            && showStr.indexOf("247,153,255") == -1
                            && showStr.indexOf("130,148,255") == -1
                            && showStr.indexOf("144,222,255") == -1
                            && showStr.indexOf("107,255,255") == -1
                            && showStr.indexOf("211,255,153") == -1) {
//                            showStr= "设置为 自定义颜色";

                        int dex = showStr.lastIndexOf("设置调色为");
                        showStr = showStr.substring(0, dex);
                        showStr += "设置调色为定义颜色";

                    }

//                    牛奶白/西瓜红/可爱粉/海洋蓝/浅天蓝/羞涩青/绿油油
                    showStr = showStr.replace("255,255,255", "牛奶白");
                    showStr = showStr.replace("255,107,107", "西瓜红");
                    showStr = showStr.replace("247,153,255", "可爱粉");
                    showStr = showStr.replace("130,148,255", "海洋蓝");
                    showStr = showStr.replace("144,222,255", "浅天蓝");
                    showStr = showStr.replace("107,255,255", "羞涩青");
                    showStr = showStr.replace("211,255,153", "绿油油");

                }
            }
        }


        header.setImageResource(R.drawable.ic_no_head);
        if (!TextUtils.isEmpty(logList.getHeadPicture())) {
            Glide.with(mContext).load(Config.BASE_PIC_URL + logList.getHeadPicture()).error(R.drawable.ic_no_head)
                    .into(header);
        }

        title.setText(showStr);
        tv_nick_name.setText(logList.getNiceName());


    /*    View splitLine = view.findViewById(R.id.split_line);
        View splitBottom = view.findViewById(R.id.split_bottom);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(AutoUtils.getPercentWidthSize(2), ViewGroup.LayoutParams.MATCH_PARENT);
        params.setMargins(AutoUtils.getPercentWidthSize(64), 0, 0, 0);
        splitLine.setLayoutParams(params);
        splitBottom.setVisibility(View.VISIBLE);
        RelativeLayout.LayoutParams headerParams = new RelativeLayout.LayoutParams(AutoUtils.getPercentWidthSize(32), AutoUtils.getPercentWidthSize(32));
        headerParams.setMargins(AutoUtils.getPercentWidthSize(50), 0, 0, 0);
        headerParams.addRule(RelativeLayout.CENTER_VERTICAL);
        header.setLayoutParams(headerParams);
*/


        /*if (mDatas.get(groupPosition).getHouseControlLogList().size() == 1) {
            splitLine.setVisibility(View.INVISIBLE);
            splitBottom.setVisibility(View.INVISIBLE);
            headerParams.width = AutoUtils.getPercentWidthSize(56);
            headerParams.height = AutoUtils.getPercentWidthSize(56);
            headerParams.setMargins(AutoUtils.getPercentWidthSize(35), 0, 0, 0);
            header.setLayoutParams(headerParams);
        } else if (childPosition == 0) {
            int marginTop = view.getHeight() == 0 ? AutoUtils.getPercentWidthSize(100) / 2 : view.getHeight() / 2;
            params.setMargins(AutoUtils.getPercentWidthSize(64), marginTop, 0, 0);
            splitLine.setLayoutParams(params);
            headerParams.width = AutoUtils.getPercentWidthSize(56);
            headerParams.height = AutoUtils.getPercentWidthSize(56);
            headerParams.setMargins(AutoUtils.getPercentWidthSize(35), 0, 0, 0);
            header.setLayoutParams(headerParams);
        } else if (childPosition == mDatas.get(groupPosition).getHouseControlLogList().size() - 1) {
            int marginBottom = view.getHeight() == 0 ? AutoUtils.getPercentWidthSize(100) / 2 : view.getHeight() / 2;
            params.setMargins(AutoUtils.getPercentWidthSize(64), 0, 0, marginBottom);
            splitLine.setLayoutParams(params);
            splitBottom.setVisibility(View.GONE);
        }*/


        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
