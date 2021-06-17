package com.example.selfwich.ui.currentOrder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.selfwich.databinding.CurrentOrderFragmentBinding
import com.example.selfwich.model.Singleton
import com.example.selfwich.repository.CurrentOrderRepository
import com.example.selfwich.viewModel.CurrentOrderViewModel

class CurrentOrderFragment : Fragment() {

    private lateinit var binding: CurrentOrderFragmentBinding
    private lateinit var viewModel: CurrentOrderViewModel
    private lateinit var currentOrderRepository: CurrentOrderRepository


    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CurrentOrderFragmentBinding.inflate(inflater)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?){
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {}

        viewModel = ViewModelProvider(
            this,
            CurrentOrderViewModel.Factory(activity.application, currentOrderRepository)
            //  çift ünleme tekrar bak
        ).get(CurrentOrderViewModel::class.java)

        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        Singleton.globalOrderLive.observe(viewLifecycleOwner, {
            viewModel.setCurrentOrderr(it)
        })

    }
}