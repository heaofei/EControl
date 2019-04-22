package com.mxkj.econtrol.model;

import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.bean.request.ReqGetScenePartDetail;
import com.mxkj.econtrol.bean.request.ReqScenePartEdit;
import com.mxkj.econtrol.bean.response.ResGetScenePartDetail;
import com.mxkj.econtrol.contract.AgFragmentContract;
import com.mxkj.econtrol.contract.LightSettingContract;
import com.mxkj.econtrol.net.APITransFormer;
import com.mxkj.econtrol.net.RetrofitUtil;

import rx.Observable;

/**
 * Created by chan on 2017/11/2.
 *
 * @Description：
 */

public class AgFragmentModel implements AgFragmentContract.Model {


    @Override
    public Observable<BaseResponse> scenePartEdit(ReqScenePartEdit request) {
        return RetrofitUtil.getInstance().getmApiService()
                .scenePartEdit(request.toJsonStr())
                .compose(new APITransFormer<BaseResponse>());
    }
}
