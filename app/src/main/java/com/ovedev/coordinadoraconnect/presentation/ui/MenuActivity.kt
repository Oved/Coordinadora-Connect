package com.ovedev.coordinadoraconnect.presentation.ui

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
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
        requestPermissions()
    }

    private fun setupViewModel() {
        menuViewModel.responsePdf.observe(this) { response ->
            when (response) {
                is Response.Error -> Unit
                is Response.Loading -> if (response.isLoading) loadingModal.show() else loadingModal.hide()
                is Response.Success -> processData(response.data)
            }
        }
    }

    private fun loadData() {
        menuViewModel.getPdfLocation()
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
            loadData()
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

    private fun goToMap() {
        val intent = Intent(this, MapActivity::class.java)
        intent.putParcelableArrayListExtra(Constant.BUNDLE_KEY_POSITIONS, ArrayList(gPositions ?: emptyList()))
        startActivity(intent)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PermissionsUtil.STORAGE_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                loadData()
            } else {
                Toast.makeText(this, "Permisos de almacenamiento denegados", Toast.LENGTH_LONG).show()
            }
        }
    }

}