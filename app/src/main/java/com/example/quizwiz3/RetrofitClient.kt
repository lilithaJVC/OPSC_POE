package com.example.quizwiz3
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager


object RetrofitClient {

    private const val BASE_URL = "https://quizwizapi-bwhegqg7bcdze9gy.canadacentral-01.azurewebsites.net/"

    // Create a custom OkHttpClient that trusts all certificates
    //code attribution
    //this code was taken from youtube
    //link:https://youtu.be/lz5lPAdA3fQ?si=JhNTuySEabDPHz3k
    private val okHttpClient: OkHttpClient by lazy {
        val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
            override fun checkClientTrusted(chain: Array<out X509Certificate>, authType: String) {}
            override fun checkServerTrusted(chain: Array<out X509Certificate>, authType: String) {}
            override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
        })

        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, trustAllCerts, java.security.SecureRandom())

        OkHttpClient.Builder()
            .sslSocketFactory(sslContext.socketFactory, trustAllCerts[0] as X509TrustManager)
            .hostnameVerifier { _, _ -> true } // This bypasses hostname verification
            .connectTimeout(10, TimeUnit.MINUTES)  // Set connection timeout to 30 seconds
            .readTimeout(10, TimeUnit.MINUTES)     // Set read timeout to 30 seconds
            .writeTimeout(10, TimeUnit.MINUTES)
            .build()
    }

    // Create Retrofit instance lazily
    //code attribution
    //this code was taken from youtube
    //link:https://youtu.be/lz5lPAdA3fQ?si=JhNTuySEabDPHz3k

    val instance: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient) // Use the custom OkHttpClient
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}