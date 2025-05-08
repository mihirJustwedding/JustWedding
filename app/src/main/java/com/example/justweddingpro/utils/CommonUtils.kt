package com.example.justweddingpro.utils

import android.app.Dialog
import android.content.Context
import android.content.pm.PackageManager
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import com.example.justweddingpro.R
import okhttp3.RequestBody
import okio.Buffer
import java.io.IOException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class CommonUtils {

    companion object {

        var progressDialog: CustomProgressDialog? = null

        fun bodyToString(request: RequestBody?): String {
            return try {
                val buffer = Buffer()
                if (request != null) request.writeTo(buffer) else return ""
                buffer.readUtf8()
            } catch (e: IOException) {
                "did not work"
            }
        }

        fun showProgressDialog(context: Context?) {
            progressDialog = CustomProgressDialog(context)
            progressDialog!!.show()
        }

        interface OnDialogClickListener {
            fun OnYesClick(dialog: Dialog)
            fun OnNoClick(dialog: Dialog)
        }

        fun hideProgressDialog() {
            if (progressDialog != null) {
                progressDialog!!.hide()
            }
        }

        fun ClickedAnimation(context: Context, view: View) {
            val animation: Animation =
                AnimationUtils.loadAnimation(context, R.anim.alpha)
            view.startAnimation(animation)
        }

        @JvmStatic
        fun parseDateToViewUtcToLocal(time: String?): String? {
            val inputPattern = "yyyy-MM-dd HH:mm"
            val outputPattern = "dd-MM-yyyy HH:mm"
            val inputFormat = SimpleDateFormat(inputPattern)
            val outputFormat = SimpleDateFormat(outputPattern)

            inputFormat.timeZone = TimeZone.getDefault()
            outputFormat.timeZone = TimeZone.getDefault()
            var date: Date? = null
            var str: String? = null
            try {
                date = inputFormat.parse(time)
                str = outputFormat.format(date)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return str
        }

        @JvmStatic
        fun mGetPackageName(context: Context): String {
            var PACKAGE_NAME = "";
            try {
                PACKAGE_NAME = context.applicationContext.packageName
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }
            return PACKAGE_NAME
        }

        @JvmStatic
        fun parseDateAndTimeToViewDate(time: String?): String? {
            val inputPattern = "yyyy-MM-dd HH:mm"
            val outputPattern = "dd-MMMM-yyyy"
            val inputFormat = SimpleDateFormat(inputPattern)
            val outputFormat = SimpleDateFormat(outputPattern)

            inputFormat.timeZone = TimeZone.getDefault()
            outputFormat.timeZone = TimeZone.getDefault()
            var date: Date? = null
            var str: String? = null
            try {
                date = inputFormat.parse(time)
                str = outputFormat.format(date)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return str
        }

        @JvmStatic
        fun parseDateAndToViewDate(time: String?): String? {
            val inputPattern = "yyyy-MM-dd"
            val outputPattern = "dd-MMMM-yyyy"
            val inputFormat = SimpleDateFormat(inputPattern)
            val outputFormat = SimpleDateFormat(outputPattern)

            inputFormat.timeZone = TimeZone.getDefault()
            outputFormat.timeZone = TimeZone.getDefault()
            var date: Date? = null
            var str: String? = null
            try {
                date = inputFormat.parse(time)
                str = outputFormat.format(date)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return str
        }

        @JvmStatic
        fun parseDateAndTimeToViewTime(time: String?): String? {
            val inputPattern = "yyyy-MM-dd HH:mm"
            val outputPattern = "HH:mm aa"
            val inputFormat = SimpleDateFormat(inputPattern)
            val outputFormat = SimpleDateFormat(outputPattern)

            inputFormat.timeZone = TimeZone.getDefault()
            outputFormat.timeZone = TimeZone.getDefault()
            var date: Date? = null
            var str: String? = null
            try {
                date = inputFormat.parse(time)
                str = outputFormat.format(date)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return str
        }

        @JvmStatic
        fun parseOnlyDateToUploadFormat(time: String?): String? {
            val inputPattern = "dd-MM-yyyy"
            val outputPattern = "yyyy-MM-dd"
            val inputFormat = SimpleDateFormat(inputPattern)
            val outputFormat = SimpleDateFormat(outputPattern)
            var date: Date? = null
            var str: String? = null
            try {
                date = inputFormat.parse(time)
                str = outputFormat.format(date)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return str
        }

        @JvmStatic
        fun parseOnlyUploadToDateFormat(time: String?): String? {
            val inputPattern = "yyyy-MM-dd"
            val outputPattern = "dd-MM-yyyy"
            val inputFormat = SimpleDateFormat(inputPattern)
            val outputFormat = SimpleDateFormat(outputPattern)
            var date: Date? = null
            var str: String? = null
            try {
                date = inputFormat.parse(time)
                str = outputFormat.format(date)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return str
        }

        @JvmStatic
        fun parseDateToUploadFormat(time: String?): String? {
            val inputPattern = "dd-MM-yyyy HH:mm"
            val outputPattern = "yyyy-MM-dd HH:mm"
            val inputFormat = SimpleDateFormat(inputPattern)
            val outputFormat = SimpleDateFormat(outputPattern)
            var date: Date? = null
            var str: String? = null
            try {
                date = inputFormat.parse(time)
                str = outputFormat.format(date)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return str
        }

        @JvmStatic
        fun printDateViewFormate(time: String?): String? {
            val inputPattern = "dd-M-yyyy"
            val outputPattern = "dd-MM-yyyy"
            val inputFormat = SimpleDateFormat(inputPattern, Locale.ENGLISH)
            val outputFormat = SimpleDateFormat(outputPattern, Locale.ENGLISH)
            var date: Date? = null
            var str: String? = null
            try {
                date = inputFormat.parse(time)
                str = outputFormat.format(date)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return str
        }

        @JvmStatic
        fun printTimeViewFormat(time: String?): String? {
            val inputPattern = "HH:m"
            val outputPattern = "HH:mm"
            val inputFormat = SimpleDateFormat(inputPattern, Locale.ENGLISH)
            val outputFormat = SimpleDateFormat(outputPattern, Locale.ENGLISH)
            var date: Date? = null
            var str: String? = null
            try {
                date = inputFormat.parse(time)
                str = outputFormat.format(date)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return str
        }

        @JvmStatic
        fun confirmShowDialog(
            context: Context?,
            msg: String?,
            onDialogClickListener: OnDialogClickListener
        ) {
            val dialog = Dialog(context!!, R.style.TransparentStyle)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.alert_normal_dialog_options)
            dialog.window!!.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT
            )
            dialog.setCancelable(false)
            val txt = dialog.findViewById<TextView>(R.id.txt_alert)
            val yes = dialog.findViewById<Button>(R.id.btn_alert)
            val no = dialog.findViewById<Button>(R.id.btnCancel)
            txt.text = msg

            yes.setOnClickListener {
                dialog.dismiss()
                onDialogClickListener.OnYesClick(dialog)
            }
            no.setOnClickListener {
                dialog.dismiss()
                onDialogClickListener.OnNoClick(dialog)
            }
            dialog.show()
        }

        @JvmStatic
        fun confirmShowAlertDialog(
            context: Context?,
            msg: String?,
            onDialogClickListener: OnDialogClickListener
        ) {
            val dialog = Dialog(context!!, R.style.TransparentStyle)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.alert_normal_thankyou_dialog_options)
            dialog.window!!.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT
            )
            dialog.setCancelable(false)
            val txt = dialog.findViewById<TextView>(R.id.txt_alert)
            val yes = dialog.findViewById<Button>(R.id.btn_alert)
            txt.text = msg
            yes.setOnClickListener {
                dialog.dismiss()
                onDialogClickListener.OnYesClick(dialog)
            }
            dialog.show()
        }

        @JvmStatic
        fun confirmShowDeleteDialog(
            context: Context?,
            msg: String?,
            onDialogClickListener: OnDialogClickListener
        ) {
            val dialog = Dialog(context!!, R.style.TransparentStyle)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.alert_normal_delete_dialog)
            dialog.window!!.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT
            )
            dialog.setCancelable(false)
            val txt = dialog.findViewById<TextView>(R.id.txt_alert)
            val yes = dialog.findViewById<Button>(R.id.btn_alert)
            val no = dialog.findViewById<Button>(R.id.btnCancel)
            txt.text = msg
            yes.setOnClickListener {
                dialog.dismiss()
                onDialogClickListener.OnYesClick(dialog)
            }
            no.setOnClickListener {
                dialog.dismiss()
                onDialogClickListener.OnNoClick(dialog)
            }
            dialog.show()
        }

        @JvmStatic
        fun confirmShowInformationDialog(
            context: Context?,
            msg: String?,
            onDialogClickListener: OnDialogClickListener
        ) {
            val dialog = Dialog(context!!, R.style.TransparentStyle)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.alert_normal_information_dialog)
            dialog.window!!.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT
            )
            dialog.setCancelable(false)
            val txt = dialog.findViewById<TextView>(R.id.txt_alert)
            val yes = dialog.findViewById<Button>(R.id.btn_alert)
            val no = dialog.findViewById<Button>(R.id.btnCancel)
            txt.text = msg
            yes.setOnClickListener {
                dialog.dismiss()
                onDialogClickListener.OnYesClick(dialog)
            }
            no.setOnClickListener {
                dialog.dismiss()
                onDialogClickListener.OnNoClick(dialog)
            }
            dialog.show()
        }

        @JvmStatic
        fun mGetStatus(status: Int): String {
            var mStatusString = ""
            if (status == 0) {
                mStatusString = "inquiry"
            } else if (status == 1) {
                mStatusString = "Confirm"
            } else if (status == 2) {
                mStatusString = "Cancel"
            }
            return mStatusString
        }
    }
}