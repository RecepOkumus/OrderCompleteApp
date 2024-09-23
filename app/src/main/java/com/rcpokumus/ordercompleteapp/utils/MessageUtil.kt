package com.rcpokumus.ordercompleteapp.utils

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.rcpokumus.ordercompleteapp.R

object MessageUtil {
    fun showMessage1(context: Context, message: String, action: (() -> Unit)? = null) {
        val builder = AlertDialog.Builder(context)
        builder.setMessage(message)
            .setPositiveButton("Tamam") { dialog, _ ->
                dialog.dismiss()
                action?.invoke()
            }
        val alert = builder.create()
        alert.show()
    }

    fun showMessage(context: Context, message: String, action: (() -> Unit)? = null) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.custom_alert_dialog, null)
        val dialogMessage = dialogView.findViewById<TextView>(R.id.dialog_message)
        val positiveButton = dialogView.findViewById<Button>(R.id.positive_button)
        val closeButton = dialogView.findViewById<ImageView>(R.id.close_button)
        dialogMessage.text = message
        val alertDialog = AlertDialog.Builder(context)
            .setView(dialogView)
            .setCancelable(false)
            .create()

        positiveButton.setOnClickListener {
            alertDialog.dismiss()
            action?.invoke()
        }
        closeButton.setOnClickListener {
            alertDialog.dismiss()
        }

        alertDialog.show()
    }

}