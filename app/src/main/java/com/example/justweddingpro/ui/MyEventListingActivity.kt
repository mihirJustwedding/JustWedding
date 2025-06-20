package com.example.justweddingpro.ui

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.crmapplication.MyApplication
import com.example.justweddingpro.R
import com.example.justweddingpro.Response.TableListResponse
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

        binding.btnSignin.setOnClickListener {
            MyEventDetailsActivity.mIsEdite = false
            startActivity(Intent(this@MyEventListingActivity, CreateEventActivity::class.java))
        }
        mApiCalling()
    }

    var mList = ArrayList<EventDetailsResponse.EventMasterDetail>()
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
                            mList =
                                response.body()!!.mData!!.getEventMasterDetails() as ArrayList<EventDetailsResponse.EventMasterDetail>

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
//                            mEventListAdapter.SetOnclickListner(object :
//                                EventListAdapter.OnclickListner {
//                                override fun onclick(position: Int) {
//
//                                }
//                            })

                            mEventListAdapter.SetOnPopupclickListner(object :
                                EventListAdapter.PopupOnclickListner {
                                override fun onclick(view: View, position: Int) {
                                    val popupwindow_obj =
                                        popupDisplay(position, mList[position].eventId.toString())
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

    fun popupDisplay(position: Int, mEventId: String): PopupWindow {
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

        var tvEdite = view.findViewById<TextView>(R.id.tvEdite)
        var tvView = view.findViewById<TextView>(R.id.tvView)
        var tvPlanning = view.findViewById<TextView>(R.id.tvPlanning)
        var tvMenuReport = view.findViewById<TextView>(R.id.tvMenuReport)
        var tvAssignFunc = view.findViewById<TextView>(R.id.tvAssignFunc)
        var tvDelete = view.findViewById<TextView>(R.id.tvDelete)
        var tvPdf = view.findViewById<TextView>(R.id.tvPdf)
        var tvAgency = view.findViewById<TextView>(R.id.tvAgency)

        BasedActivity.mEventId = mEventId

        tvAssignFunc.visibility = View.GONE
        tvPdf.visibility = View.GONE
        tvAgency.visibility = View.GONE

        tvView.setOnClickListener {
            popupWindow.dismiss()
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

        tvEdite.setOnClickListener {
            popupWindow.dismiss()
            MyEventDetailsActivity.mIsEdite = true
            startActivity(
                Intent(
                    this@MyEventListingActivity,
                    CreateEventActivity::class.java
                )
            )
        }

        tvPlanning.setOnClickListener {
            popupWindow.dismiss()
            startActivity(
                Intent(
                    this@MyEventListingActivity,
                    MenuActivity::class.java
                )
            )
        }

        tvDelete.setOnClickListener {
            popupWindow.dismiss()
            CommonUtils.confirmShowDeleteDialog(this@MyEventListingActivity,
                "Are you sure want to Delete?",
                object : CommonUtils.Companion.OnDialogClickListener {
                    override fun OnYesClick(dialog: Dialog) {
                        mApiDeleteTable(mEventId)
                    }

                    override fun OnNoClick(dialog: Dialog) {
                    }

                })
        }

        tvMenuReport.setOnClickListener {
            popupWindow.dismiss()
            startActivity(
                Intent(
                    this@MyEventListingActivity,
                    PDFViewerActivity::class.java
                ).putExtra("isevent", "1")
            )
        }

        return popupWindow
    }

    private fun mApiDeleteTable(EventId: String) {
        CommonUtils.showProgressDialog(this@MyEventListingActivity)
        MyApplication.getRestClient()?.API_GetDeleteEvent(EventId)
            ?.enqueue(object : Callback<ResponseBase<TableListResponse>> {
                override fun onResponse(
                    call: Call<ResponseBase<TableListResponse>?>?,
                    response: Response<ResponseBase<TableListResponse>?>
                ) {
                    CommonUtils.hideProgressDialog()
                    if (response.isSuccessful) {
                        if (response.body()?.mSuccess!!) {
                            mApiCalling()
                        } else {
                            Log.d("Mytag", response.body()?.mError!!)
                        }
                    } else {
                        Toast.makeText(
                            this@MyEventListingActivity,
                            response.message(),
                            Toast.LENGTH_SHORT
                        )
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