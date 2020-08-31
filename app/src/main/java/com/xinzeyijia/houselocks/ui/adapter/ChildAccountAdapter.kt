package com.xinzeyijia.houselocks.ui.adapter

import android.databinding.adapters.TextViewBindingAdapter.setText
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xinzeyijia.houselocks.R
import com.xinzeyijia.houselocks.model.bean.DataBean


class ChildAccountAdapter : BaseQuickAdapter<DataBean, BaseViewHolder>(R.layout.child_account_item) {
    override fun convert(helper: BaseViewHolder?, item: DataBean?) {
        helper!!.apply {
            item!!.apply {
                setText(R.id.tv_account_name, userName)
                    .setText(R.id.tv_account_phone, "手机号：$phone")
                    .setText(R.id.tv_name, "姓名：$realName")
                    .setText(R.id.tv_state, "状态：${if (status == "1") "启用" else "未启用"}")
            }
        }

    }
}
