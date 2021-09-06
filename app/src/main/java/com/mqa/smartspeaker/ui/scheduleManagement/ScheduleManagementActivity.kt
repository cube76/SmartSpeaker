package com.mqa.smartspeaker.ui.scheduleManagement

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mqa.smartspeaker.R
import com.mqa.smartspeaker.databinding.ActivityConnectSmartSpeakerBinding
import com.mqa.smartspeaker.databinding.ActivityScheduleManagementBinding
import com.mqa.smartspeaker.ui.connectSmartSpeaker.scanWifi.ScanWifiFragment
import com.mqa.smartspeaker.ui.scheduleManagement.calendar.CalendarFragment

class ScheduleManagementActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScheduleManagementBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScheduleManagementBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var mainFragment: CalendarFragment = CalendarFragment()
        supportFragmentManager.beginTransaction().add(R.id.container_schedule_management, mainFragment)
            .commit()
    }
}