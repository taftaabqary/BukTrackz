package com.unsoed.buktrackz

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.scottyab.rootbeer.RootBeer
import com.unsoed.buktrackz.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val rootBeer = RootBeer(this)
        rootBeer.setLogging(true)
        if (rootBeer.detectRootManagementApps() || rootBeer.detectPotentiallyDangerousApps() || rootBeer.checkForDangerousProps()) {
            Toast.makeText(this, "Device is rooted with bypass! Exiting app...", Toast.LENGTH_LONG).show()
            finish()
        }

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        navController.addOnDestinationChangedListener {_, destination, _ ->
            if(destination.id == R.id.detailBookFragment || destination.id == R.id.editBookFragment) {
                binding.navView.visibility = View.GONE
            } else {
                binding.navView.visibility = View.VISIBLE
            }
        }

        mainViewModel.getDisplayUser().observe(this) {
            it?.let { result ->
                if(result) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            }
        }
        navView.setupWithNavController(navController)
    }
}