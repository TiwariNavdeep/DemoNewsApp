package com.example.demosample.presentation.ui.profile

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.demosample.R
import com.example.demosample.databinding.ActivityRandomUsersBinding
import com.example.demosample.presentation.ui.profile.adapter.UsersProfilePagerAdapter
import com.example.demosample.presentation.viewModels.ProfileViewModel
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RandomUsersActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRandomUsersBinding
    lateinit var viewModel: ProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRandomUsersBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        viewModel = ViewModelProvider(this)[ProfileViewModel::class.java]

        initView()
    }

    private fun initView() {
        val adapter = UsersProfilePagerAdapter(this)
        binding.viewPager.adapter = adapter
        initTabs()
    }

    private fun initTabs() {
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.allMatches)
                else -> getString(R.string.myChoices)
            }
        }.attach()
    }
}