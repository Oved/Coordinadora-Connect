package com.ovedev.coordinadoraconnect.presentation.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ovedev.coordinadoraconnect.utils.FileUtil
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
open class BaseActivity : AppCompatActivity() {

    @Inject
    lateinit var fileUtil: FileUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

}