// data/remote/ApiService.kt
package com.example.architectureexplorer.data.remote

import com.example.architectureexplorer.data.model.LoginResponse
import com.example.architectureexplorer.data.model.DashboardResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("{campus}/auth")
    suspend fun login(
        @Path("campus") campus: String,
        @Body credentials: Map<String, String>
    ): LoginResponse

    @GET("dashboard/{keypass}")
    suspend fun getDashboard(
        @Path("keypass") keypass: String
    ): DashboardResponse
}