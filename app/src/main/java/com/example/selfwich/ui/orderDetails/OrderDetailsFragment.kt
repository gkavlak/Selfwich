package com.example.selfwich.ui.orderDetails

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
import com.example.selfwich.databinding.OrderDetailsFragmentBinding
import com.example.selfwich.databinding.OrderFragmentBinding
import com.example.selfwich.model.Singleton
import com.example.selfwich.repository.OrderDetailsRepository
import com.example.selfwich.viewModel.OrderDetailsViewModel
import com.example.selfwich.viewModel.OrderViewModel

class OrderDetailsFragment : Fragment(){

    private lateinit var binding: OrderDetailsFragmentBinding
    private lateinit var orderDetailsRepository: OrderDetailsRepository
    private lateinit var viewModel: OrderDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = OrderDetailsFragmentBinding.inflate(inflater)
        return binding.root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val arguments = OrderDetailsFragmentArgs.fromBundle(requireArguments())

        val activity = requireNotNull(this.activity) {}

        orderDetailsRepository= OrderDetailsRepository()

        viewModel = ViewModelProvider(this, OrderDetailsViewModel.Factory(arguments.orderId,activity.application,orderDetailsRepository))
            .get(OrderDetailsViewModel::class.java)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        binding.productRcy.adapter =  OrderDetailsProductAdapter(OrderDetailsProductClickListener
        { product -> viewModel.deleteProductInOrder(product) })
        binding.selfwichRcy.adapter = OrderDetailsSelfwichAdapter(OrderDetailsSelfwichClickListener { selfwich ->
            viewModel.deleteSelfwichInOrder(selfwich)
        })
        Singleton.globalUser.observe(viewLifecycleOwner, {
            when(it.userType){
                "customer"-> forCustomer()
                "admin" ->forAdmin()

            }
        })
        binding.imageView6.setOnClickListener {
            viewModel.readyforOder()
        }
        binding.imageView7.setOnClickListener {
            viewModel.cancelForOrder()
        }






    }

    private fun forCustomer() {
        binding.imageView6.visibility= View.GONE
        binding.imageView7.visibility=View.GONE
    }

    private fun forAdmin() {
        binding.imageView6.visibility= View.VISIBLE
        binding.imageView7.visibility=View.VISIBLE

    }

}