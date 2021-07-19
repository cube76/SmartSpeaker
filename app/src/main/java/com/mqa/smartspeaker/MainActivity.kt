package com.mqa.smartspeaker

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentTransaction
import com.mqa.smartspeaker.databinding.ActivityMainBinding
import com.mqa.smartspeaker.ui.account.AccountFragment
import com.mqa.smartspeaker.ui.home.HomeFragment
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
        }

        binding.navView.LLHome.setOnClickListener {
            openHome()
            closeAccount()
        }

        binding.welcomeView.TVNext.setOnClickListener {
            binding.welcomeView.root.visibility = View.GONE
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
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, mainFragment)
            .commit()
        binding.navView.IVHome.setImageResource(R.drawable.home_yellow_icon)
        binding.navView.IVHomeDot.visibility = View.VISIBLE
        binding.navView.TVHome.setTextColor(ContextCompat.getColor(applicationContext, R.color.yellow))
    }

    fun openAccount(){
        var accountFragment: AccountFragment = AccountFragment()
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, accountFragment)
            .commit()
        binding.navView.IVAccount.setImageResource(R.drawable.account_yellow_icon)
        binding.navView.IVAccountDot.visibility = View.VISIBLE
        binding.navView.TVAccount.setTextColor(ContextCompat.getColor(applicationContext, R.color.yellow))
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
}