package com.mxkj.econtrol.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.allen.library.SuperTextView;
import com.bumptech.glide.Glide;
import com.mxkj.econtrol.R;
import com.mxkj.econtrol.app.APP;
import com.mxkj.econtrol.app.Config;
import com.mxkj.econtrol.base.CommonAdapter;
import com.mxkj.econtrol.base.CommonViewHolder;
import com.mxkj.econtrol.bean.response.ResGetAppPushMessageList;
import com.mxkj.econtrol.bean.response.ResGetAtHomeUserList;
import com.mxkj.econtrol.utils.TextUtil;
import com.mxkj.econtrol.utils.gateway.DeviceInfo;
import com.mxkj.econtrol.widget.CustomAlignTextView;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by chanjun on 2017/9/6.
 * @Description:
 * 消息中心，消息列表adapter
 */

public class MessageListAdapter extends CommonAdapter<ResGetAppPushMessageList.PageBean.ContentBean> {

    private Context context;
    private View.OnClickListener mListener;

    public MessageListAdapter(Context context, List<ResGetAppPushMessageList.PageBean.ContentBean> mDatas, int mLayoutId,View.OnClickListener listener) {
        super(mDatas, mLayoutId);
        this.context = context;
        this.mListener =listener;
    }


    public void upDateAdapter(List<ResGetAppPushMessageList.PageBean.ContentBean> mList){
        this.mDatas =mList;
        notifyDataSetChanged();
    }
    @Override
    public void convert(CommonViewHolder viewHolder, ResGetAppPushMessageList.PageBean.ContentBean contentBean) {

        CustomAlignTextView tv_content = viewHolder.getView(R.id.tv_content);
        TextView tv_time = viewHolder.getView(R.id.tv_time);
        tv_content.setAlignTextColor(context.getResources().getColor(R.color.text_black_666));
        tv_content.setAlignTextSize(sp2px(context, 15));
        //top,buttom暂时无效, 可通过view或space来设置,欢迎高手来指导
        tv_content.setAlignPadding(dip2px(context, 20), dip2px(context, 20), 0, 0);
        tv_content.setText(contentBean.getContent());
        tv_time.setText(contentBean.getInsertTime());
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue
     * @return
     */

    public int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
