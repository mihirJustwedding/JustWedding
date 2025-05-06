package com.example.justweddingpro.ClientUi

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.crmapplication.MyApplication
import com.example.justweddingpro.ClientUi.adapter.ManagerFunctionListAdapter
import com.example.justweddingpro.Response.AssignFunctionResponse
import com.example.justweddingpro.databinding.ActivityUserFunctionListBinding
import com.example.justweddingpro.ui.Response.ResponseBase
import com.example.justweddingpro.utils.CommonUtils
import com.example.justweddingpro.utils.Constants
import com.example.justweddingpro.utils.PreferenceManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserFunctionListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserFunctionListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserFunctionListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.headerTitle.headerTitle.text = "Function List"
        binding.headerTitle.frdIcon.setOnClickListener { onBackPressed() }

        ApiGetMAnagerList()
    }

    private fun ApiGetMAnagerList() {
        CommonUtils.showProgressDialog(this@UserFunctionListActivity)
        MyApplication.getRestClient()?.API_GetFunctionAssignList(
            PreferenceManager.getPref(
                Constants.Preference.PREF_CLIENT_USERID, ""
            )!!
        )?.enqueue(object : Callback<ResponseBase<AssignFunctionResponse>> {
            override fun onResponse(
                call: Call<ResponseBase<AssignFunctionResponse>?>?,
                response: Response<ResponseBase<AssignFunctionResponse>?>
            ) {
                CommonUtils.hideProgressDialog()
                if (response.isSuccessful) {
                    if (response.body()?.mData != null) {

                        val linearLayoutManager = LinearLayoutManager(
                            this@UserFunctionListActivity, RecyclerView.VERTICAL, false
                        )

                        val mItemAdapter = ManagerFunctionListAdapter(
                            this@UserFunctionListActivity,
                            response.body()!!.mData?.getFunctionManagerAssignDetails() as List<AssignFunctionResponse.FunctionManagerAssignDetail>
                        )
                        binding.rvFunctionList.layoutManager = linearLayoutManager
                        binding.rvFunctionList.adapter = mItemAdapter

                        mItemAdapter.SetOnclickListner(object :
                            ManagerFunctionListAdapter.OnclickListner {
                            override fun onclick(position: Int) {
                                startActivity(
                                    Intent(
                                        this@UserFunctionListActivity,
                                        UserTableScreenActivity::class.java
                                    ).putExtra(
                                        "mFunId",
                                        response.body()!!.mData?.getFunctionManagerAssignDetails()
                                            ?.get(position)?.functionId.toString()
                                    )
                                )
                            }
                        })

                    } else {
                        Log.d("Mytag", response.body()?.mError!!)
                    }
                }
            }

            override fun onFailure(
                call: Call<ResponseBase<AssignFunctionResponse>?>, t: Throwable
            ) {
                CommonUtils.hideProgressDialog()
                Log.d("MyTAG", "onFailure: " + t.message)
            }
        })
    }
}