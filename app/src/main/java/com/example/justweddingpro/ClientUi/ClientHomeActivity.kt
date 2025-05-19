package com.example.justweddingpro.ClientUi

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.justweddingpro.ManagerAndCaptainUi.OrderHistoryActivity
import com.example.justweddingpro.R
import com.example.justweddingpro.databinding.ActivityClientHomeBinding
import com.example.justweddingpro.ui.BasedActivity
import com.example.justweddingpro.ui.CreateCaptainActivity
import com.example.justweddingpro.utils.Constants
import com.example.justweddingpro.utils.PreferenceManager
import com.google.android.material.navigation.NavigationView


class ClientHomeActivity : BasedActivity() {

    lateinit var binding: ActivityClientHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClientHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toggle = ActionBarDrawerToggle(
            this, binding.drawerLayout, R.string.nav_open, R.string.nav_close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        toggle.syncState()

        binding.nvView.setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener { item ->
            // Called when an item in the NavigationView is selected.
            // Handle the selected item based on its ID
            if (item.itemId == R.id.home) {

            }
            if (item.itemId == R.id.activity) {

            }
            if (item.itemId == R.id.orderHistory) {
                startActivity(Intent(this@ClientHomeActivity, OrderHistoryActivity::class.java))
            }
            if (item.itemId == R.id.navigation_planning) {

            }
            if (item.itemId == R.id.feedback) {
                startActivity(Intent(this@ClientHomeActivity, FeedbackActivity::class.java))
            }

            if (item.itemId == R.id.createCaptain) {
                startActivity(Intent(this@ClientHomeActivity, CreateCaptainActivity::class.java))
            }

            if (item.itemId == R.id.FunctionList) {
                startActivity(Intent(this@ClientHomeActivity, UserFunctionListActivity::class.java))
            }

            if (item.itemId == R.id.mLogout) {
                mLogout()
            }
            // Close the drawer after selection
            binding.drawerLayout.closeDrawers()
            // Indicate that the item selection has been handled
            true
        })

        if (PreferenceManager.getPref(Constants.Preference.IS_WRITE_PERMISSION, false) == false)
            binding.nvView.menu.findItem(R.id.createCaptain).setVisible(false)

        var mView = binding.nvView.getHeaderView(0)
        var imgClose: ImageView = mView.findViewById(R.id.imgClose)
        var tvUsername: TextView = mView.findViewById(R.id.tvUsername)
        var tvUserEmail: TextView = mView.findViewById(R.id.tvUserEmail)
        imgClose.setOnClickListener {
            if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            }
        }
        tvUsername.text = PreferenceManager.getPref(
            Constants.Preference.Pref_UserName,
            ""
        )
        tvUserEmail.text = PreferenceManager.getPref(
            Constants.Preference.Pref_Email,
            ""
        )
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    fun mOpenDrawer() {
        binding.drawerLayout.openDrawer(GravityCompat.START)
    }
}