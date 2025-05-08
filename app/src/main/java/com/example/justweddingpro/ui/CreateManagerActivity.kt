package com.example.justweddingpro.ui

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.example.crmapplication.MyApplication
import com.example.justweddingpro.Network.RequestModel.AddUserRequest
import com.example.justweddingpro.Response.MenuCategoryListResponse
import com.example.justweddingpro.databinding.ActivityCreateManagerBinding
import com.example.justweddingpro.ui.Response.AddUserResponse
import com.example.justweddingpro.ui.Response.ResponseBase
import com.example.justweddingpro.utils.CommonUtils
import com.example.justweddingpro.utils.Constants
import com.example.justweddingpro.utils.PreferenceManager
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

class CreateManagerActivity : BasedActivity() {

    private lateinit var binding: ActivityCreateManagerBinding

    private lateinit var pickImageLauncher: ActivityResultLauncher<String>
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>
    private var ImageUrlEncoder = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateManagerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.headerview.headerTitle.text = "Create Manager"
        binding.headerview.frdIcon.setOnClickListener {
            onBackPressed()
        }

        binding.btnSignin.setOnClickListener {
            mApiCreateManager()
        }

        binding.llUpload.setOnClickListener {
            checkPermissionAndPickImage()
        }

        mInitPermission()
    }

    private fun mInitPermission() {
        pickImageLauncher =
            registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
                uri?.let {
                    binding.crlImage.setImageURI(it)
                    ImageUrlEncoder = getRealPathFromURI(it, this@CreateManagerActivity)!!
                }
            }

        requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                if (isGranted) {
                    pickImageLauncher.launch("image/*")
                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun getRealPathFromURI(uri: Uri, context: Context): String? {
        val returnCursor = context.contentResolver.query(uri, null, null, null, null)
        val nameIndex = returnCursor!!.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        val sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE)
        returnCursor.moveToFirst()
        val name = returnCursor.getString(nameIndex)
        val size = returnCursor.getLong(sizeIndex).toString()
        val file = File(context.filesDir, name)
        try {
            val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
            val outputStream = FileOutputStream(file)
            var read = 0
            val maxBufferSize = 1 * 1024 * 1024
            val bytesAvailable: Int = inputStream?.available() ?: 0
            //int bufferSize = 1024;
            val bufferSize = Math.min(bytesAvailable, maxBufferSize)
            val buffers = ByteArray(bufferSize)
            while (inputStream?.read(buffers).also {
                    if (it != null) {
                        read = it
                    }
                } != -1) {
                outputStream.write(buffers, 0, read)
            }
            Log.e("File Size", "Size " + file.length())
            inputStream?.close()
            outputStream.close()
            Log.e("File Path", "Path " + file.path)

        } catch (e: java.lang.Exception) {
            Log.e("Exception", e.message!!)
        }
        return file.path
    }

    private fun mApiCreateManager() {
        CommonUtils.showProgressDialog(this@CreateManagerActivity)

        var mAddUserRequest = AddUserRequest()
        mAddUserRequest.setClientUserId(0)
        mAddUserRequest.setName(binding.edtName.text.toString().trim())
        mAddUserRequest.setAge(binding.edtAge.text.toString().trim().toInt())
        mAddUserRequest.setEmailId(binding.edtEmailID.text.toString().trim())
        mAddUserRequest.setCompanyEmail(binding.edtCompanyEmailAddress.text.toString().trim())
        mAddUserRequest.setMobileNo(binding.edtPhone.text.toString().trim())
        mAddUserRequest.setUserName(binding.edtName.text.toString().trim())
        mAddUserRequest.setPassword(binding.edtPassword.text.toString().trim())
        mAddUserRequest.setConfirmPassword(binding.edtCPassword.text.toString().trim())
        mAddUserRequest.setClientId(
            PreferenceManager.getPref(
                Constants.Preference.PREF_CLIENT_USERID,
                ""
            )?.toInt()
        )
        mAddUserRequest.setClientCategory("Manager")
        mAddUserRequest.setCityName("Ahmedabad")

        MyApplication.getRestClient()
            ?.API_AddUser(mAddUserRequest)
            ?.enqueue(object :
                Callback<ResponseBase<AddUserResponse>> {
                override fun onResponse(
                    call: Call<ResponseBase<AddUserResponse>>?,
                    response: Response<ResponseBase<AddUserResponse>?>
                ) {
                    CommonUtils.hideProgressDialog()
                    if (response.isSuccessful) {
                        if (response.body()?.mSuccess!!) {
                            Toast.makeText(
                                this@CreateManagerActivity,
                                response.body()!!.mMessage, Toast.LENGTH_LONG
                            ).show()

                            if (ImageUrlEncoder.isNotEmpty()) {
                                ApiGetUploadFile(
                                    response.body()!!.mData?.getModuleId().toString(),
                                    response.body()!!.mData?.getModuleName()!!
                                )
                            } else {
                                finish()
                            }
                        } else {
                            CommonUtils.hideProgressDialog()
                            Toast.makeText(
                                this@CreateManagerActivity,
                                response.body()?.mMessage!!,
                                Toast.LENGTH_LONG
                            ).show()
                            Log.d("Mytag", response.body()?.mMessage!!)
                        }
                    }
                }

                override fun onFailure(
                    call: Call<ResponseBase<AddUserResponse>?>,
                    t: Throwable
                ) {
                    CommonUtils.hideProgressDialog()
                    Log.d("MyTAG", "onFailure: " + t.message)
                }
            })
    }

    private fun ApiGetUploadFile(modualID: String, ItemName: String) {

        val mediaType: MediaType = "multipart/form-data".toMediaTypeOrNull()!!
        val mediaTypeText: MediaType = "text/plain".toMediaTypeOrNull()!!

        val file = File(ImageUrlEncoder)
        var sendFile: MultipartBody.Part? = null

        var fileName = ""
        val requestBody: RequestBody
        if (file != null && file.exists()) {
            requestBody = RequestBody.create(mediaType, file)
            fileName = file.name
            Log.e("data insert file: ", file.toString())
        } else {
            requestBody = RequestBody.create(mediaType, "")
        }
        sendFile = MultipartBody.Part.createFormData("file", fileName, requestBody)

        MyApplication.getRestClient()?.API_UploadFile(
            sendFile,
            RequestBody.create(mediaTypeText, modualID),
            RequestBody.create(mediaTypeText, ItemName),
            RequestBody.create(mediaTypeText, "img")
        )?.enqueue(object : Callback<ResponseBase<MenuCategoryListResponse>> {
            override fun onResponse(
                call: Call<ResponseBase<MenuCategoryListResponse>?>?,
                response: Response<ResponseBase<MenuCategoryListResponse>?>
            ) {
                CommonUtils.hideProgressDialog()
                if (response.isSuccessful) {
//                    if (response.body()?.mData != null) {
                    finish()
//                    } else {
                    Toast.makeText(
                        this@CreateManagerActivity,
                        response.body()?.mMessage!!,
                        Toast.LENGTH_LONG
                    ).show()
                    Log.d("Mytag", response.body()?.mMessage!!)
//                    }
                }
            }

            override fun onFailure(
                call: Call<ResponseBase<MenuCategoryListResponse>?>, t: Throwable
            ) {
                CommonUtils.hideProgressDialog()
                Log.d("MyTAG", "onFailure: " + t.message)
            }
        })
    }

    private fun checkPermissionAndPickImage() {
        val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Manifest.permission.READ_MEDIA_IMAGES
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE
        }

        when {
            ContextCompat.checkSelfPermission(
                this, permission
            ) == PackageManager.PERMISSION_GRANTED -> {
                pickImageLauncher.launch("image/*")
            }

            shouldShowRequestPermissionRationale(permission) -> {
                Toast.makeText(
                    this, "Permission is needed to access images", Toast.LENGTH_SHORT
                ).show()
                requestPermissionLauncher.launch(permission)
            }

            else -> {
                requestPermissionLauncher.launch(permission)
            }
        }
    }
}