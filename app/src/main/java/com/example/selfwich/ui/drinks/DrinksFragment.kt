package com.example.selfwich.ui.drinks

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.selfwich.ProductAdapter
import com.example.selfwich.ProductClickListener
import com.example.selfwich.databinding.DrinksFragmentBinding
import com.example.selfwich.repository.DrinksRepository
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

        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        binding.drinksRc.adapter = ProductAdapter(ProductClickListener(
            { product -> viewModel.addLikePoint(product) },
            {product -> viewModel.addProducttoOrder(product)})

        )
    }

}