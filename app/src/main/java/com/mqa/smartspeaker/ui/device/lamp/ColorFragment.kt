package com.mqa.smartspeaker.ui.device.lamp

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
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
import com.tuya.smart.sdk.centralcontrol.api.ILightListener
import com.tuya.smart.sdk.centralcontrol.api.ITuyaLightDevice
import com.tuya.smart.sdk.centralcontrol.api.bean.LightDataPoint
import dev.jorgecastillo.androidcolorx.library.asHsv


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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentColorBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        val mArcSeekBar = binding.SBWarm
        mArcSeekBar.setArcColors(mColorSeeds)
        var hue: Int
        var saturation: Int = 80
        var value: Int = 90
        val lightDevice: ITuyaLightDevice =
            TuyaLightDevice(deviceId)
        currentState(lightDevice)
        binding.SBWarm.setOnProgressChangeListener(object : ArcSeekBar.OnProgressChangeListener {
            override fun onProgressChanged(seekBar: ArcSeekBar, progress: Int, isUser: Boolean) {
                val backgroundGradient = binding.view5.background as GradientDrawable
                backgroundGradient.setColor(seekBar.color)

//                var hex =
//                    Color.parseColor(java.lang.String.format("#%06X", 0xFFFFFF and seekBar.color))
//                var hsv = hex.asHsv()
//                hue = hsv.hue.toInt()
//                saturation = (hsv.saturation * 100).toInt()
//                value = (hsv.value * 100).toInt()

                hue = progress
                changeColor(lightDevice, hue, saturation, value)

                Log.e("hsv", "$hue+$saturation+$value")

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
                        changeColor(lightDevice, hue, saturation, value)
                    }
                })

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
                        changeColor(lightDevice, hue, saturation, value)
                    }
                })
            }

            override fun onStartTrackingTouch(seekBar: ArcSeekBar) {}
            override fun onStopTrackingTouch(seekBar: ArcSeekBar) {}
        })

        binding.IVWarm.setOnClickListener {
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.fragment_container_lamp, WarmFragment())
            transaction?.commit()
        }

        return binding.root
    }

    fun changeColor(lightDevice: ITuyaLightDevice, hue: Int, saturation: Int, value: Int) {

        lightDevice?.colorHSV(hue, saturation, value, object : IResultCallback {
            override fun onError(code: String, error: String) {
                Log.i("test_light", "colorHSV onError:$code$error")
            }

            override fun onSuccess() {
                Log.i("test_light", "colorHSV onSuccess:")
            }
        })
    }

    fun currentState(lightDevice: ITuyaLightDevice){
        lightDevice.registerLightListener(object : ILightListener {
            override fun onDpUpdate(dataPoint: LightDataPoint) { // return LightDataPoint，Contains the values of all function points of the lamp
                Log.i("test_light", "onDpUpdate:$dataPoint")
                if(dataPoint.powerSwitch){
                    binding.btnOnOff.setImageResource(R.drawable.on_icon)
                    binding.btnOnOff.setOnClickListener {
                        onOffDevice(lightDevice, false)
                    }
                }else{
                    binding.btnOnOff.setImageResource(R.drawable.off_icon)
                    binding.btnOnOff.setOnClickListener {
                        onOffDevice(lightDevice, true)
                    }
                }
            }

            override fun onRemoved() {
                Log.i("test_light", "onRemoved")
            }

            override fun onStatusChanged(status: Boolean) {
                Log.i("test_light", "onDpUpdate:$status")
            }

            override fun onNetworkStatusChanged(status: Boolean) {
                Log.i("test_light", "onDpUpdate:$status")
            }

            override fun onDevInfoUpdate() {
                Log.i("test_light", "onDevInfoUpdate:")
            }
        })
    }

    fun onOffDevice(lightDevice: ITuyaLightDevice, state: Boolean){
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