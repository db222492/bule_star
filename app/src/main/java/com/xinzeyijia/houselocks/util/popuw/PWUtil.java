package com.xinzeyijia.houselocks.util.popuw;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.clj.fastble.BleManager;
import com.xinzeyijia.houselocks.R;
import com.xinzeyijia.houselocks.util.EditorSharePopWin;
import com.xinzeyijia.houselocks.util.Tools;
import com.xinzeyijia.houselocks.utils.ToastUtil;
import com.xinzeyijia.houselocks.utils.time.CountdownUtil;

import static com.xinzeyijia.houselocks.util.chicun.DensityUtil.getScreenHeight;
import static com.xinzeyijia.houselocks.util.chicun.DensityUtil.getScreenWidth;

/**
 * Created by EDZ on 2018/11/26.
 */

public class PWUtil {

    private PopupWindow popupWindow;
    private View inflate;
    private EditorSharePopWin takePhotoPopWin;
    private Activity mContext;
    private View view;
    private String WXtype;
    private Object t;
    private View progressView;
    private View defView;
    private View contentView;
    private ProgressDialog progressDialog;
    private AlertDialog alertDialog;
    private AlertDialog alertDialog2;
    private CountdownUtil countdownUtil;

    private static class SinglePW {
        @SuppressLint("StaticFieldLeak")
        private static final PWUtil PW_UTIL = new PWUtil();
    }

    public static PWUtil getInstace() {
        return SinglePW.PW_UTIL;
    }

    /**
     * @param activity 当前页面activity
     * @param title    提示语言
     * @param b        点击确认取消是否需要关闭界面
     */
    public void showPw(Activity activity, String title, boolean b) {
        PWUtil pwUtil = defPW(0, activity).build();
        View inflate = pwUtil.getInflate();
        TextView view = inflate.findViewById(R.id.title);
        view.setVisibility(View.VISIBLE);
        view.setText(title);
        inflate.findViewById(R.id.yes).setOnClickListener(view1 -> {
            PWUtil.getInstace().dissmissPW();
            if (b) activity.finish();
        });
        inflate.findViewById(R.id.no).setOnClickListener(view1 -> {
            PWUtil.getInstace().dissmissPW();
            if (b) activity.finish();
        });
    }

    public PWUtil makeSharePW(Activity context, Object t, String type) {
        WXtype = type;
        mContext = context;
        this.t = t;
        takePhotoPopWin = new EditorSharePopWin(context, onClickListener);
        view = takePhotoPopWin.getView();
        //showAtLocation(View parent, int gravity, int x, int y)
        if (mContext.isFinishing())
        takePhotoPopWin.showAtLocation(context.getWindow().getDecorView(), Gravity.CENTER, 0, 0);

        return this;
    }

    public PWUtil makeDialog(Activity context, boolean focus, String message) {
        if (!context.isFinishing()) {
            dissmissPW();
            disDialog();
            progressDialog = new ProgressDialog(context);
            progressDialog.setCancelable(focus);
            progressDialog.setMessage(message);
        }
        return this;
    }

    /**
     * 10秒钟后自动关闭
     */
    public void showDialog() {
        if (progressDialog != null && !progressDialog.isShowing()) {
            progressDialog.show();
            countdownUtil = CountdownUtil.newInstance();
            countdownUtil.totalTime(10000)
                    .intervalTime(1000)
                    .callback(new CountdownUtil.OnCountdownListener() {

                        @Override
                        public void onRemain(long millisUntilFinished) {

                        }

                        @Override
                        public void onFinish() {
                            PWUtil.getInstace().disDialog();
                        }
                    }).start();
        }
    }

    public void disDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            if (countdownUtil != null)
                countdownUtil.stop();
            progressDialog.dismiss();
        }
        if (alertDialog2 != null && alertDialog2.isShowing()) {
            alertDialog2.dismiss();
        }
        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
        }
    }

    public void destoryDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    public View getView() {
        if (view == null)
            throw new RuntimeException("view is null");
        return view;
    }

    private View.OnClickListener onClickListener = v -> {

//        switch (v.getId()) {
//            case R.id.ib_yaoqing:
//
//                break;
//            case R.id.ib_weixin:
//                Share( 0 );
//                break;
//            case R.id.ib_pengyouquan:
//                Share( 1 );
//                break;
//            case R.id.ib_weibo:
//                Share( 2 );
//                break;
//            case R.id.ib_qq:
//                Share( 3 );
//                break;
//            case R.id.ib_qqkongjian:
//                Share( 4 );
//                break;
//            case R.id.ib_jubao:
//                break;
//        }
    };

