package com.mxkj.econtrol.contract;

import com.mxkj.econtrol.base.BaseModel;
import com.mxkj.econtrol.base.BasePresenter;
import com.mxkj.econtrol.base.BaseView;

/**
 * Created by liangshan on 2017/4/17.
 *
 * @Description：
 */

public class LightContract {
    interface View extends BaseView<Presenter> {

    }

    interface Presenter extends BasePresenter {

    }

    interface Model extends BaseModel {

    }
}
