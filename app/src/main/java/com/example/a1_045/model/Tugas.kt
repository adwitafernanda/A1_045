package com.example.a1_045.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class Tugas (
    @SerialName("id_tugas")val idtugas : String,
    @SerialName("id_proyek") val idProyek : String? = "",
    @SerialName("id_tim") val idtim : String? = "",
    @SerialName("nama_tugas") val namatugas : String,
    @SerialName("deskripsi_tugas") val deskripsitugas : String,
    @SerialName("prioritas") val prioritas : String,
    @SerialName("status_tugas") val statustugas : String
)

@Serializable
data class AllTugasResponse(
    val status: Boolean,
    val message: String,
    val data: List<Tugas>
)

@Serializable
data class TugasDetailResponse(
    val status: Boolean,
    val message: String,
    val data:Tugas
)