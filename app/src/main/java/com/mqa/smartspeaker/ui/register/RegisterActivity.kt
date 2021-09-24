package com.mqa.smartspeaker.ui.register

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.mqa.smartspeaker.R
import com.mqa.smartspeaker.core.data.Resource
import com.mqa.smartspeaker.core.data.source.remote.request.RegisterRequest
import com.mqa.smartspeaker.core.data.source.remote.response.RegisterResponse
import com.mqa.smartspeaker.core.utils.Internet
import com.mqa.smartspeaker.databinding.ActivityRegisterBinding
import com.mqa.smartspeaker.ui.emailVerification.EmailVerificationActivity
import com.pixplicity.easyprefs.library.Prefs
import com.tuya.smart.android.user.api.ILoginCallback
import com.tuya.smart.android.user.api.ILogoutCallback
import com.tuya.smart.android.user.bean.User
import com.tuya.smart.home.sdk.TuyaHomeSdk
import com.tuya.smart.home.sdk.bean.HomeBean
import com.tuya.smart.home.sdk.callback.ITuyaHomeResultCallback
import com.tuya.smart.sdk.api.IResultCallback
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var reg: RegisterResponse
    private val registerViewModel: RegisterViewModel by viewModels()
    var email: String = ""
    var homeId: String? = ""
    var currentDate = ""

    companion object {
        const val HOME_ID = "homeId"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val currentDateTime =
                    LocalDateTime.now()
                currentDate = currentDateTime.format(
                    DateTimeFormatter.ofPattern("yyyyMMddHHmmss")
                )
            }
            if (!Internet.isOnline(applicationContext)) {
                Toast.makeText(
                    applicationContext,
                    "Tidak terhubung ke internet",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val firstName = binding.TVFirstName.text.toString()
                val lastName = binding.TVLastName.text.toString()
                email = binding.TVEmail.text.toString()
                val password = binding.TVPassword.text.toString()
                val passwordConfirmation = binding.TVPasswordConfirmation.text.toString()

                if (binding.CBTnc.isChecked) {
                    binding.PBRegister.visibility = View.VISIBLE
                    TuyaHomeSdk.getUserInstance()
                        .loginWithEmail(
                            86.toString(),
                            "aqubayisi@yahoo.com",
                            "rahasiasaya",
                            object :
                                ILoginCallback {
                                override fun onSuccess(user: User?) {
                                    val homeName = currentDate + ('a'..'z').randomString(6)
                                    Log.e("homeName", currentDate)
                                    TuyaHomeSdk.getHomeManagerInstance().createHome(
                                        homeName,
                                        // Get location by yourself, here just sample as Shanghai's location
                                        120.52,
                                        30.40,
                                        "Indonesia",
                                        arrayListOf(),
                                        object : ITuyaHomeResultCallback {
                                            override fun onSuccess(bean: HomeBean?) {
                                                homeId = bean?.homeId.toString()
                                                Prefs.putString(HOME_ID, homeId)

                                                val data =
                                                    RegisterRequest(
                                                        firstName,
                                                        lastName,
                                                        email,
                                                        password,
                                                        passwordConfirmation,
                                                        Prefs.getString(HOME_ID, "")
                                                    )

                                                registerViewModel.postRegister(data)
                                                observeData()
                                            }

                                            override fun onError(
                                                errorCode: String?,
                                                errorMsg: String?
                                            ) {
                                                Toast.makeText(
                                                    this@RegisterActivity,
                                                    "Create Home error->$errorMsg",
                                                    Toast.LENGTH_LONG
                                                ).show()
                                                Log.e("gagal buat home", "")
                                            }

                                        }
                                    )
                                }

                                override fun onError(code: String?, error: String) {
                                    Toast.makeText(
                                        this@RegisterActivity,
                                        "login tuya error->$error",
                                        Toast.LENGTH_LONG
                                    ).show()
                                    showError(error)
                                }
                            })
                } else {
                    binding.TVError.visibility = View.VISIBLE
                    binding.TVError.text = getString(R.string.TnC)
                }
            }
        }

        binding.TVHaveAccount.setOnClickListener {
            super.onBackPressed()
        }

    }

    fun ClosedRange<Char>.randomString(length: Int) =
        (1..length)
            .map { (Random().nextInt(endInclusive.code - start.code) + start.code).toChar() }
            .joinToString("")

    private fun observeData() {
        with(binding) {
            registerViewModel.postRegister.observe(this@RegisterActivity, { results ->
                Log.e("result0", results.message.toString())
                when (results) {
                    is Resource.Loading -> binding.PBRegister.visibility = View.VISIBLE
                    is Resource.Success -> {
                        binding.PBRegister.visibility = View.GONE

                        val result = results.data
                        Log.e("result1", result?.message.toString())
                        if (result != null) {
                            reg = result
                            verification(reg)
                        }
                    }
                    is Resource.Error -> {
                        Log.e("contain", Prefs.contains(HOME_ID).toString())
                        if (Prefs.contains(HOME_ID)) {
                            TuyaHomeSdk.newHomeInstance(Prefs.getString(HOME_ID, "").toLong())
                                .dismissHome(object : IResultCallback {
                                    override fun onSuccess() {
                                        showError(results.message.toString())
                                    }

                                    override fun onError(code: String?, error: String) {
                                        Toast.makeText(
                                            this@RegisterActivity,
                                            "$error",
                                            Toast.LENGTH_LONG
                                        ).show()
                                        showError(error)
                                    }

                                })
                        } else {
                            showError(results.message.toString())
                        }
                    }
                }

            })
        }
    }

    private fun verification(result: RegisterResponse) {
        Log.e("last result", result.toString())
        val intent = Intent(this, EmailVerificationActivity::class.java)
        intent.putExtra("email", email)
        startActivity(intent)
    }

    private fun showError(errorMsg: String) {
        Log.e("last result", errorMsg)
        binding.PBRegister.visibility = View.GONE
        binding.TVError.visibility = View.VISIBLE
        binding.TVError.text = errorMsg
    }

}