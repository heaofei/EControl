package com.mxkj.econtrol.test;

import com.mxkj.econtrol.bean.request.ReqUserLogin;
import com.mxkj.econtrol.bean.response.ResUserLogin;
import com.mxkj.econtrol.contract.LoginContract;
import com.mxkj.econtrol.net.APITransFormer;
import com.mxkj.econtrol.net.RetrofitUtil;
import com.mxkj.econtrol.utils.RSAPKCS8X509Utils;

import rx.Observable;

/**
 * Created by liangshan on 2017/3/23.
 *
 * @Destription:
 */

public class TestStringModel implements LoginContract.Model {
    @Override
    public Observable<ResUserLogin> login(String name, String password) {
        final ReqUserLogin reqUserLogin = new ReqUserLogin();
        reqUserLogin.setUserName(name);
//        String key = (new BigInteger(APP.publicKey, 16)).toString();
//        RSAPublicKey publcikey = RSAUtils.createRSAPublicKey(key);

        reqUserLogin.setPassWord(RSAPKCS8X509Utils.getInstance().encryptWithBase64(password));
        return RetrofitUtil.getInstance()
                .getmApiService()
                .userLogin(reqUserLogin.toJsonStr())
                .compose(new APITransFormer<ResUserLogin>());


    }
}
