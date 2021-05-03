package com.example.selfwich

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.selfwich.databinding.ProductItemBinding
import com.example.selfwich.model.Product

class ProductAdapter ():
        ListAdapter<Product,ProductAdapter.ProductViewHolder>(ProductsDiffCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder.from(parent)
    }
            class ProductViewHolder(private var itemBinding:ProductItemBinding):
                    RecyclerView.ViewHolder(itemBinding.root){

                        fun bind(data:Product){
                            itemBinding.productItem=data
                            itemBinding.executePendingBindings()
                        }
                companion object{
                    fun from(parent: ViewGroup):ProductViewHolder{
                        val layoutInflater= LayoutInflater.from(parent.context)
                        val binding=ProductItemBinding.inflate(layoutInflater,parent,false)
                        return ProductViewHolder(binding)
                    }
                }
                    }
    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

}
class ProductsDiffCallback() : DiffUtil.ItemCallback<Product>(){
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem == newItem
    }

}

