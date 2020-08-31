package com.xinzeyijia.houselocks.ui.adapter

import android.support.constraint.ConstraintLayout
import android.support.v4.content.ContextCompat
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xinzeyijia.houselocks.R
import com.xinzeyijia.houselocks.base.Common
import com.xinzeyijia.houselocks.model.bean.DataBean
import com.xinzeyijia.houselocks.utils.io.SPUtil

/**
 * User: dibo
 * Date: 2019/9/10
 * Time: 16:20
 */
class HomeAdapter : BaseQuickAdapter<DataBean, BaseViewHolder>(R.layout.bedroomitem_layout) {

    override fun convert(helper: BaseViewHolder, item: DataBean) {
        helper.apply {
            item.apply {
                setText(R.id.tv_number, roomNoAlias)
                if (room_floor == "-1" || room_floor == "0") {
                    setText(
                        R.id.tv_number_start, if (room_unit == "-1" || room_unit == "0") "" else (room_unit + "单元")
                    )
                } else {
                    setText(
                        R.id.tv_number_start,
                        room_floor + "号楼" + if (room_unit == "-1" || room_unit == "0") "" else ("$room_unit-单元")
                    )
                }

                getView<TextView>(R.id.tv_number_start).isSelected = true
                getView<TextView>(R.id.tv_number).isSelected = true
                val textView = getView<TextView>(R.id.tv_room_state)
                val tv_room_state2 = getView<TextView>(R.id.tv_room_state2)
                //1，空房2，已预订3，已入住 4,空  脏5,待审核6,未绑锁     1,代入住2,入住中3，今日预离，4，订单取消，5 已离店,6待确认  is_check
//                textView.text = "停  业"
//                clt_color_type.background =
//                    ContextCompat.getDrawable(mContext, R.drawable.grey_shape)
                val type = SPUtil.getInstance().getString(Common.LOGIN_TYPE, "")
                if (type == "2") {
                    val clt_color_type = getView<ConstraintLayout>(R.id.clt_color_type)
                    when (isbangble) {
                        "0" -> {
                            textView.text = "未绑定"
                            clt_color_type.background =
                                ContextCompat.getDrawable(mContext, R.drawable.green_shape)
                        }
                        "1" -> {
                            textView.text = "已绑定"
                            clt_color_type.background =
                                ContextCompat.getDrawable(mContext, R.drawable.darkblue_shape_gradual)
                        }
                    }
                } else {
                    val clt_color_type = getView<ConstraintLayout>(R.id.clt_color_type)
                    when (state) {
                        "1" -> {
                            textView.text = "空  房"
                            clt_color_type.background =
                                ContextCompat.getDrawable(mContext, R.drawable.green_shape)
                        }
                        "2" -> {
                            textView.text = "已预订"
                            clt_color_type.background =
                                ContextCompat.getDrawable(mContext, R.drawable.darkblue_shape_gradual)
                        }
                        "3" -> {
                            textView.text = "已入住"
                            clt_color_type.background =
                                ContextCompat.getDrawable(mContext, R.drawable.yellow_shape)
                        }
                        "4" -> {
                            textView.text = "空  脏"
                            clt_color_type.background =
                                ContextCompat.getDrawable(mContext, R.drawable.blue_shape_gradual)
                        }
                        "5" -> {
                            textView.text = "待审核"
                            clt_color_type.background =
                                ContextCompat.getDrawable(mContext, R.drawable.grey_shape)
                        }
                        "6" -> {
                            textView.text = "未绑锁"
                            clt_color_type.background =
                                ContextCompat.getDrawable(mContext, R.drawable.zi_shapel)
                        }
                    }
//                民宿状态 1/2=营业/停业
                    when (room_state) {
                        "1" -> {
                            tv_room_state2.text = "营业中"
                        }
                        "2" -> {
                            tv_room_state2.text = "停  业"
                        }
                    }
                }

            }

        }
    }
}

