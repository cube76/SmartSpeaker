package com.mqa.smartspeaker.ui.device

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import com.mqa.smartspeaker.R
import com.mqa.smartspeaker.databinding.FragmentDeviceBinding
import com.mqa.smartspeaker.ui.device.lamp.LampActivity


class DeviceFragment : Fragment() {
    private var _binding: FragmentDeviceBinding ? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDeviceBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment

        binding.RVDevice.setOnClickListener {
            val intent = Intent(activity, LampActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }

}