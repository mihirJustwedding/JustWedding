package com.example.justweddingpro.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.crmapplication.MyApplication
import com.example.justweddingpro.Network.RequestModel.FunctionAssignRequest
import com.example.justweddingpro.R
import com.example.justweddingpro.Response.RegisterResponse
import com.example.justweddingpro.databinding.ActivityAssignFunctionBinding
import com.example.justweddingpro.ui.Fragment.CaptionsListFragment
import com.example.justweddingpro.ui.Fragment.ManagerListFragment
import com.example.justweddingpro.ui.Response.ResponseBase
import com.example.justweddingpro.utils.CommonUtils
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AssignFunctionActivity : BasedActivity() {
    private lateinit var binding: ActivityAssignFunctionBinding

    companion object {
        var clientUserIdList = ArrayList<Int>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAssignFunctionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.headerTitle.headerTitle.setText("Function List")
        binding.headerTitle.frdIcon.setOnClickListener { onBackPressed() }

        Tabview()

        binding.btnAssign.setOnClickListener {
            ApiGetManagerAssign()
        }

        binding.btnNext.setOnClickListener {
            finish()
            startActivity(
                Intent(
                    this@AssignFunctionActivity,
                    AssignManagerAndCaptainActivity::class.java
                )
            )
        }
    }

    private fun Tabview() {
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(getString(R.string.manager)))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(getString(R.string.captions)))

        val listOfFragments = listOf(ManagerListFragment(true), CaptionsListFragment(true))
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

    private fun ApiGetManagerAssign() {
        val mFunctionAssignRequest = FunctionAssignRequest()
        mFunctionAssignRequest.setClientUserId(clientUserIdList)
        mFunctionAssignRequest.setEventId(mEventId.toInt())
        mFunctionAssignRequest.setFunctionId(mFunctionId.toInt())

        CommonUtils.showProgressDialog(this@AssignFunctionActivity)
        MyApplication.getRestClient()?.API_AddAssignUser(mFunctionAssignRequest)
            ?.enqueue(object : Callback<ResponseBase<RegisterResponse>> {
                override fun onResponse(
                    call: Call<ResponseBase<RegisterResponse>?>?,
                    response: Response<ResponseBase<RegisterResponse>?>
                ) {
                    CommonUtils.hideProgressDialog()
                    if (response.isSuccessful) {
                        if (response.body()?.mSuccess!!) {
                            clientUserIdList.clear()
                            Toast.makeText(
                                this@AssignFunctionActivity,
                                response.body()?.mMessage!!,
                                Toast.LENGTH_LONG
                            ).show()

                            finish()
                            startActivity(
                                Intent(
                                    this@AssignFunctionActivity,
                                    AssignManagerAndCaptainActivity::class.java
                                )
                            )
                        } else {
                            Log.d("Mytag", response.body()?.mError!!)
                        }
                    }
                }

                override fun onFailure(
                    call: Call<ResponseBase<RegisterResponse>?>, t: Throwable
                ) {
                    CommonUtils.hideProgressDialog()
                    Log.d("MyTAG", "onFailure: " + t.message)
                }
            })
    }

}