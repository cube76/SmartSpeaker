package com.mqa.smartspeaker.ui.skill.alarmPersonal.recordAlarm

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mqa.smartspeaker.R
import com.mqa.smartspeaker.databinding.FragmentAlarmPersonalRecordBinding


class RecordAlarmPersonalFragment : Fragment() {
    private var _binding: FragmentAlarmPersonalRecordBinding? = null
    private val binding get() = _binding!!
    lateinit var recordAnimation:AnimationDrawable
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAlarmPersonalRecordBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment

        val record = binding.IVRecording

        record.setOnTouchListener { v, event ->
            when(event.action){
                MotionEvent.ACTION_DOWN -> {
                    record.setImageResource(R.drawable.mic_active_1)
                    record.setBackgroundResource(R.drawable.record_animation)
                    recordAnimation = record.background as AnimationDrawable
                    recordAnimation.start()
                    true
                }
                MotionEvent.ACTION_UP -> {
                    record.setImageResource(R.drawable.mic_inactive)
                    record.setBackgroundResource(R.drawable.mic_inactive)
                    binding.btnRecordFinish.visibility = VISIBLE
                    binding.btnRerecord.visibility = VISIBLE
                    true
                }
                else -> false
            }
        }

//        record.setOnClickListener {
//            record.setImageResource(R.drawable.mic_active_1)
//            record.setBackgroundResource(R.drawable.record_animation)
//            recordAnimation = record.background as AnimationDrawable
//            recordAnimation.start()
//        }

        return binding.root
    }

}