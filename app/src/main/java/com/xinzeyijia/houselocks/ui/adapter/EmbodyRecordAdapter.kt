package com.xinzeyijia.houselocks.ui.adapter

import android.support.v4.content.ContextCompat
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xinzeyijia.houselocks.R
import com.xinzeyijia.houselocks.model.bean.DataBean

class EmbodyRecordAdapter : BaseQuickAdapter<DataBean, BaseViewHolder>(R.layout.embodyrecord_item) {
    override fun convert(helper: BaseViewHolder?, item: DataBean?) {
        item!!.apply {
            helper!!.setText(R.id.tv_money, waller_change)
                .setText(R.id.tv_come, remark)
                .setText(R.id.tv_time, crt_time)
                .setText(R.id.tv_order_number, orderNo)
        }
        val state = helper!!.getView<TextView>(R.id.tv_state)
        item.apply {
            if (this.wallet_type == "2") {
                state.text = "收入"
                state.setTextColor(ContextCompat.getColor(mContext, R.color.blue))
                state.background =
                    ContextCompat.getDrawable(mContext, R.mipmap.order_blue_icon)
            } else {
                state.text = "支出"
                state.setTextColor(ContextCompat.getColor(mContext, R.color.red))
                state.background = ContextCompat.getDrawable(mContext, R.mipmap.order_orange_icon)
            }
        }
    }
}
