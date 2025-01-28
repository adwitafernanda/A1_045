package com.example.a1_045.ui.page.tugas.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a1_045.model.Proyek
import com.example.a1_045.model.Tim
import com.example.a1_045.repository.ProyekRepository
import com.example.a1_045.repository.TimRepository
import com.example.a1_045.repository.TugasRepository
import com.example.a1_045.ui.page.tugas.view.DestinasiUpdateTugas
import kotlinx.coroutines.launch

class UpdateTugasViewModel(
    savedStateHandle: SavedStateHandle,
    private val tgs: TugasRepository,
    private val proyek: ProyekRepository,
    private val tim: TimRepository
) : ViewModel() {

    // State untuk UI
    var updateUiState by mutableStateOf(TugasUiState())
        private set

    var proyekList by mutableStateOf(listOf<Proyek>())
        private set

    var timList by mutableStateOf(listOf<Tim>())
        private set

    private val idtugas: String = checkNotNull(savedStateHandle[DestinasiUpdateTugas.IDTugas])

    init {
        viewModelScope.launch {
            updateUiState = tgs.getTugasbyid(idtugas).toUiStateTugas()
        }
        loadData()
    }

    // Fungsi untuk memuat data proyek dan tim
    fun loadData() {
        viewModelScope.launch {
            try {
                proyekList = proyek.getProyek().data
                timList = tim.getTim().data
            } catch (e: Exception) {
                e.printStackTrace() // Log error jika gagal mengambil data
            }
        }
    }

    // Fungsi untuk update state tugas
    fun updateInsertTugasState(insertUiEvent: TugasUiEvent) {
        updateUiState = TugasUiState(tugasUiEvent = insertUiEvent)
    }

    // Fungsi untuk melakukan update tugas
    suspend fun updateTgs() {
        try {
            // Melakukan update tugas berdasarkan ID tugas dan data yang ada di UI state
            tgs.updateTugas(idtugas, updateUiState.tugasUiEvent.toTugas())
        } catch (e: Exception) {
            e.printStackTrace() // Tangani error jika update gagal
        }
    }
}
