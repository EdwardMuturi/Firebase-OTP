package com.abiria.firebase_auth.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider

import com.abiria.firebase_auth.R
import com.abiria.firebase_auth.utils.Constants
import com.abiria.firebase_auth.viewmodel.RegisterViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.fragment_phone_registration.*

/**
 * A simple [Fragment] subclass.
 */
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class PhoneRegistrationFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var registerViewModel: RegisterViewModel
    private val firebaseAuth = FirebaseAuth.getInstance()
    lateinit var mVerificationId: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        registerViewModel = ViewModelProvider(requireActivity()).get(RegisterViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_phone_registration, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ccp_next_btn.setOnClickListener {
            when {
                Constants.checkEmptyEditText(tiet_ccp_phone) -> {
                    Constants.setErrorOnEmptyEditText(tiet_ccp_phone, "Phone")
                }
                Constants.checkInternetAvailability(it.context) -> {
                    getUserPhone()
                }
                !Constants.checkInternetAvailability(it.context) -> {
                    Constants.showSnackBar(container_register, getString(R.string.text_no_internet))
                }
            }
        }
    }

    private fun getUserPhone() {
        registerViewModel.phone.value = ccp_code.selectedCountryCodeWithPlus + tiet_ccp_phone.text.toString().trim()
        openFragment(VerificationCodeFragment.newInstance())

    }

    private fun openFragment(fragment: Fragment) {
        requireActivity().supportFragmentManager.commit {
            replace(R.id.container_register, fragment)
        }
    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PhoneRegistrationFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            PhoneRegistrationFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
