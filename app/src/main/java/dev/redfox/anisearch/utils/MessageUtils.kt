package dev.redfox.anisearch.utils

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dev.redfox.anisearch.R


fun showLog(message: String?, tag: String = "AniSearch") {
    message?.let {
        printFullLog(it, tag)
    }
}

fun Exception.showLog() {
    printStackTrace()
}

fun Throwable.showLog() {
    printStackTrace()
}

/**
 * Log contents of bundle in debug mode
 * */

fun Bundle?.showLog(tag: String = "EIGHT") {
    this?.apply {
        for (key in keySet()) {
            Log.e(tag, "KEY: $key ::: VALUE: ${get(key).toString()}")
        }
    }
}

fun Map<String, String>?.showLog(tag: String = "EIGHT") {
    this?.apply {
        for ((key, value) in this) {
            Log.e(tag, "KEY: $key ::: VALUE: $value")
        }
    }
}

private fun printFullLog(message: String, tag: String) {
    if (message.length > 3000) {
        Log.e(tag, message.substring(0, 3000))
        printFullLog(message.substring(3000), tag)
    } else {
        Log.e(tag, message)
    }
}

fun Context.showToast(message: String?, length: Int = Toast.LENGTH_SHORT) {
    message?.let {
        Toast.makeText(this, it, length).show()
    }
}

var alertDialog: AlertDialog? = null

fun Context.showAlert(
    message: String?,
    posText: String = "Ok",
    onPosClick: ((Context) -> Unit)? = null
) {
    message?.let {
        try {
            if (alertDialog?.isShowing != true) {
                showLog("SHOWING ALERT $message", "DIALOG")
                val dialogBuilder =
                    MaterialAlertDialogBuilder(this, R.style.AlertDialogTheme).apply {
                        setTitle("Oops!!")
                        setMessage(message)
                        setCancelable(false)
                        setPositiveButton(posText) { dialog, _ ->
                            dialog.dismiss()
                            onPosClick?.invoke(this@showAlert)
                        }
                    }
                alertDialog = dialogBuilder.create()
                alertDialog?.show()
            }
        } catch (ex: Exception) {
            ex.showLog()
        }
    }
}

fun Context.showAlert(
    message: String?,
    title: String? = null,
    posText: String = "Ok",
    onPosClick: ((Context) -> Unit)? = null,
    negText: String? = null,
    onNegClick: ((Context) -> Unit)? = null,
    isCancelable: Boolean = false
) {
    try {
        if (message != null) {
            val dialogBuilder =
                MaterialAlertDialogBuilder(this, R.style.AlertDialogTheme).apply {
                    if (title != null)
                        setTitle(title)
                    setMessage(message)
                    setCancelable(isCancelable)
                    setPositiveButton(posText) { dialog, _ ->
                        dialog.dismiss()
                        onPosClick?.invoke(context)
                    }
                    setNegativeButton(negText) { dialog, _ ->
                        dialog.dismiss()
                        onNegClick?.invoke(context)
                    }
                }
            alertDialog = dialogBuilder.create()
            alertDialog?.show()
        }
    } catch (ex: Exception) {
        ex.showLog()
    }
}