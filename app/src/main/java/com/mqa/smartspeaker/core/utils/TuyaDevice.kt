package com.mqa.smartspeaker.core.utils

import android.util.Log
import com.tuya.smart.centralcontrol.TuyaLightDevice
import com.tuya.smart.sdk.api.IResultCallback
import com.tuya.smart.sdk.centralcontrol.api.ITuyaLightDevice

abstract class TuyaDevice {
    fun TuyaLightInit(deviceId: String){
        val lightDevice: ITuyaLightDevice =
            TuyaLightDevice(deviceId)
    }

    fun PowerLight(lightDevice: ITuyaLightDevice, state: Boolean){
        lightDevice.powerSwitch(state, object : IResultCallback {
            override fun onError(code: String, error: String) {
                Log.i("test_light", "powerSwitch onError:$code$error")
            }

            override fun onSuccess() {
                Log.i("test_light", "powerSwitch onSuccess:")
            }
        })
    }
}