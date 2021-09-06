package com.mqa.smartspeaker.ui.login

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.mqa.smartspeaker.MainActivity
import com.mqa.smartspeaker.core.data.Resource
import com.mqa.smartspeaker.core.data.source.remote.request.LoginRequest
import com.mqa.smartspeaker.core.data.source.remote.response.LoginResponse
import com.mqa.smartspeaker.core.utils.Internet
import com.mqa.smartspeaker.core.utils.SheetDialog
import com.mqa.smartspeaker.databinding.ActivityLoginBinding
import com.mqa.smartspeaker.ui.forgetPassword.inputEmail.InputEmailActivity
import com.mqa.smartspeaker.ui.intro.Intro2Activity
import com.mqa.smartspeaker.ui.register.RegisterActivity
import com.mqa.smartspeaker.ui.register.RegisterActivity.Companion.HOME_ID
import com.pixplicity.easyprefs.library.Prefs
import com.tuya.smart.android.user.api.ILoginCallback
import com.tuya.smart.android.user.bean.User
import com.tuya.smart.home.sdk.TuyaHomeSdk
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()
    private lateinit var token: LoginResponse

    companion object {
        const val TOKEN = "token"
        const val FIRST_LAUNCH = "firstLaunch"
        const val EMAIL = "email"
        const val FIRST_NAME = "firstName"
        const val LAST_NAME = "lastName"
        const val PROFILE_IMAGE = "profileImage"
        const val USER_ID = "userId"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.e("homeidLogin", Prefs.getString(HOME_ID, ""))
        Log.e("first", "" + Prefs.getBoolean(FIRST_LAUNCH, true))
        Log.e("token", "" + Prefs.getString(TOKEN, ""))
        if (Prefs.contains(TOKEN)) {
            if (Prefs.getBoolean(FIRST_LAUNCH, true)) {
                Prefs.putBoolean(FIRST_LAUNCH, false)
                val intent = Intent(this, Intro2Activity::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }

        binding.includeSheet.IVCloseSheet.setOnClickListener {
            SheetDialog.toggle(false, binding.includeSheet.root, binding.parent)
        }
        binding.includeSheet.btnSettings.setOnClickListener {
            val intent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
            startActivity(intent)
        }
        binding.includeSheet.btnTryAgain.setOnClickListener {
            SheetDialog.toggle(false, binding.includeSheet.root, binding.parent)
        }
        binding.btnLogin.setOnClickListener {
            if (!Internet.isOnline(applicationContext)) {
                SheetDialog.toggle(true, binding.includeSheet.root, binding.parent)
            } else {
                val email = binding.ETEmail.text.toString()
                val password = binding.ETPassword.text.toString()

                val data =
                    LoginRequest(email, password)
                loginViewModel.getLogin(data)

                observeData()
//                val intent = Intent(this, Intro2Activity::class.java)
//                startActivity(intent)

            }
        }
        binding.TVNoAccount.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
        binding.TVForgetPassword.setOnClickListener {
            val intent = Intent(this, InputEmailActivity::class.java)
            startActivity(intent)
        }

    }

    private fun observeData() {
        with(binding) {
            loginViewModel.getLogin.observe(this@LoginActivity, { results ->
                when (results) {
                    is Resource.Loading -> {
                        binding.PBLogin.bringToFront()
                        binding.PBLogin.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {

                        val result = results.data
                        if (result != null) {
                            token = result
                            verification(token)
                        }
                    }
                    is Resource.Error -> {
                        binding.PBLogin.visibility = View.GONE
                        Log.e("error", "" + results.message.toString())
                        showError(results.message.toString())
                    }
                }
            })
        }
    }

    private fun verification(result: LoginResponse) {
        Prefs.putString(TOKEN, result.token)

        TuyaHomeSdk.getUserInstance()
            .loginWithEmail(
                86.toString(),
                "aqubayisi@yahoo.com",
                "rahasiasaya",
                object :
                    ILoginCallback {
                    override fun onSuccess(user: User?) {
                        loginViewModel.getUser("Bearer "+ result.token)
                        observeDataUser()
                    }

                    override fun onError(code: String?, error: String?) {
                        binding.PBLogin.visibility = View.GONE
                        Toast.makeText(
                            this@LoginActivity,
                            "login tuya error->$error",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                })

    }

    private fun showError(errorMsg: String) {
        Log.e("last result", errorMsg)
        binding.TVErrorLogin.visibility = View.VISIBLE
        binding.TVErrorLogin.text = errorMsg
    }

    private fun observeDataUser() {
        with(binding) {
            loginViewModel.getUser.observe(this@LoginActivity, { results ->
                Log.e("result0", results.message.toString())
                when (results) {
                    is Resource.Loading -> {
                        binding.PBLogin.bringToFront()
                        binding.PBLogin.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        binding.PBLogin.visibility = View.GONE

                        val result = results.data
                        Log.e("result1", result?.user.toString())
                        Prefs.putString(HOME_ID, result?.user?.homeId)
                        Prefs.putInt(USER_ID, result?.user?.id!!)
                        if (Prefs.getBoolean(FIRST_LAUNCH, false)) {
                            val intent = Intent(this@LoginActivity, Intro2Activity::class.java)
                            startActivity(intent)
                        } else {
                            Prefs.putBoolean(FIRST_LAUNCH, false)
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            startActivity(intent)
                        }
                    }
                    is Resource.Error -> {
                        binding.PBLogin.visibility = View.GONE
                        Log.e("error", "" + results.message.toString())
                        showError(results.message.toString())
                    }
                }
            })
        }
    }

}