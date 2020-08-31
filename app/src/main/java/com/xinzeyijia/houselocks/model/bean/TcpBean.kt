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

data class TcpBean(
    var deltaPercentage: UpBrightnessBean = UpBrightnessBean()
    , var brightness: BrightnessBean = BrightnessBean()
    , var previousState: PreviousStateBean = PreviousStateBean()
    , var deltaValue: DeltaValueBean = DeltaValueBean()
    , var mode: ModeBean = ModeBean()
    , var temperature: TemperatureBean = TemperatureBean()
    , var targetTemperature: TargetTemperatureBean = TargetTemperatureBean()
    , var fanSpeed: FanSpeedBean = FanSpeedBean()
)

class FanSpeedBean (
    var value: String = "0"
)

data class TargetTemperatureBean (
    var scale:String=""
    ,var value:String=""
)
data class TemperatureBean (
    var value: String = "0"
)

data class ModeBean (
    var value: String = "0"
    ,var deviceType: String = ""
)

data class DeltaValueBean (
    var scale:String=""
    ,var value:String=""
)

data class UpBrightnessBean(
    var value: String = "0"
)

data class PreviousStateBean(
    var brightness: BrightnessBean = BrightnessBean()
    , var fanSpeed: FanSpeedBean = FanSpeedBean()
    , var mode: ModeBean = ModeBean()
)

data class BrightnessBean(
    var value: String = "0.0"
)
