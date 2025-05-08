package com.example.justweddingpro.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.crmapplication.MyApplication
import com.example.justweddingpro.Network.RequestModel.LoginRequest
import com.example.justweddingpro.R
import com.example.justweddingpro.Response.LoginResponse
import com.example.justweddingpro.databinding.ActivityLoginBinding
import com.example.justweddingpro.ui.Response.ResponseBase
import com.example.justweddingpro.utils.CommonUtils
import com.example.justweddingpro.utils.Constants
import com.example.justweddingpro.utils.PreferenceManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        setContentView(binding.root)

        binding.headerview.headerTitle.text = "Back"
        binding.headerview.frdIcon.setOnClickListener {
            onBackPressed()
        }

        binding.tvForgotpss.setOnClickListener {
            startActivity(Intent(this@LoginActivity, ForgotPasswordActivity::class.java))
        }

        binding.tvLoginWithOtp.setOnClickListener {
            startActivity(Intent(this@LoginActivity, EnterOtpScreenActivity::class.java))
        }

        binding.tvSignup.setOnClickListener {
            startActivity(Intent(this@LoginActivity, SignupActivity::class.java))
        }

        binding.btnSignin.setOnClickListener {
            CommonUtils.showProgressDialog(this)
            val loginRequest = LoginRequest(
                binding.edtEmailID.text.toString().trim(),
                binding.edtPassword.text.toString().trim()
            )
            MyApplication.getRestClient()?.Login(loginRequest)?.enqueue(object :
                Callback<ResponseBase<LoginResponse>> {
                override fun onResponse(
                    call: Call<ResponseBase<LoginResponse>?>?,
                    response: Response<ResponseBase<LoginResponse>?>
                ) {
                    CommonUtils.hideProgressDialog()
                    if (response.isSuccessful) {
                        PreferenceManager.setPref(
                            Constants.Preference.PREF_CLIENT_USERID,
                            response.body()?.mData?.clientUserDetails!![0].clientUserId.toString()
                        )
//                        PreferenceManager.setPref(
//                            Constants.Preference.PREF_CLIENTID,
//                            response.body()?.mData?.clientUserDetails!![0].clientUserId.toString()
//                        )

                        PreferenceManager.setPref(
                            Constants.Preference.Pref_Category,
                            response.body()!!.mData!!.clientUserDetails!![0].category!!
                        )
                        PreferenceManager.setPref(
                            Constants.Preference.Pref_UserName,
                            response.body()!!.mData!!.clientUserDetails!![0].companyName!!
                        )
                        PreferenceManager.setPref(
                            Constants.Preference.Pref_Email,
                            response.body()!!.mData!!.clientUserDetails!![0].companyEmail!!
                        )
                        PreferenceManager.setPref(Constants.Preference.IS_LOGIN, true)

                        if (CommonUtils.mGetPackageName(this@LoginActivity)
                                .contains("com.example.justweddingpro")
                        ) {
                            if (response.body()!!.mData!!.clientUserDetails!![0].category.equals("mainuser")) {
                                val accountsIntent =
                                    Intent(this@LoginActivity, MainActivity::class.java)
                                accountsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                accountsIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                                accountsIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                startActivity(accountsIntent)
                                Log.d("MyTAG", "Success")
                            } else {
                                Toast.makeText(
                                    this@LoginActivity,
                                    "Wrong Credential",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<ResponseBase<LoginResponse>?>, t: Throwable) {
                    CommonUtils.hideProgressDialog()
                    Log.d("MyTAG", "onFailure: " + t.message)
                }
            })
        }
    }
}