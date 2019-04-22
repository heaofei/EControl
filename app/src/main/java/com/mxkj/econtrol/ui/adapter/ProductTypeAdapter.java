package com.mxkj.econtrol.ui.adapter;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.mxkj.econtrol.R;
import com.mxkj.econtrol.base.CommonAdapter;
import com.mxkj.econtrol.base.CommonViewHolder;
import com.mxkj.econtrol.bean.response.ResGetProductTypeList;
import com.mxkj.econtrol.utils.ResourceUtil;

import java.util.List;

/**
 * Created by liangshan on 2017/5/3.
 *
 * @Description： 商城商品类型适配器
 */

public class ProductTypeAdapter extends CommonAdapter<ResGetProductTypeList.ProductType> {
    private View.OnClickListener mListener;

    public ProductTypeAdapter(List<ResGetProductTypeList.ProductType> mDatas, int mLayoutId, View.OnClickListener listener) {
        super(mDatas, mLayoutId);
        mListener = listener;
    }

    @Override
    public void convert(CommonViewHolder viewHolder, ResGetProductTypeList.ProductType productType) {
        TextView name = viewHolder.getView(R.id.tv_product_type);
        View splitLine = viewHolder.getView(R.id.view_split);
        View indecate = viewHolder.getView(R.id.view_indecate);
        if (mDatas.indexOf(productType) == mDatas.size() - 1) {
            //最后一项，去掉分割线
            splitLine.setVisibility(View.GONE);
        } else {
            splitLine.setVisibility(View.VISIBLE);
        }
        name.setText(productType.getName());
        View view = viewHolder.getView(R.id.tv_product_type);
        view.setOnClickListener(mListener);
        view.setTag(productType);
        if (productType.isSelected()) {
            name.setTextColor(0xfff72971);
            indecate.setVisibility(View.VISIBLE);
        } else {
            name.setTextColor(0xff888888);
            indecate.setVisibility(View.GONE);
        }

    }
}
