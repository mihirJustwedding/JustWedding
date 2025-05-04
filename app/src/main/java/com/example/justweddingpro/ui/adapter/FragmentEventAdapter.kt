package com.example.justweddingpro.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.justweddingpro.ui.Fragment.BridalGroomDetailsFragment
import com.example.justweddingpro.ui.Fragment.EventDetailsFragment
import com.example.justweddingpro.ui.Fragment.FunctionDetailsFragment
import com.example.justweddingpro.ui.Fragment.PartyDetailsFragment


class FragmentEventAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = 4 // Number of fragments

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> EventDetailsFragment()
            1 -> PartyDetailsFragment()
            2 -> BridalGroomDetailsFragment()
            3 -> FunctionDetailsFragment()
            else -> EventDetailsFragment()
        }
    }
}
