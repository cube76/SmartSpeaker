package com.mqa.smartspeaker.ui.pairing.tuya

import android.Manifest
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.mqa.smartspeaker.R
import com.mqa.smartspeaker.databinding.FragmentTuyaPairing1Binding
import pub.devrel.easypermissions.EasyPermissions


class TuyaPairing1Fragment : Fragment() {

    private var _binding: FragmentTuyaPairing1Binding? = null
    private val binding get() = _binding!!
    var gpsStatus: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTuyaPairing1Binding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        val locationManager =
            activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        binding.CBConfirmKedip.setOnClickListener {
            if (!binding.CBConfirmKedip.isChecked) {
                binding.btnLampSecondStep.isEnabled = false
                binding.viewUnable.visibility = VISIBLE
            } else if (binding.CBConfirmKedip.isChecked) {
                binding.btnLampSecondStep.isEnabled = true
                binding.viewUnable.visibility = GONE
            }
        }

        binding.btnLampSecondStep.setOnClickListener {
            gpsStatus = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            Log.e("location", gpsStatus.toString())
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !EasyPermissions.hasPermissions(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION)) {
                requestPermissions(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), 0)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P && !gpsStatus) {
                    enableLocation()
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P && !EasyPermissions.hasPermissions(
                            requireContext(),
                            Manifest.permission.ACCESS_BACKGROUND_LOCATION
                        )
                    ) {
                        allowAllTheTime()
                    }
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P && !EasyPermissions.hasPermissions(
                        requireContext(),
                        Manifest.permission.ACCESS_BACKGROUND_LOCATION
                    )
                ) {
                    allowAllTheTime()
                }
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P && !gpsStatus) {
                enableLocation()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P && !EasyPermissions.hasPermissions(
                        requireContext(),
                        Manifest.permission.ACCESS_BACKGROUND_LOCATION
                    )
                ) {
                    allowAllTheTime()
                }
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P && !EasyPermissions.hasPermissions(requireContext(), Manifest.permission.ACCESS_BACKGROUND_LOCATION)) {
                allowAllTheTime()
            } else {
                val transaction = requireActivity().supportFragmentManager.beginTransaction()
                transaction.replace(R.id.fragmentContainerPairing, TuyaPairing2Fragment(), "LAMP_2")
                transaction.disallowAddToBackStack()
                transaction.commit()
            }
        }

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun allowAllTheTime() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Perhatian")
        builder.setMessage("Pilih \"Allow All The Time\"")

        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
            requestPermissions(arrayOf(Manifest.permission.ACCESS_BACKGROUND_LOCATION), 0)
        }

        builder.setNegativeButton(android.R.string.no) { dialog, which ->
            dialog.dismiss()
        }

        builder.show()
    }

    private fun enableLocation() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Perhatian")
        builder.setMessage("Nyalakan Location")

        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivity(intent)
        }

        builder.setNegativeButton(android.R.string.no) { dialog, which ->
            dialog.dismiss()
        }

        builder.show()
    }

}