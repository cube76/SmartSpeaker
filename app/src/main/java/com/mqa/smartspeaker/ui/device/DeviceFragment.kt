package com.mqa.smartspeaker.ui.device

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.mqa.smartspeaker.R
import com.mqa.smartspeaker.core.utils.SpacesItemDecoration
import com.mqa.smartspeaker.databinding.FragmentDeviceBinding
import com.tuya.smart.home.sdk.TuyaHomeSdk
import com.tuya.smart.home.sdk.bean.HomeBean
import com.tuya.smart.home.sdk.callback.ITuyaHomeResultCallback
import com.tuya.smart.sdk.bean.DeviceBean


class DeviceFragment : Fragment() {
    private var _binding: FragmentDeviceBinding? = null
    private val binding get() = _binding!!
    lateinit var deviceAdapter: DeviceAdapter
    var item: ArrayList<String> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDeviceBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        val view = requireActivity().findViewById<View>(R.id.LL_device)

        item.add("add device")
        binding.RVDevice.layoutManager = GridLayoutManager(context, 2)
        val itemDecoration: SpacesItemDecoration = SpacesItemDecoration(requireContext(), R.dimen.list)
        binding.RVDevice.addItemDecoration(itemDecoration)
        binding.refreshDevice.isRefreshing = true
        showList()
        binding.refreshDevice.setOnRefreshListener {
            view.performClick()
//            showList()
        }

        return binding.root
    }

    fun showList(){
        var deviceList = arrayListOf<DeviceBean>()
        deviceList.clear()
        TuyaHomeSdk.newHomeInstance(38246244).getHomeDetail(object : ITuyaHomeResultCallback {
            override fun onSuccess(bean: HomeBean?) {
                binding.refreshDevice.isRefreshing = false
                bean?.let { it ->
//                        it.deviceList.addAll(DeviceBean("",""))
//                        val deviceList: List<DeviceBean?> = ArrayList<DeviceBean>()
//                        deviceList = it.deviceList()
                    deviceList = it.deviceList as ArrayList<DeviceBean>
                    deviceAdapter = DeviceAdapter(deviceList, item)
//                    for (device in it.deviceList){
//                        Log.e("devices", "" + device.ui)
//                        val lightDevice: ITuyaLightDevice? =
//                            TuyaLightDevice(device.devId)
//                        lightDevice?.registerLightListener(object : ILightListener {
//                            override fun onDpUpdate(dataPoint: LightDataPoint) {
//                                Log.i("test_light", "onDpUpdate:$dataPoint")
//                                val status = dataPoint.powerSwitch
//
//                                if (status) {
//                                    device.ui = "ON"
//                                }else{
//                                    device.ui = "OFF"
//                                }
//                            }
//
//                            override fun onRemoved() {
//                                Log.i("test_light", "onRemoved")
//                            }
//
//                            override fun onStatusChanged(status: Boolean) {
//                                Log.i("test_light", "onDpUpdateS:$status")
//                            }
//
//                            override fun onNetworkStatusChanged(status: Boolean) {
//                                Log.i("test_light", "onDpUpdateN:$status")
//                            }
//
//                            override fun onDevInfoUpdate() {
//                                Log.i("test_light", "onDevInfoUpdate:")
//                            }
//                        })
//
//                        Log.e("devices2", "" + device.ui)
//                    }

                    binding.RVDevice.apply {
                        invalidate()
                        adapter = deviceAdapter
                    }

//                    deviceAdapter.data = it.deviceList as ArrayList<DeviceBean>
//                    Log.e("devices", "" + it.deviceList[0])
//                    deviceAdapter.data.add("")
                    deviceAdapter.notifyDataSetChanged()

                }

            }

            override fun onError(errorCode: String?, errorMsg: String?) {
                binding.refreshDevice.isRefreshing = false
                Toast.makeText(
                    activity,
                    "$errorMsg",
                    Toast.LENGTH_LONG
                ).show()
            }

        })
    }

}