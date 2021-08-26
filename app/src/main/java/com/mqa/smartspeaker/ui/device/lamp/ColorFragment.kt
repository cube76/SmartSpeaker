package com.mqa.smartspeaker.ui.device.lamp

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.fragment.app.Fragment
import com.gcssloop.widget.ArcSeekBar
import com.mqa.smartspeaker.R
import com.mqa.smartspeaker.core.domain.model.ColorModel
import com.mqa.smartspeaker.databinding.FragmentColorBinding
import com.mqa.smartspeaker.ui.device.lamp.LampActivity.Companion.DEVICE_ID
import com.pixplicity.easyprefs.library.Prefs
import com.tuya.smart.centralcontrol.TuyaLightDevice
import com.tuya.smart.sdk.api.IResultCallback
import com.tuya.smart.sdk.centralcontrol.api.ITuyaLightDevice
import com.tuya.smart.sdk.centralcontrol.api.constants.LightMode


class ColorFragment : Fragment() {

    private var _binding: FragmentColorBinding? = null
    private val binding get() = _binding!!
    private val mColorSeeds = intArrayOf(
        Color.parseColor("#FF5252"),
        Color.parseColor("#FFEB3B"),
        Color.parseColor("#00C853"),
        Color.parseColor("#00B0FF"),
        Color.parseColor("#D500F9"),
        Color.parseColor("#FF5252")
    )
    lateinit var color: ColorModel
    var deviceId: String = Prefs.getString(DEVICE_ID, "")
    val lightDevice: ITuyaLightDevice =
        TuyaLightDevice(deviceId)
    var current: Boolean = lightDevice.lightDataPoint.powerSwitch

    val callback = object : IResultCallback {
        override fun onError(code: kotlin.String, error: kotlin.String) {
            Log.i("test_light", "workMode onError:$code$error")
        }

        override fun onSuccess() {
            Log.i("test_light", "workMode onSuccess")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentColorBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        val mArcSeekBar = binding.SBWarm
        mArcSeekBar.setArcColors(mColorSeeds)

        var hue: Int? = lightDevice.lightDataPoint.colorHSV.h
        var saturation: Int? = lightDevice.lightDataPoint.colorHSV.s
        var value: Int? = lightDevice.lightDataPoint.colorHSV.v

        binding.SBWarm.progress = hue!!
        binding.SBWarm.setOnProgressChangeListener(object : ArcSeekBar.OnProgressChangeListener {
            override fun onProgressChanged(seekBar: ArcSeekBar, progress: Int, isUser: Boolean) {

                binding.view5.setColorFilter(seekBar.color)

                hue = progress
                changeColor(lightDevice, hue!!, saturation!!, value!!)

                Log.e("hsv", "$hue+$saturation+$value")
            }

            override fun onStartTrackingTouch(seekBar: ArcSeekBar) {}
            override fun onStopTrackingTouch(seekBar: ArcSeekBar) {}
        })

        binding.SBBrightness.progress = value!!
        binding.TVPersentageBrightness.text = "$value%"
        binding.SBBrightness.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onStopTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onProgressChanged(
                seekBar: SeekBar,
                progress: Int,
                fromUser: Boolean
            ) {
                binding.TVPersentageBrightness.text = "$progress%"
                value = progress
                changeColor(lightDevice, hue!!, saturation!!, value!!)
            }
        })

        binding.SBContras.progress = saturation!!
        binding.TVPersentageContras.text = "$saturation%"
        binding.SBContras.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onStopTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onProgressChanged(
                seekBar: SeekBar,
                progress: Int,
                fromUser: Boolean
            ) {
                binding.TVPersentageContras.text = "$progress%"
                saturation = progress
                changeColor(lightDevice, hue!!, saturation!!, value!!)
            }
        })

        binding.IVWarm.setOnClickListener {
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.fragment_container_lamp, WarmFragment())
            transaction?.commit()

            lightDevice.workMode(LightMode.MODE_WHITE, callback)
        }

        return binding.root
    }

    fun changeColor(lightDevice: ITuyaLightDevice, hue: Int, saturation: Int, value: Int) {

        lightDevice.colorHSV(hue, saturation, value, callback)
    }

}