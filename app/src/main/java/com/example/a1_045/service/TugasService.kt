package com.example.a1_045.service

import com.example.a1_045.model.AllProyekResponse
import com.example.a1_045.model.AllTugasResponse
import com.example.a1_045.model.Proyek
import com.example.a1_045.model.ProyekDetailResponse
import com.example.a1_045.model.Tugas
import com.example.a1_045.model.TugasDetailResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TugasService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )

    //@POST("insertmahasiswa.php")
    @POST("tugas/tugas")
    suspend fun insertTugas(@Body tugas: Tugas)

    //@GET("bacamahasiswa.php")
    @GET("tugas/.")
    suspend fun getAllTugas(): AllTugasResponse

    //@GET("baca1mahasiswa.php")
    @GET("tugas/{idtugas}")
    suspend fun getTugasbyId(@Path("idtugas")idtugas: String): TugasDetailResponse

    // @PUT("editmahasiswa.php")
    @PUT("tugas/{idtugas}")
    suspend fun updateTugas(@Path("idtugas")idtugas: String, @Body tugas: Tugas)

    //@DELETE("deletemahasiswa.php")
    @DELETE("tugas/{idtugas}")
    suspend fun deleteTugas(@Path("idtugas")idtugas: String): Response<Void>
}