package com.example.a1_045.repository

import com.example.a1_045.model.AllTugasResponse
import com.example.a1_045.model.Tugas
import com.example.a1_045.service.TugasService
import java.io.IOException

interface  TugasRepository{
    suspend fun insertTugas(tugas: Tugas)
    suspend fun getTugas(): AllTugasResponse
    suspend fun updateTugas(idtugas: String,tugas: Tugas)
    suspend fun deleteTugas(idtugas: String)
    suspend fun getTugasbyid(idtugas: String): Tugas
}

class NetworkTugasRepository(
    private val tugasApiService: TugasService
):TugasRepository {
    override suspend fun insertTugas(tugas: Tugas) {
        tugasApiService.insertTugas((tugas))
    }

    override suspend fun getTugas(): AllTugasResponse =
        tugasApiService.getAllTugas()


    override suspend fun updateTugas(idtugas: String, tugas: Tugas) {
        tugasApiService.updateTugas(idtugas, tugas)
    }

    override suspend fun deleteTugas(idtugas: String) {
        try {
            val response = tugasApiService.deleteTugas(idtugas)
            if (!response.isSuccessful){
                throw IOException("Failed to delete tugas. HTTP Status code:"+
                        "${response.code()}")
            }else {
                response.message()
                println(response.message())
            }
        }catch (e:Exception){
            throw e
        }
    }

    override suspend fun getTugasbyid(idtugas: String): Tugas {
        return tugasApiService.getTugasbyId(idtugas).data
    }
}