package com.example.justweddingpro.ui

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.crmapplication.MyApplication
import com.example.justweddingpro.Response.AssignFunctionResponse
import com.example.justweddingpro.Response.TableListResponse
import com.example.justweddingpro.databinding.ActivityUserAssignFunctionListBinding
import com.example.justweddingpro.ui.Response.ResponseBase
import com.example.justweddingpro.ui.adapter.AssignFunctionListAdapter
import com.example.justweddingpro.utils.CommonUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserAssignFunctionListActivity : BasedActivity() {

    private lateinit var binding: ActivityUserAssignFunctionListBinding

    companion object {
        var mUserId = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserAssignFunctionListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.headerTitle.headerTitle.text = "Function List"
        binding.headerTitle.frdIcon.setOnClickListener { onBackPressed() }

        mUserId = intent.getIntExtra("Ides", 0).toString()
        ApiGetMAnagerList()

    }

    private fun ApiGetMAnagerList() {
        CommonUtils.showProgressDialog(this@UserAssignFunctionListActivity)
        MyApplication.getRestClient()?.API_GetFunctionAssignList(
            mUserId
        )?.enqueue(object : Callback<ResponseBase<AssignFunctionResponse>> {
            override fun onResponse(
                call: Call<ResponseBase<AssignFunctionResponse>?>?,
                response: Response<ResponseBase<AssignFunctionResponse>?>
            ) {
                CommonUtils.hideProgressDialog()
                if (response.isSuccessful) {
                    if (response.body()?.mData != null) {

                        val linearLayoutManager = LinearLayoutManager(
                            this@UserAssignFunctionListActivity, RecyclerView.VERTICAL, false
                        )

                        val mItemAdapter = AssignFunctionListAdapter(
                            this@UserAssignFunctionListActivity,
                            response.body()!!.mData?.getFunctionManagerAssignDetails() as List<AssignFunctionResponse.FunctionManagerAssignDetail>
                        )
                        binding.rvFunctionList.layoutManager = linearLayoutManager
                        binding.rvFunctionList.adapter = mItemAdapter

                        mItemAdapter.SetOnclickListner(object :
                            AssignFunctionListAdapter.OnclickListner {
                            override fun onclick(position: Int) {
                                mEventId =
                                    response.body()!!.mData?.getFunctionManagerAssignDetails()!![position]?.eventId.toString()
                                mFunctionId =
                                    response.body()!!.mData?.getFunctionManagerAssignDetails()!![position]?.functionId.toString()

                                startActivity(
                                    Intent(
                                        this@UserAssignFunctionListActivity,
                                        FunctionDetailsActivity::class.java
                                    )
                                )
                            }
                        })

                        mItemAdapter.SetOnEditeclickListner(object :
                            AssignFunctionListAdapter.OnclickListner {
                            override fun onclick(position: Int) {
                                CommonUtils.confirmShowDeleteDialog(this@UserAssignFunctionListActivity,
                                    "Are you sure want to Delete?",
                                    object : CommonUtils.Companion.OnDialogClickListener {
                                        override fun OnYesClick(dialog: Dialog) {
                                            mApiDeleteFunction(response.body()!!.mData?.getFunctionManagerAssignDetails()!![position]?.functionAssignId.toString())
                                        }

                                        override fun OnNoClick(dialog: Dialog) {
                                        }

                                    })
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

    private fun mApiDeleteFunction(assignFunId: String) {
        CommonUtils.showProgressDialog(this@UserAssignFunctionListActivity)
        MyApplication.getRestClient()?.API_GetDeleteAssignFunction(assignFunId)
            ?.enqueue(object : Callback<ResponseBase<TableListResponse>> {
                override fun onResponse(
                    call: Call<ResponseBase<TableListResponse>?>?,
                    response: Response<ResponseBase<TableListResponse>?>
                ) {
                    CommonUtils.hideProgressDialog()
                    if (response.isSuccessful) {
                        if (response.body()?.mSuccess!!) {
                            ApiGetMAnagerList()
                        } else {
                            Log.d("Mytag", response.body()?.mError!!)
                        }
                    }
                }

                override fun onFailure(
                    call: Call<ResponseBase<TableListResponse>?>, t: Throwable
                ) {
                    CommonUtils.hideProgressDialog()
                    Log.d("MyTAG", "onFailure: " + t.message)
                }
            })
    }

}