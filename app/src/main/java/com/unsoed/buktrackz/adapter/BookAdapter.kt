package com.unsoed.buktrackz.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.unsoed.buktrackz.databinding.ItemBookCoverBinding
import com.unsoed.buktrackz.domain.entity.Book


class BookAdapter(private val onItemClick: (Book) -> Unit): PagingDataAdapter<Book, BookAdapter.BookViewHolder>(DIFF_CALLBACK) {

    inner class BookViewHolder(private val binding: ItemBookCoverBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Book) {
            binding.tvTitleBook.text = item.title
            binding.tvAuthorBook.text = item.author

            itemView.setOnClickListener {
                onItemClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val binding = ItemBookCoverBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val data = getItem(position)
        if(data != null) {
            holder.bind(data)
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