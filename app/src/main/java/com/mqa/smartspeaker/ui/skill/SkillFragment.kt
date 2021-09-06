package com.mqa.smartspeaker.ui.skill

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mqa.smartspeaker.MainActivity
import com.mqa.smartspeaker.R
import com.mqa.smartspeaker.databinding.FragmentSkillBinding
import com.mqa.smartspeaker.databinding.FragmentWarmBinding
import com.mqa.smartspeaker.ui.scheduleManagement.ScheduleManagementActivity

class SkillFragment : Fragment() {

    private var _binding: FragmentSkillBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSkillBinding.inflate(inflater, container, false)

        binding.pengelolaanJadwal.setOnClickListener{
            val intent = Intent(activity, ScheduleManagementActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }

}