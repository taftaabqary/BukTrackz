package com.unsoed.buktrackz.ui.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.unsoed.buktrackz.R
import com.unsoed.buktrackz.databinding.FragmentSettingBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingFragment : Fragment() {

    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!

    private val settingViewModel: SettingViewModel by viewModel()
    private var selectedIndex = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getLanguagePref()
        setupCardSwitch()
        setupCardAbout()

    }

    private fun getLanguagePref() {
        settingViewModel.getLanguageUser().observe(viewLifecycleOwner) {
            it?.let { currentLang ->
                selectedIndex = when (currentLang) {
                    "en" -> 0
                    "id" -> 1
                    else -> 0
                }
            }
        }
    }


    private fun setupCardSwitch() {
        binding.cardDarkMode.apply {
            tvSetting.text = getString(R.string.dark_mode_text)
            ivSetting.setImageResource(R.drawable.outline_dark_mode_24)
        }

        binding.cardLanguage.apply {
            tvSetting.text = getString(R.string.language_text)
            ivSetting.setImageResource(R.drawable.icon_language)

        }

        binding.cardLanguage.cardMaterial.setOnClickListener {
            showLanguageChangeDialog()
        }


        settingViewModel.getDisplayUser().observe(viewLifecycleOwner) { isDarkMode ->
            binding.cardDarkMode.materialSwitch.isChecked = isDarkMode
        }

        binding.cardDarkMode.materialSwitch.setOnCheckedChangeListener { _, isChecked ->
            settingViewModel.saveDisplayUser(isChecked)
        }
    }

    private fun showLanguageChangeDialog() {
        val languages = arrayOf("English", "Indonesia")
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.title_language_dialog))
            .setSingleChoiceItems(languages, selectedIndex) { dialog, which ->
                val languageTag = when (which) {
                    0 -> "en"
                    1 -> "id"
                    else -> "en"
                }

                val appLocale = LocaleListCompat.forLanguageTags(languageTag)
                AppCompatDelegate.setApplicationLocales(appLocale)

                lifecycleScope.launch {
                    settingViewModel.saveLanguageUser(languageTag)
                }

                dialog.dismiss()
            }
            .setNegativeButton(android.R.string.cancel) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }


    private fun setupCardAbout() {
        binding.cardRate.apply {
            tvSetting.text = getString(R.string.rate_us_text)
            ivSetting.setImageResource(R.drawable.icon_star)
        }

        binding.cardAbout.apply {
            tvSetting.text = getString(R.string.about_app_text)
            ivSetting.setImageResource(R.drawable.icon_book)
        }

        binding.cardAbout.cardMaterial.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(getString(R.string.title_about_dialog))
                .setMessage(getString(R.string.about_message_text))
                .setNeutralButton(getString(R.string.neutral_button_about)) { dialog, _ ->
                    dialog.dismiss()
                }
                .setIcon(R.drawable.icon_bug_black)
                .show()
        }

        binding.cardRate.cardMaterial.setOnClickListener {
            Toast.makeText(
                requireContext(),
                getString(R.string.toast_rate_text),
                Toast.LENGTH_SHORT
            )
                .show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}