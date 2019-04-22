package com.mxkj.econtrol.ui.activity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mxkj.econtrol.R;
import com.mxkj.econtrol.app.Config;
import com.mxkj.econtrol.base.BaseActivity;
import com.mxkj.econtrol.bean.response.ResGetShopProductList;


/**
 * Created by liangshan on 2017/4/26.
 *
 * @Description：
 */

public class ProductDetailActivity extends BaseActivity {
    private ImageView mImvProduct;
    private TextView mTvBook;
    private TextView mTvDesc;
    private RelativeLayout mRlBook;
    private ResGetShopProductList.Page.Product mProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_product_detail);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        mImvProduct = findView(R.id.imv_product_detail_pic);
        mTvBook = findView(R.id.tv_book);
        mTvDesc = findView(R.id.tv_product_des);
        mRlBook = findView(R.id.rl_book);
    }

    @Override
    protected void initData() {
        mProduct = (ResGetShopProductList.Page.Product) getIntent().getSerializableExtra("product");
        Glide.with(this).load(Config.BASE_PIC_URL + mProduct.getBigPic()).into(mImvProduct);
        mTvDesc.setText(mProduct.getDescription());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                palyAnimator(0);
            }
        }, 1000);

    }

    public void palyAnimator(int type) {
        int y1 = 0, y2 = 0;
        if (type == 0) {
            y2 = mRlBook.getHeight();
        } else {
            y2 = -mRlBook.getHeight();
        }
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mRlBook, "translationY", 0, mRlBook.getHeight());
        objectAnimator.setDuration(1000);
        objectAnimator.start();
    }

    @Override
    protected void initListener() {
        mTvBook.setOnClickListener(this);
        mImvProduct.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v == mTvBook) {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            Uri data = Uri.parse("tel:" + mProduct.getTel());
            intent.setData(data);
            startActivity(intent);
        }
        if (v.getId() == R.id.imv_product_detail_pic) {
            palyAnimator(2);
        }
    }
}
