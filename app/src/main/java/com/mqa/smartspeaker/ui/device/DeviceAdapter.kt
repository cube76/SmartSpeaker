package com.mqa.smartspeaker.ui.device

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.INVISIBLE
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mqa.smartspeaker.R
import com.mqa.smartspeaker.ui.device.lamp.LampActivity
import com.mqa.smartspeaker.ui.pairing.PairingActivity
import com.tuya.smart.sdk.bean.DeviceBean

class DeviceAdapter(private val data: ArrayList<DeviceBean>, private val string: ArrayList<String>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        private const val ITEM_FOOTER = 0
        private const val ITEM_MENU = 1
    }

//    var data: ArrayList<DeviceBean> = arrayListOf()

override fun getItemViewType(position: Int): Int {
    return if (position < data.size) {
        ITEM_MENU
    }else{
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
//            if (CameraUtils.ipcProcess(it.context, data[holder.adapterPosition].devId)) {
//                return@setOnClickListener
//            }
                    // Navigate to zigBee sub device management
                    val intent = Intent(it.context, LampActivity::class.java)
                    intent.putExtra("deviceId", data[holder.adapterPosition].devId)
                    it.context.startActivity(intent)
                }
                return holder
            }
            ITEM_FOOTER -> {
                val holder = ViewFooterHolder(
                    LayoutInflater.from(parent.context).inflate(R.layout.item_device, parent, false)
                )
                holder.itemView.setOnClickListener {

                    val intent = Intent(it.context, PairingActivity::class.java)
//                    intent.putExtra("deviceId", data[holder.adapterPosition].devId)
                    it.context.startActivity(intent)
                }
                return holder
            }
            else -> {throw IllegalArgumentException("Undefined view type")
            }
        }
    }

    override fun getItemCount(): Int {
        Log.e("size", (data.size + string.size).toString())
        return data.size + string.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.e("holder", holder.toString())
        if(holder is ViewMenuHolder) {
            val bean = data[position]
            holder.tvDeviceType.text = bean.name
//    holder.tvStatus.text =
//        holder.itemView.context.getString(if (bean.isOnline) R.string.device_mgt_online else R.string.device_mgt_offline)
        }
        if(holder is ViewFooterHolder) {
            holder.switch.visibility = INVISIBLE
        }
    }

    class ViewMenuHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvDeviceType = itemView.findViewById<TextView>(R.id.TV_device_type)
        val tvDeviceName = itemView.findViewById<TextView>(R.id.TV_device_name)
    }

    class ViewFooterHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvDeviceType = itemView.findViewById<TextView>(R.id.TV_device_type)
        val switch = itemView.findViewById<Switch>(R.id.switch_on_off)
    }
}