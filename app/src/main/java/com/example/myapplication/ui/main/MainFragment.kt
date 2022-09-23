package com.example.myapplication.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val wordDao = (activity?.application as App).db.wordDao()
                return MainViewModel(wordDao, requireContext()) as T
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickListeners()
        lauchCoroutines()
    }

    private fun lauchCoroutines() {
        lifecycleScope.launchWhenCreated {
            viewModel.allWords
                .collect {
                }
        }

        lifecycleScope.launchWhenCreated {
            viewModel.sortedWords
                .collect { words ->
                    var str = ""
                    var count = ""
                    words.forEach {
                        str += "${it.text}\n"
                        count += "${it.count}\n"
                    }
                    binding.words.text = str
                    binding.count.text = count
                }
        }
    }

    private fun setOnClickListeners() {
        binding.addButton.setOnClickListener {
            viewModel.onAddButton(binding.textInputEditText.text.toString())
        }

        binding.clearButton.setOnClickListener {
            viewModel.onDeleteBtn()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}