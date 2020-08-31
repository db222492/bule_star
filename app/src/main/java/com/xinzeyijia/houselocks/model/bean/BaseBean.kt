package com.xinzeyijia.houselocks.model.bean


class BaseBean(
    var data: DataBean = DataBean(),
    var lockVersion: DataBean = DataBean(),
    var status: Int = 0,
    var message: String = "",
    var errcode: Int = 0,
    var errmsg: String = "",
    var description: String = "",
    var rel: Boolean = false,
    var access_token: String = "",
    var startDate: Int = 0,
    var md5Pwd: String = "",
    var date: String = "",
    var lockData: String = "",
    var lockMac: String = "",
    var lockId: Int = 0,
    var uid: Int = 0,
    var endDate: Int = 0,
    var total: Int = 0,
    var pageNo: Int = 0,
    var pageSize: Int = 0

)
