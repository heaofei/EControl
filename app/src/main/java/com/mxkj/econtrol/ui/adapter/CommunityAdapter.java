package com.mxkj.econtrol.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.mxkj.econtrol.R;
import com.mxkj.econtrol.app.APP;
import com.mxkj.econtrol.base.CommonAdapter;
import com.mxkj.econtrol.base.CommonViewHolder;
import com.mxkj.econtrol.bean.response.CommunityContent;
import com.mxkj.econtrol.utils.DateTimeUtil;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

import static com.mxkj.econtrol.app.Config.BASE_PIC_URL;

/**
 * Created by liangshan on 2017/4/21.
 *
 * @Description： 社区说说适配器
 */

public class CommunityAdapter extends CommonAdapter<CommunityContent> {
    private int mPicMax = AutoUtils.getPercentWidthSize((int) (750 * 0.8f));
    private View.OnClickListener mListener;

    public CommunityAdapter(List<CommunityContent> mDatas, int mLayoutId, View.OnClickListener listener) {
        super(mDatas, mLayoutId);
        mListener = listener;
    }

    @Override
    public void convert(CommonViewHolder viewHolder, CommunityContent communityContent) {

        if (communityContent.getUser() != null) {
            viewHolder.setText(R.id.tv_user_name, communityContent.getUser().getNiceName() + "");
            if (!TextUtils.isEmpty(communityContent.getUser().getHeadPicture())) {
                viewHolder.setSrc(R.id.imv_header_pic, BASE_PIC_URL + communityContent.getUser().getHeadPicture());
            }
            View view = viewHolder.getView(R.id.imv_header_pic);
            view.setTag(R.id.imv_header_pic, communityContent.getUser());
            view.setOnClickListener(mListener);
        }
       /* if (communityContent.getEstate() != null) {
            viewHolder.getView(R.id.tv_estate).setVisibility(View.VISIBLE);
//            viewHolder.setText(R.id.tv_estate, communityContent.getEstate().getCity().getRegionName() + "·" + communityContent.getEstate().getName());
        } else {
            viewHolder.getView(R.id.tv_estate).setVisibility(View.GONE);
        }*/
        viewHolder.setText(R.id.tv_content, communityContent.getMsg());
        viewHolder.setText(R.id.tv_trumb_up, "" + communityContent.getThumbUpCount());
        viewHolder.setText(R.id.tv_comment, "" + communityContent.getCommentsCount());
//        viewHolder.setText(R.id.tv_time, DateTimeUtil.getTime(System.currentTimeMillis(), Long.valueOf(communityContent.getCreatTime())));
        ImageView start = viewHolder.getView(R.id.imv_start);
        if (APP.user != null && communityContent.getHaveThumbUp() == 1) {
            //已点赞
            start.setImageResource(R.drawable.icon_have_start);
        } else {
            start.setImageResource(R.drawable.icon_good);
        }
        if (mListener != null) {
            start.setTag(communityContent);
            start.setOnClickListener(mListener);
        }
        ImageView view = viewHolder.getView(R.id.imv_community_pic);

        if (communityContent.getPic() == null) {
            view.setVisibility(View.GONE);
        } else {
            view.setVisibility(View.VISIBLE);

            if (mListener != null) {
                view.setTag(communityContent);
                view.setOnClickListener(mListener);
            }
            int picWidth = communityContent.getWidth();
            int picHeight = communityContent.getHeight();
            int imageViewH = 0;
            int imageViewW = 0;

            if (picWidth > picHeight && picWidth > mPicMax) {
                imageViewW = mPicMax;
                float scale = picHeight / (float) picWidth;
                imageViewH = (int) (imageViewW * scale);
            } else if (picHeight >= picWidth && picHeight > mPicMax) {
                imageViewH = mPicMax;
                float scale = picWidth / (float) picHeight;
                imageViewW = (int) (imageViewH * scale);
            } else {
                imageViewW = picWidth;
                imageViewH = picHeight;
            }
            ViewGroup.LayoutParams params = new LinearLayout.LayoutParams(imageViewW, imageViewH);
            if (params.width != 0 && params.height != 0)
                view.setLayoutParams(params);
            view.setImageResource(R.drawable.pic_defaut_bg);
            loadIntoUseFitWidth(view.getContext(), BASE_PIC_URL + communityContent.getPic(), view);

        }


    }


    /**
     * 自适应宽度加载图片。保持图片的长宽比例不变，通过修改imageView的高度来完全显示图片。
     */
    public void loadIntoUseFitWidth(final Context context, final String imageUrl, final ImageView imageView) {
//        imageView.setTag(R.id.imv_pic, imageUrl);
        Glide.with(context)
                .load(imageUrl)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(new SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//                        if (imageView.getTag(R.id.imv_pic) == null || !imageView.getTag(R.id.imv_pic).equals(imageUrl)) {
//                            return;
//                        }
                        int picWidth = resource.getWidth();
                        int picHeight = resource.getHeight();
                        int imageViewH = 0;
                        int imageViewW = 0;
                        if (picWidth > picHeight && picWidth > mPicMax) {
                            imageViewW = mPicMax;
                            float scale = picHeight / (float) picWidth;
                            imageViewH = (int) (imageViewW * scale);
                        } else if (picHeight >= picWidth && picHeight > mPicMax) {
                            imageViewH = mPicMax;
                            float scale = picWidth / (float) picHeight;
                            imageViewW = (int) (imageViewH * scale);
                        } else {
                            imageViewW = picWidth;
                            imageViewH = picHeight;
                        }
                        ViewGroup.LayoutParams params = new LinearLayout.LayoutParams(imageViewW, imageViewH);

                        imageView.setLayoutParams(params);
                        imageView.setImageBitmap(resource);
                    }
                });
    }
}
