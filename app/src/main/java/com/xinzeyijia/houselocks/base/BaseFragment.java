package com.xinzeyijia.houselocks.base;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gyf.immersionbar.ImmersionBar;
import com.xinzeyijia.houselocks.R;
import com.xinzeyijia.houselocks.contract.home.HomeContract;
import com.xinzeyijia.houselocks.model.bean.BaseBean;
import com.xinzeyijia.houselocks.ui.activity.LoginActivity;
import com.xinzeyijia.houselocks.util.ST;
import com.xinzeyijia.houselocks.util.TUtils;
import com.xinzeyijia.houselocks.utils.NetUtil;
import com.xinzeyijia.houselocks.utils.ToastUtil;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;


/**
 * Created by  on 2018/12/8.
 */

public abstract class BaseFragment<P extends BasePresenter> extends Fragment implements HomeContract.View , View.OnClickListener  {

    /**
     * 视图是否已经初初始化
     */
    protected boolean isInit = false;
    protected boolean isLoad = false;
    private View view;
    public static long mLastClick = 0L;//下一次点击的时间
    private static final int THRESHOLD = 500;//两次点击的间隔时长
    public P p;

    public void noNetWork() {
        ToastUtil.show("没有网络");
    }

    public void planDevelop() {
        ToastUtil.show("指令开发中......");
    }
    public void makeToast(String content) {
        ToastUtil.show(content, Toast.LENGTH_LONG);
    }

    public void makeToast(int content) {
        ToastUtil.show(content, Toast.LENGTH_LONG);
    }


    protected void initLoad(View view) {
        p = TUtils.getT(this, 0);
        assert p != null;
        p.attachV(this);
        if (!NetUtil.isConnected()) {
            noNetWork();
        }
    }


    protected int getLayoutId() {
        return 0;
    }


    protected void lazyLoad() {
        p = TUtils.getT(this, 0);
        assert p != null;
        p.attachV(this);
        if (!NetUtil.isConnected()) {
            noNetWork();
        }
    }

    @Override
    public void onClick(View v) {
        if (!isDoubleClick() && isConnect()) {
            mClick(v);
        } else {
            alreadyClick();
        }
    }

    public void mClick(View v) {

    }

    public void alreadyClick() {
        ToastUtil.show("请勿重复点击");
    }
    public void showData(@NotNull String type, @org.jetbrains.annotations.Nullable BaseBean data) {
    }

    public void showData(@NotNull String type, @org.jetbrains.annotations.Nullable Object data) {
    }


    public void setProgress(long currentSize, long totalSize) {

    }

    @Override
    public void timeOut() {

    }

    public void showError(String type, Throwable e) {

    }

    /**
     * 防止连点
     *
     * @return
     */
    /**
     * 防止连点
     *
     * @return
     */
    public synchronized static boolean isDoubleClick() {
        long now = System.currentTimeMillis();
        boolean b = now - mLastClick < THRESHOLD;
        if (!b){
            mLastClick = now;
        }
        return b;
    }


    public void finishActivity() {
        Objects.requireNonNull(getActivity()).finish();
    }
    @Override
    public void showMessage(String message) {
        if (!message.equals("请联系后台!timeout"))
            ToastUtil.show(message, Toast.LENGTH_LONG);
    }
    @Override
    public final View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(getLayoutId(), container, false);

        isInit = true;
        /*
        初始化的时候去加载数据
        **/
        isCanLoadData();
        return view;
    }

    private void fitsLayoutOverlap() {
        ImmersionBar.with(this)
                .titleBar(R.id.toolbar)
                .navigationBarDarkIcon(false)
                .statusBarDarkFont(true).init();
    }
    public boolean isConnect() {
        if (!NetUtil.isConnected()){
            ToastUtil.show("请先打开网络！");
            return false;
        }
        return true;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fitsLayoutOverlap();//初始化沉浸式状态栏
        initLoad(view);
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //旋转屏幕为什么要重新设置布局与状态栏重叠呢？因为旋转屏幕有可能使状态栏高度不一样，如果你是使用的静态方法修复的，所以要重新调用修复
        fitsLayoutOverlap();
    }

    public void startBind() {
        ToastUtil.show("请绑定手机号！");
//        ST.startActivity(getActivity(), BindPhoneActivity.class);
        Objects.requireNonNull(getActivity()).finish();
    }

    public void startLogin() {
        ToastUtil.show("用户失效请重新登录！");
        ST.startActivity(getActivity(), LoginActivity.class);
        finishActivity();
    }

    public void startRegist() {
        ToastUtil.show("请先注册！");
//        ST.startActivity(getActivity(), RegistActivity.class);
    }

    /**
     * 视图是否已经对用户可见，系统的方法
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isCanLoadData();
    }

    /**
     * 是否可以加载数据
     * 可以加载数据的条件：
     * 1.视图已经初始化
     * 2.视图对用户可见
     */
    private void isCanLoadData() {
        if (!isInit) {
            return;
        }

        if (getUserVisibleHint()) {
            lazyLoad();
            isLoad = true;
        } else {
            if (isLoad) {
                stopLoad();
            }
        }
    }

    /**
     * 视图销毁的时候讲Fragment是否初始化的状态变为false
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isInit = false;
        isLoad = false;
    }


    /**
     * 当视图已经对用户不可见并且加载过数据，如果需要在切换到其他页面时停止加载数据，可以覆写此方法
     */
    protected void stopLoad() {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Glide.get(Objects.requireNonNull(getActivity())).clearMemory();
        if (p != null) p.onDetached();
    }

    public void showDeveloping() {
        ToastUtil.show("正在开发中");
    }
}
