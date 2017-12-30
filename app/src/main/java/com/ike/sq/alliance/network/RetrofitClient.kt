package com.bjike.issp.network

import android.content.Context
import android.util.Log
import com.ike.sq.alliance.R
import com.ike.sq.alliance.network.HttpsUtils
import com.ike.sq.alliance.utils.CookieManger
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLSession


/**
 * Created by T-BayMax on 2017/8/21.
 */
class RetrofitClient private constructor(context: Context, baseUrl: String) {
    var httpCacheDirectory: File? = null
    val mContext: Context = context
    var cache: Cache? = null
    var okHttpClient: OkHttpClient? = null
    var retrofit: Retrofit? = null
    val DEFAULT_TIMEOUT: Long = 10
    val url = baseUrl

    init {
        //缓存地址
        if (httpCacheDirectory == null) {
            httpCacheDirectory = File(mContext.cacheDir, "app_cache")
        }
        try {
            if (cache == null) {
                cache = Cache(httpCacheDirectory, 10 * 1024 * 1024)
            }
        } catch (e: Exception) {
            Log.e("OKHttp", "Could not create http cache", e)
        }
        val certficates = intArrayOf(R.raw.issp)
        val certificate = context.resources.openRawResource(certficates[0])
        val sslParams = HttpsUtils.getSslSocketFactory(null, certificate, null)
        //okhttp创建了
        okHttpClient = OkHttpClient.Builder()
                //   .addNetworkInterceptor(
                //        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                //  .cache(cache)
                // .addInterceptor(CacheInterceptor(context))
                //.addNetworkInterceptor(CacheInterceptor(context))
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                .cookieJar(CookieManger(context))
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .retryOnConnectionFailure(false)
                .hostnameVerifier(HostnameVerifier { _: String, _: SSLSession ->
                    true
                })
                .build()
        //retrofit创建了
        retrofit = Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(url)
                .build()
    }

    companion object {
        @Volatile
        var instance: RetrofitClient? = null

        fun getInstance(context: Context, baseUrl: String): RetrofitClient {
            if (instance == null) {
                synchronized(RetrofitClient::class) {
                    if (instance == null) {
                        instance = RetrofitClient(context, baseUrl)
                    }
                }
            }
            return instance!!
        }

    }

    fun <T> create(service: Class<T>?): T? {
        if (service == null) {
            throw RuntimeException("Api service is null!")
        }
        return retrofit?.create(service)
    }
}

fun RetrofitClient.add(x: Int, y: Int): Int {
    return x + y
}