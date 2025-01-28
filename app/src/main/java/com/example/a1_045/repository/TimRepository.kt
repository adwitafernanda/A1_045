package com.example.a1_045.repository


import com.example.a1_045.model.AllTimResponse
import com.example.a1_045.model.Tim
import com.example.a1_045.service.TimService
import java.io.IOException

interface  TimRepository{
    suspend fun insertTim(tim: Tim)
    suspend fun getTim(): AllTimResponse
    suspend fun updateTim(idTim: String,tim: Tim)
    suspend fun deleteTim(idTim: String)
    suspend fun getTimbyid(idTim: String): Tim
}

class NetworkTimRepository(
    private val timApiService: TimService
):TimRepository {
    override suspend fun insertTim(tim: Tim) {
        timApiService.insertTim((tim))
    }

    override suspend fun getTim(): AllTimResponse =
        timApiService.getAllTim()


    override suspend fun updateTim(idTim: String, tim: Tim) {
        timApiService.updateTim(idTim, tim)
    }

    override suspend fun deleteTim(idTim: String) {
        try {
            val response = timApiService.deleteTim(idTim)
            if (!response.isSuccessful){
                throw IOException("Failed to delete tim. HTTP Status code:"+
                        "${response.code()}")
            }else {
                response.message()
                println(response.message())
            }
        }catch (e:Exception){
            throw e
        }
    }

    override suspend fun getTimbyid(idTim: String): Tim {
        return timApiService.getTimbyId(idTim).data
    }
}