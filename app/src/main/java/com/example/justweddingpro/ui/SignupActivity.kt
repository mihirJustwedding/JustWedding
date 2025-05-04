package com.example.justweddingpro.ui

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.crmapplication.MyApplication
import com.example.justweddingpro.Network.RequestModel.RegisterRequest
import com.example.justweddingpro.R
import com.example.justweddingpro.Response.RegisterResponse
import com.example.justweddingpro.databinding.ActivitySignupBinding
import com.example.justweddingpro.ui.Response.ResponseBase
import com.example.justweddingpro.utils.CommonUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signup)
        setContentView(binding.root)

        binding.headerview.headerTitle.setText("Back")
        binding.headerview.frdIcon.setOnClickListener {
            onBackPressed()
        }

        binding.btnSignin.setOnClickListener { ApiAddItems() }
        binding.tvSignin.setOnClickListener { onBackPressed() }
    }

    private fun ApiAddItems() {
        var mRegisterRequest = RegisterRequest()
        mRegisterRequest.setClientUserId(0)
        mRegisterRequest.setCompanyName(binding.edtCompanyName.text.toString().trim())
        mRegisterRequest.setCityName(binding.edtCityName.text.toString().trim())
        mRegisterRequest.setCompanyAddress(binding.edtCompanyAddress.text.toString().trim())
        mRegisterRequest.setCompanyEmail(binding.edtCompanyEmail.text.toString().trim())
        mRegisterRequest.setMobileNo(binding.edtMobile.text.toString().trim())
        mRegisterRequest.setUserName(binding.edtUserName.text.toString().trim())
        mRegisterRequest.setPassword(binding.edtPassword.text.toString().trim())
        mRegisterRequest.setConfirmPassword(binding.edtCPassword.text.toString().trim())
        mRegisterRequest.setUsedreferralCode("")
        mRegisterRequest.setClientId(0)


        CommonUtils.showProgressDialog(this)
        MyApplication.getRestClient()?.API_RegisterUser(mRegisterRequest)
            ?.enqueue(object : Callback<ResponseBase<RegisterResponse>> {
                override fun onResponse(
                    call: Call<ResponseBase<RegisterResponse>?>?,
                    response: Response<ResponseBase<RegisterResponse>?>
                ) {
                    CommonUtils.hideProgressDialog()
                    if (response.isSuccessful) {
                        if (response.body()?.mData != null) {
                            ApiIsApprove(response.body()!!.mData?.getClientUserDetails()!![0]?.clientUserId.toString())
                        } else {
                            Log.d("Mytag", response.body()?.mError!!)
                        }
                    }
                }

                override fun onFailure(
                    call: Call<ResponseBase<RegisterResponse>?>, t: Throwable
                ) {
                    CommonUtils.hideProgressDialog()
                    Log.d("MyTAG", "onFailure: " + t.message)
                }
            })
    }

    private fun ApiIsApprove(mClientId: String) {
        MyApplication.getRestClient()?.API_IsApprove(mClientId)
            ?.enqueue(object : Callback<ResponseBase<RegisterResponse>> {
                override fun onResponse(
                    call: Call<ResponseBase<RegisterResponse>?>?,
                    response: Response<ResponseBase<RegisterResponse>?>
                ) {
                    CommonUtils.hideProgressDialog()
                    if (response.isSuccessful) {
                        if (response.body()?.mData != null) {
                            onBackPressed()
                            Toast.makeText(
                                this@SignupActivity,
                                response.body()!!.mMessage,
                                Toast.LENGTH_LONG
                            ).show()
                            finish()
                        } else {
                            Log.d("Mytag", response.body()?.mError!!)
                        }
                    }
                }

                override fun onFailure(
                    call: Call<ResponseBase<RegisterResponse>?>, t: Throwable
                ) {
                    CommonUtils.hideProgressDialog()
                    Log.d("MyTAG", "onFailure: " + t.message)
                }
            })
    }
}