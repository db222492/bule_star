package com.xinzeyijia.houselocks.model.bean

/**
 * @ProjectName: intelligent_guest_control
 * @Package: com.xinzeyijia.guest_control.model.bean
 * @ClassName: TcpBean
 * @Description: java类作用描述
 * @Author: 作者名
 * @CreateDate: 2019/11/05 下午 01:40
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/11/05 下午 01:40
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */

data class TcpBean2(
    var deltaPercentage: TcpInBean = TcpInBean()
    , var brightness: TcpInBean = TcpInBean()
    , var previousState: TcpInBean = TcpInBean()
    , var deltaValue: TcpInBean = TcpInBean()
    , var mode: TcpInBean = TcpInBean()
    , var temperature: TcpInBean = TcpInBean()
    , var targetTemperature: TcpInBean = TcpInBean()
    , var fanSpeed: TcpInBean = TcpInBean()
)

class TcpInBean(
    var scale: String = ""
    , var value: String = ""
    , var deviceType: String = ""
    , var brightness: BrightnessBean = BrightnessBean()
    , var fanSpeed: FanSpeedBean = FanSpeedBean()
    , var mode: ModeBean = ModeBean()
)



