package com.xinzeyijia.houselocks.ui.adapter

import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xinzeyijia.houselocks.R
import com.xinzeyijia.houselocks.model.bean.DataBean

class OrderAdapter : BaseQuickAdapter<DataBean, BaseViewHolder>(R.layout.order_item) {
    private var isCheck = ""
    override fun convert(helper: BaseViewHolder?, item: DataBean?) {
        helper!!
            .addOnClickListener(R.id.tv_additional_recording)
            .setText(R.id.tv_order_name, "姓名：" + item!!.name)
            .setText(R.id.tv_order_phone, "手机号：" + item.phone)
            .setText(R.id.tv_id_card, "证件号：" + item.idcard)
            .setText(R.id.tv_nation, "民族：" + item.nation)
            .setText(
                R.id.tv_sex, "性别：" + when (item.sex) {
                    "1" -> "男"
                    "2" -> "女"
                    "0" -> "未知"
                    else -> "未说明"
                }
            )
        val mSex = helper.getView<TextView>(R.id.tv_sex)
        if (item.sex.isEmpty()) {
            mSex.visibility = View.GONE
        } else {
            mSex.visibility = View.VISIBLE
        }
        val tv_nation = helper.getView<TextView>(R.id.tv_nation)
        if (item.nation.isEmpty()) {
            tv_nation.visibility = View.GONE
        } else {
            tv_nation.visibility = View.VISIBLE
        }
        val tv_approve = helper.getView<TextView>(R.id.tv_approve)
        val tv_additional_recording = helper.getView<TextView>(R.id.tv_additional_recording)
        when (isCheck) {
            "1", "2" ->
                if (item.attestation == "1") {
                    tv_approve.text = "已认证"
                    tv_approve.setBackgroundResource(R.drawable.green_shape)
                    tv_additional_recording.visibility = View.GONE
                } else {
                    tv_approve.setBackgroundResource(R.drawable.red_shape2)
                    tv_approve.text = "未认证"
                    tv_additional_recording.visibility = View.VISIBLE
                }
            else ->
                tv_additional_recording.visibility = View.GONE
        }
    }

    /**
     * 1，已预订2，已入住
     */
    fun setOrderState(isCheck: String) {
        this.isCheck = isCheck
        notifyDataSetChanged()
    }

}
