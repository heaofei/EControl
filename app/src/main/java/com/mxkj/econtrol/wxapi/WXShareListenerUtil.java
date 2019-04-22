package com.mxkj.econtrol.wxapi;

import android.content.Context;
import android.widget.Toast;

import com.mxkj.econtrol.utils.AbToastUtil;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

/**
 * Created by ${  chenjun  } on 2018/8/9.
 */

public class WXShareListenerUtil {

    private Context contexts;
    public WXShareListenerUtil(Context context) {
        this.contexts = context;
    }

    public UMShareListener shareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }
        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            AbToastUtil.showToast(contexts,"分享成功!");
        }
        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            AbToastUtil.showToast(contexts,"分享失败!");
        }
        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            AbToastUtil.showToast(contexts,"取消分享!");
        }
    };
}
