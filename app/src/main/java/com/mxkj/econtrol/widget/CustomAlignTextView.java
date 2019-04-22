package com.mxkj.econtrol.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomAlignTextView extends AppCompatTextView {

    private final String namespace = "rong.android.TextView";
    private String text;
    private float textSize;
    private int paddingLeft;
    private int paddingRight;
    private int paddingTop;
    private int paddingBottom;
    private float marginLeft;
    private float marginTop;
    private float marginRight;
    private float marginBottom;
    private int textColor;
    private JSONArray colorIndex;
    private Paint paint1 = new Paint();
    private Paint paintColor = new Paint();
    private float textShowWidth;
    private float Spacing = 0;
    private float LineSpacing = 1.3f;//行与行的间距

    public CustomAlignTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        text = attrs.getAttributeValue("http://schemas.android.com/apk/res/android", "text");
        textSize = attrs.getAttributeIntValue(namespace, "textSize", 25);//字体大小
        textColor = attrs.getAttributeIntValue(namespace, "textColor", Color.BLACK);//字体颜色
        paddingLeft = attrs.getAttributeIntValue(namespace, "paddingLeft", 0);
        paddingRight = attrs.getAttributeIntValue(namespace, "paddingRight", 0);
        paddingTop = attrs.getAttributeIntValue(namespace, "paddingTop", 0);
        paddingBottom = attrs.getAttributeIntValue(namespace, "paddingBottom", 0);
        marginLeft = attrs.getAttributeIntValue(namespace, "marginLeft", 0);
        marginRight = attrs.getAttributeIntValue(namespace, "marginRight", 0);
        marginTop = attrs.getAttributeIntValue(namespace, "marginTop", 0);
        marginBottom = attrs.getAttributeIntValue(namespace, "marginBottom", 0);
        paint1.setTextSize(textSize);
        paint1.setColor(textColor);
        paint1.setAntiAlias(true);
        paintColor.setAntiAlias(true);
        paintColor.setTextSize(textSize);
        paintColor.setColor(Color.BLACK);
    }

    public CustomAlignTextView(Context context, float textSize, int textColor, int paddingLeft, int paddingRight, float marginLeft, float marginRight) {
        super(context);
        this.textSize = textSize;
        this.textColor = textColor;
        this.paddingLeft = paddingLeft;
        this.paddingRight = paddingRight;
        this.marginLeft = marginLeft;
        this.marginRight = marginRight;
        paint1.setTextSize(textSize);
        paint1.setColor(textColor);
        paint1.setAntiAlias(true);
        paintColor.setAntiAlias(true);
        paintColor.setTextSize(textSize);
        paintColor.setColor(Color.BLACK);
    }


    public JSONArray getColorIndex() {
        return colorIndex;
    }

    public void setColorIndex(JSONArray colorIndex) {
        this.colorIndex = colorIndex;
    }

    /**
     * 传入一个索引，判断当前字是否被高亮
     *
     * @param index
     * @return
     * @throws JSONException
     */
    public boolean isColor(int index) throws JSONException {
        if (colorIndex == null) {
            return false;
        }
        for (int i = 0; i < colorIndex.length(); i++) {
            JSONArray array = colorIndex.getJSONArray(i);
            int start = array.getInt(0);
            int end = array.getInt(1) - 1;
            if (index >= start && index <= end) {
                return true;
            }
        }
        return false;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        //      super.onDraw(canvas);
        View view = (View) this.getParent();
        textShowWidth = view.getMeasuredWidth() - paddingLeft - paddingRight - marginLeft - marginRight;
        int lineCount = 0;

        text = this.getText().toString();//.replaceAll("\n", "\r\n");
        char[] textCharArray = text.toCharArray();
        // 已绘的宽度
        float drawedWidth = 0;
        float charWidth;
        for (int i = 0; i < textCharArray.length; i++) {
            charWidth = paint1.measureText(textCharArray, i, 1);

            if (textCharArray[i] == '\n') {
                lineCount++;
                drawedWidth = 0;
                continue;
            }
            if (textShowWidth - drawedWidth < charWidth) {
                lineCount++;
                drawedWidth = 0;
            }
            boolean color = false;
            try {
                color = isColor(i);
            } catch (JSONException e1) {
                e1.printStackTrace();
            }

            if (color) {

                canvas.drawText(textCharArray, i, 1, paddingLeft + drawedWidth, (lineCount + 1) * textSize * LineSpacing, paintColor);
            } else {

                canvas.drawText(textCharArray, i, 1, paddingLeft + drawedWidth, (lineCount + 1) * textSize * LineSpacing, paint1);
            }

            if (textCharArray[i] > 127 && textCharArray[i] != '、' && textCharArray[i] != '，' && textCharArray[i] != '。' && textCharArray[i] != '：' && textCharArray[i] != '！') {

                drawedWidth += charWidth + Spacing;

            } else {
                drawedWidth += charWidth;

            }
        }
        setHeight((int) ((lineCount + 1) * (int) textSize * LineSpacing + 10));
    }

    public void setAlignTextColor(int colorVal) {
        textColor = colorVal;
        paint1.setColor(textColor);
        paintColor.setColor(textColor);

        super.setTextColor(textColor);
    }

    public void setAlignPadding(int left, int right, int top, int bottom) {

        paddingLeft = left;
        paddingRight = right;
        paddingTop = top;
        paddingBottom = bottom;

        super.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
    }

    public void setAlignMargin(float left, float right, float top, float bottom) {

        marginLeft = left;
        marginRight = right;
        marginTop = top;
        marginBottom = bottom;

    }

    public void setAlignSpacing(float spacing) {
        Spacing = spacing;
    }

    public void setAlignLineSpacing(float lineSpacing) {
        LineSpacing = lineSpacing;
    }

    public void setAlignTextSize(float textSize) {
        this.textSize = textSize;
        paint1.setTextSize(textSize);
        paintColor.setTextSize(textSize);
        super.setTextSize(textSize);
    }

    public void setText(String text) {
//        this.text = stringFilter(ToDBC(text));
        this.text = text;
        super.setText(this.text);
    }

    /**
     * 半角转换为全角
     *
     * @param input
     * @return
     */
    public String ToDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {// 全角空格为12288，半角空格为32
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)// 其他字符半角(33-126)与全角(65281-65374)的对应关系是：均相差65248
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

    /**
     * 去除特殊字符或将所有中文标号替换为英文标号
     *
     * @param str
     * @return
     */
    public String stringFilter(String str) {
        str = str.replaceAll("【", "[").replaceAll("】", "]").replaceAll("！", "!").replaceAll("：", ":");// 替换中文标号
        String regEx = "[『』]"; // 清除掉特殊字符
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }
}
