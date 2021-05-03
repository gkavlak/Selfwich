package com.example.selfwich

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.selfwich.model.Product

    @BindingAdapter("submitList")
    fun setRecyclerViewListData(recyclerView: RecyclerView, listData: List<Product>?) {
        listData?.let {
            val adapter = recyclerView.adapter as ProductAdapter
            adapter.submitList(listData)
        }
    }
