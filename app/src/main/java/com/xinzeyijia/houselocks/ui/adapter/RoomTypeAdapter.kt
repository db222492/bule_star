package com.xinzeyijia.houselocks.ui.adapter

import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xinzeyijia.houselocks.R
import com.xinzeyijia.houselocks.model.bean.ItemBean
import com.xinzeyijia.houselocks.utils.ToastUtil

class RoomTypeAdapter : BaseQuickAdapter<ItemBean, BaseViewHolder>(R.layout.room_type_item) {
    override fun convert(helper: BaseViewHolder?, item: ItemBean?) {
        val img_remove_count = helper!!.getView<ImageView>(R.id.img_remove_count)
        val img_add_count = helper.getView<ImageView>(R.id.img_add_count)
        val tv_count_num = helper.getView<TextView>(R.id.tv_count_num)
        var num = item!!.price
        tv_count_num.text =num.toString()
        img_add_count.setOnClickListener {
             num += 1
            item.price =num
            tv_count_num.text = num.toString()
        }
        img_remove_count.setOnClickListener {
            if (num < 1) {
                ToastUtil.show("数量不能小于0")
            } else {
                num -= 1
            }
            item.price =num
            tv_count_num.text = num.toString()
        }
        helper.setText(R.id.tv_title, item.name)

    }

}

