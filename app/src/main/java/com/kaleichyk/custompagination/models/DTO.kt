package com.kaleichyk.custompagination.models

import java.util.*

class DataDTO : ArrayList<DataItemDTO>() {
    fun toDataItem(): List<DataItem> {
        val res = mutableListOf<DataItem>()
        for (i in this) res.add(i.toDataItem())
        return res
    }
}

data class DataItemDTO(
    val author: String,
    val download_url: String,
    val height: Int,
    val id: String,
    val url: String,
    val width: Int
) {
    fun toDataItem() = DataItem(
        author = author,
        download_url = download_url,
        height = height,
        id = id,
        url = url,
        width = width,
        dateGetting = Date().time
    )
}