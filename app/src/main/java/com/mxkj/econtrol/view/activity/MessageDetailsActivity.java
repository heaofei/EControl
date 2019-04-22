package com.mxkj.econtrol.view.activity;

import android.graphics.drawable.Drawable;
import android.icu.util.RangeValueIterator;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.DocumentsContract;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mxkj.econtrol.R;
import com.mxkj.econtrol.app.Config;
import com.mxkj.econtrol.base.BaseActivity;
import com.mxkj.econtrol.bean.request.ReqGetSysAnnouncementDetail;
import com.mxkj.econtrol.bean.response.ResGetSysAnnouncementDetail;
import com.mxkj.econtrol.contract.GetSysAnnouncementDetailContract;
import com.mxkj.econtrol.contract.MessageListContract;
import com.mxkj.econtrol.model.GetSysAnnouncementDetailModel;
import com.mxkj.econtrol.presenter.GetSysAnnouncementDetailPresenter;


import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import io.netty.util.Constant;

/**
 * 消息详情
 */
public class MessageDetailsActivity extends BaseActivity implements GetSysAnnouncementDetailContract.View {

    // 初始化title
    private TextView tv_title_left;
    private TextView tv_title_content;
    private TextView tv_title_right;


    private String type;
    private String id;
    private TextView tv_content;
    private WebView webView;
    private GetSysAnnouncementDetailContract.Presenter mPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_message_details);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        tv_title_left = (TextView) findViewById(R.id.tv_title_left);
        tv_title_content = (TextView) findViewById(R.id.tv_title_content);
        tv_title_right = (TextView) findViewById(R.id.tv_title_right);
        tv_title_left.setCompoundDrawables(null,null,null,null);
        tv_title_left.setText("关闭");

        tv_content = (TextView) findViewById(R.id.tv_content);
        webView = (WebView) findViewById(R.id.webView);

       /* Drawable drawable= getResources().getDrawable(R.drawable.center_back);
        /// 这一步必须要做,否则不会显示.
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        tv_title_left.setCompoundDrawables(drawable,null,null,null);
        tv_title_left.setCompoundDrawablePadding(20) ;*/




        type = getIntent().getStringExtra("type");
        if ( type .equals("NOTICE")){
            tv_title_content.setText("系统通知");
        }else {
            tv_title_content.setText("消息中心");
        }

        id = getIntent().getStringExtra("id");

        mPresenter = new GetSysAnnouncementDetailPresenter(this,new GetSysAnnouncementDetailModel());
        mPresenter.getSysAnnouncementDetail(new ReqGetSysAnnouncementDetail(id));
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
        switch (v.getId()){
            case R.id.tv_title_left:
                finish();
                break;
        }
    }
    @Override
    public void getSysAnnouncementDetailSuccess(ResGetSysAnnouncementDetail resGetSysAnnouncementDetail) {

    String content;

         content = resGetSysAnnouncementDetail.getContent();

/*
        CharSequence charSequence= Html.fromHtml(content);
        tv_content.setText(charSequence);
        //该语句在设置后必加，不然没有任何效果
        tv_content.setMovementMethod(LinkMovementMethod.getInstance());
*/


      /*  Html.ImageGetter imgGetter = new Html.ImageGetter() {
            public Drawable getDrawable(String source) {
                Drawable drawable = null;
                URL url;
                try {
                    url = new URL(source);
                    Drawable.createFromStream(url.openStream(), "");
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                        drawable.getIntrinsicHeight());

                return drawable;
            }
        };
        CharSequence charSequence = Html.fromHtml(content, imgGetter, null);
        tv_content.setText(charSequence );
        tv_content.setMovementMethod(LinkMovementMethod.getInstance());*/






        //支持javascript
        webView.getSettings().setJavaScriptEnabled(true);
        // 设置可以支持缩放
        webView.getSettings().setSupportZoom(false);
        // 设置出现缩放工具
        webView.getSettings().setBuiltInZoomControls(true);
        //自适应屏幕
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        webView.getSettings().setDisplayZoomControls(false);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY); //取消滚动条白边效果
        webView.getSettings().setDefaultTextEncodingName("UTF-8") ;
        webView.getSettings().setBlockNetworkImage(false);

//        webView.loadData(content,"text/html", "UTF-8");
        webView.loadDataWithBaseURL(null, content, "text/html", "UTF-8", null);





    }

    @Override
    public void getSysAnnouncementDetailFail(String msg) {

    }

    @Override
    public void setPresenter(MessageListContract.Presenter presenter) {

    }






}
