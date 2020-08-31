package com.xinzeyijia.houselocks.ui.adapter

import android.view.View
import android.widget.RadioGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xinzeyijia.houselocks.R
import com.xinzeyijia.houselocks.model.bean.AddRoomBean3
import com.xinzeyijia.houselocks.model.bean.DataBean

class MatingAdapter : BaseQuickAdapter<AddRoomBean3, BaseViewHolder>(R.layout.mating_item) {
    private var type: String = ""
    override fun convert(helper: BaseViewHolder?, item: AddRoomBean3?) {
        helper!!.setText(R.id.tv_title, item!!.name)
        val group = helper.getView<RadioGroup>(R.id.rg)
        if (type == "1") {
            group.visibility = View.GONE
        }
        group.check(if (item.is_select) R.id.cbx_offer else R.id.cbx_un_offer)
        group.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.cbx_offer -> item.is_select = true
                else -> item.is_select = false
            }
        }
    }

    fun setType(type: String) {
        this.type = type
    }
}
