package com.mxkj.econtrol.ui.adapter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mxkj.econtrol.R;
import com.mxkj.econtrol.app.APP;
import com.mxkj.econtrol.app.Config;
import com.mxkj.econtrol.app.Msg;
import com.mxkj.econtrol.base.CommonAdapter;
import com.mxkj.econtrol.base.CommonViewHolder;
import com.mxkj.econtrol.bean.EventBusUIMessage;
import com.mxkj.econtrol.bean.response.MyHouse;
import com.mxkj.econtrol.ui.activity.QRCodeActivity;
import com.mxkj.econtrol.utils.AbToastUtil;
import com.mxkj.econtrol.utils.ResourceUtil;
import com.mxkj.econtrol.view.activity.HouseUserListActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by liangshan on 2017/4/14.
 *
 * @Description:
 */

public class MyHouseListAdapter extends CommonAdapter<MyHouse> {
    private List<MyHouse> mDatas;
    private boolean showDelete = false;
    private View.OnClickListener mListener;
    private Context context;

    public MyHouseListAdapter(Context context, List<MyHouse> mDatas, int mLayoutId, View.OnClickListener listener) {
        super(mDatas, mLayoutId);
        this.mDatas = mDatas;
        this.mListener = listener;
        this.context = context;
   /*     //添加新方案
        MyHouse addNew = new MyHouse();
        mDatas.add(addNew);*/
    }

    public void upDateAdapter(List<MyHouse> mDatas, boolean isShow) {
        this.mDatas = mDatas;
        this.showDelete = isShow;
        notifyDataSetChanged();

    }

    @Override
    public void convert(final CommonViewHolder viewHolder, final MyHouse myHouse) {

        TextView tv_house_estate_name = viewHolder.getView(R.id.tv_house_estate_name);
        TextView tv_house_name = viewHolder.getView(R.id.tv_house_name);
        TextView tv_owner = viewHolder.getView(R.id.tv_owner);
        TextView tv_state = viewHolder.getView(R.id.tv_state);
        TextView tv_look = viewHolder.getView(R.id.tv_look);// 查看二维码
        ImageView iv_delete = viewHolder.getView(R.id.iv_delete);// 删除
        ImageView iv_code = viewHolder.getView(R.id.iv_code);// 二维码

        RelativeLayout rl_item = viewHolder.getView(R.id.rl_item);//
        RelativeLayout rl_state = viewHolder.getView(R.id.rl_state);//
        LinearLayout ll_item = viewHolder.getView(R.id.ll_item);//
        RelativeLayout rl_code = viewHolder.getView(R.id.rl_code);// 查看二维码
        LinearLayout ll_isdefaul = viewHolder.getView(R.id.ll_isdefaul);// 是否默认
        ImageView iv_isdefaul = viewHolder.getView(R.id.iv_isdefaul);// 是否默认
        TextView tv_isdefaul = viewHolder.getView(R.id.tv_isdefaul);// 是否默认
        LinearLayout ll_change = viewHolder.getView(R.id.ll_change);// 修改信息

        if (showDelete) {
            iv_delete.setVisibility(View.VISIBLE);
            float toXDelta = iv_delete.getWidth();// 删除键宽度
            ObjectAnimator objectAnimatorTranslate = ObjectAnimator.ofFloat(iv_delete, "translationX", 0f, iv_delete.getWidth() * 2);
            objectAnimatorTranslate.setDuration(500);
            objectAnimatorTranslate.start();
            ObjectAnimator objectAnimatorTranslate02 = ObjectAnimator.ofFloat(rl_item, "translationX", 0f, iv_delete.getWidth() * 2);
            objectAnimatorTranslate02.setDuration(500);
            objectAnimatorTranslate02.start();
        } else {
            if (iv_delete.getVisibility() == View.VISIBLE) {
                ObjectAnimator objectAnimatorTranslate = ObjectAnimator.ofFloat(iv_delete, "translationX", 100f, 0f);
                objectAnimatorTranslate.setDuration(500);
                objectAnimatorTranslate.start();
                ObjectAnimator objectAnimatorTranslate02 = ObjectAnimator.ofFloat(rl_item, "translationX", 100f, 0f);
                objectAnimatorTranslate02.setDuration(500);
                objectAnimatorTranslate02.start();
            }
        }
        tv_house_estate_name.setText(myHouse.getHousingEstate());
        tv_house_name.setText(myHouse.getBuilding() + "-" + myHouse.getHouseNo());
        if (myHouse.getBindType().equals("0")) {
            //是管理员
            tv_owner.setText("户主");
        } else {
            tv_owner.setText("家庭用户");
        }
        if (myHouse.getState().equals("0")) {  //状态：0、未审核，1、审核通过，2、拒绝',
            tv_state.setText("审核中");
            ll_change.setVisibility(View.GONE);
            ll_isdefaul.setVisibility(View.GONE);
            rl_code.setVisibility(View.GONE);
            rl_state.setVisibility(View.VISIBLE);
            tv_state.setText("审核中");

            ll_item.setBackgroundResource(R.drawable.center_home_card_survey);
            tv_state.setTextColor(Color.parseColor("#FEB57E"));

        } else if (myHouse.getState().equals("2")) {
            tv_state.setText("审核失败");
            ll_isdefaul.setVisibility(View.GONE);
            ll_change.setVisibility(View.GONE);
            rl_code.setVisibility(View.GONE);
            rl_state.setVisibility(View.VISIBLE);

            iv_isdefaul.setImageResource(R.drawable.center_home_card_choose_default);
            iv_code.setImageResource(R.drawable.center_home_card_code_default);
            ll_item.setBackgroundResource(R.drawable.center_home_card_fail);
            tv_isdefaul.setTextColor(context.getResources().getColor(R.color.text_black_c3c3c3));

        } else {
            ll_change.setVisibility(View.GONE);
            ll_isdefaul.setVisibility(View.VISIBLE);
            rl_code.setVisibility(View.VISIBLE);
            rl_state.setVisibility(View.GONE);

            if (myHouse.getIsDefault() == 0) { // 默认状态:0、不；1、是
                iv_isdefaul.setImageResource(R.drawable.center_home_card_choose_default);
                iv_code.setImageResource(R.drawable.center_home_card_code_default);
                ll_item.setBackgroundResource(R.drawable.center_home_card_fail);
                tv_isdefaul.setTextColor(context.getResources().getColor(R.color.text_black_c3c3c3));
            } else {
                iv_isdefaul.setImageResource(R.drawable.center_home_card_choose_selected);
                iv_code.setImageResource(R.drawable.center_home_card_code_inuse);
                ll_item.setBackgroundResource(R.drawable.center_home_card_inuse);
                tv_isdefaul.setTextColor(context.getResources().getColor(R.color.text_orangereg02));
            }

        }


        ll_isdefaul.setTag(myHouse); // 在activity里面做点击事件
        ll_isdefaul.setOnClickListener(mListener);

        iv_delete.setTag(myHouse); // 在activity里面做点击事件
        iv_delete.setOnClickListener(mListener);

        ll_change.setTag(myHouse); // 在activity里面做点击事件
        ll_change.setOnClickListener(mListener);

        rl_code.setTag(myHouse); // 在activity里面做点击事件
        rl_code.setOnClickListener(mListener);

    /*    rl_code.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), QRCodeActivity.class);
                    intent.putExtra("houseId", myHouse.getId());
                    v.getContext().startActivity(intent);
                }
            });
            */

    }
}
