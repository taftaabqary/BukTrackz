package com.unsoed.buktrackz.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.unsoed.buktrackz.core.adapter.AllBookAdapter
import com.unsoed.buktrackz.favorite.databinding.FragmentFavoriteBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    private val favoriteViewModel: FavoriteViewModel by viewModel()
    private lateinit var allBookAdapter: AllBookAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        allBookAdapter = AllBookAdapter { idBook ->
            val navigation = FavoriteFragmentDirections.actionFavoriteFragmentToDetailBookFragment2()
            navigation.idBook = idBook
            this@FavoriteFragment.findNavController().navigate(navigation)
        }
        binding.rvFavorite.layoutManager = LinearLayoutManager(requireContext())
        binding.rvFavorite.adapter = allBookAdapter
        setupListFavorite()
    }

    private fun setupListFavorite() {
        favoriteViewModel.getBookFavorite().observe(viewLifecycleOwner) {
            it?.let {
                lifecycleScope.launch {
                    allBookAdapter.submitData(it)
                }
            }
        }
    }

}