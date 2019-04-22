package com.mxkj.econtrol.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.mxkj.econtrol.R;
import com.mxkj.econtrol.utils.ScreenUtil;

/**
 * Created by liangshan on 2017/3/24.
 *
 * @Destription:
 */

public class LoadingDialog  {

    private Context context;
    private Dialog dialog;
    public LoadingDialog(Context context) {
        this.context=context;
    }

    public void showProgressDialog(String content) {
        if (dialog==null){
        dialog =  new Dialog(context, R.style.LoadDialog);
        TextView tv_content;
        View view =null;
        if(content==null){
            content="";
        }
        view = LayoutInflater.from(context).inflate(R.layout.com_dialog_loading,null);
        tv_content = (TextView) view.findViewById(R.id.tv_content);
        tv_content.setText(content);
        dialog.setContentView(view, new ViewGroup.LayoutParams(/*(int) (TheApp.screenWidth * 0.8),// 设置为屏幕宽度的0.9*/
                android.widget.RelativeLayout.LayoutParams.WRAP_CONTENT,
                android.widget.RelativeLayout.LayoutParams.WRAP_CONTENT));//设置为屏幕高度的0.8
        // 设置显示宽高
        dialog.show();
        // 设置点击外围解散
        dialog.setCanceledOnTouchOutside(false);
        }else {
            dialog.show();
        }

    }

    public void dialogDismiss() {
        if (dialog!=null && dialog.isShowing()){
            dialog.dismiss();
        }
    }


}

