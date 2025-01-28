package com.example.a1_045.ui.page.anggota.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a1_045.model.Anggota
import com.example.a1_045.model.Tim
import com.example.a1_045.repository.AnggotaRepository
import com.example.a1_045.repository.TimRepository
import kotlinx.coroutines.launch

class InsertAnggotaViewModel(
    private val anggota: AnggotaRepository,
    private val tim: TimRepository
): ViewModel(){
    var anggotaUiState by mutableStateOf(AnggotaUiState())
        private set

    var timList by mutableStateOf(listOf<Tim>())
        private set

    init {
        loadData()
    }

    fun UpdateInsertAnggotaState(anggotaUiEvent: AnggotaUiEvent){
        anggotaUiState = AnggotaUiState(anggotaUiEvent = anggotaUiEvent)
    }

    suspend fun InsertAnggota(){
        viewModelScope.launch {
            try {
                anggota.insertAnggota(anggotaUiState.anggotaUiEvent.toAnggota())
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }

    fun loadData(){
        viewModelScope.launch {
            try {
                timList = tim.getTim().data
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }




}

data class AnggotaUiState(
    val anggotaUiEvent: AnggotaUiEvent = AnggotaUiEvent()
)

data class AnggotaUiEvent(
    val idanggota: String = "",
    val idtim: String = "",
    val namaanggota: String = "",
    val peran: String = "",
    )


fun AnggotaUiEvent.toAnggota(): Anggota = Anggota(
    idanggota =  idanggota,
    idtim = idtim,
    namaanggota = namaanggota,
    peran = peran
)

fun Anggota.toUiStateAnggota(): AnggotaUiState = AnggotaUiState(
    anggotaUiEvent = toAnggotaUiEvent()
)

fun Anggota.toAnggotaUiEvent(): AnggotaUiEvent = AnggotaUiEvent(
    idanggota =  idanggota,
    idtim = idtim.toString(),
    namaanggota = namaanggota,
    peran = peran,
)