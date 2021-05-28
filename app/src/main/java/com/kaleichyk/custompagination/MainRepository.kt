package com.kaleichyk.custompagination

import com.kaleichyk.custompagination.data.ServerApi


class MainRepository(private val api: ServerApi) {

    suspend fun getListTestEntities(page: Int, limit: Int) =
        api.getListTestEntities(page, limit)

}