package com.example.justweddingpro.ui.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.crmapplication.MyApplication
import com.example.justweddingpro.ClientUi.Response.ManagerTableListResponse
import com.example.justweddingpro.R
import com.example.justweddingpro.Response.AssignTableListResponse
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

        mApiGetMenuPlanningList()
    }

    private fun mApiGetMenuPlanningList() {
        CommonUtils.showProgressDialog(requireActivity())
        MyApplication.getRestClient()?.API_GetAssignMenuTable(
            UserAssignFunctionListActivity.mUserId,
            BasedActivity.mFunctionId
        )
            ?.enqueue(object : Callback<ResponseBase<AssignTableListResponse>> {
                override fun onResponse(
                    call: Call<ResponseBase<AssignTableListResponse>>?,
                    response: Response<ResponseBase<AssignTableListResponse>?>
                ) {
                    if (response.isSuccessful) {
                        CommonUtils.hideProgressDialog()
                        if (response.body()?.mSuccess!!) {
                            val linearLayoutManager = LinearLayoutManager(
                                requireActivity(), RecyclerView.VERTICAL, false
                            )

                            val mItemAdapter = AssignTableAdapter(
                                requireActivity(),
                                response.body()!!.mData?.getManagerTableAssignDetails() as ArrayList<ManagerTableListResponse.ManagerTableAssignDetail>
                            )
                            rvTableList.layoutManager = linearLayoutManager
                            rvTableList.adapter = mItemAdapter

                        } else {
                            CommonUtils.hideProgressDialog()
                            Log.d("Mytag", response.body()?.mError!!)
                        }
                    }
                }

                override fun onFailure(
                    call: Call<ResponseBase<AssignTableListResponse>>, t: Throwable
                ) {
                    CommonUtils.hideProgressDialog()
                    Log.d("MyTAG", "onFailure: " + t.message)
                }
            })
    }
}