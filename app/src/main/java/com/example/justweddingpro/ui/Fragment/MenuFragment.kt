package com.example.justweddingpro.ui.Fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.PopupWindow
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.crmapplication.MyApplication
import com.example.justweddingpro.R
import com.example.justweddingpro.ui.MyEventDetailsActivity
import com.example.justweddingpro.ui.Response.EventDetailsResponse
import com.example.justweddingpro.ui.Response.ResponseBase
import com.example.justweddingpro.ui.adapter.EventListAdapter
import com.example.justweddingpro.utils.CommonUtils
import com.example.justweddingpro.utils.Constants
import com.example.justweddingpro.utils.PreferenceManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MenuFragment : Fragment() {

    private lateinit var rvEventList: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvEventList = view.findViewById(R.id.rvEventList)

        mApiCalling()
    }

    private fun mApiCalling() {
        CommonUtils.showProgressDialog(requireActivity())
        MyApplication.getRestClient()
            ?.GET_EventList(
                PreferenceManager.getPref(
                    Constants.Preference.PREF_CLIENT_USERID,
                    ""
                )!!
            )
            ?.enqueue(object :
                Callback<ResponseBase<EventDetailsResponse>> {
                override fun onResponse(
                    call: Call<ResponseBase<EventDetailsResponse>?>?,
                    response: Response<ResponseBase<EventDetailsResponse>?>
                ) {
                    CommonUtils.hideProgressDialog()
                    if (response.isSuccessful) {
                        if (response.body()?.mData != null) {
                            var mList = response.body()!!.mData!!.getEventMasterDetails()

                            val mEventListAdapter = EventListAdapter(
                                requireActivity(),
                                mList as List<EventDetailsResponse.EventMasterDetail>
                            )

                            val linearLayoutManager = LinearLayoutManager(
                                requireActivity(),
                                RecyclerView.VERTICAL,
                                false
                            )
                            rvEventList.layoutManager = linearLayoutManager
                            rvEventList.adapter = mEventListAdapter
                            mEventListAdapter.SetOnclickListner(object :
                                EventListAdapter.OnclickListner {
                                override fun onclick(position: Int) {
                                    PreferenceManager.setPref(
                                        Constants.Preference.Pref_EVENTId,
                                        mList[position].eventId.toString()
                                    )
                                    startActivity(
                                        Intent(
                                            requireActivity(),
                                            MyEventDetailsActivity::class.java
                                        )
                                    )
                                }
                            })

                            mEventListAdapter.SetOnPopupclickListner(object :
                                EventListAdapter.PopupOnclickListner {
                                override fun onclick(view: View) {
                                    val popupwindow_obj = popupDisplay()
                                    popupwindow_obj.showAsDropDown(
                                        view,
                                        -40,
                                        18
                                    )
                                }
                            })

                        } else {
                            Log.d("Mytag", response.body()?.mError!!)
                        }
                    }
                }

                override fun onFailure(
                    call: Call<ResponseBase<EventDetailsResponse>?>,
                    t: Throwable
                ) {
                    CommonUtils.hideProgressDialog()
                    Log.d("MyTAG", "onFailure: " + t.message)
                }
            })
    }

    fun popupDisplay(): PopupWindow {
        val popupWindow = PopupWindow(requireActivity())

        // inflate your layout or dynamically add view
        val inflater =
            requireActivity().getSystemService(AppCompatActivity.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = inflater.inflate(R.layout.popupmenu_layout, null)
        popupWindow.isFocusable = true
        popupWindow.width = WindowManager.LayoutParams.WRAP_CONTENT
        popupWindow.height = WindowManager.LayoutParams.WRAP_CONTENT
        popupWindow.contentView = view
        popupWindow.elevation = 5f
        popupWindow.setBackgroundDrawable(requireActivity().getDrawable(R.drawable.popup_menu_background))
        return popupWindow
    }

}