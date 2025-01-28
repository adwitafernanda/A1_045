package com.example.a1_045.service

import com.example.a1_045.model.AllTimResponse
import com.example.a1_045.model.Tim
import com.example.a1_045.model.TimDetailResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TimService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )

    //@POST("insertmahasiswa.php")
    @POST("tim/tim")
    suspend fun insertTim(@Body tim: Tim)

    //@GET("bacamahasiswa.php")
    @GET("tim/.")
    suspend fun getAllTim(): AllTimResponse

    //@GET("baca1mahasiswa.php")
    @GET("tim/{id}")
    suspend fun getTimbyId(@Path("id")idTim: String): TimDetailResponse

    // @PUT("editmahasiswa.php")
    @PUT("tim/{id}")
    suspend fun updateTim(@Path("id")idTim: String, @Body tim: Tim)

    //@DELETE("deletemahasiswa.php")
    @DELETE("tim/{id}")
    suspend fun deleteTim(@Path("id")idTim: String): Response<Void>
}