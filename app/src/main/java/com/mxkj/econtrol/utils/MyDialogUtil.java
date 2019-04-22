package com.mxkj.econtrol.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mxkj.econtrol.R;
import com.mxkj.econtrol.app.APP;
import com.mxkj.econtrol.utils.gateway.ContainsEmojiEditText;


/**
 * Created by ${  chenjun  } on 2017/7/31.
 */

public abstract class MyDialogUtil {

    private Dialog dialog;
    private Context context;
    private showMainSceneMoreDialogOnClickInterface showMainSceneMoreDialogOnClickInterface;
    private showInputDialogOnClickInterface showInputDialogOnClickInterface;

    public MyDialogUtil(Context context) {
        this.context = context;
    }

    /****
     * 取消，确定
     **/
    public void showTipDialog(String title, String content) {
        dialog = new Dialog(context, R.style.Alert_Dialog_Style);
        TextView tv_title;
        TextView tv_content;
        TextView tv_confirm = null;
        TextView tv_cancel;
        View view = null;

        view = LayoutInflater.from(context).inflate(R.layout.dialog_sure_again, null);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_content = (TextView) view.findViewById(R.id.tv_content);
        tv_confirm = (TextView) view.findViewById(R.id.tv_confirm);
        tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
        if (title == null) {
            title = "";
        }
        if (content == null || TextUtils.isEmpty(content)) {
            content = "";
            tv_content.setVisibility(View.GONE);
        }
        tv_title.setText(title);
        tv_content.setText(content);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                dialog.dismiss();
            }
        });
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirm();
                dialog.dismiss();
            }
        });
        dialog.setContentView(view, new ViewGroup.LayoutParams(/*(int) (TheApp.screenWidth * 0.8),// 设置为屏幕宽度的0.9*/
                (int) (APP.screenWidth * 0.7),
                android.widget.RelativeLayout.LayoutParams.WRAP_CONTENT));//设置为屏幕高度的0.8
        // 设置显示宽高
        dialog.show();
        // 设置点击外围解散
        dialog.setCanceledOnTouchOutside(true);

    }

    /****
     * 提示完善资料对话框
     **/
    public void showPerfectTipDialog(String title, String content, String cancel, String confirm) {
        dialog = new Dialog(context, R.style.Alert_Dialog_Style);
        TextView tv_title;
        TextView tv_content;
        TextView tv_confirm = null;
        TextView tv_cancel;
        View view = null;

        view = LayoutInflater.from(context).inflate(R.layout.dialog_sure_again, null);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_content = (TextView) view.findViewById(R.id.tv_content);
        tv_confirm = (TextView) view.findViewById(R.id.tv_confirm);
        tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
        if (title == null) {
            title = "";
        }
        if (content == null || TextUtils.isEmpty(content)) {
            content = "";
            tv_content.setVisibility(View.GONE);
        }
        if (cancel != null && !TextUtils.isEmpty(cancel)) {
            tv_cancel.setText(cancel);
        }
        if (confirm != null && !TextUtils.isEmpty(confirm)) {
            tv_confirm.setText(confirm);
        }
        tv_title.setText(title);
        tv_content.setText(content);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                dialog.dismiss();
            }
        });
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirm();
                dialog.dismiss();
            }
        });
        dialog.setContentView(view, new ViewGroup.LayoutParams(/*(int) (TheApp.screenWidth * 0.8),// 设置为屏幕宽度的0.9*/
                (int) (APP.screenWidth * 0.7),
                android.widget.RelativeLayout.LayoutParams.WRAP_CONTENT));//设置为屏幕高度的0.8
        // 设置显示宽高
        dialog.show();
        // 设置点击外围解散
        dialog.setCanceledOnTouchOutside(false);

    }

    /****
     * 确定提示框
     ***/
    public void showConfirmTipDialog(String title, String content) {
        dialog = new Dialog(context, R.style.Alert_Dialog_Style);
        TextView tv_title;
        TextView tv_content;
        TextView tv_confirm = null;
        View view = null;
        if (title == null) {
            title = "";
        }
        if (content == null) {
            content = "";
        }
        view = LayoutInflater.from(context).inflate(R.layout.dialog_confirm_again, null);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_content = (TextView) view.findViewById(R.id.tv_content);
        tv_confirm = (TextView) view.findViewById(R.id.tv_confirm);
        tv_title.setText(title);
        tv_content.setText(content);
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirm();
                dialog.dismiss();
            }
        });

        dialog.setContentView(view, new ViewGroup.LayoutParams(/*(int) (TheApp.screenWidth * 0.8),// 设置为屏幕宽度的0.9*/
                (int) (APP.screenWidth * 0.7),
                android.widget.RelativeLayout.LayoutParams.WRAP_CONTENT));//设置为屏幕高度的0.8
        // 设置显示宽高
        dialog.show();
        // 设置点击外围解散
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);

    }

    /****
     * 编辑，取消对话框
     */
    public void showEditDialog() {
        View view = View.inflate(context, R.layout.dialog_choose_edit02, null);
        dialog = new Dialog(context, R.style.Alert_Dialog_Style);
        dialog.setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        Button bt_cancel;
        Button bt_edit;
        bt_edit = (Button) view.findViewById(R.id.bt_edit);
        bt_cancel = (Button) view.findViewById(R.id.bt_cancel);
        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                dialog.dismiss();
            }
        });
        bt_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirm();
                dialog.dismiss();
            }
        });
        Window window = dialog.getWindow();
        // 设置显示动画
        window.setWindowAnimations(R.style.main_menu_animstyle);
        WindowManager.LayoutParams wl = window.getAttributes();
        window.setGravity(Gravity.BOTTOM);
        wl.x = 0;
        wl.y = ViewGroup.LayoutParams.MATCH_PARENT;
        // 以下这两句是为了保证按钮可以水平满屏
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;

        // 设置显示位置
        dialog.onWindowAttributesChanged(wl);
        // 设置点击外围解散
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    /****
     * 输入编辑对话框
     */
    public void showInputDialog(String title, String content, String edContent) {
        if (title == null) {
            title = "";
        }
        if (content == null) {
            content = "";
        }
        View view = View.inflate(context, R.layout.dialog_sure_input, null);
        dialog = new Dialog(context, R.style.Alert_Dialog_Style);
        dialog.setContentView(view,
                new ViewGroup.LayoutParams((int) (APP.screenWidth * 0.7), ViewGroup.LayoutParams.WRAP_CONTENT));
        Button bt_cancel;
        Button bt_confirm;
        final EditText ed_content;
        TextView tv_title;
        TextView tv_content;

        bt_confirm = (Button) view.findViewById(R.id.bt_confirm);
        bt_cancel = (Button) view.findViewById(R.id.bt_cancel);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_content = (TextView) view.findViewById(R.id.tv_content);
        ed_content = (EditText) view.findViewById(R.id.ed_content);
        if (null != edContent && !edContent.equals("")) {
            ed_content.setText(edContent);
        }
        ed_content.setFocusable(true);
        ed_content.setFocusableInTouchMode(true);
        ed_content.requestFocus();
        ed_content.requestFocusFromTouch();

       /* InputMethodManager inputManager = (InputMethodManager)ed_content.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(ed_content, 0);*/


        InputMethodManager imm = (InputMethodManager) ed_content.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

        tv_title.setText(title);
        tv_content.setText(content);
        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                dialog.dismiss();
            }
        });
        bt_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(ed_content.getText().toString().trim())) {
                    Toast.makeText(context, "请输入内容", Toast.LENGTH_SHORT).show();
                    return;
                }
                confirm();
                dialog.dismiss();
                showInputDialogOnClickInterface.doEdit(ed_content.getText().toString().trim());
            }
        });
        // 设置点击外围解散
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    /***
     * 主页： 编辑，新增，删除 弹窗
     **/
    public void showMainSceneMoreDialog() {
        dialog = new Dialog(context, R.style.Alert_Dialog_Style);
        View view = null;
        view = LayoutInflater.from(context).inflate(R.layout.dialog_choose_edit, null);
        dialog.setContentView(view, new ViewGroup.LayoutParams(/*(int) (TheApp.screenWidth * 0.8),// 设置为屏幕宽度的0.9*/
                ViewGroup.LayoutParams.MATCH_PARENT,
                android.widget.RelativeLayout.LayoutParams.WRAP_CONTENT));//设置为屏幕高度的0.8
        // 设置显示宽高
        Button bt_edit;
        Button bt_new;
        Button bt_delete;
        Button bt_cancel;

        bt_edit = (Button) view.findViewById(R.id.bt_edit);
        bt_new = (Button) view.findViewById(R.id.bt_new);
        bt_delete = (Button) view.findViewById(R.id.bt_delete);
        bt_cancel = (Button) view.findViewById(R.id.bt_cancel);

        bt_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                showMainSceneMoreDialogOnClickInterface.doEdit();
            }
        });
        bt_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                showMainSceneMoreDialogOnClickInterface.doNew();
            }
        });
        bt_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                showMainSceneMoreDialogOnClickInterface.doDelete();
            }
        });
        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                dialog.dismiss();
            }
        });
        Window window = dialog.getWindow();
        // 设置显示动画
        window.setWindowAnimations(R.style.main_menu_animstyle);
        WindowManager.LayoutParams wl = window.getAttributes();
        window.setGravity(Gravity.BOTTOM);
        wl.x = 0;
        wl.y = ViewGroup.LayoutParams.MATCH_PARENT;
        // 以下这两句是为了保证按钮可以水平满屏
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        dialog.show();
        // 设置点击外围解散
        dialog.setCanceledOnTouchOutside(true);
    }


    /****
     * 更新提示diallog
     ***/
    public void showUpdateTipDialog(String appVersionName, String content, final String appVersionState) {
        dialog = new Dialog(context, R.style.Alert_Dialog_Style);
        TextView tvc_version;
        TextView tv_content;
        TextView tv_update = null;
        ImageView iv_close;
        View view = null;
        if (appVersionName == null) {
            appVersionName = "";
        }
        if (content == null) {
            content = "";
        }
        view = LayoutInflater.from(context).inflate(R.layout.layou_dialog_update, null);
        tvc_version = (TextView) view.findViewById(R.id.tvc_version);
        tv_content = (TextView) view.findViewById(R.id.tv_content);
        tv_content.setMovementMethod(ScrollingMovementMethod.getInstance());
        tv_update = (TextView) view.findViewById(R.id.tv_update);
        iv_close = (ImageView) view.findViewById(R.id.iv_close);
        tv_content.setText(content);
        tvc_version.setText(appVersionName);
        if (appVersionState != null && appVersionState.equals("2")) { // 2是强制更新，所以取消按钮隐藏
            iv_close.setVisibility(View.INVISIBLE);
        }
        tv_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirm();
                dialog.dismiss();
            }
        });
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (appVersionState != null && !appVersionState.equals("2")) {
                    dialog.dismiss();
                }
            }
        });
        dialog.setContentView(view, new ViewGroup.LayoutParams(/*(int) (TheApp.screenWidth * 0.8),// 设置为屏幕宽度的0.9*/
                APP.screenWidth,
                APP.screenHeight));//设置为屏幕高度的0.8
        dialog.setCancelable(false);// 返回键不取消dialog显示
        // 设置显示宽高
        dialog.show();
        // 设置点击外围解散
        if (appVersionState != null && !appVersionState.equals("2")) {
            dialog.setCanceledOnTouchOutside(true);
        } else {
            dialog.setCanceledOnTouchOutside(false);
        }
    }


    /****
     * 门锁警报提示框
     ***/

    public void showLockAlertTipDialog(String title, String confirmText) {

        if (dialog == null) {
            dialog = new Dialog(context, R.style.Alert_Dialog_Style);
            View view = null;
            TextView tv_confirm, tv_content, tv_title;

            view = LayoutInflater.from(context).inflate(R.layout.layou_dialog_lock_alert, null);

            tv_title = (TextView) view.findViewById(R.id.tv_title);
            tv_confirm = (TextView) view.findViewById(R.id.tv_confirm);
            tv_content = (TextView) view.findViewById(R.id.tv_content);

            tv_content.setText("门锁已触发警报！！ \n请您立即查看！");
            if (!TextUtils.isEmpty(title)) {
                tv_title.setText(title);
            } else {
                tv_title.setText("提示");
            }
            if (!TextUtils.isEmpty(confirmText)) {
                tv_confirm.setText(confirmText);
            } else {
                tv_confirm.setText("确定");
            }
            tv_confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    confirm();
                    dialog.dismiss();
                }
            });
            dialog.setContentView(view, new ViewGroup.LayoutParams(/*(int) (TheApp.screenWidth * 0.8),// 设置为屏幕宽度的0.9*/
                    APP.screenWidth,
                    APP.screenHeight));//设置为屏幕高度的0.8
            // 设置显示宽高
            dialog.show();
            // 设置点击外围解散
            dialog.setCanceledOnTouchOutside(false);
        } else {
            dialog.show();
        }

    }

    /****
     * 门锁手势密码提示
     ***/
    public void showLockGestureTipDialog(String title, String content, String cancelString, String confirmString) {

        dialog = new Dialog(context, R.style.Alert_Dialog_Style);
        TextView tv_title;
        TextView tv_content;
        TextView tv_cancel;
        TextView tv_confirm;
        View view = null;
        if (title == null) {
            title = "";
        }
        if (content == null) {
            content = "";
        }
        if (cancelString == null) {
            cancelString = "取消";
        }
        if (confirmString == null) {
            confirmString = "确定";
        }
        view = LayoutInflater.from(context).inflate(R.layout.layou_dialog_lock_gesture, null);
        tv_content = (TextView) view.findViewById(R.id.tv_content);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
        tv_confirm = (TextView) view.findViewById(R.id.tv_confirm);

        tv_content.setText(content);
        tv_title.setText(title);
        tv_cancel.setText(cancelString);
        tv_confirm.setText(confirmString);


        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirm();
                dialog.dismiss();
            }
        });
        dialog.setContentView(view, new ViewGroup.LayoutParams(/*(int) (TheApp.screenWidth * 0.8),// 设置为屏幕宽度的0.9*/
                APP.screenWidth,
                APP.screenHeight));//设置为屏幕高度的0.8
        // 设置显示宽高
        dialog.show();
        // 设置点击外围解散
        dialog.setCanceledOnTouchOutside(false);
    }


    /****
     * 评论输入框
     ***/
    public void showInputCommentDialog(String confirmString) {

        dialog = new Dialog(context, R.style.inputDialogStyle);
        final ContainsEmojiEditText ed_content;
        final TextView tv_send_comment;
        View view = null;

        view = LayoutInflater.from(context).inflate(R.layout.include_news_detail_tool_bar02, null);
        ed_content = (ContainsEmojiEditText) view.findViewById(R.id.ed_content);
        tv_send_comment = (TextView) view.findViewById(R.id.tv_send_comment);

        tv_send_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ed_content.getText().toString().trim().length() > 0) {
                    dialog.dismiss();
                    showInputDialogOnClickInterface.doEdit(ed_content.getText().toString().trim());
                } else {
                    AbToastUtil.showToast(context, "输入内容为空");
                }
            }
        });
        ed_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (ed_content.getText().toString().trim().length() > 0) {
                    tv_send_comment.setTextColor(context.getResources().getColor(R.color.text_orangereg02));
                } else {
                    tv_send_comment.setTextColor(context.getResources().getColor(R.color.text_black_c3c3c3));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        // 设置宽度为屏宽, 靠近屏幕底部。
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.BOTTOM; // 紧贴底部
//        lp.alpha = 1; // 0 <范围<=255，0为透明，255表示不透明
        lp.dimAmount = 0.5f;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT; // 宽度持平
        window.setAttributes(lp);
        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

        dialog.setCanceledOnTouchOutside(true); // 外部点击取消

        dialog.setContentView(view, new ViewGroup.LayoutParams(// 设置为屏幕宽度的0.9
                APP.screenWidth,
                android.widget.RelativeLayout.LayoutParams.WRAP_CONTENT));//设置为屏幕高度的0.8
//        dialog.setContentView(view);
        // 设置显示宽高
        dialog.show();
        // 设置点击外围解散
        dialog.setCanceledOnTouchOutside(true);
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int keyCode, KeyEvent keyEvent) {
                if (keyCode == KeyEvent.KEYCODE_BACK && keyEvent.getRepeatCount() == 0)
                    dialog.dismiss();
                return false;
            }
        });


    }


    public void DialogDismiss() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
    public boolean DialogGetShow() {
        if (dialog != null && dialog.isShowing()) {
            return true;
        }else {
            return false;
        }
    }

    public abstract void confirm();


    public void setSceneMoreDialogClicklistener(showMainSceneMoreDialogOnClickInterface clickListenerInterface) {
        this.showMainSceneMoreDialogOnClickInterface = clickListenerInterface;
    }

    public interface showMainSceneMoreDialogOnClickInterface {
        void doEdit();

        void doNew();

        void doDelete();
    }

    public void setInputDialogClicklistener(showInputDialogOnClickInterface showInputDialogOnClickInterface) {
        this.showInputDialogOnClickInterface = showInputDialogOnClickInterface;
    }

    public interface showInputDialogOnClickInterface {
        void doEdit(String content);
    }


}
