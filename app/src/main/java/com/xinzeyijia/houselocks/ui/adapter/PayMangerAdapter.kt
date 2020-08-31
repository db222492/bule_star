package com.xinzeyijia.houselocks.ui.adapter

import android.support.v4.content.ContextCompat
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xinzeyijia.houselocks.R
import com.xinzeyijia.houselocks.model.bean.DataBean

class PayMangerAdapter : BaseQuickAdapter<DataBean, BaseViewHolder>(R.layout.pay_manger_item) {
    override fun convert(helper: BaseViewHolder?, item: DataBean?) {
        item!!.apply {
            helper!!.setText(R.id.tv_money, if (investType == "2") "+$money" else "-$money")
                .setText(R.id.tv_come, "来源："+if (investType == "2") "支付宝充值" else "人证核验费")
                .setText(R.id.tv_time, crtTime)
        }
        val state = helper!!.getView<TextView>(R.id.tv_state)
        item.apply {
            if (this.investType == "2") {
                state.text = "充值"
                state.setTextColor(ContextCompat.getColor(mContext, R.color.blue))
                state.background = ContextCompat.getDrawable(mContext, R.mipmap.order_blue_icon)
            } else {
                state.text = "扣费"
                state.setTextColor(ContextCompat.getColor(mContext, R.color.red))
                state.background = ContextCompat.getDrawable(mContext, R.mipmap.order_orange_icon)
            }
        }
    }
}