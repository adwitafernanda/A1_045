package com.example.a1_045.model
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class Anggota (
    @SerialName("id_anggota")val idanggota : String,
    @SerialName("id_tim") val idtim : String,
    @SerialName("nama_anggota") val namaanggota : String,
    @SerialName("peran") val peran : String,
)

@Serializable
data class AllAnggotaResponse(
    val status: Boolean,
    val message: String,
    val data: List<Anggota>
)

@Serializable
data class AnggotaDetailResponse(
    val status: Boolean,
    val message: String,
    val data:Anggota
)