package com.example.justweddingpro.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.justweddingpro.R
import com.example.justweddingpro.databinding.ActivityManagerAndCaptainListBinding
import com.example.justweddingpro.ui.Fragment.CaptionsListFragment
import com.example.justweddingpro.ui.Fragment.ManagerListFragment
import com.google.android.material.tabs.TabLayoutMediator

class ManagerAndCaptainListActivity : BasedActivity() {

    private lateinit var binding: ActivityManagerAndCaptainListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityManagerAndCaptainListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Tabview()
    }

    private fun Tabview() {
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(getString(R.string.manager)))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(getString(R.string.captions)))

        val listOfFragments = listOf(ManagerListFragment(false), CaptionsListFragment(false))
        val viewpagerAdapter = ViewpagerAdapter(
            listOfFragments,
            supportFragmentManager,
            lifecycle
        )

        binding.viewpager.adapter = viewpagerAdapter

        // attach tabLayout with viewpager and create tabs with text
        TabLayoutMediator(binding.tabLayout, binding.viewpager) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.manager)
                1 -> getString(R.string.captions)
                else -> ""
            }
        }.attach()
    }

    class ViewpagerAdapter(
        private val fragments: List<Fragment>,
        fragmentManager: FragmentManager,
        lifecycle: Lifecycle
    ) : FragmentStateAdapter(fragmentManager, lifecycle) {

        override fun getItemCount() = fragments.size

        override fun createFragment(position: Int) = fragments[position]
    }

}