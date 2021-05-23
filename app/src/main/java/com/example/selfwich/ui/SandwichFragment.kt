package com.example.selfwich.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.selfwich.IngredientsAdapter
import com.example.selfwich.ProductAdapter
import com.example.selfwich.databinding.SandwichFragmentBinding
import com.example.selfwich.model.Product
import com.example.selfwich.repository.SandwichRepository
import com.example.selfwich.viewModel.SandwichViewModel

class SandwichFragment : Fragment() {

    private lateinit var binding: SandwichFragmentBinding
    private lateinit var sandwichRepository: SandwichRepository
    private lateinit var viewModel: SandwichViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding=SandwichFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        sandwichRepository= SandwichRepository()
        val activity = requireNotNull(this.activity) {}

        viewModel = ViewModelProvider(this,SandwichViewModel.Factory(activity.application,sandwichRepository)).get(SandwichViewModel::class.java)

        binding.viewmodel=viewModel
        binding.lifecycleOwner=this
       // binding.saveButton.setOnClickListener {
            val product=Product()
         //   val name= binding.editText.text.toString()
            //if (name.isEmpty()){

              //  Toast.makeText(context, "hello please add your sandwich name",Toast.LENGTH_SHORT)
            //}
             //  product.pName=name
          //viewModel.addSelfSandwich(product)

          //  val desc=binding.editText1.text.toString()
           // if(desc.isEmpty()){
             //   Toast.makeText(context, "hello please add your sandwich name",Toast.LENGTH_SHORT)
            //}
            //product.pNone=desc
        binding.sandwichlistrc.adapter=IngredientsAdapter()


        }
    }

