package com.example.selfwich

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.selfwich.model.*

@BindingAdapter("submitList")
fun setRecyclerViewListData(recyclerView: RecyclerView, listData: List<Product>?) {
    listData?.let {
        val sortedlist = it.sortedByDescending { pr-> pr.pLike }
        val adapter = recyclerView.adapter as ProductAdapter
        adapter.submitList(sortedlist)
    }
}

@BindingAdapter("orderProductList")
fun setRecyclerViewListOrderProduct(recyclerView: RecyclerView, listData: List<Product>?) {
    listData?.let {
        val adapter = recyclerView.adapter as OrderDetailsProductAdapter
        adapter.submitList(listData)
    }
}

@BindingAdapter("orderSelfwichList")
fun setRecyclerViewListOrderSelfwich(recyclerView: RecyclerView, listData: List<Selfwich>?) {
    listData?.let {
        val adapter = recyclerView.adapter as OrderDetailsSelfwichAdapter
        adapter.submitList(listData)
    }
}
@BindingAdapter("inviseblyForCustomer")
fun checkIsCostumer(view: View, string: String) {

    when(string){
        "customer"->view.visibility= View.GONE
    }
}
@BindingAdapter("inviseblyForAdmin")
fun checkIsAdmin(view: View, string: String) {
    when(string){
        "admin"->view.visibility= View.GONE
    }
}

@BindingAdapter("submitSelfwichList")
fun setRecylerViewListSelfwich(recyclerView: RecyclerView, listData: List<Selfwich>?) {
    listData?.let {
        val sortedlist = it.sortedByDescending { el->
            el.selfwichLike }
        val adapter = recyclerView.adapter as SelfWichAdapter
        adapter.submitList(sortedlist)
    }
}

@BindingAdapter("submitOrderList")
fun setRecylerViewListOrder(recyclerView: RecyclerView, listData: List<Order>?) {
    listData?.let {
        val adapter = recyclerView.adapter as OrderAdapter
        adapter.submitList(listData)

    }
}


@BindingAdapter("submitIngredients")
fun setRecyclerViewListIngredient(recyclerView: RecyclerView, listData: List<Ingredient>?) {
    listData?.let {
        val adapterIngredient = recyclerView.adapter as IngredientsAdapter
        adapterIngredient.submitList(listData)
    }
}

@BindingAdapter("changeText")
fun TextView.changeText(ingredient: Ingredient) {
    ingredient.let {
        if (ingredient.ingredientIsAdded) {
            text = "Drop"
            Log.i("Click", "Button is changed ")
        } else {
            text = "ADD"
            Log.i("Click", "Button is changed ")
        }
    }
}

@BindingAdapter("imgUrl")
fun setImage(imageView: ImageView, url: String?) {
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

@BindingAdapter("textWithPrice")
fun setTextWithPrice(textView: TextView, number: Long?) {
    number?.let {

        textView.text = "$number "

    }
}

@BindingAdapter("textWithDolarSign")
fun setTextWithDolarSign(textView: TextView, number: Long?) {
    number?.let {

        textView.text = "$number$"

    }
}