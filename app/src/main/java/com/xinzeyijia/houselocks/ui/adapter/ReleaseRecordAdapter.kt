package com.xinzeyijia.houselocks.ui.adapter

import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xinzeyijia.houselocks.R
import com.xinzeyijia.houselocks.model.bean.DataBean


class ReleaseRecordAdapter : BaseQuickAdapter<DataBean, BaseViewHolder>(R.layout.release_record_item) {
    override fun convert(helper: BaseViewHolder?, item: DataBean?) {
        helper!!
            .addOnClickListener(R.id.tv_look)
        item!!.apply {
            helper.setText(R.id.tv_title, updateType)
                .setText(R.id.tv_time, updTime)

            val tv_state = helper.getView<TextView>(R.id.tv_state)
            val tv_title = helper.getView<TextView>(R.id.tv_title)
            val tv_look = helper.getView<TextView>(R.id.tv_look)
            tv_title.isSelected=true
            when (reviseStatus) {
                "2" -> {
                    tv_state.text="已通过"
                    tv_state.setTextColor(ContextCompat.getColor(mContext, R.color.green))
                    tv_look.visibility= View.GONE
                }
                "3" -> {
                    tv_state.text="已驳回"
                    tv_state.setTextColor(ContextCompat.getColor(mContext, R.color.red))
                    tv_look.visibility= View.VISIBLE
                }
                "1" -> {
                    tv_state.text="审核中"
                    tv_state.setTextColor(ContextCompat.getColor(mContext, R.color.blue))
                    tv_look.visibility= View.GONE
                }
            }
        }

    }
}
