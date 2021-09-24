package com.mqa.smartspeaker.ui.skill

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mqa.smartspeaker.R
import com.mqa.smartspeaker.ui.skill.alarmPersonal.AlarmPersonalActivity
import com.mqa.smartspeaker.ui.skill.scheduleManagement.ScheduleManagementActivity

class WhatSkillActivity : AppCompatActivity() {

    companion object{
        const val SKILL_ID = "skillId"
        const val SKILL_NAME = "skillName"
        const val SKILL_DESC = "skillDesc"
        const val SKILL_CATEGORY = "skillCategory"
        const val SKILL_IMAGE = "skillImage"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_what_skill)
        val skillName = intent.getStringExtra(SKILL_NAME).toString()

        when(skillName){
            "Pengelolaan Jadwal"->{
                val intent = Intent(this, ScheduleManagementActivity::class.java)
                startActivity(intent)
                finish()
            }
            "Alarm Personal"->{
                val intent = Intent(this, AlarmPersonalActivity::class.java)
                startActivity(intent)
                finish()
            }
            else-> finish()
        }
    }
}