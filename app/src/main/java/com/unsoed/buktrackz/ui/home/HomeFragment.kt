package com.unsoed.buktrackz.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.unsoed.buktrackz.adapter.BookAdapter
import com.unsoed.buktrackz.databinding.FragmentHomeBinding
import com.unsoed.buktrackz.helper.ViewModelFactory

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var bookAdapter: BookAdapter

    private val homeViewModel: HomeViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.e("Home Fragment", "Fragment dibuat kembali")
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bookAdapter = BookAdapter {}
        binding.rvReadingBook.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvReadingBook.adapter = bookAdapter
        binding.rvReadingBook.setHasFixedSize(true)
        binding.rvFavoriteBook.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvFavoriteBook.adapter = bookAdapter
        setupAllBook()
        setupFavoriteBook()
    }

    private fun setupFavoriteBook() {

    }

    private fun setupAllBook() {
        homeViewModel.getAllBook().observe(viewLifecycleOwner) { it ->
            it?.let { pagingData ->
                bookAdapter.submitData(lifecycle, pagingData)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}