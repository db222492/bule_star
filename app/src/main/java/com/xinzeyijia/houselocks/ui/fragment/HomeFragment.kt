package com.xinzeyijia.houselocks.ui.fragment

import android.annotation.TargetApi
import android.app.ProgressDialog
import android.arch.lifecycle.Observer
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.xinzeyijia.houselocks.R
import com.xinzeyijia.houselocks.base.BaseFragment
import com.xinzeyijia.houselocks.base.Common
import com.xinzeyijia.houselocks.contract.home.HomeContract
import com.xinzeyijia.houselocks.contract.home.HomePresenter
import com.xinzeyijia.houselocks.model.bean.BaseBean
import com.xinzeyijia.houselocks.model.bean.BusBean
import com.xinzeyijia.houselocks.model.bean.DataBean
import com.xinzeyijia.houselocks.model.bus.LiveDataBus
import com.xinzeyijia.houselocks.ui.activity.LoginActivity
import com.xinzeyijia.houselocks.util.ST
import com.xinzeyijia.houselocks.util.popuw.PWUtil
import com.xinzeyijia.houselocks.utils.DensityUtil
import com.xinzeyijia.houselocks.utils.io.SPUtil
import kotlinx.android.synthetic.main.popup_home_help.view.*
import kotlinx.android.synthetic.main.rlt_three_toolbar.*

//房态页
@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
class HomeFragment : BaseFragment<HomePresenter>(), HomeContract.View {
    override fun timeOut() {
        super.timeOut()
        if (progressDialog != null) progressDialog!!.dismiss()
    }

    override fun mClick(v: View?) {
        super.mClick(v)
        when (v!!.id) {
            R.id.img_right_icon -> {
                showPop()
            }
            R.id.tv_1 -> {//添加房间
//                ST.startActivity(activity, AddPropertyIdActivity::class.java)
//                PWUtil.getInstace().dissmissPW()
            }
            R.id.tv_2 -> {//添加房间
//                val intent = Intent(activity, AddRoomActivity::class.java)
//                intent.putExtra("type", "add")
//                ST.startActivity(activity, intent)
//                PWUtil.getInstace().dissmissPW()
            }
            R.id.tv_3 -> {//刷新房间
//                p.refreshHotel()
                progressDialog!!.show()
                PWUtil.getInstace().dissmissPW()
            }
            R.id.tv_4 -> {//退出登录
//                p.logout()
            }
            R.id.tv_5 -> {//操作员管理
//                ST.startActivity(activity, ChildAccountActivity::class.java)
            }
        }
    }

    private var progressDialog: ProgressDialog? = null
    private var isRefreshHotel: Boolean = false

    private var villageList: List<DataBean> = arrayListOf();
    override fun initLoad(view: View?) {
        super.initLoad(view)
        initData()
        initRequest()
        initClick()
        initDatabus()
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
                Common.FRESH_PUB -> {
                    initRequest()//刷新TAB
                }
                Common.ADD_VILLAGE -> {
                    initRequest()//添加房间更新
                }
                Common.CHECK_TAB -> {
                    val i = it.t as Int
                    if (i == 0) {
                        initRequest()//刷新TAB
                    }
                }

            }
        }
    }

    private fun initClick() {
        img_right_icon.setOnClickListener(this)
    }

    /**
     * 右上角帮助
     */
    private fun showPop() {
        val defPW = PWUtil.getInstace()
            .makePopupWindow(
                activity,
                R.layout.popup_home_help,
                DensityUtil.dp2px(160f),
                -2,
                true
            )
        val login_type = SPUtil.getInstance().get(
            Common.LOGIN_TYPE,
            ""
        ) as String
        val userType = SPUtil.getInstance().getString(Common.USER_TYPE, "")
        val view = defPW.popupWindow.contentView
        if (login_type == "2") {
            defPW.popupWindow.height = DensityUtil.dp2px(120f)
            view.tv_1.visibility = View.GONE
            view.tv_2.visibility = View.GONE
            view.tv_5.visibility = View.GONE
            view.tv_3.visibility = View.VISIBLE
            view.tv_3.text = "更新房间"
        }
        when (userType) {
            "4" -> {
                defPW.popupWindow.height = DensityUtil.dp2px(200f)
                view.tv_5.visibility = View.GONE
                val layoutParams = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    DensityUtil.dp2px(50f)
                )  //定义一个LayoutParams
                layoutParams.setMargins(DensityUtil.dp2px(20f), DensityUtil.dp2px(20f), DensityUtil.dp2px(20f), 0)
                view.tv_1.layoutParams = layoutParams
            }
        }
        defPW.showLocationWithAnimation(R.style.pop_add)
            .showAsDropDown(img_right_icon, -50, DensityUtil.dp2px(10f))
        view.tv_1.setOnClickListener(this)
        view.tv_2.setOnClickListener(this)
        view.tv_3.setOnClickListener(this)
        view.tv_4.setOnClickListener(this)
        view.tv_5.setOnClickListener(this)
    }

    private fun initRequest() {
//        p.getVillageList()
    }


    override fun showData(type: String, data: BaseBean?) {
        super.showData(type, data)
        when (type) {
            Common.getVillageList -> {
//                if (data!!.data.village_list.isEmpty()) {
//                    ToastUtil.show("您还没有自己的小区，请先创建小区")
//                    val login_type = SPUtil.getInstance().get(
//                        Common.LOGIN_TYPE,
//                        ""
//                    ) as String
//                    if (login_type != "2") {
////                        ST.startActivity(activity, AddPropertyIdActivity::class.java)
//                    }
//                } else {
//                    villageList = data.data.village_list
//                    if (mpr == null) {
//                        mpr = NoteStarAdapter(childFragmentManager, villageList)
//                        vpr.adapter = mpr
//                        tlt.setViewPager(vpr)
//                    } else {
//                        mpr!!.setNewData(villageList)
//                        tlt.notifyDataSetChanged()
//                    }
//                }
            }
            Common.refreshHotel -> {
                isRefreshHotel = true
                progressDialog!!.dismiss()
                LiveDataBus.get().with(Common.SEND_TYPE).value = (BusBean(Common.REFRESH_HOTEL, ""))
            }
            Common.logout -> {
                SPUtil.getInstance().delete(Common.TOKEN)
                ST.startActivity(activity, LoginActivity::class.java)
                finishActivity()
            }
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    private fun initData() {
        tv_title.text = "房态盘"
        img_right_icon.visibility = View.VISIBLE
        back.visibility = View.GONE
        progressDialog = ProgressDialog(activity)
        progressDialog!!.setCancelable(false)
        progressDialog!!.setMessage("正在刷新请稍等...")

    }


}

