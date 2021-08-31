package com.mqa.smartspeaker.ui.account

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mqa.smartspeaker.R
import com.mqa.smartspeaker.databinding.FragmentAccountBinding
import com.mqa.smartspeaker.databinding.FragmentDeviceBinding
import com.pixplicity.easyprefs.library.Prefs

class AccountFragment : Fragment() {
    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAccountBinding.inflate(inflater, container, false)

         binding.btnLogout.setOnClickListener {
             Prefs.clear()
             activity?.finish()
         }

        return binding.root
    }

}