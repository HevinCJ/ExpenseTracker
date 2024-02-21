package com.example.expensetracker.loginandsignup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.expensetracker.R
import com.example.expensetracker.databinding.ActivityLoginandSignUpBinding
import com.example.expensetracker.fragments.DashBoard
import com.example.expensetracker.mainactivity.MainActivity
import com.google.android.material.tabs.TabLayoutMediator

class LoginandSignUp : AppCompatActivity() {
    private var activityLoginAndSignUP:ActivityLoginandSignUpBinding ?=null
    private val binding get() = activityLoginAndSignUP!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loginand_sign_up)
        activityLoginAndSignUP = ActivityLoginandSignUpBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val fragments = ArrayList<Fragment>()
        fragments.add(Login())
        fragments.add(SignUp())

        val title = ArrayList<String>()
        title.add("Login")
        title.add("SignUp")

        val adapter = PagerAdapter(this,fragments)
        binding.viewpgrloginandsignup.adapter = adapter

        val tabLayout = binding.tblayoutloginandsignup
        val viewPager = binding.viewpgrloginandsignup


        TabLayoutMediator(tabLayout,viewPager){tab,postion->
            tab.text=title[postion]
        }.attach()








    }



}