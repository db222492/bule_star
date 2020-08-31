package com.xinzeyijia.houselocks.ui.activity

import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import android.security.keystore.KeyProperties
import android.text.TextUtils
import android.view.View
import com.vector.update_app.UpdateAppManager
import com.xinzeyijia.houselocks.R
import com.xinzeyijia.houselocks.base.BaseActivity
import com.xinzeyijia.houselocks.base.Common
import com.xinzeyijia.houselocks.base.Common.*
import com.xinzeyijia.houselocks.base.Constants
import com.xinzeyijia.houselocks.contract.home.HomePresenter
import com.xinzeyijia.houselocks.model.bean.BaseBean
import com.xinzeyijia.houselocks.model.bean.BusBean
import com.xinzeyijia.houselocks.model.bus.LiveDataBus
import com.xinzeyijia.houselocks.model.fingerprint.FingerprintHelper
import com.xinzeyijia.houselocks.model.widget.CommonTipDialog
import com.xinzeyijia.houselocks.model.widget.FingerprintVerifyDialog
import com.xinzeyijia.houselocks.util.FingerprintUtil
import com.xinzeyijia.houselocks.util.LogUtils.loge
import com.xinzeyijia.houselocks.util.ST
import com.xinzeyijia.houselocks.util.UpdateAppHttpUtil
import com.xinzeyijia.houselocks.util.popuw.PWUtil
import com.xinzeyijia.houselocks.utils.ToastUtil
import com.xinzeyijia.houselocks.utils.encrypt.MD5Util
import com.xinzeyijia.houselocks.utils.io.SPUtil
import kotlinx.android.synthetic.main.lagin_activity.*


class LoginActivity : BaseActivity<HomePresenter>(), FingerprintHelper.SimpleAuthenticationCallback {
    private var edAccountNumber: String = ""
    private var edPassword: String = ""
    private var helper: FingerprintHelper? = null
    private var fingerprintVerifyDialog: FingerprintVerifyDialog? = null
    private var fingerprintVerifyErrorTipDialog: CommonTipDialog? = null
    private var fingerprintChangeTipDialog: CommonTipDialog? = null
    private var isCancle: Boolean = false
    private var openFingerprintLoginTipDialog: CommonTipDialog? = null
    private var isOpen: Boolean = false
    override fun getLayoutId(): Int {
        return R.layout.lagin_activity
    }

    override fun onDestroy() {
        super.onDestroy()
        PWUtil.getInstace().destoryDialog()
    }

