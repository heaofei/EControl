package com.mxkj.econtrol.ui.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mxkj.econtrol.R;
import com.mxkj.econtrol.base.BaseActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;

/**
 * 二维码扫描
 */
public class QRCodeActivity extends BaseActivity {

    // 初始化title
    private TextView tv_title_left;
    private ImageView iv_qrcode;
    private TextView tv_building_name;
    private TextView tv_house_name;

    private String houseId;
    private String housingEstate;
    private String building;
    private String houseNo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_qrcode);
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initView() {
        tv_title_left = findView(R.id.tv_title_left);
        iv_qrcode = findView(R.id.iv_qrcode);
        tv_building_name = findView(R.id.tv_building_name);
        tv_house_name = findView(R.id.tv_house_name);

        houseId = getIntent().getStringExtra("houseId");
        housingEstate = getIntent().getStringExtra("housingEstate");
        building = getIntent().getStringExtra("building");
        houseNo = getIntent().getStringExtra("houseNo");

        if (!TextUtils.isEmpty(houseId)) {
            String houseName = housingEstate + "-" + building + "-" + houseNo;
            String content = "type=001#" + houseId + "#" + houseName;
            createQrCode(content);

            tv_building_name.setText(housingEstate);
            tv_house_name.setText(building + "-" + houseNo);
        } else {
            showToast("房子ID错误!");
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        tv_title_left.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_title_left:
                finish();
                break;
        }
    }


    // 生成二维码
    private void createQrCode(final String content) {
        try {
            Bitmap mBitmap = CodeUtils.createImage(content, 400, 400, null);
            iv_qrcode.setImageBitmap(mBitmap);
        } catch (Exception e) {
            Toast.makeText(QRCodeActivity.this, "二维码生成失败", Toast.LENGTH_SHORT).show();
        }

    }

}
