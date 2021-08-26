package com.mqa.smartspeaker.ui.emailVerification

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
import com.mqa.smartspeaker.databinding.ActivityEmailVerificationBinding
import com.mqa.smartspeaker.ui.dialog.SuccessDialogVerifyEmail
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EmailVerificationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEmailVerificationBinding
    val emailVerificationViewModel: EmailVerificationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityEmailVerificationBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val email:String = intent.getStringExtra("email").toString()
        val otpView = binding.otpView

        object : CountDownTimer(60000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                val timeResult =
                    "${(millisUntilFinished / 1000 / 60).toString().padStart(2, '0')}:" +
                            "${(millisUntilFinished / 1000 % 60).toString().padStart(2, '0')} "
                binding.TVCountDown.text = timeResult
            }

            override fun onFinish() {
                binding.TVResend.setTextColor(
                    ContextCompat.getColor(
                        applicationContext,
                        R.color.yellow
                    )
                )
            }
        }.start()

        otpView.setAnimationEnable(true)
        otpView.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                Log.e("before count",""+count)
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Log.e("email",""+email)
                if (start == 3){
                    emailVerificationViewModel.getVerifyEmail(email,otpView.text.toString().toInt())
                    observeData()
                }
            }

            override fun afterTextChanged(s: Editable?) {
                Log.e("Editable",""+s)
            }
        })

    }

    private fun observeData() {
        with(binding) {
            emailVerificationViewModel.getVerifyEmail.observe(this@EmailVerificationActivity, { results ->
                Log.e("result0", results.message.toString())
                when (results) {
                    is Resource.Loading -> binding.PBVerifyEmail.visibility = View.VISIBLE
                    is Resource.Success -> {
                        binding.PBVerifyEmail.visibility = View.GONE

                        val result = results.data
                        Log.e("result1", result?.message.toString())
                        if (result != null) {
                            SuccessDialogVerifyEmail(this@EmailVerificationActivity).show()
                        }
                    }
                    is Resource.Error -> {
                        binding.PBVerifyEmail.visibility = View.GONE
                        Log.e("error", "" + results.message.toString())
                        showError(results.message.toString())
                    }
                }

            })
        }
    }

    private fun showError(errorMsg: String) {
        Log.e("last result", errorMsg)
        binding.TVErrorVerifyEmail.visibility = View.VISIBLE
        binding.TVErrorVerifyEmail.text = errorMsg
    }
}