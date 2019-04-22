package com.mxkj.econtrol.presenter;

import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.bean.request.ReqGetHouseAllPartList;
import com.mxkj.econtrol.bean.request.ReqGetPatternDetail;
import com.mxkj.econtrol.bean.request.ReqPatternDelete;
import com.mxkj.econtrol.bean.request.ReqPatternList;
import com.mxkj.econtrol.bean.response.ResGetHouseAllPartList;
import com.mxkj.econtrol.bean.response.ResGetPatternDetail;
import com.mxkj.econtrol.bean.response.ResGetPatternList;
import com.mxkj.econtrol.contract.EditDeviceListContract;
import com.mxkj.econtrol.contract.SceneEditContract;
import com.mxkj.econtrol.net.APISubcriber;

/**
 * Created by chanjun on 2017/8/3.
 *
 * @Description:
 */

public class EditDeviceListPresenter implements EditDeviceListContract.Presenter {
    private EditDeviceListContract.Model mModel;
    private EditDeviceListContract.View mView;

    public EditDeviceListPresenter(EditDeviceListContract.View mView, EditDeviceListContract.Model mModel) {
        this.mModel = mModel;
        this.mView = mView;
    }

    /**获取场景详情*/
    @Override
    public void getPatternDetail(String id) {
        final ReqGetPatternDetail reqGetPatternDetail = new ReqGetPatternDetail(id);

        mModel.getPatternDetail(reqGetPatternDetail)
                .subscribe(new APISubcriber<ResGetPatternDetail>() {
                    @Override
                    public void onFail(ResGetPatternDetail baseResponse,String msg) {
                        mView.getPatternDetailFali(msg);
                    }
                    @Override
                    public void onSuccess(ResGetPatternDetail resGetPatternList) {
                        mView.getPatternDetailSuccess(resGetPatternList);
                    }
                });
    }

    /***获取房子下所有部件列表**/
    @Override
    public void getHouseAllPartList(String houseId) {
        ReqGetHouseAllPartList reqGetHouseAllPartList = new ReqGetHouseAllPartList(houseId);

        mModel.getHouseAllPartList(reqGetHouseAllPartList)
                .subscribe(new APISubcriber<ResGetHouseAllPartList>() {
                    @Override
                    public void onFail(ResGetHouseAllPartList baseResponse,String msg) {
                        mView.getHouseAllPartListFail(msg);
                    }
                    @Override
                    public void onSuccess(ResGetHouseAllPartList resGetPatternList) {
                        mView.getHouseAllPartListSuccess(resGetPatternList);
                    }
                });
    }

    /*****场景模式编辑**/
    @Override
    public void patternEdit(String content) {
//        final ReqPatternEdit reqPatternEdit = new ReqPatternEdit(content);
        mModel.patternEdit(content)
                .subscribe(new APISubcriber<BaseResponse>() {
                    @Override
                    public void onFail(BaseResponse baseResponse,String msg) {
                        mView.patternEditFali(msg);
                    }
                    @Override
                    public void onSuccess(BaseResponse baseResponse) {
                        mView.patternEditSuccess(baseResponse);
                    }
                });
    }

    /*****场景模式删除**/
    @Override
    public void patternDelete(String id) {
        ReqPatternDelete reqPatternDelete = new ReqPatternDelete(id);
        mModel.patternDelete(reqPatternDelete)
                .subscribe(new APISubcriber<BaseResponse>() {
                    @Override
                    public void onFail(BaseResponse baseResponse,String msg) {
                        mView.patternDeleteFali(msg);
                    }
                    @Override
                    public void onSuccess(BaseResponse baseResponse) {
                        mView.patternDeleteSuccess(baseResponse);
                    }
                });
    }



    @Override
    public void start() {

    }
}
