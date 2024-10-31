package com.ovedev.coordinadoraconnect.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.ovedev.coordinadoraconnect.BuildConfig
import com.ovedev.coordinadoraconnect.R
import com.ovedev.coordinadoraconnect.data.Response
import com.ovedev.coordinadoraconnect.databinding.ActivitySplashBinding
import com.ovedev.coordinadoraconnect.presentation.ui.base.BaseActivity
import com.ovedev.coordinadoraconnect.presentation.viewmodel.AuthViewModel
import com.ovedev.coordinadoraconnect.utils.Constant
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashActivity : BaseActivity() {

    private val authViewModel: AuthViewModel by viewModels()
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()
        setupView()
        doAnimation()
        authViewModel.isSessionActive()
    }

    private fun setupView() {
        val strVersion = getString(R.string.text_version).replace(Constant.GLOBAL_REPLACEMENT, BuildConfig.VERSION_NAME)
        binding.txtVersion.text = strVersion
    }

    private fun setupViewModel() {
        authViewModel.isSessionActive.observe(this) { response ->
            when (response) {
                is Response.Error -> Unit
                is Response.Loading -> Unit
                is Response.Success -> validateNavigation(response.data)
            }
        }
    }

    private fun validateNavigation(isSessionActive: Boolean) {
        lifecycleScope.launch {
            delay(2000)
            val destination = if (isSessionActive) MenuActivity::class.java else LoginActivity::class.java
            startActivity(Intent(this@SplashActivity, destination))
            finish()
        }
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