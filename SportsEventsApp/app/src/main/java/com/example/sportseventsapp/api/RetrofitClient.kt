package com.example.sportseventsapp.api

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.nio.charset.Charset

object RetrofitClient {
    private const val BASE_URL = "https://618d3aa7fe09aa001744060a.mockapi.io/api/"

    private val gson: Gson = GsonBuilder()
        .setLenient()
        .create()

    val instance: Retrofit by lazy {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val response = chain.proceed(chain.request())
                val responseBody = response.body
                if (responseBody != null) {
                    var charset = Charset.forName("UTF-8") // Adjust charset as per your response
                    val contentType = responseBody.contentType()
                    if (contentType != null) {
                        charset = contentType.charset(charset)
                    }
                    val bodyString = responseBody.string() // Convert response body to string
                    // Rebuild the response before passing it to Retrofit
                    return@addInterceptor response.newBuilder()
                        .body(bodyString.toByteArray(charset).toResponseBody(contentType))
                        .build()
                }
                response
            }
            .build()

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
    }
}