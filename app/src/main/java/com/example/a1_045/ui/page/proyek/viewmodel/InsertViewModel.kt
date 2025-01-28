package com.example.a1_045.ui.page.proyek.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a1_045.model.Proyek
import com.example.a1_045.repository.ProyekRepository
import kotlinx.coroutines.launch

class InsertViewModel(private val proyekRepository: ProyekRepository) : ViewModel() {

    var uiState by mutableStateOf(InsertUiState())
        private set

    fun InsertProyekState(event: InsertUiEvent) {
        uiState = uiState.copy(insertUiEvent = event)
    }

    fun insertProyek() {
        viewModelScope.launch {
            try {
                val proyek = uiState.insertUiEvent.toProyek()
                proyekRepository.insertProyek(proyek)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class InsertUiState(
    val insertUiEvent: InsertUiEvent = InsertUiEvent()
)

data class InsertUiEvent(
    val idProyek: String = "",
    val namaproyek: String = "",
    val deskripsiproyek: String = "",
    val tanggalmulai: String = "",
    val tanggalberakhir: String = "",
    val statusproyek: String = ""
)

fun InsertUiEvent.toProyek(): Proyek = Proyek(
    idProyek = idProyek,
    namaproyek = namaproyek,
    deskripsiproyek = deskripsiproyek,
    tanggalmulai = tanggalmulai,
    tanggalberakhir = tanggalberakhir,
    statusproyek = statusproyek
)


fun Proyek.toUiStatePyk(): InsertUiState = InsertUiState(
    insertUiEvent = toInsertUiEvent()
)


fun Proyek.toInsertUiEvent(): InsertUiEvent = InsertUiEvent(
    idProyek = idProyek,
    namaproyek = namaproyek,
    deskripsiproyek = deskripsiproyek,
    tanggalmulai = tanggalmulai,
    tanggalberakhir = tanggalberakhir,
    statusproyek = statusproyek
)
