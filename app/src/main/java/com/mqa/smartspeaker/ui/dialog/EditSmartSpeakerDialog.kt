package com.mqa.smartspeaker.ui.dialog

import android.R
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.Window
import com.mqa.smartspeaker.databinding.DialogEditBinding

class EditSmartSpeakerDialog (context: Context) : Dialog(context) {
    private lateinit var binding: DialogEditBinding

    init {
        setCancelable(true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DialogEditBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(binding.root)

        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.btnCancel.setOnClickListener {
            cancel()
        }
    }
}