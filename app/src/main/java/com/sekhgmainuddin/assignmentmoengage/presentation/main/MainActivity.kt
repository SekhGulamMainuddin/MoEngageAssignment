package com.sekhgmainuddin.assignmentmoengage.presentation.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.sekhgmainuddin.assignmentmoengage.R
import com.sekhgmainuddin.assignmentmoengage.databinding.ActivityMainBinding
import com.sekhgmainuddin.assignmentmoengage.presentation.main.fragments.AllNewsListFragment
import com.sekhgmainuddin.assignmentmoengage.presentation.main.fragments.BookmarkedFragment
import dagger.hilt.android.AndroidEntryPoint

// This is the main Activity and contains the host fragment

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        askNotificationPermission()
        viewModel.getNews()
        val fragment1 = AllNewsListFragment()
        val fragment2 = BookmarkedFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_fragment_container, fragment1, "").commit()

        binding.bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_fragment_container, fragment1, "").commit()
                }

                R.id.bookmarked -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_fragment_container, fragment2, "").commit()
                }
            }
            true
        }

    }

    // Since from Tiramisu we need to take notification permission from user
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { isGranted: Boolean ->
        if (isGranted) {
            Toast.makeText(this, "Notification Permission Granted", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Notification Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }

    private fun askNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) !=
                PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }
}