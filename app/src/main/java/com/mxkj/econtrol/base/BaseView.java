package com.mxkj.econtrol.base;

/**
 * Created by liangshan on 2017/3/23.
 *
 * @Destription:
 */

  public interface BaseView<T extends BasePresenter> {
    void setPresenter(T presenter);
}