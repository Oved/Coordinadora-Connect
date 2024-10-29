package com.ovedev.coordinadoraconnect.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.ovedev.coordinadoraconnect.BuildConfig
import com.ovedev.coordinadoraconnect.R
import com.ovedev.coordinadoraconnect.databinding.ActivitySplashBinding
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
        doAnimation()
        validateNavigation()
    }

    private fun setupView() {
        val strVersion = "CC:${BuildConfig.VERSION_NAME}"
        binding.txtVersion.text = strVersion
    }

    private fun validateNavigation() {

        lifecycleScope.launch {
            delay(2000)
            val destination = if (isSessionActive()) MenuActivity::class.java else LoginActivity::class.java
            startActivity(Intent(this@SplashActivity, destination))
            finish()
        }

    }

    private fun isSessionActive(): Boolean {
        return false
    }

    private fun doAnimation() {
        val animation1 = AnimationUtils.loadAnimation(this, R.anim.move_up)
        val animation2 = AnimationUtils.loadAnimation(this, R.anim.move_bottom)
        binding.ivSplashLogo.animation = animation2
        binding.txtVersion.animation = animation1
    }

    override fun onPause() {
        super.onPause()
        lifecycleScope.cancel()
    }

}