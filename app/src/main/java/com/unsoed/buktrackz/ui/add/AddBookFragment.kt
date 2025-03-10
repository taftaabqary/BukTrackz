package com.unsoed.buktrackz.ui.add

import android.icu.text.DecimalFormat
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.datepicker.MaterialDatePicker
import com.jakewharton.rxbinding2.widget.RxTextView
import com.unsoed.buktrackz.R
import com.unsoed.buktrackz.core.adapter.NoteAdapter
import com.unsoed.buktrackz.core.domain.entity.Book
import com.unsoed.buktrackz.core.domain.entity.Note
import com.unsoed.buktrackz.core.utils.DateConverter
import com.unsoed.buktrackz.core.utils.Event
import com.unsoed.buktrackz.databinding.FragmentAddBookBinding
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddBookFragment : Fragment() {

    private var _binding: FragmentAddBookBinding? = null
    private val binding get() = _binding!!
    private val compositeDisposable = CompositeDisposable()
    private var dateTime: Long = 0

    private lateinit var noteAdapter: NoteAdapter
    private val addNote: MutableList<Note> = mutableListOf()
    private var indexNote = 0

    private val addBookViewModel: AddBookViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddBookBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.acRate.isSaveEnabled = false
        binding.acGenre.isSaveEnabled = false
        setupAction()
        setupProgressBar()
        setupAddBook()
        binding.rvNote.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun setupAddBook() {
        binding.btnSave.setOnClickListener {
            if (checkValue()) {
                val bookData = Book(
                    title = binding.tiTitle.editText?.text.toString().trim(),
                    author = binding.tiAuthor.editText?.text.toString().trim(),
                    genre = binding.acGenre.text.toString().trim(),
                    totalPages = binding.tiTotalPages.editText?.text.toString().toInt(),
                    currentPages = binding.tiCurrentPage.editText?.text.toString().toInt(),
                    rate = getStarValue(),
                    lastRead = dateTime,
                    note = getAdditionalNote(),
                    isFavorite = false,
                )

                addBookViewModel.insertNewBook(bookData)
                addBookViewModel.result.observe(viewLifecycleOwner) {
                    it?.let {
                        showSnackBar(it)
                    }
                }
            } else {
                Toast.makeText(requireContext(), "Please fill the empty fields", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }
        }
    }

    private fun showSnackBar(id: Event<Long>) {
        val result = id.getContentIfNotHandled() ?: return
        if (result > 0) {
            Toast.makeText(requireContext(), "Insert operation succeed", Toast.LENGTH_SHORT).show()
            clearAllEditText()
        } else {
            Toast.makeText(requireContext(), "Insert operation failed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun clearAllEditText() {
        binding.tiTitle.editText?.text?.clear()
        binding.tiAuthor.editText?.text?.clear()
        binding.tiGenre.editText?.text?.clear()
        binding.tiTotalPages.editText?.text?.clear()
        binding.tiCurrentPage.editText?.text?.clear()
        binding.tiDate.editText?.text?.clear()
        binding.tiRate.editText?.text?.clear()
        binding.lpiPage.progress = 0
        addNote.clear()
        indexNote = 0
        binding.rvNote.visibility = View.GONE
    }

    private fun getAdditionalNote(): String {
        val noteBuilder = StringBuilder("")
        addNote.forEach {
            noteBuilder.append("${it.note},")
        }

        return noteBuilder.toString()
    }

    private fun checkValue(): Boolean {
        var isValid = false
        val titleValue = binding.tiTitle.editText?.text.toString().trim()
        val authorValue = binding.tiAuthor.editText?.text.toString().trim()
        val genreValue = binding.acGenre.text.toString().trim()
        val totalValue = binding.tiTotalPages.editText?.text.toString().trim()
        val currentValue = binding.tiCurrentPage.editText?.text.toString().trim()
        val rateValue = getStarValue()
        val lastReadValue = binding.tiDate.editText?.text.toString().trim()

        binding.tiTitle.error = null
        binding.tiAuthor.error = null
        binding.tiGenre.error = null
        binding.tiTotalPages.error = null
        binding.tiCurrentPage.error = null
        binding.tiDate.error = null
        binding.tiRate.error = null
        binding.tiGenre.error = null

        when {
            titleValue.isEmpty() -> binding.tiTitle.error = "Insert value"
            authorValue.isEmpty() -> binding.tiAuthor.error = "Insert value"
            genreValue.isEmpty() -> binding.tiGenre.error = "Insert value"
            totalValue.isEmpty() -> binding.tiTotalPages.error = "Insert value"
            currentValue.isEmpty() -> binding.tiCurrentPage.error = "Insert value"
            lastReadValue.isEmpty() -> binding.tiDate.error = "Insert value"
            rateValue < 0 -> binding.tiRate.error = "Insert value"
            genreValue.isEmpty() -> binding.tiGenre.error = "Insert value"
            else -> isValid = true
        }

        return isValid
    }

    private fun getStarValue(): Int {
        val star = binding.acRate.text.toString()

        val starValue: Int = when (star) {
            "★" -> 1
            "★★" -> 2
            "★★★" -> 3
            "★★★★" -> 4
            "★★★★★" -> 5
            else -> -1
        }

        return starValue
    }

    private fun setupProgressBar() {
        val totalPagesStream = RxTextView.textChanges(binding.edTotalPage)
            .skipInitialValue()
            .map { total ->
                total.isNotEmpty()
            }

        val currentPagesStream = RxTextView.textChanges(binding.edCurrentPage)
            .skipInitialValue()
            .map { current ->
                current.isNotEmpty()
            }

        val totalPageSubscribe = totalPagesStream.subscribe { valid ->
            binding.tiTotalPages.helperText = if (!valid) "Insert total pages" else null
        }

        val currentPageSubscribe = currentPagesStream.subscribe { valid ->
            binding.tiCurrentPage.helperText = if (!valid) "Insert current pages" else null
        }

        val validProgressBar = Observable.combineLatest(
            totalPagesStream,
            currentPagesStream,
        ) { totalPageValid: Boolean, currentPage: Boolean ->
            totalPageValid && currentPage
        }

        val validProgressSubscribe = validProgressBar.subscribe { isValid ->
            if (isValid) {
                val totalPagesValue = binding.edTotalPage.text!!.toString().toDouble()
                val currentPagesValue = binding.edCurrentPage.text!!.toString().toDouble()
                if (currentPagesValue > totalPagesValue) {
                    binding.tvProgressBar.text = getString(R.string.progress_bar_numeric, 100)
                    binding.lpiPage.progress = 100
                } else {
                    val progressPercent = (currentPagesValue / totalPagesValue) * 100
                    val convert = DecimalFormat("#.##").format(progressPercent)
                    binding.lpiPage.progress = progressPercent.toInt()
                    binding.tvProgressBar.text = getString(R.string.progress_bar_text, convert)
                }
            } else {
                binding.tvProgressBar.text = getString(R.string.progress_text)
                binding.lpiPage.progress = 0
            }
        }

        compositeDisposable.addAll(totalPageSubscribe, currentPageSubscribe, validProgressSubscribe)
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
                binding.tiDate.editText?.setText(sdf)
            }
        }

        binding.btnAddNote.setOnClickListener {
            indexNote++
            binding.rvNote.visibility = View.VISIBLE
            addNote.add(
                Note(
                    id = indexNote,
                    note = ""
                )
            )
            noteAdapter = NoteAdapter { id, note ->
                addNote.forEach { item ->
                    if (item.id == id) {
                        addNote[id - 1] = Note(
                            id = id,
                            note = note
                        )
                    }
                }
            }
            binding.rvNote.adapter = noteAdapter
            noteAdapter.addNote(addNote)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.rvNote.adapter = null
        compositeDisposable.clear()
    }
}