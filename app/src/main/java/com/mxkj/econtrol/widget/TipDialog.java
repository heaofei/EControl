package com.mxkj.econtrol.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.mxkj.econtrol.R;
import com.mxkj.econtrol.utils.ScreenUtil;


/**
 * @Description: 提示窗口
 */
public class TipDialog extends Dialog {

    private TextView tvTitle, tvContent, tvCancel, tvSure;
    private View contentView;

    private View.OnClickListener mListener;
    private boolean isDimiss = true;// 点击确认按钮后是否隐藏；

    public TipDialog(Context context, View.OnClickListener listener) {
        super(context, R.style.Alert_Dialog_Style);
        mListener = listener;
        init(context);
        isDimiss = true;
    }

    // 自定义布局
    public TipDialog(Context context, int id, View.OnClickListener listener) {
        super(context, R.style.Alert_Dialog_Style);
        contentView = View.inflate(context, id, null);
        mListener = listener;
        init(context);
    }

    public TipDialog(Context context, View contentView, View.OnClickListener listener) {
        super(context, R.style.Alert_Dialog_Style);
        this.contentView = contentView;
        mListener = listener;
        init(context);
    }

    private void init(Context context) {
        setCanceledOnTouchOutside(false);
        if (contentView == null) {
            // 未指定布局使用默认布局
            contentView = View.inflate(context, R.layout.layout_dialog_tip, null);
        }

        setContentView(contentView);
        init(contentView);
    }

    private void init(View view) {
        tvContent = (TextView) view.findViewById(R.id.tvContent);
        tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        tvCancel = (TextView) view.findViewById(R.id.tvCancel);
        tvSure = (TextView) view.findViewById(R.id.tvSure);
        if (tvSure != null) {
            tvSure.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    if (mListener != null) {
                        mListener.onClick(arg0);
                    }
                    if (isDimiss)
                        dismiss();
                }
            });
        }
        if (tvCancel != null) {
            tvCancel.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    // TODO Auto-generated method stub
                    dismiss();
                }
            });
        }
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        params.alpha = 1.0f;
        params.width = (int) (ScreenUtil.getScreenWidth() * 0.8);
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;// ScreenUtil.dip2px(150);//120改为150
        window.setAttributes(params);
    }

    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    public void setContent(String content) {
        tvContent.setText(content);
    }

    public void setCancelStr(String cancel) {
        tvCancel.setText(cancel);
    }

    public void setSureStr(String sure) {
        tvSure.setText(sure);
    }

    public void setCancelVisible(int visible) {
        tvCancel.setVisibility(visible);
    }

    public void setCancelOnClickListener(final View.OnClickListener listener) {
        tvCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                listener.onClick(arg0);
                dismiss();
            }
        });

    }

    public void setTag(Object obj) {
        tvSure.setTag(obj);
    }

    public View getContentView() {
        return contentView;
    }

    /**
     * @return the isDimiss
     */
    public boolean isDimiss() {
        return isDimiss;
    }

    /**
     * @param isDimiss the isDimiss to set
     */
    public void setDimiss(boolean isDimiss) {
        this.isDimiss = isDimiss;
    }
}
