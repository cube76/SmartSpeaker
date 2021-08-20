package com.mqa.smartspeaker.ui.device.lamp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.mqa.smartspeaker.R
import com.mqa.smartspeaker.databinding.ActivityLampBinding
import com.mqa.smartspeaker.databinding.ActivityLoginBinding
import com.tuya.smart.sdk.centralcontrol.api.ILightListener
import com.tuya.smart.sdk.centralcontrol.api.bean.LightDataPoint

class LampActivity : AppCompatActivity() {
    companion object {
        const val DEVICE_ID = "deviceId"
    }
    private lateinit var binding: ActivityLampBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLampBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        lightDevice.registerLightListener(object : ILightListener {
//            override fun onDpUpdate(dataPoint: LightDataPoint) { // return LightDataPoint，Contains the values of all function points of the lamp
//                Log.i("test_light", "onDpUpdate:$dataPoint")
//
//            }
//
//            override fun onRemoved() {
//                Log.i("test_light", "onRemoved")
//            }
//
//            override fun onStatusChanged(status: Boolean) {
//                Log.i("test_light", "onDpUpdate:$status")
//            }
//
//            override fun onNetworkStatusChanged(status: Boolean) {
//                Log.i("test_light", "onDpUpdate:$status")
//            }
//
//            override fun onDevInfoUpdate() {
//                Log.i("test_light", "onDevInfoUpdate:")
//            }
//        })

        val mainFragment: ColorFragment = ColorFragment()
        supportFragmentManager.beginTransaction().add(R.id.fragment_container_lamp, mainFragment)
            .commit()
    }
}