package com.xinzeyijia.houselocks.ui.adapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xinzeyijia.houselocks.R
import com.xinzeyijia.houselocks.model.bean.DataBean
import com.xinzeyijia.houselocks.model.http.Urls
import com.xinzeyijia.houselocks.util.ImageUtil
import com.xinzeyijia.houselocks.util.Tools
import kotlinx.android.synthetic.main.activity_room_detail.*

class RoomImageAdapter : BaseQuickAdapter<String, BaseViewHolder>(R.layout.img_item) {

    override fun convert(helper: BaseViewHolder?, item: String?) {
        val imageView = helper!!.getView<ImageView>(R.id.fiv_img)
        ImageUtil.getInstance().getRoundRadiusImg(mContext, (Urls.getFile + item).replace("\\", ""), imageView, 0, 0, 12,false)

    }
}
