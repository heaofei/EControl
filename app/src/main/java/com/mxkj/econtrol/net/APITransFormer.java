package com.mxkj.econtrol.net;

import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.net.APIException;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by liangshan on 2017/3/28.
 *
 * @Description: 统一对请求结果进行处理
 */

public class APITransFormer<T> implements Observable.Transformer<BaseResponse, T> {
    @Override
    public Observable<T> call(Observable<BaseResponse> observable) {
        return observable
                .map(new Func1<BaseResponse, T>() {
                    @Override
                    public T call(BaseResponse baseResponse) {
                        //0000代表请求成功，其他值为失败
                      /*  if (!baseResponse.getState().equals("0000")) {
                            if (baseResponse.getState().equals("0005")) {
                                //登录已失效
                                throw new APIException(baseResponse,"0005");
                            } else {
                                throw new APIException(baseResponse,baseResponse.getMsg());
                            }
                        }*/
                        // 变成所有的异常以及信息都返回
                        if (baseResponse.getState().equals("0005")) {
                            //登录已失效
                            throw new APIException(baseResponse,"0005");
                        }
                        return (T) baseResponse;

                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io());
    }
}
