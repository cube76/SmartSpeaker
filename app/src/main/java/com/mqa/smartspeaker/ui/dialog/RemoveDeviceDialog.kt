package com.mqa.smartspeaker.ui.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.annotation.Nullable
import androidx.fragment.app.DialogFragment
import com.mqa.smartspeaker.R
import com.mqa.smartspeaker.databinding.DialogRemoveBinding
import com.mqa.smartspeaker.ui.device.lamp.LampActivity
import com.pixplicity.easyprefs.library.Prefs
import com.tuya.smart.home.sdk.TuyaHomeSdk
import com.tuya.smart.sdk.api.IResultCallback
import com.tuya.smart.sdk.api.ITuyaDevice


class RemoveDeviceDialog: DialogFragment() {
    private lateinit var _binding: DialogRemoveBinding
    private val binding get() = _binding!!
    var deviceId: String = Prefs.getString(LampActivity.DEVICE_ID, "")
    val mDevice: ITuyaDevice = TuyaHomeSdk.newDeviceInstance(deviceId)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogRemoveBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, @Nullable savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.TVTitle.text = getString(R.string.hapus_lamp)

        binding.btnYes.setOnClickListener {
            mDevice.removeDevice(object : IResultCallback {
                override fun onError(errorCode: String, errorMsg: String) {}
                override fun onSuccess() {}
            })
            SuccessRemoveDeviceDialog(requireContext()).show()
            dialog?.cancel()
        }

        binding.btnNo.setOnClickListener {
            dialog?.cancel()
        }

        dialog!!.window!!.setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE
        )
    }

}
