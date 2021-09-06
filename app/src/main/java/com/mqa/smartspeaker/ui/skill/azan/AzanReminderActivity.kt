package com.mqa.smartspeaker.ui.skill.azan

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.mqa.smartspeaker.R
import com.mqa.smartspeaker.databinding.ActivityAlarmPersonalBinding
import com.mqa.smartspeaker.databinding.ActivityAzanReminderBinding

class AzanReminderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAzanReminderBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAzanReminderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.topBar.apply {
            IVIcon.visibility = View.VISIBLE
            IVBack.setOnClickListener {
                finish()
            }
            TVActionBar.text = getString(R.string.pengingat_azan)
        }

    }
}