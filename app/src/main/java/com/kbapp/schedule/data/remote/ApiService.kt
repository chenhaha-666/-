package com.kbapp.schedule.data.remote

import com.kbapp.schedule.data.model.ApiResponse
import com.kbapp.schedule.data.model.InviteResponse
import com.kbapp.schedule.data.model.Schedule
import retrofit2.http.*

/**
 * 课表 API 服务接口
 */
interface ApiService {
    
    /**
     * 验证邀请码
     */
    @GET("api/invite/{code}")
    suspend fun verifyInviteCode(@Path("code") code: String): InviteResponse
    
    /**
     * 获取课表（按周次）
     */
    @GET("api/schedule/{inviteCode}")
    suspend fun getSchedules(
        @Path("inviteCode") inviteCode: String,
        @Query("week") week: Int? = null,
        @Query("day") day: Int? = null
    ): ApiResponse<Schedule>
    
    /**
     * 获取指定周次和星期的课表
     */
    @GET("api/schedule/{inviteCode}")
    suspend fun getScheduleByDay(
        @Path("inviteCode") inviteCode: String,
        @Query("week") week: Int,
        @Query("day") day: Int
    ): ApiResponse<Schedule>
}

/**
 * 创建 Retrofit 实例
 */
object ApiClient {
    private const val BASE_URL = "https://overhuman-ed-lineally.ngrok-free.dev/"
    
    fun createApiService(): ApiService {
        val client = okhttp3.OkHttpClient.Builder()
            .addInterceptor(okhttp3.logging.HttpLoggingInterceptor().apply {
                level = okhttp3.logging.HttpLoggingInterceptor.Level.BODY
            })
            .build()
        
        val retrofit = retrofit2.Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create())
            .build()
        
        return retrofit.create(ApiService::class.java)
    }
}
