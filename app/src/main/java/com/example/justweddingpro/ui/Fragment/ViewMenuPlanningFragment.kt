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
import com.example.justweddingpro.R
import com.example.justweddingpro.ui.BasedActivity
import com.example.justweddingpro.ui.Response.EvenMenuPlanningDetails
import com.example.justweddingpro.ui.Response.ResponseBase
import com.example.justweddingpro.ui.UserAssignFunctionListActivity
import com.example.justweddingpro.ui.adapter.ViewMenuPlanningAdapter
import com.example.justweddingpro.utils.CommonUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ViewMenuPlanningFragment : Fragment() {

    private lateinit var rvMenuPlanning: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_view_menu_planning, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvMenuPlanning = view.findViewById(R.id.rvMenuPlanning)

        mApiGetMenuPlanningList()
    }

    private fun mApiGetMenuPlanningList() {
        CommonUtils.showProgressDialog(requireActivity())
        MyApplication.getRestClient()?.API_GetMenuPlanningDetails(
            BasedActivity.mEventId,
            BasedActivity.mFunctionId
        )
            ?.enqueue(object : Callback<ResponseBase<EvenMenuPlanningDetails>> {
                override fun onResponse(
                    call: Call<ResponseBase<EvenMenuPlanningDetails>?>?,
                    response: Response<ResponseBase<EvenMenuPlanningDetails>?>
                ) {
                    CommonUtils.hideProgressDialog()
                    if (response.isSuccessful) {
                        if (response.body()?.mSuccess!!) {
                            val linearLayoutManager = LinearLayoutManager(
                                requireActivity(), RecyclerView.VERTICAL, false
                            )

                            val mItemAdapter = ViewMenuPlanningAdapter(
                                requireActivity(),
                                response.body()!!.mData?.getEventMenuPlanDetails() as ArrayList<EvenMenuPlanningDetails.EventMenuPlanDetail>
                            )
                            rvMenuPlanning.layoutManager = linearLayoutManager
                            rvMenuPlanning.adapter = mItemAdapter

                        } else {
                            Log.d("Mytag", response.body()?.mError!!)
                        }
                    }
                }

                override fun onFailure(
                    call: Call<ResponseBase<EvenMenuPlanningDetails>?>, t: Throwable
                ) {
                    CommonUtils.hideProgressDialog()
                    Log.d("MyTAG", "onFailure: " + t.message)
                }
            })
    }
}