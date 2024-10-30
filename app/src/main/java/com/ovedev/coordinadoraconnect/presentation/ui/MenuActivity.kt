package com.ovedev.coordinadoraconnect.presentation.ui

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.ovedev.coordinadoraconnect.data.Response
import com.ovedev.coordinadoraconnect.data.remote.response.PdfResponse
import com.ovedev.coordinadoraconnect.databinding.ActivityMenuBinding
import com.ovedev.coordinadoraconnect.presentation.ui.base.BaseActivity
import com.ovedev.coordinadoraconnect.presentation.viewmodel.MenuViewModel
import com.tbruyelle.rxpermissions3.RxPermissions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MenuActivity : BaseActivity() {

    private val menuViewModel: MenuViewModel by viewModels()
    private lateinit var binding: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()
        requestPermissionsStorage()
        setupListeners()
    }

    private fun setupViewModel() {
        menuViewModel.responsePdf.observe(this) { response ->
            when (response) {
                is Response.Error -> Unit
                is Response.Loading -> Unit
                is Response.Success -> processData(response.data)
            }
        }
    }

    private fun loadData() {
        menuViewModel.getPdfLocation()
    }

    private fun setupListeners() {
        binding.btnReload.setOnClickListener {
            //loadData()
        }
    }

    private fun processData(response: PdfResponse) {

        response.base64Data?.let {
            val fileDoc = fileUtil.saveFileInLocalBase64(it, "archivo.pdf")
            binding.pdfView.fromFile(fileDoc)
                .defaultPage(0)
                .load()
        }

    }

    @SuppressLint("CheckResult")
    private fun requestPermissionsStorage() {

        RxPermissions(this).request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe({ granted ->
            if (granted) loadData()
            else Unit
        }, {

        })

    }

}