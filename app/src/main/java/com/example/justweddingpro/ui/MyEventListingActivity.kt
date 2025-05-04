package com.example.justweddingpro.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.PopupWindow
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.crmapplication.MyApplication
import com.example.justweddingpro.R
import com.example.justweddingpro.databinding.ActivityMyEventListingBinding
import com.example.justweddingpro.ui.Response.EventDetailsResponse
import com.example.justweddingpro.ui.Response.ResponseBase
import com.example.justweddingpro.ui.adapter.EventListAdapter
import com.example.justweddingpro.utils.CommonUtils
import com.example.justweddingpro.utils.Constants
import com.example.justweddingpro.utils.PreferenceManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyEventListingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyEventListingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyEventListingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.headerview.headerTitle.setText("My Events")
        binding.headerview.frdIcon.setOnClickListener {
            onBackPressed()
        }
        mApiCalling()
    }

    private fun mApiCalling() {
        CommonUtils.showProgressDialog(this@MyEventListingActivity)
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
                                this@MyEventListingActivity,
                                mList as List<EventDetailsResponse.EventMasterDetail>
                            )

                            val linearLayoutManager = LinearLayoutManager(
                                this@MyEventListingActivity,
                                RecyclerView.VERTICAL,
                                false
                            )
                            binding.rvEventList.layoutManager = linearLayoutManager
                            binding.rvEventList.adapter = mEventListAdapter
                            mEventListAdapter.SetOnclickListner(object :
                                EventListAdapter.OnclickListner {
                                override fun onclick(position: Int) {
                                    PreferenceManager.setPref(
                                        Constants.Preference.Pref_EVENTId,
                                        mList[position].eventId.toString()
                                    )
                                    startActivity(
                                        Intent(
                                            this@MyEventListingActivity,
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
        val popupWindow = PopupWindow(this)

        // inflate your layout or dynamically add view
        val inflater = this.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = inflater.inflate(R.layout.popupmenu_layout, null)
        popupWindow.isFocusable = true
        popupWindow.width = WindowManager.LayoutParams.WRAP_CONTENT
        popupWindow.height = WindowManager.LayoutParams.WRAP_CONTENT
        popupWindow.contentView = view
        popupWindow.elevation = 5f
        popupWindow.setBackgroundDrawable(getDrawable(R.drawable.popup_menu_background))
        return popupWindow
    }
}