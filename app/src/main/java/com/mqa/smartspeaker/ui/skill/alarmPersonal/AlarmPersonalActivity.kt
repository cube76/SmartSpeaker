package com.mqa.smartspeaker.ui.skill.alarmPersonal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.VISIBLE
import com.mqa.smartspeaker.R
import com.mqa.smartspeaker.databinding.ActivityAlarmPersonalBinding
import com.mqa.smartspeaker.ui.skill.alarmPersonal.recordAlarm.RecordAlarmPersonalFragment

class AlarmPersonalActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAlarmPersonalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlarmPersonalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragment: RecordAlarmPersonalFragment = RecordAlarmPersonalFragment()
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