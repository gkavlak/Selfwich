package com.example.selfwich.ui.order

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.selfwich.OrderAdapter
import com.example.selfwich.OrderClickListener
import com.example.selfwich.R
import com.example.selfwich.databinding.OrderFragmentBinding
import com.example.selfwich.model.Order
import com.example.selfwich.repository.EatsRepository
import com.example.selfwich.repository.OrderRepository
import com.example.selfwich.viewModel.OrderViewModel

class OrderFragment : Fragment(){

    private lateinit var binding: OrderFragmentBinding
    private lateinit var viewModel: OrderViewModel
    private lateinit var orderRepository: OrderRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = OrderFragmentBinding.inflate(inflater)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {}
        orderRepository= OrderRepository()

        viewModel=ViewModelProvider(this,OrderViewModel.Factory(activity.application,orderRepository))
            .get(OrderViewModel::class.java)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        orderRepository= OrderRepository()

        viewModel.orderlist.observe(viewLifecycleOwner, Observer {


        })

        binding.orderRc.adapter=OrderAdapter(
            OrderClickListener (
                { order -> viewModel.orderIsReady(order) },
                { order -> viewModel.orderISCanceled(order)},
                { order -> findNavController().navigate(OrderFragmentDirections.actionOrderFragmentToOrderDetailsFragment(order.orderId))})
        )
    }
}


