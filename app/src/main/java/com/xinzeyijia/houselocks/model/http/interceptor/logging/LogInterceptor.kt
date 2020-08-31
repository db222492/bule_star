package com.xinzeyijia.houselocks.model.http.interceptor.logging

import android.text.TextUtils
import com.google.gson.GsonBuilder
import com.xinzeyijia.houselocks.base.Common
import com.xinzeyijia.houselocks.model.exception.XCrashHandlerUtils
import com.xinzeyijia.houselocks.util.LogUtils.*
import okhttp3.Interceptor
import okhttp3.Response
import okio.Buffer
import java.net.URLDecoder
import java.nio.charset.Charset
import java.nio.charset.UnsupportedCharsetException
import java.util.concurrent.TimeUnit


/**
 * Description: <\br>
 */
class LogInterceptor : Interceptor {
    // 不对 HTML $ = 进行转义
    val gson = GsonBuilder().disableHtmlEscaping().create()
    private val UTF8 = Charset.forName("UTF-8")
    // 为了打印JSON
    val headerMap = HashMap<String, String>()
    val bodyMap = HashMap<String, String>()

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val requestBody = request.body()
        var body: String? = null
        requestBody?.let {
            val buffer = Buffer()
            requestBody.writeTo(buffer)
            var charset: Charset? = UTF8
            val contentType = requestBody.contentType()
            contentType?.let {
                charset = contentType.charset(UTF8)
            }
            body = buffer.readString(charset!!)
        }
        headerMap.clear()
        bodyMap.clear()
        //---------------
        if (!TextUtils.isEmpty(body)) {
//            val arrayStr = URLDecoder.decode(body, "UTF-8")?.split("&")
            val arrayStr = body?.split("&")
//            val arrayStr = body?.split("&")
            arrayStr?.forEach {
                val nameValue = it.split("=")
                try {
                    //如果是文件流 这里会抛异常
                    bodyMap[nameValue[0]] = URLDecoder.decode(nameValue[1], "UTF-8")
                } catch (e: Exception) {
                    // 如果是文件流 可能没有KEY
                    if (nameValue.size > 1) {
                        bodyMap[nameValue[0]] = nameValue[1]
                    } else {
                        bodyMap["noKey"] = nameValue[0]
                    }
                }
            }
        }
        //------------------
        request.headers().let {
            val names = it.names()
            for (name in names) {
                headerMap[name] = it.get(name) ?: ""
            }
        }


        val startNs = System.nanoTime()
        val response = chain.proceed(request)
        val tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs)

        val responseBody = response.body()
        val rBody: String

        val source = responseBody!!.source()
        source.request(java.lang.Long.MAX_VALUE)
        val buffer = source.buffer()

        var charset: Charset? = UTF8
        val contentType = responseBody.contentType()
        contentType?.let {
            try {
                charset = contentType.charset(UTF8)
            } catch (e: UnsupportedCharsetException) {
                loge(Common.TAG, e.message)
            }
        }
        rBody = buffer.clone().readString(charset!!)

        val headers = request.headers()
        val names = headers.names()
        val iterator = names.iterator()
        val str = StringBuilder()
        while (iterator.hasNext()) {
            val next = iterator.next()
            val value = headers.get(next)
            str.append("\n$next:$value")
        }

        logi(
            "发送请求: method：" + request.method()
                    + "\n请求url：" + request.url()
                    + "\n请求头：" + str

        )
        logi("请求参数: ")
        logjson(body)
        logi(
            "收到响应: code:" + response.code()
                    + "\n响应结果: "
        )
        logjson(rBody)
        XCrashHandlerUtils.getInstance().writeLogToSDCard(json(rBody))
        return response
    }
}