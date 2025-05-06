package com.example.justweddingpro.ClientUi

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.crmapplication.MyApplication
import com.example.justweddingpro.ClientUi.Request.AddOrderRequest
import com.example.justweddingpro.ClientUi.Response.EventFunctionMenuDetailsResponse
import com.example.justweddingpro.ClientUi.Response.ItemsDetail
import com.example.justweddingpro.ClientUi.Response.ManagerTableListResponse
import com.example.justweddingpro.ClientUi.Response.UpcomingFunctionListResponse
import com.example.justweddingpro.ClientUi.adapter.ClientFunctionListAdapter
import com.example.justweddingpro.ClientUi.adapter.HomeFoodItemListAdapter
import com.example.justweddingpro.ClientUi.adapter.HomeHorizontalAdapter
import com.example.justweddingpro.ClientUi.adapter.TableNoListAdapter
import com.example.justweddingpro.ManagerAndCaptainUi.OrderHistoryActivity
import com.example.justweddingpro.ManagerAndCaptainUi.Response.MessageEvent
import com.example.justweddingpro.R
import com.example.justweddingpro.Response.AddEventResponse
import com.example.justweddingpro.databinding.FragmentClientHomeBinding
import com.example.justweddingpro.ui.Response.ResponseBase
import com.example.justweddingpro.utils.CommonUtils
import com.example.justweddingpro.utils.Constants
import com.example.justweddingpro.utils.PreferenceManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ClientHomeFragment : Fragment() {

    private var _binding: FragmentClientHomeBinding? = null
    private val binding get() = _binding!!
    private var mManagerAssignOrderTableList =
        ArrayList<ManagerTableListResponse.ManagerTableAssignDetail>()

    private var mEventId = ""
    private var mFunctionId = ""

    companion object {
        var itemDetailsList: ArrayList<AddOrderRequest.ItemDetail>? = ArrayList()
    }

    private lateinit var mItemAdapter: ClientFunctionListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentClientHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (PreferenceManager.getPref(Constants.Preference.IS_WRITE_PERMISSION, false)!!) {
            binding.RlBottom.visibility = View.VISIBLE
//            binding.ivNavigation.visibility = View.VISIBLE
        } else {
            binding.RlBottom.visibility = View.GONE
//            binding.ivNavigation.visibility = View.GONE
        }

        binding.tvDopDownText.setOnClickListener {
            mFunctionDialog()
        }

        mApiGetFunctionList()

        itemDetailsList?.clear()
        binding.btnSignin.setOnClickListener {
            if (itemDetailsList!!.isNotEmpty()) {
                onButtonShowPopupWindowClick()
            } else {
                Toast.makeText(requireActivity(), "Please enter item first", Toast.LENGTH_LONG)
                    .show()
            }
        }

        binding.ivNavigation.setOnClickListener {
            (requireActivity() as ClientHomeActivity?)?.mOpenDrawer()
        }

//        binding.imgPopupMenu.setOnClickListener {
//            val popupMenu = PopupMenu(requireActivity(), binding.imgPopupMenu)
//            popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu())
//            popupMenu.setOnMenuItemClickListener { menuItem ->
//                if (menuItem.title!!.equals("21 Feb")) {
//                    PreferenceManager.setPref(
//                        Constants.Preference.Pref_FunctionId,
//                        "23216"
//                    )
//                    mApiCalling()
//                } else {
//                    PreferenceManager.setPref(
//                        Constants.Preference.Pref_FunctionId,
//                        "23217"
//                    )
//                    mApiCalling()
//                }
//
//                true
//            }
//            popupMenu.show()
//        }
    }

    private fun mAddOrderApi(mTableId: String) {

        var mADAddOrderRequest = AddOrderRequest()
        mADAddOrderRequest.setClientUserId(
            PreferenceManager.getPref(Constants.Preference.PREF_CLIENT_USERID, "")?.toInt()
        )
        mADAddOrderRequest.setEventId(
            mEventId.toInt()
        )
        mADAddOrderRequest.setTableId(mTableId.toInt())
        mADAddOrderRequest.setFunctionId(
            mFunctionId.toInt()
        )
        mADAddOrderRequest.setItemDetails(itemDetailsList)

        CommonUtils.showProgressDialog(requireActivity())
        MyApplication.getRestClient()
            ?.API_AddOrder(mADAddOrderRequest)
            ?.enqueue(object :
                Callback<ResponseBase<AddEventResponse>> {
                override fun onResponse(
                    call: Call<ResponseBase<AddEventResponse>?>?,
                    response: Response<ResponseBase<AddEventResponse>?>
                ) {
                    CommonUtils.hideProgressDialog()
                    if (response.isSuccessful) {
                        EventBus.getDefault().post(MessageEvent("Hello, EventBus!"))

                        Log.d("Mytag", response.body()?.mMessage!!)
                        itemDetailsList?.clear()
                        binding.tvTotalItems.text = "(${0}) Items added"
                        mApiCalling()

                        CommonUtils.confirmShowDialog(
                            requireActivity(),
                            response.body()?.mMessage!!,
                            object : CommonUtils.Companion.OnDialogClickListener {
                                override fun OnYesClick(dialog: Dialog) {
                                    startActivity(
                                        Intent(
                                            requireActivity(),
                                            OrderHistoryActivity::class.java
                                        )
                                    )
                                }

                                override fun OnNoClick(dialog: Dialog) {
                                }
                            })

                        Toast.makeText(
                            requireActivity(),
                            response.body()?.mMessage!!,
                            Toast.LENGTH_LONG
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

    private fun mApiCalling() {
        MyApplication.getRestClient()
            ?.API_Event_Menu_Details(
                mEventId, mFunctionId
            )
            ?.enqueue(object :
                Callback<ResponseBase<EventFunctionMenuDetailsResponse>> {
                override fun onResponse(
                    call: Call<ResponseBase<EventFunctionMenuDetailsResponse>?>?,
                    response: Response<ResponseBase<EventFunctionMenuDetailsResponse>?>
                ) {
                    CommonUtils.hideProgressDialog()
                    if (response.isSuccessful) {
                        if (response.body()?.mData != null) {
                            val mList = response.body()!!.mData!!.getEventMenuPlanDetails()
                            var itemsDetList: List<ItemsDetail>? = null
                            if (response.body()!!.mData!!.getEventMenuPlanDetails()!![0] !== null) {
                                itemsDetList =
                                    response.body()!!.mData!!.getEventMenuPlanDetails()!![0]?.itemsDetails
                            }

                            val linearLayoutManager = LinearLayoutManager(
                                requireActivity(),
                                RecyclerView.HORIZONTAL,
                                false
                            )

                            val mHorizontalAdapter =
                                HomeHorizontalAdapter(
                                    requireActivity(),
                                    mList as ArrayList<EventFunctionMenuDetailsResponse.EventMenuPlanDetail>
                                )
                            binding.rvItems.layoutManager = linearLayoutManager
                            binding.rvItems.adapter = mHorizontalAdapter
                            mHorizontalAdapter.SetOnclickListner(object :
                                HomeHorizontalAdapter.OnclickListner {
                                override fun onclick(
                                    position: Int,
                                    itemsDetails: List<ItemsDetail>?
                                ) {
                                    itemsDetList = itemsDetails
                                    SetItemDetailAdapter(itemsDetList)
                                }
                            })

                            SetItemDetailAdapter(itemsDetList)

                        } else {
                            Log.d("Mytag", response.body()?.mError!!)
                        }
                    }
                }

                override fun onFailure(
                    call: Call<ResponseBase<EventFunctionMenuDetailsResponse>?>,
                    t: Throwable
                ) {
                    CommonUtils.hideProgressDialog()
                    Log.d("MyTAG", "onFailure: " + t.message)
                }
            })
    }

    private fun SetItemDetailAdapter(itemsDetList: List<ItemsDetail>?) {
        val linearLayoutManager1 = LinearLayoutManager(
            requireActivity(),
            RecyclerView.VERTICAL,
            false
        )

        var mHomeFoodListAdapter =
            HomeFoodItemListAdapter(
                requireActivity(),
                itemsDetList as ArrayList<ItemsDetail>
            )

        mHomeFoodListAdapter.SetOnclickListner(object : HomeFoodItemListAdapter.OnclickListner {
            override fun onclick(position: Int) {
                binding.tvTotalItems.text = "(${position}) Items added"
            }
        })

        binding.rvListItems.layoutManager = linearLayoutManager1
        binding.rvListItems.adapter = mHomeFoodListAdapter
    }

    private fun onButtonShowPopupWindowClick() {

        val popupView: View = layoutInflater.inflate(R.layout.confirm_number_popup, null)
        val width = LinearLayout.LayoutParams.MATCH_PARENT
        val height = LinearLayout.LayoutParams.MATCH_PARENT
        val popupWindow = PopupWindow(popupView, width, height, false)
        var recyclerView = popupView.findViewById(R.id.rvPopupItem) as RecyclerView
        var cv_close = popupView.findViewById(R.id.cv_close) as ImageView
        cv_close.setOnClickListener {
            popupWindow.dismiss()
        }

        val mList = ArrayList<String>()
        mList.add("Name")
        mList.add("Address")

        val mTableNoListAdapter =
            TableNoListAdapter(
                requireActivity(),
                mManagerAssignOrderTableList
            )
        val linearLayoutManager = LinearLayoutManager(
            requireActivity(),
            RecyclerView.VERTICAL,
            false
        )
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = mTableNoListAdapter
        mTableNoListAdapter.SetOnclickListner(object :
            TableNoListAdapter.OnclickListner {
            override fun onclick(ItemId: String) {
                popupWindow.dismiss()
                Log.d("Mytag", "------" + itemDetailsList.toString())
                mAddOrderApi(ItemId)
            }
        })

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)
    }

    private fun mApiShowOrderTableList() {
        MyApplication.getRestClient()
            ?.API_GET_Manager_TableList(
                PreferenceManager.getPref(Constants.Preference.PREF_CLIENT_USERID, "")!!,
                mFunctionId

            )
            ?.enqueue(object :
                Callback<ResponseBase<ManagerTableListResponse>> {
                override fun onResponse(
                    call: Call<ResponseBase<ManagerTableListResponse>?>?,
                    response: Response<ResponseBase<ManagerTableListResponse>?>
                ) {
                    CommonUtils.hideProgressDialog()
                    if (response.isSuccessful) {
                        if (response.body()?.mData != null) {
                            mManagerAssignOrderTableList =
                                response.body()?.mData!!.getManagerTableAssignDetails() as ArrayList<ManagerTableListResponse.ManagerTableAssignDetail>
                        } else {
                            Log.d("Mytag", response.body()?.mMessage!!)
                        }
                    }
                }

                override fun onFailure(
                    call: Call<ResponseBase<ManagerTableListResponse>?>,
                    t: Throwable
                ) {
                    CommonUtils.hideProgressDialog()
                    Log.d("MyTAG", "onFailure: " + t.message)
                }
            })
    }

    private fun mFunctionDialog() {
        val dialog = Dialog(requireActivity(), R.style.TransparentStyle)
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
            requireActivity(), RecyclerView.VERTICAL, false
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

                mEventId = EventID.toString()
                mFunctionId = FunctionID.toString()

                PreferenceManager.setPref(Constants.Preference.Pref_EVENTId, mEventId)!!
                PreferenceManager.setPref(Constants.Preference.Pref_FunctionId, mFunctionId)!!
                PreferenceManager.setPref(Constants.Preference.Pref_FunctionName, FunctionName)!!

                mFunctionName = FunctionName
                binding.tvDopDownText.text = mFunctionName
            }
        })

        btnNext.setOnClickListener {
            binding.tvDopDownText.text = mFunctionName
            mApiCalling()
            dialog.dismiss()
        }

        btnBack.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun mApiGetFunctionList() {
        CommonUtils.showProgressDialog(requireActivity())
        MyApplication.getRestClient()?.API_GetUpcomingFunction(
            PreferenceManager.getPref(
                Constants.Preference.PREF_CLIENT_USERID, ""
            )!!
        )?.enqueue(object : Callback<ResponseBase<UpcomingFunctionListResponse>> {
            override fun onResponse(
                call: Call<ResponseBase<UpcomingFunctionListResponse>?>?,
                response: Response<ResponseBase<UpcomingFunctionListResponse>?>
            ) {
                if (response.isSuccessful) {
                    if (response.body()?.mSuccess!!) {
                        mItemAdapter = ClientFunctionListAdapter(
                            requireActivity(),
                            response.body()!!.mData?.getFunctionManagerAssignDetails() as List<UpcomingFunctionListResponse.FunctionManagerAssignDetail>
                        )

                        mEventId =
                            response.body()!!.mData?.getFunctionManagerAssignDetails()!![0]?.eventId.toString()
                        mFunctionId =
                            response.body()!!.mData?.getFunctionManagerAssignDetails()!![0]?.functionId.toString()

                        binding.tvDopDownText.setText(
                            response.body()!!.mData?.getFunctionManagerAssignDetails()!![0]?.functionName.toString()
                        )

                        PreferenceManager.setPref(
                            Constants.Preference.Pref_FunctionName,
                            response.body()!!.mData?.getFunctionManagerAssignDetails()!![0]?.functionName!!
                        )

                        PreferenceManager.setPref(Constants.Preference.Pref_EVENTId, mEventId)
                        PreferenceManager.setPref(
                            Constants.Preference.Pref_FunctionId,
                            mFunctionId
                        )

                        GlobalScope.launch {
                            val dispatcher = this.coroutineContext
                            CoroutineScope(dispatcher).launch {
                                mApiShowOrderTableList()
                            }
                        }

                        mApiCalling()

                    } else {
                        CommonUtils.hideProgressDialog()
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

}