package com.mqa.smartspeaker.ui.splashScreen.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.mqa.smartspeaker.MainActivity
import com.mqa.smartspeaker.R
import com.mqa.smartspeaker.ui.splashScreen.videoSplash.VideoSplashActivity


class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler().postDelayed(Runnable {
            val intent = Intent(this@SplashScreenActivity, VideoSplashActivity::class.java)
            startActivity(intent)
            finish()
        }, 2000)
    }
}