package com.mqa.smartspeaker.ui.connectSmartSpeaker.scanWifi

import android.R
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.ScanResult
import android.net.wifi.WifiManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.mqa.smartspeaker.databinding.FragmentScanWifiBinding


class ScanWifiFragment : Fragment() {
    private var _binding: FragmentScanWifiBinding? = null
    private val binding get() = _binding!!
    private var wifiManager: WifiManager? = null
    private var listView: ListView? = null
    private val size = 0
    private var results: ArrayList<ScanResult>? = null
    private val arrayList = arrayListOf<String>()
    private var adapter: ArrayAdapter<*>? = null

//    val wifiManager = context?.getSystemService(Context.WIFI_SERVICE) as WifiManager

    val wifiScanReceiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            val success = intent.getBooleanExtra(WifiManager.EXTRA_RESULTS_UPDATED, false)
            if (success) {
                scanSuccess()
            } else {
                scanFailure()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentScanWifiBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        listView = binding.includeWifi.RVWifiList
//        wifiManager = activity?.applicationContext?.getSystemService(Context.WIFI_SERVICE) as WifiManager?
//
//        if (!wifiManager!!.isWifiEnabled) {
//            Toast.makeText(context, "WiFi is disabled ... We need to enable it", Toast.LENGTH_LONG)
//                .show()
//            wifiManager!!.isWifiEnabled = true
//        }
//
//        adapter = ArrayAdapter<Any?>(requireContext(), R.layout.simple_list_item_1,
//            arrayList as List<Any?>
//        )
//        listView!!.adapter = adapter
//        scanWifi()
//        val intentFilter = IntentFilter()
//        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)
//        context?.registerReceiver(wifiScanReceiver, intentFilter)
//
//        val success = wifiManager.startScan()
//        Log.e("success", success.toString())
//        if (!success) {
//            // scan failure handling
//            scanFailure()
//        }

    }

    private fun scanWifi() {
        arrayList.clear();
        context?.registerReceiver(wifiReceiver, IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        wifiManager?.startScan()
        Toast.makeText(context, "Scanning WiFi ...", Toast.LENGTH_SHORT).show();
    }

    private val wifiReceiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            context?.unregisterReceiver(this)

            Log.e("hasil", results.toString())
            for (scanResult in results!!) {
                arrayList.add(scanResult.SSID + " - " + scanResult.capabilities);
                adapter?.notifyDataSetChanged()
            }

        }
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

}