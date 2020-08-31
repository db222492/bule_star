package com.xinzeyijia.houselocks.ui.view

import android.hardware.fingerprint.FingerprintManager
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat
import com.xinzeyijia.houselocks.util.LogUtils.loge

class MyCallBack : FingerprintManagerCompat.AuthenticationCallback() {
        override fun onAuthenticationError(
            errMsgId: Int,
            errString: CharSequence
        ) {
            //当出现错误的时候回调此函数，比如多次尝试都失败了的时候，errString是错误信息
            //一般来说我们都是先判断一下是不是自己手动取消

            loge("TAG", "errMsgId=$errMsgId")
            if (errMsgId == FingerprintManager.FINGERPRINT_ERROR_LOCKOUT) {
                loge("TAG", "" + errString)
            }
        }

        //当指纹验证失败的时候会回调此函数，失败之后允许多次尝试，失败次数过多会停止响应一段时间然后再停止sensor的工作
        override fun onAuthenticationFailed() {
            //指纹认证失败，请再试一次

            loge("TAG", "onAuthenticationFailed")
        }

        override fun onAuthenticationHelp(
            helpMsgId: Int,
            helpString: CharSequence
        ) {
            //错误时提示帮助，比如说指纹错误，我们将显示在界面上 让用户知道情况

            loge("TAG", "helpString=$helpString")
        }

        //当验证的指纹成功时会回调此函数，然后不再监听指纹sensor
        override fun onAuthenticationSucceeded(result: FingerprintManagerCompat.AuthenticationResult) {
            //这里我们可以做取消弹框之类的

            loge("TAG", "onAuthenticationSucceeded=$result")
        }
    }