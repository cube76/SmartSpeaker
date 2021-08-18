package com.mqa.smartspeaker.ui.device

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.mqa.smartspeaker.R
import com.mqa.smartspeaker.databinding.FragmentDeviceBinding
import com.mqa.smartspeaker.ui.device.lamp.LampActivity
import com.tuya.smart.home.sdk.TuyaHomeSdk
import com.tuya.smart.home.sdk.bean.HomeBean
import com.tuya.smart.home.sdk.callback.ITuyaHomeResultCallback
import com.tuya.smart.sdk.bean.DeviceBean


class DeviceFragment : Fragment() {
    private var _binding: FragmentDeviceBinding ? = null
    private val binding get() = _binding!!
    lateinit var deviceAdapter: DeviceAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDeviceBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment

        deviceAdapter = DeviceAdapter()
        binding.RVDevice.apply {
            layoutManager = GridLayoutManager(activity, 2)
            adapter = deviceAdapter
        }
        TuyaHomeSdk.newHomeInstance(38246244).getHomeDetail(object : ITuyaHomeResultCallback {
            override fun onSuccess(bean: HomeBean?) {
                bean?.let { it ->
//                        it.deviceList.addAll(DeviceBean("",""))
//                        val deviceList: List<DeviceBean?> = ArrayList<DeviceBean>()
//                        deviceList = it.deviceList()

                    deviceAdapter.data = it.deviceList as ArrayList<DeviceBean>
                        Log.e("devices", ""+ it.deviceList[0])
                    deviceAdapter.notifyDataSetChanged()

                }

            }

            override fun onError(errorCode: String?, errorMsg: String?) {

            }

        })

        return binding.root
    }

}