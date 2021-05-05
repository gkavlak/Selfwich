package com.example.selfwich

import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.selfwich.model.Product
    @BindingAdapter("submitList")
    fun setRecyclerViewListData(recyclerView: RecyclerView, listData: List<Product>?) {
        listData?.let {
            val adapter = recyclerView.adapter as ProductAdapter
            adapter.submitList(listData)
        }
    }
@BindingAdapter("imgUrl")
fun setImage(imageView: ImageView , url:String?){
    url?.let {

        val imgUrl = url.toUri().buildUpon().scheme("https").build()

        Glide.with(imageView.context)
                .load(url)
                .apply(
                        RequestOptions()
                                .placeholder(R.drawable.ic_launcher_foreground)
                )
                .into(imageView)
    }
}
