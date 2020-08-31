package com.xinzeyijia.houselocks.ui.activity

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.KeyEvent
import android.view.KeyEvent.ACTION_DOWN
import com.jpeng.jptabbar.OnTabSelectListener
import com.vector.update_app.UpdateAppManager
import com.xinzeyijia.houselocks.base.BaseActivity
import com.xinzeyijia.houselocks.base.Common
import com.xinzeyijia.houselocks.contract.home.HomeContract
import com.xinzeyijia.houselocks.contract.home.HomePresenter
import com.xinzeyijia.houselocks.model.bean.BusBean
import com.xinzeyijia.houselocks.model.bus.LiveDataBus
import com.xinzeyijia.houselocks.ui.fragment.HomeFragment
import com.xinzeyijia.houselocks.ui.fragment.LockFragment
import com.xinzeyijia.houselocks.ui.fragment.MineFragment
import com.xinzeyijia.houselocks.ui.fragment.OrderFragment
import com.xinzeyijia.houselocks.ui.manager.FragmentChangeManager
import com.xinzeyijia.houselocks.util.UpdateAppHttpUtil
import com.xinzeyijia.houselocks.utils.io.SPUtil
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : BaseActivity<HomePresenter>(), OnTabSelectListener, HomeContract.View {

    private var isRemoveVillage: Boolean = false
    private var mFragmentChangeManager: FragmentChangeManager? = null
    private var fragment1: HomeFragment? = null
    private var fragment2: OrderFragment? = null
    private var fragment3: LockFragment? = null
    private var fragment4: MineFragment? = null
    private var firstTime: Long = 0
    override fun onInterruptSelect(index: Int): Boolean {
        return false
    }

    override fun onTabSelect(index: Int) {
        mFragmentChangeManager!!.setFragments(index)
        LiveDataBus.get().with(Common.SEND_TYPE).value = (BusBean(Common.CHECK_TAB, index))

    }


    private lateinit var list: MutableList<Fragment>

    override fun getLayoutId(): Int {
        return com.xinzeyijia.houselocks.R.layout.activity_home
    }


    @SuppressLint("MissingSuperCall")
    override fun onSaveInstanceState(outState: Bundle) {
        //如果用以下这种做法则不保存状态，再次进来的话会显示默认tab
        //总是执行这句代码来调用父类去保存视图层的状态
        //super.onSaveInstanceState(outState);
    }


    override fun initView() {
        super.initView()
        val login_type = SPUtil.getInstance().get(
            Common.LOGIN_TYPE,
            ""
        ) as String
        if (login_type == "2") {
            tabBar.setTitles("房态管理")
                .setNormalIcons(
                    com.xinzeyijia.houselocks.R.mipmap.room_status_unselect
                )
                .setSelectedIcons(
                    com.xinzeyijia.houselocks.R.mipmap.room_status_select
                ).generate()
        } else {
            tabBar.setTitles("房态管理", "订单管理", "门锁管理", "我的")
                .setNormalIcons(
                    com.xinzeyijia.houselocks.R.mipmap.room_status_unselect,
                    com.xinzeyijia.houselocks.R.mipmap.order_unselect,
                    com.xinzeyijia.houselocks.R.mipmap.lock_unselect,
                    com.xinzeyijia.houselocks.R.mipmap.my_select
                )
                .setSelectedIcons(
                    com.xinzeyijia.houselocks.R.mipmap.room_status_select,
                    com.xinzeyijia.houselocks.R.mipmap.order_select,
                    com.xinzeyijia.houselocks.R.mipmap.lock_select,
                    com.xinzeyijia.houselocks.R.mipmap.my_unselect
                ).generate()
        }

        list = ArrayList()
        fragment1 = HomeFragment()
        fragment2 = OrderFragment()
        fragment3 = LockFragment()
        fragment4 = MineFragment()
        list.add(fragment1!!)
        list.add(fragment2!!)
        list.add(fragment3!!)
        list.add(fragment4!!)
        mFragmentChangeManager = FragmentChangeManager(supportFragmentManager, com.xinzeyijia.houselocks.R.id.flt, list)
        tabBar.setTabListener(this)
        initDatabus()

    }

    /**
     * 版本更新
     */

    private fun initUpdate() {
        UpdateAppManager.Builder()
            //当前Activity
            .setActivity(this)
            //更新地址
            .setUpdateUrl("not null")
//            .topPic=头部图片
            //实现httpManager接口的对象
            .setHttpManager(UpdateAppHttpUtil())
            .build()
            .update()
    }

    private fun initDatabus() {
        LiveDataBus.get().with(Common.SEND_TYPE, BusBean::class.java).observeSticky(
            this,
            observer()
        )
    }

    private fun observer(): Observer<BusBean> {
        return Observer {
            when (it!!.type) {
                Common.REMOVE_VILLAGE -> {
                    val t = it.t.toString()
                    if (t == "1") {
                        isRemoveVillage = true
                        if (mFragmentChangeManager != null) {
//                    vpr!!.currentItem = 3
                            mFragmentChangeManager?.setFragments(3)
                            tabBar?.setSelectTab(3)
                        }
                    } else {
                        isRemoveVillage = false
                    }
                    if (!isRemoveVillage) {
                        initUpdate()
                    }
                }
            }
        }
    }

    /**
     * 第二种办法
     * * @param keyCode
     * * @param event
     * * @return
     */
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.action == ACTION_DOWN) {
            val secondTime = System.currentTimeMillis()
            if (secondTime - firstTime > 2000) {
                makeToast(com.xinzeyijia.houselocks.R.string.press_exit_again)
                firstTime = secondTime
                return true
            } else {
                finish()
            }
        }
        return super.onKeyDown(keyCode, event)
    }

}
