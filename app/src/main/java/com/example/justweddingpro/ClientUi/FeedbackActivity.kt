package com.example.justweddingpro.ClientUi

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.crmapplication.MyApplication
import com.example.justweddingpro.ClientUi.Request.FeedBackRequest
import com.example.justweddingpro.R
import com.example.justweddingpro.Response.AddEventResponse
import com.example.justweddingpro.databinding.ActivityFeedbackBinding
import com.example.justweddingpro.ui.Response.ResponseBase
import com.example.justweddingpro.utils.CommonUtils
import com.example.justweddingpro.utils.Constants
import com.example.justweddingpro.utils.PreferenceManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Calendar

class FeedbackActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFeedbackBinding
    private var mRating = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeedbackBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.headerview.headerTitle.text = "Feedback"
        binding.headerview.frdIcon.setOnClickListener {
            onBackPressed()
        }

        mInit()

        binding.btnSignin.setOnClickListener {
            mAddFeedbackApi()
        }

        binding.edtDOB.setOnClickListener {
            DateAndTimePickerDialog(true)
        }

        binding.edtAnyversryDOB.setOnClickListener {
            DateAndTimePickerDialog(false)
        }
    }

    private fun DateAndTimePickerDialog(IsStart: Boolean) {
        val c: Calendar = Calendar.getInstance()
        var mYear = c.get(Calendar.YEAR)
        var mMonth = c.get(Calendar.MONTH)
        var mDay = c.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this@FeedbackActivity,
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                var mDate = dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year
                if (IsStart) {
                    binding.edtDOB.setText("${CommonUtils.printDateViewFormate(mDate)}")
                } else {
                    binding.edtAnyversryDOB.setText("${CommonUtils.printDateViewFormate(mDate)}")
                }
            },
            mYear,
            mMonth,
            mDay
        )
        datePickerDialog.show()
    }

    private fun mAddFeedbackApi() {
        var mFeedBackRequest = FeedBackRequest()
        mFeedBackRequest.setEventId(
            PreferenceManager.getPref(
                Constants.Preference.Pref_EVENTId,
                ""
            )!!
        )
        mFeedBackRequest.setClientId(
            PreferenceManager.getPref(Constants.Preference.PREF_CLIENTID, "")?.toInt()
        )
        mFeedBackRequest.setRating(mRating)
        mFeedBackRequest.setPersonName(binding.edtName.text.toString().trim())
        mFeedBackRequest.setMobileNo(binding.edtMobile.text.toString().trim())
        mFeedBackRequest.setCityName(binding.edtCityName.text.toString().trim())
        mFeedBackRequest.setInstagramId(binding.edtInstagram.text.toString().trim())
        mFeedBackRequest.setDateOfBirth(
            CommonUtils.parseOnlyDateToUploadFormat(
                binding.edtDOB.text.toString().trim()
            )
        )
        mFeedBackRequest.setAnniversaryDate(
            CommonUtils.parseOnlyDateToUploadFormat(
                binding.edtAnyversryDOB.text.toString().trim()
            )
        )
        mFeedBackRequest.setDescription(binding.edtDescription.text.toString().trim())

        CommonUtils.showProgressDialog(this)
        MyApplication.getRestClient()
            ?.API_Feedback(mFeedBackRequest)
            ?.enqueue(object :
                Callback<ResponseBase<AddEventResponse>> {
                override fun onResponse(
                    call: Call<ResponseBase<AddEventResponse>?>?,
                    response: Response<ResponseBase<AddEventResponse>?>
                ) {
                    CommonUtils.hideProgressDialog()
                    if (response.isSuccessful) {
                        CommonUtils.confirmShowAlertDialog(
                            this@FeedbackActivity,
                            response.body()?.mMessage!!,
                            object : CommonUtils.Companion.OnDialogClickListener {
                                override fun OnYesClick(dialog: Dialog) {
                                    this@FeedbackActivity.finish()
                                }

                                override fun OnNoClick(dialog: Dialog) {}
                            })
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

    private fun mInit() {
        binding.tv1.setOnClickListener {
            binding.tv1.background = getDrawable(R.drawable.active_ovel_background)
            binding.tv1.setTextColor(getColor(R.color.white))
            binding.tv2.background = getDrawable(R.drawable.ovel_background)
            binding.tv2.setTextColor(getColor(R.color.Secondary_color))
            binding.tv3.background = getDrawable(R.drawable.ovel_background)
            binding.tv3.setTextColor(getColor(R.color.Secondary_color))
            binding.tv4.background = getDrawable(R.drawable.ovel_background)
            binding.tv4.setTextColor(getColor(R.color.Secondary_color))
            binding.tv5.background = getDrawable(R.drawable.ovel_background)
            binding.tv5.setTextColor(getColor(R.color.Secondary_color))
            mRating = 1
        }

        binding.tv2.setOnClickListener {
            binding.tv2.background = getDrawable(R.drawable.active_ovel_background)
            binding.tv2.setTextColor(getColor(R.color.white))
            binding.tv1.background = getDrawable(R.drawable.ovel_background)
            binding.tv1.setTextColor(getColor(R.color.Secondary_color))
            binding.tv3.background = getDrawable(R.drawable.ovel_background)
            binding.tv3.setTextColor(getColor(R.color.Secondary_color))
            binding.tv4.background = getDrawable(R.drawable.ovel_background)
            binding.tv4.setTextColor(getColor(R.color.Secondary_color))
            binding.tv5.background = getDrawable(R.drawable.ovel_background)
            binding.tv5.setTextColor(getColor(R.color.Secondary_color))
            mRating = 2
        }

        binding.tv3.setOnClickListener {
            binding.tv3.background = getDrawable(R.drawable.active_ovel_background)
            binding.tv3.setTextColor(getColor(R.color.white))
            binding.tv2.background = getDrawable(R.drawable.ovel_background)
            binding.tv2.setTextColor(getColor(R.color.Secondary_color))
            binding.tv1.background = getDrawable(R.drawable.ovel_background)
            binding.tv1.setTextColor(getColor(R.color.Secondary_color))
            binding.tv4.background = getDrawable(R.drawable.ovel_background)
            binding.tv4.setTextColor(getColor(R.color.Secondary_color))
            binding.tv5.background = getDrawable(R.drawable.ovel_background)
            binding.tv5.setTextColor(getColor(R.color.Secondary_color))
            mRating = 3
        }

        binding.tv4.setOnClickListener {
            binding.tv4.background = getDrawable(R.drawable.active_ovel_background)
            binding.tv4.setTextColor(getColor(R.color.white))
            binding.tv2.background = getDrawable(R.drawable.ovel_background)
            binding.tv2.setTextColor(getColor(R.color.Secondary_color))
            binding.tv3.background = getDrawable(R.drawable.ovel_background)
            binding.tv3.setTextColor(getColor(R.color.Secondary_color))
            binding.tv1.background = getDrawable(R.drawable.ovel_background)
            binding.tv1.setTextColor(getColor(R.color.Secondary_color))
            binding.tv5.background = getDrawable(R.drawable.ovel_background)
            binding.tv5.setTextColor(getColor(R.color.Secondary_color))
            mRating = 4
        }

        binding.tv5.setOnClickListener {
            binding.tv5.background = getDrawable(R.drawable.active_ovel_background)
            binding.tv5.setTextColor(getColor(R.color.white))
            binding.tv2.background = getDrawable(R.drawable.ovel_background)
            binding.tv2.setTextColor(getColor(R.color.Secondary_color))
            binding.tv3.background = getDrawable(R.drawable.ovel_background)
            binding.tv3.setTextColor(getColor(R.color.Secondary_color))
            binding.tv4.background = getDrawable(R.drawable.ovel_background)
            binding.tv4.setTextColor(getColor(R.color.Secondary_color))
            binding.tv1.background = getDrawable(R.drawable.ovel_background)
            binding.tv1.setTextColor(getColor(R.color.Secondary_color))
            mRating = 5
        }
    }
}
