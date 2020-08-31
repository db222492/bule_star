package com.xinzeyijia.houselocks.model.bean

/**
 * author : dibo
 * e-mail : db20206@163.com
 * date   : 2019/10/2418:00
 * desc   :
 * version: 1.0
 */
data class SkyworthTVBean(
    /**
     * 1、灯light
    2、模式sleepMode
    3、窗帘curtainSwitch
    4、空调air
    5、电视televisionSwitch
     */
    var requestType: String? = "",

    var air: Air? = Air(),//空调

    var curtainSwitch: String? = "",//窗帘开关，开on关off
    var light: Light? = Light(),//灯
    var sleepMode: String? = "",//睡眠模式sleep、明亮模式bright、阅读模式read
    var televisionSwitch: String? = ""//电视开关，开on关off
)

data class Light(
    var lightSwitch: String? = "",// 开灯on，关灯off
    var lightType: String? = ""//porch light廊灯、who_lights卫灯、bed_lamp床灯、night_light夜灯
)

data class Air(
    var airMode: String? = "",//空调模式:制冷cold_wind，制热hot_wind
    var airSwitch: String? = "",//打开空调on，关闭空调off
    var airTemperature: String? = "",//空调X度
    var airWindSpeed: String? = ""//风速大小，大big小small中center
)