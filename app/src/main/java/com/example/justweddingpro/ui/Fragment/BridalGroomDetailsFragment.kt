package com.example.justweddingpro.ui.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import androidx.fragment.app.Fragment
import com.example.crmapplication.MyApplication
import com.example.justweddingpro.R
import com.example.justweddingpro.ui.CreateEventActivity
import com.example.justweddingpro.ui.CreateEventActivity.Companion.mEventAddRequest
import com.example.justweddingpro.ui.CreateEventActivity.Companion.mEventDetailsResponse
import com.example.justweddingpro.ui.Response.EventDetailsResponse
import com.example.justweddingpro.ui.Response.ResponseBase
import com.example.justweddingpro.utils.CommonUtils
import com.example.justweddingpro.utils.Constants
import com.example.justweddingpro.utils.PreferenceManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class BridalGroomDetailsFragment : Fragment() {

    private var edtBrideName: AppCompatEditText? = null
    private var edtGroomName: AppCompatEditText? = null
    private var edtFoodNotes: AppCompatEditText? = null
    private var edtEventRemark: AppCompatEditText? = null
    private var edtVenueName: AppCompatEditText? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bridel_groom_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        edtBrideName = view.findViewById(R.id.edtBrideName)
        edtGroomName = view.findViewById(R.id.edtGroomName)
        edtFoodNotes = view.findViewById(R.id.edtFoodNotes)
        edtEventRemark = view.findViewById(R.id.edtEventRemark)
        edtVenueName = view.findViewById(R.id.edtVenueName)

//        if (mIsEdite) {
//            mApiCalling()
//        } else
        if (mEventDetailsResponse.brideName != null) {
            edtBrideName?.setText(mEventDetailsResponse.brideName!!)
            edtGroomName?.setText(mEventDetailsResponse.groomName!!)
            edtFoodNotes?.setText(mEventDetailsResponse.foodNotes!!)
            edtEventRemark?.setText(mEventDetailsResponse.eventRemarks!!)
            edtVenueName?.setText(mEventDetailsResponse.venueName!!)
        }
    }

    override fun onResume() {
        mSetItem()
        super.onResume()
    }

    private fun mSetItem() {
        (requireActivity() as CreateEventActivity?)?.binding?.btnNext?.setOnClickListener {
            if (mIsValidation()) {
                mEventAddRequest.brideName = edtBrideName?.text.toString()
                mEventAddRequest.groomName = edtGroomName?.text.toString()
                mEventAddRequest.foodNotes = edtFoodNotes?.text.toString()
                mEventAddRequest.eventRemarks = edtEventRemark?.text.toString()
                mEventAddRequest.venueName = edtVenueName?.text.toString()

                (requireActivity() as CreateEventActivity?)?.mRedirectNext()
            }
        }
    }

    private fun mIsValidation(): Boolean {
        if (edtBrideName!!.text.toString().equals("")) {
            Toast.makeText(requireActivity(), getString(R.string.bride_name), Toast.LENGTH_LONG)
                .show()
            return false
        } else if (edtGroomName!!.text.toString().equals("")) {
            Toast.makeText(requireActivity(), getString(R.string.groom_name), Toast.LENGTH_LONG)
                .show()
            return false
        } else if (edtFoodNotes!!.text.toString().equals("")) {
            Toast.makeText(requireActivity(), getString(R.string.food_note), Toast.LENGTH_LONG)
                .show()
            return false
        } else if (edtEventRemark!!.text.toString().equals("")) {
            Toast.makeText(
                requireActivity(), getString(R.string.your_event_remarks), Toast.LENGTH_LONG
            ).show()
            return false
        } else if (edtVenueName!!.text.toString().equals("")) {
            Toast.makeText(requireActivity(), getString(R.string.venue_name), Toast.LENGTH_LONG)
                .show()
            return false
        }

        return true
    }

    private fun mApiCalling() {
        CommonUtils.showProgressDialog(requireActivity())
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
                        edtBrideName?.setText(mEventDetailsResponse.brideName!!)
                        edtGroomName?.setText(mEventDetailsResponse.groomName!!)
                        edtFoodNotes?.setText(mEventDetailsResponse.foodNotes!!)
                        edtEventRemark?.setText(mEventDetailsResponse.eventRemarks!!)
                        edtVenueName?.setText(mEventDetailsResponse.venueName!!)
                        mSetItem()
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
}