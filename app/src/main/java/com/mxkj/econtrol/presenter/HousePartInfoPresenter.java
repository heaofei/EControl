package com.mxkj.econtrol.presenter;

import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.bean.request.ReqGetHouseAllPartList;
import com.mxkj.econtrol.bean.response.ResGetHouseAllPartList;
import com.mxkj.econtrol.contract.HousePartInfoContract;
import com.mxkj.econtrol.net.APISubcriber;

/**
 * Created by liangshan on 2017/4/17.
 *
 * @Description：
 */

public class HousePartInfoPresenter implements HousePartInfoContract.Presenter {
    private HousePartInfoContract.View mView;
    private HousePartInfoContract.Model mModel;

    public HousePartInfoPresenter(HousePartInfoContract.View mView, HousePartInfoContract.Model mModel) {
        this.mView = mView;
        this.mModel = mModel;
    }

    @Override
    public void start() {

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
}
