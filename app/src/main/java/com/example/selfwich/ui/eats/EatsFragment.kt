package com.example.selfwich.ui.eats

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.selfwich.ProductAdapter
import com.example.selfwich.ProductClickListener
import com.example.selfwich.databinding.EatsFragmentBinding
import com.example.selfwich.model.Product
import com.example.selfwich.model.Singleton
import com.example.selfwich.repository.EatsRepository
import com.example.selfwich.ui.customDialogFragment.CustomDialogFragment
import com.example.selfwich.ui.updateDialog.UpdateDialogFragment
import com.example.selfwich.viewModel.EatsViewModel

class   EatsFragment : Fragment() {

    private lateinit var binding:EatsFragmentBinding
    private lateinit var eatsRepository: EatsRepository
    private lateinit var viewModel: EatsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding=EatsFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {}
        eatsRepository= EatsRepository()
        viewModel = ViewModelProvider(this,EatsViewModel.Factory(activity.application,eatsRepository)).get(EatsViewModel::class.java)


        binding.viewmodel=viewModel
        binding.lifecycleOwner=this

        fun showDialog(product: Product){
            UpdateDialogFragment(product).show(childFragmentManager,"")
        }
        binding.eatsRc.adapter = ProductAdapter(
            ProductClickListener (
                { product -> viewModel.addLikePoint(product) },
                {product -> viewModel.addProducttoOrder(product)},
                {product ->viewModel.deleteProduct(product)},
                {product -> showDialog(product) }
                )
        )
        binding.imageButton2.setOnClickListener {
            val type="eats"
            CustomDialogFragment(tpye = type)
                .show(childFragmentManager,"")
        }
        Singleton.globalUser.observe(viewLifecycleOwner, {
            when(it.userType){
                "customer"->forCostumers()
            }
        })

    }

    private fun forCostumers() {
        binding.imageButton2.visibility = View.GONE
    }

}