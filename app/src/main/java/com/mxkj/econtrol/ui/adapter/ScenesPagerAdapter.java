package com.mxkj.econtrol.ui.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.mxkj.econtrol.R;
import com.mxkj.econtrol.app.Config;
import com.mxkj.econtrol.bean.response.Room;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * Created by liangshan on 2017/4/15.
 *
 * @Description：
 */

public class ScenesPagerAdapter extends PagerAdapter {

    //场景集合
    private List<Room> mScenes;
    protected Context mContext;
    private View.OnClickListener mListener;

    public ScenesPagerAdapter(Context mContext, List<Room> mScenes, View.OnClickListener listener) {
        this.mScenes = mScenes;
        this.mContext = mContext;
        this.mListener = listener;
    }

    @Override
    public int getCount() {
        return mScenes == null ? 0 : mScenes.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.layout_scene_head_item, null);
        AutoUtils.auto(view);
        ImageView scenePic = (ImageView) view.findViewById(R.id.imv_scene_pic);
        ImageView imageView = (ImageView) view.findViewById(R.id.imv_modify_pic);
        Room scene = mScenes.get(position);
        Glide.with(mContext).load(Config.BASE_PIC_URL + scene.getScencePic()).into(scenePic);
        imageView.setTag(scene);
        imageView.setOnClickListener(mListener);
        container.addView(view);
        return view;
    }
}
