package com.example.hw_4_1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.hw_4_1.databinding.ActivityMainBinding
import com.example.hw_4_1.prefs.Prefs
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private val prefs: Prefs by lazy {
        Prefs(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.bnv
        val navController = findNavController(R.id.nav_host_fragment_container)
        val appConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_favorite,
                R.id.navigation_home,
                R.id.navigation_profile,
            )
        )
        setupActionBarWithNavController(navController, appConfiguration)
        navView.setupWithNavController(navController)

        if (prefs.isFirstLaunch()) {
            navController.navigate(R.id.onboardingFragment)
            prefs.makeFirstLaunch()
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.onboardingFragment) {
                navView.visibility = View.GONE
                supportActionBar?.hide()
            } else {
                navView.visibility = View.VISIBLE
                supportActionBar?.show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}