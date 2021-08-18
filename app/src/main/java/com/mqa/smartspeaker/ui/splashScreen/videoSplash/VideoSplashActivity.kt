package com.mqa.smartspeaker.ui.splashScreen.videoSplash

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.media.session.MediaSessionCompat
import android.view.View
import android.widget.LinearLayout
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import com.mqa.smartspeaker.MainActivity
import com.mqa.smartspeaker.R
import com.mqa.smartspeaker.databinding.ActivityVideoSplashBinding
import com.mqa.smartspeaker.ui.login.LoginActivity
import com.mqa.smartspeaker.ui.login.LoginActivity.Companion.FIRST_LAUNCH
import com.pixplicity.easyprefs.library.Prefs


private lateinit var binding: ActivityVideoSplashBinding
private lateinit var mediaSession: MediaSessionCompat

class VideoSplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoSplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Prefs.getBoolean(FIRST_LAUNCH, false)) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.andExoPlayerView.setSource("android.resource://"+ packageName +"/"+ R.raw.vertikal)

        binding.IVMaximize.setOnClickListener {
            binding.andExoPlayerView.layoutParams.height = LinearLayout.LayoutParams.MATCH_PARENT
            binding.andExoPlayerView.requestLayout()
            binding.IVMaximize.visibility = View.GONE
            binding.IVMinimize.visibility = View.VISIBLE
            binding.btnSkip.visibility = View.GONE
        }

        binding.IVMinimize.setOnClickListener {
            binding.andExoPlayerView.layoutParams.height = resources.getDimensionPixelSize(R.dimen.video_dip);
            binding.andExoPlayerView.requestLayout()
            binding.IVMaximize.visibility = View.VISIBLE
            binding.IVMinimize.visibility = View.GONE
            binding.btnSkip.visibility = View.VISIBLE
        }

        binding.btnSkip.setOnClickListener {
            val intent = Intent(this@VideoSplashActivity, LoginActivity::class.java)
            startActivity(intent)

            binding.andExoPlayerView.stopPlayer()
        }

//        val videoview = findViewById<View>(R.id.videoView2) as VideoView
//        val uri = Uri.parse("android.resource://" + packageName + "/" + R.raw.vertikal)
//        videoview.setVideoURI(uri)
//        videoview.setOnPreparedListener { videoview.start() }
    }
}