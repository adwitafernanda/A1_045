package com.example.a1_045.repository

import com.example.a1_045.model.AllProyekResponse
import com.example.a1_045.model.Proyek
import com.example.a1_045.model.ProyekDetailResponse
import com.example.a1_045.service.ProyekService
import java.io.IOException

interface  ProyekRepository{
    suspend fun insertProyek(proyek: Proyek)
    suspend fun getProyek(): AllProyekResponse
    suspend fun updateProyek(idProyek: String,proyek:Proyek)
    suspend fun deleteProyek(idProyek: String)
    suspend fun getProyekbyid(idProyek: String): Proyek
}

class NetworkProyekRepository(
    private val proyekApiService: ProyekService
):ProyekRepository {
    override suspend fun insertProyek(proyek: Proyek) {
        proyekApiService.insertProyek((proyek))
    }

    override suspend fun getProyek(): AllProyekResponse =
        proyekApiService.getAllProyek()


    override suspend fun updateProyek(idProyek: String, proyek: Proyek) {
        proyekApiService.updateProyek(idProyek, proyek)
    }

    override suspend fun deleteProyek(idProyek: String) {
        try {
            val response = proyekApiService.deleteProyek(idProyek)
            if (!response.isSuccessful){
                throw IOException("Failed to delete proyek. HTTP Status code:"+
                        "${response.code()}")
            }else {
                response.message()
                println(response.message())
            }
        }catch (e:Exception){
            throw e
        }
    }

    override suspend fun getProyekbyid(idProyek: String): Proyek {
        return proyekApiService.getProyekbyId(idProyek).data
    }
}