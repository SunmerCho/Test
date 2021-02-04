package com.treker.test.api

import com.treker.test.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface TrekerService {

    @GET("secret/register")
    suspend fun register(@Query("register_way") register_way: Int,
                         @Query("device_type") device_type: Int,
                         @Query("device_uuid") device_uuid: String,
                         @Query("device_model") device_model: String,
                         @Query("region_code") region_code: String,
                         @Query("calling_code") calling_code: String,
                         @Query("phone_number") phone_number: String) : Call<ResponseBody>

    companion object {

        fun create(): TrekerService {
            val logger =
                HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create(TrekerService::class.java)
        }
    }
}