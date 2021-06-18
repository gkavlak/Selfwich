package com.example.selfwich

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.selfwich.databinding.ProductItemBinding
import com.example.selfwich.model.Product
import com.example.selfwich.model.Singleton

class ProductAdapter(val clickListener: ProductClickListener) :
        ListAdapter<Product,ProductAdapter.ProductViewHolder>(ProductsDiffCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder.from(parent)
    }
            class ProductViewHolder(private var itemBinding:ProductItemBinding):
                    RecyclerView.ViewHolder(itemBinding.root){

                        fun bind(data:Product, clickListener: ProductClickListener){
                            itemBinding.productItem=data
                            itemBinding.productClickListener=clickListener
                            itemBinding.domainUser= Singleton.globalUser.value!!
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
        holder.bind(item,clickListener)
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
class ProductClickListener(
        val addLikeClickListener: (product: Product) -> Unit,
        val buyProductClikListener:(product: Product) -> Unit,
        val deleteProductClickListener: (product:Product)-> Unit,
        val updateProductClickListener: (product:Product)-> Unit

) {
    fun addLikePoint(product: Product) = addLikeClickListener(product)
    fun addProducttoOrder(product: Product) = buyProductClikListener(product)
    fun deleteProduct(product:Product) = deleteProductClickListener(product)
    fun updateProduct(product: Product) = updateProductClickListener(product)

}


