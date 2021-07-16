package com.mqa.smartspeaker.ui.forgetPassword

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Window
import com.mqa.smartspeaker.databinding.DialogSuccessForgetPasswordBinding
import com.mqa.smartspeaker.ui.login.LoginActivity

class SuccessDialogForgetPass (context: Context) : Dialog(context) {
    private lateinit var binding: DialogSuccessForgetPasswordBinding

    init {
        setCancelable(false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DialogSuccessForgetPasswordBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            val i = Intent(context, LoginActivity::class.java)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            context.startActivity(i)
        }
    }

}