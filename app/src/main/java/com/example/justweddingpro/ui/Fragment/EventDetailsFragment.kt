package com.example.justweddingpro.ui.Fragment

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatSpinner
import androidx.fragment.app.Fragment
import com.example.crmapplication.MyApplication
import com.example.justweddingpro.Network.RequestModel.EventFunctionDetail
import com.example.justweddingpro.R
import com.example.justweddingpro.ui.CreateEventActivity
import com.example.justweddingpro.ui.CreateEventActivity.Companion.mEventAddRequest
import com.example.justweddingpro.ui.CreateEventActivity.Companion.mEventDetailsResponse
import com.example.justweddingpro.ui.Fragment.FunctionDetailsFragment.Companion.mFunctionDetailsList
import com.example.justweddingpro.ui.Response.EventDetailsResponse
import com.example.justweddingpro.ui.Response.ResponseBase
import com.example.justweddingpro.utils.CommonUtils
import com.example.justweddingpro.utils.CommonUtils.Companion.parseOnlyDateToUploadFormat
import com.example.justweddingpro.utils.CommonUtils.Companion.parseOnlyUploadToDateFormat
import com.example.justweddingpro.utils.Constants
import com.example.justweddingpro.utils.PreferenceManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class EventDetailsFragment : Fragment() {

    private var mStatusId = 0
    private var edtStatusName: AppCompatEditText? = null
    private var spiner: AppCompatSpinner? = null
    private var edtEventDate: AppCompatEditText? = null
    private var edtFoodNotes: AppCompatEditText? = null
    private var edtEventRemark: AppCompatEditText? = null
    private var edtVenueName: AppCompatEditText? = null
    private var edtEventName: AppCompatEditText? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_event_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        edtStatusName = view.findViewById(R.id.edtStatusName)
        spiner = view.findViewById(R.id.spiner)
        edtEventDate = view.findViewById(R.id.edtEventDate)
        edtEventName = view.findViewById(R.id.edtEventName)
//        edtFoodNotes = view.findViewById(R.id.edtFoodNotes)
//        edtEventRemark = view.findViewById(R.id.edtEventRemark)
//        edtVenueName = view.findViewById(R.id.edtVenueName)
        init()
    }

    private fun mIsValidation(): Boolean {
        if (edtStatusName!!.text.toString().equals("")) {
            Toast.makeText(requireActivity(), getString(R.string.select_status), Toast.LENGTH_LONG)
                .show()
            return false
        } else if (edtEventDate!!.text.toString().equals("")) {
            Toast.makeText(requireActivity(), getString(R.string.select_date), Toast.LENGTH_LONG)
                .show()
            return false
        } else if (edtEventName!!.text.toString().equals("")) {
            Toast.makeText(
                requireActivity(),
                getString(R.string.your_event_name),
                Toast.LENGTH_LONG
            )
                .show()
            return false
        } /*else if (edtFoodNotes!!.text.toString().equals("")) {
            Toast.makeText(requireActivity(), getString(R.string.food_note), Toast.LENGTH_LONG)
                .show()
            return false
        } else if (edtEventRemark!!.text.toString().equals("")) {
            Toast.makeText(
                requireActivity(),
                getString(R.string.your_event_remarks),
                Toast.LENGTH_LONG
            )
                .show()
            return false
        } else if (edtVenueName!!.text.toString().equals("")) {
            Toast.makeText(requireActivity(), getString(R.string.venue_name), Toast.LENGTH_LONG)
                .show()
            return false
        }*/

        return true
    }

    private fun init() {
        edtStatusName?.setOnClickListener {
            spiner?.performClick()
        }

        spiner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View,
                position: Int,
                id: Long
            ) {
                if (position != 0) {
                    edtStatusName?.setText(spiner?.selectedItem.toString())
                    mStatusId = position - 1
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        edtEventDate?.setOnClickListener {
            showDatePicker()
        }

        if (mEventDetailsResponse.status != null) {
            mStatusId = mEventDetailsResponse.status!!
            spiner?.setSelection(mStatusId)

            edtEventDate?.setText(parseOnlyUploadToDateFormat(mEventDetailsResponse.eventDate.toString())!!)
            edtEventName?.setText(mEventDetailsResponse.eventname!!)
        }
//        if (mIsEdite) {
//            mApiCalling()
//        } else
        mSetItem()
    }

    private fun mSetItem() {
        (requireActivity() as CreateEventActivity?)?.binding?.btnNext?.setOnClickListener {
            if (mIsValidation()) {
                mEventAddRequest.status = mStatusId
                mEventAddRequest.eventDate =
                    parseOnlyDateToUploadFormat(edtEventDate?.text.toString())!!
                mEventAddRequest.eventname = edtEventName?.text.toString()
//                mEventAddRequest.foodNotes = edtFoodNotes?.text.toString()
//                mEventAddRequest.eventRemarks = edtEventRemark?.text.toString()
//                mEventAddRequest.venueName = edtVenueName?.text.toString()
                mEventAddRequest.clientuserId =
                    PreferenceManager.getPref(
                        Constants.Preference.PREF_CLIENT_USERID,
                        ""
                    )?.toInt()!!

                (requireActivity() as CreateEventActivity?)?.mRedirectNext()
            }
        }
    }

    private fun mApiCalling() {
        CommonUtils.showProgressDialog(requireActivity())
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
                            }

                            mStatusId = mEventDetailsResponse.status!!
                            spiner?.setSelection(mStatusId)

                            edtEventDate?.setText(parseOnlyUploadToDateFormat(mEventDetailsResponse.eventDate.toString())!!)
                            edtEventName?.setText(mEventDetailsResponse.eventname!!)
                            /*           edtFoodNotes?.setText(mEventDetailsResponse.foodNotes!!)
                                       edtEventRemark?.setText(mEventDetailsResponse.eventRemarks!!)
                                       edtVenueName?.setText(mEventDetailsResponse.venueName!!)*/
                            mSetItem()
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

    private fun showDatePicker() {
        var calendar = Calendar.getInstance()
        // Create a DatePickerDialog
        val datePickerDialog = DatePickerDialog(
            requireActivity(), { DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                // Create a new Calendar instance to hold the selected date
                val selectedDate = Calendar.getInstance()
                // Set the selected date using the values received from the DatePicker dialog
                selectedDate.set(year, monthOfYear, dayOfMonth)
                // Create a SimpleDateFormat to format the date as "dd/MM/yyyy"
                val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                // Format the selected date into a string
                val formattedDate = dateFormat.format(selectedDate.time)
                // Update the TextView to display the selected date with the "Selected Date: " prefix
                edtEventDate?.setText(formattedDate.toString())
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        // Show the DatePicker dialog
        datePickerDialog.show()
    }
}