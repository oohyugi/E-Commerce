package com.yogi.ecommerce.feature.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Log.wtf
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.GoogleApiClient
import com.google.gson.Gson
import com.yogi.ecommerce.MainActivity
import com.yogi.ecommerce.R
import com.yogi.ecommerce.core.helpers.PrefHelper
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.loading_state.*
import org.json.JSONObject
import org.koin.android.ext.android.inject
import java.util.*


class LoginActivity : AppCompatActivity() {

    var mGoogleApiClient: GoogleApiClient? = null
    var callbackManager: CallbackManager? = null
    var permissionNeeds = Arrays.asList("public_profile, email, user_birthday")
    private val RC_SIGN_IN = 11
    val prefHelper: PrefHelper by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        callbackManager = CallbackManager.Factory.create()


        btnSignin?.setOnClickListener {
            gotoMain()
        }
        btnFacebook?.setOnClickListener {
            loadingState?.visibility = View.VISIBLE
            LoginManager.getInstance().logInWithReadPermissions(this, permissionNeeds)
            LoginManager.getInstance().registerCallback(callbackManager, object :
                FacebookCallback<LoginResult> {
                override fun onSuccess(loginResult: LoginResult) {
                    loadingState?.visibility = View.GONE
                    facebookUser(loginResult.accessToken)

                }

                override fun onCancel() {
                    Log.e("cancel", "onCancel: ")
                    val token = AccessToken.getCurrentAccessToken()
                    if (token != null) {
                        facebookUser(token)
                    } else {

                    }
//                    Log.e("cancel", "onCancel: " + token!!.userId)

                }

                override fun onError(error: FacebookException) {
                    Log.e("cancel", "onError: " + error.message)

                }
            })
        }
        btnGoogle?.setOnClickListener {
            loadingState?.visibility = View.VISIBLE
            configurasiGoogleSign()
            val signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }
    }

    private fun gotoMain() {
        MainActivity.startThisActivity(this)
        finish()
    }

    private fun configurasiGoogleSign() {
        //CONFIGURASI GOOGLE LOGIN
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        mGoogleApiClient = GoogleApiClient.Builder(this)
            .enableAutoManage(this) { connectionResult ->
                wtf(
                    "onConnectionFailed: ",
                    connectionResult.errorMessage!!
                )
            }
            .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
            .build()

    }

    var userID: String? = null
    var birthday: String? = null
    lateinit var picture: String
    lateinit var name: String
    var email: String? = null

    private fun facebookUser(token: AccessToken?) {
        val request: GraphRequest = GraphRequest.newMeRequest(
            token,
            object : GraphRequest.GraphJSONObjectCallback {
                override fun onCompleted(`object`: JSONObject?, response: GraphResponse?) {
                    Log.e("LoginActivity", response.toString())

                    // Application code
                    try {

                        userID = `object`!!.getString("id")
//                            birthday = `object`.getString("birthday") // 01/31/1980 format
                        picture = "https://graph.facebook.com/$userID/picture?type=large"
                        name = `object`.getString("name")
                        email = `object`.getString("email")
                        prefHelper.saveUser(name)
                        gotoMain()

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                }
            })

        val parameters = Bundle()
        parameters.putString("fields", "id,name,email,gender,birthday,picture")
        request.parameters = parameters
        request.executeAsync()


    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager!!.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            try {
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                val account = task.getResult(ApiException::class.java)
                wtf("onActivityResult: ", Gson().toJson(account))
//                CompleteAccountActivity().startThisActivity(this,mUser)
//                finish()
                loadingState?.visibility = View.GONE
                prefHelper.saveUser(account?.displayName)
                gotoMain()

            } catch (e: Exception) {
                e.printStackTrace()
            }

        }


    }
}
