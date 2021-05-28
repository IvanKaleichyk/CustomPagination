package com.kaleichyk.custompagination.data

import com.kaleichyk.custompagination.models.DataDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ServerApi {

    @GET("list")
    suspend fun getListTestEntities(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
    ): Response<DataDTO>

}