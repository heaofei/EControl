package com.mxkj.econtrol.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mxkj.econtrol.R;
import com.mxkj.econtrol.app.APP;
import com.mxkj.econtrol.app.Config;
import com.mxkj.econtrol.base.BaseActivity;
import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.base.CommonAdapter;
import com.mxkj.econtrol.bean.request.ReqGetProductTypeList;
import com.mxkj.econtrol.bean.request.ReqGetShopProductList;
import com.mxkj.econtrol.bean.response.ResGetProductTypeList;
import com.mxkj.econtrol.bean.response.ResGetShopProductList;
import com.mxkj.econtrol.net.APISubcriber;
import com.mxkj.econtrol.net.APITransFormer;
import com.mxkj.econtrol.net.RetrofitUtil;
import com.mxkj.econtrol.ui.activity.ProductDetailActivity;
import com.mxkj.econtrol.ui.adapter.ProductTypeAdapter;
import com.mxkj.econtrol.ui.adapter.StoreProductAdapter;

import java.util.ArrayList;
import java.util.List;

import me.hwang.widgets.SmartPullableLayout;

public class StoreActivity extends BaseActivity implements SmartPullableLayout.OnPullListener {
    private RecyclerView mHouseList;
    private EditText mEditScan;
    private TextView mTvScan;
    private SmartPullableLayout smartPullableLayout;
    private int mPage = 0;
    private ImageView imvClear;
    private String mType;
    private List<ResGetShopProductList.Page.Product> mProduct;
    private StoreProductAdapter mProductAdapter;
    protected boolean isLastPage = false;
    private RecyclerView mProductTypeList;
    private List<ResGetProductTypeList.ProductType> mProductTypes;
    private ProductTypeAdapter productTypeAdapter;
    private ImageView imvHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_store);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        mHouseList = findView(R.id.recycle_house);
        mHouseList.setLayoutManager(new GridLayoutManager(this, 2));
        mEditScan = findView(R.id.edit_scan_content);
        mTvScan = findView(R.id.tv_scan);
        smartPullableLayout = findView(R.id.smpl_product);
        mProductTypeList = findView(R.id.recycle_product_style);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mProductTypeList.setLayoutManager(linearLayoutManager);
        imvClear = findView(R.id.imv_clear);
        imvHeader = findView(R.id.imv_user_header);

    }

    @Override
    protected void initData() {
        mProduct = new ArrayList<>();
        getProducts();
        getProductTypeList();
        if (APP.user != null) {
            Glide.with(this).load(Config.BASE_PIC_URL + APP.user.getHeadPicture())
                    .into(imvHeader);
        }

    }

    @Override
    protected void initListener() {
        mTvScan.setOnClickListener(this);
        imvClear.setOnClickListener(this);
        smartPullableLayout.setOnPullListener(this);

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_scan:
                mPage = 0;
                mProduct.clear();
                getProducts();
                break;
            case R.id.tv_product_type:
                //全部恢复为未选状态
                for (ResGetProductTypeList.ProductType type : mProductTypes) {
                    type.setSelected(false);
                }
                ResGetProductTypeList.ProductType productType = (ResGetProductTypeList.ProductType) v.getTag();
                mType = productType.getType();

                productType.setSelected(true);
                if (productTypeAdapter != null) {
                    productTypeAdapter.notifyDataSetChanged();
                }
                getProducts();
                break;
            case R.id.imv_clear:
                mEditScan.setText("");
                break;
        }
    }

    private void getProducts() {
        ReqGetShopProductList request = new ReqGetShopProductList(mPage, "6", mType, mEditScan.getText().toString());
        RetrofitUtil.getInstance().getmApiService()
                .getShopProductList(request.toJsonStr())
                .compose(new APITransFormer<ResGetShopProductList>())
                .subscribe(new APISubcriber<ResGetShopProductList>() {
                    @Override
                    public void onFail(ResGetShopProductList baseResponse, String msg) {
                        showToast(msg);
                        smartPullableLayout.stopPullBehavior();
                    }

                    @Override
                    public void onSuccess(final ResGetShopProductList resGetShopProductList) {
                        isLastPage = resGetShopProductList.getPage().isLastPage();
                        mProduct.addAll(resGetShopProductList.getPage().getContent());
                        if (mProductAdapter == null) {
                            mProductAdapter = new StoreProductAdapter(resGetShopProductList.getPage().getContent(), R.layout.layout_store_product_item);
                            mProductAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {
                                    Intent intent = new Intent(StoreActivity.this, ProductDetailActivity.class);
                                    intent.putExtra("product", resGetShopProductList.getPage().getContent().get(position));
                                    startToActivity(intent);
                                }
                            });
                            mHouseList.setAdapter(mProductAdapter);
                        } else {
                            mProductAdapter.notifyDataSetChanged();
                        }
                        smartPullableLayout.stopPullBehavior();

                    }
                });
    }

    @Override
    public void onPullDown() {
        mPage = 0;
        mProduct.clear();
        getProducts();
    }

    @Override
    public void onPullUp() {
        if (isLastPage) {
            showToast("没有更多啦");
            smartPullableLayout.stopPullBehavior();
            return;
        }
        mPage++;
        getProducts();

    }

    private void getProductTypeList() {
        RetrofitUtil.getInstance().getmApiService()
                .getProductTypeList(new ReqGetProductTypeList().toJsonStr())
                .compose(new APITransFormer<ResGetProductTypeList>())
                .subscribe(new APISubcriber<ResGetProductTypeList>() {
                    @Override
                    public void onFail(ResGetProductTypeList baseResponse,String msg) {
                        showToast(msg);
                    }

                    @Override
                    public void onSuccess(ResGetProductTypeList resGetShopProductList) {
                        mProductTypes = resGetShopProductList.getProductTypeList();
                        productTypeAdapter = new ProductTypeAdapter(mProductTypes, R.layout.layout_store_product_type_item, StoreActivity.this);
                        mProductTypeList.setAdapter(productTypeAdapter);
                    }
                });
    }

}
