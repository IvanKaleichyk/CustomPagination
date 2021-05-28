package com.kaleichyk.custompagination

import androidx.lifecycle.ViewModel
import com.kaleichyk.custompagination.models.DataItem

class MainViewModel : ViewModel() {

    private val _list = mutableListOf<DataItem>()
    val list: List<DataItem> get() = _list

    fun addToList(addList: List<DataItem>) {
        _list.addAll(addList)
    }
}