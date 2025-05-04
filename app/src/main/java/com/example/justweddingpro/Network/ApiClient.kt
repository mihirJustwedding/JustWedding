package com.example.justweddingpro.Network

import android.content.Context
import android.util.Log
import com.example.justweddingpro.utils.CommonUtils
import com.example.justweddingpro.utils.Constants
import com.example.justweddingpro.utils.PreferenceManager
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient(var context: Context) {

    private var apiService: ApiInterface? = null

    fun Client() {

        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val builder: OkHttpClient.Builder =
            OkHttpClient.Builder().addInterceptor(interceptor).addInterceptor(SupportInterceptor())
        builder.connectTimeout(5, TimeUnit.MINUTES)
            .writeTimeout(5, TimeUnit.MINUTES)
            .readTimeout(5, TimeUnit.MINUTES)
        val client: OkHttpClient = builder.build()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(APIConfig.SERVER_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        apiService = retrofit.create(ApiInterface::class.java)
    }

    fun getApiService(): ApiInterface? {
        return apiService
    }

    /* internal class ResponseInterceptor : Interceptor {
         @Throws(IOException::class)
         override fun intercept(chain: Interceptor.Chain): Response {
             val postBodyString: String = CommonUtils.bodyToString(chain.request().body)
             Log.d("ApiTag", "REQ_PARAMS: $postBodyString")
             val request: Request = chain.request()
             var response: Response? = null
             return response
         }
     }*/

    internal class SupportInterceptor : Interceptor {

        override fun intercept(chain: Interceptor.Chain): Response {
            var request = chain.request()
            val postBodyString: String = CommonUtils.bodyToString(request.body)
            Log.d("ApiTag", "REQ_PARAMS: $postBodyString")
            val response: Response? = null
            request = request.newBuilder()
                .addHeader("Accept", "application/json")
                .addHeader(
                    APIConfig.ApiConst.Authorization,
                    PreferenceManager.getPref(Constants.Preference.BEARER_TOKEN, "")!!
                )
                .build()
            return chain.proceed(request)
        }

        /*override fun authenticate(route: Route?, response: Response): Request? {
            var requestAvailable: Request? = null
            try {
                requestAvailable = response.request.newBuilder()
                        .addHeader("Authorization", PreferenceManager.getPref(Constants.Preference.BEARER_TOKEN, "")!!)
                        .build()
            } catch (e: Exception) {
            }
            return requestAvailable
        }*/
    }
}