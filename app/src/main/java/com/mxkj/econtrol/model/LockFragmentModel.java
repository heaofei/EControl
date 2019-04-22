package com.mxkj.econtrol.model;

import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.bean.request.ReqGetScenePartDetail;
import com.mxkj.econtrol.bean.request.ReqScenePartEdit;
import com.mxkj.econtrol.bean.response.ResGetScenePartDetail;
import com.mxkj.econtrol.contract.LightSettingContract;
import com.mxkj.econtrol.contract.LockFragmentContract;
import com.mxkj.econtrol.net.APITransFormer;
import com.mxkj.econtrol.net.RetrofitUtil;

import rx.Observable;

/**
 * Created by chan on 2018/1/24.
 *
 * @Description：
 */

public class LockFragmentModel implements LockFragmentContract.Model {


    @Override
    public Observable<ResGetScenePartDetail> getScenePartDetail(ReqGetScenePartDetail request) {
        return RetrofitUtil.getInstance().getmApiService()
                .getScenePartDetail(request.toJsonStr())
                .compose(new APITransFormer<ResGetScenePartDetail>());
    }

}
