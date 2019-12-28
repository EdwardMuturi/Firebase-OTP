package com.mementoguy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.abiria.firebase_auth.ui.RegisterActivity
import com.abiria.firebase_auth.utils.Constants
import com.mementoguy.firebasephoneauth.R

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler().postDelayed({
            startActivityAfterVerification()
        },500)
    }

    private fun startActivityAfterVerification() {
        val registerActivityIntent= Intent(this, RegisterActivity::class.java)
        registerActivityIntent.putExtra(Constants.EXTRA_ACTIVITY_NAME_TO_START, getString(R.string.activity_name_to_start))
        startActivity(registerActivityIntent)
        finish()
    }
}
