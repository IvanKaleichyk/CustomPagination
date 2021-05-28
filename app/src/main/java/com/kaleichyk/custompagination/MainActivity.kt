package com.kaleichyk.custompagination

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kaleichyk.custompagination.data.RetrofitInstance
import com.kaleichyk.custompagination.models.DataItem
import com.kaleichyk.pagination.models.sealeds.PagingState
import com.kaleichyk.pagination.showLog
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val rv: RecyclerView by lazy {
        findViewById(R.id.rv)
    }

    private val adapter = MainAdapter()

    private val repository by lazy { MainRepository(RetrofitInstance.api) }

    private lateinit var paginationListener: MainPaginationListener

    private val viewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupRV()
        setupPagination()
        subscribe()
    }

    private fun setupPagination() {
        if (viewModel.list.isEmpty()) paginationListener.loadFirstData()
        else adapter.addToList(viewModel.list)
    }

    private fun subscribe() {
        lifecycleScope.launch {
            paginationListener.state
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect {
                    when (it) {
                        is PagingState.ReturnResult<*> -> {
                            adapter.removeLoading()
                            showLog("PagingState.ReturnResult: size = ${it.data.size}, ${it.data}")
                            @Suppress("UNCHECKED_CAST")
                            addNewList(it.data as List<DataItem>)
                        }
                        is PagingState.Error -> {
                            showLog("PagingState.Error, $it")
                            adapter.showError(it.error)
                        }
                        is PagingState.Loading -> {
                            showLog("PagingState.Loading")
                            adapter.addLoading()
                        }
                        is PagingState.WaitingForLoading -> showLog("PagingState.WaitingForLoading")
                    }
                }
        }
    }

    private fun setupRV() {
        val layoutManager = LinearLayoutManager(applicationContext)
        paginationListener = MainPaginationListener(repository, layoutManager, lifecycleScope)
        rv.run {
            this.layoutManager = layoutManager
            addOnScrollListener(paginationListener)
            adapter = this@MainActivity.adapter
        }
    }

    private fun addNewList(newList: List<DataItem>) {
        viewModel.addToList(newList)
        adapter.addToList(newList)
    }

}