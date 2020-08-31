package com.xinzeyijia.houselocks.contract.home


import com.clj.fastble.data.BleDevice
import com.xinzeyijia.houselocks.base.BasePresenter
import com.xinzeyijia.houselocks.base.BaseView
import com.xinzeyijia.houselocks.model.bean.BaseBean
import java.util.*

/**
 * author : DiBo
 * e-mail : db222492@163.com
 * date   : 2019/6/1215:15
 * desc   :
 */
interface HomeContract {
    interface View : BaseView {
        fun showData(type: String, data: BaseBean?)
        fun showData(type: String, data: Any?)
        fun setProgress(currentSize: Long, totalSize: Long)
        fun showError(type: String, e: Throwable)
        fun timeOut()
        fun finishActivity()
    }


    abstract class Presenter : BasePresenter<View>() {


        abstract fun login(
            name: String,
            phone: String,
            password: String,
            code: String

        )


        abstract fun getCode(phone: String)
    }
}