    override fun initView() {
        super.initView()

        val isRemember = SPUtil.getInstance().get(Common.remember_password, false) as Boolean

        if (isRemember) {
            //将账号密码复制到文本框中
            val username = SPUtil.getInstance().get(Common.user_name, "") as String
            val password = SPUtil.getInstance().get(Common.user_psw, "") as String
            ed_account_number.setText(username)
            ed_password.setText(password)
            cbx_save.isChecked = true
        }

        PWUtil.getInstace().makeDialog(this, false, "正在登录请稍候...")

        val token: String = SPUtil.getInstance().get(Common.TOKEN, "").toString()
        if (!TextUtils.isEmpty(token)) {
            startHomeActivity()
        }
        cbx_save.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {//检查复选框是否被选中
                val edAccountNumber = ed_account_number.text.toString()
                val edPassword = ed_password.text.toString()
                when {
                    edAccountNumber.isEmpty() -> {
                        ToastUtil.show("请输入账号")
                        cbx_save.isChecked = false
                    }
                    edPassword.isEmpty() -> {
                        ToastUtil.show("请输入密码")
                        cbx_save.isChecked = false
                    }
                    else -> {
                        SPUtil.getInstance().put(Common.remember_password, true)
                        SPUtil.getInstance().put(Common.user_name, edAccountNumber)
                        SPUtil.getInstance().put(Common.user_psw, edPassword)
                    }
                }
            } else {
                SPUtil.getInstance().clear()
            }
        }
        initFingerprint()//初始化指纹验证
    }


    private fun initFingerprint() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            helper = FingerprintHelper.getInstance()
            helper!!.init(applicationContext)
            helper!!.setCallback(this)
            if (SPUtil.getInstance().getBoolean(Constants.SP_HAD_OPEN_FINGERPRINT_LOGIN)) {//只有开启指纹登录的时候才会验证
                tv_landing.postDelayed({
                    openFingerprintLogin()
                }, 500)
            }
        }
    }

    private fun showFingerprintChangeTipDialog() {
        if (fingerprintChangeTipDialog == null) {
            fingerprintChangeTipDialog = CommonTipDialog(this)
        }
        fingerprintChangeTipDialog!!.apply {
            setContentText(resources.getString(R.string.fingerprintChangeTip))
            setSingleButton(true)
            setOnSingleConfirmButtonClickListener {
                SPUtil.getInstance().put(Constants.SP_HAD_OPEN_FINGERPRINT_LOGIN, false)
                isOpen = false
                helper!!.closeAuthenticate()
            }
            show()
        }
    }


    /**
     * @description 开启指纹登录功能
     * @author HaganWu
     * @date 2019/1/29-10:20
     */
    private fun openFingerprintLogin() {
        //验证指纹库信息是否发生变化
        if (FingerprintUtil.isLocalFingerprintInfoChange(this)) {
            //指纹库信息发生变化
            showFingerprintChangeTipDialog()
            return
        }
        helper!!.apply {
            generateKey()
            if (fingerprintVerifyDialog == null) {
                fingerprintVerifyDialog = FingerprintVerifyDialog(this@LoginActivity)
            }
            fingerprintVerifyDialog!!.apply {
                setContentText("请验证指纹")
                setOnCancelButtonClickListener {
                    stopAuthenticate()
                }
                if (!isFinishing)
                    show()
            }
            setPurpose(KeyProperties.PURPOSE_ENCRYPT)
            authenticate()
        }
    }

    override fun onAuthenticationSucceeded(value: String) {
        loge("hagan", "HomeActivity->onAuthenticationSucceeded-> value:$value")
        val ap = SPUtil.getInstance().getString(Constants.SP_A_P)
        isOpen = SPUtil.getInstance().getBoolean(Constants.SP_HAD_OPEN_FINGERPRINT_LOGIN)

        if (isOpen) {
        } else {
            SPUtil.getInstance().put(Constants.SP_HAD_OPEN_FINGERPRINT_LOGIN, true)
            if (fingerprintVerifyDialog != null && fingerprintVerifyDialog!!.isShowing) {
                fingerprintVerifyDialog!!.dismiss()
                val userType = SPUtil.getInstance().getString(USER_TYPE)
                val operationId = SPUtil.getInstance().getString(Common.USER_ID)
                isOpen = true
                saveLocalFingerprintInfo()
            }
        }
    }

    private fun saveLocalFingerprintInfo() {
        SPUtil.getInstance().put(
            Constants.SP_LOCAL_FINGERPRINT_INFO, FingerprintUtil.getFingerprintInfoString(
                applicationContext
            )
        )
    }

    override fun onAuthenticationFail() {
        showFingerprintVerifyErrorInfo("指纹不匹配")
    }

    override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
        if (fingerprintVerifyDialog != null && fingerprintVerifyDialog!!.isShowing) {
            fingerprintVerifyDialog!!.dismiss()
        }
        showTipDialog(errorCode, errString.toString())

    }

    private fun showTipDialog(errorCode: Int, errString: CharSequence) {
        if (fingerprintVerifyErrorTipDialog == null) {
            fingerprintVerifyErrorTipDialog = CommonTipDialog(this)
        }
        fingerprintVerifyErrorTipDialog!!.apply {
            setContentText(errString.toString())
            setSingleButton(true)
            setOnSingleConfirmButtonClickListener { helper!!.stopAuthenticate() }
            if (!isFinishing)
                show()
        }
    }


    override fun onAuthenticationHelp(helpCode: Int, helpString: CharSequence) {
        showFingerprintVerifyErrorInfo(helpString.toString())
    }

    private fun showFingerprintVerifyErrorInfo(info: String) {
        if (fingerprintVerifyDialog != null && fingerprintVerifyDialog!!.isShowing) {
            fingerprintVerifyDialog!!.setContentText(info)
        }
    }


    override fun showMessage(message: String?) {
        super.showMessage(message)
        PWUtil.getInstace().disDialog()
    }

    override fun showData(type: String, data: BaseBean?) {
        super.showData(type, data)
        when (type) {
            login -> {
                SPUtil.getInstance().put(USER_TYPE, data!!.data.loginType)
                SPUtil.getInstance().put(USER_ID, data.data.operationId)//记录账号
                SPUtil.getInstance().put(PHONE, data.data.phone)
                val pi: PackageInfo =
                    packageManager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES)
                PWUtil.getInstace().disDialog()

                val stringBuffer = StringBuffer()
                stringBuffer.append(edAccountNumber)
                stringBuffer.append(edPassword)
                SPUtil.getInstance().put(Constants.SP_A_P, MD5Util.md5Password(stringBuffer.toString()))
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    when (helper?.checkFingerprintAvailable(this)) {
                        1 -> {
                            tv_landing.postDelayed({
                                isOpen = SPUtil.getInstance().getBoolean(Constants.SP_HAD_OPEN_FINGERPRINT_LOGIN)
                                if (!isOpen && !isCancle) {
                                    showOpenFingerprintLoginDialog()
                                } else {
                                    startHomeActivity()
                                }
                            }, 500)
                        }
                        else -> {
                            startHomeActivity()
                        }
                    }
                } else {
                    startHomeActivity()
                }
            }
            fingerprintLogin -> {
                startHomeActivity()
            }
            addFingerprint -> {
                startHomeActivity()
            }
        }
    }

    private fun showOpenFingerprintLoginDialog() {
        if (openFingerprintLoginTipDialog == null) {
            openFingerprintLoginTipDialog = CommonTipDialog(this)
        }
        openFingerprintLoginTipDialog!!.apply {
            setSingleButton(false)
            setContentText("您的设备支持指纹登录,是否现在开启?")
            setOnDialogButtonsClickListener(object :
                CommonTipDialog.OnDialogButtonsClickListener {


                override fun onCancelClick(v: View) {
                    startHomeActivity()
                }

                override fun onConfirmClick(v: View) {
                    helper!!.apply {
                        generateKey()
                        if (fingerprintVerifyDialog == null) {
                            fingerprintVerifyDialog = FingerprintVerifyDialog(this@LoginActivity)
                        }
                        fingerprintVerifyDialog!!.apply {
                            setContentText("请验证指纹")
                            setOnCancelButtonClickListener {
                                stopAuthenticate()
                                isCancle = true
                            }
                            if (!isFinishing)
                                show()
                        }
                        setPurpose(KeyProperties.PURPOSE_ENCRYPT)
                        authenticate()
                    }
                }
            })
            if (!isFinishing)
                show()
        }
    }

    private fun startHomeActivity() {
        LiveDataBus.get().with(Common.SEND_TYPE).value = (BusBean(REMOVE_VILLAGE, "2"))
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_landing -> {
                if (!isConnect()) return//判断是否有网
                edAccountNumber = ed_account_number.text.toString()
                edPassword = ed_password.text.toString()
                //本地存储账号用户指纹登录时显示账号信息
                if (cbx_save.isChecked) {
                    SPUtil.getInstance().put(Common.remember_password, true)
                    SPUtil.getInstance().put(Common.user_name, edAccountNumber)
                    SPUtil.getInstance().put(Common.user_psw, edPassword)
                }
                p.login(
                    ""
                    , edAccountNumber
                    , edPassword
                    , ""
                )
            }
            R.id.tv_code_login -> {
                val activity = ST.startActivity(this, LoginActivity::class.java)
                if (activity) finish()
            }
            R.id.tv_forget_password -> {
                showDeveloping()
            }
        }
    }


}

