package com.example.selfwich.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.selfwich.SelfWichAdapter
import com.example.selfwich.SelfwichClickListener
import com.example.selfwich.databinding.SelfwichFragmentBinding
import com.example.selfwich.model.Singleton
import com.example.selfwich.repository.SelfWichRepository
import com.example.selfwich.viewModel.SelfWichViewModel

class SelfWichFragment : Fragment() {

    private lateinit var binding: SelfwichFragmentBinding
    private lateinit var selfWichRepository: SelfWichRepository
    private lateinit var viewModel: SelfWichViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding=SelfwichFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        selfWichRepository= SelfWichRepository()
        val activity = requireNotNull(this.activity) {}

        viewModel = ViewModelProvider(this,SelfWichViewModel.Factory(activity.application,selfWichRepository)).get(SelfWichViewModel::class.java)

        binding.viewmodel=viewModel
        binding.lifecycleOwner=this
        viewModel.isSelfWichLikeAdded.observe(viewLifecycleOwner,{

        })
        binding.sandwichlistrc.adapter=SelfWichAdapter(SelfwichClickListener(
            { selfwich-> viewModel.addLikeSelfwichPoint(selfwich)},
            { selfwich-> viewModel.addSelfwichtoOrder(selfwich)}
        )
        )
        }

}




