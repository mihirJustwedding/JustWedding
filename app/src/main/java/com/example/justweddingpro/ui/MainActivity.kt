package com.example.justweddingpro.ui

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.os.Handler
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.justweddingpro.ClientUi.FeedbackActivity
import com.example.justweddingpro.ManagerAndCaptainUi.OrderHistoryActivity
import com.example.justweddingpro.R
import com.example.justweddingpro.databinding.ActivityMainBinding
import com.example.justweddingpro.ui.Fragment.EventsFragment
import com.example.justweddingpro.ui.Fragment.HomeFragment
import com.example.justweddingpro.ui.Fragment.ProfileFragment
import com.example.justweddingpro.ui.MyEventDetailsActivity.Companion.mIsEdite
import com.example.justweddingpro.utils.Constants
import com.example.justweddingpro.utils.PreferenceManager
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding
    var doubleBackToExitPressedOnce = false
    var isHomeClick = false
    var isEventClick = false
    var isProfilelick = false
    var isMenuClick = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setContentView(binding.root)
        mIsEdite = false

        binding.img2.imageTintList =
            ColorStateList.valueOf(resources.getColor(R.color.Color_Primery))
        binding.txt2.setTextColor(resources.getColor(R.color.Color_Primery))
        isEventClick = true
        replaceFragment(EventsFragment())

        binding.RlHome.setOnClickListener(this)
        binding.RlEvent.setOnClickListener(this)
        binding.RlProfile.setOnClickListener(this)
        binding.RlMenu.setOnClickListener(this)
        binding.addimage.setOnClickListener(this)


        SetupDrawer()
    }

    fun replaceFragment(fragment: Fragment) {
        val backStateName = fragment.javaClass.name
        val manager: FragmentManager = supportFragmentManager
        val fragmentPopped: Boolean = manager.popBackStackImmediate(backStateName, 0)
        if (!fragmentPopped) { //fragment not in back stack, create it.
            val ft: FragmentTransaction = manager.beginTransaction()
            ft.replace(R.id.nav_host_fragment, fragment)
            ft.addToBackStack(backStateName)
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            ft.commit()
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 1) {
            if (doubleBackToExitPressedOnce) {
                clearBackStack()
                super.onBackPressed()
                return
            }
            doubleBackToExitPressedOnce = true
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show()
            Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
        } else {
            super.onBackPressed()
        }
    }

    fun clearBackStack() {
        val fm = supportFragmentManager
        for (i in 0 until fm.backStackEntryCount) {
            fm.popBackStack()
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.RlHome -> if (!isHomeClick) {
                isHomeClick = true
                isEventClick = false
                isProfilelick = false
                isMenuClick = false
                clearBackStack()
                replaceFragment(HomeFragment())
                binding.img1.imageTintList =
                    ColorStateList.valueOf(resources.getColor(R.color.Color_Primery))
                binding.txt1.setTextColor(resources.getColor(R.color.Color_Primery))

                binding.img2.imageTintList =
                    ColorStateList.valueOf(resources.getColor(R.color.Secondary_color))
                binding.img3.imageTintList =
                    ColorStateList.valueOf(resources.getColor(R.color.Secondary_color))
                binding.img4.imageTintList =
                    ColorStateList.valueOf(resources.getColor(R.color.Secondary_color))

                binding.txt2.setTextColor(resources.getColor(R.color.Secondary_color))
                binding.txt3.setTextColor(resources.getColor(R.color.Secondary_color))
                binding.txt4.setTextColor(resources.getColor(R.color.Secondary_color))
            }

            R.id.RlEvent -> if (!isEventClick) {
                isHomeClick = false
                isEventClick = true
                isProfilelick = false
                isMenuClick = false
                clearBackStack()
                replaceFragment(EventsFragment())
                binding.img1.imageTintList =
                    ColorStateList.valueOf(resources.getColor(R.color.Secondary_color))
                binding.txt1.setTextColor(resources.getColor(R.color.Secondary_color))

                binding.img2.imageTintList =
                    ColorStateList.valueOf(resources.getColor(R.color.Color_Primery))
                binding.img3.imageTintList =
                    ColorStateList.valueOf(resources.getColor(R.color.Secondary_color))
                binding.img4.imageTintList =
                    ColorStateList.valueOf(resources.getColor(R.color.Secondary_color))

                binding.txt2.setTextColor(resources.getColor(R.color.Color_Primery))
                binding.txt3.setTextColor(resources.getColor(R.color.Secondary_color))
                binding.txt4.setTextColor(resources.getColor(R.color.Secondary_color))
            }

            R.id.RlProfile -> if (!isProfilelick) {
                isHomeClick = false
                isEventClick = false
                isProfilelick = true
                isMenuClick = false
                clearBackStack()
                replaceFragment(ProfileFragment())
                binding.img1.imageTintList =
                    ColorStateList.valueOf(resources.getColor(R.color.Secondary_color))
                binding.txt1.setTextColor(resources.getColor(R.color.Secondary_color))
                binding.img2.imageTintList =
                    ColorStateList.valueOf(resources.getColor(R.color.Secondary_color))
                binding.img3.imageTintList =
                    ColorStateList.valueOf(resources.getColor(R.color.Color_Primery))
                binding.img4.imageTintList =
                    ColorStateList.valueOf(resources.getColor(R.color.Secondary_color))

                binding.txt2.setTextColor(resources.getColor(R.color.Secondary_color))
                binding.txt3.setTextColor(resources.getColor(R.color.Color_Primery))
                binding.txt4.setTextColor(resources.getColor(R.color.Secondary_color))
            }

            R.id.RlMenu -> if (!isMenuClick) {
                isHomeClick = false
                isEventClick = false
                isProfilelick = false
                isMenuClick = true
//                replaceFragment(MenuFragment())
                binding.drawerLayout.openDrawer(Gravity.START)

                binding.img1.imageTintList =
                    ColorStateList.valueOf(resources.getColor(R.color.Secondary_color))
                binding.txt1.setTextColor(resources.getColor(R.color.Secondary_color))

                binding.img2.imageTintList =
                    ColorStateList.valueOf(resources.getColor(R.color.Secondary_color))
                binding.img3.imageTintList =
                    ColorStateList.valueOf(resources.getColor(R.color.Secondary_color))
                binding.img4.imageTintList =
                    ColorStateList.valueOf(resources.getColor(R.color.Color_Primery))

                binding.txt2.setTextColor(resources.getColor(R.color.Secondary_color))
                binding.txt3.setTextColor(resources.getColor(R.color.Secondary_color))
                binding.txt4.setTextColor(resources.getColor(R.color.Color_Primery))
            }

            R.id.addimage -> {
                MyEventDetailsActivity.mIsEdite = false
                startActivity(Intent(this@MainActivity, CreateEventActivity::class.java))
            }
        }
    }

    private fun SetupDrawer() {
        val toggle = ActionBarDrawerToggle(
            this, binding.drawerLayout, R.string.nav_open, R.string.nav_close
        )
        binding.drawerLayout.addDrawerListener(toggle)
//        binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        toggle.syncState()
        binding.nvView.background.alpha = 122

        binding.nvView.setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener { item ->
            // Called when an item in the NavigationView is selected.
            // Handle the selected item based on its ID
            if (item.itemId == R.id.home) {

            }
            if (item.itemId == R.id.activity) {

            }
            if (item.itemId == R.id.orderHistory) {
                startActivity(Intent(this@MainActivity, OrderHistoryActivity::class.java))
            }
            if (item.itemId == R.id.navigation_planning) {

            }
            if (item.itemId == R.id.feedback) {
                startActivity(Intent(this@MainActivity, FeedbackActivity::class.java))
            }
            // Close the drawer after selection
            binding.drawerLayout.closeDrawers()
            // Indicate that the item selection has been handled
            true
        })

