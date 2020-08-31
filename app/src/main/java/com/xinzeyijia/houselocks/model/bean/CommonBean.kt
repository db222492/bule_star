package com.xinzeyijia.houselocks.model.bean

import java.util.ArrayList

/**
 * author : dibo
 * e-mail : db20206@163.com
 * date   : 2020/5/2520:34
 * desc   :
 * version: 1.0
 */
class CommonBean (
    var status: Int = 0,
    var message: String = "",
    var data:MutableList<TypeBean> = mutableListOf()
)
