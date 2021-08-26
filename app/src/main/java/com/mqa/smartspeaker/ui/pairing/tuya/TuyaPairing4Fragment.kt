package com.mqa.smartspeaker.ui.pairing.tuya

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mqa.smartspeaker.R
import com.mqa.smartspeaker.databinding.FragmentTuyaPairing3Binding
import com.mqa.smartspeaker.databinding.FragmentTuyaPairing4Binding

class TuyaPairing4Fragment : Fragment() {
    private var _binding: FragmentTuyaPairing4Binding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTuyaPairing4Binding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        binding.btnBackHome.setOnClickListener {
            activity?.finish()
        }
        return binding.root
    }

}