package com.example.justweddingpro.ui

import android.Manifest
import android.app.Dialog
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.OpenableColumns
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.crmapplication.MyApplication
import com.example.justweddingpro.Network.RequestModel.AddCategoryRequest
import com.example.justweddingpro.Network.RequestModel.AddEventMenuPlanRequest
import com.example.justweddingpro.Network.RequestModel.AddItemsRequest
import com.example.justweddingpro.R
import com.example.justweddingpro.Response.AddEventResponse
import com.example.justweddingpro.Response.AddItemsResponse
import com.example.justweddingpro.Response.MenuCategoryListResponse
import com.example.justweddingpro.databinding.ActivityMenuBinding
import com.example.justweddingpro.ui.Response.FunctionListResponse
import com.example.justweddingpro.ui.Response.MenuItemDetailsResponse
import com.example.justweddingpro.ui.Response.ResponseBase
import com.example.justweddingpro.ui.adapter.AddItemAdapter
import com.example.justweddingpro.ui.adapter.MenuFunctionAdapter
import com.example.justweddingpro.ui.adapter.MenuItemAdapter
import com.example.justweddingpro.ui.adapter.MenuItemAdapter.Companion.mSelectedItemId
import com.example.justweddingpro.ui.adapter.MenuItemAdapter.Companion.mSelectedItemList
import com.example.justweddingpro.ui.adapter.SliderMenuAdapter
import com.example.justweddingpro.utils.CommonUtils
import com.example.justweddingpro.utils.Constants
import com.example.justweddingpro.utils.PreferenceManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
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


class MenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuBinding

    private lateinit var pickImageLauncher: ActivityResultLauncher<String>
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>
    private var ImageUrlEncoder = ""
    private lateinit var mCrlImage: CircleImageView

    private var mFunctionList = ArrayList<FunctionListResponse.FunctionDetail>()

    private var mCategoryList = ArrayList<MenuCategoryListResponse.MenuCategoryDetail>()

    private var MenuCatId = ""

    private var page = 1
    private var TOTAL_PAGES = 1
    private var isLoading = true

    private var mMenuItemDetailList = ArrayList<MenuItemDetailsResponse.MenuItemDetail>()

    private lateinit var mItemAdapter: MenuFunctionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mSelectedItemList?.clear()
        mSelectedItemId?.clear()


        GlobalScope.launch {
            val dispatcher = this.coroutineContext
            CoroutineScope(dispatcher).launch {
            }
        }

        mApiGetFunctionList()


        binding.headerTitle.text = "Menu & Services"
        binding.frdIcon.setOnClickListener {
            onBackPressed()
        }

        binding.tvAddItem.setOnClickListener {
            onBottomSheetDialog()
        }

        binding.tvPlaceOrder.setOnClickListener {
            mApiAddEventMenu(false, 0)
        }

        binding.mAddCategory.setOnClickListener {
            mAddCategoryFunctionDialog()
        }

        binding.tvDopDownText.setOnClickListener {
            mFunctionDialog()
        }

        binding.mAddItem.setOnClickListener {
            confirmShowAlertDialog()
        }

        binding.nestedScroll.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            val diff: Int =
                v.getChildAt(v.getChildCount() - 1).bottom - (v.getHeight() + v.getScrollY())

            if (scrollY == (v.getChildAt(0).measuredHeight - v.measuredHeight)) {
                if (isLoading) {
                    loadMoreItems()
                }
            }
        })

        mInitPermission()
    }

    private fun mInitPermission() {
        pickImageLauncher =
            registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
                uri?.let {
                    // Handle the image URI (e.g., show in ImageView)
                    if (this::mCrlImage.isInitialized) {
                        mCrlImage.setImageURI(it)
                    }

                    if (this::CategorycrlImage.isInitialized) {
                        CategorycrlImage.setImageURI(it)
                    }
                    ImageUrlEncoder = getRealPathFromURI(it, this@MenuActivity)!!
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

    private fun onBottomSheetDialog() {
        val dialog = BottomSheetDialog(this@MenuActivity, R.style.BottomSheetDialogTheme)
        val view = layoutInflater.inflate(R.layout.item_bottomsheet_menu_dialog, null)
        val btnClose = view.findViewById(R.id.imgClose) as ImageView
        val recyclerview = view.findViewById(R.id.rvAddToCardItem) as RecyclerView
        val tvClearAll = view.findViewById(R.id.tvClearAll) as TextView
        val tvNoRecordFound = view.findViewById(R.id.tvNoRecordFound) as TextView

        btnClose.setOnClickListener {
            dialog.dismiss()
        }

        val mItemAdapter = AddItemAdapter(this, mSelectedItemList!!)

        tvClearAll.setOnClickListener {
            mSelectedItemList?.clear()
            mItemAdapter.notifyDataSetChanged()
        }

        if (mSelectedItemList!!.size > 0) {
            tvNoRecordFound.visibility = View.GONE
            recyclerview.visibility = View.VISIBLE
        } else {
            tvNoRecordFound.visibility = View.VISIBLE
            recyclerview.visibility = View.GONE
        }

        val linearLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recyclerview.layoutManager = linearLayoutManager
        recyclerview.adapter = mItemAdapter

        dialog.setCancelable(true)
        dialog.setContentView(view)
        dialog.show()
    }

    private fun onBottomSheetViewItemDialog(mModel: MenuItemDetailsResponse.MenuItemDetail) {
        val dialog = BottomSheetDialog(this@MenuActivity, R.style.BottomSheetDialogTheme)
        val view = layoutInflater.inflate(R.layout.item_bottomsheet_viewitem_dialog, null)
        val btnClose = view.findViewById(R.id.imgClose) as ImageView
        val ivImageview = view.findViewById(R.id.ivImageview) as ImageView
        val tvProductName = view.findViewById(R.id.tvProductName) as TextView
        val tvRating = view.findViewById(R.id.tvRating) as TextView
        val tvCal = view.findViewById(R.id.tvCal) as TextView
        val tvSlogan = view.findViewById(R.id.tvSlogan) as TextView

        Glide.with(this@MenuActivity)
            .load(mModel.getItemimage())
            .placeholder(R.drawable.slider3)
            .into(ivImageview)

        tvProductName.setText(mModel.getItemname())
        tvSlogan.setText(mModel.getItemslogan())

        btnClose.setOnClickListener {
            dialog.dismiss()
        }

        dialog.setCancelable(true)
        dialog.setContentView(view)
        dialog.show()
    }

    private var mIsSelectedStored = true
    private fun ApiCalling(MenuCatid: String) {
        if (isLoading)
            CommonUtils.showProgressDialog(this)

        MyApplication.getRestClient()?.GET_Menu_Planning_Item_List(
            page.toString(),
            "10",
            PreferenceManager.getPref(Constants.Preference.PREF_CLIENT_USERID, "")!!,
            MenuCatid,
            PreferenceManager.getPref(Constants.Preference.Pref_EVENTId, "")!!,
            PreferenceManager.getPref(Constants.Preference.Pref_FunctionId, "")!!
        )?.enqueue(object : Callback<ResponseBase<MenuItemDetailsResponse>> {
            override fun onResponse(
                call: Call<ResponseBase<MenuItemDetailsResponse>?>?,
                response: Response<ResponseBase<MenuItemDetailsResponse>?>
            ) {
                CommonUtils.hideProgressDialog()
                binding.idPBLoading.visibility = View.GONE

                if (response.isSuccessful) {
                    if (response.body()?.mSuccess!!) {
//                        val mList = response.body()!!.mData?.getItemDetails()!!`
                        mMenuItemDetailList.addAll(response.body()!!.mData?.getItemDetails()!!)

//                        var itemsDetList: List<MenuItemDetailsResponse.ItemDetail>? = null
//
//                        if (response.body()!!.mData!!.getMenuItems()!![0] != null) {
//                            itemsDetList =
//                                response.body()!!.mData!!.getMenuItems()!![0].itemsDetails
//                        }

                        if (mIsSelectedStored) {
                            mIsSelectedStored = false
                            for (i in response.body()!!.mData!!.getEventMenuPlanDetails()!!.indices) {
                                if (!mSelectedItemId?.contains(
                                        response.body()!!.mData!!.getEventMenuPlanDetails()!![i].getItemId()
                                            .toString()
                                    )!!
                                ) {
                                    mSelectedItemList?.add(response.body()!!.mData!!.getEventMenuPlanDetails()!![i])
                                    mSelectedItemId!!.add(
                                        response.body()!!.mData!!.getEventMenuPlanDetails()!![i].getItemId()
                                            .toString()
                                    )
                                } /*else {
                                mSelectedItemList?.remove(response.body()!!.mData!!.getEventMenuPlanDetails()!![i])
                                mSelectedItemId!!.remove(
                                    response.body()!!.mData!!.getEventMenuPlanDetails()!![i].getItemId()
                                        .toString()
                                )
                            }*/
                            }

                            mMenuItemDetailList.forEachIndexed { index, menuItemDetail ->
                                if (mSelectedItemId!!.contains(menuItemDetail.getId().toString())) {
                                    menuItemDetail.setIsSelected(true)
                                }
                            }
                        }



//                        for (i in mMenuItemDetailList.indices) {
//                            val mEventMenuPlanDetail =
//                                AddEventMenuPlanRequest.EventMenuPlanDetail()
//                            mEventMenuPlanDetail.setItemId(mMenuItemDetailList[i].getId())
//                            mEventMenuPlanDetail.setMenuName(mMenuItemDetailList[i].getItemname())
//                            mEventMenuPlanDetail.setMenuSortorder(0)
//                            mEventMenuPlanDetail.setMenuCategoryId(mMenuItemDetailList[i].getMenucategoryId())
//                            mEventMenuPlanDetail.setItemSortorder(0)
//                            mEventMenuPlanDetail.setEventmenudetailsId(mMenuItemDetailList[i].getSubmenuId())
//                            mEventMenuPlanDetail.setInstruction(mMenuItemDetailList[i].getItemslogan())
//                            mEventMenuPlanDetail.setIsadditionalcounter(0)
//                            mEventMenuPlanDetail.setItemName(mMenuItemDetailList[i].getItemname())
//                            if (mMenuItemDetailList[i].getIsSelected() == true) {
//                                if (!mSelectedItemId?.contains(
//                                        mEventMenuPlanDetail.getItemId().toString()
//                                    )!!
//                                ) {
//                                    mSelectedItemList?.add(mEventMenuPlanDetail)
//                                    mSelectedItemId!!.add(
//                                        mMenuItemDetailList[i].getId().toString()
//                                    )
//                                }
//                            } else {
//                                mSelectedItemList?.remove(mEventMenuPlanDetail)
//                                mSelectedItemId!!.remove(
//                                    mMenuItemDetailList[i].getId().toString()
//                                )
//                            }
//                        }

//                        val linearLayoutManager = LinearLayoutManager(
//                            this@MenuActivity, RecyclerView.VERTICAL, false
//                        )

//                        val mItemAdapter = SliderMenuAdapter(this@MenuActivity, mList!!)
//                        binding.rvMenuLeft.layoutManager = linearLayoutManager
//                        binding.rvMenuLeft.adapter = mItemAdapter
//                        mItemAdapter.SetOnclickListner(object :
//                            SliderMenuAdapter.OnclickListner {
//                            override fun onclick(
//                                position: Int,
//                                itemsDetails: List<MenuPlanningResponse.ItemsDetail>?
//                            ) {
//                                itemsDetList = itemsDetails
//                                SetItemDetailAdapter(itemsDetList)
//                            }
//                        })

                        TOTAL_PAGES = response.body()?.mData!!.getTotalPage()!!
                        isLoading = true
                        SetItemDetailAdapter(mMenuItemDetailList as List<MenuItemDetailsResponse.MenuItemDetail>)

                    } else {
                        Log.d("Mytag", response.body()?.mMessage!!)
                    }
                } else {
                    mMenuItemDetailList.clear()
//                    mSelectedItemList?.clear()
//                    mSelectedItemId?.clear()
                    SetItemDetailAdapter(mMenuItemDetailList as List<MenuItemDetailsResponse.MenuItemDetail>)
                    Log.d("Mytag", response.message())
                }
            }

            override fun onFailure(
                call: Call<ResponseBase<MenuItemDetailsResponse>?>, t: Throwable
            ) {
                CommonUtils.hideProgressDialog()
                Log.d("MyTAG", "onFailure: " + t.message)
            }
        })
    }

    private fun SetItemDetailAdapter(itemsDetList: List<MenuItemDetailsResponse.MenuItemDetail>) {

        val menuItemAdapter = MenuItemAdapter(this, itemsDetList!!)
        val layoutManager = GridLayoutManager(this, 2)
        binding.rvLayout.layoutManager = layoutManager
        binding.rvLayout.adapter = menuItemAdapter
        menuItemAdapter.SetOnclickListner(object : MenuItemAdapter.OnclickListner {
            override fun onclick(position: Int) {
                onBottomSheetViewItemDialog(itemsDetList[position])
            }
        })

        menuItemAdapter.SetOnSelectedListner(object : MenuItemAdapter.OnSeletedListner {
            override fun onselected(position: Int) {
                binding.cardCount.setText(position.toString())
            }
        })
    }

    private fun mApiAddEventMenu(misFunction: Boolean, SelectedFunctionID: Int) {
        CommonUtils.showProgressDialog(this@MenuActivity)
        var mAddEventMenuPlanRequest = AddEventMenuPlanRequest()
        mAddEventMenuPlanRequest.setEventmenuId(0)
        mAddEventMenuPlanRequest.setEventId(
            PreferenceManager.getPref(
                Constants.Preference.Pref_EVENTId, ""
            )?.toInt()
        )
        mAddEventMenuPlanRequest.setFunctionId(
            PreferenceManager.getPref(
                Constants.Preference.Pref_FunctionId, ""
            )?.toInt()
        )
        mAddEventMenuPlanRequest.setPax(intent.getIntExtra("mPax", 0))
        mAddEventMenuPlanRequest.setPrice(0)
        mAddEventMenuPlanRequest.setSortorder(1)
        mAddEventMenuPlanRequest.setEventMenuPlanDetails(mSelectedItemList)

        MyApplication.getRestClient()?.API_AddMenuPlanning(mAddEventMenuPlanRequest)
            ?.enqueue(object : Callback<ResponseBase<AddEventResponse>> {
                override fun onResponse(
                    call: Call<ResponseBase<AddEventResponse>?>?,
                    response: Response<ResponseBase<AddEventResponse>?>
                ) {
                    CommonUtils.hideProgressDialog()
                    mIsSelectedStored = true
                    if (response.isSuccessful) {
                        if (response.body()?.mSuccess!!) {
                            if (misFunction) {
                                PreferenceManager.setPref(
                                    Constants.Preference.Pref_FunctionId,
                                    SelectedFunctionID.toString()
                                )
                                mSelectedItemList?.clear()
                                mSelectedItemId?.clear()

                                mMenuItemDetailList.clear()
                                page = 1
                                ApiGetMenuCategoryList()
                            } else {
                                CommonUtils.confirmShowInformationDialog(
                                    this@MenuActivity,
                                    "Menu Planning Completed",
                                    object :
                                        CommonUtils.Companion.OnDialogClickListener {
                                        override fun OnYesClick(dialog: Dialog) {
                                            this@MenuActivity.finish()
                                        }

                                        override fun OnNoClick(dialog: Dialog) {}
                                    })
                            }

                        } else {
                            Toast.makeText(
                                this@MenuActivity,
                                response.body()?.mMessage!!,
                                Toast.LENGTH_SHORT
                            ).show()
                            Log.d("Mytag", response.body()?.mMessage!!)
                        }
                    } else {
                        if (misFunction) {
                            PreferenceManager.setPref(
                                Constants.Preference.Pref_FunctionId,
                                SelectedFunctionID.toString()
                            )
                            mSelectedItemList?.clear()
                            mSelectedItemId?.clear()
                        }
                        mMenuItemDetailList.clear()
                        page = 1
                        ApiGetMenuCategoryList()
                        Log.d("Mytag", response.message())
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

    private fun confirmShowAlertDialog() {
        val dialog = Dialog(this@MenuActivity, R.style.TransparentStyle)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.item_addmenu_dialog_options)
        dialog.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT
        )
        dialog.setCancelable(false)
        mCrlImage = dialog.findViewById(R.id.crlImage)
        val mItemName = dialog.findViewById<AppCompatEditText>(R.id.mItemName)
        val mItemDes = dialog.findViewById<AppCompatEditText>(R.id.mItemDes)
        val btnBack = dialog.findViewById<AppCompatButton>(R.id.btnBack)
        val btnNext = dialog.findViewById<AppCompatButton>(R.id.btnNext)
        val llUpload = dialog.findViewById<LinearLayout>(R.id.llUpload)
        val spinner = dialog.findViewById<Spinner>(R.id.spinner)
        val edtPartyName = dialog.findViewById<AppCompatEditText>(R.id.edtPartyName)

        edtPartyName.setOnClickListener {
            spinner.performClick()
        }

        val mPartyName: MutableList<String> = java.util.ArrayList()

        for (i in mCategoryList) {
            mPartyName.add(i.menuname!!)
        }
        val dataAdapter = ArrayAdapter<String>(
            this@MenuActivity, android.R.layout.simple_spinner_item, mPartyName
        )

        var CategoryId = ""
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner?.adapter = dataAdapter
        spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View, position1: Int, id: Long
            ) {
                if (position1 != 0) {
                    edtPartyName?.setText(
                        mCategoryList[position1 - 1].menuname
                    )
                    CategoryId = mCategoryList[position1 - 1].id.toString()
                } else {
                    edtPartyName?.setText("")
                    CategoryId = ""
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                edtPartyName?.setText("")
                CategoryId = ""
            }
        }
        mPartyName.add(0, "Select")
        spinner?.setSelection(0)

        llUpload.setOnClickListener {
            checkPermissionAndPickImage()
        }

        btnNext.setOnClickListener {
            ApiAddItems(
                mItemName.text.toString().trim(), mItemDes.text.toString().trim(), CategoryId
            )
            dialog.dismiss()
        }

        btnBack.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
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

    private fun ApiAddItems(mName: String, mDes: String, mCatID: String) {
        var mAddItemsRequest = AddItemsRequest()
        mAddItemsRequest.setItemId("0")
        mAddItemsRequest.setItemname(mName)
        mAddItemsRequest.setItemslogan(mDes)
        mAddItemsRequest.setItemnamelanguage("english")
        mAddItemsRequest.setItemprice(0)
        mAddItemsRequest.setMenucategoryId(mCatID.toInt())
        mAddItemsRequest.setSubmenuId(4)

        CommonUtils.showProgressDialog(this)
        MyApplication.getRestClient()?.API_AddItems(mAddItemsRequest)
            ?.enqueue(object : Callback<ResponseBase<AddItemsResponse>> {
                override fun onResponse(
                    call: Call<ResponseBase<AddItemsResponse>?>?,
                    response: Response<ResponseBase<AddItemsResponse>?>
                ) {
//                    CommonUtils.hideProgressDialog()
                    if (response.isSuccessful) {
                        if (response.body()?.mData != null) {
                            val mList = response.body()!!.mData
                            if (ImageUrlEncoder.isNotEmpty()) {
                                ApiGetUploadFile(
                                    mList?.getModuleId().toString(), mList?.getModuleName()!!
                                )
                            } else {
                                CommonUtils.hideProgressDialog()
                                mMenuItemDetailList.clear()
                                page = 1
                                ApiGetMenuCategoryList()
                            }
                        } else {
                            Log.d("Mytag", response.body()?.mError!!)
                        }
                    }
                }

                override fun onFailure(
                    call: Call<ResponseBase<AddItemsResponse>?>, t: Throwable
                ) {
                    CommonUtils.hideProgressDialog()
                    Log.d("MyTAG", "onFailure: " + t.message)
                }
            })
    }

    private fun ApiGetMenuCategoryList() {
        CommonUtils.showProgressDialog(this)
        MyApplication.getRestClient()?.API_GetMenuCategory(
            PreferenceManager.getPref(
                Constants.Preference.PREF_CLIENT_USERID, ""
            )!!
        )?.enqueue(object : Callback<ResponseBase<MenuCategoryListResponse>> {
            override fun onResponse(
                call: Call<ResponseBase<MenuCategoryListResponse>?>?,
                response: Response<ResponseBase<MenuCategoryListResponse>?>
            ) {
                if (response.isSuccessful) {
                    if (response.body()?.mSuccess!!) {
                        mCategoryList =
                            response.body()!!.mData?.getMenuCategoryDetails() as ArrayList<MenuCategoryListResponse.MenuCategoryDetail>

                        val linearLayoutManager = LinearLayoutManager(
                            this@MenuActivity, RecyclerView.VERTICAL, false
                        )

                        val mItemAdapter = SliderMenuAdapter(this@MenuActivity, mCategoryList!!)
                        binding.rvMenuLeft.layoutManager = linearLayoutManager
                        binding.rvMenuLeft.adapter = mItemAdapter
                        mItemAdapter.SetOnclickListner(object : SliderMenuAdapter.OnclickListner {
                            override fun onclick(
                                position: Int
                            ) {
                                mMenuItemDetailList.clear()
                                page = 1
                                binding.nestedScroll.scrollTo(0, 0)
                                ApiCalling(mCategoryList[position].id.toString())
                                MenuCatId = mCategoryList[position].id.toString()
                            }
                        })

                        mMenuItemDetailList.clear()
                        isLoading = false
                        MenuCatId = mCategoryList[0].id.toString()
                        binding.nestedScroll.scrollTo(0, 0)
                        ApiCalling(mCategoryList[0].id.toString())

                    } else {
                        CommonUtils.hideProgressDialog()
                        Log.d("Mytag", response.body()?.mError!!)
                    }
                } else {
                    CommonUtils.hideProgressDialog()
                    Toast.makeText(this@MenuActivity, response.message(), Toast.LENGTH_SHORT).show()
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

//        CommonUtils.showProgressDialog(this)
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
                    if (response.body()?.mSuccess!!) {
                        mMenuItemDetailList.clear()
                        page = 1
                        ApiGetMenuCategoryList()
                    } else {
                        Log.d("Mytag", response.body()?.mMessage!!)
                    }
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

    private fun mApiGetFunctionList() {
        CommonUtils.showProgressDialog(this)
        MyApplication.getRestClient()?.GET_Function_List(
            PreferenceManager.getPref(
                Constants.Preference.Pref_EVENTId, ""
            )!!
        )?.enqueue(object : Callback<ResponseBase<FunctionListResponse>> {
            override fun onResponse(
                call: Call<ResponseBase<FunctionListResponse>?>?,
                response: Response<ResponseBase<FunctionListResponse>?>
            ) {
                CommonUtils.hideProgressDialog()
                if (response.isSuccessful) {
                    if (response.body()?.mSuccess!!) {
                        mFunctionList =
                            response.body()!!.mData?.getFunctionDetails() as ArrayList<FunctionListResponse.FunctionDetail>

                        mItemAdapter = MenuFunctionAdapter(this@MenuActivity, mFunctionList)

                        PreferenceManager.setPref(
                            Constants.Preference.Pref_FunctionId,
                            mFunctionList[0].functionId.toString()
                        )

                        binding.tvDopDownText.text = mFunctionList[0].functionName

                        ApiGetMenuCategoryList()

                    } else {
                        CommonUtils.hideProgressDialog()
                        Log.d("Mytag", response.body()?.mError!!)
                    }
                }
            }

            override fun onFailure(
                call: Call<ResponseBase<FunctionListResponse>?>, t: Throwable
            ) {
                CommonUtils.hideProgressDialog()
                Log.d("MyTAG", "onFailure: " + t.message)
            }
        })
    }

    private fun mFunctionDialog() {
        val dialog = Dialog(this@MenuActivity, R.style.TransparentStyle)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.item_function_dialog_options)
        dialog.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT
        )
        dialog.setCancelable(false)
        val rvList = dialog.findViewById<RecyclerView>(R.id.rvList)
        val btnBack = dialog.findViewById<AppCompatButton>(R.id.btnBack)
        val btnNext = dialog.findViewById<AppCompatButton>(R.id.btnNext)

        val linearLayoutManager = LinearLayoutManager(
            this@MenuActivity, RecyclerView.VERTICAL, false
        )

        var mFunctionName = ""

        rvList.layoutManager = linearLayoutManager
        rvList.adapter = mItemAdapter

        var mSelectedFunctionID: Int? = null

        mItemAdapter.SetOnclickListner(object : MenuFunctionAdapter.OnclickListner {
            override fun onclick(FunctionID: Int, FunctionName: String, isClick: Boolean) {
                mSelectedFunctionID = FunctionID

                mFunctionName = FunctionName
                if (!isClick) {
                    binding.tvDopDownText.text = mFunctionName
                }
            }
        })

        btnNext.setOnClickListener {
            binding.tvDopDownText.text = mFunctionName
            mApiAddEventMenu(true, mSelectedFunctionID!!)
            dialog.dismiss()
        }

        btnBack.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun loadMoreItems() {
        isLoading = false
        page++
        if (page <= TOTAL_PAGES) {
            binding.idPBLoading.visibility = View.VISIBLE
            val handler = Handler(Looper.getMainLooper())
            handler.postDelayed({
                ApiCalling(MenuCatId)
            }, 500)
        }
    }

    override fun onResume() {
        super.onResume()
        binding.nestedScroll.scrollTo(0, 0)
        page = 1
        mMenuItemDetailList.clear()
    }

    lateinit var CategorycrlImage: CircleImageView
    private fun mAddCategoryFunctionDialog() {
        val dialog = Dialog(this@MenuActivity, R.style.TransparentStyle)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.item_addcategory_function_dialog)
        dialog.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT
        )
        dialog.setCancelable(false)
        val edtCategoryName = dialog.findViewById<AppCompatEditText>(R.id.edtCategoryName)
        val edtLanguage = dialog.findViewById<AppCompatEditText>(R.id.edtLanguage)
        val edtMenuSlogan = dialog.findViewById<AppCompatEditText>(R.id.edtMenuSlogan)
        val llUpload = dialog.findViewById<LinearLayout>(R.id.llUpload)
        CategorycrlImage = dialog.findViewById<CircleImageView>(R.id.crlImage)

        val btnBack = dialog.findViewById<AppCompatButton>(R.id.btnBack)
        val btnNext = dialog.findViewById<AppCompatButton>(R.id.btnNext)

        llUpload.setOnClickListener {
            checkPermissionAndPickImage()
        }

        btnNext.setOnClickListener {
            ApiAddCategory(
                edtCategoryName.text.toString().trim(),
                edtLanguage.text.toString().trim(),
                edtMenuSlogan.text.toString().trim()
            )
            dialog.dismiss()
        }

        btnBack.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun ApiAddCategory(mName: String, Lang: String, Slogan: String) {
        var mAddCategoryRequest = AddCategoryRequest()
        mAddCategoryRequest.setMenucategoryId(0)
        mAddCategoryRequest.setMenuname(mName)
        mAddCategoryRequest.setMenunamelanguage(Lang)
        mAddCategoryRequest.setMenuslogan(Slogan)
        mAddCategoryRequest.setPrice(10)
        mAddCategoryRequest.setIsActive(1)
        mAddCategoryRequest.setIsMenuHasSepItem(0)
        mAddCategoryRequest.setDatabaseId(2)

        mAddCategoryRequest.setClientuserId(
            PreferenceManager.getPref(
                Constants.Preference.PREF_CLIENT_USERID, ""
            )!!.toInt()
        )

        CommonUtils.showProgressDialog(this@MenuActivity)
        MyApplication.getRestClient()?.API_AddCategory(mAddCategoryRequest)
            ?.enqueue(object : Callback<ResponseBase<AddItemsResponse>> {
                override fun onResponse(
                    call: Call<ResponseBase<AddItemsResponse>?>?,
                    response: Response<ResponseBase<AddItemsResponse>?>
                ) {
                    CommonUtils.hideProgressDialog()
                    if (response.isSuccessful) {
                        if (response.body()?.mSuccess!!) {

                            if (ImageUrlEncoder.isNotEmpty()) {
                                ApiGetUploadFile(
                                    response.body()!!.mData?.getModuleId().toString(),
                                    response.body()!!.mData?.getModuleName()!!
                                )
                            } else {
                                mMenuItemDetailList.clear()
                                page = 1
                                ApiGetMenuCategoryList()
                            }
                            Toast.makeText(
                                this@MenuActivity,
                                response.body()!!.mMessage,
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Log.d("Mytag", response.body()?.mError!!)
                        }
                    }
                }

                override fun onFailure(
                    call: Call<ResponseBase<AddItemsResponse>?>, t: Throwable
                ) {
                    CommonUtils.hideProgressDialog()
                    Log.d("MyTAG", "onFailure: " + t.message)
                }
            })
    }
}