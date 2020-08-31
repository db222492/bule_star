package com.xinzeyijia.houselocks.util


import android.annotation.SuppressLint
import com.vector.update_app.HttpManager
import com.xinzeyijia.houselocks.base.Common
import com.xinzeyijia.houselocks.model.http.RxSchedulers
import com.xinzeyijia.houselocks.model.http.Urls.getHomeApiService
import com.xinzeyijia.houselocks.util.LogUtils.loge
import com.xinzeyijia.houselocks.util.date_util.TimeUtils
import com.xinzeyijia.houselocks.util.date_util.TimeUtils.HOUR_SDF_D
import com.xinzeyijia.houselocks.utils.io.SPUtil
import com.zhy.http.okhttp.OkHttpUtils
import com.zhy.http.okhttp.callback.FileCallBack
import com.zhy.http.okhttp.callback.StringCallback
import okhttp3.Call
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import java.io.File
import java.util.*

@SuppressLint("CheckResult")
class UpdateAppHttpUtil : HttpManager {
    /**
     * 异步get
     *
     * @param url      get请求地址
     * @param params   get参数
     * @param callBack 回调
     */

    override fun asyncGet(url: String, params: Map<String, String>, callBack: HttpManager.Callback) {
        //此处省略请求接口  模拟请求接口直接返回 json
        val map = HashMap<String, Any>()
        map["version_code"] = VersionCodeUtil.getInstance().versionCode
        map["app_type"] = "lock"
        getHomeApiService().versionQuerystatus(map)
            .compose(RxSchedulers.io_main())
            .subscribe({ responseBody ->
                val jsonObject = JSONObject(responseBody.string())
                val data = jsonObject.getJSONObject("data")
                SPUtil.getInstance().put(Common.TEL_PHONE, data.getString("tel_phone"))
                val constraint = data.getBoolean("constraint")
                val dateTime = SPUtil.getInstance().getString(Common.TIME_UPDATE, "")
                if (!constraint && dateTime != TimeUtils.date2String(Date(), HOUR_SDF_D)) {
                    SPUtil.getInstance().put(Common.TIME_UPDATE, TimeUtils.date2String(Date(), HOUR_SDF_D))
                    callBack.onResponse(data.toString())
                } else if (constraint) {
                    callBack.onResponse(data.toString())
                }
            },
                { throwable ->
                    loge("versionQuerystatus", "数据请求异常！" + throwable.message)
                })

    }

    /**
     * 异步post
     * @param url      post请求地址
     * @param params   post请求参数
     * @param callBack 回调
     */
    override fun asyncPost(url: String, params: Map<String, String>, callBack: HttpManager.Callback) {
        OkHttpUtils.post()
            .url(url)
            .params(params)
            .build()
            .execute(object : StringCallback() {
                override fun onError(call: Call, response: Response, e: Exception, id: Int) {
                    callBack.onError(validateError(e, response))
                }

                override fun onResponse(response: String, id: Int) {
                    callBack.onResponse(response)
                }
            })

    }

    /**
     * 下载
     *
     * @param url      下载地址
     * @param path     文件保存路径
     * @param fileName 文件名称
     * @param callback 回调
     */
    override fun download(url: String, path: String, fileName: String, callback: HttpManager.FileCallback) {
        OkHttpUtils.get()
            .url(url)
            .build()
            .execute(object : FileCallBack(path, fileName) {
                override fun inProgress(progress: Float, total: Long, id: Int) {
                    callback.onProgress(progress, total)
                }

                override fun onError(call: Call, response: Response, e: Exception, id: Int) {
                    callback.onError(validateError(e, response))
                }

                override fun onResponse(response: File, id: Int) {
                    callback.onResponse(response)

                }

                override fun onBefore(request: Request?, id: Int) {
                    super.onBefore(request, id)
                    callback.onBefore()
                }
            })

    }
}