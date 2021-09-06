package com.mqa.smartspeaker.ui.changePassword

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import com.mqa.smartspeaker.core.data.Resource
import com.mqa.smartspeaker.core.data.source.remote.request.RecoveryPasswordRequest
import com.mqa.smartspeaker.databinding.ActivityChangePasswordBinding
import com.mqa.smartspeaker.databinding.ActivityLampBinding
import com.mqa.smartspeaker.ui.alarmPersonal.AlarmPersonalActivity
import com.mqa.smartspeaker.ui.forgetPassword.ForgetPasswordActivity
import com.mqa.smartspeaker.ui.forgetPassword.inputCode.InputCodeActivity
import com.mqa.smartspeaker.ui.forgetPassword.inputEmail.InputEmailActivity
import com.mqa.smartspeaker.ui.forgetPassword.inputEmail.InputEmailActivity.Companion.CHANGE_PASS
import com.mqa.smartspeaker.ui.forgetPassword.inputEmail.InputEmailViewModel
import com.mqa.smartspeaker.ui.login.LoginActivity.Companion.EMAIL
import com.pixplicity.easyprefs.library.Prefs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangePasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChangePasswordBinding
    private val inputEmailViewModel: InputEmailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnEmail.setOnClickListener {
            val data =
                RecoveryPasswordRequest(Prefs.getString(EMAIL, ""),"","","")

            inputEmailViewModel.postForgetPassword(data)
            observeData()
        }
    }

    private fun observeData() {
        with(binding) {
            inputEmailViewModel.postForgetPassword.observe( this@ChangePasswordActivity, { results ->
                Log.e("result0", results.message.toString())
                when (results) {
                    is Resource.Loading -> {
                        binding.PBChangePass.bringToFront()
                        binding.PBChangePass.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        binding.PBChangePass.visibility = View.GONE

                        val result = results.data
                        if (result != null) {
                            next()
                        }
                    }
                    is Resource.Error -> {
                        binding.PBChangePass.visibility = View.GONE
                        Log.e("error", "" + results.message.toString())
                        showError(results.message.toString())
                    }
                }

            })
        }

    }

    fun next(){
        Prefs.putInt(InputEmailActivity.PASS, CHANGE_PASS)
        val intent = Intent(this, InputCodeActivity::class.java)
        intent.putExtra(ForgetPasswordActivity.EMAIL, Prefs.getString(EMAIL, ""))
        startActivity(intent)
    }

    private fun showError(errorMsg: String) {
        Log.e("last result", errorMsg)
//        binding.TVErrorForgetEmail.visibility = View.VISIBLE
//        binding.TVErrorForgetEmail.text = errorMsg
    }
}