package com.mxkj.econtrol.ui.activity;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mxkj.econtrol.R;
import com.mxkj.econtrol.app.APP;
import com.mxkj.econtrol.base.BaseActivity;

/***
 * 身份证，拍照上传
 */
public class TakePhotoActivity extends BaseActivity {

    // 初始化title
    private TextView tv_title_left;
    private TextView tv_title_content;
    private ImageView iv_more;
    private TextView tv_title_right;

    private LinearLayout ll_propertyFront;
    private ImageView iv_propertyFront;
    private ImageView iv_propertyFront_bg;
    private LinearLayout ll_idCardFront;
    private TextView tv_tips;
    private ImageView iv_carema_just;
    private ImageView iv_just;
    private TextView tv_tjust;
    private LinearLayout ll_idCardBack;
    private RelativeLayout rl_item;
    private ImageView iv_carema_reverse;
    private ImageView iv_reverse;
    private TextView tv_property;
    private LinearLayout ll_property;


    private String justUrl;
    private String reverseUrl;
    private String type;

    private String idCardFront;//身份证正面
    private String idCardBack;//身份证背面
    private String propertyFront;//物业证明正面


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_take_photo);
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initView() {
        tv_title_left = findView(R.id.tv_title_left);
        tv_title_content = findView(R.id.tv_title_content);
        iv_more = findView(R.id.iv_more);
        tv_title_right = findView(R.id.tv_title_right);
        tv_title_right.setText("完成");


        ll_propertyFront = (LinearLayout) findViewById(R.id.ll_propertyFront);
        iv_propertyFront = (ImageView) findViewById(R.id.iv_propertyFront);
        iv_propertyFront_bg = (ImageView) findViewById(R.id.iv_propertyFront_bg);
        ll_idCardFront = (LinearLayout) findViewById(R.id.ll_idCardFront);
        tv_tips = (TextView) findViewById(R.id.tv_tips);
        iv_carema_just = (ImageView) findViewById(R.id.iv_carema_just);
        iv_just = (ImageView) findViewById(R.id.iv_just);
        tv_tjust = (TextView) findViewById(R.id.tv_tjust);
        ll_idCardBack = (LinearLayout) findViewById(R.id.ll_idCardBack);
        rl_item = (RelativeLayout) findViewById(R.id.rl_item);
        iv_carema_reverse = (ImageView) findViewById(R.id.iv_carema_reverse);
        iv_reverse = (ImageView) findViewById(R.id.iv_reverse);
        tv_property = (TextView) findViewById(R.id.tv_property);
        ll_property = (LinearLayout) findViewById(R.id.ll_property);


        type = getIntent().getStringExtra("type");
        if (type != null && type.equals("PROPERTY")) { // 物业证明
            ll_propertyFront.setVisibility(View.VISIBLE);
            ll_property.setVisibility(View.VISIBLE);
            ll_idCardFront.setVisibility(View.GONE);
            ll_idCardBack.setVisibility(View.INVISIBLE);
            tv_title_content.setText("添加物业材料");
            propertyFront = getIntent().getStringExtra("propertyFront");
            if (!TextUtils.isEmpty(propertyFront)) {
                Glide.with(this).load(propertyFront).into(iv_propertyFront_bg);
            }
        } else {                                        // 身份证
            ll_propertyFront.setVisibility(View.GONE);
            ll_property.setVisibility(View.INVISIBLE);
            ll_idCardFront.setVisibility(View.VISIBLE);
            ll_idCardBack.setVisibility(View.VISIBLE);
            tv_title_content.setText("添加身份证");
            idCardFront = getIntent().getStringExtra("idCardFront");
            idCardBack = getIntent().getStringExtra("idCardBack");

            if (!TextUtils.isEmpty(idCardFront) && !TextUtils.isEmpty(idCardBack)) {
                Glide.with(this).load(idCardFront).into(iv_carema_reverse);
                Glide.with(this).load(idCardBack).into(iv_carema_just);
            }

        }


    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        tv_title_left.setOnClickListener(this);
        tv_title_right.setOnClickListener(this);
        iv_just.setOnClickListener(this);
        iv_reverse.setOnClickListener(this);
        iv_reverse.setOnClickListener(this);
        iv_propertyFront.setOnClickListener(this);
        tv_property.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_title_left:
                finish();
                break;
            case R.id.tv_title_right:

                if (type != null && type.equals("PROPERTY")) { // 物业证明
                    if (TextUtils.isEmpty(propertyFront)) {
                        showToast("请上传相关物业材料");
                    } else {
                        Intent intent01 = new Intent();
                        intent01.putExtra("propertyFront", propertyFront);
                        setResult(RESULT_OK, intent01);
                        finish();
                    }
                } else {
                    if (TextUtils.isEmpty(idCardFront) || TextUtils.isEmpty(idCardBack)) {
                        showToast("请上传身份证");
                    } else {
                        Intent intent01 = new Intent();
                        intent01.putExtra("idCardFront", idCardFront);
                        intent01.putExtra("idCardBack", idCardBack);
                        setResult(RESULT_OK, intent01);
                        finish();
                    }
                }
                break;
            case R.id.tv_property:               // 物业证明示例

                Intent picDetail = new Intent(this, BrowsePicActivity.class);
                picDetail.putExtra("drawableUrl", R.drawable.ic_property_ba);
                picDetail.putExtra("picType", 1);
                startActivity(picDetail);
                break;
            case R.id.iv_just:               // 身份证正面
                Intent intent = new Intent(this, SelectPictureActivity.class);
                intent.putExtra("isTakePhoto", true);
                intent.putExtra("requestCode", 1);
                startActivityForResult(intent, 1);
                break;
            case R.id.iv_reverse:            // 身份证反面
                Intent intent02 = new Intent(this, SelectPictureActivity.class);
                intent02.putExtra("isTakePhoto", true);
                intent02.putExtra("requestCode", 2);
                startActivityForResult(intent02, 2);
                break;
            case R.id.iv_propertyFront:           // 业证明正面
                Intent intent03 = new Intent(this, SelectPictureActivity.class);
                intent03.putExtra("isTakePhoto", true);
                intent03.putExtra("requestCode", 3);
                startActivityForResult(intent03, 3);
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            justUrl = data.getStringExtra("patch");
            if (requestCode == 01) { // 请求码
                idCardFront = justUrl;
                Glide.with(this).load(justUrl).into(iv_carema_just);
            } else if (requestCode == 02) {
                idCardBack = justUrl;
                Glide.with(this).load(justUrl).into(iv_carema_reverse);
            } else if (requestCode == 03) {
                propertyFront = justUrl;
                Glide.with(this).load(justUrl).into(iv_propertyFront_bg);
            }
        }

    }
}
