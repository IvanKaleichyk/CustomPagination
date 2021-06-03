package com.kaleichyk.custompagination

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kaleichyk.custompagination.data.RetrofitInstance

class MainActivity : AppCompatActivity() {

    private val rv: RecyclerView by lazy {
        findViewById(R.id.rv)
    }

    private val adapter by lazy {
        MainAdapter(paginationListener, lifecycle) { viewModel.addToList(it) }
    }

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
    }

    private fun setupPagination() {
        if (viewModel.list.isEmpty()) paginationListener.loadFirstData()
        else adapter.submitList(viewModel.list)
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
}