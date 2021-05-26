package com.example.selfwich

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.selfwich.databinding.IngredientListItemsBinding
import com.example.selfwich.model.Ingredient

class IngredientsAdapter(): androidx.recyclerview.widget.ListAdapter<Ingredient, IngredientsAdapter.IngredientViewHolder>(IngredientDiffCallbacks())
        {

    override fun onCreateViewHolder(parent: ViewGroup , viewType: Int):IngredientViewHolder {
        return IngredientViewHolder.from(parent)
    }
        class IngredientViewHolder(private var itemBinding: IngredientListItemsBinding):
             RecyclerView.ViewHolder(itemBinding.root) {
            fun bind(data: Ingredient) {
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


class IngredientDiffCallbacks() : DiffUtil.ItemCallback<Ingredient>(){
    override fun areItemsTheSame(oldItem: Ingredient, newItem: Ingredient): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Ingredient, newItem: Ingredient): Boolean {
        return oldItem == newItem
    }
}

