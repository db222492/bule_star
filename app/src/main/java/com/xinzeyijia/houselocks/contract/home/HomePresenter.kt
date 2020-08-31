package com.xinzeyijia.houselocks.contract.home


import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.xinzeyijia.houselocks.App
import com.xinzeyijia.houselocks.R
import com.xinzeyijia.houselocks.base.Common
import com.xinzeyijia.houselocks.base.Common.*
import com.xinzeyijia.houselocks.base.Constants
import com.xinzeyijia.houselocks.base.Constants.SP_A_P
import com.xinzeyijia.houselocks.model.bean.BaseBean
import com.xinzeyijia.houselocks.model.bean.CommonBean
import com.xinzeyijia.houselocks.model.bean.DataBean
import com.xinzeyijia.houselocks.model.http.RxSchedulers
import com.xinzeyijia.houselocks.model.http.Urls.getHomeApiService
import com.xinzeyijia.houselocks.util.LogUtils.loge
import com.xinzeyijia.houselocks.util.date_util.TimeUtils
import com.xinzeyijia.houselocks.util.popuw.PWUtil
import com.xinzeyijia.houselocks.utils.RegexUtil
import com.xinzeyijia.houselocks.utils.ToastUtil
import com.xinzeyijia.houselocks.utils.encrypt.MD5Util
import com.xinzeyijia.houselocks.utils.io.SPUtil
import com.xinzeyijia.houselocks.utils.time.CountdownUtil
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.util.*

/**
 * Created by TMVPHelper on 2019/06/12
 */
@SuppressLint("CheckResult")
open class HomePresenter : HomeContract.Presenter() {
    override fun onAttached() {

    }


    override fun login(
        name: String,
        phone: String,
        password: String,
        code: String
    ) {

        val map = HashMap<String, Any>()
        map["password"] = password
        map["code"] = code
        if (phone.isNotEmpty()) {
            when (phone.length) {
                11 -> {//手机号
                    map["phone"] = phone
                    if (RegexUtil.isMobileExact(phone)) {
                        map["istype"] = "1"
                        SPUtil.getInstance().put(Common.LOGIN_TYPE, "1")
                    } else {
                        v.showMessage("手机号输入有误")
                    }
                }
                14 -> {//企业代码
                    map["istype"] = "2"
                    map["name"] = phone
                    SPUtil.getInstance().put(Common.LOGIN_TYPE, "1")
                }
                10 -> {//民宿PC代码
                    map["istype"] = "2"
                    map["name"] = phone
                    SPUtil.getInstance().put(Common.LOGIN_TYPE, "2")//用来控制首页右上角的弹窗
                    childLogin(map, phone)
                }
                else -> {
                    childLogin(map, phone)
                }
            }
        } else {
            v.showMessage("账号不能为空")
            return
        }
        if (password.isEmpty()) {
            v.showMessage("您输入的密码有误")
            return
        }
        val stringBuffer = StringBuffer()
        stringBuffer.append(phone)
        stringBuffer.append(password)
        val username = SPUtil.getInstance().getString(SP_A_P, "")
        if (SPUtil.getInstance().getBoolean(Constants.SP_HAD_OPEN_FINGERPRINT_LOGIN) && username.isNotEmpty() && username != MD5Util.md5Password(
                stringBuffer.toString()
            )
        ) {
            ToastUtil.show("您已更换账户，需要重新录入指纹")
            SPUtil.getInstance().put(Constants.SP_HAD_OPEN_FINGERPRINT_LOGIN, false)
        }
        PWUtil.getInstace().showDialog()
        mc.add(
            getHomeApiService().login(map).compose(RxSchedulers.io_main())
                .subscribe({ baseBean ->
                    initDataType(baseBean, Common.login, false, false, true, false)
                },
                    { throwable ->
                        showError(Common.login, throwable)
                    })
        )

    }


