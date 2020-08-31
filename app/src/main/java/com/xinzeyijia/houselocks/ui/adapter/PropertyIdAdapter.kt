package com.xinzeyijia.houselocks.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xinzeyijia.houselocks.R
import com.xinzeyijia.houselocks.model.bean.DataBean


class PropertyIdAdapter : BaseQuickAdapter<DataBean, BaseViewHolder>(R.layout.property_id_item) {
    override fun convert(helper: BaseViewHolder?, item: DataBean?) {
        helper!!.addOnClickListener(R.id.tv_add_room)
            .addOnClickListener(R.id.tv_delete)
        helper.setText(R.id.tv_title, item!!.village_name)
            .setText(R.id.tv_address, if (item.village_area == "create") "" else item.village_area)
            .setText(R.id.tv_detailed_address, if (item.village_address == "create") "" else item.village_address)
            .setText(R.id.tv_room_num, "房间数："+if (item.roomNum == "") "0" else item.roomNum)
    }
}
