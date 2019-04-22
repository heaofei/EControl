package com.mxkj.econtrol.ui.activity;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mxkj.econtrol.R;
import com.mxkj.econtrol.base.BaseActivity;
import com.mxkj.econtrol.bean.response.ResGetPatternList;
import com.mxkj.econtrol.bean.response.ResGetRoomList;
import com.mxkj.econtrol.bean.response.SmartPart;
import com.mxkj.econtrol.widget.MyLinearLayout;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/****
 *
 * 体验账号
 *
 */
public class ExperienceActivity extends BaseActivity {


    // 初始化title
    private TextView tv_title_left;
    private TextView tv_title_content;
    private ImageView iv_more;
    private TextView tv_title_right;

    private RecyclerView recycleview_scene;
    private RecyclerView recycleview_device;
    private String roomName = ""; // 默认房间名
    private MyLinearLayout ll_status;
    private Context context;

    private List<ResGetPatternList.ListBeaner> scenelist = new ArrayList<ResGetPatternList.ListBeaner>();

    private List<List<SmartPart>> roomPart = new ArrayList<>(); // 全部房间设备集合

    private ResGetRoomList resGetRoomList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experience);


    }

    @Override
    protected void initView() {

        tv_title_left = (TextView) findViewById(R.id.tv_title_left);
        tv_title_content = (TextView) findViewById(R.id.tv_title_content);
        iv_more = (ImageView) findViewById(R.id.iv_more);
        tv_title_right = (TextView) findViewById(R.id.tv_title_right);
        tv_title_right.setText("设置");
        recycleview_scene = (RecyclerView) findViewById(R.id.recycleview_scene);
        ll_status = (MyLinearLayout) findViewById(R.id.ll_status);
        recycleview_device = (RecyclerView) findViewById(R.id.recycleview_device);
        tv_title_content.setText(roomName);
        
        


    }

    @Override
    protected void initData() {
        

        try {
            String JsonData = getJson(this, "device_experience.json");//获取assets目录下的json文件数据
            resGetRoomList = new Gson().fromJson(JsonData, ResGetRoomList.class);//用Gson 转成实体
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        
    }

    @Override
    protected void initListener() {

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
