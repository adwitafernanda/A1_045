package com.example.a1_045.service

import com.example.a1_045.model.AllAnggotaResponse
import com.example.a1_045.model.Anggota
import com.example.a1_045.model.AnggotaDetailResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface AnggotaService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )

    //@POST("insertmahasiswa.php")
    @POST("anggota/.")
    suspend fun insertAnggota(@Body anggota: Anggota)

    //@GET("bacamahasiswa.php")
    @GET("anggota/.")
    suspend fun getAllAnggota(): AllAnggotaResponse

    //@GET("baca1mahasiswa.php")
    @GET("anggota/{idanggota}")
    suspend fun getAnggotabyId(@Path("idanggota")idanggota: String): AnggotaDetailResponse

    // @PUT("editmahasiswa.php")
    @PUT("anggota/{idanggota}")
    suspend fun updateAnggota(@Path("idanggota")idanggota: String, @Body anggota: Anggota)

    //@DELETE("deletemahasiswa.php")
    @DELETE("anggota/{idanggota}")
    suspend fun deleteAnggota(@Path("idanggota")idanggota: String): Response<Void>
}