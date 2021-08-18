package com.mqa.smartspeaker.ui.device

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mqa.smartspeaker.R
import com.mqa.smartspeaker.ui.device.lamp.LampActivity
import com.tuya.smart.sdk.bean.DeviceBean

class DeviceAdapter : RecyclerView.Adapter<DeviceAdapter.ViewHolder>() {
    var data: ArrayList<DeviceBean> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val holder = ViewHolder(
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

override fun getItemCount(): Int {
    return data.size
}

override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val bean = data[position]
    holder.tvDeviceType.text = bean.name
//    holder.tvStatus.text =
//        holder.itemView.context.getString(if (bean.isOnline) R.string.device_mgt_online else R.string.device_mgt_offline)
}

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tvDeviceType = itemView.findViewById<TextView>(R.id.TV_device_type)
    val tvDeviceName= itemView.findViewById<TextView>(R.id.TV_device_name)
}
}