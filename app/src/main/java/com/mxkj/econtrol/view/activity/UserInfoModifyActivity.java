package com.mxkj.econtrol.view.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aigestudio.wheelpicker.WheelPicker;
import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.jph.takephoto.model.CropOptions;
import com.mxkj.econtrol.R;
import com.mxkj.econtrol.app.APP;
import com.mxkj.econtrol.app.Config;
import com.mxkj.econtrol.app.Msg;
import com.mxkj.econtrol.base.BaseActivity;
import com.mxkj.econtrol.bean.EventBusUIMessage;
import com.mxkj.econtrol.bean.response.Area;
import com.mxkj.econtrol.bean.response.City;
import com.mxkj.econtrol.bean.response.ResAreaBean;
import com.mxkj.econtrol.bean.response.ResProvinceBean;
import com.mxkj.econtrol.bean.response.ResUserHeadPicModify;
import com.mxkj.econtrol.contract.UserInfoModifyContract;
import com.mxkj.econtrol.model.UserInfoModifyModel;
import com.mxkj.econtrol.presenter.UserInfoModifyPresenter;
import com.mxkj.econtrol.ui.activity.BrowsePicActivity;
import com.mxkj.econtrol.ui.activity.SelectPictureActivity;
import com.mxkj.econtrol.utils.Bitmap2StringUtil;
import com.mxkj.econtrol.utils.MyDialogUtil;
import com.mxkj.econtrol.utils.TextUtil;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 我的详细资料
 */
public class UserInfoModifyActivity extends BaseActivity implements UserInfoModifyContract.View {

    // 初始化title
    private TextView tv_title_left;
    private TextView tv_title_content;
    private ImageView iv_more;
    private TextView tv_title_right;

    private com.mxkj.econtrol.widget.CircleImageView imv_header;
    private TextView tv_header;
    private TextView tv_nick;
    private EditText edit_nice_name;
    private ImageView imv_male;
    private TextView tv_male;
    private ImageView imv_female;
    private TextView tv_female;
    private TextView tv_name;
    private EditText edit_name;
    private RelativeLayout rl_birthday;
    private TextView tv_both;
    private RelativeLayout rl_address;
    private RelativeLayout rl_item;
    private TextView tv_address;


    private List<ResAreaBean.AreaListBean> options1Items = new ArrayList<>();// 省级数据
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>(); // 市级数据
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();// 区级数据
    private Thread thread;
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;
    private boolean isLoaded = false;// 地区数据是否加载完

    private UserInfoModifyContract.Presenter mPresenter;
    //图片路径
    private String patch;
    private DatePickerDialog mDatePickerDialog;

