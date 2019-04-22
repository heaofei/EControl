package com.mxkj.econtrol.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.mxkj.econtrol.R;
import com.mxkj.econtrol.utils.ScreenUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liangshan on 2017/6/12.
 *
 * @Description：
 */

public class LinearLayoutList<T> extends LinearLayout {
    public static final int TAG_KEY_DATA = 1234567890;
    private LayoutInflater inflater;
    private List<View> views;
    private int itemLayoutId;
    private List<T> datas;
    @SuppressWarnings({"rawtypes", "unused"})
    private IConvert iConvert;
    private OnItemClickListener itemClickListener;
    private OnItemLongClickListener itemLongClickListener;
    private int splitColor = 0;// 分割线颜色资源Id;

    /**
     * @return the splitColor
     */
    public int getSplitColor() {
        return splitColor;
    }

    /**
     * @param splitColor the splitColor to set
     */
    public void setSplitColor(int splitColor) {
        this.splitColor = splitColor;
    }

    public LinearLayoutList(Context context) {
        super(context);
    }

    public LinearLayoutList(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public LinearLayoutList(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }

    /**
     * @param datas
     * @param itemLayoutId
     * @param iConvert     void
     * @author：liaoxy
     * @Description: 设置数据集
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void setDataList(List<T> datas, int itemLayoutId, IConvert iConvert) {
        this.inflater = LayoutInflater.from(getContext());
        this.datas = datas;
        this.itemLayoutId = itemLayoutId;
        this.iConvert = iConvert;
        this.views = new ArrayList<View>();
        this.removeAllViews();
        // 分隔线属性配置
        LayoutParams splitParam = new LayoutParams(LayoutParams.MATCH_PARENT,
                ScreenUtil.dip2px(2));

        // 循环取数据
        for (int i = 0; i < datas.size(); i++) {
            T data = datas.get(i);
            View vChild = inflater.inflate(itemLayoutId, null);
            vChild.setTag(i);
            vChild.setTag(TAG_KEY_DATA, data);
            iConvert.convert(vChild, data);

            if (itemClickListener != null) {
                vChild.setOnClickListener(clickListener);
            }
            if (itemLongClickListener != null) {
                vChild.setOnLongClickListener(clickLongListener);
            }

            addView(vChild);
            views.add(vChild);

            if (i != datas.size() - 1) {
                // 最后一行不添加分割线
                // 添加分隔线
                View split = new View(getContext());
                if (splitColor == 0) {
                    split.setBackgroundColor(getResources().getColor(R.color.com_splitline02));
                } else {
                    split.setBackgroundColor(getResources().getColor(splitColor));
                }
                addView(split, splitParam);
            }

        }
    }

    /**
     * @author：liaoxy
     * @Description: 刷新列表并替换数据 void
     */
    public void refresh(List<T> datas) {
        if (datas != null) {
            setDataList(datas, itemLayoutId, iConvert);
        }
    }

    /**
     * @author：liaoxy
     * @Description: 刷新列表 void
     */
    public void refresh() {
        if (datas != null) {
            setDataList(datas, itemLayoutId, iConvert);
        }
    }

    // 每个Item单击时的监听变量
    private OnClickListener clickListener = new OnClickListener() {

        @Override
        public void onClick(View arg0) {
            itemClickListener.OnItemClick(arg0, (Integer) arg0.getTag());
        }
    };

    // 每个Item长按时的监听变量
    private OnLongClickListener clickLongListener = new OnLongClickListener() {

        @Override
        public boolean onLongClick(View arg0) {
            itemLongClickListener.OnItemLongClick(arg0, (Integer) arg0.getTag());
            return true;
        }
    };

    /**
     * @return int
     * @author：liaoxy
     * @Description: 获取数据总数量
     */
    public int getCount() {
        return datas.size();
    }

    /**
     * @param position
     * @return T
     * @author：liaoxy
     * @Description: 获取某一行的数据
     */
    public T getItemData(int position) {
        return datas.get(position);
    }

    /**
     * @param position
     * @return View
     * @author：liaoxy
     * @Description: 获取某一行的view
     */
    public View getItem(int position) {
        return views.get(position);
    }

    /**
     * @param position
     * @param id
     * @return View
     * @author：liaoxy
     * @Description: 获取某一行中布局中的子view
     */
    public View getItemView(int position, int id) {
        return views.get(position).findViewById(id);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        itemClickListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        itemLongClickListener = listener;
    }

    /**
     * @param <T>
     * @author liaoxy
     * @ClassName: IConvert
     * @Description: 提供一个接口，供外部实现每一行数据的具体设置
     * @date 2015-8-1 上午11:23:55
     */
    public interface IConvert<T> {
        void convert(View vChild, T data);
    }

    /**
     * @author liaoxy
     * @ClassName: OnItemClickListener
     * @Description: 提供外部单击的接口
     * @date 2015-8-1 上午11:23:33
     */
    public interface OnItemClickListener {
        void OnItemClick(View vChild, int position);
    }

    /**
     * @author liaoxy
     * @ClassName: OnItemLongClickListener
     * @Description: 提供外部长按的接口
     * @date 2015-8-1 上午11:23:13
     */
    public interface OnItemLongClickListener {
        void OnItemLongClick(View vChild, int position);
    }
}
