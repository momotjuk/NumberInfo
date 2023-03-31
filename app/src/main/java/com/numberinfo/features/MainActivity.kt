package com.numberinfo.features

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import com.numberinfo.R
import com.numberinfo.core.extensions.hideKeyboard
import com.numberinfo.databinding.ActivityMainBinding
import org.koin.core.component.KoinComponent

class MainActivity : AppCompatActivity(), KoinComponent {

    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initNavController()
    }

    private fun initNavController() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
        navController = navHostFragment.navController
    }

    fun showProgress() {
        hideKeyboard()
        binding.progressBar.isVisible = true
    }

    fun hideProgress() {
        binding.progressBar.isVisible = false
    }
}