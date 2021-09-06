package com.mqa.smartspeaker.ui.forgetPassword.inputCode

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.mqa.smartspeaker.R
import com.mqa.smartspeaker.core.data.Resource
import com.mqa.smartspeaker.core.data.source.remote.request.RecoveryPasswordRequest
import com.mqa.smartspeaker.databinding.ActivityInputCodeBinding
import com.mqa.smartspeaker.ui.forgetPassword.ForgetPasswordActivity.Companion.CODE
import com.mqa.smartspeaker.ui.forgetPassword.ForgetPasswordActivity.Companion.EMAIL
import com.mqa.smartspeaker.ui.forgetPassword.inputEmail.InputEmailActivity.Companion.CHANGE_PASS
import com.mqa.smartspeaker.ui.forgetPassword.inputEmail.InputEmailActivity.Companion.FORGET_PASS
import com.mqa.smartspeaker.ui.forgetPassword.inputEmail.InputEmailActivity.Companion.PASS
import com.mqa.smartspeaker.ui.forgetPassword.renewPassword.RenewPasswordActivity
import com.pixplicity.easyprefs.library.Prefs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InputCodeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInputCodeBinding
    val inputCodeViewModel: InputCodeViewModel by viewModels()
    var email = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputCodeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.TVResend.isClickable = false
        email = intent.getStringExtra(EMAIL).toString()
        val otpView = binding.otpViewForget
        val from = Prefs.getInt(PASS, 0)

        if (from == CHANGE_PASS){
            binding.include4.TVTitle.text == "Ubah kata sandi"
        }

        object : CountDownTimer(60000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                val timeResult =
                    "${(millisUntilFinished / 1000 / 60).toString().padStart(2, '0')}:" +
                            "${(millisUntilFinished / 1000 % 60).toString().padStart(2, '0')} "
                binding.TVCountDownForget.text = timeResult
            }

            override fun onFinish() {
                binding.TVResend.setTextColor(
                    ContextCompat.getColor(
                        this@InputCodeActivity,
                        R.color.yellow
                    )
                )
                binding.TVResend.isClickable = true
            }
        }.start()

        otpView.setAnimationEnable(true)
        otpView.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                Log.e("before count",""+count)
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (start == 3){
                    var code = otpView.text.toString()
                    val data =
                        RecoveryPasswordRequest(email,code,"","")
                    inputCodeViewModel.postCheckForgetPasswordCode(data)
                    observeData()
                }
            }

            override fun afterTextChanged(s: Editable?) {
                Log.e("Editable",""+s)
            }
        })

        binding.TVResend.setOnClickListener {
            val data =
                RecoveryPasswordRequest(email,"","","")
            inputCodeViewModel.postForgetPassword(data)
            observeDataResendEmail()
        }

    }

    private fun observeData() {
        with(binding) {
            inputCodeViewModel.postCheckForgetPasswordCode.observe(this@InputCodeActivity, { results ->
                Log.e("result0", results.message.toString())
                when (results) {
                    is Resource.Loading -> {
                        binding.PBInputCode.bringToFront()
                        binding.PBInputCode.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        binding.PBInputCode.visibility = View.GONE

                        val result = results.data
                        if (result != null) {
                            next()
                        }
                    }
                    is Resource.Error -> {
                        binding.PBInputCode.visibility = View.GONE
                        Log.e("error", "" + results.message.toString())
                        showError(results.message.toString())
                    }
                }

            })
        }

        binding.include4.btnBack.setOnClickListener {
            super.onBackPressed()
        }

    }

    private fun observeDataResendEmail() {
        with(binding) {
            inputCodeViewModel.postForgetPassword.observe(this@InputCodeActivity, { results ->
                Log.e("result0", results.message.toString())
                when (results) {
                    is Resource.Loading -> {
                        binding.PBInputCode.bringToFront()
                        binding.PBInputCode.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        binding.PBInputCode.visibility = View.GONE

                        val result = results.data
                        if (result != null) {
                            binding.TVErrorForgetCode.visibility = View.VISIBLE
                            binding.TVErrorForgetCode.text = result.message
                        }
                    }
                    is Resource.Error -> {
                        binding.PBInputCode.visibility = View.GONE
                        Log.e("error", "" + results.message.toString())
                        showError(results.message.toString())
                    }
                }

            })
        }

    }

    fun next(){
        val intent = Intent(this, RenewPasswordActivity::class.java)
        intent.putExtra(EMAIL, email)
        intent.putExtra(CODE, binding.otpViewForget.text.toString())
        startActivity(intent)
    }

    private fun showError(errorMsg: String) {
        Log.e("last result", errorMsg)
        binding.TVErrorForgetCode.visibility = View.VISIBLE
        binding.TVErrorForgetCode.text = errorMsg
    }

}