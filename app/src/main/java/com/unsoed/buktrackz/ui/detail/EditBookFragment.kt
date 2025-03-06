package com.unsoed.buktrackz.ui.detail

import android.icu.text.DecimalFormat
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.datepicker.MaterialDatePicker
import com.jakewharton.rxbinding2.widget.RxTextView
import com.unsoed.buktrackz.R
import com.unsoed.buktrackz.core.adapter.NoteAdapter
import com.unsoed.buktrackz.core.domain.entity.Book
import com.unsoed.buktrackz.core.domain.entity.Note
import com.unsoed.buktrackz.core.utils.DateConverter
import com.unsoed.buktrackz.databinding.FragmentEditBookBinding
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditBookFragment : Fragment() {

    private var _binding: FragmentEditBookBinding? = null
    private val binding get() = _binding!!

    private val detailViewModel: DetailBookViewModel by viewModel()
    private var currentImageUri: Uri? = null
    private val compositeDisposable = CompositeDisposable()

    private var dateTime: Long = 0
    private lateinit var detailBook: Book
    private lateinit var noteAdapter: NoteAdapter
    private val listNote: MutableList<Note> = mutableListOf()
    private var indexNote = 0

    private val launcher = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri: Uri? ->
        if(uri != null) {
            currentImageUri = uri
            showImage()
        } else {
            Toast.makeText(requireContext(), "No media selected", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showImage() {
        binding.ivDetailCover.setImageURI(currentImageUri)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditBookBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        detailBook = EditBookFragmentArgs.fromBundle(arguments as Bundle).editBook
        setupEditData()
        setupAction()
        setupProgressBar()
    }

    private fun setupAction() {
        binding.tiDateClick.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select the last date you read this book")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build()

            datePicker.show(parentFragmentManager, "DateBook")

            datePicker.addOnPositiveButtonClickListener {
                dateTime = datePicker.selection ?: 0
                val sdf = DateConverter.convertMillisToString(dateTime)
                binding.tiEditDate.editText?.setText(sdf)
            }
        }

        binding.btnEdit.setOnClickListener {
            if(checkValue()) {
                val bookData = Book(
                    id = detailBook.id,
                    title = binding.tiEditTitle.editText?.text.toString().trim(),
                    author = binding.tiEditAuthor.editText?.text.toString().trim(),
                    genre = binding.acEditGenre.text.toString().trim(),
                    totalPages = binding.tiEditTotalPages.editText?.text.toString().toInt(),
                    currentPages = binding.tiEditCurrentPage.editText?.text.toString().toInt(),
                    rate = getStarValue(),
                    lastRead = dateTime,
                    note = getAdditionalNote(),
                    isFavorite = detailBook.isFavorite,
                    image = if(currentImageUri != null) currentImageUri.toString() else ""
                )

                if(bookData != detailBook) detailViewModel.updateBook(bookData) else this@EditBookFragment.findNavController().navigateUp()

                detailViewModel.result.observe(viewLifecycleOwner) {
                    it?.let {
                        if(it > 0){
                            Toast.makeText(requireContext(), "Update operation succeed", Toast.LENGTH_SHORT).show()
                            this@EditBookFragment.findNavController().navigateUp()
                        } else {
                            Toast.makeText(requireContext(), "Update operation failed", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } else {
                Toast.makeText(requireContext(), "Please fill the empty fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
        }

        binding.btnEditAddNote.setOnClickListener {
            indexNote++
            binding.rvEditNote.visibility = View.VISIBLE
            listNote.add(
                Note(
                    id = indexNote,
                    note = ""
                )
            )
            noteAdapter = NoteAdapter { id, note ->
                listNote.forEach { item ->
                    if (item.id == id) {
                        listNote[id - 1] = Note(
                            id = id,
                            note = note
                        )
                    }
                }
            }
            binding.rvEditNote.adapter = noteAdapter
            noteAdapter.addNote(listNote)
        }

        binding.btnEditImage.setOnClickListener {
            launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
    }

    private fun getAdditionalNote(): String {
        val noteBuilder = StringBuilder()
        listNote.forEach {
            noteBuilder.append("${it.note},")
        }

        return noteBuilder.toString()
    }

    private fun checkValue(): Boolean {
        var isValid = false
        val titleValue = binding.tiEditTitle.editText?.text.toString().trim()
        val authorValue = binding.tiEditAuthor.editText?.text.toString().trim()
        val genreValue = binding.acEditGenre.text.toString().trim()
        val totalValue = binding.tiEditTotalPages.editText?.text.toString().trim()
        val currentValue = binding.tiEditCurrentPage.editText?.text.toString().trim()
        val rateValue = getStarValue()
        val lastReadValue = binding.tiEditRate.editText?.text.toString().trim()

        when {
            titleValue.isEmpty() -> binding.tiEditTitle.error = "Insert value"
            authorValue.isEmpty() -> binding.tiEditAuthor.error = "Insert value"
            genreValue.isEmpty() -> binding.tiEditGenre.error = "Insert value"
            totalValue.isEmpty() && totalValue.toInt() > 0 -> binding.tiEditTotalPages.error = "Insert value"
            currentValue.isEmpty() -> binding.tiEditCurrentPage.error = "Insert value"
            lastReadValue.isEmpty() -> binding.tiEditDate.error = "Insert value"
            rateValue < 0 -> binding.tiEditRate.error = "Insert value"
            genreValue.isEmpty() -> binding.tiEditGenre.error = "Insert value"
            else -> isValid = true
        }

        return isValid
    }

    private fun setupProgressBar() {
        val totalPagesStream = RxTextView.textChanges(binding.edEditTotalPage)
            .skipInitialValue()
            .map { total ->
                total.isNotEmpty()
            }

        val currentPagesStream = RxTextView.textChanges(binding.edEditCurrentPage)
            .skipInitialValue()
            .map { current ->
                current.isNotEmpty()
            }

        val totalPageSubscribe = totalPagesStream.subscribe { valid ->
            binding.tiEditTotalPages.helperText = if(!valid) "Insert total pages" else null
        }

        val currentPageSubscribe = currentPagesStream.subscribe { valid ->
            binding.tiEditCurrentPage.helperText = if(!valid) "Insert current pages" else null
        }

        val validProgressBar = Observable.combineLatest(
            totalPagesStream,
            currentPagesStream,
        ) { totalPageValid: Boolean, currentPage: Boolean ->
            totalPageValid && currentPage
        }

        val validProgressSubscribe = validProgressBar.subscribe { isValid ->
            if(isValid) {
                val totalPagesValue = binding.edEditTotalPage.text!!.toString().toDouble()
                val currentPagesValue = binding.edEditCurrentPage.text!!.toString().toDouble()
                if(currentPagesValue > totalPagesValue) {
                    binding.tvEditProgressBar.text = getString(R.string.progress_bar_numeric, 100)
                    binding.lpiEditPage.progress = 100
                } else {
                    val progressPercent = (currentPagesValue / totalPagesValue) * 100
                    val convert = DecimalFormat("#.##").format(progressPercent)
                    binding.lpiEditPage.progress = progressPercent.toInt()
                    binding.tvEditProgressBar.text = getString(R.string.progress_bar_text, convert)
                }
            } else {
                binding.tvEditProgressBar.text = getString(R.string.progress_text)
                binding.lpiEditPage.progress = 0
            }
        }

        compositeDisposable.addAll(totalPageSubscribe, currentPageSubscribe, validProgressSubscribe)
    }


    private fun setupEditData() {
        val progressPercent = (detailBook.currentPages.toDouble() / detailBook.totalPages.toDouble()) * 100
        val convert = DecimalFormat("#.##").format(progressPercent)
        dateTime = detailBook.lastRead

        binding.apply {
            tiEditTitle.editText?.setText(detailBook.title)
            tiEditAuthor.editText?.setText(detailBook.author)
            acEditGenre.setText(detailBook.genre, false)
            tiEditDate.editText?.setText(DateConverter.convertMillisToString(detailBook.lastRead))
            tiEditTotalPages.editText?.setText(String.format("${detailBook.totalPages}"))
            tiEditCurrentPage.editText?.setText(String.format("${detailBook.currentPages}"))
            acEditRate.setText(getStar(detailBook.rate), false)
            tvEditProgressBar.text = getString(R.string.progress_bar_text, convert)
            lpiEditPage.progress = progressPercent.toInt()
            setupNote()
        }
    }

    private fun setupNote() {
        binding.rvEditNote.visibility = View.VISIBLE
        val noteData = detailBook.note
        noteData.split(",").map { item ->
            if(item.isNotEmpty()) {
                indexNote++
                listNote.add(Note(
                    id = indexNote,
                    note = item
                ))
            }
        }
        binding.rvEditNote.layoutManager = LinearLayoutManager(requireContext())
        noteAdapter = NoteAdapter { id, note ->
            listNote.forEach { item ->
                if (item.id == id) {
                    listNote[id - 1] = Note(
                        id = id,
                        note = note
                    )
                }
            }
        }
        noteAdapter.addNote(listNote)
        binding.rvEditNote.adapter = noteAdapter
    }

    private fun getStarValue(): Int {
        val star = binding.acEditRate.text.toString()
        val starValue: Int = when(star) {
            "★" -> 1
            "★★" -> 2
            "★★★" -> 3
            "★★★★" -> 4
            "★★★★★" -> 5
            else -> -1
        }

        return starValue
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

    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposable.clear()
        _binding = null
    }
}