    private fun showError(type: String, throwable: Throwable) {
        when (throwable) {
            is HttpException -> {
                val t = throwable
                when (t.code()) {
                    504 -> {
                        v.showMessage("网络已断开,请检查网络!")
                    }
                    502 -> {
                        v.showMessage("正在发版,请联系后台!")
                    }
                    else -> {
                        v.showMessage("请联系后台!" + errorMessage(throwable))
                    }
                }
            }
            is SocketTimeoutException -> v.showMessage("请联系后台!" + errorMessage(throwable))
            else -> if (errorMessage(throwable) == "timeout") {
                v.showMessage("请联系后台!" + throwable.message)
            }
        }
        loge(type, "请联系后台!" + errorMessage(throwable))
    }

    private fun errorMessage(throwable: Throwable) =
        if (throwable.message.isNullOrEmpty()) "" else throwable.message


    private fun childLogin(map: HashMap<String, Any>, phone: String) {
        if (RegexUtil.isUsername(phone)) {
            map["istype"] = "4"
            map["name"] = phone
            SPUtil.getInstance().put(LOGIN_TYPE, "1")
        }
    }


    override fun getCode(phone: String) {
        val map = HashMap<String, Any>()
        if (phone.isEmpty() || !RegexUtil.isMobileExact(phone)) {
            v.showMessage("您输入的手机号有误")
            return
        } else {
            map["mobile"] = phone
            getHomeApiService().getCode(map).compose(RxSchedulers.io_main())
                .subscribe({ baseBean ->
                    initDataType(baseBean, Common.getCode, false, false, false, false)
                },
                    { throwable ->
                        showError(Common.getCode, throwable)
                    })
        }
    }

    private var countdownUtil: CountdownUtil? = null
    fun initTimeDown(tv_get_code: TextView): CountdownUtil? {
        countdownUtil = CountdownUtil.newInstance()
        countdownUtil!!.totalTime(60000)
            .intervalTime(1000)
            .callback(object : CountdownUtil.OnCountdownListener {
                @SuppressLint("SetTextI18n")
                override fun onRemain(millisUntilFinished: Long) {
                    val time = millisUntilFinished / 1000
                    tv_get_code.text = "重新发送(" + time.toString() + "s)"
                    tv_get_code.isClickable = false
                }

                override fun onFinish() {
                    tv_get_code.text = "重新获取验证码"
                    tv_get_code.isClickable = true
                }
            }).start()
        return countdownUtil
    }


    fun showCloseDialog(homeActivity: Activity, subTitle: String) {
        /**
         * 弹出是否拨打电话
         */
        PWUtil.getInstace().defPW(0, homeActivity)
            .addContent("是否拨打电话：$subTitle")
            .addNoListener { v -> PWUtil.getInstace().dissmissPW() }.addYesListener { v ->
                callPhone(homeActivity, subTitle)
                PWUtil.getInstace().dissmissPW()
            }.build()


    }


    /**
     * 拨打电话（跳转到拨号界面，用户手动点击拨打）
     *
     * @param activity
     * @param phoneNum 电话号码
     */

    fun callPhone(activity: Activity, phoneNum: String) {
        val intent = Intent(Intent.ACTION_DIAL)
        val data = Uri.parse("tel:$phoneNum")
        intent.data = data
        activity.startActivity(intent)
    }



