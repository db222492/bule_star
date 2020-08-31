package com.xinzeyijia.houselocks.ui.adapter

import android.support.constraint.ConstraintLayout
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xinzeyijia.houselocks.R
import com.xinzeyijia.houselocks.model.bean.DataBean

class LockAdapter : BaseQuickAdapter<DataBean, BaseViewHolder>(R.layout.lock_item) {
    override fun convert(helper: BaseViewHolder?, item: DataBean?) {
        item!!.apply {
            helper!!.apply {
                setText(R.id.tv_order_name, "房间号：$roomNoAlias")
                if (room_floor == "-1" || room_floor == "0") {
                    setText(
                        R.id.tv_address, if (room_unit == "-1" || room_unit == "0") "" else (room_unit + "单元")
                    )
                } else {
                    setText(
                        R.id.tv_address,
                        room_floor + "号楼-" + if (room_unit == "-1" || room_unit == "0") "" else (room_unit + "单元")
                    )
                }

                    .addOnClickListener(R.id.tv_bind_room)
                    .addOnClickListener(R.id.tv_open_lock)
                val constraintLayout = getView<ConstraintLayout>(R.id.clt)
                val tv_lock_state = getView<TextView>(R.id.tv_lock_state)
                val tv_bind_room = getView<TextView>(R.id.tv_bind_room)
                val tv_open_lock = getView<TextView>(R.id.tv_open_lock)


                /**
                 * 如果不是待审核，就展示两个按钮
                 */
                if (state != "5") {
                    tv_bind_room.visibility = View.VISIBLE
                    tv_open_lock.visibility = View.VISIBLE
                }
                /**
                 * 根据绑定状态判断显示绑定按钮如果是已绑定，显示开锁和解绑，如果是未绑定显示绑定
                 */
                when (isbangble) {//TODO  将来要加一个房间内部的订单数量，根据订单数判断是否可以解锁
                    "0" -> {
                        constraintLayout.background =
                            ContextCompat.getDrawable(mContext, R.mipmap.order_gray_bg)
                        tv_lock_state.text = "未绑定门锁"
                        tv_lock_state.setTextColor(ContextCompat.getColor(mContext, R.color.hui5))
                        tv_bind_room.text = "去绑定"
                        tv_open_lock.visibility = View.GONE

                    }
                    "1" -> {
                        constraintLayout.background =
                            ContextCompat.getDrawable(mContext, R.mipmap.order_blue_bg)
                        tv_lock_state.setTextColor(ContextCompat.getColor(mContext, R.color.blue))
                        tv_lock_state.text = "已绑定门锁"
                        tv_bind_room.text = "解除绑定"
                        tv_open_lock.visibility = View.VISIBLE
                    }
                }
                /**
                 * 根据房间状态，判断是否展示两个按钮，只有在待审核状态隐藏
                 */
                when (state) {//1，空房2，已预订3，已入住 4,空  脏5,待审核6,未绑锁     1,代入住2,入住中3，今日预离，4，订单取消，5 已离店,6待确认  is_check
                    "1" -> {
                        //空房状态要绑定完之后才能预定
                        tv_bind_room.visibility = View.VISIBLE
                    }
                    "2" -> {
                        //不能解绑,
                        tv_bind_room.visibility = View.GONE

                    }
                    "3" -> {

                        tv_bind_room.visibility = View.GONE

                    }
                    "4" -> {//不能解绑
                        tv_bind_room.visibility = View.GONE
                    }
                    "5" -> {//不能操作门锁
                        /**
                         * 如果是待审核，就隐藏两个按钮
                         */
                        constraintLayout.background = ContextCompat.getDrawable(mContext, R.mipmap.order_gray_bg)

                        tv_bind_room.visibility = View.GONE
                        tv_open_lock.visibility = View.GONE
                    }
                    "6" -> {//可以绑锁
                        tv_bind_room.visibility = View.VISIBLE
                    }
                }
            }
        }


    }

}
