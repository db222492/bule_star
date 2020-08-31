package com.xinzeyijia.houselocks.base;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkRequest;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.gyf.immersionbar.ImmersionBar;
import com.luck.picture.lib.tools.PictureFileUtils;
import com.xinzeyijia.houselocks.R;
import com.xinzeyijia.houselocks.contract.home.HomeContract;
import com.xinzeyijia.houselocks.model.bean.BaseBean;
import com.xinzeyijia.houselocks.ui.activity.LoginActivity;
import com.xinzeyijia.houselocks.util.TUtils;
import com.xinzeyijia.houselocks.util.chicun.Density;
import com.xinzeyijia.houselocks.util.permissionutil.PermissionListener;
import com.xinzeyijia.houselocks.util.permissionutil.PermissionUtil;
import com.xinzeyijia.houselocks.util.popuw.PWUtil;
import com.xinzeyijia.houselocks.utils.NetUtil;
import com.xinzeyijia.houselocks.utils.ToastUtil;
import com.xinzeyijia.houselocks.utils.io.SPUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static com.xinzeyijia.houselocks.util.LogUtils.logi;

//import com.umeng.message.PushAgent;


/**
 * 这里相当于是一个制造方法的工厂，
 * 所有v层的方法都在这里统一管理，实现多次复用
 * Created by EDZ on 2018/12/8.
 */

