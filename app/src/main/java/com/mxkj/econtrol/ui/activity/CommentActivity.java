package com.mxkj.econtrol.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.mxkj.econtrol.R;
import com.mxkj.econtrol.app.APP;
import com.mxkj.econtrol.base.BaseActivity;
import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.bean.request.ReqPublicCommunityComment;
import com.mxkj.econtrol.net.APIService;
import com.mxkj.econtrol.net.APISubcriber;
import com.mxkj.econtrol.net.APITransFormer;
import com.mxkj.econtrol.net.RetrofitUtil;

/**
 * 评论（旧）
 *
 */
public class CommentActivity extends BaseActivity {
    private EditText mEditComment;
    private TextView mTvSend;
    private TextView mTvCancel;
    private String communityId;
    private String replyUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_comment_activity);
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initView() {
        mEditComment = findView(R.id.edit_comment);
        mTvCancel = findView(R.id.tv_cancel);
        mTvSend = findView(R.id.tv_send);
    }

    @Override
    protected void initData() {
        communityId = getIntent().getStringExtra("communityId");
        replyUserId = getIntent().getStringExtra("replyUserId");
    }

    @Override
    protected void initListener() {
        mTvCancel.setOnClickListener(this);
        mTvSend.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_cancel:
                finish();
                break;
            case R.id.tv_send:
                addComment();
                break;
        }
    }

    private void addComment() {
        String comment = mEditComment.getText().toString();
        if (TextUtils.isEmpty(comment)) {
            showToast("评论内容不能为空");
            return;
        }
        ReqPublicCommunityComment reqPublicCommunityComment = new ReqPublicCommunityComment();
        reqPublicCommunityComment.setMsg(comment);
        reqPublicCommunityComment.setCommunityId(communityId);
        reqPublicCommunityComment.setUserId(APP.user.getUserId());
        if (replyUserId != null) {
            reqPublicCommunityComment.setReplyUserId(replyUserId);
        }
        RetrofitUtil.getInstance().getmApiService()
                .API(reqPublicCommunityComment.toJsonStr(), APIService.PUBLIC_COMMUNITY_COMMENTS)
                .compose(new APITransFormer<BaseResponse>())
                .subscribe(new APISubcriber<BaseResponse>() {
                    @Override
                    public void onFail(BaseResponse baseResponse,String msg) {
                        showToast(msg);
                    }

                    @Override
                    public void onSuccess(BaseResponse baseResponse) {
                        showToast("评论成功！");
                        finish();
                    }
                });
    }
}
