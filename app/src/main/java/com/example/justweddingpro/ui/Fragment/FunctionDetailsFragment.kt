package com.example.justweddingpro.ui.Fragment

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatSpinner
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.crmapplication.MyApplication
import com.example.justweddingpro.Network.RequestModel.EventFunctionDetail
import com.example.justweddingpro.R
import com.example.justweddingpro.Response.FunctionDetailsResponse
import com.example.justweddingpro.ui.Response.ResponseBase
import com.example.justweddingpro.ui.adapter.FunctionListAdapter
import com.example.justweddingpro.utils.CommonUtils
import com.example.justweddingpro.utils.CommonUtils.Companion.parseDateToUploadFormat
import com.example.justweddingpro.utils.CommonUtils.Companion.printDateViewFormate
import com.example.justweddingpro.utils.CommonUtils.Companion.printTimeViewFormat
import com.example.justweddingpro.utils.Constants
import com.example.justweddingpro.utils.PreferenceManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Calendar


class FunctionDetailsFragment : Fragment() {

    companion object {
        var mFunctionDetailsList = ArrayList<EventFunctionDetail>()
    }

    private var mFunctionListAdapter: FunctionListAdapter? = null
    private var mFunctionId = ""
    private var mFunctionIdList = ArrayList<Int>()

    private var imgAdd: ImageView? = null
    private var imgEmptyView: ImageView? = null
    private var edtFunctionName: AppCompatEditText? = null
    private var spinnerFunction: AppCompatSpinner? = null
    private var mRvAddFunction: RecyclerView? = null
    private var llAddFunction: LinearLayout? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_function_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imgAdd = view.findViewById(R.id.imgAdd)
        imgEmptyView = view.findViewById(R.id.imgEmptyView)
        edtFunctionName = view.findViewById(R.id.edtFunctionName)
        spinnerFunction = view.findViewById(R.id.spinnerFunction)
        mRvAddFunction = view.findViewById(R.id.mRvAddFunction)
        llAddFunction = view.findViewById(R.id.llAddFunction)

        imgAdd?.setOnClickListener {
            if (mFunctionId.isNotEmpty())
                onBottomSheetDialog(edtFunctionName?.text.toString().trim())
            else
                Toast.makeText(requireActivity(), "Please Select Function First", Toast.LENGTH_LONG)
                    .show()
        }

        mApiCalling()

