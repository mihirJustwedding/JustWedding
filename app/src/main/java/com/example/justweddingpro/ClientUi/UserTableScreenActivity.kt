package com.example.justweddingpro.ClientUi

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.crmapplication.MyApplication
import com.example.justweddingpro.ClientUi.Response.ManagerTableListResponse
import com.example.justweddingpro.databinding.ActivityUserTableScreenBinding
import com.example.justweddingpro.ui.Response.ResponseBase
import com.example.justweddingpro.ui.adapter.AssignTableAdapter
import com.example.justweddingpro.utils.CommonUtils
import com.example.justweddingpro.utils.Constants
import com.example.justweddingpro.utils.PreferenceManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserTableScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserTableScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserTableScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rlHeader.headerTitle.text = "Table List"
        binding.rlHeader.frdIcon.setOnClickListener { onBackPressed() }
        mApiShowOrderTableList()
    }

    private fun mApiShowOrderTableList() {
        CommonUtils.showProgressDialog(this@UserTableScreenActivity)
        MyApplication.getRestClient()
            ?.API_GET_Manager_TableList(
                PreferenceManager.getPref(
                    Constants.Preference.PREF_CLIENT_USERID, ""
                )!!,
                intent.getStringExtra("mFunId")!!
            )
            ?.enqueue(object :
                Callback<ResponseBase<ManagerTableListResponse>> {
                override fun onResponse(
                    call: Call<ResponseBase<ManagerTableListResponse>?>?,
                    response: Response<ResponseBase<ManagerTableListResponse>?>
                ) {
                    CommonUtils.hideProgressDialog()
                    if (response.isSuccessful) {
                        if (response.body()?.mSuccess!!) {
                            val linearLayoutManager = LinearLayoutManager(
                                this@UserTableScreenActivity, RecyclerView.VERTICAL, false
                            )

                            val mItemAdapter = AssignTableAdapter(
                                false,
                                this@UserTableScreenActivity,
                                response.body()!!.mData?.getManagerTableAssignDetails() as ArrayList<ManagerTableListResponse.ManagerTableAssignDetail>
                            )
                            binding.rvTableList.layoutManager = linearLayoutManager
                            binding.rvTableList.adapter = mItemAdapter

                        } else {
                            Log.d("Mytag", response.body()?.mError!!)
                        }
                    } else {
                        Toast.makeText(
                            this@UserTableScreenActivity,
                            response.message(),
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }

                override fun onFailure(
                    call: Call<ResponseBase<ManagerTableListResponse>?>,
                    t: Throwable
                ) {
                    CommonUtils.hideProgressDialog()
                    Log.d("MyTAG", "onFailure: " + t.message)
                }
            })
    }
}