package com.example.a1_045.ui.page.proyek.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a1_045.repository.ProyekRepository
import com.example.a1_045.ui.page.proyek.view.DestinasiUpdate
import kotlinx.coroutines.launch


class UpdateViewModel (
    savedStateHandle: SavedStateHandle,
    private val pyk: ProyekRepository
): ViewModel(){
    var updateUiState by mutableStateOf(InsertUiState())
        private set

    private val idProyek: String = checkNotNull(savedStateHandle[DestinasiUpdate.ID])

    init {
        viewModelScope.launch {
            updateUiState = pyk.getProyekbyid(idProyek)
                .toUiStatePyk()
        }
    }

    fun updateInsertPykState(insertUiEvent: InsertUiEvent){
        updateUiState = InsertUiState(insertUiEvent = insertUiEvent)
    }

    suspend fun updatePyk(){
        viewModelScope.launch {
            try {
                pyk.updateProyek(idProyek, updateUiState.insertUiEvent.toProyek())
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}