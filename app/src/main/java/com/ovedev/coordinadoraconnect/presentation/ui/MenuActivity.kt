package com.ovedev.coordinadoraconnect.presentation.ui

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import android.window.OnBackInvokedDispatcher
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.ovedev.coordinadoraconnect.data.Response
import com.ovedev.coordinadoraconnect.data.remote.response.PdfResponse
import com.ovedev.coordinadoraconnect.databinding.ActivityMenuBinding
import com.ovedev.coordinadoraconnect.presentation.model.Position
import com.ovedev.coordinadoraconnect.presentation.ui.base.BaseActivity
import com.ovedev.coordinadoraconnect.presentation.viewmodel.MenuViewModel
import com.ovedev.coordinadoraconnect.utils.Constant
import com.ovedev.coordinadoraconnect.utils.PermissionsUtil
import com.ovedev.coordinadoraconnect.utils.view.DialogInfo
import com.ovedev.coordinadoraconnect.utils.view.IDialogInfo
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MenuActivity : BaseActivity() {

    private val menuViewModel: MenuViewModel by viewModels()
    private lateinit var binding: ActivityMenuBinding
    private var gPositions: List<Position>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()
        setupListeners()
        menuViewModel.verifyContinueInSession()
    }

    private fun setupViewModel() {
        menuViewModel.responsePdf.observe(this) { response ->
            when (response) {
                is Response.Error -> showDialogError(response.errorMessage)
                is Response.Loading -> if (response.isLoading) loadingModal.show() else loadingModal.hide()
                is Response.Success -> processData(response.data)
            }
        }
        menuViewModel.responseContinueInSession.observe(this) { response ->
            when (response) {
                is Response.Error -> Unit
                is Response.Loading -> if (response.isLoading) loadingModal.show() else loadingModal.hide()
                is Response.Success -> processContinueInSession(response.data)
            }
        }
        menuViewModel.responseCloseSession.observe(this) { response ->
            when (response) {
                is Response.Error -> goToSplash()
                is Response.Loading -> if (response.isLoading) loadingModal.show() else loadingModal.hide()
                is Response.Success -> goToSplash()
            }
        }
    }

    private fun setupListeners() {
        binding.btnReload.setOnClickListener {
            requestPermissions()
        }
        binding.btnMap.setOnClickListener {
            goToMap()
        }
    }

    private fun requestPermissions() {
        PermissionsUtil().requestStoragePermissions(this) {
            menuViewModel.getPdfLocation()
        }
    }

    private fun processData(response: PdfResponse) {
        gPositions = response.positions
        response.base64Data?.let {
            val fileDoc = fileUtil.saveFileInLocalBase64(it, Constant.FILE_NAME)
            binding.pdfView.fromUri(fileDoc)
                .defaultPage(0)
                .load()
        }
    }

    private fun processContinueInSession(inSession: Boolean) {
        if (inSession) requestPermissions()
        else showDialogSessionExpired()
    }

    private fun goToMap() {
        val intent = Intent(this, MapActivity::class.java)
        intent.putParcelableArrayListExtra(Constant.BUNDLE_KEY_POSITIONS, ArrayList(gPositions ?: emptyList()))
        startActivity(intent)
    }

    private fun goToSplash() {
        val intent = Intent(this@MenuActivity, SplashActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PermissionsUtil.STORAGE_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                menuViewModel.getPdfLocation()
            } else {
                Toast.makeText(this, "Permisos de almacenamiento denegados", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun showDialogError(message: String) {
        val dialog = DialogInfo(this)
        dialog.setCallbacks(object : IDialogInfo {
            override fun onPressBtn() = menuViewModel.getPdfLocation()
            override fun onPressBtnTwo() = Unit
        })
        dialog.show(
            "Error",
            message,
            "Reintentar",
            btnTwoEnable = true,
            "Entendido"
        )
    }

    private fun showDialogSessionExpired() {
        val dialog = DialogInfo(this)
        dialog.setCallbacks(object : IDialogInfo {
            override fun onPressBtn() = menuViewModel.closeSession()
            override fun onPressBtnTwo() = Unit
        })
        dialog.show(
            "Tu sesión ha expirado",
            "Tu sesión ha expirado, por favor inicia sesión nuevamente",
            "Entendido"
        )
    }

}