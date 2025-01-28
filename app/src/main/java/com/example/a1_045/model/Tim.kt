package com.example.a1_045.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class Tim (
    @SerialName("id_tim")val idtim : String,
    @SerialName("nama_tim") val namatim : String,
    @SerialName("deskripsi_tim") val deskripsitim : String
)
@Serializable
data class AllTimResponse(
    val status: Boolean,
    val message: String,
    val data: List<Tim>
)

@Serializable
data class TimDetailResponse(
    val status: Boolean,
    val message: String,
    val data:Tim
)

