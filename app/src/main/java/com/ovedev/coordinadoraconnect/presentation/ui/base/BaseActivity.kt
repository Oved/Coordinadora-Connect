package com.ovedev.coordinadoraconnect.presentation.ui.base

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ovedev.coordinadoraconnect.R
import com.ovedev.coordinadoraconnect.utils.FileUtil
import com.ovedev.coordinadoraconnect.utils.view.DialogInfo
import com.ovedev.coordinadoraconnect.utils.view.IDialogInfo
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
open class BaseActivity : AppCompatActivity() {

    @Inject
    lateinit var fileUtil: FileUtil

    lateinit var loadingModal: ProgressLoadingModal

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupView()
    }

    private fun setupView() {
        loadingModal = ProgressLoadingModal(this)
    }

    fun showDialogErrorNetwork(retryMethod: () -> Unit) {
        val dialog = DialogInfo(this)
        dialog.setCallbacks(object : IDialogInfo {
            override fun onPressBtn() = retryMethod()
            override fun onPressBtnTwo() = Unit
        })
        dialog.show(
            getString(R.string.text_error_no_connection),
            getString(R.string.text_error_network),
            getString(R.string.text_try)
        )
    }

    fun validateNetwork(validNetwork: () -> Unit, errorNetwork: () -> Unit) {
        if (isInternetAvailable()) validNetwork()
        else errorNetwork()
    }

    @Suppress("DEPRECATION")
    private fun isInternetAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

}