    private OptionsPickerView pvOptions; // 所在地弹窗
    private TimePickerView pvTime; // 生日时间选择器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_user_info_modify);
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initView() {
        tv_title_left = findView(R.id.tv_title_left);
        tv_title_content = findView(R.id.tv_title_content);
        iv_more = findView(R.id.iv_more);
        tv_title_right = findView(R.id.tv_title_right);
        tv_title_content.setText(getString(R.string.person_info));
        tv_title_right.setText(getString(R.string.keep));

        imv_header = (com.mxkj.econtrol.widget.CircleImageView) findViewById(R.id.imv_header);
        tv_header = (TextView) findViewById(R.id.tv_header);
        tv_nick = (TextView) findViewById(R.id.tv_nick);
        edit_nice_name = (EditText) findViewById(R.id.edit_nice_name);
        imv_male = (ImageView) findViewById(R.id.imv_male);
        tv_male = (TextView) findViewById(R.id.tv_male);
        imv_female = (ImageView) findViewById(R.id.imv_female);
        tv_female = (TextView) findViewById(R.id.tv_female);
        tv_name = (TextView) findViewById(R.id.tv_name);
        edit_name = (EditText) findViewById(R.id.edit_name);
        rl_birthday = (RelativeLayout) findViewById(R.id.rl_birthday);
        tv_both = (TextView) findViewById(R.id.tv_both);
        rl_address = (RelativeLayout) findViewById(R.id.rl_address);
        rl_item = (RelativeLayout) findViewById(R.id.rl_item);
        tv_address = (TextView) findViewById(R.id.tv_address);


        int year = 2017, mon = 1, day = 1;

        try {
            if (!TextUtils.isEmpty(APP.user.getBorn())) {
                String[] split = APP.user.getBorn().split("-");
                for (int i = 0; i < split.length; i++) {
                    if (i == 0) {
                        year = Integer.valueOf(split[i].trim());
                    } else if (i == 1) {
                        mon = Integer.valueOf(split[i].trim());
                    } else if (i == 2) {
                        day = Integer.valueOf(split[i].trim());
                    }
                }
            }
        } catch (Exception e) {

        }

        mDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month += 1;
                tv_both.setText(year + "年" + month + "月" + dayOfMonth + "日");
            }
        }, year, mon - 1, day);
    }

    @Override
    protected void initData() {
        mPresenter = new UserInfoModifyPresenter(this, new UserInfoModifyModel());
        if (!TextUtils.isEmpty(APP.user.getNiceName())) {
            edit_nice_name.setText(APP.user.getNiceName()+"");
        }
        edit_name.setText(APP.user.getRealName());

        if (APP.user.getHeadPicture() != null && !TextUtils.isEmpty(APP.user.getHeadPicture())) {
            Glide.with(this)
                    .load(Config.BASE_PIC_URL + APP.user.getHeadPicture())
                    .error(R.drawable.ic_no_head)
                    .into(imv_header);
        }
        if (!TextUtils.isEmpty(APP.user.getBorn())) {
            String[] split = APP.user.getBorn().split("-");

            tv_both.setText(APP.user.getBorn());
        }
        if (APP.user.getSex() != null) {
            if (APP.user.getSex().equals("1")) {   //  0：女  1： 男
                imv_male.setEnabled(false);
                imv_female.setEnabled(true);
                imv_male.setImageResource(R.drawable.center_userinfo_choose_selected);
                imv_female.setImageResource(R.drawable.center_userinfo_choose_default);
                tv_male.setTextColor(getResources().getColor(R.color.text_orangereg02));
                tv_female.setTextColor(getResources().getColor(R.color.text_black_999));
            } else {
                imv_male.setEnabled(true);
                imv_female.setEnabled(false);
                imv_male.setImageResource(R.drawable.center_userinfo_choose_default);
                imv_female.setImageResource(R.drawable.center_userinfo_choose_selected);
                tv_male.setTextColor(getResources().getColor(R.color.text_black_999));
                tv_female.setTextColor(getResources().getColor(R.color.text_orangereg02));
            }
        } else {
            imv_male.setEnabled(true);
            imv_female.setEnabled(false);
            imv_male.setImageResource(R.drawable.center_userinfo_choose_default);
            imv_female.setImageResource(R.drawable.center_userinfo_choose_selected);
            tv_male.setTextColor(getResources().getColor(R.color.text_black_999));
            tv_female.setTextColor(getResources().getColor(R.color.text_orangereg02));
        }
        tv_address.setText(APP.user.getAddress());
//        mPresenter.getAreaList("0"); // 首先获取省级数据
        mHandler.sendEmptyMessage(MSG_LOAD_DATA);
    }

    @Override
    protected void initListener() {
        tv_header.setOnClickListener(this);
        rl_birthday.setOnClickListener(this);
        imv_header.setOnClickListener(this);
        rl_address.setOnClickListener(this);
        rl_item.setOnClickListener(this);
        imv_male.setOnClickListener(this);
        imv_female.setOnClickListener(this);
        tv_title_left.setOnClickListener(this);
        tv_title_right.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);

        switch (v.getId()) {
            case R.id.tv_title_left:
//                showTipDialog();
                finish();
                break;
            case R.id.tv_title_right:
                if (checkData()) {
                    mPresenter.userInfoModify();
                }
                break;
            case R.id.tv_header:
                CropOptions cropOptions = new CropOptions.Builder()
                        .setAspectX(1)
                        .setAspectY(1)
                        .create();
                Intent intent = new Intent(this, SelectPictureActivity.class);
                intent.putExtra("cropOptions", cropOptions);
                startActivityForResult(intent, 1);
                break;
            case R.id.rl_item:
                CropOptions cropOptions02 = new CropOptions.Builder()
                        .setAspectX(1)
                        .setAspectY(1)
                        .create();
                Intent intent02 = new Intent(this, SelectPictureActivity.class);
                intent02.putExtra("cropOptions", cropOptions02);
                startActivityForResult(intent02, 1);
                break;
            case R.id.imv_header:
               /* Intent picDetail = new Intent(this, BrowsePicActivity.class);
                picDetail.putExtra("picUrl", APP.user.getHeadPicture());
                startActivity(picDetail);*/
                break;
            case R.id.rl_birthday:
                hideSoftInput();  // 动态隐藏软键盘
                if (pvOptions!=null  && pvOptions.isShowing()) {
                    pvOptions.dismiss();
                }
//                mDatePickerDialog.show();
                if (pvTime == null) {
                    //时间选择器
                    pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
                        @Override
                        public void onTimeSelect(Date date, View v) {//选中事件回调
                            tv_both.setText(getTime(date));
                        }
                    })
                            .setType(new boolean[]{true, true, true, false, false, false})// 默认全部显示
                            .setLabel("年", "月", "日", "时", "分", "秒")//默认设置为年月日时分秒
                            .build();
                    pvTime.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
                    pvTime.show();
                } else {
                    pvTime.show();
                }
                break;
            case R.id.rl_address:
                hideSoftInput();  // 动态隐藏软键盘
                if (pvTime!=null && pvTime.isShowing()){
                    pvTime.dismiss();
                }
                if (isLoaded) {
                    if (pvOptions == null) {
                        //条件选择器
                        pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
                            @Override
                            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                                //返回的分别是三个级别的选中位置
                                String tx = options1Items.get(options1).getAreaName() +
                                        options2Items.get(options1).get(option2) +
                                        options3Items.get(options1).get(option2).get(options3);

                                tv_address.setText(tx);
                            }
                        }).build();
                        pvOptions.setPicker(options1Items, options2Items, options3Items);
                        pvOptions.show();
                    } else {
                        pvOptions.show();

                    }
                } else {
                    showToast("解析地区数据有误。");
                }


                break;
            case R.id.imv_female:

                imv_male.setEnabled(true);
                imv_female.setEnabled(false);
                imv_female.setImageResource(R.drawable.center_userinfo_choose_selected);
                imv_male.setImageResource(R.drawable.center_userinfo_choose_default);
                tv_male.setTextColor(getResources().getColor(R.color.text_black_999));
                tv_female.setTextColor(getResources().getColor(R.color.text_orangereg02));
                break;
            case R.id.imv_male:
                imv_female.setEnabled(true);
                imv_male.setEnabled(false);
                imv_male.setImageResource(R.drawable.center_userinfo_choose_selected);
                imv_female.setImageResource(R.drawable.center_userinfo_choose_default);
                tv_male.setTextColor(getResources().getColor(R.color.text_orangereg02));
                tv_female.setTextColor(getResources().getColor(R.color.text_black_999));
                break;

        }

    }

    /**
     * 动态隐藏软键盘
     */
    private void hideSoftInput() {
        InputMethodManager imm =
                (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (imm == null) return;
        View view = getCurrentFocus();
        if (view == null) view = new View(this);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
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

    @Override
    public void setPresenter(UserInfoModifyContract.Presenter presenter) {

    }

    @Override
    public String getUserName() {
        return edit_nice_name.getText().toString();
    }

    @Override
    public String getSex() {
        if (imv_male.isEnabled()) {
            return "0";
        } else {
            return "1";
        }

    }

    @Override
    public String getTel() {      // 获取昵称
        return edit_nice_name.getText().toString();
    }

    @Override
    public String getBoth() { //  获取时间（出生日）
        return tv_both.getText().toString();
    }

    @Override
    public String getAddress() {
        return tv_address.getText().toString();
    }

    @Override
    public String getEmail() {
        return "email";
    }

    @Override
    public void modifySuccess() {
        showToast(getString(R.string.modify_user_info_success));
        APP.user.setRealName(edit_name.getText().toString().trim()); // 真实姓名
        EventBus.getDefault().post(new EventBusUIMessage(Msg.CHANGE_USER_INFO));
        finish();
    }

    @Override
    public void modifyFail(String msg) {
        showToast(msg);
    }

    @Override
    public void showMsg(String msg) {
        showToast(msg);
    }

    @Override
    public void modidyHeaderSuccess(ResUserHeadPicModify result) {
        Glide.with(UserInfoModifyActivity.this).load(patch).into(imv_header);
        APP.user.setHeadPicture(result.getUserHeadPicture());
        EventBus.getDefault().post(new EventBusUIMessage(Msg.CHANGE_USER_INFO));
        showMsg(getString(R.string.modify_header_pic_success));

    }

    @Override
    public String getNiceName() {
        return edit_nice_name.getText().toString();
    }

    @Override
    public String getRealName() {
        return edit_name.getText().toString().trim();
    }

    @Override
    public void getAreaListSuccess(ResAreaBean resAreaBean) {
        Gson gson = new Gson();
        String content = gson.toJson(resAreaBean);
        Log.i("====tag==", content);


     /*   for (int l = 0; l < resAreaBean.getAreaList().size(); l++) {
            if (resAreaBean.getAreaList().get(l).getChildList().size() > 0) {
                options1Items.add(resAreaBean.getAreaList().get(l));
            }
        }
//        options1Items = resAreaBean.getAreaList();
        for (int i = 0; i < options1Items.size(); i++) {  //遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            if (resAreaBean.getAreaList().get(i).getChildList() == null
                    || resAreaBean.getAreaList().get(i).getChildList().size() == 0) {
//                CityList.add("");
            } else {
                for (int j = 0; j < resAreaBean.getAreaList().get(i).getChildList().size(); j++) {  //遍历该省份的所有城市
                    String CityName = resAreaBean.getAreaList().get(i).getChildList().get(j).getAreaName();
                    CityList.add(CityName);//添加城市

                    ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表
                    //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                    if (resAreaBean.getAreaList().get(i).getChildList().get(j).getChildList02() == null
                            || resAreaBean.getAreaList().get(i).getChildList().get(j).getChildList02().size() == 0) {
                        City_AreaList.add("");
                    } else {
                        for (int d = 0; d < resAreaBean.getAreaList().get(i).getChildList().get(j).getChildList02().size(); d++) {//该城市对应地区所有数据
                            String AreaName = resAreaBean.getAreaList().get(i).getChildList().get(j).getChildList02().get(d).getAreaName();
                            City_AreaList.add(AreaName);//添加该城市所有地区数据
                        }
                    }
                    Province_AreaList.add(City_AreaList);//添加该省所有地区数据

                }
            }
            *//**
         * 添加城市数据
         *//*
            options2Items.add(CityList);

            *//**
         * 添加地区数据
         *//*
            options3Items.add(Province_AreaList);

        }*/


    }

    @Override
    public void getAreaListFail(String msg) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (RESULT_OK == resultCode) {
            patch = data.getStringExtra("patch");
            if (TextUtils.isEmpty(patch)) {
                return;
            }
            String s = Bitmap2StringUtil.bitmapToString(patch); // 直接util转String之后直接传给后台
            isForeground = true;
            mPresenter.userHeadPicModify(s);
            Glide.with(UserInfoModifyActivity.this).load(patch).into(imv_header);

        }
    }

    /**
     * @Desc: 校验数据
     * @author:liangshan
     */
    public boolean checkData() {
        if (TextUtils.isEmpty(edit_nice_name.getText())) {
            showMsg("昵称不能为空");
            return false;
        }
        if (!imv_male.isEnabled() && !imv_female.isEnabled()) {
            showMsg("请选择性别");
            return false;
        }
        if (TextUtils.isEmpty(edit_name.getText())) {
            showMsg("真实姓名不能为空");
            return false;
        }
        return true;
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }


    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_LOAD_DATA:
                    if (thread == null) {//如果已创建就不再重新创建子线程了
                        thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // 写子线程中的操作,解析省市区数据
                                initJsonData();
                            }
                        });
                        thread.start();
                    }
                    break;
                case MSG_LOAD_SUCCESS:
                    isLoaded = true;
                    break;
                case MSG_LOAD_FAILED:
                    isLoaded = false;
                    showToast("解析地区数据有误。");
                    break;
            }
        }
    };

    private void initJsonData() {//解析数据

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        ResAreaBean resAreaBean = new ResAreaBean();
        try {
            String JsonData = getJson(this, "province.json");//获取assets目录下的json文件数据
            resAreaBean = new Gson().fromJson(JsonData, ResAreaBean.class);//用Gson 转成实体
        } catch (Exception e) {
            e.printStackTrace();
            mHandler.sendEmptyMessage(MSG_LOAD_FAILED);
            return;
        }
        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        for (int l = 0; l < resAreaBean.getAreaList().size(); l++) {
            if (resAreaBean.getAreaList().get(l).getChildList().size() > 0) {
                options1Items.add(resAreaBean.getAreaList().get(l));
            }
        }
//        options1Items = resAreaBean.getAreaList();
        for (int i = 0; i < options1Items.size(); i++) {  //遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            if (resAreaBean.getAreaList().get(i).getChildList() == null
                    || resAreaBean.getAreaList().get(i).getChildList().size() == 0) {
//                CityList.add("");
            } else {
                for (int j = 0; j < resAreaBean.getAreaList().get(i).getChildList().size(); j++) {  //遍历该省份的所有城市
                    String CityName = resAreaBean.getAreaList().get(i).getChildList().get(j).getAreaName();
                    CityList.add(CityName);//添加城市

                    ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表
                    //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                    if (resAreaBean.getAreaList().get(i).getChildList().get(j).getChildList02() == null
                            || resAreaBean.getAreaList().get(i).getChildList().get(j).getChildList02().size() == 0) {
                        City_AreaList.add("");
                    } else {
                        for (int d = 0; d < resAreaBean.getAreaList().get(i).getChildList().get(j).getChildList02().size(); d++) {//该城市对应地区所有数据
                            String AreaName = resAreaBean.getAreaList().get(i).getChildList().get(j).getChildList02().get(d).getAreaName();
                            City_AreaList.add(AreaName);//添加该城市所有地区数据
                        }
                    }
                    Province_AreaList.add(City_AreaList);//添加该省所有地区数据

                }
            }
            /**
             * 添加城市数据
             */
            options2Items.add(CityList);

            /**
             * 添加地区数据
             */
            options3Items.add(Province_AreaList);

        }
        mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS);

    }

    public String getJson(Context context, String fileName) {

        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }


}
