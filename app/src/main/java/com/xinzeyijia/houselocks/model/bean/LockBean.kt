package com.xinzeyijia.houselocks.model.bean
/*
       0x01：上电
       0x02：唤醒
       0x03：试错报警
       0x04：解除常开状态
       0x05：设置常开状态
       0x06：初始化
       0x07：锁验证模式更改
       0x08：锁音量调节
       0x09：锁时间更改
       0x0A：添加或修改用户，数据内包含用户ＩＤ
       0x0B：添加密钥，数据内包含用户ＩＤ及密钥ＩＤ
       0x0C：删除用户，数据内包含用户ＩＤ
       0x0D：删除密钥，数据内包含用户ＩＤ及密钥ＩＤ
       0x0E：门开
       0x0F：门关
       0x10：反锁打开
       0x11：反锁关闭
       0x12：门铃
       0x15：用户删除 - 密钥同步
       0x16：用户删除 - 密钥分类同步
       0x17：撬锁报警
       0x18：机械钥匙
       0x19：锁操作信息变化
       */
class LockBean(
    var title: String = "",
    val type: String = "",
    var hexResultData: String = "",

    var hexCrcValue: String = "",
    var hexHead: String = "",
    var event: String = "",
    var hexSumValue: String = "",
    var hexLen: String = "",
    var dataLength: Int = 0,
    var hexData: String = "",
    var dataString: String = "",
    var errorNo: String = "",
    var hexCmd: String = "",
    var hexFlag: String = "",
    var hexRnd: String = "",
    var hexCmdIndex: String = "",
    var data: LockItemBean = LockItemBean()



    ) {
    override fun toString(): String {
        return "LockBean(title='$title', type='$type', hexResultData='$hexResultData', data=$data, hexCrcValue='$hexCrcValue', hexHead='$hexHead', event='$event', hexSumValue='$hexSumValue', hexLen='$hexLen', dataLength=$dataLength, hexData='$hexData', dataString='$dataString', errorNo='$errorNo', hexCmd='$hexCmd', hexFlag='$hexFlag', hexRnd='$hexRnd', hexCmdIndex='$hexCmdIndex')"
    }
}

class LockItemBean(
    var title: String = "",
    val type: String = "",
    var version: String = "",
    var data: LockItem2Bean = LockItem2Bean(),
    var hexCrcValue: String = "",
    var hexBindAnswer: String = "",
    var hexVersion: String = "",
    var hexHead: String = "",
    var hexSumValue: String = "",
    var hexLen:String ="",
    var dataLength: Int = 0,
    // 值 （发送数据或者接受数据）
    var hexData: String = "",
    var dataString: String = "",
    var errorNo: String = "",
    // 命令字
    var hexCmd: String = "",
    var hexFlag: String = "",
    var hexRnd: String = "",
    var event: String = "",

    var hexCmdIndex: String = ""


) {
    override fun toString(): String {
        return "LockItemBean(title='$title', type='$type', version='$version', data=$data, hexCrcValue='$hexCrcValue', hexBindAnswer='$hexBindAnswer', hexVersion='$hexVersion', hexHead='$hexHead', hexSumValue='$hexSumValue', hexLen='$hexLen', dataLength=$dataLength, hexData='$hexData', dataString='$dataString', errorNo='$errorNo', hexCmd='$hexCmd', hexFlag='$hexFlag', hexRnd='$hexRnd', event='$event', hexCmdIndex='$hexCmdIndex')"
    }
}

class LockItem2Bean(

    var user2ID: Int = 0,
    var userName2: String = "",
    var logType: Int = 0,
    var hexUserName1: String = "",
    var hexKeyType: String = "",
    var hexKey1ID: String = "",
    var hexLogID: String = "",
    var hexVoltage: String = "",
    var hexUser1ID: String = "",
    var hexKey2ID: String = "",
    var hexUser2ID: String = "",
    var hexUserName2: String = "",
    var hexLogType: String ="",
    var keyType: Int =0,
    var userName1: String = "",
    var key1ID: Int = 0,
    var logID: Int = 0,
    var voltage: Int = 0,
    var user1ID: Int = 0,
    var key2ID: Int = 0,
    var hexDateTime: String = "",//判断返回的类型是成功还是失败
    var event: String = ""


) {
    override fun toString(): String {
        return "LockItem2Bean(user2ID=$user2ID, userName2='$userName2', logType=$logType, hexUserName1='$hexUserName1', hexKeyType='$hexKeyType', hexKey1ID='$hexKey1ID', hexLogID='$hexLogID', hexVoltage='$hexVoltage', hexUser1ID='$hexUser1ID', hexKey2ID='$hexKey2ID', hexUser2ID='$hexUser2ID', hexUserName2='$hexUserName2', hexLogType='$hexLogType', keyType=$keyType, userName1='$userName1', key1ID=$key1ID, logID=$logID, voltage=$voltage, user1ID=$user1ID, key2ID=$key2ID, hexDateTime='$hexDateTime', event='$event')"
    }
}