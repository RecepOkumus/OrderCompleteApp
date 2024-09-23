package com.rcpokumus.ordercompleteapp.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.rcpokumus.ordercompleteapp.R
import com.rcpokumus.ordercompleteapp.databinding.ActivitySplashBinding
import com.rcpokumus.ordercompleteapp.viewmodel.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel._loading.observe(this) { isLoading ->
            if (isLoading) {
                binding.loadingSpinner.visibility = android.view.View.VISIBLE
            } else {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }

    }
}