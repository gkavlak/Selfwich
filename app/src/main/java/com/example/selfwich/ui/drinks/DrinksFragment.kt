package com.example.selfwich.ui.drinks

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.example.selfwich.ProductAdapter
import com.example.selfwich.ProductClickListener
import com.example.selfwich.databinding.DrinksFragmentBinding
import com.example.selfwich.model.Product
import com.example.selfwich.model.Singleton
import com.example.selfwich.repository.DrinksRepository
import com.example.selfwich.ui.customDialogFragment.CustomDialogFragment
import com.example.selfwich.ui.updateDialog.UpdateDialogFragment
import com.example.selfwich.viewModel.DrinksViewModel

class DrinksFragment : Fragment() {
    private lateinit var binding: DrinksFragmentBinding
    private lateinit var viewModel: DrinksViewModel
    private lateinit var drinksRepository: DrinksRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DrinksFragmentBinding.inflate(inflater)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        drinksRepository = DrinksRepository()
        val activity = requireNotNull(this.activity) {}

        viewModel = ViewModelProvider(
            this,
            DrinksViewModel.Factory(activity.application, drinksRepository)
            //  çift ünleme tekrar bak
        ).get(DrinksViewModel::class.java)

            viewModel.isLikeAdded.observe(viewLifecycleOwner, Observer {
            })
        fun showDialog(product: Product){

            UpdateDialogFragment(product).show(childFragmentManager,"")
        }

        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        binding.drinksRc.adapter = ProductAdapter(ProductClickListener(
            { product -> viewModel.addLikePoint(product) },

            {product -> viewModel.addProducttoOrder(product)},
            {product ->viewModel.deleteProduct(product) },
            {product -> showDialog(product) }
            )

        )

        binding.imageButton3.setOnClickListener {
            val type = "drinks"
            CustomDialogFragment(type)
                .show(childFragmentManager,"")
        }
        Singleton.globalUser.observe(viewLifecycleOwner, {
            when(it.userType){
                "customer"->forCostumers()
            }
        })
    }

    private fun forCostumers() {
        binding.imageButton3.visibility = View.GONE
    }


}