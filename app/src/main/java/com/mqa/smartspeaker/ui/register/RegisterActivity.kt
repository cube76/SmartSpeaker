package com.mqa.smartspeaker.ui.register

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.mqa.smartspeaker.R
import com.mqa.smartspeaker.core.data.Resource
import com.mqa.smartspeaker.core.data.source.remote.response.RegisterRequest
import com.mqa.smartspeaker.core.data.source.remote.response.RegisterResponse
import com.mqa.smartspeaker.databinding.ActivityRegisterBinding
import com.mqa.smartspeaker.ui.emailVerification.EmailVerificationActivity
import com.mqa.smartspeaker.ui.intro.Intro2Activity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var job: RegisterResponse
    private val registerViewModel: RegisterViewModel by viewModels()
    var email: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener {
            if (!isOnline(applicationContext)) {
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
                val data =
                    RegisterRequest(firstName, lastName, email, password, passwordConfirmation)

                if (binding.CBTnc.isChecked) {
                    registerViewModel.postRegister(data)
                    observeData()
                } else {
                    binding.TVError.visibility = View.VISIBLE
                    binding.TVError.text = getString(R.string.TnC)
                }
            }
        }

    }

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
                            job = result
                            verification(job)
                        }
                    }
                    is Resource.Error -> {
                        binding.PBRegister.visibility = View.GONE
                        Log.e("error", "" + results.message.toString())
                        showError(results.message.toString())
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
        binding.TVError.visibility = View.VISIBLE
        binding.TVError.text = errorMsg
    }

    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            Log.i("Internet", "true")
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
    }
}