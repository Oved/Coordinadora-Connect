package com.ovedev.coordinadoraconnect.presentation.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.ovedev.coordinadoraconnect.data.Response
import com.ovedev.coordinadoraconnect.databinding.ActivityLoginBinding
import com.ovedev.coordinadoraconnect.presentation.ui.base.BaseActivity
import com.ovedev.coordinadoraconnect.presentation.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity() {

    private val authViewModel: AuthViewModel by viewModels()

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()
        setupListeners()
    }

    private fun setupViewModel() {
        authViewModel.responseLogin.observe(this) { response ->
            when (response) {
                is Response.Error -> Unit
                is Response.Loading -> if (response.isLoading) loadingModal.show() else loadingModal.hide()
                is Response.Success -> startActivity(Intent(this@LoginActivity, MenuActivity::class.java))
            }
        }
    }

    private fun setupListeners() {
        binding.btnLogin.setOnClickListener {
            doLogin()
        }
    }

    private fun doLogin() {
        authViewModel.login(
            binding.edtName.text.toString(),
            binding.edtPassword.text.toString()
        )
    }
}