//        var tvName: TextView = mView.findViewById(R.id.tvName)
//        var tvUsername: TextView = mView.findViewById(R.id.tvUsername)
//        var tvUserEmail: TextView = mView.findViewById(R.id.tvUserEmail)
//        imgClose.setOnClickListener {
//            if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
//                binding.drawerLayout.closeDrawer(GravityCompat.START)
//            }
//        }
        binding.mNavigationLayout.tvName.text = PreferenceManager.getPref(
            Constants.Preference.Pref_UserName,
            ""
        )

        binding.mNavigationLayout.tvUsername.text = PreferenceManager.getPref(
            Constants.Preference.Pref_Email,
            ""
        )

        binding.mNavigationLayout.tvMAnager.setOnClickListener {
            startActivity(Intent(this, CreateManagerActivity::class.java))
        }

        binding.mNavigationLayout.tvCaptain.setOnClickListener {
            startActivity(Intent(this, CreateCaptainActivity::class.java))
        }

        binding.mNavigationLayout.tvUserList.setOnClickListener {
            startActivity(Intent(this, ManagerAndCaptainListActivity::class.java))
        }

        binding.mNavigationLayout.tvCalender.setOnClickListener {
            if (!isEventClick) {
                isHomeClick = false
                isEventClick = true
                isProfilelick = false
                isMenuClick = false
                clearBackStack()
                replaceFragment(EventsFragment())
                binding.img1.imageTintList =
                    ColorStateList.valueOf(resources.getColor(R.color.Secondary_color))
                binding.txt1.setTextColor(resources.getColor(R.color.Secondary_color))

                binding.img2.imageTintList =
                    ColorStateList.valueOf(resources.getColor(R.color.Color_Primery))
                binding.img3.imageTintList =
                    ColorStateList.valueOf(resources.getColor(R.color.Secondary_color))
                binding.img4.imageTintList =
                    ColorStateList.valueOf(resources.getColor(R.color.Secondary_color))

                binding.txt2.setTextColor(resources.getColor(R.color.Color_Primery))
                binding.txt3.setTextColor(resources.getColor(R.color.Secondary_color))
                binding.txt4.setTextColor(resources.getColor(R.color.Secondary_color))
                binding.drawerLayout.closeDrawers()
            } else
                binding.drawerLayout.closeDrawers()
        }

        binding.mNavigationLayout.tvAllEvents.setOnClickListener {
            startActivity(Intent(this, MyEventListingActivity::class.java))
        }

        binding.mNavigationLayout.tvFeedback.setOnClickListener {
            startActivity(Intent(this, FeedbackActivity::class.java))
        }

        binding.mNavigationLayout.imgView.setOnClickListener {
            isHomeClick = false
            isEventClick = true
            isProfilelick = false
            isMenuClick = false
            clearBackStack()
            binding.drawerLayout.closeDrawers()
            replaceFragment(EventsFragment())
            binding.img1.imageTintList =
                ColorStateList.valueOf(resources.getColor(R.color.Secondary_color))
            binding.txt1.setTextColor(resources.getColor(R.color.Secondary_color))

            binding.img2.imageTintList =
                ColorStateList.valueOf(resources.getColor(R.color.Color_Primery))
            binding.img3.imageTintList =
                ColorStateList.valueOf(resources.getColor(R.color.Secondary_color))
            binding.img4.imageTintList =
                ColorStateList.valueOf(resources.getColor(R.color.Secondary_color))

            binding.txt2.setTextColor(resources.getColor(R.color.Color_Primery))
            binding.txt3.setTextColor(resources.getColor(R.color.Secondary_color))
            binding.txt4.setTextColor(resources.getColor(R.color.Secondary_color))
        }
    }
}