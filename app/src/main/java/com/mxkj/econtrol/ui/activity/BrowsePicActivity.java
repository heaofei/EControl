package com.mxkj.econtrol.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.mxkj.econtrol.R;
import com.mxkj.econtrol.app.Config;
import com.mxkj.econtrol.widget.ZoomImageView;

public class BrowsePicActivity extends Activity {

    private int PICTURE_LOOK = 0;
    private int PICTURE_WUYEZHENGMING = 1;
    private int PICTURE_CAMERA_LOOK = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_pic);

    }

    @Override
    protected void onResume() {
        super.onResume();
        int picType = getIntent().getIntExtra("picType",0);
        ZoomImageView imageView = (ZoomImageView) findViewById(R.id.imv_pic);
        String picUrl = getIntent().getStringExtra("picUrl");

        if (picType == PICTURE_LOOK) {  // 普通图片查看
            if (null != picUrl && !TextUtils.isEmpty(picUrl)) {
                Glide.with(this).load(Config.BASE_PIC_URL + picUrl).error(R.drawable.ic_no_pic).into(imageView);
            } else {
                Glide.with(this).load(R.drawable.ic_no_pic).into(imageView);
            }
        }else if (picType == PICTURE_WUYEZHENGMING) { // 物业证明图片
            int drawableUrl = getIntent().getIntExtra("drawableUrl",0);// 获取 物业证明示例
            if (drawableUrl!=0){// 获取 物业证明示例
                Glide.with(this).load(drawableUrl).error(R.drawable.ic_no_pic).into(imageView);
            }
        }else if (picType == PICTURE_CAMERA_LOOK) {    // 摄像头图片查看，（本地图片查看）
            if (null != picUrl && !TextUtils.isEmpty(picUrl)) {
                Glide.with(this).load( picUrl).error(R.drawable.ic_no_pic).into(imageView);
            } else {
                Glide.with(this).load(R.drawable.ic_no_pic).into(imageView);
            }
        }else {
            Glide.with(this).load(R.drawable.ic_no_pic).into(imageView);
        }



        imageView.setSingleClickLinstenter(new ZoomImageView.OnSingleClickLinstenter() {
            @Override
            public void onSingleClick() {
                finish();
            }
        });
    }
}
