package com.unsoed.buktrackz.core.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.unsoed.buktrackz.core.R
import com.unsoed.buktrackz.core.databinding.ItemBookBestSellerBinding
import com.unsoed.buktrackz.core.domain.entity.BookBestSeller

class BestSellerAdapter(private val onItemClick: (String) -> Unit): PagingDataAdapter<BookBestSeller, BestSellerAdapter.ViewHolder>(DIFF_CALLBACK) {
    inner class ViewHolder(private val binding: ItemBookBestSellerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: BookBestSeller) {
            Glide.with(itemView.context)
                .load(item.bookImage)
                .into(binding.ivBookBestSellet)

            binding.tvTitleBestSeller.text = item.title
            binding.tvAuthorFavorite.text = item.author
            binding.tvBestSellerDate.text = item.bestSellerDate
            binding.tvRank.text = itemView.context.getString(R.string.best_seller_text, item.rank)
            binding.tvContributorBestSeller.text = item.contributor
            binding.tvPublisherBestSeller.text = item.publisher
            binding.tvTypeBestSeller.text = item.displayName
            binding.tvTitleBestSeller.text = item.title
            binding.tvDescriptionBestSeller.text = item.description

            itemView.setOnClickListener {
                onItemClick(item.amazonProductUrl)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemBookBestSellerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        if(item != null) {
            holder.bind(item)
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<BookBestSeller>() {
            override fun areItemsTheSame(
                oldItem: BookBestSeller,
                newItem: BookBestSeller
            ): Boolean {
                return oldItem.primaryIsbn13 == newItem.primaryIsbn13
            }

            override fun areContentsTheSame(
                oldItem: BookBestSeller,
                newItem: BookBestSeller
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}