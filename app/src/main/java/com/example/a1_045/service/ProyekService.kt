package com.example.a1_045.service

import com.example.a1_045.model.AllProyekResponse
import com.example.a1_045.model.Proyek
import com.example.a1_045.model.ProyekDetailResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ProyekService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )

    //@POST("insertmahasiswa.php")
    @POST("proyek/proyek")
    suspend fun insertProyek(@Body proyek: Proyek)

    //@GET("bacamahasiswa.php")
    @GET("proyek/.")
    suspend fun getAllProyek(): AllProyekResponse

    //@GET("baca1mahasiswa.php")
    @GET("proyek/{id}")
    suspend fun getProyekbyId(@Path("id")idProyek: String): ProyekDetailResponse

    // @PUT("editmahasiswa.php")
    @PUT("proyek/{id}")
    suspend fun updateProyek(@Path("id")idProyek: String, @Body proyek: Proyek)

    //@DELETE("deletemahasiswa.php")
    @DELETE("proyek/{id}")
    suspend fun deleteProyek(@Path("id")idProyek: String): Response<Void>
}