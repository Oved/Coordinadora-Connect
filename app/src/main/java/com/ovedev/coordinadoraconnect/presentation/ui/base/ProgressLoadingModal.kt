package com.ovedev.coordinadoraconnect.presentation.ui.base

import android.app.Dialog
import android.content.Context
import android.view.Window
import android.view.WindowManager
import com.ovedev.coordinadoraconnect.R

class ProgressLoadingModal(context: Context) {
    private val dialog: Dialog = Dialog(context)

    init {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.modal_loader)

        val layoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(dialog.window?.attributes)
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        dialog.window?.attributes = layoutParams
        dialog.window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent);
        dialog.window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

    }

    fun show() {
        dialog.show()
    }

    fun hide() {
        dialog.dismiss()
    }
}