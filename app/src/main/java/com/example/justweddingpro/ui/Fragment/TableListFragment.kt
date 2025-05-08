package com.example.justweddingpro.ui.Fragment

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.crmapplication.MyApplication
import com.example.justweddingpro.ClientUi.Response.ManagerTableListResponse
import com.example.justweddingpro.R
import com.example.justweddingpro.Response.TableListResponse
import com.example.justweddingpro.ui.BasedActivity
import com.example.justweddingpro.ui.Response.ResponseBase
import com.example.justweddingpro.ui.UserAssignFunctionListActivity
import com.example.justweddingpro.ui.adapter.AssignTableAdapter
import com.example.justweddingpro.utils.CommonUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TableListFragment : Fragment() {

    private lateinit var rvTableList: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_table_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvTableList = view.findViewById(R.id.rvTableList)

        mApiShowOrderTableList()
    }


    private fun mApiShowOrderTableList() {
        CommonUtils.showProgressDialog(requireActivity())
        MyApplication.getRestClient()
            ?.API_GET_Manager_TableList(
                UserAssignFunctionListActivity.mUserId,
                BasedActivity.mFunctionId
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
                                requireActivity(), RecyclerView.VERTICAL, false
                            )

                            val mItemAdapter = AssignTableAdapter(
                                true,
                                requireActivity(),
                                response.body()!!.mData?.getManagerTableAssignDetails() as ArrayList<ManagerTableListResponse.ManagerTableAssignDetail>
                            )
                            rvTableList.layoutManager = linearLayoutManager
                            rvTableList.adapter = mItemAdapter

                            mItemAdapter.setOnItemClickListener(object :
                                AssignTableAdapter.OnItemClickListener {
                                override fun onButtonClick(position: Int) {
                                    CommonUtils.confirmShowDeleteDialog(requireActivity(),
                                        "Are you sure want to Delete?",
                                        object : CommonUtils.Companion.OnDialogClickListener {
                                            override fun OnYesClick(dialog: Dialog) {
                                                mApiDeleteTable(
                                                    response.body()!!.mData?.getManagerTableAssignDetails()!![position]?.getTableAssignId()
                                                        .toString()
                                                )
                                            }

                                            override fun OnNoClick(dialog: Dialog) {
                                            }

                                        })
                                }
                            })

                        } else {
                            Log.d("Mytag", response.body()?.mError!!)
                        }
                    } else {
                        Toast.makeText(requireActivity(),response.message(),Toast.LENGTH_SHORT).show()
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

    private fun mApiDeleteTable(assignFunId: String) {
        CommonUtils.showProgressDialog(requireActivity())
        MyApplication.getRestClient()?.API_GetDeleteAssignTable(assignFunId)
            ?.enqueue(object : Callback<ResponseBase<TableListResponse>> {
                override fun onResponse(
                    call: Call<ResponseBase<TableListResponse>?>?,
                    response: Response<ResponseBase<TableListResponse>?>
                ) {
                    CommonUtils.hideProgressDialog()
                    if (response.isSuccessful) {
                        if (response.body()?.mSuccess!!) {
                            mApiShowOrderTableList()
                        } else {
                            Log.d("Mytag", response.body()?.mError!!)
                        }
                    } else {
                        Toast.makeText(requireActivity(), response.message(), Toast.LENGTH_SHORT)
                            .show()
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