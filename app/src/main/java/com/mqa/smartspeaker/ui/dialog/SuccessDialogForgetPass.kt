package com.mqa.smartspeaker.ui.dialog

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import com.mqa.smartspeaker.R
import com.mqa.smartspeaker.databinding.DialogSuccessBinding
import com.mqa.smartspeaker.ui.login.LoginActivity
import com.tuya.smart.android.base.utils.PreferencesUtil.getString

class SuccessDialogForgetPass (context: Context) : Dialog(context) {
    private lateinit var binding: DialogSuccessBinding

    init {
        setCancelable(false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DialogSuccessBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        binding.TVTitleDialog.text = context.getString(R.string.berhasil)
        binding.TVSuccessDialog.text = context.getString(R.string.pass_telah_diperbarui)

        binding.btnBack.setOnClickListener {
            val i = Intent(context, LoginActivity::class.java)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            context.startActivity(i)
        }
    }

}