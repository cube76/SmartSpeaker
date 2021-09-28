package com.mqa.smartspeaker

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.mqa.smartspeaker.databinding.ActivityMainBinding
import com.mqa.smartspeaker.ui.account.AccountFragment
import com.mqa.smartspeaker.ui.connectSmartSpeaker.ConnectSmartSpeakerActivity
import com.mqa.smartspeaker.ui.device.DeviceFragment
import com.mqa.smartspeaker.ui.device.lamp.LampActivity
import com.mqa.smartspeaker.ui.home.HomeFragment
import com.mqa.smartspeaker.ui.skill.SkillFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        var mainFragment: HomeFragment = HomeFragment()
        supportFragmentManager.beginTransaction().add(R.id.fragment_container, mainFragment)
            .commit()

        binding.navView.LLAccount.setOnClickListener {
            openAccount()
            closeHome()
            closeDevice()
            closeSkill()
        }

        binding.navView.LLHome.setOnClickListener {
            openHome()
            closeAccount()
            closeDevice()
            closeSkill()
        }

        binding.navView.LLDevice.setOnClickListener {
            openDevice()
            closeAccount()
            closeHome()
            closeSkill()
        }

        binding.navView.LLSkill.setOnClickListener {
            openSkill()
            closeAccount()
            closeHome()
            closeDevice()
        }

        binding.welcomeView.CLNext.setOnClickListener {
            binding.welcomeView.root.visibility = View.GONE
        }

        binding.welcomeView.IVConnectNow.setOnClickListener {
            val i = Intent(this, ConnectSmartSpeakerActivity::class.java)
           startActivity(i)
        }


//        val navController = findNavController(R.id.nav_host_fragment)
//        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.
//        val appBarConfiguration = AppBarConfiguration(
//            setOf(
//                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
//            )
//        )
//        setupActionBarWithNavController(navController, appBarConfiguration)
//        navView.setupWithNavController(navController)
    }

    fun openHome(){
        var mainFragment: HomeFragment = HomeFragment()
        supportFragmentManager.beginTransaction().
        remove(supportFragmentManager.findFragmentById(R.id.fragment_container)!!).commit()
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, mainFragment)
            .commit()
        binding.navView.IVHome.setImageResource(R.drawable.home_yellow_icon)
        binding.navView.IVHomeDot.visibility = View.VISIBLE
        binding.navView.TVHome.setTextColor(ContextCompat.getColor(applicationContext, R.color.yellow))
    }

    fun openAccount(){
        var accountFragment: AccountFragment = AccountFragment()
        supportFragmentManager.beginTransaction().
        remove(supportFragmentManager.findFragmentById(R.id.fragment_container)!!).commit()
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, accountFragment)
            .commit()
        binding.navView.IVAccount.setImageResource(R.drawable.account_yellow_icon)
        binding.navView.IVAccountDot.visibility = View.VISIBLE
        binding.navView.TVAccount.setTextColor(ContextCompat.getColor(applicationContext, R.color.yellow))
    }

    fun openDevice(){
        var deviceFragment: DeviceFragment = DeviceFragment()
        supportFragmentManager.beginTransaction().
        remove(supportFragmentManager.findFragmentById(R.id.fragment_container)!!).commit()
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, deviceFragment)
            .commit()
        binding.navView.IVDevice.setImageResource(R.drawable.device_yellow_icon)
        binding.navView.IVDeviceDot.visibility = View.VISIBLE
        binding.navView.TVDevice.setTextColor(ContextCompat.getColor(applicationContext, R.color.yellow))
    }

    fun openSkill(){
        var mainFragment: SkillFragment = SkillFragment()
        supportFragmentManager.beginTransaction().
        remove(supportFragmentManager.findFragmentById(R.id.fragment_container)!!).commit()
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, mainFragment)
            .commit()
        binding.navView.IVSkill.setImageResource(R.drawable.skill_yellow_icon)
        binding.navView.IVSkillDot.visibility = View.VISIBLE
        binding.navView.TVSkill.setTextColor(ContextCompat.getColor(applicationContext, R.color.yellow))
    }

    fun closeHome(){
        binding.navView.IVHome.setImageResource(R.drawable.home_icon)
        binding.navView.IVHomeDot.visibility = View.GONE
        binding.navView.TVHome.setTextColor(ContextCompat.getColor(applicationContext, R.color.white))
    }

    fun closeAccount(){
        binding.navView.IVAccount.setImageResource(R.drawable.account_icon)
        binding.navView.IVAccountDot.visibility = View.GONE
        binding.navView.TVAccount.setTextColor(ContextCompat.getColor(applicationContext, R.color.white))
    }

    fun closeDevice(){
        binding.navView.IVDevice.setImageResource(R.drawable.device_icon)
        binding.navView.IVDeviceDot.visibility = View.GONE
        binding.navView.TVDevice.setTextColor(ContextCompat.getColor(applicationContext, R.color.white))
    }

    fun closeSkill(){
        binding.navView.IVSkill.setImageResource(R.drawable.skill_icon)
        binding.navView.IVSkillDot.visibility = View.GONE
        binding.navView.TVSkill.setTextColor(ContextCompat.getColor(applicationContext, R.color.white))
    }

    fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            // Is the button now checked?
            val checked = view.isChecked

            // Check which radio button was clicked
            when (view.getId()) {
                R.id.radio_semua ->
                    if (checked) {
                        val intent = Intent(this, LampActivity::class.java)
                        startActivity(intent)
                    }
                R.id.radio_on ->
                    if (checked) {
                        // Ninjas rule
                    }
            }
        }
    }
}