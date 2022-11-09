package com.example.pinsurance

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.pinsurance.databinding.ActivityMainBinding
import com.example.pinsurance.fragments.AccountFragment
import com.example.pinsurance.fragments.ChatroomFragment
import com.example.pinsurance.fragments.HomeFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(HomeFragment())


        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.ic_home -> replaceFragment(HomeFragment())
                R.id.ic_chatroom -> replaceFragment(ChatroomFragment())
                R.id.ic_account -> replaceFragment(AccountFragment())

                else -> {

                }
            }
            true
        }

    }

    private fun replaceFragment(fragment: Fragment) {
        if (fragment != null) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.commit()
        }
    }
}