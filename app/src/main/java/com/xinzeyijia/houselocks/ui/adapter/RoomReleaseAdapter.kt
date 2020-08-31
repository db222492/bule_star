package com.xinzeyijia.houselocks.ui.adapter

import android.databinding.adapters.TextViewBindingAdapter.setText
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xinzeyijia.houselocks.R
import com.xinzeyijia.houselocks.model.bean.DataBean


class RoomReleaseAdapter : BaseQuickAdapter<DataBean, BaseViewHolder>(R.layout.room_release_item) {
    override fun convert(helper: BaseViewHolder?, item: DataBean?) {
        helper!!.apply {
            addOnClickListener(R.id.tv_sure)
            addOnClickListener(R.id.tv_sure2)
            item!!.apply {
                setText(R.id.tv_title, roomNo)
                    .setText(R.id.tv_detailed_address, "$roomArea㎡")
                if (roomFloor == "-1" || roomFloor == "0") {
                    setText(
                        R.id.tv_address,
                        villageName + if (roomUnit == "-1" || roomUnit == "0") "" else (roomUnit + "单元")
                    )
                } else {
                    setText(
                        R.id.tv_address,
                        villageName + roomFloor + "号楼-" + if (roomUnit == "-1" || roomUnit == "0") "" else (roomUnit + "单元")
                    )
                }
                val tv_state = getView<TextView>(R.id.tv_state)
                val tv_sure = getView<TextView>(R.id.tv_sure)
                val tv_sure2 = getView<TextView>(R.id.tv_sure2)
                when (auditStatus) {
                    "0" -> {
                        tv_state.text = "未发布"
                        tv_state.setTextColor(ContextCompat.getColor(mContext, R.color.hui3))
                        tv_sure.text = "去发布"
                        tv_sure.background = ContextCompat.getDrawable(mContext, R.drawable.select_blue_round)
                    }
                    "1" -> {
                        tv_state.text = "审核中"
                        tv_state.setTextColor(ContextCompat.getColor(mContext, R.color.blue))
                        tv_sure.text = "查看"
                        tv_sure.background = ContextCompat.getDrawable(mContext, R.drawable.select_blue_round)
                    }
                    "2" -> {
                        tv_state.text = "已通过"
                        tv_state.setTextColor(ContextCompat.getColor(mContext, R.color.green))
                        tv_sure.text = "修改信息"
                        tv_sure.background = ContextCompat.getDrawable(mContext, R.drawable.select_blue_round)
                        when (reviseStatus) {
                            "1" -> {
                                tv_state.text = "修改审核中"
                                tv_state.setTextColor(ContextCompat.getColor(mContext, R.color.blue))
                                tv_sure.text = "查看"
                                tv_sure.background = ContextCompat.getDrawable(mContext, R.drawable.select_blue_round)
                            }
                            "3" -> {
                                tv_state.text = "修改驳回"
                                tv_state.setTextColor(ContextCompat.getColor(mContext, R.color.red))
                                tv_sure.text = "重新填写"
                                tv_sure.background = ContextCompat.getDrawable(mContext, R.drawable.select_blue_round)
                            }
                        }
                    }
                    "3" -> {
                        tv_state.text = "未通过"
                        tv_state.setTextColor(ContextCompat.getColor(mContext, R.color.red))
                        tv_sure.text = "重新填写"
                        tv_sure.background = ContextCompat.getDrawable(mContext, R.drawable.select_blue_round)
                    }
                }

                if (auditStatus == "2") {  //auditStatus
                    when (status) {//民宿状态 1/2=营业/停业
                        "0" -> {
                            tv_sure2.text = "重新营业"
                            tv_sure2.background = ContextCompat.getDrawable(mContext, R.drawable.select_blue_round)
                        }
                        "1" -> {
                            tv_sure2.text = "停业"
                            tv_sure2.background = ContextCompat.getDrawable(mContext, R.drawable.select_red_round)
                        }
                    }
                    when (state) {//1，空房2，已预订3，已入住 4,空  脏5,待审核6,未绑锁
                        "6", "5" -> {//这两个状态都是隐藏
                            tv_sure2.visibility = View.GONE
                        }
                        else -> {
                            tv_sure2.visibility = View.VISIBLE
                        }
                    }
                } else {
                    tv_sure2.visibility = View.GONE
                }
                when (state) {//1，空房2，已预订3，已入住 4,空  脏5,待审核6,未绑锁      //未绑锁和待审核是不能发布的
                    "5", "6" -> {
                        tv_state.text = "不可发布"
                        tv_state.setTextColor(ContextCompat.getColor(mContext, R.color.hui3))
                        tv_sure.visibility = View.GONE
                    }
                    else -> {
                        tv_sure.visibility = View.VISIBLE
                    }
                }

            }

        }

    }
}
