package com.example.selfwich.ui.ingredient

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.selfwich.IngredientClickListener
import com.example.selfwich.IngredientsAdapter
import com.example.selfwich.R
import com.example.selfwich.databinding.IngredientFragmentBinding
import com.example.selfwich.model.Ingredient
import com.example.selfwich.repository.IngredientRepository
import com.example.selfwich.viewModel.IngredientViewModel

class IngredientFragment : Fragment() {
    private lateinit var binding: IngredientFragmentBinding
    private lateinit var viewModel: IngredientViewModel
    private lateinit var repository: IngredientRepository

    override fun onCreateView(
            inflater: LayoutInflater , container: ViewGroup? ,
            savedInstanceState: Bundle?
    ): View? {
        binding= IngredientFragmentBinding.inflate(inflater)
        return binding.root


    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {}
        repository= IngredientRepository()
        viewModel = ViewModelProvider(this,IngredientViewModel.Factory(activity.application,repository)).get(IngredientViewModel::class.java)
        binding.viewmodel=viewModel
        binding.lifecycleOwner=this
        binding.ingredientRc.adapter= IngredientsAdapter(IngredientClickListener{ingredient ->
            viewModel.addNewSelfwichIngredient(ingredient)
        })

        val product= Ingredient()

        binding.button2.setOnClickListener {
            val sandwichName=binding.editTextSandwichName.text.toString().trim()


            if(sandwichName.isEmpty()){
                R.string.this_field_cant_be_empty
            }else{

                product.ingredientName=sandwichName
            }
            viewModel.publishSandwich(product)

        }
    }
}