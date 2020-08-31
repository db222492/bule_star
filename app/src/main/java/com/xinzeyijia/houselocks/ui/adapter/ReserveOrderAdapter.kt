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

class ReserveOrderAdapter : BaseQuickAdapter<DataBean, BaseViewHolder>(R.layout.item_order_list) {
    override fun convert(helper: BaseViewHolder?, item: DataBean?) {
        helper!!
            .setText(
                R.id.tv_date, TimeUtils.string2string(
                    item!!.beg_date,
                    TimeUtils.DEFAULT_SDF,
                    TimeUtils.HOUR_SDF_CT
                ) + "  至  " + TimeUtils.string2string(
                    item.end_date,
                    TimeUtils.DEFAULT_SDF,
                    TimeUtils.HOUR_SDF_CT
                ) + "    共" + item.day + "晚"
            )
            .setText(R.id.tv_name, "预订人:" + item.name)
            .setText(R.id.tv_mobile, "手机号:" + item.phone)
            .setText(R.id.tv_order_time, "订单时间:" + item.crt_time)
        val tvRefuse = helper.getView<TextView>(R.id.tv_refuse)
        val tvConfirm = helper.getView<TextView>(R.id.tv_comfirm)
        helper.addOnClickListener(R.id.tv_refuse)
        helper.addOnClickListener(R.id.tv_comfirm)
        val outlayout = helper.getView<ConstraintLayout>(R.id.outlayout)
        when (item.is_check) {// 1,代入住2,入住中3，今日预离，4，订单取消，5 已离店,6待确认
            "1" -> {
                outlayout.background = ContextCompat.getDrawable(mContext, R.mipmap.order_blue_bg)
                tvRefuse.visibility = View.GONE
                tvConfirm.visibility = View.GONE
            }
            "2" -> {
                outlayout.background = ContextCompat.getDrawable(mContext, R.mipmap.order_orange_bg)
                tvRefuse.visibility = View.GONE
                tvConfirm.visibility = View.GONE
            }
            "6" -> {
                outlayout.background =
                    ContextCompat.getDrawable(mContext, R.mipmap.order_green_bg)
                tvRefuse.visibility = View.VISIBLE
                tvConfirm.visibility = View.VISIBLE
            }
        }
    }
}

