package com.mqa.smartspeaker.ui.pairing.tuya

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mqa.smartspeaker.databinding.FragmentTuyaPairing2Binding
import com.mqa.smartspeaker.ui.pairing.PairingActivity

class TuyaPairing2Fragment : Fragment() {

    private var _binding: FragmentTuyaPairing2Binding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTuyaPairing2Binding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        (activity as PairingActivity).scanWifi()
        return binding.root
    }

}