package com.example.expensetracker.loginandsignup


import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.adapter.FragmentStateAdapter

class PagerAdapter(private val fragmentActivity: FragmentActivity,private val fragmentlist:List<Fragment>):FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return fragmentlist.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentlist[position]
    }


}