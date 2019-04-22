package com.mxkj.econtrol.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.LubanOptions;
import com.jph.takephoto.model.TImage;
import com.jph.takephoto.model.TResult;
import com.mxkj.econtrol.R;
import com.mxkj.econtrol.utils.LogUtil;

import java.io.File;

/**
 * 拍照、选择照片弹窗
 *
 */

public class SelectPictureActivity extends TakePhotoActivity {
    private CropOptions mCropOptions;
    private View contentView;
    private Button mBtnRestorePic;
    private LinearLayout ll_layout;

    private static final Integer MAXHEIGHT = 128*1024;
    private static final Integer MAXWIDTH = 128*1024;
    private static final Integer MAXSIZE = 256*1024;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contentView = LayoutInflater.from(this).inflate(R.layout.activity_select_picture, null);
        setContentView(contentView);
        mCropOptions = (CropOptions) getIntent().getSerializableExtra("cropOptions");
        mBtnRestorePic = (Button) findViewById(R.id.btn_restore_pic);
        ll_layout = (LinearLayout) findViewById(R.id.ll_layout);
        if (getIntent().getBooleanExtra("isFromSceneActivity", false)) {
            //如果来至场景
            mBtnRestorePic.setVisibility(View.VISIBLE);
            mBtnRestorePic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra("type", 1);
                    setResult(RESULT_OK, intent);
                    finish();

                }
            });
        }
        if (getIntent().getBooleanExtra("isTakePhoto", false)) {
            ll_layout.setVisibility(View.GONE);
            // 来自拍摄身份证页面
            TakePhoto takePhoto = getTakePhoto();
            CompressConfig compressConfig = new CompressConfig.Builder().create();

            File file = new File(Environment.getExternalStorageDirectory(), "/temp/"+System.currentTimeMillis() + ".jpg");
            if (!file.getParentFile().exists())file.getParentFile().mkdirs();
            Uri imageUri = Uri.fromFile(file);

        /*    CompressConfig  config =new CompressConfig.Builder()
                    .setMaxSize(100*1024)
                    .setMaxPixel(1200)
                    .enableReserveRaw(true)
                    .enableQualityCompress(true)
                    .create();*/
            CompressConfig config;

            LubanOptions option=new LubanOptions.Builder()
                    .setMaxHeight(MAXHEIGHT)
                    .setMaxWidth(MAXWIDTH)
                    .setMaxSize(MAXSIZE)
                    .create();
            config=CompressConfig.ofLuban(option);
            config.enableReserveRaw(true);

            takePhoto.onEnableCompress(config, true);
            takePhoto.onPickFromCapture(imageUri);
        }




    }

    @Override
    public void takeCancel() {
        super.takeCancel();
        finish();
    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        TImage image = result.getImage();
        Intent intent = new Intent();
        intent.putExtra("patch", image.getCompressPath());
        LogUtil.i("压缩图片："+image.getCompressPath());
            setResult(RESULT_OK, intent);
        LogUtil.i(image.getOriginalPath());

        finish();


    }

    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
    }

    public void onClick(View view) {
        CompressConfig compressConfig = new CompressConfig.Builder().create();
        contentView.setVisibility(View.GONE);
        switch (view.getId()) {
            case R.id.btnPickBySelect:
                TakePhoto takePhoto = getTakePhoto();
                takePhoto.onEnableCompress(compressConfig, false);
                if (mCropOptions != null) {
                    File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
                    if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
                    takePhoto.onPickFromGalleryWithCrop(Uri.fromFile(file), mCropOptions);
                } else {
                    takePhoto.onPickFromGallery();
                }
                break;
            case R.id.btnPickByTake:
                File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
                if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
                Uri imageUri = Uri.fromFile(file);
                takePhoto = getTakePhoto();
                takePhoto.onEnableCompress(compressConfig, false);
                if (mCropOptions != null) {
                    takePhoto.onPickFromCaptureWithCrop(imageUri, mCropOptions);
                } else {
                    takePhoto.onPickFromCapture(imageUri);
                }
                break;
            default:

        }
    }




}
