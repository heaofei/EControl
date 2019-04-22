package com.mxkj.econtrol.utils;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mxkj.econtrol.R;
import com.mxkj.econtrol.app.APP;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${  chenjun  } on 2017/7/31.
 */

public abstract class ChoiceWeekDialogUtil {

    private Dialog dialog;
    private Context context;
    private List<Boolean> weekList = new ArrayList<>();

    public ChoiceWeekDialogUtil(Context context) {
        this.context = context;
    }

    public void showChoiceWeekDialog(List<Boolean> oldWeeKList) {
        if (oldWeeKList == null || oldWeeKList.size()==0) {
            weekList.add(false);
            weekList.add(false);
            weekList.add(false);
            weekList.add(false);
            weekList.add(false);
            weekList.add(false);
            weekList.add(false);
        } else {
            weekList.addAll(oldWeeKList);
        }


        if (dialog == null) {
            dialog = new Dialog(context, R.style.Alert_Dialog_Style);
            RelativeLayout rl_Mon;
            final ImageView iv_Mon;
            RelativeLayout rl_Tue;
            final ImageView iv_Tue;
            RelativeLayout rl_Wed;
            final ImageView iv_Wed;
            RelativeLayout rl_Thu;
            final ImageView iv_Thu;
            RelativeLayout rl_Fri;
            final ImageView iv_Fri;
            RelativeLayout rl_Sat;
            final ImageView iv_Sat;
            RelativeLayout rl_Sun;
            final ImageView iv_Sun;
            Button tv_confirm;

            View view = null;

            view = LayoutInflater.from(context).inflate(R.layout.dialog_choice_week, null);
            tv_confirm = (Button) view.findViewById(R.id.tv_confirm);
            rl_Mon = (RelativeLayout) view.findViewById(R.id.rl_Mon);
            rl_Tue = (RelativeLayout) view.findViewById(R.id.rl_Tue);
            rl_Wed = (RelativeLayout) view.findViewById(R.id.rl_Wed);
            rl_Thu = (RelativeLayout) view.findViewById(R.id.rl_Thu);
            rl_Fri = (RelativeLayout) view.findViewById(R.id.rl_Fri);
            rl_Sat = (RelativeLayout) view.findViewById(R.id.rl_Sat);
            rl_Sun = (RelativeLayout) view.findViewById(R.id.rl_Sun);

            iv_Mon = (ImageView) view.findViewById(R.id.iv_Mon);
            iv_Tue = (ImageView) view.findViewById(R.id.iv_Tue);
            iv_Wed = (ImageView) view.findViewById(R.id.iv_Wed);
            iv_Thu = (ImageView) view.findViewById(R.id.iv_Thu);
            iv_Fri = (ImageView) view.findViewById(R.id.iv_Fri);
            iv_Sat = (ImageView) view.findViewById(R.id.iv_Sat);
            iv_Sun = (ImageView) view.findViewById(R.id.iv_Sun);


            for (int i = 0; i < weekList.size(); i++) {
                if (i == 0 && weekList.get(i)) {
                    iv_Mon.setImageResource(R.drawable.home_edit_choose_selected32);
                }
                if (i == 1 && weekList.get(i)) {
                    iv_Tue.setImageResource(R.drawable.home_edit_choose_selected32);
                }
                if (i == 2 && weekList.get(i)) {
                    iv_Wed.setImageResource(R.drawable.home_edit_choose_selected32);
                }
                if (i == 3 && weekList.get(i)) {
                    iv_Thu.setImageResource(R.drawable.home_edit_choose_selected32);
                }
                if (i == 4 && weekList.get(i)) {
                    iv_Fri.setImageResource(R.drawable.home_edit_choose_selected32);
                }
                if (i == 5 && weekList.get(i)) {
                    iv_Sat.setImageResource(R.drawable.home_edit_choose_selected32);
                }
                if (i == 6 && weekList.get(i)) {
                    iv_Sun.setImageResource(R.drawable.home_edit_choose_selected32);
                }

            }
            rl_Mon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (weekList.get(0)) {
                        weekList.set(0, false);
                        iv_Mon.setImageResource(R.drawable.home_edit_choose_default32);
                    } else {
                        weekList.set(0, true);
                        iv_Mon.setImageResource(R.drawable.home_edit_choose_selected32);
                    }
                }
            });

            rl_Tue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (weekList.get(1)) {
                        weekList.set(1, false);
                        iv_Tue.setImageResource(R.drawable.home_edit_choose_default32);
                    } else {
                        weekList.set(1, true);
                        iv_Tue.setImageResource(R.drawable.home_edit_choose_selected32);
                    }
                }
            });

            rl_Wed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (weekList.get(2)) {
                        weekList.set(2, false);
                        iv_Wed.setImageResource(R.drawable.home_edit_choose_default32);
                    } else {
                        weekList.set(2, true);
                        iv_Wed.setImageResource(R.drawable.home_edit_choose_selected32);
                    }
                }
            });

            rl_Thu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (weekList.get(3)) {
                        weekList.set(3, false);
                        iv_Thu.setImageResource(R.drawable.home_edit_choose_default32);
                    } else {
                        weekList.set(3, true);
                        iv_Thu.setImageResource(R.drawable.home_edit_choose_selected32);
                    }
                }
            });
            rl_Fri.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (weekList.get(4)) {
                        weekList.set(4, false);
                        iv_Fri.setImageResource(R.drawable.home_edit_choose_default32);
                    } else {
                        weekList.set(4, true);
                        iv_Fri.setImageResource(R.drawable.home_edit_choose_selected32);
                    }
                }
            });
            rl_Sat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (weekList.get(5)) {
                        weekList.set(5, false);
                        iv_Sat.setImageResource(R.drawable.home_edit_choose_default32);
                    } else {
                        weekList.set(5, true);
                        iv_Sat.setImageResource(R.drawable.home_edit_choose_selected32);
                    }
                }
            });
            rl_Sun.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (weekList.get(6)) {
                        weekList.set(6, false);
                        iv_Sun.setImageResource(R.drawable.home_edit_choose_default32);
                    } else {
                        weekList.set(6, true);
                        iv_Sun.setImageResource(R.drawable.home_edit_choose_selected32);
                    }
                }
            });


            tv_confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    confirm(weekList);
                    dialog.dismiss();
                }
            });
            dialog.setContentView(view, new ViewGroup.LayoutParams(/*(int) (TheApp.screenWidth * 0.8),// 设置为屏幕宽度的0.9*/
                    (int) (APP.screenWidth * 0.8),
                    android.widget.RelativeLayout.LayoutParams.WRAP_CONTENT));//设置为屏幕高度的0.8
            // 设置显示宽高
            // 设置点击外围解散
            dialog.setCanceledOnTouchOutside(true);
            dialog.show();
        }/* else {
            dialog.show();
        }*/
    }

    public abstract void confirm(List<Boolean> weekList);

}
