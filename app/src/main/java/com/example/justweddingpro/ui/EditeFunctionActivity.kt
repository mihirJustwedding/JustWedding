package com.example.justweddingpro.ui

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.crmapplication.MyApplication
import com.example.justweddingpro.Network.RequestModel.EventFunctionDetail
import com.example.justweddingpro.R
import com.example.justweddingpro.Response.AddEventResponse
import com.example.justweddingpro.Response.FunctionDetailsResponse
import com.example.justweddingpro.databinding.ActivityEditeFunctionBinding
import com.example.justweddingpro.ui.CreateEventActivity.Companion.mEventAddRequest
import com.example.justweddingpro.ui.CreateEventActivity.Companion.mEventDetailsResponse
import com.example.justweddingpro.ui.Fragment.FunctionDetailsFragment
import com.example.justweddingpro.ui.Response.EventDetailsResponse
import com.example.justweddingpro.ui.Response.ResponseBase
import com.example.justweddingpro.ui.adapter.FunctionListAdapter
import com.example.justweddingpro.utils.CommonUtils
import com.example.justweddingpro.utils.CommonUtils.Companion.parseDateToViewUtcToLocal
import com.example.justweddingpro.utils.Constants
import com.example.justweddingpro.utils.PreferenceManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Calendar

class EditeFunctionActivity : BasedActivity() {

    private lateinit var binding: ActivityEditeFunctionBinding

    private var mFunctionDetailsList = ArrayList<EventFunctionDetail>()

    private var mFunctionListAdapter: FunctionListAdapter? = null
    private var mFunctionId = ""
    private var mFunctionIdList = ArrayList<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditeFunctionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imgAdd.setOnClickListener {
            if (mFunctionId.isNotEmpty()) onBottomSheetDialog(
                0,
                binding.edtFunctionName.text.toString().trim(),
                "", "", "", "", false
            )
            else Toast.makeText(
                this@EditeFunctionActivity, "Please Select Function First", Toast.LENGTH_LONG
            ).show()
        }

        mGetFunctionApiCalling()

