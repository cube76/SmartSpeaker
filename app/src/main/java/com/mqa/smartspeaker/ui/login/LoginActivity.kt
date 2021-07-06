package com.mqa.smartspeaker.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mqa.smartspeaker.R
import com.mqa.smartspeaker.databinding.ActivityIntroBinding
import com.mqa.smartspeaker.databinding.ActivityLoginBinding
import com.mqa.smartspeaker.ui.intro.Intro2Activity
import com.mqa.smartspeaker.ui.register.RegisterActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val intent = Intent(this, Intro2Activity::class.java)
            startActivity(intent)
        }
        binding.TVNoAccount.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}