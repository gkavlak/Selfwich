package com.example.selfwich

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.selfwich.databinding.SelfwichListItemBinding
import com.example.selfwich.model.Product
import com.example.selfwich.model.Selfwich
import com.example.selfwich.model.Singleton

class SelfWichAdapter(val clickListener: SelfwichClickListener): ListAdapter<Selfwich, SelfWichAdapter.SelfwichViewHolder>(SelfwichDiffCallbacks()) {

    override fun submitList(list: List<Selfwich>?) {
        super.submitList(list)
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup , viewType: Int): SelfwichViewHolder {
     return SelfwichViewHolder.from(parent)
    }
    class SelfwichViewHolder(private var itemBinding: SelfwichListItemBinding):
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(data: Selfwich, clickListener: SelfwichClickListener) {
            itemBinding.selfwich = data
            itemBinding.selfwichClickListener= clickListener
            itemBinding.domainUser= Singleton.globalUser.value!!
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
    val addLikeSelfWichClickListener: (selfwich: Selfwich) -> Unit,
    val buySelfwichClikListener: (selfwich: Selfwich) -> Unit,
    val deleteSelfwichClickListener: (selfwich: Selfwich) -> Unit

) {
    fun addLikeSelfwichPoint(selfwich: Selfwich) = addLikeSelfWichClickListener(selfwich)
    fun addSelfwichtoOrder(selfwich: Selfwich) = buySelfwichClikListener(selfwich)
    fun deleteSelfwichToDatabase(selfwich: Selfwich) =deleteSelfwichClickListener(selfwich)

}