package com.unsoed.buktrackz.ui.discover

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.unsoed.buktrackz.R
import com.unsoed.buktrackz.core.adapter.BestSellerAdapter
import com.unsoed.buktrackz.core.utils.ListBook
import com.unsoed.buktrackz.databinding.FragmentDiscoverBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class DiscoverFragment : Fragment() {

    private var _binding: FragmentDiscoverBinding? = null
    private val binding get() = _binding!!

    private val discoverViewModel: DiscoverViewModel by viewModel()
    private lateinit var bestSellerAdapter: BestSellerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDiscoverBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.acType.isSaveEnabled = false
        binding.icDiscover.tvEmpty.text = getString(R.string.empty_discover)
        binding.icDiscover.emptyStateLayout.visibility = View.VISIBLE
        setupListType()
        setupBestSellerBook()
        observeArrayList()

        bestSellerAdapter = BestSellerAdapter { link ->
            val uri = Uri.parse(link)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
        binding.rvResultBestSeller.layoutManager = LinearLayoutManager(requireContext())
        binding.rvResultBestSeller.adapter = bestSellerAdapter

        bestSellerAdapter.addLoadStateListener { loadState ->
                if (loadState.source.refresh is LoadState.Loading) {
                    binding.lottieLoading.visibility = View.VISIBLE
                    binding.rvResultBestSeller.visibility = View.GONE
                } else {
                    binding.lottieLoading.visibility = View.GONE
                    binding.rvResultBestSeller.visibility = View.VISIBLE
                }
            }
    }

    private fun observeArrayList() {
        viewLifecycleOwner.lifecycleScope.launch {
            discoverViewModel.arrayList.collect { data ->
                (binding.acType as MaterialAutoCompleteTextView).setSimpleItems(data)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        discoverViewModel.loadArrayListBook()
    }

    private fun setupListType() {
        binding.acType.threshold = 1

        binding.acType.setOnItemClickListener  { parent, _, position, _ ->
            binding.icDiscover.emptyStateLayout.visibility = View.GONE
            val selectedText = parent.getItemAtPosition(position) as String
            val valueBook = ListBook.entries.find { it.display == selectedText }
            discoverViewModel.changeTypeBook(valueBook!!)
        }
    }


    private fun setupBestSellerBook() {
        discoverViewModel.typeBook
            .observe(viewLifecycleOwner) {
                it?.let { pagingData ->
                    lifecycleScope.launch {
                        bestSellerAdapter.submitData(pagingData)
                    }
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.rvResultBestSeller.adapter = null
        _binding = null
    }
}