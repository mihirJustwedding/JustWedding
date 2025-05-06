package com.example.justweddingpro.ManagerAndCaptainUi

import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.crmapplication.MyApplication
import com.example.justweddingpro.ClientUi.Request.OrderStatusChange
import com.example.justweddingpro.ClientUi.Response.UpcomingFunctionListResponse
import com.example.justweddingpro.ClientUi.adapter.ClientFunctionListAdapter
import com.example.justweddingpro.ManagerAndCaptainUi.Response.MessageEvent
import com.example.justweddingpro.ManagerAndCaptainUi.Response.OrderListTableResponse
import com.example.justweddingpro.ManagerAndCaptainUi.adapter.OrderHistoryAdapter
import com.example.justweddingpro.R
import com.example.justweddingpro.Response.AddEventResponse
import com.example.justweddingpro.databinding.ActivityOrderHistoryBinding
import com.example.justweddingpro.ui.Response.ResponseBase
import com.example.justweddingpro.utils.CommonUtils
import com.example.justweddingpro.utils.Constants
import com.example.justweddingpro.utils.PreferenceManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderHistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOrderHistoryBinding
    private var mHandler: Handler? = null

    private lateinit var mItemAdapter: ClientFunctionListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.headerTitle.frdIcon.setOnClickListener {
            onBackPressed()
        }

        if (PreferenceManager.getPref(Constants.Preference.IS_WRITE_PERMISSION, false) == false) {
            binding.headerTitle.headerTitle.text = "Live Order List"
        } else {
            binding.headerTitle.headerTitle.text = "OrderHistory"
        }

        mApiGetFunctionList()
        binding.tvDopDownText.setOnClickListener {
            mFunctionDialog()
        }

        binding.headerTitle.imgPopupMenu.setOnClickListener {
            val popupMenu = PopupMenu(this, binding.headerTitle.imgPopupMenu)
            popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu())
            popupMenu.setOnMenuItemClickListener { menuItem ->
                if (menuItem.title!!.equals("21 Feb")) {
                    PreferenceManager.setPref(
                        Constants.Preference.Pref_FunctionId,
                        "23216"
                    )
                    mApiCalling(false)
                } else {
                    PreferenceManager.setPref(
                        Constants.Preference.Pref_FunctionId,
                        "23217"
                    )
                    mApiCalling(false)
                }

                true
            }
            popupMenu.show()
        }

        mHandler = Handler()
        startRepeatingTask()
    }

    fun startRepeatingTask() {
        mHandler!!.postDelayed(mStatusChecker, 3000)
    }

    fun stopRepeatingTask() {
        mHandler!!.removeCallbacks(mStatusChecker)
    }

    var mStatusChecker: Runnable = object : Runnable {
        override fun run() {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    withContext(Dispatchers.Main) {
                        mApiCalling(true)
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            applicationContext,
                            "Request failed: ${e.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
            mHandler!!.postDelayed(this, 10000)
        }
    }

    private fun mApiCalling(mIsBus: Boolean) {
        if (!mIsBus)
            CommonUtils.showProgressDialog(this)
        MyApplication.getRestClient()
            ?.API_GETOrderListByTableDetails(
                PreferenceManager.getPref(Constants.Preference.Pref_EVENTId, "")!!,
                PreferenceManager.getPref(Constants.Preference.Pref_FunctionId, "")!!
//                PreferenceManager.getPref(Constants.Preference.PREF_CLIENT_USERID, "")!!
            )
            ?.enqueue(object :
                Callback<ResponseBase<OrderListTableResponse>> {
                override fun onResponse(
                    call: Call<ResponseBase<OrderListTableResponse>>?,
                    response: Response<ResponseBase<OrderListTableResponse>?>
                ) {
                    CommonUtils.hideProgressDialog()
                    if (response.isSuccessful) {
                        if (response.body()?.mData != null) {
                            binding.tvNoRecFound.visibility = View.GONE
                            binding.mRvOrderHistory.visibility = View.VISIBLE

                            val mList = response.body()!!.mData!!.orderTableDetails
                            val linearLayoutManager = LinearLayoutManager(
                                this@OrderHistoryActivity,
                                RecyclerView.VERTICAL,
                                false
                            )

                            val mAdapter =
                                OrderHistoryAdapter(
                                    this@OrderHistoryActivity,
                                    mList as List<OrderListTableResponse.OrderTableDetail>
                                )

                            mAdapter.SetOnclickListner(object : OrderHistoryAdapter.OnclickListner {
                                override fun onclick(OrderTableId: Int, Status: String) {
                                    mApiChangeStatus(OrderTableId, mStatus = Status)
                                }
                            })

                            binding.mRvOrderHistory.layoutManager = linearLayoutManager
                            binding.mRvOrderHistory.adapter = mAdapter
                        } else {
                            Log.d("Mytag", response.body()?.mMessage!!)
                        }
                    } else {
                        binding.tvNoRecFound.visibility = View.VISIBLE
                        binding.mRvOrderHistory.visibility = View.GONE
                    }
                }

                override fun onFailure(
                    call: Call<ResponseBase<OrderListTableResponse>?>,
                    t: Throwable
                ) {
                    CommonUtils.hideProgressDialog()
                    Log.d("MyTAG", "onFailure: " + t.message)
                }
            })
    }

    private fun mApiChangeStatus(OrderTableId: Int, mStatus: String) {

        var mList = ArrayList<Int>()
        mList.add(OrderTableId)

        var mOrderStatusChange = OrderStatusChange()
        mOrderStatusChange.setStatus(mStatus)
        mOrderStatusChange.setOrderTableId(mList)

        CommonUtils.showProgressDialog(this)
        MyApplication.getRestClient()
            ?.API_ORDER_Status_Change(mOrderStatusChange)
            ?.enqueue(object :
                Callback<ResponseBase<AddEventResponse>> {
                override fun onResponse(
                    call: Call<ResponseBase<AddEventResponse>?>?,
                    response: Response<ResponseBase<AddEventResponse>?>
                ) {
                    CommonUtils.hideProgressDialog()
                    if (response.isSuccessful) {
                        mApiCalling(false)
                        Toast.makeText(
                            this@OrderHistoryActivity,
                            response.body()?.mMessage,
                            Toast.LENGTH_SHORT
                        ).show()
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

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    @Throws(ClassNotFoundException::class)
    fun onEvent(event: MessageEvent) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                withContext(Dispatchers.Main) {
                    println("Received event: ${event.message}")
                    mApiCalling(true)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        applicationContext,
                        "Request failed: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun mFunctionDialog() {
        val dialog = Dialog(this@OrderHistoryActivity, R.style.TransparentStyle)
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
            this@OrderHistoryActivity, RecyclerView.VERTICAL, false
        )

        var mFunctionName = ""

        rvList.layoutManager = linearLayoutManager
        rvList.adapter = mItemAdapter
        mItemAdapter.SetOnclickListner(object : ClientFunctionListAdapter.OnclickListner {
            override fun onclick(
                FunctionID: Int,
                FunctionName: String,
                EventID: Int,
                isClick: Boolean
            ) {

                val mEventId = EventID.toString()
                val mFunctionId = FunctionID.toString()

                PreferenceManager.setPref(Constants.Preference.Pref_EVENTId, mEventId)!!
                PreferenceManager.setPref(Constants.Preference.Pref_FunctionId, mFunctionId)!!
                PreferenceManager.setPref(
                    Constants.Preference.Pref_FunctionName,
                    FunctionName
                )

                mFunctionName = FunctionName
                binding.tvDopDownText.text = mFunctionName
            }
        })

        btnNext.setOnClickListener {
            mApiCalling(false)
            binding.tvDopDownText.text = mFunctionName
            dialog.dismiss()
        }

        btnBack.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun mApiGetFunctionList() {
        CommonUtils.showProgressDialog(this@OrderHistoryActivity)
        MyApplication.getRestClient()?.API_GetUpcomingFunction(
            PreferenceManager.getPref(
                Constants.Preference.PREF_CLIENT_USERID, ""
            )!!
        )?.enqueue(object : Callback<ResponseBase<UpcomingFunctionListResponse>> {
            override fun onResponse(
                call: Call<ResponseBase<UpcomingFunctionListResponse>?>?,
                response: Response<ResponseBase<UpcomingFunctionListResponse>?>
            ) {
                CommonUtils.hideProgressDialog()
                if (response.isSuccessful) {
                    if (response.body()?.mSuccess!!) {
                        mItemAdapter = ClientFunctionListAdapter(
                            this@OrderHistoryActivity,
                            response.body()!!.mData?.getFunctionManagerAssignDetails() as List<UpcomingFunctionListResponse.FunctionManagerAssignDetail>
                        )
                        binding.tvDopDownText.text = PreferenceManager.getPref(
                            Constants.Preference.Pref_FunctionName,
                            ""
                        )

                        mApiCalling(true)

                    } else {
                        Log.d("Mytag", response.body()?.mError!!)
                    }
                }
            }

            override fun onFailure(
                call: Call<ResponseBase<UpcomingFunctionListResponse>?>, t: Throwable
            ) {
                CommonUtils.hideProgressDialog()
                Log.d("MyTAG", "onFailure: " + t.message)
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        // Unregister to avoid memory leaks
        EventBus.getDefault().unregister(this)
    }

    override fun onStop() {
        super.onStop()
        stopRepeatingTask()
    }
}