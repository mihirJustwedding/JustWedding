package com.example.justweddingpro.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.example.justweddingpro.R
import com.example.justweddingpro.databinding.ActivityWelcomeBinding
import com.example.justweddingpro.utils.Constants
import com.example.justweddingpro.utils.PreferenceManager


class WelcomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWelcomeBinding

    companion object {
        private var layouts: IntArray? = null
    }

    private var myViewPagerAdapter: MyViewPagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_welcome)
        setContentView(binding.root)
        layouts = intArrayOf(
            R.layout.welcome_slide1,
            R.layout.welcome_slider2,
            R.layout.welcome_slider3
        )

        binding.btnNext.setOnClickListener {
            val current: Int = getItem(+1)
            if (current < layouts!!.size) {
                // move to next screen
                binding.viewpager.currentItem = current
            } else {
                launchHomeScreen()
            }
        }

        myViewPagerAdapter = MyViewPagerAdapter(this@WelcomeActivity, layouts)
        binding.viewpager.adapter = myViewPagerAdapter
        binding.viewpager.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                if (position == layouts!!.size - 1) {
                    // last page. make button text to GOT IT
                    binding.btnNext.setImageDrawable(resources.getDrawable(R.drawable.btn_welcome3))
                } else {
                    if (position == 0) {
                        binding.btnNext.setImageDrawable(resources.getDrawable(R.drawable.btn_welcome))
                    } else if (position == 1) {
                        binding.btnNext.setImageDrawable(resources.getDrawable(R.drawable.btn_welcome2))
                    }
                }
            }

            override fun onPageSelected(position: Int) {
            }

            override fun onPageScrollStateChanged(state: Int) {
            }
        })
    }


    private fun launchHomeScreen() {
        PreferenceManager.setPref(Constants.Preference.IS_TUTORIAL, false)
        startActivity(Intent(this@WelcomeActivity, StartActivity::class.java))
        finish()
    }


    class MyViewPagerAdapter(var mContext: Context, layouts: IntArray?) : PagerAdapter() {

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val view: View =
                LayoutInflater.from(mContext).inflate(layouts?.get(position)!!, container, false)!!
            container.addView(view)
            return view
        }

        override fun getCount(): Int {
            return layouts!!.size
        }

        override fun isViewFromObject(view: View, obj: Any): Boolean {
            return view == obj
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            val view = `object` as View
            container.removeView(view)
        }
    }

    private fun getItem(i: Int): Int {
        return binding.viewpager!!.currentItem + i
    }
}