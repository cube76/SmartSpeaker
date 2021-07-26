package com.mqa.smartspeaker.ui.detailSmartSpeaker

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mqa.smartspeaker.R
import com.mqa.smartspeaker.databinding.ActivityDetailSmartSpeakerBinding
import com.mqa.smartspeaker.ui.emailVerification.SuccessDialog

class DetailSmartSpeakerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailSmartSpeakerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityDetailSmartSpeakerBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.editTextTextPersonName.setOnClickListener {
            EditSmartSpeakerDialog(this@DetailSmartSpeakerActivity).show()
        }

    }
}