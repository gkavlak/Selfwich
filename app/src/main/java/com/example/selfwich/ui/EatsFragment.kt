package com.example.selfwich.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.selfwich.R
import com.example.selfwich.viewModel.EatsViewModel

class EatsFragment : Fragment() {

    companion object {
        fun newInstance() = EatsFragment()
    }

    private lateinit var viewModel: EatsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.eats_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(EatsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}