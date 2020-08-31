package com.xinzeyijia.houselocks.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import com.xinzeyijia.houselocks.R;

public class EditorSharePopWin extends PopupWindow {

    private View view;

    public View getView() {
        return view;
    }

    @SuppressLint("ClickableViewAccessibility")
    public EditorSharePopWin(Context mContext, View.OnClickListener itemsOnClick) {

        view = LayoutInflater.from( mContext ).inflate( R.layout.share_editor_pop, null );

        ImageButton weixin = view.findViewById( R.id.ib_weixin );
        weixin.setOnClickListener( itemsOnClick );
        ImageButton qq = view.findViewById( R.id.ib_qq );
        qq.setOnClickListener( itemsOnClick );
        ImageButton weibo = view.findViewById( R.id.ib_weibo );
        ImageButton yaoqing = view.findViewById( R.id.ib_yaoqing );
        weibo.setOnClickListener( itemsOnClick );
        yaoqing.setOnClickListener( itemsOnClick );
        ImageButton pengyouquan = view.findViewById( R.id.ib_pengyouquan );
        pengyouquan.setOnClickListener( itemsOnClick );
        ImageButton kongjian = view.findViewById( R.id.ib_qqkongjian );
        ImageButton ibJubao = view.findViewById( R.id.ib_jubao );
        ibJubao.setOnClickListener( itemsOnClick );
        kongjian.setOnClickListener( itemsOnClick );
        // 设置外部可点击
        setOutsideTouchable( true );
        setClippingEnabled( false );
        // mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        view.setOnTouchListener( (v, event) -> {
            int height = view.findViewById( R.id.pop_layout ).getTop();
            int y = (int) event.getY();
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (y < height) {
                    dismiss();
                }
            }
            return true;
        } );


    /* 设置弹出窗口特征 */
        // 设置视图
        this.setContentView( this.view );
        // 设置弹出窗体的宽和高
        this.setHeight( RelativeLayout.LayoutParams.MATCH_PARENT );
        this.setWidth( RelativeLayout.LayoutParams.MATCH_PARENT );

        // 设置弹出窗体可点击
        this.setFocusable( true );

        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable( 0xb0000000 );
        // 设置弹出窗体的背景
        this.setBackgroundDrawable( dw );

        // 设置弹出窗体显示时的动画，从底部向上弹出
        this.setAnimationStyle( R.style.take_photo_anim );

    }
}