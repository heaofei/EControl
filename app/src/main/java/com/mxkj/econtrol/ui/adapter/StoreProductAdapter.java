package com.mxkj.econtrol.ui.adapter;

import com.mxkj.econtrol.R;
import com.mxkj.econtrol.app.Config;
import com.mxkj.econtrol.base.CommonAdapter;
import com.mxkj.econtrol.base.CommonViewHolder;
import com.mxkj.econtrol.bean.response.ResGetShopProductList;

import java.util.List;

/**
 * Created by liangshan on 2017/4/26.
 *
 * @Description：
 */

public class StoreProductAdapter extends CommonAdapter<ResGetShopProductList.Page.Product> {
    public StoreProductAdapter(List<ResGetShopProductList.Page.Product> mDatas, int mLayoutId) {
        super(mDatas, mLayoutId);
    }

    @Override
    public void convert(CommonViewHolder viewHolder, ResGetShopProductList.Page.Product product) {
        viewHolder.setSrc(R.id.imv_product_pic, Config.BASE_PIC_URL + product.getSmallPic());
    }
}
