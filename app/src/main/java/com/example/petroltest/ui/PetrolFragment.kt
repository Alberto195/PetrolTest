package com.example.petroltest.ui

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.petroltest.NumberTextWatcher
import com.example.petroltest.databinding.PetrolFragmentBinding
import com.example.petroltest.viewmodels.PetrolViewModel

class PetrolFragment: Fragment() {

    private var _binding: PetrolFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel = PetrolViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PetrolFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val petrolInWatcher = NumberTextWatcher(
                binding.petrolIn,
                viewModel.locale,
                viewModel.numDecs)
        val petrolOutWatcher = NumberTextWatcher(
                binding.petrolOut,
                viewModel.locale,
                viewModel.numDecs)

        binding.petrolIn.addTextChangedListener(petrolInWatcher)
        binding.petrolOut.addTextChangedListener(petrolOutWatcher)

        viewModel.resultChanger.observe(viewLifecycleOwner, {
            val result = viewModel.calculateResult(
                binding.petrolIn.text,
                binding.petrolOut.text)
            showMistakes(viewModel.petrolInCorrect, binding.petrolIn)
            showMistakes(viewModel.petrolOutCorrect, binding.petrolOut)
            if(result != null) {
                binding.result.text = result.toString()
            } else {
                viewModel.setError("Неправильное значение")
            }
        })
        viewModel.errorCatcher.observe(viewLifecycleOwner, {
            binding.result.text = it
        })
    }

    private fun showMistakes(flag: Boolean, et: EditText) {
        if(flag) {
            et.setBackgroundColor(Color.WHITE)
        } else {
            et.setBackgroundColor(Color.RED)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}