        binding.btnNext.setOnClickListener {
            mUpdateApiCalling()
        }

    }

    var edtStartTime: AppCompatEditText? = null
    var edtEndTime: AppCompatEditText? = null

    private fun onBottomSheetDialog(
        mEventFunctionId: Int,
        mFuncationName: String,
        mStartTime: String,
        mEndTime: String,
        VanueName: String,
        Pax: String, IsEdite: Boolean
    ) {
        val dialog = BottomSheetDialog(this@EditeFunctionActivity, R.style.BottomSheetDialogTheme)
        val view = layoutInflater.inflate(R.layout.item_bottomsheet_dialog, null)


        val btnClose = view.findViewById(R.id.imgClose) as ImageView
        val edtFunName = view.findViewById(R.id.edtFunName) as AppCompatEditText
        val btnNext = view.findViewById(R.id.btnNext) as AppCompatButton
        val edtVenueName = view.findViewById(R.id.edtVenueName) as AppCompatEditText
        val edtPax = view.findViewById(R.id.edtPax) as AppCompatEditText
        edtStartTime = view.findViewById(R.id.edtStartTime) as AppCompatEditText
        edtEndTime = view.findViewById(R.id.edtEndTime) as AppCompatEditText

        edtStartTime!!.setText(parseDateToViewUtcToLocal(mStartTime))
        edtEndTime!!.setText(parseDateToViewUtcToLocal(mEndTime))
        edtVenueName!!.setText(VanueName)
        edtPax!!.setText(Pax)

        edtStartTime!!.setOnClickListener {
            DateAndTimePickerDialog(true)
        }

        edtEndTime!!.setOnClickListener {
            DateAndTimePickerDialog(false)
        }

        btnNext.setOnClickListener {
            var mFunctionMasterDetail: EventFunctionDetail? = null
            mFunctionMasterDetail = EventFunctionDetail(
                eventFunctionId = mEventFunctionId.toInt(),
                edtPax.text.toString().toInt(),
                CommonUtils.parseDateToUploadFormat(edtStartTime!!.text.toString())!!,
                CommonUtils.parseDateToUploadFormat(edtEndTime!!.text.toString())!!,
                edtVenueName.text.toString().trim(),
                mFunctionId.toInt(),
                edtFunName.text.toString()
            )

            if (!mFunctionIdList.contains(mFunctionId.toInt())) {
                mFunctionDetailsList.add(mFunctionMasterDetail)
                mFunctionIdList.add(mFunctionId.toInt())
                SetAdapter()
                dialog.dismiss()
            } else {
                if (IsEdite) {
                    mFunctionDetailsList.forEachIndexed { index, eventFunctionDetail ->
                        eventFunctionDetail.takeIf { it.eventFunctionId == mFunctionMasterDetail.eventFunctionId }
                            ?.let {
                                it.eventFunctionId = mFunctionMasterDetail.eventFunctionId
                                it.pax = mFunctionMasterDetail.pax
                                it.starttime = mFunctionMasterDetail.starttime
                                it.endtime = mFunctionMasterDetail.endtime
                                it.vanueName = mFunctionMasterDetail.vanueName
                                it.functionId = mFunctionMasterDetail.functionId
                                it.functionName = mFunctionMasterDetail.functionName
                            }

                        SetAdapter()
                        dialog.dismiss()
                    }

                } else {
                    Toast.makeText(
                        this@EditeFunctionActivity, "Please Select Another", Toast.LENGTH_LONG
                    ).show()
                }
            }
        }

        edtFunName.setText(mFuncationName)
        btnClose.setOnClickListener {
            dialog.dismiss()
        }

        dialog.setCancelable(true)
        dialog.setContentView(view)
        dialog.show()
    }

    private fun mApiCalling() {
        binding.edtFunctionName?.setOnClickListener {
            binding.spinnerFunction?.performClick()
        }
        MyApplication.getRestClient()?.GetFunctionDetails(
            PreferenceManager.getPref(
                Constants.Preference.PREF_CLIENT_USERID, ""
            )!!
        )?.enqueue(object : Callback<ResponseBase<FunctionDetailsResponse>> {
            override fun onResponse(
                call: Call<ResponseBase<FunctionDetailsResponse>?>?,
                response: Response<ResponseBase<FunctionDetailsResponse>?>
            ) {
                CommonUtils.hideProgressDialog()
                if (response.isSuccessful) {
                    if (response.body()?.mData != null) {

                        val mPartyName: MutableList<String> = java.util.ArrayList()
                        if (response.body()?.mData!!.functionMasterDetails?.size!! > 0) {
                            for (i in response.body()!!.mData?.functionMasterDetails!!) {
                                mPartyName.add(i.functionname!!)
                            }
                            val dataAdapter = ArrayAdapter<String>(
                                this@EditeFunctionActivity,
                                android.R.layout.simple_spinner_item,
                                mPartyName
                            )
                            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                            binding.spinnerFunction?.adapter = dataAdapter
                            binding.spinnerFunction?.onItemSelectedListener =
                                object : AdapterView.OnItemSelectedListener {
                                    override fun onItemSelected(
                                        parent: AdapterView<*>?,
                                        view: View,
                                        position1: Int,
                                        id: Long
                                    ) {
                                        if (position1 != 0) {

                                            binding.edtFunctionName?.setText(
                                                response.body()?.mData!!.functionMasterDetails?.get(
                                                    position1 - 1
                                                )?.functionname
                                            )

                                            mFunctionId =
                                                response.body()?.mData!!.functionMasterDetails?.get(
                                                    position1 - 1
                                                )!!.id.toString()
                                        } else {
                                            binding.edtFunctionName?.setText("")
                                            mFunctionId = ""
                                        }
                                    }

                                    override fun onNothingSelected(parent: AdapterView<*>?) {
                                        binding.edtFunctionName?.setText("")
                                        mFunctionId = ""
                                    }
                                }
                            mPartyName.add(0, "Select Function")
                            binding.spinnerFunction?.setSelection(0)
                        }

                    } else {
                        Log.d("Mytag", response.body()?.mError!!)
                    }
                }
            }

            override fun onFailure(
                call: Call<ResponseBase<FunctionDetailsResponse>?>, t: Throwable
            ) {
                CommonUtils.hideProgressDialog()
                Log.d("MyTAG", "onFailure: " + t.message)
            }
        })
    }

    private fun DateAndTimePickerDialog(IsStart: Boolean) {
        val c: Calendar = Calendar.getInstance()
        var mYear = c.get(Calendar.YEAR)
        var mMonth = c.get(Calendar.MONTH)
        var mDay = c.get(Calendar.DAY_OF_MONTH)

        var mHour = c[Calendar.HOUR_OF_DAY]
        var mMinute = c[Calendar.MINUTE]

        val datePickerDialog = DatePickerDialog(
            this@EditeFunctionActivity, OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                var mDate = dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year

                val timePickerDialog = TimePickerDialog(
                    this@EditeFunctionActivity, OnTimeSetListener { view, hourOfDay, minute ->
                        var mTime = "$hourOfDay:$minute"
                        if (IsStart) {
                            edtStartTime?.setText(
                                "${CommonUtils.printDateViewFormate(mDate)} ${
                                    CommonUtils.printTimeViewFormat(
                                        mTime
                                    )
                                }"
                            )
                        } else edtEndTime?.setText(
                            "${CommonUtils.printDateViewFormate(mDate)} ${
                                CommonUtils.printTimeViewFormat(
                                    mTime
                                )
                            }"
                        )
                    }, mHour, mMinute, false
                )
                timePickerDialog.show()
            }, mYear, mMonth, mDay
        )
        datePickerDialog.show()
    }

    private fun SetAdapter() {
        if (mFunctionDetailsList.isNotEmpty()) {
            binding.llAddFunction?.visibility = View.VISIBLE
            binding.imgEmptyView?.visibility = View.GONE
        } else {
            binding.llAddFunction?.visibility = View.GONE
            binding.imgEmptyView?.visibility = View.VISIBLE
        }
        mFunctionListAdapter = FunctionListAdapter(
            this@EditeFunctionActivity, mFunctionDetailsList
        )
        binding.mRvAddFunction?.layoutManager =
            LinearLayoutManager(this@EditeFunctionActivity, LinearLayoutManager.VERTICAL, false)
        binding.mRvAddFunction?.adapter = mFunctionListAdapter
        binding.mRvAddFunction?.setHasFixedSize(true)

        mFunctionListAdapter!!.SetOnclickListner(object : FunctionListAdapter.OnclickListner {
            override fun onclick(position: Int) {
                mFunctionDetailsList.removeAt(position)
                mFunctionIdList.removeAt(position)
                SetAdapter()
            }
        })

        mFunctionListAdapter!!.SetOnEditeclickListner(object : FunctionListAdapter.OnclickListner {
            override fun onclick(position: Int) {
                mFunctionId = mFunctionDetailsList[position].functionId.toString()
                onBottomSheetDialog(
                    mFunctionDetailsList[position].eventFunctionId,
                    mFunctionDetailsList[position].functionName,
                    mFunctionDetailsList[position].starttime,
                    mFunctionDetailsList[position].endtime,
                    mFunctionDetailsList[position].vanueName,
                    mFunctionDetailsList[position].pax.toString(), true
                )
            }
        })
    }

    private fun mGetFunctionApiCalling() {
        CommonUtils.showProgressDialog(this@EditeFunctionActivity)
        MyApplication.getRestClient()?.GET_EventDetails(
            PreferenceManager.getPref(
                Constants.Preference.Pref_EVENTId, ""
            )!!
        )?.enqueue(object : Callback<ResponseBase<EventDetailsResponse>> {
            override fun onResponse(
                call: Call<ResponseBase<EventDetailsResponse>?>?,
                response: Response<ResponseBase<EventDetailsResponse>?>
            ) {
                CommonUtils.hideProgressDialog()
                if (response.isSuccessful) {
                    if (response.body()?.mData != null) {
                        mEventDetailsResponse =
                            response.body()!!.mData!!.getEventMasterDetails()!![0]!!

                        mEventDetailsResponse.eventFunctionDetails?.forEachIndexed { index, eventFunctionDetail ->
                            mFunctionDetailsList.add(
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
                            mFunctionIdList.add(eventFunctionDetail.functionId!!)
                        }

                        mApiCalling()

                        SetAdapter()

                    } else {
                        Log.d("Mytag", response.body()?.mError!!)
                    }
                }
            }

            override fun onFailure(
                call: Call<ResponseBase<EventDetailsResponse>?>, t: Throwable
            ) {
                CommonUtils.hideProgressDialog()
                Log.d("MyTAG", "onFailure: " + t.message)
            }
        })
    }

    private fun mUpdateApiCalling() {
        CommonUtils.showProgressDialog(this@EditeFunctionActivity)
        mEventAddRequest.eventId = mEventDetailsResponse.eventId!!
        mEventAddRequest.status = mEventDetailsResponse.status!!
        mEventAddRequest.eventDate = mEventDetailsResponse.eventDate!!
        mEventAddRequest.eventname = mEventDetailsResponse.eventname!!
        mEventAddRequest.foodNotes = mEventDetailsResponse.foodNotes!!
        mEventAddRequest.contactNo = mEventDetailsResponse.contactNo!!
        mEventAddRequest.brideName = mEventDetailsResponse.brideName!!
        mEventAddRequest.groomName = mEventDetailsResponse.groomName!!
//        mEventAddRequest.address = mEventDetailsResponse.address!!
        mEventAddRequest.eventRemarks = mEventDetailsResponse.eventRemarks!!
        mEventAddRequest.venueName = mEventDetailsResponse.venueName!!
        mEventAddRequest.clientuserId = mEventDetailsResponse.clientuserId!!
        mEventAddRequest.partyaccId = mEventDetailsResponse.partyaccId!!

        mEventAddRequest.eventFunctionDetails = mFunctionDetailsList

        MyApplication.getRestClient()
            ?.AddEvent(mEventAddRequest)
            ?.enqueue(object :
                Callback<ResponseBase<AddEventResponse>> {
                override fun onResponse(
                    call: Call<ResponseBase<AddEventResponse>?>?,
                    response: Response<ResponseBase<AddEventResponse>?>
                ) {
                    CommonUtils.hideProgressDialog()
                    if (response.isSuccessful) {
                        if (response.body()?.mData != null) {
                            MyEventDetailsActivity.mIsEdite = false
                            finish()
                        } else {
                            Log.d("Mytag", response.body()?.mError!!)
                        }
                    }
                }

                override fun onFailure(
                    call: Call<ResponseBase<AddEventResponse>?>,
                    t: Throwable
                ) {
                    CommonUtils.hideProgressDialog()
                    Log.d("MyTAG", "onFailure: " + t.message)
                }
            })
    }
}