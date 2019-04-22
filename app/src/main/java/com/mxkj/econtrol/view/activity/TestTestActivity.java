package com.mxkj.econtrol.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.mxkj.econtrol.R;
import com.mxkj.econtrol.base.BaseActivity;
import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.net.APIService;
import com.mxkj.econtrol.net.APISubcriber;
import com.mxkj.econtrol.net.APITransFormer;
import com.mxkj.econtrol.net.RetrofitUtil;
import com.mxkj.econtrol.utils.gateway.GatewayService;
import com.mxkj.econtrol.utils.gateway.IAddGatewayCallback;

public class TestTestActivity extends BaseActivity {

    private Button button;
    private GatewayService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_test_test);
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initView() {
        button = findViewById(R.id.bind_button);
        button.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        service = new GatewayService();
    }

    @Override
    protected void initListener() {




    }

    @Override
    public void onClick(View v) {

    }

    private void Binding(String uuid) {

        /*RetrofitUtil.getInstance().getmApiService()
                .API(reqPublicCommunityComment.toJsonStr(), APIService.PUBLIC_COMMUNITY_COMMENTS)
                .compose(new APITransFormer<BaseResponse>())
                .subscribe(new APISubcriber<BaseResponse>() {
                    @Override
                    public void onFail(BaseResponse baseResponse, String msg) {
                        showToast(msg);
                    }

                    @Override
                    public void onSuccess(BaseResponse baseResponse) {
                        showToast("评论成功！");
                        finish();
                    }
                });*/

    }


}
