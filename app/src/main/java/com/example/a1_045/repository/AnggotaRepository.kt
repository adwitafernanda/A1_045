package com.example.a1_045.repository

import com.example.a1_045.model.AllAnggotaResponse
import com.example.a1_045.model.Anggota
import com.example.a1_045.service.AnggotaService
import java.io.IOException

interface  AnggotaRepository{
    suspend fun insertAnggota(anggota: Anggota)
    suspend fun getAnggota(): AllAnggotaResponse
    suspend fun updateAnggota(idanggota: String,anggota: Anggota)
    suspend fun deleteAnggota(idanggota: String)
    suspend fun getAnggotabyid(idanggota: String): Anggota
}

class NetworkAnggotaRepository(
    private val anggotaApiService: AnggotaService
):AnggotaRepository {
    override suspend fun insertAnggota(anggota: Anggota) {
        anggotaApiService.insertAnggota((anggota))
    }

    override suspend fun getAnggota(): AllAnggotaResponse =
        anggotaApiService.getAllAnggota()


    override suspend fun updateAnggota(idanggota: String, anggota: Anggota) {
        anggotaApiService.updateAnggota(idanggota, anggota)
    }

    override suspend fun deleteAnggota(idanggota: String) {
        try {
            val response = anggotaApiService.deleteAnggota(idanggota)
            if (!response.isSuccessful){
                throw IOException("Failed to delete anggota. HTTP Status code:"+
                        "${response.code()}")
            }else {
                response.message()
                println(response.message())
            }
        }catch (e:Exception){
            throw e
        }
    }

    override suspend fun getAnggotabyid(idanggota: String): Anggota {
        return anggotaApiService.getAnggotabyId(idanggota).data
    }
}