package com.ovedev.coordinadoraconnect.presentation.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import com.ovedev.coordinadoraconnect.databinding.ActivityMenuBinding
import com.ovedev.coordinadoraconnect.presentation.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MenuActivity : BaseActivity() {

    private lateinit var binding: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}