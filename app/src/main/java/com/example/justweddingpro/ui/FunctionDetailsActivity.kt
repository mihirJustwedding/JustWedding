package com.example.justweddingpro.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.justweddingpro.R
import com.example.justweddingpro.databinding.ActivityFunctionDetailsBinding
import com.example.justweddingpro.ui.Fragment.TableListFragment
import com.example.justweddingpro.ui.Fragment.ViewMenuPlanningFragment
import com.example.justweddingpro.utils.Constants
import com.example.justweddingpro.utils.PreferenceManager
import com.google.android.material.tabs.TabLayoutMediator

class FunctionDetailsActivity : BasedActivity() {

    private lateinit var binding: ActivityFunctionDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFunctionDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAssign.setOnClickListener {
            PreferenceManager.setPref(
                Constants.Preference.Pref_EVENTId, mEventId
            )!!
            startActivity(
                Intent(
                    this@FunctionDetailsActivity,
                    MenuActivity::class.java
                )
            )
        }

        Tabview()
    }

    private fun Tabview() {
        binding.tabLayout.addTab(
            binding.tabLayout.newTab().setText(getString(R.string.menu_planning))
        )
        binding.tabLayout.addTab(
            binding.tabLayout.newTab().setText(getString(R.string.tables_view))
        )

        val listOfFragments = listOf(ViewMenuPlanningFragment(), TableListFragment())
        val viewpagerAdapter = ViewpagerAdapter(
            listOfFragments,
            supportFragmentManager,
            lifecycle
        )

        binding.viewpager.adapter = viewpagerAdapter

        // attach tabLayout with viewpager and create tabs with text
        TabLayoutMediator(binding.tabLayout, binding.viewpager) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.menu_planning)
                1 -> getString(R.string.tables_view)
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