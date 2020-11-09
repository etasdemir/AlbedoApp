package com.elacqua.albedo.ui.search

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.elacqua.albedo.data.remote.jikan_api.model.SearchResult

abstract class SearchRecyclerAdapter<T>
    : SearchResult, RecyclerView.Adapter<SearchRecyclerAdapter<T>.SearchViewHolder>() {

    protected val dataList = ArrayList<T>()

    fun setDataList(data: List<T>) {
        dataList.clear()
        dataList.addAll(data)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.onBind(position)
        holder.onClick(position)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    abstract inner class SearchViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        abstract fun onBind(position: Int)
        abstract fun onClick(position: Int)

    }
}

interface OnSearchSelected<T> {
    fun onClick(item: T)
}