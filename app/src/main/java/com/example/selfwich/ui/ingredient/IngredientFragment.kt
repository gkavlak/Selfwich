package com.example.selfwich.ui.ingredient

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.selfwich.IngredientClickListener
import com.example.selfwich.IngredientsAdapter
import com.example.selfwich.R
import com.example.selfwich.databinding.IngredientFragmentBinding
import com.example.selfwich.model.Ingredient
import com.example.selfwich.model.Singleton
import com.example.selfwich.repository.IngredientRepository
import com.example.selfwich.ui.customDialogFragment.CustomDialogFragment
import com.example.selfwich.ui.register.RegisterFragmentDirections
import com.example.selfwich.viewModel.IngredientViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

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
        binding.ingredientRc.adapter= IngredientsAdapter(IngredientClickListener(
            {ingredient -> viewModel.addNewSelfwichIngredient(ingredient)},
            {ingredient -> viewModel.deleteIngredient(ingredient)})
        )
//        viewModel.newSelfwich.observe(viewLifecycleOwner, {selfwich->
//            selfwich?.let {
//                if (selfwich.selfwichPrice > 0){
//                    setClikListernerOrderNpush()
//                    setClikListernerOrder()
//                }
//            }
//
//        })
        binding.toOrderButton.setOnClickListener {
            val selfwichName =binding.editTextTextSelfwichName.text.toString()
            val selfwichDesc = binding.editTextSelfwichDesc.text.toString()
            viewModel.aaddSelfWichName(selfwichName)
            viewModel.addSelfwichDesc(selfwichDesc)
            viewModel.toOrder()
        }
        binding.orderNPublishbutton.setOnClickListener {
            val selfwichName =binding.editTextTextSelfwichName.text.toString()
            val selfwichDesc = binding.editTextSelfwichDesc.text.toString()
            viewModel.aaddSelfWichName(selfwichName)
            viewModel.addSelfwichDesc(selfwichDesc)
            viewModel.goToDataBase()
        }
        viewModel.isSuccess.observe(viewLifecycleOwner,{
            binding.toOrderButton.isEnabled= it
            binding.orderNPublishbutton.isEnabled = it
        })



        binding.imageButton5.setOnClickListener {
            val type = "ingredient"
            CustomDialogFragment(type)
                .show(childFragmentManager,"")
        }
        Singleton.globalUser.observe(viewLifecycleOwner, {
            when(it.userType){
                "customer"->forCostumers()
            }
        })

    }
    fun forCostumers(){
        binding.imageButton5.visibility = View.GONE
    }



}