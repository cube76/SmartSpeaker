package com.mqa.smartspeaker.ui.pairing.tuya

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.mqa.smartspeaker.R
import com.mqa.smartspeaker.databinding.FragmentTuyaPairing1Binding
import com.mqa.smartspeaker.databinding.FragmentTuyaPairing3Binding
import com.mqa.smartspeaker.ui.dialog.FailedDialogTuyaPairing
import com.mqa.smartspeaker.ui.register.RegisterActivity.Companion.HOME_ID
import com.pixplicity.easyprefs.library.Prefs
import com.tuya.smart.home.sdk.TuyaHomeSdk
import com.tuya.smart.home.sdk.builder.ActivatorBuilder
import com.tuya.smart.sdk.api.ITuyaActivatorGetToken
import com.tuya.smart.sdk.api.ITuyaSmartActivatorListener
import com.tuya.smart.sdk.bean.DeviceBean
import com.tuya.smart.sdk.enums.ActivatorModelEnum

class TuyaPairing3Fragment : Fragment() {
    companion object {
        const val TAG = "DeviceConfigEZ"
        const val SUCCESS_LAMP = "success_lamp"
    }
    private var _binding: FragmentTuyaPairing3Binding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTuyaPairing3Binding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        TuyaHomeSdk.getActivatorInstance().getActivatorToken(Prefs.getString(HOME_ID, "").toLong(),
            object : ITuyaActivatorGetToken {
                override fun onSuccess(token: String) {
                    // Start network configuration -- EZ mode
                    val builder = ActivatorBuilder()
                        .setSsid(Prefs.getString("ssid", ""))
                        .setContext(activity)
                        .setPassword(Prefs.getString("password_wifi", ""))
                        .setActivatorModel(ActivatorModelEnum.TY_EZ)
                        .setTimeOut(100)
                        .setToken(token)
                        .setListener(object : ITuyaSmartActivatorListener {

                            @Override
                            override fun onStep(step: String?, data: Any?) {
                                Log.i("step menghubungkan", "$step --> $data")
                            }

                            override fun onActiveSuccess(devResp: DeviceBean?) {

                                Log.i(TAG, "Activate success")
                                Toast.makeText(
                                    requireContext(),
                                    "Activate success",
                                    Toast.LENGTH_LONG
                                ).show()

                                Prefs.remove("ssid")
                                Prefs.remove("password_wifi")
                                Prefs.putBoolean(SUCCESS_LAMP, true)

                                val transaction = requireActivity().supportFragmentManager.beginTransaction()
                                transaction.replace(R.id.fragmentContainerPairing, TuyaPairing4Fragment())
                                transaction.disallowAddToBackStack()
                                transaction.commit()
                            }

                            override fun onError(
                                errorCode: String?,
                                errorMsg: String?
                            ) {

                                Toast.makeText(
                                    requireContext(),
                                    "Activate error-->$errorMsg",
                                    Toast.LENGTH_LONG
                                ).show()
                                Log.e(TAG, "Activate error-->$errorMsg")
                                Prefs.putBoolean(SUCCESS_LAMP, false)
                                FailedDialogTuyaPairing(requireActivity()).show()
                            }
                        }
                        )

                    val mTuyaActivator =
                        TuyaHomeSdk.getActivatorInstance().newMultiActivator(builder)

                    //Start configuration
                    mTuyaActivator.start()
                }

                override fun onFailure(s: String, s1: String) {
                    Prefs.putBoolean(SUCCESS_LAMP, false)
                    FailedDialogTuyaPairing(requireActivity()).show()
                }
            })
        return binding.root
    }

}