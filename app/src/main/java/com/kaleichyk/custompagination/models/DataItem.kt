package com.kaleichyk.custompagination.models

import com.kaleichyk.pagination.models.PaginationData

data class DataItem(
    val author: String,
    val download_url: String,
    val height: Int,
    val id: String,
    val url: String,
    val width: Int,
    val dateGetting: Long
) : PaginationData()
