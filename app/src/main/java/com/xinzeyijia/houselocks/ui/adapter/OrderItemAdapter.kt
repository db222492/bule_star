package com.xinzeyijia.houselocks.ui.adapter

import android.support.constraint.ConstraintLayout
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xinzeyijia.houselocks.R
import com.xinzeyijia.houselocks.model.bean.DataBean
import com.xinzeyijia.houselocks.util.date_util.TimeUtils
import com.xinzeyijia.houselocks.util.date_util.TimeUtils.HOUR_SDF_CD

class OrderItemAdapter : BaseQuickAdapter<DataBean, BaseViewHolder>(R.layout.order_home_item) {
    override fun convert(helper: BaseViewHolder?, item: DataBean?) {
        item!!.apply {
            helper!!.apply {
                setText(R.id.tv_order_name, name)
                setText(R.id.tv_room_no, "房间号：$room_number")
                setText(R.id.tv_user_num, "(" + check_persons.size + "人)")

                setText(
                    R.id.tv_id_card,
                    TimeUtils.string2String(
                        startTime1,
                        TimeUtils.DEFAULT_SDF,
                        HOUR_SDF_CD
                    ) + "~" + TimeUtils.string2String(endTime1, TimeUtils.DEFAULT_SDF, HOUR_SDF_CD)
                )
                if (floor_number == "-1" || floor_number == "0") {
                    setText(
                        R.id.tv_address,
                        village_name + if (unit_number == "-1" || unit_number == "0") "" else (unit_number + "单元")
                    )
                } else {
                    setText(
                        R.id.tv_address,
                        village_name + floor_number + "号楼-" + if (unit_number == "-1" || unit_number == "0") "" else (unit_number + "单元")
                    )
                }

                val constraintLayout = getView<ConstraintLayout>(R.id.clt)
                val tv_room_state = getView<TextView>(R.id.tv_room_state)
                val tvRefuse = getView<TextView>(R.id.tv_refuse)
                val tvConfirm = getView<TextView>(R.id.tv_comfirm)
                addOnClickListener(R.id.tv_refuse)
                addOnClickListener(R.id.tv_comfirm)

                when (order_status) {// 1,代入住2,入住中3，今日预离，4，订单取消，5 已退宿,6待确认
                    "1" -> {
                        tv_room_state.background =
                            ContextCompat.getDrawable(mContext, R.mipmap.order_blue_icon)
                        constraintLayout.background =
                            ContextCompat.getDrawable(mContext, R.mipmap.order_blue_bg)
                        tv_room_state.setTextColor(ContextCompat.getColor(mContext, R.color.blue))
                        tv_room_state.text = "已预订"
                    }
                    "2" -> {
                        tv_room_state.background =
                            ContextCompat.getDrawable(mContext, R.mipmap.order_orange_icon)
                        constraintLayout.background =
                            ContextCompat.getDrawable(mContext, R.mipmap.order_orange_bg)
                        tv_room_state.setTextColor(ContextCompat.getColor(mContext, R.color.orange))
                        tv_room_state.text = "已入住"
                    }

                    "6" -> {
                        constraintLayout.background =
                            ContextCompat.getDrawable(mContext, R.mipmap.order_green_bg)
                        tv_room_state.background = ContextCompat.getDrawable(mContext, R.mipmap.order_green_icon)
                        tv_room_state.setTextColor(ContextCompat.getColor(mContext, R.color.green))
                        tvRefuse.visibility = View.VISIBLE
                        tvConfirm.visibility = View.VISIBLE
                        tv_room_state.text = "待确认"
                    }

                    "5" -> {
                        constraintLayout.background = ContextCompat.getDrawable(mContext, R.mipmap.order_gray_bg)
                        tv_room_state.background = ContextCompat.getDrawable(mContext, R.mipmap.order_gray_icon)
                        tv_room_state.setTextColor(ContextCompat.getColor(mContext, R.color.hui8))
                        tv_room_state.text = "已退宿"
                    }


                }
            }
        }

    }


}
