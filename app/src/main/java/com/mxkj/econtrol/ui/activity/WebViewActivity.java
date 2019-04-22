package com.mxkj.econtrol.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mxkj.econtrol.R;
import com.mxkj.econtrol.app.APP;
import com.mxkj.econtrol.app.Config;
import com.mxkj.econtrol.app.Msg;
import com.mxkj.econtrol.base.BaseActivity;
import com.mxkj.econtrol.bean.EventBusUIMessage;
import com.mxkj.econtrol.view.activity.LoginActivity;
import com.mxkj.econtrol.view.activity.MainActivity;
import com.mxkj.econtrol.view.activity.PublicCommunityActivity;
import com.mxkj.econtrol.widget.LoadingWebView;

import org.greenrobot.eventbus.EventBus;


/**
 * @author liaoxy
 * @Description: 通用的网页页面
 * @date 2015-9-9 下午8:15:02
 */
public class WebViewActivity extends BaseActivity {

    private LoadingWebView webContent;
    private TextView tvTitle;
    private ImageView mImvUserPic;
    private String url = "", title = "";
    private TextView tvLeft, tvMidle, tvRight;
    private ImageView imvLeft, imvMidle, imvRight;
    private int mType = 0;//0代表来至智享社区，1代表来至控制页面
    private int mPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        setContentView(R.layout.activity_web_view);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        webContent = findView(R.id.webContent);
        tvTitle = findView(R.id.tv_title);
        mImvUserPic = findView(R.id.user_pic);
        tvLeft = findView(R.id.tv_bottom_left);
        tvMidle = findView(R.id.tv_bottom_midle);
        tvRight = findView(R.id.tv_bottom_right);
        imvRight = findView(R.id.imv__bottom_right);
        imvLeft = findView(R.id.imv__bottom_left);
        imvMidle = findView(R.id.imv__bottom_midle);
    }

    @Override
    protected void initListener() {
        // TODO Auto-generated method stub
        mImvUserPic.setOnClickListener(this);
        findView(R.id.ll_bottom_left).setOnClickListener(this);
        findView(R.id.ll_bottom_right).setOnClickListener(this);
        findView(R.id.ll_bottom_midle).setOnClickListener(this);

    }

    @Override
    protected void initData() {
        // 覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        webContent.setWebViewClient(webClient);
        webContent.addProgressBar();

        title = getIntent().getStringExtra("title");
        url = getIntent().getStringExtra("url");

        tvTitle.setText(title);
        webContent.loadUrl(url);

        if (APP.user != null) {
            Glide.with(this).load(Config.BASE_PIC_URL + APP.user.getHeadPicture())
                    .error(R.drawable.ic_no_head)
                    .into(mImvUserPic);
        }
        mType = getIntent().getIntExtra("type", 0);
        mPosition = getIntent().getIntExtra("position", 0);
        if (mType == 0) {
            tvLeft.setText("壁纸");
            imvLeft.setImageResource(R.drawable.wallpaper);
            tvMidle.setText("智享社区");
            imvMidle.setImageResource(R.drawable.social);
            tvRight.setText("链家");
            imvRight.setImageResource(R.drawable.homelink);
        } else if (mType == 1) {
            tvLeft.setText("本地链接");
            tvMidle.setText(getIntent().getStringExtra("page"));
            tvRight.setText("小区物业");
            imvLeft.setImageResource(R.drawable.location);
            imvMidle.setImageResource(R.drawable.toolbar_icon_homepage);
            imvRight.setImageResource(R.drawable.service);
        }
        switch (mPosition) {
            case 0:
                tvLeft.setTextColor(0xfff72971);
                if (mType == 0) {
                    imvLeft.setImageResource(R.drawable.wallpaper_active);
                } else {
                    imvLeft.setImageResource(R.drawable.location_active);
                }
                break;
            case 1:
                tvMidle.setTextColor(0xfff72971);
                if (mType == 0) {
                    imvMidle.setImageResource(R.drawable.social_active);
                } else {
                    imvMidle.setImageResource(R.drawable.toolbar_icon_homepage_active);
                }

                break;
            case 2:
                tvRight.setTextColor(0xfff72971);
                if (mType == 0) {
                    imvRight.setImageResource(R.drawable.homelink_active);
                } else {
                    imvRight.setImageResource(R.drawable.service_active);
                }

                break;
        }
    }

    // webview客户端对象
    private WebViewClient webClient = new WebViewClient() {
        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);
        }

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // 返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
            view.loadUrl(url);
            return true;
        }
    };

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.user_pic:
                if (APP.user != null) {
                    isClose = false;
                    EventBus.getDefault().post(new EventBusUIMessage(Msg.CLEAR_ACITVITY));
                    startToActivity(MainActivity.class);
                    finish();
                } else {
                    startToActivity(LoginActivity.class);
                }
                break;
            case R.id.ll_bottom_left:
                if (mPosition == 0) {
                    return;
                }
                if (mType == 0) {
                    Intent intent01 = new Intent(this, WebViewActivity.class);
                    intent01.putExtra("type", 0);
                    intent01.putExtra("position", 0);
                    intent01.putExtra("url", "https://image.baidu.com/search/wiseala?tn=wiseala&ie=utf8&fmpage=search&dulisearch=1&pos=tagclick&word=家居手机壁纸%20竖屏&oriquery=家居手机壁纸 ");
                    intent01.putExtra("title", "壁纸");
                    startToActivity(intent01);
                    finish();
                } else if (mType == 1) {
                    Intent intent = new Intent(this, WebViewActivity.class);
                    intent.putExtra("url", getIntent().getStringExtra("otherUrl"));
                    intent.putExtra("type", 1);
                    intent.putExtra("position", 0);
                    intent.putExtra("otherUrl", getIntent().getStringExtra("url"));
                    intent.putExtra("title", "服务商");
                    intent.putExtra("page", getIntent().getStringExtra("page"));
                    startToActivity(intent);
                    finish();
                }

                break;
            case R.id.ll_bottom_midle:
                if (mPosition == 1) {
                    return;
                }
                if (mType == 0) {
                    startToActivity(PublicCommunityActivity.class);
                    finish();
                } else if (mType == 1) {
                    //关闭，回到控制页面
                    finish();
                }


                break;
            case R.id.ll_bottom_right:
                if (mPosition == 2) {
                    return;
                }
                if (mType == 0) {
                    Intent intent = new Intent(this, WebViewActivity.class);
                    intent.putExtra("url", "http://gz.lianjia.com/");
                    intent.putExtra("type", 0);
                    intent.putExtra("position", 2);
                    intent.putExtra("title", "链家");
                    startToActivity(intent);
                    finish();
                } else if (mType == 1) {
                    Intent intent = new Intent(this, WebViewActivity.class);
                    intent.putExtra("url", getIntent().getStringExtra("otherUrl"));
                    intent.putExtra("type", 1);
                    intent.putExtra("position", 0);
                    intent.putExtra("otherUrl", getIntent().getStringExtra("url"));
                    intent.putExtra("position", 2);
                    intent.putExtra("title", "物业管理");
                    intent.putExtra("page", getIntent().getStringExtra("page"));
                    startToActivity(intent);
                    finish();
                }
                break;
        }
    }

    //改写物理按键——返回的逻辑
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webContent.canGoBack()) {
                webContent.goBack();//返回上一页面
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
