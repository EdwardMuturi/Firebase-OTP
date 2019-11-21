package com.abiria.firebase_auth.ui

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.abiria.firebase_auth.R
import com.abiria.firebase_auth.utils.Constants
import com.abiria.firebase_auth.viewmodel.RegisterViewModel
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.activity_register.*
import java.util.concurrent.TimeUnit

class RegisterActivity : AppCompatActivity() {
    private lateinit var registerViewModel: RegisterViewModel
    private lateinit var firebaseAuth : FirebaseAuth

    lateinit var mVerificationId: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        FirebaseApp.initializeApp(this)
        firebaseAuth = FirebaseAuth.getInstance()

        registerViewModel = ViewModelProviders.of(this).get(RegisterViewModel::class.java)

        ccp_next_btn.setOnClickListener {
            when {
                Constants.checkEmptyEditText(tiet_ccp_phone) -> {
                    Constants.setErrorOnEmptyEditText(tiet_ccp_phone, "Phone")
                }
                Constants.checkInternetAvailability(it.context) -> {
                    authenticatePhone()
                }
                !Constants.checkInternetAvailability(it.context) -> {
                    Constants.showSnackBar(container_register, getString(R.string.text_no_internet))
                }
            }
        }

        ccp_verify_btn.setOnClickListener {
            if (Constants.checkEmptyEditText(tiet_verification_code)) {
                Constants.setErrorOnEmptyEditText(tiet_verification_code, "Code")
                tiet_verification_code.requestFocus()
            } else if (Constants.checkInternetAvailability(this)) {
                registerViewModel.code = tiet_verification_code.text.toString()

                hideSmsViews()
                progress_register.visibility = View.VISIBLE

                if (mVerificationId == null) {
                    mVerificationId.let {
                        val credential = PhoneAuthProvider.getCredential(it, registerViewModel.code)
                        addPhoneNumber(credential)
                    }
                } else {
                    Constants.showSnackBar(
                        container_register,
                        "Something went wrong, please try again later"
                    )
                }

            } else
                Constants.showSnackBar(container_register, getString(R.string.text_no_internet))
        }
    }

    fun authenticatePhone() {
        registerViewModel.phone =
            ccp_code.selectedCountryCodeWithPlus + tiet_ccp_phone.text.toString().trim()

        PhoneAuthProvider.getInstance()
            .verifyPhoneNumber(
                registerViewModel.phone,
                60,
                TimeUnit.SECONDS,
                this,
                phoneAuthCallback
            )

        hidePhoneViews()
        showSmsViews()
    }


    override fun onStart() {
        super.onStart()
    }

    private fun showSmsViews() {
        ccp_verify_btn.visibility = View.VISIBLE
        tiet_verification_code.visibility = View.VISIBLE
        til_verification_code.visibility = View.VISIBLE
        mtv_verification_code_guide.visibility= View.VISIBLE
    }

    private fun hidePhoneViews() {
        tiet_ccp_phone.visibility = View.GONE
        ccp_code.visibility = View.GONE
        ccp_next_btn.visibility = View.GONE
        til_ccp_phone.visibility = View.GONE
    }

    private fun hideSmsViews() {
        ccp_verify_btn.visibility = View.GONE
        tiet_verification_code.visibility = View.GONE
        til_verification_code.visibility = View.GONE
        mtv_verification_code_guide.visibility= View.GONE
    }

    fun displayToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }


    private val phoneAuthCallback =
        object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                p0?.let { phoneAuth ->
                    addPhoneNumber(phoneAuth)
                }
            }

            override fun onVerificationFailed(p0: FirebaseException) {
                p0.message?.let { displayToast(it) }

            }

            override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                super.onCodeSent(p0, p1)
                mVerificationId = p0.toString()
            }

        }

    private fun addPhoneNumber(phoneAuthCredential: PhoneAuthCredential) {
        firebaseAuth.signInWithCredential(phoneAuthCredential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    progress_register.visibility = View.GONE
                    startMain()
                } else {
                    task.exception?.message?.let { displayToast(it) }
                }
            }
    }

    private fun startMain() {
        //TODO How do you pass this dynamically, inorder to use as library
        startActivity(Intent("com.msafirismart.user.screens.MainActivity"))
    }


}
