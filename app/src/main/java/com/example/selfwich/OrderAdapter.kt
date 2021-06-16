package com.example.selfwich

import android.text.Layout
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.selfwich.ProductAdapter.ProductViewHolder.Companion.from
import com.example.selfwich.databinding.OrderListItemBinding
import com.example.selfwich.model.Order
import com.example.selfwich.model.Product
import com.example.selfwich.model.Selfwich

class OrderAdapter (val clickListener: OrderClickListener) : androidx.recyclerview.widget.ListAdapter<Order,OrderAdapter.OrderViewHolder>(OrderDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderAdapter.OrderViewHolder {
        return OrderAdapter.OrderViewHolder.from(parent)
    }
    class OrderViewHolder(private val itemBinding:OrderListItemBinding):
        RecyclerView.ViewHolder(itemBinding.root){

            fun bind(data: Order,clickListener: OrderClickListener){
                itemBinding.orderListItem=data
                itemBinding.orderClickListener=clickListener
                itemBinding.executePendingBindings()
            }
        companion object {
            fun from(parent:ViewGroup):OrderViewHolder{
                val layoutInflater=LayoutInflater.from(parent.context)
                val binding=OrderListItemBinding.inflate(layoutInflater,parent,false)
                return OrderViewHolder(binding)
            }
        }

        }
    override fun onBindViewHolder(holder: OrderAdapter.OrderViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item,clickListener)
    }

}







class OrderClickListener(val readyClickListener: (order: Order) -> Unit,
                         val canceledClickListener: (order: Order) -> Unit,
                         val detailsClickListener:(order: Order)-> Unit)
{
    fun orderDetails(order:Order) = detailsClickListener(order)
    fun orderIsReady(order: Order) = readyClickListener(order)
    fun orderIsCanceled(order: Order) = canceledClickListener(order)

}
class OrderDiffCallBack() : DiffUtil.ItemCallback<Order>(){
    override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean {
        return oldItem == newItem
    }

}