package com.mxkj.econtrol.presenter;

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
import com.mxkj.econtrol.net.APISubcriber;

/**
 * Created by chanjun on 2017/8/3.
 *
 * @Description:
 */

public class SceneEditPresenter implements SceneEditContract.Presenter {
    private SceneEditContract.Model mModel;
    private SceneEditContract.View mView;

    public SceneEditPresenter(SceneEditContract.View mView, SceneEditContract.Model mModel) {
        this.mModel = mModel;
        this.mView = mView;
    }

    /**获取所有场景列表*/
    @Override
    public void getPatternList() {
        final ReqPatternList reqPatternList = new ReqPatternList(mView.getHouseId());
        mModel.getPatternList(reqPatternList)
                .subscribe(new APISubcriber<ResGetPatternList>() {
                    @Override
                    public void onFail(ResGetPatternList baseResponse,String msg) {
                        mView.getPatternListFali(msg);
                    }
                    @Override
                    public void onSuccess(ResGetPatternList resGetPatternList) {
                        mView.getPatternListSuccess(resGetPatternList);
                    }
                });
    }

    @Override
    public void start() {

    }
}
