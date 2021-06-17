package com.example.selfwich.ui.currentOrder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.selfwich.OrderDetailsProductAdapter
import com.example.selfwich.OrderDetailsProductClickListener
import com.example.selfwich.OrderDetailsSelfwichAdapter
import com.example.selfwich.OrderDetailsSelfwichClickListener
import com.example.selfwich.databinding.CurrentOrderFragmentBinding
import com.example.selfwich.model.Singleton
import com.example.selfwich.repository.CurrentOrderRepository
import com.example.selfwich.repository.OrderDetailsRepository
import com.example.selfwich.viewModel.CurrentOrderViewModel

class CurrentOrderFragment : Fragment() {

    private lateinit var binding: CurrentOrderFragmentBinding
    private lateinit var viewModel: CurrentOrderViewModel
    private lateinit var currentOrderRepository: CurrentOrderRepository


    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CurrentOrderFragmentBinding.inflate(inflater)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?){
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {}

        currentOrderRepository=CurrentOrderRepository()
        viewModel = ViewModelProvider(
            this,
            CurrentOrderViewModel.Factory(activity.application, currentOrderRepository)
        ).get(CurrentOrderViewModel::class.java)

        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        Singleton.globalOrderLive.observe(viewLifecycleOwner, {
            viewModel.setCurrentOrderr(it)
        })
        binding.productRcy22.adapter =  OrderDetailsProductAdapter(OrderDetailsProductClickListener())
        binding.selfwichRcy22.adapter = OrderDetailsSelfwichAdapter(OrderDetailsSelfwichClickListener())

    }
}