package com.mqa.smartspeaker.ui.pairing

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.mqa.smartspeaker.R
import com.mqa.smartspeaker.core.domain.model.Wifi
import com.mqa.smartspeaker.ui.dialog.InputPasswordWifiDialog
import com.mqa.smartspeaker.ui.pairing.smartSpeaker.SmartSpeakerPairingFragment.Companion.newInstance
import com.mqa.smartspeaker.ui.pairing.stb.StbPairingFragment.Companion.newInstance
import com.pixplicity.easyprefs.library.Prefs

class WifiAdapter(val context: Context) : RecyclerView.Adapter<WifiAdapter.ViewHolder>() {
    var data: ArrayList<Wifi> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val holder = ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_wifi, parent, false)
        )
        holder.itemView.setOnClickListener {
//            InputPasswordWifiDialog(context).show()
            var dialog = InputPasswordWifiDialog()
            dialog.show((context as PairingActivity).supportFragmentManager, "dialog")
            Prefs.putString("ssid", data[holder.bindingAdapterPosition].ssid)
        }
        return holder
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val result = data[position]
        holder.tvWifiName.text = result.ssid
        when (result.level) {
            0 -> holder.ivLevel.setImageResource(R.drawable.ic_signal_level_0_20dp)
            1 -> holder.ivLevel.setImageResource(R.drawable.ic_signal_level_1_20dp)
            2 -> holder.ivLevel.setImageResource(R.drawable.ic_signal_level_2_20dp)
            3 -> holder.ivLevel.setImageResource(R.drawable.ic_signal_level_3_20dp)
            4 -> holder.ivLevel.setImageResource(R.drawable.ic_signal_level_4_20dp)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvWifiName: TextView = itemView.findViewById(R.id.TV_wifi_name)
        val ivLevel: ImageView = itemView.findViewById(R.id.IV_level)
    }
}