package com.xinzeyijia.houselocks.model.bean


/**
 * author : DiBo
 * e-mail : db222492@163.com
 * date   : 2019/5/615:13
 * desc   :
 */

class DataBean {

    var room_list: List<DataBean> = arrayListOf()
     var village_list: List<DataBean> = arrayListOf()
    var lock_vendor: List<DataBean> = arrayListOf()
    var check_persons: List<DataBean> = arrayListOf()
    var persons: List<DataBean> = arrayListOf()
    var priceLists: List<DataBean> = arrayListOf()
    var roomTypeArr: AddRoomBean2 = AddRoomBean2()
    var inTimeList: List<String> = arrayListOf()
    var outTimeList: List<String> = arrayListOf()
    var pictureArr: MutableList<String> = mutableListOf()
    var result: ItemBean = ItemBean()
    var dyneLockRoom: ItemBean = ItemBean()
    var dyneLockOrderPersons: ItemBean = ItemBean()
    var message: String = ""
    var code: String = ""
    var dyPsEnd: String = ""
    var body: String = ""
    var dyPass: String = ""
    var description: String = ""
    var lockPsw: String = ""
    var realName: String = ""
    var orderId: String = ""
    var dyOrderNo: String = ""
    var orderNo: String = ""
    var showEndDate: String = ""
    var waller_change: String = ""
    var investChannel: String = ""
    var money: String = ""
    var rejectReason: String = ""
    var wallet_type: String = ""
    var orderState: String = ""
    var kjxId: String = ""
    var dyne_lock_name: String = ""
    var investType: String = ""
    var version_code: Int = 0
    var countyName: String = ""
    var checkInTime: String = ""
    var checkOutTime: String = ""
    var price: String = ""
    var psw: String = ""
    var loginType: String = ""
    var operationId: String = ""
    var collectFeesType: String = ""
    var feesMoney: String = ""
    var collectFeesTime: String = ""
    var wallet_balance: String = ""
    var invest: String = "0"
    var bankPhone: String = ""
    var bankNo: String = ""
    var bankName: String = ""
    var bankOpen: String = ""
    var brokeragePort: String = ""
    var bankCardState: String = ""
    var time: String = ""

    var cityid: String = ""
    var cityname: String = ""
    var bureauname: String = ""
    var bureauid: String = ""
    var beg_date: String = ""
    var is_check: String = ""
    var end_date: String = ""
    var hotelid: String = ""
    var data: String = ""
    var blue_key: String = ""
    var roomNoAlias: String = ""
    var blue_mac: String = ""
    var order_status: String = ""
    var isbangble: String = ""//是否已绑定

