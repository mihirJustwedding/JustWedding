package com.example.justweddingpro.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import com.example.justweddingpro.R

class CustomProgressDialog(context: Context?) {
    var dialog: Dialog?
    fun show() {
        try {
            if (dialog != null && !dialog!!.isShowing) {
                dialog!!.show()
            }
        } catch (e: Exception) {
        }
    }

    fun hide() {
        try {
            if (dialog != null && dialog!!.isShowing) {
                dialog!!.dismiss()
            }
        } catch (e: Exception) {
        }
    }

    init {
        dialog = Dialog(context!!)
        dialog!!.setContentView(R.layout.custom_progress_dialog)
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog!!.setCancelable(false)
    }
}