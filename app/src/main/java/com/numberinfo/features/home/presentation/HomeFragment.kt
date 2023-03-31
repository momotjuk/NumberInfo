package com.numberinfo.features.home.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.numberinfo.R
import com.numberinfo.core.db.Numbers
import com.numberinfo.core.presentation.BaseFragment
import com.numberinfo.databinding.FragmentHomeBinding
import com.numberinfo.features.details.presentation.DetailsFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinComponent

class HomeFragment : BaseFragment(), KoinComponent {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: FactAdapter
    private val viewModel by viewModel<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun initUI() {
        initRecyclerView()

        binding.numberEditText.requestFocus()
        binding.getFactButton.setOnClickListener() {
            if (numberValidation()) {
                viewModel.getFact(binding.numberEditText.text.toString())
            }
        }

        binding.numberEditText.addTextChangedListener {
            numberValidation()
        }

        binding.getRandomFactButton.setOnClickListener() {
            viewModel.getRandomFact()
        }
    }

    private fun initRecyclerView() {
        adapter = FactAdapter()
        adapter.onItemClick = {
            onItemClicked(it)
        }
        adapter.data = viewModel.allFacts.value?.reversed() ?: listOf()

        val layoutManager = LinearLayoutManager(this.requireContext())
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter
    }

    private fun onItemClicked(numbers: Numbers) {
        findNavController().navigate(
            R.id.navigateToDetailsFragment,
            DetailsFragment.newBundle(numbers)
        )
    }

    private fun numberValidation(): Boolean {
        return if (binding.numberEditText.text.isNullOrEmpty()) {
            if (binding.numberEditText.hasFocus()) {
                binding.numberLayout.error = "Type number!"
                binding.numberLayout.isErrorEnabled = true
            }
            false
        } else {
            binding.numberLayout.isErrorEnabled = false
            true
        }
    }

    private fun updateRecyclerView(data: List<Numbers>) {
        adapter.data = data.reversed()
        adapter.notifyDataSetChanged()
    }

    override fun initObservers() {
        observeLiveData(viewModel.allFacts) {
            hideProgress()
            updateRecyclerView(it)
        }
    }
}