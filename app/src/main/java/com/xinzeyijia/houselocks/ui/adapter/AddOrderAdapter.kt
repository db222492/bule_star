package com.xinzeyijia.houselocks.ui.adapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xinzeyijia.houselocks.R
import com.xinzeyijia.houselocks.model.bean.DataBean
import com.xinzeyijia.houselocks.util.ImageUtil
import com.xinzeyijia.houselocks.util.Tools

class AddOrderAdapter : BaseQuickAdapter<DataBean, BaseViewHolder>(R.layout.add_order_item) {

    override fun convert(helper: BaseViewHolder?, item: DataBean?) {
        if (item!!.idcard_id.isEmpty()) {
            item.idcard_id =  item.idcard
        }
        helper!!
            .setText(R.id.tv_phone, "手机号：" + item!!.phone)
            .setText(R.id.tv_name, "姓名：" + item.name)
            .setText(
                R.id.tv_idcard,
                "身份证号：" + item.idcard_id
            )
            .addOnClickListener(R.id.img_back)
        val imageView = helper.getView<ImageView>(R.id.img_idcard)
        if (Tools.NotNull(item.idcard_img)) {
            ImageUtil.getInstance().getImg(mContext, item.idcard_img, imageView, 0, 0, false)
        } else {
            imageView.setImageResource(R.mipmap.idcard_img)
        }
    }
}
