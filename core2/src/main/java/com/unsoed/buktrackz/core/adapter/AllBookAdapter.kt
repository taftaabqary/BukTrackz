package com.unsoed.buktrackz.core.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.unsoed.buktrackz.core.databinding.ItemBookAllBinding
import com.unsoed.buktrackz.core.domain.entity.Book

class AllBookAdapter(private val onClick: (Int) -> Unit) :
    PagingDataAdapter<Book, AllBookAdapter.AllBookViewHolder>(DIFF_CALLBACK) {
    class AllBookViewHolder(private val binding: ItemBookAllBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Book) {
            binding.tvTitleFavorite.text = item.title
            binding.tvAuthorFavorite.text = item.author
            binding.tvGenreFavorite.text = item.genre
            binding.tvRateFavorite.text = getStar(item.rate)
            binding.tvProgressFavorite.text = getPercentProgress(item.totalPages, item.currentPages)
            binding.lpiFavorite.progress = ((item.currentPages/ item.totalPages).toDouble() * 100).toInt()
        }

        private fun getPercentProgress(totalPages: Int, currentPages: Int): String {
            val percentProgress = ((currentPages / totalPages).toDouble() * 100)
            return "$percentProgress%"
        }

        private fun getStar(rate: Int): String {
            return when(rate) {
                1 -> "★"
                2 -> "★★"
                3 -> "★★★"
                4 -> "★★★★"
                5 -> "★★★★★"
                else -> ""
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllBookViewHolder {
        val binding = ItemBookAllBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AllBookViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AllBookViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Book>() {
            override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
                return oldItem == newItem
            }
        }
    }
}