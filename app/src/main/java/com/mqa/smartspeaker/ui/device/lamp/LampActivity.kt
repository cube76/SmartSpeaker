package com.mqa.smartspeaker.ui.device.lamp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mqa.smartspeaker.R
import com.mqa.smartspeaker.ui.home.HomeFragment

class LampActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lamp)

        var mainFragment: WarmFragment = WarmFragment()
        supportFragmentManager.beginTransaction().add(R.id.fragment_container_lamp, mainFragment)
            .commit()
    }
}