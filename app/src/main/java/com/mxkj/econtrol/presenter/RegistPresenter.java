package com.mxkj.econtrol.presenter;

import com.mxkj.econtrol.app.APP;
import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.bean.request.ReqUserRegist;
import com.mxkj.econtrol.bean.request.ReqUserRegistSms;
import com.mxkj.econtrol.bean.response.ResUserLogin;
import com.mxkj.econtrol.bean.response.ResUserRegist;
import com.mxkj.econtrol.contract.RegistContract;
import com.mxkj.econtrol.net.APISubcriber;
import com.mxkj.econtrol.utils.RSAPKCS8X509Utils;

/**
 * Created by liangshan on 2017/3/23.
 *
 * @Destription:
 */

public class RegistPresenter implements RegistContract.Presenter {

    private RegistContract.Model model;
    private RegistContract.View view;

    public RegistPresenter(RegistContract.View view, RegistContract.Model model) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void regist() {
        String userName = view.getUserName();
        String passwd = view.getRePassword();
        final String rePasswd = view.getUserPassword();
        String tel = view.getTel();
        final ReqUserRegist reqUserRegist = new ReqUserRegist();
        reqUserRegist.setUserName(userName);
        reqUserRegist.setTel(tel);
        reqUserRegist.setSmsCode(view.getCode());
//        String key = (new BigInteger(APP.publicKey, 16)).toString();
//        RSAPublicKey publcikey = RSAUtils.createRSAPublicKey(key);
//        reqUserRegist.setPassWord(RSAUtils.encryptString(publcikey, passwd));
//        reqUserRegist.setRePassWord(RSAUtils.encryptString(publcikey, passwd));
        reqUserRegist.setPassWord(RSAPKCS8X509Utils.getInstance().encryptWithBase64(passwd));
        reqUserRegist.setRePassWord(RSAPKCS8X509Utils.getInstance().encryptWithBase64(passwd));
        model.regist(reqUserRegist)
                .subscribe(new APISubcriber<ResUserRegist>() {
                    @Override
                    public void onFail(ResUserRegist baseResponse,String msg) {
                        view.registFail(msg);
                    }

                    @Override
                    public void onSuccess(ResUserRegist resUserRegist) {
                        APP.user = new ResUserLogin();
                        APP.user.setUserName(resUserRegist.getUserName());
                        APP.user.setUserId(resUserRegist.getUserId());
                        APP.user.setToken(resUserRegist.getToken());
                        APP.user.setBorn(resUserRegist.getBoth());
                        APP.user.setNiceName(resUserRegist.getNiceName());
                        APP.headerData.getHeaderToken().setToken(resUserRegist.getToken());
                        APP.headerData.getHeaderToken().setUserName(resUserRegist.getUserName());
                        view.registSuccess();
                    }
                });


    }

    @Override
    public void userRegistSms() {
        ReqUserRegistSms reqUserRegist = new ReqUserRegistSms(view.getTel());
        model.userRegistSms(reqUserRegist)
                .subscribe(new APISubcriber<BaseResponse>() {
                    @Override
                    public void onFail(BaseResponse baseResponse,String msg) {
                        view.showMsg(msg);
                    }

                    @Override
                    public void onSuccess(BaseResponse baseResponse) {
                        view.sendCodeSuccess();
                    }
                });
    }

    @Override
    public void start() {

    }
}
