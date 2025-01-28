package com.example.a1_045.dependenciesinjection

import com.example.a1_045.repository.AnggotaRepository
import com.example.a1_045.repository.NetworkAnggotaRepository
import com.example.a1_045.repository.NetworkProyekRepository
import com.example.a1_045.repository.NetworkTimRepository
import com.example.a1_045.repository.NetworkTugasRepository
import com.example.a1_045.repository.ProyekRepository
import com.example.a1_045.repository.TimRepository
import com.example.a1_045.repository.TugasRepository
import com.example.a1_045.service.AnggotaService
import com.example.a1_045.service.ProyekService
import com.example.a1_045.service.TimService
import com.example.a1_045.service.TugasService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit


interface AppContainerProyek{
    val proyekRepository: ProyekRepository
    val timRepository: TimRepository
    val tugasRepository:TugasRepository
    val anggotaRepository:AnggotaRepository
}

class ProyekContainer: AppContainerProyek {
    private val baseUrl = "http://10.0.2.2:3000/api/"
    private val json = Json { ignoreUnknownKeys = true }
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl).build()

    private val proyekService: ProyekService by lazy {
        retrofit.create(ProyekService::class.java)
    }
    override val proyekRepository: ProyekRepository by lazy {
        NetworkProyekRepository(proyekService)
    }

    private val timService: TimService by lazy {
        retrofit.create(TimService::class.java)
    }
    override val timRepository: TimRepository by lazy {
        NetworkTimRepository(timService)
    }

    private val tugasService: TugasService by lazy {
        retrofit.create(TugasService::class.java)
    }
    override val tugasRepository: TugasRepository by lazy {
        NetworkTugasRepository(tugasService)
    }

    private val anggotaService: AnggotaService by lazy {
        retrofit.create(AnggotaService::class.java)
    }
    override val anggotaRepository: AnggotaRepository by lazy {
        NetworkAnggotaRepository(anggotaService)
    }
}
