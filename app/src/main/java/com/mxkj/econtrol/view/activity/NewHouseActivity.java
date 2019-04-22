package com.mxkj.econtrol.view.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aigestudio.wheelpicker.WheelPicker;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.mxkj.econtrol.R;
import com.mxkj.econtrol.app.APP;
import com.mxkj.econtrol.base.BaseActivity;
import com.mxkj.econtrol.base.BaseResponse;
import com.mxkj.econtrol.bean.request.ReqGetBuildingList;
import com.mxkj.econtrol.bean.request.ReqGetHouseList;
import com.mxkj.econtrol.bean.request.ReqGetHousingEstateList;
import com.mxkj.econtrol.bean.request.ReqUserApplyBindHouse;
import com.mxkj.econtrol.bean.request.ReqUserInfoModify;
import com.mxkj.econtrol.bean.response.Area;
import com.mxkj.econtrol.bean.response.Building;
import com.mxkj.econtrol.bean.response.City;
import com.mxkj.econtrol.bean.response.House;
import com.mxkj.econtrol.bean.response.HousingEstat;
import com.mxkj.econtrol.contract.NewHouseContract;
import com.mxkj.econtrol.model.NewHouseModel;
import com.mxkj.econtrol.net.APIService;
import com.mxkj.econtrol.net.APISubcriber;
import com.mxkj.econtrol.net.APITransFormer;
import com.mxkj.econtrol.net.RetrofitUtil;
import com.mxkj.econtrol.presenter.NewHousePresenter;
import com.mxkj.econtrol.ui.activity.TakePhotoActivity;
import com.mxkj.econtrol.utils.Bitmap2StringUtil;
import com.mxkj.econtrol.utils.MyDialogUtil;
import com.mxkj.econtrol.widget.TipDialog;

import java.util.ArrayList;
import java.util.List;

/***
 * 新增房子
 *
 */

public class NewHouseActivity extends BaseActivity implements NewHouseContract.View {

    private List<City> mProvinceList; // 省级数据 （包括市）详细看数据结构
    private List<City> mCityList;     // 市级数据（包括区）详细看数据结构

    private NewHouseContract.Presenter mPresenter;
    //房子id
    private String mHouseId;
    //小区id
    private String mHouseEstateId;
    //选择小区
    private RelativeLayout rl_select_house_estate;
    private TextView tv_select_house_estate;
    private RelativeLayout rl_select_house;

    private String hoursProvince;  // 省名
    private String hoursCity;  // 城市名
    private String hoursArea; // 区名
    private String hoursAreaId; // 区Id，用来获取小区
    private String hoursCityId; // 市Id，

    private String estate; // 小区名
    private String buidingName; // 栋数
    private String houseName; // 房号

    //房子
    private TextView tv_select_house;
    private EditText ed_name;// 真实姓名
    private RelativeLayout rl_certificates;// 身份证
    private RelativeLayout rl_property;// 物业这证明
    private boolean isOwner = false;// 是否业主(默认不是业主)
    private ImageView iv_is_owner;
    private ImageView iv_not_owner;
    private LinearLayout ll_is_owner;


    //确定按钮
    private Button bv_submit;
    // 省市区
    private WheelPicker provinceWheelPicker;
    private WheelPicker cityWheelPicker;
    private WheelPicker areaWheelPicker;
    // 小区，区域，房号
    private WheelPicker stateWheelPicker;
    private WheelPicker buildingWheelPicker;
    private WheelPicker houseWheelPicker;

    private TextView mTvSelectStateOk;
    private TextView mTvSelectHouseOk;
    private TextView tv_is_owner, tv_not_owner;
    private TextView tv_select_certificates, tv_select_property;

    // 初始化title
    private TextView tv_title_left;
    private TextView tv_title_content;
    private ImageView iv_more;
    private TextView tv_title_right;

