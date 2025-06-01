package com.example.justweddingpro.ui

import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.content.res.Resources
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.crmapplication.MyApplication
import com.example.justweddingpro.R
import com.example.justweddingpro.Response.TableListResponse
import com.example.justweddingpro.databinding.ActivityMyEventDetailsBinding
import com.example.justweddingpro.ui.Response.EventDetailsResponse
import com.example.justweddingpro.ui.Response.ResponseBase
import com.example.justweddingpro.ui.adapter.EventFunctionListAdapter
import com.example.justweddingpro.utils.CommonUtils
import com.example.justweddingpro.utils.CommonUtils.Companion.parseDateAndToViewDate
import com.example.justweddingpro.utils.Constants
import com.example.justweddingpro.utils.PreferenceManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL


class MyEventDetailsActivity : BasedActivity() {

    private lateinit var binding: ActivityMyEventDetailsBinding

    companion object {
        var mIsEdite: Boolean = true
        var IsBackpress = false
    }

    override fun onResume() {
        super.onResume()
        mApiCalling()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyEventDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.headerView.headerTitle.setText("View Events")
        binding.headerView.frdIcon.setOnClickListener {
            onBackPressed()
        }

        binding.headerView.imgPopupMenu.visibility = View.VISIBLE
        binding.headerView.imgPopupMenu.setOnClickListener {
//            val popupMenu = PopupMenu(this, binding.headerView.imgPopupMenu)
//            popupMenu.getMenuInflater().inflate(R.menu.menu_main, popupMenu.getMenu())
//            popupMenu.setOnMenuItemClickListener { menuItem ->
//                if (menuItem.title!!.contains("Edite")) {
//
//                } else if (menuItem.title!!.contains("Menu Planning")) {
//
//                }
//                true
//            }
//            popupMenu.show()

            val popupwindow_obj = popupDisplay()
            popupwindow_obj.showAsDropDown(
                binding.headerView.imgPopupMenu,
                -40,
                20
            ) // where u want show on view click event popupwindow.showAsDropDown(view, x, y);


        }
    }

    private fun popupDisplay(): PopupWindow {
        val popupWindow = PopupWindow(this)

        // inflate your layout or dynamically add view
        val inflater = this.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = inflater.inflate(R.layout.popupmenu_layout, null)
        popupWindow.isFocusable = true
        popupWindow.width = WindowManager.LayoutParams.WRAP_CONTENT
        popupWindow.height = WindowManager.LayoutParams.WRAP_CONTENT
        popupWindow.contentView = view
        popupWindow.elevation = 5f
        popupWindow.setBackgroundDrawable(getDrawable(R.drawable.popup_menu_background))

        var tvEdite = view.findViewById<TextView>(R.id.tvEdite)
        var tvView = view.findViewById<TextView>(R.id.tvView)
        var tvPlanning = view.findViewById<TextView>(R.id.tvPlanning)
        var tvMenuReport = view.findViewById<TextView>(R.id.tvMenuReport)
        var tvAssignFunc = view.findViewById<TextView>(R.id.tvAssignFunc)
        var tvDelete = view.findViewById<TextView>(R.id.tvDelete)
        var tvPdf = view.findViewById<TextView>(R.id.tvPdf)

        tvAssignFunc.visibility = View.GONE
        tvPdf.visibility = View.GONE

        tvEdite.setOnClickListener {
            popupWindow.dismiss()
            mIsEdite = true
            startActivity(
                Intent(
                    this@MyEventDetailsActivity,
                    CreateEventActivity::class.java
                )
            )
        }

        tvView.setOnClickListener {
            popupWindow.dismiss()
        }

        tvPlanning.setOnClickListener {
            popupWindow.dismiss()
            startActivity(
                Intent(
                    this@MyEventDetailsActivity,
                    MenuActivity::class.java
                )
            )
        }

        tvDelete.setOnClickListener {
            popupWindow.dismiss()
            CommonUtils.confirmShowDeleteDialog(this@MyEventDetailsActivity,
                "Are you sure want to Delete?",
                object : CommonUtils.Companion.OnDialogClickListener {
                    override fun OnYesClick(dialog: Dialog) {
                        mApiDeleteTable(
                            PreferenceManager.getPref(
                                Constants.Preference.Pref_EVENTId,
                                ""
                            )!!
                        )
                    }

                    override fun OnNoClick(dialog: Dialog) {
                    }

                })
        }

        tvMenuReport.setOnClickListener {
            popupWindow.dismiss()
            startActivity(
                Intent(
                    this@MyEventDetailsActivity,
                    PDFViewerActivity::class.java
                ).putExtra("isevent", "1")
            )
        }

        return popupWindow
    }

