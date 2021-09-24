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
import com.alibaba.fastjson.JSONObject
import com.gcssloop.widget.ArcSeekBar
import com.mqa.smartspeaker.R
import com.mqa.smartspeaker.databinding.FragmentWarmBinding
import com.pixplicity.easyprefs.library.Prefs
import com.tuya.smart.android.device.bean.SchemaBean
import com.tuya.smart.centralcontrol.TuyaLightDevice
import com.tuya.smart.home.sdk.TuyaHomeSdk
import com.tuya.smart.home.sdk.utils.SchemaMapper
import com.tuya.smart.sdk.api.IResultCallback
import com.tuya.smart.sdk.centralcontrol.api.ITuyaLightDevice
import com.tuya.smart.sdk.centralcontrol.api.bean.LightDataPoint
import com.tuya.smart.sdk.centralcontrol.api.constants.LightMode
import dev.jorgecastillo.androidcolorx.library.asHsv
import java.lang.String
import kotlin.math.pow
import kotlin.math.roundToInt


class WarmFragment : Fragment() {
    private var _binding: FragmentWarmBinding? = null
    private val binding get() = _binding!!
    var colors = intArrayOf(
        Color.parseColor("#fed275"),
        Color.parseColor("#ffffff"),
        Color.parseColor("#d2effd")
    )
    var deviceId: kotlin.String = Prefs.getString(LampActivity.DEVICE_ID, "")
    val lightDevice: ITuyaLightDevice =
        TuyaLightDevice(deviceId)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWarmBinding.inflate(inflater, container, false)

        Log.e("deviceid", "$deviceId")
        val callback = object : IResultCallback {
            override fun onError(code: kotlin.String, error: kotlin.String) {
                Log.i("test_light", "workMode onError:$code$error")
            }

            override fun onSuccess() {
                Log.i("test_light", "workMode onSuccess")
            }
        }

        val mArcSeekBar = binding.SBColor
        var total = lightDevice.lightDataPoint.colorTemperature.toFloat() / 100 * 1000
        mArcSeekBar.progress = total.toInt()
        mArcSeekBar.setArcColors(colors)
        mArcSeekBar.setOnProgressChangeListener(object : ArcSeekBar.OnProgressChangeListener {
            override fun onProgressChanged(seekBar: ArcSeekBar, progress: Int, isUser: Boolean) {
                binding.view5.setColorFilter(seekBar.color)

                val map = HashMap<kotlin.String, Any>()
                map["23"] = progress
                JSONObject.toJSONString(map)?.let {
                    lightDevice.publishDps(it, callback)
                }
            }

            override fun onStartTrackingTouch(seekBar: ArcSeekBar) {}
            override fun onStopTrackingTouch(seekBar: ArcSeekBar) {}
        })

        binding.TVPersentage.text = "${lightDevice.lightDataPoint.brightness}%"
        binding.SBBrightnessWarm.setOnSeekBarChangeListener(
            object :
                SeekBar.OnSeekBarChangeListener {
                override fun onStopTrackingTouch(seekBar: SeekBar) {
                }

                override fun onStartTrackingTouch(seekBar: SeekBar) {
                }

                override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                    var percentage = (progress.toDouble() / 990) * 100
                    Log.e("progress", progress.toString())
                    binding.TVPersentage.text = "${percentage.toInt()}%"
                    val map = HashMap<kotlin.String, Any>()
                    map["22"] = progress
                    JSONObject.toJSONString(map)?.let {
                        lightDevice.publishDps(it, callback)
                    }
                }
            })

        binding.IVColor.setOnClickListener{
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.fragment_container_lamp, ColorFragment())
            transaction?.commit()
            lightDevice.workMode(LightMode.MODE_COLOUR, callback)
        }

        return binding.root
    }
}