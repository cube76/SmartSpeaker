package com.mqa.smartspeaker.ui.forgetPassword.inputEmail

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.mqa.smartspeaker.core.data.Resource
import com.mqa.smartspeaker.core.data.source.remote.request.RecoveryPasswordRequest
import com.mqa.smartspeaker.databinding.ActivityInputEmailBinding
import com.mqa.smartspeaker.ui.forgetPassword.ForgetPasswordActivity.Companion.EMAIL
import com.mqa.smartspeaker.ui.forgetPassword.inputCode.InputCodeActivity
import com.pixplicity.easyprefs.library.Prefs
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class InputEmailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInputEmailBinding
    private val inputEmailViewModel: InputEmailViewModel by viewModels()

    companion object {
        const val PASS = "change_pass"
        const val FORGET_PASS = 101
        const val CHANGE_PASS = 102
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnSendForget.setOnClickListener {
            val data =
                RecoveryPasswordRequest(binding.ETEmailForget.text.toString(),"","","")

            inputEmailViewModel.postForgetPassword(data)
            observeData()
        }

        binding.include3.btnBack.setOnClickListener {
            super.onBackPressed()
        }
    }


    private fun observeData() {
        with(binding) {
            inputEmailViewModel.postForgetPassword.observe(this@InputEmailActivity, { results ->
                Log.e("result0", results.message.toString())
                when (results) {
                    is Resource.Loading -> {
                        binding.PBInputEmail.bringToFront()
                        binding.PBInputEmail.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        binding.PBInputEmail.visibility = View.GONE

                        val result = results.data
                        if (result != null) {
                            next()
                        }
                    }
                    is Resource.Error -> {
                        binding.PBInputEmail.visibility = View.GONE
                        Log.e("error", "" + results.message.toString())
                        showError(results.message.toString())
                    }
                }

            })
        }

    }

    fun next(){
        val intent = Intent(this, InputCodeActivity::class.java)
        Prefs.putInt(PASS, FORGET_PASS)
        intent.putExtra(EMAIL, binding.ETEmailForget.text.toString())
        startActivity(intent)
    }

    private fun showError(errorMsg: String) {
        Log.e("last result", errorMsg)
        binding.TVErrorForgetEmail.visibility = View.VISIBLE
        binding.TVErrorForgetEmail.text = errorMsg
    }
}