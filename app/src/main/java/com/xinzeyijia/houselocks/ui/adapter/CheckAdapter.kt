package com.xinzeyijia.houselocks.ui.adapter

import android.support.v4.content.ContextCompat
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xinzeyijia.houselocks.R
import com.xinzeyijia.houselocks.base.Common
import com.xinzeyijia.houselocks.model.bean.DataBean

class CheckAdapter : BaseQuickAdapter<DataBean, BaseViewHolder>(R.layout.checkpopu_item) {
    override fun convert(helper: BaseViewHolder?, item: DataBean?) {
        val title = helper!!
            .getView<TextView>(R.id.tv_title)
        title.text = item!!.title
        val drawable = ContextCompat.getDrawable(mContext, item.img_icon)

        title.setCompoundDrawablesRelativeWithIntrinsicBounds(null, drawable, null, null)
    }

}
