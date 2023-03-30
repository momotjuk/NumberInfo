package com.numberinfo.features.details.presentation

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.numberinfo.core.db.Numbers
import com.numberinfo.core.presentation.BaseFragment
import com.numberinfo.databinding.FragmentDetailsBinding

class DetailsFragment : BaseFragment() {
    companion object {
        private const val NUMBERS_KEY = "numbers_key"

        fun newBundle(numbers: Numbers): Bundle {
            val bundle = Bundle()
            bundle.putSerializable(NUMBERS_KEY, numbers)
            return bundle
        }
    }

    private lateinit var binding: FragmentDetailsBinding
    private var numbers: Numbers? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    @Suppress("DEPRECATION")
    override fun initUI() {
        numbers = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getSerializable(NUMBERS_KEY, Numbers::class.java)
        } else {
            arguments?.getSerializable(NUMBERS_KEY) as Numbers
        }

        binding.numberTextView.text = numbers?.number
        binding.detailsTextView.text = numbers?.fact

        binding.backButton.setOnClickListener() {
            findNavController().popBackStack()
        }
    }

    override fun initObservers() {
        // Not yet implemented
    }
}