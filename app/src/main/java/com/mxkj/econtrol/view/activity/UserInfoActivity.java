package com.mxkj.econtrol.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.mxkj.econtrol.R;

public class UserInfoActivity extends AppCompatActivity {

    private PopupWindow tip;
    private View inflate;
    private Button mbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inflate = getLayoutInflater().inflate(R.layout.activity_user_info, null);
        setContentView(R.layout.activity_user_info);
        View view = getLayoutInflater().inflate(R.layout.com_dialog_loading, null);
        tip = new PopupWindow(view, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        tip.setContentView(view);
//        tip.setBackgroundDrawable(null);
        mbutton = (Button) findViewById(R.id.btn);
        mbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tip.showAtLocation(inflate, Gravity.CENTER, 0, 0);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

}
