package com.xinzeyijia.houselocks.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xinzeyijia.houselocks.R
import com.xinzeyijia.houselocks.model.bean.DataBean

class PriceAdapter : BaseQuickAdapter<DataBean, BaseViewHolder>(R.layout.price_item) {
    override fun convert(helper: BaseViewHolder?, item: DataBean?) {
        helper!!
            .setText(R.id.tv_time,  item!!.data)
            .setText(R.id.tv_price, "￥" + item.price)
    }

}
