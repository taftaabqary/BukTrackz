package com.unsoed.buktrackz.ui.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.unsoed.buktrackz.R
import com.unsoed.buktrackz.core.adapter.BookAdapter
import com.unsoed.buktrackz.core.adapter.LoadingStateAdapter
import com.unsoed.buktrackz.core.utils.Filter
import com.unsoed.buktrackz.databinding.FragmentHomeBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var bookAdapter: BookAdapter
    private lateinit var favoriteAdapter: BookAdapter

    private val homeViewModel: HomeViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAllBook()
        setupFavoriteBook()
        setupAction()
    }

    private fun setupAction() {
        binding.btnViewAllFav.setOnClickListener {
            val uri = Uri.parse("buktrackz://favorite")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }

        binding.chipFilterGroup.isSingleSelection = true
        binding.chipFilterGroup.setOnCheckedStateChangeListener  { group, _ ->
            val checkId = group.checkedChipId
            when(checkId) {
                R.id.chip_all -> homeViewModel.changeFilter(Filter.ALL)
                R.id.chip_self -> homeViewModel.changeFilter(Filter.SELF_GROWTH)
                R.id.chip_novel -> homeViewModel.changeFilter(Filter.NOVEL)
                R.id.chip_fantasy -> homeViewModel.changeFilter(Filter.FANTASY)
                R.id.chip_anthropology -> homeViewModel.changeFilter(Filter.ANTHROPOLOGY)
                R.id.chip_dystopic -> homeViewModel.changeFilter(Filter.DYSTOPIC)
                R.id.chip_fiction -> homeViewModel.changeFilter(Filter.FICTION)
                R.id.chip_historic -> homeViewModel.changeFilter(Filter.HISTORIC)
                R.id.chip_finance -> homeViewModel.changeFilter(Filter.FINANCE)
            }
        }

    }

    private fun setupFavoriteBook() {
        favoriteAdapter = BookAdapter { id ->
            val navigation = HomeFragmentDirections.actionNavigationHomeToDetailBookFragment()
            navigation.idBook = id
            this@HomeFragment.findNavController().navigate(navigation)
        }

        binding.icFav.tvEmpty.text = getString(R.string.empty_fav)

        favoriteAdapter.addLoadStateListener { loadState ->
            if (loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached && favoriteAdapter.itemCount < 1) {
                binding.rvFavoriteBook.visibility = View.GONE
                binding.icFav.emptyStateLayout.visibility = View.VISIBLE
            } else {
                binding.rvFavoriteBook.visibility = View.VISIBLE
                binding.icFav.emptyStateLayout.visibility = View.GONE
            }
        }

        binding.rvFavoriteBook.adapter = favoriteAdapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                favoriteAdapter.retry()
            }
        )

        binding.rvFavoriteBook.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        homeViewModel.getFavoriteBook().observe(viewLifecycleOwner) {
            it?.let { pagingData ->
                lifecycleScope.launch {
                    favoriteAdapter.submitData(pagingData)
                }
            }
        }
    }

    private fun setupAllBook() {
        bookAdapter = BookAdapter { id ->
            val navigation = HomeFragmentDirections.actionNavigationHomeToDetailBookFragment()
            navigation.idBook = id
            this@HomeFragment.findNavController().navigate(navigation)
        }

        binding.icAll.tvEmpty.text = getString(R.string.empty_all)

        bookAdapter.addLoadStateListener { loadState ->
            if (loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached && bookAdapter.itemCount < 1) {
                binding.rvReadingBook.visibility = View.GONE
                binding.icAll.emptyStateLayout.visibility = View.VISIBLE
            } else {
                binding.rvReadingBook.visibility = View.VISIBLE
                binding.icAll.emptyStateLayout.visibility = View.GONE
            }
        }

        binding.rvReadingBook.adapter = bookAdapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                bookAdapter.retry()
            }
        )

        binding.rvReadingBook.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeViewModel.books.collectLatest { pagingData ->
                    bookAdapter.submitData(lifecycle, pagingData)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.rvReadingBook.adapter = null
        binding.rvFavoriteBook.adapter = null
        _binding = null
    }
}