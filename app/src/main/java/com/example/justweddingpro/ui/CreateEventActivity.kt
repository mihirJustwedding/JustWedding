package com.example.justweddingpro.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.crmapplication.MyApplication
import com.example.justweddingpro.Network.RequestModel.AddEventRequest
import com.example.justweddingpro.Network.RequestModel.EventFunctionDetail
import com.example.justweddingpro.R
import com.example.justweddingpro.Response.AddEventResponse
import com.example.justweddingpro.databinding.ActivityEventCreateBinding
import com.example.justweddingpro.ui.Fragment.FunctionDetailsFragment
import com.example.justweddingpro.ui.MyEventDetailsActivity.Companion.mIsEdite
import com.example.justweddingpro.ui.Response.EventDetailsResponse
import com.example.justweddingpro.ui.Response.ResponseBase
import com.example.justweddingpro.ui.adapter.FragmentEventAdapter
import com.example.justweddingpro.utils.CommonUtils
import com.example.justweddingpro.utils.Constants
import com.example.justweddingpro.utils.PreferenceManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CreateEventActivity : AppCompatActivity() {

    lateinit var binding: ActivityEventCreateBinding

    companion object {
        var mEventAddRequest = AddEventRequest()
        var mEventDetailsResponse = EventDetailsResponse.EventMasterDetail()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_event_create)
        setContentView(binding.root)

        binding.headerView.frdIcon.setOnClickListener {
            onBackPressed()
        }
        binding.headerView.headerTitle.text = "Create Event"

        binding.btnBack.setOnClickListener {
            mRedirectBack()
        }

        if (PreferenceManager.getPref(
                Constants.Preference.Pref_EVENTId,
                ""
            )!!.isEmpty()
        ) {
            SetAdapter()
        } else {
            mApiGetEventDetails()
        }

    }

    fun mRedirectBack() {
        mIsEdite = false
        if (binding.mViewPagerIntro.currentItem == 3) binding.mViewPagerIntro.currentItem = 2
        else if (binding.mViewPagerIntro.currentItem == 2) {
            binding.mViewPagerIntro.currentItem = 1
        } else if (binding.mViewPagerIntro.currentItem == 1) {
            binding.mViewPagerIntro.currentItem = 0
        } else {
            onBackPressed()
        }
    }

    fun mRedirectNext() {
        if (binding.mViewPagerIntro.currentItem == 0) {
            binding.mViewPagerIntro.currentItem = 1
        } else if (binding.mViewPagerIntro.currentItem == 1) {
            binding.mViewPagerIntro.currentItem = 2
        } else if (binding.mViewPagerIntro.currentItem == 2) {
            if (mIsEdite) {
                mEventAddRequest.eventId = PreferenceManager.getPref(
                    Constants.Preference.Pref_EVENTId, ""
                )!!.toInt()
                mApiCalling()
            } else {
                binding.mViewPagerIntro.currentItem = 3
            }
        } else {
            mApiCalling()
        }
    }

    fun mApiCalling() {
        CommonUtils.showProgressDialog(this@CreateEventActivity)
        mEventAddRequest.eventFunctionDetails = FunctionDetailsFragment.mFunctionDetailsList
        MyApplication.getRestClient()?.AddEvent(mEventAddRequest)
            ?.enqueue(object : Callback<ResponseBase<AddEventResponse>> {
                override fun onResponse(
                    call: Call<ResponseBase<AddEventResponse>?>?,
                    response: Response<ResponseBase<AddEventResponse>?>
                ) {
                    CommonUtils.hideProgressDialog()
                    if (response.isSuccessful) {
                        if (response.body()?.mData != null) {
                            mIsEdite = false
                            finish()
                            startActivity(
                                Intent(
                                    this@CreateEventActivity, MainActivity::class.java
                                )
                            )
                        } else {
                            Log.d("Mytag", response.body()?.mError!!)
                        }
                    }
                }

                override fun onFailure(
                    call: Call<ResponseBase<AddEventResponse>?>, t: Throwable
                ) {
                    CommonUtils.hideProgressDialog()
                    Log.d("MyTAG", "onFailure: " + t.message)
                }
            })
    }

    private fun mApiGetEventDetails() {
        MyApplication.getRestClient()
            ?.GET_EventDetails(
                PreferenceManager.getPref(
                    Constants.Preference.Pref_EVENTId,
                    ""
                )!!
            )?.enqueue(object :
                Callback<ResponseBase<EventDetailsResponse>> {
                override fun onResponse(
                    call: Call<ResponseBase<EventDetailsResponse>?>?,
                    response: Response<ResponseBase<EventDetailsResponse>?>
                ) {
                    CommonUtils.hideProgressDialog()
                    if (response.isSuccessful) {
                        if (response.body()?.mData != null) {
                            if (mIsEdite) {
                                mEventDetailsResponse =
                                    response.body()!!.mData!!.getEventMasterDetails()!![0]!!

//                            mEventAddRequest.status = mEventDetailsResponse.status!!
//                            mEventAddRequest.eventDate = mEventDetailsResponse.eventDate!!
//                            mEventAddRequest.eventname = mEventDetailsResponse.eventname!!
//                            mEventAddRequest.partyaccId = mEventDetailsResponse.partyaccId!!
//                            mEventAddRequest.contactNo = mEventDetailsResponse.contactNo!!
//                            mEventAddRequest.brideName = mEventDetailsResponse.brideName!!
//                            mEventAddRequest.groomName = mEventDetailsResponse.groomName!!
//                            mEventAddRequest.foodNotes = mEventDetailsResponse.foodNotes!!
//                            mEventAddRequest.eventRemarks = mEventDetailsResponse.eventRemarks!!
//                            mEventAddRequest.venueName = mEventDetailsResponse.venueName!!
                                SetAdapter()
                                mEventDetailsResponse.eventFunctionDetails?.forEachIndexed { index, eventFunctionDetail ->
                                    FunctionDetailsFragment.mFunctionDetailsList.add(
                                        EventFunctionDetail(
                                            eventFunctionDetail.eventfunctionId!!,
                                            eventFunctionDetail.pax!!,
                                            eventFunctionDetail.starttime!!,
                                            eventFunctionDetail.endtime!!,
                                            eventFunctionDetail.venueName!!,
                                            eventFunctionDetail.functionId!!,
                                            eventFunctionDetail.functionName!!
                                        )
                                    )
                                }
                            } else {
                                SetAdapter()
                            }

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

    private fun SetAdapter() {
        val viewPagerAdapter =
            FragmentEventAdapter(this@CreateEventActivity) //view pager adapter
        binding.mViewPagerIntro.adapter = viewPagerAdapter
        binding.mViewPagerIntro.setUserInputEnabled(false)
    }
}