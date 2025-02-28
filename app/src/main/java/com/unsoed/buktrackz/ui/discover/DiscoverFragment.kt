package com.unsoed.buktrackz.ui.discover

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.textfield.MaterialAutoCompleteTextView
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
    private lateinit var listBookNames: Array<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDiscoverBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("Discover Fragment", "OnViewCreated Discover dibuat")
        bestSellerAdapter = BestSellerAdapter { link ->
            val uri = Uri.parse(link)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
        binding.rvResultBestSeller.layoutManager = LinearLayoutManager(requireContext())
        binding.rvResultBestSeller.adapter = bestSellerAdapter
        bestSellerAdapter.addLoadStateListener { loadState ->
            if (loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached && bestSellerAdapter.itemCount < 1) {

            } else {

            }
        }
        setupListType()
        setupBestSellerBook()
    }

    private fun setupListType() {
        val listBook = ListBook.entries.map {
            it.display
        }.toTypedArray()
        listBookNames = listBook

        (binding.acType as MaterialAutoCompleteTextView).setSimpleItems(listBook)

        binding.acType.setOnItemClickListener  { parent, _, position, _ ->
            Log.d("Discover Fragment", "setOnItemClickListener Discover dibuat")
            val selectedText = parent.getItemAtPosition(position) as String
            val valueBook = ListBook.entries.find { it.display == selectedText }
            discoverViewModel.changeTypeBook(valueBook!!)
        }
    }


    private fun setupBestSellerBook() {
        Log.d("Discover Fragment", "setupBestSellerBook Discover dibuat")
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
        _binding = null
    }


}