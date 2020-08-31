package com.xinzeyijia.houselocks.ui.fragment

import android.annotation.TargetApi
import android.arch.lifecycle.Observer
import android.os.Build
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.ArrayAdapter
import android.widget.PopupWindow
import com.xinzeyijia.houselocks.App
import com.xinzeyijia.houselocks.R
import com.xinzeyijia.houselocks.base.BaseActivity
import com.xinzeyijia.houselocks.base.BaseFragment
import com.xinzeyijia.houselocks.base.Common
import com.xinzeyijia.houselocks.base.Common.JWT_V1
import com.xinzeyijia.houselocks.contract.home.HomePresenter
import com.xinzeyijia.houselocks.model.bean.BaseBean
import com.xinzeyijia.houselocks.model.bean.BusBean
import com.xinzeyijia.houselocks.model.bean.DataBean
import com.xinzeyijia.houselocks.model.bus.LiveDataBus
import com.xinzeyijia.houselocks.ui.adapter.LockAdapter
import com.xinzeyijia.houselocks.util.DefIconUtil
import com.xinzeyijia.houselocks.util.Tools
import com.xinzeyijia.houselocks.util.popuw.PWUtil
import com.xinzeyijia.houselocks.utils.ToastUtil
import com.xinzeyijia.houselocks.utils.io.SPUtil
import kotlinx.android.synthetic.main.fragment_lock.*
import kotlinx.android.synthetic.main.recyclerview_item.*
import kotlinx.android.synthetic.main.rlt_toolbar.img_right_icon
import org.angmarch.views.NiceSpinner