    private fun mApiDeleteTable(EventId: String) {
        CommonUtils.showProgressDialog(this@MyEventDetailsActivity)
        MyApplication.getRestClient()?.API_GetDeleteEvent(EventId)
            ?.enqueue(object : Callback<ResponseBase<TableListResponse>> {
                override fun onResponse(
                    call: Call<ResponseBase<TableListResponse>?>?,
                    response: Response<ResponseBase<TableListResponse>?>
                ) {
                    CommonUtils.hideProgressDialog()
                    if (response.isSuccessful) {
                        if (response.body()?.mSuccess!!) {
                            IsBackpress = true
                            onBackPressed()
                        } else {
                            Log.d("Mytag", response.body()?.mError!!)
                        }
                    } else {
                        Toast.makeText(
                            this@MyEventDetailsActivity,
                            response.message(),
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }

                override fun onFailure(
                    call: Call<ResponseBase<TableListResponse>?>, t: Throwable
                ) {
                    CommonUtils.hideProgressDialog()
                    Log.d("MyTAG", "onFailure: " + t.message)
                }
            })
    }

    private fun mApiCalling() {
        CommonUtils.showProgressDialog(this@MyEventDetailsActivity)
        MyApplication.getRestClient()
            ?.GET_EventDetails(
                PreferenceManager.getPref(
                    Constants.Preference.Pref_EVENTId,
                    ""
                )!!
            )?.enqueue(object :
                Callback<ResponseBase<EventDetailsResponse>> {
                override fun onResponse(
                    call: Call<ResponseBase<EventDetailsResponse>?>?,
                    response: Response<ResponseBase<EventDetailsResponse>?>
                ) {
                    CommonUtils.hideProgressDialog()
                    if (response.isSuccessful) {
                        if (response.body()?.mData != null) {
                            var mEventDetailsResponse =
                                response.body()!!.mData!!.getEventMasterDetails()!![0]

                            mEventId =
                                mEventDetailsResponse?.eventId!!.toString()


                            binding.tvPartyName.text =
                                "Party name: ${mEventDetailsResponse?.partyName}"
                            binding.tvEventName.text = mEventDetailsResponse?.eventname
                            binding.btnStatus.setText(CommonUtils.mGetStatus(mEventDetailsResponse?.status!!))
                            binding.btnStatus.backgroundTintList =
                                (ColorStateList.valueOf(getRandomColor(mEventDetailsResponse?.status!!)))

                            binding.tvEventDate.setText(
                                "${
                                    parseDateAndToViewDate(
                                        mEventDetailsResponse.eventDate
                                    )
                                }"
                            )

                            val mEventFunctionListAdapter = EventFunctionListAdapter(
                                this@MyEventDetailsActivity,
                                mEventDetailsResponse?.eventFunctionDetails!!
                            )

                            val linearLayoutManager = LinearLayoutManager(
                                this@MyEventDetailsActivity,
                                RecyclerView.VERTICAL,
                                false
                            )
                            binding.rvList.layoutManager = linearLayoutManager
                            binding.rvList.adapter = mEventFunctionListAdapter
                            mEventFunctionListAdapter.SetOnclickListner(object :
                                EventFunctionListAdapter.OnclickListner {
                                override fun onclick(position: Int) {
                                    PreferenceManager.setPref(
                                        Constants.Preference.Pref_FunctionId,
                                        mEventDetailsResponse.eventFunctionDetails!![position].functionId.toString()!!
                                    )
                                }
                            })

                            mEventFunctionListAdapter.SetOnMenuclickListner(object :
                                EventFunctionListAdapter.OnMenuClickListner {
                                override fun onclick(position: Int, mView: View) {
                                    mFunctionId =
                                        mEventDetailsResponse.eventFunctionDetails!![position].functionId.toString()
                                    mEventId =
                                        mEventDetailsResponse.eventFunctionDetails!![position].eventId.toString()

                                    mFunctionName =
                                        mEventDetailsResponse.eventFunctionDetails!![position].functionName!!

                                    val popupwindow_obj = FunctionPopupDialog()
                                    popupwindow_obj.showAsDropDown(
                                        mView,
                                        -40,
                                        20
                                    ) // where u want show on view click event popupwindow.showAsDropDown(view, x, y);

                                }
                            })

                        } else {
                            Log.d("Mytag", response.body()?.mError!!)
                        }
                    }
                }

                override fun onFailure(
                    call: Call<ResponseBase<EventDetailsResponse>?>,
                    t: Throwable
                ) {
                    CommonUtils.hideProgressDialog()
                    Log.d("MyTAG", "onFailure: " + t.message)
                }
            })
    }

    private class DownloadFile(private val context: Context) : AsyncTask<String?, Void?, Void?>() {
        private val MEGABYTE = 1024 * 1024
        override fun doInBackground(vararg strings: String?): Void? {
            val fileUrl = strings[0] // -> http://maven.apache.org/maven-1.x/maven.pdf
            val fileName = strings[1] // -> maven.pdf

            val exportRealmPATH: File = context.getExternalFilesDir(null)!!
            val folder = File(exportRealmPATH, "testthreepdf")
            folder.mkdir()
            val pdfFile = File(folder, fileName)
            try {
                val url = URL(fileUrl)
                val urlConnection = url.openConnection() as HttpURLConnection
                urlConnection.requestMethod = "GET"
                urlConnection.doOutput = true
                urlConnection.connect()
                val inputStream = urlConnection.inputStream
                val fileOutputStream = FileOutputStream(pdfFile)
                val totalSize = urlConnection.contentLength
                val buffer = ByteArray(MEGABYTE)
                var bufferLength = 0
                while (inputStream.read(buffer).also { bufferLength = it } > 0) {
                    fileOutputStream.write(buffer, 0, bufferLength)
                }
                val path = Uri.fromFile(pdfFile)
                val pdfIntent = Intent(Intent.ACTION_VIEW)
                pdfIntent.setDataAndType(path, "application/pdf")
                pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                try {
                    context.startActivity(pdfIntent)
                } catch (e: ActivityNotFoundException) {
                    Toast.makeText(
                        context,
                        "No Application available to view PDF",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                fileOutputStream.close()
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: MalformedURLException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return null
        }
    }

    private fun getRandomColor(eventType: Int): Int {
        if (this != null) {
            val resources: Resources = this.getResources()
            return if (eventType == 0) {
                resources.getColor(R.color.inqury_event)
            } else if (eventType == 1) {
                resources.getColor(R.color.confirmed_event)
            } else if (eventType == 2) {
                resources.getColor(R.color.not_confirmed_event)
            } else {
                resources.getColor(R.color.not_confirmed_event)
            }
        }
        return 0
    }


    private fun FunctionPopupDialog(): PopupWindow {
        val popupWindow = PopupWindow(this)

        // inflate your layout or dynamically add view
        val inflater = this.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = inflater.inflate(R.layout.popupmenu_layout, null)
        popupWindow.isFocusable = true
        popupWindow.width = WindowManager.LayoutParams.WRAP_CONTENT
        popupWindow.height = WindowManager.LayoutParams.WRAP_CONTENT
        popupWindow.contentView = view
        popupWindow.elevation = 5f
        popupWindow.setBackgroundDrawable(getDrawable(R.drawable.popup_menu_background))

        var tvEdite = view.findViewById<TextView>(R.id.tvEdite)
        var tvView = view.findViewById<TextView>(R.id.tvView)
        var tvPlanning = view.findViewById<TextView>(R.id.tvPlanning)
        var tvMenuReport = view.findViewById<TextView>(R.id.tvMenuReport)
        var tvDelete = view.findViewById<TextView>(R.id.tvDelete)
        var tvAssignFunc = view.findViewById<TextView>(R.id.tvAssignFunc)
        var tvAgency = view.findViewById<TextView>(R.id.tvAgency)
        var tvInvoice = view.findViewById<TextView>(R.id.tvInvoice)
        var tvPdf = view.findViewById<TextView>(R.id.tvPdf)
        var tvWeddingIte = view.findViewById<TextView>(R.id.tvWeddingIte)

        tvPlanning.visibility = View.GONE
        tvDelete.visibility = View.VISIBLE
        tvAgency.visibility = View.GONE
        tvInvoice.visibility = View.GONE
        tvPdf.visibility = View.GONE
        tvWeddingIte.visibility = View.GONE

        tvEdite.setOnClickListener {
            popupWindow.dismiss()
            startActivity(
                Intent(
                    this@MyEventDetailsActivity, EditeFunctionActivity::class.java
                )
            )
        }

        tvView.setOnClickListener {
            popupWindow.dismiss()
        }

        tvMenuReport.setOnClickListener {
            popupWindow.dismiss()
            startActivity(
                Intent(
                    this@MyEventDetailsActivity,
                    PDFViewerActivity::class.java
                )
            )
        }

        tvAssignFunc.setOnClickListener {
            popupWindow.dismiss()
            startActivity(
                Intent(
                    this@MyEventDetailsActivity, AssignFunctionActivity::class.java
                )
            )
        }

        tvDelete.setOnClickListener {
            CommonUtils.confirmShowDeleteDialog(
                this@MyEventDetailsActivity,
                "Are you sure want to Delete?",
                object : CommonUtils.Companion.OnDialogClickListener {
                    override fun OnYesClick(dialog: Dialog) {
                        mApiDeleteFunction()
                    }

                    override fun OnNoClick(dialog: Dialog) {
                    }

                })
        }

        return popupWindow
    }

    private fun mApiDeleteFunction() {
        CommonUtils.showProgressDialog(this@MyEventDetailsActivity)
        MyApplication.getRestClient()
            ?.API_GetDeleteFunction(mEventId, mFunctionId)?.enqueue(object :
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

}