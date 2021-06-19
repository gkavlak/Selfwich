package com.example.selfwich.ui.customDialogFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.selfwich.R
import com.example.selfwich.databinding.CustomDialogFragmentBinding
import com.example.selfwich.model.Ingredient
import com.example.selfwich.model.Product
import com.example.selfwich.repository.CustomDialogRepository
import com.example.selfwich.viewModel.CustomDialogViewModel

class CustomDialogFragment(val tpye:String) :DialogFragment() {
    private lateinit var binding: CustomDialogFragmentBinding
    private lateinit var customDialogRepository: CustomDialogRepository
    private lateinit var viewModel: CustomDialogViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = CustomDialogFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {}
        customDialogRepository = CustomDialogRepository()

        viewModel = ViewModelProvider(
            this,
            CustomDialogViewModel.Factory(activity.application, customDialogRepository)
        ).get(CustomDialogViewModel::class.java)



             val product = Product()

         when(tpye){
             "drinks"-> forDrinks()
             "ingredient"->forIngredient()
             "eats"-> forEats()
         }

         binding.button.setOnClickListener {

                val eatsName = binding.editTextTextPersonName2.text.toString()
                val eatsPrice= binding.editTextTextPersonName3.text.toString()
                val eatsDesc=   binding.editTextTextPersonName4.text.toString()
             if(eatsName.isEmpty() || eatsDesc.isEmpty() || eatsPrice.isEmpty()){
                 Toast.makeText(requireContext(),"All field must be full !", Toast.LENGTH_SHORT).show()
             }
             else{
                 product.pDesc = eatsDesc
                 product.pName = eatsName
                 product.pPrice = eatsPrice.toString().toLong()
                 viewModel.goToDataBase(product)
                 Toast.makeText(requireContext(),"Product Added Succesfully", Toast.LENGTH_SHORT).show()
             }

        }
        binding.button3.setOnClickListener {
            val drinksName = binding.editTextTextPersonName2.text.toString()
            val drinksPrice = binding.editTextTextPersonName3.text.toString()
            val drinksDesc =   binding.editTextTextPersonName4.text.toString()

                if(drinksName.isEmpty() || drinksDesc.isEmpty() || drinksPrice.isEmpty()){
                    Toast.makeText(requireContext(),"All field must be full !", Toast.LENGTH_SHORT).show()
                }else {
                    product.pName=drinksName
                    product.pDesc=drinksDesc
                    product.pPrice=drinksPrice.toString().toLong()
                    viewModel.addDrinksToDatabase(product)
                    Toast.makeText(requireContext(),"Product Added Succesfully", Toast.LENGTH_SHORT).show()
                }
        }
            // Adding Ingredients
            val ingredient= Ingredient()
         binding.button5.setOnClickListener {
            val ingredientName = binding.editTextTextPersonName2.text.toString()
            val ingredientPrice = binding.editTextTextPersonName3.text.toString()
            val ingredientDesc = binding.editTextTextPersonName4.text.toString()
            if(ingredientName.isEmpty() || ingredientDesc.isEmpty() || ingredientPrice.isEmpty()){
                Toast.makeText(requireContext(),"All field must be full !", Toast.LENGTH_SHORT).show()
            }
             else{
                ingredient.ingredientName = ingredientName
                ingredient.ingredientPrice = ingredientPrice.toString().toLong()
                ingredient.ingredientDesc  = ingredientDesc
                viewModel.addIngredientToDatabase(ingredient)
                Toast.makeText(requireContext(),"Product Added Succesfully", Toast.LENGTH_SHORT).show()
            }


        }
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
    }
    private fun forEats(){
        binding.button3.visibility= View.GONE
        binding.button5.visibility= View.GONE
    }
    private fun forDrinks(){

        binding.button5.visibility= View.GONE
        binding.button.visibility= View.GONE
    }
    private fun forIngredient(){
        binding.button.visibility= View.GONE
        binding.button3.visibility= View.GONE

    }
}