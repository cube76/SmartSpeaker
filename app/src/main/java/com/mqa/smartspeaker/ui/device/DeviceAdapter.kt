package com.mqa.smartspeaker.ui.device

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mqa.smartspeaker.R
import com.mqa.smartspeaker.ui.device.lamp.LampActivity
import com.mqa.smartspeaker.ui.device.lamp.LampActivity.Companion.DEVICE_ID
import com.mqa.smartspeaker.ui.pairing.PairingActivity
import com.pixplicity.easyprefs.library.Prefs
import com.tuya.smart.centralcontrol.TuyaLightDevice
import com.tuya.smart.sdk.api.IResultCallback
import com.tuya.smart.sdk.bean.DeviceBean
import com.tuya.smart.sdk.centralcontrol.api.ILightListener
import com.tuya.smart.sdk.centralcontrol.api.ITuyaLightDevice
import com.tuya.smart.sdk.centralcontrol.api.bean.LightDataPoint


class DeviceAdapter(
    private val data: ArrayList<DeviceBean>,
    private val string: ArrayList<String>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        private const val ITEM_FOOTER = 0
        private const val ITEM_MENU = 1
    }

//    var data: ArrayList<DeviceBean> = arrayListOf()

    override fun getItemViewType(position: Int): Int {
        return if (position < data.size) {
            ITEM_MENU
        } else {
            ITEM_FOOTER
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_MENU -> {
                val holder = ViewMenuHolder(
                    LayoutInflater.from(parent.context).inflate(R.layout.item_device, parent, false)
                )
                holder.itemView.setOnClickListener {
                    val intent = Intent(it.context, LampActivity::class.java)
                    it.context.startActivity(intent)
                    Prefs.putString(DEVICE_ID, data[holder.adapterPosition].devId)
                }

                holder.switch.setOnCheckedChangeListener { buttonView, isChecked ->
                    val lightDevice: ITuyaLightDevice? =
                        TuyaLightDevice(data[holder.adapterPosition].devId)

                    if (isChecked) {
                        lightDevice?.powerSwitch(true, object : IResultCallback {
                            override fun onError(code: String, error: String) {
                                Log.i("test_light", "powerSwitch onError:$code$error")
                            }

                            override fun onSuccess() {
                                Log.i("test_light", "powerSwitch onSuccess:")
                                holder.tvStatus.text = "ON"
                            }
                        })
                    } else {
                        lightDevice?.powerSwitch(false, object : IResultCallback {
                            override fun onError(code: String, error: String) {
                                Log.i("test_light", "powerSwitch onError:$code$error")
                            }

                            override fun onSuccess() {
                                Log.i("test_light", "powerSwitch onSuccess:")
                                holder.tvStatus.text = "OFF"
                            }
                        })
                    }
                }
                return holder
            }
            ITEM_FOOTER -> {
                val holder = ViewFooterHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_add_device, parent, false)
                )
                holder.itemView.setOnClickListener {

                    val intent = Intent(it.context, PairingActivity::class.java)
//                    intent.putExtra("deviceId", data[holder.adapterPosition].devId)
                    it.context.startActivity(intent)
                }
                return holder
            }
            else -> {
                throw IllegalArgumentException("Undefined view type")
            }
        }
    }

    override fun getItemCount(): Int {
        Log.e("size", (data.size + string.size).toString())
        return data.size + string.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewMenuHolder) {
            val bean = data[position]
            Log.e("holder", bean.isLocalOnline.toString())
            holder.tvDeviceType.text = "Smart Lamp"
            holder.tvDeviceName.text = bean.name
            holder.tvImage.setImageResource(R.drawable.lamp_icon)

//            holder.switch.isChecked = bean.isOnline
            val lightDevice: ITuyaLightDevice? =
                TuyaLightDevice(bean.devId)
//            holder.switch.isChecked = true
            lightDevice?.registerLightListener(object : ILightListener {
                override fun onDpUpdate(dataPoint: LightDataPoint) {
                    Log.i("test_light", "onDpUpdate:$dataPoint")
                    val status = dataPoint.powerSwitch
                    holder.tvStatus.text =
                        holder.itemView.context.getString(if (status) R.string.device_mgt_online else R.string.device_mgt_offline)
                    holder.switch.isChecked = status
                }

                override fun onRemoved() {
                    Log.i("test_light", "onRemoved")
                }

                override fun onStatusChanged(status: Boolean) {
                    Log.i("test_light", "onDpUpdateS:$status")
                }

                override fun onNetworkStatusChanged(status: Boolean) {
                    Log.i("test_light", "onDpUpdateN:$status")
                }

                override fun onDevInfoUpdate() {
                    Log.i("test_light", "onDevInfoUpdate:")
                }
            })
        }
        if (holder is ViewFooterHolder) {
        }
    }

    class ViewMenuHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvDeviceType: TextView = itemView.findViewById(R.id.TV_device_type)
        val tvStatus: TextView = itemView.findViewById(R.id.TV_on_off)
        val tvDeviceName: TextView = itemView.findViewById(R.id.TV_device_name)
        val tvImage: ImageView = itemView.findViewById(R.id.IV_device_type)
        val switch: Switch = itemView.findViewById(R.id.switch_on_off)
    }

    class ViewFooterHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }
}