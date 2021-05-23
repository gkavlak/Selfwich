package com.example.selfwich

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.selfwich.databinding.IngredientListItemsBinding
import com.example.selfwich.model.Product

class IngredientsAdapter(): androidx.recyclerview.widget.ListAdapter<Product, IngredientsAdapter.IngredientViewHolder>(ProductDiffCallbacks())
        {

    override fun onCreateViewHolder(parent: ViewGroup , viewType: Int):IngredientViewHolder {
        return IngredientViewHolder.from(parent)
    }
        class IngredientViewHolder(private var itemBinding: IngredientListItemsBinding):
             RecyclerView.ViewHolder(itemBinding.root) {
            fun bind(data: Product) {
                itemBinding.ingredientListItem = data
                itemBinding.executePendingBindings()
            }

            companion object {
                fun from(parent: ViewGroup): IngredientViewHolder {
                    val layoutInflater = LayoutInflater.from(parent.context)
                    val binding = IngredientListItemsBinding.inflate(layoutInflater , parent , false)
                    return IngredientViewHolder(binding)
                }
            }
        }

            override fun onBindViewHolder(holder: IngredientViewHolder , position: Int) {
                val item=getItem(position)
                holder.bind(item)

            }
        }


class ProductDiffCallbacks() : DiffUtil.ItemCallback<Product>(){
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem == newItem
    }

}
