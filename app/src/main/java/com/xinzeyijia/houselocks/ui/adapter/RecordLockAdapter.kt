package com.xinzeyijia.houselocks.ui.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xinzeyijia.houselocks.R
import com.xinzeyijia.houselocks.model.bean.DataBean
import com.xinzeyijia.houselocks.model.http.Urls.getFile
import com.xinzeyijia.houselocks.util.ImageUtil

class RecordLockAdapter : BaseQuickAdapter<DataBean, BaseViewHolder>(R.layout.record_lock_item) {
    override fun convert(helper: BaseViewHolder?, item: DataBean?) {
        helper!!
            .setText(R.id.tv_time, item!!.upd_time)
            .setText(R.id.tv_order_phone, "手机号："+item.phone)
            .setText(R.id.tv_id_card,"证件号："+ item.identity)
            .setText(R.id.tv_mac, "mac：" + item.mac)
        val textView = helper.getView<TextView>(R.id.tv_order_name)
        val img_avatar = helper.getView<ImageView>(R.id.img_avatar)
        val tv_id_card = helper.getView<TextView>(R.id.tv_id_card)
        val tv_open_type = helper.getView<TextView>(R.id.tv_open_type)
        if (item.open_type=="1") {
            tv_open_type.text = "开锁成功"
        } else if (item.open_type == "2") {
            tv_open_type.text = "开锁失败"
        } else {
            tv_open_type.text = "动态密码开锁成功"
        }
        if (item.identity.isEmpty() && item.img_url.isEmpty()) {
            textView.text = "房东"
            img_avatar.visibility=View.GONE
            tv_id_card.visibility=View.GONE
        } else {
            textView.text = "客人"
            img_avatar.visibility=View.VISIBLE
            tv_id_card.visibility=View.VISIBLE
            ImageUtil.getInstance().getRoundRadiusImg(mContext, (getFile+item.upload).replace("\\",""), img_avatar, 0, 0,50, false)
        }




    }
}