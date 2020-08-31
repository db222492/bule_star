package com.xinzeyijia.houselocks.ui.fragment


import android.arch.lifecycle.Observer
import android.os.Build
import android.support.v4.app.Fragment
import android.view.View
import com.vector.update_app.UpdateAppManager
import com.xinzeyijia.houselocks.R
import com.xinzeyijia.houselocks.base.BaseFragment
import com.xinzeyijia.houselocks.base.Common
import com.xinzeyijia.houselocks.base.Common.*
import com.xinzeyijia.houselocks.contract.home.HomePresenter
import com.xinzeyijia.houselocks.model.bean.BaseBean
import com.xinzeyijia.houselocks.model.bean.BusBean
import com.xinzeyijia.houselocks.model.bus.LiveDataBus
import com.xinzeyijia.houselocks.ui.activity.*
import com.xinzeyijia.houselocks.util.ST
import com.xinzeyijia.houselocks.util.UpdateAppHttpUtil
import com.xinzeyijia.houselocks.util.VersionCodeUtil
import com.xinzeyijia.houselocks.utils.ToastUtil
import com.xinzeyijia.houselocks.utils.io.SPUtil
import kotlinx.android.synthetic.main.fragment_mine.*
import com.xgr.easypay.callback.IPayCallback
import com.xgr.easypay.EasyPay
import com.xgr.alipay.alipay.AlipayInfoImpli
import com.xgr.alipay.alipay.AliPay


/**
 * A simple [Fragment] subclass.
 */
@Suppress("UNREACHABLE_CODE")
class MineFragment : BaseFragment<HomePresenter>() {
    private lateinit var districtId: String

    override fun getLayoutId(): Int {
        return R.layout.fragment_mine
    }

    override fun initLoad(view: View?) {
        super.initLoad(view)
        initClick()//初始化监听事件
        initDatabus()
    }

    private fun initDatabus() {
        LiveDataBus.get().with(SEND_TYPE, BusBean::class.java).observeSticky(
            this,
            observer()
        )
    }

    private fun observer(): Observer<BusBean> {
        return Observer { bus ->
            when (bus!!.type) {
                CHECK_TAB -> {
                    initRequest()//刷新TAB
                }
            }
        }
    }


    /**
     * 版本更新
     */

    private fun initUpdate() {
        UpdateAppManager.Builder()
            //当前Activity
            .setActivity(activity)
            //更新地址
            .setUpdateUrl("not null")
//            .topPic=头部图片
            //实现httpManager接口的对象
            .setHttpManager(UpdateAppHttpUtil())
            .build()
            .update()
    }

    private fun initRequest() {

    }

    override fun showData(type: String, data: BaseBean?) {
        super.showData(type, data)
        when (type) {
            Common.reRoomState -> {
                ToastUtil.show("房间状态已更新")
                LiveDataBus.get().with(Common.SEND_TYPE).value = (BusBean(FRESH_PUB, districtId))
            }
            Common.logout -> {
                SPUtil.getInstance().delete(TOKEN)
                ST.startActivity(activity, LoginActivity::class.java)
                finishActivity()
            }
            Common.versionQuerystatus -> {
                val versionCode = data!!.data.version_code
                if (versionCode > VersionCodeUtil.getInstance().versionCode) {
                    initUpdate()
                } else {
                    ToastUtil.show("已是最新版本")
                    tv_hint_version.text = "已是最新版本：V" + VersionCodeUtil.getInstance().versionName
                }
            }
        }


    }

    private fun initClick() {
        img_avatar.setOnClickListener(this)
        tv_set1.setOnClickListener(this)
        tv_set2.setOnClickListener(this)
        tv_set3.setOnClickListener(this)
        tv_set4.setOnClickListener(this)
        tv_set5.setOnClickListener(this)
        tv_set6.setOnClickListener(this)
        tv_set7.setOnClickListener(this)
        tv_set8.setOnClickListener(this)
        tv_set9.setOnClickListener(this)
        tv_login_out.setOnClickListener(this)
        tv_hint_version.text = "V" + VersionCodeUtil.getInstance().versionName
        districtId = SPUtil.getInstance().getString(DISTRICT_ID, "0")
        val userType = SPUtil.getInstance().getString(USER_TYPE, "")
        when (userType) {
            "4" -> {
                tv_set5.visibility = View.GONE
                view5.visibility = View.GONE
                tv_set9.visibility = View.GONE
                view9.visibility = View.GONE
            }
        }
        val phone = SPUtil.getInstance().getString(PHONE)
        tv_title.text = phone

    }

    override fun onClick(v: View?) {
        super.onClick(v)
        when (v!!.id) {
            R.id.img_avatar -> {

            }
            R.id.tv_set1 -> {//小区管理页
//                ST.startActivity(activity, PropertyIdListActivity::class.java)
                finishActivity()
            }
            R.id.tv_set2 -> {//房间发布管理页
//                ST.startActivity(activity, RoomReleaseActivity::class.java)
//                showDeveloping()
            }
            R.id.tv_set3 -> {
//                ST.startActivity(activity, UpdatePasswordActivity::class.java)
            }
            R.id.tv_set5 -> {//我的钱包
//                ST.startActivity(activity, MyMoneyActivity::class.java)
            }
            R.id.tv_set9 -> {//充值管理
//                ST.startActivity(activity, PayMangerActivity::class.java)
            }
            R.id.tv_set4 -> {
                p.showCloseDialog(activity!!, SPUtil.getInstance().get(TEL_PHONE, "") as String)
            }
            R.id.tv_set6 -> {
//                p.reRoomState()
            }
            R.id.tv_set7 -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    ST.startActivity(activity, FingerprintActivity::class.java)
                } else {
                    ToastUtil.show("您的手机暂不支持指纹登录")
                }
            }
            R.id.tv_set8 -> {
//                p.initUpdate()
//                ST.startActivity(activity, PayActivity::class.java)
            }
            R.id.tv_login_out -> {
                if (!isConnect) return//判断是否有网
//                p.logout()//退出登录的回调

            }
        }

    }

}
