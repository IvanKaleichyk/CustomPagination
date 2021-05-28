package com.kaleichyk.custompagination

import com.kaleichyk.pagination.models.PaginationError
import retrofit2.HttpException

fun HttpException.toPaginationError() = PaginationError(
    message(), code()
)