@SuppressLint("Registered")
public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements HomeContract.View {

    public P p;
    private BluetoothMonitorReceiver bleListenerReceiver;

    public void showMessage(String message) {
        if (!message.equals("请联系后台!timeout"))
            ToastUtil.show(message, Toast.LENGTH_LONG);
    }

    protected abstract int getLayoutId();


    protected void initView() {
        p = TUtils.getT(this, 0);
        assert p != null;
        p.attachV(this);//初始化P层对象
    }

    protected void initView(Bundle savedInstanceState) {
        p = TUtils.getT(this, 0);
        assert p != null;
        p.attachV(this);//初始化P层对象
    }

    @Override
    public void timeOut() {

    }

    public void showDeveloping() {
        if (!isFinishing())
            ToastUtil.show("正在开发中");
    }

    public void showData(@NotNull String type, @Nullable Object data) {
    }

    public void showData(@NotNull String type, @Nullable BaseBean data) {
    }

    public void setProgress(long currentSize, long totalSize) {
    }

    public void showError(String type, Throwable e) {
    }

    public void startTargetActivity(Class targetClass) {
        Intent mIntent = new Intent(getApplicationContext(), targetClass);
        startActivity(mIntent);
    }

    public void makeToast(String content) {
        ToastUtil.show(content, Toast.LENGTH_LONG);
    }

    public void makeToast(int content) {
        ToastUtil.show(content, Toast.LENGTH_LONG);
    }

    public void finishActivity() {
        finish();
    }

    public static long mLastClick = 0L;//下一次点击的时间
    private static final int THRESHOLD = 1000;//两次点击的间隔时长

    public void click(View view) {
        if (view.getId() == R.id.back) {
            onClick(view);
        } else {
            if (!isDoubleClick() && isConnect()) {
                onClick(view);
            } else {
                alreadyClick();
            }
        }
    }

    public void alreadyClick() {
    }

    public void onClick(View view) {
    }

    /**
     * 防止连点
     *
     * @return
     */
    public synchronized static boolean isDoubleClick() {
        long now = System.currentTimeMillis();
        boolean b = now - mLastClick < THRESHOLD;
        if (!b) {
            mLastClick = now;
        }
        return b;
    }

    /**
     * 显示键盘
     *
     * @param et 输入焦点
     */
    public void showInput(final EditText et) {
        et.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        assert imm != null;
        et.requestFocus();
        if (imm.isActive()) {
            imm.showSoftInput(et, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    public boolean isConnect() {
        if (!NetUtil.isConnected()) {
            ToastUtil.show("请先打开网络！");
            return false;
        }
        return true;
    }

    /**
     * 隐藏键盘
     */
    protected void hideInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        View v = getWindow().peekDecorView();
        if (null != v) {
            assert imm != null;
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }


    public void startLogin() {
        ToastUtil.show("用户失效请重新登录！");
        startActivity(new Intent(this, LoginActivity.class));
        finishActivity();
    }

    public void startRegist() {
        ToastUtil.show("请先注册！");
//        startActivity(new Intent(this, RegistActivity.class));
    }

    public void startBind() {
        ToastUtil.show("请绑定手机号！");
//        ST.startActivity(this, BindPhoneActivity.class);
        finish();
    }

    /**
     * 点击其他区域隐藏软件盘
     *
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    assert v != null;
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if ((v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    public class BluetoothMonitorReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action != null) {
                switch (action) {
                    case BluetoothAdapter.ACTION_STATE_CHANGED:
                        int blueState = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, 0);
                        switch (blueState) {
                            case BluetoothAdapter.STATE_TURNING_ON:
//                                makeToast("蓝牙正在打开");
                                break;
                            case BluetoothAdapter.STATE_ON:
//                                makeToast("蓝牙已经打开");
                                openBlueTouch();
                                break;
                            case BluetoothAdapter.STATE_TURNING_OFF:
//                                makeToast("蓝牙正在关闭");
                                break;
                            case BluetoothAdapter.STATE_OFF:
//                                makeToast("蓝牙已经关闭");
                                closeBlueTouch();
                                break;
                        }
                        break;

                    case BluetoothDevice.ACTION_ACL_CONNECTED:
                        logi("蓝牙设备已连接");
                        break;

                    case BluetoothDevice.ACTION_ACL_DISCONNECTED:
                        logi("蓝牙设备已断开");
                        break;
                }

            }
        }
    }

    public void closeBlueTouch() {
    }

    public void openBlueTouch() {
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Density.setDefault(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);//键盘遮挡
        setContentView(getLayoutId());
        initPermission();
        initBlueTouch();
        initNetWork();
//        PushAgent.getInstance(this).onAppStart();//推送
        fitsLayoutOverlap();//初始化沉浸式状态栏
        initView();
        initView(savedInstanceState);
    }

    private void initBlueTouch() {
        // 初始化广播
        bleListenerReceiver = new BluetoothMonitorReceiver();
        IntentFilter intentFilter = new IntentFilter();
        // 监视蓝牙关闭和打开的状态
        intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);

        // 监视蓝牙设备与APP连接的状态
        intentFilter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
        intentFilter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);

        // 注册广播
        registerReceiver(bleListenerReceiver, intentFilter);
    }


    private void fitsLayoutOverlap() {
        ImmersionBar.with(this)
                .titleBar(R.id.toolbar)
                .navigationBarDarkIcon(false)
                .statusBarDarkFont(true).init();
    }

    protected void initNetWork() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivityManager!=null){
                connectivityManager.requestNetwork(new NetworkRequest.Builder().build(), new ConnectivityManager.NetworkCallback() {
                    public void onAvailable(@NotNull Network network) {
                        super.onAvailable(network);
//                    LiveDataBus.get().with(Common.SEND_TYPE).setValue(new BusBean(Common.FRESH_PUB, ""));
                    }

                    @Override
                    public void onLost(@NotNull Network network) {
                        super.onLost(network);
                        ToastUtil.show("网络已断开");
                    }
                });
            }
        }

    }


    private void initPermission() {
        //创建PermissionUtil对象，参数为继承自V4包的 FragmentActivity
        PermissionUtil permissionUtil = new PermissionUtil(this);
        //调用requestPermissions
        String[] permissions = {
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_FINE_LOCATION,
//                    Manifest.permission.READ_PHONE_STATE,
//                    Manifest.permission.SYSTEM_ALERT_WINDOW,
//                    Manifest.permission.GET_ACCOUNTS,
                Manifest.permission.CAMERA,
                Manifest.permission.CALL_PHONE,
//                    Manifest.permission.WRITE_APN_SETTINGS
        };
        permissionUtil.requestPermissions(permissions,
                new PermissionListener() {
                    @Override
                    public void onGranted() {
                        //所有权限都已经授权
                        boolean isFirst = SPUtil.getInstance().getBoolean(Common.IS_FIRST, true);
                        if (isFirst) {
//                            makeToast("所有权限都已授权");
                            SPUtil.getInstance().put(Common.IS_FIRST, false);
                        }
                    }

                    @Override
                    public void onDenied(List<String> deniedPermission) {
                        //Toast第一个被拒绝的权限
                        PWUtil.getInstace().showMsg(BaseActivity.this, "您有部分权限被拒绝，可能会导致部分功能不能使用");
                    }

                    @Override
                    public void onShouldShowRationale(List<String> deniedPermission) {
                        //Toast第一个勾选不在提示的权限
                        PWUtil.getInstace().showMsg(BaseActivity.this, "您有部分权限被禁止提示，如需打开请到应用管理页设置");
                    }
                });

    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        // 必须调用该方法，防止内存泄漏
//包括裁剪和压缩后的缓存，要在上传成功后调用，注意：需要系统sd卡权限
        PictureFileUtils.deleteCacheDirFile(this);
        Glide.get(this).clearMemory();
        if (p != null) p.onDetached();
        unregisterReceiver(this.bleListenerReceiver);
    }
}