        SetAdapter()

//        (requireActivity() as CreateEventActivity?)?.binding?.btnNext?.setOnClickListener {
//            (requireActivity() as CreateEventActivity?)?.mRedirectNext()
//        }
    }

    var edtStartTime: AppCompatEditText? = null
    var edtEndTime: AppCompatEditText? = null

    private fun onBottomSheetDialog(mFuncationName: String) {
        val dialog = BottomSheetDialog(requireActivity(), R.style.BottomSheetDialogTheme)
        val view = layoutInflater.inflate(R.layout.item_bottomsheet_dialog, null)


        val btnClose = view.findViewById(R.id.imgClose) as ImageView
        val edtFunName = view.findViewById(R.id.edtFunName) as AppCompatEditText
        val btnNext = view.findViewById(R.id.btnNext) as AppCompatButton
        val edtVenueName = view.findViewById(R.id.edtVenueName) as AppCompatEditText
        val edtPax = view.findViewById(R.id.edtPax) as AppCompatEditText
        edtStartTime = view.findViewById(R.id.edtStartTime) as AppCompatEditText
        edtEndTime = view.findViewById(R.id.edtEndTime) as AppCompatEditText

        edtStartTime!!.setOnClickListener {
            DateAndTimePickerDialog(true)
        }

        edtEndTime!!.setOnClickListener {
            DateAndTimePickerDialog(false)
        }

        btnNext.setOnClickListener {
            var mFunctionMasterDetail: EventFunctionDetail? = null
            mFunctionMasterDetail = EventFunctionDetail(
                0, edtPax.text.toString().toInt(),
                parseDateToUploadFormat(edtStartTime!!.text.toString())!!,
                parseDateToUploadFormat(edtEndTime!!.text.toString())!!,
                edtVenueName.text.toString().trim(),
                mFunctionId.toInt(), edtFunName.text.toString()
            )

//            mFunctionMasterDetail.setEventFunctionId(0)
//            mFunctionMasterDetail.setPax(edtPax.text.toString().toInt())
//            mFunctionMasterDetail.setStarttime(parseDateToUploadFormat(edtStartTime!!.text.toString())!!)
//            mFunctionMasterDetail.setEndtime(parseDateToUploadFormat(edtEndTime!!.text.toString())!!)
//            mFunctionMasterDetail.setVanueName(edtVenueName.text.toString().trim())
//            mFunctionMasterDetail.setFunctionId(mFunctionId.toInt())
//            mFunctionMasterDetail.setFunctionName(edtFunName.text.toString())

            if (!mFunctionIdList.contains(mFunctionId.toInt())) {
                mFunctionDetailsList.add(mFunctionMasterDetail)
                mFunctionIdList.add(mFunctionId.toInt())
                SetAdapter()
                dialog.dismiss()
            } else {
                Toast.makeText(requireActivity(), "Please Select Another", Toast.LENGTH_LONG)
                    .show()
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
        edtFunctionName?.setOnClickListener {
            spinnerFunction?.performClick()
        }
        MyApplication.getRestClient()
            ?.GetFunctionDetails(
                PreferenceManager.getPref(
                    Constants.Preference.PREF_CLIENT_USERID,
                    ""
                )!!
            )
            ?.enqueue(object :
                Callback<ResponseBase<FunctionDetailsResponse>> {
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
                                    requireActivity(),
                                    android.R.layout.simple_spinner_item,
                                    mPartyName
                                )
                                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                                spinnerFunction?.adapter = dataAdapter
                                spinnerFunction?.onItemSelectedListener = object :
                                    AdapterView.OnItemSelectedListener {
                                    override fun onItemSelected(
                                        parent: AdapterView<*>?,
                                        view: View,
                                        position1: Int,
                                        id: Long
                                    ) {
                                        if (position1 != 0) {

                                            edtFunctionName?.setText(
                                                response.body()?.mData!!.functionMasterDetails?.get(
                                                    position1 - 1
                                                )?.functionname
                                            )

                                            mFunctionId =
                                                response.body()?.mData!!.functionMasterDetails?.get(
                                                    position1 - 1
                                                )!!.id.toString()
                                        } else {
                                            edtFunctionName?.setText("")
                                            mFunctionId = ""
                                        }
                                    }

                                    override fun onNothingSelected(parent: AdapterView<*>?) {
                                        edtFunctionName?.setText("")
                                        mFunctionId = ""
                                    }
                                }
                                mPartyName.add(0, "Select Function")
                                spinnerFunction?.setSelection(0)
                            }

                        } else {
                            Log.d("Mytag", response.body()?.mError!!)
                        }
                    }
                }

                override fun onFailure(
                    call: Call<ResponseBase<FunctionDetailsResponse>?>,
                    t: Throwable
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
            requireActivity(),
            OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                var mDate = dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year

                val timePickerDialog = TimePickerDialog(
                    requireActivity(),
                    OnTimeSetListener { view, hourOfDay, minute ->
                        var mTime = "$hourOfDay:$minute"
                        if (IsStart) {
                            edtStartTime?.setText(
                                "${printDateViewFormate(mDate)} ${
                                    printTimeViewFormat(
                                        mTime
                                    )
                                }"
                            )
                        } else
                            edtEndTime?.setText(
                                "${printDateViewFormate(mDate)} ${
                                    printTimeViewFormat(
                                        mTime
                                    )
                                }"
                            )
                    },
                    mHour,
                    mMinute,
                    false
                )
                timePickerDialog.show()
            },
            mYear,
            mMonth,
            mDay
        )
        datePickerDialog.show()
    }

    private fun SetAdapter() {
        if (mFunctionDetailsList.isNotEmpty()) {
            llAddFunction?.visibility = View.VISIBLE
            imgEmptyView?.visibility = View.GONE
        } else {
            llAddFunction?.visibility = View.GONE
            imgEmptyView?.visibility = View.VISIBLE
        }
        mFunctionListAdapter = FunctionListAdapter(requireActivity(), mFunctionDetailsList)
        mRvAddFunction?.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        mRvAddFunction?.adapter = mFunctionListAdapter
        mRvAddFunction?.setHasFixedSize(true)

        mFunctionListAdapter!!.SetOnclickListner(object : FunctionListAdapter.OnclickListner {
            override fun onclick(position: Int) {
                mFunctionDetailsList.removeAt(position)
                mFunctionIdList.removeAt(position)
                SetAdapter()
            }
        })
    }

}