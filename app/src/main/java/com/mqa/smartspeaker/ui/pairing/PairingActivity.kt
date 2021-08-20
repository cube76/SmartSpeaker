package com.mqa.smartspeaker.ui.pairing

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.mqa.smartspeaker.R
import com.mqa.smartspeaker.databinding.ActivityPairingBinding
import com.mqa.smartspeaker.ui.pairing.smartSpeaker.SmartSpeakerPairingFragment
import com.mqa.smartspeaker.ui.pairing.stb.StbPairingFragment
import com.mqa.smartspeaker.ui.pairing.tuya.TuyaPairingFragment

class PairingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPairingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPairingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.actionBar.TVActionBar.text == "Tambah Perangkat"
        binding.actionBar.IVBack.setOnClickListener {
            finish()
        }

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
            val fragment: TuyaPairingFragment = TuyaPairingFragment()
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

    fun remove(){
        binding.LLSmartSpeaker.visibility = GONE
        binding.LLLamp.visibility = GONE
        binding.LLStb.visibility = GONE
    }

}