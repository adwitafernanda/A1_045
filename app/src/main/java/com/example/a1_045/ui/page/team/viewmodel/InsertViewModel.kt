package com.example.a1_045.ui.page.team.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a1_045.model.Tim
import com.example.a1_045.repository.TimRepository
import kotlinx.coroutines.launch


class InsertTimViewModel(private val timRepository: TimRepository) : ViewModel() {

    var uiState by mutableStateOf(InsertUiState())
        private set

    fun InsertTimState(event: InsertUiEvent) {
        uiState = uiState.copy(insertUiEvent = event)
    }

    fun insertTim() {
        viewModelScope.launch {
            try {
                val tim = uiState.insertUiEvent.toTim()
                timRepository.insertTim(tim)
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
    val idtim: String = "",
    val namatim: String = "",
    val deskripsitim: String = ""
)

fun InsertUiEvent.toTim(): Tim = Tim(
    idtim = idtim,
    namatim = namatim,
    deskripsitim = deskripsitim
)


fun Tim.toUiStateTm(): InsertUiState = InsertUiState(
    insertUiEvent = toInsertUiEvent()
)


fun Tim.toInsertUiEvent(): InsertUiEvent = InsertUiEvent(
    idtim = idtim,
    namatim = namatim,
    deskripsitim = deskripsitim
)