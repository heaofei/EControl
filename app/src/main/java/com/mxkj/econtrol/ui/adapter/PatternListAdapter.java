package com.mxkj.econtrol.ui.adapter;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mxkj.econtrol.R;
import com.mxkj.econtrol.app.APP;
import com.mxkj.econtrol.app.Config;
import com.mxkj.econtrol.base.CommonAdapter;
import com.mxkj.econtrol.base.CommonViewHolder;
import com.mxkj.econtrol.bean.response.ResGetPatternList;
import com.mxkj.econtrol.bean.response.Scene;
import com.mxkj.econtrol.utils.LogUtil;
import com.mxkj.econtrol.utils.TextUtil;

import java.util.List;

/**
 * Created by liangshan on 2017/3/31.
 * 场景Adapter
 *
 * @Description:
 */

public class PatternListAdapter extends CommonAdapter<ResGetPatternList.ListBeaner> {

    public PatternListAdapter(List<ResGetPatternList.ListBeaner> mDatas, int mLayoutId) {
        super(mDatas, mLayoutId);
    }

    public void upDateAdapter(List<ResGetPatternList.ListBeaner> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();

    }


    @Override
    public void convert(CommonViewHolder viewHolder, ResGetPatternList.ListBeaner listBeaner) {
        TextView tv_scene_name = viewHolder.getView(R.id.tv_scene_name);
        ImageView imv_scene_pic = viewHolder.getView(R.id.imv_scene_pic);

        String status = listBeaner.getStatus(); // status 0 未应用，1-已应用
            /*if (listBeaner.getType().equals("1") ) {      // 回家模式图片
//                tv_scene_name.setText("全开模式");// 场景名
                if (status.equals("1")) {
                    imv_scene_pic.setImageResource(R.drawable.home_content_allopen_selected);
//                    tv_scene_name.setTextColor(Color.parseColor("#FFAD5C"));
                    listBeaner.setClick(true);
                } else {
                    imv_scene_pic.setImageResource(R.drawable.home_content_allopen_default);
//                    tv_scene_name.setTextColor(Color.parseColor("#c3c3c3"));
                    listBeaner.setClick(false);
                }
            } else if (listBeaner.getType().equals("0") ) {   // 离家模式图片
//                tv_scene_name.setText("全关模式");// 场景名
                if (status.equals("1")) {
                    imv_scene_pic.setImageResource(R.drawable.home_content_allclose_selected);
//                    tv_scene_name.setTextColor(Color.parseColor("#8D76FF"));
                    listBeaner.setClick(true);
                } else {
                    imv_scene_pic.setImageResource(R.drawable.home_content_allclose_default);
//                    tv_scene_name.setTextColor(Color.parseColor("#c3c3c3"));
                    listBeaner.setClick(false);
                }
            } else {                               // 其他模式图片
//                tv_scene_name.setText(listBeaner.getName());// 场景名
                if (status.equals("1")) {  // 选中状态
//                    imv_scene_pic.setImageResource(R.drawable.home_content_zidingyi_selected);
//                    tv_scene_name.setTextColor(Color.parseColor("#FF695A"));
                    Glide.with(APP.getInstance()).load(Config.BASE_PIC_URL + listBeaner.getSelectPic()).error(R.drawable.home_content_zidingyi_selected).into(imv_scene_pic);
                    listBeaner.setClick(true);
                } else {
//                    imv_scene_pic.setImageResource(R.drawable.home_content_zidingyi_default);
//                    tv_scene_name.setTextColor(Color.parseColor("#c3c3c3"));
                    Glide.with(APP.getInstance()).load(Config.BASE_PIC_URL + listBeaner.getPic()).error(R.drawable.home_content_zidingyi_default).into(imv_scene_pic);
                    listBeaner.setClick(false);
                }
            }*/

        if (status.equals("1")) {  // 选中状态
            Glide.with(APP.getInstance()).load(Config.BASE_PIC_URL + listBeaner.getSelectPic()).error(R.drawable.home_content_zidingyi_selected).into(imv_scene_pic);
            listBeaner.setClick(true);
        } else {
            Glide.with(APP.getInstance()).load(Config.BASE_PIC_URL + listBeaner.getPic()).error(R.drawable.home_content_zidingyi_default).into(imv_scene_pic);
            listBeaner.setClick(false);
        }


        if (TextUtils.isEmpty(listBeaner.getMsName())) {
             tv_scene_name.setText(listBeaner.getName());// 场景名
        }else {
             tv_scene_name.setText(listBeaner.getMsName());// 场景名
        }



    }
}
