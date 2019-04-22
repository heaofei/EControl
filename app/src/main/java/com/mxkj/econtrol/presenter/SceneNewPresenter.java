package com.mxkj.econtrol.presenter;

import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.bean.request.ReqGetHouseAllPartList;
import com.mxkj.econtrol.bean.request.ReqGetPatternDetail;
import com.mxkj.econtrol.bean.request.ReqPatternAdd;
import com.mxkj.econtrol.bean.request.ReqPatternDelete;
import com.mxkj.econtrol.bean.response.ResGetHouseAllPartList;
import com.mxkj.econtrol.bean.response.ResGetPatternDetail;
import com.mxkj.econtrol.contract.SceneNewContract;
import com.mxkj.econtrol.net.APISubcriber;

/**
 * Created by chanjun on 2017/8/3.
 *
 * @Description:
 */

public class SceneNewPresenter implements SceneNewContract.Presenter {
    private SceneNewContract.Model mModel;
    private SceneNewContract.View mView;

    public SceneNewPresenter(SceneNewContract.View mView, SceneNewContract.Model mModel) {
        this.mModel = mModel;
        this.mView = mView;
    }
    @Override
    public void getHouseAllPartList(String houseId) {
        mModel.getHouseAllPartList(new ReqGetHouseAllPartList(houseId))
                .subscribe(new APISubcriber<ResGetHouseAllPartList>() {
                    @Override
                    public void onFail(ResGetHouseAllPartList baseResponse, String msg) {
                        mView.getHouseAllPartListFail(msg);
                    }

                    @Override
                    public void onSuccess(ResGetHouseAllPartList resGetHouseAllPartList) {
                        mView.getHouseAllPartListSuccess(resGetHouseAllPartList);
                    }
                });
    }


    @Override
    public void patternAdd(String content) {
//        final ReqPatternAdd reqPatternAdd = new ReqPatternAdd(content);
        mModel.patternAdd(content)
                .subscribe(new APISubcriber<BaseResponse>() {
                    @Override
                    public void onFail(BaseResponse baseResponse,String msg) {
                        mView.patternAddFali(msg);
                    }
                    @Override
                    public void onSuccess(BaseResponse baseResponse) {
                        mView.patternAddSuccess(baseResponse);
                    }
                });
    }

    @Override
    public void start() {

    }
}
