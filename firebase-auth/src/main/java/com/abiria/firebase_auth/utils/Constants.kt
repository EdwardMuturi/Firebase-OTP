package com.abiria.firebase_auth.utils

import android.content.Context
import android.net.ConnectivityManager
import android.text.TextUtils
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.activity_register.*


class Constants {

    companion object {
        lateinit var snackbar: Snackbar

        fun checkEmptyEditText(view: TextInputEditText): Boolean {
            return TextUtils.isEmpty(view.text.toString())
        }

        fun setErrorOnEmptyEditText(view1: TextInputEditText, viewName: String) {
            if (checkEmptyEditText(view1)) {
                view1.error = "Enter $viewName"
            }
        }

        fun checkInternetAvailability(activityContext: Context): Boolean {
            val connectivityManager =
                activityContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnected
        }

        fun showSnackBar(view: View, message: String) {
            snackbar = Snackbar.make(
                view,
                message,
                Snackbar.LENGTH_LONG
            )
            snackbar.show()
        }

        fun closeSnackBar() {
            snackbar.dismiss()
        }
    }
}