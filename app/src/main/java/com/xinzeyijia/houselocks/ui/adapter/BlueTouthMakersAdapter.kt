package com.xinzeyijia.houselocks.ui.adapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xinzeyijia.houselocks.R
import com.xinzeyijia.houselocks.base.Common.*
import com.xinzeyijia.houselocks.model.bean.DataBean

class BlueTouthMakersAdapter :
    BaseQuickAdapter<DataBean, BaseViewHolder>(R.layout.bluetouth_makers_item) {
    override fun convert(helper: BaseViewHolder, item: DataBean) {
        helper
            .setText(R.id.tv_title, item.lock_type)
        val imgBg = helper.getView<ImageView>(R.id.img_bg)
        when (item.lock_type_en) {
            JWT_V1 -> {
                imgBg.setImageResource(R.drawable.yellow_shape)
            }
            SKY_V1 -> {
                imgBg.setImageResource(R.drawable.darkblue_shape_gradual)
            }
            WSL_V1 -> {
                imgBg.setImageResource(R.drawable.green_shape)
            }
        }
    }
}