package com.example.selfwich.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.selfwich.ProductAdapter
import com.example.selfwich.R
import com.example.selfwich.databinding.EatsFragmentBinding
import com.example.selfwich.repository.EatsRepository
import com.example.selfwich.viewModel.EatsViewModel

class EatsFragment : Fragment() {

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
      //  binding.eatsRc.adapter=ProductAdapter()


    }

}