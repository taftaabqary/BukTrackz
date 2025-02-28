package com.unsoed.buktrackz.ui.detail

import android.icu.text.DecimalFormat
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.unsoed.buktrackz.R
import com.unsoed.buktrackz.core.domain.entity.Book
import com.unsoed.buktrackz.core.utils.DateConverter
import com.unsoed.buktrackz.databinding.FragmentDetailBookBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailBookFragment : Fragment() {

    private var _binding: FragmentDetailBookBinding? = null
    private val binding get() = _binding!!

    private val detailViewModel: DetailBookViewModel by viewModel()

    private var mainClick: Boolean = false
    private var firstTime: Boolean = true
    private var bookFav: Boolean = false
    private lateinit var bookData: Book

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBookBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fabState()
        setupAction()
        val idBook = DetailBookFragmentArgs.fromBundle(arguments as Bundle).idBook
        Log.d("DetailIdBook", idBook.toString())
        detailViewModel.getBookId(idBook)
        detailViewModel.book.observe(viewLifecycleOwner) { data ->
            Log.d("DetailIdBook", "Observe??")

            data?.let {
                val progressPercent = (data.currentPages.toDouble() / data.totalPages.toDouble()) * 100
                val convert = DecimalFormat("#.##").format(progressPercent)
                Log.d("DetailIdBook", progressPercent.toString())

                if(data.image.isNotEmpty()) {
                    binding.ivDetailCover.setImageURI(Uri.parse(data.image))
                } else {
                    binding.ivDetailCover.setImageResource(R.drawable.cover_book)
                }

                binding.tvDetailTitle.text = data.title
                binding.tvDetailAuthor.text = data.author
                binding.tvDetailGenre.text = data.genre
                binding.tvDetailProgress.text = "$convert%"
                binding.lpiDetailValue.progress = progressPercent.toInt()
                binding.tvDetailTotal.text = String.format("${data.totalPages}")
                binding.tvDetailCurrent.text = String.format("${data.currentPages}")
                binding.tvDetailRate.text = getStar(data.rate)
                binding.tvDetailRead.text = DateConverter.convertMillisToString(data.lastRead)
                binding.tvDetailNotes.text = getNote(data.note)
                bookFav = data.isFavorite
                fabFavoriteState()

                bookData = data
            }
        }
    }

    private fun fabFavoriteState() {
        if(bookFav) {
            if(!firstTime) {
                Toast.makeText(requireContext(), "Added to favorites", Toast.LENGTH_SHORT).show()
            }
            binding.fabFavorite.setImageDrawable(AppCompatResources.getDrawable(requireContext(), R.drawable.icon_fav_full))
        } else {
            if(!firstTime) {
                Toast.makeText(requireContext(), "Removed from favorites", Toast.LENGTH_SHORT).show()
            }
            binding.fabFavorite.setImageDrawable(AppCompatResources.getDrawable(requireContext(), R.drawable.icon_fav_out))
        }

    }

    private fun setupAction() {
        binding.fabMain.setOnClickListener {
            mainClick = !mainClick
            fabState()
        }

        binding.fabDelete.setOnClickListener {
            detailViewModel.deleteBook()
        }

        binding.fabFavorite.setOnClickListener {
            bookFav = !bookFav
            detailViewModel.updateFavorite(bookFav)
            firstTime = false
            fabFavoriteState()
        }

        binding.fabEdit.setOnClickListener {
            val navigation = DetailBookFragmentDirections.actionDetailBookFragmentToEditBookFragment(bookData)
            it.findNavController().navigate(navigation)
        }
    }

    private fun fabState() {
        if(mainClick) {
            binding.fabEdit.visibility = View.VISIBLE
            binding.fabFavorite.visibility = View.VISIBLE
            binding.fabDelete.visibility = View.VISIBLE
        } else {
            binding.fabEdit.visibility = View.GONE
            binding.fabFavorite.visibility = View.GONE
            binding.fabDelete.visibility = View.GONE
        }
    }

    private fun getNote(note: String?): String {
        var noteSummary = ""
        if(note != null) {
            val separate = note.split(",")
            val joinString = StringBuilder("")

            separate.forEach {
                joinString.append("$it\n")
            }
            noteSummary = joinString.toString()
        } else {
            noteSummary = "No additional note"
        }

        return noteSummary
    }

    private fun getStar(rate: Int): String {
        return when(rate) {
            1 -> "★"
            2 -> "★★"
            3 -> "★★★"
            4 -> "★★★★"
            5 -> "★★★★★"
            else -> "No rate"
        }
    }
}