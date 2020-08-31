package com.xinzeyijia.houselocks.ui.fragment

import android.annotation.TargetApi
import android.arch.lifecycle.Observer
import android.os.Build
import android.support.v4.app.Fragment
import android.view.View
import com.xinzeyijia.houselocks.R
import com.xinzeyijia.houselocks.base.BaseFragment
import com.xinzeyijia.houselocks.base.Common
import com.xinzeyijia.houselocks.contract.home.HomePresenter
import com.xinzeyijia.houselocks.model.bean.BusBean
import com.xinzeyijia.houselocks.model.bus.LiveDataBus
import com.xinzeyijia.houselocks.ui.adapter.MyFragmentPagerAdapter
import kotlinx.android.synthetic.main.activity_order.*
import kotlinx.android.synthetic.main.fragment_order.*
import kotlinx.android.synthetic.main.fragment_order.tlt
import kotlinx.android.synthetic.main.fragment_order.vpr
import kotlinx.android.synthetic.main.rlt_three_toolbar.*

//订单页
@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
class OrderFragment : BaseFragment<HomePresenter>(){

    private lateinit var titles: MutableList<String>
    private lateinit var mFragment: ArrayList<Fragment>

    private var myFragmentPagerAdapter: MyFragmentPagerAdapter? = null



    override fun initLoad(view: View?) {
        super.initLoad(view)
        initList()
        initAdapter()
        initDatabus()
    }

    private fun initDatabus() {
        LiveDataBus.get().with(Common.SEND_TYPE, BusBean::class.java).observeSticky(
            this,
            observer()
        )
    }

    private fun observer(): Observer<BusBean> {
        return Observer { bus ->
            when (bus!!.type) {
                Common.FRESH_PUB -> {
                    initCallBack()
                }
                Common.FRESH_DETAIL -> {
                    initCallBack()
                }
                Common.ADD_ORDER -> {
                    initCallBack()
                }
                Common.ADVANCE_ORDER -> {
                    initCallBack()
                }
            }
        }
    }

    private fun initCallBack() {
        if (tlt != null) {
            val tab = tlt.currentTab
            when (tab) {
                0 -> {
//                    if (fragment1 != null) {
//                        fragment1!!.callBack()
//                    }
                }
                1 -> {
//                    if (fragment2 != null) {
//                        fragment2!!.callBack()
//                    }
                }
                2 -> {
//                    if (fragment3 != null) {
//                        fragment3!!.callBack()
//                    }
                }
                3 -> {
//                    if (fragment4 != null) {
//                        fragment4!!.callBack()
//                    }
                }
            }
        }
    }

    private fun initList() {
        tv_title.text = "订单"
        back.visibility = View.GONE
        mFragment = arrayListOf()
        titles = mutableListOf()
//        fragment1 = OrderItemFragment("1")
//        fragment2 = OrderItemFragment("6")
//        fragment3 = OrderItemFragment("2")
//        fragment4 = OrderItemFragment("5")

//        mFragment.add(fragment1!!)
//        mFragment.add(fragment2!!)
//        mFragment.add(fragment3!!)
//        mFragment.add(fragment4!!)
//        titles.add("已预订")
//        titles.add("待确认")
//        titles.add("已入住")
//        titles.add("历史订单")

    }

    private fun initAdapter() {
//        myFragmentPagerAdapter = MyFragmentPagerAdapter(childFragmentManager, mFragment, titles)
//        vpr.adapter = myFragmentPagerAdapter
//        tlt.setViewPager(vpr)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_order
    }


}
