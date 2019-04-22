package com.mxkj.econtrol.net;

import com.mxkj.econtrol.R;
import com.mxkj.econtrol.app.Msg;
import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.bean.EventBusUIMessage;
import com.mxkj.econtrol.utils.LogUtil;
import com.mxkj.econtrol.utils.ResourceUtil;

import org.greenrobot.eventbus.EventBus;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import rx.Subscriber;

/**
 * Created by liangshan on 2017/3/28.
 *
 * @Description:
 */

public abstract class APISubcriber<T> extends Subscriber<T> {

    public abstract void onFail(T t ,String msg);

    public abstract void onSuccess(T t);

    private boolean isShowLoding = true;


    @Override
    public void onCompleted() {
        EventBus.getDefault().post(new EventBusUIMessage(Msg.DISMISS_LOADING));
    }

    @Override
    public void onError(Throwable e) {
        EventBus.getDefault().post(new EventBusUIMessage(Msg.DISMISS_LOADING));
        e.printStackTrace();
        LogUtil.e("请求失败：" + e.toString());
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setMsg(e.getMessage());

        if (e instanceof SocketTimeoutException) {
            onFail(null,ResourceUtil.getString(R.string.network_connect_timeout));
        } else if (e instanceof ConnectException) {
            onFail(null,ResourceUtil.getString(R.string.network_connect_fail));
        } else if (e instanceof APIException && e.getMessage().equals("0005")) {
            EventBus.getDefault().post(new EventBusUIMessage(Msg.LOGON_FAILURE));
        } else {
            onFail(null,e.getMessage());
        }
    }

    @Override
    public void onNext(T t) {
        BaseResponse baseResponse = (BaseResponse) t;
        if (baseResponse.getState().equals("0000")) {    //APITransFormer 将所有的错误号都返回，然后在这里将错误的信息返回到onFail方法里面
            onSuccess(t);
        } else {
            onFail(t,baseResponse.getMsg());
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (isShowLoding) {
            EventBus.getDefault().post(new EventBusUIMessage(Msg.SHOW_LOADING));
        }
    }

    public void setShowLoding(boolean isShowLoding) {
        this.isShowLoding = isShowLoding;
    }
}
