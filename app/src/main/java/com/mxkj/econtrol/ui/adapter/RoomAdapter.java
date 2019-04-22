package com.mxkj.econtrol.ui.adapter;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mxkj.econtrol.R;
import com.mxkj.econtrol.app.APP;
import com.mxkj.econtrol.app.Config;
import com.mxkj.econtrol.base.CommonAdapter;
import com.mxkj.econtrol.base.CommonViewHolder;
import com.mxkj.econtrol.bean.response.Room;
import com.mxkj.econtrol.utils.TextUtil;

import java.util.List;

/**
 * Created by liangshan on 2017/3/31.
 * 房间Adapter
 *
 * @Description:
 */

public class RoomAdapter extends CommonAdapter<Room> {
    private boolean isEdit = false;

    public RoomAdapter(List<Room> mDatas, int mLayoutId) {
        super(mDatas, mLayoutId);

    }

    public void upDateAdapter(List<Room> mDatas, boolean isEdit) {
        this.mDatas = mDatas;
        this.isEdit = isEdit;
        notifyDataSetChanged();
    }

    @Override
    public void convert(CommonViewHolder viewHolder, Room room) {
        ImageView imv_scene_pic = viewHolder.getView(R.id.imv_scene_pic);
        TextView tv_scene_name = viewHolder.getView(R.id.tv_scene_name);
        RelativeLayout rl_item = viewHolder.getView(R.id.rl_item);
        RelativeLayout rl_edit_item = viewHolder.getView(R.id.rl_edit_item);

        tv_scene_name.setText(TextUtil.setTest(room.getName()));
//        viewHolder.setSrc(R.id.imv_scene_pic, Config.BASE_PIC_URL + s.getSmallPic());


        if (isEdit) { // 是否编辑状态
            rl_edit_item.setBackgroundResource(R.color.layou_menu_edit_bg_edit);
            tv_scene_name.setTextColor(Color.parseColor("#ffffffff"));
            if (room.getId().equals("常用")) {
                rl_edit_item.setBackgroundResource(R.color.layou_menu_edit_bg_defult);
            } else {
                rl_edit_item.setBackgroundResource(R.color.layou_menu_edit_bg_edit);
            }
            rl_item.setBackgroundResource(R.color.layou_menu_edit_bg_defult);
            Glide.with(APP.getInstance()).load(Config.BASE_PIC_URL + room.getSmallPic()).error(R.drawable.ic_home_commonly_used_default).into(imv_scene_pic);

        } else {   // 正常状态
            rl_edit_item.setBackgroundResource(R.color.layou_menu_edit_bg_defult);

            if (room.isClick()) {  // 选中
                rl_item.setBackgroundResource(R.color.layou_menu_edit_bg_edit);
                tv_scene_name.setTextColor(Color.parseColor("#ff695a"));
                if (room.getId().equals("常用") || room.getId().equals("体验常用")) {
                    imv_scene_pic.setImageResource(R.drawable.ic_home_commonly_used_selected);
                } else {
                    Glide.with(APP.getInstance()).load(Config.BASE_PIC_URL + room.getSmallSelectPic()).error(R.drawable.ic_home_commonly_used_default).into(imv_scene_pic);
                }
            } else {
                tv_scene_name.setTextColor(Color.parseColor("#ffffffff"));
                rl_item.setBackgroundResource(R.color.layou_menu_edit_bg_defult);
                Glide.with(APP.getInstance()).load(Config.BASE_PIC_URL + room.getSmallPic()).error(R.drawable.ic_home_commonly_used_default).into(imv_scene_pic);
            }
        }

    }
}
