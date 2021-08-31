package com.mqa.smartspeaker.ui.alarmPersonal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.VISIBLE
import com.mqa.smartspeaker.R
import com.mqa.smartspeaker.databinding.ActivityAlarmPersonalBinding
import com.mqa.smartspeaker.databinding.ActivityPairingBinding
import com.mqa.smartspeaker.ui.alarmPersonal.chooseType.ChooseTypeAlarmPersonalFragment
import com.mqa.smartspeaker.ui.pairing.smartSpeaker.SmartSpeakerPairingFragment

class AlarmPersonalActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAlarmPersonalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlarmPersonalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragment: ChooseTypeAlarmPersonalFragment = ChooseTypeAlarmPersonalFragment()
        supportFragmentManager.beginTransaction().add(R.id.container_alarm_personal, fragment)
            .commit()

        binding.topBar.apply {
            IVIcon.visibility = VISIBLE
            IVBack.setOnClickListener {
                finish()
            }
            TVActionBar.text = "Alarm Personal"
        }
    }
}