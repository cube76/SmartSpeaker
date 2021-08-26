package com.mqa.smartspeaker.ui.pairing

import android.app.Dialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.wifi.ScanResult
import android.net.wifi.WifiManager
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.Window
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.mqa.smartspeaker.R
import com.mqa.smartspeaker.core.domain.model.Wifi
import com.mqa.smartspeaker.databinding.ActivityPairingBinding
import com.mqa.smartspeaker.ui.pairing.smartSpeaker.SmartSpeakerPairingFragment
import com.mqa.smartspeaker.ui.pairing.stb.StbPairingFragment
import com.mqa.smartspeaker.ui.pairing.tuya.TuyaPairing1Fragment
import com.mqa.smartspeaker.ui.pairing.tuya.TuyaPairing2Fragment

class PairingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPairingBinding

    private var wifiManager: WifiManager? = null
    private lateinit var wifiAdapter: WifiAdapter
    private val data = arrayListOf<Wifi>()
    private var results: List<ScanResult>? = null

    private val arrayList = arrayListOf<String>()
    private var adapter: ArrayAdapter<*>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPairingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.actionBar.TVActionBar.text == "Tambah Perangkat"
        binding.actionBar.IVBack.setOnClickListener {
            finish()
        }
        wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        wifiAdapter = WifiAdapter(this)
        binding.includeWifiPairing.RVWifiResult.layoutManager = LinearLayoutManager(this)
        binding.includeWifiPairing.RVWifiResult.adapter = wifiAdapter

//        val myFragment: TuyaPairing2Fragment? =
//            supportFragmentManager.findFragmentByTag("LAMP_2") as TuyaPairing2Fragment?
//        if (myFragment != null && myFragment.isVisible) {
//            scanWifi()
//        }

//        adapter = ArrayAdapter(this, R.layout.RV_wifi_result, arrayList)


        binding.LLSmartSpeaker.setOnClickListener {
            binding.fragmentContainerPairing.visibility = VISIBLE
            remove()
            val fragment: SmartSpeakerPairingFragment = SmartSpeakerPairingFragment()
            supportFragmentManager.beginTransaction().add(R.id.fragmentContainerPairing, fragment)
                .commit()
        }

        binding.LLLamp.setOnClickListener {
            binding.fragmentContainerPairing.visibility = VISIBLE
            remove()
            val fragment: TuyaPairing1Fragment = TuyaPairing1Fragment()
            supportFragmentManager.beginTransaction().add(R.id.fragmentContainerPairing, fragment)
                .commit()
        }

        binding.LLStb.setOnClickListener {
            binding.fragmentContainerPairing.visibility = VISIBLE
            remove()
            val fragment: StbPairingFragment = StbPairingFragment()
            supportFragmentManager.beginTransaction().add(R.id.fragmentContainerPairing, fragment)
                .commit()
        }

    }

    fun remove() {
        binding.LLSmartSpeaker.visibility = GONE
        binding.LLLamp.visibility = GONE
        binding.LLStb.visibility = GONE
        binding.actionBar.root.visibility = GONE
    }

    fun scanWifi() {
        data.clear()
        registerReceiver(
            wifiReceiver,
            IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)
        )
        wifiManager!!.startScan()
        Toast.makeText(this, "Scanning WiFi ...", Toast.LENGTH_SHORT).show()
    }

    var wifiReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            results = wifiManager!!.scanResults
            unregisterReceiver(this)

            for (scanResult in results!!) {
                var wifi_ssid = ""
                wifi_ssid = scanResult.SSID
                Log.d("WIFIScannerActivity", "WIFI SSID: $wifi_ssid")

                var wifi_ssid_first_nine_characters = ""

                if (wifi_ssid.length > 8) {
                    wifi_ssid_first_nine_characters = wifi_ssid.substring(0, 9)
                } else {
                    wifi_ssid_first_nine_characters = wifi_ssid
                }
                Log.d("WIFIScannerActivity", "WIFI SSID 9: $wifi_ssid_first_nine_characters")

                // Display only WIFI that matched "WIFI_NAME"
//                if (wifi_ssid_first_nine_characters == "WIFI_NAME") {
//                Log.d(
//                    "WIFIScannerActivity",
//                    "scanResult.SSID: " + scanResult.SSID + ", scanResult.capabilities: " + scanResult.level
//                )
                data.add(
                    Wifi(
                        wifi_ssid_first_nine_characters,
                        WifiManager.calculateSignalLevel(scanResult.level, 4)
                    )
                )

                wifi_ssid = ""
                wifi_ssid_first_nine_characters = ""

                wifiAdapter.data = data
                wifiAdapter.notifyDataSetChanged()
            }
            binding.includeWifiPairing.root.visibility = VISIBLE
            binding.viewUnable.visibility = VISIBLE


        }
    }

    fun removeWifi(){
        binding.includeWifiPairing.root.visibility = GONE
        binding.viewUnable.visibility = GONE
    }

}