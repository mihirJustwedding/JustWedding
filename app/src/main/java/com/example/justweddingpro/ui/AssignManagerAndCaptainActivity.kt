package com.example.justweddingpro.ui

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.crmapplication.MyApplication
import com.example.justweddingpro.Network.RequestModel.AddTableRequest
import com.example.justweddingpro.Network.RequestModel.ManagerTableAssignRequest
import com.example.justweddingpro.R
import com.example.justweddingpro.Response.TableListResponse
import com.example.justweddingpro.databinding.ActivityAssignManagerAndCaptainBinding
import com.example.justweddingpro.ui.Response.AssignManagerAndCaptainResponse
import com.example.justweddingpro.ui.Response.ResponseBase
import com.example.justweddingpro.ui.adapter.AssignManagerandCaptainAdapter
import com.example.justweddingpro.ui.adapter.TableListAdapter
import com.example.justweddingpro.utils.CommonUtils
import com.example.justweddingpro.utils.Constants
import com.example.justweddingpro.utils.PreferenceManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AssignManagerAndCaptainActivity : BasedActivity() {

    private lateinit var binding: ActivityAssignManagerAndCaptainBinding

    companion object {
        var mSelectedList = ArrayList<Int>()
    }


    private var ClentUSerID = ""
    private var eventId = ""
    private var functionId = ""
    private var functionManagaerId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAssignManagerAndCaptainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.Headerlayout.headerTitle.setText("Assigned")
        binding.Headerlayout.frdIcon.setOnClickListener { onBackPressed() }
        binding.Headerlayout.imgAdd.visibility = View.VISIBLE
        binding.Headerlayout.imgAdd.setOnClickListener {
            mAddCategoryFunctionDialog()
        }

        ApiGetManagerAssign()
    }

    private fun ApiGetManagerAssign() {
        CommonUtils.showProgressDialog(this@AssignManagerAndCaptainActivity)
        MyApplication.getRestClient()?.API_GetAssignManagerCaptain(mEventId, mFunctionId)
            ?.enqueue(object : Callback<ResponseBase<AssignManagerAndCaptainResponse>> {
                override fun onResponse(
                    call: Call<ResponseBase<AssignManagerAndCaptainResponse>?>?,
                    response: Response<ResponseBase<AssignManagerAndCaptainResponse>?>
                ) {
                    CommonUtils.hideProgressDialog()
                    if (response.isSuccessful) {
                        if (response.body()?.mSuccess!!) {
                            val linearLayoutManager = LinearLayoutManager(
                                this@AssignManagerAndCaptainActivity, RecyclerView.VERTICAL, false
                            )

                            val mItemAdapter = AssignManagerandCaptainAdapter(
                                this@AssignManagerAndCaptainActivity,
                                response.body()?.mData?.getFunctionManagerAssignDetails() as List<AssignManagerAndCaptainResponse.FunctionManagerAssignDetail>
                            )
                            binding.rvLayout.layoutManager = linearLayoutManager
                            binding.rvLayout.adapter = mItemAdapter

                            mItemAdapter.SetOnclickListner(object :
                                AssignManagerandCaptainAdapter.OnclickListner {
                                override fun onclick(
                                    position: Int, mDetail: AssignManagerAndCaptainResponse.Detail
                                ) {
                                    ClentUSerID = mDetail.clientUserId.toString()
                                    eventId = mDetail.eventId.toString()
                                    functionId = mDetail.functionId.toString()
                                    functionManagaerId = mDetail.functionAssignId.toString()

                                    ApiGetTableList()
                                }
                            })

                        } else {
                            Log.d("Mytag", response.body()?.mError!!)
                        }
                    }
                }

                override fun onFailure(
                    call: Call<ResponseBase<AssignManagerAndCaptainResponse>?>, t: Throwable
                ) {
                    CommonUtils.hideProgressDialog()
                    Log.d("MyTAG", "onFailure: " + t.message)
                }
            })
    }

    private fun onBottomSheetDialog(mList: ArrayList<TableListResponse.TableMasterDetail>) {
        val dialog =
            BottomSheetDialog(this@AssignManagerAndCaptainActivity, R.style.BottomSheetDialogTheme)
        val view = layoutInflater.inflate(R.layout.item_bottomsheet_tables_dialog, null)
        val imgClose = view.findViewById(R.id.imgClose) as ImageView
        val rvTables = view.findViewById(R.id.rvTables) as RecyclerView
        val btnNext = view.findViewById(R.id.btnNext) as AppCompatButton
        val btnBack = view.findViewById(R.id.btnBack) as AppCompatButton

        btnBack.setOnClickListener { imgClose.performClick() }
        imgClose.setOnClickListener {
            dialog.dismiss()
        }

        btnNext.setOnClickListener {
            dialog.dismiss()
            ApiManagerAssignTable()
        }

        val linearLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        val mItemAdapter = TableListAdapter(this, mList)
        rvTables.layoutManager = linearLayoutManager
        rvTables.adapter = mItemAdapter

        dialog.setCancelable(true)
        dialog.setContentView(view)
        dialog.show()
    }

    private fun ApiGetTableList() {
        CommonUtils.showProgressDialog(this@AssignManagerAndCaptainActivity)
        MyApplication.getRestClient()?.API_GetTableList(
            PreferenceManager.getPref(
                Constants.Preference.PREF_CLIENT_USERID, ""
            )!!
        )?.enqueue(object : Callback<ResponseBase<TableListResponse>> {
            override fun onResponse(
                call: Call<ResponseBase<TableListResponse>?>?,
                response: Response<ResponseBase<TableListResponse>?>
            ) {
                CommonUtils.hideProgressDialog()
                if (response.isSuccessful) {
                    if (response.body()?.mSuccess!!) {
                        onBottomSheetDialog(
                            response.body()!!.mData?.getTableMasterDetails() as ArrayList<TableListResponse.TableMasterDetail>
                        )
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

    private fun ApiManagerAssignTable() {
        var mManagerTableAssignRequest = ManagerTableAssignRequest()

        mManagerTableAssignRequest.setClientUserId(ClentUSerID.toInt())
        mManagerTableAssignRequest.setEventId(eventId.toInt())
        mManagerTableAssignRequest.setFunctionId(functionId.toInt())
        mManagerTableAssignRequest.setFunctionManagaerId(functionManagaerId.toInt())
        mManagerTableAssignRequest.setTableId(mSelectedList)

        CommonUtils.showProgressDialog(this@AssignManagerAndCaptainActivity)
        MyApplication.getRestClient()?.API_UserTableAssign(mManagerTableAssignRequest)
            ?.enqueue(object : Callback<ResponseBase<TableListResponse>> {
                override fun onResponse(
                    call: Call<ResponseBase<TableListResponse>?>?,
                    response: Response<ResponseBase<TableListResponse>?>
                ) {
                    CommonUtils.hideProgressDialog()
                    if (response.isSuccessful) {
                        if (response.body()?.mSuccess!!) {
                            mSelectedList.clear()
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

    private fun mAddCategoryFunctionDialog() {
        val dialog = Dialog(this@AssignManagerAndCaptainActivity, R.style.TransparentStyle)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.item_add_table_dialog)
        dialog.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT
        )
        dialog.setCancelable(false)
        val edtTableName = dialog.findViewById<AppCompatEditText>(R.id.edtTableName)

        val btnBack = dialog.findViewById<AppCompatButton>(R.id.btnBack)
        val btnNext = dialog.findViewById<AppCompatButton>(R.id.btnNext)

        btnNext.setOnClickListener {
            ApiCreateTable(
                edtTableName.text.toString().trim(),
            )
            dialog.dismiss()
        }

        btnBack.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun ApiCreateTable(mTableName: String) {

        val mAddTableRequest = AddTableRequest()
        mAddTableRequest.setTableId(0)
        mAddTableRequest.setTableName(mTableName)
        mAddTableRequest.setIsActive(1)
        mAddTableRequest.setClientUserId(
            PreferenceManager.getPref(
                Constants.Preference.PREF_CLIENT_USERID, ""
            )!!
        )

        CommonUtils.showProgressDialog(this@AssignManagerAndCaptainActivity)
        MyApplication.getRestClient()?.API_AddTable(mAddTableRequest)
            ?.enqueue(object : Callback<ResponseBase<TableListResponse>> {
                override fun onResponse(
                    call: Call<ResponseBase<TableListResponse>?>?,
                    response: Response<ResponseBase<TableListResponse>?>
                ) {
                    CommonUtils.hideProgressDialog()
                    if (response.isSuccessful) {
                        if (response.body()?.mSuccess!!) {
                            ApiGetTableList()
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