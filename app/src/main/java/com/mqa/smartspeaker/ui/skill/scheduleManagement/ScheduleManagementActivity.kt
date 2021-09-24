package com.mqa.smartspeaker.ui.skill.scheduleManagement

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import com.mqa.smartspeaker.R
import com.mqa.smartspeaker.core.data.Resource
import com.mqa.smartspeaker.core.data.source.remote.request.SkillInfoState
import com.mqa.smartspeaker.core.data.source.remote.response.Skills
import com.mqa.smartspeaker.databinding.ActivityScheduleManagementBinding
import com.mqa.smartspeaker.ui.dialog.SkillInfoDialog
import com.mqa.smartspeaker.ui.dialog.SkillInfoDialogViewModel
import com.mqa.smartspeaker.ui.login.LoginActivity
import com.mqa.smartspeaker.ui.skill.WhatSkillActivity
import com.mqa.smartspeaker.ui.skill.scheduleManagement.calendar.CalendarFragment
import com.pixplicity.easyprefs.library.Prefs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScheduleManagementActivity : AppCompatActivity() {

    private val scheduleMagamenetViewModel: ScheduleMagamenetViewModel by viewModels()
    val dialog = SkillInfoDialog()

    private lateinit var binding: ActivityScheduleManagementBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScheduleManagementBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.topBar.IVIcon.apply {
            visibility = VISIBLE
            setOnClickListener {
                dialog.show(this@ScheduleManagementActivity.supportFragmentManager, "dialog")
            }
        }

        scheduleMagamenetViewModel.getSkillInfoState("Bearer " + Prefs.getString(LoginActivity.TOKEN, ""), SkillInfoState(Prefs.getInt(
            WhatSkillActivity.SKILL_ID, 0))
        )
        observeData()

        var mainFragment: CalendarFragment = CalendarFragment()
        supportFragmentManager.beginTransaction().add(R.id.container_schedule_management, mainFragment)
            .commit()
    }

    private fun observeData() {
        with(binding) {
            scheduleMagamenetViewModel.getSkillInfoState.observe(this@ScheduleManagementActivity, { results ->
                Log.e("result0", results.toString())
                when (results) {
                    is Resource.Loading -> {
                        binding.PBScheduleManagement.visibility = VISIBLE
                    }
                    is Resource.Success -> {
                        val data = results.data
                        Log.e("results", "" + data?.show)
                        Prefs.putBoolean("skillInfo", data?.show!!)
                        if (data!!.show){
                            dialog.show(this@ScheduleManagementActivity.supportFragmentManager, "dialog")
                        }
                        binding.PBScheduleManagement.visibility = GONE
                    }
                    is Resource.Error -> {
                        binding.PBScheduleManagement.visibility = GONE
                        Log.e("error", "" + results.message.toString())
//                        showError(results.message.toString())
                    }
                }
            })
        }
    }
}