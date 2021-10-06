package com.mqa.smartspeaker.ui.device

import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mqa.smartspeaker.R
import com.mqa.smartspeaker.ui.device.lamp.LampActivity
import com.mqa.smartspeaker.ui.device.lamp.LampActivity.Companion.DEVICE_ID
import com.mqa.smartspeaker.ui.pairing.PairingActivity
import com.pixplicity.easyprefs.library.Prefs
import com.tuya.smart.android.user.api.ILoginCallback
import com.tuya.smart.centralcontrol.TuyaLightDevice
import com.tuya.smart.sdk.api.IResultCallback
import com.tuya.smart.sdk.bean.DeviceBean
import com.tuya.smart.sdk.centralcontrol.api.ILightListener
import com.tuya.smart.sdk.centralcontrol.api.ITuyaLightDevice
import com.tuya.smart.sdk.centralcontrol.api.bean.LightDataPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.CoroutineScope


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
                    intent.putExtra("name", data[holder.bindingAdapterPosition].name)
                    intent.putExtra("id", data[holder.bindingAdapterPosition].devId)
                    it.context.startActivity(intent)
                    Prefs.putString(DEVICE_ID, data[holder.bindingAdapterPosition].devId)
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
        return data.size + string.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewMenuHolder) {
            val bean = data[position]
            holder.pbDev.visibility = VISIBLE
            Log.e("holder", bean.ui.toString())
            holder.tvDeviceType.text = "Smart Lamp"
            holder.tvDeviceName.text = bean.name
            holder.tvImage.setImageResource(R.drawable.lamp_icon)

            val lightDevice: ITuyaLightDevice? =
                TuyaLightDevice(bean.devId)

            if (bean.isOnline) {
                var status = lightDevice?.lightDataPoint?.powerSwitch
                holder.tvStatus.text =
                                holder.itemView.context.getString(if (status == true) R.string.device_mgt_online else R.string.device_mgt_offline)
                holder.pbDev.visibility = GONE
            } else {
                holder.pbDev.visibility = GONE
            }

        }
        if (holder is ViewFooterHolder) {
        }
    }

    class ViewMenuHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvDeviceType: TextView = itemView.findViewById(R.id.TV_device_type)
        val tvStatus: TextView = itemView.findViewById(R.id.TV_on_off)
        val tvDeviceName: TextView = itemView.findViewById(R.id.TV_device_name)
        val tvImage: ImageView = itemView.findViewById(R.id.IV_device_type)
        val pbDev: ProgressBar = itemView.findViewById(R.id.PB_list_dev)
    }

    class ViewFooterHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}