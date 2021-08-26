package com.mqa.smartspeaker.ui.detailSmartSpeaker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mqa.smartspeaker.databinding.ActivityDetailSmartSpeakerBinding
import com.mqa.smartspeaker.ui.dialog.EditSmartSpeakerDialog

class DetailSmartSpeakerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailSmartSpeakerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityDetailSmartSpeakerBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.ETRenameSmartSpeaker.setOnClickListener {
            EditSmartSpeakerDialog(this@DetailSmartSpeakerActivity).show()
        }

    }
}