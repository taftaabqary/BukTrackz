package com.unsoed.buktrackz.ui.detail

import android.icu.text.DecimalFormat
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import com.unsoed.buktrackz.R
import com.unsoed.buktrackz.databinding.FragmentDetailBookBinding
import com.unsoed.core.utils.DateConverter
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailBookFragment : Fragment() {

    private var _binding: FragmentDetailBookBinding? = null
    private val binding get() = _binding!!

    private val detailViewModel: DetailBookViewModel by viewModel()

    private var mainClick: Boolean = false
    private var bookFav: Boolean = false

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
        detailViewModel.getBookId(idBook)
        detailViewModel.book.observe(viewLifecycleOwner) { data ->
            data?.let {
                val progressPercent = (data.currentPages.toDouble() / data.totalPages.toDouble()) * 100
                val convert = DecimalFormat("#.##").format(progressPercent)

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
            }
        }
    }

    private fun fabFavoriteState() {
        if(bookFav) {
            binding.fabFavorite.setImageDrawable(AppCompatResources.getDrawable(requireContext(), R.drawable.icon_fav_full))
        } else {
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
            fabFavoriteState()
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