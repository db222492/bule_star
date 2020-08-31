package com.xinzeyijia.houselocks.ui.activity

import android.content.Intent
import android.view.View

import com.xinzeyijia.houselocks.R
import com.xinzeyijia.houselocks.base.BaseActivity
import com.xinzeyijia.houselocks.base.Common.getCode
import com.xinzeyijia.houselocks.base.Common.userRegist
import com.xinzeyijia.houselocks.contract.home.HomeContract
import com.xinzeyijia.houselocks.contract.home.HomePresenter
import com.xinzeyijia.houselocks.model.bean.BaseBean
import com.xinzeyijia.houselocks.util.ST
import com.xinzeyijia.houselocks.utils.time.CountdownUtil
import kotlinx.android.synthetic.main.activity_user_regist.*

class RegistActivity : BaseActivity<HomePresenter>(), HomeContract.View {
    private var countdownUtil: CountdownUtil = CountdownUtil.newInstance()
    override fun getLayoutId(): Int {
        return R.layout.activity_regist
    }

    override fun initView() {
        super.initView()

    }

    override fun showData(type: String, data: BaseBean?) {
        super.showData(type, data)
        when (type) {
            getCode -> {
                countdownUtil = p.initTimeDown(tv_get_code)!!//倒计时
                tv_get_code.setText(data!!.data.code)
            }
            userRegist -> {
                startActivity(Intent(this, LoginActivity::class.java))
                if (countdownUtil.isRunning) {
                    countdownUtil.stop()
                }
                finish()
            }
        }
    }


    override fun onClick(view: View) {
        when (view.id) {
            R.id.tv_get_code -> {
                p.getCode(reed_phone_number.text.toString().trim())
            }
            R.id.ev_d -> {
                ST.startActivity(this, LoginActivity::class.java)
                finish()
            }
            R.id.tv_regist -> {
                val etUserName = et_user_name.text.toString().trim()
                val reedPhoneNumber = reed_phone_number.text.toString().trim()
                val ed_auth_code = ed_auth_code.text.toString().trim()
                val edPassword = ed_password.text.toString().trim()
                val edNewPassword = ed_new_password.text.toString().trim()
//                p.userRegist(etUserName, reedPhoneNumber, edNewPassword, edPassword, ed_auth_code)
            }


        }
    }

}
