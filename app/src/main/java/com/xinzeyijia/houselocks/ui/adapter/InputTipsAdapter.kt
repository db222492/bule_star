package com.xinzeyijia.houselocks.ui.adapter

import android.widget.ImageView
import com.amap.api.services.core.PoiItem
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xinzeyijia.houselocks.R

class InputTipsAdapter
    : BaseQuickAdapter<PoiItem, BaseViewHolder>(R.layout.item_poi_address) {
    private var selectedPosition: Int = 0

    override fun convert(helper: BaseViewHolder, item: PoiItem) {
        helper.setText(R.id.name_tv, item.title)
        helper.setText(R.id.address_tv, item.snippet)
        val check_image_iv = helper.getView<ImageView>(R.id.check_image_iv)
        if (selectedPosition == helper.adapterPosition) {
            check_image_iv.setImageResource(R.mipmap.icon_selected)
        } else {
            check_image_iv.setImageResource(0)
        }
    }


    fun changeSelected(positon: Int) {
        if (positon != selectedPosition) {
            selectedPosition = positon
            notifyDataSetChanged()
        }
    }

};
