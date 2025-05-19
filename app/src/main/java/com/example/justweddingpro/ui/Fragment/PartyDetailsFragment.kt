package com.example.justweddingpro.ui.Fragment

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatSpinner
import androidx.fragment.app.Fragment
import com.example.crmapplication.MyApplication
import com.example.justweddingpro.Network.RequestModel.AddPartyRequest
import com.example.justweddingpro.R
import com.example.justweddingpro.Response.PartyDetailsResponse
import com.example.justweddingpro.Response.TableListResponse
import com.example.justweddingpro.ui.CreateEventActivity
import com.example.justweddingpro.ui.CreateEventActivity.Companion.mEventAddRequest
import com.example.justweddingpro.ui.CreateEventActivity.Companion.mEventDetailsResponse
import com.example.justweddingpro.ui.MyEventDetailsActivity
import com.example.justweddingpro.ui.Response.ResponseBase
import com.example.justweddingpro.utils.CommonUtils
import com.example.justweddingpro.utils.Constants
import com.example.justweddingpro.utils.PreferenceManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class PartyDetailsFragment : Fragment() {

    private var mPartyId: String = ""
    var isFirstSelection = true

    private var edtPartyName: AppCompatEditText? = null
    private var spinner: AppCompatSpinner? = null
    private var edtPartyAddress: AppCompatEditText? = null
    private var edtContactNumber: AppCompatEditText? = null
    private var imgAdd: ImageView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_party_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        edtPartyName = view.findViewById(R.id.edtPartyName)
        spinner = view.findViewById(R.id.spinner)
        edtPartyAddress = view.findViewById(R.id.edtPartyAddress)
        edtContactNumber = view.findViewById(R.id.edtContactNumber)
        imgAdd = view.findViewById(R.id.imgAdd)

        edtPartyName?.setOnClickListener {
            spinner?.performClick()
        }

        imgAdd!!.setOnClickListener {
            onBottomSheetDialog()
        }

        mApiCalling()

        mSetData()
    }

    fun mSetData() {
        if (MyEventDetailsActivity.mIsEdite) {
//            edtPartyAddress?.setText(mEventDetailsResponse.address)
            edtContactNumber?.setText(mEventDetailsResponse.contactNo)
        }
        (requireActivity() as CreateEventActivity?)?.binding?.btnNext?.setOnClickListener {
            if (mIsValidation()) {
                mEventAddRequest.contactNo =
                    edtContactNumber?.text.toString().trim()
//                mEventAddRequest.address = edtPartyAddress?.text.toString()
                if (mPartyId != "")
                    mEventAddRequest.partyaccId = mPartyId.toInt()

                (requireActivity() as CreateEventActivity?)?.mRedirectNext()
            }
        }
    }

    private fun mIsValidation(): Boolean {
        if (edtPartyName!!.text.toString().trim() == "") {
            Toast.makeText(
                requireActivity(),
                getString(R.string.select_party_name),
                Toast.LENGTH_LONG
            )
                .show()
            return false
        } else if (edtContactNumber!!.text.toString().trim() == "") {
            Toast.makeText(
                requireActivity(),
                getString(R.string.party_contact_no),
                Toast.LENGTH_LONG
            )
                .show()
            return false
        }
        return true
    }

    private fun mApiCalling() {
        edtPartyName?.setOnClickListener {
            spinner?.performClick()
        }

        CommonUtils.showProgressDialog(requireActivity())
        MyApplication.getRestClient()
            ?.GetPartyDetails(
                PreferenceManager.getPref(
                    Constants.Preference.PREF_CLIENT_USERID,
                    ""
                )!!
            )
            ?.enqueue(object :
                Callback<ResponseBase<PartyDetailsResponse>> {
                override fun onResponse(
                    call: Call<ResponseBase<PartyDetailsResponse>?>?,
                    response: Response<ResponseBase<PartyDetailsResponse>?>
                ) {
                    CommonUtils.hideProgressDialog()
                    if (response.isSuccessful) {
                        if (response.body()?.mData != null) {

                            val mPartyName: MutableList<String> = java.util.ArrayList()
                            if (response.body()?.mData!!.partyAccountMasterDetails?.size!! > 0) {
                                for (i in response.body()!!.mData?.partyAccountMasterDetails!!) {
                                    mPartyName.add(i.partyname!!)
                                }
                                val dataAdapter = ArrayAdapter<String>(
                                    requireActivity(),
                                    android.R.layout.simple_spinner_item,
                                    mPartyName
                                )

                                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                                spinner?.adapter = dataAdapter
                                spinner?.onItemSelectedListener = object :
                                    AdapterView.OnItemSelectedListener {
                                    override fun onItemSelected(
                                        parent: AdapterView<*>?,
                                        view: View,
                                        position1: Int,
                                        id: Long
                                    ) {
//                                        if (isFirstSelection) {
//                                            isFirstSelection = false
//                                            return
//                                        }
                                        if (position1 != 0) {
                                            edtPartyName?.setText(
                                                response.body()?.mData!!.partyAccountMasterDetails?.get(
                                                    position1 - 1
                                                )?.partyname
                                            )

                                            edtContactNumber?.setText(
                                                response.body()?.mData!!.partyAccountMasterDetails?.get(
                                                    position1 - 1
                                                )?.watsappno
                                            )
                                            mPartyId =
                                                response.body()?.mData!!.partyAccountMasterDetails?.get(
                                                    position1 - 1
                                                )?.id.toString()
                                        } else {
                                            if (MyEventDetailsActivity.mIsEdite) {
                                                if (mEventDetailsResponse.partyaccId != null) {
                                                    for (i in 0 until response.body()!!.mData?.partyAccountMasterDetails!!.size) {
                                                        if (response.body()!!.mData?.partyAccountMasterDetails!![i].id
                                                            == mEventDetailsResponse.partyaccId
                                                        ) {
                                                            edtPartyName?.setText(
                                                                response.body()?.mData!!.partyAccountMasterDetails?.get(
                                                                    i
                                                                )?.partyname
                                                            )

                                                            edtContactNumber?.setText(
                                                                response.body()?.mData!!.partyAccountMasterDetails?.get(
                                                                   i
                                                                )?.watsappno
                                                            )

                                                            mPartyId =
                                                                response.body()?.mData!!.partyAccountMasterDetails?.get(
                                                                    i
                                                                )?.id.toString()
                                                        }
                                                    }
                                                }
                                            } else {
                                                edtPartyName?.setText("")
                                                mPartyId = ""
                                            }
                                        }
                                    }

                                    override fun onNothingSelected(parent: AdapterView<*>?) {
                                        edtPartyName?.setText("")
                                        mPartyId = ""
                                    }
                                }
                                mPartyName.add(0, "Select")
                                spinner?.setSelection(0)
                            }

                        } else {
                            Log.d("Mytag", response.body()?.mError!!)
                        }
                    }
                }

                override fun onFailure(
                    call: Call<ResponseBase<PartyDetailsResponse>?>,
                    t: Throwable
                ) {
                    CommonUtils.hideProgressDialog()
                    Log.d("MyTAG", "onFailure: " + t.message)
                }
            })
    }

    lateinit var edtBirthDate: AppCompatEditText
    lateinit var edtAniDate: AppCompatEditText
    private fun onBottomSheetDialog() {
        val dialog = BottomSheetDialog(requireActivity(), R.style.BottomSheetDialogTheme)
        val view = layoutInflater.inflate(R.layout.item_addparty_details_dialog, null)


        val btnClose = view.findViewById(R.id.imgClose) as ImageView
        val btnNext = view.findViewById(R.id.btnNext) as AppCompatButton
        val edtPartName = view.findViewById(R.id.edtPartName) as AppCompatEditText
        val edtEmail = view.findViewById(R.id.edtEmail) as AppCompatEditText
        val edtMobile = view.findViewById(R.id.edtMobile) as AppCompatEditText
        val edtPartyAddress = view.findViewById(R.id.edtPartyAddress) as AppCompatEditText
        edtBirthDate = view.findViewById(R.id.edtBirthDate) as AppCompatEditText
        edtAniDate = view.findViewById(R.id.edtAniDate) as AppCompatEditText

        edtBirthDate.setOnClickListener {
            showDatePicker("Birth")
        }

        edtAniDate.setOnClickListener {
            showDatePicker("Anni")
        }

        btnNext.setOnClickListener {
            mApiAddPartyAccount(
                edtPartName.text.toString(),
                edtEmail.text.toString(),
                edtMobile.text.toString(),
                edtPartyAddress.text.toString(),
                edtBirthDate.text.toString(),
                edtAniDate.text.toString(),
            )
            dialog.dismiss()
        }

        btnClose.setOnClickListener {
            dialog.dismiss()
        }

        dialog.setCancelable(true)
        dialog.setContentView(view)
        dialog.show()
    }

    private fun mApiAddPartyAccount(
        Partyname: String,
        Partyemail: String,
        Partymobile: String,
        Partyadd: String,
        DOB: String,
        AnniDate: String
    ) {
        var mAddPartyRequest = AddPartyRequest()
        mAddPartyRequest.setPartyaccId(0)
        mAddPartyRequest.setAnniversarydate(AnniDate)
        mAddPartyRequest.setBirthdate(DOB)
        mAddPartyRequest.setClientUserId(
            PreferenceManager.getPref(
                Constants.Preference.PREF_CLIENT_USERID,
                ""
            )!!.toInt()
        )
        mAddPartyRequest.setEmailid(Partyemail)
        mAddPartyRequest.setPartyname(Partyname)
        mAddPartyRequest.setWatsappno(Partymobile)
        mAddPartyRequest.setaddress(Partyadd)

        CommonUtils.showProgressDialog(requireActivity())
        MyApplication.getRestClient()
            ?.API_AddPartyAccount(mAddPartyRequest)?.enqueue(object :
                Callback<ResponseBase<TableListResponse>> {
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
                    }
                }

                override fun onFailure(
                    call: Call<ResponseBase<TableListResponse>?>,
                    t: Throwable
                ) {
                    CommonUtils.hideProgressDialog()
                    Log.d("MyTAG", "onFailure: " + t.message)
                }
            })
    }

    private fun showDatePicker(mstring: String) {
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
                if (mstring == "Birth")
                    edtBirthDate.setText(formattedDate.toString())
                else
                    edtAniDate.setText(formattedDate.toString())
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        // Show the DatePicker dialog
        datePickerDialog.show()
    }
}