package com.kaleichyk.custompagination

import androidx.lifecycle.LifecycleCoroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.kaleichyk.pagination.PaginationListener
import com.kaleichyk.pagination.models.sealeds.LoadResult
import com.kaleichyk.pagination.showLog
import retrofit2.HttpException

class MainPaginationListener(
    private val repository: MainRepository,
    layoutManager: LinearLayoutManager,
    lifecycleScope: LifecycleCoroutineScope
) : PaginationListener(layoutManager, lifecycleScope, PAGE_SIZE) {

    companion object {
        const val PAGE_SIZE = 10
    }

    override suspend fun load(page: Int): LoadResult {
        showLog("start load page = $page")
        val result = repository.getListTestEntities(page, PAGE_SIZE)
        return if (!result.isSuccessful) LoadResult.ERROR(HttpException(result).toPaginationError())
        else LoadResult.SUCCESSFUL(result.body()!!.toDataItem())
    }
}