//    /**
//     * 微信分享
//     */
//    private void Share(int type) {
//        SHARE_MEDIA share = null;
//        switch (type) {
//            case 0:
//                share = SHARE_MEDIA.WEIXIN;
//                break;
//            case 1:
//                share = SHARE_MEDIA.WEIXIN_CIRCLE;
//                break;
//            case 2:
//                share = SHARE_MEDIA.SINA;
//                break;
//            case 3:
//                share = SHARE_MEDIA.QQ;
//                break;
//            case 4:
//                share = SHARE_MEDIA.QZONE;
//                break;
//            default:
//                break;


//        }
//
//        DataBean webBean = (DataBean) t;
////        DialogUtils.shareUM( mContext, share,"", "", "","" );
//
//        takePhotoPopWin.dismiss();
//    }


    public PWUtil defPW(int layout, Activity context) {
        this.mContext = context;
        if (layout == 0)
            layout = R.layout.def_popu_item;
        inflate = View.inflate(context, layout, null);
        return this;
    }

    public View getInflate() {
        return inflate;
    }

    public PWUtil addTitle(String mtitle) {
        TextView title = inflate.findViewById(R.id.title);
        if (Tools.NotNull(mtitle))
            title.setVisibility(View.VISIBLE);
        title.setText(mtitle);
        return this;
    }

    public PWUtil addTitle(int mtitle) {
        TextView title = inflate.findViewById(R.id.title);
        if (mtitle != 0)
            title.setVisibility(View.VISIBLE);
        title.setText(mtitle);
        return this;
    }

    public PWUtil addContent(String content) {
        TextView tv_content = inflate.findViewById(R.id.content);
        if (Tools.NotNull(content))
            tv_content.setVisibility(View.VISIBLE);
        tv_content.setText(content);
        return this;
    }

    public PWUtil addContent(int content) {
        TextView tv_content = inflate.findViewById(R.id.content);
        if (content != 0)
            tv_content.setVisibility(View.VISIBLE);
        tv_content.setText(content);
        return this;
    }

    public PWUtil addNoListener(View.OnClickListener onClickListener) {
        TextView no = inflate.findViewById(R.id.no);
        no.setOnClickListener(onClickListener);
        return this;
    }

    public PWUtil addNoListener(int noName, View.OnClickListener onClickListener) {
        TextView no = inflate.findViewById(R.id.no);
        no.setText(noName);
        no.setOnClickListener(onClickListener);
        return this;
    }

    public PWUtil addYesListener(View.OnClickListener onClickListener) {
        TextView yes = inflate.findViewById(R.id.yes);
        yes.setOnClickListener(onClickListener);
        return this;
    }

    public PWUtil addYesListener(int yesName, View.OnClickListener onClickListener) {
        TextView yes = inflate.findViewById(R.id.yes);
        yes.setText(yesName);
        yes.setOnClickListener(onClickListener);
        return this;
    }

    public PWUtil defListener() {
        TextView no = inflate.findViewById(R.id.no);
        no.setOnClickListener(v -> popupWindow.dismiss());
        TextView yes = inflate.findViewById(R.id.yes);
        yes.setOnClickListener(v -> popupWindow.dismiss());
        return this;
    }

    public PWUtil addFocus(boolean isFocus) {
        RelativeLayout rt_back = inflate.findViewById(R.id.rt_back);
        if (isFocus)
            rt_back.setOnClickListener(view1 -> dissmissPW());
        return this;
    }


    public PWUtil build() {
        if (!mContext.isFinishing()) {
            dissmissPW();
            disDialog();
            popupWindow = PWUtil.getInstace().makePopupWindow(inflate, -1, -1, true)
                    .showLocationWithAnimation(R.style.AnimationFade);
            popupWindow.setClippingEnabled(false);
            popupWindow.showAtLocation(mContext.getWindow().getDecorView(), Gravity.CENTER, 0, 0);//y轴越小越向上
        }
        return this;
    }

    public PopupWindow getPopupWindow() {
        return popupWindow;
    }

    public void showMsg(Activity ctx, String txt) {
        if (!ctx.isFinishing()) {
            destoryDialog();
            PWUtil.getInstace().dissmissPW();
            PWUtil.getInstace().defPW(R.layout.def_popu_item2, ctx).build()
                    .addTitle(R.string.notice)
                    .addContent(txt)
                    .defListener();
        }
    }


    public PopupWindow showLocation(View view, int xOff, int yOff, int graty) {
        if (popupWindow != null){
            popupWindow.showAtLocation(view, graty, xOff, yOff);
        }
        return popupWindow;
    }


    @SuppressLint("WrongConstant")
    public PWUtil makePopupWindow(View view, int width, int height, boolean isFocus) {
        popupWindow = new PopupWindow(view, width, height, isFocus);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
//        popupWindow.setClippingEnabled(false);
        popupWindow.setTouchable(true); // 设置PopupWindow可触摸
        popupWindow.setOutsideTouchable(true); // 设置非PopupWindow区域可触摸
//        popupWindow.setSoftInputMode(INPUT_METHOD_NEEDED);
//        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return this;
    }

    @SuppressLint("WrongConstant")
    public PWUtil makePopupWindow(Context context, int layout, int width, int height, boolean isFocus) {
        contentView = View.inflate(context, layout, null);
        popupWindow = new PopupWindow(contentView, width, height, isFocus);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.setClippingEnabled(false);
        popupWindow.setTouchable(true); // 设置PopupWindow可触摸
        popupWindow.setOutsideTouchable(true); // 设置非PopupWindow区域可触摸
//        popupWindow.setSoftInputMode(INPUT_METHOD_NEEDED);
//        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return this;
    }

    public View getContentView() {
        return popupWindow.getContentView();
    }

    public void dissmissPW() {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
        popupWindow = null;
    }

    public boolean isShow() {
        return popupWindow != null && popupWindow.isShowing();
    }

    public PWUtil setPWClippingEnabled(boolean enable) {
        popupWindow.setClippingEnabled(enable);
        return this;
    }

    /**
     * @param cx   此处必须为Activity的实例
     * @param anim 弹出及消失动画
     * @return
     */

    public PopupWindow showLocationWithAnimation(final Context cx, int anim) {
        // 弹出动画
        popupWindow.setAnimationStyle(anim);


        // 弹出PopupWindow时让后面的界面变暗
        WindowManager.LayoutParams parms = ((Activity) cx).getWindow().getAttributes();
        parms.alpha = 0.5f;
        ((Activity) cx).getWindow().setAttributes(parms);


        popupWindow.setOnDismissListener(() -> {
            // PopupWindow消失后让后面的界面变亮
            WindowManager.LayoutParams parms1 = ((Activity) cx).getWindow().getAttributes();
            parms1.alpha = 1.0f;
            ((Activity) cx).getWindow().setAttributes(parms1);
        });

        return popupWindow;
    }

    /**
     * @param cx   此处必须为Activity的实例
     * @param view 显示在该控件之下
     * @param anim 弹出及消失动画
     * @return
     */

    public PopupWindow showLocationWithAnimation(final Context cx, View view, int anim) {
        // 弹出动画
        popupWindow.setAnimationStyle(anim);


        // 弹出PopupWindow时让后面的界面变暗
        WindowManager.LayoutParams parms = ((Activity) cx).getWindow().getAttributes();
        parms.alpha = 0.5f;
        ((Activity) cx).getWindow().setAttributes(parms);

        int[] positon = new int[2];
        view.getLocationOnScreen(positon);
        // 弹窗的出现位置，在指定view之下


        popupWindow.setOnDismissListener(() -> {
            // PopupWindow消失后让后面的界面变亮
            WindowManager.LayoutParams parms1 = ((Activity) cx).getWindow().getAttributes();
            parms1.alpha = 1.0f;
            ((Activity) cx).getWindow().setAttributes(parms1);
        });

        return popupWindow;
    }

    public PopupWindow showLocationWithAnimation(int anim) {
        // 弹出动画
        popupWindow.setAnimationStyle(anim);


        return popupWindow;
    }

    /**
     * 计算出来的位置，y方向就在anchorView的上面和下面对齐显示，x方向就是与屏幕右边对齐显示
     * 如果anchorView的位置有变化，就可以适当自己额外加入偏移来修正
     *
     * @param anchorView  呼出window的view
     * @param contentView window的内容布局
     * @return window显示的左上角的xOff, yOff坐标
     */
    public static int[] calculatePopWindowPos(final View anchorView, final View contentView) {
        final int windowPos[] = new int[2];
        final int anchorLoc[] = new int[2];
        // 获取锚点View在屏幕上的左上角坐标位置
        anchorView.getLocationOnScreen(anchorLoc);
        final int anchorHeight = anchorView.getHeight();
        // 获取屏幕的高宽
        final int screenHeight = getScreenHeight(anchorView.getContext());
        final int screenWidth = getScreenWidth(anchorView.getContext());
        contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        // 计算contentView的高宽
        final int windowHeight = contentView.getMeasuredHeight();
        final int windowWidth = contentView.getMeasuredWidth();
        // 判断需要向上弹出还是向下弹出显示
        final boolean isNeedShowUp = (screenHeight - anchorLoc[1] - anchorHeight < windowHeight);
        if (isNeedShowUp) {
            windowPos[0] = screenWidth - windowWidth;
            windowPos[1] = anchorLoc[1] - windowHeight;
        } else {
            windowPos[0] = screenWidth - windowWidth;
            windowPos[1] = anchorLoc[1] + anchorHeight;
        }
        return windowPos;
    }

    /**
     * 计算popwindow在长按view 的什么位置显示
     *
     * @param anchorView 长按锚点的view
     * @param popView    弹出框
     * @param onScreenx  锚点距离屏幕左边的距离
     * @param onScreeny  锚点距离屏幕上方的距离
     * @return popwindow在长按view中的xy轴的偏移量
     */
    public int[] calculatePopWindowPos(final View anchorView, final View popView, int onScreenx, int onScreeny) {
        final int windowPos[] = new int[2];
        final int anchorLoc[] = new int[2];
        // 获取触点在屏幕上相对左上角坐标位置
        anchorLoc[0] = onScreenx;
        anchorLoc[1] = onScreeny;
        //当前item的高度
        final int anchorHeight = anchorView.getHeight();
        // 获取屏幕的高宽
        final int screenHeight = getScreenHeight(anchorView.getContext());
        final int screenWidth = getScreenWidth(anchorView.getContext());
        // 测量popView 弹出框
        popView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        // 计算弹出框的高宽
        final int popHeight = popView.getMeasuredHeight();
        final int popWidth = popView.getMeasuredWidth();
        // 判断需要向上弹出还是向下弹出显示
        // 屏幕高度-触点距离左上角的高度 < popwindow的高度
        // 如果小于弹出框的高度那么说明下方空间不够显示 popwindow，需要放在触点的上方显示
        final boolean isNeedShowUp = (screenHeight - anchorLoc[1] < popHeight);
        // 判断需要向右边弹出还是向左边弹出显示
        //判断触点右边的剩余空间是否够显示popwindow 大于就说明够显示
        final boolean isNeedShowRight = (screenWidth - anchorLoc[0] > popWidth);
        if (isNeedShowUp) {
            //如果在上方显示 则用 触点的距离上方的距离 - 弹框的高度
            windowPos[1] = anchorLoc[1] - popHeight;
        } else {
            //如果在下方显示 则用 触点的距离上方的距离
            windowPos[1] = anchorLoc[1];
        }
        if (isNeedShowRight) {
            windowPos[0] = anchorLoc[0];
        } else {
            //显示在左边的话 那么弹出框的位置在触点左边出现，则是触点距离左边距离 - 弹出框的宽度
            windowPos[0] = anchorLoc[0] - popWidth;
        }
        return windowPos;
    }

    /**
     * 打开蓝牙
     */
    public void openBlueTouch(Activity activity) {
        if (!activity.isFinishing()) {
            destoryDialog();
            PWUtil.getInstace().dissmissPW();
            alertDialog = new AlertDialog.Builder(activity).setTitle("蓝牙功能尚未打开，是否打开蓝牙")
                    .setInverseBackgroundForced(false)
                    .setCancelable(false)
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .setPositiveButton("确定", (dialog, which) -> {
                        alertDialog.dismiss();
                        if (turnOnBluetooth()) {
                            ToastUtil.show("打开蓝牙成功");
                        } else {
                            ToastUtil.show("打开蓝牙失败！！");
                        }
                    })
                    .setNegativeButton("取消", (dialog, which) -> {
                        dialog.cancel();
                        activity.finish();
                    })
                    .show();
        }
    }

    /**
     * 强制开启当前 Android 设备的 Bluetooth
     *
     * @return true：强制打开 Bluetooth　成功　false：强制打开 Bluetooth 失败
     */
    private boolean turnOnBluetooth() {
        return BleManager.getInstance().getBluetoothAdapter().enable();
    }


    public void reOpenBlueTouch(Activity activity) {
        if (!activity.isFinishing()) {
            destoryDialog();
            PWUtil.getInstace().dissmissPW();
            alertDialog2 = new AlertDialog.Builder(activity).setTitle("多次连接失败，请重启蓝牙重试")
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .setInverseBackgroundForced(false)
                    .setCancelable(false)
                    .setPositiveButton("确定", (dialog, which) -> {
                        BleManager.getInstance().disableBluetooth();
                    })
                    .setNegativeButton("取消", (dialog, which) -> {
                        dialog.cancel();
                        activity.finish();
                    })
                    .show();
        }

    }
}

