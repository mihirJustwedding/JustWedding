package com.example.justweddingpro.ui.Fragment

import android.content.Intent
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
import com.example.justweddingpro.Response.ManagerListResponse
import com.example.justweddingpro.ui.ManagerAndCaptainListActivity
import com.example.justweddingpro.ui.ManagerAndCaptainListActivity.Companion.mClientUserDetail
import com.example.justweddingpro.ui.Response.ResponseBase
import com.example.justweddingpro.ui.UserAssignFunctionListActivity
import com.example.justweddingpro.ui.adapter.CaptainListAdapter
import com.example.justweddingpro.utils.CommonUtils
import com.example.justweddingpro.utils.Constants
import com.example.justweddingpro.utils.PreferenceManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CaptionsListFragment(var Isfunction: Boolean) : Fragment() {

    private lateinit var rvLayout: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_captions_liat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvLayout = view.findViewById(R.id.rvLayout)

        ApiGetMAnagerList()
    }

    private fun ApiGetMAnagerList() {
        CommonUtils.showProgressDialog(requireActivity())
        MyApplication.getRestClient()?.API_GetManagerAndCaptain(
            PreferenceManager.getPref(
                Constants.Preference.PREF_CLIENT_USERID, ""
            )!!, "captain"
        )?.enqueue(object : Callback<ResponseBase<ManagerListResponse>> {
            override fun onResponse(
                call: Call<ResponseBase<ManagerListResponse>?>?,
                response: Response<ResponseBase<ManagerListResponse>?>
            ) {
                CommonUtils.hideProgressDialog()
                if (response.isSuccessful) {
                    if (response.body()?.mData != null) {

                        val linearLayoutManager = LinearLayoutManager(
                            requireActivity(), RecyclerView.VERTICAL, false
                        )

                        val mItemAdapter = CaptainListAdapter(
                            requireActivity(),
                            response.body()!!.mData?.getClientUserDetails() as List<ManagerListResponse.ClientUserDetail>,
                            Isfunction
                        )
                        rvLayout.layoutManager = linearLayoutManager
                        rvLayout.adapter = mItemAdapter

                        mItemAdapter.SetOnclickListner(object : CaptainListAdapter.OnclickListner {
                            override fun onclick(position: Int) {
                                mClientUserDetail =
                                    response.body()!!.mData?.getClientUserDetails()?.get(position)!!
                                startActivity(
                                    Intent(
                                        requireActivity(),
                                        UserAssignFunctionListActivity::class.java
                                    ).putExtra(
                                        "Ides", response.body()!!.mData?.getClientUserDetails()
                                            ?.get(position)?.eventfunctionId
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
                call: Call<ResponseBase<ManagerListResponse>?>, t: Throwable
            ) {
                CommonUtils.hideProgressDialog()
                Log.d("MyTAG", "onFailure: " + t.message)
            }
        })
    }
}