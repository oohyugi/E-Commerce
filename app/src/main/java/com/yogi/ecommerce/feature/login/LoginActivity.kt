package com.yogi.ecommerce.feature.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yogi.ecommerce.MainActivity
import com.yogi.ecommerce.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnSignin?.setOnClickListener {
            MainActivity.startThisActivity(this)
        }
        btnFacebook?.setOnClickListener {
            MainActivity.startThisActivity(this)
        }
        btnGoogle?.setOnClickListener {
            MainActivity.startThisActivity(this)
        }
    }
}
