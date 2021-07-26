package com.mqa.smartspeaker.ui.login

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.mqa.smartspeaker.MainActivity
import com.mqa.smartspeaker.core.data.Resource
import com.mqa.smartspeaker.core.data.source.remote.response.LoginResponse
import com.mqa.smartspeaker.core.utils.Internet
import com.mqa.smartspeaker.databinding.ActivityLoginBinding
import com.mqa.smartspeaker.ui.forgetPassword.inputEmail.InputEmailActivity
import com.mqa.smartspeaker.ui.intro.Intro2Activity
import com.mqa.smartspeaker.ui.register.RegisterActivity
import com.pixplicity.easyprefs.library.Prefs
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()
    private lateinit var token: LoginResponse
    val TOKEN = "token"
    val FIRST_LAUNCH = "firstLaunch"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.e("first", ""+Prefs.getBoolean(FIRST_LAUNCH, true))
        Log.e("token", ""+Prefs.getString(TOKEN,""))
//        if (Prefs.contains(TOKEN)) {
//            if (Prefs.getBoolean(FIRST_LAUNCH, true)){
//                val intent = Intent(this, Intro2Activity::class.java)
//                startActivity(intent)
//            }else {
//                val intent = Intent(this, MainActivity::class.java)
//                startActivity(intent)
//            }
//        }

        binding.includeSheet.IVCloseSheet.setOnClickListener {
            Internet.toggle(false, binding.includeSheet.root, binding.parent)
        }
        binding.includeSheet.btnSettings.setOnClickListener{
            val intent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
            startActivity(intent)
        }
        binding.includeSheet.btnTryAgain.setOnClickListener{
            Internet.toggle(false, binding.includeSheet.root, binding.parent)
        }
        binding.btnLogin.setOnClickListener {
            if (!Internet.isOnline(applicationContext)) {
                Internet.toggle(true, binding.includeSheet.root, binding.parent)
            } else {
//                val email = binding.ETEmail.text.toString()
//                val password = binding.ETPassword.text.toString()
//
//                val data =
//                    LoginRequest(email, password)
//                loginViewModel.getLogin(data)
//                observeData()
                val intent = Intent(this, Intro2Activity::class.java)
                startActivity(intent)

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
                Log.e("result0", results.message.toString())
                when (results) {
                    is Resource.Loading -> {
                        binding.PBLogin.bringToFront()
                        binding.PBLogin.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        binding.PBLogin.visibility = View.GONE

                        val result = results.data
                        Log.e("result1", result?.token.toString())
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
        if (Prefs.getBoolean(FIRST_LAUNCH, true)){
            val intent = Intent(this, Intro2Activity::class.java)
            startActivity(intent)
        }else {
            Prefs.putBoolean(FIRST_LAUNCH, false)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showError(errorMsg: String) {
        Log.e("last result", errorMsg)
        binding.TVErrorLogin.visibility = View.VISIBLE
        binding.TVErrorLogin.text = errorMsg
    }

}