package com.example.a1_045.ui.page.team.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a1_045.repository.TimRepository
import com.example.a1_045.ui.page.team.view.DestinasiUpdateTim
import kotlinx.coroutines.launch

class UpdateTimViewModel (
    savedStateHandle: SavedStateHandle,
    private val tm: TimRepository
): ViewModel(){
    var updateUiState by mutableStateOf(InsertUiState())
        private set

    private val idtim: String = checkNotNull(savedStateHandle[DestinasiUpdateTim.IDTim])

    init {
        viewModelScope.launch {
            updateUiState = tm.getTimbyid(idtim)
                .toUiStateTm()
        }
    }

    fun updateInsertTmState(insertUiEvent: InsertUiEvent){
        updateUiState = InsertUiState(insertUiEvent = insertUiEvent)
    }

    suspend fun updateTm(){
        viewModelScope.launch {
            try {
                tm.updateTim(idtim, updateUiState.insertUiEvent.toTim())
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}