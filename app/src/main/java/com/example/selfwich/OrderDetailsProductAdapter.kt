package com.example.selfwich

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.selfwich.databinding.OrderDetailsProducListItemBinding
import com.example.selfwich.model.Product
import com.example.selfwich.model.Singleton


class OrderDetailsProductAdapter(val clickListener:OrderDetailsProductClickListener):
    ListAdapter<Product,OrderDetailsProductAdapter.OrderProductViewHolder>(OrderProductsDiffCallback()) {
    override fun submitList(list: List<Product>?) {
        super.submitList(list)
        this.notifyDataSetChanged()
    }



        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
                OrderProductViewHolder {
            return OrderProductViewHolder.from(parent)
        }
        override fun onBindViewHolder(holder: OrderProductViewHolder,position: Int){
            val item = getItem(position)
            holder.bind(item,clickListener)
    }

            class OrderProductViewHolder(private var itemBinding: OrderDetailsProducListItemBinding)
                :RecyclerView.ViewHolder(itemBinding.root) {
                    fun bind (data: Product,clickListener: OrderDetailsProductClickListener){
                        itemBinding.orderProductItem=data
                        itemBinding.orderProductClickListener=clickListener
                        itemBinding.domainUser= Singleton.globalUser.value!!
                        itemBinding.executePendingBindings()
                    }
                companion object{
                    fun from(parent: ViewGroup):OrderProductViewHolder{
                        val layoutInflater = LayoutInflater.from(parent.context)
                        val binding= OrderDetailsProducListItemBinding.inflate(layoutInflater,parent,false)
                        return OrderProductViewHolder(binding)
                    }
                }
            }

    }

class OrderProductsDiffCallback() : DiffUtil.ItemCallback<Product>(){
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem == newItem
    }

}


class OrderDetailsProductClickListener(
    val deleteOrderProductClikListener : (product: Product) -> Unit)
{
    fun deleteProductInOrder(product: Product)=deleteOrderProductClikListener(product)

}