package com.unsoed.buktrackz.ui.add

import android.icu.text.DecimalFormat
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.datepicker.MaterialDatePicker
import com.jakewharton.rxbinding2.widget.RxTextView
import com.unsoed.buktrackz.adapter.NoteAdapter
import com.unsoed.buktrackz.databinding.FragmentAddBookBinding
import com.unsoed.buktrackz.domain.entity.Book
import com.unsoed.buktrackz.domain.entity.Note
import com.unsoed.buktrackz.helper.ViewModelFactory
import com.unsoed.buktrackz.utils.DateConverter
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable

class AddBookFragment : Fragment() {

    private var _binding: FragmentAddBookBinding? = null
    private val binding get() = _binding!!
    private val compositeDisposable = CompositeDisposable()
    private var dateTime: Long = 0

    private lateinit var noteAdapter: NoteAdapter
    private val addNote: MutableList<Note> = mutableListOf()
    private var indexNote = 0

    private val addBookViewModel: AddBookViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddBookBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAction()
        setupProgressBar()
        setupAddBook()
        binding.rvNote.layoutManager = LinearLayoutManager(requireContext())

    }

    private fun setupAddBook() {
        binding.btnSave.setOnClickListener {
            if(checkValue()) {
                val bookData = Book(
                    title = binding.tiTitle.editText?.text.toString().trim(),
                    author = binding.tiAuthor.editText?.text.toString().trim(),
                    genre = binding.acGenre.text.toString().trim(),
                    totalPages = binding.tiTotalPages.editText?.text.toString().toInt(),
                    currentPages = binding.tiCurrentPage.editText?.text.toString().toInt(),
                    rate = getStarValue(),
                    lastRead = dateTime,
                    note = getAdditionalNote(),
                    isFavorite = false
                )

                Log.d("AddBook", bookData.toString())

//                addBookViewModel.insertNewBook(bookData)
//                addBookViewModel.result.observe(viewLifecycleOwner) {
//                    it?.let {
//                        if(it > 0){
//                            Toast.makeText(requireContext(), "Insert operation succeed", Toast.LENGTH_SHORT).show()
//                        } else {
//                            Toast.makeText(requireContext(), "Insert operation failed", Toast.LENGTH_SHORT).show()
//                        }
//                    }
//                }
            } else {
                Toast.makeText(requireContext(), "Please fill the empty fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
        }
    }

    private fun getAdditionalNote(): String {
        val noteBuilder = StringBuilder()
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

        when {
            titleValue.isEmpty() -> binding.tiTitle.error = "Insert value"
            authorValue.isEmpty() -> binding.tiAuthor.error = "Insert value"
            genreValue.isEmpty() -> binding.tiGenre.error = "Insert value"
            totalValue.isEmpty() && totalValue.toInt() > 0 -> binding.tiTotalPages.error = "Insert value"
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
        var starValue = 0

        starValue = when(star) {
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
            binding.tiTotalPages.helperText = if(!valid) "Insert total pages" else null
        }

        val currentPageSubscribe = currentPagesStream.subscribe { valid ->
            binding.tiCurrentPage.helperText = if(!valid) "Insert current pages" else null
        }

        val validProgressBar = Observable.combineLatest(
            totalPagesStream,
            currentPagesStream,
        ) { totalPageValid: Boolean, currentPage: Boolean ->
            totalPageValid && currentPage
        }

        val validProgressSubscribe = validProgressBar.subscribe { isValid ->
            if(isValid) {
                val totalPagesValue = binding.edTotalPage.text!!.toString().toDouble()
                val currentPagesValue = binding.edCurrentPage.text!!.toString().toDouble()
                if(currentPagesValue > totalPagesValue) {
                    binding.tvProgressBar.text = "Progress Bar - 100%"
                    binding.lpiPage.progress = 100
                } else {
                    val progressPercent = (currentPagesValue / totalPagesValue) * 100
                    val convert = DecimalFormat("#.##").format(progressPercent)
                    binding.lpiPage.progress = progressPercent.toInt()
                    binding.tvProgressBar.text = "Progress Bar - $convert%"
                }
            } else {
                binding.tvProgressBar.text = "Progress Bar"
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
        compositeDisposable.clear()
    }
}