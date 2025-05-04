package com.example.justweddingpro.ui

import android.Manifest
import android.app.DownloadManager
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.net.http.SslError
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.webkit.CookieManager
import android.webkit.SslErrorHandler
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.example.crmapplication.MyApplication
import com.example.justweddingpro.Response.TableListResponse
import com.example.justweddingpro.databinding.ActivityPdfviewerBinding
import com.example.justweddingpro.ui.Response.ResponseBase
import com.example.justweddingpro.utils.CommonUtils
import com.example.justweddingpro.utils.Constants
import com.example.justweddingpro.utils.PreferenceManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.URI


class PDFViewerActivity : BasedActivity() {

    private lateinit var binding: ActivityPdfviewerBinding
    private var mFileUrl = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPdfviewerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.header.headerTitle.text = "Report"
        binding.header.frdIcon.setOnClickListener { onBackPressed() }

        APIgetPdf()

        binding.header.btnDownload.visibility = View.VISIBLE
        binding.header.btnDownload.setOnClickListener { checkPermissionAndDownload() }

    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                downloadPdfFile()
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }

    private fun checkPermissionAndDownload() {
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            downloadPdfFile()
        } else {
            requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
    }

    private fun downloadPdfFile() {
        val url = mFileUrl
        val request = DownloadManager.Request(Uri.parse(url))
        request.setTitle("Downloading PDF")
        request.setDescription("Downloading sample.pdf")
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "sample.pdf")

        val downloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        downloadManager.enqueue(request)

        Toast.makeText(this, "Download started...", Toast.LENGTH_SHORT).show()
    }

    private fun APIgetPdf() {
        CommonUtils.showProgressDialog(this@PDFViewerActivity)
        MyApplication.getRestClient()
            ?.API_getPdfReport(
                PreferenceManager.getPref(
                    Constants.Preference.PREF_CLIENT_USERID,
                    ""
                )!!, mEventId, mFunctionId
            )?.enqueue(object :
                Callback<ResponseBase<TableListResponse>> {
                override fun onResponse(
                    call: Call<ResponseBase<TableListResponse>?>?,
                    response: Response<ResponseBase<TableListResponse>?>
                ) {
                    CommonUtils.hideProgressDialog()
                    if (response.isSuccessful) {
                        if (response.body()?.mSuccess!!) {

                            mFileUrl = URI(response.body()!!.filePath!!).normalize().toString()

                            val webSettings = binding.mWebview.settings
                            webSettings.javaScriptEnabled = true
                            webSettings.domStorageEnabled = true
                            webSettings.useWideViewPort = true
                            webSettings.loadWithOverviewMode = true
                            webSettings.allowFileAccess = true
                            webSettings.allowContentAccess = true
                            webSettings.mixedContentMode =
                                WebSettings.MIXED_CONTENT_ALWAYS_ALLOW  // Allow all content
                            webSettings.javaScriptCanOpenWindowsAutomatically = true
                            webSettings.setSupportMultipleWindows(true)
                            webSettings.setSupportZoom(false)
                            binding.mWebview.isVerticalScrollBarEnabled = false
                            binding.mWebview.isHorizontalScrollBarEnabled = false


                            val cookieManager = CookieManager.getInstance()
                            cookieManager.setAcceptCookie(true)
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                cookieManager.setAcceptThirdPartyCookies(binding.mWebview, true)
                            }

                            webSettings.setDatabasePath(
                                getApplicationContext().getFilesDir()
                                    .getAbsolutePath() + "/databases"
                            )

//                            val progressDialog = ProgressDialog(this@PDFViewerActivity)
//                            progressDialog.setMessage("Loading...")
//                            progressDialog.show()

                            binding.mWebview.webViewClient = object : WebViewClient() {
                                override fun shouldOverrideUrlLoading(
                                    view: WebView,
                                    url: String
                                ): Boolean {
                                    view.loadUrl(url)
                                    return true
                                }

                                override fun onPageFinished(view: WebView, url: String) {
//                                    if (progressDialog.isShowing()) {
//                                        progressDialog.dismiss()
//                                    }
                                }

                                override fun onReceivedError(
                                    view: WebView,
                                    errorCode: Int,
                                    description: String,
                                    failingUrl: String
                                ) {
                                    Toast.makeText(
                                        this@PDFViewerActivity,
                                        "Error:$description",
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                }

                                override fun onReceivedSslError(
                                    view: WebView?,
                                    handler: SslErrorHandler?,
                                    error: SslError?
                                ) {
                                    handler?.proceed() // ⚠️ DANGEROUS: Accepts all SSL certs
                                }
                            }

                            val pdfViewerURL =
                                "https://drive.google.com/viewerng/viewer?embedded=true&url="
                            val mUrl = pdfViewerURL + mFileUrl

                            binding.mWebview.apply {
                                this.loadUrl(mUrl)
                            }

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