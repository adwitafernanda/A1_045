package com.example.a1_045.ui.page.anggota.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a1_045.model.Tim
import com.example.a1_045.repository.AnggotaRepository
import com.example.a1_045.repository.TimRepository
import com.example.a1_045.ui.page.anggota.view.DestinasiUpdateAnggota
import kotlinx.coroutines.launch

class UpdateAnggotaViewModel(
    savedStateHandle: SavedStateHandle,
    private val agt: AnggotaRepository,
    private val tim: TimRepository
) : ViewModel() {

    // State untuk UI
    var updateUiState by mutableStateOf(AnggotaUiState())
        private set

    var timList by mutableStateOf(listOf<Tim>())
        private set

    // ID anggota yang diterima dari SavedStateHandle
    private val idanggota: String = checkNotNull(savedStateHandle[DestinasiUpdateAnggota.IDAnggota])

    init {
        // Mengambil data anggota berdasarkan ID anggota dan proyek/tim
        viewModelScope.launch {
            updateUiState = agt.getAnggotabyid(idanggota).toUiStateAnggota()
        }
        loadData()
    }

    // Fungsi untuk memuat data proyek dan tim
    fun loadData() {
        viewModelScope.launch {
            try {
                timList = tim.getTim().data
            } catch (e: Exception) {
                e.printStackTrace() // Log error jika gagal mengambil data
            }
        }
    }

    // Fungsi untuk update state anggota
    fun updateInsertAnggotaState(insertUiEvent: AnggotaUiEvent) {
        updateUiState = AnggotaUiState(anggotaUiEvent = insertUiEvent)
    }

    // Fungsi untuk melakukan update anggota
    suspend fun updateAgt() {
        try {
            // Melakukan update anggota berdasarkan ID anggota dan data yang ada di UI state
            agt.updateAnggota(idanggota, updateUiState.anggotaUiEvent.toAnggota())
        } catch (e: Exception) {
            e.printStackTrace() // Tangani error jika update gagal
        }
    }
}