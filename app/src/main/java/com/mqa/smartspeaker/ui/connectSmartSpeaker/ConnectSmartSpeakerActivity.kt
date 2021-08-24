package com.mqa.smartspeaker.ui.connectSmartSpeaker

import android.content.BroadcastReceiver
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.ScanResult
import android.net.wifi.WifiManager
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.mqa.smartspeaker.R
import com.mqa.smartspeaker.databinding.ActivityConnectSmartSpeakerBinding
import com.mqa.smartspeaker.ui.connectSmartSpeaker.scanWifi.ScanWifiFragment
import com.thanosfisherman.wifiutils.WifiUtils


class ConnectSmartSpeakerActivity : FragmentActivity() {

    private lateinit var binding: ActivityConnectSmartSpeakerBinding
    private var wifiManager: WifiManager? = null
    private var listView: ListView? = null
    private var results: ArrayList<ScanResult>? = null
    private val arrayList = arrayListOf<String>()
    private var adapter: ArrayAdapter<*>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConnectSmartSpeakerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        WifiUtils.withContext(applicationContext).scanWifi(this::getScanResults).start()

//        wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
//
//        val wifiScanReceiver = object : BroadcastReceiver() {
//
//            override fun onReceive(context: Context, intent: Intent) {
//                val success = intent.getBooleanExtra(WifiManager.EXTRA_RESULTS_UPDATED, false)
//                if (success) {
//                    scanSuccess()
//                } else {
//                    scanFailure()
//                }
//            }
//        }
//        val success = wifiManager!!.startScan()
//        if (!success) {
//            // scan failure handling
//            scanFailure()
//        }
//
//        val intentFilter = IntentFilter()
//        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)
//        applicationContext.registerReceiver(wifiScanReceiver, intentFilter)

//        listView = binding.includeWifi.RVWifiList
//        wifiManager = applicationContext?.getSystemService(Context.WIFI_SERVICE) as WifiManager?
//
//        if (!wifiManager!!.isWifiEnabled) {
//            Toast.makeText(this, "WiFi is disabled ... We need to enable it", Toast.LENGTH_LONG)
//                .show()
//            wifiManager!!.isWifiEnabled = true
//        }
//
//        adapter = ArrayAdapter<Any?>(this, android.R.layout.simple_list_item_1,
//            arrayList as List<Any?>
//        )
//        listView!!.adapter = adapter
//        scanWifi()

        var mainFragment: ScanWifiFragment = ScanWifiFragment()
        supportFragmentManager.beginTransaction().add(R.id.fragmentContainerView, mainFragment)
            .commit()
    }

    private fun getScanResults(results: List<ScanResult>) {
        if (results.isEmpty()) {
            Log.i(TAG, "SCAN RESULTS IT'S EMPTY")
            return
        }
        Log.i(TAG, "GOT SCAN RESULTS $results")
    }

    private fun scanSuccess() {
        val results = wifiManager?.scanResults
        Log.e("result", results.toString())
    }

    private fun scanFailure() {
        // handle failure: new scan did NOT succeed
        // consider using old scan results: these are the OLD results!
        val results = wifiManager?.scanResults
        Log.e("gagal", results.toString())

    }

    private fun scanWifi() {
        arrayList.clear()
        registerReceiver(wifiReceiver, IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION))
        wifiManager?.startScan()
        Toast.makeText(this, "Scanning WiFi ...", Toast.LENGTH_SHORT).show()
    }

    private val wifiReceiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            context.unregisterReceiver(this)

            Log.e("hasil", results.toString())
            for (scanResult in results!!) {
                arrayList.add(scanResult.SSID + " - " + scanResult.capabilities)
                adapter?.notifyDataSetChanged()
            }

        }
    }
}