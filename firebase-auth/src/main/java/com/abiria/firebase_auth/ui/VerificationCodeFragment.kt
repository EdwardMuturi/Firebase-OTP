package com.abiria.firebase_auth.ui

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.abiria.firebase_auth.BuildConfig

import com.abiria.firebase_auth.R
import com.abiria.firebase_auth.utils.Constants
import com.abiria.firebase_auth.viewmodel.RegisterViewModel
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.fragment_verification_code.*
import java.util.concurrent.TimeUnit

/**
 * A simple [Fragment] subclass.
 */
class VerificationCodeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var registerViewModel: RegisterViewModel
    private val firebaseAuth = FirebaseAuth.getInstance()
    lateinit var mVerificationId: String
    lateinit var resendingToken: PhoneAuthProvider.ForceResendingToken


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(requireContext())
        registerViewModel = ViewModelProvider(requireActivity()).get(RegisterViewModel::class.java)
        authenticatePhone()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_verification_code, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ccp_verify_btn.setOnClickListener {
            if (Constants.checkEmptyEditText(tiet_verification_code)) {
                Constants.setErrorOnEmptyEditText(tiet_verification_code, "Code")
                tiet_verification_code.requestFocus()
            } else if (Constants.checkInternetAvailability(requireContext())) {
                registerViewModel.code.value = tiet_verification_code.text.toString()
                tiet_verification_code.setText("")
                progress_register.visibility = View.VISIBLE
                verification_input_container.visibility= View.GONE

                if (mVerificationId != null) {
                    registerViewModel.code.observe(requireActivity(), Observer { code ->

                        mVerificationId.let { verficationId ->
                            val credential = PhoneAuthProvider.getCredential(verficationId, code)
                            addPhoneNumber(credential)
                        }
                    })
                } else {
//                    Constants.showSnackBar(
//                        view.container_register,
//                        "Something went wrong, please try again later"
//                    )
                    displayToast("Something went wrong, please try again later")
                }

            } else
//                Constants.showSnackBar(container_register, getString(R.string.text_no_internet))
                displayToast("Something went wrong, please try again later")
        }

        tv_resend_code.setOnClickListener {
            tiet_verification_code.setText("")
            toggleViewState(it, false)
            tv_resend_code.setTextColor(Color.parseColor("#eeeeee"))
            if (registerViewModel.phone.value != null)
                resendToken(registerViewModel.phone.value!!, resendingToken)
        }
    }

    private fun authenticatePhone() {
        registerViewModel.phone.observe(requireActivity(), Observer {
            PhoneAuthProvider.getInstance()
                .verifyPhoneNumber(it, 60, TimeUnit.SECONDS, requireActivity(), phoneAuthCallback)
        })
    }

    private val phoneAuthCallback =
        object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                addPhoneNumber(p0)
            }

            override fun onVerificationFailed(p0: FirebaseException) {
                p0.message?.let { message ->
                    displayToast(message)
                }

            }

            override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                super.onCodeSent(p0, p1)
                mVerificationId = p0
                resendingToken = p1
            }

            override fun onCodeAutoRetrievalTimeOut(p0: String) {
                super.onCodeAutoRetrievalTimeOut(p0)
                toggleViewState(tv_resend_code, true)
                displayToast(p0)
            }

        }

    fun toggleViewState(view: View, isEnabled: Boolean){
        view.isEnabled= isEnabled
    }

    private fun addPhoneNumber(phoneAuthCredential: PhoneAuthCredential) {
        firebaseAuth.signInWithCredential(phoneAuthCredential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    progress_register.visibility = View.GONE
                    startMain()
                } else {
                    progress_register.visibility = View.GONE
                    if (task.exception is FirebaseAuthInvalidCredentialsException)
                        task.exception?.message?.let { displayToast("Invalid Verification Code") }
                    else
                        task.exception?.message?.let { exceptionMessage ->
                            displayToast(
                                exceptionMessage
                            )
                        }
                }
            }
    }

    fun displayToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun startMain() {
        if (requireActivity().intent.extras != null) {
            val extraActivityName =
                requireActivity().intent.extras?.getString(Constants.EXTRA_ACTIVITY_NAME_TO_START)
            startActivity(Intent(extraActivityName))
        } else {
            if (BuildConfig.DEBUG)
                Toast.makeText(
                    requireContext(),
                    getString(R.string.error_acvitity_4040),
                    Toast.LENGTH_LONG
                ).show()
            else
                Log.e(
                    VerificationCodeFragment::class.java.simpleName,
                    getString(R.string.error_acvitity_4040)
                )
        }

    }

    fun resendToken(phone: String, token: PhoneAuthProvider.ForceResendingToken) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            phone,
            60,
            TimeUnit.SECONDS,
            requireActivity(),
            phoneAuthCallback,
            token
        )
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment VerificationCodeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            VerificationCodeFragment().apply {
            }
    }
}