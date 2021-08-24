package com.mqa.smartspeaker.ui.device.lamp

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
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
import com.pixplicity.easyprefs.library.Prefs
import com.tuya.smart.centralcontrol.TuyaLightDevice
import com.tuya.smart.home.sdk.TuyaHomeSdk
import com.tuya.smart.sdk.api.IResultCallback
import com.tuya.smart.sdk.centralcontrol.api.ITuyaLightDevice


//@AndroidEntryPoint
class LampActivity : AppCompatActivity() {
    companion object {
        const val DEVICE_ID = "deviceId"
    }

    private lateinit var binding: ActivityLampBinding
    lateinit var deviceId: String
    lateinit var name: String
//    val deviceId: String = Prefs.getString(DEVICE_ID, "")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLampBinding.inflate(layoutInflater)
        setContentView(binding.root)

        deviceId = Prefs.getString(DEVICE_ID, "")
        val mDevice = TuyaHomeSdk.newDeviceInstance(deviceId)
        val lightDevice: ITuyaLightDevice =
            TuyaLightDevice(deviceId)
        val workMode = lightDevice.lightDataPoint.workMode.name
        name = intent.getStringExtra("name").toString()

        if (workMode == "MODE_COLOUR") {
            binding.PBLamp.visibility = GONE
            val mainFragment: ColorFragment = ColorFragment()
//                    mainFragment.arguments = bundle
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

}