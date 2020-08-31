package com.xinzeyijia.houselocks.util

import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.classic.common.MultipleStatusView
import com.xinzeyijia.houselocks.utils.NetUtil
import com.xinzeyijia.houselocks.utils.ToastUtil

class DefIconUtil {
    object SingTon {
        internal val DEF_ICON_UTIL = DefIconUtil()
    }

    /**
     * 获取空试图
     */
    fun getNoDataIcon(multiple_status_view: MultipleStatusView?, mHomeItemAdapter: BaseQuickAdapter<*, *>) {
        if (multiple_status_view != null) {
            if (mHomeItemAdapter.itemCount == 0)
                multiple_status_view.showEmpty()
            else
                multiple_status_view.showContent()
        }
    }

    /**
     * 获取空试图（可以自定义试图）
     */
    fun getNoDataIcon(
        multiple_status_view: MultipleStatusView?,
        mHomeItemAdapter: BaseQuickAdapter<*, *>,
        layout: Int
    ) {
        if (multiple_status_view != null) {
            if (mHomeItemAdapter.itemCount == 0)
                multiple_status_view.showEmpty(layout, ViewGroup.LayoutParams(-1, -1))
            else
                multiple_status_view.showContent()
        }
    }


    /**
     * 获取没网试图
     *
     * @param multiple_status_view
     * @param mHomeItemAdapter
     */
    fun getNoNetIcon(multiple_status_view: MultipleStatusView?, mHomeItemAdapter: BaseQuickAdapter<*, *>) {
        if (multiple_status_view != null) {
            if (NetUtil.isConnected()) {
                if (mHomeItemAdapter.itemCount == 0)
                    multiple_status_view.showEmpty()
                else
                    multiple_status_view.showContent()
            } else {
                ToastUtil.show("没有网络")
                if (!multiple_status_view.isShown)
                    multiple_status_view.showNoNetwork()
            }
        }
    }

    /**
     * 获取没网试图(可以自定义)
     *
     * @param multiple_status_view
     * @param mHomeItemAdapter
     */
    fun getNoNetIcon(multiple_status_view: MultipleStatusView?, mHomeItemAdapter: BaseQuickAdapter<*, *>, layout: Int) {
        if (multiple_status_view != null) {
            if (NetUtil.isConnected()) {
                if (mHomeItemAdapter.itemCount == 0)
                    multiple_status_view.showEmpty(layout, ViewGroup.LayoutParams(-1, -1))
                else
                    multiple_status_view.showContent()
            } else {
                ToastUtil.show("没有网络")
                if (!multiple_status_view.isShown)
                    multiple_status_view.showNoNetwork()
            }
        }
    }

    /**
     * 获取没网试图(可以自定义)
     *
     * @param multiple_status_view
     */
    fun getNoNetIcon(multiple_status_view: MultipleStatusView?) {
        if (multiple_status_view != null) {
            if (NetUtil.isConnected()) {
                multiple_status_view.showContent()
            } else {
                ToastUtil.show("没有网络")
                multiple_status_view.showNoNetwork()
            }
        }
    }


    companion object {
        val instance: DefIconUtil
            get() = SingTon.DEF_ICON_UTIL
    }
}
