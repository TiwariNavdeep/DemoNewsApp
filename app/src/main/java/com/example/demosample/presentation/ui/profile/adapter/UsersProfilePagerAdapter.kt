package com.example.demosample.presentation.ui.profile.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.demosample.presentation.ui.profile.fragment.AllMatchesFragment
import com.example.demosample.presentation.ui.profile.fragment.MyChoiceFragment

class UsersProfilePagerAdapter (
    fragmentActivity: FragmentActivity
): FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> AllMatchesFragment.newInstance()
            else -> MyChoiceFragment.newInstance()
        }
    }
}