package com.mqa.smartspeaker.ui.forgetPassword.renewPassword

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.mqa.smartspeaker.core.data.Resource
import com.mqa.smartspeaker.core.data.source.remote.request.RecoveryPasswordRequest
import com.mqa.smartspeaker.databinding.ActivityRenewPasswordBinding
import com.mqa.smartspeaker.ui.forgetPassword.ForgetPasswordActivity.Companion.CODE
import com.mqa.smartspeaker.ui.forgetPassword.ForgetPasswordActivity.Companion.EMAIL
import com.mqa.smartspeaker.ui.dialog.SuccessDialogForgetPass
import com.mqa.smartspeaker.ui.forgetPassword.inputEmail.InputEmailActivity
import com.pixplicity.easyprefs.library.Prefs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RenewPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRenewPasswordBinding
    private val renewPasswordViewModel: RenewPasswordViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRenewPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val from = Prefs.getInt(InputEmailActivity.PASS, 0)

        if (from == InputEmailActivity.CHANGE_PASS) {
            binding.include5.TVTitle.text == "Ubah kata sandi"
            binding.btnChangeForget.text == "Perbarui Kata Sandi"
        }

        binding.btnChangeForget.setOnClickListener {
            val email = intent.getStringExtra(EMAIL).toString()
            val code = intent.getStringExtra(CODE).toString()
            val data =
                RecoveryPasswordRequest(
                    email,
                    code,
                    binding.ETRenewPassword.text.toString(),
                    binding.ETRenewPasswordConfirmation.text.toString()
                )
            renewPasswordViewModel.postRecoveryPassword(data)
            observeData()
        }

        binding.include5.btnBack.setOnClickListener {
            super.onBackPressed()
        }

    }

    private fun observeData() {
        with(binding) {
            renewPasswordViewModel.postRecoveryPassword.observe(
                this@RenewPasswordActivity,
                { results ->
                    Log.e("result0", results.message.toString())
                    when (results) {
                        is Resource.Loading -> {
                            binding.PBRenewPassword.bringToFront()
                            binding.PBRenewPassword.visibility = View.VISIBLE
                        }
                        is Resource.Success -> {
                            binding.PBRenewPassword.visibility = View.GONE

                            val result = results.data
                            if (result != null) {
                                SuccessDialogForgetPass(this@RenewPasswordActivity).show()
                            }
                        }
                        is Resource.Error -> {
                            binding.PBRenewPassword.visibility = View.GONE
                            Log.e("error", "" + results.message.toString())
                            showError(results.message.toString())
                        }
                    }

                })
        }
    }

    private fun showError(errorMsg: String) {
        Log.e("last result", errorMsg)
        binding.TVErrorForgetEmail.visibility = View.VISIBLE
        binding.TVErrorForgetEmail.text = errorMsg
    }
}