    private fun initDataType(
        baseBean: BaseBean,
        typeData: String,
        is_show_message: Boolean,
        isFinish: Boolean,
        isSave: Boolean,
        is_null_data: Boolean
    ) {
        when {
            baseBean.status == SUCCESS_DATA -> {
                if (isSave) {
                    SPUtil.getInstance().put(TOKEN, baseBean.data.token)
                }
                v.showData(typeData, baseBean)
                if (is_show_message) v.showMessage(baseBean.message)
            }

            baseBean.status == START_LOGIN -> {
                if (is_null_data) v.showData(typeData, null)
                SPUtil.getInstance().delete(TOKEN)
                v.startLogin()
            }
            baseBean.status == LOGIN_USER_BANNED -> {
                v.showMessage(baseBean.message)
            }
            baseBean.status == FILED -> {
                v.showMessage(baseBean.message)
            }
            baseBean.status == NO_USER -> {
                v.showMessage(baseBean.message)
                v.startRegist()
            }
            baseBean.status == NO_TOKEN -> {
                v.showMessage(baseBean.message)
                SPUtil.getInstance().delete(TOKEN)
                v.startLogin()
            }
            baseBean.status == NO_TOKEN2 -> {
                v.showMessage(baseBean.message)
                SPUtil.getInstance().delete(TOKEN)
                v.startLogin()
            }
            baseBean.status == LOGIN_USER_ANOTHOER_PLACE_ONLINE -> {
                v.showMessage(baseBean.message)
                SPUtil.getInstance().delete(TOKEN)
                v.startLogin()
            }
            baseBean.status == WX_LOGIN_BIND_MOBILE -> v.startBind()
            baseBean.status == EXCEPTION -> {
                v.showMessage("请联系后台！" + baseBean.status)
                loge(typeData, "请联系后台！" + baseBean.status)
            }
            baseBean.status == ERROR -> {
                v.showMessage("请联系后台！" + baseBean.status)
                loge(typeData, "请联系后台！" + baseBean.status)
            }
            baseBean.status == -6 -> {//未查询到门锁id
                v.showMessage("未查询到门锁id，请到修改管理员密码页，同步门锁id")
                loge(typeData, "请联系后台！" + baseBean.status)
            }
            baseBean.status == SEVER_STOP -> {
                v.showMessage(baseBean.message)
                loge(typeData, baseBean.message)
            }
            baseBean.status == -1 -> {
                v.showMessage(baseBean.message)
            }
            baseBean.status == -96 -> {
                v.showMessage(baseBean.message)
            }
            else -> {
                v.showMessage(baseBean.message)
                loge(typeData, baseBean.message)
            }
        }
    }


    /*
     * 时间选择器
     */
    fun showStartTimePicker(
        activity: Activity
        , tv_start_time: TextView
        , type: String
    ): Long {

        val pvTime = TimePickerBuilder(activity) { date, v ->
            val dataBean = DataBean()
            dataBean.crt_time = date.time.toString()
            App.getmInstance().saveDataBean(dataBean)
            when (type) {
                Offline -> {
                    val startTimeStr = TimeUtils.date2String(date, TimeUtils.HOUR_SDF_CD)
                    tv_start_time.text = "起始日 $startTimeStr "
                }
                AddOrder -> {
                    val startTimeStr = TimeUtils.date2String(date, TimeUtils.HOUR_SDF_CD)
                    tv_start_time.text = startTimeStr
                }
            }
        }.setType(booleanArrayOf(false, true, true, true, true, false))
            .isDialog(false)//默认设置false ，内部实现将DecorView 作为它的父控件。
            .build()

        val mDialog = pvTime.getDialog()
        if (mDialog != null) {
            val params = FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                Gravity.BOTTOM
            )
            params.leftMargin = 0
            params.rightMargin = 0
            pvTime.getDialogContainerLayout().setLayoutParams(params)
            val dialogWindow = mDialog.getWindow()
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(R.style.picker_view_slide_anim)//修改动画样式
                dialogWindow.setGravity(Gravity.BOTTOM)//改成Bottom,底部显示
            }
        }

        pvTime.show()
        return 0
    }


    /*
            * 时间选择器
            */
    @SuppressLint("NewApi")
    fun showEndTimePicker(
        activity: Activity
        , tv_end_time: TextView
        , type: String
    ) {
        val pvTime = TimePickerBuilder(activity) { date, v ->
            val dataBean = DataBean()
            dataBean.upd_time = date.time.toString()
            App.getmInstance().saveDataBean(dataBean)
            when (type) {
                Offline -> {
                    val endTimePicker = TimeUtils.date2String(date, TimeUtils.HOUR_SDF_CD)
                    tv_end_time.text = "结束日 $endTimePicker "
                }
                AddOrder -> {
                    val endTimePicker = TimeUtils.date2String(date, TimeUtils.HOUR_SDF_CD)
                    tv_end_time.text = endTimePicker
                }
            }

        }.setType(booleanArrayOf(false, true, true, true, true, false))
            .isDialog(false)//默认设置false ，内部实现将DecorView 作为它的父控件。
            .build()

            .show()
    }


}