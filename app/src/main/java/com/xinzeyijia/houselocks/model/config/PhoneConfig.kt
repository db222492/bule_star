package com.keyi.tubo.mysocket

/**
 *    author : dibo
 *    e-mail : db20206@163.com
 *    date   : 2019/10/1712:15
 *    desc   :
 *    version: 1.0
 */
data class PhoneConfig(
        var dhcpType: Boolean = false,//DHCP 模式
        var debugMode: Boolean = false,//调试模式
        var versionCode: String = "",//版本号
        var deviceState: String = "",//设备状态
        var dns: String = "",//DNS 服务器
        var firmwareLength: Int = 0,//固件长度
        var gatewayIp: String = "",//网关 IP
        var instruct: String = "",//操作指令
        var ipAddress: String = "",//本地 IP location_icon
        var macAddress: String = "",//MAC location_icon
        var netmaskIp: String = "",//子网掩码
        var serviceIp: String = "",//服务器 IP
        var servicePort: Int = 0//服务器端口号
)