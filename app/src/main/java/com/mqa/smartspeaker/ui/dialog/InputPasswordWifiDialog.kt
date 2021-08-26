package com.mqa.smartspeaker.ui.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.EditText
import androidx.annotation.Nullable
import androidx.fragment.app.DialogFragment
import com.mqa.smartspeaker.R
import com.mqa.smartspeaker.databinding.DialogPasswordBinding
import com.mqa.smartspeaker.databinding.FragmentTuyaPairing1Binding
import com.mqa.smartspeaker.ui.pairing.PairingActivity
import com.mqa.smartspeaker.ui.pairing.tuya.TuyaPairing2Fragment
import com.mqa.smartspeaker.ui.pairing.tuya.TuyaPairing3Fragment
import com.pixplicity.easyprefs.library.Prefs


class InputPasswordWifiDialog  : DialogFragment() {
    private lateinit var _binding: DialogPasswordBinding
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, @Nullable savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ETPassword.requestFocus()
        binding.TVDialogTitle.text = Prefs.getString("ssid", "")
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.btnSave.setOnClickListener {
            Prefs.putString("password_wifi", binding.ETPassword.text.toString())
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainerPairing, TuyaPairing3Fragment())
            transaction.disallowAddToBackStack()
            transaction.commit()
            dialog?.cancel()
            (activity as PairingActivity).removeWifi()
        }

        binding.btnCancel.setOnClickListener {
            dialog?.cancel()
        }
        dialog!!.window!!.setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE
        )
    }
}