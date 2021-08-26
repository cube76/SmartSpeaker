package com.mqa.smartspeaker.ui.device.lamp

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mqa.smartspeaker.R
import com.mqa.smartspeaker.databinding.ActivityLampBinding
import com.mqa.smartspeaker.ui.dialog.EditSmartSpeakerDialog
import com.mqa.smartspeaker.ui.dialog.InputPasswordWifiDialog
import com.mqa.smartspeaker.ui.dialog.RemoveDeviceDialog
import com.mqa.smartspeaker.ui.pairing.PairingActivity
import com.pixplicity.easyprefs.library.Prefs
import com.tuya.smart.centralcontrol.TuyaLightDevice
import com.tuya.smart.home.sdk.TuyaHomeSdk
import com.tuya.smart.sdk.api.IResultCallback
import com.tuya.smart.sdk.api.ITuyaDevice
import com.tuya.smart.sdk.centralcontrol.api.ITuyaLightDevice


//@AndroidEntryPoint
class LampActivity : AppCompatActivity() {
    companion object {
        const val DEVICE_ID = "deviceId"
    }

    private lateinit var binding: ActivityLampBinding
    var deviceId: String =Prefs.getString(DEVICE_ID, "")
    lateinit var name: String
    val mDevice: ITuyaDevice = TuyaHomeSdk.newDeviceInstance(deviceId)
    val lightDevice: ITuyaLightDevice = TuyaLightDevice(deviceId)
    var current: Boolean = lightDevice.lightDataPoint.powerSwitch

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLampBinding.inflate(layoutInflater)
        setContentView(binding.root)

        currentState()
        removeDevice()

        val workMode = lightDevice.lightDataPoint.workMode.name
        name = intent.getStringExtra("name").toString()

        if (workMode == "MODE_COLOUR") {
            binding.PBLamp.visibility = GONE
            val mainFragment: ColorFragment = ColorFragment()
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container_lamp, mainFragment)
                .commit()
        } else if (workMode == "MODE_WHITE") {
            binding.PBLamp.visibility = GONE
            val mainFragment: WarmFragment = WarmFragment()
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container_lamp, mainFragment)
                .commit()
        }

        setText(name)
        binding.ETRenameLamp.setOnClickListener {
            val dialog = Dialog(this)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.dialog_edit)
            val yesBtn = dialog.findViewById(R.id.btn_save) as Button
            val noBtn = dialog.findViewById(R.id.btn_cancel) as Button
            val rename = dialog.findViewById(R.id.ET_input_edit) as EditText
            rename.setText(name)
            val pb = dialog.findViewById(R.id.PB_rename_lamp) as ProgressBar
            yesBtn.setOnClickListener {
                val rename = rename.text.toString()
                pb.visibility = VISIBLE
                mDevice.renameDevice(rename, object : IResultCallback {
                    override fun onError(code: String?, error: String?) {
                        // Failed to rename the device.
                        dialog.dismiss()
                        Toast.makeText(
                            applicationContext,
                            "Gagal ubah nama",
                            Toast.LENGTH_SHORT
                        ).show()
                        pb.visibility = GONE
                    }

                    override fun onSuccess() {
                        // The device is renamed successfully.
                        dialog.dismiss()
                        pb.visibility = GONE
                        name = TuyaHomeSdk.getDataInstance().getDeviceBean(deviceId)?.name.toString()
                        setText(name)
                    }
                })
            }
            noBtn.setOnClickListener { dialog.dismiss() }
            dialog.show()
        }
    }

    fun setText(name: String){
        binding.ETRenameLamp.setText(name)
    }

    fun currentState() {
        if (current) {
            btnOn()
        } else {
            btnOff()
        }
        binding.btnOnOff.setOnClickListener {
            if (current) {
                current = false
                onOffDevice(false)
            } else {
                current = true
                onOffDevice(true)
            }
        }

    }

    fun onOffDevice(state: Boolean) {
        lightDevice.powerSwitch(state, object : IResultCallback {
            override fun onError(code: String, error: String) {
                Log.i("test_light", "powerSwitch onError:$code$error")
            }

            override fun onSuccess() {
                Log.i("test_light", "powerSwitch onSuccess:${lightDevice.lightDataPoint.powerSwitch}")
                if (current) {
                    btnOn()
                } else {
                    btnOff()
                }
            }
        })
    }

    fun btnOn() {
        binding.btnOnOff.setImageResource(R.drawable.on_icon)
        binding.IVRing.visibility = VISIBLE
    }

    fun btnOff() {
        binding.btnOnOff.setImageResource(R.drawable.off_icon)
        binding.IVRing.visibility = View.INVISIBLE
    }

    fun removeDevice(){
        binding.IVDelete.setOnClickListener {
            val dialog = RemoveDeviceDialog()
            dialog.show(this.supportFragmentManager,"remove_dialog")
        }
    }
}