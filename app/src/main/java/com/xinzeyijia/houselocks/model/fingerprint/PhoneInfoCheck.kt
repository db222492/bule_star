package com.xinzeyijia.houselocks.model.fingerprint

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.ComponentName
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.provider.Settings
import android.text.TextUtils

import java.util.regex.Matcher
import java.util.regex.Pattern

import com.xinzeyijia.houselocks.util.LogUtils.loge
import com.xinzeyijia.houselocks.utils.ToastUtil
import android.provider.Settings.ACTION_QUICK_LAUNCH_SETTINGS
import com.xinzeyijia.houselocks.util.ST.startActivity


/**
 * 描述手机信息的检测对象
 */
class PhoneInfoCheck private constructor(private val context: Context, private val brand: String) {

    private val SONY = "sony"
    private val OPPO = "oppo"
    private val HUAWEI = "huawei"
    private val HONOR = "honor"
    private val XIAOMI = "Xiaomi"
    private val KNT = "knt"

    /**
     * 跳转到指纹页面 或 通知用户去指纹录入
     */
    fun startFingerprint() {

        var pcgName: String? = null
        var clsName: String? = null

//        when {
//            compareTextSame(SONY) -> {
//                pcgName = "com.android.settings"
//                clsName = "com.android.settings.Settings\$FingerprintEnrollSuggestionActivity"
//            }
//            compareTextSame(OPPO) -> {
//                pcgName = "com.coloros.fingerprint"
//                clsName = "com.coloros.fingerprint.FingerLockActivity"
//            }
//            compareTextSame(HUAWEI) -> {
//                pcgName = "com.android.settings"
//                clsName = "com.android.settings.fingerprint.FingerprintSettingsActivity"
//            }
//            compareTextSame(HONOR) -> {
//                pcgName = "com.android.settings"
//                clsName = "com.android.settings.fingerprint.FingerprintSettingsActivity"
//            }
//            else -> {
        pcgName = "com.android.settings"
        clsName = "com.android.settings.Settings"
        // 如果以上判断没有符合该机型，那就跳转到设置界面，让用户自己设置吧
//        ToastUtil.show("请到设置中，找到指纹录入，进行指纹录入操作")
        // 跳转到Settings页面的Intent
        /*pcgName = "com.android.settings";
        clsName = "com.android.settings.Settings";*/
//            }
//        }// TODO 后续机型会继续加入的 （Deliliu）
        AlertDialog.Builder(context)
            .setTitle("指纹录入")
            .setMessage("请到设置中，找到指纹录入，进行指纹录入操作")
            .setPositiveButton("好的，我去录入指纹") { _: DialogInterface, i: Int ->
            }
            .show()
    }

    /**
     * 获得当前手机品牌
     * @return 例如：HONOR
     */
    private fun getBrand(): String {
        loge("PhoneInfoCheck Board:" + android.os.Build.BRAND + " brand:" + brand)
        return this.brand
    }

    /**
     * 对比两个字符串，并且比较字符串是否包含在其中的，并且忽略大小写
     *
     * @param value
     * @return
     */
    private fun compareTextSame(value: String): Boolean {
        return value.toUpperCase().indexOf(getBrand().toUpperCase()) >= 0
    }

    companion object {

        @SuppressLint("StaticFieldLeak")
        private var instance: PhoneInfoCheck? = null

        @Synchronized
        fun getInstance(context: Context, brand: String): PhoneInfoCheck {
            if (null == instance)
                instance = PhoneInfoCheck(context, brand)
            return instance!!
        }

        @Deprecated("")
        @JvmStatic
        fun main(args: Array<String>) {
            val str = "java JavA ABC"
            val test_ = Pattern.compile("java", Pattern.CASE_INSENSITIVE)
            val result_ = test_.matcher(str)

            // str = result_.replaceAll("Perl6");

            loge("frden$str")
            loge("frden$result_")

            // -----------------------------------

            val str1 = "abcdefghijklmnabc"
            val str2 = "HiJK"
            // 查找是否存在指定的字符
            loge("frden" + str2.indexOf(str1))
            // 忽略大小写查找
            loge("frden" + ("HONOR".toUpperCase().indexOf("HONOR".toUpperCase()) >= 0))

        }
    }
}