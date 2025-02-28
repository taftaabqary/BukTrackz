package com.unsoed.buktrackz.ui.setting

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.unsoed.buktrackz.R
import com.unsoed.buktrackz.databinding.FragmentSettingBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingFragment : Fragment() {

    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!

    private val settingViewModel: SettingViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupCardSwitch()
        setupCardAbout()

        setupActionDarkMode()
    }

    private fun setupActionDarkMode() {

    }

    private fun setupCardSwitch() {
        binding.cardDarkMode.apply {
            tvSetting.text = "Dark Mode"
            ivSetting.setImageResource(R.drawable.outline_dark_mode_24)
        }

        binding.cardLanguage.apply {
            tvSetting.text = "Language"
            ivSetting.setImageResource(R.drawable.icon_language)
        }

        binding.cardLanguage.cardMaterial.setOnClickListener {
            startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
        }


        settingViewModel.getDisplayUser().observe(viewLifecycleOwner) { isDarkMode ->
            Log.d("SettingFragment", "Observe DataStore Switch $isDarkMode")
            binding.cardDarkMode.materialSwitch.isChecked = isDarkMode
        }

        binding.cardDarkMode.materialSwitch.setOnCheckedChangeListener { _, isChecked ->
            Log.d("SettingFragment", "Initial Check : $isChecked")
            settingViewModel.saveDisplayUser(isChecked)
        }
    }



    private fun setupCardAbout() {
        binding.cardRate.apply {
            tvSetting.text = "Rate Us"
            ivSetting.setImageResource(R.drawable.icon_star)
        }

        binding.cardAbout.apply {
            tvSetting.text = "About Us"
            ivSetting.setImageResource(R.drawable.icon_book)
        }

    }
}