package com.example.selfwich.ui.updateDialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.selfwich.databinding.UpdateDialogFragmentBinding
import com.example.selfwich.model.Product
import com.example.selfwich.repository.UpdateDialogRepository
import com.example.selfwich.viewModel.UpdateDialogViewModel

class UpdateDialogFragment(val product: Product) :DialogFragment() {
    private lateinit var binding: UpdateDialogFragmentBinding
    private lateinit var updateDialogRepository: UpdateDialogRepository
    private lateinit var viewModel: UpdateDialogViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = UpdateDialogFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {}
        updateDialogRepository= UpdateDialogRepository()
        viewModel = ViewModelProvider(
            this,
            UpdateDialogViewModel.Factory(activity.application, updateDialogRepository)
        ).get(UpdateDialogViewModel::class.java)

        binding.button4.setOnClickListener {
            val drinksName= binding.editTextTextPersonName5.text.toString()
            product.pName= drinksName

            viewModel.updateDrinks(product)
        }
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

    }
}