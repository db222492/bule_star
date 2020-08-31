package com.xinzeyijia.houselocks.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v7.widget.AppCompatTextView;
import android.text.Layout;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.view.View;

/**
 * 固定行数展开收缩控件
 */
public class QFolderTextView extends AppCompatTextView {

    private static String ELLIPSIZE = "...";
    private static String FOLD_TEXT = "收缩";
    private static String UNFOLD_TEXT = "查看详情";

    /**
     * 禁止展开状态
     */
    private boolean isForbidFold = false;

    /**
     * 收缩状态
     */
    private boolean isFold = false;

    /**
     * 绘制，防止重复进行绘制
     */
    private boolean isDrawn = false;

    /**
     * 内部绘制
     */
    private boolean isInner = false;

    /**
     * 折叠行数
     */
    private int mFoldLine;

    /**
     * 收缩文案颜色
     */
    private int mFoldColor;

    /**
     * 原始文案
     */
    private String mFullText;
    private float mSpacingMult = 1.0f;
    private float mSpacingAdd = 0.0f;

    /**
     * 收缩状态监听器
     */
    private IFolderSpanClickListener mFolderSpanClickListener;

    public QFolderTextView(Context context) {
        super(context);
    }

    public QFolderTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public QFolderTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setForbidFold(boolean forbidFold) {
        this.isForbidFold = forbidFold;
    }

    public void setFoldLine(int foldLine) {
        this.mFoldLine = foldLine;
    }

    public void setEllipsize(String ellipsize) {
        this.ELLIPSIZE = ellipsize;
    }

    public void setFoldText(String foldText) {
        this.FOLD_TEXT = foldText;
    }

    public void setUnfoldText(String unfoldText) {
        this.UNFOLD_TEXT = unfoldText;
    }

    public void setFoldColor(int foldColor) {
        this.mFoldColor = foldColor;
    }

    public void setFolderSpanClickListener(IFolderSpanClickListener folderSpanClickListener) {
        this.mFolderSpanClickListener = folderSpanClickListener;
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        if (TextUtils.isEmpty(mFullText) || !isInner) {
            isDrawn = false;
            mFullText = String.valueOf(text);
        }
        super.setText(text, type);
    }

    @Override
    public void setLineSpacing(float add, float mult) {
        mSpacingAdd = add;
        mSpacingMult = mult;
        super.setLineSpacing(add, mult);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (!isDrawn) {
            resetText();
        }
        super.onDraw(canvas);
        isDrawn = true;
        isInner = false;
    }

    private void resetText() {
        String spanText = mFullText;
        SpannableString spanStr;

        if (isForbidFold) { //禁止展开状态
            spanStr = createFoldSpan(spanText);
        } else if (isFold) { //收缩状态
            spanStr = createUnFoldSpan(spanText);
        } else { //展开状态
            spanStr = createFoldSpan(spanText);
        }

        setUpdateText(spanStr);
        setMovementMethod(LinkMovementMethod.getInstance());
    }

    /**
     * 不更新全文本下，进行展开和收缩操作
     *
     * @param text
     */
    private void setUpdateText(CharSequence text) {
        isInner = true;
        setText(text);
    }

    /**
     * 创建展开状态下的Span
     *
     * @param text 源文本
     * @return
     */
    private SpannableString createUnFoldSpan(String text) {
        String destStr = text + FOLD_TEXT;
        if (makeTextLayout(destStr).getLineCount() <= mFoldLine) {
            return new SpannableString(text);
        }

        int start = destStr.length() - FOLD_TEXT.length();
        int end = destStr.length();

        SpannableString spanStr = new SpannableString(destStr);
        spanStr.setSpan(clickSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spanStr;
    }

    /**
     * 创建收缩状态下的Span
     *
     * @param text
     * @return
     */
    private SpannableString createFoldSpan(String text) {
        if (makeTextLayout(text + UNFOLD_TEXT).getLineCount() <= mFoldLine) {
            return new SpannableString(text);
        }
        String destStr = tailorText(text);

        int start = destStr.length() - UNFOLD_TEXT.length();
        int end = destStr.length();

        SpannableString spanStr = new SpannableString(destStr);
        spanStr.setSpan(clickSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spanStr;
    }

    /**
     * 裁剪文本至固定行数
     *
     * @param text 源文本
     * @return
     */
    private String tailorText(String text) {
        String destStr = text + ELLIPSIZE + UNFOLD_TEXT;
        Layout layout = makeTextLayout(destStr);

        //如果行数大于固定行数
        if (layout.getLineCount() > mFoldLine) {
            int index = layout.getLineEnd(mFoldLine);
            if (text.length() < index) {
                index = text.length();
            }
            String subText = text.substring(0, index - 1); //从最后一位逐渐试错至固定行数
            return tailorText(subText);
        } else {
            return destStr;
        }
    }

    private Layout makeTextLayout(String text) {
        return new StaticLayout(text, getPaint(), getWidth() - getPaddingLeft() - getPaddingRight(),
                Layout.Alignment.ALIGN_NORMAL, mSpacingMult, mSpacingAdd, false);
    }

    private ClickableSpan clickSpan = new ClickableSpan() {
        @Override
        public void onClick(View widget) {
            if (mFolderSpanClickListener != null) {
                mFolderSpanClickListener.onClick(isFold);
            }
            isFold = !isFold;
            isDrawn = false;
            invalidate();
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            if (mFoldColor != 0) {
                ds.setColor(mFoldColor);
            } else {
                ds.setColor(ds.linkColor);
            }
        }
    };

    public interface IFolderSpanClickListener {
        void onClick(boolean isFold);
    }
}
