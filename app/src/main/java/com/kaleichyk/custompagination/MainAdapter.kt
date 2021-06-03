package com.kaleichyk.custompagination

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import coil.clear
import coil.load
import com.kaleichyk.custompagination.models.DataItem
import com.kaleichyk.pagination.PagingAdapter
import com.kaleichyk.pagination.PagingDiffUtil
import com.kaleichyk.pagination.getView
import com.kaleichyk.pagination.models.PaginationError
import com.kaleichyk.pagination.viewHolders.ErrorViewHolder
import com.kaleichyk.pagination.viewHolders.LoadingViewHolder
import com.kaleichyk.pagination.viewHolders.ShowDataViewHolder

class MainAdapter(
    paginationListener: MainPaginationListener,
    lifecycle: Lifecycle,
    onAddNewItem: (addToList: List<DataItem>) -> Unit
) : PagingAdapter<DataItem>(
    paginationListener,
    lifecycle,
    onAddNewItem,
    object : PagingDiffUtil<DataItem> {
        override fun compare(o1: DataItem, o2: DataItem): Int =
            (o1.dateGetting - o2.dateGetting).toInt()

        override fun areItemsTheSame(item1: DataItem, item2: DataItem): Boolean =
            item1 == item2
    }
) {

    override fun createShowDataViewHolder(parent: ViewGroup): ShowDataViewHolder<DataItem> {
        return MainViewHolder(parent.getView(R.layout.item_rv_show_data))
    }

    override fun createLoadingViewHolder(parent: ViewGroup) = LoadingViewHolder(
        parent.getView(R.layout.item_rv_loading)
    )

    override fun createErrorViewHolder(parent: ViewGroup): ErrorViewHolder {
        return ItemErrorViewHolder(parent.getView(R.layout.item_rv_error))
    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        if (holder is MainViewHolder) holder.clear()
    }

    class MainViewHolder(view: View) : ShowDataViewHolder<DataItem>(view) {

        private val img: ImageView by lazy {
            itemView.findViewById(R.id.img)
        }

        private val text: TextView by lazy {
            itemView.findViewById(R.id.text)
        }

        fun clear() {
            img.clear()
        }

        override fun bind(data: DataItem) {
            text.text = data.id
            img.load(data.url) {
                allowRgb565(true)
            }
        }

    }

    class ItemErrorViewHolder(view: View) : ErrorViewHolder(view) {

        private val textError: TextView by lazy { view.findViewById(R.id.errorText) }

        override fun bind(error: PaginationError) {
            val message = "${error.code}, ${error.message}"
            textError.text = message
        }
    }

}