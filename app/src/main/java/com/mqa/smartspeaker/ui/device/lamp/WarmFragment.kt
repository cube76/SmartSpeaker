package com.mqa.smartspeaker.ui.device.lamp

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.gcssloop.widget.ArcSeekBar
import com.mqa.smartspeaker.R
import com.mqa.smartspeaker.databinding.FragmentWarmBinding
import com.pixplicity.easyprefs.library.Prefs
import com.tuya.smart.centralcontrol.TuyaLightDevice
import com.tuya.smart.sdk.api.IResultCallback
import com.tuya.smart.sdk.centralcontrol.api.ITuyaLightDevice
import com.tuya.smart.sdk.centralcontrol.api.bean.LightDataPoint
import com.tuya.smart.sdk.centralcontrol.api.constants.LightMode
import dev.jorgecastillo.androidcolorx.library.asHsv
import java.lang.String
import kotlin.math.roundToInt


class WarmFragment : Fragment() {
    private var _binding: FragmentWarmBinding? = null
    private val binding get() = _binding!!
    var colors = intArrayOf(
        Color.parseColor("#fed275"),
        Color.parseColor("#ffffff"),
        Color.parseColor("#d2effd")
    )
    var current: Boolean = Prefs.getBoolean(LampActivity.POWER, false)
    var deviceId: kotlin.String = Prefs.getString(LampActivity.DEVICE_ID, "")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWarmBinding.inflate(inflater, container, false)

        val lightDevice: ITuyaLightDevice =
            TuyaLightDevice(deviceId)

        val callback = object : IResultCallback {
            override fun onError(code: kotlin.String, error: kotlin.String) {
                Log.i("test_light", "workMode onError:$code$error")
            }

            override fun onSuccess() {
                Log.i("test_light", "workMode onSuccess")
            }
        }
        Log.e("lightType", "${lightDevice.lightType()}")
        Log.e("lightDataPoint", "${lightDevice.lightDataPoint.colorTemperature}")

        val mArcSeekBar = binding.SBColor
        mArcSeekBar.setArcColors(colors)
        mArcSeekBar.setOnProgressChangeListener(object : ArcSeekBar.OnProgressChangeListener {
            override fun onProgressChanged(seekBar: ArcSeekBar, progress: Int, isUser: Boolean) {
                val backgroundGradient = binding.view5.background as GradientDrawable
                backgroundGradient.setColor(seekBar.color)
//
//                var hex = Color.parseColor(String.format("#%06X", 0xFFFFFF and seekBar.color))
//                var hsv = hex.asHsv()
//                var hue = hsv.hue
//                var saturation = hsv.saturation
//                var value = hsv.value
//                Log.e("hsv", "$hue+$saturation+$value")
//                Log.e("hsvtoint", "${hue.roundToInt()}+${(saturation * 100).roundToInt()}+${(value * 100).roundToInt()}")

                lightDevice.colorTemperature(progress, callback)
            }

            override fun onStartTrackingTouch(seekBar: ArcSeekBar) {}
            override fun onStopTrackingTouch(seekBar: ArcSeekBar) {}
        })

        binding.SBBrightnessWarm.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onStopTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                binding.TVPersentage.text = "$progress%"
                lightDevice.brightness(progress, callback)
            }
        })

        binding.IVColor.setOnClickListener {
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.fragment_container_lamp, ColorFragment())
            transaction?.commit()
            lightDevice.workMode(LightMode.MODE_COLOUR, callback)
        }

        return binding.root
    }
}