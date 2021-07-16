package com.mqa.smartspeaker.ui.forgetPassword

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mqa.smartspeaker.R
import com.mqa.smartspeaker.ui.forgetPassword.inputEmail.InputEmailActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgetPasswordActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.frame_forget_password_topbar)

//        var mainFragment: InputEmailActivity = InputEmailActivity()
//        supportFragmentManager.beginTransaction().add(R.id.container_forget_password, mainFragment)
//            .commit()
    }

    companion object {
        val EMAIL = "email"
        val CODE = "code"
    }
}