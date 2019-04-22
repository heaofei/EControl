package com.mxkj.econtrol.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aigestudio.wheelpicker.WheelPicker;
import com.bumptech.glide.Glide;
import com.mxkj.econtrol.R;
import com.mxkj.econtrol.app.APP;
import com.mxkj.econtrol.base.BaseActivity;
import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.bean.request.ReqPublicCommunity;
import com.mxkj.econtrol.bean.response.ResGetUserEstateList;
import com.mxkj.econtrol.model.PublicCommunityModel;
import com.mxkj.econtrol.net.APISubcriber;
import com.mxkj.econtrol.utils.Bitmap2StringUtil;

import java.util.List;

/**
 * Created by liangshan on 2017/4/22.
 *
 * @Description：
 */

public class SendCommunityActivity extends BaseActivity {

    private TextView mTvCancel;
    private TextView mTvSend;
    private EditText mEditContent;
    private ImageView mImvPic;
    private String mPicStr;
    private RelativeLayout mRlSelectEstate;
    private TextView mTvEstateName;
    private String mEstateId;
    private int picWidth, picHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_send_community);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        mTvCancel = findView(R.id.tv_cancel);
        mTvSend = findView(R.id.tv_send);
        mEditContent = findView(R.id.edit_content);
        mImvPic = findView(R.id.imv_pic);
        mRlSelectEstate = findView(R.id.rl_select_estate);
        mTvEstateName = findView(R.id.tv_estate_name);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        mTvCancel.setOnClickListener(this);
        mTvSend.setOnClickListener(this);
        mImvPic.setOnClickListener(this);
        mRlSelectEstate.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_cancel:
                finish();
                break;
            case R.id.tv_send:
                sendCommunity();
                break;
            case R.id.imv_pic:
                startActivityForResult(new Intent(this, SelectPictureActivity.class), 1);
                break;
            case R.id.rl_select_estate:
                getUserEstateList();
                break;
        }
    }

    public void sendCommunity() {
        String content = mEditContent.getText().toString();
        if (TextUtils.isEmpty(content)) {
            showToast("请输入要发布的内容");
            return;
        }
        ReqPublicCommunity reqPublicCommunity = new ReqPublicCommunity(content, mPicStr, mEstateId, picWidth, picHeight);
        PublicCommunityModel publicCommunityModel = new PublicCommunityModel();
        publicCommunityModel.publicCommunity(reqPublicCommunity)
                .subscribe(new APISubcriber<BaseResponse>() {
                    @Override
                    public void onFail(BaseResponse baseResponse,String msg) {
                        showToast(msg);
                    }

                    @Override
                    public void onSuccess(BaseResponse baseResponse) {
                        showToast("发表成功！");
                        setResult(RESULT_OK);
                        finish();
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            String patch = data.getStringExtra("patch");
            Glide.with(this).load(patch).into(mImvPic);
            mPicStr = Bitmap2StringUtil.bitmapToString(patch);
            Bitmap bitmap = BitmapFactory.decodeFile(patch);
            picHeight = bitmap.getHeight();
            picWidth = bitmap.getWidth();


        }
    }


    public void getUserEstateList() {
        PublicCommunityModel publicCommunityModel = new PublicCommunityModel();
        publicCommunityModel.getUserEstateList()
                .subscribe(new APISubcriber<ResGetUserEstateList>() {
                    @Override
                    public void onFail(ResGetUserEstateList baseResponse,String msg) {
                        showToast(msg);
                    }

                    @Override
                    public void onSuccess(ResGetUserEstateList resGetUserEstateList) {
                        if (resGetUserEstateList.getHousingEstatList().size() > 0) {
                            showSelectState(resGetUserEstateList.getHousingEstatList());
                        } else {
                            showToast("您目前还没有绑定房子");
                        }
                    }
                });
    }

    private void showSelectState(final List<ResGetUserEstateList.HousingEstat> data) {
        final Dialog selectState = new Dialog(this);
        selectState.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = getLayoutInflater().inflate(R.layout.layout_select_estate, null);
        selectState.setContentView(view);
        final WheelPicker estatePicker = (WheelPicker) view.findViewById(R.id.wheel_picker01);
        estatePicker.setData(data);
        TextView mTvSelectStateOk = (TextView) view.findViewById(R.id.tv_ok);
        mTvSelectStateOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectState.dismiss();
                ResGetUserEstateList.HousingEstat housingEstat = data.get(estatePicker.getCurrentItemPosition());
                mTvEstateName.setText(housingEstat.getHousingEstatName());
                mEstateId = housingEstat.getHousingEstatId();

            }
        });
        selectState.show();

    }
}
