package com.xinzeyijia.houselocks.ui.adapter

import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xinzeyijia.houselocks.R
import com.xinzeyijia.houselocks.model.bean.DataBean


class RoomMangerAdapter : BaseQuickAdapter<DataBean, BaseViewHolder>(R.layout.room_manger_item) {
    override fun convert(helper: BaseViewHolder?, item: DataBean?) {
        helper!!.addOnClickListener(R.id.tv_sure)
        item!!.apply {
            helper.setText(R.id.tv_title, roomNo)
                .setText(R.id.tv_address, villageName + roomFloor + "号楼" + roomUnit + "单元")
                .setText(R.id.tv_detailed_address, "$roomArea㎡")
            val tv_state = helper.getView<TextView>(R.id.tv_state)
            val tv_sure = helper.getView<TextView>(R.id.tv_sure)
            //                民宿状态 1/2=营业/停业
            when (roomState) {
                "1" -> {
                    tv_state.text = "营业中"
                    tv_state.setTextColor(ContextCompat.getColor(mContext, R.color.hui3))
                    tv_sure.text = "停  业"
                    tv_sure.background = ContextCompat.getDrawable(mContext, R.drawable.select_blue_round)
                }
                "2" -> {
                    tv_state.text = "已停业"
                    tv_state.setTextColor(ContextCompat.getColor(mContext, R.color.blue))
                    tv_sure.text = "重新营业"
                    tv_sure.background = ContextCompat.getDrawable(mContext, R.drawable.select_red_round)
                }
            }
            when (state) {//停业营业只有在未绑锁状态才能操作
                "6" -> {
                    tv_sure.visibility = View.VISIBLE
                }
                else-> {
                    tv_sure.visibility = View.GONE
                }
            }
        }

    }
}