    var provinceName: String = ""
    var cityName: String = ""
    var longitude: Double = 0.0
    var latitude: Double = 0.0
    var idcard: String = ""
    var nickName: String = ""
    var sex: String = ""
    var dir: String = ""
    var currentPage: Int = -1
    var token: String = ""
    var expire: String = ""
    var roows: MutableList<AddRoomBean3> = ArrayList()
    var facilitiesArr: MutableList<AddRoomBean3> = ArrayList()
    var rows: MutableList<DataBean> = ArrayList()
    var point: Int = 0
    var avatar_url: String = ""
    var type: String = ""
    var id: Int = 0
    var room_id: Int = 0
    var img_icon: Int = 0
    var urlId: String = ""
    var url: String = ""
    var city: MutableList<DataBean> = mutableListOf()
    var vill: MutableList<DataBean> = mutableListOf()
    var totalCount: Int = 0
//上传阿里云用 start
    var accessid: String = ""
    var location: String = ""
    var address: String = ""
    var nation: String = ""
    var nationPosi: Int = 0
    var sexPosi: Int = 0
    var idName: String = ""
    var nation_code: String = ""
    var date: String = ""
//    var room_no: String = ""
    var district_id: Int = 0
    var room_unit: String = "0"
    var floor_number: String = ""
    var unit_number: String = ""
    var room_floor: String = "0"
    var state: String = ""
    var room_state: String = ""
    var phone: String = ""
    var attestation: String = ""
    var cardNo: String = ""
    var upload: String = ""
    var identity: String = ""
    var open_type: String = ""
    var mac: String = ""
    var village_area: String = ""
    var roomArea: String = ""
    var roomState: String = ""
    var village_address: String = ""//房间详细地址
    var policy: String = ""
    var roomNum: String = ""
    var host: String = ""
    var accessKeySecret: String = ""
    var bucket: String = ""
    var count: String = ""
    var number: String = ""
    var roomId: Int = 0
    var startTime1: String = ""
    var endTime1: String = ""
    var village_name: String = ""
    var villageName: String = ""
    var updateType: String = ""
    var updTime: String = ""
    var roomNo: String = ""
    var auditStatus: String = ""
    var reviseStatus: String = ""
    var status: String = ""
    var roomFloor: String = ""
    var roomUnit: String = ""
    var startTime: String = ""
    var endTime: String = ""
    var festivalStartTime: String = ""
    var festivalEndTime: String = ""
    var userName: String = ""
    var name: String = ""
    var room_number: String = ""
    var day: String = ""
    var payAmt: String = ""
    var idcard_img: String = ""
    var idcard_id: String = ""
    var img_url: String = ""
    var title: String = ""
    var lock_type: String = ""
    var lock_type_en: String = ""
    var lock_id: String = "0"
    var ble_uuid_service_id: String = ""
    var ble_uuid_receive_id: String = ""
    var ble_uuid_send_id: String = ""
    var crt_time: String = ""
    var upd_time: String = ""
    var price1: String = ""
    var weekendPrice: String = ""
    var festivalPrice: String = ""
    var remark: String = ""
    var crtTime: String = ""



}

class ItemBean{  var passwordKey: String = ""
    var village_name: String = ""
    var called: String = ""//民宿名
    var price: Int = 1
    var room_state: String = ""
    var pic_arr: List<String> = arrayListOf()
    var district_id: Int = 0
    var code: String = ""
    var location: String = ""
    var village_address: String = ""
    var room_area: String = ""
    var roomNoAlias: String = ""
    var room_num: String = ""
    var day: String = ""
    var bed_num: String = ""
    var lock_type: String = ""
    var state: String = ""
    var cqr_xm: String = ""
    var cqr_lxdh: String = ""
    var cqr_zjhm: String = ""
    var cqdw_tydm: String = ""
    var cqdw_lxdh: String = ""
    var cqdw: String = ""
    var jyqk: String = ""//房间情况
    var company: String = ""//民宿名
    var fwly: String = "0"//房间来源
    var dataFlag: String = ""//房间来源
    var cqtype: String = ""//房间来源
    var czly: String = ""//出租类型
    var mac: String = ""//出租类型
    var djsj: String = ""//发布时间
    var room_unit: String = ""
    var building: String = ""
    var room_floor: String = ""
    var cmdId: String = ""
    var id: Int = 1
    var type: String = ""
    var name: String = ""
    var subName: String = ""
    var regions: String = ""
    var city: String = ""
    var ssx: String = ""
    var latitude: Double = 0.0
    var longitude: Double = 0.0
    var province: String = ""
    var sex: String = ""
    var address: String = ""
    var idcard_img: String = ""
    var phone: String = ""}
  

class AddRoomBean{
    var title: String = ""
    var address: String = ""
    var roomDetail: String = ""
    var roomType: AddRoomBean2 = AddRoomBean2()
    var county: String = ""
    var city: String = ""
    var province: String = ""
    var latitude: Double = 0.0
    var longitude: Double = 0.0
}
  


class AddRoomBean3{
    var id: Int = 0
    var title: String = ""
    var name: String = ""
    var is_select: Boolean = false

}
    

class AddRoomBean2{
    var room: String = ""
    var hall: String = ""
    var kitchen: String = ""
    var toilet: String = ""
}
   
