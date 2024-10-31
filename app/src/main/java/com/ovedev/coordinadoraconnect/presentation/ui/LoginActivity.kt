package com.ovedev.coordinadoraconnect.presentation.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.widget.doOnTextChanged
import com.ovedev.coordinadoraconnect.R
import com.ovedev.coordinadoraconnect.data.Response
import com.ovedev.coordinadoraconnect.databinding.ActivityLoginBinding
import com.ovedev.coordinadoraconnect.presentation.ui.base.BaseActivity
import com.ovedev.coordinadoraconnect.presentation.viewmodel.AuthViewModel
import com.ovedev.coordinadoraconnect.utils.view.DialogInfo
import com.ovedev.coordinadoraconnect.utils.view.IDialogInfo
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
                is Response.Error -> showDialogError(response.errorMessage)
                is Response.Loading -> if (response.isLoading) loadingModal.show() else loadingModal.hide()
                is Response.Success -> goToMenu()
            }
        }
    }

    private fun setupListeners() {
        binding.btnLogin.setOnClickListener {
            doLogin()
        }
        binding.edtName.doOnTextChanged { _, _, _, _ ->
            validateBtnLogin()
        }
        binding.edtPassword.doOnTextChanged { _, _, _, _ ->
            validateBtnLogin()
        }
    }

    private fun validateBtnLogin() {
        binding.btnLogin.isEnabled = ((binding.edtName.text?.length ?: 0) > 4 && (binding.edtPassword.text?.length ?: 0) > 4)
    }

    private fun goToMenu() {
        startActivity(Intent(this@LoginActivity, MenuActivity::class.java))
        finish()
    }

    private fun doLogin() {
        authViewModel.login(
            binding.edtName.text.toString(),
            binding.edtPassword.text.toString()
        )
    }

    private fun showDialogError(message: String) {
        val dialog = DialogInfo(this)
        dialog.setCallbacks(object : IDialogInfo {
            override fun onPressBtn() = doLogin()
            override fun onPressBtnTwo() = Unit
        })
        dialog.show(
            getString(R.string.text_error_title),
            message,
            getString(R.string.text_try),
            btnTwoEnable = true,
            getString(R.string.text_understood)
        )
    }
}