//房态页
@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
class LockFragment : BaseFragment<HomePresenter>(), View.OnClickListener {
    private var lockTypeEn: String = ""
    private var item: DataBean? = null
    private lateinit var districtId: String
    private var rows: MutableList<DataBean>? = null
    private var page: Int = 1
    private var mPosition: Int = 0
    private var villageType: List<DataBean>? = null
    private var mPopupWindow: PopupWindow? = null
    private var lockAdapter: LockAdapter? = null
    private var spinnerId: Int = 0
    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.tv_search -> {
//                ST.startActivity(activity, SearchRoomActivity::class.java)
            }
        }
    }


    override fun initLoad(view: View?) {
        super.initLoad(view)
        img_right_icon.visibility = View.GONE
        districtId = SPUtil.getInstance().getString(Common.DISTRICT_ID, "0")
        initData()
        initAdapter()
        initItemListener()
        initRefresh()
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
        return Observer { bus ->
            when (bus!!.type) {
                Common.FRESH_PUB -> {
                    initRequest()
                }
                Common.FRESH_DETAIL -> {
                    initRequest()
                }
                Common.ADD_ORDER -> {
                    initRequest()
                }
                Common.ADVANCE_ORDER -> {
                    initRequest()
                }
                Common.CHECK_TAB -> {
                    if (bus.t as Int == 2) {
//                        p.getVillageList()
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        PWUtil.getInstace().destoryDialog()
    }

    override fun showMessage(message: String?) {
        super.showMessage(message)
        PWUtil.getInstace().disDialog()
    }

    private fun setStoreDefeltInfo(data: List<DataBean>?) {
        val mData = ArrayList<String>()
        data!!.forEach {
            mData.add(it.village_name)
        }
        val adapter = ArrayAdapter(activity!!, android.R.layout.simple_spinner_item, mData)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.setAdapter(adapter)
        spinner.setOnSpinnerItemSelectedListener { parent: NiceSpinner?, view: View?, position: Int, id: Long ->
            spinnerId = data[position].id
            mPosition = position
            page = 1
            if (refreshLayout != null)
                refreshLayout.isEnableLoadMore = true
//            p.getRoomList(spinnerId.toString(), page)
            PWUtil.getInstace().makeDialog(activity, false, "信息正在加载请稍候...").showDialog()
        }
        spinner.selectedIndex = mPosition
        initRequest()
    }

    private fun initItemListener() {
        lockAdapter!!.setOnItemClickListener { adapter, view, position ->
//            SPUtil.getInstance().put(Common.roomId, lockAdapter!!.data[position].id)
//            LiveDataBus.get().with(Common.SEND_TYPE).value =
//                (BusBean(Common.DATA_BEAN, lockAdapter!!.data[position]))
//            ST.startActivity(activity, RoomDetailActivity::class.java)
        }
        lockAdapter!!.setOnItemChildClickListener { adapter, view, position ->
            item = lockAdapter!!.data[position]
            SPUtil.getInstance().put(Common.roomId, item!!.id)
            App.getmInstance().saveDataBean(item)
            when (view.id) {
                R.id.tv_bind_room -> {
                    if (!BaseActivity.isDoubleClick()) {
                        initUnBind(item!!)//去绑定,解除绑定
                    } else {
                        ToastUtil.show("请勿重复点击")
                    }
                }
                R.id.tv_open_lock -> {
                    if (!BaseActivity.isDoubleClick()) {
//                        val intent = Intent(
//                            activity,
//                            LockOpenActivity::class.java
//                        )
//                        intent.putExtra(Common.roomId, item!!.id)
//                        ST.startActivity(
//                            activity,
//                            intent
//                        )
                    } else {
                        ToastUtil.show("请勿重复点击")
                    }
                }
            }

        }
    }

    private fun initUnBind(item: DataBean) {
        when (item.isbangble) {
            "0" -> {
//                ST.startActivity(
//                    activity,
//                    BlueTouthMakersActivity::class.java
//                )
            }
            "1" -> {
                PWUtil.getInstace().makeDialog(activity, false, "信息正在加载请稍候...").showDialog()
//                p.getRoomble(item!!.id)
            }
        }
    }

    private fun initClick() {
        img_right_icon.setOnClickListener(this)
        tv_search.setOnClickListener(this)
    }


    private fun initRequest() {
        if (Tools.NotNull(villageType)) {
            page = 1
            if (refreshLayout != null)
                refreshLayout.isEnableLoadMore = true
//            p.getRoomList(villageType!![mPosition].id.toString(), page)
        }
    }


    private fun initAdapter() {
        refreshLayout.isEnableLoadMore = false
        rcw.layoutManager = LinearLayoutManager(activity)
        lockAdapter = LockAdapter()
        rcw.adapter = lockAdapter
    }

    private fun initRefresh() {
        refreshLayout.setOnRefreshListener { refreshLayout ->
            refreshLayout.layout.postDelayed({
                DefIconUtil.instance.getNoNetIcon(
                    multiple_status_view!!,
                    lockAdapter!!,
                    R.layout.def_no_room_data
                )//初始化没有网的情况下的空试图
                refreshLayout.isEnableLoadMore = true
                initRequest()
                refreshLayout.finishRefresh()
            }, 100)
        }
        refreshLayout.setOnLoadMoreListener { refreshLayout ->
            refreshLayout.layout.postDelayed({
                if (rows!!.size < Common.LIMIT) {
                    refreshLayout.isEnableLoadMore = false
                } else {
                    refreshLayout.isEnableLoadMore = true
//                    p.getRoomList(villageType!![mPosition].id.toString(), page)
                }
                refreshLayout.finishLoadMore()
            }, 100)
        }
    }

    override fun showData(type: String, data: BaseBean?) {
        super.showData(type, data)
        when (type) {
            Common.getRoomList -> {
                val bean = data!!.data
                PWUtil.getInstace().disDialog()
                if (bean.currentPage == 1)
                    lockAdapter!!.data.clear()
                rows = bean.rows
                lockAdapter!!.addData(rows!!)
                page++
                DefIconUtil.instance.getNoDataIcon(
                    multiple_status_view!!,
                    lockAdapter!!,
                    R.layout.def_no_room_data
                )//设置网络请求后的占位图
            }
            Common.isHaveOrder -> {
                PWUtil.getInstace().disDialog()
                if (data!!.data.orderState == "0") {
                    when (lockTypeEn) {
                        JWT_V1 -> {
                            PWUtil.getInstace().defPW(0, activity).build()
                                .addTitle("确认要解绑门锁吗？")
                                .addYesListener {
//                                    p.unBindLock(item!!.id)
                                    PWUtil.getInstace().dissmissPW()
                                }
                                .addNoListener {
                                    PWUtil.getInstace().dissmissPW()
                                }

                        }
                        else -> {
//                            val intent = Intent(
//                                activity,
//                                LockUnBindActivity::class.java
//                            )
//                            intent.putExtra(Common.roomId, item!!.id)
//                            ST.startActivity(activity, intent)
                        }
                    }

                } else {
                    ToastUtil.show("此房间有订单不能解绑门锁")
                }

            }
            Common.getRoomble -> {
                val body = data!!.data
                lockTypeEn = body.lock_type_en
//                p.isHaveOrder(item!!.id)

            }
            Common.unBindLock -> {
                ToastUtil.show("解绑成功")
                LiveDataBus.get().with(Common.SEND_TYPE).value = (BusBean(Common.FRESH_PUB, districtId))
            }

            Common.getVillageList -> {
                villageType = data!!.data.village_list
                when {
                    villageType!!.size > 1 -> {
                        spinner.isFocusable = true
                        spinner.isEnabled = true
                        setStoreDefeltInfo(villageType)
                    }
                    villageType!!.size == 1 -> {
                        spinner.isFocusable = false
                        spinner.isEnabled = false
                        setStoreDefeltInfo(villageType)
                    }
                    else -> {
                        ToastUtil.show("暂无数据")
                        DefIconUtil.instance.getNoNetIcon(
                            multiple_status_view!!,
                            lockAdapter!!,
                            R.layout.def_no_room_data
                        )//初始化没有网的情况下的空试图
                    }
                }


            }
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_lock
    }

    private fun initData() {
        mPopupWindow = PopupWindow(activity)
    }


}
