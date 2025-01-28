package com.example.a1_045.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Proyek (
    @SerialName("id_proyek")val idProyek : String,
    @SerialName("nama_proyek") val namaproyek : String,
    @SerialName("deskripsi_proyek") val deskripsiproyek : String,
    @SerialName("tanggal_mulai") val tanggalmulai : String,
    @SerialName("tanggal_berakhir") val tanggalberakhir : String,
    @SerialName("status_proyek") val statusproyek : String
)

@Serializable
data class AllProyekResponse(
    val status: Boolean,
    val message: String,
    val data: List<Proyek>
)

@Serializable
data class ProyekDetailResponse(
    val status: Boolean,
    val message: String,
    val data:Proyek
)