package com.mxkj.econtrol.ui.adapter;

import android.content.pm.ActivityInfo;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
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
import com.mxkj.econtrol.bean.response.CommunityContent;
import com.mxkj.econtrol.utils.TextUtil;
import com.mxkj.econtrol.view.activity.ArticleDetailsActivity;

import java.util.List;


import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;


/**
 * Created by chan on 2018/7/4.
 *
 * @Description： 广场文章视频列表适配去
 */

public class InformationAdapter extends CommonAdapter<CommunityContent> {
    private View.OnClickListener mListener;

    public InformationAdapter(List<CommunityContent> mDatas, int mLayoutId, View.OnClickListener listener) {
        super(mDatas, mLayoutId);
        mListener = listener;
    }

    public void upDateAdapter(List<CommunityContent> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }


    @Override
    public void convert(CommonViewHolder viewHolder, CommunityContent communityContent) {

        LinearLayout ll_video = viewHolder.getView(R.id.ll_video); // 视频
        LinearLayout ll_imagetext = viewHolder.getView(R.id.ll_imagetext); // 图文

        RelativeLayout rl_type01 = viewHolder.getView(R.id.rl_type01); // type 1专题
        RelativeLayout rl_type02 = viewHolder.getView(R.id.rl_type02); // type 2大图
        RelativeLayout rl_type03 = viewHolder.getView(R.id.rl_type03); // type 3小图
        RelativeLayout rl_type04 = viewHolder.getView(R.id.rl_type04); // type 4单图

        ImageView iv_01 = viewHolder.getView(R.id.iv_01); // 专题封面
        ImageView iv_02 = viewHolder.getView(R.id.iv_02); // 大图封面
        ImageView iv_06 = viewHolder.getView(R.id.iv_06); // 单图封面
        ImageView iv_03 = viewHolder.getView(R.id.iv_03); // 小图封面1
        ImageView iv_04 = viewHolder.getView(R.id.iv_04); // 小图封面2
        ImageView iv_05 = viewHolder.getView(R.id.iv_05); // 小图封面3

        TextView tv_biaoti1 = viewHolder.getView(R.id.tv_title1);// 专题标题
        TextView tv_biaoti2 = viewHolder.getView(R.id.tv_title2);// 大图标题
        TextView tv_biaoti3 = viewHolder.getView(R.id.tv_title3);// 小图标题
        TextView tv_biaoti4 = viewHolder.getView(R.id.tv_title4);// 单图标题

        RelativeLayout rl_item = viewHolder.getView(R.id.rl_item); // 来源查看数item
        TextView tv_source = viewHolder.getView(R.id.tv_source);             // 来源
        TextView tv_source2 = viewHolder.getView(R.id.tv_source2);             // 来源
        TextView tv_readingCount = viewHolder.getView(R.id.tv_readingCount); // 阅读数
        TextView tv_readingCount2 = viewHolder.getView(R.id.tv_readingCount2); // 阅读数

        TextView tv_comment_count = viewHolder.getView(R.id.tv_comment_count); // 评论数
        TextView tv_thumbup_count = viewHolder.getView(R.id.tv_thumbup_count); // 点赞数

        //---------视频------------
        JZVideoPlayerStandard videoplayer = viewHolder.getView(R.id.videoplayer_item);
        TextView tv_video_title = viewHolder.getView(R.id.tv_video_title);
        TextView tv_video_source = viewHolder.getView(R.id.tv_video_source);
        ImageView tv_video_collection = viewHolder.getView(R.id.tv_video_collection);
        ImageView iv_video_comment = viewHolder.getView(R.id.iv_video_comment);
        ImageView iv_video_thumbup = viewHolder.getView(R.id.iv_video_thumbup);
        ImageView iv_video_share = viewHolder.getView(R.id.iv_video_share);

        if (communityContent.getType() == 1) { // type 1专题 2大图 3小图4视频5 单图
            ll_video.setVisibility(GONE);
            ll_imagetext.setVisibility(VISIBLE);
            rl_type01.setVisibility(VISIBLE);
            rl_type02.setVisibility(GONE);
            rl_type03.setVisibility(GONE);
            rl_type04.setVisibility(GONE);
            rl_item.setVisibility(VISIBLE);

            tv_biaoti1.setText(TextUtil.setTest(communityContent.getTitle()).trim());
            tv_source.setText("来源：" + TextUtil.setTest(communityContent.getSource()));
            tv_readingCount.setText("" + communityContent.getReadingCount()); // 阅读数量
            tv_comment_count.setText("" + communityContent.getCommentsCount()); // 评论数量
            tv_thumbup_count.setText("" + communityContent.getThumbUpCount()); // 点赞数量

            if (!TextUtils.isEmpty(communityContent.getPic())) {
                Glide.with(APP.getInstance()).load(Config.BASE_PIC_URL + communityContent.getPic()).error(R.drawable.ic_information_default_picture_big).into(iv_01);
            } else {
                iv_01.setImageResource(R.drawable.ic_information_default_picture_big);
            }


        } else if (communityContent.getType() == 2) { // type 1专题 2大图 3小图4视频
            ll_video.setVisibility(GONE);
            ll_imagetext.setVisibility(VISIBLE);
            rl_type01.setVisibility(GONE);
            rl_type02.setVisibility(VISIBLE);
            rl_type03.setVisibility(GONE);
            rl_type04.setVisibility(GONE);
            rl_item.setVisibility(GONE);

            tv_biaoti2.setText(TextUtil.setTest(communityContent.getTitle()).trim());
            tv_source2.setText("来源：" + TextUtil.setTest(communityContent.getSource()));
            tv_readingCount2.setText("" + communityContent.getReadingCount());// 阅读数量
            tv_comment_count.setText("" + communityContent.getCommentsCount()); // 评论数量
            tv_thumbup_count.setText("" + communityContent.getThumbUpCount()); // 点赞数量

            if (!TextUtils.isEmpty(communityContent.getPic())) {
                Glide.with(APP.getInstance()).load(Config.BASE_PIC_URL + communityContent.getPic()).error(R.drawable.ic_information_default_picture).into(iv_02);
            } else {
                iv_02.setImageResource(R.drawable.ic_information_default_picture);
            }

        } else if (communityContent.getType() == 3) { // type 1专题 2大图 3小图4视频5 单图
            ll_video.setVisibility(GONE);
            ll_imagetext.setVisibility(VISIBLE);
            rl_type01.setVisibility(GONE);
            rl_type02.setVisibility(GONE);
            rl_type03.setVisibility(VISIBLE);
            rl_type04.setVisibility(GONE);
            rl_item.setVisibility(VISIBLE);
            tv_biaoti3.setText(TextUtil.setTest(communityContent.getTitle().trim()));
            tv_source.setText("来源：" + TextUtil.setTest(communityContent.getSource()));
            tv_readingCount.setText("" + communityContent.getReadingCount());// 阅读数量
            tv_comment_count.setText("" + communityContent.getCommentsCount()); // 评论数量
            tv_thumbup_count.setText("" + communityContent.getThumbUpCount()); // 点赞数量

            String pisList[] = communityContent.getPicList();
            if (pisList!=null &&  pisList.length > 2) {
                Glide.with(APP.getInstance()).load(Config.BASE_PIC_URL + pisList[0]).error(R.drawable.ic_information_default_picture).into(iv_03);
                Glide.with(APP.getInstance()).load(Config.BASE_PIC_URL + pisList[1]).error(R.drawable.ic_information_default_picture).into(iv_04);
                Glide.with(APP.getInstance()).load(Config.BASE_PIC_URL + pisList[2]).error(R.drawable.ic_information_default_picture).into(iv_05);
            } else {
                iv_03.setImageResource(R.drawable.ic_information_default_picture);
                iv_04.setImageResource(R.drawable.ic_information_default_picture);
                iv_05.setImageResource(R.drawable.ic_information_default_picture);
            }
        }else if (communityContent.getType() == 5) { // type 1专题 2大图 3小图4视频 5 单图
            ll_video.setVisibility(GONE);
            ll_imagetext.setVisibility(VISIBLE);
            rl_type01.setVisibility(GONE);
            rl_type02.setVisibility(GONE);
            rl_type03.setVisibility(GONE);
            rl_type04.setVisibility(VISIBLE);
            rl_item.setVisibility(VISIBLE);
            tv_biaoti4.setText(TextUtil.setTest(communityContent.getTitle()).trim());
            tv_source.setText("来源：" + TextUtil.setTest(communityContent.getSource()));
            tv_readingCount.setText("" + communityContent.getReadingCount()); // 阅读数量
            tv_comment_count.setText("" + communityContent.getCommentsCount()); // 评论数量
            tv_thumbup_count.setText("" + communityContent.getThumbUpCount()); // 点赞数量
            if (!TextUtils.isEmpty(communityContent.getPic())) {
                Glide.with(APP.getInstance()).load(Config.BASE_PIC_URL + communityContent.getPic()).error(R.drawable.ic_information_default_picture_big).into(iv_06);
            } else {
                iv_06.setImageResource(R.drawable.ic_information_default_picture_big);
            }
        } else if (communityContent.getType() == 4) { // type 1专题 2大图 3小图4视频5 单图
            ll_video.setVisibility(VISIBLE);
            ll_imagetext.setVisibility(GONE);
            rl_type01.setVisibility(GONE);
            rl_type02.setVisibility(GONE);
            rl_type03.setVisibility(GONE);
            rl_type04.setVisibility(GONE);
//            tv_video_title.setText(TextUtil.setTest(communityContent.getTitle()));
            tv_video_source.setText("来源：" + TextUtil.setTest(communityContent.getSource()));

            if (!TextUtils.isEmpty(communityContent.getPic())) { // 设置缩略图
                videoplayer.thumbImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                Glide.with(APP.getInstance()).load(Config.BASE_PIC_URL + communityContent.getPic()).error(R.drawable.ic_information_default_picture).into(videoplayer.thumbImageView);
            }
//            String url = Config.BASE_VIDEO_URL + communityContent.getMsg();
            String url = communityContent.getMsg(); // 后面改用西瓜视频的url之后
//            url.replace("<p>", "");
//            url.replace("</p>", "");

            String title = communityContent.getTitle();

            videoplayer.setUp( url, JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL,title);
            videoplayer.positionInList = viewHolder.getPosition();
            videoplayer.setOnClickListener(mListener);

            JZVideoPlayer.FULLSCREEN_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;// 加上这两句，可以使视频横屏放大
            JZVideoPlayer.NORMAL_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;

            if (communityContent.getHaveThumbUp() == 1) {
                iv_video_thumbup.setImageResource(R.drawable.ic_information_list_fabulous_selected);
            } else {
                iv_video_thumbup.setImageResource(R.drawable.ic_information_list_fabulous_default);
            }
            if (communityContent.getHaveCollectionUp() == 1) {
                tv_video_collection.setImageResource(R.drawable.ic_information_list_collection_selected);
            } else {
                tv_video_collection.setImageResource(R.drawable.ic_information_list_collection_default);
            }


            ll_video.setTag(communityContent);
            ll_video.setOnClickListener(mListener);
            tv_video_collection.setTag(communityContent);
            tv_video_collection.setOnClickListener(mListener);
            iv_video_comment.setTag(communityContent);
            iv_video_comment.setOnClickListener(mListener);
            iv_video_thumbup.setTag(communityContent);
            iv_video_thumbup.setOnClickListener(mListener);
            iv_video_share.setTag(communityContent);
            iv_video_share.setOnClickListener(mListener);

         } else if (communityContent.getType() == 0) {


        }


        communityContent.setPosition(viewHolder.getPosition());
        rl_type01.setTag(communityContent);
        rl_type02.setTag(communityContent);
        rl_type03.setTag(communityContent);
        rl_type04.setTag(communityContent);
        rl_type01.setOnClickListener(mListener);
        rl_type02.setOnClickListener(mListener);
        rl_type03.setOnClickListener(mListener);
        rl_type04.setOnClickListener(mListener);


      /*  JCVideoPlayerStandard player = viewHolder.getView(R.id.player_list_video);
        if (player != null) {
            player.release();
        }
        player.setAllControlsVisible(GONE, GONE, VISIBLE, GONE, VISIBLE, VISIBLE, GONE);
        player.setUp(houseUser, JCVideoPlayer.SCREEN_LAYOUT_LIST, "666666");
        player.setPosition(viewHolder.getPosition());
       *//* if (setUp) {
            Glide.with(APP.getInstance()).load("http://a4.att.hudong.com/05/71/01300000057455120185716259013.jpg").into(player.thumbImageView);
        }*//*
        ImageView iv = viewHolder.getView(R.id.imv_header_pic);

*/


    }


}
