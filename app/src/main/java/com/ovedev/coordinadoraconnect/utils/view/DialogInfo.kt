package com.ovedev.coordinadoraconnect.utils.view

import android.content.Context
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.ovedev.coordinadoraconnect.databinding.DialogInfoBinding

class DialogInfo(private val context: Context) {

    private val dialogBuilder = MaterialAlertDialogBuilder(context)
    private var callbacks: IDialogInfo? = null

    fun setCallbacks(callbacks: IDialogInfo) {
        this.callbacks = callbacks
    }

    fun show(
        title: String,
        subTitle: String,
        btnText: String,
        btnTwoEnable: Boolean = false,
        btnTwoText: String = ""
    ) {

        val binding = DialogInfoBinding.inflate(LayoutInflater.from(context))
        dialogBuilder.setView(binding.root)

        val dialog = dialogBuilder.create()
        dialog.window?.setDimAmount(0.7f)
        dialog.setCanceledOnTouchOutside(false)

        dialog.setOnKeyListener { _, keyCode, event ->
            keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_UP
        }

        binding.txtTitle.text = title
        binding.txtSubTitle.text = subTitle
        binding.btnOne.text = btnText
        binding.btnTwo.text = btnTwoText

        binding.btnOne.setOnClickListener {
            callbacks?.onPressBtn()
            dialog.dismiss()
        }

        binding.btnTwo.setOnClickListener {
            callbacks?.onPressBtnTwo()
            dialog.dismiss()
        }

        binding.btnTwo.visibility = if (btnTwoEnable) View.VISIBLE else View.GONE

        dialog.show()

    }

}