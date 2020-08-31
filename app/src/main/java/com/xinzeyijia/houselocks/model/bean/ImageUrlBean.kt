package com.xinzeyijia.houselocks.model.bean

import com.chad.library.adapter.base.entity.MultiItemEntity

class ImageUrlBean : MultiItemEntity {
    override fun getItemType(): Int {
        return 0
    }

    var url: String = ""

    constructor(url: String) {
        this.url = url
    }

}