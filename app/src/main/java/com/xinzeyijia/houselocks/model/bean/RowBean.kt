package com.xinzeyijia.houselocks.model.bean

import com.chad.library.adapter.base.entity.MultiItemEntity

class RowBean() : MultiItemEntity {
    override fun getItemType(): Int {
        return 0
    }

    var orderStatus: Int = 0

    constructor(orderStatus: Int) : this() {
        this.orderStatus = orderStatus
    }

    constructor(title: String) : this() {
        this.title = title
    }

    var is_select: Boolean = false
    var authorId: Int = 0
    var roomNo: Int = 0
    var guestNum: Int = 0
    var price: Int = 0
    var rooms: Int = 0
    var currentStatus: Int = 0
    var avatar: String = ""
    var status: String = ""
    var hotelTitle: String = ""
    var peopleNum: String = ""
    var certificateNo: String = ""
    var address: String = ""
    var occupancyStartTime: String = ""
    var occupancyEndTime: String = ""
    var orderNo: String = ""
    var slogan: String = ""
    var teamImg: String = ""
    var orderDay: String = ""
    var userId: Int = 0
    var heat: Int = 0
    var teamName: String = ""
    var commentNum: Int = 0
    var orderCost: Int = 0
    var content: String = ""
    var sendTime: String = ""
    var lastTime: String = ""
    var crtTime: String = ""
    var id: Int = 0
    var typeId: Int = 0
    var point: Int = 0
    var messageId: Int = 0
    var imageUrl: List<ImageUrlBean> = ArrayList()
    var isDeleted: Int = 0
    var nickName: String = ""
    var nickNameH: String = ""
    var pageView: Int = 0
    var praiseNum: Int = 0
    var forwardedId: Int = 0
    var pubTime: String = ""
    var pubType: Int = 0
    var teamId: Int = 0
    var title: String = ""
    var updTime: String = ""
    var crtUserAvatar: String = ""
    var userFollowStatus: Int = 1
    var teamFollowStatus: Int = 1
    var teamJoinStatus: Int = 1
    var isCreateUser: Int = 0
    var type: Int = 0
    var sendId: Int = 0
    var chatUserId: Int = 0
    var recId: Int = 0
    var isRead: Int = 0
    var unreadCount: Int = 0
    var praiseStatus: Int = 1
    var isShowTime: Int = 0
    var leftOrRight: Int = 0

    var adminIsLock: Int = 0// 是否被锁
    var adminIsTop: Int = 0// 是否置顶
    var captainIsTop: Int = 0// 队长置顶

}