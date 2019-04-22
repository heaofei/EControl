package com.mxkj.econtrol.model;

import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.bean.request.ReqGetHouseAllPartList;
import com.mxkj.econtrol.bean.request.ReqGetPatternDetail;
import com.mxkj.econtrol.bean.request.ReqPatternDelete;
import com.mxkj.econtrol.bean.request.ReqPatternList;
import com.mxkj.econtrol.bean.response.ResGetHouseAllPartList;
import com.mxkj.econtrol.bean.response.ResGetPatternDetail;
import com.mxkj.econtrol.bean.response.ResGetPatternList;
import com.mxkj.econtrol.contract.SceneEditContract;
import com.mxkj.econtrol.contract.SceneNewContract;
import com.mxkj.econtrol.net.APITransFormer;
import com.mxkj.econtrol.net.RetrofitUtil;

import rx.Observable;

/**
 * Created by chanjun on 2017/8/3.
 *
 * @Description:
 */

public class SceneEditModel implements SceneEditContract.Model {

    /***获取所有场景列表***/
    @Override
    public Observable<ResGetPatternList> getPatternList(ReqPatternList reqPatternList) {
        return RetrofitUtil.getInstance().getmApiService()
                .getPatternList(reqPatternList.toJsonStr())
                .compose(new APITransFormer<ResGetPatternList>());
    }

}
