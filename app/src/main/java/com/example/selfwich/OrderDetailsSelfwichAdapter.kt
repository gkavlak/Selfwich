package com.example.selfwich

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

import com.example.selfwich.databinding.OrderDetailsSelfwichListItemBinding
import com.example.selfwich.model.Product
import com.example.selfwich.model.Selfwich

class OrderDetailsSelfwichAdapter(val clickListener: OrderDetailsSelfwichClickListener) :
    ListAdapter<Selfwich, OrderDetailsSelfwichAdapter.OrderDetailsSelfwichViewHolder>(
        OrderDetailsSelfwichDiffCallbacks()
    ) {
    override fun submitList(list: List<Selfwich>?) {
        super.submitList(list)
        this.notifyDataSetChanged()
    }

    class OrderDetailsSelfwichViewHolder(private var itemBinding: OrderDetailsSelfwichListItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(data: Selfwich, clickListener: OrderDetailsSelfwichClickListener) {
            itemBinding.orderSelfwichItem = data
            itemBinding.orderDetailsSelfwichClickListener = clickListener
            itemBinding.executePendingBindings()

        }

        companion object {
            fun from(parent: ViewGroup): OrderDetailsSelfwichViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding =
                    OrderDetailsSelfwichListItemBinding.inflate(layoutInflater, parent, false)
                return OrderDetailsSelfwichViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            OrderDetailsSelfwichAdapter.OrderDetailsSelfwichViewHolder {
        return OrderDetailsSelfwichAdapter.OrderDetailsSelfwichViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: OrderDetailsSelfwichViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }

}


class OrderDetailsSelfwichClickListener(
    val deleteOrderSelfwichClikListener: (selfwich: Selfwich) -> Unit
) {
    fun deleteSelfwichinOrder(selfwich: Selfwich) = deleteOrderSelfwichClikListener(selfwich)

}

class OrderDetailsSelfwichDiffCallbacks() : DiffUtil.ItemCallback<Selfwich>() {
    override fun areItemsTheSame(oldItem: Selfwich, newItem: Selfwich): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Selfwich, newItem: Selfwich): Boolean {
        return oldItem == newItem
    }
}