    private String idCardFront;//身份证正面
    private String idCardBack;//身份证背面
    private String propertyFront = "";//物业证明正面

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_new_house);

        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initView() {

        tv_title_left = findView(R.id.tv_title_left);
        tv_title_content = findView(R.id.tv_title_content);
        iv_more = findView(R.id.iv_more);
        tv_title_right = findView(R.id.tv_title_right);

        tv_title_content.setText("新增住宅");

        bv_submit = findView(R.id.bv_submit);
        iv_is_owner = findView(R.id.iv_is_owner);
        iv_not_owner = findView(R.id.iv_not_owner);
        rl_select_house_estate = findView(R.id.rl_select_house_estate);
        tv_select_house_estate = findView(R.id.tv_select_house_estate);
        rl_select_house = findView(R.id.rl_select_house);
        tv_select_house = findView(R.id.tv_select_house);
        ed_name = findView(R.id.ed_name);
        rl_certificates = findView(R.id.rl_certificates);
        ed_name = findView(R.id.ed_name);
        rl_property = findView(R.id.rl_property);
        ll_is_owner = findView(R.id.ll_is_owner);
        tv_is_owner = findView(R.id.tv_is_owner);
        tv_not_owner = findView(R.id.tv_not_owner);
        tv_select_certificates = findView(R.id.tv_select_certificates);
        tv_select_property = findView(R.id.tv_select_property);

        /*Editable ea = ed_name.getText();
        ed_name.setSelection(ea.length());*/



        if (!TextUtils.isEmpty(APP.user.getRealName())) {
            ed_name.setText(APP.user.getRealName());
        }

    }

    @Override
    protected void initData() {
        mPresenter = new NewHousePresenter(this, new NewHouseModel());
        mPresenter.getAreaList("0"); // 进来时首先获取省列表
    }

    @Override
    protected void initListener() {
        tv_title_left.setOnClickListener(this);
        iv_is_owner.setOnClickListener(this);
        iv_not_owner.setOnClickListener(this);
        bv_submit.setOnClickListener(this);
        rl_select_house_estate.setOnClickListener(this);
        rl_select_house.setOnClickListener(this);
        rl_certificates.setOnClickListener(this);
        rl_property.setOnClickListener(this);
        ed_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkData();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    public void setPresenter(NewHouseContract.Presenter presenter) {

    }

    @Override
    public void getAreaListSuccess(List<City> cities) {
        Gson gson = new Gson();
        String content = gson.toJson(cities);

        if (cities.size() > 0 && cities.get(0).getLevel().equals("1")) {
            this.mCityList = cities;             // 市级数据
            setAreaWheelPicker(mCityList.get(0).getChildList()); // 设置显示区
        } else if (cities.size() > 0) {
            this.mProvinceList = cities;     // 省级数据
        }
    }

    @Override
    public void getAreaListFail(String msg) {
        showToast(msg);
    }

    @Override
    public void getHousingEstateListSuccess(List<HousingEstat> housingEstats) {
        Gson gson = new Gson();
        String content = gson.toJson(housingEstats);

        initHousingEstateWheelPicker(); // 初始化 选择小区，栋数，房号对话框

        WheelPicker.OnItemSelectedListener stateOnItemSelectedListener = new WheelPicker.OnItemSelectedListener() {
            @Override
            public void onItemSelected(WheelPicker wheelPicker, Object o, int i) {
                if (o instanceof HousingEstat) {
                    HousingEstat housingEstat = (HousingEstat) o;
                    mHouseEstateId = housingEstat.getHousingEstatId();
                    estate = housingEstat.getHousingEstatName(); // 显示小区名字
                    mTvSelectHouseOk.setEnabled(true);
                    showSelectHouse(); // 获取 小区栋数
                }
            }
        };
        if (housingEstats.size() > 0) {
            stateWheelPicker.setData(housingEstats); //  设置数据
            stateOnItemSelectedListener.onItemSelected(null, housingEstats.get(0), 0);
            stateWheelPicker.setSelectedItemPosition(0);
            mHouseEstateId = housingEstats.get(0).getHousingEstatId(); // 默认第一个id
            showSelectHouse(); // 获取 小区栋数
        } else {
            mTvSelectHouseOk.setEnabled(false);


        }
        stateWheelPicker.setOnItemSelectedListener(stateOnItemSelectedListener);


    }

    @Override
    public void getHousingEstateListFail(String msg) {
        showToast(msg);
    }

    @Override
    public void getBuildingListSuccess(final List<Building> buildings) {


        WheelPicker.OnItemSelectedListener buildingOnItemSelectedListener = new WheelPicker.OnItemSelectedListener() {
            @Override
            public void onItemSelected(WheelPicker wheelPicker, Object o, int i) {
                if (o instanceof Building) {
                    Building building = (Building) o;
                    buidingName = building.getBuidingName();  // 建筑名，栋数
                    if (!buidingName.equals("请选择")) {
                        mPresenter.getHouseList(new ReqGetHouseList(building.getBuidingId()));
                    }
                }
            }
        };
        if (buildings.size() > 0) {
            buildingWheelPicker.setData(buildings);
            buildingOnItemSelectedListener.onItemSelected(null, buildings.get(0), 0);
            buildingWheelPicker.setSelectedItemPosition(0);
        } else {                        // 增加一个空的实体类是为了没有数据的时候显示 “请选择”
            List<Building> areaList = new ArrayList<>();
            Building building = new Building();
            building.setBuidingName("请选择地区");
            areaList.add(building);
            buildingWheelPicker.setData(areaList);
//            areaWheelPicker.setSelectedItemPosition(0);
            mTvSelectHouseOk.setEnabled(false);

            List<House> houselist = new ArrayList<>();
            House house = new House();
            house.setHouseName("请选择房号");
            houselist.add(house);
            houseWheelPicker.setData(houselist);

        }
        buildingWheelPicker.setOnItemSelectedListener(buildingOnItemSelectedListener);


    }

    @Override
    public void getBuildingListFail(String msg) {
        showToast(msg);
    }

    @Override
    public void getHouseListSuccess(List<House> houses) {

        String content = new Gson().toJson(houses);
        WheelPicker.OnItemSelectedListener houseOnItemSelectedListener = new WheelPicker.OnItemSelectedListener() {
            @Override
            public void onItemSelected(WheelPicker wheelPicker, Object o, int i) {
                if (o instanceof House) {
                    mHouseId = ((House) o).getHouseId();
                    houseName = ((House) o).getHouseName(); // 房子名
                }
            }
        };
        if (houses.size() > 0) {
            houseWheelPicker.setData(houses);
            houseOnItemSelectedListener.onItemSelected(null, houses.get(0), 0);
            houseWheelPicker.setSelectedItemPosition(0);
            mTvSelectHouseOk.setEnabled(true);
        } else {                        // 增加一个空的实体类是为了没有数据的时候显示 “请选择”
            List<House> houselist = new ArrayList<>();
            House house = new House();
            house.setHouseName("请选择");
            houselist.add(house);
            houseWheelPicker.setData(houselist);
            mTvSelectHouseOk.setEnabled(false);
        }
        houseWheelPicker.setOnItemSelectedListener(houseOnItemSelectedListener);
    }

    @Override
    public void getHouseListFail(String msg) {
        showToast(msg);
    }

    @Override
    public void userApplyBindHouseSuccess() {
        showToast(getString(R.string.apply_success));
        finish();
    }

    @Override
    public void userApplyBindHouseFail(String msg) {
        showToast(msg);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_title_left:
                showTipDialog(); // 放弃编辑弹窗
                break;
            case R.id.bv_submit:
                if (TextUtils.isEmpty(mHouseId)) {
                    showToast(getString(R.string.please_select_house));
                    return;
                } else if (TextUtils.isEmpty(ed_name.getText().toString().trim())) {
                    showToast(getString(R.string.please_edit_name));
                    return;
                }
//                String string = Bitmap2StringUtil.bitmapToString("/data/data/com.mxkj.econtrol/cache/takephoto_cache/1510292560899.jpg");
                updateUserRealName();// 同步真实姓名
                if (isOwner) {
                   String idCardFrontString =  Bitmap2StringUtil.bitmapToString(idCardFront); // 直接util转String之后直接传给后台
                    String idCardBackString =  Bitmap2StringUtil.bitmapToString(idCardBack); // 直接util转String之后直接传给后台
                    String propertyFrontString =  Bitmap2StringUtil.bitmapToString(propertyFront); // 直接util转String之后直接传给后台
                    mPresenter.userApplyBindHouse(
                            new ReqUserApplyBindHouse(
                                    mHouseId, "0", ed_name.getText().toString().trim(),idCardFrontString, idCardBackString, propertyFrontString)); // 业主

                } else {
                    mPresenter.userApplyBindHouse(new ReqUserApplyBindHouse(mHouseId, "1", ed_name.getText().toString().trim(), "", "", "")); // 家庭成员(不用填写身份证和业主证明)
                }
                break;
            case R.id.rl_select_house_estate: // 选择省市区
                // 选择区域前， 把房子id都清空了
                mHouseId = "";
                tv_select_house.setText("");
                tv_select_house.setHint("请选择房号");
                tv_select_house.setTextColor(getResources().getColor(R.color.text_black_c3c3c3));
                showSelectState();
                break;
            case R.id.rl_select_house:          // 选择房子
                if (TextUtils.isEmpty(hoursAreaId)) {
                    showToast("请选择地区");
                    return;
                }
                showHousingEstate(); // 显示选择 小区，栋数，房号
                break;
            case R.id.iv_is_owner:
                isOwner = true;
                ll_is_owner.setVisibility(View.VISIBLE);
                iv_is_owner.setImageResource(R.drawable.center_userinfo_choose_selected);
                iv_not_owner.setImageResource(R.drawable.center_userinfo_choose_default);
                tv_is_owner.setTextColor(getResources().getColor(R.color.text_black_666));
                tv_not_owner.setTextColor(getResources().getColor(R.color.text_black_999));
                checkData();
                break;
            case R.id.iv_not_owner:
                isOwner = false;
                ll_is_owner.setVisibility(View.INVISIBLE);
                iv_not_owner.setImageResource(R.drawable.center_userinfo_choose_selected);
                iv_is_owner.setImageResource(R.drawable.center_userinfo_choose_default);
                tv_not_owner.setTextColor(getResources().getColor(R.color.text_black_666));
                tv_is_owner.setTextColor(getResources().getColor(R.color.text_black_999));
                checkData();
                break;
            case R.id.rl_certificates:
                Intent intent = new Intent(this, TakePhotoActivity.class);
                intent.putExtra("type", "CERTIFICATES");
                intent.putExtra("idCardFront", idCardFront);
                intent.putExtra("idCardBack", idCardBack);
                startActivityForResult(intent, 1);
                break;
            case R.id.rl_property:
                Intent intent02 = new Intent(this, TakePhotoActivity.class);
                intent02.putExtra("type", "PROPERTY"); // 物业证明
                intent02.putExtra("propertyFront", propertyFront);
                startActivityForResult(intent02, 2);
                break;

        }
    }

    /***
     * 更新真实姓名
     */
    private void updateUserRealName() {
        ReqUserInfoModify modify = new ReqUserInfoModify();
        modify.setNiceName(APP.user.getNiceName()+"");
        if (TextUtils.isEmpty(APP.user.getSex())) {
            modify.setSex("0");
        }else {
          modify.setSex(APP.user.getSex()+"");
        }
        modify.setRealName(ed_name.getText().toString().trim());
        RetrofitUtil.getInstance().getmApiService()
                .API(modify.toJsonStr(), APIService.USER_INFO_MODIFY)
                .compose(new APITransFormer<BaseResponse>())
                .subscribe(new APISubcriber<BaseResponse>() {
                    @Override
                    public void onFail(BaseResponse baseResponse, String msg) {
                    }
                    @Override
                    public void onSuccess(BaseResponse response) {
                        APP.user.setRealName(ed_name.getText().toString().trim());
                    }
                });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 01) { // 结果码
            if (null != data) {
                idCardFront =  data.getStringExtra("idCardFront");
                idCardBack =  data.getStringExtra("idCardBack");

//                idCardFront =  Bitmap2StringUtil.bitmapToString(data.getStringExtra("idCardFront")); // 直接util转String之后直接传给后台
//                idCardBack = Bitmap2StringUtil.bitmapToString(data.getStringExtra("idCardBack"));
                if (!TextUtils.isEmpty(idCardFront) && !TextUtils.isEmpty(idCardBack)) {
                    tv_select_certificates.setText("已添加");
                    tv_select_certificates.setTextColor(getResources().getColor(R.color.text_black_999));
                }
            }
        } else if (requestCode == 02) {
            if (null != data) {
                propertyFront =  data.getStringExtra("propertyFront");

//                propertyFront = Bitmap2StringUtil.bitmapToString(data.getStringExtra("propertyFront"));
                if (!TextUtils.isEmpty(propertyFront)) {
                    tv_select_property.setText("已添加");
                    tv_select_property.setTextColor(getResources().getColor(R.color.text_black_999));
                }
            }
        }
       checkData();

    }


    /***
     * 显示推出编辑弹窗
     */
    private void showTipDialog() {
        MyDialogUtil dialog = new MyDialogUtil(this) {
            @Override
            public void confirm() {
                finish();
            }
        };
        dialog.showTipDialog(getString(R.string.giveup_edit), "");
    }

    /**
     * @Desc:显示选择房子
     * @author:liangshan
     */
    private void showSelectHouse() {
        mPresenter.getBuildingList(new ReqGetBuildingList(mHouseEstateId));
    }

    private void showHousingEstate() {
        mPresenter.getHousingEstateList(new ReqGetHousingEstateList(hoursCityId, hoursAreaId)); // 获取小区信息列表（ 例如： 珠海华发小区）
    }

    /**
     * @Desc: 显示选择 省、市、
     * @author:liangshan
     */
    private void showSelectState() {
        final Dialog selectState = new Dialog(this);
        selectState.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = getLayoutInflater().inflate(R.layout.layout_select_housestate, null);
        provinceWheelPicker = (WheelPicker) view.findViewById(R.id.wheel_picker01);
        cityWheelPicker = (WheelPicker) view.findViewById(R.id.wheel_picker02);
        areaWheelPicker = (WheelPicker) view.findViewById(R.id.wheel_picker03);

        mTvSelectStateOk = (TextView) view.findViewById(R.id.tv_ok);
        mTvSelectStateOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_select_house_estate.setText(hoursProvince + "-" + hoursCity + "-" + hoursArea); // 确定后，显示全名字
                tv_select_house_estate.setTextColor(getResources().getColor(R.color.text_black_999));
                selectState.dismiss();
                checkData();
            }
        });

        if (mProvinceList != null && mProvinceList.size() > 0) {
            provinceWheelPicker.setData(mProvinceList);
        }

        final WheelPicker.OnItemSelectedListener cityOnItemSelectedListener = new WheelPicker.OnItemSelectedListener() {
            @Override
            public void onItemSelected(WheelPicker wheelPicker, Object o, int i) {
                if (o instanceof Area) {
                    Area area = (Area) o;
                    mTvSelectStateOk.setEnabled(false);
                    hoursAreaId = null;
                    hoursCityId = area.getAreaId();
                    hoursCity = area.getAreaName();// 市级名字，例如：广州市
                    if (mCityList != null && mCityList.size() > 0) {
                        setAreaWheelPicker(mCityList.get(i).getChildList());  // 设置显示区
                    } else {
                        List<Area> areaList = new ArrayList<>();
                        Area area1 = new Area();
                        area1.setAreaName("请选择");
                        areaList.add(area1);
                        areaWheelPicker.setData(areaList);
                        areaWheelPicker.setSelectedItemPosition(0);
                        mTvSelectStateOk.setEnabled(false);
                    }
                }
            }
        };
        WheelPicker.OnItemSelectedListener provinceOnItemSelectedListener = new WheelPicker.OnItemSelectedListener() {
            @Override
            public void onItemSelected(WheelPicker wheelPicker, Object o, int i) {
                if (o instanceof City) {
                    City city = (City) o;
                    hoursProvince = city.getAreaName();// 省名字
                    if (city.getChildList() != null) {

                        if (city.getChildList().size() > 0)   // 默认显示第一个城市
                        {
                            cityWheelPicker.setData(city.getChildList());
                            cityOnItemSelectedListener.onItemSelected(null, city.getChildList().get(0), 0);
                            cityWheelPicker.setSelectedItemPosition(0);
                        } else {
                            List<City> list = new ArrayList<>();
                            City cit = new City();
                            cit.setAreaName("请选择");
                            list.add(cit);
                            cityWheelPicker.setData(list);


                            List<Area> areaList = new ArrayList<>();
                            Area area = new Area();
                            area.setAreaName("请选择");
                            areaList.add(area);
                            areaWheelPicker.setData(areaList);
                            areaWheelPicker.setSelectedItemPosition(0);
                            mTvSelectStateOk.setEnabled(false);
                        }
                    }
                    mTvSelectStateOk.setEnabled(false);
                    hoursAreaId = null;
                    mPresenter.getAreaList(city.getAreaCode()); // 获取市级列表
                }
            }
        };
        if (mProvinceList != null && mProvinceList.size() > 0) {
            //默认选择第一个，手动回调下
            provinceOnItemSelectedListener.onItemSelected(null, mProvinceList.get(0), 0);
        }

        provinceWheelPicker.setOnItemSelectedListener(provinceOnItemSelectedListener);
        cityWheelPicker.setOnItemSelectedListener(cityOnItemSelectedListener);

        selectState.setContentView(view);
        selectState.show();
    }

    /****
     * 显示选择 区
     ***/
    private void setAreaWheelPicker(final List<Area> areaList) {
        areaWheelPicker.setData(areaList);
        WheelPicker.OnItemSelectedListener stateOnItemSelectedListener = new WheelPicker.OnItemSelectedListener() {
            @Override
            public void onItemSelected(WheelPicker wheelPicker, Object o, int i) {
                if (o instanceof Area) {          //
                    Area area = (Area) o;
                    hoursArea = area.getAreaName(); // 区名字
                    if (!hoursArea.equals("请选择")) {
                        hoursAreaId = area.getAreaId(); /// 区ID
                        mTvSelectStateOk.setEnabled(true);
                    }
                } else if (areaList.size() > 0 && areaList.size() > i && !areaList.get(i).equals("请选择")) {
                    hoursAreaId = areaList.get(i).getAreaId(); /// 区ID
                    hoursArea = areaList.get(i).getAreaName();
                    mTvSelectStateOk.setEnabled(true);
                }
            }
        };
        if (areaList.size() > 0) {
            stateOnItemSelectedListener.onItemSelected(null, areaList, 0);
            areaWheelPicker.setSelectedItemPosition(0);
        } else {                            // 增加一个空的实体类是为了没有数据的时候显示 “请选择”
            List<Area> areaList01 = new ArrayList<>();
            Area area = new Area();
            area.setAreaName("请选择");
            areaList01.add(area);
            areaWheelPicker.setData(areaList01);
            areaWheelPicker.setSelectedItemPosition(0);
            mTvSelectStateOk.setEnabled(false);
        }
        areaWheelPicker.setOnItemSelectedListener(stateOnItemSelectedListener);
    }

    /****
     * 初始化 选择 小区，栋数，房号的 对话框
     */
    private void initHousingEstateWheelPicker() {


        final Dialog selectHouse = new Dialog(this);
        selectHouse.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = getLayoutInflater().inflate(R.layout.layout_select_house, null);
        selectHouse.setContentView(view);
        stateWheelPicker = (WheelPicker) view.findViewById(R.id.wheel_picker01);
        buildingWheelPicker = (WheelPicker) view.findViewById(R.id.wheel_picker02);
        houseWheelPicker = (WheelPicker) view.findViewById(R.id.wheel_picker03);
        mTvSelectHouseOk = (TextView) view.findViewById(R.id.tv_ok);
        mTvSelectHouseOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectHouse.dismiss();
                bv_submit.setEnabled(true);
                bv_submit.setTextColor(0xffffffff);
                tv_select_house.setText(estate + "-" + buidingName + "-" + houseName);
                tv_select_house.setTextColor(getResources().getColor(R.color.text_black_999));
                checkData();
            }
        });
        selectHouse.show();

    }

    private boolean checkData() {
        if (TextUtils.isEmpty(tv_select_house_estate.getText().toString().trim())) {
            bv_submit.setEnabled(false);
            bv_submit.setBackgroundResource(R.drawable.login_regist_bg_50opaque);
            return false;
        } else if (TextUtils.isEmpty(tv_select_house.getText().toString().trim())) {
            bv_submit.setEnabled(false);
            bv_submit.setBackgroundResource(R.drawable.login_regist_bg_50opaque);
            return false;
        } else if (TextUtils.isEmpty(ed_name.getText().toString().trim())) {
            bv_submit.setEnabled(false);
            bv_submit.setBackgroundResource(R.drawable.login_regist_bg_50opaque);
            return false;
        } else if (isOwner) {
            if (!TextUtils.isEmpty(idCardFront) && !TextUtils.isEmpty(idCardBack) && !TextUtils.isEmpty(propertyFront)){ // 物业证明暂时设置必选
                bv_submit.setEnabled(true);
                bv_submit.setBackgroundResource(R.drawable.login_regist_bg);
                return true;
            }else {
                bv_submit.setEnabled(false);
                bv_submit.setBackgroundResource(R.drawable.login_regist_bg_50opaque);
                return false;
            }
        } else if (!isOwner) {
            bv_submit.setEnabled(true);
            bv_submit.setBackgroundResource(R.drawable.login_regist_bg);
            return false;
        }
        bv_submit.setEnabled(true);
        bv_submit.setBackgroundResource(R.drawable.login_regist_bg);
        return true;
    }

    @Override
    public void onBackPressed() {
            showTipDialog();
//        super.onBackPressed();
    }
}
