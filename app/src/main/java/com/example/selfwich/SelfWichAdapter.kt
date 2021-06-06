package com.example.selfwich

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.selfwich.databinding.SelfwichListItemBinding
import com.example.selfwich.model.Selfwich

class SelfWichAdapter(val clickListener: SelfwichClickListener): androidx.recyclerview.widget.ListAdapter<Selfwich, SelfWichAdapter.SelfwichViewHolder>(SelfwichDiffCallbacks()) {

    override fun onCreateViewHolder(parent: ViewGroup , viewType: Int): SelfwichViewHolder {
     return SelfwichViewHolder.from(parent)
    }
    class SelfwichViewHolder(private var itemBinding: SelfwichListItemBinding):
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(data: Selfwich, clickListener: SelfwichClickListener) {
            itemBinding.selfwich = data
            itemBinding.selfwichClickListener= clickListener
            itemBinding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): SelfwichViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = SelfwichListItemBinding.inflate(layoutInflater , parent , false)
                return SelfwichViewHolder(binding)
            }
        }
    }
    override fun onBindViewHolder(holder: SelfwichViewHolder , position: Int) {
        val item=getItem(position)
        holder.bind(item,clickListener)
    }

}
class SelfwichDiffCallbacks() : DiffUtil.ItemCallback<Selfwich>(){
    override fun areItemsTheSame(oldItem: Selfwich, newItem: Selfwich): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Selfwich, newItem: Selfwich): Boolean {
        return oldItem == newItem
    }
}
class SelfwichClickListener(
    val addLikeSelfWichClickListener: (selfwich: Selfwich) -> Unit

) {
    fun addLikeSelfwichPoint(selfwich: Selfwich) = addLikeSelfWichClickListener(selfwich)

}